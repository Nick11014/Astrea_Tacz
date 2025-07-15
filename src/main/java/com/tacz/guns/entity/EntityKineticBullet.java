package com.tacz.guns.entity;

import com.google.common.collect.Lists;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ITargetEntity;
import com.tacz.guns.api.entity.KnockBackModifier;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import com.tacz.guns.api.event.server.AmmoHitBlockEvent;
import com.tacz.guns.client.particle.AmmoParticleSpawner;
import com.tacz.guns.config.common.AmmoConfig;
import com.tacz.guns.config.sync.SyncConfig;
import com.tacz.guns.init.ModDamageTypes;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunHurt;
import com.tacz.guns.network.message.event.ServerMessageGunKill;
import com.tacz.guns.particles.BulletHoleOption;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.*;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.ExplosionData;
import com.tacz.guns.resource.pojo.data.gun.ExtraDamage.DistanceDamagePair;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.resource.pojo.data.gun.Ignite;
import com.tacz.guns.util.EntityUtil;
import com.tacz.guns.util.ExplodeUtil;
import com.tacz.guns.util.TacHitResult;
import com.tacz.guns.util.block.BlockRayTrace;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
// Corrected import for IEntityWithComplexSpawn
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
// Corrected import for PartEntity
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.util.*;

import static com.tacz.guns.api.event.common.GunDamageSourcePart.ARMOR_PIERCING;
import static com.tacz.guns.api.event.common.GunDamageSourcePart.NON_ARMOR_PIERCING;

/**
 * ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ¦Ã‚Â­Ã‚Â¦ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
 */
// Implemented the new IEntityWithComplexSpawn interface
public class EntityKineticBullet extends Projectile implements IEntityWithComplexSpawn {
    public static final EntityType<EntityKineticBullet> TYPE = EntityType.Builder.<EntityKineticBullet>of(EntityKineticBullet::new, MobCategory.MISC).noSummon().noSave().fireImmune().sized(0.0625F, 0.0625F).clientTrackingRange(5).updateInterval(5).setShouldReceiveVelocityUpdates(false).build("bullet");
    public static final TagKey<EntityType<?>> USE_MAGIC_DAMAGE_ON = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("tacz:use_magic_damage_on"));
    public static final TagKey<EntityType<?>> USE_VOID_DAMAGE_ON = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("tacz:use_void_damage_on"));
    public static final TagKey<EntityType<?>> PRETEND_MELEE_DAMAGE_ON = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("tacz:pretend_melee_damage_on"));

    /**
     * ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¸ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œ mod ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ persistent dataÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¦Ã‚Â°Ã‚Â¸ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â° ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â³ÃƒÂ¥Ã¢â‚¬Â¦Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ§Ã‚Â²Ã¢â‚¬â€ÃƒÂ§Ã‚Â»Ã¢â‚¬Â ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<p>
     * ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã‚Â°Ã‚Â¸ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¦ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¥Ã‚Â½ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â³ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œ mod ÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¤Ã‚Â¼Ã…Â¡ÃƒÂ¥Ã‚Â´Ã‚Â©ÃƒÂ¦Ã‚ÂºÃ†â€™ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<p>
     * ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ©Ã‚ÂÃ‚Â¢ÃƒÂ¤Ã‚Â¸Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚ÂµÃƒÂ¦Ã‹Å“Ã‚Â¯ persistent data ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ keyÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<p>
     * ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚ÂµÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ int[4]ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡<p>
     * <p>
     * ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…Â¡
     * <pre>{@code
     * bullet.getPersistentData().putIntArray(TRACER_COLOR_OVERRIDER_KEY, new int[]{255, 255, 255, 255});
     * }</pre>
     */
    public static final String TRACER_COLOR_OVERRIDER_KEY = GunMod.MOD_ID + ":tracer_override";

    /**
     * ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â­Ã¢â‚¬â€ÃƒÂ¦Ã‚Â®Ã‚ÂµÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã‹Å“Ã‚Â¯ floatÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * 1 ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™0 ÃƒÂ¨Ã‚Â¡Ã‚Â¨ÃƒÂ§Ã‚Â¤Ã‚Âº 0 ÃƒÂ¥Ã¢â€šÂ¬Ã‚ÂÃƒÂ§Ã…Â½Ã¢â‚¬Â¡ÃƒÂ§Ã‚Â²Ã¢â‚¬â€ÃƒÂ§Ã‚Â»Ã¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
     */
    public static final String TRACER_SIZE_OVERRIDER_KEY = GunMod.MOD_ID + ":tracer_size";

    private ResourceLocation ammoId = DefaultAssets.EMPTY_AMMO_ID;
    private int life = 200;
    private float speed = 1;
    private float gravity = 0;
    private float friction = 0.01F;
    private LinkedList<DistanceDamagePair> damageAmount = Lists.newLinkedList();
    private float distanceAmount = 0;
    private float knockback = 0;
    private boolean explosion = false;
    private boolean igniteEntity = false;
    private boolean igniteBlock = false;
    private int igniteEntityTime = 2;
    private float explosionDamage = 3;
    private float explosionRadius = 3;
    private int explosionDelayCount = Integer.MAX_VALUE;
    private boolean explosionKnockback = false;
    private boolean explosionDestroyBlock = false;
    private float damageModifier = 1;
    // ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
    private int pierce = 1;
    // ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
    private Vec3 startPos;
    // ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â³ÃƒÂ¥Ã¢â‚¬Â¦Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹
    private boolean isTracerAmmo;
    // ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â³ÃƒÂ¥Ã¢â‚¬Â¦Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
    private float cameraXRot;
    private float cameraYRot;
    private Vector3f firstPersonRenderOffset;
    // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â° ID
    private ResourceLocation gunId;
    // ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°display ID
    private ResourceLocation gunDisplayId;
    private float armorIgnore;
    private float headShot;

    public EntityKineticBullet(EntityType<? extends Projectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityKineticBullet(EntityType<? extends Projectile> type, double x, double y, double z, Level worldIn) {
        this(type, worldIn);
        this.setPos(x, y, z);
    }

    public EntityKineticBullet(Level worldIn, LivingEntity throwerIn, ItemStack gunItem, ResourceLocation ammoId, ResourceLocation gunId,
                               ResourceLocation gunDisplayId, boolean isTracerAmmo, GunData gunData, BulletData bulletData) {
        this(TYPE, worldIn, throwerIn, gunItem, ammoId, gunId, gunDisplayId, isTracerAmmo, gunData, bulletData);
    }

    public EntityKineticBullet(Level worldIn, LivingEntity throwerIn, ItemStack gunItem, ResourceLocation ammoId, ResourceLocation gunId, boolean isTracerAmmo, GunData gunData, BulletData bulletData) {
        this(TYPE, worldIn, throwerIn, gunItem, ammoId, gunId, DefaultAssets.DEFAULT_GUN_DISPLAY_ID, isTracerAmmo, gunData, bulletData);
    }

    protected EntityKineticBullet(EntityType<? extends Projectile> type, Level worldIn, LivingEntity throwerIn, ItemStack gunItem,
                                  ResourceLocation ammoId, ResourceLocation gunId, ResourceLocation gunDisplayId,
                                  boolean isTracerAmmo, GunData gunData, BulletData bulletData) {
        this(type, throwerIn.getX(), throwerIn.getEyeY() - (double) 0.1F, throwerIn.getZ(), worldIn);
        this.setOwner(throwerIn);
        AttachmentCacheProperty cacheProperty = Objects.requireNonNull(IGunOperator.fromLivingEntity(throwerIn).getCacheProperty());
        this.armorIgnore = Mth.clamp(cacheProperty.getCache(ArmorIgnoreModifier.ID), 0f, 1f);
        this.headShot = Math.max(cacheProperty.getCache(HeadShotModifier.ID), 0f);
        this.knockback = Math.max(cacheProperty.getCache(KnockbackModifier.ID), 0f);
        this.ammoId = ammoId;
        this.life = Mth.clamp((int) (bulletData.getLifeSecond() * 20), 1, Integer.MAX_VALUE);
        // ÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¤Ã‚Â¸Ã‚Âº 600 m / sÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¨Ã‚Â½Ã‚Â»ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¨Ã‚Â´Ã…Â¸ÃƒÂ¦Ã¢â‚¬Â¹Ã¢â‚¬Â¦
        this.speed = Mth.clamp(cacheProperty.<Float>getCache(AmmoSpeedModifier.ID) / 20f, 0f, 30f);
        this.gravity = Mth.clamp(bulletData.getGravity(), 0f, Float.MAX_VALUE);
        this.friction = Mth.clamp(bulletData.getFriction(), 0f, Float.MAX_VALUE);
        Ignite ignite = cacheProperty.getCache(IgniteModifier.ID);
        this.igniteEntity = bulletData.getIgnite().isIgniteEntity() || ignite.isIgniteEntity();
        this.igniteEntityTime = Math.max(bulletData.getIgniteEntityTime(), 0);
        this.igniteBlock = bulletData.getIgnite().isIgniteBlock() || ignite.isIgniteBlock();
        this.damageAmount = cacheProperty.getCache(DamageModifier.ID);
        this.distanceAmount = cacheProperty.getCache(EffectiveRangeModifier.ID);
        // ÃƒÂ©Ã…â€œÃ‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â¯Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£ÃƒÂ¥Ã…Â½Ã‚Â»
        if (bulletData.getBulletAmount() > 1) {
            this.damageModifier = 1f / bulletData.getBulletAmount();
        }
        this.pierce = Mth.clamp(cacheProperty.getCache(PierceModifier.ID), 1, Integer.MAX_VALUE);
        ExplosionData explosionData = cacheProperty.getCache(ExplosionModifier.ID);
        if (explosionData != null) {
            this.explosion = explosionData.isExplode();
            this.explosionDamage = (float) Mth.clamp(explosionData.getDamage() * SyncConfig.DAMAGE_BASE_MULTIPLIER.get(), 0, Float.MAX_VALUE);
            this.explosionRadius = Mth.clamp(explosionData.getRadius(), 0, Float.MAX_VALUE);
            this.explosionKnockback = explosionData.isKnockback();
            // ÃƒÂ©Ã‹Å“Ã‚Â²ÃƒÂ¦Ã‚Â­Ã‚Â¢ÃƒÂ¨Ã‚Â¶Ã…Â ÃƒÂ§Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡
            int delayTickCount = (int)(explosionData.getDelay() * 20);
            if (delayTickCount < 0) {
                delayTickCount = Integer.MAX_VALUE;
            }
            this.explosionDestroyBlock = explosionData.isDestroyBlock() && AmmoConfig.EXPLOSIVE_AMMO_DESTROYS_BLOCK.get();
            this.explosionDelayCount = Math.max(delayTickCount, 1);
        }
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
        double posX = throwerIn.xOld + (throwerIn.getX() - throwerIn.xOld) / 2.0;
        double posY = throwerIn.yOld + (throwerIn.getY() - throwerIn.yOld) / 2.0 + throwerIn.getEyeHeight();
        double posZ = throwerIn.zOld + (throwerIn.getZ() - throwerIn.zOld) / 2.0;
        this.setPos(posX, posY, posZ);
        this.startPos = this.position();
        this.isTracerAmmo = isTracerAmmo;
        this.gunId = gunId;
        this.gunDisplayId = gunDisplayId;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        // TODO: Define synced data here
    }

    @Override
    public void tick() {
        super.tick();
        // ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ TaC ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
        this.onBulletTick();
        // ÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œ
        if (this.level().isClientSide) {
            AmmoParticleSpawner.addParticle(this);
        }
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ¦Ã…Â Ã¢â‚¬ÂºÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ§Ã‚ÂºÃ‚Â¿
        Vec3 movement = this.getDeltaMovement();
        double x = movement.x;
        double y = movement.y;
        double z = movement.z;
        double distance = movement.horizontalDistance();
        this.setYRot((float) Math.toDegrees(Mth.atan2(x, z)));
        this.setXRot((float) Math.toDegrees(Mth.atan2(y, distance)));
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚Â¿Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚ÂÃ‚Â«ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
        this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
        this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°
        double nextPosX = this.getX() + x;
        double nextPosY = this.getY() + y;
        double nextPosZ = this.getZ() + z;
        this.setPos(nextPosX, nextPosY, nextPosZ);
        float friction = this.friction;
        float gravity = this.gravity;
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¦Ã‚Â°Ã‚Â´ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â´
        if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(ParticleTypes.BUBBLE, nextPosX - x * 0.25F, nextPosY - y * 0.25F, nextPosZ - z * 0.25F, x, y, z);
            }
            // ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â°Ã‚Â´ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‹Å“Ã‚Â»ÃƒÂ¥Ã…Â Ã¢â‚¬Âº
            friction = 0.4F;
            gravity *= 0.6F;
        }
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ©Ã‹Å“Ã‚Â»ÃƒÂ¥Ã…Â Ã¢â‚¬ÂºÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ©Ã¢â€šÂ¬Ã…Â¸ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        this.setDeltaMovement(this.getDeltaMovement().scale(1 - friction));
        this.setDeltaMovement(this.getDeltaMovement().add(0, -gravity, 0));
        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸
        if (this.tickCount >= this.life - 1) {
            this.discard();
        }
    }

    // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â 
    protected void onBulletTick() {
        // ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
        if (!this.level().isClientSide()) {
            // ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡
            if (this.explosion) {
                if (this.explosionDelayCount > 0) {
                    this.explosionDelayCount--;
                } else {
                    ExplodeUtil.createExplosion(this.getOwner(), this, this.explosionDamage, this.explosionRadius, this.explosionKnockback, this.explosionDestroyBlock, this.position());
                    // ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ§Ã¢â‚¬Â¢Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â­Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
                    this.discard();
                    return;
                }
            }
            // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€œÃ‚Â¨ tick ÃƒÂ¨Ã‚ÂµÃ‚Â·ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
            Vec3 startVec = this.position();
            // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã…â€œÃ‚Â¨ tick ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
            Vec3 endVec = startVec.add(this.getDeltaMovement());
            // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã¢â‚¬â„¢Ã…Â¾ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹
            HitResult result = BlockRayTrace.rayTraceBlocks(this.level(), new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            BlockHitResult resultB = (BlockHitResult) result;
            if (resultB.getType() != HitResult.Type.MISS) {
                // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
                endVec = resultB.getLocation();
            }

            List<EntityResult> hitEntities = null;
            // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã‚ÂµÃ¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚Âº 1 ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡
            if (this.pierce <= 1 || this.explosion) {
                EntityResult entityResult = EntityUtil.findEntityOnPath(this, startVec, endVec);
                // ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ list
                if (entityResult != null) {
                    hitEntities = Collections.singletonList(entityResult);
                }
            } else {
                hitEntities = EntityUtil.findEntitiesOnPath(this, startVec, endVec);
            }
            // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œ
            if (hitEntities != null && !hitEntities.isEmpty()) {
                EntityResult[] hitEntityResult = hitEntities.toArray(new EntityResult[0]);
                // ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¦Ã…Â½Ã¢â‚¬â„¢ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ§Ã¢â‚¬Â¦Ã‚Â§ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã‚ÂÃ¢â‚¬Â¡ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¦Ã…Â½Ã¢â‚¬â„¢ÃƒÂ¥Ã‚ÂºÃ‚Â
                for (int i = 0; (i < this.pierce || i < 1) && i < (hitEntityResult.length - 1); i++) {
                    int k = i;
                    for (int j = i + 1; j < hitEntityResult.length; j++) {
                        if (hitEntityResult[j].hitVec.distanceTo(startVec) < hitEntityResult[k].hitVec.distanceTo(startVec)) {
                            k = j;
                        }
                    }
                    EntityResult t = hitEntityResult[i];
                    hitEntityResult[i] = hitEntityResult[k];
                    hitEntityResult[k] = t;
                }
                for (EntityResult entityResult : hitEntityResult) {
                    result = new TacHitResult(entityResult);
                    this.onHitEntity((TacHitResult) result, startVec, endVec);
                    this.pierce--;
                    if (this.pierce < 1 || this.explosion) {
                        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â£Ã…Â¾ÃƒÂ¨Ã‚Â¡Ã…â€™
                        this.discard();
                        return;
                    }
                }
            }
            this.onHitBlock(resultB, startVec, endVec);
        }
    }

    public void shoot(double pitch, double yaw, float pVelocity, Vector2d vector2d) {
        Vector3d left = new Vector3d(vector2d.x, vector2d.y, 8);

        left.rotateX(pitch * Mth.DEG_TO_RAD);
        left.rotateY(-yaw * Mth.DEG_TO_RAD);

        Vec3 vec3 = new Vec3(left.x, left.y, left.z).normalize().scale(pVelocity);

        this.setDeltaMovement(vec3.x, vec3.y, vec3.z);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, Vector2d vector2d) {
        this.shoot(pX, pY, pVelocity, vector2d);
        Vec3 vec3 = pShooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, pShooter.onGround() ? 0.0D : vec3.y, vec3.z));
    }

    public record MaybeMultipartEntity(
            Entity hitPart,
            Entity core
    ) {
        public static MaybeMultipartEntity of(Entity hitPart) {
            var core = (hitPart instanceof PartEntity<?> part)
                    ? part.getParent()
                    : hitPart;
            return new MaybeMultipartEntity(hitPart, core);
        }
    }

    protected void onHitEntity(TacHitResult result, Vec3 startVec, Vec3 endVec) {
        if (result.getEntity() instanceof ITargetEntity targetEntity) {
            DamageSource source = this.damageSources().thrown(this, this.getOwner());
            targetEntity.onProjectileHit(this, result, source, this.getDamage(result.getLocation()));
            // ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ©Ã‚ÂÃ‚Â¶ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾
            return;
        }
        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œPreÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¿Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¿Ã‚Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¯
        Entity entity = result.getEntity();
        @Nullable Entity owner = this.getOwner();
        // ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦
        LivingEntity attacker = owner instanceof LivingEntity ? (LivingEntity) owner : null;
        var sources = createDamageSources(MaybeMultipartEntity.of(entity));
        boolean headshot = result.isHeadshot();
        float damage = this.getDamage(result.getLocation());
        float headShotMultiplier = Math.max(this.headShot, 0);
        // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚Â¸Ã†â€™PreÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
        var preEvent = new EntityHurtByGunEvent.Pre(this, entity, attacker, this.gunId, this.gunDisplayId, damage, sources, headshot, headShotMultiplier, LogicalSide.SERVER);
        // Updated event bus call
        boolean cancelled = NeoForge.EVENT_BUS.post(preEvent).isCanceled();
        if (cancelled) {
            return;
        }
        // ÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã¢â‚¬ÂÃ‚Â±PreÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        entity = preEvent.getHurtEntity();
        // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â®ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        var parts = MaybeMultipartEntity.of(entity);
        attacker = preEvent.getAttacker();
        var newGunId = preEvent.getGunId();
        damage = preEvent.getBaseAmount();
        sources = Pair.of(preEvent.getDamageSource(NON_ARMOR_PIERCING), preEvent.getDamageSource(ARMOR_PIERCING));
        headshot = preEvent.isHeadShot();
        headShotMultiplier = preEvent.getHeadshotMultiplier();
        if (entity == null) {
            return;
        }
        // ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ§Ã¢â‚¬Â¡Ã†â€™
        if (this.igniteEntity && AmmoConfig.IGNITE_ENTITY.get()) {
            ((LivingEntity) entity).setSecondsOnFire(this.igniteEntityTime);
            // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ¤Ã‚ÂºÃ‹â€ ÃƒÂ§Ã‚Â²Ã¢â‚¬â„¢ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œ
            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.LAVA, entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ(), 1, 0, 0, 0, 0);
            }
        }
        // TODO ÃƒÂ¦Ã…Â¡Ã‚Â´ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°ÃƒÂ¦Ã…Â¡Ã‚Â´ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¨Ã‚Â¾Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¦Ã…Â¡Ã‚Â´ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ flag
        if (headshot) {
            // ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã‚Â´ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¦Ã‹Å“Ã‚Â¯ 1x
            damage *= headShotMultiplier;
        }
        // ÃƒÂ¥Ã‚Â¯Ã‚Â¹ LivingEntity ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â€šÂ¬Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â¼Ã‚ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â°
        if (parts.core() instanceof LivingEntity livingCore) {
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â€šÂ¬Ã¢â€šÂ¬ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ©Ã¢â€šÂ¬Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â¼Ã‚ÂºÃƒÂ¥Ã‚ÂºÃ‚Â¦
            KnockBackModifier modifier = KnockBackModifier.fromLivingEntity(livingCore);
            modifier.setKnockBackStrength(this.knockback);
            // ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³
            tacAttackEntity(parts, damage, sources);
            // ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ¤Ã‚Â½Ã‚Â
            modifier.resetKnockBackStrength();
        } else {
            // ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³
            tacAttackEntity(parts, damage, sources);
        }
        // ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
        if (this.explosion) {
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
            parts.core().invulnerableTime = 0;
            ExplodeUtil.createExplosion(this.getOwner(), this, this.explosionDamage, this.explosionRadius, this.explosionKnockback, this.explosionDestroyBlock, result.getLocation());
        }
        // ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¥Ã‚Â¯Ã‚Â¹ LivingEntity ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‚ÂÃ¢â€šÂ¬ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡
        if (parts.core() instanceof LivingEntity livingCore) {
            // ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¥ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯
            if (!level().isClientSide) {
                int attackerId = attacker == null ? 0 : attacker.getId();
                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã¢â‚¬ÂÃ…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¦Ã‚Â­Ã‚Â»ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â 
                if (livingCore.isDeadOrDying()) {
                    // Updated event bus call
                    NeoForge.EVENT_BUS.post(new EntityKillByGunEvent(this, livingCore, attacker, newGunId, gunDisplayId, damage, sources, headshot, headShotMultiplier, LogicalSide.SERVER));
                    NetworkHandler.sendToClientPlayer(new ServerMessageGunKill(getId(), livingCore.getId(), attackerId, newGunId, gunDisplayId, damage, headshot, headShotMultiplier), (ServerPlayer) livingCore);
                } else {
                    // Updated event bus call
                    NeoForge.EVENT_BUS.post(new EntityHurtByGunEvent.Post(this, livingCore, attacker, newGunId, gunDisplayId, damage, sources, headshot, headShotMultiplier, LogicalSide.SERVER));
                    NetworkHandler.sendToClientPlayer(new ServerMessageGunHurt(getId(), livingCore.getId(), attackerId, newGunId, gunDisplayId, damage, headshot, headShotMultiplier), (ServerPlayer) livingCore);
                }
            }
        }
    }

    protected void onHitBlock(BlockHitResult result, Vec3 startVec, Vec3 endVec) {
        if (result.getType() == HitResult.Type.MISS) {
            return;
        }
        BlockPos pos = result.getBlockPos();
        Vec3 hitVec = result.getLocation();
        // ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
        // ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¨Ã‚Â®Ã‚Â©ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¥Ã…Â½Ã…Â¸ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â²ÃƒÂ©Ã¢â‚¬â„¢Ã…Â¸ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¥Ã¢â€šÂ¬Ã¢â‚¬â„¢ÃƒÂ©Ã‚ÂÃ‚Â¶ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
        // Updated event bus call
        if (NeoForge.EVENT_BUS.post(new AmmoHitBlockEvent(this.level(), result, this.level().getBlockState(pos))).isCanceled()) {
            return;
        }
        super.onHitBlock(result);
        // ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸
        if (this.explosion) {
            ExplodeUtil.createExplosion(this.getOwner(), this, this.explosionDamage, this.explosionRadius, this.explosionKnockback, this.explosionDestroyBlock, hitVec);
            // ÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¸ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ§Ã‚Â»Ã¢â‚¬Å“ÃƒÂ¦Ã‚ÂÃ…Â¸ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ§Ã¢â‚¬Â¢Ã¢â€žÂ¢ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â­Ã¢â‚¬ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
            this.discard();
            return;
        }
        // ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã‚Â­Ã¢â‚¬ÂÃƒÂ¤Ã‚Â¸Ã…Â½ÃƒÂ§Ã¢â‚¬Å¡Ã‚Â¹ÃƒÂ§Ã¢â‚¬Â¡Ã†â€™ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
        if (this.level() instanceof ServerLevel serverLevel) {
            BulletHoleOption bulletHoleOption = new BulletHoleOption(result.getDirection(), result.getBlockPos(), this.ammoId.toString(), this.gunId.toString(), this.gunDisplayId.toString());
            serverLevel.sendParticles(bulletHoleOption, hitVec.x, hitVec.y, hitVec.z, 1, 0, 0, 0, 0);
            if (this.igniteBlock) {
                serverLevel.sendParticles(ParticleTypes.LAVA, hitVec.x, hitVec.y, hitVec.z, 1, 0, 0, 0, 0);
            }
        }
        if (this.igniteBlock && AmmoConfig.IGNITE_BLOCK.get()) {
            BlockPos offsetPos = pos.relative(result.getDirection());
            if (BaseFireBlock.canBePlacedAt(this.level(), offsetPos, result.getDirection())) {
                BlockState fireState = BaseFireBlock.getState(this.level(), offsetPos);
                this.level().setBlock(offsetPos, fireState, Block.UPDATE_ALL_IMMEDIATE);
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.LAVA, hitVec.x - 1.0 + this.random.nextDouble() * 2.0, hitVec.y, hitVec.z - 1.0 + this.random.nextDouble() * 2.0, 4, 0, 0, 0, 0);
            }
        }
        this.discard();
    }

    // ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¨Ã‚Â·Ã‚ÂÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¨Ã‚Â¡Ã‚Â°ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ¨Ã‚Â®Ã‚Â¡
    public float getDamage(Vec3 hitVec) {
        // ÃƒÂ©Ã‚ÂÃ‚ÂÃƒÂ¥Ã…Â½Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­
        double playerDistance = hitVec.distanceTo(this.startPos);
        for (DistanceDamagePair pair : this.damageAmount) {
            float effectiveDistance = this.damageAmount.get(0).getDistance() == pair.getDistance() ? this.distanceAmount : pair.getDistance();
            if (playerDistance < effectiveDistance) {
                float damage = pair.getDamage();
                return Math.max(damage * this.damageModifier, 0F);
            }
        }
        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¥Ã‚Â¿Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â°ÃƒÂ¥Ã¢â‚¬Â Ã¢â€žÂ¢ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¦Ã‹â€ Ã¢â‚¬ËœÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â½Ã‚Â ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¤Ã‚Â¸Ã‚Âº 0
        return 0;
    }

    /**
     * @return Pair<ÃƒÂ©Ã‚ÂÃ…Â¾ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¦Ã‚ÂºÃ‚Â>
     */
    private Pair<DamageSource, DamageSource> createDamageSources(MaybeMultipartEntity parts) {
        DamageSource source1, source2;
        var hitPartType = parts.hitPart().getType();
        var directCause = hitPartType.is(PRETEND_MELEE_DAMAGE_ON) ? this.getOwner() : this;
        // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ¦Ã…â€œÃ‚Â«ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³
        if (hitPartType.is(USE_MAGIC_DAMAGE_ON)) {
            source1 = source2 = this.damageSources().indirectMagic(this, getOwner());
        } else if (hitPartType.is(USE_VOID_DAMAGE_ON)) {
            source1 = ModDamageTypes.Sources.bulletVoid(this.level().registryAccess(), directCause, this.getOwner(), false);
            source2 = ModDamageTypes.Sources.bulletVoid(this.level().registryAccess(), directCause, this.getOwner(), true);
        } else {
            source1 = ModDamageTypes.Sources.bullet(this.level().registryAccess(), directCause, this.getOwner(), false);
            source2 = ModDamageTypes.Sources.bullet(this.level().registryAccess(), directCause, this.getOwner(), true);
        }
        return Pair.of(source1, source2);
    }

    private void tacAttackEntity(MaybeMultipartEntity parts, float damage, Pair<DamageSource, DamageSource> sources) {
        var source1 = sources.getLeft();
        var source2 = sources.getRight();
        // ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â®ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã‚Â¯Ã¢â‚¬ÂÃƒÂ¤Ã‚Â¾Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€
        float armorDamagePercent = Mth.clamp(this.armorIgnore, 0.0F, 1.0F);
        float normalDamagePercent = 1 - armorDamagePercent;
        // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
        parts.core().invulnerableTime = 0;
        // ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â®ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³
        parts.hitPart().hurt(source1, damage * normalDamagePercent);
        // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã¢â‚¬Â¢Ã…â€™ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´
        parts.core().invulnerableTime = 0;
        // ÃƒÂ§Ã‚Â©Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã‚Â³
        parts.hitPart().hurt(source2, damage * armorDamagePercent);
    }

    public Packet<ClientGamePacketListener> getSpawnPacket() {
        return IEntityWithComplexSpawn.super.getSpawnPacket();
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeFloat(getXRot());
        buffer.writeFloat(getYRot());
        buffer.writeDouble(getDeltaMovement().x);
        buffer.writeDouble(getDeltaMovement().y);
        buffer.writeDouble(getDeltaMovement().z);
        Entity entity = getOwner();
        buffer.writeInt(entity != null ? entity.getId() : 0);
        buffer.writeResourceLocation(ammoId);
        buffer.writeFloat(this.gravity);
        buffer.writeBoolean(this.explosion);
        buffer.writeBoolean(this.igniteEntity);
        buffer.writeBoolean(this.igniteBlock);
        buffer.writeFloat(this.explosionRadius);
        buffer.writeFloat(this.explosionDamage);
        buffer.writeInt(this.life);
        buffer.writeFloat(this.speed);
        buffer.writeFloat(this.friction);
        buffer.writeInt(this.pierce);
        buffer.writeBoolean(this.isTracerAmmo);
        buffer.writeResourceLocation(this.gunId);
        buffer.writeResourceLocation(this.gunDisplayId);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        setXRot(additionalData.readFloat());
        setYRot(additionalData.readFloat());
        setDeltaMovement(additionalData.readDouble(), additionalData.readDouble(), additionalData.readDouble());
        Entity entity = this.level().getEntity(additionalData.readInt());
        if (entity != null) {
            this.setOwner(entity);
        }
        this.ammoId = additionalData.readResourceLocation();
        this.gravity = additionalData.readFloat();
        this.explosion = additionalData.readBoolean();
        this.igniteEntity = additionalData.readBoolean();
        this.igniteBlock = additionalData.readBoolean();
        this.explosionRadius = additionalData.readFloat();
        this.explosionDamage = additionalData.readFloat();
        this.life = additionalData.readInt();
        this.speed = additionalData.readFloat();
        this.friction = additionalData.readFloat();
        this.pierce = additionalData.readInt();
        this.isTracerAmmo = additionalData.readBoolean();
        this.gunId = additionalData.readResourceLocation();
        this.gunDisplayId = additionalData.readResourceLocation();
    }

    public ResourceLocation getAmmoId() {
        return ammoId;
    }

    public ResourceLocation getGunId() {
        return gunId;
    }

    public ResourceLocation getGunDisplayId() {
        return gunDisplayId;
    }

    public boolean isTracerAmmo() {
        return isTracerAmmo;
    }

    public RandomSource getRandom() {
        return this.random;
    }

    public float getCameraYRot() {
        return cameraYRot;
    }

    public void setCameraYRot(float cameraYRot) {
        this.cameraYRot = cameraYRot;
    }

    public float getCameraXRot() {
        return cameraXRot;
    }

    public void setCameraXRot(float cameraXRot) {
        this.cameraXRot = cameraXRot;
    }

    public Vector3f getFirstPersonRenderOffset() {
        return firstPersonRenderOffset;
    }

    public void setFirstPersonRenderOffset(Vector3f originRenderOffset) {
        this.firstPersonRenderOffset = originRenderOffset;
    }

    public Optional<float[]> getTracerColorOverride() {
        var pd = getPersistentData();
        if (!pd.contains(TRACER_COLOR_OVERRIDER_KEY, Tag.TAG_INT_ARRAY)) {
            return Optional.empty();
        } else {
            var ints = pd.getIntArray(TRACER_COLOR_OVERRIDER_KEY);
            // ÃƒÂ¨Ã‚Â¯Ã‚Â·ÃƒÂ©Ã‚ÂÃ‚Â¿ÃƒÂ¥Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ 1 ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¨Ã¢â€šÂ¬Ã¢â‚¬Â¦ 2 ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
            // ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ 1~2 ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¼Ã‹Å“ÃƒÂ©Ã¢â‚¬ÂºÃ¢â‚¬Â¦ÃƒÂ¥Ã…â€œÃ‚Â°ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã‚Â¼Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂµÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â¿ÃƒÂ¥Ã‚Â´Ã‚Â©ÃƒÂ¦Ã‚ÂºÃ†â€™ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â½Ã…â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â½Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â½ :(
            switch (ints.length) {
                case 0:
                    return Optional.empty();
                case 1: {
                    var albedo = ints[0] / 255F;
                    return Optional.of(new float[]{albedo, albedo, albedo, 1});
                }
                case 2: {
                    var albedo = ints[0] / 255F;
                    var alpha = ints[1] / 255F;
                    return Optional.of(new float[]{albedo, albedo, albedo, alpha});
                }
                case 3: {
                    var r = ints[0] / 255F;
                    var g = ints[1] / 255F;
                    var b = ints[2] / 255F;
                    return Optional.of(new float[]{r, g, b, 1});
                }
                default: {
                    var r = ints[0] / 255F;
                    var g = ints[1] / 255F;
                    var b = ints[2] / 255F;
                    var a = ints[3] / 255F;
                    return Optional.of(new float[]{r, g, b, a});
                }
            }
        }
    }

    public float getTracerSizeOverride() {
        var pd = getPersistentData();
        return pd.contains(TRACER_SIZE_OVERRIDER_KEY, Tag.TAG_ANY_NUMERIC) ? pd.getFloat(TRACER_SIZE_OVERRIDER_KEY) : 1;
    }

    @Override
    public boolean ownedBy(@Nullable Entity entity) {
        if (entity == null) {
            return false;
        }
        return super.ownedBy(entity);
    }

    public static class EntityResult {
        private final Entity entity;
        private final Vec3 hitVec;
        private final boolean headshot;

        public EntityResult(Entity entity, Vec3 hitVec, boolean headshot) {
            this.entity = entity;
            this.hitVec = hitVec;
            this.headshot = headshot;
        }

        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ¤Ã‚Â½Ã¢â‚¬Å“
        public Entity getEntity() {
            return this.entity;
        }

        // ÃƒÂ¥Ã‚Â­Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¥Ã¢â‚¬ËœÃ‚Â½ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®
        public Vec3 getHitPos() {
            return this.hitVec;
        }

        // ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã‹â€ Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã‚Â´
        public boolean isHeadshot() {
            return this.headshot;
        }
    }
}






























































