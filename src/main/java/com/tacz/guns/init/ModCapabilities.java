package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import com.tacz.guns.entity.sync.core.DataHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;

public class ModCapabilities {
    public static final EntityCapability<DataHolder, Void> ENTITY_DATA_HOLDER =
        EntityCapability.create(
            ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "entity_data_holder"),
            DataHolder.class,
            Void.class
        );
}































































