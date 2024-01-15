package com.remag.ucse.data.recipes;

import com.remag.ucse.init.UCItems;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class SmeltingProvider extends RecipeProvider {

    public SmeltingProvider(DataGenerator gen) {

        super(gen);
    }

    @Override
    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(UCItems.UNCOOKEDWAFFLE.get()), UCItems.UNCOOKEDWAFFLE.get(), 0.35F, 200)
                .unlockedBy("hasItem", has(UCItems.UNCOOKEDWAFFLE.get()))
                .save(consumer, "ucse:smelting/waffles");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.MILK_BUCKET), UCItems.BOILED_MILK.get(), 0.1F, 200)
                .unlockedBy("hasItem", has(Items.MILK_BUCKET))
                .save(consumer, "ucse:smelting/boiled_mlk");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(UCItems.UNCOOKEDWAFFLE.get()), UCItems.UNCOOKEDWAFFLE.get(), 0.35F, 200, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("hasItem", has(UCItems.UNCOOKEDWAFFLE.get()))
                .save(consumer, "ucse:campfire/waffles");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(Items.MILK_BUCKET), UCItems.BOILED_MILK.get(), 0.1F, 200, RecipeSerializer.CAMPFIRE_COOKING_RECIPE)
                .unlockedBy("hasItem", has(Items.MILK_BUCKET))
                .save(consumer, "ucse:campfire/boiled_mlk");
    }

    @Override
    public String getName() {

        return "Unique Crops cooking recipes";
    }
}
