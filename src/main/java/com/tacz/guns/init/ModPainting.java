package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPainting {

    /**
     * Registro diferido para as variantes de pintura.
     * Em vez de um registro específico, agora usamos o registro principal de 'PaintingVariant'.
     * O primeiro argumento é a chave do registro, que para variantes de pintura é 'Registries.PAINTING_VARIANT'.
     * O segundo argumento é o ID do seu mod.
     */
    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(Registries.PAINTING_VARIANT, GunMod.MOD_ID);

    /**
     * Registro de uma nova variante de pintura chamada "blood_strike_1".
     * O construtor de PaintingVariant agora requer a largura, a altura e a ResourceLocation da textura do sprite.
     * A largura e a altura são fornecidas em blocos (pixels / 16). Assumindo que a textura original
     * era 32x32 pixels, isso se traduz em uma pintura de 2x2 blocos. Se a intenção era 32x32 blocos,
     * mantenha os valores como 32, 32.
     *
     * A ResourceLocation agora deve apontar para o diretório de texturas de pinturas dentro dos assets do seu mod.
     * Geralmente: "modid:painting/nome_da_pintura"
     */
    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLOOD_STRIKE_1 =
            PAINTING_VARIANTS.register("blood_strike_1", () -> new PaintingVariant(
                    2, // Largura em blocos (width in blocks)
                    2, // Altura em blocos (height in blocks)
                    ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "painting/blood_strike_1")
            ));

    // Exemplo para uma pintura de 32x32 pixels que ocupa 2x2 blocos:
    // public static final DeferredHolder<PaintingVariant, PaintingVariant> BLOOD_STRIKE_1 = PAINTING_VARIANTS.register("blood_strike_1", 
    //      () -> new PaintingVariant(2, 2, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "painting/blood_strike_1")));

    // Se a pintura realmente deve ter 32x32 BLOCOS (o que é enorme), o original estaria correto:
    // public static final DeferredHolder<PaintingVariant, PaintingVariant> BLOOD_STRIKE_1 = PAINTING_VARIANTS.register("blood_strike_1", 
    //      () -> new PaintingVariant(32, 32, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "painting/blood_strike_1")));
}
