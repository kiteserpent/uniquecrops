package com.remag.ucse.gui;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.core.UCUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import org.jline.reader.Widget;

public class GuiEulaBook extends Screen {

    public static ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/bookeula.png");
    public final int WIDTH = 175;
    public final int HEIGHT = 228;
    public final int WORDWRAP = WIDTH - 40;

    private Button next, prev, agree, disagree;

    private int pageIndex = 0;

    public GuiEulaBook() {

        super(Component.empty());
    }

    @Override
    public void init() {

        super.init();
        int k = (this.width - this.WIDTH) / 2;
        int l = (this.height - this.HEIGHT) / 2;
        this.renderables.add(this.next = new GuiButtonPageChange(k + WIDTH - 26, l + 210, false, (button) -> {
            this.pageIndex++;
        }));
        this.renderables.add(this.prev = new GuiButtonPageChange(k + 10, l + 210, true, (button) -> {
            --this.pageIndex;
        }));
        this.renderables.add(this.agree = new GuiButtonEula(k + 65, l + 210, true, (button) -> {
            minecraft.setScreen(null);
        }));
        this.renderables.add(this.disagree = new GuiButtonEula(k + 95, l + 210, false, (button) -> {
            this.pageIndex = 0;
        }));
        updateButtons();
    }

    private void updateButtons() {

        this.next.visible = (this.pageIndex < 7);
        this.prev.visible = (this.pageIndex > 0);
        this.agree.visible = this.pageIndex == 7;
        this.disagree.visible = this.pageIndex == 7;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

        this.renderBackground(guiGraphics);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RES);

        int k = (this.width - this.WIDTH) / 2;
        int b0 = (this.height - this.HEIGHT) / 2;
        guiGraphics.blit(RES, k, b0, 0, 0, WIDTH, HEIGHT);

        Component text = Component.translatable(UniqueCrops.MOD_ID + ".eula.text" + pageIndex);
        UCUtils.drawSplitString(guiGraphics, font, text, k + 25, b0 + 15, WORDWRAP, ChatFormatting.GRAY.getColor());

        // --- Manual button rendering ---
        for (Renderable renderable : this.renderables) {
            if (!(renderable instanceof Button btn)) continue;
            if (!btn.visible) continue;

            int u = 175, v = 0;

            boolean hovered = mouseX >= btn.getX() && mouseY >= btn.getY() &&
                    mouseX < btn.getX() + btn.getWidth() && mouseY < btn.getY() + btn.getHeight();

            if (btn instanceof GuiButtonPageChange pageBtn) {
                v = hovered ? 17 : 0;
                if (pageBtn.previous) u += 17;
            } else if (btn instanceof GuiButtonEula eulaBtn) {
                v = 34 + (hovered ? 17 : 0);
                if (eulaBtn.agree) u += 17;
            }

            guiGraphics.blit(RES, btn.getX(), btn.getY(), u, v, 16, 16);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if (button == 0) {
            for (Renderable wig : renderables) {
                if (wig instanceof Button) {
                    if (((Button)wig).visible && ((Button)wig).isHoveredOrFocused()) {
                        ((Button)wig).onClick(mouseX, mouseY);
                        updateButtons();
                        return true;
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {

        return false;
    }

    private static class GuiButtonEula extends Button {

        final boolean agree;

        public GuiButtonEula(int x, int y, boolean agree, OnPress pressedAction) {

            super(x, y, 16, 16, Component.empty(), pressedAction, Button.DEFAULT_NARRATION);
            this.agree = agree;
        }
    }

    private static class GuiButtonPageChange extends Button {

        final boolean previous;

        public GuiButtonPageChange(int x, int y, boolean previous, OnPress pressedAction) {

            super(x, y, 16, 16, Component.empty(), pressedAction, Button.DEFAULT_NARRATION);
            this.previous = previous;
        }
    }
}
