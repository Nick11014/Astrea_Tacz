package com.tacz.guns.api.client.animation.statemachine;

import com.tacz.guns.api.client.animation.AnimationController;

import java.util.function.Consumer;

public class LuaAnimationStateMachine<T extends AnimationStateContext> extends AnimationStateMachine<T> {
    Consumer<T> initializeFunc;
    Consumer<T> exitFunc;

    /**
     * ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€šÂ¬Ã…â€™ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â·Ã‚Â¥ÃƒÂ¥Ã…Â½Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹
     *
     * @param animationController ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
     * @see LuaStateMachineFactory
     */
    LuaAnimationStateMachine(AnimationController animationController) {
        super(animationController);
    }

    @Override
    public void initialize() {
        this.initializeFunc.accept(this.context);
        super.initialize();
    }

    @Override
    public void exit() {
        this.exitFunc.accept(this.context);
        super.exit();
    }
}































































