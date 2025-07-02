package com.tacz.guns.event;

import com.tacz.guns.config.common.OtherConfig;
import com.tacz.guns.util.HitboxHelper;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber
public class HitboxHelperEvent {
    @SubscribeEvent(receiveCanceled = true)
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!OtherConfig.SERVER_HITBOX_LATENCY_FIX.get()) {
            return;
        }
        HitboxHelper.onPlayerTick(event.getEntity());
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        HitboxHelper.onPlayerLoggedOut(event.getEntity());
    }
}
