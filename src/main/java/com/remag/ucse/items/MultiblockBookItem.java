package com.remag.ucse.items;

import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.patchouli.api.PatchouliAPI;

import javax.annotation.Nullable;
import java.util.List;

public class MultiblockBookItem extends ItemBaseUC {

    public MultiblockBookItem() {

        super(UCItems.unstackable().rarity(Rarity.RARE));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        list.add(getEdition().copy().withStyle(ChatFormatting.GOLD));
    }

    public static Component getEdition() {

        try {
            return PatchouliAPI.get().getSubtitle(ForgeRegistries.ITEMS.getKey(UCItems.BOOK_MULTIBLOCK.get()));
        } catch (IllegalArgumentException e) {
            return Component.literal("");
        }
//        return PatchouliAPI.get().getSubtitle(UCItems.BOOK_MULTIBLOCK.getId());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getMainHandItem();

        if (player instanceof ServerPlayer sPlayer) {
            PatchouliAPI.get().openBookGUI(sPlayer, UCItems.BOOK_MULTIBLOCK.getId());
        }
        return InteractionResultHolder.success(stack);
    }
}
