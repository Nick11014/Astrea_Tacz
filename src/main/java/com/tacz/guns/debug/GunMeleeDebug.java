package com.tacz.guns.debug;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GunMeleeDebug {
    public static void showRange(LivingEntity user, int distance, Vec3 centrePos, Vec3 eyeVec, float rangeAngle) {
        if (!(user.level() instanceof ServerLevel serverLevel)) {
            return;
        }
        // ÃƒÂ¨Ã‚ÂµÃ‚Â·ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        int half = distance / 2;
        Vec3 startPos = user.getEyePosition().subtract(half, half, half);
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â¯Ã¢â‚¬Â¢ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚Â distance^3 ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        for (int i = 0; i < distance * 2; i++) {
            for (int j = 0; j < distance * 2; j++) {
                for (int k = 0; k < distance * 2; k++) {
                    // ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
                    Vec3 tmpPos = startPos.add(i / 2.0, j / 2.0, k / 2.0);
                    // ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡->ÃƒÂ§Ã‚ÂÃ†â€™ÃƒÂ¥Ã‚Â¿Ã†â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
                    Vec3 targetVec = tmpPos.subtract(centrePos);
                    // ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã‚ÂÃ†â€™ÃƒÂ¥Ã‚Â¿Ã†â€™ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»
                    double targetLength = targetVec.length();
                    // ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¥Ã¢â€šÂ¬Ã‚ÂÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚Â
                    if (targetLength < distance) {
                        continue;
                    }
                    // ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢
                    double degree = Math.toDegrees(Math.acos(targetVec.dot(eyeVec) / (targetLength * distance)));
                    // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¨Ã…â€™Ã†â€™ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‚Â
                    if (degree < (rangeAngle / 2)) {
                        serverLevel.sendParticles(ParticleTypes.FLAME, tmpPos.x, tmpPos.y, tmpPos.z, 1, 0, 0, 0, 0);
                    }
                }
            }
        }
    }
}































































