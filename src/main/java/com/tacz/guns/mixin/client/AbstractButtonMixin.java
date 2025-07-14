package com.tacz.guns.mixin.client;

import com.tacz.guns.client.gameplay.LocalPlayerDataHolder;
import net.minecraft.client.gui.components.AbstractButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractButton.class)
public class AbstractButtonMixin {
    /**
     * ÃƒÂ¨Ã‚Â®Ã‚Â°ÃƒÂ¥Ã‚Â½Ã¢â‚¬Â¢ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬â„¢Ã‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã‚Â»Ã‚Â­ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¤Ã‚Â¾Ã‚Â¿ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ¤Ã‚ÂºÃ‹â€ ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã¢â‚¬Â Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã‹Å“Ã‚Â²ÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬â„¢Ã‚Â®ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¨Ã‚Â¯Ã‚Â¯ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«
     */
    @Inject(method = "onClick(DD)V", at = @At("HEAD"))
    public void onClickHead(double mouseX, double mouseY, CallbackInfo ci) {
        LocalPlayerDataHolder.clientClickButtonTimestamp = System.currentTimeMillis();
    }
}































































