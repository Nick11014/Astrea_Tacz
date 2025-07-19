package com.tacz.guns.compat.kubejs.util;

import com.tacz.guns.api.item.gun.FireMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * Wrapper principal para criação de itens TacZ no KubeJS
 * IMPLEMENTADO: Migrado para DataComponents (NeoForge 1.21.1)
 * 
 * Esta classe fornece métodos de conveniência para criar ItemStacks
 * configurados com os dados corretos usando as factory classes DataComponent.
 */
public class TimelessItemWrapper {
    
    // ========== MÉTODOS PRINCIPAIS ==========
    
    /**
     * Cria um ItemStack de arma configurado
     * @param callback Função para configurar a arma
     * @return ItemStack da arma configurado
     */
    public static ItemStack gunItem(Consumer<GunDataComponentFactory> callback) {
        GunDataComponentFactory factory = new GunDataComponentFactory();
        callback.accept(factory);
        return factory.getItemStack();
    }

    /**
     * Cria um ItemStack de attachment configurado
     * @param callback Função para configurar o attachment
     * @return ItemStack do attachment configurado
     */
    public static ItemStack attachmentItem(Consumer<AttachmentDataComponentFactory> callback) {
        AttachmentDataComponentFactory factory = new AttachmentDataComponentFactory();
        callback.accept(factory);
        return factory.getItemStack();
    }

    /**
     * Cria um ItemStack de munição configurado
     * @param callback Função para configurar a munição
     * @return ItemStack da munição configurado
     */
    public static ItemStack ammoItem(Consumer<AmmoDataComponentFactory> callback) {
        AmmoDataComponentFactory factory = new AmmoDataComponentFactory();
        callback.accept(factory);
        return factory.getItemStack();
    }

    // ========== MÉTODOS DE CONVENIÊNCIA PARA ARMAS ==========

    /**
     * Cria uma arma básica com configurações mínimas
     * @param gunId ID da arma
     * @param ammo Munição inicial
     * @return ItemStack da arma
     */
    public static ItemStack basicGun(ResourceLocation gunId, int ammo) {
        return gunItem(gun -> gun.setupBasicGun(gunId, ammo));
    }

    /**
     * Cria uma arma básica usando string para o ID
     * @param gunIdString ID da arma como string (formato: "namespace:path")
     * @param ammo Munição inicial
     * @return ItemStack da arma
     */
    public static ItemStack basicGun(String gunIdString, int ammo) {
        ResourceLocation gunId = ResourceLocation.parse(gunIdString);
        return basicGun(gunId, ammo);
    }

    /**
     * Cria uma arma completamente configurada
     * @param gunId ID da arma
     * @param ammo Munição inicial
     * @param fireMode Modo de fogo
     * @param hasAmmoInBarrel Se há bala no cano
     * @return ItemStack da arma
     */
    public static ItemStack configuredGun(ResourceLocation gunId, int ammo, FireMode fireMode, boolean hasAmmoInBarrel) {
        return gunItem(gun -> gun
                .setupBasicGun(gunId, ammo)
                .setFireMode(fireMode)
                .setAmmoInBarrel(hasAmmoInBarrel)
        );
    }

    /**
     * Cria uma arma configurada usando strings
     * @param gunIdString ID da arma como string
     * @param ammo Munição inicial
     * @param fireModeString Modo de fogo como string
     * @param hasAmmoInBarrel Se há bala no cano
     * @return ItemStack da arma
     */
    public static ItemStack configuredGun(String gunIdString, int ammo, String fireModeString, boolean hasAmmoInBarrel) {
        ResourceLocation gunId = ResourceLocation.parse(gunIdString);
        FireMode fireMode = FireMode.valueOf(fireModeString.toUpperCase());
        return configuredGun(gunId, ammo, fireMode, hasAmmoInBarrel);
    }

    // ========== MÉTODOS DE CONVENIÊNCIA PARA ATTACHMENTS ==========

    /**
     * Cria um attachment básico
     * @param attachmentId ID do attachment
     * @return ItemStack do attachment
     */
    public static ItemStack basicAttachment(ResourceLocation attachmentId) {
        return attachmentItem(attachment -> attachment.setupBasicAttachment(attachmentId));
    }

    /**
     * Cria um attachment básico usando string
     * @param attachmentIdString ID do attachment como string
     * @return ItemStack do attachment
     */
    public static ItemStack basicAttachment(String attachmentIdString) {
        ResourceLocation attachmentId = ResourceLocation.parse(attachmentIdString);
        return basicAttachment(attachmentId);
    }

    /**
     * Cria uma mira laser
     * @param attachmentId ID do attachment
     * @param laserColor Cor do laser (formato RGB)
     * @return ItemStack da mira laser
     */
    public static ItemStack laserSight(ResourceLocation attachmentId, int laserColor) {
        return attachmentItem(attachment -> attachment.setupLaserSight(attachmentId, laserColor));
    }

    /**
     * Cria uma mira laser usando strings
     * @param attachmentIdString ID do attachment como string
     * @param hexColor Cor do laser em formato hexadecimal (ex: "FF0000")
     * @return ItemStack da mira laser
     */
    public static ItemStack laserSight(String attachmentIdString, String hexColor) {
        ResourceLocation attachmentId = ResourceLocation.parse(attachmentIdString);
        return attachmentItem(attachment -> attachment
                .setupBasicAttachment(attachmentId)
                .setLaserColorHex(hexColor)
        );
    }

    /**
     * Cria uma mira telescópica
     * @param attachmentId ID do attachment
     * @param zoomLevel Nível de zoom
     * @return ItemStack da mira telescópica
     */
    public static ItemStack scope(ResourceLocation attachmentId, int zoomLevel) {
        return attachmentItem(attachment -> attachment.setupScope(attachmentId, zoomLevel));
    }

    /**
     * Cria uma mira telescópica usando string
     * @param attachmentIdString ID do attachment como string
     * @param zoomLevel Nível de zoom
     * @return ItemStack da mira telescópica
     */
    public static ItemStack scope(String attachmentIdString, int zoomLevel) {
        ResourceLocation attachmentId = ResourceLocation.parse(attachmentIdString);
        return scope(attachmentId, zoomLevel);
    }

    // ========== MÉTODOS DE CONVENIÊNCIA PARA MUNIÇÃO ==========

    /**
     * Cria munição básica
     * @param ammoId ID da munição
     * @return ItemStack da munição
     */
    public static ItemStack basicAmmo(ResourceLocation ammoId) {
        return ammoItem(ammo -> ammo.setupBasicAmmo(ammoId));
    }

    /**
     * Cria munição básica usando string
     * @param ammoIdString ID da munição como string
     * @return ItemStack da munição
     */
    public static ItemStack basicAmmo(String ammoIdString) {
        ResourceLocation ammoId = ResourceLocation.parse(ammoIdString);
        return basicAmmo(ammoId);
    }

    /**
     * Cria munição de pistola
     * @param name Nome da munição
     * @return ItemStack da munição
     */
    public static ItemStack pistolAmmo(String name) {
        return ammoItem(ammo -> ammo.setupPistolAmmo(name));
    }

    /**
     * Cria munição de rifle
     * @param name Nome da munição
     * @return ItemStack da munição
     */
    public static ItemStack rifleAmmo(String name) {
        return ammoItem(ammo -> ammo.setupRifleAmmo(name));
    }

    /**
     * Cria munição de sniper
     * @param name Nome da munição
     * @return ItemStack da munição
     */
    public static ItemStack sniperAmmo(String name) {
        return ammoItem(ammo -> ammo.setupSniperAmmo(name));
    }

    /**
     * Cria munição de shotgun
     * @param name Nome da munição
     * @return ItemStack da munição
     */
    public static ItemStack shotgunAmmo(String name) {
        return ammoItem(ammo -> ammo.setupShotgunAmmo(name));
    }

    // ========== MÉTODOS UTILITÁRIOS ==========

    /**
     * Verifica se um ItemStack é uma arma TacZ válida
     * @param itemStack ItemStack a verificar
     * @return true se é uma arma válida
     */
    public static boolean isValidGun(ItemStack itemStack) {
        return TimelessItemType.MODERN_KINETIC_GUN.matches(itemStack.getItem()) && !itemStack.isEmpty();
    }

    /**
     * Verifica se um ItemStack é um attachment TacZ válido
     * @param itemStack ItemStack a verificar
     * @return true se é um attachment válido
     */
    public static boolean isValidAttachment(ItemStack itemStack) {
        return TimelessItemType.ATTACHMENT.matches(itemStack.getItem()) && !itemStack.isEmpty();
    }

    /**
     * Verifica se um ItemStack é munição TacZ válida
     * @param itemStack ItemStack a verificar
     * @return true se é munição válida
     */
    public static boolean isValidAmmo(ItemStack itemStack) {
        return TimelessItemType.AMMO.matches(itemStack.getItem()) && !itemStack.isEmpty();
    }

    /**
     * Obtém uma factory de gun a partir de um ItemStack existente
     * @param gunStack ItemStack da arma
     * @return GunDataComponentFactory configurada ou null se inválida
     */
    public static GunDataComponentFactory fromGunStack(ItemStack gunStack) {
        if (isValidGun(gunStack)) {
            return new GunDataComponentFactory(gunStack);
        }
        return null;
    }

    /**
     * Obtém uma factory de attachment a partir de um ItemStack existente
     * @param attachmentStack ItemStack do attachment
     * @return AttachmentDataComponentFactory configurada ou null se inválida
     */
    public static AttachmentDataComponentFactory fromAttachmentStack(ItemStack attachmentStack) {
        if (isValidAttachment(attachmentStack)) {
            return new AttachmentDataComponentFactory(attachmentStack);
        }
        return null;
    }

    /**
     * Obtém uma factory de ammo a partir de um ItemStack existente
     * @param ammoStack ItemStack da munição
     * @return AmmoDataComponentFactory configurada ou null se inválida
     */
    public static AmmoDataComponentFactory fromAmmoStack(ItemStack ammoStack) {
        if (isValidAmmo(ammoStack)) {
            return new AmmoDataComponentFactory(ammoStack);
        }
        return null;
    }
}
