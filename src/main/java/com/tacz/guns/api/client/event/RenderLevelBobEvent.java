package com.tacz.guns.api.client.event;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
 * ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Â¡ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¢â‚¬â€œÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â¯ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Â¡ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™
 */
public class RenderLevelBobEvent extends Event implements ICancellableEvent {
    // A interface ICancellableEvent substitui a anotaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o @Cancelable removida no NeoForge

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































































