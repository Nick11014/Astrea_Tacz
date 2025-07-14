package com.tacz.guns.api.modifier;

/**
 * ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
 */
public class CacheValue<T> {
    private T value;

    public CacheValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}































































