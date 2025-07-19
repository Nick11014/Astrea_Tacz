package com.tacz.guns.compat.kubejs.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Classe base para factories de DataComponents do TacZ para KubeJS
 * IMPLEMENTADO: Migração completa de NBT para DataComponents (NeoForge 1.21.1)
 * 
 * Esta classe substitui TimelessItemNbtFactory.java e usa o novo sistema
 * DataComponents em vez das tags NBT antigas.
 */
public abstract class TimelessItemDataComponentFactory<T extends Item, E extends TimelessItemDataComponentFactory<T, E>> {
    protected ItemStack itemStack;
    protected T item;

    /**
     * Construtor com item específico
     * @param item Item a ser usado
     */
    public TimelessItemDataComponentFactory(@Nonnull T item) {
        this.item = item;
        this.itemStack = new ItemStack(item);
    }

    /**
     * Construtor padrão - cria com ItemStack vazio
     */
    public TimelessItemDataComponentFactory() {
        this.itemStack = ItemStack.EMPTY;
    }

    /**
     * Obtém o ItemStack atual
     * @return ItemStack configurado
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Define o ItemStack a ser usado
     * @param itemStack ItemStack a ser configurado
     */
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Obtém o item base
     * @return Item base
     */
    public T getItem() {
        return item;
    }

    /**
     * Cria uma cópia desta factory
     * @return Cópia da factory com ItemStack copiado
     */
    @SuppressWarnings("unchecked")
    public E copy() {
        try {
            E copy = (E) this.getClass().getDeclaredConstructor().newInstance();
            copy.itemStack = this.itemStack.copy();
            copy.item = this.item;
            return copy;
        } catch (Exception e) {
            System.err.println("TacZ: Error creating factory copy: " + e.getMessage());
            throw new RuntimeException("Failed to create copy of " + this.getClass().getSimpleName(), e);
        }
    }

    /**
     * Verifica se o ItemStack está vazio
     * @return true se o ItemStack está vazio
     */
    public boolean isEmpty() {
        return itemStack.isEmpty();
    }

    /**
     * Verifica se este factory é válido
     * @return true se tem um ItemStack válido
     */
    public boolean isValid() {
        return !itemStack.isEmpty() && item != null;
    }

    /**
     * Converte para string para debug
     * @return Representação em string
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "item=" + (item != null ? item.getClass().getSimpleName() : "null") +
                ", itemStack=" + itemStack +
                ", valid=" + isValid() +
                '}';
    }
}
