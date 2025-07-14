package com.tacz.guns.resource.modifier.custom;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.api.modifier.JsonProperty;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import com.tacz.guns.resource.pojo.data.attachment.Modifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.GunFireModeAdjustData;
import com.tacz.guns.resource.pojo.data.gun.InaccuracyType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InaccuracyModifier implements IAttachmentModifier<Map<InaccuracyType, Modifier>, Map<InaccuracyType, Float>> {
    public static final String ID = GunProperties.INACCURACY.name();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getOptionalFields() {
        return "inaccuracy_addend";
    }

    @Override
    @SuppressWarnings("deprecation")
    public JsonProperty<Map<InaccuracyType, Modifier>> readJson(String json) {
        Data data = CommonAssetsManager.GSON.fromJson(json, Data.class);
        Modifier inaccuracy = data.getInaccuracy();
        Modifier aimInaccuracy = data.getAimInaccuracy();
        Modifier sneakInaccuracy = data.getSneakInaccuracy();
        Modifier lieInaccuracy = data.getLieInaccuracy();

        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â§ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬
        if (inaccuracy == null) {
            float inaccuracyAddendTime = data.getInaccuracyAddendTime();
            inaccuracy = new Modifier();
            inaccuracy.setAddend(inaccuracyAddendTime);
        }
        // inaccuracyÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â aim(ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ©Ã¢â‚¬Â¢Ã…â€œ)ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™sneak(ÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã…â€œÃ‚Â¯ÃƒÂ¥Ã‚Â§Ã‚Â¿ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â)ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
        Map<InaccuracyType, Modifier> jsonProperties = Maps.newHashMap();
        for (InaccuracyType type : InaccuracyType.values()) {
            switch (type) {
                case AIM -> {
                    if (aimInaccuracy != null) jsonProperties.put(type, aimInaccuracy);
                }
                case SNEAK -> {
                    if (sneakInaccuracy != null) jsonProperties.put(type, sneakInaccuracy);
                }
                case LIE -> {
                    if (lieInaccuracy != null) jsonProperties.put(type, lieInaccuracy);
                }
                default -> jsonProperties.put(type, inaccuracy);
            }
        }
        return new InaccuracyJsonProperty(jsonProperties);
    }

    @Override
    public CacheValue<Map<InaccuracyType, Float>> initCache(ItemStack gunItem, GunData gunData) {
        Map<InaccuracyType, Float> tmp = Maps.newHashMap();
        IGun iGun = Objects.requireNonNull(IGun.getIGunOrNull(gunItem));
        FireMode fireMode = iGun.getFireMode(gunItem);
        gunData.getInaccuracy().forEach((type, value) -> {
            float inaccuracyAddend = 0;
            GunFireModeAdjustData fireModeAdjustData = gunData.getFireModeAdjustData(fireMode);
            if (fireModeAdjustData != null) {
                if (type == InaccuracyType.AIM) {
                    inaccuracyAddend = fireModeAdjustData.getAimInaccuracy();
                } else {
                    inaccuracyAddend = fireModeAdjustData.getOtherInaccuracy();
                }
            }
            float inaccuracy = gunData.getInaccuracy(type, inaccuracyAddend);
            tmp.put(type, inaccuracy);
        });
        return new CacheValue<>(tmp);
    }

    @Override
    public void eval(List<Map<InaccuracyType, Modifier>> modifiedValues, CacheValue<Map<InaccuracyType, Float>> cache) {
        Map<InaccuracyType, Float> result = Maps.newHashMap();
        Map<InaccuracyType, List<Modifier>> tmpModified = Maps.newHashMap();
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ©Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â Ã…Â ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ©Ã¢â‚¬ÂºÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¨Ã‚ÂµÃ‚Â·
        for (InaccuracyType type : InaccuracyType.values()) {
            List<Modifier> tmp = Lists.newArrayList();
            for (Map<InaccuracyType, Modifier> value : modifiedValues) {
                if (value.get(type) == null) {
                    continue;
                }
                tmp.add(value.get(type));
            }
            tmpModified.put(type, tmp);
        }
        // ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¦Ã…Â Ã…Â ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â®Ã…â€™
        cache.getValue().forEach((type, value) -> {
            double eval = AttachmentPropertyManager.eval(tmpModified.get(type), cache.getValue().get(type));
            result.put(type, (float) eval);
        });
        // ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
        cache.setValue(result);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<DiagramsData> getPropertyDiagramsData(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty) {
        IGun iGun = Objects.requireNonNull(IGun.getIGunOrNull(gunItem));
        FireMode fireMode = iGun.getFireMode(gunItem);
        GunFireModeAdjustData fireModeAdjustData = gunData.getFireModeAdjustData(fireMode);

        return List.of(
                buildNormal(gunData, cacheProperty, fireModeAdjustData, InaccuracyType.STAND, "gui.tacz.gun_refit.property_diagrams.hipfire_inaccuracy", 10.0),
                buildNormal(gunData, cacheProperty, fireModeAdjustData, InaccuracyType.SNEAK, "gui.tacz.gun_refit.property_diagrams.sneak_inaccuracy", 5.0),
                buildNormal(gunData, cacheProperty, fireModeAdjustData, InaccuracyType.LIE, "gui.tacz.gun_refit.property_diagrams.lie_inaccuracy", 5.0),
                buildAim(gunData, cacheProperty, fireModeAdjustData)
        );
    }

    private @NotNull DiagramsData buildNormal(GunData gunData, AttachmentCacheProperty cacheProperty, GunFireModeAdjustData fireModeAdjustData,
                                              InaccuracyType type, String titleKey, double referenceValue) {
        // ÃƒÂ¨Ã¢â‚¬Â¦Ã‚Â°ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â£
        float inaccuracy = gunData.getInaccuracy(type);
        if (fireModeAdjustData != null) {
            inaccuracy += fireModeAdjustData.getOtherInaccuracy();
        }

        float modifiedValue = cacheProperty.<Map<InaccuracyType, Float>>getCache(InaccuracyModifier.ID).get(type);
        // ÃƒÂ¥Ã‚Â·Ã‚Â®ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
        float inaccuracyModifier = modifiedValue - inaccuracy;
        // ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã¢â€žÂ¢Ã‚Â¾ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ¦Ã‚Â¯Ã¢â‚¬Â
        double standInaccuracyPercent = Math.min(inaccuracy / referenceValue, 1);
        // ÃƒÂ¥Ã‚Â·Ã‚Â®ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã¢â€žÂ¢Ã‚Â¾ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ¦Ã‚Â¯Ã¢â‚¬Â
        double inaccuracyModifierPercent = Math.min(inaccuracyModifier / referenceValue, 1);

        String positivelyString = String.format("%.2f Ãƒâ€šÃ‚Â§c(+%.2f)", modifiedValue, inaccuracyModifier);
        String negativelyString = String.format("%.2f Ãƒâ€šÃ‚Â§a(%.2f)", modifiedValue, inaccuracyModifier);
        String defaultString = String.format("%.2f", modifiedValue);
        boolean positivelyBetter = false;

        return new DiagramsData(standInaccuracyPercent, inaccuracyModifierPercent, inaccuracyModifier,
                titleKey, positivelyString, negativelyString, defaultString, positivelyBetter);
    }

    private @NotNull DiagramsData buildAim(GunData gunData, AttachmentCacheProperty cacheProperty, GunFireModeAdjustData fireModeAdjustData) {
        float aimInaccuracy = gunData.getInaccuracy(InaccuracyType.AIM);
        if (fireModeAdjustData != null) {
            aimInaccuracy += fireModeAdjustData.getAimInaccuracy();
        }

        aimInaccuracy = 1f - aimInaccuracy;
        float modifiedValue = 1 - cacheProperty.<Map<InaccuracyType, Float>>getCache(InaccuracyModifier.ID).get(InaccuracyType.AIM);

        aimInaccuracy = Mth.clamp(aimInaccuracy, 0f, 1f);
        modifiedValue = Mth.clamp(modifiedValue, 0f, 1f);

        float inaccuracyModifier = modifiedValue - aimInaccuracy;

        double aimInaccuracyPercent = Mth.clamp(aimInaccuracy, 0f, 1f);
        double inaccuracyModifierPercent = Mth.clamp(inaccuracyModifier, 0f, 1f);

        String titleKey = "gui.tacz.gun_refit.property_diagrams.aim_inaccuracy";
        String positivelyString = String.format("%.1f%% Ãƒâ€šÃ‚Â§a(+%.1f%%)", modifiedValue * 100, inaccuracyModifier * 100);
        String negativelyString = String.format("%.1f%% Ãƒâ€šÃ‚Â§c(%.1f%%)", modifiedValue * 100, inaccuracyModifier * 100);
        String defaultString = String.format("%.1f%%", modifiedValue * 100);
        boolean positivelyBetter = true;

        return new DiagramsData(aimInaccuracyPercent, inaccuracyModifierPercent, inaccuracyModifier,
                titleKey, positivelyString, negativelyString, defaultString, positivelyBetter);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getDiagramsDataSize() {
        return 3;
    }

    public static class InaccuracyJsonProperty extends JsonProperty<Map<InaccuracyType, Modifier>> {
        public InaccuracyJsonProperty(Map<InaccuracyType, Modifier> value) {
            super(value);
        }

        @Override
        public void initComponents() {
            createEntry(InaccuracyType.STAND, "tooltip.tacz.attachment.inaccuracy.decrease", "tooltip.tacz.attachment.inaccuracy.increase");
            createEntry(InaccuracyType.AIM, "tooltip.tacz.attachment.aim_inaccuracy.decrease", "tooltip.tacz.attachment.aim_inaccuracy.increase");
            createEntry(InaccuracyType.SNEAK, "tooltip.tacz.attachment.sneak_inaccuracy.decrease", "tooltip.tacz.attachment.sneak_inaccuracy.increase");
            createEntry(InaccuracyType.LIE, "tooltip.tacz.attachment.lie_inaccuracy.decrease", "tooltip.tacz.attachment.lie_inaccuracy.increase");
        }

        private void createEntry(InaccuracyType type, String decreaseKey, String increaseKey) {
            var value = this.getValue();
            float inaccuracyAddend = 0;
            if (value != null && value.containsKey(type)) {
                // ÃƒÂ©Ã…Â¡Ã‚ÂÃƒÂ¤Ã‚Â¾Ã‚Â¿ÃƒÂ¤Ã‚Â¼Ã‚Â ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹ÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã…â€œÃ¢â‚¬Â¹ÃƒÂ§Ã…â€œÃ¢â‚¬Â¹ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ§Ã‚Â»Ã‹â€ ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â·Ã‚Â®ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
                double eval = AttachmentPropertyManager.eval(value.get(type), 5);
                inaccuracyAddend = (float) (eval - 5);
            }
            // ÃƒÂ¦Ã‚Â·Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ§Ã‚Â¤Ã‚Âº
            if (inaccuracyAddend > 0) {
                components.add(Component.translatable(decreaseKey).withStyle(ChatFormatting.RED));
            } else if (inaccuracyAddend < 0) {
                components.add(Component.translatable(increaseKey).withStyle(ChatFormatting.GREEN));
            }
        }
    }

    public static class Data {
        @Nullable
        @SerializedName("inaccuracy")
        private Modifier inaccuracy;

        @Nullable
        @SerializedName("aim_inaccuracy")
        private Modifier aimInaccuracy;

        @Nullable
        @SerializedName("sneak_inaccuracy")
        private Modifier sneakInaccuracy;

        @Nullable
        @SerializedName("lie_inaccuracy")
        private Modifier lieInaccuracy;

        @SerializedName("inaccuracy_addend")
        @Deprecated
        private float adsAddendTime = 0;

        @Nullable
        public Modifier getInaccuracy() {
            return inaccuracy;
        }

        @Nullable
        public Modifier getAimInaccuracy() {
            return aimInaccuracy;
        }

        @Nullable
        public Modifier getSneakInaccuracy() {
            return sneakInaccuracy;
        }

        @Nullable
        public Modifier getLieInaccuracy() {
            return lieInaccuracy;
        }

        @Deprecated
        public float getInaccuracyAddendTime() {
            return adsAddendTime;
        }
    }
}































































