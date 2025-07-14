package com.tacz.guns.network.message.handshake;

import com.tacz.guns.GunMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public record Acknowledge() implements CustomPacketPayload {
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "acknowledge");
    public static final Marker ACKNOWLEDGE_MARKER = MarkerManager.getMarker("HANDSHAKE_ACKNOWLEDGE");

    public Acknowledge(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // No data to write
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(Acknowledge message, IPayloadContext context) {
        GunMod.LOGGER.debug(ACKNOWLEDGE_MARKER, "Received acknowledgement from client");
    }
}































































