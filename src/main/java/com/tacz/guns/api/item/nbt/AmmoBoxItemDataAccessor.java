package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.init.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

/**
 * Accessor para dados de caixas de munição usando DataComponents (NeoForge 1.21.1+)
 * 
 * Esta interface migra do sistema NBT legado para o novo sistema DataComponent.
 * Todos os métodos agora usam os DataComponents definidos em ModDataComponents
 * em vez de acessar diretamente as tags NBT.
 * 
 * @see ModDataComponents para as definições dos componentes
 */

public interface AmmoBoxItemDataAccessor extends IAmmoBox {
    String AMMO_ID_TAG = "AmmoId";
    String AMMO_COUNT_TAG = "AmmoCount";
    String CREATIVE_TAG = "Creative";
    String ALL_TYPE_CREATIVE_TAG = "AllTypeCreative";
    String LEVEL_TAG = "Level";    @Override
    default ResourceLocation getAmmoId(ItemStack ammoBox) {
        ResourceLocation ammoId = ammoBox.get(ModDataComponents.AMMO_BOX_AMMO_ID.get());
        return Objects.requireNonNullElse(ammoId, DefaultAssets.EMPTY_AMMO_ID);
    }

    @Override
    default void setAmmoId(ItemStack ammoBox, ResourceLocation ammoId) {
        ammoBox.set(ModDataComponents.AMMO_BOX_AMMO_ID.get(), ammoId);
    }

    @Override
    default int getAmmoCount(ItemStack ammoBox) {
        if (isAllTypeCreative(ammoBox) || isCreative(ammoBox)) {
            return Integer.MAX_VALUE;
        }
        return ammoBox.getOrDefault(ModDataComponents.AMMO_BOX_AMOUNT.get(), 0);
    }

    @Override
    default void setAmmoCount(ItemStack ammoBox, int count) {
        if (isCreative(ammoBox)) {
            ammoBox.set(ModDataComponents.AMMO_BOX_AMOUNT.get(), Integer.MAX_VALUE);
            return;
        }
        ammoBox.set(ModDataComponents.AMMO_BOX_AMOUNT.get(), count);
    }

    @Override
    default boolean isAmmoBoxOfGun(ItemStack gun, ItemStack ammoBox) {
        if (gun.getItem() instanceof IGun iGun && ammoBox.getItem() instanceof IAmmoBox iAmmoBox) {
            if (isAllTypeCreative(ammoBox)) {
                return true;
            }
            ResourceLocation ammoId = iAmmoBox.getAmmoId(ammoBox);
            if (ammoId.equals(DefaultAssets.EMPTY_AMMO_ID)) {
                return false;
            }
            ResourceLocation gunId = iGun.getGunId(gun);
            
            // TODO: Implementar verificação completa quando CommonGunIndex estiver disponível
            // Por enquanto, apenas verificação básica de não-vazio
            return !gunId.equals(DefaultAssets.EMPTY_GUN_ID);
        }
        return false;
    }    @Override
    default ItemStack setAmmoLevel(ItemStack ammoBox, int level) {
        ammoBox.set(ModDataComponents.AMMO_BOX_LEVEL.get(), Math.max(level, 0));
        return ammoBox;
    }

    @Override
    default int getAmmoLevel(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModDataComponents.AMMO_BOX_LEVEL.get(), 0);
    }

    @Override
    default boolean isCreative(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModDataComponents.AMMO_BOX_CREATIVE.get(), false);
    }

    @Override
    default boolean isAllTypeCreative(ItemStack ammoBox) {
        return ammoBox.getOrDefault(ModDataComponents.AMMO_BOX_ALL_TYPE_CREATIVE.get(), false);
    }

    @Override
    default ItemStack setCreative(ItemStack ammoBox, boolean isAllType) {
        if (isAllType) {
            // Remove possível flag criativo específico
            ammoBox.remove(ModDataComponents.AMMO_BOX_CREATIVE.get());
            ammoBox.set(ModDataComponents.AMMO_BOX_ALL_TYPE_CREATIVE.get(), true);
        } else {
            // Remove possível flag criativo universal
            ammoBox.remove(ModDataComponents.AMMO_BOX_ALL_TYPE_CREATIVE.get());
            ammoBox.set(ModDataComponents.AMMO_BOX_CREATIVE.get(), true);
        }
        return ammoBox;
    }
}
