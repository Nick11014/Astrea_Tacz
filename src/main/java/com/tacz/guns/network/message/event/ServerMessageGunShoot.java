package com.tacz.guns.network.message.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.GunShootEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerMessageGunShoot(int shooterId, ItemStack gunItemStack) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_gun_shoot");

    public ServerMessageGunShoot(FriendlyByteBuf buf) {
        this(buf.readVarInt(), ItemStack.STREAM_CODEC.decode(buf));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(shooterId);
        ItemStack.STREAM_CODEC.encode(buf, gunItemStack);
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(ServerMessageGunShoot message, IPayloadContext context) {
        context.enqueueWork(() -> doClientEvent(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void doClientEvent(ServerMessageGunShoot message) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        if (level.getEntity(message.shooterId) instanceof LivingEntity shooter) {
            GunShootEvent gunShootEvent = new GunShootEvent(shooter, message.gunItemStack, LogicalSide.CLIENT);
            NeoForge.EVENT_BUS.post(gunShootEvent);
        }
    }
}































































