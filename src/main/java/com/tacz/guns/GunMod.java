package com.tacz.guns;

import com.tacz.guns.config.CommonConfig;
import com.tacz.guns.config.ServerConfig;
import com.tacz.guns.init.*;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GunMod.MOD_ID)
public class GunMod {
    public static final String MOD_ID = "tacz";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    /**
     * 默认模型包文件夹
     */
    public static final String DEFAULT_GUN_PACK_NAME = "tacz_default_gun";

    /**
     * Método utilitário para criar ResourceLocation do mod.
     * Baseado no padrão do SuperbWarfare 1.21.1
     */
    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public GunMod(IEventBus modEventBus, ModContainer modContainer) {
        // Registrar configurações usando a API correta do NeoForge 1.21.1
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.init());
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.init());

        // Registrar DataComponents (prioridade máxima - Nova funcionalidade 1.21.1)
        ModDataComponents.COMPONENTS.register(modEventBus);
        
        // Registrar partículas (baseado no padrão SuperbWarfare)
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        
        // Registrar itens com implementação mínima (baseado no padrão SuperbWarfare)
        ModItems.ITEMS.register(modEventBus);
        
        // Registrar blocos com implementação mínima (baseado no padrão SuperbWarfare)
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.TILE_ENTITIES.register(modEventBus);
        
        // Registrar entidades e receitas com implementação mínima
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModRecipe.RECIPE_SERIALIZERS.register(modEventBus);
        ModRecipe.RECIPE_TYPES.register(modEventBus);
        
        // Registrar Creative Tabs com implementação mínima
        ModCreativeTabs.TABS.register(modEventBus);
        
        // TODO: Habilitar gradualmente os outros registros conforme as classes ficarem disponíveis
        // ModContainer.CONTAINER_TYPE.register(modEventBus);
        // ModSounds.SOUNDS.register(modEventBus);
        // ModAttributes.ATTRIBUTES.register(modEventBus);
        // ModPainting.PAINTINGS.register(modEventBus);
        
        // TODO: Habilitar KubeJS quando estiver funcionando
        // if (ModList.get().isLoaded("kubejs")) {
        //     modEventBus.register(new TimelessKubeJSPlugin());
        // }

        // TODO: Habilitar resource e attachment manager quando estiverem funcionando  
        // registerDefaultExtraGunPack();
        AttachmentPropertyManager.registerModifier(); // HABILITADO: Sistema de modificadores funcionando com implementação mínima
    }

    // TODO: Reativar quando ResourceManager estiver funcionando
    /*
    private static void registerDefaultExtraGunPack() {
        String jarDefaultPackPath = String.format("/assets/%s/custom/%s", GunMod.MOD_ID, DEFAULT_GUN_PACK_NAME);
        ResourceManager.registerExportResource(GunMod.class, jarDefaultPackPath);
    }
    */
}
