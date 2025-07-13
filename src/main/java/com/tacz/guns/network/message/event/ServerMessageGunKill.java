package com.tacz.guns.network.message.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
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
    public static final ResourceLocation TYPE = new ResourceLocation(GunMod.MOD_ID, "server_gun_kill");

    public ServerMessageGunKill(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readResourceLocation(), buf.readResourceLocation(), buf.readFloat(), buf.readBoolean(), buf.readFloat());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(bulletId);
        buf.writeInt(killEntityId);
        buf.writeInt(attackerId);
        buf.writeResourceLocation(gunId);
        buf.writeResourceLocation(gunDisplayId);
        buf.writeFloat(baseDamage);
        buf.writeBoolean(isHeadShot);
        buf.writeFloat(headshotMultiplier);
    }

    @Override
    public ResourceLocation type() {
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
