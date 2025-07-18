package com.tacz.guns.client.model.functional;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.IFunctionalRenderer;
import com.tacz.guns.client.model.SlotModel;
import com.tacz.guns.client.model.bedrock.BedrockModel;
import com.tacz.guns.client.resource.index.GunDisplayInstance;
import com.tacz.guns.client.resource.pojo.display.gun.MuzzleFlash;
import com.tacz.guns.compat.oculus.OculusCompat;
import com.tacz.guns.resource.modifier.custom.SilenceModifier;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class MuzzleFlashRender implements IFunctionalRenderer {
    private static final SlotModel MUZZLE_FLASH_MODEL = new SlotModel(true);
    /**
     * 50ms ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     */
    private static final long TIME_RANGE = 50;
    public static boolean isSelf = false;
    private static long shootTimeStamp = -1;
    private static boolean muzzleFlashStartMark = false;
    private static float muzzleFlashRandomRotate = 0;
    private static Matrix3f muzzleFlashNormal = new Matrix3f();
    private static Matrix4f muzzleFlashPose = new Matrix4f();

    private final BedrockGunModel bedrockGunModel;

    public MuzzleFlashRender(BedrockGunModel bedrockGunModel) {
        this.bedrockGunModel = bedrockGunModel;
    }

    public static void onShoot() {
        shootTimeStamp = System.currentTimeMillis();
        muzzleFlashStartMark = true;
        muzzleFlashRandomRotate = (float) (Math.random() * 360);
    }

    private static void renderMuzzleFlash(GunDisplayInstance display, PoseStack poseStack, BedrockModel bedrockModel, long time) {
        MuzzleFlash muzzleFlash = display.getMuzzleFlash();
        if (muzzleFlash == null) {
            return;
        }
        if (muzzleFlashStartMark) {
            muzzleFlashNormal = new Matrix3f(poseStack.last().normal());
            muzzleFlashPose = new Matrix4f(poseStack.last().pose());
        }
        bedrockModel.delegateRender((poseStack1, vertexConsumer1, transformType1, light, overlay) -> doRender(light, overlay, muzzleFlash, time));
    }

    private static void doRender(int light, int overlay, MuzzleFlash muzzleFlash, long time) {
        if (muzzleFlashNormal != null && muzzleFlashPose != null) {
            float scale = 0.5f * muzzleFlash.getScale();
            float scaleTime = TIME_RANGE / 2.0f;
            scale = time < scaleTime ? (scale * (time / scaleTime)) : scale;
            muzzleFlashStartMark = false;
            MultiBufferSource multiBufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

            PoseStack poseStack2 = new PoseStack();
            poseStack2.last().normal().mul(muzzleFlashNormal);
            poseStack2.last().pose().mul(muzzleFlashPose);

            poseStack2.pushPose();
            {
                poseStack2.scale(scale, scale, scale);
                poseStack2.mulPose(Axis.ZP.rotationDegrees(muzzleFlashRandomRotate));
                poseStack2.translate(0, -1, 0);
                RenderType renderTypeBg = RenderType.entityTranslucent(muzzleFlash.getTexture());
                MUZZLE_FLASH_MODEL.renderToBuffer(poseStack2, multiBufferSource.getBuffer(renderTypeBg), light, overlay);
            }
            poseStack2.popPose();

            poseStack2.pushPose();
            {
                poseStack2.scale(scale / 2, scale / 2, scale / 2);
                poseStack2.mulPose(Axis.ZP.rotationDegrees(muzzleFlashRandomRotate));
                poseStack2.translate(0, -0.9, 0);
                RenderType renderTypeLight = RenderType.energySwirl(muzzleFlash.getTexture(), 1, 1);
                MUZZLE_FLASH_MODEL.renderToBuffer(poseStack2, multiBufferSource.getBuffer(renderTypeLight), light, overlay);
            }
            poseStack2.popPose();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void render(PoseStack poseStack, VertexConsumer vertexBuffer, ItemDisplayContext transformType, int light, int overlay) {
        if (OculusCompat.isRenderShadow()) {
            return;
        }
        if (!isSelf) {
            return;
        }
        long time = System.currentTimeMillis() - shootTimeStamp;
        if (time > TIME_RANGE) {
            return;
        }
        ItemStack currentGunItem = bedrockGunModel.getCurrentGunItem();

        TimelessAPI.getGunDisplay(currentGunItem).ifPresent(display -> {
            ItemStack muzzleAttachment = bedrockGunModel.getCurrentAttachmentItem().get(AttachmentType.MUZZLE);
            IAttachment iAttachment = IAttachment.getIAttachmentOrNull(muzzleAttachment);
            if (iAttachment != null) {
                ResourceLocation attachmentId = iAttachment.getAttachmentId(muzzleAttachment);
                TimelessAPI.getCommonAttachmentIndex(attachmentId).ifPresent(index -> {
                    var modifier = index.getData().getModifier();
                    if (modifier.containsKey(SilenceModifier.ID) && modifier.get(SilenceModifier.ID).getValue() instanceof Pair<?, ?> pair) {
                        if (((Pair<Integer, Boolean>) pair).right()) {
                            return;
                        }
                    }
                    // renderMuzzleFlash(display, poseStack, bedrockGunModel, time);
                });
            } else {
            // renderMuzzleFlash(display, poseStack, bedrockGunModel, time);
            }
        });
    }
}































































