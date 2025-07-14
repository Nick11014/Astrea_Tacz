package com.tacz.guns.client.resource.pojo.display.ammo;

import com.google.gson.annotations.SerializedName;
import net.minecraft.core.particles.ParticleOptions;
import org.joml.Vector3f;

import javax.annotation.Nullable;

public class AmmoParticle {
    private static final Vector3f ZERO = new Vector3f(0, 0, 0);
    @SerializedName("name")
    private String name;

    @SerializedName("delta")
    private Vector3f delta = ZERO;

    @SerializedName("speed")
    private float speed = 0f;

    @SerializedName("life_time")
    private int lifeTime = 20;

    @SerializedName("count")
    private int count = 1;

    // ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬â€ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â€šÂ¬Ã…â€™ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚Â deco ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
    private transient ParticleOptions particleOptions;

    public String getName() {
        return name;
    }

    public Vector3f getDelta() {
        return delta;
    }

    public float getSpeed() {
        return speed;
    }

    public int getCount() {
        return count;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    @Nullable
    public ParticleOptions getParticleOptions() {
        return particleOptions;
    }

    public void setParticleOptions(ParticleOptions particleOptions) {
        this.particleOptions = particleOptions;
    }
}































































