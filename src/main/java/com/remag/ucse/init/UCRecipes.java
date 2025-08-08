package com.remag.ucse.init;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.api.IArtisiaRecipe;
import com.remag.ucse.crafting.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class UCRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UniqueCrops.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, UniqueCrops.MOD_ID);

    // Recipe Serializers
    public static final RegistryObject<RecipeSerializer<?>> ARTISIA_SERIALIZER =
            registerSerializer("artisia", RecipeArtisia.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> HOURGLASS_SERIALIZER =
            registerSerializer("hourglass", RecipeHourglass.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> ENCHANTER_SERIALIZER =
            registerSerializer("enchanter", RecipeEnchanter.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> HEATER_SERIALIZER =
            registerSerializer("heater", RecipeHeater.Serializer::new);
    public static final RegistryObject<RecipeSerializer<?>> MULTIBLOCK_SERIALIZER =
            registerSerializer("multiblock", RecipeMultiblock.Serializer::new);
    // public static final RegistryObject<RecipeSerializer<?>> DISCOUNTBOOK_SERIALIZER = registerSerializer("discount_book", () -> new SimpleCraftingRecipeSerializer<>(RecipeDiscountBook::new));

    // Recipe Types
    public static final RegistryObject<RecipeType<RecipeArtisia>> ARTISIA_TYPE =
            registerType("artisia");
    public static final RegistryObject<RecipeType<RecipeHourglass>> HOURGLASS_TYPE =
            registerType("hourglass");
    public static final RegistryObject<RecipeType<RecipeEnchanter>> ENCHANTER_TYPE =
            registerType("enchanter");
    public static final RegistryObject<RecipeType<RecipeHeater>> HEATER_TYPE =
            registerType("heater");
    public static final RegistryObject<RecipeType<RecipeMultiblock>> MULTIBLOCK_TYPE =
            registerType("multiblock");

    public static void registerBrews() {
        ItemStack awkwardPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("awkward"));
        ItemStack invisibilityPotion = PotionUtils.setPotion(new ItemStack(Items.POTION), Potion.byName("invisibility"));
        if (!awkwardPotion.isEmpty()) {
            BrewingRecipeRegistry.addRecipe(Ingredient.of(awkwardPotion), Ingredient.of(UCItems.TIMEDUST.get()), new ItemStack(UCItems.POTION_REVERSE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(invisibilityPotion), Ingredient.of(UCBlocks.INVISIBILIA_GLASS.get()), new ItemStack(UCItems.POTION_IGNORANCE.get()));
            BrewingRecipeRegistry.addRecipe(Ingredient.of(awkwardPotion), Ingredient.of(UCItems.ZOMBIE_SLURRY.get()), new ItemStack(UCItems.POTION_ZOMBIFICATION.get()));
        }
    }

    private static <R extends RecipeSerializer<?>> RegistryObject<R> registerSerializer(String name, Supplier<? extends R> supplier) {
        return RECIPE_SERIALIZERS.register(name, supplier);
    }

    public static <T extends Recipe<?>> RegistryObject<RecipeType<T>> registerType(String name) {
        return RECIPE_TYPES.register(name, () -> new RecipeType<T>() {
            @Override
            public String toString() {
                return UniqueCrops.MOD_ID + ":" + name;
            }
        });
    }
}
