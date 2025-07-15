package com.tacz.guns.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.client.model.BedrockAmmoModel;
import com.tacz.guns.client.model.SlotModel;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.client.resource.pojo.TransformScale;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import java.util.List;

import static net.minecraft.world.item.ItemDisplayContext.GUI;

/**
 * Renderizador de itens de muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o para NeoForge 1.21.1
 * MantÃƒÆ’Ã‚Â©m a heranÃƒÆ’Ã‚Â§a de BlockEntityWithoutLevelRenderer por compatibilidade
 */
public class AmmoItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final SlotModel SLOT_AMMO_MODEL = new SlotModel();

    public AmmoItemRenderer(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }

    private static void applyPositioningNodeTransform(List<BedrockPart> nodePath, PoseStack poseStack, Vector3f scale) {
        if (nodePath == null) {
            return;
        }
        if (scale == null) {
            scale = new Vector3f(1, 1, 1);
        }
        // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â¿Ã†â€™
        poseStack.translate(0, 1.5, 0);
        for (int i = nodePath.size() - 1; i >= 0; i--) {
            BedrockPart t = nodePath.get(i);
            poseStack.mulPose(Axis.XN.rotation(t.xRot));
            poseStack.mulPose(Axis.YN.rotation(t.yRot));
            poseStack.mulPose(Axis.ZN.rotation(t.zRot));
            if (t.getParent() != null) {
                poseStack.translate(-t.x * scale.x() / 16.0F, -t.y * scale.y() / 16.0F, -t.z * scale.z() / 16.0F);
            } else {
                poseStack.translate(-t.x * scale.x() / 16.0F, (1.5F - t.y / 16.0F) * scale.y(), -t.z * scale.z() / 16.0F);
            }
        }
        poseStack.translate(0, -1.5, 0);
    }

    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext transformType, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (!(stack.getItem() instanceof IAmmo iAmmo)) {
            return;
        }
        ResourceLocation ammoId = iAmmo.getAmmoId(stack);
        poseStack.pushPose();
        TimelessAPI.getClientAmmoIndex(ammoId).ifPresentOrElse(ammoIndex -> {
            // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ 3D ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â»Ã…Â¸ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ GUI ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
            BedrockAmmoModel ammoModel = ammoIndex.getAmmoModel();
            ResourceLocation modelTexture = ammoIndex.getModelTextureLocation();
            // GUI ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã‚Â®Ã…Â ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
            if (transformType == GUI || ammoModel == null || modelTexture == null) {
                poseStack.translate(0.5, 1.5, 0.5);
                poseStack.mulPose(Axis.ZN.rotationDegrees(180));
                VertexConsumer buffer = pBuffer.getBuffer(RenderType.entityTranslucent(ammoIndex.getSlotTextureLocation()));
                SLOT_AMMO_MODEL.renderToBuffer(poseStack, buffer, pPackedLight, pPackedOverlay);
                return;
            }
            // ÃƒÂ¥Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
            // ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹
            poseStack.translate(0.5, 2, 0.5);
            // ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
            poseStack.scale(-1, -1, 1);
            // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â¬ÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
            applyPositioningTransform(transformType, ammoIndex.getTransform().getScale(), ammoModel, poseStack);
            // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ display ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾
            applyScaleTransform(transformType, ammoIndex.getTransform().getScale(), poseStack);
            // ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ¢â‚¬â„¢ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
            RenderType renderType = RenderType.entityCutout(modelTexture);
            ammoModel.render(poseStack, transformType, renderType, pPackedLight, pPackedOverlay);
        }, () -> {
            // ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚Âª ammoIDÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ©Ã¢â‚¬ÂÃ¢â€žÂ¢ÃƒÂ¨Ã‚Â¯Ã‚Â¯ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¨Ã‚Â´Ã‚Â¨ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ©Ã¢â‚¬Â Ã¢â‚¬â„¢ÃƒÂ¥Ã‹â€ Ã‚Â«ÃƒÂ¤Ã‚ÂºÃ‚Âº
            poseStack.translate(0.5, 1.5, 0.5);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            VertexConsumer buffer = pBuffer.getBuffer(RenderType.entityTranslucent(MissingTextureAtlasSprite.getLocation()));
            SLOT_AMMO_MODEL.renderToBuffer(poseStack, buffer, pPackedLight, pPackedOverlay);
        });
        poseStack.popPose();
    }

    private void applyPositioningTransform(ItemDisplayContext transformType, TransformScale scale, BedrockAmmoModel model, PoseStack poseStack) {
        switch (transformType) {
            case FIXED -> applyPositioningNodeTransform(model.getFixedOriginPath(), poseStack, scale.getFixed());
            case GROUND -> applyPositioningNodeTransform(model.getGroundOriginPath(), poseStack, scale.getGround());
            case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND ->
                    applyPositioningNodeTransform(model.getThirdPersonHandOriginPath(), poseStack, scale.getThirdPerson());
        }
    }

    private void applyScaleTransform(ItemDisplayContext transformType, TransformScale scale, PoseStack poseStack) {
        if (scale == null) {
            return;
        }
        Vector3f vector3f = null;
        switch (transformType) {
            case FIXED -> vector3f = scale.getFixed();
            case GROUND -> vector3f = scale.getGround();
            case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND -> vector3f = scale.getThirdPerson();
        }
        if (vector3f != null) {
            poseStack.translate(0, 1.5, 0);
            poseStack.scale(vector3f.x(), vector3f.y(), vector3f.z());
            poseStack.translate(0, -1.5, 0);
        }
    }
}































































