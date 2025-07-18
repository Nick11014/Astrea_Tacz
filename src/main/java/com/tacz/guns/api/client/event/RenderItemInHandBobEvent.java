package com.tacz.guns.api.client.event;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * Evento para controlar o balanço do item na mão do jogador em primeira pessoa.
 */
public class RenderItemInHandBobEvent extends Event implements ICancellableEvent {

    public static class BobHurt extends RenderItemInHandBobEvent {
        public BobHurt() {
        }
    }

    public static class BobView extends RenderItemInHandBobEvent {
        public BobView() {
        }
    }
}































































