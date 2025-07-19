package com.tacz.guns.compat.kubejs.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * Informações de resultado para receitas da mesa de trabalho de armas
 * MIGRAÇÃO 1.21.1: Implementação simplificada
 */
public class GunSmithTableResultInfo {
    private final ItemStack resultItem;
    private final String groupName;
    private final ResourceLocation recipeId;
    
    private GunSmithTableResultInfo(ItemStack resultItem, String groupName, ResourceLocation recipeId) {
        this.resultItem = resultItem;
        this.groupName = groupName;
        this.recipeId = recipeId;
    }
    
    /**
     * Cria uma instância de GunSmithTableResultInfo
     */
    public static GunSmithTableResultInfo of(Object from) {
        if (from instanceof GunSmithTableResultInfo info) {
            return info;
        }
        
        // Implementação básica - pode ser expandida conforme necessário
        return new GunSmithTableResultInfo(
            ItemStack.EMPTY, 
            "default", 
            ResourceLocation.fromNamespaceAndPath("tacz", "unknown")
        );
    }
    
    /**
     * Cria uma instância com dados específicos
     */
    public static GunSmithTableResultInfo create(ItemStack item, String group, ResourceLocation id) {
        return new GunSmithTableResultInfo(item, group, id);
    }
    
    public ItemStack getResultItem() {
        return resultItem;
    }
    
    public String getGroupName() {
        return groupName;
    }
    
    public ResourceLocation getRecipeId() {
        return recipeId;
    }
    
    /**
     * Enumeração para grupos de saída
     */
    public enum OutputGroupName {
        AMMO("ammo"),
        ATTACHMENT("attachment"),
        GUN("gun"),
        OTHER("other");
        
        private final String name;
        
        OutputGroupName(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
    }
}
