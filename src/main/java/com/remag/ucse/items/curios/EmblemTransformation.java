package com.remag.ucse.items.curios;

import com.remag.ucse.items.base.ItemCurioUC;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

public class EmblemTransformation extends ItemCurioUC {

    public EmblemTransformation() {

        MinecraftForge.EVENT_BUS.addListener(this::onHitEntity);
    }

    private void onHitEntity(LivingHurtEvent event) {
        if (event.getAmount() <= 0 || event.getEntity() instanceof Player) return;
        if (!(event.getSource().getDirectEntity() instanceof Player)) return;
        if (!hasCurio((LivingEntity) event.getSource().getDirectEntity())) return;

        Random rand = new Random();
        if (rand.nextInt(100) == 0) {
            LivingEntity elb = event.getEntity();

            // Get all EntityTypes from the registry
            List<EntityType<?>> entityTypes = ForgeRegistries.ENTITY_TYPES.getValues().stream()
                    .filter(type -> type.create(elb.level()) instanceof LivingEntity) // only spawn living entities
                    .filter(type -> !(type.create(elb.level()) instanceof WitherBoss)) // exclude Wither
                    .filter(type -> !(type.create(elb.level()) instanceof EnderDragon)) // exclude Dragon
                    .toList();

            if (entityTypes.isEmpty()) return;

            EntityType<?> type = entityTypes.get(rand.nextInt(entityTypes.size()));
            Entity entity = type.create(elb.level());

            if (entity == null) return;

            entity.moveTo(elb.getX(), elb.getY(), elb.getZ(), elb.getYRot(), elb.getXRot());
            elb.level().addFreshEntity(entity);
            elb.discard();
        }
    }
}
