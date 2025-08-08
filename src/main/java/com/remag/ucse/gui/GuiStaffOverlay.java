package com.remag.ucse.gui;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.capabilities.CPProvider;
import com.remag.ucse.core.UCConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class GuiStaffOverlay {

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/leaf_bar.png");
    Minecraft mc;

    long lastSystemTime, staffUpdateCounter, updateCounter;
    int capacity;

    public GuiStaffOverlay(Minecraft mc) {

        this.mc = mc;
    }

    @SubscribeEvent
    public void renderOverlay(RenderGuiOverlayEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen != null) return; // skip if a screen is open

        if (!event.getOverlay().id().toString().equals("minecraft:all")) return;

        ProfilerFiller profiler = mc.getProfiler();
        profiler.push("UC-hud");

        PoseStack ms = guiGraphics.pose();
        ms.pushPose();

        Window scaled = mc.getWindow();

        mc.player.getMainHandItem().getCapability(CPProvider.CROP_POWER, null).ifPresent(crop -> {
            int i = crop.getPower();
            boolean flag = this.staffUpdateCounter > this.updateCounter;
            if (flag)
                RenderSystem.setShaderColor(0.0F, 1.0F, 0.0F, 0);

            float scale = (float) Math.sin(Util.getMillis() / 500D) * 0.01562F;

            if (i < this.capacity) {
                this.lastSystemTime = Util.getMillis();
                this.staffUpdateCounter = this.updateCounter + 200;
            }
            if (i > this.capacity) {
                this.lastSystemTime = Util.getMillis();
                this.staffUpdateCounter = this.updateCounter + 200;
            }
            if (Util.getMillis() - this.lastSystemTime > 1000L) {
                this.capacity = i;
                this.lastSystemTime = Util.getMillis();
            }

            ms.scale(1 + scale, 1 + scale, 1 + scale);

            this.renderLeafBar(scaled, crop.getPower(), crop.getCapacity(), guiGraphics);
            this.capacity = i;
        });

        ms.popPose();
        profiler.pop();
    }


    private void renderLeafBar(Window res, int currentPower, int capacity, GuiGraphics guiGraphics) {

        ResourceLocation ICONS = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/icons.png");

        if (capacity <= 0) return;

        int x = res.getGuiScaledWidth() / 2 + UCConfig.CLIENT.guiWidth.get();
        int y = res.getGuiScaledHeight() + UCConfig.CLIENT.guiHeight.get();

        RenderSystem.setShaderTexture(0, TEX);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int count = 5;
        if (capacity < count)
            count = capacity;
        double start = Math.PI / 2;
        int direction = -1;
        int radius = 15;
        double interval = 2 * Math.PI / count;
        int mod = capacity / count;

        for (int i = 0; i < count; ++i) {
            double angle = start + i * interval * (direction < 0 ? -1 : 1);
            double r1 = radius * Math.cos(angle);
            double r2 = radius * Math.sin(angle);
            guiGraphics.blit(ICONS, (int)r1 + x, (int)r2 + y, 0, 0, 15, 15, 256, 256);

            if (i < (currentPower / mod))
                guiGraphics.blit(ICONS, (int)r1 + x, (int)r2 + y, 15, 0, 15, 15, 256, 256);

            if ((i * mod) + (capacity / (count * 2)) <= currentPower)
                guiGraphics.blit(ICONS, (int)r1 + x, (int)r2 + y, 30, 0, 15, 15, 256, 256);

            RenderSystem.disableBlend();
        }
    }
}
