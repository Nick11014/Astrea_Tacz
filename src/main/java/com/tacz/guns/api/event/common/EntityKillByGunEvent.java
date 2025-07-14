package com.tacz.guns.api.event.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.LogicalSide;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‚ÂÃ¢â€šÂ¬ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
 * 
 * TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima atÃƒÆ’Ã‚Â© KubeJSGunEventPoster ser habilitado
 */
public class EntityKillByGunEvent extends Event /* implements KubeJSGunEventPoster<EntityKillByGunEvent> */ {
    private final Entity bullet;
    private final @Nullable LivingEntity killedEntity;
    private final @Nullable LivingEntity attacker;
    private final ResourceLocation gunId;
    private final ResourceLocation gunDisplayId;
    private final float baseDamage;
    private final DamageSource nonApPartDamageSource;
    private final DamageSource apPartDamageSource;
    private final boolean isHeadShot;
    private final float headshotMultiplier;
    private final LogicalSide logicalSide;

    public EntityKillByGunEvent(Entity bullet, @Nullable LivingEntity hurtEntity, @Nullable LivingEntity attacker,
                                ResourceLocation gunId, ResourceLocation gunDisplayId, float baseDamage, @Nullable Pair<DamageSource, DamageSource> sources,
                                boolean isHeadShot, float headshotMultiplier, LogicalSide logicalSide) {
        this.bullet = bullet;
        this.killedEntity = hurtEntity;
        this.attacker = attacker;
        this.gunId = gunId;
        this.gunDisplayId = gunDisplayId;
        this.baseDamage = baseDamage;
        this.nonApPartDamageSource = Optional.ofNullable(sources).map(Pair::getLeft).orElse(null);
        this.apPartDamageSource = Optional.ofNullable(sources).map(Pair::getRight).orElse(null);
        this.isHeadShot = isHeadShot;
        this.headshotMultiplier = headshotMultiplier;
        this.logicalSide = logicalSide;
        // TODO: Re-habilitar quando KubeJSGunEventPoster for habilitado
        // postEventToKubeJS(this);
    }

    /**
     * ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¨Ã‚Â¯Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨
     */
    public Entity getBullet() {
        return bullet;
    }

    @Nullable
    public LivingEntity getKilledEntity() {
        return killedEntity;
    }

    @Nullable
    public LivingEntity getAttacker() {
        return attacker;
    }

    public ResourceLocation getGunId() {
        return gunId;
    }

    public float getBaseDamage() {
        return baseDamage;
    }

    public DamageSource getDamageSource(GunDamageSourcePart part) {
        if (logicalSide.isClient()) {
            throw new UnsupportedOperationException("DamageSource about gun hit is not available on client side!");
        }
        return part == GunDamageSourcePart.ARMOR_PIERCING ? apPartDamageSource : nonApPartDamageSource;
    }

    public boolean isHeadShot() {
        return isHeadShot;
    }

    public float getHeadshotMultiplier() {
        return headshotMultiplier;
    }

    public LogicalSide getLogicalSide() {
        return logicalSide;
    }

    public ResourceLocation getGunDisplayId() {
        return gunDisplayId;
    }
}































































