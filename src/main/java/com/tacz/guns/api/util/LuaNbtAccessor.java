package com.tacz.guns.api.util;

import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.init.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Accessor para dados de items no NeoForge 1.21.1
 * Migrado do sistema NBT para DataComponents
 * MantÃƒÆ’Ã‚Â©m compatibilidade com scripts Lua existentes
 */
@SuppressWarnings("unused")
public record LuaNbtAccessor(ItemStack stack, CompoundTag fallbackNbt) {

    public static LuaNbtAccessor from(ItemStack stack) {
        return new LuaNbtAccessor(stack, new CompoundTag());
    }

    public static LuaNbtAccessor from(CompoundTag nbt) {
        return new LuaNbtAccessor(ItemStack.EMPTY, nbt);
    }

    public boolean contains(String key) {
        // Tentar primeiro com DataComponents, depois fallback para NBT
        return getDataComponentValue(key) != null || fallbackNbt.contains(key);
    }

    public boolean contains(String key, int type) {
        return fallbackNbt.contains(key, type);
    }

    public LuaNbtAccessor newCompoundTag() {
        return new LuaNbtAccessor(ItemStack.EMPTY, new CompoundTag());
    }

    public int getInt(String key) {
        Object value = getDataComponentValue(key);
        if (value instanceof Integer intValue) {
            return intValue;
        }
        return fallbackNbt.getInt(key);
    }

    public double getDouble(String key) {
        return fallbackNbt.getDouble(key);
    }

    public float getFloat(String key) {
        return fallbackNbt.getFloat(key);
    }

    public long getLong(String key) {
        Object value = getDataComponentValue(key);
        if (value instanceof Long longValue) {
            return longValue;
        }
        return fallbackNbt.getLong(key);
    }

    public String getString(String key) {
        Object value = getDataComponentValue(key);
        if (value instanceof String stringValue) {
            return stringValue;
        }
        return fallbackNbt.getString(key);
    }

    public boolean getBoolean(String key) {
        Object value = getDataComponentValue(key);
        if (value instanceof Boolean boolValue) {
            return boolValue;
        }
        return fallbackNbt.getBoolean(key);
    }

    public LuaNbtAccessor getCompound(String key) {
        if (!fallbackNbt.contains(key, Tag.TAG_COMPOUND)) {
            return null;
        }
        return from(fallbackNbt.getCompound(key));
    }

    public void putInt(String key, int value) {
        setDataComponentValue(key, value);
        fallbackNbt.putInt(key, value);
    }

    public void putDouble(String key, double value) {
        fallbackNbt.putDouble(key, value);
    }

    public void putFloat(String key, float value) {
        fallbackNbt.putFloat(key, value);
    }

    public void putLong(String key, long value) {
        setDataComponentValue(key, value);
        fallbackNbt.putLong(key, value);
    }

    public void putString(String key, String value) {
        setDataComponentValue(key, value);
        fallbackNbt.putString(key, value);
    }

    public void putBoolean(String key, boolean value) {
        setDataComponentValue(key, value);
        fallbackNbt.putBoolean(key, value);
    }

    /**
     *ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾CompoundÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã‚Â·Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾Compound
     *
     * @param key   ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®
     * @param value ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¨Ã‚Â¯Ã‚Â·ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨{@link LuaNbtAccessor#newCompoundTag()}ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾LuaNbtAccessorÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â±Ã‚Â¡
     */
    public void putCompound(String key, LuaNbtAccessor value) {
        if (value != null) {
            fallbackNbt.put(key, value.fallbackNbt());
        }
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m valor de DataComponent baseado na chave
     */
    private Object getDataComponentValue(String key) {
        if (stack.isEmpty()) return null;
        
        return switch (key) {
            case "current_ammo" -> stack.get(ModDataComponents.GUN_CURRENT_AMMO_COUNT.get());
            case "has_bullet_in_barrel" -> stack.get(ModDataComponents.GUN_HAS_BULLET_IN_BARREL.get());
            case "gun_fire_mode" -> stack.get(ModDataComponents.GUN_FIRE_MODE.get());
            case "gun_id" -> stack.get(ModDataComponents.GUN_ID.get());
            case "gun_exp" -> stack.get(ModDataComponents.GUN_EXP.get());
            case "gun_attachments" -> stack.get(ModDataComponents.GUN_ATTACHMENTS.get());
            case "gun_dummy_ammo" -> stack.get(ModDataComponents.GUN_DUMMY_AMMO.get());
            case "gun_max_dummy_ammo" -> stack.get(ModDataComponents.GUN_MAX_DUMMY_AMMO.get());
            case "gun_attachment_lock" -> stack.get(ModDataComponents.GUN_ATTACHMENT_LOCK.get());
            case "gun_display_id" -> stack.get(ModDataComponents.GUN_DISPLAY_ID.get());
            case "laser_color" -> stack.get(ModDataComponents.LASER_COLOR.get());
            case "gun_overheat" -> stack.get(ModDataComponents.GUN_OVERHEAT.get());
            case "gun_overheat_lock" -> stack.get(ModDataComponents.GUN_OVERHEAT_LOCK.get());
            case "ammo_id" -> stack.get(ModDataComponents.AMMO_ID.get());
            case "attachment_id" -> stack.get(ModDataComponents.ATTACHMENT_ID.get());
            case "ammo_box_ammo_id" -> stack.get(ModDataComponents.AMMO_BOX_AMMO_ID.get());
            case "ammo_box_amount" -> stack.get(ModDataComponents.AMMO_BOX_AMOUNT.get());
            case "ammo_box_level" -> stack.get(ModDataComponents.AMMO_BOX_LEVEL.get());
            case "ammo_box_creative" -> stack.get(ModDataComponents.AMMO_BOX_CREATIVE.get());
            case "ammo_box_all_type_creative" -> stack.get(ModDataComponents.AMMO_BOX_ALL_TYPE_CREATIVE.get());
            case "block_id" -> stack.get(ModDataComponents.BLOCK_ID.get());
            case "hide_flags" -> stack.get(ModDataComponents.HIDE_FLAGS.get());
            default -> null;
        };
    }

    /**
     * Define valor de DataComponent baseado na chave
     */
    @SuppressWarnings("unchecked")
    private void setDataComponentValue(String key, Object value) {
        if (stack.isEmpty()) return;
        
        switch (key) {
            case "current_ammo" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.GUN_CURRENT_AMMO_COUNT.get(), intValue);
                }
            }
            case "has_bullet_in_barrel" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.GUN_HAS_BULLET_IN_BARREL.get(), boolValue);
                }
            }
            case "gun_fire_mode" -> {
                if (value instanceof Integer intValue) {
                    // FireMode ÃƒÆ’Ã‚Â© um enum, precisa converter
                    var fireMode = FireMode.values()[intValue % FireMode.values().length];
                    stack.set(ModDataComponents.GUN_FIRE_MODE.get(), fireMode);
                }
            }
            case "gun_id" -> {
                if (value instanceof String stringValue) {
                    stack.set(ModDataComponents.GUN_ID.get(), ResourceLocation.parse(stringValue));
                }
            }
            case "gun_exp" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.GUN_EXP.get(), intValue);
                }
            }
            case "gun_dummy_ammo" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.GUN_DUMMY_AMMO.get(), intValue);
                }
            }
            case "gun_max_dummy_ammo" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.GUN_MAX_DUMMY_AMMO.get(), intValue);
                }
            }
            case "gun_attachment_lock" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.GUN_ATTACHMENT_LOCK.get(), boolValue);
                }
            }
            case "gun_display_id" -> {
                if (value instanceof String stringValue) {
                    stack.set(ModDataComponents.GUN_DISPLAY_ID.get(), ResourceLocation.parse(stringValue));
                }
            }
            case "laser_color" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.LASER_COLOR.get(), intValue);
                }
            }
            case "gun_overheat" -> {
                if (value instanceof Float floatValue) {
                    stack.set((DataComponentType<Float>) ModDataComponents.GUN_OVERHEAT.get(), floatValue);
                }
            }
            case "gun_overheat_lock" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.GUN_OVERHEAT_LOCK.get(), boolValue);
                }
            }
            case "ammo_id" -> {
                if (value instanceof String stringValue) {
                    stack.set(ModDataComponents.AMMO_ID.get(), ResourceLocation.parse(stringValue));
                }
            }
            case "attachment_id" -> {
                if (value instanceof String stringValue) {
                    stack.set(ModDataComponents.ATTACHMENT_ID.get(), ResourceLocation.parse(stringValue));
                }
            }
            case "ammo_box_ammo_id" -> {
                if (value instanceof String stringValue) {
                    stack.set(ModDataComponents.AMMO_BOX_AMMO_ID.get(), ResourceLocation.parse(stringValue));
                }
            }
            case "ammo_box_amount" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.AMMO_BOX_AMOUNT.get(), intValue);
                }
            }
            case "ammo_box_level" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.AMMO_BOX_LEVEL.get(), intValue);
                }
            }
            case "ammo_box_creative" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.AMMO_BOX_CREATIVE.get(), boolValue);
                }
            }
            case "ammo_box_all_type_creative" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.AMMO_BOX_ALL_TYPE_CREATIVE.get(), boolValue);
                }
            }
            case "block_id" -> {
                if (value instanceof String stringValue) {
                    stack.set(ModDataComponents.BLOCK_ID.get(), ResourceLocation.parse(stringValue));
                }
            }
            case "hide_flags" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.HIDE_FLAGS.get(), intValue);
                }
            }
        }
    }

    @Override
    @ApiStatus.Internal
    public CompoundTag fallbackNbt() {
        return fallbackNbt;
    }
}































































