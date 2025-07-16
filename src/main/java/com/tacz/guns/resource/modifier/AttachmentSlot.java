package com.tacz.guns.resource.modifier;

import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentType;
import net.minecraft.world.item.ItemStack;

public class AttachmentSlot {
    private final AttachmentType type;

    public AttachmentSlot(AttachmentType type) {
        this.type = type;
    }

    public AttachmentType getType() {
        return type;
    }
}