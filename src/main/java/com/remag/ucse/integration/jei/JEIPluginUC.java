package com.remag.ucse.integration.jei;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.crafting.RecipeArtisia;
import com.remag.ucse.crafting.RecipeEnchanter;
import com.remag.ucse.crafting.RecipeHeater;
import com.remag.ucse.crafting.RecipeHourglass;
import com.remag.ucse.init.UCBlocks;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.init.UCRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIPluginUC implements IModPlugin {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "main");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        registry.addRecipeCategories(
                new UCArtisiaCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCHourglassCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCHeaterCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCEnchanterCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        Minecraft mc = Minecraft.getInstance();
        RecipeManager recipeManager = mc.level.getRecipeManager();

        List<RecipeArtisia> artisiaRecipes = recipeManager
                .getAllRecipesFor(UCRecipes.ARTISIA_TYPE.get());
        List<RecipeHourglass> hourglassRecipes = recipeManager
                .getAllRecipesFor(UCRecipes.HOURGLASS_TYPE.get());
        List<RecipeHeater> heaterRecipes = recipeManager
                .getAllRecipesFor(UCRecipes.HEATER_TYPE.get());
        List<RecipeEnchanter> enchanterRecipes = recipeManager
                .getAllRecipesFor(UCRecipes.ENCHANTER_TYPE.get());

        registry.addRecipes(JEIRecipeTypesUC.ARTISIA, artisiaRecipes);
        registry.addRecipes(JEIRecipeTypesUC.HOURGLASS, hourglassRecipes);
        registry.addRecipes(JEIRecipeTypesUC.HEATER, heaterRecipes);
        registry.addRecipes(JEIRecipeTypesUC.ENCHANTER, enchanterRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {

        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_ARTISIA.get()), JEIRecipeTypesUC.ARTISIA);
        registry.addRecipeCatalyst(new ItemStack(UCBlocks.HOURGLASS.get()), JEIRecipeTypesUC.HOURGLASS);
        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_HEATER.get()), JEIRecipeTypesUC.HEATER);
        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_FASCINO.get()), JEIRecipeTypesUC.ENCHANTER);
    }

    @Override
    public ResourceLocation getPluginUid() {

        return ID;
    }
}
