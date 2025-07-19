package com.tacz.guns.compat.kubejs.events;

import dev.latvian.mods.kubejs.event.KubeEvent;

/**
 * Classe base para eventos TacZ no KubeJS
 * MIGRAÇÃO 1.21.1: Base para todos os eventos customizados
 */
public abstract class TimelessEventJS implements KubeEvent {
    
    private boolean cancelled = false;
    
    /**
     * Verifica se o evento foi cancelado
     */
    public boolean isCanceled() {
        return cancelled;
    }
    
    /**
     * Cancela o evento
     */
    public void cancel() {
        this.cancelled = true;
    }
    
    /**
     * Define se o evento foi cancelado
     */
    public void setCanceled(boolean canceled) {
        this.cancelled = canceled;
    }
}
