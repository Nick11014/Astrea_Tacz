package com.tacz.guns.item;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.api.util.LuaEntityAccessor;
import com.tacz.guns.api.util.LuaNbtAccessor;
import com.tacz.guns.client.animation.statemachine.GunAnimationStateContext;
import com.tacz.guns.config.common.AmmoConfig;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunFire;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.AmmoSpeedModifier;
import com.tacz.guns.resource.modifier.custom.InaccuracyModifier;
import com.tacz.guns.resource.modifier.custom.SilenceModifier;
import com.tacz.guns.resource.pojo.data.gun.*;
import com.tacz.guns.sound.SoundManager;
import com.tacz.guns.util.AttachmentDataUtils;
import com.tacz.guns.util.CycleTaskHelper;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.LogicalSide;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModernKineticGunScriptAPI {
    public static String MARKER = "ScriptAPI";

    private LivingEntity shooter;

    private ShooterDataHolder dataHolder;

    private ItemStack itemStack;

    private AbstractGunItem abstractGunItem;

    private CommonGunIndex gunIndex;

    private ResourceLocation gunId;

    private ResourceLocation gunDisplayId;

    private Supplier<Float> pitchSupplier;

    private Supplier<Float> yawSupplier;

    private LuaNbtAccessor nbtUtil;

    private LuaEntityAccessor entityAccessor;

    /**
     * ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¨Ã¢â€šÂ¬Ã†â€™ÃƒÂ¨Ã¢â€žÂ¢Ã¢â‚¬ËœÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â(ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã…â€™Ã‚ÂÃƒÂ¥Ã…â€™Ã‚ÂÃƒÂ§Ã‚Â­Ã¢â‚¬Â°)ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‚Â¤Ã…Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¤Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â£ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¨Ã‚Â¿Ã…Â¾ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ£Ã¢â€šÂ¬Ã‚Â
     * @param consumeAmmo ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯
     */
    public void shootOnce(boolean consumeAmmo){
        // TODO: [OBJECT STRATEGY] VerificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de null para suportar Object Strategy
        if (gunIndex == null) {
            return; // Object Strategy: retorna early se index nÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­vel
        }
        
        GunData gunData = gunIndex.getGunData();
        BulletData bulletData = gunIndex.getBulletData();
        IGunOperator gunOperator = IGunOperator.fromLivingEntity(shooter);

        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
        AttachmentCacheProperty cacheProperty = gunOperator.getCacheProperty();
        if (cacheProperty == null) {
            return;
        }

        //Handle Heat Data
        float heatInaccuracy = 1f;
        if(hasHeatData()) {
            GunHeatData heatData = gunIndex.getGunData().getHeatData();
            float heatPercentage = (getHeatAmount() / heatData.getHeatMax());
            heatInaccuracy *= Mth.lerp(heatPercentage, heatData.getMinInaccuracy(), heatData.getMaxInaccuracy());
        }

        // ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â£ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ¥Ã¢â‚¬Å“Ã‚Â
        InaccuracyType inaccuracyType = InaccuracyType.getInaccuracyType(shooter);
        final float inaccuracy = Math.max(0, cacheProperty.<Map<InaccuracyType, Float>>getCache(InaccuracyModifier.ID).get(inaccuracyType) * heatInaccuracy);

        // ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ¥Ã¢â‚¬Å“Ã‚Â
        Pair<Integer, Boolean> silence = cacheProperty.getCache(SilenceModifier.ID);
        final int soundDistance = silence.first();
        final boolean useSilenceSound = silence.right();

        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ©Ã‚Â£Ã…Â¾ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¥Ã‚ÂºÃ‚Â¦
        float speed = cacheProperty.<Float>getCache(AmmoSpeedModifier.ID);
        speed *= AmmoConfig.GLOBAL_BULLET_SPEED_MODIFIER.get();
        float processedSpeed = Mth.clamp(speed / 20, 0, Float.MAX_VALUE);
        // ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¤Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
        int bulletAmount = Math.max(bulletData.getBulletAmount(), 1);

        // ÃƒÂ¨Ã‚Â¿Ã…Â¾ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
        FireMode fireMode = abstractGunItem.getFireMode(itemStack);
        int cycles = fireMode == FireMode.BURST ? gunData.getBurstData().getCount() : 1;
        // ÃƒÂ¨Ã‚Â¿Ã…Â¾ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬Â
        long period = fireMode == FireMode.BURST ? gunData.getBurstShootInterval() : 1;

        CycleTaskHelper.addCycleTask(() -> {
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â­Ã‚Â»ÃƒÂ¤Ã‚ÂºÃ‚Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
            if (shooter.isDeadOrDying()) {
                return false;
            }
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â­Ã‚Â¦ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
            if (!shooter.getMainHandItem().equals(itemStack) || shooter.getMainHandItem().isEmpty()) {
                return false;
            }
            // ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
            boolean fire = !NeoForge.EVENT_BUS.post(new GunFireEvent(shooter, itemStack, LogicalSide.SERVER));
            if (fire) {
                NetworkHandler.sendToTrackingEntity(new ServerMessageGunFire(shooter.getId(), itemStack), shooter);
                // ÃƒÂ¥Ã¢â‚¬Â°Ã…Â ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯
                if (consumeAmmo) {
                    if (!this.reduceAmmoOnce()) {
                        return false;
                    }
                }
                //Handle Heat Data
                if(gunIndex.getGunData().hasHeatData()) {
                    Optional.ofNullable(gunIndex.getScript())
                            .map(script -> checkFunction(script.get("handle_shoot_heat")))
                            .ifPresentOrElse(
                                    func -> func.call(CoerceJavaToLua.coerce(this)),
                                    this::handleShootHeat
                            );
                }
                // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã‹â€ pitch ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ yawÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
                float pitch = pitchSupplier != null ? pitchSupplier.get() : shooter.getXRot();
                float yaw = yawSupplier != null ? yawSupplier.get() : shooter.getYRot();
                // ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
                Level world = shooter.level();
                ResourceLocation ammoId = gunData.getAmmoId();
                for (int i = 0; i < bulletAmount; i++) {
                    boolean isTracer = bulletData.hasTracerAmmo() && gunOperator.nextBulletIsTracer(bulletData.getTracerCountInterval());
                    EntityKineticBullet bullet = new EntityKineticBullet(world, shooter, itemStack, ammoId, gunId,
                            gunDisplayId, isTracer, gunData, bulletData);
                    abstractGunItem.doBulletSpread(dataHolder, itemStack, shooter, bullet, i, processedSpeed,
                            inaccuracy, pitch, yaw);
                    world.addFreshEntity(bullet);
                }
                // ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚Â£Ã‚Â°
                if (soundDistance > 0) {
                    String soundId = useSilenceSound ? SoundManager.SILENCE_3P_SOUND : SoundManager.SHOOT_3P_SOUND;
                    SoundManager.sendSoundToNearby(shooter, soundDistance, gunId, gunDisplayId, soundId, 0.8f, 0.9f + shooter.getRandom().nextFloat() * 0.125f);
                }
            }
            return true;
        }, period, cycles);
    }

    /**
     * ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ§Ã†â€™Ã‚Â­ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œ
     */
    public void handleShootHeat() {
        GunHeatData heatData = gunIndex.getGunData().getHeatData();
        if (heatData == null) {
            return;
        }
        float newHeat = Math.min(abstractGunItem.getHeatAmount(itemStack) + heatData.getHeatPerShot(), heatData.getHeatMax());
        abstractGunItem.setHeatAmount(itemStack, newHeat);
        if (newHeat >= heatData.getHeatMax()) {
            abstractGunItem.setOverheatLocked(itemStack, true);
        }
    }

    /**
     * ÃƒÂ¨Ã‚Â®Ã‚Â©ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ©Ã‚ÂÃ‚ÂµÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã¢â‚¬â€Ã‚Â­ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â§Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ falseÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã‚Â­Ã‚Â¥ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€žÂ¢Ã‚Â½ÃƒÂ§Ã¢â‚¬Å¾Ã‚Â¶ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã…â€œÃ‚Â¨ bolt ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ falseÃƒÂ¯Ã‚Â¼Ã…â€™
     * @return ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public boolean reduceAmmoOnce() {
        Bolt boltType = TimelessAPI.getCommonGunIndex(abstractGunItem.getGunId(itemStack))
                .map(index -> index.getGunData().getBolt())
                .orElse(null);
        // ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
        boolean hasAmmoInBarrel = abstractGunItem.hasBulletInBarrel(itemStack) && boltType != Bolt.OPEN_BOLT;
        // ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ (ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹)
        boolean hasInventoryAmmo = abstractGunItem.hasInventoryAmmo(shooter, itemStack, isReloadingNeedConsumeAmmo());
        // ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ (ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ / ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â° < 1)
        boolean noAmmo = useInventoryAmmo() && !hasInventoryAmmo ||
                !useInventoryAmmo() && abstractGunItem.getCurrentAmmoCount(itemStack) < 1;
        if (boltType == null) {
            return false;
        }
        // ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
        if (boltType == Bolt.MANUAL_ACTION) {
            // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
            if (!hasAmmoInBarrel) {
                return false;
            }
            // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
            abstractGunItem.setBulletInBarrel(itemStack, false);
            return true;
        }
        // ÃƒÂ©Ã¢â‚¬â€Ã‚Â­ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
        if (boltType == Bolt.CLOSED_BOLT) {
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¼Ã‹Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
            if (!noAmmo) {
                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ - 1
                if (useInventoryAmmo()) {
                    return consumeAmmoFromPlayer(1) == 1;
                }
                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ - 1
                abstractGunItem.reduceCurrentAmmoCount(itemStack);
                return true;
            }
            // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
            if (!hasAmmoInBarrel) {
                return false;
            }
            // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
            abstractGunItem.setBulletInBarrel(itemStack, false);
            return true;
        }
        // ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
        if (boltType == Bolt.OPEN_BOLT) {
            // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
            if (noAmmo) {
                return false;
            }
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ - 1
            if (useInventoryAmmo()) {
                return consumeAmmoFromPlayer(1) == 1;
            }
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ - 1
            abstractGunItem.reduceCurrentAmmoCount(itemStack);
            return true;
        }
        // ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ§Ã‚Â§Ã‚ÂÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã…Â¸Ã‚Â¥ Bolt ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ (ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â½Ã‚Â°)ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ false
        return false;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Âº ms
     *
     * @return ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Âº ms
     */
    public long getReloadTime() {
        if (dataHolder.reloadTimestamp == -1) {
            return 0;
        }
        return System.currentTimeMillis() - dataHolder.reloadTimestamp;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Âº ms
     *
     * @return ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Âº ms
     */
    public long getBoltTime() {
        if (!dataHolder.isBolting) {
            return 0;
        }
        return System.currentTimeMillis() - dataHolder.boltTimestamp;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
     *
     * @return ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬Â
     */
    public long getShootInterval() {
        // TODO: [OBJECT STRATEGY] VerificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de null para suportar Object Strategy
        if (gunIndex == null) {
            return 1000L; // Object Strategy: retorna valor padrÃƒÆ’Ã‚Â£o se index nÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­vel
        }
        
        FireMode fireMode = abstractGunItem.getFireMode(itemStack);
        if (fireMode == FireMode.BURST) {
            long coolDown = (long) (gunIndex.getGunData().getBurstData().getMinInterval() * 1000f);
            // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ 5 ms ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚Â¹Ã‚Â³ÃƒÂ¨Ã‚Â¡Ã‚Â¡ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸
            coolDown = coolDown - 5;
            return Math.max(coolDown, 0L);
        }
        long coolDown = gunIndex.getGunData().getShootInterval(this.shooter, fireMode, itemStack);
        // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ 5 ms ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚Â¹Ã‚Â³ÃƒÂ¨Ã‚Â¡Ã‚Â¡ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸
        coolDown = coolDown - 5;
        return Math.max(coolDown, 0L);
    }

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ timestamp(ÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´)ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Âº -1ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ timestampÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Âº -1ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public long getLastShootTimestamp() {
        return dataHolder.lastShootTimestamp + dataHolder.baseTimestamp;
    }

    /**
     * ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¦Ã‚Â¯Ã¢â‚¬ÂÃƒÂ¨Ã‚Â¾Ã†â€™ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â®Ã†â€™ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ¥Ã‹â€ Ã‚Â«ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¤Ã‚Â½Ã‚Â ÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬Å“Ã‚ÂÃƒÂ¤Ã‚Â½Ã…â€œÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param alpha ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @see GunAnimationStateContext#adjustClientShootInterval
     */
    public void adjustShootInterval(long alpha) {
        dataHolder.shootTimestamp += alpha;
    }

    /**
     * ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     *
     * @param alpha ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¥Ã‚Â¿Ã‚Â«ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¦Ã‚Â¢ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public void adjustReloadTime(long alpha) {
        dataHolder.reloadTimestamp -= alpha;
    }

    /**
     * ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     *
     * @param alpha ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¥Ã‚Â¿Ã‚Â«ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¦Ã‚Â¢ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public void adjustBoltTime(long alpha) {
        dataHolder.boltTimestamp -= alpha;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´ 0~1ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡0 ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ¦Ã…â€œÃ‚ÂªÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™1 ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public float getAimingProgress() {
        return dataHolder.aimingProgress;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â (ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°)
     */
    public int getReloadStateType() {
        return dataHolder.reloadStateType.ordinal();
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‚ÂÃ…Â ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¨Ã‚Â¿Ã…Â¾ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â (ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°)
     */
    public int getFireMode() {
        return abstractGunItem.getFireMode(itemStack).ordinal();
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯
     */
    public boolean isShootingNeedConsumeAmmo() {
        return IGunOperator.fromLivingEntity(shooter).consumesAmmoOrNot();
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¨Ã‹â€ Ã‚Â¬ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¨Ã‚Â¯Ã‚Â´ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯
     */
    public boolean isReloadingNeedConsumeAmmo() {
        return IGunOperator.fromLivingEntity(shooter).needCheckAmmo();
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     */
    public int getNeededAmmoAmount() {
        // TODO: [OBJECT STRATEGY] VerificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de null para suportar Object Strategy
        if (gunIndex == null) {
            return 0; // Object Strategy: retorna 0 se index nÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­vel
        }
        
        int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(itemStack, gunIndex.getGunData());
        int currentAmmoCount = abstractGunItem.getCurrentAmmoCount(itemStack);
        return maxAmmoCount - currentAmmoCount;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public int getAmmoAmount() {
        return abstractGunItem.getCurrentAmmoCount(itemStack);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public int getMaxAmmoCount() {
        // TODO: [OBJECT STRATEGY] VerificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de null para suportar Object Strategy
        if (gunIndex == null) {
            return 0; // Object Strategy: retorna 0 se index nÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­vel
        }
        
        return AttachmentDataUtils.getAmmoCountWithAttachment(itemStack, gunIndex.getGunData());
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´ 0 ~ 3ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡0 ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¯Ã‚Â¼Ã…â€™1 ~ 3 ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ§Ã‚ÂºÃ‚Â§ 1 ~ 3 ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£
     */
    public int getMagExtentLevel() {
        return AttachmentDataUtils.getMagExtendLevel(itemStack, gunIndex.getGunData());
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã‚Â½ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã‚Â¤Ã…Â¡ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã‚ÂºÃ‚Â«ÃƒÂ¤Ã‚Â¸Ã…Â  (ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹) ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¦Ã…Â½Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     *
     * @param neededAmount ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     * @return ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ©Ã¢â€žÂ¢Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     */
    public int consumeAmmoFromPlayer(int neededAmount) {
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚Âµ
        if (useInventoryAmmo() && !isReloadingNeedConsumeAmmo()) {
            return neededAmount;
        }
        if (abstractGunItem.useDummyAmmo(itemStack)) {
            return abstractGunItem.findAndExtractDummyAmmo(itemStack, neededAmount);
        } else {
            return shooter.getCapability(ForgeCapabilities.ITEM_HANDLER, null)
                    .map(cap -> abstractGunItem.findAndExtractInventoryAmmo(cap, itemStack, neededAmount))
                    .orElse(0);
        }
    }

    /**
     * ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã‚ÂºÃ‚Â«ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ true
     * @return ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã‚ÂºÃ‚Â«ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬â€
     */
    public boolean hasAmmoToConsume(){
        if (!isReloadingNeedConsumeAmmo()) {
            return true;
        }
        if (abstractGunItem.useDummyAmmo(itemStack)) {
            return abstractGunItem.getDummyAmmoAmount(itemStack) > 0;
        }
        return shooter.getCapability(ForgeCapabilities.ITEM_HANDLER, null).map(cap -> {
            // ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(itemStack, checkAmmoStack)) {
                    return true;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(itemStack, checkAmmoStack)) {
                    return true;
                }
            }
            return false;
        }).orElse(false);
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã…Â½Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param amount ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã…Â½Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     * @return ÃƒÂ¥Ã‚Â¤Ã…Â¡ÃƒÂ¤Ã‚Â½Ã¢â€žÂ¢ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
     */
    public int putAmmoInMagazine(int amount) {
        if (amount < 0) {
            return 0;
        }
        int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(itemStack, gunIndex.getGunData());
        int currentAmmoCount = abstractGunItem.getCurrentAmmoCount(itemStack);
        int newAmmoCount = currentAmmoCount + amount;
        if (maxAmmoCount < newAmmoCount) {
            abstractGunItem.setCurrentAmmoCount(itemStack, maxAmmoCount);
            return newAmmoCount - maxAmmoCount;
        } else {
            abstractGunItem.setCurrentAmmoCount(itemStack, newAmmoCount);
            return 0;
        }
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param amount ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     * @return ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     */
    public int removeAmmoFromMagazine(int amount) {
        if (amount < 0) {
            return 0;
        }
        int currentAmmoCount = abstractGunItem.getCurrentAmmoCount(itemStack);
        if (currentAmmoCount < amount) {
            abstractGunItem.setCurrentAmmoCount(itemStack, 0);
            return currentAmmoCount;
        } else {
            abstractGunItem.setCurrentAmmoCount(itemStack, currentAmmoCount - amount);
            return amount;
        }
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
     */
    public int getAmmoCountInMagazine() {
        return abstractGunItem.getCurrentAmmoCount(itemStack);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹.ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ falseÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public boolean hasAmmoInBarrel() {
        Bolt boltType = gunIndex.getGunData().getBolt();
        return boltType != Bolt.OPEN_BOLT && abstractGunItem.hasBulletInBarrel(itemStack);
    }

    /**
     * ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹
     */
    public void setAmmoInBarrel(boolean ammoInBarrel) {
        abstractGunItem.setBulletInBarrel(itemStack, ammoInBarrel);
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¦Ã¢â‚¬Å¾Ã‚Â lua ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â±Ã‚Â¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â¼Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â¥ÃƒÂ¤Ã‚Â¼Ã‚Â ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬â„¢ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â·Ã‚Â¨ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¤Ã‚Â¼Ã‚Â ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬â„¢ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param luaValue ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ lua ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â±Ã‚Â¡
     */
    public void cacheScriptData(LuaValue luaValue) {
        this.dataHolder.scriptData = luaValue;
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ lua ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â±Ã‚Â¡ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ lua ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â±Ã‚Â¡
     */
    public LuaValue getCachedScriptData() {
        return dataHolder.scriptData;
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â° data ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ¦Ã‹Å“Ã…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
     *
     * @return ÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¨Ã‚Â¡Ã‚Â¨
     */
    public LuaTable getScriptParams() {
        LuaTable param = gunIndex.getScriptParam();
        return param == null ? new LuaTable() : param;
    }

    /**
     * ÃƒÂ¥Ã‚Â§Ã¢â‚¬ÂÃƒÂ¦Ã¢â‚¬Â°Ã‹Å“ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ§Ã‚ÂºÃ‚Â¿ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã‚ÂºÃ‚Â¿ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚Â¥ÃƒÂ¦Ã‚Â Ã‚Â¼ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â Ã‚Â³ÃƒÂ¤Ã‚ÂºÃ…Â½ TPSÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param value    ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ boolean ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ LuaFunctionÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ false ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ©Ã¢â€šÂ¬Ã¢â€šÂ¬ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @param delayMs  ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @param periodMs ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ©Ã…Â¡Ã¢â‚¬ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * @param cycles   ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¾Ã‚ÂªÃƒÂ§Ã…Â½Ã‚Â¯ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡-1 ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    public void safeAsyncTask(LuaValue value, long delayMs, long periodMs, int cycles) {
        LuaFunction func = value.checkfunction();
        CycleTaskHelper.addCycleTask(() -> func.call().checkboolean(), delayMs, periodMs, cycles);
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¦Ã‚Â¯Ã‚Â«ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @return ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     */
    public long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID
     *
     * @return ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID, ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ©Ã¢â‚¬ÂÃ¢â€žÂ¢ÃƒÂ¨Ã‚Â¯Ã‚Â¯ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ ID 'tacz:empty'
     */
    public String getAttachment(String type) {
        try {
            AttachmentType t = AttachmentType.valueOf(type);
            return abstractGunItem.getAttachmentId(itemStack, t).toString();
        } catch (IllegalArgumentException e) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID.toString();
        }
    }

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ NBT ÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¦ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã‚Â ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ©Ã‚Â¢Ã¢â‚¬ËœÃƒÂ§Ã‚Â¹Ã‚ÂÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<br/>
     * ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¨Ã‚Â§Ã‚Â {@link LuaNbtAccessor}
     * @return NBT ÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
     */
    public LuaNbtAccessor getNbt() {
        return nbtUtil;
    }

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â·Ã‚Â¥ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¤Ã‚Â¾Ã¢â‚¬ÂºÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã‚ÂÃ‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â€šÂ¬Ã‚ÂActionBarÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<br/>
     * ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¨Ã‚Â§Ã‚Â {@link LuaEntityAccessor}
     * @return ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
     */
    public LuaEntityAccessor getEntityUtil() {
        if (entityAccessor == null) {
            entityAccessor = new LuaEntityAccessor(shooter);
        }
        return entityAccessor;
    }

    public void setShooter(LivingEntity shooter) {
        this.shooter = shooter;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        initGunItem();
    }

    public void setPitchSupplier(Supplier<Float> pitchSupplier) {
        this.pitchSupplier = pitchSupplier;
    }

    public void setYawSupplier(Supplier<Float> yawSupplier) {
        this.yawSupplier = yawSupplier;
    }

    public LivingEntity getShooter() {
        return shooter;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public AbstractGunItem getAbstractGunItem() {
        return abstractGunItem;
    }

    public CommonGunIndex getGunIndex() {
        return gunIndex;
    }

    public void setHeatAmount(float amount) {
        abstractGunItem.setHeatAmount(itemStack, amount);
    }

    public float getHeatAmount() {
        return abstractGunItem.getHeatAmount(itemStack);
    }

    public boolean hasHeatData() {
        // TODO: [OBJECT STRATEGY] VerificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de null para suportar Object Strategy
        if (gunIndex == null) {
            return false; // Object Strategy: retorna false se index nÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­vel
        }
        return gunIndex.getGunData().getHeatData() != null;
    }

    public float getHeatMinRpm() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMinRpmMod();
        return 0f;
    }

    public float getHeatMaxRpm() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMaxRpmMod();
        return 0f;
    }

    public float getHeatMinInaccuracy() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMinInaccuracy();
        return 0f;
    }

    public float getHeatMaxInaccuracy() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMaxInaccuracy();
        return 0f;
    }

    public float getHeatMax() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getHeatMax();
        return 0f;
    }

    public float getHeatPerShot() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getHeatPerShot();
        return 0f;
    }

    public boolean isOverheatLocked() {
        return abstractGunItem.isOverheatLocked(itemStack);
    }

    public void setOverheatLocked(boolean locked) {
        abstractGunItem.setOverheatLocked(itemStack, locked);
    }

    public long getOverheatTime() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getOverHeatTime();
        return 0;
    }

    public long getCoolingDelay() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getCoolingDelay();
        return 0;
    }

    public float calcHeatReduction(long heatTimestamp) {
        GunHeatData heatData = gunIndex.getGunData().getHeatData();
        if (heatData != null) {
            return ((float)(System.currentTimeMillis() - heatTimestamp) / 10000f)
                    * heatData.getCoolingMultiplier();
        }
        return 0f;
    }

    // TODO: ÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹ÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ enum ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã…â€œÃ‚Â¨ lua ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ§Ã‚Â®Ã¢â€šÂ¬ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢
    public int getBoltByInt() {
        Bolt bolt = gunIndex.getGunData().getBolt();
        if (bolt == Bolt.MANUAL_ACTION) {
            return 1;
        }
        if (bolt == Bolt.CLOSED_BOLT) {
            return 2;
        }
        if (bolt == Bolt.OPEN_BOLT) {
            return 3;
        }
        return 0;
    }

    public Bolt getBolt() {
        return gunIndex.getGunData().getBolt();
    }

    public void setDataHolder(ShooterDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public boolean useInventoryAmmo() {
        return abstractGunItem.useInventoryAmmo(itemStack);
    }

    ShooterDataHolder getDataHolder() {
        return this.dataHolder;
    }

    private void initGunItem() {
        if (itemStack == null || !(itemStack.getItem() instanceof AbstractGunItem gunItem)) {
            gunIndex = null;
            abstractGunItem = null;
            return;
        }
        gunId = gunItem.getGunId(itemStack);
        gunDisplayId = gunItem.getGunDisplayId(itemStack);
        Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(gunId);
        gunIndex = gunIndexOptional.orElse(null);
        abstractGunItem = gunItem;
        if (itemStack.hasTag()) {
            nbtUtil = new LuaNbtAccessor(itemStack.getTag());
        }
    }


    private LuaFunction checkFunction(LuaValue luaValue) {
        if (luaValue.isfunction()) {
            return (LuaFunction) luaValue;
        } else if (luaValue.isnil()) {
            return null;
        } else {
            throw new LuaError("bad argument: function or nil expected, got " + luaValue.typename());
        }
    }
}































































