package com.tacz.guns.network.message;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.tacz.guns.GunMod.MOD_ID;

public record ClientMessageUnloadAttachment(int gunSlotIndex,
                                            AttachmentType attachmentType) implements CustomPacketPayload {
    public static final ResourceLocation TYPE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "client_unload_attachment");

    public ClientMessageUnloadAttachment(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readEnum(AttachmentType.class));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(gunSlotIndex);
        buf.writeEnum(attachmentType);
    }

    @Override
    public ResourceLocation type() {
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
                // ÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
                AttachmentPropertyManager.postChangeEvent(player, gunItem);
                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚ÂÃ‚Â¸ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
                if (message.attachmentType == AttachmentType.EXTENDED_MAG) {
                    iGun.dropAllAmmo(player, gunItem);
                }
                player.inventoryMenu.broadcastChanges();
                NetworkHandler.sendToClientPlayer(new ServerMessageRefreshRefitScreen(), player);
            }
        }
    }
}































































