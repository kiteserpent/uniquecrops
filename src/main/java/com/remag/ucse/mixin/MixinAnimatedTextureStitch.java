package com.remag.ucse.mixin;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Mixin(targets = "net.minecraft.client.renderer.texture.SpriteContents$Ticker")
public abstract class MixinAnimatedTextureStitch {

    @Shadow int frame;
    @Shadow int subFrame;

    @Shadow @Final
    SpriteContents this$0;

    private long lastUpdateTime = -1;
    private int currentFrame = 0;

    @Inject(method = "tickAndUpload", at = @At("HEAD"), cancellable = true)
    private void updateTextureAnimations(int x, int y, CallbackInfo ci) {
        if (updateTextureInvisible(x, y)) {
            ci.cancel();
            return;
        }
        if (updateTextureDyeius(x, y)) {
            ci.cancel();
        }
    }

    private boolean updateTextureInvisible(int x, int y) {
        if (!isUniqueTexture("invisibilia")) return false;
        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null) {
            if ((p.getInventory().armor.get(3).getItem() != UCItems.GLASSES_3D.get()))
                this.frame = 1;
            else
                this.frame = 0;
        }
        invokeUploadFrame(x, y, this.frame);
        return true;
    }

    private boolean updateTextureDyeius(int x, int y) {
        if (!isUniqueTexture("dyeplant5")) return false;
        Level world = Minecraft.getInstance().level;
        if (world == null) return false;

        long time = world.getDayTime() % 24000L;

        int frameCount = 16;
        int calculatedFrame = (int)(time / 1500) % frameCount;
        this.frame = calculatedFrame + 1;
        invokeUploadFrame(x, y, this.frame);
        return true;
    }

    private boolean isUniqueTexture(String texName) {
        if (this$0 == null) return false;
        if (!this$0.name().getNamespace().equals(UniqueCrops.MOD_ID)) return false;
        return this$0.name().getPath().contains(texName);
    }

    private void invokeUploadFrame(int x, int y, int frame) {
        try {
            Field animationInfoField = this.getClass().getDeclaredField("animationInfo");
            animationInfoField.setAccessible(true);
            Object animationInfo = animationInfoField.get(this);

            Method uploadFrameMethod = animationInfo.getClass().getDeclaredMethod("uploadFrame", int.class, int.class, int.class);
            uploadFrameMethod.setAccessible(true);
            uploadFrameMethod.invoke(animationInfo, x, y, frame);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke uploadFrame", e);
        }
    }
}
