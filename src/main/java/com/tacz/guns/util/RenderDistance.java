package com.tacz.guns.util;

// Imports comentados temporariamente devido a problemas de resolução
// import com.mojang.blaze3d.vertex.PoseStack;
// import com.tacz.guns.config.client.RenderConfig;
// import net.neoforged.api.distmarker.Dist;
// import net.neoforged.api.distmarker.OnlyIn;
// import org.joml.Matrix4f;

/**
 * Utilitário para cálculo de distância de renderização
 * 
 * MIGRAÇÃO TEMPORÁRIA: Usando Object para evitar problemas de import
 * TODO: Restaurar tipos específicos quando imports estiverem estáveis:
 * - Object poseStack → PoseStack
 */
// @OnlyIn(Dist.CLIENT) // TODO: Restaurar quando net.neoforged estiver disponível
public final class RenderDistance {
    private static long GUI_RENDER_TIMESTAMP = -1L;

    public static boolean inRenderHighPolyModelDistance(Object poseStack) {
        if (isGuiRender()) {
            return true;
        }
        
        // TODO: Implementar cálculo completo quando PoseStack estiver disponível
        // int distance = RenderConfig.GUN_LOD_RENDER_DISTANCE.get();
        // if (distance <= 0) {
        //     return false;
        // }
        // Matrix4f matrix4f = poseStack.last().pose();
        // float viewDistance = matrix4f.m30() * matrix4f.m30() + matrix4f.m31() * matrix4f.m31() + matrix4f.m32() * matrix4f.m32();
        // return viewDistance < distance * distance;
        
        // Implementação mínima: sempre retorna true (sempre renderiza alta qualidade)
        return true;
    }

    public static void markGuiRenderTimestamp() {
        GUI_RENDER_TIMESTAMP = System.currentTimeMillis();
    }

    private static boolean isGuiRender() {
        return System.currentTimeMillis() - GUI_RENDER_TIMESTAMP < 100;
    }
}
