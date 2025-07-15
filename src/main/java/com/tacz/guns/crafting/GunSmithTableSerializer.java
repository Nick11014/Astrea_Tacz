package com.tacz.guns.crafting;

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































































