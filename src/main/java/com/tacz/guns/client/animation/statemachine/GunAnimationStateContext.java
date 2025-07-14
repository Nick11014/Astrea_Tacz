package com.tacz.guns.client.animation.statemachine;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.api.util.LuaNbtAccessor;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.functional.ShellRender;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.joml.Vector3f;
import org.luaj.vm2.LuaTable;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unused")
public class GunAnimationStateContext extends ItemAnimationStateContext {
    private ItemStack currentGunItem;
    private IGun iGun;
    private GunDisplayInstance display;
    private GunData gunData;
    private float walkDistAnchor = 0f;
    private LuaNbtAccessor nbtUtil;

    private <T> Optional<T> processGunData(BiFunction<IGun, GunDisplayInstance, T> processor) {
        if (iGun != null && display != null) {
            return Optional.ofNullable(processor.apply(iGun, display));
        }
        return Optional.empty();
    }

    private <T> Optional<T> processGunOperator(Function<IClientPlayerGunOperator, T> processor) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            return Optional.ofNullable(processor.apply(IClientPlayerGunOperator.fromLocalPlayer(player)));
        }
        return Optional.empty();
    }

    private <T> Optional<T> processRemoteGunOperator(Function<IGunOperator, T> processor) {
        return processCameraEntity(entity -> {
            if (entity instanceof LivingEntity) {
                IGunOperator gunOperator = IGunOperator.fromLivingEntity((LivingEntity) entity);
                return processor.apply(gunOperator);
            }
            return null;
        });
    }

    private <T> Optional<T> processCameraEntity(Function<Entity, T> processor) {
        Entity entity = Minecraft.getInstance().cameraEntity;
        if (entity != null) {
            return Optional.ofNullable(processor.apply(entity));
        }
        return Optional.empty();
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ falseÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public boolean hasBulletInBarrel() {
        return processGunData((iGun, gunIndex) -> {
            Bolt boltType = gunData.getBolt();
            return boltType != Bolt.OPEN_BOLT && iGun.hasBulletInBarrel(currentGunItem);
        }).orElse(false);
    }

    public boolean isOverHeat() {
        return gunData.getHeatData() != null && iGun.isOverheatLocked(currentGunItem);
    }

    public float getHeatProgress() {
        return gunData.getHeatData() != null ?
                Mth.clamp(iGun.getHeatAmount(currentGunItem) / gunData.getHeatData().getHeatMax(), 0f, 1f) : 0f;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢
     * @return ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬Â
     */
    public long getShootInterval() {
        return processCameraEntity(entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                FireMode fireMode = iGun.getFireMode(currentGunItem);
                if (fireMode == FireMode.BURST) {
                    long coolDown = (long) (gunData.getBurstData().getMinInterval() * 1000f);
                    return Math.max(coolDown, 0L);
                }
                long coolDown = gunData.getShootInterval(livingEntity, fireMode, currentGunItem);
                return Math.max(coolDown, 0L);
            }
            return 0L;
        }).orElse(0L);
    }

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ timestamp(ÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´)ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Âº -1ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ timestampÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Âº -1ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public long getLastShootTimestamp() {
        return processGunOperator(operator -> operator.getDataHolder().clientLastShootTimestamp).orElse(-1L);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     */
    public long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡(ÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã…Â½Ã‚Â°)
     * @param alpha ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public void adjustClientShootInterval(long alpha) {
        processGunOperator(operator -> {
            long timestamp = operator.getDataHolder().clientShootTimestamp;
            operator.getDataHolder().clientShootTimestamp = timestamp + alpha;
            return null;
        });
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public int getAmmoCount() {
        return processGunData((iGun, gunIndex) -> iGun.getCurrentAmmoCount(currentGunItem)).orElse(0);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public int getMaxAmmoCount() {
        return processGunData(
                (iGun, gunIndex) ->
                        AttachmentDataUtils.getAmmoCountWithAttachment(currentGunItem, gunData)
        ).orElse(0);
    }

    /**
     * ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã‚ÂºÃ‚Â«ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ true
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã‚ÂºÃ‚Â«ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€
     */
    public boolean hasAmmoToConsume(){
        if (!processRemoteGunOperator(IGunOperator::needCheckAmmo).orElse(true)) {
            return true;
        }
        if (iGun.useDummyAmmo(currentGunItem)) {
            return iGun.getDummyAmmoAmount(currentGunItem) > 0;
        }
        return processCameraEntity(entity -> {
                    IItemHandler cap = entity.getCapability(Capabilities.ItemHandler.ENTITY);
                    if (cap != null) {
                        // ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥
                        for (int i = 0; i < cap.getSlots(); i++) {
                            ItemStack checkAmmoStack = cap.getStackInSlot(i);
                            if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(currentGunItem, checkAmmoStack)) {
                                return true;
                            }
                            if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(currentGunItem, checkAmmoStack)) {
                                return true;
                            }
                        }
                    }
                    return false;
                }
        ).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´ 0 ~ 3ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡0 ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¯Ã‚Â¼Ã…â€™1 ~ 3 ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ 1 ~ 3 ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£
     */
    public int getMagExtentLevel() {
        return processGunData(
                (iGun, gunIndex) ->
                        AttachmentDataUtils.getMagExtendLevel(currentGunItem, gunData)
        ).orElse(0);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return FireMode ÃƒÂ¦Ã…Â¾Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ ordinal ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
     */
    public int getFireMode() {
        return processGunData((iGun, gunIndex) -> iGun.getFireMode(currentGunItem).ordinal()).orElse(0);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¯Ã‚Â¼Ã…Â¡0 ~ 1ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *         0 ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã¢â‚¬â€œÃ‚ÂµÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™1 ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ¥Ã¢â‚¬â€œÃ‚ÂµÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public float getAimingProgress() {
        return processGunOperator(operator -> operator.getClientAimingProgress(partialTicks)).orElse(0f);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™aiming progress ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â 
     */
    public boolean isAiming() {
        return processGunOperator(IClientPlayerGunOperator::isAim).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã¢â‚¬Â Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‚Â´ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã¢â‚¬Â Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢(ms)ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public long getShootCoolDown() {
        return processGunOperator(IClientPlayerGunOperator::getClientShootCoolDown).orElse(0L);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
     */
    public int getReloadStateType() {
        return processCameraEntity(entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                return IGunOperator.fromLivingEntity(livingEntity).getSynReloadState().getStateType().ordinal();
            }
            return ReloadState.StateType.NOT_RELOADING.ordinal();
        }).orElse(ReloadState.StateType.NOT_RELOADING.ordinal());
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã…Â  (ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã‚ÂÃ¢â€šÂ¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ W)
     */
    public boolean isInputUp() {
        return Optional.ofNullable(Minecraft.getInstance().player).map(player -> player.input.up).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ (ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã‚ÂÃ¢â€šÂ¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ©Ã¢â€šÂ¬Ã¢â€šÂ¬ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ S)
     */
    public boolean isInputDown() {
        return Optional.ofNullable(Minecraft.getInstance().player).map(player -> player.input.down).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚Â·Ã‚Â¦ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚Â·Ã‚Â¦ (ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã‚ÂÃ¢â€šÂ¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â·Ã‚Â¦ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ A)
     */
    public boolean isInputLeft() {
        return Optional.ofNullable(Minecraft.getInstance().player).map(player -> player.input.left).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚ÂÃ‚Â³ (ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã‚ÂÃ¢â€šÂ¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ D)
     */
    public boolean isInputRight() {
        return Optional.ofNullable(Minecraft.getInstance().player).map(player -> player.input.right).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¨Ã‚Â·Ã‚Â³ÃƒÂ¨Ã‚Â·Ã†â€™ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¨Ã‚Â·Ã‚Â³ÃƒÂ¨Ã‚Â·Ã†â€™ (ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã‚ÂÃ¢â€šÂ¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â³ÃƒÂ¨Ã‚Â·Ã†â€™ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ Space)
     */
    public boolean isInputJumping() {
        return Optional.ofNullable(Minecraft.getInstance().player).map(player -> player.input.jumping).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã…â€™Ã‚ÂÃƒÂ¥Ã…â€™Ã‚Â
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã…â€™Ã‚ÂÃƒÂ¥Ã…â€™Ã‚Â
     */
    public boolean isCrawl() {
        return processGunOperator(IClientPlayerGunOperator::isCrawl).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ©Ã‚ÂÃ‚Â¢
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ©Ã‚ÂÃ‚Â¢
     */
    public boolean isOnGround() {
        return processCameraEntity(Entity::onGround).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¨Ã‚Â¹Ã‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¨Ã‚Â¹Ã‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â
     */
    public boolean isCrouching() {
        return processCameraEntity(Entity::isCrouching).orElse(false);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¦Ã¢â‚¬â€œÃ…â€œÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°
     * ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã‚Â»Ã‚Â¡ÃƒÂ¨Ã‚Â¶Ã‚Â³ÃƒÂ¨Ã‚Â¹Ã‚Â²ÃƒÂ¤Ã‚Â¼Ã‚ÂÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¦Ã¢â‚¬â€œÃ…â€œÃƒÂ¦Ã‚ÂÃ‚Â¡
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¦Ã¢â‚¬â€œÃ…â€œÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°
     */
    public boolean shouldSlide() {
        return processCameraEntity(e -> e.isCrouching() && gunData.canSlide()).orElse(false);
    }

    /**
     * ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚ÂµÃ‚Â°ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ©Ã¢â‚¬ÂÃ…Â¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¯Ã‚Â¼Ã…â€™getWalkDist() ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ©Ã¢â‚¬ÂÃ…Â¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
     */
    public void anchorWalkDist() {
        processCameraEntity(entity -> {
            walkDistAnchor = entity.walkDist + (entity.walkDist - entity.walkDistO) * partialTicks;
            return null;
        });
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ©Ã¢â‚¬ÂÃ…Â¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚ÂµÃ‚Â°ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ©Ã¢â‚¬ÂÃ…Â¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚ÂµÃ‚Â°ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @return ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ©Ã¢â‚¬ÂÃ…Â¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚ÂµÃ‚Â°ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ©Ã¢â‚¬ÂÃ…Â¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚ÂµÃ‚Â°ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public float getWalkDist() {
        return processCameraEntity(entity -> {
            float currentWalkDist = entity.walkDist + (entity.walkDist - entity.walkDistO) * partialTicks;
            return currentWalkDist - walkDistAnchor;
        }).orElse(0f);
    }

    /**
     * ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‚ÂÃ‚Â·ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â£Ã‚Â³ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¾Ã…Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â£Ã‚Â³
     * @param index ÃƒÂ¦Ã…Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â£Ã‚Â³ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‚ÂÃ‚Â·
     */
    public void popShellFrom(int index) {
        if (display.getShellEjection() != null) {
            BedrockGunModel gunModel = display.getGunModel();
            if (gunModel != null) {
                // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] ShellRender system temporarily disabled - requires full renderer migration
                // Optional<ShellRender> shellRenderOptional = gunModel.getShellRender(index);
                Vector3f velocity = display.getShellEjection().getRandomVelocity();
                // shellRenderOptional.ifPresent(shellRender -> shellRender.addShell(velocity));

                var lod = display.getLodModel();
                if (lod != null) {
                    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] ShellRender system temporarily disabled - requires full renderer migration
                    // Optional<ShellRender> lodShellOptional = lod.getLeft().getShellRender(index);
                    // lodShellOptional.ifPresent(lodShell -> lodShell.addShell(velocity));
                }
            }
        }
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â° display ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ¦Ã‹Å“Ã…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
     *
     * @return ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¨Ã‚Â¡Ã‚Â¨
     */
    public LuaTable getStateMachineParams() {
        LuaTable param = display.getStateMachineParam();
        return param == null ? new LuaTable() : param;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ NBT ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<br/>
     * ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¦Ã¢â‚¬Å¾Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã‚Â ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¤Ã‚Â¾Ã‚Â§ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ NBT ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<br/>
     * ÃƒÂ¤Ã‚Â½Ã‚Â ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¦Ã¢â‚¬Å“Ã‚ÂÃƒÂ¤Ã‚Â½Ã…â€œ
     * @return NBT ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
     */
    public LuaNbtAccessor getNbtAccessor() {
        return nbtUtil;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID
     *
     * @return ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID, ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ©Ã¢â‚¬ÂÃ¢â€žÂ¢ÃƒÂ¨Ã‚Â¯Ã‚Â¯ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID 'tacz:empty'
     */
    public String getAttachment(String type) {
        try {
            AttachmentType t = AttachmentType.valueOf(type);
            return iGun.getAttachmentId(currentGunItem, t).toString();
        } catch (IllegalArgumentException e) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID.toString();
        }
    }

    /**
     * ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¨Ã‚Â¯Ã‚Â·ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â±Ã‚Â¡ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public void setCurrentGunItem(ItemStack currentGunItem) {
        this.currentGunItem = currentGunItem;
        this.iGun = IGun.getIGunOrNull(currentGunItem);
        if (iGun != null) {
            // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] getGunDisplay() retorna ClientGunIndex, precisa converter para GunDisplayInstance
            // display = TimelessAPI.getGunDisplay(currentGunItem).orElse(null);
            display = null; // Temporariamente null atÃƒÆ’Ã‚Â© migraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o completa
            gunData = TimelessAPI.getClientGunIndex(iGun.getGunId(currentGunItem))
                    .map(ClientGunIndex::getGunData).orElse(null);
        }
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] hasTag() e getTag() removidos - migrar para DataComponents
        // if (currentGunItem.hasTag()) {
        //     nbtUtil = new LuaNbtAccessor(currentGunItem.getTag());
        // } else {
        //     nbtUtil = null;
        // }
        nbtUtil = null; // TemporÃƒÆ’Ã‚Â¡rio atÃƒÆ’Ã‚Â© migraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de DataComponents
    }
}































































