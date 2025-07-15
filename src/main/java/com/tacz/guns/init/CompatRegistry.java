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
 * MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O 1.21.1: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima que mantÃƒÆ’Ã‚Â©m as constantes
 * e funcionalidade bÃƒÆ’Ã‚Â¡sica de verificaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o de mod.
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String OCULUS = "oculus";
    public static final String CARRY_ON_ID = "carryon";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        event.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                // checkModLoad(CLOTH_CONFIG, MenuIntegration::registerModsPage);
                // ClothConfigScreen.registerNoClothConfigPage();
            }
        });
        // event.enqueueWork(() -> checkModLoad(OCULUS, OculusCompat::initCompat));
        
        // event.enqueueWork(() -> checkModLoad(CARRY_ON_ID, BlackList::addBlackList));
    }

    /**
     * Verifica se um mod estÃƒÆ’Ã‚Â¡ carregado e executa um Runnable se estiver
     */
    public static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}































































