package com.remag.ucse.items.base;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemSeedsUC extends ItemNameBlockItem {

    public ItemSeedsUC(BaseCropsBlock block) {

        super(block, UCItems.defaultBuilder());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag whatIsthis) {

        boolean flag = Screen.hasShiftDown();

        if (flag) {
            list.add(Component.literal("Bonemealable: ").withStyle(ChatFormatting.GRAY).append(tf(getCrop().isBonemealable())));
            list.add(Component.literal("Click Harvest: ").withStyle(ChatFormatting.GRAY).append(tf(getCrop().isClickHarvest())));
            list.add(Component.literal("Ignores Growth Restrictions: ").withStyle(ChatFormatting.GRAY).append(tf(getCrop().isIgnoreGrowthRestrictions())));
        } else
            list.add(Component.literal("<Press Shift>").withStyle(ChatFormatting.GRAY));
    }

    private BaseCropsBlock getCrop() {

        return (BaseCropsBlock)this.getBlock();
    }

    private Component tf(boolean flag) {

        return Component.literal(String.valueOf(flag)).withStyle(flag ? ChatFormatting.GREEN : ChatFormatting.RED);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {

        return super.useOn(ctx);
    }
}
