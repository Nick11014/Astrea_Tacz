package com.tacz.guns.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tacz.guns.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

/**
 * ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o baseada no padrÃƒÆ’Ã‚Â£o do SuperbWarfare 1.21.1 para partÃƒÆ’Ã‚Â­culas customizadas.
 * As APIs de partÃƒÆ’Ã‚Â­culas mudaram significativamente - agora usam MapCodec e StreamCodec.
 */
public class BulletHoleOption implements ParticleOptions {
    
    public static final MapCodec<BulletHoleOption> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    Direction.CODEC.fieldOf("dir").forGetter(BulletHoleOption::getDirection),
                    BlockPos.CODEC.fieldOf("pos").forGetter(BulletHoleOption::getPos),
                    Codec.STRING.fieldOf("ammo_id").forGetter(BulletHoleOption::getAmmoId),
                    Codec.STRING.fieldOf("gun_id").forGetter(BulletHoleOption::getGunId),
                    Codec.STRING.optionalFieldOf("gun_display_id", "default").forGetter(BulletHoleOption::getGunDisplayId)
            ).apply(builder, BulletHoleOption::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BulletHoleOption> STREAM_CODEC = StreamCodec.composite(
            Direction.STREAM_CODEC,
            BulletHoleOption::getDirection,
            BlockPos.STREAM_CODEC,
            BulletHoleOption::getPos,
            ByteBufCodecs.STRING_UTF8,
            BulletHoleOption::getAmmoId,
            ByteBufCodecs.STRING_UTF8,
            BulletHoleOption::getGunId,
            ByteBufCodecs.STRING_UTF8,
            BulletHoleOption::getGunDisplayId,
            BulletHoleOption::new
    );

    private final Direction direction;
    private final BlockPos pos;
    private final String ammoId;
    private final String gunId;
    private final String gunDisplayId;

    public BulletHoleOption(Direction dir, BlockPos pos, String ammoId, String gunId, String gunDisplayId) {
        this.direction = dir;
        this.pos = pos;
        this.ammoId = ammoId;
        this.gunId = gunId;
        this.gunDisplayId = gunDisplayId;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public String getAmmoId() {
        return ammoId;
    }

    public String getGunId() {
        return gunId;
    }

    public String getGunDisplayId() {
        return gunDisplayId;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticles.BULLET_HOLE.get();
    }
}































































