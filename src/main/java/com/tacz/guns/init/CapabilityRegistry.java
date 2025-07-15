package com.tacz.guns.init;

import com.tacz.guns.entity.sync.core.DataHolder;
import com.tacz.guns.entity.sync.core.SyncedEntityData;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = com.tacz.guns.GunMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CapabilityRegistry {
    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        // Registra o provedor de capability para entidades que possuem dados sincronizados
        event.registerEntity(
            ModCapabilities.ENTITY_DATA_HOLDER,
            null, // EntityType is now required, using null for wildcard
            (entity, context) -> {
                if (SyncedEntityData.instance().hasSyncedDataKey(entity)) {
                    return new DataHolder();
                }
                return null;
            }
        );
    }
}































































