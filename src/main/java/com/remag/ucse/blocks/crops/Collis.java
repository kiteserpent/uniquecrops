package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class Collis extends BaseCropsBlock {

    public Collis() {

        super(UCItems.GOLDENRODS, UCItems.COLLIS_SEED);
        setIgnoreGrowthRestrictions(true);
        setIncludeSeed(false);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {

        if (this.canIgnoreGrowthRestrictions(world, pos)) {
            super.randomTick(state, world, pos, rand);
            return;
        }
        checkHighplant(world, pos, state);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {

        checkHighplant(world, pos, state);
    }

    private void checkHighplant(Level world, BlockPos pos, BlockState state) {

        int chanceByHeight = Math.round(pos.getY() / 16);

        if (world.getBrightness(LightLayer.SKY, pos.above()) >= 9) {
            int growthChance = world.random.nextInt(16 - chanceByHeight);
            if (!isMaxAge(state) && growthChance == 0) {
                world.setBlock(pos, this.setValueAge(getAge(state) + 1), 2);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        if (ctx.getClickedPos().getY() < 100) return Blocks.AIR.defaultBlockState();
        return super.getStateForPlacement(ctx);
    }
}
