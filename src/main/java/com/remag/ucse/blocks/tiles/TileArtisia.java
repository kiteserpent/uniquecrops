package com.remag.ucse.blocks.tiles;

import com.remag.ucse.api.IArtisiaRecipe;
import com.remag.ucse.blocks.crops.Artisia;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.core.enums.EnumParticle;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.init.UCTiles;
import com.remag.ucse.network.PacketUCEffect;
import com.remag.ucse.network.UCPacketDispatcher;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TileArtisia extends BaseTileUC {

    public BlockPos core = BlockPos.ZERO;
    private ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
        }
    };

    private static final BlockPos[] GRIDPOS = {
            new BlockPos(-1, 0, -1), new BlockPos(1, 0, 1), new BlockPos(-1, 0, 1), new BlockPos(1, 0, -1),
            new BlockPos(1, 0, 0), new BlockPos(-1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1)
    };

    public TileArtisia(BlockPos pos, BlockState state) {

        super(UCTiles.ARTISIA.get(), pos, state);
    }

    public void findCore() {

        boolean foundGrid = true;
        for (int i = 0; i < GRIDPOS.length; i++) {
            BlockPos looppos = getBlockPos().offset(GRIDPOS[i]);
            BlockEntity te = getLevel().getBlockEntity(looppos);
            if (te instanceof TileArtisia) continue;

            else {
                foundGrid = false;
                break;
            }
        }
        if (foundGrid) {
            for (int i = 0; i < GRIDPOS.length; i++) {
                BlockPos looppos = getBlockPos().offset(GRIDPOS[i]);
                BlockEntity te = getLevel().getBlockEntity(looppos);
                if (te instanceof TileArtisia) {
                    TileArtisia tile = (TileArtisia)te;
                    tile.setCore(this.getBlockPos());
                }
            }
            this.setCore(getBlockPos());
        }
    }

    public void setCore(BlockPos pos) {

        this.core = pos;
    }

    public boolean isCore() {

        return (isValid()) && (this.core.equals(getBlockPos()));
    }

    public boolean isValid() {

        return this.core != null && !core.equals(BlockPos.ZERO);
    }

    public void setStackSpace(ItemEntity ei) {

        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < GRIDPOS.length; i++) {
            BlockPos loopPos = getBlockPos().offset(GRIDPOS[i]);
            BlockEntity te = getLevel().getBlockEntity(loopPos);
            if (te instanceof TileArtisia artisia) {
                if (canAccept(loopPos)) {
                    if (!artisia.getItem().isEmpty()) {
                        stacks.add(artisia.getItem());
                    }
                    else if (artisia.getItem().isEmpty()) {
                        ItemStack stack = ei.getItem().copy();
                        stack.setCount(1);
                        if (!getLevel().isClientSide) {
                            artisia.setItem(stack);
                            stacks.add(stack);
                            ei.getItem().shrink(1);
                        }
                        if (ei.getItem().getCount() <= 0) ei.discard();
                        break;
                    }
                }
            }
        }
        if (stacks.size() >= 8 && ei.isAlive()) {
            ItemStack stack = ei.getItem().copy();
            stack.setCount(1);
            if (!getLevel().isClientSide) {
                this.setItem(stack);
                ei.getItem().shrink(1);
                stacks.add(stack);
            }
            if (ei.getItem().getCount() <= 0) ei.discard();

            Optional<IArtisiaRecipe> seedRecipe = level.getRecipeManager().getRecipeFor(UCItems.ARTISIA_TYPE, UCUtils.wrap(stacks), level);
            seedRecipe.ifPresent(recipe -> {
               if (!getLevel().isClientSide) {
                    ItemStack output = recipe.getResultItem().copy();
                    clearItems();
                    this.setItem(output);
               }
            });
        }
    }

    private boolean canAccept(BlockPos pos) {

        BlockState state = getLevel().getBlockState(pos);
        return ((Artisia)state.getBlock()).isMaxAge(state);
    }

    private void clearItems() {

        for (int i = 0; i < GRIDPOS.length; i++) {
            BlockPos looppos = getBlockPos().offset(GRIDPOS[i]);
            BlockEntity te = getLevel().getBlockEntity(looppos);
            if (te instanceof TileArtisia) {
                ((TileArtisia)te).setItem(ItemStack.EMPTY);
                UCPacketDispatcher.dispatchTEToNearbyPlayers(te);
                UCPacketHandler.sendToNearbyPlayers(getLevel(), looppos, new PacketUCEffect(EnumParticle.CLOUD, looppos.getX(), looppos.getY() + 0.5D, looppos.getZ(), 6));
            }
        }
    }

    public ItemStack getItem() {

        return inv.getStackInSlot(0);
    }

    public void setItem(ItemStack stack) {

        inv.setStackInSlot(0, stack);
        UCPacketDispatcher.dispatchTEToNearbyPlayers(this);
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putLong("Core", core.asLong());
        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        this.core = BlockPos.of(tag.getLong("Core"));
        inv.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    public boolean triggerEvent(int id, int param) {

        return super.triggerEvent(id, param);
    }
}
