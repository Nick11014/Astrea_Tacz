package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class LivingEntityCrawl {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityCrawl(LivingEntity shooter, ShooterDataHolder data) {
        this.shooter = shooter;
        this.data = data;
    }

    public void crawl(boolean isCrawl) {
        data.isCrawling = isCrawl;
    }

    public void tickCrawling() {
        // currentGunItem ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚Âº nullÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        if (data.currentGunItem == null || !(data.currentGunItem.get().getItem() instanceof IGun iGun)) {
            data.isCrawling = false;
            this.setCrawlPose();
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â­Ã‚Â¦ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        if (!iGun.isCanCrawl(currentGunItem)) {
            data.isCrawling = false;
            this.setCrawlPose();
            return;
        }
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â° gunIndexÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        ResourceLocation gunId = iGun.getGunId(currentGunItem);
        if (TimelessAPI.getCommonGunIndex(gunId).isEmpty()) {
            data.isCrawling = false;
            this.setCrawlPose();
            return;
        }
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã‚Â§Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¯Ã…Â¸ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã‚ÂªÃ¢â‚¬ËœÃƒÂ¤Ã‚Â¹Ã‹Å“ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¨Ã‚Â·Ã‚Â³ÃƒÂ¨Ã‚Â·Ã†â€™ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã‚Â³Ã‚Â³ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ 
        if (shooter.isSpectator() || shooter.isPassenger() || shooter.isSwimming() || !shooter.onGround()) {
            data.isCrawling = false;
            this.setCrawlPose();
            return;
        }
        this.setCrawlPose();
    }

    private void setCrawlPose() {
        if (data.isCrawling) {
            if (shooter instanceof Player player) {
                player.setForcedPose(Pose.SWIMMING);
            } else {
                this.shooter.setPose(Pose.SWIMMING);
            }
        } else {
            if (shooter instanceof Player player) {
                player.setForcedPose(null);
            }
        }
    }
}































































