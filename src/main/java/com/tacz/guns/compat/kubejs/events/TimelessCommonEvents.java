package com.tacz.guns.compat.kubejs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.event.KubeEventJS;
import net.neoforged.bus.api.Event;

public class TimelessCommonEvents {
    public static final EventGroup GROUP = EventGroup.of("TimelessCommonEvents").register();

    public static final KubeEvent<CommonEventJS> COMMON_EVENT = GROUP.common("common_event", CommonEventJS.class);

    public static void postKubeJSEvent(Event event) {
        COMMON_EVENT.post(new CommonEventJS(event));
    }

    public static class CommonEventJS extends KubeEventJS {
        private final Event forgeEvent;

        public CommonEventJS(Event forgeEvent) {
            this.forgeEvent = forgeEvent;
        }

        public Event getForgeEvent() {
            return forgeEvent;
        }
    }
}
