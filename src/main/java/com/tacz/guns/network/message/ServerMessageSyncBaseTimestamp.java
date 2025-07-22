package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.client.gameplay.LocalPlayerDataHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Objects;

public record ServerMessageSyncBaseTimestamp() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageSyncBaseTimestamp> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_sync_base_timestamp"));
    private static final Marker MARKER = MarkerManager.getMarker("SYNC_BASE_TIMESTAMP");

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageSyncBaseTimestamp> STREAM_CODEC = 
        StreamCodec.unit(new ServerMessageSyncBaseTimestamp());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageSyncBaseTimestamp message, IPayloadContext context) {
        long timestamp = System.currentTimeMillis();
        context.enqueueWork(() -> updateBaseTimestamp(timestamp));
        PacketDistributor.sendToServer(new ClientMessageSyncBaseTimestamp());
    }

    @OnlyIn(Dist.CLIENT)
    private static void updateBaseTimestamp(long timestamp) {
        LocalPlayer player = Objects.requireNonNull(Minecraft.getInstance().player);
        try {
            // Use instanceof check to verify mixin was applied safely
            if (player instanceof IClientPlayerGunOperator) {
                LocalPlayerDataHolder dataHolder = ((IClientPlayerGunOperator) player).getDataHolder();
                dataHolder.clientBaseTimestamp = timestamp;
                GunMod.LOGGER.debug(MARKER, "Update client base timestamp: {}", dataHolder.clientBaseTimestamp);
            } else {
                // Safeguard: Mixin not applied yet during development - skip timestamp sync
                GunMod.LOGGER.warn(MARKER, "LocalPlayer mixin not applied yet - skipping timestamp sync. This is expected during mod migration.");
            }
        } catch (Exception e) {
            // Additional fallback for any other issues
            GunMod.LOGGER.warn(MARKER, "Failed to sync timestamp: {}", e.getMessage());
        }
    }
}































































