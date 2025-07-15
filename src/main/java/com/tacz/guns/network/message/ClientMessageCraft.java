package com.tacz.guns.network.message;

import com.tacz.guns.inventory.GunSmithTableMenu;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessageCraft(ResourceLocation recipeId, int menuId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientMessageCraft> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_craft"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessageCraft> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC, ClientMessageCraft::recipeId,
        ByteBufCodecs.VAR_INT, ClientMessageCraft::menuId,
        ClientMessageCraft::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientMessageCraft message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        if (player.containerMenu.containerId == message.menuId && player.containerMenu instanceof GunSmithTableMenu menu) {
            menu.doCraft(message.recipeId, player);
        }
    }
}































































