package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * Renderizador funcional para miras telescÃƒÆ’Ã‚Â³picas (scopes)
 * Demonstra funcionalidades especÃƒÆ’Ã‚Â­ficas usando Object Strategy
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
    public void render(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        if (!isEnabled || attachmentIndex == null) {
            return;
        }

        if (!attachmentIndex.isScope()) {
            return;
        }

        /*
        if (transformType instanceof ItemDisplayContext displayContext) {
            if (displayContext.firstPerson()) {
                renderFirstPersonScope(poseStack, vertexBuffer, light, overlay);
            } else {
                renderThirdPersonScope(poseStack, vertexBuffer, light, overlay);
            }
        }
        */

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
     * Define o nÃƒÆ’Ã‚Â­vel de zoom atual
     */
    public static void setZoomLevel(float zoom) {
        zoomLevel = Math.max(1.0f, Math.min(zoom, 10.0f)); // Limitado entre 1x e 10x
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m o nÃƒÆ’Ã‚Â­vel de zoom atual
     */
    public static float getZoomLevel() {
        return zoomLevel;
    }

    /**
     * Verifica se o scope estÃƒÆ’Ã‚Â¡ sendo usado (ativado)
     */
    public boolean isScopeActive() {
        return attachmentIndex != null && attachmentIndex.isScope() && zoomLevel > 1.0f;
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m o FOV efetivo baseado no zoom
     */
    public float getEffectiveFOV() {
        if (attachmentIndex != null && attachmentIndex.isScope()) {
            return attachmentIndex.getFov() / zoomLevel;
        }
        return 70.0f; // FOV padrÃƒÆ’Ã‚Â£o
    }

    /**
     * Verifica se tem zoom mÃƒÆ’Ã‚Âºltiplo disponÃƒÆ’Ã‚Â­vel
     */
    public boolean hasMultipleZoom() {
        return attachmentIndex != null && 
               attachmentIndex.getZoom() != null && 
               attachmentIndex.getZoom().length > 1;
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m prÃƒÆ’Ã‚Â³ximo nÃƒÆ’Ã‚Â­vel de zoom disponÃƒÆ’Ã‚Â­vel
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































































