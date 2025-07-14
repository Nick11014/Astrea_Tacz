package com.tacz.guns.client.resource.index;

import com.google.common.base.Preconditions;
// TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - BedrockAttachmentModel temporariamente como Object
// import com.tacz.guns.client.model.BedrockAttachmentModel;
import com.tacz.guns.client.resource.pojo.skin.attachment.AttachmentSkin;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;

public class ClientAttachmentSkinIndex {
    // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - usando Object temporariamente
    private Object model; // TODO: BedrockAttachmentModel quando disponÃƒÆ’Ã‚Â­vel
    private ResourceLocation texture;
    private String name;

    private ClientAttachmentSkinIndex() {
    }

    public static ClientAttachmentSkinIndex getInstance(AttachmentSkin skinPojo) {
        ClientAttachmentSkinIndex index = new ClientAttachmentSkinIndex();
        checkIndex(skinPojo, index);
        checkName(skinPojo, index);
        checkTextureAndModel(skinPojo, index);
        return index;
    }

    private static void checkIndex(AttachmentSkin skinPojo, ClientAttachmentSkinIndex index) {
        Preconditions.checkArgument(skinPojo != null, "skin index file is empty");
    }

    private static void checkName(AttachmentSkin skinPojo, ClientAttachmentSkinIndex index) {
        index.name = skinPojo.getName();
        if (StringUtils.isBlank(index.name)) {
            index.name = "custom.tacz.error.no_name";
        }
    }

    private static void checkTextureAndModel(AttachmentSkin skinPojo, ClientAttachmentSkinIndex index) {
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ - TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
        ResourceLocation modelLocation = skinPojo.getModel();
        Preconditions.checkArgument(modelLocation != null, "display object missing model field");
        index.model = ClientAttachmentIndex.getOrLoadAttachmentModel(modelLocation);
        // TODO: Quando BedrockAttachmentModel estiver disponÃƒÆ’Ã‚Â­vel, remover este comentÃƒÆ’Ã‚Â¡rio:
        // Preconditions.checkArgument(index.model != null, "there is no model data in the model file");
        
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¨Ã‚Â´Ã‚Â¨
        ResourceLocation textureLocation = skinPojo.getTexture();
        Preconditions.checkArgument(textureLocation != null, "missing default texture");
        index.texture = textureLocation;
    }

    // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - retorna Object temporariamente
    public Object getModel() {
        return model;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }
}































































