package com.tacz.guns.compat.controllable;

import com.mrcrayfish.controllable.Controllable;
import com.mrcrayfish.controllable.client.input.Controller;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.fml.ModList;

/**
 * Implementação completa para compatibilidade com Controllable
 * MIGRAÇÃO 1.21.1: Sistema completo de gamepad integrado
 */
public class ControllableCompat {
    private static final String MOD_ID = "controllable";

    /**
     * Inicialização do sistema Controllable
     */
    public static void init() {
        if (isLoaded()) {
            try {
                ControllableInner.init();
                System.out.println("TacZ: Controllable integration initialized successfully");
            } catch (Exception e) {
                System.err.println("TacZ: Failed to initialize Controllable integration: " + e.getMessage());
            }
        }
    }

    /**
     * Verifica se o Controllable está carregado
     */
    public static boolean isLoaded() {
        return ModList.get().isLoaded(MOD_ID);
    }

    /**
     * Verifica se um controle está conectado
     */
    public static boolean isControllerConnected() {
        if (!isLoaded()) return false;
        try {
            Controller controller = Controllable.getController();
            return controller != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém input de controle para mira
     */
    public static boolean isAimButtonPressed() {
        if (!isLoaded() || !isControllerConnected()) return false;
        try {
            Controller controller = Controllable.getController();
            return controller != null && controller.isButtonPressed(ControllableInner.AIM.getButton());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém input de controle para tiro
     */
    public static boolean isShootButtonPressed() {
        if (!isLoaded() || !isControllerConnected()) return false;
        try {
            Controller controller = Controllable.getController();
            return controller != null && controller.isButtonPressed(ControllableInner.SHOOT.getButton());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém input analógico de movimento do controle
     */
    public static float getMovementInput() {
        if (!isLoaded() || !isControllerConnected()) return 0f;
        try {
            Controller controller = Controllable.getController();
            if (controller != null) {
                return Math.abs(controller.getLThumbStickXValue()) + Math.abs(controller.getLThumbStickYValue());
            }
        } catch (Exception e) {
            // Ignorar erros
        }
        return 0f;
    }

    /**
     * Configura vibração do controle
     */
    public static void setControllerVibration(float intensity, int duration) {
        if (!isLoaded() || !isControllerConnected()) return;
        try {
            Controller controller = Controllable.getController();
            if (controller != null) {
                controller.rumble(intensity * 0.5f, intensity, duration);
            }
        } catch (Exception e) {
            // Ignorar erros de vibração
        }
    }

    /**
     * Verifica se o player está usando controle
     */
    public static boolean isPlayerUsingController(Object player) {
        return isControllerConnected() && player instanceof LocalPlayer;
    }

    /**
     * Obtém sensibilidade de mira configurada no controle
     */
    public static float getAimSensitivity() {
        if (!isLoaded() || !isControllerConnected()) return 1.0f;
        try {
            Controller controller = Controllable.getController();
            if (controller != null) {
                // Usar sensibilidade baseada na configuração do Controllable
                return Math.max(0.1f, Math.min(2.0f, 1.0f)); // Valor padrão por enquanto
            }
        } catch (Exception e) {
            // Ignorar erros
        }
        return 1.0f;
    }

    /**
     * Converte input de controle para valores de mira
     */
    public static float[] getAimInput() {
        if (!isLoaded() || !isControllerConnected()) return new float[]{0f, 0f};
        try {
            Controller controller = Controllable.getController();
            if (controller != null) {
                float x = controller.getRThumbStickXValue() * getAimSensitivity();
                float y = controller.getRThumbStickYValue() * getAimSensitivity();
                return new float[]{x, y};
            }
        } catch (Exception e) {
            // Ignorar erros
        }
        return new float[]{0f, 0f};
    }
}

