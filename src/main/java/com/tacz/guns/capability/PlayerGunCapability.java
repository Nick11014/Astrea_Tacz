package com.tacz.guns.capability;

import com.tacz.guns.client.gameplay.LocalPlayerDataHolder;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.WeakHashMap;

/**
 * Sistema de capability para armazenar dados dos jogadores relacionados às armas
 * Substitui o sistema de mixins problemático por um sistema baseado em WeakHashMap
 */
public class PlayerGunCapability {
    
    // Client-side storage for LocalPlayer data
    @OnlyIn(Dist.CLIENT)
    private static final WeakHashMap<LocalPlayer, LocalPlayerDataHolder> CLIENT_DATA = new WeakHashMap<>();
    
    // Server-side storage for ServerPlayer data  
    private static final WeakHashMap<ServerPlayer, ShooterDataHolder> SERVER_DATA = new WeakHashMap<>();
    
    /**
     * Get or create LocalPlayerDataHolder for client player
     */
    @OnlyIn(Dist.CLIENT)
    public static LocalPlayerDataHolder getOrCreateClientData(LocalPlayer player) {
        return CLIENT_DATA.computeIfAbsent(player, p -> new LocalPlayerDataHolder(p));
    }
    
    /**
     * Get or create ShooterDataHolder for server player
     */
    public static ShooterDataHolder getOrCreateServerData(ServerPlayer player) {
        return SERVER_DATA.computeIfAbsent(player, p -> new ShooterDataHolder());
    }
    
    /**
     * Remove client data (cleanup)
     */
    @OnlyIn(Dist.CLIENT)
    public static void removeClientData(LocalPlayer player) {
        CLIENT_DATA.remove(player);
    }
    
    /**
     * Remove server data (cleanup)
     */
    public static void removeServerData(ServerPlayer player) {
        SERVER_DATA.remove(player);
    }
    
    /**
     * Check if client data exists
     */
    @OnlyIn(Dist.CLIENT)
    public static boolean hasClientData(LocalPlayer player) {
        return CLIENT_DATA.containsKey(player);
    }
    
    /**
     * Check if server data exists
     */
    public static boolean hasServerData(ServerPlayer player) {
        return SERVER_DATA.containsKey(player);
    }
}
