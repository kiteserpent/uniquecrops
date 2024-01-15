package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.enums.EnumParticle;
import com.remag.ucse.entities.BattleCropEntity;
import com.remag.ucse.init.UCEntities;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.network.PacketUCEffect;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class DonutSteel extends BaseCropsBlock {

    public DonutSteel() {

        super(UCItems.STEEL_DONUT, UCItems.DONUTSTEEL_SEED);
        setBonemealable(false);
        setClickHarvest(false);
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, BlockEntity tile, ItemStack stack) {

        if (player != null && isMaxAge(state)) {
            BattleCropEntity ent = UCEntities.BATTLE_CROP.get().create(world);
            ent.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            if (!world.isClientSide) {
                world.addFreshEntity(ent);
                UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY() + 0.3D, pos.getZ(), 6));
                world.removeBlock(pos, false);
            }
        }
        super.playerDestroy(world, player, pos, state, tile, stack);
    }
}
