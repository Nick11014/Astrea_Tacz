package com.tacz.guns.mixin.client;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StairBlock.class)
public interface StairBlockAccessor {
    /**
     * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã‚Â­Ã‚Â£ forge ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¤Ã‚Â¾Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ getModelBlock ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â®Ã‚Â¿ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ©Ã‚Â¢Ã‹Å“
     */
    @Invoker(remap = false)
    Block invokeGetModelBlock();
}































































