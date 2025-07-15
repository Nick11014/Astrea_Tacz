package com.tacz.guns.crafting;
package com.tacz.guns.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Representa o resultado de uma receita de mesa de armeiro.
 * Contém o item resultante e o grupo ao qual pertence.
 */
public class RawGunTableResult {
    private final ItemStack result;
    private final ResourceLocation group;

    public RawGunTableResult(ItemStack result, ResourceLocation group) {
        this.result = result;
        this.group = group;
    }

    public ItemStack getResult() {
        return result;
    }

    public ResourceLocation getGroup() {
        return group;
    }
}
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Representa o resultado de uma receita de mesa de armeiro.
 * Contém o item resultante e o grupo ao qual pertence.
 */
public class RawGunTableResult {
    private final ItemStack result;
    private final ResourceLocation group;

    public RawGunTableResult(ItemStack result, ResourceLocation group) {
        this.result = result;
        this.group = group;
    }

    public ItemStack getResult() {
        return result;
    }

    public ResourceLocation getGroup() {
        return group;
    }
}
