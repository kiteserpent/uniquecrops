package com.remag.ucse.data;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.data.advancements.UCAdvancementGenerator;
import com.remag.ucse.init.UCBlocks;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.remag.ucse.init.UCItems.*;

public class UCAdvancementProvider extends ForgeAdvancementProvider {

    public UCAdvancementProvider(DataGenerator gen, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, ExistingFileHelper existingFileHelper) {

        super(gen.getPackOutput(), providerCompletableFuture, existingFileHelper, List.of(new UCAdvancementGenerator()));
    }
}
