package com.remag.ucse.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class BaseSuperCropsBlock extends Block {

    public BaseSuperCropsBlock() {

        super(Properties.of().noCollission().randomTicks().strength(5.0F, 1000.0F).sound(SoundType.CROP).mapColor(MapColor.GRASS));
    }

    public BaseSuperCropsBlock(Properties prop) {

        super(prop);
    }
}
