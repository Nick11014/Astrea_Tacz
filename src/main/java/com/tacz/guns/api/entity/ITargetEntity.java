package com.tacz.guns.api.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

/**
 * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ©Ã‚ÂÃ…Â¾ {@link LivingEntity} ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â 
 */
public interface ITargetEntity {
    /**
     * @param projectile ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“
     * @param result     ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
     * @param source     ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
     * @param damage     ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
     */
    void onProjectileHit(Entity projectile, EntityHitResult result, DamageSource source, float damage);
}































































