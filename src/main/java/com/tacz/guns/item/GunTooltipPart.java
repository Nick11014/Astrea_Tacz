package com.tacz.guns.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public enum GunTooltipPart {
    DESCRIPTION,
    AMMO_INFO,
    BASE_INFO,
    EXTRA_DAMAGE_INFO,
    UPGRADES_TIP,
    PACK_INFO;

    private final int mask = 1 << this.ordinal();

    public int getMask() {
        return this.mask;
    }

    public static int getHideFlags(ItemStack stack) {
        // Usar DataComponents em vez de NBT - HideFlags agora é um componente nativo
        return stack.getOrDefault(DataComponents.HIDE_TOOLTIP, 0);
    }

    public static void setHideFlags(ItemStack stack, int mask) {
        // Usar DataComponents em vez de NBT
        stack.set(DataComponents.HIDE_TOOLTIP, mask);
    }
}
