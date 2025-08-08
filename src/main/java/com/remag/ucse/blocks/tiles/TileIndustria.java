package com.remag.ucse.blocks.tiles;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.UCConfig;
import com.remag.ucse.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileIndustria extends BaseTileUC {

    final UCEnergyStorage energy = new UCEnergyStorage(40000, 200);

    public TileIndustria(BlockPos pos, BlockState state) {

        super(UCTiles.INDUSTRIA.get(), pos, state);
    }

    public void tickServer() {

        if (!level.canSeeSkyFromBelowWater(worldPosition)) return;

        if (level.isDay()) {
            if (!energy.canReceive()) return;

            energy.receiveEnergy(UCConfig.COMMON.energyPerTick.get(), false);
            int age = energy.getEnergyStored() / 5000;
            if (Math.min(age, 7) != getBlockState().getValue(BaseCropsBlock.AGE))
                level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BaseCropsBlock.AGE, Math.min(age, 7)));
        }
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putInt("UC:energy", this.energy.getEnergyStored());
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        energy.setEnergy(tag.getInt("UC:energy"));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        return cap == ForgeCapabilities.ENERGY ? LazyOptional.of(() -> energy).cast() : LazyOptional.empty();
    }

    public static class UCEnergyStorage extends EnergyStorage {

        public UCEnergyStorage(int capacity, int maxTransfer) {

            super(capacity, maxTransfer);
        }

        public void setEnergy(int energy) {

            this.energy = energy;
        }
    }
}
