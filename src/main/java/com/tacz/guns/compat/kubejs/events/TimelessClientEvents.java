package com.tacz.guns.compat.kubejs.events;

import net.neoforged.bus.api.Event;

public class TimelessClientEvents {
    // TODO: [MIGRAÇÃO] Reimplementar quando KubeJS for completamente migrado para 1.21.1
    // A API do KubeJS mudou significativamente entre versões
    
    public static final TimelessClientEvents INSTANCE = new TimelessClientEvents();

    public static void postKubeJSEvent(Event event) {
        // TODO: Implementar quando a API do KubeJS estiver estável
        // Por enquanto, apenas um stub para evitar erros de compilação
    }

    public static class ClientEventJS {
        private final Event forgeEvent;

        public ClientEventJS(Event forgeEvent) {
            this.forgeEvent = forgeEvent;
        }

        public Event getForgeEvent() {
            return forgeEvent;
        }
    }
}
