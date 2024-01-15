package com.remag.ucse.items.base;

import com.remag.ucse.render.entity.TEISR;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;
import java.util.function.Consumer;

public class ItemRenderUC extends ItemDummyUC {

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {

        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {

                return new TEISR();
            }
        });
    }
}
