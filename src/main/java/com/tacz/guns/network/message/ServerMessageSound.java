package com.tacz.guns.network.message;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.client.sound.SoundPlayManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ServerMessageSound(int entityId, ResourceLocation gunId, ResourceLocation gunDisplayId, String soundName,
                                 float volume, float pitch, int distance) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "server_sound");

    public ServerMessageSound(int entityId, ResourceLocation gunId, String soundName, float volume, float pitch, int distance) {
        this(entityId, gunId, DefaultAssets.DEFAULT_GUN_DISPLAY_ID, soundName, volume, pitch, distance);
    }

    public ServerMessageSound(FriendlyByteBuf buf) {
        this(buf.readVarInt(), buf.readResourceLocation(), buf.readResourceLocation(), buf.readUtf(), buf.readFloat(), buf.readFloat(), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(entityId);
        buf.writeResourceLocation(gunId);
        buf.writeResourceLocation(gunDisplayId);
        buf.writeUtf(soundName);
        buf.writeFloat(volume);
        buf.writeFloat(pitch);
        buf.writeInt(distance);
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(ServerMessageSound message, IPayloadContext context) {
        SoundPlayManager.playClientSound(message);
    }
}































































