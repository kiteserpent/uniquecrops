package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCBlocks;
import com.remag.ucse.init.UCItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class Instabilis extends BaseCropsBlock {

    public Instabilis() {

        super(() -> Items.AIR, UCItems.INSTABILIS_SEED);
        setClickHarvest(false);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {

        if (!canGrow(world, pos)) return;

        super.randomTick(state, world, pos, rand);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {

        if (!canGrow(world, pos)) return;

        super.performBonemeal(world, rand, pos, state);
    }

    private boolean canGrow(ServerLevel world, BlockPos pos) {

        for (Direction dir : Direction.Plane.HORIZONTAL)
            if (world.getBlockState(pos.relative(dir)).getBlock() != this) return false;

        return true;
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, BlockEntity tile, ItemStack stack) {

        if (stack.getItem() instanceof ShearsItem) {
            if (!world.isClientSide) {
                if (!player.isCreative())
                    stack.hurt(1, world.random, (ServerPlayer)player);
                if (isMaxAge(state))
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCBlocks.DEMO_CORD.get()));
                world.removeBlock(pos, false);
            }
            super.playerDestroy(world, player, pos, state, tile, stack);
            return;
        }
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos loopPos = pos.relative(dir);
            if (world.getBlockState(loopPos).getBlock() == this) {
                world.destroyBlock(loopPos, false);
            }
        }
        super.playerDestroy(world, player, pos, state, tile, stack);
    }
}
