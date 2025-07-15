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
        this.resetData();

        ItemStack currentItem = player.getMainHandItem();
        long drawTime = System.currentTimeMillis() - data.clientDrawTimestamp;
        IGun currentGun = IGun.getIGunOrNull(currentItem);
        IGun lastGun = IGun.getIGunOrNull(lastItem);

        if (drawTime >= 0) {
            drawTime = getDrawTime(lastItem, lastGun, drawTime);
        }
        long putAwayTime = Math.abs(drawTime);

        if (Minecraft.getInstance().gameMode != null) {
            // TODO: [MIGRATION] ensureHasSentCarriedItem() is now private in NeoForge 1.21.1
            // Minecraft.getInstance().gameMode.ensureHasSentCarriedItem();
        }            NetworkHandler.sendToServer(new ClientMessagePlayerDrawGun());
        NeoForge.EVENT_BUS.post(new GunDrawEvent(player, lastItem, currentItem, LogicalSide.CLIENT));

        if (drawTime >= 0) {
            doPutAway(lastItem, putAwayTime);
        }

        if (currentGun != null) {
            doDraw(currentItem, putAwayTime);
            AttachmentPropertyManager.postChangeEvent(player, currentItem);
        }
    }

    private void doDraw(ItemStack currentItem, long putAwayTime) {
        TimelessAPI.getGunDisplay(currentItem).ifPresent(display -> {
            if (data.drawFuture != null) {
                data.drawFuture.cancel(false);
            }
            data.drawFuture = LocalPlayerDataHolder.SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
                Minecraft.getInstance().submitAsync(() -> {
                    SoundPlayManager.stopPlayGunSound();
                    SoundPlayManager.playDrawSound(player, display.getDisplayInstance());
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
                SoundPlayManager.stopPlayGunSound();
                SoundPlayManager.playPutAwaySound(player, display.getDisplayInstance());
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
        data.lockState(operator -> operator.getSynDrawCoolDown() > 0);
        data.isShootRecorded = true;
        data.clientShootTimestamp = -1;
        data.clientIsAiming = false;
        data.clientAimingProgress = 0;
        LocalPlayerDataHolder.oldAimingProgress = 0;
        data.isBolting = false;
        if (data.clientDrawTimestamp == -1) {
            data.clientDrawTimestamp = System.currentTimeMillis();
        }
    }
}































































