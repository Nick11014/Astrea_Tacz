
package com.tacz.guns;

// import com.tacz.guns.client.event.ClientSetupEvent;
// import com.tacz.guns.client.init.KeyBinding;
import com.tacz.guns.config.common.GunConfig;
import com.tacz.guns.network.NetworkHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GunMod.MOD_ID)
public class GunMod {
    public static final String MOD_ID = "tacz";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    // ===== SEÇÃO DE CONTROLE: DIVIDIR E CONQUISTAR =====
    // Flags para controlar quais seções estão ativas
    public static final boolean ENABLE_CONFIGS = false;           // SEÇÃO 2: Configurações
    public static final boolean ENABLE_NETWORK = false;           // SEÇÃO 3: Sistema de rede
    public static final boolean ENABLE_ASSETS = false;            // SEÇÃO 4: Sistema de recursos
    public static final boolean ENABLE_CLIENT = false;            // SEÇÃO 5: Renderização
    public static final boolean ENABLE_GAMEPLAY = false;          // SEÇÃO 6: Gameplay básico
    public static final boolean ENABLE_SCRIPTS = false;           // SEÇÃO 7: Sistema de scripts
    public static final boolean ENABLE_ADVANCED_RENDER = false;   // SEÇÃO 8: Renderização avançada
    public static final boolean ENABLE_COMPAT = false;            // SEÇÃO 9: Compatibilidade
    
    // SEÇÃO 1: REGISTROS BÁSICOS - SEMPRE ATIVO
    // DataComponents e SoundEvents são essenciais para o funcionamento básico

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public GunMod(IEventBus bus, ModContainer container) {
        bus.addListener(this::setup);
        
        // SEÇÃO 1: REGISTROS BÁSICOS - SEMPRE ATIVO
        com.tacz.guns.init.ModDataComponents.register(bus);
        com.tacz.guns.init.ModSoundEvents.SOUND_EVENTS.register(bus);
        com.tacz.guns.init.ModItems.ITEMS.register(bus);
        com.tacz.guns.init.ModBlocks.BLOCKS.register(bus);
        com.tacz.guns.init.ModBlocks.TILE_ENTITIES.register(bus);
        
        // SEÇÃO 2: CONFIGURAÇÕES
        if (ENABLE_CONFIGS) {
            container.registerConfig(ModConfig.Type.COMMON, GunConfig.init());
        }
        
        // SEÇÃO 3: SISTEMA DE REDE
        if (ENABLE_NETWORK) {
            bus.addListener(this::registerPayloadHandler);
        }

        // SEÇÃO 5: RENDERIZAÇÃO (Cliente)
        if (ENABLE_CLIENT && FMLEnvironment.dist == Dist.CLIENT) {
            // bus.addListener(ClientSetupEvent::init);
            // bus.addListener(KeyBinding::register);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // SEÇÃO 4: SISTEMA DE RECURSOS
            if (ENABLE_ASSETS) {
                // CommonNetworkCache.init(); // Removed as it's not found
                // ServerGunPackManager.init();
            }
            
            // SEÇÃO 6: GAMEPLAY BÁSICO
            if (ENABLE_GAMEPLAY) {
                // Inicialização de gameplay será adicionada aqui
            }
            
            // SEÇÃO 7: SISTEMA DE SCRIPTS
            if (ENABLE_SCRIPTS) {
                // ScriptManager.init(); // Será reabilitado na fase apropriada
            }
            
            // SEÇÃO 9: COMPATIBILIDADE
            if (ENABLE_COMPAT) {
                // Inicialização de compatibilidade será adicionada aqui
            }
        });
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        // SEÇÃO 3: SISTEMA DE REDE
        if (ENABLE_NETWORK) {
            NetworkHandler.register(event);
        }
    }
}































































