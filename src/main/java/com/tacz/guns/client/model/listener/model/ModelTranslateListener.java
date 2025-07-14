package com.tacz.guns.client.model.listener.model;

import com.tacz.guns.api.client.animation.AnimationListener;
import com.tacz.guns.api.client.animation.ObjectAnimationChannel;
import com.tacz.guns.client.model.BedrockAnimatedModel;
import com.tacz.guns.client.model.bedrock.ModelRendererWrapper;
import com.tacz.guns.client.resource.pojo.model.BonesItem;

import javax.annotation.Nullable;

public class ModelTranslateListener implements AnimationListener {
    private final ModelRendererWrapper rendererWrapper;
    private final @Nullable BonesItem bonesItem;

    public ModelTranslateListener(BedrockAnimatedModel model, ModelRendererWrapper rendererWrapper, String nodeName) {
        this.rendererWrapper = rendererWrapper;
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚Â node ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã‚Â Ã‚Â¹ nodeÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚ÂÃ‚Â«ÃƒÂ¤Ã‚ÂºÃ…Â½ shouldRender ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ bonesItemÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚Â¾Ã‚Â¿ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã‚Â»Ã‚Â­ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â» offsetÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
        if (model.getShouldRender().contains(rendererWrapper.getModelRenderer())) {
            this.bonesItem = model.getIndexBones().get(nodeName);
        } else {
            this.bonesItem = null;
        }
    }

    @Override
    public void update(float[] values, boolean blend) {
        if (blend) {
            // ÃƒÂ§Ã‚ÂºÃ‚Â¦ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ¦Ã‚Â·Ã‚Â·ÃƒÂ¥Ã‚ÂÃ‹â€ 
            rendererWrapper.addOffsetX(values[0]);
            rendererWrapper.addOffsetY(-values[1]);
            rendererWrapper.addOffsetZ(values[2]);
        } else {
            rendererWrapper.setOffsetX(values[0]);
            rendererWrapper.setOffsetY(-values[1]);
            rendererWrapper.setOffsetZ(values[2]);
        }
    }

    @Override
    public float[] initialValue() {
        // ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã‚Â®Ã‚Â© offset ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¦Ã‚Â¸Ã‚Â¡ÃƒÂ¤Ã‚Â¸Ã‚Âº 0
        float[] recover = new float[3];
        if (bonesItem != null) {
            recover[0] = bonesItem.getPivot().get(0) / 16f;
            recover[1] = -bonesItem.getPivot().get(1) / 16f;
            recover[2] = bonesItem.getPivot().get(2) / 16f;
        } else {
            recover[0] = rendererWrapper.getRotationPointX() / 16f;
            recover[1] = rendererWrapper.getRotationPointY() / 16f;
            recover[2] = rendererWrapper.getRotationPointZ() / 16f;
        }
        return recover;
    }

    @Override
    public ObjectAnimationChannel.ChannelType getType() {
        return ObjectAnimationChannel.ChannelType.TRANSLATION;
    }
}































































