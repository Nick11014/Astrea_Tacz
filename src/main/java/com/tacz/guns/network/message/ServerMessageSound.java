package com.tacz.guns.network.message;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.client.sound.SoundPlayManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ServerMessageSound(int entityId, ResourceLocation gunId, ResourceLocation gunDisplayId, String soundName,
                                 float volume, float pitch, int distance) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageSound> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "server_sound"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageSound> STREAM_CODEC = StreamCodec.composite(
        StreamCodec.VAR_INT, ServerMessageSound::entityId,
        ResourceLocation.STREAM_CODEC, ServerMessageSound::gunId,
        ResourceLocation.STREAM_CODEC, ServerMessageSound::gunDisplayId,
        StreamCodec.STRING, ServerMessageSound::soundName,
        StreamCodec.FLOAT, ServerMessageSound::volume,
        StreamCodec.FLOAT, ServerMessageSound::pitch,
        StreamCodec.INT, ServerMessageSound::distance,
        ServerMessageSound::new
    );

    public ServerMessageSound(int entityId, ResourceLocation gunId, String soundName, float volume, float pitch, int distance) {
        this(entityId, gunId, DefaultAssets.DEFAULT_GUN_DISPLAY_ID, soundName, volume, pitch, distance);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageSound message, IPayloadContext context) {
        SoundPlayManager.playClientSound(message);
    }
}































































