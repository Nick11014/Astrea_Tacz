package com.tacz.guns.compat.controllable;

import com.tacz.guns.init.CompatRegistry;
import com.mrcrayfish.controllable.Controllable;
import com.mrcrayfish.controllable.client.Buttons;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.fml.ModList;

/**
 * Implementação para compatibilidade com Controllable
 */
public class ControllableCompat {
    
    /**
     * Verifica se o Controllable está carregado
     */
    public static boolean isLoaded() {
        return ModList.get().isLoaded(CompatRegistry.CONTROLLABLE);
    }
    
    /**
     * Verifica se um controle está conectado
     */
    public static boolean isControllerConnected() {
        return Controllable.isControllerConnected();
    }
    
    /**
     * Obtém input de controle para mira
     */
    public static boolean isAimButtonPressed() {
        return Controllable.isButtonPressed(Buttons.AIM);
    }
    
    /**
     * Obtém input de controle para tiro
     */
    public static boolean isShootButtonPressed() {
        return Controllable.isButtonPressed(Buttons.SHOOT);
    }
    
    /**
     * Obtém input analógico de movimento do controle
     */
    public static float getMovementInput() {
        return Controllable.getLeftStickValue();
    }
    
    /**
     * Configura vibração do controle
     */
    public static void setControllerVibration(float intensity, int duration) {
        Controllable.setVibration(intensity, duration);
    }
    
    /**
     * Verifica se o player está usando controle
     */
    public static boolean isPlayerUsingController(LocalPlayer player) {
        return Controllable.isPlayerUsingController(player);
    }
    
    /**
     * Obtém sensibilidade de mira configurada no controle
     */
    public static float getAimSensitivity() {
        return Controllable.getAimSensitivity();
    }
    
    /**
     * Converte input de controle para valores de mira
     */
    public static float[] getAimInput() {
        return Controllable.getAimInput();
    }
}































































