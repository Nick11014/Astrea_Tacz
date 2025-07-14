package com.tacz.guns.api.item.gun;

import com.google.gson.annotations.SerializedName;
import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum FireMode implements StringRepresentable {
    /**
     * ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨
     */
    @SerializedName("auto")
    AUTO,
    /**
     * ÃƒÂ¥Ã‚ÂÃ…Â ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨
     */
    @SerializedName("semi")
    SEMI,
    /**
     * ÃƒÂ¥Ã‚Â¤Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã…Â¾ÃƒÂ¥Ã‚ÂÃ¢â‚¬Ëœ
     */
    @SerializedName("burst")
    BURST,
    /**
     * ÃƒÂ¦Ã…â€œÃ‚ÂªÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ¯Ã‚Â¼Ã…Â¸
     */
    @SerializedName("unknown")
    UNKNOWN;

    // Codecs necessÃƒÆ’Ã‚Â¡rios para DataComponents no NeoForge 1.21.1
    public static final Codec<FireMode> CODEC = StringRepresentable.fromEnum(FireMode::values);
    public static final StreamCodec<RegistryFriendlyByteBuf, FireMode> STREAM_CODEC = 
        StreamCodec.of((buf, mode) -> buf.writeUtf(mode.getSerializedName()),
                      buf -> {
                          String name = buf.readUtf();
                          for (FireMode mode : values()) {
                              if (mode.getSerializedName().equals(name)) {
                                  return mode;
                              }
                          }
                          return UNKNOWN;
                      });

    @Override
    public String getSerializedName() {
        return switch (this) {
            case AUTO -> "auto";
            case SEMI -> "semi";
            case BURST -> "burst";
            case UNKNOWN -> "unknown";
        };
    }
}































































