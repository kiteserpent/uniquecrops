package com.remag.ucse.events;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.gui.GuiStaffOverlay;
import com.remag.ucse.init.*;
import com.remag.ucse.network.PacketSendKey;
import com.remag.ucse.network.UCPacketHandler;
import com.remag.ucse.render.CustomBufferSource;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.ChatFormatting;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UCEventHandlerClient {

    @SubscribeEvent
    public static void onDrawScreenPost(RenderGuiOverlayEvent.Post event) {

        Minecraft mc = Minecraft.getInstance();
        GuiStaffOverlay overlay = new GuiStaffOverlay(mc);
        overlay.renderOverlay(event);
    }

    @SubscribeEvent
    public static void handleKeyPressed(InputEvent.Key event) {

        if (UCClient.PIXEL_GLASSES.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            if (player.getInventory().getArmor(3).getItem() != UCItems.GLASSES_PIXELS.get()) return;

            UCPacketHandler.INSTANCE.sendToServer(new PacketSendKey());
        }
    }

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {

        if (event.getSound() == null || event.getSound().getSource() == null) return;

        if (event.getSound().getLocation().getPath().equals("entity.player.hurt")) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                boolean flag = mc.player.getInventory().contains(new ItemStack(UCBlocks.HOURGLASS.get()));
                if (flag) {
                    event.setSound(null);
                    mc.level.playSound(mc.player, mc.player.blockPosition(), UCSounds.OOF.get(), SoundSource.PLAYERS, 1F, 1F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void showTooltip(ItemTooltipEvent event) {

        if (event.getItemStack().getItem() != Blocks.SPAWNER.asItem()) return;

        ItemStack tooltipper = event.getItemStack();
        if (!tooltipper.hasTag() || (tooltipper.hasTag() && !tooltipper.getTag().contains("Spawner"))) return;

        CompoundTag tag = tooltipper.getTag().getCompound("Spawner");
        event.getToolTip().add(Component.literal("Mob spawner data:"));
        event.getToolTip().add(Component.literal(ChatFormatting.GOLD + tag.getCompound("SpawnData").getString("id")));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return; // run only once per tick

        Player player = event.player;
        if (player.level().isClientSide) { // just to be safe, confirm client side

            // Check if player is wearing your special armor
            if (isWearingYourArmor(player)) {

                // Update flying speed (client-only)
                player.getAbilities().setFlyingSpeed(0.025F + 1 * 0.02F);

                // Modify vertical movement and fall distance
                if (player.getDeltaMovement().y < -0.175F
                        && !player.onGround()
                        && !player.getAbilities().flying
                        && !player.isCrouching()) {

                    Vec3 velocity = player.getDeltaMovement();
                    player.setDeltaMovement(velocity.x, -0.175F, velocity.z);
                    player.fallDistance = 0f;
                }
            }
        }
    }

    private static boolean isWearingYourArmor(Player player) {
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        return !chestplate.isEmpty() && chestplate.getItem() == UCItems.PONCHO.get();
    }

    private static MultiBufferSource.BufferSource buffers = null;

    public static void renderWorldLast(PoseStack ms) {

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (mc.level == null || player == null) return;

        ItemStack glasses = player.getInventory().getArmor(3);
        if (glasses.getItem() == UCItems.GLASSES_PIXELS.get()) {
            boolean flag = NBTUtils.getBoolean(glasses, "isActive", false);
            boolean flag2 = ((IBookUpgradeable) glasses.getItem()).isMaxLevel(glasses);

            if (flag && flag2) {
                BlockPos pos = BlockPos.of(NBTUtils.getLong(glasses, "orePos", BlockPos.ZERO.asLong()));
                if (!pos.equals(BlockPos.ZERO)) {
                    if (buffers == null)
                        buffers = CustomBufferSource.initBuffers(mc.renderBuffers().bufferSource());
                    renderOres(pos, mc, ms);
                }
            }
        }
    }

    private static void renderOres(BlockPos pos, Minecraft mc, PoseStack ms) {

        double renderPosX = mc.getEntityRenderDispatcher().camera.getPosition().x;
        double renderPosY = mc.getEntityRenderDispatcher().camera.getPosition().y;
        double renderPosZ = mc.getEntityRenderDispatcher().camera.getPosition().z;

        ms.pushPose();
        ms.translate(pos.getX() - renderPosX, pos.getY() - renderPosY, pos.getZ() - renderPosZ);

        BlockRenderDispatcher brd = mc.getBlockRenderer();
        brd.renderSingleBlock(Blocks.PINK_CONCRETE.defaultBlockState(), ms, buffers, 0xF000F0, OverlayTexture.NO_OVERLAY);
        buffers.endBatch();
        ms.popPose();
    }
}
