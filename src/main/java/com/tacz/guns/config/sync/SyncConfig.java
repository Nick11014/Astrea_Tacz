package com.tacz.guns.config.sync;

import com.google.common.collect.Lists;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class SyncConfig {
    // ÃƒÂ¤Ã‚ÂºÃ‚Â¤ÃƒÂ¤Ã‚ÂºÃ¢â‚¬â„¢ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã¢â‚¬Â ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶
    public static ModConfigSpec.ConfigValue<List<String>> INTERACT_KEY_WHITELIST_BLOCKS;
    public static ModConfigSpec.ConfigValue<List<String>> INTERACT_KEY_WHITELIST_ENTITIES;
    public static ModConfigSpec.ConfigValue<List<String>> INTERACT_KEY_BLACKLIST_BLOCKS;
    public static ModConfigSpec.ConfigValue<List<String>> INTERACT_KEY_BLACKLIST_ENTITIES;
    public static ModConfigSpec.BooleanValue ENABLE_TABLE_FILTER;
    public static ModConfigSpec.BooleanValue SERVER_SHOOT_NETWORK_V;
    public static ModConfigSpec.BooleanValue SERVER_SHOOT_COOLDOWN_V;

    // ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ¥Ã‚Â±Ã¢â€šÂ¬ÃƒÂ§Ã‚Â³Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¥
    public static ModConfigSpec.DoubleValue DAMAGE_BASE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue ARMOR_IGNORE_BASE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue HEAD_SHOT_BASE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue WEIGHT_SPEED_MULTIPLIER;

    // ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¥ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¤Ã‚Â¾Ã‚Â¿ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ debug ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ§Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã¢â‚¬â„¢Ã…Â¾ÃƒÂ§Ã‚Â®Ã‚Â±
    public static ModConfigSpec.ConfigValue<List<String>> HEAD_SHOT_AABB;
    // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂºÃ¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã¢â‚¬Å¡Ã‚Â¨ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¯ÃƒÂ¦Ã…â€™Ã‚Â
    public static ModConfigSpec.IntValue AMMO_BOX_STACK_SIZE;
    // ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦
    public static ModConfigSpec.ConfigValue<List<List<String>>> CLIENT_GUN_PACK_DOWNLOAD_URLS;
    // ÃƒÂ§Ã‚Â¦Ã‚ÂÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã‚Â¶Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã…â€œÃ‚Â¯ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â½Ã…â€œ
    public static ModConfigSpec.BooleanValue ENABLE_CRAWL;

    public static void init(ModConfigSpec.Builder builder) {
        interactKey(builder);
        baseMultiplier(builder);
        misc(builder);
    }

    public static void interactKey(ModConfigSpec.Builder builder) {
        builder.push("interact_key");

        builder.comment("These whitelist blocks can be interacted with when the interact key is pressed");
        INTERACT_KEY_WHITELIST_BLOCKS = builder.define("InteractKeyWhitelistBlocks", Lists.newArrayList());

        builder.comment("These whitelist entities can be interacted with when the interact key is pressed");
        INTERACT_KEY_WHITELIST_ENTITIES = builder.define("InteractKeyWhitelistEntities", Lists.newArrayList());

        builder.comment("These blacklist blocks can be interacted with when the interact key is pressed");
        INTERACT_KEY_BLACKLIST_BLOCKS = builder.define("InteractKeyBlacklistBlocks", Lists.newArrayList());

        builder.comment("These blacklist entities can be interacted with when the interact key is pressed");
        INTERACT_KEY_BLACKLIST_ENTITIES = builder.define("InteractKeyBlacklistEntities", Lists.newArrayList());

        builder.pop();
    }

    private static void baseMultiplier(ModConfigSpec.Builder builder) {
        builder.push("base_multiplier");

        builder.comment("All base damage number is multiplied by this factor");
        DAMAGE_BASE_MULTIPLIER = builder.defineInRange("DamageBaseMultiplier", 1, 0, Double.MAX_VALUE);

        builder.comment("All armor ignore damage number is multiplied by this factor");
        ARMOR_IGNORE_BASE_MULTIPLIER = builder.defineInRange("ArmorIgnoreBaseMultiplier", 1, 0, Double.MAX_VALUE);

        builder.comment("All head shot damage number is multiplied by this factor");
        HEAD_SHOT_BASE_MULTIPLIER = builder.defineInRange("HeadShotBaseMultiplier", 1, 0, Double.MAX_VALUE);

        builder.comment("The movement speed will decrease per kg of weight. 0.015 means 1.5% speed decrease per kg. Set a negative value to disable this feature");
        WEIGHT_SPEED_MULTIPLIER = builder.defineInRange("WeightSpeedMultiplier", 0.015, -1, Double.MAX_VALUE);

        builder.pop();
    }

    private static void misc(ModConfigSpec.Builder builder) {
        builder.push("misc");

        builder.comment("The entity's head hitbox during the headshot");
        builder.comment("Format: touhou_little_maid:maid [-0.5, 1.0, -0.5, 0.5, 1.5, 0.5]");
        HEAD_SHOT_AABB = builder.define("HeadShotAABB", Lists.newArrayList());

        builder.comment("The maximum stack size of ammo that the ammo box can hold");
        AMMO_BOX_STACK_SIZE = builder.defineInRange("AmmoBoxStackSize", 3, 1, Integer.MAX_VALUE);

        builder.comment("Deprecated. Use vanilla server resource pack");
        CLIENT_GUN_PACK_DOWNLOAD_URLS = builder.define("ClientGunPackDownloadUrls", Lists.newArrayList());

        builder.comment("Whether or not players are allowed to use the crawl feature");
        ENABLE_CRAWL = builder.define("EnableCrawl", true);

        builder.comment("Enable the recipe limit of default gunsmith table or not");
        ENABLE_TABLE_FILTER = builder.define("EnableDefaultGunSmithTableFilter", true);

        builder.comment("[Debug Option] Do server-side network check while shooting or not");
        SERVER_SHOOT_NETWORK_V = builder.define("ServerShootNetworkCheck", true);

        builder.comment("[Debug Option] Do server-side shoot cooldown check or not." +
                " WARNING: Close this will disable the shoot cooldown check in server-side at all," +
                " which may lead to potential for cheating." +
                " Only consider to close this when you can't shoot at all sometimes.");
        SERVER_SHOOT_COOLDOWN_V = builder.define("ServerShootCooldownCheck", true);
        builder.pop();
    }
}































































