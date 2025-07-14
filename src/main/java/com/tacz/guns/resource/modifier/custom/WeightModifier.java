package com.tacz.guns.resource.modifier.custom;

import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.item.IAttachment;
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

public class WeightModifier implements IAttachmentModifier<Modifier, Float> {
    public static final String ID = GunProperties.WEIGHT.name();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    @SuppressWarnings("deprecation")
    public JsonProperty<Modifier> readJson(String json) {
        WeightModifier.Data data = CommonAssetsManager.GSON.fromJson(json, WeightModifier.Data.class);
        Modifier weightModifier = data.getWeightModifier();
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â§ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢
        if (weightModifier == null) {
            weightModifier = new Modifier();
            weightModifier.setAddend(data.getWeightAddend());
        }
        return new WeightModifier.WeightJsonProperty(weightModifier);
    }

    @Override
    public CacheValue<Float> initCache(ItemStack gunItem, GunData gunData) {
        return new CacheValue<>(gunData.getWeight());
    }

    @Override
    public void eval(List<Modifier> modifiers, CacheValue<Float> cache) {
        double eval = AttachmentPropertyManager.eval(modifiers, cache.getValue());
        cache.setValue((float) eval);
    }

    @Override
    public String getOptionalFields() {
        return "weight";
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<DiagramsData> getPropertyDiagramsData(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty, IAttachment.Slot<ItemStack> slot) {
        float weight = gunData.getWeight() ;
        float modifiedValue = cacheProperty.<Float>getCache(WeightModifier.ID);
        float modifier = modifiedValue - weight;

        double percent = Math.min(weight / 20.0, 1);
        double modifierPercent = Math.min(modifier / 20.0, 1);

        String titleKey = "gui.tacz.gun_refit.property_diagrams.weight";
        String positivelyString = String.format("%.2fkg Ãƒâ€šÃ‚Â§c(+%.2f)", modifiedValue, modifier);
        String negativelyString = String.format("%.2fkg Ãƒâ€šÃ‚Â§a(%.2f)", modifiedValue, modifier);
        String defaultString = String.format("%.2fkg", modifiedValue);
        boolean positivelyBetter = false;

        DiagramsData diagramsData = new DiagramsData(percent, modifierPercent, modifier, titleKey, positivelyString, negativelyString, defaultString, positivelyBetter);
        return Collections.singletonList(diagramsData);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getDiagramsDataSize() {
        return 1;
    }

    public static class WeightJsonProperty extends JsonProperty<Modifier> {
        public WeightJsonProperty(Modifier value) {
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
                components.add(Component.translatable("tooltip.tacz.attachment.weight.increase").withStyle(ChatFormatting.RED));
            } else if (adsAddendTime < 0) {
                components.add(Component.translatable("tooltip.tacz.attachment.weight.decrease").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    public static class Data {
        @Nullable
        @SerializedName("weight_modifier")
        private Modifier weightModifier;

        @SerializedName("weight")
        @Deprecated
        private float weightAddend = 0;

        @Nullable
        public Modifier getWeightModifier() {
            return weightModifier;
        }

        @Deprecated
        public float getWeightAddend() {
            return weightAddend;
        }
    }
}































































