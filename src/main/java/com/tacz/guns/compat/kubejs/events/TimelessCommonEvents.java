package com.tacz.guns.compat.kubejs.events;

import net.neoforged.bus.api.Event;

public class TimelessCommonEvents {
    // TODO: [MIGRAÇÃO] Reimplementar quando KubeJS for completamente migrado para 1.21.1
    // A API do KubeJS mudou significativamente entre versões
    
    public static final TimelessCommonEvents INSTANCE = new TimelessCommonEvents();

    public static void postKubeJSEvent(Event event) {
        // TODO: Implementar quando a API do KubeJS estiver estável
        // Por enquanto, apenas um stub para evitar erros de compilação
    }

    public static class CommonEventJS {
        private final Event forgeEvent;

        public CommonEventJS(Event forgeEvent) {
            this.forgeEvent = forgeEvent;
        }

        public Event getForgeEvent() {
            return forgeEvent;
        }
    }
}
