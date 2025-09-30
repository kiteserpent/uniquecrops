package com.bafomdad.uniquecrops.items;

import com.bafomdad.uniquecrops.core.NBTUtils;
import com.bafomdad.uniquecrops.init.UCItems;
import com.bafomdad.uniquecrops.items.base.ItemBaseUC;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ImpactShieldItem extends ItemBaseUC {

    private static final String DAMAGE_POOL = "UC:ImpactShieldDamage";
    private static long lastBlockTime = 0;

    public ImpactShieldItem() {

        super(UCItems.defaultBuilder().durability(25));
        MinecraftForge.EVENT_BUS.addListener(this::onShieldBlock);
    }

    private void onShieldBlock(LivingAttackEvent event) {

        Level level = event.getEntity().level;
        if (level.isClientSide || !(event.getEntityLiving() instanceof Player)) return;

        Player player = (Player)event.getEntityLiving();
        if (event.getSource() != DamageSource.MAGIC && event.getSource().getEntity() instanceof LivingEntity) {
            ItemStack activeStack = player.getUseItem();
            if (activeStack.getItem() == UCItems.IMPACT_SHIELD.get()) {
                long blockTime = level.getGameTime();
                if (blockTime - lastBlockTime >= 10) {
                    level.playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0f, 1.0f);
                    lastBlockTime = blockTime;
                }
                damageImpactShield(player, activeStack, event.getAmount());
                event.setCanceled(true);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {

        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {

        return UseAnim.BLOCK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this))
            return new InteractionResultHolder(InteractionResult.PASS, stack);

        player.startUsingItem(hand);
        return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
    }

    private void damageImpactShield(Player player, ItemStack stack, float damage) {

        stack.setDamageValue(stack.getDamageValue() + 1);
        float strength = NBTUtils.getFloat(stack, DAMAGE_POOL, 0);
        if (stack.getDamageValue() > stack.getMaxDamage()) {
            player.level.explode(player, player.getX(), player.getY(), player.getZ(), Math.min(strength, 20F), Explosion.BlockInteraction.NONE);

            stack.setDamageValue(0);
            player.getCooldowns().addCooldown(this, 300);
            NBTUtils.setFloat(stack, DAMAGE_POOL, 0);
            player.stopUsingItem();
            return;
        }
        NBTUtils.setFloat(stack, DAMAGE_POOL, strength + damage);
    }
}
