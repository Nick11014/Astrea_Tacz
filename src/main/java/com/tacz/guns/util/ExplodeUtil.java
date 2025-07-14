package com.tacz.guns.util;

import com.tacz.guns.config.common.AmmoConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * UtilitÃƒÆ’Ã‚Â¡rio de explosÃƒÆ’Ã‚Â£o com implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima temporÃƒÆ’Ã‚Â¡ria
 * TODO: Implementar ProjectileExplosion quando APIs de Explosion forem migradas
 */
public class ExplodeUtil {
    public static void createExplosion(Entity owner, Entity exploder, float damage, float radius, boolean knockback, boolean destroy, Vec3 hitPos) {
        // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - usar explosion vanilla temporariamente
        if (!(exploder.level() instanceof ServerLevel level)) {
            return;
        }
        
        // MigraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o NeoForge 1.21.1: Usar API de explosÃƒÆ’Ã‚Â£o correta com ExplosionInteraction
        // TODO: Restaurar ProjectileExplosion quando APIs forem migradas
        Level.ExplosionInteraction interaction = destroy ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE;
        level.explode(owner, hitPos.x(), hitPos.y(), hitPos.z(), radius, interaction);
    }
}































































