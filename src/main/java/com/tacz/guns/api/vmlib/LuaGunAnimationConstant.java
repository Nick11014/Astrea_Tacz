package com.tacz.guns.api.vmlib;

import com.google.common.collect.Maps;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ {@link LuaAnimationConstant} ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¤Ã‚Â¼Ã‚Â¼ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
 */
public class LuaGunAnimationConstant implements LuaLibrary {
    private final Map<String, Object> constantMap = Maps.newHashMap();

    public LuaGunAnimationConstant() {
        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ GunAnimationConstant ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â° public ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚Âµ
        Field[] fields = GunAnimationConstant.class.getFields();
        // ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â  static final ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚ÂµÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‹â€ Ã‚Â° constantMap
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                try {
                    // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
                    String name = field.getName();
                    Object value = field.get(null);
                    constantMap.put(name, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // ÃƒÂ¦Ã‹Å“Ã‚Â ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ ReloadState.StateType ÃƒÂ¦Ã…Â¾Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â¾
        for (ReloadState.StateType stateType : ReloadState.StateType.values()) {
            constantMap.put(stateType.name(), stateType.ordinal());
        }

        // ÃƒÂ¦Ã‹Å“Ã‚Â ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ FireMode ÃƒÂ¦Ã…Â¾Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â¾
        for (var fireMode : FireMode.values()) {
            constantMap.put(fireMode.name(), fireMode.ordinal());
        }
    }

    @Override
    public void install(LuaValue chunk) {
        for(Map.Entry<String, Object> entry : constantMap.entrySet()) {
            chunk.set(entry.getKey(), CoerceJavaToLua.coerce(entry.getValue()));
        }
    }
}































































