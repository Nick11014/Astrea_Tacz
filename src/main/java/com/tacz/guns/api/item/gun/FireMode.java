package com.tacz.guns.api.item.gun;

import com.google.gson.annotations.SerializedName;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum FireMode implements StringRepresentable {
    /**
     * 全自动
     */
    @SerializedName("auto")
    AUTO,
    /**
     * 半自动
     */
    @SerializedName("semi")
    SEMI,
    /**
     * 多连发
     */
    @SerializedName("burst")
    BURST,    /**
     * 未知的其他情况？
     */
    @SerializedName("unknown")
    UNKNOWN;

    // Codecs necessários para DataComponents no NeoForge 1.21.1
    public static final Codec<FireMode> CODEC = StringRepresentable.fromEnum(FireMode::values);
    public static final StreamCodec<?, FireMode> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

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
