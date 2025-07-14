package com.tacz.guns.util;

import com.tacz.guns.config.util.HeadShotAABBConfigRead;
import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EntityUtil {
    private static final Predicate<Entity> PROJECTILE_TARGETS = input -> input != null && input.isPickable() && !input.isSpectator();
    @Nullable
    public static EntityKineticBullet.EntityResult findEntityOnPath(Projectile bulletEntity, Vec3 startVec, Vec3 endVec) {

        Vec3 hitVec = null;
        Entity hitEntity = null;
        boolean headshot = false;
        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ tick ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“
        List<Entity> entities = bulletEntity.level().getEntities(bulletEntity, bulletEntity.getBoundingBox().expandTowards(bulletEntity.getDeltaMovement()).inflate(1.0), PROJECTILE_TARGETS);
        double closestDistance = Double.MAX_VALUE;
        Entity owner = bulletEntity.getOwner();
        for (Entity entity : entities) {
            // ÃƒÂ§Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â  Config ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
            if (!entity.equals(owner)) {
                // ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ¤Ã‚Â¹Ã‹Å“ÃƒÂ¥Ã‚Â®Ã‚Â¢
                if (owner != null && entity.isPassengerOfSameVehicle(owner)) {
                    continue;
                }
                EntityKineticBullet.EntityResult result = getHitResult(bulletEntity, entity, startVec, endVec);
                if (result == null) {
                    continue;
                }
                Vec3 hitPos = result.getHitPos();
                double distanceToHit = startVec.distanceTo(hitPos);
                if (entity.isAlive()) {
                    if (distanceToHit < closestDistance) {
                        hitVec = hitPos;
                        hitEntity = entity;
                        closestDistance = distanceToHit;
                        headshot = result.isHeadshot();
                    }
                }
            }
        }
        return hitEntity != null ? new EntityKineticBullet.EntityResult(hitEntity, hitVec, headshot) : null;
    }

    @NotNull
    public static List<EntityKineticBullet.EntityResult> findEntitiesOnPath(Projectile bulletEntity, Vec3 startVec, Vec3 endVec) {
        List<EntityKineticBullet.EntityResult> hitEntities = new ArrayList<>();
        List<Entity> entities = bulletEntity.level().getEntities(bulletEntity, bulletEntity.getBoundingBox().expandTowards(bulletEntity.getDeltaMovement()).inflate(1.0), PROJECTILE_TARGETS);
        Entity owner = bulletEntity.getOwner();
        for (Entity entity : entities) {
            if (!entity.equals(owner)) {
                if (owner != null && entity.equals(owner.getVehicle())) {
                    continue;
                }
                EntityKineticBullet.EntityResult result = getHitResult(bulletEntity, entity, startVec, endVec);
                if (result == null) {
                    continue;
                }
                if (entity.isAlive()) {
                    hitEntities.add(result);
                }
            }
        }
        return hitEntities;
    }

    @Nullable
    protected static EntityKineticBullet.EntityResult getHitResult(Projectile bulletEntity, Entity entity, Vec3 startVec, Vec3 endVec) {
        AABB boundingBox = HitboxHelper.getFixedBoundingBox(entity, bulletEntity.getOwner());
        // ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂºÃ‚Â¿ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ boundingBox ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹
        Vec3 hitPos = boundingBox.clip(startVec, endVec).orElse(null);
        // ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã‚Â´ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡
        if (hitPos == null) {
            return null;
        }        Vec3 hitBoxPos = hitPos.subtract(entity.position());
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        // ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
        if (entityId != null) {
            AABB aabb = HeadShotAABBConfigRead.getAABB(entityId);
            if (aabb != null) {
                return new EntityKineticBullet.EntityResult(entity, hitPos, aabb.contains(hitBoxPos));
            }
        }
        // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚Âª
        boolean headshot = false;
        float eyeHeight = entity.getEyeHeight();
        if ((eyeHeight - 0.25) < hitBoxPos.y && hitBoxPos.y < (eyeHeight + 0.25)) {
            headshot = true;
        }
        return new EntityKineticBullet.EntityResult(entity, hitPos, headshot);
    }
}































































