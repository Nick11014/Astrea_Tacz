package com.tacz.guns.compat.kubejs;

import net.neoforged.fml.ModList;

/**
 * Plugin básico para KubeJS
 * MIGRAÇÃO 1.21.1: Implementação simplificada
 */
public class TimelessKubeJSPluginBasic {
    public static final String KUBEJS_MODID = "kubejs";

    public static void init() {
        if (ModList.get().isLoaded(KUBEJS_MODID)) {
            System.out.println("TacZ: KubeJS detected - basic integration active");
            System.out.println("TacZ: DataComponent factories available via TimelessItemWrapper");
            // As factory classes já estão disponíveis via TimelessItemWrapper
        }
    }
    
    public static boolean isInstalled() {
        return ModList.get().isLoaded(KUBEJS_MODID);
    }
}
