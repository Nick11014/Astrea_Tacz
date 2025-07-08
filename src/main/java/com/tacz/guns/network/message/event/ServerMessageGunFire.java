package com.tacz.guns.network.message.event;

import com.tacz.guns.api.event.common.GunFireEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerMessageGunFire {
    private final int shooterId;
    private final ItemStack gunItemStack;

    public ServerMessageGunFire(int shooterId, ItemStack gunItemStack) {
        this.shooterId = shooterId;
        this.gunItemStack = gunItemStack;
    }

    public static void encode(ServerMessageGunFire message, FriendlyByteBuf buf) {
        buf.writeVarInt(message.shooterId);
        // TODO: Migração NeoForge 1.21.1 - writeItem() mudou APIs
        // buf.writeItem(message.gunItemStack);
        buf.writeBoolean(false); // placeholder para gunItemStack
    }

    public static ServerMessageGunFire decode(FriendlyByteBuf buf) {
        int shooterId = buf.readVarInt();
        // TODO: Migração NeoForge 1.21.1 - readItem() mudou APIs
        // ItemStack gunItemStack = buf.readItem();
        buf.readBoolean(); // placeholder para gunItemStack
        return new ServerMessageGunFire(shooterId, ItemStack.EMPTY);
    }

    public static void handle(ServerMessageGunFire message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> doClientEvent(message));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void doClientEvent(ServerMessageGunFire message) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        if (level.getEntity(message.shooterId) instanceof LivingEntity shooter) {
            GunFireEvent gunFireEvent = new GunFireEvent(shooter, message.gunItemStack, LogicalSide.CLIENT);
            NeoForge.EVENT_BUS.post(gunFireEvent);
        }
    }
}
