package com.tacz.guns.compat.playeranimator;

import com.tacz.guns.client.resource.index.GunDisplayInstance;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationAccess;
import dev.kosmx.playerAnim.api.layered.AnimationStack;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.api.layered.KeyframeAnimationPlayer;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;

import javax.annotation.Nullable;

/**
 * Camada de compatibilidade para o mod Player Animator.
 * Usa uma classe interna para soft-dependency, evitando crashes se o mod não estiver instalado.
 */
public class PlayerAnimatorCompat {
    /**
     * O ID do mod Player Animator.
     */
    private static final String PLAYER_ANIMATOR_MOD_ID = "playeranimator";

    // ResourceLocations usados pelo TacZ para identificar diferentes tipos de animação
    public static final ResourceLocation LOWER_ANIMATION = ResourceLocation.fromNamespaceAndPath("tacz", "lower_animation");
    public static final ResourceLocation LOOP_UPPER_ANIMATION = ResourceLocation.fromNamespaceAndPath("tacz", "loop_upper_animation");
    public static final ResourceLocation ONCE_UPPER_ANIMATION = ResourceLocation.fromNamespaceAndPath("tacz", "once_upper_animation");
    public static final ResourceLocation ROTATION_ANIMATION = ResourceLocation.fromNamespaceAndPath("tacz", "rotation_animation");

    /**
     * Verifica se o Player Animator está instalado e carregado.
     */
    public static boolean isInstalled() {
        return ModList.get().isLoaded(PLAYER_ANIMATOR_MOD_ID);
    }

    /**
     * Inicializa o sistema de compatibilidade com Player Animator.
     * Método chamado durante a inicialização do cliente.
     */
    public static void init() {
        if (isInstalled()) {
            try {
                // Registrar factories de animação para diferentes tipos
                PlayerAnimatorImpl.registerAnimationFactories();
                System.out.println("[TacZ] Player Animator integration initialized successfully!");
            } catch (Exception e) {
                System.err.println("[TacZ] Failed to initialize Player Animator integration: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Toca uma animação para o jogador usando um ResourceLocation.
     */
    public static void playAnimation(Player player, ResourceLocation animationId) {
        if (isInstalled() && player instanceof AbstractClientPlayer clientPlayer) {
            PlayerAnimatorImpl.playAnimation(clientPlayer, animationId);
        }
    }

    /**
     * Toca uma animação para o jogador usando um GunDisplayInstance.
     */
    public static void playAnimation(LivingEntity entity, GunDisplayInstance display, float limbSwingAmount) {
        if (isInstalled() && entity instanceof AbstractClientPlayer clientPlayer) {
            PlayerAnimatorImpl.playAnimationWithDisplay(clientPlayer, display, limbSwingAmount);
        }
    }

    /**
     * Verifica se o jogador tem animação de terceira pessoa disponível.
     */
    public static boolean hasPlayerAnimator3rd(LivingEntity entity, GunDisplayInstance display) {
        if (isInstalled() && entity instanceof AbstractClientPlayer clientPlayer) {
            return PlayerAnimatorImpl.hasThirdPersonAnimation(clientPlayer, display);
        }
        return false;
    }

    /**
     * Para todas as animações de um jogador.
     */
    public static void stopAllAnimations(Player player) {
        if (isInstalled() && player instanceof AbstractClientPlayer clientPlayer) {
            PlayerAnimatorImpl.stopAllAnimations(clientPlayer);
        }
    }

    /**
     * Verifica se uma animação específica está sendo reproduzida.
     */
    public static boolean isAnimationPlaying(Player player, ResourceLocation animationId) {
        if (isInstalled() && player instanceof AbstractClientPlayer clientPlayer) {
            return PlayerAnimatorImpl.isAnimationPlaying(clientPlayer, animationId);
        }
        return false;
    }

    // Os métodos abaixo são compatibilidade para a API antiga do TacZ
    // e podem precisar de ajustes dependendo do uso específico

    public static void stopAllAnimation(LivingEntity entity, int parameter) {
        if (isInstalled() && entity instanceof AbstractClientPlayer clientPlayer) {
            PlayerAnimatorImpl.stopAllAnimations(clientPlayer);
        }
    }

    public static void stopAllAnimation(LivingEntity entity) {
        if (isInstalled() && entity instanceof AbstractClientPlayer clientPlayer) {
            PlayerAnimatorImpl.stopAllAnimations(clientPlayer);
        }
    }

    @Nullable
    public static Object getCurrentAnimation(Player player) {
        if (isInstalled() && player instanceof AbstractClientPlayer clientPlayer) {
            return PlayerAnimatorImpl.getCurrentAnimation(clientPlayer);
        }
        return null;
    }

    /**
     * Classe interna para conter a lógica de compatibilidade real.
     * Isso impede que o jogo crashe se o Player Animator não estiver instalado.
     */
    private static class PlayerAnimatorImpl {
        
        // Sistema de rastreamento de animações ativas por jogador
        private static final java.util.Map<java.util.UUID, java.util.Map<ResourceLocation, ModifierLayer<KeyframeAnimationPlayer>>> 
            activeAnimations = new java.util.concurrent.ConcurrentHashMap<>();
        
        // Prioridades base para diferentes tipos de animação
        private static final int LOWER_ANIMATION_PRIORITY = 93;
        private static final int LOOP_UPPER_ANIMATION_PRIORITY = 94;
        private static final int ONCE_UPPER_ANIMATION_PRIORITY = 95;
        private static final int ROTATION_ANIMATION_PRIORITY = 96;
        
        /**
         * Registra as factories de animação para diferentes tipos de animação TacZ
         */
        public static void registerAnimationFactories() {
            try {
                // Registrar diferentes tipos de animação com prioridades específicas
                // Prioridades: números maiores = maior prioridade
                
                // LOWER_ANIMATION - Animações das pernas (andar, correr, agachar)
                dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory.ANIMATION_DATA_FACTORY
                    .registerFactory(LOWER_ANIMATION, LOWER_ANIMATION_PRIORITY, player -> new ModifierLayer<>());
                
                // LOOP_UPPER_ANIMATION - Animações do corpo superior que fazem loop
                dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory.ANIMATION_DATA_FACTORY
                    .registerFactory(LOOP_UPPER_ANIMATION, LOOP_UPPER_ANIMATION_PRIORITY, player -> new ModifierLayer<>());
                
                // ONCE_UPPER_ANIMATION - Animações do corpo superior que executam uma vez
                dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory.ANIMATION_DATA_FACTORY
                    .registerFactory(ONCE_UPPER_ANIMATION, ONCE_UPPER_ANIMATION_PRIORITY, player -> new ModifierLayer<>());
                
                // ROTATION_ANIMATION - Animações de rotação especiais
                dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory.ANIMATION_DATA_FACTORY
                    .registerFactory(ROTATION_ANIMATION, ROTATION_ANIMATION_PRIORITY, player -> new ModifierLayer<>());
                    
                System.out.println("[TacZ] Registered " + 4 + " animation factories");
            } catch (Exception e) {
                System.err.println("[TacZ] Error registering animation factories: " + e.getMessage());
                throw e;
            }
        }
        
        public static void playAnimation(AbstractClientPlayer player, ResourceLocation animationId) {
            try {
                AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(player);
                ModifierLayer<KeyframeAnimationPlayer> modifierLayer = new ModifierLayer<>();
                
                System.out.println("[TacZ] Playing animation: " + animationId + " for player: " + player.getName().getString());
                
                // Carregar animação do ResourceLocation
                KeyframeAnimation animation = loadAnimationFromResource(animationId);
                if (animation != null) {
                    KeyframeAnimationPlayer animationPlayer = new KeyframeAnimationPlayer(animation);
                    modifierLayer.setAnimation(animationPlayer);
                    animationStack.addAnimLayer(1000, modifierLayer);
                    
                    // Rastrear a animação ativa
                    trackActiveAnimation(player, animationId, modifierLayer);
                    
                    System.out.println("[TacZ] Successfully started animation: " + animationId);
                } else {
                    System.out.println("[TacZ] Failed to load animation: " + animationId + " - animation not found");
                }
            } catch (Exception e) {
                System.err.println("[TacZ] Error playing animation " + animationId + ": " + e.getMessage());
            }
        }

        public static void playAnimationWithDisplay(AbstractClientPlayer player, GunDisplayInstance display, float limbSwingAmount) {
            try {
                // Extrair nome da animação do display
                String animationName = getAnimationNameFromDisplay(display);
                if (animationName != null && !animationName.isEmpty()) {
                    ResourceLocation animationId = ResourceLocation.fromNamespaceAndPath("tacz", animationName);
                    playAnimation(player, animationId);
                    System.out.println("[TacZ] Playing gun animation: " + animationName + " with limb swing: " + limbSwingAmount);
                }
            } catch (Exception e) {
                System.err.println("[TacZ] Error playing animation with display: " + e.getMessage());
            }
        }

        public static boolean hasThirdPersonAnimation(AbstractClientPlayer player, GunDisplayInstance display) {
            try {
                String animationName = getAnimationNameFromDisplay(display);
                boolean hasAnimation = animationName != null && !animationName.isEmpty();
                System.out.println("[TacZ] Checking 3rd person animation for " + player.getName().getString() + ": " + hasAnimation);
                return hasAnimation;
            } catch (Exception e) {
                System.err.println("[TacZ] Error checking 3rd person animation: " + e.getMessage());
                return false;
            }
        }

        public static void stopAllAnimations(AbstractClientPlayer player) {
            try {
                java.util.UUID playerId = player.getUUID();
                java.util.Map<ResourceLocation, ModifierLayer<KeyframeAnimationPlayer>> playerAnimations = 
                    activeAnimations.get(playerId);
                
                if (playerAnimations != null && !playerAnimations.isEmpty()) {
                    // Parar todas as animações TacZ específicas
                    for (java.util.Map.Entry<ResourceLocation, ModifierLayer<KeyframeAnimationPlayer>> entry : playerAnimations.entrySet()) {
                        ModifierLayer<KeyframeAnimationPlayer> layer = entry.getValue();
                        if (layer != null) {
                            layer.setAnimation(null); // Para a animação
                            // Remover a layer do stack seria ideal, mas não temos referência direta
                        }
                        System.out.println("[TacZ] Stopped animation: " + entry.getKey());
                    }
                    
                    // Limpar o tracking
                    playerAnimations.clear();
                    System.out.println("[TacZ] Stopped all animations for player: " + player.getName().getString());
                } else {
                    System.out.println("[TacZ] No active animations to stop for player: " + player.getName().getString());
                }
            } catch (Exception e) {
                System.err.println("[TacZ] Error stopping animations: " + e.getMessage());
            }
        }

        public static boolean isAnimationPlaying(AbstractClientPlayer player, ResourceLocation animationId) {
            try {
                java.util.UUID playerId = player.getUUID();
                java.util.Map<ResourceLocation, ModifierLayer<KeyframeAnimationPlayer>> playerAnimations = 
                    activeAnimations.get(playerId);
                
                if (playerAnimations != null) {
                    ModifierLayer<KeyframeAnimationPlayer> layer = playerAnimations.get(animationId);
                    boolean isPlaying = layer != null && layer.isActive();
                    System.out.println("[TacZ] Animation " + animationId + " is playing: " + isPlaying);
                    return isPlaying;
                } else {
                    return false;
                }
            } catch (Exception e) {
                System.err.println("[TacZ] Error checking if animation is playing: " + e.getMessage());
                return false;
            }
        }

        @Nullable
        public static Object getCurrentAnimation(AbstractClientPlayer player) {
            try {
                java.util.UUID playerId = player.getUUID();
                java.util.Map<ResourceLocation, ModifierLayer<KeyframeAnimationPlayer>> playerAnimations = 
                    activeAnimations.get(playerId);
                
                if (playerAnimations != null && !playerAnimations.isEmpty()) {
                    // Retornar a primeira animação ativa encontrada
                    for (java.util.Map.Entry<ResourceLocation, ModifierLayer<KeyframeAnimationPlayer>> entry : playerAnimations.entrySet()) {
                        ModifierLayer<KeyframeAnimationPlayer> layer = entry.getValue();
                        if (layer != null && layer.isActive()) {
                            System.out.println("[TacZ] Current animation for player " + player.getName().getString() + ": " + entry.getKey());
                            return layer.getAnimation();
                        }
                    }
                }
                
                System.out.println("[TacZ] No current animation for player: " + player.getName().getString());
                return null;
            } catch (Exception e) {
                System.err.println("[TacZ] Error getting current animation: " + e.getMessage());
                return null;
            }
        }
        
        /**
         * Rastreia uma animação ativa para um jogador
         */
        private static void trackActiveAnimation(AbstractClientPlayer player, ResourceLocation animationId, 
                                               ModifierLayer<KeyframeAnimationPlayer> layer) {
            try {
                java.util.UUID playerId = player.getUUID();
                activeAnimations.computeIfAbsent(playerId, k -> new java.util.concurrent.ConcurrentHashMap<>())
                    .put(animationId, layer);
                System.out.println("[TacZ] Tracking animation: " + animationId + " for player: " + player.getName().getString());
            } catch (Exception e) {
                System.err.println("[TacZ] Error tracking animation: " + e.getMessage());
            }
        }
        
        /**
         * Helper method para extrair nome da animação do GunDisplayInstance
         */
        private static String getAnimationNameFromDisplay(GunDisplayInstance display) {
            try {
                // TODO: Implementar extração do nome da animação do display
                // Por enquanto, retorna um placeholder
                return "default_gun_animation";
            } catch (Exception e) {
                System.err.println("[TacZ] Error getting animation name from display: " + e.getMessage());
                return null;
            }
        }
        
        /**
         * Carrega uma animação KeyframeAnimation a partir de um ResourceLocation
         */
        @Nullable
        private static KeyframeAnimation loadAnimationFromResource(ResourceLocation animationId) {
            try {
                // Usar o sistema de registro de animações do Player Animator
                Object playable = 
                    dev.kosmx.playerAnim.minecraftApi.PlayerAnimationRegistry.getAnimation(animationId);
                
                if (playable instanceof dev.kosmx.playerAnim.core.data.KeyframeAnimation keyframeAnimation) {
                    System.out.println("[TacZ] Successfully loaded animation: " + animationId);
                    return keyframeAnimation;
                } else if (playable != null) {
                    System.err.println("[TacZ] Animation " + animationId + " is not a KeyframeAnimation but: " + playable.getClass().getSimpleName());
                    return null;
                } else {
                    System.out.println("[TacZ] Animation not found in registry: " + animationId);
                    // Tentar carregar do sistema de assets como fallback
                    return loadAnimationFromAssets(animationId);
                }
            } catch (Exception e) {
                System.err.println("[TacZ] Error loading animation from resource " + animationId + ": " + e.getMessage());
                return null;
            }
        }
        
        /**
         * Fallback: carrega animação diretamente dos assets
         */
        @Nullable
        private static KeyframeAnimation loadAnimationFromAssets(ResourceLocation animationId) {
            try {
                // TODO: Implementar carregamento direto dos assets
                // Por enquanto, retorna null - será implementado quando integrarmos com o sistema de assets TacZ
                System.out.println("[TacZ] Attempting to load animation from assets: " + animationId);
                return null;
            } catch (Exception e) {
                System.err.println("[TacZ] Error loading animation from assets " + animationId + ": " + e.getMessage());
                return null;
            }
        }
    }
}