package com.tacz.guns.init;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;

/**
 * Registro de compatibilidade com outros mods.
 * 
 * MIGRAÇÃO 1.21.1: Implementação básica funcionando
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String OCULUS = "oculus";
    public static final String CARRY_ON_ID = "carryon";
    public static final String CONTROLLABLE = "controllable";
    public static final String KUBEJS = "kubejs";
    public static final String PLAYER_ANIMATOR = "playeranimator";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        event.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                // Cloth Config integration
                checkModLoad(CLOTH_CONFIG, () -> {
                    com.tacz.guns.client.gui.compat.ClothConfigScreen.registerNoClothConfigPage();
                });
                
                // Controllable integration (basic)
                checkModLoad(CONTROLLABLE, () -> {
                    com.tacz.guns.compat.controllable.ControllableCompatBasic.init();
                });
                
                // PlayerAnimator integration
                checkModLoad(PLAYER_ANIMATOR, () -> {
                    com.tacz.guns.compat.playeranimator.PlayerAnimatorCompat.init();
                });
            }
            
            // KubeJS integration (common for client and server)
            checkModLoad(KUBEJS, () -> {
                com.tacz.guns.compat.kubejs.TimelessKubeJSPluginBasic.init();
            });
        });
    }

    /**
     * Verifica se um mod está carregado e executa um Runnable se estiver
     */
    public static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}































































