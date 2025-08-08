package com.remag.ucse.items;

import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class HandMirrorItem extends ItemBaseUC {

    public HandMirrorItem() {

        super(UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::reflectLazers);
    }


    private void reflectLazers(LivingAttackEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Guardian guardian) {
            ItemStack mirror = player.getOffhandItem();
            if (mirror.getItem() == this) {
                float damage = event.getAmount();

                if (!player.level().isClientSide && player.level() instanceof ServerLevel serverLevel) {
                    // Get Holder<DamageType> for magic
                    Holder<DamageType> magicDamageType = serverLevel.registryAccess()
                            .registryOrThrow(net.minecraft.core.registries.Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.MAGIC);

                    // Reflect damage back to the guardian
                    guardian.hurt(new DamageSource(magicDamageType, player), damage);

                    // Cancel incoming damage
                    event.setCanceled(true);

                    // Damage the mirror item
                    if (player instanceof ServerPlayer serverPlayer) {
                        mirror.hurt(1, serverLevel.random, serverPlayer);
                    }
                }
            }
        }
    }
}
