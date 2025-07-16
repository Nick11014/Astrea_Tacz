package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import com.tacz.guns.client.resource.ClientIndexManager;
import com.tacz.guns.resource.network.CommonNetworkCache;
import com.tacz.guns.resource.network.DataType;
import com.tacz.guns.GunMod;
import com.tacz.guns.client.resource.ClientIndexManager;
import com.tacz.guns.resource.network.CommonNetworkCache;
import com.tacz.guns.resource.network.DataType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;

public record ServerMessageSyncGunPack(
        Map<DataType, Map<ResourceLocation, String>> cache) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageSyncGunPack> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_sync_gun_pack"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageSyncGunPack> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ServerMessageSyncGunPack decode(RegistryFriendlyByteBuf buf) {
            return new ServerMessageSyncGunPack(buf.readMap(DataType::fromStream, b -> b.readMap(ResourceLocation::fromStream, ByteBufCodecs.STRING.apply(b))));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, ServerMessageSyncGunPack message) {
            buf.writeMap(message.cache, DataType::toStream, (buf1, map) -> {
                buf1.writeMap(map, ResourceLocation.STREAM_CODEC, ByteBufCodecs.STRING);
            });
        }
    };

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageSyncGunPack message, IPayloadContext context) {
        context.enqueueWork(() -> doSync(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void doSync(ServerMessageSyncGunPack message) {
        CommonNetworkCache.INSTANCE.fromNetwork(message.cache);
        ClientIndexManager.reload();
    }
}































































