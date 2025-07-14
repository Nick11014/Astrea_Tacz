package com.tacz.guns.client.gui.components.refit;

import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.util.LaserColorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Slider API mudou, precisa ser adaptado
// import net.minecraft.client.gui.components.Slider;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HSVSliderGroup {
    private final Inventory inventory;
    private final int gunItemIndex;

    private final AttachmentType type;

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] LaserColorSlider comentado por dependÃƒÆ’Ã‚Âªncias
    // private final LaserColorSlider hueSlider;
    // private final LaserColorSlider saturationSlider;

    public HSVSliderGroup(int x, int y, int width, int height, Inventory inventory, int gunItemIndex, @NotNull AttachmentType type) {
        this.inventory = inventory;
        this.gunItemIndex = gunItemIndex;
        this.type = type;

        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o comentada por dependÃƒÆ’Ã‚Âªncias
        // int color = getColor(type);
        // float[] hsb = Color.RGBtoHSB((color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF, null);
        // hueSlider = new LaserColorSlider(x, y, width, height, this, hsb[0]);
        // saturationSlider = new LaserColorSlider(x, y + 2 + height, width, height, this, hsb[1]);
    }

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] MÃƒÆ’Ã‚Â©todo comentado por dependÃƒÆ’Ã‚Âªncias
    // public LaserColorSlider getHueSlider() {
    //     return hueSlider;
    // }

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] MÃƒÆ’Ã‚Â©todo comentado por dependÃƒÆ’Ã‚Âªncias  
    // public LaserColorSlider getSaturationSlider() {
    //     return saturationSlider;
    // }


    public void apply() {
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o comentada por dependÃƒÆ’Ã‚Âªncias
        // ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ§Ã…Â½Ã‚Â°
        // ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ©Ã¢â‚¬Â¡Ã…â€™ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â¾Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢nbtÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã¢â‚¬Å¾Ã‚ÂÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã‚Â¢Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â§Ã‹â€ ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œ
        // ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‚ÂÃ‹â€ ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¥Ã…Â Ã‚Â¨
        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â»Ã¢â‚¬ËœÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ©Ã‚Â¢Ã¢â‚¬ËœÃƒÂ§Ã‚Â¹Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¸Ã…â€™ÃƒÂ¦Ã…â€œÃ¢â‚¬ÂºÃƒÂ©Ã‚Â¢Ã¢â‚¬ËœÃƒÂ§Ã‚Â¹Ã‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦
        // ItemStack gun = inventory.getItem(gunItemIndex);
        // if (gun.getItem() instanceof IGun iGun) {
        //     int rgb_new = Color.HSBtoRGB((float) hueSlider.getValue(), (float) saturationSlider.getValue(), 1f);
        //     if (type == AttachmentType.NONE) {
        //         iGun.setLaserColor(gun, rgb_new);
        //         return;
        //     }
        //     ItemStack laser = iGun.getAttachment(gun, type);
        //     if (laser.getItem() instanceof IAttachment iAttachment) {
        //         iAttachment.setLaserColor(laser, rgb_new);
        //     }
        // }
    }



    private int getColor(AttachmentType type) {
        if (inventory == null) {
            return 0XFF0000;
        }
        ItemStack gun = inventory.getItem(gunItemIndex);

        if (gun.getItem() instanceof IGun iGun) {
            if (type == AttachmentType.NONE) {
                return LaserColorUtil.getLaserColor(gun);
            } else {
                ItemStack attachment = iGun.getAttachment(gun, type);
                return LaserColorUtil.getLaserColor(attachment);
            }
        }

        return 0XFF0000;
    }

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] ForgeSlider nÃƒÆ’Ã‚Â£o existe mais, precisa ser adaptado
    // public static class LaserColorSlider extends ForgeSlider {
    //     private final HSVSliderGroup parent;
    //     public LaserColorSlider(int x, int y, int width, int height, HSVSliderGroup parent, double current) {
    //         super(x, y, width, height, Component.empty(), Component.empty(), 0, 1, current, 0.01, 0, true);
    //         this.parent = parent;
    //     }
    //     @Override
    //     protected void applyValue() {
    //         parent.apply();
    //     }
    // }
}































































