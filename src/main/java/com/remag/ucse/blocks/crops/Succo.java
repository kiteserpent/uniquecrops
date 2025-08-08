package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.blocks.tiles.TileSucco;
import com.remag.ucse.init.UCItems;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class Succo extends BaseCropsBlock implements EntityBlock {

    public Succo() {

        super(UCItems.VAMPIRIC_OINTMENT, UCItems.SUCCO_SEED);
        setIncludeSeed(false);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {

        if (world.dimensionType().moonPhase(world.dayTime()) == 0.0F)
            super.randomTick(state, world, pos, rand);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {

        if (world.dimensionType().moonPhase(world.dayTime()) == 0.0F)
            super.performBonemeal(world, rand, pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {

        return RenderShape.INVISIBLE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        if (world instanceof Level && ((Level)world).getMoonPhase() != 0.0F)
            return Shapes.empty();

        return super.getShape(state, world, pos, ctx);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileSucco(pos, state);
    }
}
