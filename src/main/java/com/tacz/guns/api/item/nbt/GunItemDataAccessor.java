package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.gun.FireMode;
// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] GunDisplayInstance desabilitado temporariamente
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
    // NOTA: As constantes abaixo sÃƒÆ’Ã‚Â£o mantidas para compatibilidade com sistemas 
    // de acessÃƒÆ’Ã‚Â³rios que ainda usam CompoundTag temporariamente
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
        // ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬Ëœ AmmoCount
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
        Object indexObj = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun)).orElse(null);
        if (indexObj instanceof CommonGunIndex){
            CommonGunIndex index = (CommonGunIndex) indexObj;
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
            // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] BuiltInRegistries API mudou, precisa ser adaptado
            // return ItemStack.parseOptional(BuiltInRegistries.ITEM.asLookup(), attachments.getCompound(key)).orElse(ItemStack.EMPTY);
            return ItemStack.EMPTY; // Placeholder temporÃƒÆ’Ã‚Â¡rio
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
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] ItemStack.save API mudou, precisa ser adaptado
        // CompoundTag attachmentTag = (CompoundTag) attachment.save(BuiltInRegistries.ITEM.asLookup());
        CompoundTag attachmentTag = new CompoundTag(); // Placeholder temporÃƒÆ’Ã‚Â¡rio
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
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] ItemStack.save API mudou, precisa ser adaptado
        // CompoundTag attachmentTag = (CompoundTag) ItemStack.EMPTY.save(BuiltInRegistries.ITEM.asLookup());
        CompoundTag attachmentTag = new CompoundTag(); // Placeholder temporÃƒÆ’Ã‚Â¡rio
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
            Object attachmentIndexObj = TimelessAPI.getClientAttachmentIndex(scopeId).orElse(null);
            float[] zooms = null;
            if (attachmentIndexObj != null) {
                // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Implementar acesso a getZoom quando Object Strategy for resolvida
                // ClientAttachmentIndex attachmentIndex = (ClientAttachmentIndex) attachmentIndexObj;
                // zooms = attachmentIndex.getZoom();
            }
            if (zooms != null) {
                zoom = zooms[zoomNumber % zooms.length];
            }
        } else {
            Object gunDisplayObj = TimelessAPI.getGunDisplay(gunItem).orElse(null);
            if (gunDisplayObj != null) {
                // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Implementar acesso a getIronZoom quando Object Strategy for resolvida
                // GunDisplayInstance gunDisplay = (GunDisplayInstance) gunDisplayObj;
                // zoom = gunDisplay.getIronZoom();
                zoom = 1f; // Placeholder temporÃƒÆ’Ã‚Â¡rio
            } else {
                zoom = 1f;
            }
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
        Object indexObj = TimelessAPI.getCommonGunIndex(getGunId(gun)).orElse(null);
        if (indexObj instanceof CommonGunIndex) {
            CommonGunIndex index = (CommonGunIndex) indexObj;
            Object heatDataObj = index.getGunData().getHeatData();
            if (heatDataObj != null) {
                // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Implementar acesso a mÃƒÆ’Ã‚Â©todos de HeatData quando Object Strategy for resolvida
                // GunHeatData heatData = (GunHeatData) heatDataObj;
                // float heatPercentage = (getHeatAmount(gun) / heatData.getHeatMax());
                // return Mth.lerp(heatPercentage, heatData.getMinRpmMod(), heatData.getMaxRpmMod());
                return 1f; // Placeholder temporÃƒÆ’Ã‚Â¡rio
            }
        }
        return 1f;
    }

    @Override
    default float lerpInaccuracy(ItemStack gun) {
        Object indexObj = TimelessAPI.getCommonGunIndex(getGunId(gun)).orElse(null);
        if (indexObj instanceof CommonGunIndex) {
            CommonGunIndex index = (CommonGunIndex) indexObj;
            Object heatDataObj = index.getGunData().getHeatData();
            if (heatDataObj != null) {
                // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Implementar acesso a mÃƒÆ’Ã‚Â©todos de HeatData quando Object Strategy for resolvida
                // GunHeatData heatData = (GunHeatData) heatDataObj;
                // float heatPercentage = (getHeatAmount(gun) / heatData.getHeatMax());
                // return Mth.lerp(heatPercentage, heatData.getMinInaccuracy(), heatData.getMaxInaccuracy());
                return 1f; // Placeholder temporÃƒÆ’Ã‚Â¡rio
            }
        }
        return 1f;
    }
}































































