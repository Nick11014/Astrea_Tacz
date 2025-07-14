package com.tacz.guns.api.vmlib;

import com.google.common.collect.Maps;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.item.gun.FireMode;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.Map;

public class LuaGunLogicConstant implements LuaLibrary{
    private final Map<String, Object> constantMap = Maps.newHashMap();

    public LuaGunLogicConstant() {
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































































