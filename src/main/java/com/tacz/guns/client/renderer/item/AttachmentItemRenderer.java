package com.tacz.guns.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.client.model.SlotModel;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.util.RenderDistance;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class AttachmentItemRenderer extends BlockEntityWithoutLevelRenderer {
    public static final SlotModel SLOT_ATTACHMENT_MODEL = new SlotModel();

    public AttachmentItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext transformType, @Nonnull PoseStack poseStack,
                             @Nonnull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (!(stack.getItem() instanceof IAttachment iAttachment)) {
            return;
        }

        ResourceLocation attachmentId = iAttachment.getAttachmentId(stack);
        TimelessAPI.getClientAttachmentIndex(attachmentId).ifPresentOrElse(index -> {
            if (RenderDistance.isTooFar(transformType)) {
                renderLOD((ClientAttachmentIndex) index, poseStack, pBuffer, pPackedLight, pPackedOverlay);
            } else {
                renderAttachment((ClientAttachmentIndex) index, transformType, poseStack, pBuffer, pPackedLight, pPackedOverlay);
            }
        }, () -> renderMissingAttachment(transformType, poseStack, pBuffer, pPackedLight, pPackedOverlay));
    }

    private void renderLOD(ClientAttachmentIndex index, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        // Renderiza o modelo de baixa qualidade (LOD)
        index.getLodModel().ifPresent(model -> {
            poseStack.pushPose();
            poseStack.scale(0.5f, 0.5f, 0.5f);
            poseStack.translate(0.5, 0.5, 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            VertexConsumer buffer = pBuffer.getBuffer(RenderType.entityTranslucent(model.getTextureLocation()));
            model.render(poseStack, buffer, pPackedLight, pPackedOverlay, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        });
    }

    private void renderAttachment(ClientAttachmentIndex index, ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        // Renderiza o modelo principal do acessÃƒÆ’Ã‚Â³rio
        index.getDisplay().ifPresent(display -> {
            poseStack.pushPose();
            display.getTransform().apply(transformType, poseStack);
            // TODO: A renderizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o do modelo serÃƒÆ’Ã‚Â¡ reimplementada
            // display.getModel().render(poseStack, transformType, pBuffer, pPackedLight, pPackedOverlay);
            poseStack.popPose();
        });
    }

    private void renderMissingAttachment(ItemDisplayContext transformType, PoseStack poseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        // Renderiza um modelo de fallback se o acessÃƒÆ’Ã‚Â³rio nÃƒÆ’Ã‚Â£o for encontrado
        poseStack.pushPose();
        // TODO: A renderizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o do modelo serÃƒÆ’Ã‚Â¡ reimplementada
        // SLOT_ATTACHMENT_MODEL.render(poseStack, transformType, pBuffer, pPackedLight, pPackedOverlay);
        poseStack.popPose();
    }
}































































