package com.remag.ucse.items.curios;

import com.remag.ucse.items.base.ItemCurioUC;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class EmblemPacifism extends ItemCurioUC {

    public EmblemPacifism() {

        MinecraftForge.EVENT_BUS.addListener(this::noDamage);
    }

    private void noDamage(LivingAttackEvent event) {

        Player player = null;
        if (event.getEntity() instanceof Player) player = (Player)event.getEntity();
        if (event.getSource().getDirectEntity() instanceof Player) player = (Player)event.getSource().getDirectEntity();
        if (player == null) return;

        if (!hasCurio(player)) return;

        if (event.getEntity() instanceof Player && event.getSource().getDirectEntity() != null || event.getSource().getDirectEntity() == player)
            event.setCanceled(true);
    }
}
