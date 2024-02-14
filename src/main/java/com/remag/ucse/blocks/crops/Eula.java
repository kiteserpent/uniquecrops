package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.network.PacketOpenBook;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.context.BlockPlaceContext;

public class Eula extends BaseCropsBlock {

    private boolean clickHarvest = true;

    public Eula() {

        super(UCItems.LEGALSTUFF, UCItems.EULA_SEED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        if (!ctx.getLevel().isClientSide) {
            if (ctx.getPlayer() instanceof ServerPlayer)
                UCPacketHandler.sendTo((ServerPlayer)ctx.getPlayer(), new PacketOpenBook(ctx.getPlayer().getId()));
        }
        return super.getStateForPlacement(ctx);
    }

    public boolean isClickHarvest() {

        return this.clickHarvest;
    }
}
