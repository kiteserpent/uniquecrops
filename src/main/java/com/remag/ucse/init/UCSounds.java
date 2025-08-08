package com.remag.ucse.init;

import com.remag.ucse.UniqueCrops;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class UCSounds {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UniqueCrops.MOD_ID);

    public static final RegistryObject<SoundEvent> OOF = createSound("oof");
    public static final RegistryObject<SoundEvent> NEON_SIGNS = createSound("neonsigns");
    public static final RegistryObject<SoundEvent> FAR_AWAY = createSound("faraway");
    public static final RegistryObject<SoundEvent> TAXI = createSound("taxi");
    public static final RegistryObject<SoundEvent> SIMPLY = createSound("simply");

    private static RegistryObject<SoundEvent> createSound(String name) {

        return SOUNDS.register(name, () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, name), 1.0F));
    }
}
