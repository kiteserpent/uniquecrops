package com.remag.ucse.api;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCRecipes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface IHourglassRecipe extends Recipe<Container> {

    ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "hourglass");

    boolean matches(BlockState state);

    BlockState getInput();

    BlockState getOutput();

    @Override
    default @NotNull RecipeType<?> getType() {

        return UCRecipes.HOURGLASS_TYPE.get();
    }

    @Override
    default boolean matches(Container inv, Level worldIn) {

        return false;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {

        return false;
    }

    @Override
    default boolean isSpecial() {

        return true;
    }
}
