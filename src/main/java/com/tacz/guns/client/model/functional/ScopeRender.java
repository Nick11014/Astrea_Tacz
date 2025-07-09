package com.tacz.guns.client.model.functional;

import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;

/**
 * Renderizador funcional para miras telescópicas (scopes)
 * Demonstra funcionalidades específicas usando Object Strategy
 */
public class ScopeRender implements IFunctionalRenderer {
    private final BedrockGunModel bedrockGunModel;
    private final ClientAttachmentIndex attachmentIndex;
    private static boolean isEnabled = true;
    private static float zoomLevel = 1.0f;

    public ScopeRender(BedrockGunModel bedrockGunModel, ClientAttachmentIndex attachmentIndex) {
        this.bedrockGunModel = bedrockGunModel;
        this.attachmentIndex = attachmentIndex;
    }

    @Override
    public void render(Object poseStack, Object vertexBuffer, Object transformType, int light, int overlay) {
        if (!isEnabled || attachmentIndex == null) {
            return;
        }

        // Só renderiza se for um scope
        if (!attachmentIndex.isScope()) {
            return;
        }

        // TODO: Implementação de renderização de scope quando tipos estiverem disponíveis
        /*
        if (transformType instanceof ItemDisplayContext displayContext) {
            if (displayContext.firstPerson()) {
                // Renderização de scope em primeira pessoa
                renderFirstPersonScope(poseStack, vertexBuffer, light, overlay);
            } else {
                // Renderização de scope em terceira pessoa
                renderThirdPersonScope(poseStack, vertexBuffer, light, overlay);
            }
        }
        */

        // Log para debugging (temporário)
        logScopeInfo();
    }

    private void logScopeInfo() {
        if (attachmentIndex.isScope()) {
            // System.out.println("Rendering scope: " + attachmentIndex.getName() + 
            //                   ", FOV: " + attachmentIndex.getFov() + 
            //                   ", Zoom: " + (attachmentIndex.getZoom() != null ? java.util.Arrays.toString(attachmentIndex.getZoom()) : "none"));
        }
    }

    /**
     * Define o nível de zoom atual
     */
    public static void setZoomLevel(float zoom) {
        zoomLevel = Math.max(1.0f, Math.min(zoom, 10.0f)); // Limitado entre 1x e 10x
    }

    /**
     * Obtém o nível de zoom atual
     */
    public static float getZoomLevel() {
        return zoomLevel;
    }

    /**
     * Verifica se o scope está sendo usado (ativado)
     */
    public boolean isScopeActive() {
        return attachmentIndex != null && attachmentIndex.isScope() && zoomLevel > 1.0f;
    }

    /**
     * Obtém o FOV efetivo baseado no zoom
     */
    public float getEffectiveFOV() {
        if (attachmentIndex != null && attachmentIndex.isScope()) {
            return attachmentIndex.getFov() / zoomLevel;
        }
        return 70.0f; // FOV padrão
    }

    /**
     * Verifica se tem zoom múltiplo disponível
     */
    public boolean hasMultipleZoom() {
        return attachmentIndex != null && 
               attachmentIndex.getZoom() != null && 
               attachmentIndex.getZoom().length > 1;
    }

    /**
     * Obtém próximo nível de zoom disponível
     */
    public float getNextZoomLevel() {
        if (attachmentIndex != null && attachmentIndex.getZoom() != null) {
            float[] zooms = attachmentIndex.getZoom();
            for (float zoom : zooms) {
                if (zoom > zoomLevel) {
                    return zoom;
                }
            }
            // Se chegou ao final, volta para o primeiro
            return zooms[0];
        }
        return 1.0f;
    }

    public static void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    public ClientAttachmentIndex getAttachmentIndex() {
        return attachmentIndex;
    }
}
