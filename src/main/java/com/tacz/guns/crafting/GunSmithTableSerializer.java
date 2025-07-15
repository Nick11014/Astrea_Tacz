package com.tacz.guns.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tacz.guns.GunMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Serializador para receitas de mesa de armeiro, atualizado para NeoForge 1.21.1.
 * Usa Codecs para ler/escrever receitas de/para JSON e pacotes de rede.
 */
public class GunSmithTableSerializer implements RecipeSerializer<GunSmithTableRecipe> {

    // Registro auxiliar para lidar com a lógica onde o 'group' do resultado pode usar o 'tab' como padrão.
    private record IntermediateResult(ItemStack result, Optional<ResourceLocation> group) {}

    private static final Codec<IntermediateResult> INTERMEDIATE_RESULT_CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    // Usa o codec simples para ItemStacks, que lê um objeto com "item" e "count" (opcional).
                    ItemStack.SIMPLE_CODEC.forGetter(IntermediateResult::result),
                    ResourceLocation.CODEC.optionalFieldOf("group").forGetter(IntermediateResult::group)
            ).apply(instance, IntermediateResult::new)
    );

    private static final MapCodec<GunSmithTableRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ResourceLocation.CODEC.optionalFieldOf("tab", new ResourceLocation(GunMod.MOD_ID, "guns"))
                            .forGetter(GunSmithTableRecipe::getTab),
                    // Assume que GunSmithTableIngredient.CODEC está definido na classe correspondente
                    GunSmithTableIngredient.CODEC.listOf().fieldOf("ingredients")
                            .forGetter(GunSmithTableRecipe::getInputs),
                    INTERMEDIATE_RESULT_CODEC.fieldOf("result")
                            .forGetter(recipe -> new IntermediateResult(recipe.getResult().getResult(), Optional.of(recipe.getResult().getGroup())))
            ).apply(instance, (tabId, ingredients, intermediateResult) -> {
                // Aplica a lógica de fallback do grupo aqui, durante a decodificação
                ResourceLocation group = intermediateResult.group().orElse(tabId);
                RawGunTableResult result = new RawGunTableResult(intermediateResult.result(), group);
                // O ID da receita agora é gerenciado pelo RecipeHolder, então o removemos do construtor da receita.
                return new GunSmithTableRecipe(tabId, ingredients, result);
            })
    );

    private static final StreamCodec<RegistryFriendlyByteBuf, GunSmithTableRecipe> STREAM_CODEC = StreamCodec.of(
            GunSmithTableSerializer::toNetwork,
            GunSmithTableSerializer::fromNetwork
    );

    @Override
    public MapCodec<GunSmithTableRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, GunSmithTableRecipe> streamCodec() {
        return STREAM_CODEC;
    }

    private static void toNetwork(RegistryFriendlyByteBuf buffer, GunSmithTableRecipe recipe) {
        buffer.writeResourceLocation(recipe.getTab());

        buffer.writeVarInt(recipe.getInputs().size());
        for (GunSmithTableIngredient input : recipe.getInputs()) {
            // Use o StreamCodec moderno para Ingredientes
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, input.getIngredient());
            buffer.writeVarInt(input.getCount());
        }

        // Use o StreamCodec moderno para ItemStacks
        ItemStack.STREAM_CODEC.encode(buffer, recipe.getResult().getResult());
        buffer.writeResourceLocation(recipe.getResult().getGroup());
    }

    private static GunSmithTableRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        ResourceLocation tabId = buffer.readResourceLocation();

        int ingredientCount = buffer.readVarInt();
        List<GunSmithTableIngredient> inputs = new ArrayList<>(ingredientCount);
        for (int i = 0; i < ingredientCount; i++) {
            // Use o StreamCodec moderno para Ingredientes
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            int count = buffer.readVarInt();
            inputs.add(new GunSmithTableIngredient(ingredient, count));
        }

        // Use o StreamCodec moderno para ItemStacks
        ItemStack resultStack = ItemStack.STREAM_CODEC.decode(buffer);
        ResourceLocation group = buffer.readResourceLocation();
        RawGunTableResult gunResult = new RawGunTableResult(resultStack, group);

        return new GunSmithTableRecipe(tabId, inputs, gunResult);
    }
}