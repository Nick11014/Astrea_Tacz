package com.tacz.guns.api.client.other;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

/**
 * Interface for keeping item renderer state for gun animations
 */
public interface KeepingItemRenderer {
    /**
     * Keep item rendered for specified time
     *
     * @param itemStack Item to keep rendered
     * @param timeMs    Duration in milliseconds
     */
    void keep(ItemStack itemStack, long timeMs);

    /**
     * Get the currently rendered item
     */
    ItemStack getCurrentItem();

    /**
     * Get the ItemInHandRenderer through Mixin implementation
     * Fixed for NeoForge 1.21.1 - uses gameRenderer.itemInHandRenderer
     * @return ItemInHandRenderer instance
     */
    static KeepingItemRenderer getRenderer(){
        return (KeepingItemRenderer) Minecraft.getInstance().gameRenderer.itemInHandRenderer;
    }
}
