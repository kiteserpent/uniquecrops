package com.remag.ucse.init;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.blocks.tiles.TileBarrel;
import com.remag.ucse.blocks.tiles.TileCraftyPlant;
import com.remag.ucse.gui.ContainerBarrel;
import com.remag.ucse.gui.ContainerCraftyPlant;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UCScreens {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, UniqueCrops.MOD_ID);

    public static final RegistryObject<MenuType<ContainerBarrel>> BARREL = register("abstract_barrel", (IContainerFactory<ContainerBarrel>)(windowId, inventory, data) -> {
        TileBarrel te = (TileBarrel)inventory.player.level().getBlockEntity(data.readBlockPos());
        return new ContainerBarrel(windowId, inventory, te);
    });
    public static final RegistryObject<MenuType<ContainerCraftyPlant>> CRAFTYPLANT = register("crafty_plant", (IContainerFactory<ContainerCraftyPlant>)(windowId, inventory, data) -> {
        TileCraftyPlant te = (TileCraftyPlant)inventory.player.level().getBlockEntity(data.readBlockPos());
        return new ContainerCraftyPlant(windowId, inventory, te);
    });

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory) {

        return CONTAINERS.register(id, () -> new MenuType<>(factory, FeatureFlags.DEFAULT_FLAGS));
    }
}
