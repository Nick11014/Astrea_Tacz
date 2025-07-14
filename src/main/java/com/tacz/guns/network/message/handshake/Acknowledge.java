package com.tacz.guns.network.message.handshake;

import com.tacz.guns.GunMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public record Acknowledge() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<Acknowledge> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "acknowledge"));
    public static final Marker ACKNOWLEDGE_MARKER = MarkerManager.getMarker("HANDSHAKE_ACKNOWLEDGE");

    public static final StreamCodec<RegistryFriendlyByteBuf, Acknowledge> STREAM_CODEC = 
        StreamCodec.unit(new Acknowledge());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(Acknowledge message, IPayloadContext context) {
        GunMod.LOGGER.debug(ACKNOWLEDGE_MARKER, "Received acknowledgement from client");
    }
}































































