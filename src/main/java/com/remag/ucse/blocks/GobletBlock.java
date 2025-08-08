package com.remag.ucse.blocks;

import com.remag.ucse.blocks.tiles.TileGoblet;
import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.UUID;

public class GobletBlock extends Block implements EntityBlock {

    public static final BooleanProperty FILLED = BooleanProperty.create("filled");
    public static final VoxelShape GOBLET_AABB = Shapes.box(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D);

    public GobletBlock() {

        super(Properties.of().sound(SoundType.METAL).strength(0.3F, 1.0F).noCollission().mapColor(MapColor.CLAY));
        registerDefaultState(defaultBlockState().setValue(FILLED, false));
        MinecraftForge.EVENT_BUS.addListener(this::onLivingAttack);
    }

    private void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        DamageSource source = event.getSource();
        if (!source.is(DamageTypes.MAGIC) && source.getEntity() instanceof LivingEntity) {
            if (event.getEntity().level() instanceof ServerLevel serverLevel) {
                BlockPos center = event.getEntity().blockPosition();
                int radius = 8;

                Iterable<BlockPos> positions = BlockPos.betweenClosed(
                        center.offset(-radius, -radius, -radius),
                        center.offset(radius, radius, radius)
                );

                for (BlockPos pos : positions) {
                    BlockEntity tile = serverLevel.getBlockEntity(pos);
                    if (tile instanceof TileGoblet goblet) {
                        LivingEntity tagged = UCUtils.getTaggedEntity(goblet.entityId);
                        if (tagged != null) {
                            event.setCanceled(true);
                            tagged.hurt(source, event.getAmount());
                            if (!tagged.isAlive()) {
                                goblet.eraseTaglock();
                            }
                            return; // exit early once found and processed
                        }
                    }
                }
            }
        }
    }

    public boolean isFilled(BlockState state) {

        return state.getValue(FILLED);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {

        super.createBlockStateDefinition(builder);
        builder.add(FILLED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {

        return GOBLET_AABB;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        if (!isFilled(state)) {
            ItemStack stack = player.getMainHandItem();
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof TileGoblet && stack.getItem() == UCItems.VAMPIRIC_OINTMENT.get()) {
                boolean flag = stack.hasTag() && stack.getTag().contains(UCStrings.TAG_LOCK);
                if (!world.isClientSide && flag) {
                    ((TileGoblet)tile).setTaglock(UUID.fromString(NBTUtils.getString(stack, UCStrings.TAG_LOCK, "")));
                    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                    world.setBlock(pos, state.setValue(FILLED, true), 3);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {

        if (isFilled(state)) return;
        if (!(world.getBlockEntity(pos) instanceof TileGoblet)) return;
        if (!(entity instanceof ItemEntity) || ((ItemEntity) entity).getItem().getItem() != UCItems.VAMPIRIC_OINTMENT.get()) return;

        ItemStack ointment = ((ItemEntity)entity).getItem();
        if (!ointment.hasTag() || !ointment.getTag().contains(UCStrings.TAG_LOCK)) return;

        if (!world.isClientSide) {
            world.setBlock(pos, state.setValue(FILLED, true), 3);
            ((TileGoblet)world.getBlockEntity(pos)).setTaglock(UUID.fromString(NBTUtils.getString(ointment, UCStrings.TAG_LOCK, "")));
            entity.discard();
        }
    }

    @Override
    public int getSignal(BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) {

        return isFilled(state) ? 15 : 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level worldIn, BlockPos pos, RandomSource rand) {

        if (!isFilled(state)) return;

        double d0 = (double)pos.getX() + 0.45F;
        double d1 = (double)pos.getY() + 0.4F;
        double d2 = (double)pos.getZ() + 0.5F;
        worldIn.addParticle(DustParticleOptions.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {

        return new TileGoblet(pos, state);
    }
}
