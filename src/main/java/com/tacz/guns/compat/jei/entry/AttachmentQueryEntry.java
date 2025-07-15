package com.tacz.guns.compat.jei.entry;

import com.google.common.collect.Lists;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.builder.GunItemBuilder;
import com.tacz.guns.compat.jei.category.AttachmentQueryCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Locale;

public class AttachmentQueryEntry {
    /**
     * ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶
     */
    private final ItemStack attachmentStack;
    /**
     * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°
     */
    private List<ItemStack> allowGunStacks;
    /**
     * ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ©Ã¢â‚¬Â¡Ã…â€™
     */
    private List<ItemStack> extraAllowGunStacks;

    public AttachmentQueryEntry(ResourceLocation attachmentId, GunTabType type) {
        this.attachmentStack = AttachmentItemBuilder.create().setId(attachmentId).build();
        this.allowGunStacks = Lists.newArrayList();
        this.extraAllowGunStacks = Lists.newArrayList();
        this.addAllAllowGuns(type);
        this.dividedGuns();
    }

    public static List<AttachmentQueryEntry> getAllAttachmentQueryEntries() {
        List<AttachmentQueryEntry> entries = Lists.newArrayList();
        TimelessAPI.getAllAttachments().forEach(entry -> {
            if (entry.getValue().getPojo().isHidden()) {
                return;
            }
            for (GunTabType tabType : GunTabType.values()) {
                AttachmentQueryEntry queryEntry = new AttachmentQueryEntry(entry.getKey(), tabType);
                if (!queryEntry.getAllowGunStacks().isEmpty()) {
                    entries.add(queryEntry);
                }
            }
        });
        return entries;
    }

    public ItemStack getAttachmentStack() {
        return attachmentStack;
    }

    public List<ItemStack> getAllowGunStacks() {
        return allowGunStacks;
    }

    public List<ItemStack> getExtraAllowGunStacks() {
        return extraAllowGunStacks;
    }

    private void addAllAllowGuns(GunTabType type) {
        TimelessAPI.getAllCommonGunIndex().forEach(entry -> {
            String tabType = type.name().toLowerCase(Locale.US);
            String gunType = entry.getValue().getType();
            if (tabType.equals(gunType)) {
                ItemStack gun = GunItemBuilder.create().setId(entry.getKey()).build();
                if (!(gun.getItem() instanceof IGun iGun)) {
                    return;
                }
                if (iGun.allowAttachment(gun, this.attachmentStack)) {
                    this.allowGunStacks.add(gun);
                }
            }
        });
    }

    private void dividedGuns() {
        int size = this.allowGunStacks.size();
        if (size >= AttachmentQueryCategory.MAX_GUN_SHOW_COUNT) {
            this.extraAllowGunStacks = this.allowGunStacks.subList(AttachmentQueryCategory.MAX_GUN_SHOW_COUNT, size);
            this.allowGunStacks = this.allowGunStacks.subList(0, AttachmentQueryCategory.MAX_GUN_SHOW_COUNT);
        }
    }
}































































