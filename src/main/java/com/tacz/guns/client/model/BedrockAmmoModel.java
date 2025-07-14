package com.tacz.guns.client.model;

import com.tacz.guns.client.model.bedrock.BedrockModel;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.BedrockVersion;

import javax.annotation.Nullable;
import java.util.List;

public class BedrockAmmoModel extends BedrockModel {
    private static final String FIXED_ORIGIN_NODE = "fixed";
    private static final String GROUND_ORIGIN_NODE = "ground";
    private static final String THIRD_PERSON_HAND_ORIGIN_NODE = "thirdperson_hand";

    // ÃƒÂ¥Ã‚Â±Ã¢â‚¬Â¢ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> fixedOriginPath;
    // ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> groundOriginPath;
    // ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚Â¯ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Å¾
    protected @Nullable List<BedrockPart> thirdPersonHandOriginPath;

    public BedrockAmmoModel(BedrockModelPOJO pojo, BedrockVersion version) {
        super(pojo, version);
        fixedOriginPath = getPath(modelMap.get(FIXED_ORIGIN_NODE));
        groundOriginPath = getPath(modelMap.get(GROUND_ORIGIN_NODE));
        thirdPersonHandOriginPath = getPath(modelMap.get(THIRD_PERSON_HAND_ORIGIN_NODE));
    }

    @Nullable
    public List<BedrockPart> getFixedOriginPath() {
        return fixedOriginPath;
    }

    @Nullable
    public List<BedrockPart> getGroundOriginPath() {
        return groundOriginPath;
    }

    @Nullable
    public List<BedrockPart> getThirdPersonHandOriginPath() {
        return thirdPersonHandOriginPath;
    }
}































































