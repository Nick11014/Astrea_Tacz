package com.tacz.guns.util;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.resource.pojo.display.LaserConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Implementação mínima estratégica para LaserColorUtil
 * TODO: Expandir quando GunDisplayInstance e ClientAttachmentIndex estiverem completos
 */
public class LaserColorUtil {
    public static int getLaserColor(Object stack, @NotNull LaserConfig defaultConfig) {
        if (stack == null) {
            return defaultConfig.getDefaultColor();
        }

        // TODO: Implementar quando ItemStack estiver disponível
        // if (stack.getItem() instanceof IAttachment iAttachment) {
        //     if (iAttachment.hasCustomLaserColor(stack)) {
        //         return iAttachment.getLaserColor(stack);
        //     } else {
        //         return defaultConfig.getDefaultColor();
        //     }
        // }

        // if (stack.getItem() instanceof IGun gun) {
        //     if (gun.hasCustomLaserColor(stack)) {
        //         return gun.getLaserColor(stack);
        //     } else {
        //         return defaultConfig.getDefaultColor();
        //     }
        // }

        return defaultConfig.getDefaultColor();
    }

    public static int getLaserColor(Object stack) {
        if (stack == null) {
            return 0xFF0000;
        }

        // TODO: Implementar quando ItemStack e dependências estiverem disponíveis
        // if (stack.getItem() instanceof IAttachment iAttachment) {
        //     if (iAttachment.hasCustomLaserColor(stack)) {
        //         return iAttachment.getLaserColor(stack);
        //     } else {
        //         return TimelessAPI.getClientAttachmentIndex(iAttachment.getAttachmentId(stack))
        //                 .map(ClientAttachmentIndex::getLaserConfig)
        //                 .map(LaserConfig::getDefaultColor)
        //                 .orElse(0xFF0000);
        //     }
        // }

        // if (stack.getItem() instanceof IGun gun) {
        //     if (gun.hasCustomLaserColor(stack)) {
        //         return gun.getLaserColor(stack);
        //     } else {
        //         return TimelessAPI.getGunDisplay(stack)
        //                 .map(GunDisplayInstance::getLaserConfig)
        //                 .map(LaserConfig::getDefaultColor)
        //                 .orElse(0xFF0000);
        //     }
        // }

        return 0xFF0000;
    }

    /**
     * Método utilitário para obter cor padrão de laser
     * @param defaultColor cor padrão em formato hex
     * @return cor formatada
     */
    public static int getDefaultLaserColor(int defaultColor) {
        return defaultColor != 0 ? defaultColor : 0xFF0000;
    }

    /**
     * Método utilitário para validar cor de laser
     * @param color cor a ser validada
     * @return true se a cor é válida
     */
    public static boolean isValidLaserColor(int color) {
        return color >= 0x000000 && color <= 0xFFFFFF;
    }
}
