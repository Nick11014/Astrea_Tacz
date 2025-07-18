package com.tacz.guns.compat.cloth;

import com.tacz.guns.init.CompatRegistry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ClothConfigAPI;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;

/**
 * Implementação mínima para integração com Cloth Config
 * TODO: [MIGRAÇÃO] Expandir quando Cloth Config for completamente integrado
 */
public class MenuIntegration {
    
    /**
     * Verifica se o Cloth Config está carregado
     * TODO: Implementar verificação real quando mod estiver disponível
     */
    public static boolean isLoaded() {
        return ModList.get().isLoaded(CompatRegistry.CLOTH_CONFIG);
    }
    
    /**
     * Cria a tela de configuração do TacZ
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.translatable("config.tacz.title"));

        // Add categories
        createGeneralCategory(builder);
        createWeaponsCategory(builder);

        return builder.build();
    }
    
    /**
     * Registra categorias de configuração
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static void registerConfigCategories() {
        // This method is typically used to register the config screen itself,
        // not individual categories. Categories are added when building the screen.
        // The previous TODOs were a bit misleading here.
    }
    
    /**
     * Cria categoria de configurações gerais
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static ConfigCategory createGeneralCategory(ConfigBuilder builder) {
        return builder.getOrCreateCategory(Component.translatable("config.tacz.category.general"));
    }
    
    /**
     * Cria categoria de configurações de armas
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static ConfigCategory createWeaponsCategory(ConfigBuilder builder) {
        return builder.getOrCreateCategory(Component.translatable("config.tacz.category.weapons"));
    }
    
    /**
     * Salva as configurações
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static void saveConfigs() {
        ClothConfigAPI.getConfigBuilder().save();
    }
    
    /**
     * Carrega as configurações
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static void loadConfigs() {
        // ClothConfigAPI does not have a direct load method, usually configs are loaded on startup.
        // If a specific reload is needed, it depends on the config implementation.
    }
    
    /**
     * Verifica se as configurações foram modificadas
     * TODO: Implementar quando Cloth Config estiver disponível
     */
    public static boolean hasUnsavedChanges() {
        return ClothConfigAPI.getConfigBuilder().isEdited();
    }
}































































