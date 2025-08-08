package com.remag.ucse.items;

import com.remag.ucse.core.UCStrings;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class PixelBrushItem extends ItemBaseUC {

    public PixelBrushItem() {

        super(UCItems.defaultBuilder().durability(131));
    }

    /*@Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {

        if (allowdedIn(tab)) {
            ItemStack brush = new ItemStack(this);
            items.add(brush.copy());
            brush.setDamageValue(brush.getMaxDamage());
            items.add(brush);
        }
    }

    private boolean allowdedIn(CreativeModeTab tab) {
        return true;
    }*/

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatisthis) {

        if (stack.hasTag() && stack.getTag().contains(UCStrings.TAG_BIOME)) {
            ResourceLocation biomeId = ResourceLocation.tryParse(stack.getTag().getString(UCStrings.TAG_BIOME));
            Biome biome = world.registryAccess().registryOrThrow(Registries.BIOME).get(biomeId);
            list.add(Component.literal(ChatFormatting.GREEN + "Biome: " + ChatFormatting.RESET + ForgeRegistries.BIOMES.getKey(biome).getPath()));
        } else {
            list.add(Component.literal(ChatFormatting.GREEN + "Biome: " + ChatFormatting.RESET + "<NONE>"));
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        if (ctx.getItemInHand().getDamageValue() == ctx.getItemInHand().getMaxDamage()) return InteractionResult.PASS;
        if (!ctx.getItemInHand().hasTag() || (ctx.getItemInHand().hasTag() && !ctx.getItemInHand().getTag().contains(UCStrings.TAG_BIOME))) return InteractionResult.PASS;

        ResourceLocation biomeId = ResourceLocation.tryParse(ctx.getItemInHand().getTag().getString(UCStrings.TAG_BIOME));
        boolean flag = UCUtils.setBiome(biomeId, ctx.getLevel(), ctx.getClickedPos());
        if (!flag) return InteractionResult.PASS;
        if (!ctx.getLevel().isClientSide())
            ctx.getItemInHand().hurtAndBreak(1, ctx.getPlayer(), (player) -> {});

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level world, Player player) {

        stack.setDamageValue(stack.getMaxDamage());
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {

        return false;
    }
}
