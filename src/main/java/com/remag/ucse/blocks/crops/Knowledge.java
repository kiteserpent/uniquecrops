package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.init.UCItems;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class Knowledge extends BaseCropsBlock {

    private static final Pattern PAT = Pattern.compile("[aeiou]", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final String BOOKMARK = "UC:tagBookmark";

    public Knowledge() {

        super(UCItems.BOOK_DISCOUNT, UCItems.KNOWLEDGE_SEED);
        setBonemealable(false);
        setIgnoreGrowthRestrictions(true);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {

        if (this.isMaxAge(state) || world.isClientSide) return;

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }

        int growStages = consumeKnowledge(world, pos);
        if (growStages > 0)
            world.setBlock(pos, this.setValueAge(Math.min(getAge(state) + growStages, getMaxAge())), 2);
    }

    private int consumeKnowledge(Level world, BlockPos pos) {
        AtomicInteger result = new AtomicInteger();
        Iterable<BlockPos> getBox = BlockPos.betweenClosed(pos.offset(-4, -2, -4), pos.offset(4, 2, 4));

        for (BlockPos posit : getBox) {
            BlockState loopState = world.getBlockState(posit);
            if (loopState.getEnchantPowerBonus(world, posit) >= 1F) {
                BlockEntity be = world.getBlockEntity(posit.above());
                if (be != null) {
                    be.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN).ifPresent(cap -> {
                        for (int i = 0; i < cap.getSlots(); i++) {
                            ItemStack book = cap.getStackInSlot(i);
                            if (!book.isEmpty() && book.getItem() == Items.WRITTEN_BOOK) {
                                CompoundTag tag = book.getTag();
                                if (tag != null && WrittenBookItem.makeSureTagIsValid(tag)
                                        && !NBTUtils.getBoolean(book, BOOKMARK, false)
                                        && WrittenBookItem.getGeneration(book) == 0) {

                                    ListTag tagList = tag.getList("pages", Tag.TAG_STRING);
                                    for (int j = 0; j < tagList.size(); j++) {
                                        String str = tagList.getString(j);
                                        Component text;
                                        try {
                                            text = Component.Serializer.fromJson(str); // use strict parsing
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            text = Component.literal(str); // fallback
                                        }

                                        String newString = eatSomeVowels(text.getString()); // use getString(), not getContents()
                                        Component newComponent = Component.literal(newString);
                                        tagList.set(j, StringTag.valueOf(Component.Serializer.toJson(newComponent)));
                                        result.set(j + 1);
                                    }

                                    tag.put("pages", tagList);
                                    NBTUtils.setBoolean(book, BOOKMARK, true);
                                }
                            }
                        }
                    });
                }
            }
        }

        return result.get();
    }

    private String eatSomeVowels(String str) {

        StringBuilder sb = new StringBuilder(str);
        if (str.length() >= 100 && str.length() <= 512) {
            sb.replace(0, str.length(), str.replaceAll(PAT.pattern(), " "));
            return sb.toString();
        }
        return str;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {

        if (isMaxAge(state)) {
            double x = pos.getX() + rand.nextFloat();
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + rand.nextFloat();
            worldIn.addParticle(ParticleTypes.ENCHANT, x, y, z, rand.nextGaussian(), rand.nextFloat(), rand.nextGaussian());
        }
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) {

        return isMaxAge(state) ? 3 : 0;
    }
}
