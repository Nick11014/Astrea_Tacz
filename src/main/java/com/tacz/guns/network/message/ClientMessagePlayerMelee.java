package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessagePlayerMelee() implements CustomPacketPayload {
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_player_melee");

    public ClientMessagePlayerMelee(FriendlyByteBuf buf) {
        this();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        // No data to write
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(ClientMessagePlayerMelee message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        IGunOperator.fromLivingEntity(player).melee();
    }
}































































