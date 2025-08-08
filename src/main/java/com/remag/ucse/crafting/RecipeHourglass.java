package com.remag.ucse.crafting;

import com.remag.ucse.api.IHourglassRecipe;
import com.remag.ucse.core.JsonUtils;
import com.remag.ucse.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class RecipeHourglass implements IHourglassRecipe {

    private final ResourceLocation id;
    private final BlockState input;
    private final BlockState output;

    public RecipeHourglass(ResourceLocation id, BlockState input, BlockState output) {

        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(BlockState state) {

        return input.equals(state);
    }

    @Override
    public BlockState getInput() {

        return input;
    }

    @Override
    public BlockState getOutput() {

        return output;
    }

    @Override
    public @NotNull ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return getResultItem(p_267165_).copy();
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess p_267052_) {
        return new ItemStack(output.getBlock().asItem());
    }

    public ItemStack getResultItem() {
        return new ItemStack(output.getBlock().asItem());
    }

    @Override
    public @NotNull ResourceLocation getId() {

        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {

        return UCRecipes.HOURGLASS_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<RecipeHourglass> {

        @Override
        public @NotNull RecipeHourglass fromJson(ResourceLocation id, JsonObject obj) {
            HolderGetter<Block> blockGetter = BuiltInRegistries.BLOCK.asLookup();

            BlockState input = JsonUtils.readBlockState(GsonHelper.getAsJsonObject(obj, "input"), blockGetter);
            BlockState output = JsonUtils.readBlockState(GsonHelper.getAsJsonObject(obj, "output"), blockGetter);

            return new RecipeHourglass(id, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buff, RecipeHourglass recipe) {

            buff.writeVarInt(Block.getId(recipe.input));
            buff.writeVarInt(Block.getId(recipe.output));
        }

        @Nullable
        @Override
        public RecipeHourglass fromNetwork(ResourceLocation id, FriendlyByteBuf buff) {

            BlockState input = Block.stateById(buff.readVarInt());
            BlockState output = Block.stateById(buff.readVarInt());
            return new RecipeHourglass(id, input, output);
        }
    }
}
