package com.remag.ucse.items;

import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.core.enums.EnumArmorMaterial;
import com.remag.ucse.init.UCPotions;
import com.remag.ucse.items.base.ItemArmorUC;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;

public class PonchoItem extends ItemArmorUC implements IBookUpgradeable {

    public PonchoItem() {

        super(EnumArmorMaterial.PONCHO, Type.CHESTPLATE);
        MinecraftForge.EVENT_BUS.addListener(this::checkSetTarget);
    }

    private void checkSetTarget(LivingChangeTargetEvent event) {

        if (event.getNewTarget() == null) return;
        if (!(event.getNewTarget() instanceof Player) || event.getNewTarget() instanceof FakePlayer) return;
        if (!(event.getEntity() instanceof Mob)) return;

        Player player = (Player)event.getNewTarget();
        Mob ent = (Mob)event.getEntity();
        if (player.getEffect(UCPotions.IGNORANCE.get()) != null) {
            ent.setTarget(null);
            ent.setLastHurtByMob(null);
            return;
        }
        boolean flag = player.getInventory().armor.get(2).getItem() == this && this.isMaxLevel(player.getInventory().armor.get(2));
        if (flag && ent.isPickable() && !(ent instanceof Guardian || ent instanceof Shulker)) {
            ent.setTarget(null);
            ent.setLastHurtByMob(null);
        }
    }
}
