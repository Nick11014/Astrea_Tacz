package com.tacz.guns.util;

import com.tacz.guns.resource.CommonAssetsManager;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AllowAttachmentTagMatcher {
    private static final String TAG_PREFIX = "#";

    public static boolean match(ResourceLocation gunId, ResourceLocation attachmentId) {
        Set<String> allowAttachmentTags = CommonAssetsManager.get().getAllowAttachmentTags(gunId);
        if (allowAttachmentTags == null || allowAttachmentTags.isEmpty()) {
            return false;
        }
        AtomicBoolean searchSignal = new AtomicBoolean(false);
        treeSearch(allowAttachmentTags, attachmentId, searchSignal);
        return searchSignal.get();
    }

    private static void treeSearch(Set<String> tags, ResourceLocation attachmentId, AtomicBoolean searchSignal) {
        for (String tag : tags) {
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
                if (attachmentTags != null && !attachmentTags.isEmpty()) {
                    treeSearch(attachmentTags, attachmentId, searchSignal);
                }
            }
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































































