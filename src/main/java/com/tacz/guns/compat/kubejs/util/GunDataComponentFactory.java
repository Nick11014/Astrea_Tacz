package com.tacz.guns.compat.kubejs.util;

import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.api.item.nbt.GunItemDataAccessor;
import com.tacz.guns.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Factory para criação e manipulação de dados de armas para KubeJS
 * IMPLEMENTADO: Usa GunItemDataAccessor (DataComponents) em vez de NBT
 */
public class GunDataComponentFactory extends TimelessItemDataComponentFactory<AbstractGunItem, GunDataComponentFactory> {

    /**
     * Construtor com item específico de arma
     * @param item Item de arma
     */
    public GunDataComponentFactory(@Nonnull AbstractGunItem item) {
        super(item);
    }

    /**
     * Construtor padrão - usa MODERN_KINETIC_GUN como padrão
     */
    public GunDataComponentFactory() {
        super((AbstractGunItem) ModItems.MODERN_KINETIC_GUN.get());
    }

    /**
     * Construtor com ItemStack existente
     * @param itemStack ItemStack de arma existente
     */
    public GunDataComponentFactory(ItemStack itemStack) {
        super();
        if (itemStack.getItem() instanceof AbstractGunItem gunItem) {
            this.item = gunItem;
            this.itemStack = itemStack;
        } else {
            throw new IllegalArgumentException("ItemStack must be a gun item");
        }
    }

    // ========== MÉTODOS DE MUNIÇÃO ==========

    /**
     * Define a quantidade atual de munição
     * @param ammo Quantidade de munição
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setCurrentAmmo(int ammo) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setCurrentAmmoCount(itemStack, ammo);
        }
        return this;
    }

    /**
     * Obtém a quantidade atual de munição
     * @return Quantidade de munição
     */
    public int getCurrentAmmo() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.getCurrentAmmoCount(itemStack);
        }
        return 0;
    }

    /**
     * Define se há bala no cano
     * @param hasAmmo true se há bala no cano
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setAmmoInBarrel(boolean hasAmmo) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setBulletInBarrel(itemStack, hasAmmo);
        }
        return this;
    }

    /**
     * Verifica se há bala no cano
     * @return true se há bala no cano
     */
    public boolean hasAmmoInBarrel() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.hasBulletInBarrel(itemStack);
        }
        return false;
    }

    // ========== MÉTODOS DE MODO DE FOGO ==========

    /**
     * Define o modo de fogo
     * @param fireMode Modo de fogo
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setFireMode(FireMode fireMode) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setFireMode(itemStack, fireMode);
        }
        return this;
    }

    /**
     * Define o modo de fogo por string
     * @param fireMode Nome do modo de fogo
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setFireMode(String fireMode) {
        try {
            FireMode mode = FireMode.valueOf(fireMode.toUpperCase());
            return setFireMode(mode);
        } catch (IllegalArgumentException e) {
            System.err.println("TacZ: Invalid fire mode: " + fireMode + ". Using SEMI as default.");
            return setFireMode(FireMode.SEMI);
        }
    }

    /**
     * Obtém o modo de fogo atual
     * @return Modo de fogo
     */
    public FireMode getFireMode() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.getFireMode(itemStack);
        }
        return FireMode.UNKNOWN;
    }

    // ========== MÉTODOS DE IDENTIFICAÇÃO ==========

    /**
     * Define o ID da arma
     * @param gunId ID da arma
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setGunId(ResourceLocation gunId) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setGunId(itemStack, gunId);
        }
        return this;
    }

    /**
     * Obtém o ID da arma
     * @return ID da arma ou null se não definido
     */
    public ResourceLocation getGunId() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.getGunId(itemStack);
        }
        return null;
    }

    /**
     * Define o ID de display da arma
     * @param displayId ID de display
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setGunDisplayId(ResourceLocation displayId) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setGunDisplayId(itemStack, displayId);
        }
        return this;
    }

    /**
     * Obtém o ID de display da arma
     * @return ID de display ou null se não definido
     */
    public ResourceLocation getGunDisplayId() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.getGunDisplayId(itemStack);
        }
        return null;
    }

    // ========== MÉTODOS DE MUNIÇÃO DUMMY ==========

    /**
     * Define munição dummy (para display)
     * @param amount Quantidade de munição dummy
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setDummyAmmo(int amount) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setDummyAmmoAmount(itemStack, amount);
        }
        return this;
    }

    /**
     * Obtém munição dummy
     * @return Quantidade de munição dummy
     */
    public int getDummyAmmo() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.getDummyAmmoAmount(itemStack);
        }
        return 0;
    }

    /**
     * Define quantidade máxima de munição dummy
     * @param maxAmount Quantidade máxima
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setMaxDummyAmmo(int maxAmount) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setMaxDummyAmmoAmount(itemStack, maxAmount);
        }
        return this;
    }

    /**
     * Obtém quantidade máxima de munição dummy
     * @return Quantidade máxima
     */
    public int getMaxDummyAmmo() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.getMaxDummyAmmoAmount(itemStack);
        }
        return 0;
    }

    // ========== MÉTODOS DE TRAVAMENTO ==========

    /**
     * Define se os attachments estão travados
     * @param locked true para travar attachments
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setAttachmentLock(boolean locked) {
        if (item instanceof GunItemDataAccessor accessor) {
            accessor.setAttachmentLock(itemStack, locked);
        }
        return this;
    }

    /**
     * Verifica se os attachments estão travados
     * @return true se attachments estão travados
     */
    public boolean isAttachmentLocked() {
        if (item instanceof GunItemDataAccessor accessor) {
            return accessor.hasAttachmentLock(itemStack);
        }
        return false;
    }

    // ========== MÉTODOS UTILITÁRIOS ==========

    /**
     * Configura uma arma básica com valores padrão
     * @param gunId ID da arma
     * @param ammo Munição inicial
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory setupBasicGun(ResourceLocation gunId, int ammo) {
        return setGunId(gunId)
                .setGunDisplayId(gunId)
                .setCurrentAmmo(ammo)
                .setFireMode(FireMode.SEMI)
                .setAmmoInBarrel(false)
                .setAttachmentLock(false);
    }

    /**
     * Reseta todos os dados da arma para valores padrão
     * @return Esta factory para chaining
     */
    public GunDataComponentFactory reset() {
        return setCurrentAmmo(0)
                .setAmmoInBarrel(false)
                .setFireMode(FireMode.SEMI)
                .setDummyAmmo(0)
                .setAttachmentLock(false);
    }

    @Override
    public String toString() {
        return "GunDataComponentFactory{" +
                "gunId=" + getGunId() +
                ", ammo=" + getCurrentAmmo() +
                ", fireMode=" + getFireMode() +
                ", hasAmmoInBarrel=" + hasAmmoInBarrel() +
                ", attachmentLocked=" + isAttachmentLocked() +
                ", valid=" + isValid() +
                '}';
    }
}
