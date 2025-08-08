package com.remag.ucse.items;

import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.enums.EnumParticle;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.network.PacketUCEffect;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class BrassKnucklesItem extends SwordItem {

    private static final String HIT_LIST = "UC:hitList";
    private static final String HIT_ENTITY = "UC:hitEntityId";
    private static final String HIT_TIME = "UC:hitTime";
    private static final String HIT_AMOUNT = "UC:hitAmount";

    public BrassKnucklesItem() {

        super(Tiers.IRON, 1, 1.31F, UCItems.unstackable());
        MinecraftForge.EVENT_BUS.addListener(this::knuckleDuster);
    }

    private void knuckleDuster(LivingAttackEvent event) {

        if (event.getEntity().level().isClientSide) return;
        if (event.getEntity() != null && event.getSource().getDirectEntity() instanceof Player player) {
            ItemStack brassKnuckles = player.getMainHandItem();
            if (brassKnuckles.getItem() == this) {
                boolean flag = event.getSource().getDirectEntity() != event.getSource().getEntity();
                if (flag) return;
//                boolean flag = NBTUtils.getList(brassKnuckles, HIT_LIST, 10, true) != null && NBTUtils.getList(brassKnuckles, HIT_LIST, 10, true).isEmpty();
//                if (!flag) return;
                float damage = event.getAmount();
                addHitEntity(event.getEntity(), brassKnuckles, damage);
                event.setCanceled(true);
                BlockPos pos = event.getEntity().blockPosition();
                UCPacketHandler.sendToNearbyPlayers(player.level(), player.blockPosition(), new PacketUCEffect(EnumParticle.CRIT, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 6));
            }
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.RARE;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {

        if (!world.isClientSide && entity instanceof Player)
            removeHitEntity(stack, world, (Player)entity, selected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

        return !ItemStack.isSameItem(oldStack, newStack);
    }

    private void addHitEntity(LivingEntity target, ItemStack stack, float damage) {

        ListTag tagList = NBTUtils.getList(stack, HIT_LIST, 10, false);
        if (tagList.size() > 4) return;

        CompoundTag nbt = new CompoundTag();
        nbt.putInt(HIT_ENTITY, target.getId());
        nbt.putInt(HIT_TIME, 25);
        nbt.putFloat(HIT_AMOUNT, damage);
        tagList.add(nbt);
        NBTUtils.setList(stack, HIT_LIST, tagList);
    }

    private void removeHitEntity(ItemStack stack, Level world, Player player, boolean selected) {

        ListTag tagList = NBTUtils.getList(stack, HIT_LIST, 10, true);
        if (tagList == null || tagList.isEmpty()) return;

        boolean remove = false;
        if (!selected) {
            tagList.clear();
            return;
        }
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag nbt = tagList.getCompound(i);
            int timer = nbt.getInt(HIT_TIME);
            if (timer > 0)
                nbt.putInt(HIT_TIME, --timer);
            else {
                LivingEntity elb = (LivingEntity)world.getEntity(nbt.getInt(HIT_ENTITY));
                if (elb != null) {
                    float damage = nbt.getFloat(HIT_AMOUNT);
                    Holder<DamageType> playerDamage = player.level().registryAccess()
                            .registryOrThrow(Registries.DAMAGE_TYPE)
                            .getHolderOrThrow(DamageTypes.PLAYER_ATTACK);

                    DamageSource source = new DamageSource(playerDamage);
                    elb.hurt(source, damage);
                    elb.knockback(damage * 0.131F, Mth.sin(player.yRotO * ((float)Math.PI / 180F)), -Mth.cos(player.yRotO * ((float)Math.PI / 180F)));
                    elb.invulnerableTime = 0;
                }
                tagList.remove(i);
                remove = true;
            }
        }
        if (remove)
            NBTUtils.setList(stack, HIT_LIST, tagList);
    }
}
