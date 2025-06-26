package com.tacz.guns.init;

// import com.tacz.guns.GunMod; // TODO: Re-enable when GunMod is available
// import com.tacz.guns.entity.EntityKineticBullet; // TODO: Re-enable when entity classes are available
// import com.tacz.guns.entity.TargetMinecart; // TODO: Re-enable when entity classes are available
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEntities {
    // TODO: Replace with GunMod.MOD_ID when GunMod is available
    private static final String MOD_ID = "tacz";
    
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MOD_ID);

    // TODO: Re-enable when entity classes are available
    /*
    public static DeferredHolder<EntityType<?>, EntityType<EntityKineticBullet>> BULLET = ENTITY_TYPES.register("bullet", () -> EntityKineticBullet.TYPE);
    public static DeferredHolder<EntityType<?>, EntityType<TargetMinecart>> TARGET_MINECART = ENTITY_TYPES.register("target_minecart", () -> TargetMinecart.TYPE);
    */
}
