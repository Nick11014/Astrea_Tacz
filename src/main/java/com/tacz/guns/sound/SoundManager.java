package com.tacz.guns.sound;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

/**
 * Gerenciador de sons para armas.
 * 
 * MIGRAÇÃO 1.21.1: Implementação mínima que mantém todas as constantes
 * mas implementa métodos de rede com placeholders seguros.
 */
public class SoundManager {
    /**
     * 射击音效，自己能听见
     */
    public static String SHOOT_SOUND = "shoot";
    /**
     * 其他玩家听到的枪声
     */
    public static String SHOOT_3P_SOUND = "shoot_3p";
    /**
     * 消音器音效
     */
    public static String SILENCE_SOUND = "silence";
    /**
     * 其他玩家听到的消音器枪声
     */
    public static String SILENCE_3P_SOUND = "silence_3p";
    /**
     * 近战刺刀音效
     */
    public static String MELEE_BAYONET = "melee_bayonet";
    /**
     * 近战推人音效
     */
    public static String MELEE_PUSH = "melee_push";
    /**
     * 近战枪拖砸人音效
     */
    public static String MELEE_STOCK = "melee_stock";
    /**
     * 没有子弹时，空击的声音
     */
    public static String DRY_FIRE_SOUND = "dry_fire";
    /**
     * 空仓换弹声音
     */
    public static String RELOAD_EMPTY_SOUND = "reload_empty";
    /**
     * 战术换弹声音
     */
    public static String RELOAD_TACTICAL_SOUND = "reload_tactical";
    /**
     * 空仓检视声音
     */
    public static String INSPECT_EMPTY_SOUND = "inspect_empty";
    /**
     * 普通检视声音
     */
    public static String INSPECT_SOUND = "inspect";
    /**
     * 切枪切入声音
     */
    public static String DRAW_SOUND = "draw";
    /**
     * 切枪切出的声音
     */
    public static String PUT_AWAY_SOUND = "put_away";
    /**
     * 拉栓声音
     */
    public static String BOLT_SOUND = "bolt";
    /**
     * 切换开关模式的声音
     */
    public static String FIRE_SELECT = "fire_select";
    /**
     * 爆头击中声音
     */
    public static String HEAD_HIT_SOUND = "head_hit";
    /**
     * 普通击中声音
     */
    public static String FLESH_HIT_SOUND = "flesh_hit";
    /**
     * 击杀的声音
     */
    public static String KILL_SOUND = "kill";
    /**
     * 卸载配件的声音，用于配件的
     */
    public static String UNINSTALL_SOUND = "uninstall";
    /**
     *装载配件的声音，用于配件的
     */
    public static String INSTALL_SOUND = "install";

    // ===== MÉTODOS COM IMPLEMENTAÇÃO MÍNIMA =====

    /**
     * Envia som para jogadores próximos - Implementação mínima
     * TODO: Implementar quando NetworkHandler estiver disponível
     */
    public static void sendSoundToNearby(LivingEntity sourceEntity, int distance, ResourceLocation gunId, String soundName, float volume, float pitch) {
        sendSoundToNearby(sourceEntity, distance, gunId, null, soundName, volume, pitch);
    }

    /**
     * Envia som para jogadores próximos - Implementação mínima
     * TODO: Implementar quando NetworkHandler e ServerMessageSound estiverem disponíveis
     */
    public static void sendSoundToNearby(LivingEntity sourceEntity, int distance, ResourceLocation gunId, ResourceLocation gunDisplayId, String soundName, float volume, float pitch) {
        // Implementação mínima: logs para debug
        if (sourceEntity.level() instanceof ServerLevel) {
            // TODO: Implementar envio real de som via NetworkHandler quando disponível
            // Para agora, apenas não faz nada (implementação silenciosa segura)
        }
    }
}
