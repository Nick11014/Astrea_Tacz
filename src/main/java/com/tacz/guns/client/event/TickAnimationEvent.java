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
// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Tick events nÃƒÆ’Ã‚Â£o estÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­veis ainda
// import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class TickAnimationEvent {
    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Tick events nÃƒÆ’Ã‚Â£o estÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­veis ainda
    // @SubscribeEvent  
    // public static void tickAnimation(TickEvent.ClientTickEvent event) {
    //     // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Usando API correta de eventos
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
    //         // ÃƒÂ§Ã‚Â¾Ã‚Â¤ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¤Ã‚Â¸Ã¢â‚¬â€œÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â  BUG ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ©Ã‚ÂÃ¢â‚¬Â¡ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ©Ã¢â‚¬â€Ã‚Â®ÃƒÂ©Ã‚Â¢Ã‹Å“
    //         if (player.input == null) {
    //             animationStateMachine.trigger(GunAnimationConstant.INPUT_IDLE);
    //             return;
    //         }
    //         if (!player.isMovingSlowly() && player.isSprinting()) {
    //             // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ idle ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
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
        // ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾IClientItemExtensionsÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â­Ã‚Â¥ÃƒÂ¦Ã…Â Ã‚Â½ÃƒÂ¨Ã‚Â±Ã‚Â¡
        if (IClientItemExtensions.of(mainHandItem.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            // Se o item for diferente, primeiro tenta inicializar a mÃƒÆ’Ã‚Â¡quina de estado
            if (renderer.needReInit(mainHandItem)) {
                renderer.tryInit(mainHandItem, player, event.getPartialTick());
            }
            renderer.visualUpdate(mainHandItem);
        }
    }
}































































