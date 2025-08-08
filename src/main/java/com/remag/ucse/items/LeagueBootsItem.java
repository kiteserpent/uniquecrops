package com.remag.ucse.items;

import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.core.enums.EnumArmorMaterial;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemArmorUC;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

public class LeagueBootsItem extends ItemArmorUC implements IBookUpgradeable {

    public static final float DEFAULT_SPEED = 0.055F;
    private static final float JUMP_FACTOR = 0.2F;
    private static final float FALL_BUFFER = 2F;

    public static final List<String> CMONSTEPITUP = new ArrayList<>();

    public LeagueBootsItem() {

        super(EnumArmorMaterial.BOOTS_LEAGUE, Type.BOOTS);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerJump);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerFall);
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::playerLoggedOut);
    }

    private void onPlayerJump(LivingEvent.LivingJumpEvent event) {

        if (event.getEntity() instanceof Player) {
            ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
            if (boots.getItem() == UCItems.SEVEN_LEAGUE_BOOTS.get()) {
                event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().add(0, JUMP_FACTOR, 0));
                event.getEntity().fallDistance -= FALL_BUFFER;
            }
        }
    }

    private void onPlayerFall(LivingFallEvent event) {

        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            if (boots.getItem() == this) {
                event.setDistance(Math.max(0, event.getDistance() - FALL_BUFFER));
            }
        }
    }

    private void playerTick(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof Player player) {
            String name = getPlayerStr(player);
            if (CMONSTEPITUP.contains(name)) {
                ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
                if (boots.getItem() != this) {
                    player.setMaxUpStep(0.6F);
                    CMONSTEPITUP.remove(name);
                }
            }
        }
    }

    private void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {

        String username = event.getEntity().getGameProfile().getName();
        CMONSTEPITUP.remove(username + ":false");
        CMONSTEPITUP.remove(username + ":true");
    }

    public void snapForward(Player player, ItemStack stack) {

//        if (player.world.provider.getDimension() == UCDimension.dimID) return;

        float speedMod = 0.95F;
        int sprintTicks = NBTUtils.getInt(stack, UCStrings.SPRINTING_TICKS, 0);
        if (sprintTicks > 0) {
            NBTUtils.setInt(stack, UCStrings.SPRINTING_TICKS, sprintTicks - 1);
            return;
        }
        if (player.isSprinting() && !player.getAbilities().flying) {
            if (NBTUtils.getFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED) == DEFAULT_SPEED) {
                NBTUtils.setFloat(stack, UCStrings.SPEED_MODIFIER, speedMod * Math.max(getLevel(stack), 1));
                return;
            }
            else {
                player.setSprinting(false);
                NBTUtils.setInt(stack, UCStrings.SPRINTING_TICKS, 20);
            }
        }
        if (!player.isSprinting()) {
            NBTUtils.setFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED);
        }
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {

        return false;
    }

    private String getPlayerStr(Player player) {

        return player.getGameProfile().getName() + ":" + player.level().isClientSide;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {

        return Rarity.EPIC;
    }
}
