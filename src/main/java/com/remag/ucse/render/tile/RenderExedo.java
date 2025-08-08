package com.remag.ucse.render.tile;

import com.mojang.math.Axis;
import com.remag.ucse.UniqueCrops;
import com.remag.ucse.blocks.tiles.TileExedo;
import com.remag.ucse.render.model.ModelExedo;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderExedo implements BlockEntityRenderer<TileExedo> {

    final ModelExedo model;
    static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/models/model_exedo.png");
    private final BlockRenderDispatcher renderDispatcher;

    public RenderExedo(BlockEntityRendererProvider.Context ctx) {

        model = new ModelExedo(ctx.bakeLayer(ModelExedo.LAYER_LOCATION));
        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(TileExedo te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        float f = 0.5F;
        ms.pushPose();
        ms.translate(0.5, 1.2, 0.5);
        ms.scale(f, f, f);
        ms.mulPose(Axis.XP.rotationDegrees(180.0F));
        model.renderWithWiggle(te, ms, buffer.getBuffer(model.renderType(TEX)), light, overlay);
        ms.popPose();
    }
}
