package com.tacz.guns.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;

/**
 * Um contêiner simples para uso em receitas
 */
public class SimpleContainer implements Container, RecipeInput {
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
    public int getContainerSize() {
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

    @Override
    public ItemStack getItem(int index) {
        return index >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return ContainerHelper.removeItem(this.items, index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index < this.getContainerSize()) {
            this.items.set(index, stack);
        }
    }

    @Override
    public void setChanged() {
        // Não é necessário fazer nada aqui
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
}
