package com.remag.ucse.potions;

import com.remag.ucse.UniqueCrops;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;

public class PotionZombification extends MobEffect {

    public static final ResourceKey<DamageType> ZOMBIFICATION_KEY = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "zombification")
    );

    public static DamageSource zombification(Level level) {
        Holder<DamageType> holder = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(ZOMBIFICATION_KEY);
        return new DamageSource(holder);
    }

    public PotionZombification() {

        super(MobEffectCategory.HARMFUL, 0x93C47D);
        MinecraftForge.EVENT_BUS.addListener(this::onPotionExpire);
        MinecraftForge.EVENT_BUS.addListener(this::onPotionRemove);
    }

    private void onPotionExpire(MobEffectEvent.Expired event) {

        if (event.getEffectInstance() == null) return;
        if (event.getEffectInstance().getEffect() == this && event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                ZombieVillager zombie = EntityType.ZOMBIE_VILLAGER.create(player.level());
                zombie.moveTo(player.getX(), player.getY(), player.getZ(), player.getYRot(), player.getXRot());
                player.level().addFreshEntity(zombie);
                if (player.level().getDifficulty() != Difficulty.PEACEFUL)
                    player.hurt(zombification(player.level()), Float.MAX_VALUE);
            }
        }
    }

    private void onPotionRemove(MobEffectEvent.Remove event) {

        if (event.getEffectInstance() == null) return;

        if (event.getEffect() == this)
            event.setCanceled(true);
    }
}
