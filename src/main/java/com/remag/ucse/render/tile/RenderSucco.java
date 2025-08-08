package com.remag.ucse.render.tile;

import com.mojang.math.Axis;
import com.remag.ucse.UniqueCrops;
import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.blocks.tiles.TileSucco;
import com.remag.ucse.render.CustomRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.dimension.DimensionType;
import org.joml.Matrix4f;

import java.util.stream.IntStream;

public class RenderSucco implements BlockEntityRenderer<TileSucco> {

    private final BlockRenderDispatcher renderDispatcher;
    private final int[] textureSkipper = IntStream.of(1, 2, 2, 3, 3, 4, 4, 5).toArray();

    public RenderSucco(BlockEntityRendererProvider.Context ctx) {

        this.renderDispatcher = ctx.getBlockRenderDispatcher();
    }

    @Override
    public void render(TileSucco te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        int age = te.getBlockState().getValue(BaseCropsBlock.AGE);
        ResourceLocation res = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/block/vampire" + textureSkipper[age] + ".png");
        final VertexConsumer buff = buffer.getBuffer(CustomRenderType.CUSTOM_BEAM.apply(res, true));

        ms.pushPose();

        float r = 1.0F, g = r, b = g;
        float moon = DimensionType.MOON_BRIGHTNESS_PER_PHASE[Minecraft.getInstance().level.getMoonPhase()];
        Matrix4f mat = ms.last().pose();

        ms.mulPose(Axis.YP.rotationDegrees(45.0F));
        ms.translate(0, 0.45, 0.75);
        ms.mulPose(Axis.ZP.rotationDegrees(90.0F));
        this.quad(buff, mat, 0.5F, 0.5F, 0, moon);
        ms.mulPose(Axis.XP.rotationDegrees(180.0F));
        this.quad(buff, mat, 0.5F, 0.5F, 0, moon);

        ms.popPose();
    }

    private void quad(VertexConsumer buff, Matrix4f mat, float x, float y, float z, float a) {

        buff.vertex(mat, -x, -y, z).color(1.0F, 1.0F, 1.0F, a).uv(0, 1).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, -x, y, z).color(1.0F, 1.0F, 1.0F, a).uv(1, 1).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, y, z).color(1.0F, 1.0F, 1.0F, a).uv(1, 0).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, -y, z).color(1.0F, 1.0F, 1.0F, a).uv(0, 0).uv2(15728880).normal(1, 0, 0).endVertex();

        buff.vertex(mat, -x, -y + 0.5F, z + 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(0, 1).uv2(15728880).normal(1, 0, 0).endVertex(); // bottom z
        buff.vertex(mat, -x, y - 0.5F, z - 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(1, 1).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, y - 0.5F, z - 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(1, 0).uv2(15728880).normal(1, 0, 0).endVertex();
        buff.vertex(mat, x, -y + 0.5F, z + 0.5F).color(1.0F, 1.0F, 1.0F, a).uv(0, 0).uv2(15728880).normal(1, 0, 0).endVertex(); // top z
    }
}
