package com.remag.ucse.mixin;

import com.remag.ucse.integration.patchouli.PatchouliUtils;
import com.remag.ucse.items.curios.EmblemIronStomach;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeManager.class)
public class MixinRecipeLoad {

    @Inject(method = "replaceRecipes", at = @At("RETURN"))
    public void ucse_onSync(Iterable<Recipe<?>> recipes, CallbackInfo info) {

        PatchouliUtils.registerMultiblocks();
        EmblemIronStomach.init();
    }
}
