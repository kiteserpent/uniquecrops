package com.remag.ucse.integration.jei;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.crafting.RecipeArtisia;
import com.remag.ucse.crafting.RecipeHeater;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class UCArtisiaCategory implements IRecipeCategory<RecipeArtisia> {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "artisia");
    private final IDrawable background;

    public UCArtisiaCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/craftyplant.png"), 0, 0, 126, 64);
    }

    @Override
    public RecipeType<RecipeArtisia> getRecipeType() {
        return JEIRecipeTypesUC.ARTISIA;
    }

    @Override
    public Component getTitle() {

        return Component.translatable("container.jei.ucse.craftyplant");
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(RecipeArtisia recipe) {
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeArtisia recipe, IFocusGroup ingredients) {

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = 1 + x + (y * 3);
                builder.addSlot(RecipeIngredientRole.INPUT, (x * 18) + 6, (y * 18) + 6)
                        .addIngredients(recipe.getIngredients().get(index - 1));
            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 100, 24)
                .addItemStack(recipe.getResultItem());
    }
}
