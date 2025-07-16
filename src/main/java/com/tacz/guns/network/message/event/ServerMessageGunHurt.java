package com.tacz.guns.network.message.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
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

public record ServerMessageGunHurt(int bulletId, int hurtEntityId, int attackerId, ResourceLocation gunId,
                                   ResourceLocation gunDisplayId,
                                   float amount, boolean isHeadShot,
                                   float headshotMultiplier) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageGunHurt> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_gun_hurt"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageGunHurt> STREAM_CODEC = StreamCodec.of(
            (buf, message) -> {
                buf.writeInt(message.bulletId());
                buf.writeInt(message.hurtEntityId());
                buf.writeInt(message.attackerId());
                ResourceLocation.STREAM_CODEC.encode(buf, message.gunId());
                ResourceLocation.STREAM_CODEC.encode(buf, message.gunDisplayId());
                buf.writeFloat(message.amount());
                buf.writeBoolean(message.isHeadShot());
                buf.writeFloat(message.headshotMultiplier());
            },
            buf -> {
                int bulletId = buf.readInt();
                int hurtEntityId = buf.readInt();
                int attackerId = buf.readInt();
                ResourceLocation gunId = ResourceLocation.STREAM_CODEC.decode(buf);
                ResourceLocation gunDisplayId = ResourceLocation.STREAM_CODEC.decode(buf);
                float amount = buf.readFloat();
                boolean isHeadShot = buf.readBoolean();
                float headshotMultiplier = buf.readFloat();
                return new ServerMessageGunHurt(bulletId, hurtEntityId, attackerId, gunId, gunDisplayId, amount, isHeadShot, headshotMultiplier);
            }
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageGunHurt message, IPayloadContext context) {
        context.enqueueWork(() -> onHurt(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHurt(ServerMessageGunHurt message) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        @Nullable Entity bullet = level.getEntity(message.bulletId);
        @Nullable Entity hurtEntity = level.getEntity(message.hurtEntityId);
        @Nullable LivingEntity attacker = level.getEntity(message.attackerId) instanceof LivingEntity livingEntity ? livingEntity : null;
        NeoForge.EVENT_BUS.post(new EntityHurtByGunEvent.Post(bullet, hurtEntity, attacker, message.gunId, message.gunDisplayId, message.amount, null, message.isHeadShot, message.headshotMultiplier, LogicalSide.CLIENT));
    }
}