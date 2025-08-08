package com.remag.ucse.integration.jei;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.crafting.RecipeArtisia;
import com.remag.ucse.crafting.RecipeEnchanter;
import com.remag.ucse.crafting.RecipeHeater;
import com.remag.ucse.crafting.RecipeHourglass;
import mezz.jei.api.recipe.RecipeType;

public class JEIRecipeTypesUC {
    public static final RecipeType<RecipeArtisia> ARTISIA = RecipeType.create(UniqueCrops.MOD_ID, "artisia", RecipeArtisia.class);
    public static final RecipeType<RecipeHourglass> HOURGLASS = RecipeType.create(UniqueCrops.MOD_ID, "hourglass", RecipeHourglass.class);
    public static final RecipeType<RecipeHeater> HEATER = RecipeType.create(UniqueCrops.MOD_ID, "heater", RecipeHeater.class);
    public static final RecipeType<RecipeEnchanter> ENCHANTER = RecipeType.create(UniqueCrops.MOD_ID, "enchanter", RecipeEnchanter.class);
}
