package com.remag.ucse.crafting;

import com.remag.ucse.api.IEnchanterRecipe;
import com.remag.ucse.init.UCRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.Container;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RecipeEnchanter implements IEnchanterRecipe {

    private final ResourceLocation id;
    private final Enchantment ench;
    private final int cost;
    private final NonNullList<Ingredient> inputs;

    public RecipeEnchanter(ResourceLocation id, Enchantment ench, int cost, Ingredient... inputs) {

        this.id = id;
        this.ench = ench;
        this.cost = cost;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputs);
        if (ench == null)
            throw new IllegalStateException("Enchantment cannot be null");
        if (inputs.length == 0)
            throw new IllegalStateException("Inputs cannot be empty or null");
    }

    @Override
    public boolean matches(Container inv, Level world) {

        if (!inputs.isEmpty()) {
            Ingredient ingredient = inputs.get(0);
            int inputs = 0;
            for (int i = 0; i < inv.getContainerSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (stack.isEmpty()) break;
                if (ingredient.test(stack))
                    inputs++;
            }
            return inputs == ingredient.getItems().length;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(Container p_44001_, RegistryAccess p_267165_) {
        return getResultItem(p_267165_).copy();
    }

    @Override
    public boolean matchesEnchantment(String location) {
        return ForgeRegistries.ENCHANTMENTS.getKey(this.ench).toString().equals(location);
    }

    @Override
    public void applyEnchantment(ItemStack toApply) {

        toApply.enchant(this.ench, this.ench.getMaxLevel());
    }

    @Override
    public Enchantment getEnchantment() {

        return this.ench;
    }

    @Override
    public int getCost() {

        return this.cost;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(this.ench, this.ench.getMaxLevel()));

        return enchantedBook;
    }

    public ItemStack getResultItem() {
        ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(enchantedBook, new EnchantmentInstance(this.ench, this.ench.getMaxLevel()));

        return enchantedBook;
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

        return UCRecipes.ENCHANTER_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<IEnchanterRecipe> {

        @Override
        public @NotNull IEnchanterRecipe fromJson(ResourceLocation id, JsonObject json) {

            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(json.get("enchantment").getAsString()));
            int cost = json.get("cost").getAsInt();
            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : ingredients)
                inputs.add(Ingredient.fromJson(e));

            return new RecipeEnchanter(id, ench, cost, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public IEnchanterRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

            Enchantment ench = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(buf.readUtf()));
            int cost = buf.readVarInt();
            Ingredient[] inputs = new Ingredient[buf.readVarInt()];
            for (int i = 0; i < inputs.length; i++)
                inputs[i] = Ingredient.fromNetwork(buf);

            return new RecipeEnchanter(id, ench, cost, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, IEnchanterRecipe recipe) {

            buf.writeUtf(ForgeRegistries.ENCHANTMENTS.getKey(recipe.getEnchantment()).toString());
            buf.writeVarInt(recipe.getCost());
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients())
                input.toNetwork(buf);
        }
    }
}
