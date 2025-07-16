package com.tacz.guns.network.message;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessageUnloadAttachment(int gunSlotIndex,
                                            AttachmentType attachmentType) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClientMessageUnloadAttachment> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_unload_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientMessageUnloadAttachment> STREAM_CODEC = StreamCodec.composite(
        net.minecraft.network.codec.ByteBufCodecs.VAR_INT.fieldOf(ClientMessageUnloadAttachment::gunSlotIndex),
        ByteBufCodecs.fromEnum(AttachmentType::values).fieldOf(ClientMessageUnloadAttachment::attachmentType),
        ClientMessageUnloadAttachment::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(ClientMessageUnloadAttachment message, IPayloadContext context) {
        ServerPlayer player = (ServerPlayer) NetworkHandler.getPlayer(context);
        if (player == null) {
            return;
        }
        Inventory inventory = player.getInventory();
        ItemStack gunItem = inventory.getItem(message.gunSlotIndex);
        IGun iGun = IGun.getIGunOrNull(gunItem);
        if (iGun != null) {
            ItemStack attachmentItem = iGun.getAttachment(gunItem, message.attachmentType);
            if (!attachmentItem.isEmpty() && inventory.add(attachmentItem)) {
                iGun.unloadAttachment(gunItem, message.attachmentType);
                AttachmentPropertyManager.postChangeEvent(player, gunItem);
                if (message.attachmentType == AttachmentType.EXTENDED_MAG) {
                    iGun.dropAllAmmo(player, gunItem);
                }
                player.inventoryMenu.broadcastChanges();
                NetworkHandler.sendToClientPlayer(new ServerMessageRefreshRefitScreen(), player);
            }
        }
    }
}
