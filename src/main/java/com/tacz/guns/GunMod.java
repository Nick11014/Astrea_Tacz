package com.tacz.guns;

import com.tacz.guns.config.CommonConfig;
import com.tacz.guns.config.ServerConfig;
import com.tacz.guns.init.*;
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

    public GunMod(IEventBus modEventBus, ModContainer modContainer) {
        // Registrar configurações usando a API correta do NeoForge 1.21.1
        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.init());
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.init());

        // Registrar DataComponents (prioridade máxima - Nova funcionalidade 1.21.1)
        ModDataComponents.COMPONENTS.register(modEventBus);
        
        // TODO: Habilitar gradualmente os outros registros conforme as classes ficarem disponíveis
        // ModBlocks.BLOCKS.register(modEventBus);
        // ModBlocks.TILE_ENTITIES.register(modEventBus);
        // ModCreativeTabs.TABS.register(modEventBus);
        // ModItems.ITEMS.register(modEventBus);
        // ModEntities.ENTITY_TYPES.register(modEventBus);
        // ModRecipe.RECIPE_SERIALIZERS.register(modEventBus);
        // ModRecipe.RECIPE_TYPES.register(modEventBus);
        // ModContainer.CONTAINER_TYPE.register(modEventBus);
        // ModSounds.SOUNDS.register(modEventBus);
        // ModParticles.PARTICLE_TYPES.register(modEventBus);
        // ModAttributes.ATTRIBUTES.register(modEventBus);
        // ModPainting.PAINTINGS.register(modEventBus);
        
        // TODO: Habilitar KubeJS quando estiver funcionando
        // if (ModList.get().isLoaded("kubejs")) {
        //     modEventBus.register(new TimelessKubeJSPlugin());
        // }

        // TODO: Habilitar resource e attachment manager quando estiverem funcionando  
        // registerDefaultExtraGunPack();
        // AttachmentPropertyManager.registerModifier();
    }

    // TODO: Reativar quando ResourceManager estiver funcionando
    /*
    private static void registerDefaultExtraGunPack() {
        String jarDefaultPackPath = String.format("/assets/%s/custom/%s", GunMod.MOD_ID, DEFAULT_GUN_PACK_NAME);
        ResourceManager.registerExportResource(GunMod.class, jarDefaultPackPath);
    }
    */
}
