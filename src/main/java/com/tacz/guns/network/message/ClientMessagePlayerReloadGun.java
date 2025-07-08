package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientMessagePlayerReloadGun {
    public ClientMessagePlayerReloadGun() {
    }

    public static void encode(ClientMessagePlayerReloadGun message, FriendlyByteBuf buf) {
    }

    public static ClientMessagePlayerReloadGun decode(FriendlyByteBuf buf) {
        return new ClientMessagePlayerReloadGun();
    }

    public static void handle(ClientMessagePlayerReloadGun message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer entity = context.player() instanceof ServerPlayer player ? player : null;
                if (entity == null) {
                    return;
                }
                IGunOperator.fromLivingEntity(entity).reload();
            });
        }
    }
}
