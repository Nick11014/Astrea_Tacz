package com.tacz.guns.command.sub;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.tacz.guns.config.sync.SyncConfig;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;

import java.util.concurrent.CompletableFuture;

public class ConfigCommand {
    private static final String CONFIG_NAME = "config";
    private static final String KEY = "key";
    private static final String ENABLE = "state";

    public static LiteralArgumentBuilder<CommandSourceStack> get() {
        var config = Commands.literal(CONFIG_NAME);
        // Migrado para NeoForge 1.21.1: Usando StringArgumentType com sugestÃƒÆ’Ã‚Âµes personalizadas
        var configKey = Commands.argument(KEY, StringArgumentType.string())
                .suggests(ConfigCommand::suggestConfigKeys);
        var state = Commands.argument(ENABLE, BoolArgumentType.bool());
        return config.then(configKey.then(state.executes(ConfigCommand::setConfig)));
    }

    private static CompletableFuture<Suggestions> suggestConfigKeys(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(
                new String[]{
                        ConfigKey.defaultTableLimit.getSerializedName(),
                        ConfigKey.serverShootNetworkCheck.getSerializedName(),
                        ConfigKey.serverShootCooldownCheck.getSerializedName()
                },
                builder
        );
    }

    private static int setConfig(CommandContext<CommandSourceStack> context) {
        String keyString = StringArgumentType.getString(context, KEY);
        ConfigKey key = ConfigKey.fromString(keyString);
        boolean state = BoolArgumentType.getBool(context, ENABLE);

        if (key == null) {
            context.getSource().sendFailure(Component.literal("Invalid config key: " + keyString));
            return 0;
        }
        switch (key) {
            case defaultTableLimit -> SyncConfig.ENABLE_TABLE_FILTER.set(state);
            case serverShootNetworkCheck -> SyncConfig.SERVER_SHOOT_NETWORK_V.set(state);
            case serverShootCooldownCheck -> SyncConfig.SERVER_SHOOT_COOLDOWN_V.set(state);
        }
        context.getSource().sendSystemMessage(Component.translatable(key.lang + "." + (state ? "enabled" : "disabled")));

        return Command.SINGLE_SUCCESS;
    }

    public enum ConfigKey implements StringRepresentable {
        defaultTableLimit("commands.tacz.config.default_table_limit"),
        serverShootNetworkCheck("commands.tacz.config.server_shoot_network_check"),
        serverShootCooldownCheck("commands.tacz.config.server_shoot_cooldown_check"),
        ;

        public final String lang;
        ConfigKey(String lang) {
            this.lang = lang;
        }

        @Override
        public String getSerializedName() {
            return this.name();
        }

        public static ConfigKey fromString(String name) {
            for (ConfigKey key : values()) {
                if (key.getSerializedName().equals(name)) {
                    return key;
                }
            }
            return null;
        }
    }
}































































