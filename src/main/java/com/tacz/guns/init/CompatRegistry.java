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
 * MIGRAÇÃO 1.21.1: Implementação mínima que mantém as constantes
 * e funcionalidade básica de verificação de mod.
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String OCULUS = "oculus";
    public static final String CARRY_ON_ID = "carryon";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        // Implementação mínima: registra compatibilidades básicas
        event.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                // TODO: Implementar quando ClothConfigScreen e MenuIntegration estiverem disponíveis
                // checkModLoad(CLOTH_CONFIG, MenuIntegration::registerModsPage);
                // ClothConfigScreen.registerNoClothConfigPage();
            }
        });
        // TODO: Implementar quando OculusCompat estiver disponível
        // event.enqueueWork(() -> checkModLoad(OCULUS, OculusCompat::initCompat));
        
        // TODO: Implementar quando BlackList estiver disponível  
        // event.enqueueWork(() -> checkModLoad(CARRY_ON_ID, BlackList::addBlackList));
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
