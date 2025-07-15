package com.tacz.guns.client.init;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.client.other.ThirdPersonManager;
import com.tacz.guns.client.gui.overlay.GunHudOverlay;
import com.tacz.guns.client.gui.overlay.HeatBarOverlay;
import com.tacz.guns.client.gui.overlay.InteractKeyTextOverlay;
import com.tacz.guns.client.gui.overlay.KillAmountOverlay;
import com.tacz.guns.client.input.*;
import com.tacz.guns.client.resource.ClientAssetsManager;
import com.tacz.guns.client.tooltip.ClientAmmoBoxTooltip;
import com.tacz.guns.client.tooltip.ClientAttachmentItemTooltip;
import com.tacz.guns.client.tooltip.ClientBlockItemTooltip;
import com.tacz.guns.client.tooltip.ClientGunTooltip;
import com.tacz.guns.compat.controllable.ControllableCompat;
import com.tacz.guns.compat.playeranimator.PlayerAnimatorCompat;
import com.tacz.guns.compat.shouldersurfing.ShoulderSurfingCompat;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.inventory.tooltip.AmmoBoxTooltip;
import com.tacz.guns.inventory.tooltip.AttachmentItemTooltip;
import com.tacz.guns.inventory.tooltip.BlockItemTooltip;
import com.tacz.guns.inventory.tooltip.GunTooltip;
import com.tacz.guns.item.AmmoBoxItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.CROSSHAIR;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT, modid = GunMod.MOD_ID)
public class ClientSetupEvent {
    @SubscribeEvent
    public static void onClientSetup(RegisterKeyMappingsEvent event) {
        // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ©Ã¢â‚¬ÂÃ‚Â®ÃƒÂ¤Ã‚Â½Ã‚Â
        event.register(InspectKey.INSPECT_KEY);
        event.register(ReloadKey.RELOAD_KEY);
        event.register(ShootKey.SHOOT_KEY);
        event.register(InteractKey.INTERACT_KEY);
        event.register(FireSelectKey.FIRE_SELECT_KEY);
        event.register(AimKey.AIM_KEY);
        event.register(CrawlKey.CRAWL_KEY);
        event.register(RefitKey.REFIT_KEY);
        event.register(ZoomKey.ZOOM_KEY);
        event.register(MeleeKey.MELEE_KEY);
        event.register(ConfigKey.OPEN_CONFIG_KEY);
    }

    @SubscribeEvent
    public static void onClientSetup(RegisterClientTooltipComponentFactoriesEvent event) {
        // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ§Ã‚Â¤Ã‚Âº
        event.register(GunTooltip.class, ClientGunTooltip::new);
        event.register(AmmoBoxTooltip.class, ClientAmmoBoxTooltip::new);
        event.register(AttachmentItemTooltip.class, ClientAttachmentItemTooltip::new);
        event.register(BlockItemTooltip.class, ClientBlockItemTooltip::new);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ HUD
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "gun_hud_overlay"), new GunHudOverlay());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "heat_bar"), new HeatBarOverlay());
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "kill_amount_overlay"), new KillAmountOverlay());
        event.registerAbove(CROSSHAIR, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "interact_key_overlay"), new InteractKeyTextOverlay());

    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚Â¡Ã‚Â¬ÃƒÂ§Ã‚Â¼Ã¢â‚¬â€œÃƒÂ§Ã‚Â Ã‚ÂÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
        event.enqueueWork(ThirdPersonManager::registerDefault);

        // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²
        event.enqueueWork(() -> Minecraft.getInstance().getItemColors().register(AmmoBoxItem::getColor, ModItems.AMMO_BOX.get()));

        // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ§Ã‚Â§Ã‚Â
        // noinspection deprecation
        event.enqueueWork(() -> ItemProperties.register(ModItems.AMMO_BOX.get(), AmmoBoxItem.PROPERTY_NAME, AmmoBoxItem::getStatue));

        // ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã‚Â·Ã‚Â±ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
//        event.enqueueWork(ClientGunPackDownloadManager::init);

                if (ModList.get().isLoaded("playeranimator")) {
            event.enqueueWork(PlayerAnimatorCompat::init);
        }

        // ÃƒÂ¤Ã‚Â¸Ã…Â½ Shoulder Surfing Reloaded ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹
        if (ModList.get().getModContainerById("shouldersurfingreloaded").isPresent()) {
            event.enqueueWork(ShoulderSurfingCompat::init);
        }

        // ÃƒÂ¤Ã‚Â¸Ã…Â½ Controllable ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹
        if (ModList.get().isLoaded("controllable")) {
            event.enqueueWork(ControllableCompat::init);
        }
    }

    @SubscribeEvent
    public static void onClientResourceReload(RegisterClientReloadListenersEvent event) {
        if (ModList.get().isLoaded("playeranimator")) {
            PlayerAnimatorCompat.init();
            ClientAssetsManager.INSTANCE.reloadAndRegister(event::registerReloadListener);
            
        } else {
            ClientAssetsManager.INSTANCE.reloadAndRegister(event::registerReloadListener);
        }
    }
}






























































