package com.tacz.guns.util;

import com.tacz.guns.resource.CommonAssetsManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AllowAttachmentTagMatcher {
    private static final String TAG_PREFIX = "#";

    public static boolean match(ResourceLocation gunId, ResourceLocation attachmentId) {
        Set<String> allowAttachmentTags = CommonAssetsManager.get().getAllowAttachmentTags(gunId);
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ allowAttachmentTags ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â´ÃƒÂ¦Ã‹Å“Ã…Â½ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â¢ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶
        if (allowAttachmentTags == null || allowAttachmentTags.isEmpty()) {
            return false;
        }
        // ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ©Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â  allowAttachmentTagsÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¯Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ id
        AtomicBoolean searchSignal = new AtomicBoolean(false);
        treeSearch(allowAttachmentTags, attachmentId, searchSignal);
        return searchSignal.get();
    }

    private static void treeSearch(Set<String> tags, ResourceLocation attachmentId, AtomicBoolean searchSignal) {
        // ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ©Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â  tagsÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¯Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ id
        for (String tag : tags) {
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ tagÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã…Â½Ã‚Â» attachment tag ÃƒÂ¥Ã‚Â¯Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â¾ÃƒÂ¦Ã‹â€ Ã¢â‚¬ËœÃƒÂ¤Ã‚Â»Ã‚Â¬ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã…â€œÃƒÂ¨Ã‚Â¥Ã‚Â¿
            if (tag.startsWith(TAG_PREFIX)) {
                String tagString = tag.substring(TAG_PREFIX.length());
                ResourceLocation tagId;
                if (tagString.contains(":")) {
                    String[] parts = tagString.split(":", 2);
                    tagId = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
                } else {
                    tagId = ResourceLocation.fromNamespaceAndPath("minecraft", tagString);
                }
                Set<String> attachmentTags = CommonAssetsManager.get().getAttachmentTags(tagId);
                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ§Ã‚Â´Ã‚Â¢ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ tag ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â½Ã¢â‚¬â„¢ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â¾
                if (attachmentTags != null && !attachmentTags.isEmpty()) {
                    treeSearch(attachmentTags, attachmentId, searchSignal);
                }
            }
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ idÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¦Ã‚Â¯Ã¢â‚¬Â
            else {
                ResourceLocation matchAttachmentId = ResourceLocation.parse(tag);
                if (attachmentId.equals(matchAttachmentId)) {
                    searchSignal.set(true);
                    return;
                }
            }
        }
    }
}































































