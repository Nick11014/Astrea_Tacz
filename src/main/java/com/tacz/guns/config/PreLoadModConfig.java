package com.tacz.guns.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;

/**
 * ConfiguraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o customizada para carregamento antes do sistema normal de configs do mod.
 * Migrado para NeoForge 1.21.1 - simplificado baseado na documentaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o oficial.
 */
public class PreLoadModConfig {
    private CommentedConfig configData;
    private final ModContainer container;
    private final ModConfig.Type type;
    private final ModConfigSpec spec;
    private final String fileName;

    public PreLoadModConfig(ModConfig.Type type, ModConfigSpec spec, ModContainer container, String fileName) {
        this.type = type;
        this.spec = spec;
        this.container = container;
        this.fileName = fileName;
    }

    public CommentedConfig getConfigData() {
        return this.configData;
    }

    public void setConfigData(final CommentedConfig configData) {
        this.configData = configData;
        // Em NeoForge 1.21.1, apenas armazenamos os dados
    }

    public void save() {
        if (this.configData instanceof CommentedFileConfig fileConfig) {
            fileConfig.save();
        }
    }

    public Path getFullPath() {
        if (this.configData instanceof CommentedFileConfig fileConfig) {
            return fileConfig.getNioPath();
        }
        return null;
    }

    public ModConfig.Type getType() {
        return this.type;
    }

    public ModConfigSpec getSpec() {
        return this.spec;
    }

    public String getFileName() {
        return this.fileName;
    }

    public ModContainer getModContainer() {
        return this.container;
    }
}































































