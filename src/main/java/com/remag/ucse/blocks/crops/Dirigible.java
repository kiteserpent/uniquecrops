package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.enums.EnumParticle;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.network.PacketUCEffect;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;

public class Dirigible extends BaseCropsBlock {

    public Dirigible() {

        super(UCItems.DIRIGIBLEPLUM, UCItems.DIRIGIBLE_SEED);
        setBonemealable(false);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {

        super.randomTick(state, world, pos, rand);
        if ((this.getAge(state) + 1) >= getMaxAge()) {
            Containers.dropItemStack(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, new ItemStack(UCItems.DIRIGIBLEPLUM.get()));
            world.setBlock(pos, setValueAge(0), 2);
            UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY() + 0.1D, pos.getZ(), 4));
        }
    }
}
