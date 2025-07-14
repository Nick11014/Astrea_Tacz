package com.tacz.guns.api.item.attachment;

import com.google.gson.annotations.SerializedName;

public enum AttachmentType {
    /**
     * ÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â·
     */
    @SerializedName("scope")
    SCOPE,
    /**
     * ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â»Ã‚Â¶
     */
    @SerializedName("muzzle")
    MUZZLE,
    /**
     * ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬Â°Ã‹Å“
     */
    @SerializedName("stock")
    STOCK,
    /**
     * ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã…Â Ã…Â 
     */
    @SerializedName("grip")
    GRIP,
    /**
     * ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¥Ã¢â‚¬Â¦Ã¢â‚¬Â°ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
     */
    @SerializedName("laser")
    LASER,
    /**
     * ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â¤Ã‚Â¹ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¥Ã…â€™Ã‚Â£ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
     */
    @SerializedName("extended_mag")
    EXTENDED_MAG,
    /**
     * ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    NONE
}































































