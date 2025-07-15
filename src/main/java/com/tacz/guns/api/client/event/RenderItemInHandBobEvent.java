package com.tacz.guns.api.client.event;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Â¡ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Â¡ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™
 */
public class RenderItemInHandBobEvent extends Event implements ICancellableEvent {

    public static class BobHurt extends RenderItemInHandBobEvent {
        public BobHurt() {
            // TODO: Re-add KubeJS integration when dependencies are available
        }
    }

    public static class BobView extends RenderItemInHandBobEvent {
        public BobView() {
            // TODO: Re-add KubeJS integration when dependencies are available
        }
    }
}































































