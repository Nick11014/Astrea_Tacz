package com.tacz.guns.client.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.event.BeforeRenderHandEvent;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.client.other.KeepingItemRenderer;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor;
import com.tacz.guns.api.modifier.ParameterizedCachePair;
import com.tacz.guns.client.renderer.item.AnimateGeoItemRenderer;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.config.client.RenderConfig;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.RecoilModifier;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.math.MathUtil;
import com.tacz.guns.util.math.SecondOrderDynamics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.Optional;

@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class CameraSetupEvent {
    /**
     * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚Â¹Ã‚Â³ÃƒÂ¦Ã‚Â»Ã¢â‚¬Ëœ FOV ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œ
     */
    public static final SecondOrderDynamics WORLD_FOV_DYNAMICS = new SecondOrderDynamics(0.5f, 1.2f, 0.5f, 0);
    public static final SecondOrderDynamics ITEM_MODEL_FOV_DYNAMICS = new SecondOrderDynamics(0.5f, 1.2f, 0.5f, 0);
    private static PolynomialSplineFunction pitchSplineFunction;
    private static PolynomialSplineFunction yawSplineFunction;
    private static long shootTimeStamp = -1L;
    private static double xRotO = 0;
    private static double yRotO = 0;

    @SubscribeEvent
    public static void applyLevelCameraAnimation(ViewportEvent.ComputeCameraAngles event) {
        if (!Minecraft.getInstance().options.bobView().get()) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ItemStack stack = KeepingItemRenderer.getRenderer().getCurrentItem();
        // ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â°ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
        if (IClientItemExtensions.of(stack.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            renderer.applyLevelCameraAnimation(event, stack, player);
        }

    }

    @SubscribeEvent
    public static void applyItemInHandCameraAnimation(BeforeRenderHandEvent event) {
        if (!Minecraft.getInstance().options.bobView().get()) {
            return;
        }
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        ItemStack stack = KeepingItemRenderer.getRenderer().getCurrentItem();
        // ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â°ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â¸ÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
        if (IClientItemExtensions.of(stack.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            renderer.applyItemInHandCameraAnimation(event, stack, player);
        }
    }

    @SubscribeEvent
    public static void applyScopeMagnification(ViewportEvent.ComputeFov event) {
        if (!event.usedConfiguredFov()) {
            return; // ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¤Ã‚Â¸Ã¢â‚¬â€œÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ fovÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ fov ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾
        }
        Entity entity = event.getCamera().getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack stack = KeepingItemRenderer.getRenderer().getCurrentItem();
            if (!(stack.getItem() instanceof IGun iGun)) {
                float fov = WORLD_FOV_DYNAMICS.update((float) event.getFOV());
                event.setFOV(fov);
                return;
            }
            float zoom = iGun.getAimingZoom(stack);
            if (livingEntity instanceof LocalPlayer localPlayer) {
                IClientPlayerGunOperator gunOperator = IClientPlayerGunOperator.fromLocalPlayer(localPlayer);
                float aimingProgress = gunOperator.getClientAimingProgress((float) event.getPartialTick());
                float fov = WORLD_FOV_DYNAMICS.update((float) MathUtil.magnificationToFov(1 + (zoom - 1) * aimingProgress, event.getFOV()));
                event.setFOV(fov);
            } else {
                IGunOperator gunOperator = IGunOperator.fromLivingEntity(livingEntity);
                float aimingProgress = gunOperator.getSynAimingProgress();
                float fov = WORLD_FOV_DYNAMICS.update((float) MathUtil.magnificationToFov(1 + (zoom - 1) * aimingProgress, event.getFOV()));
                event.setFOV(fov);
            }
        }
    }

    @SubscribeEvent
    public static void applyGunModelFovModifying(ViewportEvent.ComputeFov event) {
        if (event.usedConfiguredFov()) {
            return; // ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ fovÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã¢â‚¬â€œÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ fov ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾
        }
        Entity entity = event.getCamera().getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack stack = KeepingItemRenderer.getRenderer().getCurrentItem();
            if (!(stack.getItem() instanceof IGun iGun)) {
                float fov = ITEM_MODEL_FOV_DYNAMICS.update((float) event.getFOV());
                event.setFOV(fov);
                return;
            }
            ResourceLocation scopeItemId = iGun.getAttachmentId(stack, AttachmentType.SCOPE);
            if (scopeItemId.equals(DefaultAssets.EMPTY_ATTACHMENT_ID)) {
                scopeItemId = iGun.getBuiltInAttachmentId(stack, AttachmentType.SCOPE);
            }
            CompoundTag scopeTag = iGun.getAttachmentTag(stack, AttachmentType.SCOPE);
            int zoomNumber = AttachmentItemDataAccessor.getZoomNumberFromTag(scopeTag);
            // ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶fovÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â‚¬Â¹Ã‚Â¥ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¨Ã‚ÂºÃ‚Â«fovÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ§Ã‚Â»Ã‚Â´ÃƒÂ¦Ã…â€™Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚ÂÃ‹Å“
            float modifiedFov = TimelessAPI.getClientAttachmentIndex(scopeItemId)
                    .map(index -> {
                        float[] viewsFov = index.getViewsFov();
                        return viewsFov[zoomNumber % viewsFov.length];
                    })
                    .orElse(
                        TimelessAPI.getGunDisplay(stack)
                                .map(GunDisplayInstance::getZoomModelFov)
                                .orElse((float) event.getFOV())
                    );
            if (livingEntity instanceof LocalPlayer localPlayer) {
                IClientPlayerGunOperator gunOperator = IClientPlayerGunOperator.fromLocalPlayer(localPlayer);
                float aimingProgress = gunOperator.getClientAimingProgress((float) event.getPartialTick());
                float fov = ITEM_MODEL_FOV_DYNAMICS.update(Mth.lerp(aimingProgress, (float) event.getFOV(), modifiedFov));
                event.setFOV(fov);
            } else {
                IGunOperator gunOperator = IGunOperator.fromLivingEntity(livingEntity);
                float aimingProgress = gunOperator.getSynAimingProgress();
                float fov = ITEM_MODEL_FOV_DYNAMICS.update(Mth.lerp(aimingProgress, (float) event.getFOV(), modifiedFov));
                event.setFOV(fov);
            }
        }
    }

    @SubscribeEvent
    public static void initialCameraRecoil(GunFireEvent event) {
        if (event.getLogicalSide().isClient()) {
            LivingEntity shooter = event.getShooter();
            LocalPlayer player = Minecraft.getInstance().player;
            if (!shooter.equals(player)) {
                return;
            }
            ItemStack mainHandItem = player.getMainHandItem();
            if (!(mainHandItem.getItem() instanceof IGun iGun)) {
                return;
            }
            AttachmentCacheProperty cacheProperty = IGunOperator.fromLivingEntity(player).getCacheProperty();
            if (cacheProperty == null) {
                return;
            }
            ResourceLocation gunId = iGun.getGunId(mainHandItem);
            Optional<ClientGunIndex> gunIndexOptional = TimelessAPI.getClientGunIndex(gunId);
            if (gunIndexOptional.isEmpty()) {
                return;
            }
            ClientGunIndex gunIndex = gunIndexOptional.get();
            GunData gunData = gunIndex.getGunData();
            // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹
            ParameterizedCachePair<Float, Float> attachmentRecoilModifier = cacheProperty.getCache(RecoilModifier.ID);
            IClientPlayerGunOperator clientPlayerGunOperator = IClientPlayerGunOperator.fromLocalPlayer(player);
            float partialTicks = Minecraft.getInstance().getFrameTime();
            float aimingProgress = clientPlayerGunOperator.getClientAimingProgress(partialTicks);
            float zoom = iGun.getAimingZoom(mainHandItem);
            float aimingRecoilModifier = 1 - aimingProgress + aimingProgress / (float) Math.min(Math.sqrt(zoom), 1.5);
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ¦Ã…â€™Ã¢â‚¬Â° data ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â°Ã¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¤Ã‚Â½Ã…Â½ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ…Â ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
            if (!player.isSwimming() && player.getPose() == Pose.SWIMMING) {
                aimingRecoilModifier = aimingRecoilModifier * gunData.getCrawlRecoilMultiplier();
            }
            pitchSplineFunction = gunData.getRecoil().genPitchSplineFunction((float) attachmentRecoilModifier.left().eval(aimingRecoilModifier));
            yawSplineFunction = gunData.getRecoil().genYawSplineFunction((float) attachmentRecoilModifier.right().eval(aimingRecoilModifier));
            shootTimeStamp = System.currentTimeMillis();
            xRotO = 0;
            yRotO = 0;
        }
    }

    @SubscribeEvent
    public static void applyCameraRecoil(ViewportEvent.ComputeCameraAngles event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        long timeTotal = System.currentTimeMillis() - shootTimeStamp;
        if (pitchSplineFunction != null && pitchSplineFunction.isValidPoint(timeTotal)) {
            double value = pitchSplineFunction.value(timeTotal);
            player.setXRot(player.getXRot() - (float) (value - xRotO));
            xRotO = value;
        }
        if (yawSplineFunction != null && yawSplineFunction.isValidPoint(timeTotal)) {
            double value = yawSplineFunction.value(timeTotal);
            player.setYRot(player.getYRot() - (float) (value - yRotO));
            yRotO = value;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onComputeMovementFov(ComputeFovModifierEvent event) {
        if (!RenderConfig.DISABLE_MOVEMENT_ATTRIBUTE_FOV.get()) return;
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        float f = 1.0f;
        if (player.getMainHandItem().getItem() instanceof AbstractGunItem) {
            if (player.getAbilities().flying) {
                f *= 1.1F;
            }
            event.setNewFovModifier(player.isSprinting() ? 1.15f * f : f);
        }
    }
}































































