package com.remag.ucse.api;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.crafting.RecipeMultiblock;
import com.remag.ucse.init.UCRecipes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public interface IMultiblockRecipe extends Recipe<Container> {

    ResourceLocation RES = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "multiblock");

    boolean match(Level world, BlockPos originBlock);
    boolean isOriginBlock(BlockState state);

    ItemStack getCatalyst();
    int getPower();
    String[] getShape();
    Map<Character, RecipeMultiblock.Slot> getDefinition();

    void setResult(Level world, BlockPos originBlock);

    @Override
    default @NotNull RecipeType<?> getType() {

        return UCRecipes.MULTIBLOCK_TYPE.get();
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
