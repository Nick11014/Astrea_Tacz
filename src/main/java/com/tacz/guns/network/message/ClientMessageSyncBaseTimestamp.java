package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessageSyncBaseTimestamp() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientMessageSyncBaseTimestamp> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_sync_base_timestamp"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessageSyncBaseTimestamp> STREAM_CODEC = StreamCodec.unit(new ClientMessageSyncBaseTimestamp());
    private static final Marker MARKER = MarkerManager.getMarker("SYNC_BASE_TIMESTAMP");

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientMessageSyncBaseTimestamp message, IPayloadContext context) {
        long timestamp = System.currentTimeMillis();
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        ShooterDataHolder dataHolder = IGunOperator.fromLivingEntity(player).getDataHolder();
        dataHolder.baseTimestamp = timestamp;
        GunMod.LOGGER.debug(MARKER, "Update server base timestamp: {}", dataHolder.baseTimestamp);
    }
}































































