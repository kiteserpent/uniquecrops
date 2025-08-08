package com.remag.ucse.gui;

import com.remag.ucse.api.ICropPower;
import com.remag.ucse.blocks.tiles.TileCraftyPlant;
import com.remag.ucse.capabilities.CPProvider;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.init.UCScreens;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContainerCraftyPlant extends AbstractContainerMenu {

    TileCraftyPlant tile;

    public ContainerCraftyPlant(int windowId, Inventory playerinv, TileCraftyPlant tile) {

        super(UCScreens.CRAFTYPLANT.get(), windowId);
        this.tile = tile;

        addSlot(new SlotSeedCrafting(tile.getCraftingInventory(), 9, 124, 35));
        addSlot(new SlotSeedCrafting(tile.getCraftingInventory(), 10, 94, 17));

        for (int i = 0; i < 3; ++i) {
            for (int m = 0; m < 3; ++m)
                this.addSlot(new SlotSeedCrafting(tile.getCraftingInventory(), m + i * 3, 30 + m * 18, 17 + i * 18));
        }
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 9; k++)
                addSlot(new Slot(playerinv, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
        }
        for (int l = 0; l < 9; l++)
            addSlot(new Slot(playerinv, l, 8 + l * 18, 142));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {

        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);

        if (slot != null && slot.hasItem()) {
            ItemStack stack1 = slot.getItem();
            stack = stack1.copy();
            int size = tile.getCraftingInventory().getSlots();

            if (i < size) {
                slot.onTake(player, stack1);
                if (!this.moveItemStackTo(stack1, size, this.slots.size(), true))
                    return ItemStack.EMPTY;
            } else {
                boolean b = false;
                for (int j = 0; j < size; j++) {
                    if (this.getSlot(j).mayPlace(stack1)) {
                        if (this.moveItemStackTo(stack1, j, j + 1, false)) {
                            b = true;
                            break;
                        }
                    }
                }
                if (!b)
                    return ItemStack.EMPTY;
            }
            if (stack1.getCount() == 0)
                slot.set(ItemStack.EMPTY);
            else
                slot.setChanged();

            slot.onTake(player, stack1);
        }
        return stack;
    }

    @Override
    public boolean stillValid(Player player) {

        return true;
    }

    private class SlotSeedCrafting extends SlotItemHandler {

        final int COST = 50;
        final int indexSlot;

        public SlotSeedCrafting(IItemHandler itemHandler, int index, int xPosition, int yPosition) {

            super(itemHandler, index, xPosition, yPosition);
            this.indexSlot = index;
        }

        @Override
        public void setChanged() {

            IItemHandler handler = getItemHandler();
            List<ItemStack> stacks = IntStream.range(0, tile.getCraftingSize()).mapToObj(i -> handler.getStackInSlot(i)).collect(Collectors.toList());
            AtomicReference<ItemStack> result = new AtomicReference<>(ItemStack.EMPTY);
            tile.getLevel().getRecipeManager().getRecipeFor(UCItems.ARTISIA_TYPE, UCUtils.wrap(stacks), tile.getLevel())
                    .ifPresent(recipe -> {
                        result.set(recipe.getResultItem(tile.getLevel().registryAccess()).copy());
                    });
            if (!result.get().isEmpty()) {
                tile.setResult(result.get());
                tile.setChanged();
            }
        }

        @Override
        public void onTake(Player player, ItemStack stack) {

            if (indexSlot == tile.getCraftingSize()) {
                if (!stack.isEmpty()) {
                    LazyOptional<ICropPower> cap = tile.getStaff().getCapability(CPProvider.CROP_POWER, null);
                    if (!cap.isPresent()) {
                        IntStream.range(0, tile.getCraftingSize()).forEach(i -> {
                            if (!getItemHandler().getStackInSlot(i).isEmpty())
                                getItemHandler().getStackInSlot(i).shrink(1);
                        });
                    }
                    cap.ifPresent(crop -> {
                        if (crop.getPower() >= COST)
                            crop.remove(COST);
                        else
                            IntStream.range(0, tile.getCraftingSize()).forEach(i -> {
                                if (!getItemHandler().getStackInSlot(i).isEmpty())
                                    getItemHandler().getStackInSlot(i).shrink(1);
                            });
                    });
                }
            }
            super.onTake(player, stack);
        }

        @Override
        public boolean mayPlace(@Nonnull ItemStack stack) {

            if (indexSlot == 9) return false;
            if (indexSlot == 10)
                return stack.getItem() == UCItems.WILDWOOD_STAFF.get();

            return true;
        }
    }
}
