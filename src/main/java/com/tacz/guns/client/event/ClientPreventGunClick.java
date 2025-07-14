package com.tacz.guns.client.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.input.InteractKey;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class ClientPreventGunClick {
    @SubscribeEvent
    public static void onClickInput(InputEvent.InteractionKeyMappingTriggered event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬â„¢ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬â„¢
        if (InteractKey.INTERACT_KEY.isDown()) {
            return;
        }
        // ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ§Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬â„¢
        ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemInHand.getItem() instanceof IGun) {
            // ÃƒÂ¥Ã‚Â±Ã¢â‚¬Â¢ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬â„¢
            HitResult hitResult = Minecraft.getInstance().hitResult;
            if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof ItemFrame) {
                return;
            }
            // ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Âº false ÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ©Ã‹Å“Ã‚Â»ÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚Â
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }
}































































