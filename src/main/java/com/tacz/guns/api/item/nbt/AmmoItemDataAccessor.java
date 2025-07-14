package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.init.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Acessor de dados para muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o utilizando DataComponents.
 * Migrado de NBT para DataComponents no NeoForge 1.21.1.
 */
public interface AmmoItemDataAccessor extends IAmmo {

    @Override
    @Nonnull
    default ResourceLocation getAmmoId(ItemStack ammo) {
        ResourceLocation ammoId = ammo.get(ModDataComponents.AMMO_ID.get());
        return Objects.requireNonNullElse(ammoId, DefaultAssets.EMPTY_AMMO_ID);
    }

    @Override
    default void setAmmoId(ItemStack ammo, @Nullable ResourceLocation ammoId) {
        if (ammoId != null) {
            ammo.set(ModDataComponents.AMMO_ID.get(), ammoId);
        } else {
            ammo.set(ModDataComponents.AMMO_ID.get(), DefaultAssets.DEFAULT_AMMO_ID);
        }
    }

    @Override
    default boolean isAmmoOfGun(ItemStack gun, ItemStack ammo) {
        if (gun.getItem() instanceof IGun iGun && ammo.getItem() instanceof IAmmo iAmmo) {
            ResourceLocation gunId = iGun.getGunId(gun);
            ResourceLocation ammoId = iAmmo.getAmmoId(ammo);
            
            // TODO: Implementar verificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o completa quando CommonGunIndex estiver disponÃƒÆ’Ã‚Â­vel
            // Por enquanto, apenas verificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o bÃƒÆ’Ã‚Â¡sica de nÃƒÆ’Ã‚Â£o-vazio
            return !gunId.equals(DefaultAssets.EMPTY_GUN_ID) && 
                   !ammoId.equals(DefaultAssets.EMPTY_AMMO_ID);
        }
        return false;
    }
}































































