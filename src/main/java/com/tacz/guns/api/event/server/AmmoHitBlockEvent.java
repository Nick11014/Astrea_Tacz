package com.tacz.guns.api.event.server;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.Event;

/**
 * 子弹击中方块时触发的事件，目前仅在服务端触发
 * (REQUER REVISITA) - Implementação simplificada sem EntityKineticBullet e KubeJSGunEventPoster
 */
public class AmmoHitBlockEvent extends Event {
    private final Level level;
    private final BlockHitResult hitResult;
    private final BlockState state;
    // TODO: Re-adicionar EntityKineticBullet quando disponível
    // private final EntityKineticBullet ammo;

    public AmmoHitBlockEvent(Level level, BlockHitResult hitResult, BlockState state) {
        this.level = level;
        this.hitResult = hitResult;
        this.state = state;
        // TODO: Re-adicionar KubeJS integration quando disponível
        // postServerEventToKubeJS(this);
    }

    // Cancelable event - método isCancelable() não é mais @Override no NeoForge 1.21.1
    public boolean isCancelable() {
        return true;
    }

    public Level getLevel() {
        return level;
    }

    public BlockHitResult getHitResult() {
        return hitResult;
    }

    public BlockState getState() {
        return state;
    }

    // TODO: Re-adicionar quando EntityKineticBullet estiver disponível
    // public EntityKineticBullet getAmmo() {
    //     return ammo;
    // }
}
