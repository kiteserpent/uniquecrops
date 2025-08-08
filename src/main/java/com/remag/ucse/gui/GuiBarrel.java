package com.remag.ucse.gui;

import com.remag.ucse.UniqueCrops;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class GuiBarrel extends AbstractContainerScreen<ContainerBarrel> {

    private static final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/barrel.png");

    public GuiBarrel(ContainerBarrel container, Inventory inv, Component title) {

        super(container, inv, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int x, int y) {

        String s = "Abstract Barrel";
        guiGraphics.drawString(this.font, s, this.imageWidth / 2 - this.font.width(s) / 2, 6, 0x404040, false);
    }

    @Override
    public void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {

        RenderSystem.setShaderColor(1.0f, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RES);
        guiGraphics.blit(RES, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }
}
