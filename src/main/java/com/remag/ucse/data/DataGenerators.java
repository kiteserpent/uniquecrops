package com.remag.ucse.data;

import com.remag.ucse.data.recipes.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;


public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {
            // generator.addProvider(true, new UCLootProvider(generator));
            UCBlockTagsProvider blockTagGenerator = generator.addProvider(event.includeServer(),
                    new UCBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
            generator.addProvider(event.includeServer(), new UCItemTagsProvider(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));
            generator.addProvider(true, new UCAdvancementProvider(generator, lookupProvider, existingFileHelper));
            generator.addProvider(true, new UCRecipeProvider(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(true, new UCBlockStateProvider(generator, existingFileHelper));
        }
    }
}
