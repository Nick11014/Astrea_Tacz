package com.tacz.guns;

// TODO: Re-enable when dependencies are available
/*
import com.tacz.guns.api.resource.ResourceManager;
import com.tacz.guns.compat.kubejs.TimelessKubeJSPlugin;
import com.tacz.guns.resource.GunPackLoader;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
*/
import com.tacz.guns.config.ClientConfig;
import com.tacz.guns.config.CommonConfig;
import com.tacz.guns.config.ServerConfig;
import com.tacz.guns.init.*;
import net.minecraft.server.packs.PackType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
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

    public GunMod(IEventBus modEventBus) {
        // Registrar configurações (API atualizada para NeoForge)
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, CommonConfig.init());
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, ServerConfig.init());
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, ClientConfig.init());

        // TODO: Re-enable when GunPackLoader is available
        /*
        Dist side = FMLLoader.getDist();
        GunPackLoader.INSTANCE.packType = side.isClient() ? PackType.CLIENT_RESOURCES : PackType.SERVER_DATA;
        */

        // Registrar DataComponents (prioridade máxima - Nova funcionalidade 1.21.1)
        ModDataComponents.COMPONENTS.register(modEventBus);
        
        // Registros existentes que foram habilitados
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.TILE_ENTITIES.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        
        // TODO: Re-enable when these modules are available
        /*
        ModRecipe.RECIPE_SERIALIZERS.register(modEventBus);
        ModRecipe.RECIPE_TYPES.register(modEventBus);
        ModContainer.CONTAINER_TYPE.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        ModPainting.PAINTINGS.register(modEventBus);
        
        if (ModList.get().isLoaded("kubejs")) {
            modEventBus.register(new TimelessKubeJSPlugin());
        }

        registerDefaultExtraGunPack();
        AttachmentPropertyManager.registerModifier();
        */
    }

    // TODO: Re-enable when ResourceManager is available
    /*
    private static void registerDefaultExtraGunPack() {
        String jarDefaultPackPath = String.format("/assets/%s/custom/%s", GunMod.MOD_ID, DEFAULT_GUN_PACK_NAME);
        ResourceManager.registerExportResource(GunMod.class, jarDefaultPackPath);
    }
    */
}
