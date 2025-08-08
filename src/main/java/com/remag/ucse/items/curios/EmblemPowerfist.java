package com.remag.ucse.items.curios;

import com.remag.ucse.items.base.ItemCurioUC;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EmblemPowerfist extends ItemCurioUC {

    public EmblemPowerfist() {

        MinecraftForge.EVENT_BUS.addListener(this::fistingSpeed);
    }

    private void fistingSpeed(PlayerEvent.BreakSpeed event) {

        if (hasCurio(event.getEntity())) {
            ItemStack miningHand = event.getEntity().getMainHandItem();
            if (!miningHand.isEmpty()) return;

            if (event.getNewSpeed() < 8.0F)
                event.setNewSpeed(8.0F);
        }
    }
}
