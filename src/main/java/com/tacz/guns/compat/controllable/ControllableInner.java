package com.tacz.guns.compat.controllable;

import com.mrcrayfish.controllable.Controllable;
import com.mrcrayfish.controllable.client.binding.ButtonBinding;
import com.mrcrayfish.controllable.client.binding.context.InGameContext;
import com.mrcrayfish.controllable.client.binding.handlers.OnPressHandler;
import com.mrcrayfish.controllable.client.input.Buttons;
import com.mrcrayfish.controllable.client.input.Controller;
import com.mrcrayfish.controllable.event.ControllerEvents;
import com.mrcrayfish.controllable.event.Value;
import com.mrcrayfish.framework.api.event.TickEvents;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.input.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.settings.KeyConflictContext;

import java.util.Optional;

public class ControllableInner {
    
    // Timing controls for better responsiveness
    private static long lastShootTime = 0;
    private static final long SHOOT_COOLDOWN_MS = 50; // Prevent input spam
    
    // Button bindings using the new API with InGameContext
    public static final ButtonBinding AIM = new ButtonBinding(Buttons.LEFT_TRIGGER, "key.tacz.aim.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> AimKey.onAimControllerPress(true));
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding SHOOT = new ButtonBinding(Buttons.RIGHT_TRIGGER, "key.tacz.shoot.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> {
                if (ShootKey.semiShootController(true)) {
                    doRumble(context.minecraft());
                }
            });
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding RELOAD = new ButtonBinding(Buttons.B, "key.tacz.reload.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> {
                if (ReloadKey.onReloadControllerPress(true)) {
                    doSpecialRumble(RumbleType.RELOAD);
                }
            });
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding MELEE = new ButtonBinding(Buttons.X, "key.tacz.melee.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> {
                if (MeleeKey.onMeleeControllerPress(true)) {
                    doSpecialRumble(RumbleType.MELEE);
                }
            });
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding ZOOM = new ButtonBinding(Buttons.Y, "key.tacz.zoom.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> {
                if (ZoomKey.onZoomControllerPress(true)) {
                    doSpecialRumble(RumbleType.ZOOM);
                }
            });
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding CRAWL = new ButtonBinding(Buttons.LEFT_THUMB_STICK, "key.tacz.crawl.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> CrawlKey.onCrawlControllerPress(true));
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding FIRE_SELECT = new ButtonBinding(Buttons.DPAD_LEFT, "key.tacz.fire_select.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> {
                if (FireSelectKey.onFireSelectControllerPress(true)) {
                    doSpecialRumble(RumbleType.FIRE_SELECT);
                }
            });
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding INTERACT = new ButtonBinding(-1, "key.tacz.interact.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> InteractKey.onInteractControllerPress(true));
        }
        return Optional.empty();
    }));
    
    public static final ButtonBinding INSPECT = new ButtonBinding(-1, "key.tacz.inspect.desc", "key.category.tacz", InGameContext.INSTANCE, OnPressHandler.create(context -> {
        if (isGunContextActive()) {
            return Optional.of(() -> InspectKey.onInspectControllerPress(true));
        }
        return Optional.empty();
    }));

    public static void init() {
        try {
            // Register button bindings with new API
            Controllable.getBindingRegistry().register(AIM);
            Controllable.getBindingRegistry().register(SHOOT);
            Controllable.getBindingRegistry().register(RELOAD);
            Controllable.getBindingRegistry().register(MELEE);
            Controllable.getBindingRegistry().register(CRAWL);
            Controllable.getBindingRegistry().register(ZOOM);
            Controllable.getBindingRegistry().register(FIRE_SELECT);
            Controllable.getBindingRegistry().register(INTERACT);
            Controllable.getBindingRegistry().register(INSPECT);

            // Register event handlers
            ControllerEvents.INPUT.register(ControllableInner::onButtonInput);
            TickEvents.END_CLIENT.register(ControllableInner::onClientTickEnd);
            
            System.out.println("TacZ: Controllable bindings registered successfully");
            System.out.println("TacZ: Controller mappings - LT: Aim, RT: Shoot, B: Reload, X: Melee, Y: Zoom");
            System.out.println("TacZ: Controller mappings - LS: Crawl, D-Left: Fire Select, Rumble: Enabled");
        } catch (Exception e) {
            System.err.println("TacZ: Failed to register Controllable bindings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean onButtonInput(Controller controller, Value<Integer> newButton, int originalButton, boolean isPress) {
        if (!isGunContextActive()) {
            return false;
        }
        
        boolean handled = false;
        
        // Handle individual button presses with proper state management
        if (AIM.getButton() == newButton.get()) {
            if (AimKey.onAimControllerPress(isPress)) {
                handled = true;
            }
        }
        
        if (SHOOT.getButton() == newButton.get()) {
            if (isPress) {
                // Anti-spam protection
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastShootTime < SHOOT_COOLDOWN_MS) {
                    return handled;
                }
                lastShootTime = currentTime;
                
                // For semi-auto and burst weapons
                if (ShootKey.semiShootController(true)) {
                    doRumble(Minecraft.getInstance());
                    handled = true;
                }
            } else {
                // Handle button release for semi-auto
                ShootKey.semiShootController(false);
                handled = true;
            }
        }
        
        if (RELOAD.getButton() == newButton.get() && isPress) {
            if (ReloadKey.onReloadControllerPress(true)) {
                doSpecialRumble(RumbleType.RELOAD);
                handled = true;
            }
        }
        
        if (MELEE.getButton() == newButton.get() && isPress) {
            if (MeleeKey.onMeleeControllerPress(true)) {
                doSpecialRumble(RumbleType.MELEE);
                handled = true;
            }
        }
        
        if (CRAWL.getButton() == newButton.get()) {
            if (CrawlKey.onCrawlControllerPress(isPress)) {
                handled = true;
            }
        }
        
        if (ZOOM.getButton() == newButton.get() && isPress) {
            if (ZoomKey.onZoomControllerPress(true)) {
                doSpecialRumble(RumbleType.ZOOM);
                handled = true;
            }
        }
        
        if (FIRE_SELECT.getButton() == newButton.get() && isPress) {
            if (FireSelectKey.onFireSelectControllerPress(true)) {
                doSpecialRumble(RumbleType.FIRE_SELECT);
                handled = true;
            }
        }
        
        if (INTERACT.getButton() == newButton.get() && isPress) {
            if (InteractKey.onInteractControllerPress(true)) {
                handled = true;
            }
        }
        
        if (INSPECT.getButton() == newButton.get() && isPress) {
            if (InspectKey.onInspectControllerPress(true)) {
                handled = true;
            }
        }
        
        return handled;
    }

    public static void onClientTickEnd() {
        if (!isGunContextActive()) {
            return;
        }
        Controller controller = Controllable.getController();
        if (controller == null) {
            return;
        }
        
        // Handle continuous auto-shoot for full-auto and burst weapons
        if (controller.isButtonPressed(SHOOT.getButton())) {
            if (ShootKey.autoShootController()) {
                doRumble(Minecraft.getInstance());
            }
        }
        
        // Handle continuous aim (if hold-to-aim is enabled)
        if (controller.isButtonPressed(AIM.getButton())) {
            // AimKey handles continuous aiming internally
            AimKey.onAimControllerPress(true);
        }
        
        // Handle continuous crawl
        if (controller.isButtonPressed(CRAWL.getButton())) {
            CrawlKey.onCrawlControllerPress(true);
        }
    }

    /**
     * Checks if gun context is active (player has gun in main hand and not in GUI)
     */
    private static boolean isGunContextActive() {
        try {
            LocalPlayer player = Minecraft.getInstance().player;
            return !KeyConflictContext.GUI.isActive() && 
                   player != null && 
                   !player.isSpectator() && 
                   IGun.getIGunOrNull(player.getMainHandItem()) != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Safe method to check if a controller is available and functioning
     */
    public static boolean isControllerActive() {
        try {
            Controller controller = Controllable.getController();
            return controller != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get current controller input for diagnostic purposes
     */
    public static String getControllerStatus() {
        if (!isControllerActive()) {
            return "No controller detected";
        }
        
        return String.format("Controller active - Gun context: %s", isGunContextActive());
    }

    private static void doRumble(Minecraft minecraft) {
        LocalPlayer player = minecraft.player;
        if (player == null) {
            return;
        }
        ItemStack mainHandItem = player.getMainHandItem();
        IGun iGun = IGun.getIGunOrNull(mainHandItem);
        if (iGun == null) {
            return;
        }

        Controller controller = Controllable.getController();
        if (controller == null) {
            return;
        }

        FireMode fireMode = iGun.getFireMode(mainHandItem);
        
        // Enhanced rumble patterns based on fire mode and weapon characteristics
        TimelessAPI.getGunDisplay(mainHandItem).ifPresent(index -> {
            // Get weapon characteristics for dynamic rumble
            TimelessAPI.getCommonGunIndex(iGun.getGunId(mainHandItem)).ifPresentOrElse(gunIndex -> {
                // Use gun data for more realistic rumble
                float baseDamage = gunIndex.getGunData().getBulletData().getDamageAmount();
                
                // Scale rumble based on weapon power (simplified without recoil data)
                float powerMultiplier = Math.min(2.0f, Math.max(0.5f, baseDamage / 10.0f));
                
                switch (fireMode) {
                    case AUTO -> {
                        // Light, rapid rumble for auto weapons
                        controller.rumble(
                            0.1f * powerMultiplier, 
                            0.2f * powerMultiplier, 
                            (int)(60 * powerMultiplier)
                        );
                    }
                    case BURST -> {
                        // Medium intensity for burst
                        controller.rumble(
                            0.2f * powerMultiplier, 
                            0.3f * powerMultiplier, 
                            (int)(80 * powerMultiplier)
                        );
                    }
                    case SEMI -> {
                        // Strong, short rumble for semi-auto
                        controller.rumble(
                            0.3f * powerMultiplier, 
                            0.5f * powerMultiplier, 
                            (int)(120 * powerMultiplier)
                        );
                    }
                    default -> {
                        // Default fallback
                        controller.rumble(0.25f, 0.4f, 100);
                    }
                }
            }, () -> {
                // Fallback for when gun data is not available
                switch (fireMode) {
                    case AUTO -> controller.rumble(0.1f, 0.2f, 60);
                    case BURST -> controller.rumble(0.2f, 0.3f, 80);
                    case SEMI -> controller.rumble(0.3f, 0.5f, 120);
                    default -> controller.rumble(0.25f, 0.4f, 100);
                }
            });
        });
    }
    
    /**
     * Enhanced vibration for special actions with varied intensities
     */
    private static void doSpecialRumble(RumbleType type) {
        Controller controller = Controllable.getController();
        if (controller == null) {
            return;
        }
        
        switch (type) {
            case RELOAD -> {
                // Gentle, longer rumble for reload
                controller.rumble(0.15f, 0.15f, 200);
            }
            case MELEE -> {
                // Strong, impactful rumble for melee
                controller.rumble(0.4f, 0.6f, 150);
            }
            case ZOOM -> {
                // Very subtle rumble for zoom
                controller.rumble(0.05f, 0.1f, 50);
            }
            case FIRE_SELECT -> {
                // Quick, distinctive rumble for fire mode change
                controller.rumble(0.1f, 0.2f, 80);
            }
        }
    }
    
    /**
     * Types of special rumble effects
     */
    private enum RumbleType {
        RELOAD, MELEE, ZOOM, FIRE_SELECT
    }
}
