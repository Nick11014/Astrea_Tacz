package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessagePlayerBoltGun() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientMessagePlayerBoltGun> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_player_bolt_gun"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessagePlayerBoltGun> STREAM_CODEC = StreamCodec.unit(new ClientMessagePlayerBoltGun());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientMessagePlayerBoltGun message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        IGunOperator.fromLivingEntity(player).bolt();
    }
}































































