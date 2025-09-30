package com.bafomdad.uniquecrops.potions;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.bafomdad.uniquecrops.init.UCPotions;

public class PotionBehavior {

    private static final Map<MobEffect, MobEffect> REVERSE_MAP = new IdentityHashMap<>();

    static {
        REVERSE_MAP.put(MobEffects.NIGHT_VISION, MobEffects.BLINDNESS);
        REVERSE_MAP.put(MobEffects.MOVEMENT_SPEED, MobEffects.MOVEMENT_SLOWDOWN);
        REVERSE_MAP.put(MobEffects.REGENERATION, MobEffects.POISON);
        REVERSE_MAP.put(MobEffects.SATURATION, MobEffects.HUNGER);
        REVERSE_MAP.put(MobEffects.DIG_SPEED, MobEffects.DIG_SLOWDOWN);
        REVERSE_MAP.put(MobEffects.DAMAGE_BOOST, MobEffects.WEAKNESS);
        REVERSE_MAP.put(MobEffects.ABSORPTION, MobEffects.WITHER);
        REVERSE_MAP.put(MobEffects.LUCK, MobEffects.UNLUCK);
        REVERSE_MAP.put(MobEffects.JUMP, MobEffects.LEVITATION);
        REVERSE_MAP.put(MobEffects.FIRE_RESISTANCE, MobEffects.WATER_BREATHING);
        REVERSE_MAP.put(MobEffects.INVISIBILITY, MobEffects.GLOWING);
        REVERSE_MAP.put(MobEffects.HERO_OF_THE_VILLAGE, MobEffects.BAD_OMEN);
    }

    public static void reverseEffects(Player player) {

    	player.removeEffect(UCPotions.REVERSE.get());
        List<MobEffectInstance> activeEffects = new ArrayList<>(player.getActiveEffects());
        if (!activeEffects.isEmpty()) {
            for (MobEffectInstance eff : activeEffects) {
            	setReverseEffects(eff, player);
            }
        }
    }

    private static void setReverseEffects(MobEffectInstance eff, Player player) {

        REVERSE_MAP.forEach((key, value) -> {
            if (key == eff.getEffect()) {
                player.addEffect(new MobEffectInstance(value, eff.getDuration(), eff.getAmplifier()));
                player.removeEffect(eff.getEffect());
            } else if (value == eff.getEffect()) {
            	player.addEffect(new MobEffectInstance(key, eff.getDuration(), eff.getAmplifier()));
                player.removeEffect(eff.getEffect());
            }
        });
    }
}
