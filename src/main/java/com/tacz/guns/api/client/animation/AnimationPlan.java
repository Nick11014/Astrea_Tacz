package com.tacz.guns.api.client.animation;

public class AnimationPlan {
    public String animationName;
    // TODO: Re-enable when ObjectAnimation is habilitado
    // public ObjectAnimation.PlayType playType;
    public Object playType; // Temporary placeholder
    public float transitionTimeS;

    public AnimationPlan(String animationName, Object playType, float transitionTimeS) {
        this.animationName = animationName;
        this.playType = playType;
        this.transitionTimeS = transitionTimeS;
    }
}
