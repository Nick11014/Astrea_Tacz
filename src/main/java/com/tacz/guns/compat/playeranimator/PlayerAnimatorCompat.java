package com.tacz.guns.compat.playeranimator;

import com.tacz.guns.client.resource.index.GunDisplayInstance;
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
            // TODO: Inicializar registros de animação quando a dependência estiver resolvida
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
        public static void playAnimation(AbstractClientPlayer player, ResourceLocation animationId) {
            // TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
            // Por enquanto, apenas um stub vazio
        }

        public static void playAnimationWithDisplay(AbstractClientPlayer player, GunDisplayInstance display, float limbSwingAmount) {
            // TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
            // Por enquanto, apenas um stub vazio
        }

        public static boolean hasThirdPersonAnimation(AbstractClientPlayer player, GunDisplayInstance display) {
            // TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
            // Por enquanto, retorna false
            return false;
        }

        public static void stopAllAnimations(AbstractClientPlayer player) {
            // TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
            // Por enquanto, apenas um stub vazio
        }

        public static boolean isAnimationPlaying(AbstractClientPlayer player, ResourceLocation animationId) {
            // TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
            // Por enquanto, retorna false
            return false;
        }

        @Nullable
        public static Object getCurrentAnimation(AbstractClientPlayer player) {
            // TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
            // Por enquanto, retorna null
            return null;
        }
    }
}