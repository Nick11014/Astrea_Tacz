package com.tacz.guns.client.model;

// Imports comentados temporariamente devido a problemas de resolução
// import com.mojang.blaze3d.vertex.PoseStack;
// import com.mojang.blaze3d.vertex.VertexConsumer;
// import net.minecraft.world.item.ItemDisplayContext;

/**
 * Interface para renderizadores funcionais
 * 
 * MIGRAÇÃO TEMPORÁRIA: Usando Object para evitar problemas de import
 * TODO: Restaurar tipos específicos quando imports estiverem estáveis:
 * - Object poseStack → PoseStack
 * - Object vertexBuffer → VertexConsumer  
 * - Object transformType → ItemDisplayContext
 */
public interface IFunctionalRenderer {
    void render(Object poseStack, Object vertexBuffer, Object transformType, int light, int overlay);
}
