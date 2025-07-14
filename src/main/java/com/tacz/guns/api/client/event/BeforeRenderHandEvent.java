package com.tacz.guns.api.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.bus.api.Event;

/**
 * ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ ItemInHandRenderer#renderHandsWithItems ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
 * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨
 */
public class BeforeRenderHandEvent extends Event {
    private final PoseStack poseStack;

    public BeforeRenderHandEvent(PoseStack poseStack) {
        this.poseStack = poseStack;
        // TODO: Re-add KubeJS integration when dependencies are available
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }
}































































