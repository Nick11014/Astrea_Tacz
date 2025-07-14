package com.tacz.guns;

// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Imports comentados temporariamente - reabilitar quando disponÃƒÆ’Ã‚Â­veis
// import com.tacz.guns.client.event.ClientSetupEvent;
// import com.tacz.guns.client.init.KeyBinding;
import com.tacz.guns.config.common.GunConfig;
import com.tacz.guns.config.sync.SyncConfig;
import com.tacz.guns.init.CommonRegistry;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.resource.network.CommonNetworkCache;
// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Import comentado temporariamente - reabilitar quando disponÃƒÆ’Ã‚Â­vel
// import com.tacz.guns.resource.server.ServerGunPackManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.resources.ResourceLocation;

@Mod(GunMod.MOD_ID)
public class GunMod {
    public static final String MOD_ID = "tacz";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public GunMod(IEventBus bus) {
        bus.addListener(this::setup);
        bus.addListener(this::registerPayloadHandler);

        CommonRegistry.init(bus);
        GunConfig.init();
        SyncConfig.init();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Funcionalidades comentadas temporariamente - reabilitar quando disponÃƒÆ’Ã‚Â­veis
            // bus.addListener(ClientSetupEvent::init);
            // bus.addListener(KeyBinding::register);
        }
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CommonNetworkCache.init();
            // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Funcionalidade comentada temporariamente - reabilitar quando disponÃƒÆ’Ã‚Â­vel
            // ServerGunPackManager.init();
        });
    }

    private void registerPayloadHandler(final RegisterPayloadHandlersEvent event) {
        NetworkHandler.register(event);
    }
}































































