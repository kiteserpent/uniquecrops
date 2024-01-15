package com.remag.ucse.api;

import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.UCStrings;
import net.minecraft.world.item.ItemStack;

public interface IBookUpgradeable {

    default int getLevel(ItemStack stack) {

        if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_UPGRADE))
            return NBTUtils.getInt(stack, UCStrings.TAG_UPGRADE, -1);

        return -1;
    }

    default void setLevel(ItemStack stack, int level) {

        NBTUtils.setInt(stack, UCStrings.TAG_UPGRADE, level);
    }

    default boolean isMaxLevel(ItemStack stack) {

        return getLevel(stack) >= 10;
    }
}
