package com.tacz.guns.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tacz.guns.GunMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializador para receitas de mesa de armeiro.
 * Responsável por ler/escrever receitas de/para JSON e pacotes de rede.
 */
public class GunSmithTableSerializer implements RecipeSerializer<GunSmithTableRecipe> {

    @Override
    public GunSmithTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        // Ler o grupo da receita
        ResourceLocation tabId = new ResourceLocation(GsonHelper.getAsString(json, "tab", GunMod.MOD_ID + ":guns"));
        
        // Ler ingredientes
        JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
        List<GunSmithTableIngredient> inputs = new ArrayList<>();
        
        for (JsonElement element : ingredients) {
            JsonObject ingredientObject = element.getAsJsonObject();
            Ingredient ingredient = Ingredient.fromJson(ingredientObject.get("ingredient"));
            int count = GsonHelper.getAsInt(ingredientObject, "count", 1);
            inputs.add(new GunSmithTableIngredient(ingredient, count));
        }
        
        // Ler resultado
        JsonObject resultObj = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = ShapedRecipe.itemStackFromJson(resultObj);
        ResourceLocation group = new ResourceLocation(GsonHelper.getAsString(resultObj, "group", tabId.toString()));
        
        RawGunTableResult gunResult = new RawGunTableResult(result, group);
        
        return new GunSmithTableRecipe(recipeId, tabId, inputs, gunResult);
    }

    @Nullable
    @Override
    public GunSmithTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        // Ler o grupo da receita
        ResourceLocation tabId = buffer.readResourceLocation();
        
        // Ler ingredientes
        int ingredientCount = buffer.readVarInt();
        List<GunSmithTableIngredient> inputs = new ArrayList<>();
        
        for (int i = 0; i < ingredientCount; i++) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int count = buffer.readVarInt();
            inputs.add(new GunSmithTableIngredient(ingredient, count));
        }
        
        // Ler resultado
        ItemStack result = buffer.readItem();
        ResourceLocation group = buffer.readResourceLocation();
        
        RawGunTableResult gunResult = new RawGunTableResult(result, group);
        
        return new GunSmithTableRecipe(recipeId, tabId, inputs, gunResult);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, GunSmithTableRecipe recipe) {
        // Escrever o grupo da receita
        buffer.writeResourceLocation(recipe.getTab());
        
        // Escrever ingredientes
        buffer.writeVarInt(recipe.getInputs().size());
        for (GunSmithTableIngredient input : recipe.getInputs()) {
            input.getIngredient().toNetwork(buffer);
            buffer.writeVarInt(input.getCount());
        }
        
        // Escrever resultado
        buffer.writeItem(recipe.getResult().getResult());
        buffer.writeResourceLocation(recipe.getResult().getGroup());
    }
}
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tacz.guns.crafting.result.GunSmithTableResult;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import com.tacz.guns.crafting.result.GunSmithTableResult;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.recipe.TableRecipe;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * ÃƒÂ¥Ã‚Â·Ã‚Â¥ÃƒÂ¤Ã‚Â½Ã…â€œÃƒÂ¥Ã‚ÂÃ‚Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬â€ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
 */
public class GunSmithTableSerializer implements RecipeSerializer<GunSmithTableRecipe> {
    private static final MapCodec<GunSmithTableRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(GunSmithTableRecipe::getId),
                    GunSmithTableResult.CODEC.fieldOf("result").forGetter(GunSmithTableRecipe::getResult),
                    GunSmithTableIngredient.LIST_CODEC.fieldOf("materials").forGetter(GunSmithTableRecipe::getInputs)
            ).apply(instance, GunSmithTableRecipe::new)
    );

    @Override
    public MapCodec<GunSmithTableRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, GunSmithTableRecipe> streamCodec() {
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    private GunSmithTableRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        ResourceLocation recipeId = buf.readResourceLocation();
        int size = buf.readInt();
        List<GunSmithTableIngredient> ingredients = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ingredients.add(new GunSmithTableIngredient(Ingredient.CONTENTS_STREAM_CODEC.decode(buf), buf.readInt()));
        }
        ItemStack resultItem = ItemStack.STREAM_CODEC.decode(buf);
        ResourceLocation group = buf.readResourceLocation();
        GunSmithTableResult result = new GunSmithTableResult(resultItem, group);
        return new GunSmithTableRecipe(recipeId, result, ingredients);
    }

    private void toNetwork(RegistryFriendlyByteBuf buf, GunSmithTableRecipe recipe) {
        buf.writeResourceLocation(recipe.getId());
        buf.writeInt(recipe.getInputs().size());
        for (GunSmithTableIngredient ingredient : recipe.getInputs()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient.getIngredient());
            buf.writeInt(ingredient.getCount());
        }
        ItemStack.STREAM_CODEC.encode(buf, recipe.getResult().getResult());
        buf.writeResourceLocation(recipe.getResult().getGroup());
    }
}



























































package com.tacz.guns.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tacz.guns.GunMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Serializador para receitas de mesa de armeiro.
 * Responsável por ler/escrever receitas de/para JSON e pacotes de rede.
 */
public class GunSmithTableSerializer implements RecipeSerializer<GunSmithTableRecipe> {

    @Override
    public GunSmithTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        // Ler o grupo da receita
        ResourceLocation tabId = new ResourceLocation(GsonHelper.getAsString(json, "tab", GunMod.MOD_ID + ":guns"));
        
        // Ler ingredientes
        JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
        List<GunSmithTableIngredient> inputs = new ArrayList<>();
        
        for (JsonElement element : ingredients) {
            JsonObject ingredientObject = element.getAsJsonObject();
            Ingredient ingredient = Ingredient.fromJson(ingredientObject.get("ingredient"));
            int count = GsonHelper.getAsInt(ingredientObject, "count", 1);
            inputs.add(new GunSmithTableIngredient(ingredient, count));
        }
        
        // Ler resultado
        JsonObject resultObj = GsonHelper.getAsJsonObject(json, "result");
        ItemStack result = ShapedRecipe.itemStackFromJson(resultObj);
        ResourceLocation group = new ResourceLocation(GsonHelper.getAsString(resultObj, "group", tabId.toString()));
        
        RawGunTableResult gunResult = new RawGunTableResult(result, group);
        
        return new GunSmithTableRecipe(recipeId, tabId, inputs, gunResult);
    }

    @Nullable
    @Override
    public GunSmithTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        // Ler o grupo da receita
        ResourceLocation tabId = buffer.readResourceLocation();
        
        // Ler ingredientes
        int ingredientCount = buffer.readVarInt();
        List<GunSmithTableIngredient> inputs = new ArrayList<>();
        
        for (int i = 0; i < ingredientCount; i++) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            int count = buffer.readVarInt();
            inputs.add(new GunSmithTableIngredient(ingredient, count));
        }
        
        // Ler resultado
        ItemStack result = buffer.readItem();
        ResourceLocation group = buffer.readResourceLocation();
        
        RawGunTableResult gunResult = new RawGunTableResult(result, group);
        
        return new GunSmithTableRecipe(recipeId, tabId, inputs, gunResult);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, GunSmithTableRecipe recipe) {
        // Escrever o grupo da receita
        buffer.writeResourceLocation(recipe.getTab());
        
        // Escrever ingredientes
        buffer.writeVarInt(recipe.getInputs().size());
        for (GunSmithTableIngredient input : recipe.getInputs()) {
            input.getIngredient().toNetwork(buffer);
            buffer.writeVarInt(input.getCount());
        }
        
        // Escrever resultado
        buffer.writeItem(recipe.getResult().getResult());
        buffer.writeResourceLocation(recipe.getResult().getGroup());
    }
}



