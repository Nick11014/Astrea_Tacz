package com.tacz.guns.client.renderer.other;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;

public class GunHurtBobTweak {
    private static long hurtByGunTimeStamp = -1L;
    private static float lastTweakMultiplier = 0.05f;

    public static boolean onHurtBobTweak(LocalPlayer player, PoseStack matrixStack, float partialTicks) {
        // ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿ÃƒÂ¦Ã‹Å“Ã‚Â¯ 500 msÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¤Ã‚ÂºÃ…Â½ 500 msÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¨Ã‚Â¯Ã‚Â´ÃƒÂ¦Ã‹Å“Ã…Â½ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â 
        if (System.currentTimeMillis() - hurtByGunTimeStamp > 500) {
            // ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ falseÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â®Ã‚Â©ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™ÃƒÂ¥Ã…Â Ã‚Â¨
            return false;
        }
        float zRot = (float) player.hurtTime - partialTicks;
        if (zRot < 0) {
            return true;
        }
        zRot /= (float) player.hurtDuration;
        zRot = Mth.sin(zRot * zRot * zRot * zRot * (float) Math.PI);
        float yRot = player.getHurtDir();

        yRot = yRot * lastTweakMultiplier;
        zRot = zRot * lastTweakMultiplier;

        matrixStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        matrixStack.mulPose(Axis.XP.rotationDegrees(-zRot * 14.0F));
        matrixStack.mulPose(Axis.YP.rotationDegrees(yRot));
        return true;
    }

    public static void markTimestamp(float tweakMultiplier) {
        hurtByGunTimeStamp = System.currentTimeMillis();
        lastTweakMultiplier = tweakMultiplier;
    }
}































































