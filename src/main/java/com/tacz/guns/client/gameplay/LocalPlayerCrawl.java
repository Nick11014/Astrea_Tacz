package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerCrawl;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;

public class LocalPlayerCrawl {
    /**
     * ÃƒÂ¥Ã¢â‚¬Â Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã‚Âº 10 tick
     */
    private static final int COOLDOWN_TICKS = 10;
    private final LocalPlayer player;
    private boolean isCrawling = false;
    private int crawCooldownTicks = 0;

    public LocalPlayerCrawl(LocalPlayer player) {
        this.player = player;
    }

    public void crawl(boolean isCrawl) {
        // ÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            return;
        }
        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â­Ã‚Â¦ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
        if (!iGun.isCanCrawl(mainHandItem)) {
            return;
        }
        // ÃƒÂ¥Ã¢â‚¬Â Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™
        if (crawCooldownTicks > 0) {
            return;
        }
        if (player.isSpectator() || player.isPassenger() || !player.onGround()) {
            return;
        }
        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        TimelessAPI.getClientGunIndex(gunId).ifPresent(gunIndex -> {
            this.isCrawling = isCrawl;
            this.crawCooldownTicks = COOLDOWN_TICKS;
            NetworkHandler.CHANNEL.sendToServer(new ClientMessagePlayerCrawl(isCrawl));
        });
    }

    public void tickCrawl() {
        if (crawCooldownTicks > 0) {
            crawCooldownTicks--;
        }
        // ÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            isCrawling = false;
            this.setCrawlPose();
            return;
        }
        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â­Ã‚Â¦ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        if (!iGun.isCanCrawl(mainHandItem)) {
            isCrawling = false;
            this.setCrawlPose();
            return;
        }
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â° gunIndexÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        if (TimelessAPI.getCommonGunIndex(gunId).isEmpty()) {
            isCrawling = false;
            this.setCrawlPose();
            return;
        }
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã‚Â§Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¯Ã…Â¸ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã‚ÂªÃ¢â‚¬ËœÃƒÂ¤Ã‚Â¹Ã‹Å“ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¨Ã‚Â·Ã‚Â³ÃƒÂ¨Ã‚Â·Ã†â€™ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã‚Â³Ã‚Â³ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ 
        if (player.isSpectator() || player.isPassenger() || player.jumping || player.isSwimming() || !player.onGround()) {
            isCrawling = false;
            this.setCrawlPose();
            return;
        }
        this.setCrawlPose();
    }

    public boolean isCrawling() {
        return isCrawling;
    }

    private void setCrawlPose() {
        if (isCrawling) {
            player.setForcedPose(Pose.SWIMMING);
        } else {
            player.setForcedPose(null);
        }
    }
}































































