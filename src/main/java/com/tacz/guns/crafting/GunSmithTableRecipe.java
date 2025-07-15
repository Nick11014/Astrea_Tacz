package com.tacz.guns.crafting;

import com.tacz.guns.init.ModRecipe;
import com.tacz.guns.inventory.SimpleContainer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * Classe que representa uma receita de mesa de armeiro.
 * Implementa a interface Recipe do Minecraft.
 */
public class GunSmithTableRecipe implements Recipe<com.tacz.guns.crafting.SimpleContainer> {
    private final ResourceLocation id;
    private final ResourceLocation tab;
    private final List<GunSmithTableIngredient> inputs;
    private final RawGunTableResult result;

    public GunSmithTableRecipe(ResourceLocation id, ResourceLocation tab, List<GunSmithTableIngredient> inputs, RawGunTableResult result) {
        this.id = id;
        this.tab = tab;
        this.inputs = inputs;
        this.result = result;
    }

    @Override
    public boolean matches(com.tacz.guns.crafting.SimpleContainer container, Level level) {
        // Implementação simples para verificar se a receita corresponde ao contêiner
        // Na prática, você provavelmente verificaria os ingredientes no contêiner
        return true;
    }

    @Override
    public ItemStack assemble(com.tacz.guns.crafting.SimpleContainer container) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result.getResult();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipe.GUN_SMITH_TABLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipe.GUN_SMITH_TABLE_CRAFTING.get();
    }

    public ResourceLocation getTab() {
        return tab;
    }

    public List<GunSmithTableIngredient> getInputs() {
        return inputs;
    }

    public RawGunTableResult getResult() {
        return result;
    }

    public ItemStack getOutput() {
        return result.getResult();
    }
}

