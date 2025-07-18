package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.client.resource.pojo.display.LaserConfig;
import com.tacz.guns.util.LaserColorUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

/**
 * ImplementaÃ§Ã£o mÃ­nima estratÃ©gica para BeamRenderer
 * TODO: Expandir quando ItemStack, PoseStack, ItemDisplayContext, LaserConfig e outras dependÃªncias estiverem disponÃ­veis
 */
public class BeamRenderer {
    
    public static void renderLaserBeam(ItemStack stack, PoseStack poseStack, ItemDisplayContext transformType, @Nonnull List<BedrockPart> path) {
        if (stack == null || !transformType.firstPerson() && !(transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)) {
            return;
        }
        
        LaserConfig laserConfig = getLaserConfig(stack);
        if (laserConfig == null) {
            return;
        }

        int color = LaserColorUtil.getLaserColor(stack, laserConfig);

        for (BedrockPart bedrockPart : path) {
            poseStack.pushPose();
            bedrockPart.translateAndRotateAndScale(poseStack);
            Matrix4f matrix4f = poseStack.last().pose();
            VertexConsumer buffer = MultiBufferSource.immediate(null).getBuffer(RenderType.solid()); // TODO: Get actual buffer
            // TODO: Render beam using matrix4f, buffer, color, etc.
            poseStack.popPose();
        }
    }

    private static LaserConfig getLaserConfig(ItemStack stack) {
        IAttachment iAttachment = IAttachment.getIAttachmentOrNull(stack);
        if (iAttachment == null) {
            return null;
        }
        return TimelessAPI.getClientAttachmentIndex(iAttachment.getAttachmentId(stack))
                .map(index -> index.getLaserConfig())
                .orElse(null);
    }

    /**
     * MÃ©todo utilitÃ¡rio para validar parÃ¢metros de feixe laser
     */
    public static boolean isValidLaserBeam(ItemStack stack, List<BedrockPart> path) {
        return stack != null && path != null && !path.isEmpty();
    }

    /**
     * MÃ©todo utilitÃ¡rio para obter intensidade do laser baseado na distÃ¢ncia
     */
    public static float getLaserIntensity(float distance) {
        return Math.max(0.1f, 1.0f - (distance / 100.0f));
    }

    /**
     * MÃ©todo utilitÃ¡rio para calcular cor com fade baseado na distÃ¢ncia
     */
    public static int getLaserColorWithFade(int baseColor, float distance) {
        float intensity = getLaserIntensity(distance);
        int r = (int) (((baseColor >> 16) & 0xFF) * intensity);
        int g = (int) (((baseColor >> 8) & 0xFF) * intensity);
        int b = (int) ((baseColor & 0xFF) * intensity);
        return (r << 16) | (g << 8) | b;
    }
}































































