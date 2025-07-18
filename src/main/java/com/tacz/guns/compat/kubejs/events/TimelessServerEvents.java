package com.tacz.guns.compat.kubejs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.event.KubeEventJS;
import net.neoforged.bus.api.Event;

public class TimelessServerEvents {
    public static final EventGroup GROUP = EventGroup.of("TimelessServerEvents").register();

    public static final KubeEvent<ServerEventJS> SERVER_EVENT = GROUP.server("server_event", ServerEventJS.class);

    public static void postKubeJSEvent(Event event) {
        SERVER_EVENT.post(new ServerEventJS(event));
    }

    public static class ServerEventJS extends KubeEventJS {
        private final Event forgeEvent;

        public ServerEventJS(Event forgeEvent) {
            this.forgeEvent = forgeEvent;
        }

        public Event getForgeEvent() {
            return forgeEvent;
        }
    }
}
