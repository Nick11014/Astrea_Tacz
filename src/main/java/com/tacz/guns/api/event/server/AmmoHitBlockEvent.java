package com.tacz.guns.api.event.server;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.Event;

/**
 * ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬Ëœ
 * (REQUER REVISITA) - ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o simplificada sem EntityKineticBullet e KubeJSGunEventPoster
 */
public class AmmoHitBlockEvent extends Event {
    private final Level level;
    private final BlockHitResult hitResult;
    private final BlockState state;
    // TODO: Re-adicionar EntityKineticBullet quando disponÃƒÆ’Ã‚Â­vel
    // private final EntityKineticBullet ammo;

    public AmmoHitBlockEvent(Level level, BlockHitResult hitResult, BlockState state) {
        this.level = level;
        this.hitResult = hitResult;
        this.state = state;
        // TODO: Re-adicionar KubeJS integration quando disponÃƒÆ’Ã‚Â­vel
        // postServerEventToKubeJS(this);
    }

    // Cancelable event - mÃƒÆ’Ã‚Â©todo isCancelable() nÃƒÆ’Ã‚Â£o ÃƒÆ’Ã‚Â© mais @Override no NeoForge 1.21.1
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

    // TODO: Re-adicionar quando EntityKineticBullet estiver disponÃƒÆ’Ã‚Â­vel
    // public EntityKineticBullet getAmmo() {
    //     return ammo;
    // }
}































































