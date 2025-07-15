package com.tacz.guns.client.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.renderer.item.AnimateGeoItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
// import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class TickAnimationEvent {
    // @SubscribeEvent  
    // public static void tickAnimation(TickEvent.ClientTickEvent event) {
    //     if (event.phase != TickEvent.Phase.START) {
    //         return;
    //     }
    //     LocalPlayer player = Minecraft.getInstance().player;
    //     if (player == null) {
    //         return;
    //     }
    //     ItemStack mainHandItem = player.getMainHandItem();
    //     TimelessAPI.getGunDisplay(mainHandItem).ifPresent(gunIndex -> {
    //         var animationStateMachine = gunIndex.getAnimationStateMachine();
    //         if (player.input == null) {
    //             animationStateMachine.trigger(GunAnimationConstant.INPUT_IDLE);
    //             return;
    //         }
    //         if (!player.isMovingSlowly() && player.isSprinting()) {
    //             animationStateMachine.trigger(GunAnimationConstant.INPUT_RUN);
    //         } else if (!player.isMovingSlowly() && player.input.getMoveVector().length() > 0.01) {
    //             animationStateMachine.trigger(GunAnimationConstant.INPUT_WALK);
    //         } else {
    //             animationStateMachine.trigger(GunAnimationConstant.INPUT_IDLE);
    //         }
    //     });
    // }

    @SubscribeEvent
    public static void tickAnimation(RenderFrameEvent.Pre event) {
        if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ItemStack mainHandItem = player.getMainHandItem();
        if (IClientItemExtensions.of(mainHandItem.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            if (renderer.needReInit(mainHandItem)) {
                renderer.tryInit(mainHandItem, player, event.getPartialTick().getGameTimeDeltaPartialTick(false));
            }
            renderer.visualUpdate(mainHandItem);
        }
    }
}































































