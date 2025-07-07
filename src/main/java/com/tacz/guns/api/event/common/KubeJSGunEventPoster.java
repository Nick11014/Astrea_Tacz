package com.tacz.guns.api.event.common;

// TODO: Re-enable when KubeJS integration classes are available
// import com.tacz.guns.compat.kubejs.events.TimelessClientEvents;
// import com.tacz.guns.compat.kubejs.events.TimelessCommonEvents;
// import com.tacz.guns.compat.kubejs.events.TimelessServerEvents;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.ModList;

/**
 * Interface para integração com KubeJS baseada no padrão SuperbWarfare 1.21.1
 * Implementação mínima - classes de integração KubeJS desabilitadas temporariamente
 */
public interface KubeJSGunEventPoster<E extends Event> {
    default void postEventToKubeJS(E event) {
        // TODO: Re-enable when KubeJS integration is available
        // if (ModList.get().isLoaded("kubejs")) {
        //     TimelessCommonEvents.INSTANCE.postKubeJSEvent(event);
        // }
        
        // Placeholder - funcionalidade KubeJS desabilitada temporariamente
    }

    //客户端事件应调用此方法
    default void postClientEventToKubeJS(E event) {
        // TODO: Re-enable when KubeJS integration is available
        // if (ModList.get().isLoaded("kubejs")) {
        //     TimelessClientEvents.INSTANCE.postKubeJSEvent(event);
        // }
        
        // Placeholder - funcionalidade KubeJS desabilitada temporariamente
    }

    //服务端事件应调用此方法
    default void postServerEventToKubeJS(E event) {
        // TODO: Re-enable when KubeJS integration is available
        // if (ModList.get().isLoaded("kubejs")) {
        //     TimelessServerEvents.INSTANCE.postKubeJSEvent(event);
        // }
        
        // Placeholder - funcionalidade KubeJS desabilitada temporariamente
    }
}
