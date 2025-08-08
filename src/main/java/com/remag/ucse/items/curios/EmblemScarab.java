package com.remag.ucse.items.curios;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemCurioUC;
import com.google.common.collect.Lists;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.InterModComms;

import java.util.List;

public class EmblemScarab extends ItemCurioUC {

    private static final List<String> BLACKLIST = Lists.newArrayList();

    public EmblemScarab() {

        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "minecraft.effect.awkward");
        InterModComms.sendTo(UniqueCrops.MOD_ID, UCStrings.BLACKLIST_EFFECT, () -> "effect.ucse.zombification" );
        MinecraftForge.EVENT_BUS.addListener(this::onApplyPotion);
    }

    private void onApplyPotion(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();

        if (entity instanceof Player player) {
            MobEffectInstance effectInstance = event.getEffectInstance();
            MobEffect effect = effectInstance.getEffect();

            // Block all effects if Curio is equipped and not in blacklist
            if (hasCurio(player)) {
                if (!BLACKLIST.contains(effect.getDescriptionId())) {
                    event.setResult(Event.Result.DENY);
                    return;
                }
            }

            // Special case for Hunger
            if (effect == MobEffects.HUNGER) {
                if (hasCurio(player, UCItems.EMBLEM_IRONSTOMACH.get())) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    public static void blacklistPotionEffect(String effect) {

        BLACKLIST.add(effect);
    }
}
