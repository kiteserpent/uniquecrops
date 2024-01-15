package com.remag.ucse.items;

import com.remag.ucse.api.IItemBooster;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.world.item.ItemStack;

public class EmeradicDiamondItem extends ItemBaseUC implements IItemBooster {

    @Override
    public boolean isFoil(ItemStack stack) {

        return true;
    }

    @Override
    public int getRange(ItemStack stack) {

        return 5;
    }

    @Override
    public int getPower(ItemStack stack) {

        return 1;
    }
}
