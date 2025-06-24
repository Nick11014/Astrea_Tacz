package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GunMod.MOD_ID);

    public static final RegistryObject<SoundEvent> GUN = SOUNDS.register("gun", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GunMod.MOD_ID, "gun")));
    public static final RegistryObject<SoundEvent> TARGET_HIT = SOUNDS.register("target_block_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GunMod.MOD_ID, "target_block_hit")));
}
