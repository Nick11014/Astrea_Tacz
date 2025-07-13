package com.tacz.guns.event.ammo;

import com.tacz.guns.api.event.server.AmmoHitBlockEvent;
import com.tacz.guns.config.common.AmmoConfig;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
// TODO: [MIGRAÇÃO NeoForge 1.21.1] AbstractGlassBlock foi removido, usar verificação diferente
// import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.StainedGlassPaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber
public class DestroyGlassBlock {
    @SubscribeEvent
    public static void onAmmoHitBlock(AmmoHitBlockEvent event) {
        Level level = event.getLevel();
        BlockState state = event.getState();
        BlockPos pos = event.getHitResult().getBlockPos();
        EntityKineticBullet ammo = event.getAmmo();
        Block stateBlock = state.getBlock();
        NoteBlockInstrument instrument = state.instrument();
        // TODO: [MIGRAÇÃO NeoForge 1.21.1] AbstractGlassBlock removido, usar verificação por instrument
        if (AmmoConfig.DESTROY_GLASS.get() && (instrument.equals(NoteBlockInstrument.HAT) || // Glass blocks
                stateBlock instanceof StainedGlassPaneBlock ||
                (stateBlock instanceof IronBarsBlock && instrument.equals(NoteBlockInstrument.HAT)))) {
            level.destroyBlock(pos, false, ammo.getOwner());
        }
    }
}
