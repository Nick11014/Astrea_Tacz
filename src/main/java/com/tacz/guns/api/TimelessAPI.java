package com.tacz.guns.api;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.ClientAssetsManager;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.ClientAssetsManager;
import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.client.resource.index.ClientAmmoIndex;
import com.tacz.guns.client.resource.index.ClientBlockIndex;
import com.tacz.guns.resource.index.CommonAmmoIndex;
import com.tacz.guns.resource.index.CommonBlockIndex;
import com.tacz.guns.resource.index.CommonAttachmentIndex;
import com.tacz.guns.resource.CommonAssetsManager;
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
 * API principal do TacZ para acesso a dados de armas, muniÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Âµes e acessÃƒÆ’Ã‚Â³rios.
 */
public final class TimelessAPI {

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientGunIndex> getClientGunIndex(ResourceLocation gunId) {
        return ClientAssetsManager.INSTANCE.getGunIndex(gunId);
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientAmmoIndex> getClientAmmoIndex(ResourceLocation ammoId) {
        return ClientAssetsManager.INSTANCE.getAmmoIndex(ammoId);
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientAttachmentIndex> getClientAttachmentIndex(ResourceLocation attachmentId) {
        return ClientAssetsManager.INSTANCE.getAttachmentIndex(attachmentId);
    }

    @OnlyIn(Dist.CLIENT)
    public static Optional<ClientBlockIndex> getClientBlockIndex(ResourceLocation blockId) {
        return ClientAssetsManager.INSTANCE.getBlockIndex(blockId);
    }

    // ===== MÉTODOS COMMON-SIDE =====

    public static Optional<CommonAmmoIndex> getCommonAmmoIndex(ResourceLocation ammoId) {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(instance.getAmmoIndex(ammoId));
    }

    public static Optional<CommonAttachmentIndex> getCommonAttachmentIndex(ResourceLocation attachmentId) {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(instance.getAttachmentIndex(attachmentId));
    }

    public static Optional<CommonBlockIndex> getCommonBlockIndex(ResourceLocation blockId) {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(instance.getBlockIndex(blockId));
    }

    // ===== MÉTODOS DE COLEÇÃO =====

    public static Set<Map.Entry<ResourceLocation, CommonGunIndex>> getAllCommonGunIndex() {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Collections.emptySet();
        }
        return instance.getAllGuns();
    }

    public static Set<Map.Entry<ResourceLocation, CommonAmmoIndex>> getAllAmmos() {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Collections.emptySet();
        }
        return instance.getAllAmmos();
    }

    public static Set<Map.Entry<ResourceLocation, CommonAttachmentIndex>> getAllAttachments() {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Collections.emptySet();
        }
        return instance.getAllAttachments();
    }

    public static Set<Map.Entry<ResourceLocation, CommonBlockIndex>> getAllBlocks() {
        var instance = CommonAssetsManager.getInstance();
        if (instance == null) {
            return Collections.emptySet();
        }
        return instance.getAllBlocks();
    }

    // ===== MÃƒÆ’Ã¢â‚¬Â°TODOS DE RECIPE =====

    public static RecipeType<?> getGunSmithTableRecipeType() {
        // TODO: Retornar tipo de receita quando disponÃƒÆ’Ã‚Â­vel
        return null;
    }

    // ===== MÃƒÆ’Ã¢â‚¬Â°TODOS DE TERCEIRA PESSOA =====

    @OnlyIn(Dist.CLIENT)
    public static void registerThirdPersonAnimation(ResourceLocation gunId, Object animationLister) {
        // TODO: Implementar quando ThirdPersonManager estiver disponÃƒÆ’Ã‚Â­vel
    }

    // ===== MÃƒÆ’Ã¢â‚¬Â°TODOS UTILITÃƒÆ’Ã‚ÂRIOS QUE FUNCIONAM IMEDIATAMENTE =====

    /**
     * Verifica se um ItemStack ÃƒÆ’Ã‚Â© uma arma vÃƒÆ’Ã‚Â¡lida
     */
    public static boolean isGun(ItemStack stack) {
        return stack.getItem() instanceof IGun;
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m o ID da arma de um ItemStack, se for uma arma
     */
    public static Optional<ResourceLocation> getGunId(ItemStack stack) {
        if (stack.getItem() instanceof IGun iGun) {
            return Optional.of(iGun.getGunId(stack));
        }
        return Optional.empty();
    }

    /**
     * ObtÃƒÆ’Ã‚Â©m o display ID da arma de um ItemStack, se for uma arma
     */
    public static Optional<ResourceLocation> getGunDisplayId(ItemStack stack) {
        if (stack.getItem() instanceof IGun iGun) {
            ResourceLocation displayId = iGun.getGunDisplayId(stack);
            return Optional.of(displayId != null ? displayId : DefaultAssets.DEFAULT_GUN_DISPLAY_ID);
        }
        return Optional.empty();
    }
}































































