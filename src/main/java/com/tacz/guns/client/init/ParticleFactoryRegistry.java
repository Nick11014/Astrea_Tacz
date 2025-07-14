package com.tacz.guns.client.init;

import com.tacz.guns.GunMod;
// TODO: Re-habilitar quando BulletHoleParticle for habilitado
// import com.tacz.guns.client.particle.BulletHoleParticle;
import com.tacz.guns.init.ModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

// MigraÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o NeoForge 1.21.1: EventBusSubscriber agora ÃƒÆ’Ã‚Â© diretamente do fml.common
@EventBusSubscriber(modid = GunMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegistry {
    @SubscribeEvent
    public static void onRegisterParticleFactory(RegisterParticleProvidersEvent event) {
        // TODO: Re-habilitar quando BulletHoleParticle for habilitado
        // event.registerSpecial(ModParticles.BULLET_HOLE.get(), new BulletHoleParticle.Provider());
        
        // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - partÃƒÆ’Ã‚Â­cula registrada mas sem provider por enquanto
        // A partÃƒÆ’Ã‚Â­cula estÃƒÆ’Ã‚Â¡ definida em ModParticles mas nÃƒÆ’Ã‚Â£o terÃƒÆ’Ã‚Â¡ renderizaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o atÃƒÆ’Ã‚Â© BulletHoleParticle ser habilitado
    }
}































































