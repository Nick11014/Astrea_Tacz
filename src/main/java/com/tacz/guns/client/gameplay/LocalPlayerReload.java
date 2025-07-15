package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.event.common.GunReloadEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerCancelReload;
import com.tacz.guns.network.message.ClientMessagePlayerReloadGun;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.NeoForge;

public class LocalPlayerReload {
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;

    public LocalPlayerReload(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    /**
     * Inicia o processo de cancelamento de recarga, enviando uma notificação ao servidor
     * e acionando a animação de cancelamento no cliente.
     */
    public void cancelReload() {
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof AbstractGunItem)) {
            return;
        }

        TimelessAPI.getGunDisplay(mainHandItem).ifPresent(display -> {
            IGunOperator gunOperator = IGunOperator.fromLivingEntity(player);
            ReloadState reloadState = gunOperator.getSynReloadState();
            if (!reloadState.getStateType().isReloading()) {
                return;
            }
            // Notifica o servidor sobre o cancelamento
            NetworkHandler.sendToServer(new ClientMessagePlayerCancelReload());
            this.cancelReload(display);
        });
    }

    /**
     * Inicia o processo de recarga da arma.
     */
    public void reload() {
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof AbstractGunItem gunItem)) {
            return;
        }

        ResourceLocation gunId = gunItem.getGunId(mainHandItem);
        GunData gunData = TimelessAPI.getClientGunIndex(gunId).map(ClientGunIndex::getGunData).orElse(null);
        if (gunData == null) {
            return;
        }

        TimelessAPI.getGunDisplay(mainHandItem).ifPresent(display -> {
            if (gunItem.useInventoryAmmo(mainHandItem)) {
                return;
            }
            if (data.clientStateLock) {
                return;
            }
            boolean canReload = gunItem.canReload(player, mainHandItem);
            if (IGunOperator.fromLivingEntity(player).needCheckAmmo() && !canReload) {
                return;
            }
            data.lockState(operator -> operator.getSynReloadState().getStateType().isReloading());

            // Dispara o evento de recarga e verifica se foi cancelado por outro mod
            GunReloadEvent gunReloadEvent = new GunReloadEvent(player, player.getMainHandItem(), LogicalSide.CLIENT);
            NeoForge.EVENT_BUS.post(gunReloadEvent);
            if (gunReloadEvent.isCancelable() && gunReloadEvent.isCancelled()) {
                return;
            }

            NetworkHandler.sendToServer(new ClientMessagePlayerReloadGun());

            this.doReload(gunItem, display, gunData, mainHandItem);
        });
    }

    /**
     * Executa a lógica de recarga no cliente, como animações e sons.
     */
    private void doReload(IGun iGun, ClientGunIndex display, GunData gunData, ItemStack mainHandItem) {
        var animationStateMachine = display.getAnimationStateMachine();
        if (animationStateMachine != null) {
            Bolt boltType = gunData.getBolt();
            boolean noAmmo;
            if (boltType == Bolt.OPEN_BOLT) {
                noAmmo = iGun.getCurrentAmmoCount(mainHandItem) <= 0;
            } else {
                noAmmo = !iGun.hasBulletInBarrel(mainHandItem);
            }
            // Para qualquer som de arma que esteja tocando e inicia o som de recarga
            SoundPlayManager.stopPlayGunSound();
            SoundPlayManager.playReloadSound(player, display, noAmmo);
            animationStateMachine.trigger(GunAnimationConstant.INPUT_RELOAD);
        }
    }

    /**
     * Aciona a animação de cancelamento de recarga.
     */
    private void cancelReload(ClientGunIndex display) {
        var animationStateMachine = display.getAnimationStateMachine();
        if (animationStateMachine != null) {
            animationStateMachine.trigger(GunAnimationConstant.INPUT_CANCEL_RELOAD);
        }
    }
}
