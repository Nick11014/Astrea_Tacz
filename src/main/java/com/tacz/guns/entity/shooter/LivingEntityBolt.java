package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class LivingEntityBolt {
    private final ShooterDataHolder data;
    private final LivingEntityDrawGun draw;
    private final LivingEntityShoot shoot;
    private final LivingEntity shooter;

    public LivingEntityBolt(ShooterDataHolder data, LivingEntity shooter, LivingEntityDrawGun draw, LivingEntityShoot shoot) {
        this.data = data;
        this.draw = draw;
        this.shoot = shoot;
        this.shooter = shooter;
    }

    public void bolt() {
        if (data.currentGunItem == null) {
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        if (!(currentGunItem.getItem() instanceof AbstractGunItem iGun)) {
            return;
        }
        ResourceLocation gunId = iGun.getGunId(currentGunItem);
        TimelessAPI.getCommonGunIndex(gunId).ifPresent(gunIndex -> {
            // ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã¢â‚¬Â Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‚Â´
            if (shoot.getShootCoolDown() != 0) {
                return;
            }
            // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹
            if (data.reloadStateType.isReloading()) {
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
            IGunOperator gunOperator = IGunOperator.fromLivingEntity(shooter);
            // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ bolt ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ manual action
            Bolt boltType = gunIndex.getGunData().getBolt();
            // ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»
            boolean useInventoryAmmo = iGun.useInventoryAmmo(currentGunItem);
            // ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
            boolean hasAmmoInBarrel = iGun.hasBulletInBarrel(currentGunItem) && boltType != Bolt.OPEN_BOLT;
            // ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ (ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹)
            boolean hasInventoryAmmo = iGun.hasInventoryAmmo(shooter, currentGunItem, gunOperator.needCheckAmmo());
            // ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ (ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ / ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â° < 1)
            boolean noAmmo = useInventoryAmmo && !hasInventoryAmmo ||
                    !useInventoryAmmo && iGun.getCurrentAmmoCount(currentGunItem) < 1;
            if (boltType != Bolt.MANUAL_ACTION) {
                return;
            }
            // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦
            if (hasAmmoInBarrel) {
                return;
            }
            // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
            if (noAmmo) {
                return;
            }
            data.boltTimestamp = System.currentTimeMillis();
            data.isBolting = iGun.startBolt(data, currentGunItem, shooter);
        });
    }

    public void tickBolt() {
        // bolt cool down ÃƒÂ¤Ã‚Â¸Ã‚Âº -1 ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚Âtick
        if (!data.isBolting) {
            return;
        }
        if (data.currentGunItem == null) {
            data.isBolting = false;
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        if (!(currentGunItem.getItem() instanceof AbstractGunItem iGun)) {
            data.isBolting = false;
            return;
        }
        ResourceLocation gunId = iGun.getGunId(currentGunItem);
        Optional<CommonGunIndex> gunIndex = TimelessAPI.getCommonGunIndex(gunId);
        data.isBolting = gunIndex.map(index -> iGun.tickBolt(data, currentGunItem, shooter)).orElse(false);
    }
}































































