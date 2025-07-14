package com.tacz.guns.api.event.common;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.LogicalSide;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬Ëœ
 */
public class GunMeleeEvent extends Event implements KubeJSGunEventPoster<GunMeleeEvent>{
    private final LivingEntity shooter;
    private final ItemStack gunItemStack;
    private final LogicalSide logicalSide;

    public GunMeleeEvent(LivingEntity shooter, ItemStack gunItemStack, LogicalSide side) {
        this.shooter = shooter;
        this.gunItemStack = gunItemStack;
        this.logicalSide = side;
        postEventToKubeJS(this);
    }

    // Cancelable event - mÃƒÆ’Ã‚Â©todo isCancelable() nÃƒÆ’Ã‚Â£o ÃƒÆ’Ã‚Â© mais @Override no NeoForge 1.21.1
    public boolean isCancelable() {
        return true;
    }

    public LivingEntity getShooter() {
        return shooter;
    }

    public ItemStack getGunItemStack() {
        return gunItemStack;
    }

    public LogicalSide getLogicalSide() {
        return logicalSide;
    }
}































































