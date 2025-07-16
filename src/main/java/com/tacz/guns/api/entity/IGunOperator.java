package com.tacz.guns.api.entity;

import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public interface IGunOperator {
    
    static IGunOperator fromLivingEntity(LivingEntity entity) {
        return (IGunOperator) entity;
    }

    long getSynShootCoolDown();

    long getSynMeleeCoolDown();

    long getSynDrawCoolDown();

    boolean getSynIsBolting();

    ReloadState getSynReloadState();

    float getSynAimingProgress();

    boolean getSynIsAiming();

    float getSynSprintTime();

    void initialData();

    void draw(Supplier<ItemStack> itemStackSupplier);

    void bolt();

    void reload();

    void cancelReload();

    void fireSelect();

    void zoom();

    void melee();

    ShootResult shoot(Supplier<Float> pitch, Supplier<Float> yaw);

    ShootResult shoot(Supplier<Float> pitch, Supplier<Float> yaw, long timestamp);

    boolean needCheckAmmo();

    boolean consumesAmmoOrNot();

    boolean getProcessedSprintStatus(boolean sprint);

    void aim(boolean isAim);

    void crawl(boolean isCrawl);

    @Nullable 
    AttachmentCacheProperty getCacheProperty();
    
    ShooterDataHolder getDataHolder();

    boolean nextBulletIsTracer(int tracerCountInterval);
}