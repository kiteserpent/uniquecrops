package com.remag.ucse.crafting;

import com.remag.ucse.api.IArtisiaRecipe;
import com.remag.ucse.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RecipeArtisia implements IArtisiaRecipe {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;

    public RecipeArtisia(ResourceLocation id, ItemStack output, Ingredient... inputs) {

        this.id = id;
        this.output = output;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        if (inputs.length > 9)
            throw new IllegalStateException("Inputs cannot be more than 9");
    }

    @Override
    public boolean matches(Container inv, Level world) {

        List<Ingredient> ingredientsMissing = new ArrayList<>(inputs);

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack input = inv.getItem(i);
            if (input.isEmpty()) {
                break;
            }
            int stackIndex = -1;
            for (int j = 0; j < ingredientsMissing.size(); j++) {
                Ingredient ingr = ingredientsMissing.get(j);
                if (ingr.test(input)) {
                    stackIndex = j;
                    break;
                }
            }
            if (stackIndex != -1) {
                ingredientsMissing.remove(stackIndex);
            } else {
                return false;
            }
        }
        return ingredientsMissing.isEmpty();
    }

    @Override
    public @NotNull ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return getResultItem(p_267165_).copy();
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess p_267052_) {
        return output.copy();
    }

    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {

        return this.inputs;
    }

    @Override
    public @NotNull ResourceLocation getId() {

        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {

        return UCRecipes.ARTISIA_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<RecipeArtisia> {

        @Override
        public @NotNull RecipeArtisia fromJson(ResourceLocation id, JsonObject json) {

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingredients)
                inputs.add(Ingredient.fromJson(e));

            return new RecipeArtisia(id, output, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public RecipeArtisia fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            Ingredient[] inputs = new Ingredient[buf.readVarInt()];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = Ingredient.fromNetwork(buf);

            ItemStack output = buf.readItem();
            return new RecipeArtisia(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, RecipeArtisia recipe) {

            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients())
                input.toNetwork(buf);

            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
