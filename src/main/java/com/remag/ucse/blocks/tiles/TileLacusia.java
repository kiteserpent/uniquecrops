package com.remag.ucse.blocks.tiles;

import com.remag.ucse.init.UCBlocks;
import com.remag.ucse.init.UCTiles;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public class TileLacusia extends BaseTileUC {

    private final ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
        }
    };
    private int dir;
    private final int waitTime = 10;
    private final int waitTimeStuck = 20;

    public TileLacusia(BlockPos pos, BlockState state) {

        super(UCTiles.LACUSIA.get(), pos, state);
    }

    public void updateStuff() {

        boolean hasPower = level.hasNeighborSignal(getBlockPos());
        if (!level.isClientSide) {
            if (this.canAdd() && hasPower) {
                BlockEntity tileInv = null;
                for (Direction face : Direction.Plane.HORIZONTAL) {
                    BlockPos looppos = getBlockPos().relative(face);
                    if (!level.hasChunkAt(looppos)) return;

                    BlockEntity tile = level.getBlockEntity(looppos);
                    if (tile != null && tile.getCapability(ForgeCapabilities.ITEM_HANDLER, face).isPresent()) {
                        tileInv = tile;
                        dir = face.ordinal();
                        break;
                    }
                }
                if (tileInv != null) {
                    tileInv.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.from2DDataValue(dir))
                            .ifPresent(cap -> {
                                for (int i = 0; i < cap.getSlots(); i++) {
                                    ItemStack extract = cap.getStackInSlot(i);
                                    if (!extract.isEmpty()) {
                                        this.setItem(extract.copy());
                                        cap.extractItem(i, extract.getMaxStackSize(), false);
                                        this.markBlockForUpdate();
                                        break;
                                    }
                                }
                            });
                }
            }
            else if (!canAdd()) {
                for (Direction face : Direction.Plane.HORIZONTAL) {
                    if (hasPower) face = face.getOpposite();
                    if (directionMatches(face)) continue;

                    BlockPos looppos = getBlockPos().relative(face);
                    if (!level.hasChunkAt(looppos)) return;

                    BlockEntity tile = level.getBlockEntity(looppos);
                    if (tile instanceof TileLacusia lacusia) {
                        if (lacusia.canAdd()) {
                            lacusia.setItem(getItem());
                            this.setItem(ItemStack.EMPTY);
                            lacusia.markBlockForUpdate();
                            this.markBlockForUpdate();
                            dir = face.ordinal();
                            lacusia.dir = face.getOpposite().ordinal();
                            level.scheduleTick(looppos, UCBlocks.LACUSIA_CROP.get(), waitTime);
                            break;
                        }
                        else {
                            dir = face.ordinal();
                            level.scheduleTick(getBlockPos(), UCBlocks.LACUSIA_CROP.get(), waitTimeStuck);
                            lacusia.dir = face.ordinal();
                        }
                    }
                    if (tile != null && tile.getCapability(ForgeCapabilities.ITEM_HANDLER, face).isPresent()) {
                        Direction finalFace = face;
                        tile.getCapability(ForgeCapabilities.ITEM_HANDLER, face).ifPresent(cap -> {
                            ItemStack simulate = ItemHandlerHelper.insertItem(cap, getItem().copy(), true);
                            int available = getItem().getCount() - simulate.getCount();

                            if (available >= getItem().getCount()) {
                                ItemHandlerHelper.insertItem(cap, getItem(), false);
                                this.setItem(simulate);
                                this.markBlockForUpdate();
                                dir = finalFace.ordinal();
                                if (!getItem().isEmpty())
                                    level.scheduleTick(getBlockPos(), UCBlocks.LACUSIA_CROP.get(), waitTime);
//                                break;
                            }
                            else if (available <= 0) {
                                dir = finalFace.ordinal();
                                level.scheduleTick(getBlockPos(), UCBlocks.LACUSIA_CROP.get(), waitTimeStuck);
                            }
                        });
                    }
                }
            }
        }
    }

    private boolean directionMatches(Direction facing) {

        return facing.ordinal() == dir;
    }

    public boolean canAdd() {

        return inv.getStackInSlot(0).isEmpty();
    }

    public ItemStack getItem() {

        return inv.getStackInSlot(0);
    }

    public void setItem(ItemStack toSet) {

        inv.setStackInSlot(0, toSet);
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putInt("UC:facing", dir);
        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        dir = tag.getInt("UC:facing");
        inv.deserializeNBT(tag.getCompound("inventory"));
    }
}
