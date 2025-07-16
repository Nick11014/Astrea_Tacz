package com.tacz.guns.network.message;

import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.network.NetworkHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
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
    public static final CustomPacketPayload.Type<ClientMessageLaserColor> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_laser_color"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessageLaserColor> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.fromEnum(AttachmentType::values), ByteBufCodecs.INT).fieldOf(ClientMessageLaserColor::colorMap),
            ByteBufCodecs.BOOL.fieldOf(ClientMessageLaserColor::applyGunColor),
            ByteBufCodecs.INT.fieldOf(ClientMessageLaserColor::gunColor),
            ByteBufCodecs.INT.fieldOf(ClientMessageLaserColor::gunSlotIndex),
            ClientMessageLaserColor::new
    );

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

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
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































































