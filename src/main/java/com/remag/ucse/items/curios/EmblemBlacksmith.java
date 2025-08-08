package com.remag.ucse.items.curios;

import com.remag.ucse.items.base.ItemCurioUC;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

public class EmblemBlacksmith extends ItemCurioUC {

    public EmblemBlacksmith() {

        MinecraftForge.EVENT_BUS.addListener(this::blacksmithAnvil);
    }

    private void blacksmithAnvil(AnvilRepairEvent event) {

        if (event.getEntity() == null) return;

        if (hasCurio(event.getEntity())) event.setBreakChance(0.0F);
    }
}
