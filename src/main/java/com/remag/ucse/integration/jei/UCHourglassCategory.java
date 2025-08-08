package com.remag.ucse.integration.jei;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.crafting.RecipeHourglass;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class UCHourglassCategory implements IRecipeCategory<RecipeHourglass> {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "hourglass");
    private final IDrawable background;

    public UCHourglassCategory(IGuiHelper helper) {

        this.background = helper.createDrawable(ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "textures/gui/hourglass.png"), 0, 0, 126, 64);
    }

    @Override
    public RecipeType<RecipeHourglass> getRecipeType() {
        // Cast is safe as long as RecipeHourglass implements IHourglassRecipe
        return JEIRecipeTypesUC.HOURGLASS;
    }

    @Override
    public Component getTitle() {

        return Component.translatable("container.jei.ucse.hourglass");
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(RecipeHourglass recipe) {
        return ID;
    }

    @Override
    public IDrawable getBackground() {

        return this.background;
    }

    @Override
    public IDrawable getIcon() {

        return null;
    }

//    @Override
//    public void setIngredients(IHourglassRecipe recipe, IIngredients ingredients) {
//
//        BlockState inputState = recipe.getInput();
//        BlockState outputState = recipe.getOutput();
//
//        ingredients.setInput(VanillaTypes.ITEM, new ItemStack(inputState.getBlock()));
//        ingredients.setOutput(VanillaTypes.ITEM, new ItemStack(outputState.getBlock()));
//    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHourglass recipe, IFocusGroup ingredients) {

        builder.addSlot(RecipeIngredientRole.OUTPUT, 100, 24)
                .addItemStack(new ItemStack(recipe.getOutput().getBlock()));

        builder.addSlot(RecipeIngredientRole.INPUT, 11, 24)
                .addItemStack(new ItemStack(recipe.getInput().getBlock()));
//        IGuiItemStackGroup layout = recipeLayout.getItemStacks();
//
//        layout.init(0, false, 99, 23);
//        layout.init(1, false, 10, 23);
//
//        layout.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
//        layout.set(0, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
