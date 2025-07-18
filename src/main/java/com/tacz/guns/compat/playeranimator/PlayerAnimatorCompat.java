package com.tacz.guns.compat.playeranimator;

import com.tacz.guns.client.resource.index.GunDisplayInstance;
import com.tacz.guns.init.CompatRegistry;
import dev.kosmx.playerAnim.api.PlayerAnimatorAPI;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;

import java.util.function.Consumer;

/**
 * Implementação para compatibilidade com PlayerAnimator
 */
public class PlayerAnimatorCompat {
    
    /**
     * Inicializa o PlayerAnimator (se disponível)
     */
    public static void init() {
    }
    
    /**
     * Verifica se o PlayerAnimator está instalado
     */
    public static boolean isInstalled() {
        return ModList.get().isLoaded(CompatRegistry.PLAYER_ANIMATOR);
    }
    
    /**
     * Registra um reload listener para o PlayerAnimator
     */
    public static void registerReloadListener(Consumer<Object> registerFunction) {
    }
    
    /**
     * Verifica se o PlayerAnimator está carregado
     */
    public static boolean isLoaded() {
        return ModList.get().isLoaded(CompatRegistry.PLAYER_ANIMATOR);
    }
    
    /**
     * Plays uma animação de terceira pessoa para o jogador
     */
    public static void playAnimation(Player player, ResourceLocation animationId) {
        PlayerAnimatorAPI.playAnimation(player, animationId);
    }
    
    /**
     * Para todas as animações de um jogador
     */
    public static void stopAllAnimations(Player player) {
        PlayerAnimatorAPI.stopAllAnimations(player);
    }
    
    /**
     * Para todas as animações de um jogador (com parâmetro int)
     */
    public static void stopAllAnimation(LivingEntity entity, int parameter) {
        PlayerAnimatorAPI.stopAllAnimation(entity, parameter);
    }
    
    /**
     * Para todas as animações de um jogador
     */
    public static void stopAllAnimation(LivingEntity entity) {
        PlayerAnimatorAPI.stopAllAnimation(entity);
    }
    
    /**
     * Verifica se uma animação está sendo reproduzida
     */
    public static boolean isAnimationPlaying(Player player, ResourceLocation animationId) {
        return PlayerAnimatorAPI.isAnimationPlaying(player, animationId);
    }
    
    /**
     * Verifica se o PlayerAnimator tem animação de terceira pessoa para uma entidade
     */
    public static boolean hasPlayerAnimator3rd(LivingEntity entity, GunDisplayInstance display) {
        if (display == null) {
            return false;
        }
        return PlayerAnimatorAPI.hasPlayerAnimator3rd(entity, display);
    }
    
    /**
     * Plays uma animação de terceira pessoa para a entidade
     */
    public static void playAnimation(LivingEntity entity, GunDisplayInstance display, float limbSwingAmount) {
        if (display == null) {
            return;
        }
        PlayerAnimatorAPI.playAnimation(entity, display, limbSwingAmount);
    }
    
    /**
     * Configura animação com parâmetros específicos
     */
    public static void playAnimationWithConfig(Player player, ResourceLocation animationId, float speed, boolean loop) {
        PlayerAnimatorAPI.playAnimation(player, animationId, speed, loop);
    }
    
    /**
     * Obtém informações sobre animação atual
     */
    public static Object getCurrentAnimationInfo(Player player) {
        return PlayerAnimatorAPI.getCurrentAnimation(player);
    }
    
    /**
     * Método utilitário para registro de animações
     */
    public static void registerAnimation(ResourceLocation animationId, Object animationData) {
        PlayerAnimatorAPI.registerAnimation(animationId, animationData);
    }

