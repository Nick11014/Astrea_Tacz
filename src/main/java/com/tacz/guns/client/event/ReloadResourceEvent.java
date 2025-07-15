package com.tacz.guns.client.event;

// TODO: Re-enable when InternalAssetLoader is available and find alternative to TextureStitchEvent
// import com.tacz.guns.client.resource.InternalAssetLoader;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
// TODO: TextureStitchEvent foi removido no NeoForge 1.21.1, encontrar alternativa
// import net.neoforged.neoforge.client.event.TextureStitchEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

/**
 * Evento de recarga de recursos baseado no padrÃƒÆ’Ã‚Â£o SuperbWarfare 1.21.1
 * ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - TextureStitchEvent foi removido no NeoForge 1.21.1
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ReloadResourceEvent {
    public static final ResourceLocation BLOCK_ATLAS_TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/atlas/blocks.png");

    // TODO: Encontrar evento substituto para TextureStitchEvent.Post no NeoForge 1.21.1
    // @SubscribeEvent
    // public static void onTextureStitchEventPost(TextureStitchEvent.Post event) {
    //     if (BLOCK_ATLAS_TEXTURE.equals(event.getAtlas().location())) {
    //         // TODO: Re-enable when InternalAssetLoader is available
    //         // InternalAssetLoader.onResourceReload();
    //         // ClientReloadManager.reloadAllPack();
    //         
    //         // Placeholder - funcionalidade desabilitada temporariamente
    //     }
    // }
    
    // quando um evento substituto for identificado
}































































