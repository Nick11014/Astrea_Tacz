package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.config.sync.SyncConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientMessagePlayerCrawl {
    private final boolean isCrawl;

    public ClientMessagePlayerCrawl(boolean isCrawl) {
        this.isCrawl = isCrawl;
    }

    public static void encode(ClientMessagePlayerCrawl message, FriendlyByteBuf buf) {
        buf.writeBoolean(message.isCrawl);
    }

    public static ClientMessagePlayerCrawl decode(FriendlyByteBuf buf) {
        return new ClientMessagePlayerCrawl(buf.readBoolean());
    }

    public static void handle(ClientMessagePlayerCrawl message, IPayloadContext context) {
        // Migração NeoForge 1.21.1: NetworkEvent.Context → IPayloadContext
        if (context.flow().isServerbound()) {
            context.enqueueWork(() -> {
                ServerPlayer entity = context.player() instanceof ServerPlayer player ? player : null;
                if (entity == null) {
                    return;
                }
                if (!SyncConfig.ENABLE_CRAWL.get()) {
                    return;
                }
                IGunOperator.fromLivingEntity(entity).crawl(message.isCrawl);
            });
        }
    }
}
