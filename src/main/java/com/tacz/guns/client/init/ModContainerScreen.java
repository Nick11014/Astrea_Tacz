package com.tacz.guns.client.init;

import com.tacz.guns.client.gui.GunSmithTableScreen;
import com.tacz.guns.inventory.GunSmithTableMenu;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModContainerScreen {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(GunSmithTableMenu.TYPE, GunSmithTableScreen::new);
    }
}































































