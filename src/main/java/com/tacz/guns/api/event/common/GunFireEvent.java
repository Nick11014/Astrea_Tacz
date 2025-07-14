package com.tacz.guns.api.event.common;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.LogicalSide;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â¸Ã…Â½ {@link GunShootEvent}ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â³ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã‚Â¤Ã…Â¡ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ Burst ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â  {@link GunShootEvent} ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡
 */
public class GunFireEvent extends Event implements KubeJSGunEventPoster<GunFireEvent>{
    private final LivingEntity shooter;
    private final ItemStack gunItemStack;
    private final LogicalSide logicalSide;

    public GunFireEvent(LivingEntity shooter, ItemStack gunItemStack, LogicalSide side) {
        this.shooter = shooter;
        this.gunItemStack = gunItemStack;
        this.logicalSide = side;
        postEventToKubeJS(this);
    }

    // @Override - Removido: isCancelable() nÃƒÆ’Ã‚Â£o ÃƒÆ’Ã‚Â© um override em NeoForge 1.21.1
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































































