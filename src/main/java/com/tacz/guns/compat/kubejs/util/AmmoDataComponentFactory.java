package com.tacz.guns.compat.kubejs.util;

import com.tacz.guns.api.item.nbt.AmmoItemDataAccessor;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.item.AmmoItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Factory para criação e manipulação de dados de munição para KubeJS
 * IMPLEMENTADO: Usa AmmoItemDataAccessor (DataComponents) em vez de NBT
 */
public class AmmoDataComponentFactory extends TimelessItemDataComponentFactory<AmmoItem, AmmoDataComponentFactory> {

    /**
     * Construtor com item específico de munição
     * @param item Item de munição
     */
    public AmmoDataComponentFactory(@Nonnull AmmoItem item) {
        super(item);
    }

    /**
     * Construtor padrão - usa AMMO como padrão
     */
    public AmmoDataComponentFactory() {
        super((AmmoItem) ModItems.AMMO.get());
    }

    /**
     * Construtor com ItemStack existente
     * @param itemStack ItemStack de munição existente
     */
    public AmmoDataComponentFactory(ItemStack itemStack) {
        super();
        if (itemStack.getItem() instanceof AmmoItem ammoItem) {
            this.item = ammoItem;
            this.itemStack = itemStack;
        } else {
            throw new IllegalArgumentException("ItemStack must be an ammo item");
        }
    }

    // ========== MÉTODOS DE IDENTIFICAÇÃO ==========

    /**
     * Define o ID da munição
     * @param ammoId ID da munição
     * @return Esta factory para chaining
     */
    public AmmoDataComponentFactory setAmmoId(ResourceLocation ammoId) {
        if (item instanceof AmmoItemDataAccessor accessor) {
            accessor.setAmmoId(itemStack, ammoId);
        }
        return this;
    }

    /**
     * Obtém o ID da munição
     * @return ID da munição ou null se não definido
     */
    public ResourceLocation getAmmoId() {
        if (item instanceof AmmoItemDataAccessor accessor) {
            return accessor.getAmmoId(itemStack);
        }
        return null;
    }

    // ========== MÉTODOS DE COMPATIBILIDADE ==========

    /**
     * Verifica se esta munição é compatível com uma arma
     * @param gunStack ItemStack da arma
     * @return true se é compatível
     */
    public boolean isAmmoOfGun(ItemStack gunStack) {
        if (item instanceof AmmoItemDataAccessor accessor) {
            return accessor.isAmmoOfGun(gunStack, itemStack);
        }
        return false;
    }

    // ========== MÉTODOS UTILITÁRIOS ==========

    /**
     * Configura uma munição básica
     * @param ammoId ID da munição
     * @return Esta factory para chaining
     */
    public AmmoDataComponentFactory setupBasicAmmo(ResourceLocation ammoId) {
        return setAmmoId(ammoId);
    }

    /**
     * Reseta todos os dados da munição para valores padrão
     * @return Esta factory para chaining
     */
    public AmmoDataComponentFactory reset() {
        // Para munição, só precisamos limpar o ID se necessário
        // Deixar como default ammo ID
        return this;
    }

    /**
     * Verifica se a munição está configurada corretamente
     * @return true se tem ID de munição válido
     */
    public boolean isValidAmmo() {
        ResourceLocation ammoId = getAmmoId();
        return ammoId != null && isValid();
    }

    /**
     * Cria uma munição com tipo específico
     * @param ammoType Tipo da munição (ex: "pistol", "rifle", "sniper")
     * @param ammoName Nome específico da munição
     * @return Esta factory para chaining
     */
    public AmmoDataComponentFactory setupAmmoType(String ammoType, String ammoName) {
        ResourceLocation ammoId = ResourceLocation.fromNamespaceAndPath("tacz", ammoType + "_" + ammoName);
        return setAmmoId(ammoId);
    }

    /**
     * Métodos de conveniência para tipos comuns de munição
     */
    public AmmoDataComponentFactory setupPistolAmmo(String name) {
        return setupAmmoType("pistol", name);
    }

    public AmmoDataComponentFactory setupRifleAmmo(String name) {
        return setupAmmoType("rifle", name);
    }

    public AmmoDataComponentFactory setupSniperAmmo(String name) {
        return setupAmmoType("sniper", name);
    }

    public AmmoDataComponentFactory setupShotgunAmmo(String name) {
        return setupAmmoType("shotgun", name);
    }

    @Override
    public String toString() {
        return "AmmoDataComponentFactory{" +
                "ammoId=" + getAmmoId() +
                ", validAmmo=" + isValidAmmo() +
                ", valid=" + isValid() +
                '}';
    }
}
