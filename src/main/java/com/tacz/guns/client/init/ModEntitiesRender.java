package com.tacz.guns.client.init;

import com.tacz.guns.init.ModBlocks;
import com.tacz.guns.init.ModEntities;
import com.tacz.guns.client.renderer.block.GunSmithTableRenderer;
import com.tacz.guns.client.renderer.block.StatueRenderer;
import com.tacz.guns.client.renderer.block.TargetRenderer;
import com.tacz.guns.client.renderer.entity.EntityBulletRenderer;
import com.tacz.guns.client.renderer.entity.TargetMinecartRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModEntitiesRender {
    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        EntityRenderers.register(ModEntities.BULLET.get(), EntityBulletRenderer::new);
        EntityRenderers.register(ModEntities.TARGET_MINECART.get(), TargetMinecartRenderer::new);
        BlockEntityRenderers.register(ModBlocks.GUN_SMITH_TABLE_BE.get(), GunSmithTableRenderer::new);
        BlockEntityRenderers.register(ModBlocks.TARGET_BE.get(), TargetRenderer::new);
        BlockEntityRenderers.register(ModBlocks.STATUE_BE.get(), StatueRenderer::new);
    }
}































































