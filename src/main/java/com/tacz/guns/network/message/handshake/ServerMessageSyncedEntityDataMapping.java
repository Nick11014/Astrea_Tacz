package com.tacz.guns.network.message.handshake;

import com.tacz.guns.GunMod;
import com.tacz.guns.entity.sync.core.SyncedDataKey;
import com.tacz.guns.entity.sync.core.SyncedEntityData;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public record ServerMessageSyncedEntityDataMapping(
        Map<ResourceLocation, List<Pair<ResourceLocation, Integer>>> keyMap) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageSyncedEntityDataMapping> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_synced_entity_data_mapping"));
    public static final Marker HANDSHAKE_MARKER = MarkerManager.getMarker("TACZ_HANDSHAKE");

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageSyncedEntityDataMapping> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ServerMessageSyncedEntityDataMapping decode(RegistryFriendlyByteBuf buf) {
            return new ServerMessageSyncedEntityDataMapping(readKeyMap(buf));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, ServerMessageSyncedEntityDataMapping message) {
            message.write(buf);
        }
    };

    public ServerMessageSyncedEntityDataMapping() {
        this(new HashMap<>());
        Set<SyncedDataKey<?, ?>> keys = SyncedEntityData.instance().getKeys();
        keys.forEach(key -> {
            int id = SyncedEntityData.instance().getInternalId(key);
            keyMap.computeIfAbsent(key.classKey().id(), c -> new ArrayList<>()).add(Pair.of(key.id(), id));
        });
    }

    private static Map<ResourceLocation, List<Pair<ResourceLocation, Integer>>> readKeyMap(RegistryFriendlyByteBuf buffer) {
        int size = buffer.readInt();
        Map<ResourceLocation, List<Pair<ResourceLocation, Integer>>> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ResourceLocation classId = buffer.readResourceLocation();
            ResourceLocation keyId = buffer.readResourceLocation();
            int id = buffer.readVarInt();
            map.computeIfAbsent(classId, c -> new ArrayList<>()).add(Pair.of(keyId, id));
        }
        return map;
    }

    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(keyMap.size());
        keyMap.forEach((classId, pairs) -> {
            pairs.forEach(pair -> {
                buf.writeResourceLocation(classId);
                buf.writeResourceLocation(pair.getLeft());
                buf.writeVarInt(pair.getRight());
            });
        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageSyncedEntityDataMapping message, IPayloadContext context) {
        GunMod.LOGGER.debug(HANDSHAKE_MARKER, "Received synced key mappings from server");
        CountDownLatch block = new CountDownLatch(1);
        context.enqueueWork(() -> {
            if (!SyncedEntityData.instance().updateMappings(message)) {
                ((ServerPlayer) context.player()).connection.disconnect(Component.literal("Connection closed - [TacZ] Received unknown synced data keys."));
            }
            block.countDown();
        });
        try {
            block.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PacketDistributor.sendToServer(new Acknowledge());
    }
}































































