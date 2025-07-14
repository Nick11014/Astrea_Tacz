package com.tacz.guns.resource.pojo.data.gun;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.util.HitboxHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public enum InaccuracyType {
    /**
     * ÃƒÂ§Ã‚Â«Ã¢â€žÂ¢ÃƒÂ§Ã‚Â«Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â¨
     */
    @SerializedName("stand")
    STAND,
    /**
     * ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ¥Ã…Â Ã‚Â¨
     */
    @SerializedName("move")
    MOVE,
    /**
     * ÃƒÂ¦Ã‚Â½Ã…â€œÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œ FPS ÃƒÂ¦Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ…Â ÃƒÂ¨Ã‚Â¹Ã‚Â²
     */
    @SerializedName("sneak")
    SNEAK,
    /**
     * ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹
     */
    @SerializedName("lie")
    LIE,
    /**
     * ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
     */
    @SerializedName("aim")
    AIM;

    /**
     * ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
     *
     * @param livingEntity ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹
     * @return ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚Âµ
     */
    public static InaccuracyType getInaccuracyType(LivingEntity livingEntity) {
        float aimingProgress = IGunOperator.fromLivingEntity(livingEntity).getSynAimingProgress();
        // ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¤Ã‚Â¼Ã‹Å“ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ§Ã‚ÂºÃ‚Â§ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ©Ã‚Â«Ã‹Å“
        if (aimingProgress == 1.0f) {
            return InaccuracyType.AIM;
        }
        // MOJANG ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¥Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¦Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â§Ã‚Â¿ÃƒÂ¥Ã…Â Ã‚Â¿ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¦Ã‹Å“Ã‚Â¯ SWIMMING
        if (!livingEntity.isSwimming() && livingEntity.getPose() == Pose.SWIMMING) {
            return InaccuracyType.LIE;
        }
        if (livingEntity.getPose() == Pose.CROUCHING) {
            return InaccuracyType.SNEAK;
        }
        if (isMove(livingEntity)) {
            return InaccuracyType.MOVE;
        }
        return InaccuracyType.STAND;
    }

    public static Map<InaccuracyType, Float> getDefaultInaccuracy() {
        Map<InaccuracyType, Float> inaccuracy = Maps.newHashMap();
        inaccuracy.put(InaccuracyType.STAND, 5f);
        inaccuracy.put(InaccuracyType.MOVE, 5.75f);
        inaccuracy.put(InaccuracyType.SNEAK, 3.5f);
        inaccuracy.put(InaccuracyType.LIE, 2.5f);
        inaccuracy.put(InaccuracyType.AIM, 0.15f);
        return inaccuracy;
    }

    private static boolean isMove(LivingEntity livingEntity) {
        double distance = Math.abs(livingEntity.walkDist - livingEntity.walkDistO);
        if (livingEntity instanceof Player player) {
            distance = HitboxHelper.getPlayerVelocity(player).length();
        }
        return distance > 0.05f;
    }

    public boolean isAim() {
        return this == AIM;
    }
}































































