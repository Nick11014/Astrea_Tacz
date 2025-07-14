package com.tacz.guns.resource.modifier;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tacz.guns.api.GunProperty;
import com.tacz.guns.api.modifier.CacheValue;
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
    @SuppressWarnings("rawtypes")
    private final Map<String, CacheValue> cacheValues = Maps.newHashMap();
    private final Map<String, List<?>> cacheModifiers = Maps.newHashMap();

    @SuppressWarnings("all")
    public void eval(ItemStack gunItem, GunData gunData) {
        // ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œ
        var modifiers = AttachmentPropertyManager.getModifiers();
        modifiers.forEach((id, value) -> {
            cacheValues.put(id, value.initCache(gunItem, gunData));
            cacheModifiers.put(id, Lists.newArrayList());
        });

        // ÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ modifier
        AttachmentDataUtils.getAllAttachmentData(gunItem, gunData, data -> {
            data.getModifier().forEach((id, value) -> {
                List objects = cacheModifiers.get(id);
                objects.add(value.getValue());
            });
        });

        // ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¦Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
        cacheValues.forEach((id, value) -> {
            List cacheModifier = cacheModifiers.get(id);
            // ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚Âª modifier ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ modifier ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‚Â©Ã‚Âº
            if (cacheModifier == null || cacheModifier.isEmpty()) {
                return;
            }
            modifiers.get(id).eval(cacheModifier, value);
        });

        // ÃƒÂ¦Ã‚Â¸Ã¢â‚¬Â¦ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¿Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã‹Å“Ã‚Â²ÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã‚ÂÃ‚Â ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨
        cacheModifiers.clear();
    }

    @SuppressWarnings("unchecked")
    public <T> T getCache(String id) {
        return (T) cacheValues.get(id).getValue();
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
        cacheValues.get(key.name()).setValue(value);
    }
}































































