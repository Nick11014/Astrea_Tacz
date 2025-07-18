package com.tacz.guns.api.modifier;


import com.google.common.collect.ImmutableList;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import com.tacz.guns.resource.pojo.data.attachment.Modifier;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
/**
 * ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¦Ã‚Â¯Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¤Ã‚Â¹Ã‹Å“ÃƒÂ¥Ã…â€™Ã‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¿Ã‚ÂÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚Â¾Ã‚Â¿ÃƒÂ¥Ã‚Â¿Ã‚Â«ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
 * ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ§Ã‚Â¡Ã‚Â®ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¥Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬Âº
 */
public class ParameterizedCache<T> {
    private final T defaultValue;
    private final List<String> scripts;
    private final double addend;
    private final double percent;
    private final double multiplier;

    public ParameterizedCache(List<Modifier> modifiers, T defaultValue) {
        double addend = 0;
        double percent = 1;
        double multiplier = 1;

        ImmutableList.Builder<String> builder = new ImmutableList.Builder<>();
        for (Modifier mod : modifiers) {
            addend += mod.getAddend();
            percent += mod.getPercent();
            multiplier *= Math.max(mod.getMultiplier(), 0f);
            if (StringUtils.isNotEmpty(mod.getFunction())) {
                builder.add(mod.getFunction());
            }
        }

        this.addend = addend;
        this.percent = percent;
        this.multiplier = multiplier;
        this.scripts = builder.build();
        this.defaultValue = defaultValue;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public double eval(double input) {
        double percent = Math.max(this.percent, 0);
        double value = (input + addend) * percent * multiplier;
        for (String function : scripts) {
            if (StringUtils.isEmpty(function)) {
                continue;
            }
            value = AttachmentPropertyManager.functionEval(value, input, function);
        }
        return value;
    }

    public double eval(double input, double extraAddend, double extraPercent, double extraMultiplier) {
        double percent = Math.max(this.percent + extraPercent, 0);
        extraMultiplier = Math.max(extraMultiplier, 0);
        double value = (input + addend + extraAddend) * percent * multiplier * extraMultiplier;
        for (String function : scripts) {
            if (StringUtils.isEmpty(function)) {
                continue;
            }
            value = AttachmentPropertyManager.functionEval(value, input, function);
        }
        return value;
    }

    public static <T> ParameterizedCache<T> of(T defaultValue) {
        return new ParameterizedCache<>(List.of(), defaultValue);
    }

    public static <T> ParameterizedCache<T> of(List<Modifier> modifiers, T defaultValue) {
        return new ParameterizedCache<>(modifiers, defaultValue);
    }

}































































