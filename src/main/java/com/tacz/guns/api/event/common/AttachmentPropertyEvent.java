package com.tacz.guns.api.event.common;

// TODO: Re-habilitar quando AttachmentCacheProperty for habilitado
// import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

/**
 * ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
 * <p>
 * ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã‚Â³ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â·Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
 * 
 * TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima atÃƒÆ’Ã‚Â© AttachmentCacheProperty e KubeJSGunEventPoster serem habilitados
 */
public class AttachmentPropertyEvent extends Event /* implements KubeJSGunEventPoster<AttachmentPropertyEvent> */ {
    private final ItemStack gunItem;
    // TODO: Re-habilitar quando AttachmentCacheProperty for habilitado
    // private final AttachmentCacheProperty cacheProperty;
    private final Object cacheProperty; // Placeholder

    // TODO: Atualizar construtor quando AttachmentCacheProperty for habilitado
    public AttachmentPropertyEvent(ItemStack gunItem, Object attachmentProperty) {
        this.gunItem = gunItem;
        this.cacheProperty = attachmentProperty;
    }

    public ItemStack getGunItem() {
        return gunItem;
    }

    // TODO: Re-habilitar quando AttachmentCacheProperty for habilitado
    public Object getCacheProperty() {
        return cacheProperty;
    }
}































































