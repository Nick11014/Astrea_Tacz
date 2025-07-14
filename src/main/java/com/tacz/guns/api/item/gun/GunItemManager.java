package com.tacz.guns.api.item.gun;

import com.google.common.collect.Maps;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.Map;

public class GunItemManager {
    private static final Map<String, DeferredHolder<Item, ? extends AbstractGunItem>> GUN_ITEM_MAP = Maps.newHashMap();

    /**
     * ÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¨Ã‚Â®Ã‚Â®ÃƒÂ¥Ã…â€œÃ‚Â¨ RegistryEvent.Register<Item> ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ§Ã‚Â§Ã‚Â
     */
    public static void registerGunItem(String name, DeferredHolder<Item, ? extends AbstractGunItem> registryObject) {
        GUN_ITEM_MAP.put(name, registryObject);
    }

    public static DeferredHolder<Item, ? extends AbstractGunItem> getGunItemRegistryObject(String key) {
        return GUN_ITEM_MAP.get(key);
    }

    public static Collection<DeferredHolder<Item, ? extends AbstractGunItem>> getAllGunItems() {
        return GUN_ITEM_MAP.values();
    }
}































































