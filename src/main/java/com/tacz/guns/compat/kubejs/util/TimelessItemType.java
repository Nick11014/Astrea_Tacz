package com.tacz.guns.compat.kubejs.util;

import com.tacz.guns.init.ModItems;
import net.minecraft.world.item.Item;

/**
 * Enum para tipos de itens do TacZ usados no KubeJS
 * IMPLEMENTADO: Atualizado para NeoForge 1.21.1
 */
public enum TimelessItemType {
    MODERN_KINETIC_GUN(ModItems.MODERN_KINETIC_GUN.get()),
    AMMO(ModItems.AMMO.get()),
    ATTACHMENT(ModItems.ATTACHMENT.get());

    private final Item item;

    TimelessItemType(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    /**
     * Obtém um tipo por nome (case-insensitive)
     * @param name Nome do tipo
     * @return TimelessItemType correspondente ou null se não encontrado
     */
    public static TimelessItemType fromName(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Verifica se um item corresponde a este tipo
     * @param item Item a verificar
     * @return true se corresponde
     */
    public boolean matches(Item item) {
        return this.item == item;
    }
}
