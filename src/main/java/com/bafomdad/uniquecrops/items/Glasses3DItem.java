package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.api.IBookUpgradeable;
import com.bafomdad.uniquecrops.core.enums.EnumArmorMaterial;
import com.bafomdad.uniquecrops.items.base.ItemArmorUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;

public class Glasses3DItem extends ItemArmorUC implements IBookUpgradeable {

    public Glasses3DItem() {

        super(EnumArmorMaterial.GLASSES_3D, EquipmentSlot.HEAD);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {

        if (world.isClientSide) return;
        if (!isMaxLevel(stack)) return;
        if ((world.getGameTime() % 40) != 0) return;
        
        int light = world.getRawBrightness(player.blockPosition().offset(0, (int) player.getEyeHeight(), 0),
                world.getSkyDarken());
        if (light <= 3)
        	player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 250));
}

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {

        return false;
    }
}
