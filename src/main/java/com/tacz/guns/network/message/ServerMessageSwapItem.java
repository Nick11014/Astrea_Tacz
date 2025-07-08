package com.tacz.guns.network.message;

import com.tacz.guns.api.client.event.SwapItemWithOffHand;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerMessageSwapItem {
    public ServerMessageSwapItem() {
    }

    public static void encode(ServerMessageSwapItem message, FriendlyByteBuf buf) {
    }

    public static ServerMessageSwapItem decode(FriendlyByteBuf buf) {
        return new ServerMessageSwapItem();
    }

    public static void handle(ServerMessageSwapItem message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isClientbound()) {
            NeoForge.EVENT_BUS.post(new SwapItemWithOffHand());
        }
    }
}
