package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.UCProtectionHandler;
import com.remag.ucse.core.enums.EnumParticle;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.network.PacketUCEffect;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;

public class Imperia extends BaseCropsBlock {

    public Imperia() {

        super(() -> Items.AIR, UCItems.IMPERIA_SEED, Properties.copy(Blocks.WHEAT).lightLevel(s -> s.getValue(AGE) >= 7 ? 15 : 0));
        setClickHarvest(false);
        setBonemealable(false);
        MinecraftForge.EVENT_BUS.addListener(this::checkDenySpawn);
        MinecraftForge.EVENT_BUS.addListener(this::checkEntityDeath);
    }

    private void checkDenySpawn(MobSpawnEvent.FinalizeSpawn event) {

        ChunkPos cPos = new ChunkPos(event.getEntity().blockPosition());
        if (!event.getLevel().isClientSide() && !event.getSpawnType().equals(MobSpawnType.SPAWNER) && event.getEntity() instanceof Monster || event.getEntity() instanceof Slime) {
            if (UCProtectionHandler.getInstance().getChunkInfo(event.getEntity().level()).contains(cPos))
                event.setResult(Event.Result.DENY);
        }
    }

    private void checkEntityDeath(LivingDeathEvent event) {

        if (event.getEntity() instanceof Monster) {
            CompoundTag tag = event.getEntity().getPersistentData();
            if (tag.contains("ImperiaPosTag") && tag.contains("ImperiaStage")) {
                BlockPos cropPos = NbtUtils.readBlockPos(tag.getCompound("ImperiaPosTag"));
                Level world = event.getEntity().level();
                if (!world.isEmptyBlock(cropPos) && world.hasChunkAt(cropPos)) {
                    if (world.getBlockState(cropPos).getBlock() == this && !world.isClientSide) {
                        int stage = tag.getInt("ImperiaStage");
                        this.advanceStage((ServerLevel)world, cropPos, world.getBlockState(cropPos), stage);
                    }
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource rand) {

        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            if (isMaxAge(state)) {
                setChunksAsNeeded(world, pos, false);
                return;
            }
            String[] mobList = new String[] { "minecraft:witch", "minecraft:skeleton", "minecraft:zombie", "minecraft:spider" };
            EntityType type = ForgeRegistries.ENTITY_TYPES.getValue(ResourceLocation.tryParse(mobList[rand.nextInt(mobList.length)]));
            Entity entity = type.create(world);
            if (!(entity instanceof LivingEntity)) return;

            entity.setPos(pos.getX(), pos.getY() + 0.5D, pos.getZ());
            CompoundTag tag = entity.getPersistentData();
            tag.put("ImperiaPosTag", NbtUtils.writeBlockPos(pos));
            tag.putInt("ImperiaStage", getAge(state));
            world.addFreshEntity(entity);
        }
        super.randomTick(state, world, pos, rand);
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, BlockEntity tile, ItemStack stack) {

        if (!world.isClientSide)
            setChunksAsNeeded((ServerLevel)world, pos, true);
        super.playerDestroy(world, player, pos, state, tile, stack);
    }

    public void advanceStage(ServerLevel world, BlockPos pos, BlockState state, int stage) {

        if (isMaxAge(state) || stage != getAge(state)) return;

        if (getAge(state) + 1 >= getMaxAge())
            setChunksAsNeeded(world, pos, false);

        UCPacketHandler.sendToNearbyPlayers(world, pos, new PacketUCEffect(EnumParticle.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 6));
        world.setBlock(pos, this.setValueAge(getAge(state) + 1), 3);
    }

    public void setChunksAsNeeded(ServerLevel world, BlockPos pos, boolean remove) {

        ChunkPos cPos = new ChunkPos(pos);
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                ChunkPos loopPos = new ChunkPos(cPos.x + i, cPos.z + j);
                if (remove)
                    UCProtectionHandler.getInstance().removeChunk(world, loopPos, true);
                else
                    UCProtectionHandler.getInstance().addChunk(world, loopPos, true);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource rand) {

        if (isMaxAge(state))
            world.addParticle(ParticleTypes.END_ROD, pos.getX() + rand.nextFloat(), pos.getY() + 0.3, pos.getZ() + rand.nextFloat(), 0, 0, 0);
    }
}
