package com.tacz.guns.resource.pojo.data.gun;

import com.google.gson.annotations.SerializedName;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import javax.annotation.Nullable;

public class GunRecoil {
    private static final SplineInterpolator INTERPOLATOR = new SplineInterpolator();

    @SerializedName("pitch")
    @Nullable
    private GunRecoilKeyFrame[] pitch;

    @SerializedName("yaw")
    @Nullable
    private GunRecoilKeyFrame[] yaw;

    public GunRecoilKeyFrame[] getPitch() {
        return pitch;
    }

    public void setPitch(@Nullable GunRecoilKeyFrame[] pitch) {
        this.pitch = pitch;
    }

    public GunRecoilKeyFrame[] getYaw() {
        return yaw;
    }

    public void setYaw(@Nullable GunRecoilKeyFrame[] yaw) {
        this.yaw = yaw;
    }

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ©Ã…Â¡Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã…Â¾Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â Ã‚Â·ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã‚ÂÃ¢â‚¬â„¢ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â½ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param modifier ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹
     * @return ÃƒÂ¦Ã‚Â Ã‚Â·ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã‚ÂÃ¢â‚¬â„¢ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â½ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
     */
    @Nullable
    public PolynomialSplineFunction genPitchSplineFunction(float modifier) {
        return getSplineFunction(pitch, modifier);
    }

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ©Ã…Â¡Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã‚Â¼Ã‚Â©ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Å¾ÃƒÂ¥Ã†â€™Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¦Ã‚Â°Ã‚Â´ÃƒÂ¥Ã‚Â¹Ã‚Â³ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â Ã‚Â·ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã‚ÂÃ¢â‚¬â„¢ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â½ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     *
     * @param modifier ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹
     * @return ÃƒÂ¦Ã‚Â Ã‚Â·ÃƒÂ¦Ã‚ÂÃ‚Â¡ÃƒÂ¦Ã‚ÂÃ¢â‚¬â„¢ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â½ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
     */
    @Nullable
    public PolynomialSplineFunction genYawSplineFunction(float modifier) {
        return getSplineFunction(yaw, modifier);
    }

    private PolynomialSplineFunction getSplineFunction(GunRecoilKeyFrame[] keyFrames, float modifier) {
        if (keyFrames == null || keyFrames.length == 0) {
            return null;
        }
        double[] values = new double[keyFrames.length + 1];
        double[] times = new double[keyFrames.length + 1];
        times[0] = 0;
        values[0] = 0;
        for (int i = 0; i < keyFrames.length; i++) {
            times[i + 1] = keyFrames[i].getTime() * 1000 + 30;
        }
        for (int i = 0; i < keyFrames.length; i++) {
            float[] value = keyFrames[i].getValue();
            values[i + 1] = (value[0] + Math.random() * (value[1] - value[0])) * modifier;
        }
        return INTERPOLATOR.interpolate(times, values);
    }
}































































