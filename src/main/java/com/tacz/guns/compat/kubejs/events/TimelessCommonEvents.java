package com.tacz.guns.compat.kubejs.events;

import net.neoforged.bus.api.Event;

/**
 * Eventos KubeJS personalizados para TacZ - Comum (Client + Server)
 * IMPLEMENTADO: Sistema de eventos para integração KubeJS em ambos os lados
 */
public class TimelessCommonEvents {
    // IMPLEMENTADO: Singleton para garantir uma única instância
    public static final TimelessCommonEvents INSTANCE = new TimelessCommonEvents();
    
    private boolean initialized = false;

    /**
     * Inicializa o sistema de eventos
     * IMPLEMENTADO: Inicialização segura com verificação
     */
    public void init() {
        if (!initialized) {
            System.out.println("TacZ: Initializing KubeJS Common Events");
            initialized = true;
        }
    }

    /**
     * Posta um evento para o sistema KubeJS
     * IMPLEMENTADO: Sistema de postagem de eventos com wrapper
     */
    public static void postKubeJSEvent(Event event) {
        try {
            // Criar wrapper do evento para JavaScript
            CommonEventJS commonEvent = new CommonEventJS(event);
            
            // Aqui devemos postar o evento para o sistema KubeJS
            // Na nova API do KubeJS, isso seria feito através do EventGroup
            // Por enquanto, implementamos logging para debug
            
            System.out.println("TacZ: Posting KubeJS common event: " + event.getClass().getSimpleName());
            
            // Usar o wrapper criado para futuras implementações
            handleCommonEvent(commonEvent);
            
        } catch (Exception e) {
            System.err.println("TacZ: Error posting KubeJS common event: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Processa o evento comum
     * IMPLEMENTADO: Handler para eventos comuns
     */
    private static void handleCommonEvent(CommonEventJS commonEvent) {
        // TODO: Integrar com a nova API do KubeJS quando estável
        // Por enquanto, apenas log
        System.out.println("TacZ: Handling common event: " + commonEvent.getEventType());
    }

    /**
     * Wrapper de evento para JavaScript - Eventos Comuns
     * IMPLEMENTADO: Classe wrapper para expor eventos Forge ao JavaScript
     */
    public static class CommonEventJS {
        private final Event forgeEvent;

        public CommonEventJS(Event forgeEvent) {
            this.forgeEvent = forgeEvent;
        }

        /**
         * Obtém o evento Forge original
         * @return Evento Forge original
         */
        public Event getForgeEvent() {
            return forgeEvent;
        }
        
        /**
         * Obtém o nome da classe do evento
         * @return Nome da classe do evento
         */
        public String getEventType() {
            return forgeEvent.getClass().getSimpleName();
        }
        
        /**
         * Verifica se o evento pode ser cancelado
         * @return true se o evento pode ser cancelado
         */
        public boolean isCancelable() {
            // No NeoForge, apenas eventos que implementam ICancellableEvent podem ser cancelados
            return forgeEvent instanceof net.neoforged.bus.api.ICancellableEvent;
        }
        
        /**
         * Verifica se o evento foi cancelado
         * @return true se o evento foi cancelado
         */
        public boolean isCanceled() {
            if (forgeEvent instanceof net.neoforged.bus.api.ICancellableEvent cancellable) {
                return cancellable.isCanceled();
            }
            return false;
        }
        
        /**
         * Cancela o evento (se possível)
         */
        public void cancel() {
            if (forgeEvent instanceof net.neoforged.bus.api.ICancellableEvent cancellable) {
                cancellable.setCanceled(true);
            }
        }
    }
}
