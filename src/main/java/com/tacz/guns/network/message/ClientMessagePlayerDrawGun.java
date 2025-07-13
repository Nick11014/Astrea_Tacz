package com.tacz.guns.network.message;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessagePlayerDrawGun() implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(MOD_ID, "client_player_draw_gun");

    public ClientMessagePlayerDrawGun(FriendlyByteBuf buf) {
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
