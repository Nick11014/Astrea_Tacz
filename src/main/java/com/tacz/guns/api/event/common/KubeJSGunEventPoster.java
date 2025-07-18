package com.tacz.guns.api.event.common;

import com.tacz.guns.compat.kubejs.events.TimelessClientEvents;
import com.tacz.guns.compat.kubejs.events.TimelessCommonEvents;
import com.tacz.guns.compat.kubejs.events.TimelessServerEvents;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.ModList;

/**
 * Interface para integração com KubeJS baseada no padrão SuperbWarfare 1.21.1
 * Implementação mínima - classes de integração KubeJS desabilitadas temporariamente
 */
public interface KubeJSGunEventPoster<E extends Event> {
    default void postEventToKubeJS(E event) {
        if (ModList.get().isLoaded("kubejs")) {
            TimelessCommonEvents.INSTANCE.postKubeJSEvent(event);
        }
    }

    default void postClientEventToKubeJS(E event) {
        if (ModList.get().isLoaded("kubejs")) {
            TimelessClientEvents.INSTANCE.postKubeJSEvent(event);
        }
    }

    default void postServerEventToKubeJS(E event) {
        if (ModList.get().isLoaded("kubejs")) {
            TimelessServerEvents.INSTANCE.postKubeJSEvent(event);
        }
    }
}































































