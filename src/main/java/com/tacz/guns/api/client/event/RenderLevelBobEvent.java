package com.tacz.guns.api.client.event;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * 当第一人称视角触发摇晃时，世界背景的摇晃
 */
public class RenderLevelBobEvent extends Event implements ICancellableEvent {
    // A interface ICancellableEvent substitui a anotação @Cancelable removida no NeoForge

    public static class BobHurt extends RenderLevelBobEvent {
        public BobHurt() {
            // TODO: Re-add KubeJS integration when dependencies are available
        }
    }

    public static class BobView extends RenderLevelBobEvent {
        public BobView() {
            // TODO: Re-add KubeJS integration when dependencies are available
        }
    }
}
