package com.tacz.guns.compat.kubejs.events;

import net.neoforged.bus.api.Event;

public class TimelessServerEvents {
    // TODO: [MIGRAÇÃO] Reimplementar quando KubeJS for completamente migrado para 1.21.1
    // A API do KubeJS mudou significativamente entre versões
    
    public static final TimelessServerEvents INSTANCE = new TimelessServerEvents();

    public static void postKubeJSEvent(Event event) {
        // TODO: Implementar quando a API do KubeJS estiver estável
        // Por enquanto, apenas um stub para evitar erros de compilação
    }
}
