package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// TODO: Re-enable when all entity classes are available
// import com.tacz.guns.entity.EntityKineticBullet;
// import com.tacz.guns.entity.TargetMinecart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registro de entidades baseado no padrÃƒÆ’Ã‚Â£o do SuperbWarfare 1.21.1
 * Usando implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima atÃƒÆ’Ã‚Â© as classes de entidade estarem disponÃƒÆ’Ã‚Â­veis
 */
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, GunMod.MOD_ID);

    // TODO: Re-enable when entity classes are available
    // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima com placeholders - entidades sÃƒÆ’Ã‚Â£o complexas e requerem implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o adequada
    /*
    public static final DeferredHolder<EntityType<?>, EntityType<EntityKineticBullet>> BULLET = ENTITY_TYPES.register("bullet", 
        () -> EntityType.Builder.<EntityKineticBullet>of(EntityKineticBullet::new, MobCategory.MISC)
                .setShouldReceiveVelocityUpdates(false)
                .noSave()
                .setTrackingRange(64)
                .setUpdateInterval(1)
                .sized(0.25f, 0.25f)
                .build("bullet"));
                
    public static final DeferredHolder<EntityType<?>, EntityType<TargetMinecart>> TARGET_MINECART = ENTITY_TYPES.register("target_minecart", 
        () -> EntityType.Builder.<TargetMinecart>of(TargetMinecart::new, MobCategory.MISC)
                .setTrackingRange(64)
                .setUpdateInterval(3)
                .sized(0.98F, 0.7F)
                .build("target_minecart"));
    */
    
    // ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o estÃƒÆ’Ã‚Â¡ sendo postergada atÃƒÆ’Ã‚Â© que todas as classes de entidade estejam prontas
    // As entidades requerem implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o completa e nÃƒÆ’Ã‚Â£o apenas placeholders simples
}































































