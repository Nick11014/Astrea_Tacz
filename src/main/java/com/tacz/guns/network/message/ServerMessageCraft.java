package com.tacz.guns.network.message;

import com.tacz.guns.client.gui.GunSmithTableScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ServerMessageCraft(int menuId) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(MOD_ID, "server_craft");

    public ServerMessageCraft(FriendlyByteBuf buf) {
        this(buf.readVarInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(menuId);
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(ServerMessageCraft message, IPayloadContext context) {
        context.enqueueWork(() -> updateScreen(message.menuId));
    }

    @OnlyIn(Dist.CLIENT)
    private static void updateScreen(int containerId) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && player.containerMenu.containerId == containerId && Minecraft.getInstance().screen instanceof GunSmithTableScreen screen) {
            screen.updateIngredientCount();
        }
    }
}
