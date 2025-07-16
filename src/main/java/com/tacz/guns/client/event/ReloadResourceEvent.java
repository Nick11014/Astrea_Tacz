package com.tacz.guns.client.event;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Evento de recarga de recursos.
 * Este arquivo agora serve principalmente para definir constantes relacionadas a recursos.
 * A lÃƒÆ’Ã‚Â³gica de recarga de recursos foi movida para ClientResourceReloader.java.
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ReloadResourceEvent {
    public static final ResourceLocation BLOCK_ATLAS_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/atlas/blocks.png");
}































































