package com.remag.ucse.data;

import com.remag.ucse.data.advancements.UCAdvancementGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UCAdvancementProvider extends ForgeAdvancementProvider {

    public UCAdvancementProvider(DataGenerator gen, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, ExistingFileHelper existingFileHelper) {

        super(gen.getPackOutput(), providerCompletableFuture, existingFileHelper, List.of(new UCAdvancementGenerator()));
    }
}
