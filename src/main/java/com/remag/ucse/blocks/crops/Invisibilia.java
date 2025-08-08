package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCItems;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;

public class Invisibilia extends BaseCropsBlock {

    public Invisibilia() {

        super(UCItems.INVISITWINE, UCItems.INVISIBILIA_SEED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        if (!(ctx instanceof EntityCollisionContext))
            return super.getShape(state, world, pos, ctx);

        Entity entity = ((EntityCollisionContext)ctx).getEntity();
        if (!(entity instanceof Player player))
            return super.getShape(state, world, pos, ctx);

        if (!player.isCreative()) {
            if (player.getInventory().armor.get(3).getItem() != UCItems.GLASSES_3D.get())
                return Shapes.empty();
        }
        return super.getShape(state, world, pos, ctx);
    }
}
