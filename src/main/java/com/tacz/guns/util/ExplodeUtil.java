package com.tacz.guns.util;

import com.tacz.guns.config.common.AmmoConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Utilitário de explosão com implementação mínima temporária
 * TODO: Implementar ProjectileExplosion quando APIs de Explosion forem migradas
 */
public class ExplodeUtil {
    public static void createExplosion(Entity owner, Entity exploder, float damage, float radius, boolean knockback, boolean destroy, Vec3 hitPos) {
        // Implementação mínima - usar explosion vanilla temporariamente
        if (!(exploder.level() instanceof ServerLevel level)) {
            return;
        }
        
        // Migração NeoForge 1.21.1: Usar API de explosão correta com ExplosionInteraction
        // TODO: Restaurar ProjectileExplosion quando APIs forem migradas
        Level.ExplosionInteraction interaction = destroy ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE;
        level.explode(owner, hitPos.x(), hitPos.y(), hitPos.z(), radius, interaction);
    }
}
