package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.init.ModDataComponents;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Accessor para dados de armas usando DataComponents (NeoForge 1.21.1+)
 * 
 * Esta interface migra do sistema NBT legado para o novo sistema DataComponent.
 * Todos os métodos agora usam os DataComponents definidos em ModDataComponents
 * em vez de acessar diretamente as tags NBT.
 * 
 * @see ModDataComponents para as definições dos componentes
 */
public interface GunItemDataAccessor extends IGun {
    // NOTA: As constantes abaixo são mantidas para compatibilidade com sistemas 
    // de acessórios que ainda usam CompoundTag temporariamente
    String GUN_ID_TAG = "GunId";
    String GUN_FIRE_MODE_TAG = "GunFireMode";
    String GUN_HAS_BULLET_IN_BARREL = "HasBulletInBarrel";
    String GUN_CURRENT_AMMO_COUNT_TAG = "GunCurrentAmmoCount";
    String GUN_ATTACHMENT_BASE = "Attachment";
    String GUN_EXP_TAG = "GunLevelExp";
    String GUN_DUMMY_AMMO = "DummyAmmo";
    String GUN_MAX_DUMMY_AMMO = "MaxDummyAmmo";
    String GUN_ATTACHMENT_LOCK = "AttachmentLock";
    String GUN_DISPLAY_ID_TAG = "GunDisplayId";
    String LASER_COLOR_TAG = "LaserColor";
    String GUN_OVERHEAT_TAG = "HeatAmount";
    String GUN_OVERHEAT_LOCK_TAG = "OverHeated";@Override
    default boolean useDummyAmmo(ItemStack gun) {
        return gun.has(ModDataComponents.GUN_DUMMY_AMMO.get());
    }

    @Override
    default int getDummyAmmoAmount(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_DUMMY_AMMO.get(), 0);
    }

    @Override
    default void setDummyAmmoAmount(ItemStack gun, int amount) {
        gun.set(ModDataComponents.GUN_DUMMY_AMMO.get(), Math.max(amount, 0));
    }

    @Override
    default void addDummyAmmoAmount(ItemStack gun, int amount) {
        if (!useDummyAmmo(gun)) {
            return;
        }
        int maxDummyAmmo = Integer.MAX_VALUE;
        if (hasMaxDummyAmmo(gun)) {
            maxDummyAmmo = getMaxDummyAmmoAmount(gun);
        }
        amount = Math.min(getDummyAmmoAmount(gun) + amount, maxDummyAmmo);
        gun.set(ModDataComponents.GUN_DUMMY_AMMO.get(), Math.max(amount, 0));
    }

    @Override
    default boolean hasMaxDummyAmmo(ItemStack gun) {
        return gun.has(ModDataComponents.GUN_MAX_DUMMY_AMMO.get());
    }

    @Override
    default int getMaxDummyAmmoAmount(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_MAX_DUMMY_AMMO.get(), 0);
    }

    @Override
    default void setMaxDummyAmmoAmount(ItemStack gun, int amount) {
        gun.set(ModDataComponents.GUN_MAX_DUMMY_AMMO.get(), Math.max(amount, 0));
    }

    @Override
    default boolean hasAttachmentLock(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_ATTACHMENT_LOCK.get(), false);
    }

    @Override
    default void setAttachmentLock(ItemStack gun, boolean lock) {
        gun.set(ModDataComponents.GUN_ATTACHMENT_LOCK.get(), lock);
    }    @Override
    @Nonnull
    default ResourceLocation getGunId(ItemStack gun) {
        ResourceLocation gunId = gun.get(ModDataComponents.GUN_ID.get());
        return Objects.requireNonNullElse(gunId, DefaultAssets.EMPTY_GUN_ID);
    }

    @Override
    default void setGunId(ItemStack gun, @Nullable ResourceLocation gunId) {
        if (gunId != null) {
            gun.set(ModDataComponents.GUN_ID.get(), gunId);
        } else {
            gun.remove(ModDataComponents.GUN_ID.get());
        }
    }

    @Override
    @NotNull
    default ResourceLocation getGunDisplayId(ItemStack gun) {
        ResourceLocation gunDisplayId = gun.get(ModDataComponents.GUN_DISPLAY_ID.get());
        return Objects.requireNonNullElse(gunDisplayId, DefaultAssets.DEFAULT_GUN_DISPLAY_ID);
    }

    @Override
    default void setGunDisplayId(ItemStack gun, ResourceLocation displayId) {
        if (displayId != null) {
            gun.set(ModDataComponents.GUN_DISPLAY_ID.get(), displayId);
        } else {
            gun.remove(ModDataComponents.GUN_DISPLAY_ID.get());
        }
    }    @Override
    default int getLevel(ItemStack gun) {
        int exp = getExp(gun);
        return getLevel(exp);
    }

    @Override
    default int getExp(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_EXP.get(), 0);
    }

    @Override
    default int getExpToNextLevel(ItemStack gun) {
        int exp = getExp(gun);
        int level = getLevel(exp);
        if (level >= getMaxLevel()) {
            return 0;
        }
        int nextLevelExp = getExp(level + 1);
        return nextLevelExp - exp;
    }

    @Override
    default int getExpCurrentLevel(ItemStack gun) {
        int exp = getExp(gun);
        int level = getLevel(exp);
        if (level <= 0) {
            return exp;
        } else {
            return exp - getExp(level - 1);
        }
    }    @Override
    default FireMode getFireMode(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_FIRE_MODE.get(), FireMode.UNKNOWN);
    }

    @Override
    default void setFireMode(ItemStack gun, @Nullable FireMode fireMode) {
        if (fireMode != null) {
            gun.set(ModDataComponents.GUN_FIRE_MODE.get(), fireMode);
        } else {
            gun.set(ModDataComponents.GUN_FIRE_MODE.get(), FireMode.UNKNOWN);
        }
    }

    @Override
    default int getCurrentAmmoCount(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_CURRENT_AMMO_COUNT.get(), 0);
    }

    @Override
    default void setCurrentAmmoCount(ItemStack gun, int ammoCount) {
        gun.set(ModDataComponents.GUN_CURRENT_AMMO_COUNT.get(), Math.max(ammoCount, 0));
    }

    @Override
    default void reduceCurrentAmmoCount(ItemStack gun) {
        // 只在不使用背包直读的情况下减少 AmmoCount
        if (!useInventoryAmmo(gun)) {
            setCurrentAmmoCount(gun, getCurrentAmmoCount(gun) - 1);
        }
    }    @Override
    @Nullable
    default CompoundTag getAttachmentTag(ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return null;
        }
        CompoundTag attachments = gun.get(ModDataComponents.GUN_ATTACHMENTS.get());
        if (attachments == null) {
            return null;
        }
        String key = GUN_ATTACHMENT_BASE + type.name();
        if (attachments.contains(key, Tag.TAG_COMPOUND)) {
            CompoundTag allItemStackTag = attachments.getCompound(key);
            if (allItemStackTag.contains("tag", Tag.TAG_COMPOUND)) {
                return allItemStackTag.getCompound("tag");
            }
        }
        return null;
    }

    @Override
    @NotNull
    default ItemStack getBuiltinAttachment(ItemStack gun, AttachmentType type) {
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun == null) {
            return ItemStack.EMPTY;
        }
        CommonGunIndex index = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun)).orElse(null);
        if (index != null){
            var builtin = index.getGunData().getBuiltInAttachments();
            if (builtin.containsKey(type)) {
                return AttachmentItemBuilder.create().setId(builtin.get(type)).build();
            }
        }
        return ItemStack.EMPTY;
    }    @Override
    @Nonnull
    default ItemStack getAttachment(ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return ItemStack.EMPTY;
        }
        CompoundTag attachments = gun.get(ModDataComponents.GUN_ATTACHMENTS.get());
        if (attachments == null) {
            return ItemStack.EMPTY;
        }
        String key = GUN_ATTACHMENT_BASE + type.name();
        if (attachments.contains(key, Tag.TAG_COMPOUND)) {
            return ItemStack.parseOptional(BuiltInRegistries.ITEM.asLookup(), attachments.getCompound(key)).orElse(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }

    @Override
    @NotNull
    default ResourceLocation getBuiltInAttachmentId(ItemStack gun, AttachmentType type) {
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun == null) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID;
        }
        CommonGunIndex index = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun)).orElse(null);
        if (index != null){
            var builtin = index.getGunData().getBuiltInAttachments();
            if (builtin.containsKey(type)) {
                return builtin.get(type);
            }
        }
        return DefaultAssets.EMPTY_ATTACHMENT_ID;
    }

    @Override
    @Nonnull
    default ResourceLocation getAttachmentId(ItemStack gun, AttachmentType type) {
        CompoundTag attachmentTag = this.getAttachmentTag(gun, type);
        if (attachmentTag != null) {
            return AttachmentItemDataAccessor.getAttachmentIdFromTag(attachmentTag);
        }
        return DefaultAssets.EMPTY_ATTACHMENT_ID;
    }    @Override
    default void installAttachment(@Nonnull ItemStack gun, @Nonnull ItemStack attachment) {
        if (!allowAttachment(gun, attachment)) {
            return;
        }
        IAttachment iAttachment = IAttachment.getIAttachmentOrNull(attachment);
        if (iAttachment == null) {
            return;
        }
        CompoundTag attachments = gun.getOrDefault(ModDataComponents.GUN_ATTACHMENTS.get(), new CompoundTag());
        String key = GUN_ATTACHMENT_BASE + iAttachment.getType(attachment).name();
        CompoundTag attachmentTag = (CompoundTag) attachment.save(BuiltInRegistries.ITEM.asLookup());
        attachments.put(key, attachmentTag);
        gun.set(ModDataComponents.GUN_ATTACHMENTS.get(), attachments);
    }

    @Override
    default void unloadAttachment(@Nonnull ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return;
        }
        CompoundTag attachments = gun.getOrDefault(ModDataComponents.GUN_ATTACHMENTS.get(), new CompoundTag());
        String key = GUN_ATTACHMENT_BASE + type.name();
        CompoundTag attachmentTag = (CompoundTag) ItemStack.EMPTY.save(BuiltInRegistries.ITEM.asLookup());
        attachments.put(key, attachmentTag);
        gun.set(ModDataComponents.GUN_ATTACHMENTS.get(), attachments);
    }

    @Override
    default float getAimingZoom(ItemStack gunItem) {
        float zoom = 1;
        ResourceLocation scopeId = this.getAttachmentId(gunItem, AttachmentType.SCOPE);
        boolean builtin = false;
        if (scopeId.equals(DefaultAssets.EMPTY_ATTACHMENT_ID)) {
            scopeId = getBuiltInAttachmentId(gunItem, AttachmentType.SCOPE);
            builtin = true;
        }
        if (!DefaultAssets.isEmptyAttachmentId(scopeId)) {
            CompoundTag attachmentTag = this.getAttachmentTag(gunItem, AttachmentType.SCOPE);
            int zoomNumber = builtin ? 0 : AttachmentItemDataAccessor.getZoomNumberFromTag(attachmentTag);
            float[] zooms = TimelessAPI.getClientAttachmentIndex(scopeId).map(ClientAttachmentIndex::getZoom).orElse(null);
            if (zooms != null) {
                zoom = zooms[zoomNumber % zooms.length];
            }
        } else {
            zoom = TimelessAPI.getGunDisplay(gunItem).map(GunDisplayInstance::getIronZoom).orElse(1f);
        }
        return zoom;
    }    @Override
    default boolean hasBulletInBarrel(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_HAS_BULLET_IN_BARREL.get(), false);
    }

    @Override
    default void setBulletInBarrel(ItemStack gun, boolean bulletInBarrel) {
        gun.set(ModDataComponents.GUN_HAS_BULLET_IN_BARREL.get(), bulletInBarrel);
    }

    @Override
    default boolean hasCustomLaserColor(ItemStack gun) {
        return gun.has(ModDataComponents.LASER_COLOR.get());
    }

    @Override
    default int getLaserColor(ItemStack gun) {
        if (!hasCustomLaserColor(gun)) {
            return 0xFF0000;
        }
        return gun.getOrDefault(ModDataComponents.LASER_COLOR.get(), 0xFF0000);
    }

    @Override
    default void setLaserColor(ItemStack gun, int color) {
        gun.set(ModDataComponents.LASER_COLOR.get(), color);
    }    /**
     * Heat Data
     */
    @Override
    default boolean hasHeatData(ItemStack gun) {
        return gun.has(ModDataComponents.GUN_OVERHEAT.get());
    }

    @Override
    default boolean isOverheatLocked(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_OVERHEAT_LOCK.get(), false);
    }

    @Override
    default void setOverheatLocked(ItemStack gun, boolean locked) {
        gun.set(ModDataComponents.GUN_OVERHEAT_LOCK.get(), locked);
    }

    @Override
    default float getHeatAmount(ItemStack gun) {
        if (hasHeatData(gun)) {
            return gun.getOrDefault(ModDataComponents.GUN_OVERHEAT.get(), 0f);
        }
        return 0f;
    }

    @Override
    default void setHeatAmount(ItemStack gun, float amount) {
        gun.set(ModDataComponents.GUN_OVERHEAT.get(), amount >= 0 ? amount : 0f);
    }

    @Override
    default float lerpRPM(ItemStack gun) {
        return TimelessAPI.getCommonGunIndex(getGunId(gun))
                .map(index -> index.getGunData().getHeatData())
                .map(heatData -> {
                    float heatPercentage = (getHeatAmount(gun) / heatData.getHeatMax());
                    return Mth.lerp(heatPercentage, heatData.getMinRpmMod(), heatData.getMaxRpmMod());
                }).orElse(1f);
    }

    @Override
    default float lerpInaccuracy(ItemStack gun) {
        return TimelessAPI.getCommonGunIndex(getGunId(gun))
                .map(index -> index.getGunData().getHeatData())
                .map(heatData -> {
                    float heatPercentage = (getHeatAmount(gun) / heatData.getHeatMax());
                    return Mth.lerp(heatPercentage, heatData.getMinInaccuracy(), heatData.getMaxInaccuracy());
                }).orElse(1f);
    }
}
