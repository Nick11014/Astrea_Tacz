package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientMessagePlayerCancelReload {
    public ClientMessagePlayerCancelReload() {
    }

    public static void encode(ClientMessagePlayerCancelReload message, FriendlyByteBuf buf) {
    }

    public static ClientMessagePlayerCancelReload decode(FriendlyByteBuf buf) {
        return new ClientMessagePlayerCancelReload();
    }

    public static void handle(ClientMessagePlayerCancelReload message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer entity = context.player() instanceof ServerPlayer player ? player : null;
                if (entity == null) {
                    return;
                }
                IGunOperator.fromLivingEntity(entity).cancelReload();
            });
        }
    }
}
