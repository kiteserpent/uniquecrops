package com.remag.ucse.items;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.nbt.ListTag;
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

public class GuideBookItem extends ItemBaseUC {

    public GuideBookItem() {

        super(UCItems.unstackable().rarity(Rarity.UNCOMMON));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        list.add(getEdition().copy().withStyle(ChatFormatting.GOLD));
    }

    public static Component getEdition() {

        try {
            return PatchouliAPI.get().getSubtitle(ForgeRegistries.ITEMS.getKey(UCItems.BOOK_GUIDE.get()));
        } catch (IllegalArgumentException e) {
            return Component.literal("");
        }
//        return PatchouliAPI.get().getSubtitle(UCItems.BOOK_GUIDE.getId());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide && player instanceof ServerPlayer serverPlayer) {
            // Use the ResourceLocation ID of the Patchouli book, not the item ID
            ResourceLocation bookId = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "book_guide");
            PatchouliAPI.get().openBookGUI(serverPlayer, bookId);
            return InteractionResultHolder.consume(stack); // Server consumed action
        }

        return InteractionResultHolder.pass(stack); // Pass on client or if not server player
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {

        if (!(entity instanceof Player)) return;

        if (stack.getItem() == this && isSelected) {
            if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_GROWTHSTAGES)) return;
            if (world.isClientSide) return;
            ListTag tagList = UCUtils.getServerTaglist(entity.getUUID());
            if (tagList != null)
                stack.addTagElement(UCStrings.TAG_GROWTHSTAGES, tagList);
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
