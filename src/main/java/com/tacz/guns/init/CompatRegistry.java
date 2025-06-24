package com.tacz.guns.init;

import com.tacz.guns.client.gui.compat.ClothConfigScreen;
import com.tacz.guns.compat.carryon.BlackList;
import com.tacz.guns.compat.cloth.MenuIntegration;
import com.tacz.guns.compat.oculus.OculusCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";
    public static final String OCULUS = "oculus";
    public static final String CARRY_ON_ID = "carryon";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        // Registrar tela de configuração do ClothConfig apenas no cliente
        event.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                checkModLoad(CLOTH_CONFIG, MenuIntegration::registerModsPage);
                ClothConfigScreen.registerNoClothConfigPage();
            }
        });
        event.enqueueWork(() -> checkModLoad(OCULUS, OculusCompat::initCompat));
        event.enqueueWork(() -> checkModLoad(CARRY_ON_ID, BlackList::addBlackList));
    }

    public static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
