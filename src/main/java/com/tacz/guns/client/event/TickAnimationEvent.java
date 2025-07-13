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
// TODO: [MIGRAÇÃO NeoForge 1.21.1] Tick events não estão disponíveis ainda
// import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class TickAnimationEvent {
    // TODO: [MIGRAÇÃO NeoForge 1.21.1] Tick events não estão disponíveis ainda
    // @SubscribeEvent  
    // public static void tickAnimation(TickEvent.ClientTickEvent event) {
    //     // TODO: [MIGRAÇÃO NeoForge 1.21.1] Usando API correta de eventos
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
    //         // 群组服切世界导致的特殊 BUG 处理，正常情况不会遇到此问题
    //         if (player.input == null) {
    //             animationStateMachine.trigger(GunAnimationConstant.INPUT_IDLE);
    //             return;
    //         }
    //         if (!player.isMovingSlowly() && player.isSprinting()) {
    //             // 如果玩家正在移动，播放移动动画，否则播放 idle 动画
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
        // 渲染相关内容整理到物品的IClientItemExtensions了，这个接口有待进一步抽象
        if (IClientItemExtensions.of(mainHandItem.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            // Se o item for diferente, primeiro tenta inicializar a máquina de estado
            if (renderer.needReInit(mainHandItem)) {
                renderer.tryInit(mainHandItem, player, event.getPartialTick());
            }
            renderer.visualUpdate(mainHandItem);
        }
    }
}
