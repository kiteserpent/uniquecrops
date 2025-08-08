package com.remag.ucse.data;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static com.remag.ucse.init.UCBlocks.*;

public class UCBlockTagsProvider extends BlockTagsProvider {

    public static final Predicate<Block> UC_BLOCKS = b -> UniqueCrops.MOD_ID.equals(ForgeRegistries.BLOCKS.getKey(b).getNamespace());

    public UCBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, UniqueCrops.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {

        tag(BlockTags.STAIRS).add(UCBlocks.RUINEDBRICKS_STAIRS.get());
        tag(BlockTags.SLABS).add(UCBlocks.RUINEDBRICKS_SLAB.get(), UCBlocks.RUINEDBRICKSCARVED_SLAB.get());
        tag(BlockTags.LOGS).add(UCBlocks.FLYWOOD_LOG.get());
        tag(BlockTags.PLANKS).add(UCBlocks.FLYWOOD_PLANKS.get(), UCBlocks.ROSEWOOD_PLANKS.get());
        tag(BlockTags.WOODEN_STAIRS).add(UCBlocks.FLYWOOD_STAIRS.get(), UCBlocks.ROSEWOOD_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(UCBlocks.FLYWOOD_SLAB.get(), UCBlocks.ROSEWOOD_SLAB.get());
        tag(Tags.Blocks.STORAGE_BLOCKS_IRON).add(UCBlocks.OLDIRON.get());
        tag(Tags.Blocks.STORAGE_BLOCKS_DIAMOND).add(UCBlocks.OLDDIAMOND.get());
        tag(Tags.Blocks.STORAGE_BLOCKS_GOLD).add(UCBlocks.OLDGOLD.get());
        tag(Tags.Blocks.GRAVEL).add(UCBlocks.OLDGRAVEL.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(UCBlocks.FLYWOOD_TRAPDOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(UCBlocks.ROSEWOOD_TRAPDOOR.get());

        registerBlockMineable();
    }

    private void registerBlockMineable() {

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(getModBlocks(b -> ForgeRegistries.BLOCKS.getKey(b).getPath().contains("ruined")));
        var pickaxe = Set.of(
                BUCKET_ROPE.get(), CINDER_TORCH.get(), GOBLET.get(), HOURGLASS.get(),
                OLDCOBBLE.get(), OLDGOLD.get(), OLDDIAMOND.get(), OLDIRON.get(),
                OLDBRICK.get(), OLDCOBBLEMOSS.get(), PRECISION_BLOCK.get(), SUN_DIAL.get(),
                SUN_BLOCK.get(), INVISIBILIA_GLASS.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(getModBlocks(pickaxe::contains));
        var axe = Set.of(
                ABSTRACT_BARREL.get(), EGG_BASKET.get(), NORMIECRATE.get(), OBTUSE_PLATFORM.get(),
                HARVEST_TRAP.get(), TOTEMHEAD.get(), FLYWOOD_LOG.get(), FLYWOOD_PLANKS.get(),
                FLYWOOD_STAIRS.get(), FLYWOOD_TRAPDOOR.get(), FLYWOOD_LEAVES.get(), ROSEWOOD_PLANKS.get(),
                ROSEWOOD_SLAB.get(), ROSEWOOD_STAIRS.get(), ROSEWOOD_TRAPDOOR.get()
        );
        tag(BlockTags.MINEABLE_WITH_AXE).add(getModBlocks(axe::contains));
        var shovel = Set.of(
                OLDGRAVEL.get()
        );
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(getModBlocks(shovel::contains));
    }

    @Override
    public String getName() {

        return "Unique Crops block tags";
    }

    private Block[] getModBlocks(Predicate<Block> predicate) {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(UC_BLOCKS.and(predicate))
                .filter(block -> ForgeRegistries.BLOCKS.getKey(block) != null) // defensive check
                .sorted(Comparator.comparing(block -> Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block))))
                .toArray(Block[]::new);
    }
}
