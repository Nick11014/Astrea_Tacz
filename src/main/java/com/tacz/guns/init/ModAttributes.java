package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, GunMod.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> BULLET_RESISTANCE = ATTRIBUTES.register("bullet_resistance",
            () -> new RangedAttribute("attribute.name.tacz.bullet_resistance", 0.0D, 0.0D, 1.0D).setSyncable(true));

    @SubscribeEvent
    public static void addAttributes(EntityAttributeModificationEvent event) {
        List<EntityType<? extends LivingEntity>> entityTypes = event.getTypes();
        entityTypes.forEach((e) -> {
            Class<? extends Entity> baseClass = e.getBaseClass();
            if (baseClass.isAssignableFrom(LivingEntity.class)) {
                event.add(e, BULLET_RESISTANCE);
            }
        });
    }

//
//    public static final DeferredHolder<Attribute, Attribute> WEIGHT_CAPACITY = ATTRIBUTES.register("weight_capacity",
//            () -> new RangedAttribute("attribute.name.tacz.weight_capacity", 0.0D, -1024D, 1024.0D).setSyncable(true));
}































































