package com.tacz.guns.resource.modifier.custom;

import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.item.IAttachment.Slot;
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

import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
 

public class AdsModifier implements IAttachmentModifier<Float, GunData> {
    private static final String ADS_ADDEND = "ads_addend";

    @Override
    public List<Float> getAttachmentsValue(ItemStack gun, GunData gunData, AttachmentData attachmentData) {
        // A lógica agora obtém o valor diretamente de AttachmentData
        return List.of(attachmentData.getAdsAdd());
    }

    @Override
    public Float getSumValue(List<Float> values) {
        return values.stream().reduce(0f, Float::sum);
    }

    @Override
    public Float getMultipliedValue(List<Float> values) {
        // Para tempo de mira, a modificação é geralmente aditiva, não multiplicativa.
        // Se for multiplicativa, a lógica seria:
        // return values.stream().reduce(1f, (a, b) -> a * (1 + b)) - 1;
        return getSumValue(values); // Usando soma como padrão
    }

    @Override
    public float getModification(float base, Float modifier) {
        // O tempo de mira é reduzido, então subtraímos o modificador.
        return base - modifier;
    }

    @Override
    public String getPropertyId() {
        return GunProperties.ADS_TIME.getName();
    }

    @Override
    public boolean applicable(AttachmentType type) {
        return type == AttachmentType.SCOPE || type == AttachmentType.GRIP;
    }

    @Override
    public IComponent<Float> getComponent(ItemStack attachment) {
        // Implementação para obter o componente de tooltip
        return new AdsComponent(attachment);
    }

    /**
     * Lógica para os diagramas da UI, agora como um método estático.
     */
    public static List<DiagramsData> getPropertyDiagramsData(GunData gunData, float modifiedAimTime) {
        float aimTime = gunData.getAimTime();
        float adsTimeModifier = modifiedAimTime - aimTime;

        // Normalizando para um valor entre 0 e 1. 0.5s é um tempo de mira rápido.
        double percent = 1 - Mth.clamp(aimTime / 0.5, 0, 1);
        double adsTimeModifierPercent = -Mth.clamp(adsTimeModifier / 0.5, -1, 1);

        String titleKey = "gui.tacz.gun_refit.property_diagrams.ads";
        // Tempo de mira menor é melhor (positivo)
        String positivelyString = String.format("%.2fs §a(%.2fs)", modifiedAimTime, adsTimeModifier);
        String negativelyString = String.format("%.2fs §c(+%.2fs)", modifiedAimTime, adsTimeModifier);
        String defaultString = String.format("%.2fs", modifiedAimTime);
        boolean positivelyBetter = adsTimeModifier < 0;

        DiagramsData diagramsData = new DiagramsData(percent, adsTimeModifierPercent, adsTimeModifier, titleKey, positivelyString, negativelyString, defaultString, positivelyBetter);
        return Collections.singletonList(diagramsData);
    }

    /**
     * Componente que fornece a lógica para o tooltip do item.
     */
    public static class AdsComponent implements IComponent<Float> {
        private final float adsAddendTime;

        public AdsComponent(ItemStack attachment) {
            float totalAdsAdd = 0;
            // A lógica de ler JSON diretamente aqui é complexa.
            // O ideal é que o valor já venha processado do AttachmentData.
            // Esta é uma aproximação.
            // Supondo que o NBT do attachment tenha o valor pré-calculado.
            if(attachment.hasTag() && attachment.getTag().contains(ADS_ADDEND)){
                totalAdsAdd = attachment.getTag().getFloat(ADS_ADDEND);
            }
            this.adsAddendTime = totalAdsAdd;
        }

        @Override
        public void applier(List<Component> components) {
            // Tempo de mira menor é melhor
            if (adsAddendTime > 0) {
                components.add(Component.translatable("tooltip.tacz.attachment.ads.increase").withStyle(ChatFormatting.RED));
            } else if (adsAddendTime < 0) {
                components.add(Component.translatable("tooltip.tacz.attachment.ads.decrease").withStyle(ChatFormatting.GREEN));
            }
        }
    }
    
    /**
     * Classe interna para desserializar dados do JSON, mantida para compatibilidade.
     */
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





































