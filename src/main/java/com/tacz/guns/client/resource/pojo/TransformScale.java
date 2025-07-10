package com.tacz.guns.client.resource.pojo;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class TransformScale {
    @SerializedName("thirdperson")
    @Nullable
    private Vector3f thirdPerson;
    @SerializedName("ground")
    @Nullable
    private Vector3f ground;
    @SerializedName("fixed")
    @Nullable
    private Vector3f fixed;

    public void apply(ItemDisplayContext transformType, PoseStack poseStack) {
        Vector3f scale = null;
        if (transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            scale = thirdPerson;
        }
        if (transformType == ItemDisplayContext.GROUND) {
            scale = ground;
        }
        if (transformType == ItemDisplayContext.FIXED) {
            scale = fixed;
        }
        if (scale != null) {
            poseStack.scale(scale.x, scale.y, scale.z);
        }
    }

    public static TransformScale getAmmoDefault() {
        TransformScale transformScale = new TransformScale();
        transformScale.thirdPerson = new Vector3f(0.75f, 0.75f, 0.75f);
        transformScale.ground = new Vector3f(0.75f, 0.75f, 0.75f);
        transformScale.fixed = new Vector3f(1.5f, 1.5f, 1.5f);
        return transformScale;
    }

    public static TransformScale getGunDefault() {
        TransformScale transformScale = new TransformScale();
        transformScale.thirdPerson = new Vector3f(0.6f, 0.6f, 0.6f);
        transformScale.ground = new Vector3f(0.6f, 0.6f, 0.6f);
        transformScale.fixed = new Vector3f(1.2f, 1.2f, 1.2f);
        return transformScale;
    }

    @Nullable
    public Vector3f getThirdPerson() {
        return thirdPerson;
    }

    @Nullable
    public Vector3f getGround() {
        return ground;
    }

    @Nullable
    public Vector3f getFixed() {
        return fixed;
    }
}
