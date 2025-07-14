package com.tacz.guns.client.event;

import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.client.gui.GunSmithTableScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT)
public class PreventsHotbarEvent {
    @SubscribeEvent
    public static void onRenderHotbarEvent(RenderGuiLayerEvent.Pre event) {
        // todo ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹ÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚Âº
        Screen screen = Minecraft.getInstance().screen;
        // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚ÂÃ‹â€ ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚ÂÃ‚Â°ÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ©Ã¢â‚¬â€Ã‚Â­ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â¯
        if (screen instanceof GunSmithTableScreen) {
            event.setCanceled(true);
            return;
        }
        // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ©Ã¢â‚¬â€Ã‚Â­ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â¯
        if (screen instanceof GunRefitScreen) {
            event.setCanceled(true);
        }
    }
}































































