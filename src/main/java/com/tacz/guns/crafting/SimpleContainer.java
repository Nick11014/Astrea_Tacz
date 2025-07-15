package com.tacz.guns.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * Um contêiner simples para uso em receitas
 */
public class SimpleContainer implements RecipeInput {
    private final NonNullList<ItemStack> items;
    private final int width;
    private final int height;

    public SimpleContainer(int size) {
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
        this.width = 0;
        this.height = 0;
    }

    public SimpleContainer(ItemStack... stacks) {
        this.items = NonNullList.of(ItemStack.EMPTY, stacks);
        this.width = 0;
        this.height = 0;
    }

    public SimpleContainer(NonNullList<ItemStack> items, int width, int height) {
        this.items = items;
        this.width = width;
        this.height = height;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= this.size() ? ItemStack.EMPTY : this.items.get(index);
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
