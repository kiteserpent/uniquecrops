package com.remag.ucse.integration.patchouli.component;

import com.remag.ucse.core.UCStrings;
import com.remag.ucse.core.enums.EnumGrowthSteps;
import com.remag.ucse.init.UCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.function.UnaryOperator;

public class GrowthComponent implements ICustomComponent {

    private transient int x, y;

    @Override
    public void build(int cx, int cy, int pageNum) {

        this.x = cx;
        this.y = cy;
    }

    @Override
    public void render(GuiGraphics guiGraphics, IComponentRenderContext ctx, float pticks, int mouseX, int mouseY) {
        Minecraft mc = ctx.getGui().getMinecraft();
        ItemStack book = mc.player.getMainHandItem();

        if (book.getItem() == UCItems.BOOK_GUIDE.get()) {
            if (!book.hasTag() || !book.getTag().contains(UCStrings.TAG_GROWTHSTAGES)) {
                renderBlank(guiGraphics, mc.font);
                return;
            }

            guiGraphics.drawString(mc.font, Component.literal("Feroxia Growth Steps"), x, y, 0, false);
            ListTag tagList = book.getTag().getList(UCStrings.TAG_GROWTHSTAGES, 10);

            for (int i = 0; i < tagList.size(); i++) {
                CompoundTag tag = tagList.getCompound(i);
                int stage = tag.getInt("stage" + i);
                String desc = EnumGrowthSteps.values()[stage].getDescription();
                Component line = Component.literal("Stage " + (i + 1) + ": ").append(Component.translatable(desc));
                guiGraphics.drawString(mc.font, line, x, 20 + y + (i * 15), 0, false);
            }
        }
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> lookup) {

    }

    private void renderBlank(GuiGraphics guiGraphics, Font font) {
        guiGraphics.drawString(font, Component.literal("Feroxia Growth Steps"), x, y, 0, false);
        guiGraphics.drawString(font, Component.literal("Here be crops!"), x, y + 20, 0, false);
    }
}
