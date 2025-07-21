package com.tacz.guns.network;

import com.tacz.guns.network.message.*;
import com.tacz.guns.network.message.event.*;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        // Client-to-Server messages
        registerClientMessage(registrar, ClientMessagePlayerAim.TYPE, ClientMessagePlayerAim.STREAM_CODEC, ClientMessagePlayerAim::handle);
        registerClientMessage(registrar, ClientMessagePlayerShoot.TYPE, ClientMessagePlayerShoot.STREAM_CODEC, ClientMessagePlayerShoot::handle);
        registerClientMessage(registrar, ClientMessagePlayerBoltGun.TYPE, ClientMessagePlayerBoltGun.STREAM_CODEC, ClientMessagePlayerBoltGun::handle);
        registerClientMessage(registrar, ClientMessagePlayerCancelReload.TYPE, ClientMessagePlayerCancelReload.STREAM_CODEC, ClientMessagePlayerCancelReload::handle);
        registerClientMessage(registrar, ClientMessagePlayerCrawl.TYPE, ClientMessagePlayerCrawl.STREAM_CODEC, ClientMessagePlayerCrawl::handle);
        registerClientMessage(registrar, ClientMessagePlayerDrawGun.TYPE, ClientMessagePlayerDrawGun.STREAM_CODEC, ClientMessagePlayerDrawGun::handle);
        registerClientMessage(registrar, ClientMessagePlayerFireSelect.TYPE, ClientMessagePlayerFireSelect.STREAM_CODEC, ClientMessagePlayerFireSelect::handle);
        registerClientMessage(registrar, ClientMessagePlayerMelee.TYPE, ClientMessagePlayerMelee.STREAM_CODEC, ClientMessagePlayerMelee::handle);
        registerClientMessage(registrar, ClientMessagePlayerReloadGun.TYPE, ClientMessagePlayerReloadGun.STREAM_CODEC, ClientMessagePlayerReloadGun::handle);
        registerClientMessage(registrar, ClientMessagePlayerZoom.TYPE, ClientMessagePlayerZoom.STREAM_CODEC, ClientMessagePlayerZoom::handle);
        registerClientMessage(registrar, ClientMessageRefitGun.TYPE, ClientMessageRefitGun.STREAM_CODEC, ClientMessageRefitGun::handle);
        registerClientMessage(registrar, ClientMessageUnloadAttachment.TYPE, ClientMessageUnloadAttachment.STREAM_CODEC, ClientMessageUnloadAttachment::handle);
        registerClientMessage(registrar, ClientMessageCraft.TYPE, ClientMessageCraft.STREAM_CODEC, ClientMessageCraft::handle);
        registerClientMessage(registrar, ClientMessageLaserColor.TYPE, ClientMessageLaserColor.STREAM_CODEC, ClientMessageLaserColor::handle);
        registerClientMessage(registrar, ClientMessageSyncBaseTimestamp.TYPE, ClientMessageSyncBaseTimestamp.STREAM_CODEC, ClientMessageSyncBaseTimestamp::handle);

        // Server-to-Client messages
        registerServerMessage(registrar, ServerMessageGunFire.TYPE, ServerMessageGunFire.STREAM_CODEC, ServerMessageGunFire::handle);
        registerServerMessage(registrar, ServerMessageGunDraw.TYPE, ServerMessageGunDraw.STREAM_CODEC, ServerMessageGunDraw::handle);
        registerServerMessage(registrar, ServerMessageGunFireSelect.TYPE, ServerMessageGunFireSelect.STREAM_CODEC, ServerMessageGunFireSelect::handle);
        registerServerMessage(registrar, ServerMessageGunHurt.TYPE, ServerMessageGunHurt.STREAM_CODEC, ServerMessageGunHurt::handle);
        registerServerMessage(registrar, ServerMessageGunKill.TYPE, ServerMessageGunKill.STREAM_CODEC, ServerMessageGunKill::handle);
        registerServerMessage(registrar, ServerMessageGunMelee.TYPE, ServerMessageGunMelee.STREAM_CODEC, ServerMessageGunMelee::handle);
        registerServerMessage(registrar, ServerMessageSyncGunPack.TYPE, ServerMessageSyncGunPack.STREAM_CODEC, ServerMessageSyncGunPack::handle);
        registerServerMessage(registrar, ServerMessageUpdateEntityData.TYPE, ServerMessageUpdateEntityData.STREAM_CODEC, ServerMessageUpdateEntityData::handle);
        registerServerMessage(registrar, ServerMessageRefreshRefitScreen.TYPE, ServerMessageRefreshRefitScreen.STREAM_CODEC, ServerMessageRefreshRefitScreen::handle);
        registerServerMessage(registrar, ServerMessageSyncBaseTimestamp.TYPE, ServerMessageSyncBaseTimestamp.STREAM_CODEC, ServerMessageSyncBaseTimestamp::handle);
    }

    private static <T extends net.minecraft.network.protocol.common.custom.CustomPacketPayload> void registerClientMessage(
            PayloadRegistrar registrar,
            net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<T> type,
            net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, T> codec,
            net.neoforged.neoforge.network.handling.IPayloadHandler<T> handler) {
        registrar.playToServer(type, codec, handler);
    }

    private static <T extends net.minecraft.network.protocol.common.custom.CustomPacketPayload> void registerServerMessage(
            PayloadRegistrar registrar,
            net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<T> type,
            net.minecraft.network.codec.StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, T> codec,
            net.neoforged.neoforge.network.handling.IPayloadHandler<T> handler) {
        registrar.playToClient(type, codec, handler);
    }

    // Utility methods for sending packets
    public static void sendToServer(net.minecraft.network.protocol.common.custom.CustomPacketPayload payload) {
        PacketDistributor.sendToServer(payload);
    }

    public static void sendToClientPlayer(net.minecraft.network.protocol.common.custom.CustomPacketPayload payload, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static void sendToAllPlayers(net.minecraft.network.protocol.common.custom.CustomPacketPayload payload) {
        PacketDistributor.sendToAllPlayers(payload);
    }

    // Helper method to get player from context
    public static net.minecraft.world.entity.player.Player getPlayer(IPayloadContext context) {
        return context.player();
    }
}
