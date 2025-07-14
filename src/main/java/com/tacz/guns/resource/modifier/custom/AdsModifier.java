package com.tacz.guns.resource.modifier.custom;

import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.api.modifier.JsonProperty;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
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

public class AdsModifier implements IAttachmentModifier<Modifier, Float> {
    public static final String ID = GunProperties.ADS_TIME.name();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getOptionalFields() {
        return "ads_addend";
    }


    @Override
    @SuppressWarnings("deprecation")
    public JsonProperty<Modifier> readJson(String json) {
        Data data = CommonAssetsManager.GSON.fromJson(json, Data.class);
        Modifier ads = data.getAds();
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â§ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢
        if (ads == null) {
            ads = new Modifier();
            ads.setAddend(data.getAdsAddendTime());
        }
        return new AdsJsonProperty(ads);
    }

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] MÃƒÆ’Ã‚Â©todos removidos da interface IAttachmentModifier
    // @Override
    public CacheValue<Float> initCache(ItemStack gunItem, GunData gunData) {
        return new CacheValue<>(gunData.getAimTime());
    }

    // @Override
    public void eval(List<Modifier> modifiers, CacheValue<Float> cache) {
        double eval = AttachmentPropertyManager.eval(modifiers, cache.getValue());
        cache.setValue((float) eval);
    }

    // @Override
    @OnlyIn(Dist.CLIENT)
    public List<DiagramsData> getPropertyDiagramsData(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty) {
        float aimTime = gunData.getAimTime();
        float modifiedAimTime = cacheProperty.<Float>getCache(AdsModifier.ID);
        float adsTimeModifier = modifiedAimTime - aimTime;

        double percent = Math.min(gunData.getAimTime() / 0.5, 1);
        double adsTimeModifierPercent = Math.min(adsTimeModifier / 0.5, 1);

        String titleKey = "gui.tacz.gun_refit.property_diagrams.ads";
        String positivelyString = String.format("%.2fs Ãƒâ€šÃ‚Â§c(+%.2f)", modifiedAimTime, adsTimeModifier);
        String negativelyString = String.format("%.2fs Ãƒâ€šÃ‚Â§a(%.2f)", modifiedAimTime, adsTimeModifier);
        String defaultString = String.format("%.2fs", modifiedAimTime);
        boolean positivelyBetter = false;

        DiagramsData diagramsData = new DiagramsData(percent, adsTimeModifierPercent, adsTimeModifier, titleKey, positivelyString, negativelyString, defaultString, positivelyBetter);
        return Collections.singletonList(diagramsData);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getDiagramsDataSize() {
        return 1;
    }

    public static class AdsJsonProperty extends JsonProperty<Modifier> {
        public AdsJsonProperty(Modifier value) {
            super(value);
        }

        @Override
        public void initComponents() {
            Modifier value = this.getValue();
            float adsAddendTime = 0;
            if (value != null) {
                // ÃƒÂ¤Ã‚Â¼Ã‚Â ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ 0.2 ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹ÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã…â€œÃ¢â‚¬Â¹ÃƒÂ§Ã…â€œÃ¢â‚¬Â¹ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ§Ã‚Â»Ã‹â€ ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â·Ã‚Â®ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
                double eval = AttachmentPropertyManager.eval(value, 0.2);
                adsAddendTime = (float) (eval - 0.2);
            }
            // ÃƒÂ¦Ã‚Â·Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ§Ã‚Â¤Ã‚Âº
            if (adsAddendTime > 0) {
                components.add(Component.translatable("tooltip.tacz.attachment.ads.increase").withStyle(ChatFormatting.RED));
            } else if (adsAddendTime < 0) {
                components.add(Component.translatable("tooltip.tacz.attachment.ads.decrease").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    public static class Data {
        @Nullable
        @SerializedName("ads")
        private Modifier ads;

        @SerializedName("ads_addend")
        @Deprecated
        private float adsAddendTime = 0;

        @Nullable
        public Modifier getAds() {
            return ads;
        }

        @Deprecated
        public float getAdsAddendTime() {
            return adsAddendTime;
        }
    }
}































































