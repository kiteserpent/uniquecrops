package com.remag.ucse.items;

import com.remag.ucse.core.UCStrings;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class StaffPhantomItem extends StaffBatItem {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {

        tooltip.add(Component.translatable(UCStrings.TOOLTIP + "phantomstaff").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public List<? extends LivingEntity> getEntityToErase(Level world, BlockPos pos) {

        return world.getEntitiesOfClass(Phantom.class, new AABB(pos.offset(-15, -15, -15), pos.offset(15, 15, 15)));
    }
}
