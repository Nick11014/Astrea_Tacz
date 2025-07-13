package com.tacz.guns.network.message;

import com.tacz.guns.api.client.event.SwapItemWithOffHand;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ServerMessageSwapItem() implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(MOD_ID, "server_swap_item");

    public ServerMessageSwapItem(FriendlyByteBuf buf) {
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

    public static void handle(ServerMessageSwapItem message, IPayloadContext context) {
        context.enqueueWork(() -> NeoForge.EVENT_BUS.post(new SwapItemWithOffHand()));
    }
}
