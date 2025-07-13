package com.tacz.guns.network.message;

import com.tacz.guns.GunMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerMessageLevelUp(ItemStack gun, int level) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(GunMod.MOD_ID, "server_level_up");

    public ServerMessageLevelUp(FriendlyByteBuf buf) {
        this(ItemStack.STREAM_CODEC.decode(buf), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        ItemStack.STREAM_CODEC.encode(buf, gun);
        buf.writeInt(level);
    }

    @Override
    public ResourceLocation type() {
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
        // TODO: Implementar a lógica de toast de level up quando o sistema de nível estiver pronto.
    }
}
