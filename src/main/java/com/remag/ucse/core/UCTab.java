package com.remag.ucse.core;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class UCTab extends CreativeModeTab {

    public UCTab() {

        super(UniqueCrops.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {

        return new ItemStack(UCItems.BOOK_GUIDE::get);
    }
}
