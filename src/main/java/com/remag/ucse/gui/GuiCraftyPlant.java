package com.remag.ucse.gui;

import com.remag.ucse.UniqueCrops;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class GuiCraftyPlant extends AbstractContainerScreen<ContainerCraftyPlant> {

    private static final ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/guicraftyplant.png");

    public GuiCraftyPlant(ContainerCraftyPlant container, Inventory inv, Component title) {

        super(container, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void renderLabels(GuiGraphics guiGraphics, int x, int y) {

        String s = "Crafty Plant";
        guiGraphics.drawString(this.font, s, this.imageWidth / 2 - font.width(s), 6, 4210752);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int x, int y) {

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RES);

        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RES, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}
