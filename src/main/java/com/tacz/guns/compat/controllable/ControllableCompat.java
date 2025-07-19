package com.tacz.guns.compat.controllable;

import net.neoforged.fml.ModList;

/**
 * Implementação para compatibilidade com Controllable
 */
public class ControllableCompat {
    private static final String MOD_ID = "controllable";

    /**
     * Verifica se o Controllable está carregado
     */
    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    /**
     * Verifica se um controle está conectado
     * (Stub: sempre retorna false, pois API não está disponível)
     */
    public static boolean isControllerConnected() {
        return false;
    }

    /**
     * Obtém input de controle para mira
     * (Stub: sempre retorna false)
     */
    public static boolean isAimButtonPressed() {
        return false;
    }

    /**
     * Obtém input de controle para tiro
     * (Stub: sempre retorna false)
     */
    public static boolean isShootButtonPressed() {
        return false;
    }

    /**
     * Obtém input analógico de movimento do controle
     * (Stub: sempre retorna 0)
     */
    public static float getMovementInput() {
        return 0f;
    }

    /**
     * Configura vibração do controle
     * (Stub: não faz nada)
     */
    public static void setControllerVibration(float intensity, int duration) {
        // Stub
    }

    /**
     * Verifica se o player está usando controle
     * (Stub: sempre retorna false)
     */
    public static boolean isPlayerUsingController(Object player) {
        return false;
    }

    /**
     * Obtém sensibilidade de mira configurada no controle
     * (Stub: sempre retorna 1.0)
     */
    public static float getAimSensitivity() {
        return 1.0f;
    }

    /**
     * Converte input de controle para valores de mira
     * (Stub: sempre retorna [0,0])
     */
    public static float[] getAimInput() {
        return new float[]{0f, 0f};
    }
}

