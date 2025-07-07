package com.tacz.guns.client.init;

import com.tacz.guns.GunMod;
// TODO: Re-habilitar quando BulletHoleParticle for habilitado
// import com.tacz.guns.client.particle.BulletHoleParticle;
import com.tacz.guns.init.ModParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

// Migração NeoForge 1.21.1: EventBusSubscriber agora é diretamente do fml.common
@EventBusSubscriber(modid = GunMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ParticleFactoryRegistry {
    @SubscribeEvent
    public static void onRegisterParticleFactory(RegisterParticleProvidersEvent event) {
        // TODO: Re-habilitar quando BulletHoleParticle for habilitado
        // event.registerSpecial(ModParticles.BULLET_HOLE.get(), new BulletHoleParticle.Provider());
        
        // Implementação mínima - partícula registrada mas sem provider por enquanto
        // A partícula está definida em ModParticles mas não terá renderização até BulletHoleParticle ser habilitado
    }
}
