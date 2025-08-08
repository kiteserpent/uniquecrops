package com.remag.ucse.potions;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PotionEnnui extends MobEffect {

    public PotionEnnui() {

        super(MobEffectCategory.NEUTRAL, 0xeef442);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerClickItem);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJump);
    }

    private void onPlayerJump(LivingEvent.LivingJumpEvent event) {

        if (event.getEntity().getEffect(this) != null)
            event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().x, 0, event.getEntity().getDeltaMovement().z);
    }

    private void onPlayerClickBlock(PlayerInteractEvent.RightClickBlock event) {

        if (event.getEntity().getEffect(this) != null)
            event.setCanceled(true);
    }

    private void onPlayerClickItem(PlayerInteractEvent.RightClickItem event) {

        if (event.getEntity().getEffect(this) != null)
            event.setCanceled(true);
    }
}
