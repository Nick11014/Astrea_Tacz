package com.tacz.guns.client.resource.pojo.model;

public enum BedrockVersion {
    /**
     * ÃƒÂ¦Ã¢â‚¬â€Ã‚Â§ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
     */
    LEGACY("1.10.0"),
    /**
     * ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¾Ã¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ 1.14.0ÃƒÂ¯Ã‚Â¼Ã…â€™1.16.0 1.21.0 ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ
     */
    NEW("1.12.0");

    private final String version;

    BedrockVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public static boolean isNewVersion(BedrockModelPOJO bedrockModel) {
        String[] checkVersion = bedrockModel.getFormatVersion().split("\\.", 3);
        String[] newVersion = NEW.getVersion().split("\\.", 3);
        if (checkVersion.length == 3 && newVersion.length == 3) {
            return Integer.parseInt(checkVersion[1]) >= Integer.parseInt(newVersion[1]);
        }
        return false;
    }

    public static boolean isLegacyVersion(BedrockModelPOJO bedrockModel) {
        return bedrockModel.getFormatVersion().equals(LEGACY.getVersion());
    }
}































































