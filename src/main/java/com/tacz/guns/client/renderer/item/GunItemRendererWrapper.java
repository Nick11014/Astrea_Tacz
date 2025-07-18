package com.tacz.guns.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.statemachine.LuaAnimationStateMachine;
import com.tacz.guns.api.client.event.BeforeRenderHandEvent;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.client.other.KeepingItemRenderer;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.animation.screen.RefitTransform;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.animation.statemachine.GunAnimationStateContext;
import com.tacz.guns.client.event.CameraSetupEvent;
import com.tacz.guns.client.event.FirstPersonRenderGunEvent;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.model.SlotModel;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.client.model.functional.MuzzleFlashRender;
import com.tacz.guns.client.model.functional.ShellRender;
import com.tacz.guns.client.resource.index.GunDisplayInstance;
import com.tacz.guns.client.resource.pojo.TransformScale;
import com.tacz.guns.util.RenderDistance;
import com.tacz.guns.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static net.minecraft.world.item.ItemDisplayContext.*;

/**
 * ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¨Ã‚Â´Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ©Ã‚Â¢Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã‚Â§Ã‚Â {@link com.tacz.guns.client.event.FirstPersonRenderGunEvent}
 */
public class GunItemRendererWrapper extends AnimateGeoItemRenderer<BedrockGunModel, GunAnimationStateContext> {
    private static final SlotModel SLOT_GUN_MODEL = new SlotModel();
    private static BedrockGunModel lastModel = null;
    public static final Vector3f muzzleRenderOffset = new Vector3f();

    public GunItemRendererWrapper() {
        super();
    }

    @Override
    public GunAnimationStateContext initContext(ItemStack stack, Player player, float partialTick) {
        GunAnimationStateContext context = new GunAnimationStateContext();
        this.updateContext(context, stack, player, partialTick);
        return context;
    }

    @Override
    public void updateContext(GunAnimationStateContext context, ItemStack stack, Player player, float partialTick) {
        context.setPartialTicks(partialTick);
        context.setCurrentGunItem(stack);
    }

    @Override
    public void tryInit(ItemStack stack, Player player, float partialTick) {
        super.tryInit(stack, player, partialTick);
    }

    @Override
    public void tryExit(ItemStack stack, long putAwayTime) {
        var stateMachine = getStateMachine(stack);
        if (stateMachine == null) {
            return;
        }
        stateMachine.processContextIfExist(context -> {
            context.setPutAwayTime(putAwayTime / 1000F);
            context.setCurrentGunItem(stack);
        });
        if(stateMachine.isInitialized()) {
            stateMachine.trigger(GunAnimationConstant.INPUT_PUT_AWAY);
            KeepingItemRenderer.getRenderer().keep(stack, putAwayTime);
            stateMachine.exit();
            stateMachine.setExitingTime(putAwayTime + 50);
        }
    }

    @Override
    public long getPutAwayTime(ItemStack stack) {
        if (stack.getItem() instanceof IGun iGun) {
            return TimelessAPI.getCommonGunIndex(iGun.getGunId(stack))
                    .map(index -> (long) (index.getGunData().getPutAwayTime() * 1000L))
                    .orElse(0L);
        }
        return 0;
    }

    @Nullable
    @Override
    public LuaAnimationStateMachine<GunAnimationStateContext> getStateMachine(ItemStack stack) {
        return TimelessAPI.getGunDisplay(stack)
                .map(clientGunIndex -> (LuaAnimationStateMachine<GunAnimationStateContext>) clientGunIndex.getAnimationStateMachine())
                .orElse(null);
    }

    @Override
    public BedrockGunModel getModel(ItemStack stack) {
        return TimelessAPI.getGunDisplay(stack).map(display -> display.getGunModel()).orElse(null);
    }

    @Override
    public ResourceLocation getTextureLocation(ItemStack stack) {
        return TimelessAPI.getGunDisplay(stack).map(display -> display.getModelTexture()).orElse(null);
    }

    @Override
    public void applyLevelCameraAnimation(ViewportEvent.ComputeCameraAngles event, ItemStack stack, LocalPlayer player) {
        if (!(stack.getItem() instanceof IGun iGun)) {
            return;
        }
        Optional.ofNullable(getModel(stack)).ifPresent(model -> {
            if (lastModel != model) {
                model.cleanCameraAnimationTransform();
                lastModel = model;
            }
            IClientPlayerGunOperator clientPlayerGunOperator = IClientPlayerGunOperator.fromLocalPlayer(player);
            float partialTicks = (float) event.getPartialTick();
            float aimingProgress = clientPlayerGunOperator.getClientAimingProgress(partialTicks);
            float zoom = iGun.getAimingZoom(stack);
            float multiplier = 1 - aimingProgress + aimingProgress / (float) Math.sqrt(zoom);
            this.applyLevelCameraAnimation(event, stack, multiplier);
        });
    }

    @Override
    public void applyItemInHandCameraAnimation(BeforeRenderHandEvent event, ItemStack stack, LocalPlayer player) {
        if (!(stack.getItem() instanceof IGun iGun)) {
            return;
        }
        Optional.ofNullable(getModel(stack)).ifPresent(model -> {
            PoseStack poseStack = event.getPoseStack();
            IClientPlayerGunOperator clientPlayerGunOperator = IClientPlayerGunOperator.fromLocalPlayer(player);
            float partialTicks = (float) event.getPartialTick();
            float aimingProgress = clientPlayerGunOperator.getClientAimingProgress(partialTicks);
            float zoom = iGun.getAimingZoom(stack);
            float multiplier = 1 - aimingProgress + aimingProgress / (float) Math.sqrt(zoom);
            Quaternionf quaternion = MathUtil.multiplyQuaternion(model.getCameraAnimationObject().rotationQuaternion, multiplier);
            poseStack.mulPose(quaternion);
            model.cleanCameraAnimationTransform();
        });
    }

    @Override
    public void renderFirstPerson(LocalPlayer player, ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource bufferSource,
                                  int light, float partialTick) {
        if (!(stack.getItem() instanceof IGun)) {
            return;
        }

        TimelessAPI.getGunDisplay(stack).ifPresent(display -> {
            BedrockGunModel gunModel = display.getGunModel();
            var animationStateMachine = (LuaAnimationStateMachine<GunAnimationStateContext>) display.getAnimationStateMachine();
            if (gunModel == null) {
                return;
            }

            animationStateMachine.processContextIfExist(context -> {
                updateContext(context, stack, player, partialTick);
            });
            animationStateMachine.update();

            poseStack.pushPose();
            float xRotOffset = Mth.lerp(partialTick, player.xBobO, player.xBob);
            float yRotOffset = Mth.lerp(partialTick, player.yBobO, player.yBob);
            float xRot = player.getViewXRot(partialTick) - xRotOffset;
            float yRot = player.getViewYRot(partialTick) - yRotOffset;
            poseStack.mulPose(Axis.XP.rotationDegrees(xRot * -0.1F));
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot * -0.1F));
            BedrockPart rootNode = gunModel.getRootNode();
            if (rootNode != null) {
                xRot = (float) Math.tanh(xRot / 25) * 25;
                yRot = (float) Math.tanh(yRot / 25) * 25;
                rootNode.offsetX += yRot * 0.1F / 16F / 3F;
                rootNode.offsetY += -xRot * 0.1F / 16F / 3F;
                rootNode.additionalQuaternion.mul(Axis.XP.rotationDegrees(xRot * 0.05F));
                rootNode.additionalQuaternion.mul(Axis.YP.rotationDegrees(yRot * 0.05F));
            }
            poseStack.translate(0, 1.5f, 0);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            FirstPersonRenderGunEvent.applyFirstPersonGunTransform(player, stack, poseStack, gunModel, partialTick);

            MuzzleFlashRender.isSelf = true;
            ShellRender.isSelf = true;
            boolean renderHand = gunModel.getRenderHand();
            if (RefitTransform.getOpeningProgress() != 0) {
                gunModel.setRenderHand(false);
            }
            RenderType renderType = RenderType.entityCutout(display.getModelTexture());
            gunModel.render(poseStack, stack, ctx, renderType, light, OverlayTexture.NO_OVERLAY);
            cacheMuzzlePosition(poseStack, gunModel);
            gunModel.setRenderHand(renderHand);
            poseStack.popPose();
            gunModel.cleanAnimationTransform();
            MuzzleFlashRender.isSelf = false;
            ShellRender.isSelf = false;
        });
    }

    private static void cacheMuzzlePosition(PoseStack poseStack, BedrockGunModel gunModel) {
        if (gunModel.getMuzzleFlashPosPath() != null) {
            poseStack.pushPose();
            for (BedrockPart bedrockPart : gunModel.getMuzzleFlashPosPath()) {
                bedrockPart.translateAndRotateAndScale(poseStack);
            }
            Matrix4f pose = poseStack.last().pose();
            double itemRenderFov = CameraSetupEvent.ITEM_MODEL_FOV_DYNAMICS.get();
            double levelRenderFov = CameraSetupEvent.WORLD_FOV_DYNAMICS.get();
            poseStack.popPose();
            muzzleRenderOffset.set(
                    pose.m30(),
                    pose.m31(),
                    pose.m32() * Math.tan(itemRenderFov / 2 * Math.PI / 180) / Math.tan(levelRenderFov / 2 * Math.PI / 180)
            );
        }
    }


    @Override
    public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext transformType, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource pBuffer,
                             int pPackedLight, int pPackedOverlay) {
        if (!(stack.getItem() instanceof IGun)) {
            return;
        }
        poseStack.pushPose();
        TimelessAPI.getGunDisplay(stack).ifPresentOrElse(gunIndex -> {
            if (transformType == FIRST_PERSON_LEFT_HAND || transformType == FIRST_PERSON_RIGHT_HAND) {
                return;
            }
            if (transformType == THIRD_PERSON_LEFT_HAND) {
                return;
            }
            if (transformType == GUI) {
                poseStack.translate(0.5, 1.5, 0.5);
                poseStack.mulPose(Axis.ZN.rotationDegrees(180));
                VertexConsumer buffer = pBuffer.getBuffer(RenderType.entityTranslucent(gunIndex.getSlotTexture()));
                SLOT_GUN_MODEL.renderToBuffer(poseStack, buffer, pPackedLight, pPackedOverlay);
                return;
            }
            BedrockGunModel gunModel;
            ResourceLocation gunTexture;
            Pair<BedrockGunModel, ResourceLocation> lodModel = gunIndex.getLodModel();
            if (lodModel == null || RenderDistance.inRenderHighPolyModelDistance(poseStack)) {
                gunModel = gunIndex.getGunModel();
                gunTexture = gunIndex.getModelTexture();
            } else {
                gunModel = lodModel.getLeft();
                gunTexture = lodModel.getRight();
            }
            poseStack.translate(0.5, 2, 0.5);
            poseStack.scale(-1, -1, 1);
            TransformScale scale = gunIndex.getScale();
            applyPositioningTransform(transformType, scale, gunModel, poseStack);
            applyScaleTransform(transformType, scale, poseStack);
            RenderType renderType = RenderType.entityCutout(gunTexture);
            gunModel.render(poseStack, stack, transformType, renderType, pPackedLight, pPackedOverlay);
        }, () -> {
            poseStack.translate(0.5, 1.5, 0.5);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
            VertexConsumer buffer = pBuffer.getBuffer(RenderType.entityTranslucent(MissingTextureAtlasSprite.getLocation()));
            SLOT_GUN_MODEL.renderToBuffer(poseStack, buffer, pPackedLight, pPackedOverlay);
        });
        poseStack.popPose();
    }

    private static void applyPositioningTransform(ItemDisplayContext transformType, TransformScale scale, BedrockGunModel model,
                                                  PoseStack poseStack) {
        switch (transformType) {
            case FIXED -> applyPositioningNodeTransform(model.getFixedOriginPath(), poseStack, scale.getFixed());
            case GROUND -> applyPositioningNodeTransform(model.getGroundOriginPath(), poseStack, scale.getGround());
            case THIRD_PERSON_RIGHT_HAND, THIRD_PERSON_LEFT_HAND -> applyPositioningNodeTransform(model.getThirdPersonHandOriginPath(), poseStack, scale.getThirdPerson());
        }
    }

    private static void applyScaleTransform(ItemDisplayContext transformType, TransformScale scale, PoseStack poseStack) {
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

    private static void applyPositioningNodeTransform(List<BedrockPart> nodePath, PoseStack poseStack, Vector3f scale) {
        if (nodePath == null) {
            return;
        }
        if (scale == null) {
            scale = new Vector3f(1, 1, 1);
        }
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
}































































