package com.remag.ucse.blocks.tiles;

import com.remag.ucse.gui.ContainerCraftyPlant;
import com.remag.ucse.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileCraftyPlant extends BaseTileUC implements MenuProvider {

    final ItemStackHandler inv = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {

            setChanged();
        }
    };

    public TileCraftyPlant(BlockPos pos, BlockState state) {

        super(UCTiles.CRAFTYPLANT.get(), pos, state);
    }

    public IItemHandlerModifiable getCraftingInventory() {

        return this.inv;
    }

    public int getCraftingSize() {

        return 9;
    }

    public ItemStack getStaff() {

        return this.inv.getStackInSlot(10);
    }

    public ItemStack getResult() {

        return this.inv.getStackInSlot(9);
    }

    public void setResult(ItemStack toSet) {

        this.inv.setStackInSlot(9, toSet);
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.put("inventory", inv.serializeNBT());
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        inv.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    public Component getDisplayName() {

        return Component.translatable("container.ucse.craftyplant");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowId, Inventory playerinv, Player player) {

        return new ContainerCraftyPlant(windowId, playerinv, this);
    }
}
