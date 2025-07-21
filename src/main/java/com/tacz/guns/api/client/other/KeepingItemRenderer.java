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
     * Registry for the KeepingItemRenderer instance
     * This is set by the ItemInHandRendererMixin when it's created
     */
    class Registry {
        private static KeepingItemRenderer instance = null;
        
        public static void setInstance(KeepingItemRenderer renderer) {
            instance = renderer;
        }
        
        public static KeepingItemRenderer getInstance() {
            if (instance == null) {
                // Try to get through cast as fallback
                try {
                    var itemInHandRenderer = Minecraft.getInstance().gameRenderer.itemInHandRenderer;
                    if (itemInHandRenderer instanceof KeepingItemRenderer keepingItemRenderer) {
                        instance = keepingItemRenderer;
                        return instance;
                    }
                } catch (Exception e) {
                    // Log error but continue
                    System.err.println("TacZ: Failed to get ItemInHandRenderer: " + e.getMessage());
                }
                
                // Return a dummy implementation to prevent crashes
                return new KeepingItemRenderer() {
                    @Override
                    public void keep(ItemStack itemStack, long timeMs) {
                        // Do nothing
                    }
                    
                    @Override
                    public ItemStack getCurrentItem() {
                        // Return main hand item as fallback
                        var player = Minecraft.getInstance().player;
                        return player != null ? player.getMainHandItem() : ItemStack.EMPTY;
                    }
                };
            }
            return instance;
        }
    }

    /**
     * Get the ItemInHandRenderer through registered instance
     * @return ItemInHandRenderer instance
     */
    static KeepingItemRenderer getRenderer(){
        return Registry.getInstance();
    }
}
