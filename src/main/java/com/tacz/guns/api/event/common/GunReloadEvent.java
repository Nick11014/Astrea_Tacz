package com.tacz.guns.api.event.common;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.LogicalSide;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
 */

public class GunReloadEvent extends Event implements KubeJSGunEventPoster<GunReloadEvent> {
    private final LivingEntity entity;
    private final ItemStack gunItemStack;
    private final LogicalSide logicalSide;
    private boolean cancelled = false;

    public GunReloadEvent(LivingEntity entity, ItemStack gunItemStack, LogicalSide side) {
        this.entity = entity;
        this.gunItemStack = gunItemStack;
        this.logicalSide = side;
        postEventToKubeJS(this);
    }

    public boolean isCancelable() {
        return true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ItemStack getGunItemStack() {
        return gunItemStack;
    }

    public LogicalSide getLogicalSide() {
        return logicalSide;
    }
}































































