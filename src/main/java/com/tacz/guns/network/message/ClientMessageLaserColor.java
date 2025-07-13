package com.tacz.guns.network.message;

import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessageLaserColor(Map<AttachmentType, Integer> colorMap, boolean applyGunColor, int gunColor,
                                      int gunSlotIndex) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = new ResourceLocation(MOD_ID, "client_laser_color");

    public ClientMessageLaserColor(@NotNull ItemStack gun, int gunSlotIndex) {
        this(new HashMap<>(), false, 0, -1);
        if (gun.getItem() instanceof IGun iGun) {
            for (AttachmentType type : AttachmentType.values()) {
                ItemStack attachment = iGun.getAttachment(gun, type);
                if (attachment.getItem() instanceof IAttachment iAttachment) {
                    if (iAttachment.hasCustomLaserColor(attachment)) {
                        colorMap.put(type, iAttachment.getLaserColor(attachment));
                    }
                }
            }
            if (iGun.hasCustomLaserColor(gun)) {
                this.gunColor = iGun.getLaserColor(gun);
                this.applyGunColor = true;
            }
            this.gunSlotIndex = gunSlotIndex;
        }
    }

    public ClientMessageLaserColor(FriendlyByteBuf buf) {
        this(buf.readMap(HashMap::new, b -> b.readEnum(AttachmentType.class), FriendlyByteBuf::readInt),
                buf.readBoolean(), buf.readInt(), buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeMap(colorMap, (b, t) -> b.writeEnum(t), FriendlyByteBuf::writeInt);
        buf.writeBoolean(applyGunColor);
        buf.writeInt(gunColor);
        buf.writeInt(gunSlotIndex);
    }

    @Override
    public ResourceLocation type() {
        return TYPE;
    }

    public static void handle(ClientMessageLaserColor message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null || message.gunSlotIndex == -1) {
            return;
        }
        Inventory inventory = player.getInventory();
        ItemStack gunItem = inventory.getItem(message.gunSlotIndex);
        IGun iGun = IGun.getIGunOrNull(gunItem);
        if (iGun != null) {
            for (var entry : message.colorMap.entrySet()) {
                AttachmentType type = entry.getKey();
                int color = entry.getValue();
                ItemStack attachment = iGun.getAttachment(gunItem, type);
                if (attachment.getItem() instanceof IAttachment iAttachment) {
                    iAttachment.setLaserColor(attachment, color);
                }
            }
            if (message.applyGunColor) {
                iGun.setLaserColor(gunItem, message.gunColor);
            }
        }
    }
}
