package com.remag.ucse.items;

import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

public class VampiricOintmentItem extends ItemBaseUC {

    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (!(target instanceof Player)) {
            if (!hasTaglock(stack) && !player.level().isClientSide) {
                ItemStack newStack = new ItemStack(this);
                setTaglock(newStack, target);
                ItemHandlerHelper.giveItemToPlayer(player, newStack);
                stack.shrink(1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public boolean hasTaglock(ItemStack stack) {

        return stack.hasTag() && stack.getTag().contains(UCStrings.TAG_LOCK);
    }

    public void setTaglock(ItemStack stack, LivingEntity target) {

        UUID id = target.getUUID();
        NBTUtils.setString(stack, UCStrings.TAG_LOCK, id.toString());
    }
}
