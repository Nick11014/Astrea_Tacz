package com.tacz.guns.compat.controllable;

import net.neoforged.fml.ModList;

/**
 * Compatibilidade básica com Controllable
 * MIGRAÇÃO 1.21.1: Implementação simplificada temporária
 */
public class ControllableCompatBasic {
    private static final String MOD_ID = "controllable";

    public static void init() {
        if (ModList.get().isLoaded(MOD_ID)) {
            System.out.println("TacZ: Controllable detected - basic integration active");
            // TODO: Implementar integração completa quando as APIs estiverem estáveis
        }
    }
    
    public static boolean isInstalled() {
        return ModList.get().isLoaded(MOD_ID);
    }
}
