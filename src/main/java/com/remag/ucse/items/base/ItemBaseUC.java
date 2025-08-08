package com.remag.ucse.items.base;

import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.init.UCItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBaseUC extends Item {

    public ItemBaseUC() {

        super(UCItems.defaultBuilder());
    }

    public ItemBaseUC(Properties prop) {

        super(prop);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag flag) {

        if (stack.getItem() instanceof IBookUpgradeable) {
            if (((IBookUpgradeable)stack.getItem()).getLevel(stack) > -1)
                list.add(Component.literal(ChatFormatting.GOLD + "+" + ((IBookUpgradeable)stack.getItem()).getLevel(stack)));
            else
                list.add(Component.literal(ChatFormatting.GOLD + "Upgradeable"));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {

        if (stack.getItem().getName(stack).getString().contains("potion") || stack.getItem().getName(stack).getString().contains("Potion"))
            return true;

        return super.isFoil(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {

        if (entity instanceof Player) {
            if (stack.getItem().hasCraftingRemainingItem()) {
                ItemStack container = stack.getCraftingRemainingItem();
                if (!((Player)entity).isCreative()) {
                    ((Player)entity).getInventory().add(container);
                }
//                return container;
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}
