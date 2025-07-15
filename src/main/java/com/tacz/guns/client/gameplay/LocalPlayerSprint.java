package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ReloadState;
import net.minecraft.client.player.LocalPlayer;

public class LocalPlayerSprint {
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;

    public static boolean stopSprint = false;

    public LocalPlayerSprint(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    /**
     * ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â Ã‚Â²ÃƒÂ¥Ã‹â€ Ã‚ÂºÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã¢â‚¬Â Ã‚Â²ÃƒÂ¥Ã‹â€ Ã‚ÂºÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ©Ã¢â‚¬Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ¤Ã‚Â¸Ã‚Â¥ÃƒÂ¦Ã‚Â Ã‚Â¼ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ§Ã‚Â¬Ã‚Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã‚Â²ÃƒÂ¥Ã‹â€ Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€šÂ¬Ã…â€™ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ©Ã¢â€žÂ¢Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã¢â‚¬Â Ã‚Â²ÃƒÂ¥Ã‹â€ Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
     * @see com.tacz.guns.entity.shooter.LivingEntitySprint#getProcessedSprintStatus
     */
    public boolean getProcessedSprintStatus(boolean sprinting) {
        IGunOperator gunOperator = IGunOperator.fromLivingEntity(player);
        ReloadState.StateType reloadStateType = gunOperator.getSynReloadState().getStateType();
        if (gunOperator.getSynIsAiming() || (reloadStateType.isReloading() && !reloadStateType.isReloadFinishing()) || stopSprint) {
            return false;
        } else {
            return sprinting;
        }
    }
}































































