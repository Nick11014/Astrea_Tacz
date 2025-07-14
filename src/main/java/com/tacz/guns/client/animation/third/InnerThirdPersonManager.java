package com.tacz.guns.client.animation.third;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.other.ThirdPersonManager;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.compat.playeranimator.PlayerAnimatorCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;

public class InnerThirdPersonManager {
    public static void setRotationAnglesHead(LivingEntity entityIn, ModelPart rightArm, ModelPart leftArm, ModelPart body, ModelPart head, float limbSwingAmount) {
        // ÃƒÂ¦Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¥Ã‚ÂÃ…â€œÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¼Ã…Â¡ StackOverflow
        if (Minecraft.getInstance().isPaused()) {
            return;
        }
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player == entityIn) {
            if (Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
                PlayerAnimatorCompat.stopAllAnimation(entityIn, 0);
                return;
            }
        }
        if (entityIn instanceof IGunOperator operator) {
            ItemStack mainHandItem = entityIn.getMainHandItem();
            IGun iGun = IGun.getIGunOrNull(mainHandItem);
            if (iGun == null) {
                PlayerAnimatorCompat.stopAllAnimation(entityIn);
                return;
            }
            // ÃƒÂ§Ã‚ÂÃ‚Â¡ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â°ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã‹â€ Ã‚Â¬ÃƒÂ¦Ã‚Â¢Ã‚Â¯ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã‚Â³Ã‚Â³ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã…Â¾Ã‹Å“ÃƒÂ§Ã‚Â¿Ã¢â‚¬Â¦ÃƒÂ©Ã‚Â£Ã…Â¾ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
            if (entityIn.getPose() == Pose.SLEEPING || entityIn.onClimbable() || entityIn.isSwimming() || entityIn.getPose() == Pose.FALL_FLYING) {
                PlayerAnimatorCompat.stopAllAnimation(entityIn);
                return;
            }

            TimelessAPI.getGunDisplay(mainHandItem).ifPresent(display -> {
                if (PlayerAnimatorCompat.hasPlayerAnimator3rd(entityIn, display.getDisplayInstance())) {
                    PlayerAnimatorCompat.playAnimation(entityIn, display.getDisplayInstance(), limbSwingAmount);
                } else {
                    playVanillaAnimation(entityIn, rightArm, leftArm, body, head, operator, display);
                }
            });
        }
    }

    private static void playVanillaAnimation(LivingEntity entityIn, ModelPart rightArm, ModelPart leftArm, ModelPart body, ModelPart head, IGunOperator operator, ClientGunIndex gunIndex) {
        // TODO: [MIGRAÇÃO] Adaptar para usar ClientGunIndex ao invés de GunDisplayInstance
        // Por enquanto, verificamos se getDisplayInstance() retorna algo válido
        GunDisplayInstance display = gunIndex.getDisplayInstance();
        if (display == null) {
            // Fallback para quando GunDisplayInstance não estiver disponível
            return;
        }
        
        String animation = display.getThirdPersonAnimation();
        float aimingProgress = operator.getSynAimingProgress();
        if (aimingProgress <= 0) {
            ThirdPersonManager.getAnimation(animation).animateGunHold(entityIn, rightArm, leftArm, body, head);
        } else {
            ThirdPersonManager.getAnimation(animation).animateGunAim(entityIn, rightArm, leftArm, body, head, aimingProgress);
        }
    }
}































































