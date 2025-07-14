package com.tacz.guns.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;

import java.nio.file.Path;

/**
 * ConfiguraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de prÃƒÆ’Ã‚Â©-carregamento para o TacZ.
 * Migrado para NeoForge 1.21.1 - usando APIs oficiais simples.
 */
public class PreLoadConfig {
    private static ModConfigSpec spec;
    public static ModConfigSpec.BooleanValue override;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("gunpack");
        builder.comment("When enabled, the mod will not try to overwrite the default pack under .minecraft/tacz\n" +
                "Since 1.0.4, the overwriting will only run when you start client or a dedicated server");
        override = builder.define("DefaultPackDebug", false);
        builder.pop();
        spec = builder.build();
    }

    public static PreLoadModConfig getModConfig() {
        ModLoadingContext ctx = ModLoadingContext.get();
        return new PreLoadModConfig(ModConfig.Type.COMMON, spec, ctx.getActiveContainer(), "tacz-pre.toml");
    }

    public static void load(Path configBasePath) {
        if (spec.isLoaded()) return;
        
        PreLoadModConfig config = getModConfig();
        
        // Criar CommentedFileConfig usando a API oficial
        Path configPath = configBasePath.resolve(config.getFileName());
        final CommentedFileConfig configData = CommentedFileConfig.builder(configPath)
                .preserveInsertionOrder()
                .build();
        configData.load();
        
        // Associar os dados 
        config.setConfigData(configData);
        
        // Salvar para garantir que as correÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Âµes sejam persistidas
        config.save();
    }

    public static ModConfigSpec getSpec() {
        return spec;
    }
}































































