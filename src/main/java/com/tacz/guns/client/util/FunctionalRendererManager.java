package com.tacz.guns.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.model.functional.*;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.model.BedrockGunModel;
import net.minecraft.world.item.ItemDisplayContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerenciador de renderizadores funcionais
 * Centraliza a criaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o e gerenciamento de renderizadores usando Object Strategy
 */
public class FunctionalRendererManager {
    private static final Map<String, IFunctionalRenderer> REGISTERED_RENDERERS = new ConcurrentHashMap<>();
    private static boolean isEnabled = true;

    /**
     * Registra um renderizador funcional
     */
    public static void registerRenderer(String name, IFunctionalRenderer renderer) {
        if (name != null && renderer != null) {
            REGISTERED_RENDERERS.put(name, renderer);
        }
    }

    /**
     * Remove um renderizador funcional
     */
    public static void unregisterRenderer(String name) {
        REGISTERED_RENDERERS.remove(name);
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m um renderizador registrado
     */
    public static IFunctionalRenderer getRenderer(String name) {
        return REGISTERED_RENDERERS.get(name);
    }

    /**
     * Cria renderizador de laser para um acessÃƒÆ’Ã‚Â³rio
     */
    public static LaserRender createLaserRenderer(BedrockGunModel gunModel, ClientAttachmentIndex attachmentIndex) {
        if (attachmentIndex != null && attachmentIndex.isLaser()) {
            return new LaserRender(gunModel, attachmentIndex);
        }
        return null;
    }

    /**
     * Cria renderizador de scope para um acessÃƒÆ’Ã‚Â³rio
     */
    public static ScopeRender createScopeRenderer(BedrockGunModel gunModel, ClientAttachmentIndex attachmentIndex) {
        if (attachmentIndex != null && attachmentIndex.isScope()) {
            return new ScopeRender(gunModel, attachmentIndex);
        }
        return null;
    }

    /**
     * Cria renderizador apropriado baseado no tipo de acessÃƒÆ’Ã‚Â³rio
     */
    public static IFunctionalRenderer createAttachmentRenderer(BedrockGunModel gunModel, ClientAttachmentIndex attachmentIndex) {
        if (attachmentIndex == null) {
            return null;
        }

        // Criar renderizador especÃƒÆ’Ã‚Â­fico baseado no tipo
        if (attachmentIndex.isLaser()) {
            return createLaserRenderer(gunModel, attachmentIndex);
        }
        
        if (attachmentIndex.isScope()) {
            return createScopeRenderer(gunModel, attachmentIndex);
        }

        // Renderizador genÃƒÆ’Ã‚Â©rico para outros tipos
        return new ExampleRender(gunModel);
    }

    /**
     * Cria AttachmentRender para tipo especÃƒÆ’Ã‚Â­fico
     * HABILITADO: AttachmentRender agora estÃƒÆ’Ã‚Â¡ funcional
     */
    public static IFunctionalRenderer createAttachmentRenderForType(BedrockGunModel gunModel, Object attachmentType) {
        if (gunModel == null || attachmentType == null) {
            return null;
        }

        // AttachmentRender agora estÃƒÆ’Ã‚Â¡ disponÃƒÆ’Ã‚Â­vel e funcional
        return new AttachmentRender(gunModel, (com.tacz.guns.api.item.attachment.AttachmentType) attachmentType);
    }

    /**
     * Renderiza todos os renderizadores registrados
     */
    public static void renderAll(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        if (!isEnabled) {
            return;
        }

        for (IFunctionalRenderer renderer : REGISTERED_RENDERERS.values()) {
            try {
                renderer.render(poseStack, vertexBuffer, transformType, light, overlay);
            } catch (Exception e) {
                // Log error mas continua renderizando outros
                System.err.println("Error rendering functional renderer: " + e.getMessage());
            }
        }
    }

    /**
     * Limpa todos os renderizadores registrados
     */
    public static void clearAll() {
        REGISTERED_RENDERERS.clear();
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m estatÃƒÆ’Ã‚Â­sticas dos renderizadores
     */
    public static Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total_renderers", REGISTERED_RENDERERS.size());
        stats.put("enabled", isEnabled);
        
        // Contar tipos de renderizadores
        int laserCount = 0;
        int scopeCount = 0;
        int exampleCount = 0;
        int otherCount = 0;

        for (IFunctionalRenderer renderer : REGISTERED_RENDERERS.values()) {
            if (renderer instanceof LaserRender) {
                laserCount++;
            } else if (renderer instanceof ScopeRender) {
                scopeCount++;
            } else if (renderer instanceof ExampleRender) {
                exampleCount++;
            } else {
                otherCount++;
            }
        }

        stats.put("laser_renderers", laserCount);
        stats.put("scope_renderers", scopeCount);
        stats.put("example_renderers", exampleCount);
        stats.put("other_renderers", otherCount);

        return stats;
    }

    /**
     * Habilita/desabilita o sistema de renderizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o funcional
     */
    public static void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Verifica se o sistema estÃƒÆ’Ã‚Â¡ habilitado
     */
    public static boolean isEnabled() {
        return isEnabled;
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m lista de nomes dos renderizadores registrados
     */
    public static String[] getRegisteredRendererNames() {
        return REGISTERED_RENDERERS.keySet().toArray(new String[0]);
    }

    /**
     * Verifica se um renderizador especÃƒÆ’Ã‚Â­fico estÃƒÆ’Ã‚Â¡ registrado
     */
    public static boolean hasRenderer(String name) {
        return REGISTERED_RENDERERS.containsKey(name);
    }
}































































