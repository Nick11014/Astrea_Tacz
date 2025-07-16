package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import com.tacz.guns.client.resource.ClientIndexManager;
import com.tacz.guns.resource.network.CommonNetworkCache;
import com.tacz.guns.resource.network.DataType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
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

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageSyncGunPack> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(java.util.HashMap::new, DataType.STREAM_CODEC, ByteBufCodecs.map(java.util.HashMap::new, ResourceLocation.STREAM_CODEC, ByteBufCodecs.STRING_UTF8)),
            ServerMessageSyncGunPack::cache,
            ServerMessageSyncGunPack::new
    );

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