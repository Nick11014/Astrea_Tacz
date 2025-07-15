package com.tacz.guns.resource.modifier.custom;

import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.GunProperties;
import com.tacz.guns.api.item.IAttachment.Slot;
import com.tacz.guns.api.modifier.CacheValue;
import com.tacz.guns.api.modifier.IAttachmentModifier;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
import com.tacz.guns.api.modifier.IAttachmentModifier.DiagramsData;
import com.tacz.guns.api.modifier.JsonProperty;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.MoveSpeed;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚ÂµÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨modifierÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚Â¤Ã‚ÂªÃƒÂ¥Ã‚Â¥Ã¢â‚¬Â¡ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂªÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â§Ã¢â‚¬ËœÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
 */
public class ExtraMovementModifier implements IAttachmentModifier<MoveSpeed, MoveSpeed> {
    public static final String ID = GunProperties.MOVE_SPEED.name();

    @Override
    public String getId() {
        return ID;
    }

    @SuppressWarnings("deprecation")
    public JsonProperty<MoveSpeed> readJson(String json) {
        ExtraMovementModifier.Data data = CommonAssetsManager.GSON.fromJson(json, ExtraMovementModifier.Data.class);
        MoveSpeed moveSpeed = data.getMoveSpeed();
        return  new ExtraSpeedJsonProperty(moveSpeed);
    }

    @Override
    public CacheValue<MoveSpeed> initCache(ItemStack gunItem, GunData gunData) {
        return new CacheValue<>(gunData.getMoveSpeed());
    }

    @Override
    public void eval(List<MoveSpeed> modifiers, CacheValue<MoveSpeed> cache) {
        cache.setValue(MoveSpeed.of(cache.getValue(), modifiers));
    }

    public static class ExtraSpeedJsonProperty extends JsonProperty<MoveSpeed> {
        public ExtraSpeedJsonProperty(MoveSpeed value) {
            super(value);
        }

        @Override
        public void initComponents() {
            MoveSpeed speed = getValue();
            if(speed == null)return;
            resolveComponent(speed.getBaseMultiplier(), "movement_speed");
            resolveComponent(speed.getAimMultiplier(), "aim_speed");
            resolveComponent(speed.getReloadMultiplier(), "reload_speed");
        }

        private void resolveComponent(float amount, String key) {
            if (amount > 0) {
                components.add(Component.translatable(String.format("tooltip.tacz.attachment.%s.increase", key)).withStyle(ChatFormatting.GREEN));
            } else if (amount < 0) {
                components.add(Component.translatable(String.format("tooltip.tacz.attachment.%s.decrease", key)).withStyle(ChatFormatting.RED));
            }
        }
    }

    public static class Data {
        @SerializedName("movement_speed")
        @Nullable
        private MoveSpeed moveSpeed = null;

        @Nullable
        public MoveSpeed getMoveSpeed() {
            return moveSpeed;
        }
    }
}































































