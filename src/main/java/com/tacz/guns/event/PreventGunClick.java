package com.tacz.guns.event;

import com.tacz.guns.api.item.IGun;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber
public class PreventGunClick {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        // ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ§Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬â„¢
        ItemStack itemInHand = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);
        if (itemInHand.getItem() instanceof IGun) {
            event.setCanceled(true);
        }
    }
}































































