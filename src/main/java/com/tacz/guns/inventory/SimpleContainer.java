package com.tacz.guns.inventory;
package com.tacz.guns.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Um contêiner simples usado para verificação de receitas.
 * Baseado na implementação padrão do Minecraft SimpleContainer.
 */
public class SimpleContainer implements Container {
    private final int size;
    private final NonNullList<ItemStack> items;

    public SimpleContainer(int size) {
        this.size = size;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public SimpleContainer(ItemStack... items) {
        this.size = items.length;
        this.items = NonNullList.of(ItemStack.EMPTY, items);
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
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
        if (index >= 0 && index < this.items.size()) {
            this.items.set(index, stack);
        }
    }

    @Override
    public void setChanged() {
        // Não é necessário fazer nada aqui para este caso simples
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Um contêiner simples usado para verificação de receitas.
 * Baseado na implementação padrão do Minecraft SimpleContainer.
 */
public class SimpleContainer implements Container {
    private final int size;
    private final NonNullList<ItemStack> items;

    public SimpleContainer(int size) {
        this.size = size;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public SimpleContainer(ItemStack... items) {
        this.size = items.length;
        this.items = NonNullList.of(ItemStack.EMPTY, items);
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
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
        if (index >= 0 && index < this.items.size()) {
            this.items.set(index, stack);
        }
    }

    @Override
    public void setChanged() {
        // Não é necessário fazer nada aqui para este caso simples
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}
