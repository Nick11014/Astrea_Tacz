package com.tacz.guns.network.message.event;

import com.tacz.guns.api.event.common.GunDrawEvent;
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

public class ServerMessageGunDraw {
    private final int entityId;
    private final ItemStack previousGunItem;
    private final ItemStack currentGunItem;

    public ServerMessageGunDraw(int entityId, ItemStack previousGunItem, ItemStack currentGunItem) {
        this.entityId = entityId;
        this.previousGunItem = previousGunItem;
        this.currentGunItem = currentGunItem;
    }

    public static void encode(ServerMessageGunDraw message, FriendlyByteBuf buf) {
        buf.writeVarInt(message.entityId);
        // TODO: Migração NeoForge 1.21.1 - writeItem() mudou APIs
        // buf.writeItem(message.previousGunItem);
        // buf.writeItem(message.currentGunItem);
        buf.writeBoolean(false); // placeholder para previousGunItem
        buf.writeBoolean(false); // placeholder para currentGunItem
    }

    public static ServerMessageGunDraw decode(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        // TODO: Migração NeoForge 1.21.1 - readItem() mudou APIs
        // ItemStack previousGunItem = buf.readItem();
        // ItemStack currentGunItem = buf.readItem();
        buf.readBoolean(); // placeholder para previousGunItem
        buf.readBoolean(); // placeholder para currentGunItem
        return new ServerMessageGunDraw(entityId, ItemStack.EMPTY, ItemStack.EMPTY);
    }

    public static void handle(ServerMessageGunDraw message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isClientbound()) {
            context.enqueueWork(() -> doClientEvent(message));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void doClientEvent(ServerMessageGunDraw message) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        if (level.getEntity(message.entityId) instanceof LivingEntity livingEntity) {
            GunDrawEvent gunDrawEvent = new GunDrawEvent(livingEntity, message.previousGunItem, message.currentGunItem, LogicalSide.CLIENT);
            NeoForge.EVENT_BUS.post(gunDrawEvent);
        }
    }
}
