package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import com.tacz.guns.particles.BulletHoleOption;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

/**
 * Registro de partÃƒÆ’Ã‚Â­culas baseado no padrÃƒÆ’Ã‚Â£o do SuperbWarfare 1.21.1
 * APIs modernizadas para usar MapCodec e StreamCodec
 */
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, GunMod.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, ParticleType<BulletHoleOption>> BULLET_HOLE = PARTICLE_TYPES.register("bullet_hole",
            () -> createOptions(BulletHoleOption.CODEC, BulletHoleOption.STREAM_CODEC));

    /**
     * MÃƒÆ’Ã‚Â©todo utilitÃƒÆ’Ã‚Â¡rio para criar ParticleType com MapCodec e StreamCodec
     * Baseado no padrÃƒÆ’Ã‚Â£o do SuperbWarfare 1.21.1
     */
    public static <T extends ParticleOptions> ParticleType<T> createOptions(MapCodec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return new ParticleType<>(false) {
            public @NotNull MapCodec<T> codec() {
                return codec;
            }

            public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return streamCodec;
            }
        };
    }
}































































