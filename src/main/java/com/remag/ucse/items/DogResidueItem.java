package com.remag.ucse.items;

import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class DogResidueItem extends ItemBaseUC {

    public DogResidueItem() {

        super(UCItems.defaultBuilder().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        if (hand == InteractionHand.MAIN_HAND) {
            boolean canFill = false;
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack loopStack = player.getInventory().items.get(i);
                if (loopStack.isEmpty()) {
                    player.getInventory().items.set(i, new ItemStack(UCItems.DOGRESIDUE.get()));
                    canFill = true;
                }
            }
            if (!world.isClientSide) {
                if (canFill)
                    player.sendSystemMessage(Component.literal("The rest of your inventory filled up with dog residue."));
                else
                    player.sendSystemMessage(Component.literal("You finished using it. An uneasy silence fills the room."));
            }
            return InteractionResultHolder.success(player.getMainHandItem());
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
