package com.remag.ucse.init;

import com.remag.ucse.UniqueCrops;
import com.remag.ucse.api.*;
import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.enums.EnumArmorMaterial;
import com.remag.ucse.items.*;
import com.remag.ucse.items.base.*;
import com.remag.ucse.items.curios.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static com.remag.ucse.core.UCTab.addToTab;

public class UCItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UniqueCrops.MOD_ID);

    /**
     * GENERAL
     */
    public static final RegistryObject<Item> BOOK_GUIDE = addToTab(ITEMS.register("book_guide", GuideBookItem::new));
    public static final RegistryObject<Item> BOOK_MULTIBLOCK = addToTab(ITEMS.register("book_multiblock", MultiblockBookItem::new));
    public static final RegistryObject<Item> BOOK_DISCOUNT = addToTab(ITEMS.register("book_discount", () -> new ItemBaseUC(unstackable())));
    public static final RegistryObject<Item> BOOK_UPGRADE = addToTab(ITEMS.register("book_upgrade", () -> new ItemBaseUC(unstackable())));
    public static final RegistryObject<Item> BOOK_EULA = addToTab(ITEMS.register("book_eula", EulaBookItem::new));
    public static final RegistryObject<Item> DIRIGIBLEPLUM = addToTab(ITEMS.register("dirigibleplum", DirigiblePlumItem::new));
    public static final RegistryObject<Item> CINDERLEAF = addToTab(ITEMS.register("cinderleaf", CinderleafItem::new));
    public static final RegistryObject<Item> TIMEDUST = addToTab(ITEMS.register("timedust", ItemBaseUC::new));
    public static final RegistryObject<Item> LILYTWINE = addToTab(ITEMS.register("lilytwine", ItemBaseUC::new));
    public static final RegistryObject<Item> GOLDENRODS = addToTab(ITEMS.register("goldenrods", ItemBaseUC::new));
    public static final RegistryObject<Item> PRENUGGET = addToTab(ITEMS.register("prenugget", ItemBaseUC::new));
    public static final RegistryObject<Item> PREGEM = addToTab(ITEMS.register("pregem", ItemBaseUC::new));
    public static final RegistryObject<Item> SAVAGEESSENCE = addToTab(ITEMS.register("savageessence", ItemBaseUC::new));
    public static final RegistryObject<Item> TIMEMEAL = addToTab(ITEMS.register("timemeal", ItemBaseUC::new));
    public static final RegistryObject<Item> INVISITWINE = addToTab(ITEMS.register("invisitwine", ItemBaseUC::new));
    public static final RegistryObject<Item> INVISIFEATHER = addToTab(ITEMS.register("invisifeather", ItemBaseUC::new));
    public static final RegistryObject<Item> SLIPPERGLASS = addToTab(ITEMS.register("slipperglass", ItemBaseUC::new));
    public static final RegistryObject<Item> WEEPINGTEAR = addToTab(ITEMS.register("weepingtear", ItemBaseUC::new));
    public static final RegistryObject<Item> WEEPINGEYE = addToTab(ITEMS.register("weepingeye", WeepingEyeItem::new));
    public static final RegistryObject<Item> MILLENNIUMEYE = addToTab(ITEMS.register("millenniumeye", ItemBaseUC::new));
    public static final RegistryObject<Item> EGGUPGRADE = addToTab(ITEMS.register("eggupgrade", EggUpgradeItem::new));
    public static final RegistryObject<Item> EASYBADGE = addToTab(ITEMS.register("easybadge", EasyBadgeItem::new));
    public static final RegistryObject<Item> DOGRESIDUE = addToTab(ITEMS.register("dogresidue", DogResidueItem::new));
    public static final RegistryObject<Item> ABSTRACT = addToTab(ITEMS.register("abstract", ItemBaseUC::new));
    public static final RegistryObject<Item> LEGALSTUFF = addToTab(ITEMS.register("legalstuff", ItemBaseUC::new));
    public static final RegistryObject<Item> PIXELS = addToTab(ITEMS.register("pixels", ItemBaseUC::new));
    public static final RegistryObject<Item> ESCAPEROPE = addToTab(ITEMS.register("escaperope", EscapeRopeItem::new));
    public static final RegistryObject<Item> CUBEYTHINGY = addToTab(ITEMS.register("cubeythingy", ItemBaseUC::new));
    public static final RegistryObject<Item> FERROMAGNETICIRON = addToTab(ITEMS.register("ferromagnetic_iron", ItemBaseUC::new));
    public static final RegistryObject<Item> SPIRITBAIT = addToTab(ITEMS.register("spirit_bait", ItemBaseUC::new));
    public static final RegistryObject<Item> ENDERSNOOKER = addToTab(ITEMS.register("endersnooker", EnderSnookerItem::new));
    public static final RegistryObject<Item> HANDMIRROR = addToTab(ITEMS.register("handmirror", HandMirrorItem::new));
    public static final RegistryObject<Item> BATSTAFF = addToTab(ITEMS.register("batstaff", StaffBatItem::new));
    public static final RegistryObject<Item> PHANTOMSTAFF = addToTab(ITEMS.register("phantomstaff", StaffPhantomItem::new));
    public static final RegistryObject<Item> BEAN_BATTERY = addToTab(ITEMS.register("bean_battery", BeanBatteryItem::new));
    public static final RegistryObject<Item> WILDWOOD_STAFF = addToTab(ITEMS.register("wildwood_staff", StaffWildwoodItem::new));
    public static final RegistryObject<Item> VAMPIRIC_OINTMENT = addToTab(ITEMS.register("vampiric_ointment", VampiricOintmentItem::new));
    public static final RegistryObject<Item> STEEL_DONUT = addToTab(ITEMS.register("steel_donut", ItemBaseUC::new));
    public static final RegistryObject<Item> ANKH = addToTab(ITEMS.register("ankh", AnkhItem::new));
    public static final RegistryObject<Item> GOODIE_BAG = addToTab(ITEMS.register("goodie_bag", GoodieBagItem::new));
    public static final RegistryObject<Item> EMERADIC_DIAMOND = addToTab(ITEMS.register("emeradic_diamond", EmeradicDiamondItem::new));
    public static final RegistryObject<Item> USELESS_LUMP = addToTab(ITEMS.register("useless_lump", ItemBaseUC::new));
    public static final RegistryObject<Item> DIAMONDS = addToTab(ITEMS.register("diamonds", DiamondBunchItem::new));
    public static final RegistryObject<Item> PIXEL_BRUSH = addToTab(ITEMS.register("pixel_brush", PixelBrushItem::new));
    public static final RegistryObject<Item> RUBIKS_CUBE = addToTab(ITEMS.register("rubiks_cube", RubiksCubeItem::new));
    public static final RegistryObject<Item> BOILED_MILK = addToTab(ITEMS.register("boiled_milk", ItemBaseUC::new));
    public static final RegistryObject<Item> ITEM_MAGNET = addToTab(ITEMS.register("item_magnet", MagnetItem::new));
    public static final RegistryObject<Item> IMPREGNATED_LEATHER = addToTab(ITEMS.register("impregnated_leather", ImpregnatedLeatherItem::new));
    public static final RegistryObject<Item> ENCHANTED_LEATHER = addToTab(ITEMS.register("enchanted_leather", EnchantedLeatherItem::new));
    public static final RegistryObject<Item> ZOMBIE_SLURRY = addToTab(ITEMS.register("zombie_slurry", ItemBaseUC::new));

    /**
     * FOOD & POTIONS
     */
    public static final RegistryObject<Item> LARGE_PLUM = registerFood("large_plum", UCFoods.LARGE_PLUM);
    public static final RegistryObject<Item> TERIYAKI = registerFood("teriyaki", UCFoods.TERIYAKI);
    public static final RegistryObject<Item> STEVE_HEART = registerFood("steveheart", UCFoods.STEVE_HEART);
    public static final RegistryObject<Item> GOLDEN_BREAD = registerFood("golden_bread", UCFoods.GOLDEN_BREAD);
    public static final RegistryObject<Item> DIET_PILLS = registerFood("diet_pills", UCFoods.DIET_PILLS);
    public static final RegistryObject<Item> UNCOOKEDWAFFLE = register("uncookedwaffle", ItemBaseUC::new);
    public static final RegistryObject<Item> WAFFLE = registerFood("waffle", UCFoods.WAFFLE);
    public static final RegistryObject<Item> YOGURT = registerFood("yogurt", UCFoods.YOGURT);
    public static final RegistryObject<Item> EGGNOG = registerFood("eggnog", UCFoods.EGGNOG);
    public static final RegistryObject<Item> POTION_REVERSE = addToTab(ITEMS.register("potionreverse", () -> new ItemBaseUC(unstackable().food(UCFoods.REVERSE_POTION).craftRemainder(Items.GLASS_BOTTLE))));
    public static final RegistryObject<Item> POTION_ENNUI = addToTab(ITEMS.register("potionennui", () -> new ItemBaseUC(unstackable().food(UCFoods.ENNUI_POTION).craftRemainder(Items.GLASS_BOTTLE))));
    public static final RegistryObject<Item> POTION_IGNORANCE = addToTab(ITEMS.register("potionignorance", () -> new ItemBaseUC(unstackable().food(UCFoods.IGNORANCE_POTION).craftRemainder(Items.GLASS_BOTTLE))));
    public static final RegistryObject<Item> POTION_ZOMBIFICATION = addToTab(ITEMS.register("potionzombification", () -> new ItemBaseUC(unstackable().food(UCFoods.ZOMBIFICATION_POTION).craftRemainder(Items.GLASS_BOTTLE))));

    /**
     * GEAR
     */
    public static final RegistryObject<Item> GLASSES_3D = addToTab(ITEMS.register("glasses_3d",  Glasses3DItem::new));
    public static final RegistryObject<Item> GLASSES_PIXELS = addToTab(ITEMS.register("glasses_pixels", GlassesPixelItem::new));
    public static final RegistryObject<Item> PONCHO = addToTab(ITEMS.register("poncho", PonchoItem::new));
    public static final RegistryObject<Item> GLASS_SLIPPERS = addToTab(ITEMS.register("slippers", () -> new ItemArmorUC(EnumArmorMaterial.SLIPPERS, ArmorItem.Type.BOOTS)));
    public static final RegistryObject<Item> THUNDERPANTZ = addToTab(ITEMS.register("thunderpantz", ThunderpantzItem::new));
    public static final RegistryObject<Item> CACTUS_HELM = addToTab(ITEMS.register("cactus_helm", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, ArmorItem.Type.HELMET)));
    public static final RegistryObject<Item> CACTUS_CHESTPLATE = addToTab(ITEMS.register("cactus_plate", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, ArmorItem.Type.CHESTPLATE)));
    public static final RegistryObject<Item> CACTUS_LEGGINGS = addToTab(ITEMS.register("cactus_leggings", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, ArmorItem.Type.LEGGINGS)));
    public static final RegistryObject<Item> CACTUS_BOOTS = addToTab(ITEMS.register("cactus_boots", () -> new ItemArmorUC(EnumArmorMaterial.CACTUS, ArmorItem.Type.BOOTS)));
    public static final RegistryObject<Item> SEVEN_LEAGUE_BOOTS = addToTab(ITEMS.register("seven_league_boots", LeagueBootsItem::new));
    public static final RegistryObject<Item> PRECISION_PICK = addToTab(ITEMS.register("precision_pick", PrecisionPickaxeItem::new));
    public static final RegistryObject<Item> PRECISION_AXE = addToTab(ITEMS.register("precision_axe", PrecisionAxeItem::new));
    public static final RegistryObject<Item> PRECISION_SHOVEL = addToTab(ITEMS.register("precision_shovel", PrecisionShovelItem::new));
    public static final RegistryObject<Item> PRECISION_SWORD = addToTab(ITEMS.register("precision_sword", PrecisionSwordItem::new));
    public static final RegistryObject<Item> PRECISION_HAMMER = addToTab(ITEMS.register("precision_hammer", PrecisionHammerItem::new));
    public static final RegistryObject<Item> IMPACT_SHIELD = addToTab(ITEMS.register("impact_shield", ImpactShieldItem::new));
    public static final RegistryObject<Item> BRASS_KNUCKLES = addToTab(ITEMS.register("brass_knuckles", BrassKnucklesItem::new));
    public static final RegistryObject<Item> ANCIENT_BOW = addToTab(ITEMS.register("oldbow", OldBow::new));

    /**
     * EMBLEMS
     */
    public static final RegistryObject<Item> EMBLEM_BLACKSMITH = addToTab(ITEMS.register("emblem_blacksmith", EmblemBlacksmith::new));
    public static final RegistryObject<Item> EMBLEM_BOOKWORM = addToTab(ITEMS.register("emblem_bookworm", EmblemBookworm::new));
    public static final RegistryObject<Item> EMBLEM_DEFENSE = addToTab(ITEMS.register("emblem_defense", EmblemDefense::new));
    public static final RegistryObject<Item> EMBLEM_FOOD = addToTab(ITEMS.register("emblem_food", EmblemFood::new));
    public static final RegistryObject<Item> EMBLEM_IRONSTOMACH = addToTab(ITEMS.register("emblem_ironstomach", EmblemIronStomach::new));
    public static final RegistryObject<Item> EMBLEM_LEAF = addToTab(ITEMS.register("emblem_leaf", EmblemLeaf::new));
    public static final RegistryObject<Item> EMBLEM_MELEE = addToTab(ITEMS.register("emblem_melee", EmblemMelee::new));
    public static final RegistryObject<Item> EMBLEM_PACIFISM = addToTab(ITEMS.register("emblem_pacifism", EmblemPacifism::new));
    public static final RegistryObject<Item> EMBLEM_POWERFIST = addToTab(ITEMS.register("emblem_powerfist", EmblemPowerfist::new));
    public static final RegistryObject<Item> EMBLEM_RAINBOW = addToTab(ITEMS.register("emblem_rainbow", EmblemRainbow::new));
    public static final RegistryObject<Item> EMBLEM_SCARAB = addToTab(ITEMS.register("emblem_scarab", EmblemScarab::new));
    public static final RegistryObject<Item> EMBLEM_TRANSFORMATION = addToTab(ITEMS.register("emblem_transformation", EmblemTransformation::new));
    public static final RegistryObject<Item> EMBLEM_WEIGHT = addToTab(ITEMS.register("emblem_weight", EmblemWeight::new));

    /**
     * MUSIC DISCS
     */
    public static final RegistryObject<Item> RECORD_FARAWAY = addToTab(ITEMS.register("record_faraway", () -> new ItemRecordUC(UCSounds.FAR_AWAY)));
    public static final RegistryObject<Item> RECORD_NEONSIGNS = addToTab(ITEMS.register("record_neonsigns", () -> new ItemRecordUC(UCSounds.NEON_SIGNS)));
    public static final RegistryObject<Item> RECORD_SIMPLY = addToTab(ITEMS.register("record_simply", () -> new ItemRecordUC(UCSounds.SIMPLY)));
    public static final RegistryObject<Item> RECORD_TAXI = addToTab(ITEMS.register("record_taxi", () -> new ItemRecordUC(UCSounds.TAXI)));

    /**
     * SEEDS
     */
    public static final RegistryObject<BlockItem> ABSTRACT_SEED = registerSeed("abstract", UCBlocks.ABSTRACT_CROP);
    public static final RegistryObject<BlockItem> ADVENTUS_SEED = registerSeed("adventus", UCBlocks.ADVENTUS_CROP);
    public static final RegistryObject<BlockItem> ARTISIA_SEED = registerSeed("artisia", UCBlocks.ARTISIA_CROP);
    public static final RegistryObject<BlockItem> BLESSED_SEED = registerSeed("blessed", UCBlocks.HOLY_CROP);
    public static final RegistryObject<BlockItem> CINDERBELLA_SEED = registerSeed("cinderbella", UCBlocks.CINDERBELLA_CROP);
    public static final RegistryObject<BlockItem> COLLIS_SEED = registerSeed("collis", UCBlocks.COLLIS_CROP);
    public static final RegistryObject<BlockItem> COBBLONIA_SEED = registerSeed("cobblonia", UCBlocks.COBBLONIA_CROP);
    public static final RegistryObject<BlockItem> DEVILSNARE_SEED = registerSeed("devilsnare", UCBlocks.DEVILSNARE_CROP);
    public static final RegistryObject<BlockItem> DIRIGIBLE_SEED = registerSeed("dirigible", UCBlocks.DIRIGIBLE_CROP);
    public static final RegistryObject<BlockItem> DONUTSTEEL_SEED = registerSeed("donutsteel", UCBlocks.DONUTSTEEL_CROP);
    public static final RegistryObject<BlockItem> DYEIUS_SEED = registerSeed("dyeius", UCBlocks.DYEIUS_CROP);
    public static final RegistryObject<BlockItem> ENDERLILY_SEED = registerSeed("enderlily", UCBlocks.ENDERLILY_CROP);
    public static final RegistryObject<BlockItem> EULA_SEED = registerSeed("eula", UCBlocks.EULA_CROP);
    public static final RegistryObject<BlockItem> FEROXIA_SEED = registerSeed("feroxia", UCBlocks.FEROXIA_CROP);
    public static final RegistryObject<BlockItem> HEXIS_SEED = registerSeed("hexis", UCBlocks.HEXIS_CROP);
    public static final RegistryObject<BlockItem> IMPERIA_SEED = registerSeed("imperia", UCBlocks.IMPERIA_CROP);
    public static final RegistryObject<BlockItem> INDUSTRIA_SEED = registerSeed("industria", UCBlocks.INDUSTRIA_CROP);
    public static final RegistryObject<BlockItem> INSTABILIS_SEED = registerSeed("instabilis", UCBlocks.INSTABILIS_CROP);
    public static final RegistryObject<BlockItem> INVISIBILIA_SEED = registerSeed("invisibilia", UCBlocks.INVISIBILIA_CROP);
    public static final RegistryObject<BlockItem> KNOWLEDGE_SEED = registerSeed("knowledge", UCBlocks.KNOWLEDGE_CROP);
    public static final RegistryObject<BlockItem> LACUSIA_SEED = registerSeed("lacusia", UCBlocks.LACUSIA_CROP);
    public static final RegistryObject<BlockItem> MAGNES_SEED = registerSeed("magnes", UCBlocks.MAGNES_CROP);
    public static final RegistryObject<BlockItem> MALLEATORIS_SEED = registerSeed("malleatoris", UCBlocks.MALLEATORIS_CROP);
    public static final RegistryObject<BlockItem> MARYJANE_SEED = registerSeed("maryjane", UCBlocks.MARYJANE_CROP);
    public static final RegistryObject<BlockItem> MERLINIA_SEED = registerSeed("merlinia", UCBlocks.MERLINIA_CROP);
    public static final RegistryObject<BlockItem> MILLENNIUM_SEED = registerSeed("millennium", UCBlocks.MILLENNIUM_CROP);
    public static final RegistryObject<BlockItem> MUSICA_SEED = registerSeed("musica", UCBlocks.MUSICA_CROP);
    public static final RegistryObject<BlockItem> NORMAL_SEED = registerSeed("normal", UCBlocks.NORMAL_CROP);
    public static final RegistryObject<BlockItem> PETRAMIA_SEED = registerSeed("petramia", UCBlocks.PETRAMIA_CROP);
    public static final RegistryObject<BlockItem> PIXELSIUS_SEED = registerSeed("pixelsius", UCBlocks.PIXELSIUS_CROP);
    public static final RegistryObject<BlockItem> PRECISION_SEED = registerSeed("precision", UCBlocks.PRECISION_CROP);
    public static final RegistryObject<BlockItem> QUARRY_SEED = registerSeed("quarry", UCBlocks.QUARRY_CROP);
    public static final RegistryObject<BlockItem> SUCCO_SEED = registerSeed("succo", UCBlocks.SUCCO_CROP);
    public static final RegistryObject<BlockItem> WAFFLONIA_SEED = registerSeed("wafflonia", UCBlocks.WAFFLONIA_CROP);
    public static final RegistryObject<BlockItem> WEEPINGBELLS_SEED = registerSeed("weepingbells", UCBlocks.WEEPINGBELLS_CROP);

    /**
     * DYED BONEMEALS
     */
    public static final RegistryObject<Item> WHITE_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.white", DyedBonemealItem::new));
    public static final RegistryObject<Item> ORANGE_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.orange", DyedBonemealItem::new));
    public static final RegistryObject<Item> MAGENTA_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.magenta", DyedBonemealItem::new));
    public static final RegistryObject<Item> LIGHTBLUE_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.light_blue", DyedBonemealItem::new));
    public static final RegistryObject<Item> YELLOW_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.yellow", DyedBonemealItem::new));
    public static final RegistryObject<Item> LIME_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.lime", DyedBonemealItem::new));
    public static final RegistryObject<Item> PINK_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.pink", DyedBonemealItem::new));
    public static final RegistryObject<Item> GRAY_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.gray", DyedBonemealItem::new));
    public static final RegistryObject<Item> SILVER_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.silver", DyedBonemealItem::new));
    public static final RegistryObject<Item> CYAN_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.cyan", DyedBonemealItem::new));
    public static final RegistryObject<Item> PURPLE_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.purple", DyedBonemealItem::new));
    public static final RegistryObject<Item> BLUE_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.blue", DyedBonemealItem::new));
    public static final RegistryObject<Item> BROWN_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.brown", DyedBonemealItem::new));
    public static final RegistryObject<Item> GREEN_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.green", DyedBonemealItem::new));
    public static final RegistryObject<Item> RED_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.red", DyedBonemealItem::new));
    public static final RegistryObject<Item> BLACK_BONEMEAL = addToTab(ITEMS.register("dyedbonemeal.black", DyedBonemealItem::new));

    /**
     * DUMMIES
     */
    public static final RegistryObject<Item> DUMMY_ARTISIA = addToTab(ITEMS.register("dummy_artisia", ItemDummyUC::new));
    public static final RegistryObject<Item> DUMMY_HEATER = addToTab(ITEMS.register("dummy_heater", ItemDummyUC::new));
    public static final RegistryObject<Item> DUMMY_FASCINO = addToTab(ITEMS.register("dummy_fascino", ItemRenderUC::new));

    public static <I extends Item> RegistryObject<I> register(String name, Supplier<I> supplier) {

        return addToTab(ITEMS.register(name, supplier));
    }

    public static RegistryObject<BlockItem> register(String name, RegistryObject<Block> block) {

        return addToTab(ITEMS.register(name, () -> new BlockItem(block.get(), defaultBuilder())));
    }

    public static RegistryObject<Item> registerFood(String name, FoodProperties food) {

        return addToTab(ITEMS.register(name, () -> new Item(new Item.Properties().food(food))));
    }

    public static RegistryObject<BlockItem> registerSeed(String name, RegistryObject<BaseCropsBlock> crop) {

        return addToTab(ITEMS.register("seed" + name, () -> new ItemSeedsUC(crop.get())));
    }

    public static Item.Properties noTab() {

        return new Item.Properties();
    }

    public static Item.Properties defaultBuilder() {

        return new Item.Properties();
    }

    public static Item.Properties unstackable() {

        return defaultBuilder().stacksTo(1);
    }

    /** custom recipes start here */
    public static final RecipeType<IArtisiaRecipe> ARTISIA_TYPE = new ModRecipeType<>();
    public static final RecipeType<IHourglassRecipe> HOURGLASS_TYPE = new ModRecipeType<>();
    public static final RecipeType<IEnchanterRecipe> ENCHANTER_TYPE = new ModRecipeType<>();
    public static final RecipeType<IHeaterRecipe> HEATER_TYPE = new ModRecipeType<>();
    public static final RecipeType<IMultiblockRecipe> MULTIBLOCK_TYPE = new ModRecipeType<>();

    public static void registerItemsButNotReally(RegisterEvent event) {

        ResourceLocation id;

        id = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "artisia");
        ForgeRegistries.RECIPE_TYPES.register(id, ARTISIA_TYPE);

        id = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "hourglass");
        ForgeRegistries.RECIPE_TYPES.register(id, HOURGLASS_TYPE);

        id = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "enchanter");
        ForgeRegistries.RECIPE_TYPES.register(id, ENCHANTER_TYPE);

        id = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "heater");
        ForgeRegistries.RECIPE_TYPES.register(id, HEATER_TYPE);

        id = ResourceLocation.fromNamespaceAndPath(UniqueCrops.MOD_ID, "multiblock");
        ForgeRegistries.RECIPE_TYPES.register(id, MULTIBLOCK_TYPE);
    }

    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {

        @Override
        public String toString() {

            return Objects.requireNonNull(ForgeRegistries.RECIPE_TYPES.getKey(this)).toString();
        }
    }
}
