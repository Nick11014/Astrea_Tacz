package com.tacz.guns.api.event.common;

// TODO: Re-habilitar quando AttachmentCacheProperty for habilitado
// import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;

/**
 * 缓存配件属性修改值时触发的事件
 * <p>
 * 如果有其他模组想要添加自定义的配件属性修改值，可以捕获此事件
 * 
 * TODO: Implementação mínima até AttachmentCacheProperty e KubeJSGunEventPoster serem habilitados
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
