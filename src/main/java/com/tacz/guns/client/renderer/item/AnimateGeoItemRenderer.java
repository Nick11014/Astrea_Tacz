package com.tacz.guns.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.tacz.guns.api.client.animation.statemachine.LuaAnimationStateMachine;
import com.tacz.guns.api.client.event.BeforeRenderHandEvent;
import com.tacz.guns.api.client.other.KeepingItemRenderer;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.animation.statemachine.ItemAnimationStateContext;
import com.tacz.guns.client.model.BedrockAnimatedModel;
import com.tacz.guns.client.model.bedrock.BedrockPart;
import com.tacz.guns.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * ÃƒÂ¦Ã…Â Ã‚Â½ÃƒÂ¨Ã‚Â±Ã‚Â¡ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹BEWLRÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚ÂÃ‚Â«ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ§Ã…Â½Ã‚Â°
 * @param <M> ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
 * @param <CTX> ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡
 */
public abstract class AnimateGeoItemRenderer<M extends BedrockAnimatedModel, CTX extends ItemAnimationStateContext>
        extends BlockEntityWithoutLevelRenderer {
    @Nullable
    protected LuaAnimationStateMachine<CTX> stateMachine;
    protected M model;
    public ResourceLocation textureLocation;

    public AnimateGeoItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    public void setModel(M model) {
        this.model = model;
    }

    public M getModel(ItemStack stack) {
        return model;
    }

    @Nullable
    public LuaAnimationStateMachine<CTX> getStateMachine(ItemStack stack) {
        return stateMachine;
    }

    public ResourceLocation getTextureLocation(ItemStack stack) {
        return textureLocation;
    }

    public RenderType getRenderType(ItemStack stack) {
        return RenderType.entityCutout(getTextureLocation(stack));
    }

    public boolean needReInit(ItemStack stack) {
        var stateMachine = getStateMachine(stack);
        if (stateMachine == null) {
            return false;
        }
        return !stateMachine.isInitialized() && stateMachine.getExitingTime() < System.currentTimeMillis();
    }

    public abstract CTX initContext(ItemStack stack, Player player, float partialTick);

    public abstract void updateContext(CTX context, ItemStack stack, Player player, float partialTick);

    /** ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚Âms
     * @return ÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
     */
    public long getPutAwayTime(ItemStack stack) {
        return 0;
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¤Ã‚Â¿Ã‚Â¡ÃƒÂ¥Ã‚ÂÃ‚Â·
     */
    public void tryInit(ItemStack stack, Player player, float partialTick) {
        var stateMachine = getStateMachine(stack);
        if (stateMachine == null) {
            return;
        }
        if (stateMachine.isInitialized()) {
            stateMachine.exit();
        }

        stateMachine.setContext(initContext(stack, player, partialTick));
        stateMachine.initialize();

        stateMachine.trigger(GunAnimationConstant.INPUT_DRAW);
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ©Ã¢â€šÂ¬Ã¢â€šÂ¬ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¤Ã‚Â¿Ã‚Â¡ÃƒÂ¥Ã‚ÂÃ‚Â·
     */
    public void tryExit(ItemStack stack, long putAwayTime) {
        var stateMachine = getStateMachine(stack);
        if (stateMachine == null) {
            return;
        }
        stateMachine.processContextIfExist(context -> {
            context.setPutAwayTime(putAwayTime / 1000F);
        });
        if(stateMachine.isInitialized()) {
            stateMachine.trigger(GunAnimationConstant.INPUT_PUT_AWAY);
            KeepingItemRenderer.getRenderer().keep(stack, putAwayTime);
            stateMachine.exit();
            // ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¯Ã¢â‚¬ÂÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã‚Â¨Ã‚ÂÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã‚ÂÃ‚Â¿ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬Å¾Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚Â¢ÃƒÂ§Ã‚Â²Ã‚Â¾ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
            // ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬tickÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Å¾Ã…Â¸ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
            stateMachine.setExitingTime(putAwayTime + 50);
        }
    }

    /**
     * ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ§Ã‚Â§Ã‚Â»
     * @param input ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¤Ã‚Â¿Ã‚Â¡ÃƒÂ¥Ã‚ÂÃ‚Â·
     */
    public void triggerAnimation(ItemStack stack, String input) {
        var stateMachine = getStateMachine(stack);
        if (stateMachine == null) {
            return;
        }
        stateMachine.trigger(input);
    }

    /**
     * ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
     */
    public void visualUpdate(ItemStack stack) {
        var stateMachine = getStateMachine(stack);
        if (stateMachine == null) {
            return;
        }
        stateMachine.visualUpdate();
    }

    /**
     * ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã¢â‚¬â€œÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶
     */
    public void applyLevelCameraAnimation(ViewportEvent.ComputeCameraAngles event, ItemStack stack, LocalPlayer player) {
        this.applyLevelCameraAnimation(event, stack, 1);
    }

    public void applyLevelCameraAnimation(ViewportEvent.ComputeCameraAngles event, ItemStack stack, float multiplier) {
        var model = getModel(stack);
        if (model == null) {
            return;
        }
        Quaternionf q = MathUtil.multiplyQuaternion(model.getCameraAnimationObject().rotationQuaternion, multiplier);
        double yaw = Math.asin(2 * (q.w() * q.y() - q.x() * q.z()));
        double pitch = Math.atan2(2 * (q.w() * q.x() + q.y() * q.z()), 1 - 2 * (q.x() * q.x() + q.y() * q.y()));
        double roll = Math.atan2(2 * (q.w() * q.z() + q.x() * q.y()), 1 - 2 * (q.y() * q.y() + q.z() * q.z()));
        yaw = Math.toDegrees(yaw);
        pitch = Math.toDegrees(pitch);
        roll = Math.toDegrees(roll);
        event.setYaw((float) yaw + event.getYaw());
        event.setPitch((float) pitch + event.getPitch());
        event.setRoll((float) roll + event.getRoll());
    }

    /**
     * ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶
     */
    public void applyItemInHandCameraAnimation(BeforeRenderHandEvent event, ItemStack stack, LocalPlayer player) {
        applyItemInHandCameraAnimation(event, stack, 1);
    }

    public void applyItemInHandCameraAnimation(BeforeRenderHandEvent event, ItemStack stack, float multiplier) {
        var model = getModel(stack);
        if (model == null) {
            return;
        }
        Quaternionf quaternion = MathUtil.multiplyQuaternion(model.getCameraAnimationObject().rotationQuaternion, multiplier);
        PoseStack poseStack = event.getPoseStack();
        poseStack.mulPose(quaternion);
    }

    /**
     * ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ©Ã‚Â¢Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¦Ã‚ÂÃ‚Â¢
     */
    public void doExtraTransforms(PoseStack poseStack, M model, ItemStack stack) {
        applyFirstPersonPositioningTransform(poseStack, model, stack);
    }

    /**
     * ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¨Ã‚Â§Ã‚Â {@link com.tacz.guns.client.event.FirstPersonRenderEvent}
     */
    public void renderFirstPerson(LocalPlayer player, ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource bufferSource,
                                  int light, float partialTick) {
        M model = getModel(stack);
        if (model != null) {
            poseStack.pushPose();
            float xRotOffset = Mth.lerp(partialTick, player.xBobO, player.xBob);
            float yRotOffset = Mth.lerp(partialTick, player.yBobO, player.yBob);
            float xRot = player.getViewXRot(partialTick) - xRotOffset;
            float yRot = player.getViewYRot(partialTick) - yRotOffset;
            poseStack.mulPose(Axis.XP.rotationDegrees(xRot * -0.1F));
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot * -0.1F));
            BedrockPart rootNode = model.getRootNode();
            if (rootNode != null) {
                xRot = (float) Math.tanh(xRot / 25) * 25;
                yRot = (float) Math.tanh(yRot / 25) * 25;
                rootNode.offsetX += yRot * 0.1F / 16F / 3F;
                rootNode.offsetY += -xRot * 0.1F / 16F / 3F;
                rootNode.additionalQuaternion.mul(Axis.XP.rotationDegrees(xRot * 0.05F));
                rootNode.additionalQuaternion.mul(Axis.YP.rotationDegrees(yRot * 0.05F));
            }

            // ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ (0, 24, 0) ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ (0, 0, 0)
            poseStack.translate(0, 1.5f, 0);
            // ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ©Ã‚Â¢Ã‚Â ÃƒÂ¥Ã¢â€šÂ¬Ã¢â‚¬â„¢ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã‚Â¿Ã‚Â»ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
            poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            doExtraTransforms(poseStack, model, stack);

            var stateMachine = getStateMachine(stack);
            if (stateMachine != null) {
                stateMachine.processContextIfExist(context -> {
                    updateContext(context, stack, player, partialTick);
                });
                stateMachine.update();
            }

            model.render(poseStack, ctx, getRenderType(stack), light, OverlayTexture.NO_OVERLAY);

            // ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¦Ã‚Â¸Ã¢â‚¬Â¦ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¦Ã‚ÂÃ‚Â¢
            model.cleanAnimationTransform();
            poseStack.popPose();
        }
    }

    @ParametersAreNonnullByDefault
    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack poseStack, MultiBufferSource bufferSource,
                             int light, int overlay) {
        if (ctx.firstPerson()) return;
        M model = getModel(stack);
        if (model != null) {
            poseStack.pushPose();
            // ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ (0, 24, 0) ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ (0, 0, 0)
            poseStack.translate(0.5, 1.5f, 0.5);
            // ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ©Ã‚Â¢Ã‚Â ÃƒÂ¥Ã¢â€šÂ¬Ã¢â‚¬â„¢ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã‚Â¿Ã‚Â»ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
            poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
            model.render(poseStack, ctx, RenderType.entityCutout(
                    getTextureLocation(stack)
            ), light, overlay);
            poseStack.popPose();
        }
    }

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ§Ã…Â¸Ã‚Â©ÃƒÂ©Ã‹Å“Ã‚Âµ
     */
    @Nonnull
    public static Matrix4f getPositioningNodeInverse(List<BedrockPart> nodePath) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.identity();
        if (nodePath != null) {
            for (int i = nodePath.size() - 1; i >= 0; i--) {
                BedrockPart part = nodePath.get(i);
                // ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬
                matrix4f.rotate(Axis.XN.rotation(part.xRot));
                matrix4f.rotate(Axis.YN.rotation(part.yRot));
                matrix4f.rotate(Axis.ZN.rotation(part.zRot));
                // ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â§Ã‚Â»
                if (part.getParent() != null) {
                    matrix4f.translate(-part.x / 16.0F, -part.y / 16.0F, -part.z / 16.0F);
                } else {
                    matrix4f.translate(-part.x / 16.0F, (1.5F - part.y / 16.0F), -part.z / 16.0F);
                }
            }
        }
        return matrix4f;
    }

    public static void applyFirstPersonPositioningTransform(PoseStack poseStack, BedrockAnimatedModel model, ItemStack stack) {
        Matrix4f transformMatrix = new Matrix4f();
        transformMatrix.identity();
        // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â½Ã‚Â
        List<BedrockPart> idleNodePath = model.getIdleSightPath();

        Matrix4f idleViewMatrix = getPositioningNodeInverse(idleNodePath);

        // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¦Ã‚ÂÃ‚Â¢
        MathUtil.applyMatrixLerp(transformMatrix, idleViewMatrix, transformMatrix, 1);

        // ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‹â€ Ã‚Â° PoseStack
        poseStack.translate(0, 1.5f, 0);
        poseStack.mulPoseMatrix(transformMatrix);
        poseStack.translate(0, -1.5f, 0);
    }
}































































