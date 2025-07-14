package com.tacz.guns.network;

import com.tacz.guns.GunMod;
import com.tacz.guns.network.message.*;
import com.tacz.guns.network.message.event.*;
import com.tacz.guns.network.message.handshake.Acknowledge;
import com.tacz.guns.network.message.handshake.ServerMessageSyncedEntityDataMapping;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Import removido - IPayloadRegistrar agora ÃƒÆ’Ã‚Â© obtido atravÃƒÆ’Ã‚Â©s do event
// import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class NetworkHandler {
    private static final String VERSION = "1.0.5";

    public static void register(final RegisterPayloadHandlersEvent event) {
        final IPayloadRegistrar registrar = event.registrar(GunMod.MOD_ID).versioned(VERSION);
        // Server-bound
        registrar.play(ClientMessagePlayerShoot.TYPE, ClientMessagePlayerShoot::new, handler -> handler.server(ClientMessagePlayerShoot::handle));
        registrar.play(ClientMessagePlayerReloadGun.TYPE, ClientMessagePlayerReloadGun::new, handler -> handler.server(ClientMessagePlayerReloadGun::handle));
        registrar.play(ClientMessagePlayerCancelReload.TYPE, ClientMessagePlayerCancelReload::new, handler -> handler.server(ClientMessagePlayerCancelReload::handle));
        registrar.play(ClientMessagePlayerFireSelect.TYPE, ClientMessagePlayerFireSelect::new, handler -> handler.server(ClientMessagePlayerFireSelect::handle));
        registrar.play(ClientMessagePlayerAim.TYPE, ClientMessagePlayerAim::new, handler -> handler.server(ClientMessagePlayerAim::handle));
        registrar.play(ClientMessagePlayerCrawl.TYPE, ClientMessagePlayerCrawl::new, handler -> handler.server(ClientMessagePlayerCrawl::handle));
        registrar.play(ClientMessagePlayerDrawGun.TYPE, ClientMessagePlayerDrawGun::new, handler -> handler.server(ClientMessagePlayerDrawGun::handle));
        registrar.play(ClientMessageCraft.TYPE, ClientMessageCraft::new, handler -> handler.server(ClientMessageCraft::handle));
        registrar.play(ClientMessagePlayerZoom.TYPE, ClientMessagePlayerZoom::new, handler -> handler.server(ClientMessagePlayerZoom::handle));
        registrar.play(ClientMessageRefitGun.TYPE, ClientMessageRefitGun::new, handler -> handler.server(ClientMessageRefitGun::handle));
        registrar.play(ClientMessageUnloadAttachment.TYPE, ClientMessageUnloadAttachment::new, handler -> handler.server(ClientMessageUnloadAttachment::handle));
        registrar.play(ClientMessagePlayerBoltGun.TYPE, ClientMessagePlayerBoltGun::new, handler -> handler.server(ClientMessagePlayerBoltGun::handle));
        registrar.play(ClientMessagePlayerMelee.TYPE, ClientMessagePlayerMelee::new, handler -> handler.server(ClientMessagePlayerMelee::handle));
        registrar.play(ClientMessageSyncBaseTimestamp.TYPE, ClientMessageSyncBaseTimestamp::new, handler -> handler.server(ClientMessageSyncBaseTimestamp::handle));
        registrar.play(ClientMessageLaserColor.TYPE, ClientMessageLaserColor::new, handler -> handler.server(ClientMessageLaserColor::handle));

        // Client-bound
        registrar.play(ServerMessageSound.TYPE, ServerMessageSound::new, handler -> handler.client(ServerMessageSound::handle));
        registrar.play(ServerMessageCraft.TYPE, ServerMessageCraft::new, handler -> handler.client(ServerMessageCraft::handle));
        registrar.play(ServerMessageRefreshRefitScreen.TYPE, ServerMessageRefreshRefitScreen::new, handler -> handler.client(ServerMessageRefreshRefitScreen::handle));
        registrar.play(ServerMessageSwapItem.TYPE, ServerMessageSwapItem::new, handler -> handler.client(ServerMessageSwapItem::handle));
        registrar.play(ServerMessageLevelUp.TYPE, ServerMessageLevelUp::new, handler -> handler.client(ServerMessageLevelUp::handle));
        registrar.play(ServerMessageGunHurt.TYPE, ServerMessageGunHurt::new, handler -> handler.client(ServerMessageGunHurt::handle));
        registrar.play(ServerMessageGunKill.TYPE, ServerMessageGunKill::new, handler -> handler.client(ServerMessageGunKill::handle));
        registrar.play(ServerMessageUpdateEntityData.TYPE, ServerMessageUpdateEntityData::new, handler -> handler.client(ServerMessageUpdateEntityData::handle));
        registrar.play(ServerMessageSyncGunPack.TYPE, ServerMessageSyncGunPack::new, handler -> handler.client(ServerMessageSyncGunPack::handle));
        registrar.play(ServerMessageGunDraw.TYPE, ServerMessageGunDraw::new, handler -> handler.client(ServerMessageGunDraw::handle));
        registrar.play(ServerMessageGunFire.TYPE, ServerMessageGunFire::new, handler -> handler.client(ServerMessageGunFire::handle));
        registrar.play(ServerMessageGunFireSelect.TYPE, ServerMessageGunFireSelect::new, handler -> handler.client(ServerMessageGunFireSelect::handle));
        registrar.play(ServerMessageGunMelee.TYPE, ServerMessageGunMelee::new, handler -> handler.client(ServerMessageGunMelee::handle));
        registrar.play(ServerMessageGunReload.TYPE, ServerMessageGunReload::new, handler -> handler.client(ServerMessageGunReload::handle));
        registrar.play(ServerMessageGunShoot.TYPE, ServerMessageGunShoot::new, handler -> handler.client(ServerMessageGunShoot::handle));
        registrar.play(ServerMessageSyncBaseTimestamp.TYPE, ServerMessageSyncBaseTimestamp::new, handler -> handler.client(ServerMessageSyncBaseTimestamp::handle));

        // Handshake
        registrar.play(Acknowledge.TYPE, Acknowledge::new, handler -> handler.server(Acknowledge::handle));
        registrar.play(ServerMessageSyncedEntityDataMapping.TYPE, ServerMessageSyncedEntityDataMapping::new, handler -> handler.client(ServerMessageSyncedEntityDataMapping::handle));
    }

    public static void sendToClientPlayer(Object message, Player player) {
        if (player instanceof ServerPlayer sp) {
            PacketDistributor.sendToPlayer(sp, message);
        }
    }

    public static void sendToTrackingEntityAndSelf(Entity centerEntity, Object message) {
        PacketDistributor.sendToPlayersTrackingEntity(centerEntity, message);
        if (centerEntity instanceof ServerPlayer sp) {
            PacketDistributor.sendToPlayer(sp, message);
        }
    }

    public static void sendToAllPlayers(Object message) {
        PacketDistributor.sendToAllPlayers(message);
    }

    public static void sendToTrackingEntity(Object message, final Entity centerEntity) {
        PacketDistributor.sendToPlayersTrackingEntity(centerEntity, message);
    }

    public static void sendToDimension(Object message, final Entity centerEntity) {
        ResourceKey<Level> dimension = centerEntity.level().dimension();
        PacketDistributor.sendToPlayersInDimension(centerEntity.getServer().getLevel(dimension), message);
    }

    public static void sendToAllNear(Object message, double x, double y, double z, double radius, ResourceKey<Level> dimension, Level world) {
        PacketDistributor.sendToPlayersNear(world.getServer().getLevel(dimension), x, y, z, radius, message);
    }

    public static void sendToChunk(Object message, ChunkPos chunkPos, ResourceKey<Level> dimension, Level world) {
        PacketDistributor.sendToPlayersInChunk(world.getServer().getLevel(dimension), chunkPos, message);
    }

    public static void sendToBlock(Object message, BlockPos pos, ResourceKey<Level> dimension, Level world) {
        PacketDistributor.sendToPlayersNear(world.getServer().getLevel(dimension), pos.getX(), pos.getY(), pos.getZ(), 64, message);
    }

    public static void sendToServer(Object message) {
        PacketDistributor.sendToServer(message);
    }

    public static Player getPlayer(IPayloadContext context) {
        return context.player().orElse(null);
    }
}































































