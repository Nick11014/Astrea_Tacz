package com.tacz.guns.network.message.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.event.common.GunDrawEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ServerMessageGunDraw(int entityId, ItemStack previousGunItem,
                                   ItemStack currentGunItem) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(GunMod.MOD_ID, "server_gun_draw");

    public ServerMessageGunDraw(FriendlyByteBuf buf) {
        this(buf.readVarInt(), ItemStack.STREAM_CODEC.decode(buf), ItemStack.STREAM_CODEC.decode(buf));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(entityId);
        ItemStack.STREAM_CODEC.encode(buf, previousGunItem);
        ItemStack.STREAM_CODEC.encode(buf, currentGunItem);
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(ServerMessageGunDraw message, IPayloadContext context) {
        context.enqueueWork(() -> doClientEvent(message));
    }

    @OnlyIn(Dist.CLIENT)
    private static void doClientEvent(ServerMessageGunDraw message) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        if (level.getEntity(message.entityId) instanceof LivingEntity livingEntity) {
            GunDrawEvent gunDrawEvent = new GunDrawEvent(livingEntity, message.previousGunItem, message.currentGunItem, LogicalSide.CLIENT);
            NeoForge.EVENT_BUS.post(gunDrawEvent);
        }
    }
}
