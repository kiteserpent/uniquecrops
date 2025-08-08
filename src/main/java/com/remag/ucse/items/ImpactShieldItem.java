package com.remag.ucse.items;

import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemBaseUC;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class ImpactShieldItem extends ItemBaseUC {

    private static final String DAMAGE_POOL = "UC:ImpactShieldDamage";

    public ImpactShieldItem() {

        super(UCItems.defaultBuilder().durability(25));
        MinecraftForge.EVENT_BUS.addListener(this::onShieldBlock);
    }

    private void onShieldBlock(LivingAttackEvent event) {

        if (event.getEntity().level().isClientSide || !(event.getEntity() instanceof Player)) return;

        Player player = (Player)event.getEntity();
        Holder<DamageType> magicDamage = player.level().registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(DamageTypes.MAGIC);

        DamageSource source = new DamageSource(magicDamage);
        if (event.getSource() != source && event.getSource().getEntity() instanceof LivingEntity) {
            ItemStack activeStack = player.getUseItem();
            if (activeStack.getItem() == UCItems.IMPACT_SHIELD.get()) {
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
            player.level().explode(player, player.getX(), player.getY(), player.getZ(), Math.min(strength, 50F), Level.ExplosionInteraction.NONE);

            stack.setDamageValue(0);
            player.getCooldowns().addCooldown(this, 300);
            NBTUtils.setFloat(stack, DAMAGE_POOL, 0);
            player.stopUsingItem();
            return;
        }
        NBTUtils.setFloat(stack, DAMAGE_POOL, strength + damage);
    }
}
