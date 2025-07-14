package com.tacz.guns.config;

import com.tacz.guns.config.sync.SyncConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    /**
     * ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¤Ã‚Â¸Ã‚Âº Forge ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ©Ã‚Â¢Ã‹Å“ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã¢â‚¬Â¦ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â 
     */
    public static ModConfigSpec SERVER_CONFIG_SPEC;

    public static ModConfigSpec init() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        SyncConfig.init(builder);
        SERVER_CONFIG_SPEC = builder.build();
        return SERVER_CONFIG_SPEC;
    }
}































































