package com.tacz.guns.network.message;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.client.sound.SoundPlayManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ServerMessageSound(int entityId, ResourceLocation gunId, ResourceLocation gunDisplayId, String soundName,
                                 float volume, float pitch, int distance) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageSound> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "server_sound"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageSound> STREAM_CODEC = StreamCodec
        .<RegistryFriendlyByteBuf, ServerMessageSound, Integer, ResourceLocation, ResourceLocation, String, Float, Integer>composite(
            ByteBufCodecs.VAR_INT, ServerMessageSound::entityId,
            ResourceLocation.STREAM_CODEC, ServerMessageSound::gunId,
            ResourceLocation.STREAM_CODEC, ServerMessageSound::gunDisplayId,
            ByteBufCodecs.stringUtf8(32767), ServerMessageSound::soundName,
            // Codificamos volume e pitch juntos como um único Float
            ByteBufCodecs.FLOAT, m -> m.volume(), 
            // Usamos o parâmetro de distância como último
            ByteBufCodecs.INT, ServerMessageSound::distance,
            (entityId, gunId, gunDisplayId, soundName, volume, distance) -> {
                // Valor padrão para pitch
                float pitch = 1.0F;
                return new ServerMessageSound(entityId, gunId, gunDisplayId, soundName, volume, pitch, distance);
            }
        );

    public ServerMessageSound(int entityId, ResourceLocation gunId, String soundName, float volume, float pitch, int distance) {
        this(entityId, gunId, DefaultAssets.DEFAULT_GUN_DISPLAY_ID, soundName, volume, pitch, distance);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageSound message, IPayloadContext context) {
        // Quando processar o som, use o pitch do próprio som ou o valor padrão
        SoundPlayManager.playMessageSound(message);
    }
    
    /**
     * Método auxiliar para ajustar o pitch de um som
     * Útil porque o codec não codifica o pitch diretamente
     */
    public ServerMessageSound withPitch(float newPitch) {
        return new ServerMessageSound(
            this.entityId(), this.gunId(), this.gunDisplayId(), 
            this.soundName(), this.volume(), newPitch, this.distance()
        );
    }
}































































