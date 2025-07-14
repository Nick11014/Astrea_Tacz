package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import com.tacz.guns.entity.sync.core.DataEntry;
import com.tacz.guns.entity.sync.core.SyncedEntityData;
import net.minecraft.client.Minecraft;
import com.tacz.guns.GunMod;
import com.tacz.guns.entity.sync.core.DataEntry;
import com.tacz.guns.entity.sync.core.SyncedEntityData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

public record ServerMessageUpdateEntityData(int entityId,
                                            List<DataEntry<?, ?>> entries) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageUpdateEntityData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_update_entity_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageUpdateEntityData> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ServerMessageUpdateEntityData decode(RegistryFriendlyByteBuf buf) {
            return new ServerMessageUpdateEntityData(buf.readVarInt(), readEntries(buf));
        }

        private static List<DataEntry<?, ?>> readEntries(RegistryFriendlyByteBuf buffer) {
            int size = buffer.readVarInt();
            List<DataEntry<?, ?>> entries = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                entries.add(DataEntry.read(buffer));
            }
            return entries;
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, ServerMessageUpdateEntityData message) {
            buf.writeVarInt(message.entityId);
            buf.writeVarInt(message.entries.size());
            message.entries.forEach(entry -> entry.write(buf));
        }
    };

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageUpdateEntityData message, IPayloadContext context) {
        context.enqueueWork(() -> onHandle(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(ServerMessageUpdateEntityData message) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        Entity entity = level.getEntity(message.entityId);
        if (entity == null) {
            return;
        }
        SyncedEntityData instance = SyncedEntityData.instance();
        message.entries.forEach(entry -> instance.set(entity, entry.getKey(), entry.getValue()));
    }
}































































