package com.tacz.guns.api;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.ClientAssetManager;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.resource.CommonAssetManager;
import com.tacz.guns.resource.index.CommonGunIndex;
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
 */
public final class TimelessAPI {
    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientGunIndex> getGunDisplay(ItemStack stack) {
        return ClientAssetManager.INSTANCE.getGunIndex(getGunId(stack).orElse(DefaultAssets.DEFAULT_GUN_ID));
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientGunIndex> getGunDisplay(ResourceLocation displayId, ResourceLocation fallbackGunId) {
        Optional<ClientGunIndex> gunIndex = ClientAssetManager.INSTANCE.getGunIndex(displayId);
        if (gunIndex.isPresent()) {
            return gunIndex;
        }
        return ClientAssetManager.INSTANCE.getGunIndex(fallbackGunId);
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientGunIndex> getClientGunIndex(ResourceLocation gunId) {
        return ClientAssetManager.INSTANCE.getGunIndex(gunId);
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getClientAmmoIndex(ResourceLocation ammoId) {
        // TODO: Retornar ClientAmmoIndex quando disponível
        return Optional.empty();
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientAttachmentIndex> getClientAttachmentIndex(ResourceLocation attachmentId) {
        return ClientAssetManager.INSTANCE.getAttachmentIndex(attachmentId);
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<Object> getClientBlockIndex(ResourceLocation blockId) {
        // TODO: Retornar ClientBlockIndex quando disponível
        return Optional.empty();
    }

    // ===== MÉTODOS COMMON-SIDE =====

    public static Optional<CommonGunIndex> getCommonGunIndex(ResourceLocation gunId) {
        return CommonAssetManager.INSTANCE.getGunIndex(gunId);
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

    public static Set<Map.Entry<ResourceLocation, CommonGunIndex>> getAllCommonGunIndex() {
        return CommonAssetManager.INSTANCE.getAllGuns();
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
