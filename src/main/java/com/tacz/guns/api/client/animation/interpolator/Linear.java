package com.tacz.guns.api.client.animation.interpolator;

import com.tacz.guns.api.client.animation.AnimationChannelContent;

public class Linear implements Interpolator {
    private AnimationChannelContent content;

    @Override
    public void compile(AnimationChannelContent content) {
        this.content = content;
    }

    @Override
    public float[] interpolate(int indexFrom, int indexTo, float alpha) {
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¦Ã…â€œÃ¢â‚¬Â° 6 ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¤Ã‚Â¸Ã‚Âº Post ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã‚ÂÃ¢â‚¬â„¢ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¨Ã‚ÂµÃ‚Â·ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹
        int offset = content.values[indexFrom].length == 6 ? 3 : 0;
        float[] result = new float[3];
        for (int i = 0; i < 3; i++) {
            if (indexFrom == indexTo) {
                result[i] = content.values[indexFrom][i + offset];
            } else {
                result[i] = content.values[indexFrom][i + offset] * (1 - alpha) + content.values[indexTo][i] * alpha;
            }
        }
        return result;
    }

    @Override
    public Linear clone() {
        try {
            Linear linear = (Linear) super.clone();
            linear.content = this.content;
            return linear;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}































































