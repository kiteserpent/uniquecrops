package com.remag.ucse.items;

import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.core.enums.EnumArmorMaterial;
import com.remag.ucse.items.base.ItemArmorUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;

@SuppressWarnings("ALL")
public class Glasses3DItem extends ItemArmorUC implements IBookUpgradeable {

    public Glasses3DItem() {

        super(EnumArmorMaterial.GLASSES_3D, Type.HELMET);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {

        if (world.isClientSide) return;
        if (!isMaxLevel(stack)) return;

        int sunlight = world.getBrightness(LightLayer.SKY, player.blockPosition().offset(0, (int) player.getEyeHeight(), 0));
        if (sunlight <= 3)
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 30));
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {

        return false;
    }
}
