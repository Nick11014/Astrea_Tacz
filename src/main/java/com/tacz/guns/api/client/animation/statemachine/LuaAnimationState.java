package com.tacz.guns.api.client.animation.statemachine;

import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LuaAnimationState<T extends AnimationStateContext> implements AnimationState<T> {
    private final @Nonnull LuaTable stateTable;
    private final @Nonnull LuaTable scriptTable;
    private final @Nullable LuaFunction updateFunction;
    private final @Nullable LuaFunction enterFunction;
    private final @Nullable LuaFunction exitFunction;
    private final @Nullable LuaFunction transitionFunction;

    /**
     * ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ lua ÃƒÂ¨Ã¢â‚¬Å¾Ã…Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€šÂ¬Ã…â€™ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â·Ã‚Â¥ÃƒÂ¥Ã…Â½Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param stateTable ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚ÂÃ‚Â«ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â½ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¡Ã‚Â¨
     * @see LuaStateMachineFactory
     */
    LuaAnimationState(@Nonnull LuaTable stateTable, @Nonnull LuaTable scriptTable) {
        this.stateTable = stateTable;
        this.scriptTable = scriptTable;
        this.updateFunction = checkLuaFunction("update");
        this.enterFunction = checkLuaFunction("entry");
        this.exitFunction = checkLuaFunction("exit");
        this.transitionFunction = checkLuaFunction("transition");
    }

    @Override
    public void update(T context) {
        if (updateFunction != null) {
            updateFunction.call(scriptTable, CoerceJavaToLua.coerce(context));
        }
    }

    @Override
    public void entryAction(T context) {
        if (enterFunction != null) {
            enterFunction.call(scriptTable, CoerceJavaToLua.coerce(context));
        }
    }

    @Override
    public void exitAction(T context) {
        if (exitFunction != null) {
            exitFunction.call(scriptTable, CoerceJavaToLua.coerce(context));
        }
    }

    @Override
    public AnimationState<T> transition(T context, String condition) {
        if (transitionFunction != null) {
            LuaString conditionToLua = LuaString.valueOf(condition);
            LuaValue nextStateTable = transitionFunction.call(scriptTable, CoerceJavaToLua.coerce(context), conditionToLua);
            if (nextStateTable.istable()) {
                return new LuaAnimationState<>((LuaTable) nextStateTable, scriptTable);
            } else if (nextStateTable.isnil()) {
                return null;
            }
            throw new LuaError("the return of function 'transition' must be table or nil");
        }
        return null;
    }

    private LuaFunction checkLuaFunction(String funcName) {
        LuaValue value = stateTable.get(funcName);
        if (value.isfunction()) {
            return (LuaFunction) value;
        } else if (value.isnil()) {
            return null;
        }
        throw new LuaError("the type of field '" + funcName + "' must be function or nil");
    }
}































































