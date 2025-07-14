package com.tacz.guns.resource.manager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
// HABILITADO: Sistema de modificadores agora disponÃƒÆ’Ã‚Â­vel com implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
import com.tacz.guns.api.modifier.JsonProperty;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import com.tacz.guns.resource.network.DataType;
import com.tacz.guns.resource.pojo.data.attachment.AttachmentData;

public class AttachmentDataManager extends CommonDataManager<AttachmentData> {

    public AttachmentDataManager() {
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ CommonAssetsManager.GSON restaurado - serializers customizados funcionais
        super(DataType.ATTACHMENT_DATA, AttachmentData.class, CommonAssetsManager.GSON, "data/attachments", "AttachmentDataLoader");
    }

    @Override
    protected AttachmentData parseJson(JsonElement element) {
        AttachmentData data = getGson().fromJson(element, getDataClass());
        if (data != null) {
            // HABILITADO: LÃƒÆ’Ã‚Â³gica de modificadores restaurada com implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima estratÃƒÆ’Ã‚Â©gica
            // ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬â€ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹
            AttachmentPropertyManager.getModifiers().forEach((key, value) -> {
                String json = getGson().toJson(element);
                if (!element.isJsonObject()) {
                    return;
                }
                JsonObject jsonObject = element.getAsJsonObject();
                
                // TODO: Restaurar quando IAttachmentModifier estiver disponÃƒÆ’Ã‚Â­vel
                // if (jsonObject.has(key)) {
                //     JsonProperty<?> property = value.readJson(json);
                //     property.initComponents();
                //     data.addModifier(key, property);
                // } else if (jsonObject.has(value.getOptionalFields())) {
                //     // ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â§ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚ÂµÃƒÂ¥Ã‚ÂÃ‚Â
                //     JsonProperty<?> property = value.readJson(json);
                //     property.initComponents();
                //     data.addModifier(key, property);
                // }
                
                // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - apenas log do processamento
                if (jsonObject.has(key)) {
                    // System.out.println("Processing modifier: " + key);
                }
            });
        }
        return data;
    }
}































































