package com.tacz.guns.resource.modifier.custom;

import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.item.IAttachment.Slot;
import com.tacz.guns.api.modifier.JsonProperty;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import com.tacz.guns.resource.pojo.data.attachment.AttachmentData;
import com.tacz.guns.resource.pojo.data.attachment.Modifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.util.math.MathUtil;
import com.tacz.guns.init.ModDataComponents;

public class AdsModifier implements IAttachmentModifier<Float, GunData> {
    public static final String ID = "ads_modifier";
    private static final String ADS_ADDEND = "ads_addend";

    public List<Float> getAttachmentsValue(ItemStack gun, GunData gunData, AttachmentData attachmentData) {
        // Busca o modificador "ads_addend" no mapa de modificadores do attachment
        JsonProperty<?> prop = attachmentData.getModifier().get(ADS_ADDEND);
        float value = 0f;
        if (prop != null && prop.getValue() instanceof Number num) {
            value = num.floatValue();
        }
        return List.of(value);
    }

    public Float getSumValue(List<Float> values) {
        return values.stream().reduce(0f, Float::sum);
    }

    public Float getMultipliedValue(List<Float> values) {
        return getSumValue(values);
    }

    public float getModification(float base, Float modifier) {
        return base - modifier;
    }

    public String getPropertyId() {
        return "ads";
    }

    public boolean applicable(AttachmentType type) {
        return type == AttachmentType.SCOPE || type == AttachmentType.GRIP;
    }

    // Usa DataComponent para obter o valor do attachment
    public AdsComponent getComponent(ItemStack attachment) {
        return new AdsComponent(attachment);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public CacheValue<GunData> initCache(ItemStack gunItem, GunData gunData, ItemStack attachmentItem) {
        return null;
    }

    @Override
    public void eval(List<Float> modifiedValues, CacheValue<GunData> cache) {
    }

    public static List<DiagramsData> getPropertyDiagramsData(GunData gunData, float modifiedAimTime) {
        float aimTime = gunData.getAimTime();
        float adsTimeModifier = modifiedAimTime - aimTime;

        double percent = 1 - Math.max(0, Math.min(aimTime / 0.5, 1));
        double adsTimeModifierPercent = -Math.max(-1, Math.min(adsTimeModifier / 0.5, 1));

        String titleKey = "gui.tacz.gun_refit.property_diagrams.ads";
        String positivelyString = String.format("%.2fs §a(%.2fs)", modifiedAimTime, adsTimeModifier);
        String negativelyString = String.format("%.2fs §c(+%.2fs)", modifiedAimTime, adsTimeModifier);
        String defaultString = String.format("%.2fs", modifiedAimTime);
        boolean positivelyBetter = adsTimeModifier < 0;

        DiagramsData diagramsData = new DiagramsData(percent, adsTimeModifierPercent, adsTimeModifier, titleKey, positivelyString, negativelyString, defaultString, positivelyBetter);
        return Collections.singletonList(diagramsData);
    }

    // Usa DataComponent para obter o valor de ads_addend do attachment
    public static class AdsComponent {
        private final float adsAddendTime;

        public AdsComponent(ItemStack attachment) {
            this.adsAddendTime = attachment.getOrDefault(ModDataComponents.ADS_ADDEND, 0f);
        }

        public float getAdsAddendTime() {
            return adsAddendTime;
        }
    }

    public static class Data {
        @Nullable
        @SerializedName("ads")
        private com.tacz.guns.resource.pojo.data.attachment.Modifier ads;

        @SerializedName("ads_addend")
        @Deprecated
        private float adsAddendTime = 0;

        @Nullable
        public com.tacz.guns.resource.pojo.data.attachment.Modifier getAds() {
            return ads;
        }

        @Deprecated
        public float getAdsAddendTime() {
            return adsAddendTime;
        }
    }
}