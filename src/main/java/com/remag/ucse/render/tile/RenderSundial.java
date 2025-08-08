package com.remag.ucse.render.tile;

import com.mojang.math.Axis;
import com.remag.ucse.UniqueCrops;
import com.remag.ucse.blocks.tiles.TileSundial;
import com.remag.ucse.render.model.ModelSundial;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderSundial implements BlockEntityRenderer<TileSundial> {

    final ModelSundial model;
    final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/block/sundial.png");
    private final BlockRenderDispatcher renderDispatcher;

    public RenderSundial(BlockEntityRendererProvider.Context ctx) {

        this.model = new ModelSundial(ctx.bakeLayer(ModelSundial.LAYER_LOCATION));
        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(TileSundial te, float partialTicks, PoseStack ms, MultiBufferSource buff, int light, int overlay) {

        ms.pushPose();
        ms.translate(0.5F, 0.1F, 0.5F);
        ms.mulPose(Axis.XP.rotationDegrees(180.0F));
        RenderSystem.setShaderTexture(0, RES);

        model.DialRedstone.visible = te.savedTime > 0;
        model.DialRedstone.yRot = te.savedRotation;
        model.Dial.yRot = te.rotation;

        VertexConsumer buffer = buff.getBuffer(model.renderType(RES));
        model.renderToBuffer(ms, buffer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        ms.popPose();
    }
}
