package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.modifier.custom.AdsModifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class LivingEntityAim {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityAim(LivingEntity shooter, ShooterDataHolder data) {
        this.shooter = shooter;
        this.data = data;
    }

    public void aim(boolean isAim) {
        data.isAiming = isAim;
    }

    public void zoom() {
        if (data.currentGunItem == null) {
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        if (!(currentGunItem.getItem() instanceof IGun iGun)) {
            return;
        }
        ResourceLocation scopeId = iGun.getAttachmentId(currentGunItem, AttachmentType.SCOPE);
        CompoundTag scopeTag = iGun.getAttachmentTag(currentGunItem, AttachmentType.SCOPE);
        if (!DefaultAssets.isEmptyAttachmentId(scopeId) && scopeTag != null) {
            TimelessAPI.getCommonAttachmentIndex(scopeId).ifPresent(index -> {
                int zoomNumber = AttachmentItemDataAccessor.getZoomNumberFromTag(scopeTag);
                ++zoomNumber;
                // ÃƒÂ©Ã‚ÂÃ‚Â¿ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‚ÂºÃ‚Â¢ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
                zoomNumber = zoomNumber % (Integer.MAX_VALUE - 1);
                AttachmentItemDataAccessor.setZoomNumberToTag(scopeTag, zoomNumber);
            });
        }
    }

    public void tickAimingProgress() {
        // currentGunItem ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚Âº nullÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â  aimingProgress ÃƒÂ¥Ã‚Â½Ã¢â‚¬â„¢ÃƒÂ©Ã¢â‚¬ÂºÃ‚Â¶ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
        if (data.currentGunItem == null || !(data.currentGunItem.get().getItem() instanceof IGun iGun)) {
            data.aimingProgress = 0;
            data.aimingTimestamp = System.currentTimeMillis();
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â° gunIndexÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â  aimingProgress ÃƒÂ¥Ã‚Â½Ã¢â‚¬â„¢ÃƒÂ©Ã¢â‚¬ÂºÃ‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
        ResourceLocation gunId = iGun.getGunId(currentGunItem);
        Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(gunId);
        if (gunIndexOptional.isEmpty()) {
            data.aimingProgress = 0;
            return;
        }
        GunData gunData = gunIndexOptional.get().getGunData();
        float aimTime = gunData.getAimTime();
        if (this.data.cacheProperty != null) {
            aimTime = (Float) this.data.cacheProperty.getCache(AdsModifier.ID);
        }
        aimTime = Math.max(0, aimTime);
        float alphaProgress = (System.currentTimeMillis() - data.aimingTimestamp + 1) / (aimTime * 1000);
        if (data.isAiming) {
            // ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â  aimingProgress
            data.aimingProgress += alphaProgress;
            if (data.aimingProgress > 1) {
                data.aimingProgress = 1;
            }
        } else {
            // ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã‚Â aimingProgress
            data.aimingProgress -= alphaProgress;
            if (data.aimingProgress < 0) {
                data.aimingProgress = 0;
            }
        }
        data.aimingTimestamp = System.currentTimeMillis();
    }

    public void tickSprint() {
        IGunOperator operator = IGunOperator.fromLivingEntity(shooter);
        ReloadState reloadState = operator.getSynReloadState();
        if (data.isAiming || (reloadState.getStateType().isReloading() && !reloadState.getStateType().isReloadFinishing())) {
            shooter.setSprinting(false);
        }
        if (data.sprintTimestamp == -1) {
            data.sprintTimestamp = System.currentTimeMillis();
        }
        if (data.currentGunItem == null) {
            return;
        }
        ItemStack gunItem = data.currentGunItem.get();
        IGun iGun = IGun.getIGunOrNull(gunItem);
        if (iGun == null) {
            return;
        }
        TimelessAPI.getCommonGunIndex(iGun.getGunId(gunItem)).ifPresentOrElse(gunIndex -> {
            float gunSprintTime = gunIndex.getGunData().getSprintTime();
            if (shooter.isSprinting() && !shooter.isCrouching()) {
                data.sprintTimeS += (System.currentTimeMillis() - data.sprintTimestamp) / 1000f;
                if (data.sprintTimeS > gunSprintTime) {
                    data.sprintTimeS = gunSprintTime;
                }
            } else {
                data.sprintTimeS -= (System.currentTimeMillis() - data.sprintTimestamp) / 1000f;
                if (data.sprintTimeS < 0) {
                    data.sprintTimeS = 0;
                }
            }
        }, () -> data.sprintTimeS = 0);
        data.sprintTimestamp = System.currentTimeMillis();
    }
}































































