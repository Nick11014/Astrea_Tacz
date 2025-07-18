package com.tacz.guns.resource.modifier.custom;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
import com.tacz.guns.api.modifier.JsonProperty;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.pojo.data.attachment.Modifier;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.InaccuracyType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * @deprecated
 * Classe obsoleta, substituída por {@link InaccuracyModifier}.
 */
@Deprecated
public class AimInaccuracyModifier implements IAttachmentModifier<Map<InaccuracyType, Modifier>, Map<InaccuracyType, Float>> {
    public static final String ID = GunProperties.AIM_INACCURACY.name();

    public String getId() {
        return ID;
    }

    @Override
    public JsonProperty<Map<InaccuracyType, Modifier>> readJson(String json) {
        Map<InaccuracyType, Modifier> jsonProperties = Maps.newHashMap();
        return new AimInaccuracyJsonProperty(jsonProperties);
    }

    @Override
    public CacheValue<Map<InaccuracyType, Float>> initCache(ItemStack gunItem, GunData gunData, ItemStack attachmentItem) {
        Map<InaccuracyType, Float> tmp = Maps.newHashMap();
        return new CacheValue<>(tmp);
    }

    @Override
    public void eval(List<Map<InaccuracyType, Modifier>> modifiedValues, CacheValue<Map<InaccuracyType, Float>> cache) {
    }

    @Override
    public Class<Map<InaccuracyType, Modifier>> getPropertyClass() {
        return (Class<Map<InaccuracyType, Modifier>>) (Class<?>) Map.class;
    }

    public List<DiagramsData> getPropertyDiagramsData(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty, IAttachment.Slot<ItemStack> slot) {
        return List.of();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getDiagramsDataSize() {
        return 0;
    }

    public static class AimInaccuracyJsonProperty extends JsonProperty<Map<InaccuracyType, Modifier>> {
        public AimInaccuracyJsonProperty(Map<InaccuracyType, Modifier> value) {
            super(value);
        }

        @Override
        public void initComponents() {
            // Nenhum componente a inicializar
        }
    }

    public static class Data {
        @Nullable
        @SerializedName("aim_inaccuracy")
        private Modifier aimInaccuracy;

        @Nullable
        public Modifier getAimInaccuracy() {
            return aimInaccuracy;
        }
    }
}


