package com.tacz.guns.network.message.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;

public record ServerMessageGunKill(int bulletId, int killEntityId, int attackerId, ResourceLocation gunId,
                                   ResourceLocation gunDisplayId, float baseDamage, boolean isHeadShot,
                                   float headshotMultiplier) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageGunKill> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_gun_kill"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageGunKill> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ServerMessageGunKill::bulletId,
            ByteBufCodecs.INT, ServerMessageGunKill::killEntityId,
            ByteBufCodecs.INT, ServerMessageGunKill::attackerId,
            ResourceLocation.STREAM_CODEC, ServerMessageGunKill::gunId,
            ResourceLocation.STREAM_CODEC, ServerMessageGunKill::gunDisplayId,
            ByteBufCodecs.FLOAT, ServerMessageGunKill::baseDamage,
            ByteBufCodecs.BOOL, ServerMessageGunKill::isHeadShot,
            ByteBufCodecs.FLOAT, ServerMessageGunKill::headshotMultiplier,
            ServerMessageGunKill::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageGunKill message, IPayloadContext context) {
        context.enqueueWork(() -> onKill(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void onKill(ServerMessageGunKill message) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        @Nullable Entity bullet = level.getEntity(message.bulletId);
        @Nullable LivingEntity killedEntity = level.getEntity(message.killEntityId) instanceof LivingEntity livingEntity ? livingEntity : null;
        @Nullable LivingEntity attacker = level.getEntity(message.attackerId) instanceof LivingEntity livingEntity ? livingEntity : null;
        NeoForge.EVENT_BUS.post(new EntityKillByGunEvent(bullet, killedEntity, attacker, message.gunId, message.gunDisplayId, message.baseDamage, null, message.isHeadShot, message.headshotMultiplier, LogicalSide.CLIENT));
    }
}































































