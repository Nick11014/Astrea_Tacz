package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateMachine;
import com.tacz.guns.api.event.common.GunFireSelectEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerFireSelect;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;

public class LocalPlayerFireSelect {
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;

    public LocalPlayerFireSelect(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    public void fireSelect() {
        if (data.clientStateLock) {
            return;
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            return;
        }
        GunFireSelectEvent event = new GunFireSelectEvent(player, player.getMainHandItem(), LogicalSide.CLIENT);
        NeoForge.EVENT_BUS.post(event);
        if (event.isCancelable() && event.isCancelled()) {
            return;
        }

        TimelessAPI.getGunDisplay(mainHandItem).ifPresent(gunIndex -> {
            SoundPlayManager.playFireSelectSound(player, gunIndex);
            NetworkHandler.sendToServer(new ClientMessagePlayerFireSelect());
            if (iGun instanceof AbstractGunItem logicGun) {
                logicGun.fireSelect(null, mainHandItem);
            }
            AttachmentPropertyManager.postChangeEvent(player, mainHandItem);
            AnimationStateMachine<?> animationStateMachine = gunIndex.getAnimationStateMachine();
            if (animationStateMachine != null) {
                animationStateMachine.trigger(GunAnimationConstant.INPUT_FIRE_SELECT);
            }
        });
    }
}































































