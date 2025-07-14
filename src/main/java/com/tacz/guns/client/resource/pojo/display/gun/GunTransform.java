package com.tacz.guns.client.resource.pojo.display.gun;

import com.google.gson.annotations.SerializedName;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.client.resource.pojo.TransformScale;
import net.minecraft.world.item.ItemDisplayContext;

public class GunTransform {
    @SerializedName("scale")
    private TransformScale scale;

    public void apply(ItemDisplayContext transformType, PoseStack poseStack) {
        if (scale != null) {
            scale.apply(transformType, poseStack);
        }
    }

    public static GunTransform getDefault() {
        GunTransform gunTransform = new GunTransform();
        gunTransform.scale = TransformScale.getGunDefault();
        return gunTransform;
    }

    public TransformScale getScale() {
        return scale;
    }
}































































