package com.tacz.guns.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

// TODO: Major refactor needed - Particle APIs changed significantly in 1.21.1
public class BulletHoleOption implements ParticleOptions {
    public static final Codec<BulletHoleOption> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(Codec.INT.fieldOf("dir").forGetter(option -> option.direction.ordinal()),
                    Codec.LONG.fieldOf("pos").forGetter(option -> option.pos.asLong()),
                    Codec.STRING.fieldOf("ammo_id").forGetter(option -> option.ammoId),
                    Codec.STRING.fieldOf("gun_id").forGetter(option -> option.gunId),
                    Codec.STRING.optionalFieldOf("gun_display_id", "default").forGetter(option -> option.gunDisplayId)
            ).apply(builder, BulletHoleOption::new));

    // TODO: Re-enable when particle APIs are understood in 1.21.1
    // ParticleOptions.Deserializer interface seems to have been removed/changed

    private final Direction direction;
    private final BlockPos pos;
    private final String ammoId;
    private final String gunId;
    private final String gunDisplayId;

    public BulletHoleOption(int dir, long pos, String ammoId, String gunId, String gunDisplayId) {
        this.direction = Direction.values()[dir];
        this.pos = BlockPos.of(pos);
        this.ammoId = ammoId;
        this.gunId = gunId;
        this.gunDisplayId = gunDisplayId;
    }

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
    public ParticleType<?> getType() {
        // TODO: Re-enable when ModParticles is habilitado
        return null; // ModParticles.BULLET_HOLE.get();
    }

    // TODO: Check if these methods still exist in ParticleOptions in 1.21.1
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeEnum(this.direction);
        buffer.writeBlockPos(this.pos);
        buffer.writeUtf(this.ammoId);
        buffer.writeUtf(this.gunId);
        buffer.writeUtf(this.gunDisplayId);
    }    
    
    public String writeToString() {
        // TODO: Fix this when getType() returns valid ParticleType
        return "bullet_hole " + this.direction.getName();
    }
}
