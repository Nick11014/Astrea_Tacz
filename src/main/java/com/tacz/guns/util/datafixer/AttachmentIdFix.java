package com.tacz.guns.util.datafixer;

import com.google.common.collect.ImmutableMap;
import com.tacz.guns.api.DefaultAssets;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

import static com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor.*;
import static com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor.ATTACHMENT_ID_TAG;

public final class AttachmentIdFix {
    private AttachmentIdFix() {
    }

    public static final Map<ResourceLocation, ResourceLocation> OLD_TO_NEW;
    static  {
        OLD_TO_NEW = ImmutableMap.<ResourceLocation, ResourceLocation>builder()
                .put(ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silence_knight_qd"), ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silencer_knight_qd"))
                .put(ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silence_mirage"), ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silencer_mirage"))
                .put(ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silence_phantom_s1"), ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silencer_phantom_s1"))
                .put(ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silence_ptilopsis"), ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silencer_ptilopsis"))
                .put(ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silence_ursus"), ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silencer_ursus"))
                .put(ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silence_vulture"), ResourceLocation.fromNamespaceAndPath("tacz", "muzzle_silencer_vulture"))
                .build();
    }

    // ÃƒÂ©Ã‚Â¢Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â¢Ã¢â€žÂ¢ boolean ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã…â€œÃ‚ÂªÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™"ÃƒÂ¥Ã‚Â°Ã‚Â½ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ©Ã‚ÂÃ‚Â¿ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ void ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬Å“Ã‚ÂÃƒÂ¤Ã‚Â½Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¤Ã‚Â¾Ã¢â‚¬ÂºÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â¢ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¿Ã‚Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¯"
    public static boolean updateAttachmentIdInTag(CompoundTag tag) {
        ResourceLocation old = getAttachmentIdFromTag(tag);
        if (!old.equals(DefaultAssets.EMPTY_ATTACHMENT_ID)) {
            ResourceLocation fixed = updateAttachmentId(old);
            if (!old.equals(fixed)) {
                tag.putString(ATTACHMENT_ID_TAG, fixed.toString());
                return true;
            }
        }
        return false;
    }

    public static ResourceLocation updateAttachmentId(ResourceLocation old) {
        return OLD_TO_NEW.getOrDefault(old, old);
    }
}































































