package com.tacz.guns.event;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.config.common.GunConfig;
import com.tacz.guns.item.ModernKineticGunScriptAPI;
import com.tacz.guns.resource.pojo.data.gun.FeedType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber
public class PlayerRespawnEvent {
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹
        if (!GunConfig.AUTO_RELOAD_WHEN_RESPAWN.get()) return;

        var player = event.getEntity();
        player.getInventory().items.forEach(itemStack -> {
            if (!(itemStack.getItem() instanceof IGun)) return;

            var api = new ModernKineticGunScriptAPI();
            api.setItemStack(itemStack);
            api.setShooter(player);


            // ÃƒÂ©Ã¢â‚¬â„¢Ã‹â€ ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â 
            var useInventoryAmmo = api.getGunIndex().getGunData().getReloadData().getType() == FeedType.INVENTORY;
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹
            if (useInventoryAmmo) {
                return;
            }

            // ÃƒÂ©Ã¢â‚¬â„¢Ã‹â€ ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ§Ã¢â‚¬Â¡Ã†â€™ÃƒÂ¦Ã¢â‚¬â€œÃ¢â€žÂ¢ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â 
            var isFuel = api.getGunIndex().getGunData().getReloadData().getType() == FeedType.FUEL;
            int needAmmoCount = api.getNeededAmmoAmount();

            if (player.isCreative()) {
                api.putAmmoInMagazine(needAmmoCount);
            } else {
                int consumedAmount = api.consumeAmmoFromPlayer(isFuel ? 1 : needAmmoCount);
                api.putAmmoInMagazine(isFuel ? (needAmmoCount * consumedAmount) : consumedAmount);
            }
        });
    }
}































































