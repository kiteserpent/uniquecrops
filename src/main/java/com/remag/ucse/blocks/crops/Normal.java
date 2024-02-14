package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.core.UCUtils;
import com.remag.ucse.init.UCItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Random;

public class Normal extends BaseCropsBlock {

    private boolean clickHarvest = true;

    static final Item[] DROPS = new Item[] { Items.WHEAT, Items.BEETROOT, Items.CARROT, Items.POTATO };

    public Normal() {

        super(() -> Items.WHEAT, UCItems.NORMAL_SEED);
    }

    @Override
    public Item getCrop() {

        return UCUtils.selectRandom(new Random(), DROPS);
    }

    public boolean isClickHarvest() {

        return this.clickHarvest;
    }
}
