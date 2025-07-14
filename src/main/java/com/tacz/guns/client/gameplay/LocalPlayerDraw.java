package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.GunDrawEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.renderer.item.AnimateGeoItemRenderer;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerDrawGun;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;

import java.util.concurrent.TimeUnit;

public class LocalPlayerDraw {
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;
    public boolean readyToDraw = false;

    public LocalPlayerDraw(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    public void draw(ItemStack lastItem) {
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ§Ã‚Â§Ã‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        this.resetData();

        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ§Ã‚Â§Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
        ItemStack currentItem = player.getMainHandItem();
        long drawTime = System.currentTimeMillis() - data.clientDrawTimestamp;
        IGun currentGun = IGun.getIGunOrNull(currentItem);
        IGun lastGun = IGun.getIGunOrNull(lastItem);

        // ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ draw ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ putAway ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿
        if (drawTime >= 0) {
            drawTime = getDrawTime(lastItem, lastGun, drawTime);
        }
        long putAwayTime = Math.abs(drawTime);

        // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
        if (Minecraft.getInstance().gameMode != null) {
            Minecraft.getInstance().gameMode.ensureHasSentCarriedItem();
        }            NetworkHandler.sendToServer(new ClientMessagePlayerDrawGun());
        NeoForge.EVENT_BUS.post(new GunDrawEvent(player, lastItem, currentItem, LogicalSide.CLIENT));

        // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚Âª
        if (drawTime >= 0) {
            doPutAway(lastItem, putAwayTime);
        }

        // ÃƒÂ¥Ã‚Â¼Ã¢â‚¬Å¡ÃƒÂ¦Ã‚Â­Ã‚Â¥ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¦Ã‹Å“Ã‚Â ÃƒÂ¦Ã…Â Ã‚Â¬ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
        if (currentGun != null) {
            doDraw(currentItem, putAwayTime);
            // ÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
            AttachmentPropertyManager.postChangeEvent(player, currentItem);
        }
    }

    private void doDraw(ItemStack currentItem, long putAwayTime) {
        TimelessAPI.getGunDisplay(currentItem).ifPresent(display -> {
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ©Ã‚Â¢Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ draw ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚Âº
            if (data.drawFuture != null) {
                data.drawFuture.cancel(false);
            }
            // ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â® put away time ÃƒÂ©Ã‚Â¢Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¡ draw ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¨Ã‚Â¯Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
            data.drawFuture = LocalPlayerDataHolder.SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
                Minecraft.getInstance().submitAsync(() -> {
                    SoundPlayManager.stopPlayGunSound();
                    SoundPlayManager.playDrawSound(player, display);
                });
            }, putAwayTime, TimeUnit.MILLISECONDS);
        });
    }

    private void doPutAway(ItemStack lastItem, long putAwayTime) {
        if (IClientItemExtensions.of(lastItem.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            renderer.tryExit(lastItem, putAwayTime);
        }
        TimelessAPI.getGunDisplay(lastItem).ifPresent(display -> {
            Minecraft.getInstance().submitAsync(() -> {
                // ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
                SoundPlayManager.stopPlayGunSound();
                SoundPlayManager.playPutAwaySound(player, display);
            });
        });
    }

    private long getDrawTime(ItemStack lastItem, IGun lastGun, long drawTime) {
        if (IClientItemExtensions.of(lastItem.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            long putAwayTime = renderer.getPutAwayTime(lastItem);
            if (drawTime > putAwayTime) {
                drawTime = putAwayTime;
            }
            data.clientDrawTimestamp = System.currentTimeMillis() + drawTime;
        } else {
            drawTime = 0;
            data.clientDrawTimestamp = System.currentTimeMillis();
        }
        return drawTime;
    }

    private void resetData() {
        // ÃƒÂ©Ã¢â‚¬ÂÃ‚ÂÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã¢â‚¬ÂÃ‚Â
        data.lockState(operator -> operator.getSynDrawCoolDown() > 0);
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ shoot ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¦Ã‹â€ Ã‚Â³
        data.isShootRecorded = true;
        data.clientShootTimestamp = -1;
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        data.clientIsAiming = false;
        data.clientAimingProgress = 0;
        LocalPlayerDataHolder.oldAimingProgress = 0;
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        data.isBolting = false;
        // ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¦Ã‹â€ Ã‚Â³
        if (data.clientDrawTimestamp == -1) {
            data.clientDrawTimestamp = System.currentTimeMillis();
        }
    }
}































































