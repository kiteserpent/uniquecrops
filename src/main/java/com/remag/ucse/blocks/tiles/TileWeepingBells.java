package com.remag.ucse.blocks.tiles;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class TileWeepingBells extends BaseTileUC {

    private boolean looking = false;
    private static final int RANGE = 10;

    public TileWeepingBells(BlockPos pos, BlockState state) {

        super(UCTiles.WEEPINGBELLS.get(), pos, state);
    }

    public void tickServer() {

        if (level.isClientSide || level.getGameTime() % 10L != 0) return;

        boolean wasLooking = this.isLooking();
        boolean looker = false;
        Holder<DamageType> voidDamage = level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD);

        DamageSource source = new DamageSource(voidDamage);

        List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(worldPosition.offset(-RANGE, -RANGE, -RANGE), worldPosition.offset(RANGE, RANGE, RANGE)));
        for (Player player : players) {
            ItemStack helm = player.getItemBySlot(EquipmentSlot.HEAD);
            if (helm.getItem().isEnderMask(helm, player, null)) continue;

            HitResult rtr = player.pick(RANGE, 0, false);
            if (rtr != null && rtr.getType() == HitResult.Type.BLOCK && ((BlockHitResult)rtr).getBlockPos().equals(this.getBlockPos())) {
                looker = true;
                break;
            }
            if (!wasLooking && !player.isCreative() && getBlockState().getValue(BaseCropsBlock.AGE) >= 7)
                player.hurt(source, 1.0F);
        }
        if (looker != wasLooking)
            setLooking(looker);
    }

    public boolean isLooking() {

        return this.looking;
    }

    private void setLooking(boolean flag) {

        this.looking = flag;
    }

    @Override
    public void writeCustomNBT(CompoundTag tag) {

        tag.putBoolean("UC:tagLooking", this.looking);
    }

    @Override
    public void readCustomNBT(CompoundTag tag) {

        this.looking = tag.getBoolean("UC:tagLooking");
    }
}
