package com.remag.ucse.data;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.security.Provider;
import java.util.concurrent.CompletableFuture;

public class UCItemTagsProvider extends ItemTagsProvider {

    public static final TagKey<Item> STEEL_INGOT = forgeTag("ingots/steel");
    public static final TagKey<Item> NORMAL_DROP = cTag("normal_drops");

    public UCItemTagsProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                              CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, UniqueCrops.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {

        this.tag(STEEL_INGOT).add(UCItems.STEEL_DONUT.get());
        this.tag(NORMAL_DROP).add(Items.BEETROOT, Items.WHEAT, Items.CARROT, Items.POTATO);
        this.tag(ItemTags.PIGLIN_LOVED).add(UCItems.THUNDERPANTZ.get(), UCItems.GLASSES_PIXELS.get(), UCItems.GLASSES_3D.get());
    }

    @Override
    public String getName() {

        return "Unique Crops item tags";
    }

    private static TagKey<Item> forgeTag(String name) {

        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", name));
    }

    private static TagKey<Item> cTag(String name) {

        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
