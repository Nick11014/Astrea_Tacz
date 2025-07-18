package com.tacz.guns.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class GunSmithTableBlockB extends Block {
    public GunSmithTableBlockB() {
        super(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0F, 3.0F));
    }
}
