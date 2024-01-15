package com.remag.ucse.integration.jei;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCBlocks;
import com.remag.ucse.init.UCItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPluginUC implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(UniqueCrops.MOD_ID, "main");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {

        registry.addRecipeCategories(
                new UCArtisiaCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCHourglassCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCHeaterCategory(registry.getJeiHelpers().getGuiHelper()),
                new UCEnchanterCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {

        registry.addRecipes(UCUtils.loadType(UCItems.ARTISIA_TYPE), UCArtisiaCategory.UID);
        registry.addRecipes(UCUtils.loadType(UCItems.HOURGLASS_TYPE), UCHourglassCategory.UID);
        registry.addRecipes(UCUtils.loadType(UCItems.HEATER_TYPE), UCHeaterCategory.UID);
        registry.addRecipes(UCUtils.loadType(UCItems.ENCHANTER_TYPE), UCEnchanterCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {

        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_ARTISIA.get()), UCArtisiaCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(UCBlocks.HOURGLASS.get()), UCHourglassCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_HEATER.get()), UCHeaterCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(UCItems.DUMMY_FASCINO.get()), UCEnchanterCategory.UID);
    }

    @Override
    public ResourceLocation getPluginUid() {

        return ID;
    }
}
