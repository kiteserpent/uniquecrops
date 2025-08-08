package com.remag.ucse.events;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.api.IBookUpgradeable;
import com.remag.ucse.api.ICropPower;
import com.remag.ucse.api.IMultiblockRecipe;
import com.remag.ucse.capabilities.CPProvider;
import com.remag.ucse.core.DyeUtils;
import com.remag.ucse.core.NBTUtils;
import com.remag.ucse.core.UCStrings;
import com.remag.ucse.core.enums.EnumBonemealDye;
import com.remag.ucse.core.enums.EnumLily;
import com.remag.ucse.init.UCBlocks;
import com.remag.ucse.init.UCItems;
import com.remag.ucse.integration.patchouli.PatchouliUtils;
import com.remag.ucse.items.DyedBonemealItem;
import com.remag.ucse.items.GoodieBagItem;
import com.remag.ucse.items.LeagueBootsItem;
import com.remag.ucse.network.PacketSyncCap;
import com.remag.ucse.network.UCPacketHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.remag.ucse.items.LeagueBootsItem.CMONSTEPITUP;
import static com.remag.ucse.items.LeagueBootsItem.DEFAULT_SPEED;

@Mod.EventBusSubscriber(modid = UniqueCrops.MOD_ID)
public class UCEventHandlerCommon {

    public static void updateAnvilCost(AnvilUpdateEvent event) {

        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) return;

        if ((left.getItem() == UCItems.BOOK_UPGRADE.get() && right.getItem() instanceof IBookUpgradeable) || (left.getItem() instanceof IBookUpgradeable && right.getItem() == UCItems.BOOK_UPGRADE.get())) {
            ItemStack output = (left.getItem() instanceof IBookUpgradeable) ? left.copy() : right.copy();
            IBookUpgradeable upgrade = ((IBookUpgradeable)output.getItem());
            if (upgrade.isMaxLevel(output)) return;

            if (upgrade.getLevel(output) <= 0)
                upgrade.setLevel(output, 1);
            else
                upgrade.setLevel(output, upgrade.getLevel(output) + 1);

            event.setOutput(output);
            event.setCost(5);
        }
    }

    public static void onBonemealEvent(BonemealEvent event) {

        if (!(event.getBlock().getBlock() instanceof GrassBlock) || event.getLevel().isClientSide()) return;
        ItemStack stack = event.getStack();
        if (stack.getItem() instanceof DyedBonemealItem) {
            DyeUtils.BONEMEAL_DYE.forEach((key, value) -> {
                if (value.asItem() == stack.getItem()) {
                    event.setResult(EnumBonemealDye.values()[key.ordinal()].grow(event.getLevel(), event.getPos()));
                }
            });
        }
    }

    public static void jumpTele(LivingEvent.LivingJumpEvent event) {

        LivingEntity elb = event.getEntity();
        if (elb.level().isClientSide) return;

        if (elb instanceof Player) {
            if (elb.level().getBlockState(elb.blockPosition()).getBlock() == UCBlocks.LILY_ENDER.get()) {
                EnumLily.searchNearbyPads(elb.level(), elb.blockPosition(), elb, Direction.UP);
            }
        }
    }

    public static void addSeed(BlockEvent.BreakEvent event) {

        if (event.getState().is(Blocks.GRASS) || event.getState().is(Blocks.TALL_GRASS) || event.getState().is(Blocks.FERN) || event.getState().is(Blocks.LARGE_FERN)) {
            if (event.getLevel() instanceof ServerLevel serverlevel) {
                BlockPos pos = event.getPos();
                float value = serverlevel.random.nextFloat();
                if (value > 0.90F) {
                    Containers.dropItemStack(serverlevel, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCItems.NORMAL_SEED.get()));
                }
                if (value > 0.92F && GoodieBagItem.isHoliday()) {
                    Containers.dropItemStack(serverlevel, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(UCItems.ADVENTUS_SEED.get()));
                }
            }
        }
    }

    public static void injectLoot(LootTableLoadEvent event) {

        if (event.getName().equals(BuiltInLootTables.WOODLAND_MANSION))
            event.getTable().addPool(getInjectPool("chests/woodland_mansion"));
        if (event.getName().equals(BuiltInLootTables.IGLOO_CHEST))
            event.getTable().addPool(getInjectPool("chests/igloo_chest"));
        if (event.getName().equals(BuiltInLootTables.SIMPLE_DUNGEON))
            event.getTable().addPool(getInjectPool("chests/simple_dungeon"));
    }

    private static LootPool getInjectPool(String pool) {

        return LootPool.lootPool()
                .add(getInjectEntry(pool, 1))
                .name("ucse_inject")
                .build();
    }

    private static LootPoolEntryContainer.Builder<?> getInjectEntry(String name, int weight) {

        ResourceLocation injectFolder = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "inject/" + name);
        return LootTableReference.lootTableReference(injectFolder).setWeight(weight);
    }

    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {

        IMultiblockRecipe recipe = findRecipe(event.getLevel(), event.getPos());
        if (recipe != null) {
            Player player = event.getEntity();
            ItemStack held = player.getItemInHand(event.getHand());
            if (!ItemStack.isSameItem(held, recipe.getCatalyst())) return;
            int cropPower = recipe.getPower();

            LazyOptional<ICropPower> cap = held.getCapability(CPProvider.CROP_POWER, null);
            if (cropPower > 0 && !cap.isPresent()) {
                player.displayClientMessage(Component.literal("Crop power is not present in this item: " + held.getDisplayName()), true);
                return;
            }
            cap.ifPresent(crop -> {
                if (crop.getPower() < cropPower) {
                    player.displayClientMessage(Component.literal("Insufficient crop power. Needed: " + cropPower), true);
                    event.setCanceled(true);
                }
                else if (!player.isCreative()) {
                    crop.remove(cropPower);
                    if (player instanceof ServerPlayer)
                        UCPacketHandler.sendTo((ServerPlayer)player, new PacketSyncCap(crop.serializeNBT()));
                }
            });
            if (cropPower <= 0 && !event.getLevel().isClientSide && !player.isCreative()) {
                held.shrink(1);
            }
            recipe.setResult(event.getLevel(), event.getPos());
            event.setCanceled(true);
            player.swing(event.getHand());
        }
    }

    public static void attachItemCaps(AttachCapabilitiesEvent<ItemStack> event) {

        var stack = event.getObject();
        if (stack.getItem() == UCItems.WILDWOOD_STAFF.get()) {
            event.addCapability(ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "crop_power"), new CPProvider());
        }
    }

    private static IMultiblockRecipe findRecipe(Level level, BlockPos pos) {

        for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
            if (recipe instanceof IMultiblockRecipe && ((IMultiblockRecipe)recipe).match(level, pos))
                return ((IMultiblockRecipe) recipe);
        }
        return null;
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        Level world = player.level();

        ItemStack stack = player.getItemBySlot(EquipmentSlot.FEET); // Or HEAD, CHEST, LEGS
        if (!(stack.getItem() instanceof LeagueBootsItem leagueBootsItem)) return;

        String name = getPlayerStr(player);
        if (CMONSTEPITUP.contains(name)) {
            if (world.isClientSide) {
                float SPEED = NBTUtils.getFloat(stack, UCStrings.SPEED_MODIFIER, DEFAULT_SPEED);
                if ((player.onGround() || player.getAbilities().flying) && player.zza > 0F && !player.isInWaterOrBubble()) {
                    player.moveRelative(SPEED, new Vec3(0F, 0F, 1F));
                }

                if (player.isCrouching()) {
                    player.setMaxUpStep(0.60001F);
                } else {
                    player.setMaxUpStep(1.0625F);
                }

                leagueBootsItem.snapForward(player, stack);
            }
        } else {
            CMONSTEPITUP.add(name);
            player.setMaxUpStep(1.0625F);
        }
    }

    private static String getPlayerStr(Player player) {
        return player.getStringUUID();
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        System.out.println("Running Registering Multiblocks...");
        PatchouliUtils.registerMultiblocks();
    }
}
