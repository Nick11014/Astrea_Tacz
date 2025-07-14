package com.tacz.guns.client.event;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.client.renderer.other.GunHurtBobTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT)
public class PlayerHurtByGunEvent {
    @SubscribeEvent
    public static void onPlayerHurtByGun(EntityHurtByGunEvent.Post event) {
        LogicalSide logicalSide = event.getLogicalSide();
        if (logicalSide != LogicalSide.CLIENT) {
            return;
        }
        Entity hurtEntity = event.getHurtEntity();
        LocalPlayer player = Minecraft.getInstance().player;
        // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        if (player != null && player.equals(hurtEntity)) {
            ResourceLocation gunId = event.getGunId();
            TimelessAPI.getCommonGunIndex(gunId).ifPresent(index -> {
                float tweakMultiplier = index.getGunData().getHurtBobTweakMultiplier();
                GunHurtBobTweak.markTimestamp(tweakMultiplier);
            });
        }
    }
}































































