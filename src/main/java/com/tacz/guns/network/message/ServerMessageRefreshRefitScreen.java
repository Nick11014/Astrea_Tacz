package com.tacz.guns.network.message;

import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ServerMessageRefreshRefitScreen() implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(MOD_ID, "server_refresh_refit_screen");

    public ServerMessageRefreshRefitScreen(FriendlyByteBuf buf) {
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

    public static void handle(ServerMessageRefreshRefitScreen message, IPayloadContext context) {
        context.enqueueWork(ServerMessageRefreshRefitScreen::updateScreen);
    }

    @OnlyIn(Dist.CLIENT)
    private static void updateScreen() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && Minecraft.getInstance().screen instanceof GunRefitScreen screen) {
            screen.init();
            // 刷新配件数据，客户端的
            AttachmentPropertyManager.postChangeEvent(player, player.getMainHandItem());
        }
    }
}
