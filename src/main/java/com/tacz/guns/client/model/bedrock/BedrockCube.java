package com.tacz.guns.client.model.bedrock;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.item.ItemDisplayContext;

public interface BedrockCube {
    void compile(PoseStack.Pose pose, VertexConsumer consumer, ItemDisplayContext transformType, int light, int overlay, float red, float green, float blue, float alpha);
}































































