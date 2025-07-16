package com.tacz.guns;

// import com.tacz.guns.client.event.ClientSetupEvent;
// import com.tacz.guns.client.init.KeyBinding;
import com.tacz.guns.config.common.GunConfig;
import com.tacz.guns.config.sync.SyncConfig;
import com.tacz.guns.init.CommonRegistry;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.resource.network.CommonNetworkCache;
// import com.tacz.guns.resource.server.ServerGunPackManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.fml.ModLoadingContext;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GunMod.MOD_ID)
public class GunMod {
    public static final String MOD_ID = "tacz";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public GunMod(IEventBus bus, ModContainer container) {
        bus.addListener(this::setup);
        bus.addListener(this::registerPayloadHandler);
        com.tacz.guns.init.ModDataComponents.register(bus);

        container.registerConfig(ModConfig.Type.COMMON, GunConfig.init());
        container.registerConfig(ModConfig.Type.COMMON, SyncConfig.init());

        if (FMLEnvironment.dist == Dist.CLIENT) {
            // bus.addListener(ClientSetupEvent::init);
            // bus.addListener(KeyBinding::register);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // CommonNetworkCache.init(); // Removed as it's not found
            // ServerGunPackManager.init();
        });
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        NetworkHandler.register(event);
    }
}































































