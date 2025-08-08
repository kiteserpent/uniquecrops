package com.remag.ucse.crafting;

import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.init.UCRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/* public class RecipeDiscountBook extends CustomRecipe {

    public RecipeDiscountBook(ResourceLocation id) {

        super(id, CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingContainer ic, Level world) {

        boolean foundDiscountBook = false;
        boolean foundItem = false;

        for (int i = 0; i < ic.getContainerSize(); i++) {
            ItemStack stack = ic.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == UCItems.BOOK_DISCOUNT.get())
                    foundDiscountBook = true;
                else if (!foundItem && !(NBTUtils.detectNBT(stack) && NBTUtils.getBoolean(stack, UCStrings.TAG_DISCOUNT, false)) && !stack.getItem().hasCraftingRemainingItem(stack) && isStackValid(stack))
                    foundItem = true;
                else return false;
            }
        }
        return foundDiscountBook && foundItem;
    }

    @Override
    public ItemStack assemble(CraftingContainer ic, RegistryAccess registryAccess) {

        ItemStack item = ItemStack.EMPTY;

        for (int i = 0; i < ic.getContainerSize(); i++) {
            ItemStack stack = ic.getItem(i);
            if (!stack.isEmpty() && stack.getItem() != UCItems.BOOK_DISCOUNT.get())
                item = stack;
        }
        ItemStack copy = item.copy();
        NBTUtils.setBoolean(copy, UCStrings.TAG_DISCOUNT, true);
        copy.setCount(1);
        return copy;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {

        return width > 1 || height > 1;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return UCRecipes.DISCOUNTBOOK_SERIALIZER.get();
    }

    private boolean isStackValid(ItemStack stack) {

        return stack.getItem().canBeDepleted() && !NBTUtils.getBoolean(stack, UCStrings.TAG_DISCOUNT, false);
    }
} */
