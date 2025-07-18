package com.tacz.guns.resource.modifier;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tacz.guns.api.GunProperty;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

import static org.jetbrains.annotations.ApiStatus.*;

/**
 * ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã†â€™Ã‚Â½ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ©Ã¢â‚¬Â¡Ã…â€™
 */
public class AttachmentCacheProperty {
    private final Map<String, CacheValue<?>> cacheValues = Maps.newHashMap();
    private final Map<String, List<Object>> cacheModifiers = Maps.newHashMap();

    public void eval(ItemStack gunItem, GunData gunData) {
        var modifiers = AttachmentPropertyManager.getModifiers();
        modifiers.forEach((id, value) -> {
            if (value instanceof IAttachmentModifier<?, ?> modifier) {
                cacheValues.put(id, modifier.initCache(gunItem, gunData, (ItemStack) null));
                cacheModifiers.put(id, Lists.newArrayList());
            }
        });

        AttachmentDataUtils.getAllAttachmentData(gunItem, gunData, data -> {
            data.getModifier().forEach((id, value) -> {
                cacheModifiers.computeIfAbsent(id, k -> Lists.newArrayList()).add(value.getValue());
            });
        });

        cacheValues.forEach((id, value) -> {
            List<Object> cacheModifier = cacheModifiers.get(id);
            if (cacheModifier == null || cacheModifier.isEmpty()) {
                return;
            }
            if (modifiers.get(id) instanceof IAttachmentModifier mod) {
                mod.eval(cacheModifier, value);
            }
        });

        cacheModifiers.clear();
    }

    public <T> T getCache(String id) {
        CacheValue<?> cache = cacheValues.get(id);
        if (cache != null) {
            @SuppressWarnings("unchecked")
            T value = (T) cache.getValue();
            return value;
        }
        return null;
    }

    @Experimental
    public <T> T getCache(GunProperty<T> key) {
        return key.type().cast(cacheValues.get(key.name()).getValue());
    }

    @Experimental
    @SuppressWarnings("unchecked")
    public <T> void setCache(GunProperty<T> key, T value) {
        if (!key.type().isInstance(value)) {
            throw new IllegalArgumentException("Gun cache type mismatch, needs %s, found %s".formatted(key.type().getSimpleName(), value.getClass().getSimpleName()));
        }
        ((CacheValue<T>) cacheValues.get(key.name())).setValue(value);
    }
}































































