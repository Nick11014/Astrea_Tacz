package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.resource.pojo.display.LaserConfig;
import com.tacz.guns.util.LaserColorUtil;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * Renderizador funcional para sistema de laser
 * Implementa funcionalidades de laser usando Object Strategy e ClientAttachmentIndex
 */
public class LaserRender implements IFunctionalRenderer {
    private final BedrockGunModel bedrockGunModel;
    private final ClientAttachmentIndex attachmentIndex;
    private static boolean isEnabled = true;
    private static boolean isLaserActive = false;
    private static float laserRange = 50.0f;
    private static int laserColor = 0xFF0000; // Vermelho por padrão

    public LaserRender(BedrockGunModel bedrockGunModel, ClientAttachmentIndex attachmentIndex) {
        this.bedrockGunModel = bedrockGunModel;
        this.attachmentIndex = attachmentIndex;
        
        // Inicializar cor do laser se disponível
        if (attachmentIndex != null && attachmentIndex.isLaser()) {
            LaserConfig config = attachmentIndex.getLaserConfig();
            if (config != null) {
                laserColor = LaserColorUtil.getDefaultLaserColor(config.getDefaultColor());
            }
        }
    }

    @Override
    public void render(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        if (!isEnabled || !isLaserActive || attachmentIndex == null) {
            return;
        }

        // Só renderiza se for um laser
        if (!attachmentIndex.isLaser()) {
            return;
        }

        // TODO: Implementação de renderização de laser quando tipos estiverem disponíveis
        /*
        if (transformType instanceof ItemDisplayContext displayContext) {
            if (displayContext.firstPerson() || displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
                // Renderizar feixe de laser
                renderLaserBeam(poseStack, vertexBuffer, light, overlay);
                
                // Renderizar ponto de mira se necessário
                if (shouldRenderLaserDot()) {
                    renderLaserDot(poseStack, vertexBuffer, light, overlay);
                }
            }
        }
        */

        // Atualizar estatísticas do laser
        updateLaserStats();
    }

    private void updateLaserStats() {
        if (attachmentIndex != null && attachmentIndex.isLaser()) {
            LaserConfig config = attachmentIndex.getLaserConfig();
            if (config != null) {
                // Atualizar cor se mudou
                int newColor = LaserColorUtil.getDefaultLaserColor(config.getDefaultColor());
                if (LaserColorUtil.isValidLaserColor(newColor)) {
                    laserColor = newColor;
                }
            }
        }
    }

    /**
     * Ativa/desativa o laser
     */
    public static void setLaserActive(boolean active) {
        isLaserActive = active;
    }

    /**
     * Verifica se o laser está ativo
     */
    public static boolean isLaserActive() {
        return isLaserActive;
    }

    /**
     * Define o alcance do laser
     */
    public static void setLaserRange(float range) {
        laserRange = Math.max(1.0f, Math.min(range, 100.0f)); // Entre 1 e 100 blocos
    }

    /**
     * Obtém o alcance atual do laser
     */
    public static float getLaserRange() {
        return laserRange;
    }

    /**
     * Define a cor do laser
     */
    public static void setLaserColor(int color) {
        if (LaserColorUtil.isValidLaserColor(color)) {
            laserColor = color;
        }
    }

    /**
     * Obtém a cor atual do laser
     */
    public static int getLaserColor() {
        return laserColor;
    }

    /**
     * Verifica se deve renderizar o ponto do laser
     */
    public boolean shouldRenderLaserDot() {
        return isLaserActive && attachmentIndex != null && attachmentIndex.isLaser();
    }

    /**
     * Obtém a intensidade do laser baseada na configuração
     */
    public float getLaserIntensity() {
        if (attachmentIndex != null && attachmentIndex.isLaser()) {
            LaserConfig config = attachmentIndex.getLaserConfig();
            if (config != null) {
                // TODO: Obter intensidade da configuração quando disponível
                return 1.0f;
            }
        }
        return 0.5f; // Intensidade padrão
    }

    /**
     * Verifica se o laser tem configuração válida
     */
    public boolean hasValidLaserConfig() {
        return attachmentIndex != null && 
               attachmentIndex.isLaser() && 
               attachmentIndex.getLaserConfig() != null;
    }

    /**
     * Toggle do estado do laser
     */
    public static void toggleLaser() {
        isLaserActive = !isLaserActive;
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
