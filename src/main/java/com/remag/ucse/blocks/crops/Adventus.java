package com.remag.ucse.blocks.crops;

import com.remag.ucse.blocks.BaseCropsBlock;
import com.remag.ucse.init.UCItems;

public class Adventus extends BaseCropsBlock {

    public Adventus() {

        super(UCItems.GOODIE_BAG, UCItems.ADVENTUS_SEED);
        setClickHarvest(false);
        setIncludeSeed(false);
    }
}
