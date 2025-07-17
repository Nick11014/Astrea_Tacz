package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.gun.FireMode;
// import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.init.ModDataComponents;
import com.tacz.guns.resource.index.CommonGunIndex;
import net.minecraft.core.registries.BuiltInRegistries;
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
 * Todos os mÃƒÆ’Ã‚Â©todos agora usam os DataComponents definidos em ModDataComponents
 * em vez de acessar diretamente as tags NBT.
 * 
 * @see ModDataComponents para as definiÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Âµes dos componentes
 */
public interface GunItemDataAccessor extends IGun {
    
    default boolean useDummyAmmo(ItemStack gun) {
        return gun.has(ModDataComponents.GUN_DUMMY_AMMO.get());
    }

    
    default int getDummyAmmoAmount(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_DUMMY_AMMO.get(), 0);
    }

    
    default void setDummyAmmoAmount(ItemStack gun, int amount) {
        gun.set(ModDataComponents.GUN_DUMMY_AMMO.get(), Math.max(amount, 0));
    }

    
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

    
    default boolean hasMaxDummyAmmo(ItemStack gun) {
        return gun.has(ModDataComponents.GUN_MAX_DUMMY_AMMO.get());
    }

    
    default int getMaxDummyAmmoAmount(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_MAX_DUMMY_AMMO.get(), 0);
    }

    
    default void setMaxDummyAmmoAmount(ItemStack gun, int amount) {
        gun.set(ModDataComponents.GUN_MAX_DUMMY_AMMO.get(), Math.max(amount, 0));
    }

    
    default boolean hasAttachmentLock(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_ATTACHMENT_LOCK.get(), false);
    }

    
    default void setAttachmentLock(ItemStack gun, boolean lock) {
        gun.set(ModDataComponents.GUN_ATTACHMENT_LOCK.get(), lock);
    }    
    @Nonnull
    default ResourceLocation getGunId(ItemStack gun) {
        ResourceLocation gunId = gun.get(ModDataComponents.GUN_ID.get());
        return Objects.requireNonNullElse(gunId, DefaultAssets.EMPTY_GUN_ID);
    }

    
    default void setGunId(ItemStack gun, @Nullable ResourceLocation gunId) {
        if (gunId != null) {
            gun.set(ModDataComponents.GUN_ID.get(), gunId);
        } else {
            gun.remove(ModDataComponents.GUN_ID.get());
        }
    }

    
    @NotNull
    default ResourceLocation getGunDisplayId(ItemStack gun) {
        ResourceLocation gunDisplayId = gun.get(ModDataComponents.GUN_DISPLAY_ID.get());
        return Objects.requireNonNullElse(gunDisplayId, DefaultAssets.DEFAULT_GUN_DISPLAY_ID);
    }

    
    default void setGunDisplayId(ItemStack gun, ResourceLocation displayId) {
        if (displayId != null) {
            gun.set(ModDataComponents.GUN_DISPLAY_ID.get(), displayId);
        } else {
            gun.remove(ModDataComponents.GUN_DISPLAY_ID.get());
        }
    }    
    default int getLevel(ItemStack gun) {
        int exp = getExp(gun);
        return getLevel(exp);
    }

    
    default int getExp(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_EXP.get(), 0);
    }

    
    default int getExpToNextLevel(ItemStack gun) {
        int exp = getExp(gun);
        int level = getLevel(exp);
        if (level >= getMaxLevel()) {
            return 0;
        }
        int nextLevelExp = getExp(level + 1);
        return nextLevelExp - exp;
    }

    
    default int getExpCurrentLevel(ItemStack gun) {
        int exp = getExp(gun);
        int level = getLevel(exp);
        if (level <= 0) {
            return exp;
        } else {
            return exp - getExp(level - 1);
        }
    }    
    default FireMode getFireMode(ItemStack gun) {
        return FireMode.valueOf(gun.getOrDefault(ModDataComponents.FIRE_MODE.get(), FireMode.UNKNOWN.name()));
    }

    
    default void setFireMode(ItemStack gun, @Nullable FireMode fireMode) {
        if (fireMode != null) {
            gun.set(ModDataComponents.FIRE_MODE.get(), fireMode.name());
        } else {
            gun.set(ModDataComponents.FIRE_MODE.get(), FireMode.UNKNOWN.name());
        }
    }

    
    default int getCurrentAmmoCount(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_CURRENT_AMMO_COUNT.get(), 0);
    }

    
    default void setCurrentAmmoCount(ItemStack gun, int ammoCount) {
        gun.set(ModDataComponents.GUN_CURRENT_AMMO_COUNT.get(), Math.max(ammoCount, 0));
    }

    
    default void reduceCurrentAmmoCount(ItemStack gun) {
        if (!useInventoryAmmo(gun)) {
            setCurrentAmmoCount(gun, getCurrentAmmoCount(gun) - 1);
        }
    }    
    @Nullable
    default CompoundTag getAttachmentTag(ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return null;
        }
        CompoundTag attachments = gun.get(ModDataComponents.GUN_ATTACHMENTS.get());
        if (attachments == null) {
            return null;
        }
        String key = type.name();
        if (attachments.contains(key, Tag.TAG_COMPOUND)) {
            return attachments.getCompound(key);
        }
        return null;
    }

    
    @NotNull
    default ItemStack getBuiltinAttachment(ItemStack gun, AttachmentType type) {
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun == null) {
            return ItemStack.EMPTY;
        }
        Object indexObj = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun)).orElse(null);
        if (indexObj instanceof CommonGunIndex){
            CommonGunIndex index = (CommonGunIndex) indexObj;
            var builtin = index.getGunData().getBuiltInAttachments();
            if (builtin.containsKey(type)) {
                return AttachmentItemBuilder.create().setId(builtin.get(type)).build();
            }
        }
        return ItemStack.EMPTY;
    }    
    @Nonnull
    default ItemStack getAttachment(ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return ItemStack.EMPTY;
        }
        CompoundTag attachments = gun.get(ModDataComponents.GUN_ATTACHMENTS.get());
        if (attachments == null) {
            return ItemStack.EMPTY;
        }
        String key = type.name();
        if (attachments.contains(key, Tag.TAG_COMPOUND)) {
            return ItemStack.parseOptional(BuiltInRegistries.ITEM.asLookup(), attachments.getCompound(key)).orElse(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }

    
    @NotNull
    default ResourceLocation getBuiltInAttachmentId(ItemStack gun, AttachmentType type) {
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun == null) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID;
        }
        Object indexObj = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun)).orElse(null);
        if (indexObj instanceof CommonGunIndex){
            CommonGunIndex index = (CommonGunIndex) indexObj;
            var builtin = index.getGunData().getBuiltInAttachments();
            if (builtin.containsKey(type)) {
                return builtin.get(type);
            }
        }
        return DefaultAssets.EMPTY_ATTACHMENT_ID;
    }

    
    @Nonnull
    default ResourceLocation getAttachmentId(ItemStack gun, AttachmentType type) {
        CompoundTag attachmentTag = this.getAttachmentTag(gun, type);
        if (attachmentTag != null) {
            return AttachmentItemDataAccessor.getAttachmentIdFromTag(attachmentTag);
        }
        return DefaultAssets.EMPTY_ATTACHMENT_ID;
    }    
    default void installAttachment(@Nonnull ItemStack gun, @Nonnull ItemStack attachment) {
        if (!allowAttachment(gun, attachment)) {
            return;
        }
        IAttachment iAttachment = IAttachment.getIAttachmentOrNull(attachment);
        if (iAttachment == null) {
            return;
        }
        CompoundTag attachments = gun.getOrDefault(ModDataComponents.GUN_ATTACHMENTS.get(), new CompoundTag());
        String key = iAttachment.getType(attachment).name();
        CompoundTag attachmentTag = attachment.save(net.minecraft.client.Minecraft.getInstance().level.registryAccess());
        attachments.put(key, attachmentTag);
        gun.set(ModDataComponents.GUN_ATTACHMENTS.get(), attachments);
    }

    
    default void unloadAttachment(@Nonnull ItemStack gun, AttachmentType type) {
        if (!allowAttachmentType(gun, type)) {
            return;
        }
        CompoundTag attachments = gun.getOrDefault(ModDataComponents.GUN_ATTACHMENTS.get(), new CompoundTag());
        String key = type.name();
        attachments.remove(key);
        gun.set(ModDataComponents.GUN_ATTACHMENTS.get(), attachments);
    }

    
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
            zoom = TimelessAPI.getClientAttachmentIndex(scopeId).map(attachmentIndex -> {
                float[] zooms = attachmentIndex.getZoom();
                if (zooms != null) {
                    return zooms[zoomNumber % zooms.length];
                }
                return 1f;
            }).orElse(1f);
        } else {
            zoom = TimelessAPI.getGunDisplay(gunItem).map(clientGunIndex -> clientGunIndex.getDefaultDisplay().getIronZoom()).orElse(1f);
        }
        return zoom;
    }    
    default boolean hasBulletInBarrel(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_HAS_BULLET_IN_BARREL.get(), false);
    }

    
    default void setBulletInBarrel(ItemStack gun, boolean bulletInBarrel) {
        gun.set(ModDataComponents.GUN_HAS_BULLET_IN_BARREL.get(), bulletInBarrel);
    }

    
    default boolean hasCustomLaserColor(ItemStack gun) {
        return gun.has(ModDataComponents.LASER_COLOR.get());
    }

    
    default int getLaserColor(ItemStack gun) {
        if (!hasCustomLaserColor(gun)) {
            return 0xFF0000;
        }
        return gun.getOrDefault(ModDataComponents.LASER_COLOR.get(), 0xFF0000);
    }

    
    default void setLaserColor(ItemStack gun, int color) {
        gun.set(ModDataComponents.LASER_COLOR.get(), color);
    }    /**
     * Heat Data
     */
    
    default boolean hasHeatData(ItemStack gun) {
        return gun.has(ModDataComponents.GUN_OVERHEAT.get());
    }

    
    default boolean isOverheatLocked(ItemStack gun) {
        return gun.getOrDefault(ModDataComponents.GUN_OVERHEAT_LOCK.get(), false);
    }

    
    default void setOverheatLocked(ItemStack gun, boolean locked) {
        gun.set(ModDataComponents.GUN_OVERHEAT_LOCK.get(), locked);
    }

    
    default float getHeatAmount(ItemStack gun) {
        if (hasHeatData(gun)) {
            return gun.getOrDefault(ModDataComponents.GUN_OVERHEAT.get(), 0f);
        }
        return 0f;
    }

    
    default void setHeatAmount(ItemStack gun, float amount) {
        gun.set(ModDataComponents.HEAT_AMOUNT.get(), amount >= 0 ? amount : 0f);
    }

    
    default float lerpRPM(ItemStack gun) {
        return TimelessAPI.getCommonGunIndex(getGunId(gun)).map(index -> {
            if (index.getGunData().getHeatData() != null) {
                float heatPercentage = (getHeatAmount(gun) / index.getGunData().getHeatData().getHeatMax());
                return Mth.lerp(heatPercentage, index.getGunData().getHeatData().getMinRpmMod(), index.getGunData().getHeatData().getMaxRpmMod());
            }
            return 1f;
        }).orElse(1f);
    }

    default float lerpInaccuracy(ItemStack gun) {
        return TimelessAPI.getCommonGunIndex(getGunId(gun)).map(index -> {
            if (index.getGunData().getHeatData() != null) {
                float heatPercentage = (getHeatAmount(gun) / index.getGunData().getHeatData().getHeatMax());
                return Mth.lerp(heatPercentage, index.getGunData().getHeatData().getMinInaccuracy(), index.getGunData().getHeatData().getMaxInaccuracy());
            }
            return 1f;
        }).orElse(1f);
    }
}































































