package com.remag.ucse.api;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCRecipes;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface IHeaterRecipe extends Recipe<Container> {

    ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "heater");

    @Override
    default @NotNull RecipeType<?> getType() {

        return UCRecipes.HEATER_TYPE.get();
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {

        return false;
    }

    @Override
    default boolean isSpecial() {

        return true;
    }

    ItemStack getInput();
}
