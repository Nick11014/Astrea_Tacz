package com.tacz.guns.crafting;

import net.minecraft.world.item.crafting.Ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/**
 * Representa um ingrediente para a mesa de armeiro.
 * Contém o ingrediente Minecraft padrão e a quantidade necessária.
 */
public class GunSmithTableIngredient {
    public static final Codec<GunSmithTableIngredient> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(GunSmithTableIngredient::getIngredient),
                    Codec.INT.fieldOf("count").forGetter(GunSmithTableIngredient::getCount)
            ).apply(instance, GunSmithTableIngredient::new)
    );
    
    public static final Codec<List<GunSmithTableIngredient>> LIST_CODEC = CODEC.listOf();
    
    public static final Codec<GunSmithTableIngredient> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Ingredient.CODEC.fieldOf("ingredient").forGetter(GunSmithTableIngredient::getIngredient),
                    Codec.INT.fieldOf("count").forGetter(GunSmithTableIngredient::getCount)
            ).apply(instance, GunSmithTableIngredient::new)
    );
    
    public static final Codec<List<GunSmithTableIngredient>> LIST_CODEC = CODEC.listOf();
    
    private final Ingredient ingredient;
    private final int count;

    public GunSmithTableIngredient(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getCount() {
        return count;
    }
}

