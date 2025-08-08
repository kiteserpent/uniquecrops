package com.remag.ucse.crafting;

import com.remag.ucse.api.IHeaterRecipe;
import com.remag.ucse.init.UCRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class RecipeHeater implements IHeaterRecipe {

    private final ResourceLocation id;
    private final ItemStack output, input;

    public RecipeHeater(ResourceLocation id, ItemStack output, ItemStack input) {

        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override
    public boolean matches(Container inv, Level world) {

        return ItemStack.isSameItem(inv.getItem(0), input);
    }

    @Override
    public @NotNull ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return this.getResultItem(p_267165_);
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess p_267052_) {
        return this.output;
    }

    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ItemStack getInput() {

        return this.input;
    }

    @Override
    public @NotNull ResourceLocation getId() {

        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {

        return UCRecipes.HEATER_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<IHeaterRecipe> {

        @Override
        public @NotNull IHeaterRecipe fromJson(ResourceLocation id, JsonObject json) {

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "input"));

            return new RecipeHeater(id, output, input);
        }

        @Nullable
        @Override
        public IHeaterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            ItemStack output = buf.readItem();
            ItemStack input = buf.readItem();

            return new RecipeHeater(id, output, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IHeaterRecipe recipe) {
            RegistryAccess registryAccess = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
            buf.writeItem(recipe.getResultItem(registryAccess));
            buf.writeItem(recipe.getInput());
        }
    }
}
