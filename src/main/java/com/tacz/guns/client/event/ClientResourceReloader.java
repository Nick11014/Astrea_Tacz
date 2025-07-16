package com.tacz.guns.client.event;

import com.tacz.guns.client.resource.InternalAssetLoader;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener; // Added import
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientResourceReloader extends SimplePreparableReloadListener<Void> { // Changed to extend SimplePreparableReloadListener

    @SubscribeEvent
    public static void onRegisterReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(new ClientResourceReloader());
    }

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) { // Changed return type and access modifier
        // This runs on a background thread. Perform heavy loading/generation here.
        return null; // Return null as no data is being passed
    }

    @Override
    protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
        // This runs on the main thread. Apply changes to the game here.
                    InternalAssetLoader.onResourceReload();
        // TODO: Re-enable ClientReloadManager.reloadAllPack() when ClientReloadManager is available
    }
}
