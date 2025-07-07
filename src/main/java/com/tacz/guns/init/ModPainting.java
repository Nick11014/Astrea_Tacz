package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
// TODO: Re-enable when PaintingVariant APIs are clarified for NeoForge 1.21.1
// import net.neoforged.neoforge.registries.DeferredRegister;
// import net.neoforged.neoforge.registries.DeferredHolder;

public class ModPainting {
    // TODO: Re-enable when PaintingVariant registration is understood in NeoForge 1.21.1
    // public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(???, GunMod.MOD_ID);
    
    // TODO: Fix constructor - PaintingVariant now requires (int, int, ResourceLocation) in 1.21.1
    // public static final DeferredHolder<PaintingVariant, PaintingVariant> BLOOD_STRIKE_1 = PAINTINGS.register("blood_strike_1", 
    //     () -> new PaintingVariant(32, 32, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "blood_strike_1")));
}
