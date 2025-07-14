package com.tacz.guns.client.particle;

// TODO: Re-enable when TimelessAPI and EntityKineticBullet are habilitado
// import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.client.resource.pojo.display.ammo.AmmoParticle;
// import com.tacz.guns.entity.EntityKineticBullet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class AmmoParticleSpawner {
    // TODO: Re-enable when EntityKineticBullet is habilitado
    public static void addParticle(Object bullet /* EntityKineticBullet bullet */) {
        // TODO: Re-enable when TimelessAPI is habilitado
        /*
        TimelessAPI.getGunDisplay(bullet.getGunDisplayId(), bullet.getGunId()).ifPresent(gunIndex -> {
            AmmoParticle gunParticle = gunIndex.getParticle();
            if (gunParticle == null) {
                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
                TimelessAPI.getClientAmmoIndex(bullet.getAmmoId()).ifPresent(ammoIndex -> {
                    AmmoParticle ammoParticle = ammoIndex.getParticle();
                    if (ammoParticle == null) {
                        return;
                    }
                    spawnParticle(bullet, ammoParticle);
                });
            } else {
                // ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾
                spawnParticle(bullet, gunParticle);
            }
        });
        */
    }

    // TODO: Re-enable when EntityKineticBullet is habilitado
    private static void spawnParticle(Object bullet /* EntityKineticBullet bullet */, AmmoParticle particle) {
        /*
        ParticleOptions particleOptions = particle.getParticleOptions();
        if (particleOptions == null) {
            return;
        }
        int count = particle.getCount();
        Vector3f delta = particle.getDelta();
        float particleSpeed = particle.getSpeed();
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
        if (count == 0) {
            double xSpeed = particleSpeed * delta.x();
            double ySpeed = particleSpeed * delta.y();
            double zSpeed = particleSpeed * delta.z();
            Particle result = particleEngine.createParticle(particleOptions, bullet.getX(), bullet.getY(), bullet.getZ(), xSpeed, ySpeed, zSpeed);
            if (result != null) {
                result.setLifetime(particle.getLifeTime());
            }
        } else {
            RandomSource random = bullet.getRandom();
            Entity owner = bullet.getOwner();
            for (int i = 0; i < count; ++i) {
                createParticle(bullet, particle, random, delta, particleSpeed, owner, particleEngine, particleOptions);
            }
        }
        */
    }

    // TODO: Re-enable when EntityKineticBullet is habilitado
    private static void createParticle(Object bullet /* EntityKineticBullet bullet */, AmmoParticle particle, RandomSource random, Vector3f delta, float particleSpeed, Entity owner, ParticleEngine particleEngine, ParticleOptions particleOptions) {
        /*
        Vec3 deltaMovement = bullet.getDeltaMovement();
        double deltaMovementRandom = random.nextDouble();
        double offsetX = random.nextGaussian() * delta.x() + deltaMovementRandom * deltaMovement.x;
        double offsetY = random.nextGaussian() * delta.y() + deltaMovementRandom * deltaMovement.y;
        double offsetZ = random.nextGaussian() * delta.z() + deltaMovementRandom * deltaMovement.z;
        double xSpeed = random.nextGaussian() * particleSpeed;
        double ySpeed = random.nextGaussian() * particleSpeed;
        double zSpeed = random.nextGaussian() * particleSpeed;

        double posX = bullet.getX() + offsetX;
        double posY = bullet.getY() + offsetY;
        double posZ = bullet.getZ() + offsetZ;

        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â¤Ã‚ÂªÃƒÂ¨Ã‚Â´Ã‚Â´ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¦Ã‹â€ Ã‚Â
        if (owner == null || owner.distanceToSqr(posX, posY, posZ) > 3 * 3) {
            Particle result = particleEngine.createParticle(particleOptions, posX, posY, posZ, xSpeed, ySpeed, zSpeed);
            if (result != null) {
                result.setLifetime(particle.getLifeTime());
            }
        }
        */
    }
}































































