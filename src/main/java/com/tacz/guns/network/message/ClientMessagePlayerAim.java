package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessagePlayerAim(boolean isAim) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientMessagePlayerAim> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_player_aim"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessagePlayerAim> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, ClientMessagePlayerAim::isAim,
        ClientMessagePlayerAim::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientMessagePlayerAim message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        IGunOperator.fromLivingEntity(player).aim(message.isAim);
    }
}































































