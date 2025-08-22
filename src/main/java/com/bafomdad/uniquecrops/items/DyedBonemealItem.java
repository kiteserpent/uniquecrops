package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.items.base.ItemBaseUC;

import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;

public class DyedBonemealItem extends ItemBaseUC {

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        ItemStack stack = ctx.getPlayer().getItemInHand(ctx.getHand());
        if (!ctx.getPlayer().mayUseItemAt(ctx.getClickedPos(), ctx.getClickedFace(), stack))
        	return InteractionResult.FAIL;

        if (BoneMealItem.applyBonemeal(stack, ctx.getLevel(), ctx.getClickedPos(), ctx.getPlayer())) {
            if (!ctx.getLevel().isClientSide()) {
                ctx.getLevel().levelEvent(2005, ctx.getClickedPos(), 0);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean dispenseOn(ItemStack pItemStack, Level pLevel, BlockPos pBlockPos) {
    	if (pLevel.isClientSide())
    		return false;
		if (BoneMealItem.applyBonemeal(pItemStack, pLevel, pBlockPos, null)) {
			pLevel.levelEvent(2005, pBlockPos, 0);
			return true;
    	}
    	return false;
    }

}
