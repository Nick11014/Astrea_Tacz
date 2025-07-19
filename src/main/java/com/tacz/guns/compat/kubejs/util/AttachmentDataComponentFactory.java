package com.tacz.guns.compat.kubejs.util;

import com.tacz.guns.api.item.nbt.AttachmentItemDataAccessor;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.item.AttachmentItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Factory para criação e manipulação de dados de attachments para KubeJS
 * IMPLEMENTADO: Usa AttachmentItemDataAccessor (DataComponents) em vez de NBT
 */
public class AttachmentDataComponentFactory extends TimelessItemDataComponentFactory<AttachmentItem, AttachmentDataComponentFactory> {

    /**
     * Construtor com item específico de attachment
     * @param item Item de attachment
     */
    public AttachmentDataComponentFactory(@Nonnull AttachmentItem item) {
        super(item);
    }

    /**
     * Construtor padrão - usa ATTACHMENT como padrão
     */
    public AttachmentDataComponentFactory() {
        super((AttachmentItem) ModItems.ATTACHMENT.get());
    }

    /**
     * Construtor com ItemStack existente
     * @param itemStack ItemStack de attachment existente
     */
    public AttachmentDataComponentFactory(ItemStack itemStack) {
        super();
        if (itemStack.getItem() instanceof AttachmentItem attachmentItem) {
            this.item = attachmentItem;
            this.itemStack = itemStack;
        } else {
            throw new IllegalArgumentException("ItemStack must be an attachment item");
        }
    }

    // ========== MÉTODOS DE IDENTIFICAÇÃO ==========

    /**
     * Define o ID do attachment
     * @param attachmentId ID do attachment
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setAttachmentId(ResourceLocation attachmentId) {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            accessor.setAttachmentId(itemStack, attachmentId);
        }
        return this;
    }

    /**
     * Obtém o ID do attachment
     * @return ID do attachment ou null se não definido
     */
    public ResourceLocation getAttachmentId() {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            return accessor.getAttachmentId(itemStack);
        }
        return null;
    }

    // ========== MÉTODOS DE LASER ==========

    /**
     * Verifica se tem configuração de cor de laser
     * @return true se tem cor de laser configurada
     */
    public boolean hasLaserColor() {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            return accessor.hasCustomLaserColor(itemStack);
        }
        return false;
    }

    /**
     * Define a cor do laser (para miras laser)
     * @param color Cor do laser em formato RGB
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setLaserColor(int color) {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            accessor.setLaserColor(itemStack, color);
        }
        return this;
    }

    /**
     * Obtém a cor do laser
     * @return Cor do laser (padrão: vermelho 0xFF0000)
     */
    public int getLaserColor() {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            return accessor.getLaserColor(itemStack);
        }
        return 0xFF0000; // Vermelho padrão
    }

    /**
     * Define a cor do laser usando valores RGB separados
     * @param red Componente vermelho (0-255)
     * @param green Componente verde (0-255)
     * @param blue Componente azul (0-255)
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setLaserColorRGB(int red, int green, int blue) {
        int color = (red << 16) | (green << 8) | blue;
        return setLaserColor(color);
    }

    /**
     * Define a cor do laser usando um código hexadecimal
     * @param hexColor Cor em formato hexadecimal (ex: "FF0000" para vermelho)
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setLaserColorHex(String hexColor) {
        try {
            // Remove # se presente
            if (hexColor.startsWith("#")) {
                hexColor = hexColor.substring(1);
            }
            int color = Integer.parseInt(hexColor, 16);
            return setLaserColor(color);
        } catch (NumberFormatException e) {
            System.err.println("TacZ: Invalid hex color: " + hexColor + ". Using red as default.");
            return setLaserColor(0xFF0000); // Vermelho como padrão
        }
    }

    // ========== MÉTODOS DE ZOOM ==========

    /**
     * Define o número do zoom (para miras telescópicas)
     * @param zoomNumber Número do zoom
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setZoomNumber(int zoomNumber) {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            accessor.setZoomNumber(itemStack, zoomNumber);
        }
        return this;
    }

    /**
     * Obtém o número do zoom
     * @return Número do zoom (padrão: 0)
     */
    public int getZoomNumber() {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            return accessor.getZoomNumber(itemStack);
        }
        return 0;
    }

    /**
     * Verifica se tem configuração de zoom
     * @return true se tem zoom configurado
     */
    public boolean hasZoomNumber() {
        return getZoomNumber() > 0;
    }

    // ========== MÉTODOS DE SKIN ==========

    /**
     * Define o ID da skin do attachment
     * @param skinId ID da skin
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setSkinId(ResourceLocation skinId) {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            accessor.setSkinId(itemStack, skinId);
        }
        return this;
    }

    /**
     * Obtém o ID da skin do attachment
     * @return ID da skin ou null se não definido
     */
    public ResourceLocation getSkinId() {
        if (item instanceof AttachmentItemDataAccessor accessor) {
            return accessor.getSkinId(itemStack);
        }
        return null;
    }

    // ========== MÉTODOS UTILITÁRIOS ==========

    /**
     * Configura um attachment básico
     * @param attachmentId ID do attachment
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setupBasicAttachment(ResourceLocation attachmentId) {
        return setAttachmentId(attachmentId);
    }

    /**
     * Configura uma mira laser
     * @param attachmentId ID do attachment
     * @param laserColor Cor do laser
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setupLaserSight(ResourceLocation attachmentId, int laserColor) {
        return setAttachmentId(attachmentId)
                .setLaserColor(laserColor);
    }

    /**
     * Configura uma mira telescópica
     * @param attachmentId ID do attachment
     * @param zoomLevel Nível de zoom
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setupScope(ResourceLocation attachmentId, int zoomLevel) {
        return setAttachmentId(attachmentId)
                .setZoomNumber(zoomLevel);
    }

    /**
     * Configura um attachment com skin personalizada
     * @param attachmentId ID do attachment
     * @param skinId ID da skin
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory setupCustomSkin(ResourceLocation attachmentId, ResourceLocation skinId) {
        return setAttachmentId(attachmentId)
                .setSkinId(skinId);
    }

    /**
     * Reseta todos os dados do attachment para valores padrão
     * @return Esta factory para chaining
     */
    public AttachmentDataComponentFactory reset() {
        if (hasLaserColor()) {
            setLaserColor(0xFF0000); // Vermelho padrão
        }
        if (hasZoomNumber()) {
            setZoomNumber(0);
        }
        return this;
    }

    /**
     * Verifica se este attachment é uma mira laser
     * @return true se tem cor de laser configurada
     */
    public boolean isLaserSight() {
        return hasLaserColor();
    }

    /**
     * Verifica se este attachment é uma mira telescópica
     * @return true se tem zoom configurado
     */
    public boolean isScope() {
        return hasZoomNumber();
    }

    @Override
    public String toString() {
        return "AttachmentDataComponentFactory{" +
                "attachmentId=" + getAttachmentId() +
                ", hasLaser=" + hasLaserColor() +
                ", laserColor=" + (hasLaserColor() ? String.format("0x%06X", getLaserColor()) : "none") +
                ", hasZoom=" + hasZoomNumber() +
                ", zoomNumber=" + (hasZoomNumber() ? getZoomNumber() : "none") +
                ", skinId=" + getSkinId() +
                ", valid=" + isValid() +
                '}';
    }
}
