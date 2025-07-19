package com.tacz.guns.compat.kubejs.events;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Eventos principais do TacZ para KubeJS
 * MIGRAÇÃO 1.21.1: Sistema completo de eventos integrado
 */
public class GunKubeJSEvents {
    
    /**
     * Grupo principal de eventos TacZ para KubeJS
     */
    public static final EventGroup GROUP = EventGroup.of("TaczGunEvents");
    
    /**
     * Evento disparado quando um jogador atira com uma arma
     */
    public static final EventHandler PLAYER_SHOOT = GROUP.server("playerShoot", () -> GunShootEventJS.class);
    
    /**
     * Evento disparado quando um jogador recarrega uma arma
     */
    public static final EventHandler PLAYER_RELOAD = GROUP.server("playerReload", () -> GunReloadEventJS.class);
    
    /**
     * Evento disparado quando um jogador usa ataque corpo a corpo com uma arma
     */
    public static final EventHandler PLAYER_MELEE = GROUP.server("playerMelee", () -> GunMeleeEventJS.class);
    
    /**
     * Evento disparado quando um jogador muda o modo de tiro
     */
    public static final EventHandler FIRE_MODE_CHANGE = GROUP.server("fireModeChange", () -> FireModeChangeEventJS.class);
    
    /**
     * Evento disparado quando uma bala atinge um alvo
     */
    public static final EventHandler BULLET_HIT = GROUP.server("bulletHit", () -> BulletHitEventJS.class);

    /**
     * Wrapper de evento JavaScript para tiro de arma
     */
    public static class GunShootEventJS extends TimelessEventJS {
        private final Player player;
        private final ItemStack gunStack;
        private final String gunId;
        
        public GunShootEventJS(Player player, ItemStack gunStack, String gunId) {
            this.player = player;
            this.gunStack = gunStack;
            this.gunId = gunId;
        }
        
        public Player getPlayer() {
            return player;
        }
        
        public ItemStack getGunStack() {
            return gunStack;
        }
        
        public String getGunId() {
            return gunId;
        }
        
        public int getAmmoCount() {
            // TODO: Implementar com DataComponents
            return 0;
        }
    }
    
    /**
     * Wrapper de evento JavaScript para recarga de arma
     */
    public static class GunReloadEventJS extends TimelessEventJS {
        private final Player player;
        private final ItemStack gunStack;
        private final String gunId;
        private final boolean isQuickReload;
        
        public GunReloadEventJS(Player player, ItemStack gunStack, String gunId, boolean isQuickReload) {
            this.player = player;
            this.gunStack = gunStack;
            this.gunId = gunId;
            this.isQuickReload = isQuickReload;
        }
        
        public Player getPlayer() {
            return player;
        }
        
        public ItemStack getGunStack() {
            return gunStack;
        }
        
        public String getGunId() {
            return gunId;
        }
        
        public boolean isQuickReload() {
            return isQuickReload;
        }
    }
    
    /**
     * Wrapper de evento JavaScript para ataque corpo a corpo
     */
    public static class GunMeleeEventJS extends TimelessEventJS {
        private final Player player;
        private final ItemStack gunStack;
        private final String gunId;
        
        public GunMeleeEventJS(Player player, ItemStack gunStack, String gunId) {
            this.player = player;
            this.gunStack = gunStack;
            this.gunId = gunId;
        }
        
        public Player getPlayer() {
            return player;
        }
        
        public ItemStack getGunStack() {
            return gunStack;
        }
        
        public String getGunId() {
            return gunId;
        }
    }
    
    /**
     * Wrapper de evento JavaScript para mudança de modo de tiro
     */
    public static class FireModeChangeEventJS extends TimelessEventJS {
        private final Player player;
        private final ItemStack gunStack;
        private final String gunId;
        private final String oldMode;
        private final String newMode;
        
        public FireModeChangeEventJS(Player player, ItemStack gunStack, String gunId, String oldMode, String newMode) {
            this.player = player;
            this.gunStack = gunStack;
            this.gunId = gunId;
            this.oldMode = oldMode;
            this.newMode = newMode;
        }
        
        public Player getPlayer() {
            return player;
        }
        
        public ItemStack getGunStack() {
            return gunStack;
        }
        
        public String getGunId() {
            return gunId;
        }
        
        public String getOldMode() {
            return oldMode;
        }
        
        public String getNewMode() {
            return newMode;
        }
    }
    
    /**
     * Wrapper de evento JavaScript para impacto de bala
     */
    public static class BulletHitEventJS extends TimelessEventJS {
        private final Player shooter;
        private final ItemStack gunStack;
        private final String gunId;
        private final double damage;
        private final boolean isHeadshot;
        
        public BulletHitEventJS(Player shooter, ItemStack gunStack, String gunId, double damage, boolean isHeadshot) {
            this.shooter = shooter;
            this.gunStack = gunStack;
            this.gunId = gunId;
            this.damage = damage;
            this.isHeadshot = isHeadshot;
        }
        
        public Player getShooter() {
            return shooter;
        }
        
        public ItemStack getGunStack() {
            return gunStack;
        }
        
        public String getGunId() {
            return gunId;
        }
        
        public double getDamage() {
            return damage;
        }
        
        public boolean isHeadshot() {
            return isHeadshot;
        }
    }
}
