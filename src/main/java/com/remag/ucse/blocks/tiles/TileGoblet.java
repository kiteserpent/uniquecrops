package com.remag.ucse.blocks.tiles;

import com.remag.ucse.blocks.GobletBlock;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.init.UCBlocks;
import com.remag.ucse.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TileGoblet extends BaseTileUC {

    public UUID entityId;

    public TileGoblet(BlockPos pos, BlockState state) {

        super(UCTiles.GOBLET.get(), pos, state);
    }

    public void writeCustomNBT(CompoundTag tag) {

        if (entityId != null)
            tag.putString(UCStrings.TAG_LOCK, entityId.toString());
        else if (entityId == null && tag.contains(UCStrings.TAG_LOCK))
            tag.remove(UCStrings.TAG_LOCK);
    }

    public void readCustomNBT(CompoundTag tag) {

        if (tag.contains(UCStrings.TAG_LOCK))
            entityId = UUID.fromString(tag.getString(UCStrings.TAG_LOCK));
    }

    public void setTaglock(UUID uuid) {

        this.entityId = uuid;
    }

    public void eraseTaglock() {

        if (entityId != null) {
            entityId = null;
            level.setBlock(worldPosition, UCBlocks.GOBLET.get().defaultBlockState().setValue(GobletBlock.FILLED, false), 3);
        }
    }
}
