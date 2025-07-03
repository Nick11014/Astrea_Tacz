package com.tacz.guns.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

/**
 * Sistema de Data Components para substituir o uso de NBT no NeoForge 1.21.1
 * Baseado nos padrões estabelecidos pelo SuperbWarfare e documentação oficial
 */
public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents("tacz");

    // Data Components básicos para armas
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CURRENT_AMMO = register(
            "current_ammo",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> HAS_BULLET_IN_BARREL = register(
            "has_bullet_in_barrel", 
            builder -> builder.persistent(Codec.BOOL)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SHOOT_COOLDOWN = register(
            "shoot_cooldown",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RELOAD_STATE_TYPE = register(
            "reload_state_type",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RELOAD_STATE_COUNT = register(
            "reload_state_count",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FIRE_MODE = register(
            "fire_mode",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> AIM_STATUS = register(
            "aim_status",
            builder -> builder.persistent(Codec.BOOL)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DRAW_COOLDOWN = register(
            "draw_cooldown",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> DRAW_TIMESTAMP = register(
            "draw_timestamp",
            builder -> builder.persistent(Codec.LONG)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> PUT_AWAY_COOLDOWN = register(
            "put_away_cooldown",
            builder -> builder.persistent(Codec.INT)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Long>> PUT_AWAY_TIMESTAMP = register(
            "put_away_timestamp",
            builder -> builder.persistent(Codec.LONG)
    );

    // Data Components para tooltips e flags
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> HIDE_FLAGS = register(
            "hide_flags",
            builder -> builder.persistent(Codec.INT)
    );

    // Data Components para attachments
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ATTACHMENT_SCOPE_ID = register(
            "attachment_scope_id",
            builder -> builder.persistent(Codec.STRING)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ATTACHMENT_MUZZLE_ID = register(
            "attachment_muzzle_id",
            builder -> builder.persistent(Codec.STRING)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ATTACHMENT_FOREGRIP_ID = register(
            "attachment_foregrip_id",
            builder -> builder.persistent(Codec.STRING)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ATTACHMENT_STOCK_ID = register(
            "attachment_stock_id",
            builder -> builder.persistent(Codec.STRING)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> ATTACHMENT_EXTENDED_MAG_ID = register(
            "attachment_extended_mag_id",
            builder -> builder.persistent(Codec.STRING)
    );

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
