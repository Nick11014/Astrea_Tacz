package com.tacz.guns.api;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * API principal do TacZ para acesso a dados de armas, munições e acessórios.
 * <p>
 * MIGRAÇÃO 1.21.1: Implementação mínima funcional que mantém todas as assinaturas
 * originais mas retorna valores padrão seguros até que os managers sejam habilitados.
 */
public final class TimelessAPI {

    // ===== MÉTODOS DE DISPLAY CLIENT-SIDE =====

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientGunIndex> getGunDisplay(ItemStack stack) {
        // TODO: Retornar GunDisplayInstance quando disponível
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getGunDisplay(ResourceLocation displayId, ResourceLocation fallbackGunId) {
        // TODO: Retornar GunDisplayInstance quando disponível
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getClientGunIndex(ResourceLocation gunId) {
        // TODO: Retornar ClientGunIndex quando disponível
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getClientAmmoIndex(ResourceLocation ammoId) {
        // TODO: Retornar ClientAmmoIndex quando disponível
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getClientAttachmentIndex(ResourceLocation attachmentId) {
        // TODO: Implementação mínima - usar ClientIndexManager quando estiver operacional
        return Optional.empty(); // Retorna empty por enquanto
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getClientBlockIndex(ResourceLocation blockId) {
        // TODO: Retornar ClientBlockIndex quando disponível
        return Optional.empty();
    }

    // ===== MÉTODOS COMMON-SIDE =====

    public static Optional<Object> getCommonGunIndex(ResourceLocation gunId) {
        // TODO: Retornar CommonGunIndex quando CommonAssetsManager estiver disponível
        return Optional.empty();
    }

    public static Optional<Object> getCommonAmmoIndex(ResourceLocation ammoId) {
        // TODO: Retornar CommonAmmoIndex quando CommonAssetsManager estiver disponível
        return Optional.empty();
    }

    public static Optional<Object> getCommonAttachmentIndex(ResourceLocation attachmentId) {
        // TODO: Retornar CommonAttachmentIndex quando CommonAssetsManager estiver disponível
        return Optional.empty();
    }

    public static Optional<Object> getCommonBlockIndex(ResourceLocation blockId) {
        // TODO: Retornar CommonBlockIndex quando CommonAssetsManager estiver disponível
        return Optional.empty();
    }

    // ===== MÉTODOS DE COLEÇÕES =====

    public static Set<Map.Entry<ResourceLocation, Object>> getAllGuns() {
        // TODO: Retornar todos os guns quando disponível
        return Collections.emptySet();
    }

    public static Set<Map.Entry<ResourceLocation, Object>> getAllAmmos() {
        // TODO: Retornar todas as munições quando disponível
        return Collections.emptySet();
    }

    public static Set<Map.Entry<ResourceLocation, Object>> getAllAttachments() {
        // TODO: Retornar todos os acessórios quando disponível
        return Collections.emptySet();
    }

    public static Set<Map.Entry<ResourceLocation, Object>> getAllBlocks() {
        // TODO: Retornar todos os blocos quando disponível
        return Collections.emptySet();
    }

    // ===== MÉTODOS DE RECIPE =====

    public static RecipeType<?> getGunSmithTableRecipeType() {
        // TODO: Retornar tipo de receita quando disponível
        return null;
    }

    // ===== MÉTODOS DE TERCEIRA PESSOA =====

    @OnlyIn(Dist.CLIENT)
    public static void registerThirdPersonAnimation(ResourceLocation gunId, Object animationLister) {
        // TODO: Implementar quando ThirdPersonManager estiver disponível
    }

    // ===== MÉTODOS UTILITÁRIOS QUE FUNCIONAM IMEDIATAMENTE =====

    /**
     * Verifica se um ItemStack é uma arma válida
     */
    public static boolean isGun(ItemStack stack) {
        return stack.getItem() instanceof IGun;
    }

    /**
     * Obtém o ID da arma de um ItemStack, se for uma arma
     */
    public static Optional<ResourceLocation> getGunId(ItemStack stack) {
        if (stack.getItem() instanceof IGun iGun) {
            return Optional.of(iGun.getGunId(stack));
        }
        return Optional.empty();
    }

    /**
     * Obtém o display ID da arma de um ItemStack, se for uma arma
     */
    public static Optional<ResourceLocation> getGunDisplayId(ItemStack stack) {
        if (stack.getItem() instanceof IGun iGun) {
            ResourceLocation displayId = iGun.getGunDisplayId(stack);
            return Optional.of(displayId != null ? displayId : DefaultAssets.DEFAULT_GUN_DISPLAY_ID);
        }
        return Optional.empty();
    }
}
