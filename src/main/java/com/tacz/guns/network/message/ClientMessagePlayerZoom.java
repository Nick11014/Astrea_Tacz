package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientMessagePlayerZoom {
    public static void encode(ClientMessagePlayerZoom message, FriendlyByteBuf buf) {
    }

    public static ClientMessagePlayerZoom decode(FriendlyByteBuf buf) {
        return new ClientMessagePlayerZoom();
    }

    public static void handle(ClientMessagePlayerZoom message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer entity = context.player() instanceof ServerPlayer player ? player : null;
                if (entity == null) {
                    return;
                }
                IGunOperator.fromLivingEntity(entity).zoom();
            });
        }
    }
}
