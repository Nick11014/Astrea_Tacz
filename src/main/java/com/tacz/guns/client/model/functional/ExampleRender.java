package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.IFunctionalRenderer;
import net.minecraft.world.item.ItemDisplayContext;

/**
 * Renderizador funcional de exemplo demonstrando Object Strategy
 * Este ÃƒÆ’Ã‚Â© um exemplo de como criar novos renderizadores usando a interface estabilizada
 */
public class ExampleRender implements IFunctionalRenderer {
    private final BedrockGunModel bedrockGunModel;
    private static boolean isEnabled = true;

    public ExampleRender(BedrockGunModel bedrockGunModel) {
        this.bedrockGunModel = bedrockGunModel;
    }

    @Override
    public void render(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        if (!isEnabled) {
            return;
        }
        
        
        /*
        if (transformType instanceof ItemDisplayContext displayContext) {
            if (displayContext.firstPerson()) {
            } else {
            }
        }
        
        if (poseStack instanceof PoseStack stack) {
            stack.pushPose();
            stack.popPose();
        }
        */
    }

    public static void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public static boolean isEnabled() {
        return isEnabled;
    }
}































































