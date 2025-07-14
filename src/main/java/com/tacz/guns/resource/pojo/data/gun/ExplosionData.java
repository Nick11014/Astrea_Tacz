package com.tacz.guns.resource.pojo.data.gun;

import com.google.gson.annotations.SerializedName;

public class ExplosionData {
    @SerializedName("explode")
    private boolean explode;

    @SerializedName("radius")
    private float radius;

    @SerializedName("damage")
    private float damage;

    @SerializedName("knockback")
    private boolean knockback;

    @SerializedName("destroy_block")
    private boolean destroyBlock;

    /**
     * ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¨Ã‚Â®Ã‚ÂºÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ§Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ 30 ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸
     */
    @SerializedName("delay")
    private float delay;

    public ExplosionData(boolean explode, float radius, float damage, boolean knockback, float delay, boolean destroyBlock) {
        this.explode = explode;
        this.radius = radius;
        this.damage = damage;
        this.knockback = knockback;
        this.delay = delay;
        this.destroyBlock = destroyBlock;
    }

    public boolean isExplode() {
        return explode;
    }

    public float getRadius() {
        return radius;
    }

    public float getDamage() {
        return damage;
    }

    public boolean isKnockback() {
        return knockback;
    }

    public boolean isDestroyBlock() {
        return destroyBlock;
    }

    public float getDelay() {
        return delay;
    }
}































































