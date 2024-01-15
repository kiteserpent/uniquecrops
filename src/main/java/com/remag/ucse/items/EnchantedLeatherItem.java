package com.remag.ucse.items;

import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class EnchantedLeatherItem extends ItemBaseUC {

    public EnchantedLeatherItem() {

        super(UCItems.unstackable().rarity(Rarity.UNCOMMON));
    }

    @Override
    public boolean isFoil(ItemStack stack) {

        return true;
    }
}
