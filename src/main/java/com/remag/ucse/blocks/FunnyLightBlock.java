package com.remag.ucse.blocks;

import com.remag.ucse.core.enums.EnumParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FunnyLightBlock extends Block {

    protected static final VoxelShape SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public FunnyLightBlock() {

        super(Properties.copy(Blocks.TORCH).lightLevel((func) -> 15));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {

        return RenderShape.INVISIBLE;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {

        double d0 = (float)pos.getX() + 0.5F + (rand.nextFloat() - rand.nextFloat()) / 4;
        double d1 = (float)pos.getY() + 0.5F;
        double d2 = (float)pos.getZ() + 0.5F + (rand.nextFloat() - rand.nextFloat()) / 4;

        world.addParticle(EnumParticle.FLAME.getType(), d0, d1, d2, 0, 0, 0);
    }
}
