package com.tacz.guns.compat.playeranimator.animation;

import com.tacz.guns.compat.playeranimator.PlayerAnimatorCompat;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;

/**
 * Factory para registrar dados de animação do Player Animator
 * IMPLEMENTADO: Sistema de registro usando PlayerAnimatorCompat
 */
public class AnimationDataRegisterFactory {
    
    /**
     * Registra os dados de animação necessários
     * IMPLEMENTADO: Usando as ResourceLocations do PlayerAnimatorCompat
     */
    public static void registerData() {
        if (PlayerAnimatorCompat.isInstalled()) {
            try {
                // Registrar factory para animações da parte inferior (pernas)
                PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                    PlayerAnimatorCompat.LOWER_ANIMATION, 
                    93, 
                    player -> new ModifierLayer<>()
                );
                
                // Registrar factory para animações da parte superior em loop
                PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                    PlayerAnimatorCompat.LOOP_UPPER_ANIMATION, 
                    94, 
                    player -> new ModifierLayer<>()
                );
                
                // Registrar factory para animações da parte superior únicas
                PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                    PlayerAnimatorCompat.ONCE_UPPER_ANIMATION, 
                    95, 
                    player -> new ModifierLayer<>()
                );
                
                // Registrar factory para animações de rotação
                // Nota: AdjustmentYRotModifier não está disponível, usando ModifierLayer básico
                PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                    PlayerAnimatorCompat.ROTATION_ANIMATION, 
                    96,
                    player -> new ModifierLayer<>()
                );
                
                System.out.println("TacZ: Animation data factories registered successfully");
                
            } catch (Exception e) {
                System.err.println("TacZ: Error registering animation data factories: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("TacZ: Player Animator not installed, skipping animation data registration");
        }
    }
}
