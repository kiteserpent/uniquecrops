package com.remag.ucse.items;

import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.core.*;
import com.remag.ucse.core.enums.EnumArmorMaterial;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.items.base.ItemArmorUC;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Random;

public class GlassesPixelItem extends ItemArmorUC implements IBookUpgradeable {


    public GlassesPixelItem() {

        super(EnumArmorMaterial.GLASSES_PIXELS, Type.HELMET);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onBlockBreak);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {

        Player player = event.player;

        ItemStack pixelGlasses = player.getInventory().armor.get(3);
        if (pixelGlasses.is(this)) {
            boolean flag = NBTUtils.getBoolean(pixelGlasses, "isActive", false);
            boolean flag2 = isMaxLevel(pixelGlasses);
            if (flag && flag2) {
                if (event.phase == TickEvent.Phase.START && player.level().getGameTime() % 20 == 0) {
                    ChunkPos cPos = new ChunkPos(player.blockPosition());
                    if (!event.side.isClient()) {
                        if (UCOreHandler.getInstance().getSaveInfo().containsKey(cPos)) {
                            BlockPos pos = UCOreHandler.getInstance().getSaveInfo().get(cPos);
                            NBTUtils.setLong(pixelGlasses, "orePos", pos.asLong());
                            UCOreHandler.getInstance().removeChunk(player.level(), BlockPos.ZERO, true);
                        }
                        else {
                            NBTUtils.setLong(pixelGlasses, "orePos", BlockPos.ZERO.asLong());
                            UCOreHandler.getInstance().addChunk(player.level(), BlockPos.ZERO, true);
                        }
                    }
                }
            }
        }
        if (player.getPersistentData().contains(UCStrings.TAG_ABSTRACT)) {
            if (event.phase == TickEvent.Phase.START && player.level().random.nextInt(1000) == 0) {
                Random rand = new Random();
                if (rand.nextInt(10) != 0) {
                    ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(UCItems.ABSTRACT.get()));
                    if (!event.player.level().isClientSide)
                        UCUtils.setAbstractCropGrowth(event.player, -1);
                }
            }
        }
    }

    private void onBlockBreak(BlockEvent.BreakEvent event) {

        if (event.getPlayer() == null) return;

        Player player = event.getPlayer();

        if (player.getMainHandItem().isCorrectToolForDrops(event.getState()) && player.getInventory().armor.get(3).getItem() == this) {
            boolean flag = NBTUtils.getBoolean(player.getInventory().armor.get(3), "isActive", false);
            boolean flag2 = isMaxLevel(player.getInventory().armor.get(3));
            if (flag && flag2 && event.getState().is(BlockTags.BASE_STONE_OVERWORLD)) {
                if (UCOreHandler.getInstance().getSaveInfo().containsValue(event.getPos())) {
                    if (!event.getLevel().isClientSide()) {
                        Containers.dropItemStack(event.getPlayer().level(), event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, new ItemStack(UCItems.DIAMONDS.get()));
                        UCOreHandler.getInstance().removeChunk(event.getPlayer().level(), event.getPos(), true);
                    }
                    if (!player.isCreative())
                        player.getInventory().armor.get(3).hurtAndBreak(2, player, (entity) -> {});
                }
            }
        }
    }
}
