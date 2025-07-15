package com.tacz.guns.init;

import com.mojang.serialization.Codec;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.item.gun.FireMode;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

/**
 * Registra todos os DataComponents necessÃƒÆ’Ã‚Â¡rios para substituir o sistema NBT
 * usado anteriormente para armazenar dados de armas, muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Âµes e acessÃƒÆ’Ã‚Â³rios.
 * 
 * Esta ÃƒÆ’Ã‚Â© a migraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o principal da versÃƒÆ’Ã‚Â£o 1.20.1 para 1.21.1 do Minecraft,
 * onde ItemStack.getOrCreateTag() foi substituÃƒÆ’Ã‚Â­do por DataComponents.
 */
public class ModDataComponents {
    /**
     * Modificador de tempo de ADS (float)
     * Substitui: ADS_ADDEND (usado em acessórios)
     */
    public static final Supplier<DataComponentType<Float>> ADS_ADDEND = COMPONENTS.register("ads_addend",
        () -> DataComponentType.<Float>builder()
            .persistent(Codec.FLOAT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.FLOAT)
            .build()
    );
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = 
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, GunMod.MOD_ID);

    // === DADOS DE ARMAS ===
    
    /**
     * ID da arma (ResourceLocation)
     * Substitui: GUN_ID_TAG
     */
    public static final Supplier<DataComponentType<ResourceLocation>> GUN_ID = COMPONENTS.register("gun_id",
        () -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .build()
    );

    /**
     * Modo de tiro da arma (FireMode)
     * Substitui: GUN_FIRE_MODE_TAG
     */
    public static final Supplier<DataComponentType<FireMode>> GUN_FIRE_MODE = COMPONENTS.register("gun_fire_mode",
        () -> DataComponentType.<FireMode>builder()
            .persistent(FireMode.CODEC)
            .networkSynchronized(FireMode.STREAM_CODEC)
            .build()
    );

    /**
     * Se hÃƒÆ’Ã‚Â¡ bala no cano (boolean)
     * Substitui: GUN_HAS_BULLET_IN_BARREL
     */
    public static final Supplier<DataComponentType<Boolean>> GUN_HAS_BULLET_IN_BARREL = COMPONENTS.register("gun_has_bullet_in_barrel",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.BOOL)
            .build()
    );

    /**
     * Quantidade atual de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o na arma (int)
     * Substitui: GUN_CURRENT_AMMO_COUNT_TAG
     */
    public static final Supplier<DataComponentType<Integer>> GUN_CURRENT_AMMO_COUNT = COMPONENTS.register("gun_current_ammo_count",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * Dados de acessÃƒÆ’Ã‚Â³rios da arma (CompoundTag)
     * Substitui: GUN_ATTACHMENT_BASE + sufixos
     * Nota: Mantendo CompoundTag temporariamente para facilitar a migraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o
     */
    public static final Supplier<DataComponentType<CompoundTag>> GUN_ATTACHMENTS = COMPONENTS.register("gun_attachments",
        () -> DataComponentType.<CompoundTag>builder()
            .persistent(CompoundTag.CODEC)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.COMPOUND_TAG)
            .build()
    );

    /**
     * ExperiÃƒÆ’Ã‚Âªncia/nÃƒÆ’Ã‚Â­vel da arma (int)
     * Substitui: GUN_EXP_TAG
     */
    public static final Supplier<DataComponentType<Integer>> GUN_EXP = COMPONENTS.register("gun_exp",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * MuniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o dummy (int)
     * Substitui: GUN_DUMMY_AMMO
     */
    public static final Supplier<DataComponentType<Integer>> GUN_DUMMY_AMMO = COMPONENTS.register("gun_dummy_ammo",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * MÃƒÆ’Ã‚Â¡ximo de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o dummy (int)
     * Substitui: GUN_MAX_DUMMY_AMMO
     */
    public static final Supplier<DataComponentType<Integer>> GUN_MAX_DUMMY_AMMO = COMPONENTS.register("gun_max_dummy_ammo",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * Trava de acessÃƒÆ’Ã‚Â³rios (boolean)
     * Substitui: GUN_ATTACHMENT_LOCK
     */
    public static final Supplier<DataComponentType<Boolean>> GUN_ATTACHMENT_LOCK = COMPONENTS.register("gun_attachment_lock",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.BOOL)
            .build()
    );

    /**
     * ID de display da arma (ResourceLocation)
     * Substitui: GUN_DISPLAY_ID_TAG
     */
    public static final Supplier<DataComponentType<ResourceLocation>> GUN_DISPLAY_ID = COMPONENTS.register("gun_display_id",
        () -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .build()
    );

    /**
     * Cor do laser (int)
     * Substitui: LASER_COLOR_TAG
     */
    public static final Supplier<DataComponentType<Integer>> LASER_COLOR = COMPONENTS.register("laser_color",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * Quantidade de superaquecimento (float)
     * Substitui: GUN_OVERHEAT_TAG
     */
    public static final Supplier<DataComponentType<Float>> GUN_OVERHEAT = COMPONENTS.register("gun_overheat",
        () -> DataComponentType.<Float>builder()
            .persistent(Codec.FLOAT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.FLOAT)
            .build()
    );

    /**
     * Se a arma estÃƒÆ’Ã‚Â¡ travada por superaquecimento (boolean)
     * Substitui: GUN_OVERHEAT_LOCK_TAG
     */
    public static final Supplier<DataComponentType<Boolean>> GUN_OVERHEAT_LOCK = COMPONENTS.register("gun_overheat_lock",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.BOOL)
            .build()
    );

    // === DADOS DE MUNIÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O ===

    /**
     * ID da muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o (ResourceLocation)
     * Substitui: AMMO_ID_TAG
     */
    public static final Supplier<DataComponentType<ResourceLocation>> AMMO_ID = COMPONENTS.register("ammo_id",
        () -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .build()
    );

    // === DADOS DE ACESSÃƒÆ’Ã¢â‚¬Å“RIOS ===

    /**
     * ID do acessÃƒÆ’Ã‚Â³rio (ResourceLocation)
     * Para AttachmentItemDataAccessor
     */
    public static final Supplier<DataComponentType<ResourceLocation>> ATTACHMENT_ID = COMPONENTS.register("attachment_id",
        () -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .build()
    );

    // === DADOS DE CAIXA DE MUNIÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O ===

    /**
     * ID da muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o na caixa (ResourceLocation)
     * Para AmmoBoxItemDataAccessor
     */
    public static final Supplier<DataComponentType<ResourceLocation>> AMMO_BOX_AMMO_ID = COMPONENTS.register("ammo_box_ammo_id",
        () -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .build()
    );    /**
     * Quantidade de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o na caixa (int)
     * Para AmmoBoxItemDataAccessor
     */
    public static final Supplier<DataComponentType<Integer>> AMMO_BOX_AMOUNT = COMPONENTS.register("ammo_box_amount",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * NÃƒÆ’Ã‚Â­vel da caixa de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o (int)
     * Para AmmoBoxItemDataAccessor
     */
    public static final Supplier<DataComponentType<Integer>> AMMO_BOX_LEVEL = COMPONENTS.register("ammo_box_level",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );

    /**
     * Modo criativo da caixa de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o (boolean)
     * Para AmmoBoxItemDataAccessor
     */
    public static final Supplier<DataComponentType<Boolean>> AMMO_BOX_CREATIVE = COMPONENTS.register("ammo_box_creative",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.BOOL)
            .build()
    );

    /**
     * Modo criativo universal da caixa de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o (boolean)
     * Para AmmoBoxItemDataAccessor
     */
    public static final Supplier<DataComponentType<Boolean>> AMMO_BOX_ALL_TYPE_CREATIVE = COMPONENTS.register("ammo_box_all_type_creative",
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.BOOL)
            .build()
    );

    // === DADOS DE BLOCOS ===

    /**
     * ID do bloco (ResourceLocation)
     * Para BlockItemDataAccessor
     */
    public static final Supplier<DataComponentType<ResourceLocation>> BLOCK_ID = COMPONENTS.register("block_id",
        () -> DataComponentType.<ResourceLocation>builder()
            .persistent(ResourceLocation.CODEC)
            .networkSynchronized(ResourceLocation.STREAM_CODEC)
            .build()
    );

    // === DADOS DE TOOLTIP ===

    /**
     * Flags para ocultar partes do tooltip (int)
     * Para GunTooltipPart
     */
    public static final Supplier<DataComponentType<Integer>> HIDE_FLAGS = COMPONENTS.register("hide_flags",
        () -> DataComponentType.<Integer>builder()
            .persistent(Codec.INT)
            .networkSynchronized(net.minecraft.network.codec.ByteBufCodecs.VAR_INT)
            .build()
    );
}































































