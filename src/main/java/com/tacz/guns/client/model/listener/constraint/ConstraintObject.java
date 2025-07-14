package com.tacz.guns.client.model.listener.constraint;

import com.tacz.guns.api.client.animation.AnimationListener;
import com.tacz.guns.api.client.animation.AnimationListenerSupplier;
import com.tacz.guns.api.client.animation.ObjectAnimationChannel;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.client.resource.pojo.model.BonesItem;
import org.joml.Vector3f;

import javax.annotation.Nullable;

import static com.tacz.guns.client.model.BedrockAnimatedModel.CONSTRAINT_NODE;

public class ConstraintObject implements AnimationListenerSupplier {
    public Vector3f translationConstraint = new Vector3f(0, 0, 0);
    public Vector3f rotationConstraint = new Vector3f(0, 0, 0);
    /**
     * ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã…Â Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™nodeÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‚Â©Ã‚Âº
     */
    public BedrockPart node;
    /**
     * ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã…Â Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™bonesItemÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‚Â©Ã‚Âº
     */
    public BonesItem bonesItem;

    @Nullable
    @Override
    public AnimationListener supplyListeners(String nodeName, ObjectAnimationChannel.ChannelType type) {
        if (!nodeName.equals(CONSTRAINT_NODE)) {
            return null;
        }
        if (type.equals(ObjectAnimationChannel.ChannelType.ROTATION)) {
            return new ConstraintRotateListener(this);
        }
        if (type.equals(ObjectAnimationChannel.ChannelType.TRANSLATION)) {
            return new ConstraintTranslateListener(this);
        }
        return null;
    }
}































































