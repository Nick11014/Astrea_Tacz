package com.tacz.guns.network.message.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
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

public record ServerMessageGunHurt(int bulletId, int hurtEntityId, int attackerId, ResourceLocation gunId,
                                   ResourceLocation gunDisplayId,
                                   float amount, boolean isHeadShot,
                                   float headshotMultiplier) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(GunMod.MOD_ID, "server_gun_hurt");

    public ServerMessageGunHurt(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readInt(), buf.readResourceLocation(), buf.readResourceLocation(), buf.readFloat(), buf.readBoolean(), buf.readFloat());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(bulletId);
        buf.writeInt(hurtEntityId);
        buf.writeInt(attackerId);
        buf.writeResourceLocation(gunId);
        buf.writeResourceLocation(gunDisplayId);
        buf.writeFloat(amount);
        buf.writeBoolean(isHeadShot);
        buf.writeFloat(headshotMultiplier);
    }

    @Override
    public ResourceLocation type() {
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
