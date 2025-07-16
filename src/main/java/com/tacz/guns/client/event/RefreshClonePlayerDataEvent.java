package com.tacz.guns.client.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.util.DelayedTask;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.Clone;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.function.BooleanSupplier;

/**
 * ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã‚Â·Ã‚Â¨ÃƒÂ¨Ã‚Â¶Ã…Â ÃƒÂ§Ã‚Â»Ã‚Â´ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class RefreshClonePlayerDataEvent {
    @SubscribeEvent
    public static void onClientPlayerClone(Clone event) {
        LocalPlayer newPlayer = event.getNewPlayer();
        DelayedTask.add(() -> IGunOperator.fromLivingEntity(newPlayer).initialData(), 10);
    }

    /**
     * ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
     */
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
            try {
                DelayedTask.SUPPLIERS.removeIf(BooleanSupplier::getAsBoolean);
            } catch (Exception e) {
                DelayedTask.SUPPLIERS.clear();
                GunMod.LOGGER.catching(e);
            }
    }
}