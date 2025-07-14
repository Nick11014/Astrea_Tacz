package com.tacz.guns.item;

import com.tacz.guns.init.ModDataComponents;
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
        // Migrado para DataComponents no NeoForge 1.21.1
        Integer hideFlags = stack.get(ModDataComponents.HIDE_FLAGS.get());
        if (hideFlags != null) {
            return hideFlags;
        }
        // Fallback para 0 se nÃƒÆ’Ã‚Â£o houver flags definidas
        return 0;
    }

    public static void setHideFlags(ItemStack stack, int mask) {
        // Migrado para DataComponents no NeoForge 1.21.1
        stack.set(ModDataComponents.HIDE_FLAGS.get(), mask);
    }
}































































