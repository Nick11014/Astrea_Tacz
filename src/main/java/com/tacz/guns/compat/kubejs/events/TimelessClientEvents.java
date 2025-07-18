package com.tacz.guns.compat.kubejs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.event.KubeEventJS;
import net.neoforged.bus.api.Event;

public class TimelessClientEvents {
    public static final EventGroup GROUP = EventGroup.of("TimelessClientEvents").register();

    public static final KubeEvent<ClientEventJS> CLIENT_EVENT = GROUP.client("client_event", ClientEventJS.class);

    public static void postKubeJSEvent(Event event) {
        CLIENT_EVENT.post(new ClientEventJS(event));
    }

    public static class ClientEventJS extends KubeEventJS {
        private final Event forgeEvent;

        public ClientEventJS(Event forgeEvent) {
            this.forgeEvent = forgeEvent;
        }

        public Event getForgeEvent() {
            return forgeEvent;
        }
    }
}
