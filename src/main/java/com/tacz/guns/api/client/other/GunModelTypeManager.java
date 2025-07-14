package com.tacz.guns.api.client.other;

import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.BedrockVersion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class GunModelTypeManager {
    public static final Map<String, BiFunction<BedrockModelPOJO, BedrockVersion, ? extends BedrockGunModel>> GUN_MODEL_TYPE_MAP = new HashMap<>();

    //ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ§Ã‚Â¬Ã‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚Â²ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã‚Â°MapÃƒÂ¤Ã‚Â¸Ã‚Â­
    //ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¦Ã¢â‚¬Å¾Ã‚ÂÃƒÂ¥Ã‚Â¤Ã…Â¡ÃƒÂ§Ã‚ÂºÃ‚Â¿ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨
    public static synchronized void registerModelType(String typeName, BiFunction<BedrockModelPOJO, BedrockVersion, ? extends BedrockGunModel> constructor) {
        GUN_MODEL_TYPE_MAP.put(typeName, constructor);
    }

    //ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ§Ã‚Â¬Ã‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¨Ã‚Â¯Ã‚Â¢ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¦Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
    public static synchronized BiFunction<BedrockModelPOJO, BedrockVersion, ? extends BedrockGunModel> getModelInstanceConstructor(String typeName) {
        return GUN_MODEL_TYPE_MAP.getOrDefault(typeName, BedrockGunModel::new);
    }
}































































