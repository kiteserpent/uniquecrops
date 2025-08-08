package com.remag.ucse.integration.jei;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.crafting.RecipeEnchanter;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class UCEnchanterCategory implements IRecipeCategory<RecipeEnchanter> {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "enchanter");
    private final IDrawable background;

    public UCEnchanterCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/enchanter.png"), 0, 0, 100, 83);
    }

    @Override
    public RecipeType<RecipeEnchanter> getRecipeType() {
        return JEIRecipeTypesUC.ENCHANTER;
    }

    @Override
    public Component getTitle() {

        return Component.translatable("container.jei.ucse.enchanter");
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(RecipeEnchanter recipe) {
        return ID;
    }

    @Override
    public IDrawable getBackground() {

        return background;
    }

    @Override
    public IDrawable getIcon() {

        return null;
    }

    @Override
    public void draw(RecipeEnchanter recipe, IRecipeSlotsView view, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        final String text = I18n.get(recipe.getEnchantment().getDescriptionId()) + " " + recipe.getEnchantment().getMaxLevel();
        Minecraft minecraft = Minecraft.getInstance();
        int stringWidth = minecraft.font.width(text);

        // Draw the enchantment name and level
        guiGraphics.drawString(minecraft.font, text, 50 - stringWidth / 2, -20, 0x555555, false);

        // Draw the cost
        final String cost = "Cost: " + recipe.getCost();
        int costWidth = minecraft.font.width(cost);

        guiGraphics.drawString(minecraft.font, cost, 50 - costWidth / 2, 95, 0x555555, false
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeEnchanter recipe, IFocusGroup ingredients) {

        var inputs = recipe.getIngredients();
        if (inputs.size() > 1) {
            for (int i = 0; i < inputs.size(); ++i) {
                builder.addSlot(RecipeIngredientRole.INPUT, (i * 18) + 6, 64)
                        .addIngredients(inputs.get(i));
            }
        } else {
            for (int i = 0; i < inputs.get(0).getItems().length; ++i) {
                builder.addSlot(RecipeIngredientRole.INPUT, (i * 18) + 6, 64)
                        .addItemStack(inputs.get(0).getItems()[i]);
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 42, 8)
                .addItemStack(recipe.getResultItem());
    }
}
