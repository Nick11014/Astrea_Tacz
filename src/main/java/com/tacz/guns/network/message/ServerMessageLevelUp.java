package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerMessageLevelUp(ItemStack gun, int level) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ServerMessageLevelUp> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "server_level_up"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerMessageLevelUp> STREAM_CODEC = StreamCodec.composite(
        ItemStack.STREAM_CODEC, ServerMessageLevelUp::gun,
        StreamCodec.INT, ServerMessageLevelUp::level,
        ServerMessageLevelUp::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ServerMessageLevelUp message, IPayloadContext context) {
        context.enqueueWork(() -> onLevelUp(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void onLevelUp(ServerMessageLevelUp message) {
        int level = message.level();
        ItemStack gun = message.gun();
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        // TODO: Implementar a lÃƒÆ’Ã‚Â³gica de toast de level up quando o sistema de nÃƒÆ’Ã‚Â­vel estiver pronto.
    }
}































































