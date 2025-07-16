package com.tacz.guns.init;

import com.mojang.serialization.Codec;
import com.tacz.guns.GunMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
                                                                                                DeferredRegister.create(net.minecraft.core.registries.BuiltInRegistries.DATA_COMPONENT_TYPE, GunMod.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> AMMO_BOX_COLOR = DATA_COMPONENT_TYPES.register(
            "ammo_box_color",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> AMMO_BOX_AMMO_ID = DATA_COMPONENT_TYPES.register(
            "ammo_box_ammo_id",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> AMMO_BOX_AMOUNT = DATA_COMPONENT_TYPES.register(
            "ammo_box_amount",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> AMMO_BOX_LEVEL = DATA_COMPONENT_TYPES.register(
            "ammo_box_level",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> AMMO_BOX_CREATIVE = DATA_COMPONENT_TYPES.register(
            "ammo_box_creative",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> AMMO_BOX_ALL_TYPE_CREATIVE = DATA_COMPONENT_TYPES.register(
            "ammo_box_all_type_creative",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> ATTACHMENT_ID = DATA_COMPONENT_TYPES.register(
            "attachment_id",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> LASER_COLOR = DATA_COMPONENT_TYPES.register(
            "laser_color",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> GUN_ID = DATA_COMPONENT_TYPES.register(
            "gun_id",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> GUN_DISPLAY_ID = DATA_COMPONENT_TYPES.register(
            "gun_display_id",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<String>> FIRE_MODE = DATA_COMPONENT_TYPES.register(
            "fire_mode",
            () -> DataComponentType.<String>builder()
                    .persistent(Codec.STRING)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> CURRENT_AMMO_COUNT = DATA_COMPONENT_TYPES.register(
            "current_ammo_count",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> BULLET_IN_BARREL = DATA_COMPONENT_TYPES.register(
            "bullet_in_barrel",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> HEAT_AMOUNT = DATA_COMPONENT_TYPES.register(
            "heat_amount",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> OVERHEAT_LOCKED = DATA_COMPONENT_TYPES.register(
            "overheat_locked",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> AIMING_PROGRESS = DATA_COMPONENT_TYPES.register(
            "aiming_progress",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> RELOAD_STATE_TYPE = DATA_COMPONENT_TYPES.register(
            "reload_state_type",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Long>> LAST_SHOOT_TIMESTAMP = DATA_COMPONENT_TYPES.register(
            "last_shoot_timestamp",
            () -> DataComponentType.<Long>builder()
                    .persistent(Codec.LONG)
                    .build()
    );

    public static final Supplier<DataComponentType<Long>> BASE_TIMESTAMP = DATA_COMPONENT_TYPES.register(
            "base_timestamp",
            () -> DataComponentType.<Long>builder()
                    .persistent(Codec.LONG)
                    .build()
    );

    public static final Supplier<DataComponentType<Long>> BOLT_TIMESTAMP = DATA_COMPONENT_TYPES.register(
            "bolt_timestamp",
            () -> DataComponentType.<Long>builder()
                    .persistent(Codec.LONG)
                    .build()
    );

    public static final Supplier<DataComponentType<Long>> RELOAD_TIMESTAMP = DATA_COMPONENT_TYPES.register(
            "reload_timestamp",
            () -> DataComponentType.<Long>builder()
                    .persistent(Codec.LONG)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> DUMMY_AMMO_AMOUNT = DATA_COMPONENT_TYPES.register(
            "dummy_ammo_amount",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> MAX_DUMMY_AMMO_AMOUNT = DATA_COMPONENT_TYPES.register(
            "max_dummy_ammo_amount",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> ATTACHMENT_LOCK = DATA_COMPONENT_TYPES.register(
            "attachment_lock",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> LEVEL = DATA_COMPONENT_TYPES.register(
            "level",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> EXP = DATA_COMPONENT_TYPES.register(
            "exp",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> ZOOM_NUMBER = DATA_COMPONENT_TYPES.register(
            "zoom_number",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> ADS_ADDEND = DATA_COMPONENT_TYPES.register(
            "ads_addend",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> AMMO_ID = DATA_COMPONENT_TYPES.register(
            "ammo_id",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<ResourceLocation>> BLOCK_ID = DATA_COMPONENT_TYPES.register(
            "block_id",
            () -> DataComponentType.<ResourceLocation>builder()
                    .persistent(ResourceLocation.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> GUN_DUMMY_AMMO = DATA_COMPONENT_TYPES.register(
            "gun_dummy_ammo",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<net.minecraft.nbt.CompoundTag>> GUN_ATTACHMENTS = DATA_COMPONENT_TYPES.register(
            "gun_attachments",
            () -> DataComponentType.<net.minecraft.nbt.CompoundTag>builder()
                    .persistent(net.minecraft.nbt.CompoundTag.CODEC)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> HIDE_FLAGS = DATA_COMPONENT_TYPES.register(
            "hide_flags",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Float>> GUN_OVERHEAT = DATA_COMPONENT_TYPES.register(
            "gun_overheat",
            () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> GUN_OVERHEAT_LOCK = DATA_COMPONENT_TYPES.register(
            "gun_overheat_lock",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> GUN_EXP = DATA_COMPONENT_TYPES.register(
            "gun_exp",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> GUN_CURRENT_AMMO_COUNT = DATA_COMPONENT_TYPES.register(
            "gun_current_ammo_count",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> GUN_HAS_BULLET_IN_BARREL = DATA_COMPONENT_TYPES.register(
            "gun_has_bullet_in_barrel",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static final Supplier<DataComponentType<Integer>> GUN_MAX_DUMMY_AMMO = DATA_COMPONENT_TYPES.register(
            "gun_max_dummy_ammo",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .build()
    );

    public static final Supplier<DataComponentType<Boolean>> GUN_ATTACHMENT_LOCK = DATA_COMPONENT_TYPES.register(
            "gun_attachment_lock",
            () -> DataComponentType.<Boolean>builder()
                    .persistent(Codec.BOOL)
                    .build()
    );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
