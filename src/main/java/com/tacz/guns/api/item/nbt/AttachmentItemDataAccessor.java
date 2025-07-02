package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.init.ModDataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public interface AttachmentItemDataAccessor extends IAttachment {
    String ATTACHMENT_ID_TAG = "AttachmentId";
    String SKIN_ID_TAG = "Skin";
    String ZOOM_NUMBER_TAG = "ZoomNumber";
    String LASER_COLOR_TAG = "LaserColor";

    // 仅检查给定的 CompoundTag 是否具有配件 ID ，不校验其是否存在
    static boolean isAttachmentLike(CompoundTag tag) {
        return tag.contains(ATTACHMENT_ID_TAG, Tag.TAG_STRING);
    }

    @Nonnull
    static ResourceLocation getAttachmentIdFromTag(@Nullable CompoundTag nbt) {
        if (nbt == null) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID;
        }
        if (isAttachmentLike(nbt)) {
            ResourceLocation attachmentId = ResourceLocation.tryParse(nbt.getString(ATTACHMENT_ID_TAG));
            return Objects.requireNonNullElse(attachmentId, DefaultAssets.EMPTY_ATTACHMENT_ID);
        }
        return DefaultAssets.EMPTY_ATTACHMENT_ID;
    }

    /**
     * Método para migração de dados antigos NBT para DataComponents
     */
    @Nonnull
    static ResourceLocation getAttachmentIdFromStack(ItemStack stack) {
        // Primeiro tenta pegar do DataComponent (novo sistema)
        if (stack.has(ModDataComponents.ATTACHMENT_ID.get())) {
            ResourceLocation attachmentId = stack.get(ModDataComponents.ATTACHMENT_ID.get());
            return Objects.requireNonNullElse(attachmentId, DefaultAssets.EMPTY_ATTACHMENT_ID);
        }
        // Fallback para NBT (dados antigos)
        CompoundTag nbt = stack.getTag();
        return getAttachmentIdFromTag(nbt);
    }

    static int getZoomNumberFromTag(@Nullable CompoundTag nbt) {
        if (nbt == null) {
            return 0;
        }
        if (nbt.contains(ZOOM_NUMBER_TAG, Tag.TAG_INT)) {
            return nbt.getInt(ZOOM_NUMBER_TAG);
        }
        return 0;
    }

    static void setZoomNumberToTag(CompoundTag nbt, int zoomNumber) {
        nbt.putInt(ZOOM_NUMBER_TAG, zoomNumber);
    }

    /**
     * Método para migração de dados antigos NBT para DataComponents
     */
    static int getZoomNumberFromStack(ItemStack stack) {
        // Primeiro tenta pegar do DataComponent (novo sistema)
        if (stack.has(ModDataComponents.ZOOM_NUMBER.get())) {
            return stack.getOrDefault(ModDataComponents.ZOOM_NUMBER.get(), 0);
        }
        // Fallback para NBT (dados antigos)
        CompoundTag nbt = stack.getTag();
        return getZoomNumberFromTag(nbt);
    }    @Override
    @Nonnull
    default ResourceLocation getAttachmentId(ItemStack attachmentStack) {
        ResourceLocation attachmentId = attachmentStack.get(ModDataComponents.ATTACHMENT_ID.get());
        return Objects.requireNonNullElse(attachmentId, DefaultAssets.EMPTY_ATTACHMENT_ID);
    }

    @Override
    default void setAttachmentId(ItemStack attachmentStack, @Nullable ResourceLocation attachmentId) {
        if (attachmentId != null) {
            attachmentStack.set(ModDataComponents.ATTACHMENT_ID.get(), attachmentId);
        } else {
            attachmentStack.remove(ModDataComponents.ATTACHMENT_ID.get());
        }
    }

    @Override
    @Nullable
    default ResourceLocation getSkinId(ItemStack attachmentStack) {
        return attachmentStack.get(ModDataComponents.SKIN_ID.get());
    }

    @Override
    default void setSkinId(ItemStack attachmentStack, @Nullable ResourceLocation skinId) {
        if (skinId != null) {
            attachmentStack.set(ModDataComponents.SKIN_ID.get(), skinId);
        } else {
            attachmentStack.remove(ModDataComponents.SKIN_ID.get());
        }
    }

    @Override
    default int getZoomNumber(ItemStack attachmentStack) {
        return attachmentStack.getOrDefault(ModDataComponents.ZOOM_NUMBER.get(), 0);
    }

    @Override
    default void setZoomNumber(ItemStack attachmentStack, int zoomNumber) {
        attachmentStack.set(ModDataComponents.ZOOM_NUMBER.get(), zoomNumber);
    }    @Override
    default boolean hasCustomLaserColor(ItemStack attachmentStack) {
        return attachmentStack.has(ModDataComponents.LASER_COLOR.get());
    }

    @Override
    default int getLaserColor(ItemStack attachmentStack) {
        if (!hasCustomLaserColor(attachmentStack)) {
            return 0xFF0000;
        }
        return attachmentStack.getOrDefault(ModDataComponents.LASER_COLOR.get(), 0xFF0000);
    }

    @Override
    default void setLaserColor(ItemStack attachmentStack, int color) {
        attachmentStack.set(ModDataComponents.LASER_COLOR.get(), color);
    }
}
