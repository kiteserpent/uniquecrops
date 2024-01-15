package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class Abstract extends BaseCropsBlock {

    public Abstract() {

        super(UCItems.ABSTRACT, UCItems.ABSTRACT_SEED);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        if (!world.isClientSide) {
            world.removeBlock(pos, true);
            world.levelEvent(2001, pos, Block.getId(state));
            UCUtils.setAbstractCropGrowth(placer, world.random.nextInt(2) + 1);
        }
    }
}
