package com.tacz.guns.sound;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Gerenciador de sons para armas.
 * 
 * MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O 1.21.1: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima que mantÃƒÆ’Ã‚Â©m todas as constantes
 * mas implementa mÃƒÆ’Ã‚Â©todos de rede com placeholders seguros.
 */
public class SoundManager {
    /**
     * ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¥Ã‚ÂÃ‚Â¬ÃƒÂ¨Ã‚Â§Ã‚Â
     */
    public static String SHOOT_SOUND = "shoot";
    /**
     * ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¬ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚Â£Ã‚Â°
     */
    public static String SHOOT_3P_SOUND = "shoot_3p";
    /**
     * ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
     */
    public static String SILENCE_SOUND = "silence";
    /**
     * ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¬ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚Â£Ã‚Â°
     */
    public static String SILENCE_3P_SOUND = "silence_3p";
    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¥Ã‹â€ Ã‚ÂºÃƒÂ¥Ã‹â€ Ã¢â€šÂ¬ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
     */
    public static String MELEE_BAYONET = "melee_bayonet";
    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã…Â½Ã‚Â¨ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
     */
    public static String MELEE_PUSH = "melee_push";
    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬â€œÃƒÂ§Ã‚Â Ã‚Â¸ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
     */
    public static String MELEE_STOCK = "melee_stock";
    /**
     * ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String DRY_FIRE_SOUND = "dry_fire";
    /**
     * ÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ¤Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String RELOAD_EMPTY_SOUND = "reload_empty";
    /**
     * ÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¦Ã…â€œÃ‚Â¯ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String RELOAD_TACTICAL_SOUND = "reload_tactical";
    /**
     * ÃƒÂ§Ã‚Â©Ã‚ÂºÃƒÂ¤Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String INSPECT_EMPTY_SOUND = "inspect_empty";
    /**
     * ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â®ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String INSPECT_SOUND = "inspect";
    /**
     * ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String DRAW_SOUND = "draw";
    /**
     * ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String PUT_AWAY_SOUND = "put_away";
    /**
     * ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â°ÃƒÂ¦Ã‚Â Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String BOLT_SOUND = "bolt";
    /**
     * ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String FIRE_SELECT = "fire_select";
    /**
     * ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã‚Â´ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String HEAD_HIT_SOUND = "head_hit";
    /**
     * ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â®ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String FLESH_HIT_SOUND = "flesh_hit";
    /**
     * ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‚ÂÃ¢â€šÂ¬ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³
     */
    public static String KILL_SOUND = "kill";
    /**
     * ÃƒÂ¥Ã‚ÂÃ‚Â¸ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
     */
    public static String UNINSTALL_SOUND = "uninstall";
    /**
     *ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â£Ã‚Â°ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
     */
    public static String INSTALL_SOUND = "install";


    /**
     * Envia som para jogadores prÃƒÆ’Ã‚Â³ximos - ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
     * TODO: Implementar quando NetworkHandler estiver disponÃƒÆ’Ã‚Â­vel
     */
    public static void sendSoundToNearby(LivingEntity sourceEntity, int distance, ResourceLocation gunId, String soundName, float volume, float pitch) {
        sendSoundToNearby(sourceEntity, distance, gunId, null, soundName, volume, pitch);
    }

    /**
     * Envia som para jogadores prÃƒÆ’Ã‚Â³ximos - ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
     * TODO: Implementar quando NetworkHandler e ServerMessageSound estiverem disponÃƒÆ’Ã‚Â­veis
     */
    public static void sendSoundToNearby(LivingEntity sourceEntity, int distance, ResourceLocation gunId, ResourceLocation gunDisplayId, String soundName, float volume, float pitch) {
        if (sourceEntity.level() instanceof ServerLevel) {
        }
    }
}































































