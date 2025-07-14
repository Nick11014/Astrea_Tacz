package com.tacz.guns.resource.pojo.data.gun;

import com.google.gson.annotations.SerializedName;

public enum Bolt {
    /**
     * ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
     */
    @SerializedName("open_bolt")
    OPEN_BOLT,
    /**
     * ÃƒÂ©Ã¢â‚¬â€Ã‚Â­ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»
     */
    @SerializedName("closed_bolt")
    CLOSED_BOLT,
    /**
     * ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¨Ã¢â‚¬Â Ã¢â‚¬Âº
     */
    @SerializedName("manual_action")
    MANUAL_ACTION
}































































