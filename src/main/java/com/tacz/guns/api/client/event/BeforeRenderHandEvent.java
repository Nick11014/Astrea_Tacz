package com.tacz.guns.api.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.bus.api.Event;

/**
 * Usado para modificar o ItemInHandRenderer#renderHandsWithItems antes de renderizar o item
 * Usado para exibir animações relacionadas
 */
public class BeforeRenderHandEvent extends Event {
    private final PoseStack poseStack;
    private final float partialTick;

    public BeforeRenderHandEvent(PoseStack poseStack, float partialTick) {
        this.poseStack = poseStack;
        this.partialTick = partialTick;
    }    public PoseStack getPoseStack() {
        return poseStack;
    }

    public float getPartialTick() {
        return partialTick;
    }
}































































