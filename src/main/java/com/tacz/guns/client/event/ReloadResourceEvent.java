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
    //         // InternalAssetLoader ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚ÂºÃ¢â‚¬ÂºÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
    //         // InternalAssetLoader.onResourceReload();
    //         // ClientReloadManager.reloadAllPack();
    //         
    //         // Placeholder - funcionalidade desabilitada temporariamente
    //     }
    // }
    
    // Classe mantida para estrutura, mas funcionalidade serÃƒÆ’Ã‚Â¡ reimplementada
    // quando um evento substituto for identificado
}































































