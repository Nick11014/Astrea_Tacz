package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.GunMeleeEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunMelee;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.attachment.MeleeData;
import com.tacz.guns.resource.pojo.data.gun.GunDefaultMeleeData;
import com.tacz.guns.resource.pojo.data.gun.GunMeleeData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;

import javax.annotation.Nullable;
import java.util.Optional;

public class LivingEntityMelee {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;
    private final LivingEntityDrawGun draw;

    public LivingEntityMelee(LivingEntity shooter, ShooterDataHolder data, LivingEntityDrawGun draw) {
        this.shooter = shooter;
        this.data = data;
        this.draw = draw;
    }

    public void melee() {
        if (data.currentGunItem == null) {
            return;
        }
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚Âª
        if (draw.getDrawCoolDown() != 0) {
            return;
        }
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“
        if (data.isBolting) {
            return;
        }
        long coolDown = getMeleeCoolDown();
        if (coolDown != 0) {
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        // ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
        if (NeoForge.EVENT_BUS.post(new GunMeleeEvent(shooter, currentGunItem, LogicalSide.SERVER))) {
            return;
        }
        NetworkHandler.sendToClientPlayer(new ServerMessageGunMelee(shooter.getId(), currentGunItem), (ServerPlayer) shooter);
        if (currentGunItem.getItem() instanceof AbstractGunItem logicGun) {
            data.meleeTimestamp = System.currentTimeMillis();

            ResourceLocation muzzleId = logicGun.getAttachmentId(currentGunItem, AttachmentType.MUZZLE);
            MeleeData muzzleMeleeData = getMeleeData(muzzleId);
            if (muzzleMeleeData != null) {
                float prepTime = muzzleMeleeData.getPrepTime();
                data.meleePrepTickCount = (int) Math.max(0, prepTime * 20);
                return;
            }

            ResourceLocation stockId = logicGun.getAttachmentId(currentGunItem, AttachmentType.STOCK);
            MeleeData stockMeleeData = getMeleeData(stockId);
            if (stockMeleeData != null) {
                float prepTime = stockMeleeData.getPrepTime();
                data.meleePrepTickCount = (int) Math.max(0, prepTime * 20);
                return;
            }

            ResourceLocation gunId = logicGun.getGunId(currentGunItem);
            TimelessAPI.getCommonGunIndex(gunId).ifPresent(index -> {
                GunDefaultMeleeData defaultMeleeData = index.getGunData().getMeleeData().getDefaultMeleeData();
                if (defaultMeleeData == null) {
                    return;
                }
                float prepTime = defaultMeleeData.getPrepTime();
                data.meleePrepTickCount = (int) Math.max(0, prepTime * 20);
            });
        }
    }

    public void scheduleTickMelee() {
        if (this.data.meleePrepTickCount > 0) {
            this.data.meleePrepTickCount--;
            return;
        }
        if (this.data.meleePrepTickCount == 0) {
            this.data.meleePrepTickCount = -1;
            if (data.currentGunItem == null) {
                return;
            }
            ItemStack currentGunItem = data.currentGunItem.get();
            if (currentGunItem.getItem() instanceof AbstractGunItem logicGun) {
                logicGun.melee(data, this.shooter, currentGunItem);
            }
        }
    }

    public long getMeleeCoolDown() {
        if (data.currentGunItem == null) {
            return 0;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        if (!(currentGunItem.getItem() instanceof IGun iGun)) {
            return 0;
        }
        ResourceLocation gunId = iGun.getGunId(currentGunItem);
        Optional<CommonGunIndex> gunIndex = TimelessAPI.getCommonGunIndex(gunId);
        return gunIndex.map(index -> {
            GunMeleeData meleeData = index.getGunData().getMeleeData();
            // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã…â€œÃ¢â‚¬Â¹ÃƒÂ§Ã…â€œÃ¢â‚¬Â¹ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
            ResourceLocation muzzleId = iGun.getAttachmentId(currentGunItem, AttachmentType.MUZZLE);
            MeleeData muzzleMeleeData = getMeleeData(muzzleId);
            if (muzzleMeleeData != null) {
                return getTotalCooldownTime(meleeData, muzzleMeleeData.getCooldown());
            }

            // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬Â°Ã‹Å“
            ResourceLocation stockId = iGun.getAttachmentId(currentGunItem, AttachmentType.STOCK);
            MeleeData stockMeleeData = getMeleeData(stockId);
            if (stockMeleeData != null) {
                return getTotalCooldownTime(meleeData, stockMeleeData.getCooldown());
            }

            GunDefaultMeleeData defaultMeleeData = meleeData.getDefaultMeleeData();
            float defaultMeleeCooldownTime = defaultMeleeData == null ? 0 : defaultMeleeData.getCooldown();
            return getTotalCooldownTime(meleeData, defaultMeleeCooldownTime);
        }).orElse(-1L);
    }

    private long getTotalCooldownTime(GunMeleeData meleeData, float extraCooldownTime) {
        float totalCooldownTime = meleeData.getCooldown() + extraCooldownTime;
        long coolDown = (long) (totalCooldownTime * 1000) - (System.currentTimeMillis() - data.meleeTimestamp);
        // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ 5 ms ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚Â¹Ã‚Â³ÃƒÂ¨Ã‚Â¡Ã‚Â¡ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸
        coolDown = coolDown - 5;
        if (coolDown < 0) {
            return 0L;
        }
        return coolDown;
    }

    @Nullable
    private MeleeData getMeleeData(ResourceLocation attachmentId) {
        if (DefaultAssets.isEmptyAttachmentId(attachmentId)) {
            return null;
        }
        return TimelessAPI.getCommonAttachmentIndex(attachmentId).map(index -> index.getData().getMeleeData()).orElse(null);
    }
}































































