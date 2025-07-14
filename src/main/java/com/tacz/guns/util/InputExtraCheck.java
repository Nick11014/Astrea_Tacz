package com.tacz.guns.util;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class InputExtraCheck {
    public static boolean isInGame() {
        Minecraft mc = Minecraft.getInstance();
        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ©Ã‚ÂÃ‚Â¢
        if (mc.getOverlay() != null) {
            return false;
        }
        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â¢ GUI
        if (mc.screen != null) {
            return false;
        }
        // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ©Ã‚Â¼Ã‚Â ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¦Ã¢â‚¬Å“Ã‚ÂÃƒÂ¤Ã‚Â½Ã…â€œ
        if (!mc.mouseHandler.isMouseGrabbed()) {
            return false;
        }
        // ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£
        return mc.isWindowActive();
    }
}































































