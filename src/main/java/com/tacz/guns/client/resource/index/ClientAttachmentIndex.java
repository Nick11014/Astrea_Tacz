package com.tacz.guns.client.resource.index;

import com.tacz.guns.client.model.BedrockAttachmentModel;
import com.tacz.guns.client.resource.ClientAssetsManager;
import com.tacz.guns.client.resource.pojo.display.attachment.AttachmentDisplay;
import com.tacz.guns.client.resource.pojo.display.attachment.AttachmentLod;
import com.tacz.guns.client.resource.pojo.display.gun.TextShow;
import com.tacz.guns.client.resource.pojo.display.LaserConfig;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.BedrockVersion;
import com.tacz.guns.resource.pojo.AttachmentIndexPOJO;
import com.tacz.guns.resource.pojo.data.attachment.AttachmentData;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * ImplementaÃ§Ã£o mÃ­nima estratÃ©gica para ClientAttachmentIndex
 */
public class ClientAttachmentIndex {
    private BedrockAttachmentModel model;
    private ResourceLocation texture;
    private String name;
    private AttachmentData data;
    private AttachmentDisplay display;
    private ResourceLocation slotTextureLocation;
    private String adapterNodeName;
    private boolean showMuzzle = false;
    private Map<String, TextShow> textShows;
    private float[] zoom;
    private int[] views;
    private boolean isScope = false;
    private boolean isSight = false;
    private float fov = 70f;
    private float[] viewsFov;
    private AttachmentLod attachmentLod;
    private Map<String, ResourceLocation> sounds;
    private LaserConfig laserConfig;

    private ClientAttachmentIndex() {
    }

    /**
     * ImplementaÃ§Ã£o mÃ­nima - apenas estrutura bÃ¡sica
     */
    public static ClientAttachmentIndex getInstance(ResourceLocation attachmentId, AttachmentIndexPOJO indexPojo) {
        ClientAttachmentIndex index = new ClientAttachmentIndex();
        
        index.name = indexPojo.getName();
        if (index.name == null || index.name.trim().isEmpty()) {
            index.name = "custom.tacz.error.no_name";
        }
        
        index.data = ClientAssetsManager.INSTANCE.getAttachmentData(indexPojo.getData());
        index.display = ClientAssetsManager.INSTANCE.getAttachmentDisplay(indexPojo.getDisplay());
        index.model = createAttachmentModel(index.display);
        index.texture = index.display.getModelTexture();
        index.slotTextureLocation = index.display.getSlotTextureLocation();
        index.adapterNodeName = index.display.getAdapterNodeName();
        index.showMuzzle = index.display.isShowMuzzle();
        index.textShows = index.display.getTextShows();
        index.zoom = index.display.getZoom();
        index.views = index.display.getViews();
        index.isScope = index.display.isScope();
        index.isSight = index.display.isSight();
        index.fov = index.display.getFov();
        index.viewsFov = index.display.getViewsFov();
        index.attachmentLod = index.display.getAttachmentLod();
        index.sounds = index.display.getSounds();
        index.laserConfig = index.display.getLaserConfig();
        index.tooltipKey = indexPojo.getTooltip();
        
        return index;
    }

    public String getName() {
        return name;
    }

    public BedrockAttachmentModel getModel() {
        return model;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public AttachmentData getData() {
        return data;
    }

    public Optional<AttachmentDisplay> getDisplay() {
        return Optional.ofNullable(display);
    }

    public ResourceLocation getSlotTextureLocation() {
        return slotTextureLocation;
    }

    @Nullable
    public String getAdapterNodeName() {
        return adapterNodeName;
    }

    public boolean isShowMuzzle() {
        return showMuzzle;
    }

    public Map<String, TextShow> getTextShows() {
        return textShows;
    }

    @Nullable
    public float[] getZoom() {
        return zoom;
    }

    @Nullable
    public int[] getViews() {
        return views;
    }

    public boolean isScope() {
        return isScope;
    }

    public boolean isSight() {
        return isSight;
    }

    public float getFov() {
        return fov;
    }

    @Nullable
    public float[] getViewsFov() {
        return viewsFov;
    }

    @Nullable
    public AttachmentLod getAttachmentLod() {
        return attachmentLod;
    }

    public Optional<AttachmentLod> getLodModel() {
        return Optional.ofNullable(attachmentLod);
    }

    @Nullable
    public Map<String, ResourceLocation> getSounds() {
        return sounds;
    }
    
    public Optional<Map<String, ResourceLocation>> getSoundsOptional() {
        return Optional.ofNullable(sounds);
    }

    public LaserConfig getLaserConfig() {
        return laserConfig;
    }

    @Nullable
    public String getTooltipKey() {
        return null;
    }

    
    /**
     * Retorna o modelo de acessÃ³rio - agora compatÃ­vel com BedrockAttachmentModel
     */
    public BedrockAttachmentModel getAttachmentModel() {
        return model;
    }
    
    /**
     * Retorna a textura do modelo como ResourceLocation  
     */
    public ResourceLocation getModelTexture() {
        return texture;
    }
    
    /**
     * Retorna a textura do slot como ResourceLocation
     */
    public ResourceLocation getSlotTexture() {
        return slotTextureLocation;
    }
    
    /**
     * Cria uma instÃ¢ncia de BedrockAttachmentModel bÃ¡sica se necessÃ¡rio
     */
    private static BedrockAttachmentModel createAttachmentModel(AttachmentDisplay display) {
        ResourceLocation modelLocation = display.getModelLocation();
        if (modelLocation == null) {
            return null;
        }
        BedrockModelPOJO modelPOJO = ClientAssetsManager.INSTANCE.getBedrockModelPOJO(modelLocation);
        if (modelPOJO == null) {
            return null;
        }
        if (BedrockVersion.isLegacyVersion(modelPOJO) && modelPOJO.getGeometryModelLegacy() != null) {
            return new BedrockAttachmentModel(modelPOJO, BedrockVersion.LEGACY);
        }
        if (BedrockVersion.isNewVersion(modelPOJO) && modelPOJO.getGeometryModelNew() != null) {
            return new BedrockAttachmentModel(modelPOJO, BedrockVersion.NEW);
        }
        return null;
    }


    /**
     * Verifica se este acessÃ³rio tem modelo disponÃ­vel
     */
    public boolean hasModel() {
        return model != null;
    }

    /**
     * Verifica se este acessÃ³rio tem textura disponÃ­vel
     */
    public boolean hasTexture() {
        return texture != null;
    }

    /**
     * Verifica se este acessÃ³rio suporta sistema LOD
     */
    public boolean hasLodSupport() {
        return attachmentLod != null;
    }

    /**
     * Verifica se este acessÃ³rio Ã© um laser
     */
    public boolean isLaser() {
        return laserConfig != null;
    }

    /**
     * Verifica se este acessÃ³rio tem configuraÃ§Ãµes de som
     */
    public boolean hasSounds() {
        return sounds != null && !sounds.isEmpty();
    }

    /**
     * Retorna o tipo de acessÃ³rio baseado nas configuraÃ§Ãµes
     * TODO: Expandir quando AttachmentType estiver disponÃ­vel
     */
    public String getAttachmentType() {
        if (isScope()) return "scope";
        if (isSight()) return "sight";
        if (isLaser()) return "laser";
        if (showMuzzle) return "muzzle";
        return "unknown";
    }

    /**
     * Verifica se o acessÃ³rio deve ser renderizado baseado nas configuraÃ§Ãµes
     */
    public boolean shouldRender() {
        return hasModel() && hasTexture();
    }

    /**
     * MÃ©todo estÃ¡tico placeholder para carregar modelo de acessÃ³rio
     * TODO: Implementar corretamente quando BedrockAttachmentModel estiver disponÃ­vel
     */
    public static BedrockAttachmentModel getOrLoadAttachmentModel(ResourceLocation modelLocation) {
        BedrockModelPOJO modelPOJO = ClientAssetsManager.INSTANCE.getBedrockModelPOJO(modelLocation);
        if (modelPOJO == null) {
            return null;
        }
        if (BedrockVersion.isLegacyVersion(modelPOJO) && modelPOJO.getGeometryModelLegacy() != null) {
            return new BedrockAttachmentModel(modelPOJO, BedrockVersion.LEGACY);
        }
        if (BedrockVersion.isNewVersion(modelPOJO) && modelPOJO.getGeometryModelNew() != null) {
            return new BedrockAttachmentModel(modelPOJO, BedrockVersion.NEW);
        }
        return null;
    }
}






























































