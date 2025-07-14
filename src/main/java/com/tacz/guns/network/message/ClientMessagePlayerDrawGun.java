package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessagePlayerDrawGun() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientMessagePlayerDrawGun> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_player_draw_gun"));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessagePlayerDrawGun> STREAM_CODEC = StreamCodec.unit(new ClientMessagePlayerDrawGun());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientMessagePlayerDrawGun message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        Inventory inventory = player.getInventory();
        int selected = inventory.selected;
        IGunOperator.fromLivingEntity(player).draw(() -> inventory.getItem(selected));
    }
}































































