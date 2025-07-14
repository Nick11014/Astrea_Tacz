package com.tacz.guns.resource.modifier.custom;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.modifier.IAttachmentModifier;
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

/**@deprecated
 * ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬Å¾Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ¥Ã‚Â¤Ã‚Â±ÃƒÂ¨Ã‚Â¯Ã‚Â¯ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™{@link InaccuracyModifier}ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚Â<br/>
 * ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ©Ã¢â€žÂ¢Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â·ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ {@link InaccuracyModifier} <br/>
 *
 * ÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¤ModifierÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾idÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‹â€ Ã‚Â° {@link InaccuracyModifier} <br/>
 * */
@Deprecated
public class AimInaccuracyModifier implements IAttachmentModifier<Map<InaccuracyType, Modifier>, Map<InaccuracyType, Float>> {
    public static final String ID = GunProperties.AIM_INACCURACY.name();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public JsonProperty<Map<InaccuracyType, Modifier>> readJson(String json) {
        Map<InaccuracyType, Modifier> jsonProperties = Maps.newHashMap();
        return new AimInaccuracyJsonProperty(jsonProperties);
    }

    @Override
    public CacheValue<Map<InaccuracyType, Float>> initCache(ItemStack gunItem, GunData gunData) {
        Map<InaccuracyType, Float> tmp = Maps.newHashMap();
        return new CacheValue<>(tmp);
    }

    @Override
    public void eval(List<Map<InaccuracyType, Modifier>> modifiedValues, CacheValue<Map<InaccuracyType, Float>> cache) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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































































