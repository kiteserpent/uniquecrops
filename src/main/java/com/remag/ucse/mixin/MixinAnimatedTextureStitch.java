package com.remag.ucse.mixin;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.init.UCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = { "net.minecraft.client.renderer.texture.TextureAtlasSprite$AnimatedTexture" })
public abstract class MixinAnimatedTextureStitch {

    @Shadow
    int frame;

    @Shadow
    abstract void uploadFrame(int frame);

    @Shadow @Final
    TextureAtlasSprite this$0;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void updateTextureAnimations(CallbackInfo ci) {

        updateTextureDyeius();
        updateTextureInvisible();
    }

    private void updateTextureInvisible() {

        if (!isUniqueTexture("invisibilia")) return;
        LocalPlayer p = Minecraft.getInstance().player;
        if (p != null) {
            if ((p.getInventory().armor.get(3).getItem() == UCItems.GLASSES_3D.get()) || p.isCreative())
                this.frame = 1;
            else
                this.frame = 0;
        }
        this.uploadFrame(this.frame);
    }

    private void updateTextureDyeius() {

        if (!isUniqueTexture("dyeplant5")) return;

        Level world = Minecraft.getInstance().level;
        if (world != null) {
            long time = world.getDayTime() % 24000L;
            this.frame = (int)(time / 1500);
        }
        this.uploadFrame(this.frame);
    }

    private boolean isUniqueTexture(String texName) {

        if (!this.this$0.atlasLocation().getNamespace().equals(UniqueCrops.MOD_ID)) return false;

        return this.this$0.atlasLocation().getPath().contains(texName);
    }
}
