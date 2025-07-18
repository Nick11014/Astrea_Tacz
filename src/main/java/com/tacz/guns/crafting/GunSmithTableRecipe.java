package com.tacz.guns.crafting;

import com.tacz.guns.GunMod;
import com.tacz.guns.init.ModRecipe;
import net.minecraft.core.RegistryAccess;
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
public class GunSmithTableRecipe implements Recipe<SimpleContainer> {
    private final List<GunSmithTableIngredient> inputs;
    private final RawGunTableResult result;
    private final ResourceLocation tabId;

    public GunSmithTableRecipe(List<GunSmithTableIngredient> inputs, RawGunTableResult result) {
        this(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "guns"), inputs, result);
    }
    
    public GunSmithTableRecipe(ResourceLocation tabId, List<GunSmithTableIngredient> inputs, RawGunTableResult result) {
        this.tabId = tabId;
        this.inputs = inputs;
        this.result = result;
    }
    
    public ResourceLocation getTab() {
        return tabId;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, net.minecraft.core.HolderLookup.Provider provider) {
        return this.getResultItem(provider).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(net.minecraft.core.HolderLookup.Provider provider) {
        return this.result.getResult();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipe.GUN_SMITH_TABLE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipe.GUN_SMITH_TABLE_CRAFTING.get();
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

    public void init() {
        // TODO: Método vazio para inicialização futura, se necessário
    }
}
