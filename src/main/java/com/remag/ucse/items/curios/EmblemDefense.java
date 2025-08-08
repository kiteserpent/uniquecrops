package com.remag.ucse.items.curios;

import com.remag.ucse.items.base.ItemCurioUC;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EmblemDefense extends ItemCurioUC {

    public EmblemDefense() {

        MinecraftForge.EVENT_BUS.addListener(this::autoShield);
    }

    private void autoShield(LivingAttackEvent event) {

        if (!(event.getEntity() instanceof ServerPlayer)) return;
        if (!(event.getSource().getDirectEntity() instanceof Arrow)) return;
        if (!this.hasCurio(event.getEntity())) return;

        ItemStack shield = event.getEntity().getOffhandItem();
        if (!(shield.getItem() instanceof ShieldItem)) return;

        shield.hurt(1, event.getEntity().level().random, (ServerPlayer)event.getEntity());
        event.setCanceled(true);
    }
}
