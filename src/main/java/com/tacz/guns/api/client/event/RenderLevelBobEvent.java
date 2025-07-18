package com.tacz.guns.api.client.event;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * Evento para controlar o balanço do nível (câmera) do jogador em primeira pessoa.
 */
public class RenderLevelBobEvent extends Event implements ICancellableEvent {

    public static class BobHurt extends RenderLevelBobEvent {
        public BobHurt() {
        }
    }

    public static class BobView extends RenderLevelBobEvent {
        public BobView() {
        }
    }
}































































