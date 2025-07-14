package com.tacz.guns.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.config.client.ZoomConfig;
import com.tacz.guns.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @WrapOperation(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    public void reduceSensitivity(LocalPlayer player, double yaw, double pitch, Operation<Void> original) {
        ItemStack mainHandItem = player.getMainHandItem();
        IGun iGun = IGun.getIGunOrNull(mainHandItem);
        if (iGun == null) {
            original.call(player, yaw, pitch);
            return;
        }
        ResourceLocation scopeId = iGun.getAttachmentId(mainHandItem, AttachmentType.SCOPE);
        if (scopeId.equals(DefaultAssets.EMPTY_ATTACHMENT_ID)) {
            scopeId = iGun.getBuiltInAttachmentId(mainHandItem, AttachmentType.SCOPE);
        }
        float zoomLevel = 1;
        if (DefaultAssets.isEmptyAttachmentId(scopeId)) {
            // ÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã…Â½Ã¢â‚¬Â¡
            zoomLevel = TimelessAPI.getGunDisplay(mainHandItem).map(GunDisplayInstance::getIronZoom).orElse(1f);
        } else {
            Optional<ClientAttachmentIndex> optional = TimelessAPI.getClientAttachmentIndex(scopeId);
            if (optional.isPresent()) {
                float[] zoom = optional.get().getZoom();
                if (zoom != null && zoom.length > 0) {
                    CompoundTag attachmentTag = iGun.getAttachmentTag(mainHandItem, AttachmentType.SCOPE);
                    zoomLevel = zoom[AttachmentItemDataAccessor.getZoomNumberFromTag(attachmentTag) % zoom.length];
                }
            }
        }
        Minecraft minecraft = Minecraft.getInstance();
        float progress = IGunOperator.fromLivingEntity(player).getSynAimingProgress();
        // ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ©Ã¢â‚¬Â¢Ã…â€œÃƒÂ§Ã‚ÂÃ‚ÂµÃƒÂ¦Ã¢â‚¬Â¢Ã‚ÂÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        double sensitivityMultiplier = ZoomConfig.ZOOM_SENSITIVITY_BASE_MULTIPLIER.get();
        sensitivityMultiplier = 1 + (sensitivityMultiplier - 1) * progress;
        // ÃƒÂ¤Ã‚Â¸Ã‚Â¤ÃƒÂ§Ã‚Â§Ã‚ÂÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ fov ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€
        double originalFov = minecraft.options.fov().get();
        double currentFov = MathUtil.magnificationToFov(1 + (zoomLevel - 1) * progress, originalFov);
        // ÃƒÂ¨Ã‚ÂÃ‚Â§ÃƒÂ¥Ã‚Â¹Ã¢â‚¬Â¢ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™MC ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ COD ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â Ã‚Â·ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ MDV ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚Âº MDV133ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¤Ã‚Â¸Ã‚Âº 1.33ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
        double coefficient = ZoomConfig.SCREEN_DISTANCE_COEFFICIENT.get();
        double denominator = MathUtil.zoomSensitivityRatio(currentFov, originalFov, coefficient) * sensitivityMultiplier;
        // ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ§Ã‚Â»Ã‹â€ ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã…Â¾Ã…â€œ
        double finalYaw = yaw * denominator;
        double finalPitch = getCrawlPitch(player, pitch, denominator);
        original.call(player, finalYaw, finalPitch);
    }

    @Unique
    private static double getCrawlPitch(LocalPlayer player, double pitch, double denominator) {
        double finalPitch = pitch * denominator;
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ pitch ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´
        if (!player.isSwimming() && player.getPose() == Pose.SWIMMING) {
            // ÃƒÂ¤Ã‚Â»Ã‚Â°ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
            float playerPitch = -player.getXRot();
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¤Ã‚Â»Ã‚Â°ÃƒÂ¨Ã‚Â¶Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ 25 ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¤Ã‚Â¸Ã…Â 
            if (playerPitch > 45) {
                finalPitch = Math.max(finalPitch, 0);
            }
            // ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â¿Ã‚Â¯ÃƒÂ¨Ã‚Â¶Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ 25 ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹
            if (playerPitch < -30) {
                finalPitch = Math.min(finalPitch, 0);
            }
        }
        return finalPitch;
    }
}































































