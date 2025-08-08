package com.remag.ucse.data;

/* public class UCLootProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));

    private final DataGenerator gen;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public UCLootProvider(DataGenerator gen) {

        this.gen = gen;
        functionTable.put(UCBlocks.STALK.get(), (block) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(Blocks.CRAFTING_TABLE)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(StalkBlock.STALKS, "up")))
                ))
        );
        functionTable.put(UCBlocks.ARTISIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.PRECISION_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.KNOWLEDGE_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.DIRIGIBLE_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.MILLENNIUM_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.ENDERLILY_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.COLLIS_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.INVISIBILIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.MARYJANE_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.WEEPINGBELLS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.MUSICA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.EULA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.COBBLONIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.ABSTRACT_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.WAFFLONIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.DEVILSNARE_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.PIXELSIUS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.PETRAMIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.MALLEATORIS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.IMPERIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.LACUSIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.HEXIS_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.INDUSTRIA_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.QUARRY_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.DONUTSTEEL_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.INSTABILIS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.SUCCO_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.ADVENTUS_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.HOLY_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.MAGNES_CROP.get(), UCLootProvider::genCrops);
        functionTable.put(UCBlocks.FEROXIA_CROP.get(), UCLootProvider::genCropsWithBonus);
        functionTable.put(UCBlocks.NORMAL_CROP.get(), (block) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(TagEntry.expandTag(UCItemTagsProvider.NORMAL_DROP)
                        .when(fullyGrown(block)))))
                .withPool(seed(UCItems.NORMAL_SEED.get()))
        );
        functionTable.put(UCBlocks.INVISIBILIA_GLASS.get(), UCLootProvider::genSilk);
        functionTable.put(UCBlocks.FLYWOOD_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.ROSEWOOD_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.RUINEDBRICKS_SLAB.get(), UCLootProvider::genSlab);
        functionTable.put(UCBlocks.RUINEDBRICKSCARVED_SLAB.get(), UCLootProvider::genSlab);
//        functionTable.put(UCBlocks.ADVENTUS_CROP.get(), (block) -> LootTable.lootTable()
//                .withPool(LootPool.lootPool()
//                    .when(fullyGrown(block))
//                    .add(ItemLootEntry.lootTableItem(UCItems.GOODIE_BAG.get())
//                        .apply(SetCount.setCount(ConstantRange.exactly(1)))
//                        .when(null))
//                )
//                .withPool(seed(UCItems.ADVENTUS_SEED.get()))
//        );
        functionTable.put(UCBlocks.DYEIUS_CROP.get(), (block) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(fullyGrown(block))
                        .add(LootItem.lootTableItem(Items.WHITE_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(0, 1500))))
                        .add(LootItem.lootTableItem(Items.ORANGE_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(1501, 3000))))
                        .add(LootItem.lootTableItem(Items.MAGENTA_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(3001, 4500))))
                        .add(LootItem.lootTableItem(Items.LIGHT_BLUE_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(4501, 6000))))
                        .add(LootItem.lootTableItem(Items.YELLOW_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(6001, 7500))))
                        .add(LootItem.lootTableItem(Items.LIME_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(7501, 9000))))
                        .add(LootItem.lootTableItem(Items.PINK_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(9001, 10500))))
                        .add(LootItem.lootTableItem(Items.GRAY_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(10501, 12000))))
                        .add(LootItem.lootTableItem(Items.LIGHT_GRAY_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(12001, 13500))))
                        .add(LootItem.lootTableItem(Items.CYAN_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(13501, 15000))))
                        .add(LootItem.lootTableItem(Items.PURPLE_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(15001, 16500))))
                        .add(LootItem.lootTableItem(Items.BLUE_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(16501, 18000))))
                        .add(LootItem.lootTableItem(Items.BROWN_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(18001, 19500))))
                        .add(LootItem.lootTableItem(Items.GREEN_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(19501, 21000))))
                        .add(LootItem.lootTableItem(Items.RED_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(21001, 22500))))
                        .add(LootItem.lootTableItem(Items.BLACK_DYE)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                                .when(new TimeCheckBuilder(24000L, MinMaxBounds.Ints.between(22501, 23999))))
        )
        .withPool(seed(UCItems.DYEIUS_SEED.get())));
    }

    private static LootTable.Builder genCrops(Block block) {

        LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
        BaseCropsBlock crop = (BaseCropsBlock)block;

        LootPool.Builder cropPool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(crop.getCrop()))
                .when(condition);
        LootPool.Builder seedPool = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(crop.getSeed()))
                .when(ExplosionCondition.survivesExplosion());

        return LootTable.lootTable().withPool(cropPool).withPool(seedPool);
    }

    private static LootTable.Builder genCropsWithBonus(Block block) {

        LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
        BaseCropsBlock crop = (BaseCropsBlock)block;

        LootTable.Builder builder = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(crop.getSeed())
                        .when(condition)
                        .otherwise(LootItem.lootTableItem(crop.getCrop()))))
                .withPool(LootPool.lootPool().when(condition)
                 .add(LootItem.lootTableItem(crop.getCrop())
                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1)))
                        .when(condition));

        return builder;
    }

    private static LootTable.Builder droppingAndBonusWhen(Block block, Item itemConditional, Item setValueBonus, LootItemCondition.Builder conditionBuilder) {

        return LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(itemConditional).when(conditionBuilder).otherwise(LootItem.lootTableItem(setValueBonus)))).withPool(LootPool.lootPool().when(conditionBuilder).add(LootItem.lootTableItem(setValueBonus).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1))));
    }

    private static LootTable.Builder genSlab(Block block) {

        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block)
                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                .apply(ApplyExplosionDecay.explosionDecay());
        return LootTable.lootTable().withPool(LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry));
    }

    private static LootTable.Builder genRegular(Block block) {

        LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(block);
        LootPool.Builder pool = LootPool.lootPool().name("main").setRolls(ConstantValue.exactly(1)).add(entry)
                .when(ExplosionCondition.survivesExplosion());
        return LootTable.lootTable().withPool(pool);
    }

    private static LootTable.Builder genSilk(Block block) {

        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block)));
    }

    private static LootPool.Builder seed(Item item) {

        return LootPool.lootPool().add(LootItem.lootTableItem(item).apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 3))).when(ExplosionCondition.survivesExplosion());
    }

    private static LootItemCondition.Builder fullyGrown(Block block) {

        return LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BaseCropsBlock.AGE, ((BaseCropsBlock)block).getHarvestAge()));
    }

    @Override
    public String getName() {

        return "Unique crops block loot tables";
    }

    private static class TimeCheckBuilder implements LootItemCondition.Builder {

        private final Long mod;
        private final MinMaxBounds range;

        public TimeCheckBuilder(Long mod, MinMaxBounds range) {

            this.mod = mod;
            this.range = range;
        }

        @Override
        public LootItemCondition build() {

            return this.create(this.mod, this.range);
        }

        private TimeCheck create(Long mod, MinMaxBounds range) {

            try {
                Constructor<TimeCheck> ctor = TimeCheck.class.getDeclaredConstructor(Long.class, MinMaxBounds.class);
                ctor.setAccessible(true);
                TimeCheck check = ctor.newInstance(mod, range);
                ctor.setAccessible(false);
                return check;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
} */
