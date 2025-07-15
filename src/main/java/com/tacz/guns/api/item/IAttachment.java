package com.tacz.guns.api.item;

import com.tacz.guns.api.item.attachment.AttachmentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IAttachment {
    /**
     * @return ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â¸Ã‚Âº IAttachment ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ nullÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    @Nullable
    static IAttachment getIAttachmentOrNull(@Nullable ItemStack stack) {
        if (stack == null) {
            return null;
        }
        if (stack.getItem() instanceof IAttachment iAttachment) {
            return iAttachment;
        }
        return null;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID
     */
    @Nonnull
    ResourceLocation getAttachmentId(ItemStack attachmentStack);

    /**
     * ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID
     */
    void setAttachmentId(ItemStack attachmentStack, @Nullable ResourceLocation attachmentId);

    /**@deprecated
     */
    @Deprecated
    @Nullable
    ResourceLocation getSkinId(ItemStack attachmentStack);

    /**@deprecated
     */
    @Deprecated
    void setSkinId(ItemStack attachmentStack, @Nullable ResourceLocation skinId);

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã…Â½Ã¢â‚¬Â¡ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ§Ã‚Â´Ã‚Â¢ÃƒÂ¥Ã‚Â¼Ã¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨
     */
    int getZoomNumber(ItemStack attachmentStack);

    /**
     * ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã…Â½Ã¢â‚¬Â¡ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ§Ã‚Â´Ã‚Â¢ÃƒÂ¥Ã‚Â¼Ã¢â‚¬Â¢
     */
    void setZoomNumber(ItemStack attachmentStack, int zoomNumber);

    /**
     * ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
     */
    @Nonnull
    AttachmentType getType(ItemStack attachmentStack);

    boolean hasCustomLaserColor(ItemStack attachmentStack);

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ©Ã¢â‚¬Â¢Ã‚Â­ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¥Ã¢â‚¬Â¦Ã¢â‚¬Â°ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²
     * @return ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â­ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¯Ã‚Â¼Ã…â€™RGB
     */
    int getLaserColor(ItemStack attachmentStack);

    void setLaserColor(ItemStack attachmentStack, int color);

    public class Slot<T> {
    }
}































































