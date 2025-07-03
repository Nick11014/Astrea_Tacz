package com.tacz.guns.api.util;

import com.tacz.guns.init.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Accessor para dados de items no NeoForge 1.21.1
 * Migrado do sistema NBT para DataComponents
 * Mantém compatibilidade com scripts Lua existentes
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
     *向当前的Compound中添加一个新的Compound
     *
     * @param key   键
     * @param value 在脚本中请使用{@link LuaNbtAccessor#newCompoundTag()}创建一个新的LuaNbtAccessor对象
     */
    public void putCompound(String key, LuaNbtAccessor value) {
        if (value != null) {
            fallbackNbt.put(key, value.fallbackNbt());
        }
    }

    /**
     * Obtém valor de DataComponent baseado na chave
     */
    private Object getDataComponentValue(String key) {
        if (stack.isEmpty()) return null;
        
        return switch (key) {
            case "current_ammo" -> stack.get(ModDataComponents.CURRENT_AMMO.get());
            case "has_bullet_in_barrel" -> stack.get(ModDataComponents.HAS_BULLET_IN_BARREL.get());
            case "shoot_cooldown" -> stack.get(ModDataComponents.SHOOT_COOLDOWN.get());
            case "reload_state_type" -> stack.get(ModDataComponents.RELOAD_STATE_TYPE.get());
            case "reload_state_count" -> stack.get(ModDataComponents.RELOAD_STATE_COUNT.get());
            case "fire_mode" -> stack.get(ModDataComponents.FIRE_MODE.get());
            case "aim_status" -> stack.get(ModDataComponents.AIM_STATUS.get());
            case "draw_cooldown" -> stack.get(ModDataComponents.DRAW_COOLDOWN.get());
            case "draw_timestamp" -> stack.get(ModDataComponents.DRAW_TIMESTAMP.get());
            case "put_away_cooldown" -> stack.get(ModDataComponents.PUT_AWAY_COOLDOWN.get());
            case "put_away_timestamp" -> stack.get(ModDataComponents.PUT_AWAY_TIMESTAMP.get());
            case "hide_flags" -> stack.get(ModDataComponents.HIDE_FLAGS.get());
            case "attachment_scope_id" -> stack.get(ModDataComponents.ATTACHMENT_SCOPE_ID.get());
            case "attachment_muzzle_id" -> stack.get(ModDataComponents.ATTACHMENT_MUZZLE_ID.get());
            case "attachment_foregrip_id" -> stack.get(ModDataComponents.ATTACHMENT_FOREGRIP_ID.get());
            case "attachment_stock_id" -> stack.get(ModDataComponents.ATTACHMENT_STOCK_ID.get());
            case "attachment_extended_mag_id" -> stack.get(ModDataComponents.ATTACHMENT_EXTENDED_MAG_ID.get());
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
                    stack.set((DataComponentType<Integer>) ModDataComponents.CURRENT_AMMO.get(), intValue);
                }
            }
            case "has_bullet_in_barrel" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.HAS_BULLET_IN_BARREL.get(), boolValue);
                }
            }
            case "shoot_cooldown" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.SHOOT_COOLDOWN.get(), intValue);
                }
            }
            case "reload_state_type" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.RELOAD_STATE_TYPE.get(), intValue);
                }
            }
            case "reload_state_count" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.RELOAD_STATE_COUNT.get(), intValue);
                }
            }
            case "fire_mode" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.FIRE_MODE.get(), intValue);
                }
            }
            case "aim_status" -> {
                if (value instanceof Boolean boolValue) {
                    stack.set((DataComponentType<Boolean>) ModDataComponents.AIM_STATUS.get(), boolValue);
                }
            }
            case "draw_cooldown" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.DRAW_COOLDOWN.get(), intValue);
                }
            }
            case "draw_timestamp" -> {
                if (value instanceof Long longValue) {
                    stack.set((DataComponentType<Long>) ModDataComponents.DRAW_TIMESTAMP.get(), longValue);
                }
            }
            case "put_away_cooldown" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.PUT_AWAY_COOLDOWN.get(), intValue);
                }
            }
            case "put_away_timestamp" -> {
                if (value instanceof Long longValue) {
                    stack.set((DataComponentType<Long>) ModDataComponents.PUT_AWAY_TIMESTAMP.get(), longValue);
                }
            }
            case "hide_flags" -> {
                if (value instanceof Integer intValue) {
                    stack.set((DataComponentType<Integer>) ModDataComponents.HIDE_FLAGS.get(), intValue);
                }
            }
            case "attachment_scope_id" -> {
                if (value instanceof String stringValue) {
                    stack.set((DataComponentType<String>) ModDataComponents.ATTACHMENT_SCOPE_ID.get(), stringValue);
                }
            }
            case "attachment_muzzle_id" -> {
                if (value instanceof String stringValue) {
                    stack.set((DataComponentType<String>) ModDataComponents.ATTACHMENT_MUZZLE_ID.get(), stringValue);
                }
            }
            case "attachment_foregrip_id" -> {
                if (value instanceof String stringValue) {
                    stack.set((DataComponentType<String>) ModDataComponents.ATTACHMENT_FOREGRIP_ID.get(), stringValue);
                }
            }
            case "attachment_stock_id" -> {
                if (value instanceof String stringValue) {
                    stack.set((DataComponentType<String>) ModDataComponents.ATTACHMENT_STOCK_ID.get(), stringValue);
                }
            }
            case "attachment_extended_mag_id" -> {
                if (value instanceof String stringValue) {
                    stack.set((DataComponentType<String>) ModDataComponents.ATTACHMENT_EXTENDED_MAG_ID.get(), stringValue);
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
