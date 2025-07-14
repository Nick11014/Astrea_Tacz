package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerAim;
import com.tacz.guns.resource.modifier.custom.AdsModifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class LocalPlayerAim {
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;

    public LocalPlayerAim(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    public void aim(boolean isAim) {
        // ÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            return;
        }
        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        TimelessAPI.getClientGunIndex(gunId).ifPresent(gunIndex -> {
            data.clientIsAiming = isAim;
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
            NetworkHandler.CHANNEL.sendToServer(new ClientMessagePlayerAim(isAim));
        });
    }

    public float getClientAimingProgress(float partialTicks) {
        return Mth.lerp(partialTicks, LocalPlayerDataHolder.oldAimingProgress, data.clientAimingProgress);
    }

    public boolean isAim() {
        return data.clientIsAiming;
    }

    public void tickAimingProgress() {
        ItemStack mainHandItem = player.getMainHandItem();
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â  aimingProgress ÃƒÂ¥Ã‚Â½Ã¢â‚¬â„¢ÃƒÂ©Ã¢â‚¬ÂºÃ‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            data.clientAimingProgress = 0;
            LocalPlayerDataHolder.oldAimingProgress = 0;
            return;
        }
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â 
        if (System.currentTimeMillis() - data.clientDrawTimestamp < 0) {
            data.clientIsAiming = false;
        }
        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        TimelessAPI.getCommonGunIndex(gunId).ifPresentOrElse(index -> {
            float alphaProgress = this.getAlphaProgress(index.getGunData());
            this.aimProgressCalculate(alphaProgress);
        }, () -> {
            data.clientAimingProgress = 0;
            LocalPlayerDataHolder.oldAimingProgress = 0;
        });
    }

    private void aimProgressCalculate(float alphaProgress) {
        LocalPlayerDataHolder.oldAimingProgress = data.clientAimingProgress;
        if (data.clientIsAiming) {
            // ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â  aimingProgress
            data.clientAimingProgress += alphaProgress;
            if (data.clientAimingProgress > 1) {
                data.clientAimingProgress = 1;
            }
        } else {
            // ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã‚Â aimingProgress
            data.clientAimingProgress -= alphaProgress;
            if (data.clientAimingProgress < 0) {
                data.clientAimingProgress = 0;
            }
        }
        data.clientAimingTimestamp = System.currentTimeMillis();
    }

    private float getAlphaProgress(GunData gunData) {
        float aimTime = gunData.getAimTime();
        IGunOperator operator = IGunOperator.fromLivingEntity(this.player);
        if (operator.getCacheProperty() != null) {
            aimTime = operator.getCacheProperty().<Float>getCache(AdsModifier.ID);
        }
        aimTime = Math.max(0, aimTime);
        return (System.currentTimeMillis() - data.clientAimingTimestamp + 1) / (aimTime * 1000);
    }
}































































