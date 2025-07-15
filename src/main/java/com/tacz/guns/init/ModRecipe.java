package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// TODO: Re-enable when all crafting classes are available
import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.crafting.GunSmithTableSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registro de receitas baseado no padrÃƒÆ’Ã‚Â£o do SuperbWarfare 1.21.1
 * Usando implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima atÃƒÆ’Ã‚Â© as classes de crafting estarem disponÃƒÆ’Ã‚Â­veis
 */
public class ModRecipe {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, GunMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, GunMod.MOD_ID);

    // TODO: Re-enable when crafting classes are available
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GunSmithTableRecipe>> GUN_SMITH_TABLE_RECIPE_SERIALIZER = 
        RECIPE_SERIALIZERS.register("gun_smith_table_crafting", GunSmithTableSerializer::new);
        
    public static final DeferredHolder<RecipeType<?>, RecipeType<GunSmithTableRecipe>> GUN_SMITH_TABLE_CRAFTING = 
        RECIPE_TYPES.register("gun_smith_table_crafting", () -> RecipeType.simple(GunMod.loc("gun_smith_table_crafting")));
    
}































































