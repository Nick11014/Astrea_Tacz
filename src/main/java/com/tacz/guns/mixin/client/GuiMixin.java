package com.tacz.guns.mixin.client;

// TODO: Re-enable these imports when events are fixed for NeoForge 1.21.1
// import com.tacz.guns.client.event.PreventsHotbarEvent;
// import com.tacz.guns.client.event.RenderCrosshairEvent;
import com.tacz.guns.client.gui.overlay.GunHudOverlay;
import com.tacz.guns.client.gui.overlay.HeatBarOverlay;
import com.tacz.guns.client.gui.overlay.InteractKeyTextOverlay;
import com.tacz.guns.client.gui.overlay.KillAmountOverlay;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract int getGuiTicks();

    @Inject(method = "renderHotbarAndDecorations", at = @At("HEAD"), cancellable = true)
    private void renderHotbarAndDecorations(GuiGraphics graphics, DeltaTracker delta, CallbackInfo ci) {
        // TODO: Implement custom hotbar prevention logic for NeoForge 1.21.1
        // PreventsHotbarEvent.onRenderHotbarEvent(ci);
    }

    @Inject(method = "renderItemHotbar", at = @At("HEAD"))
    private void renderItemHotbar(GuiGraphics graphics, DeltaTracker delta, CallbackInfo ci) {
        // Create overlay instances safely during render instead of static initialization
        new GunHudOverlay().render(graphics, delta);
        new HeatBarOverlay().render(graphics, delta);
        new KillAmountOverlay().render(graphics, delta);
        new InteractKeyTextOverlay().render(graphics, delta);
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCrosshair(GuiGraphics p_282828_, DeltaTracker p_343490_, CallbackInfo ci) {
        // TODO: Fix RenderCrosshairEvent integration for NeoForge 1.21.1
        // RenderCrosshairEvent.onRenderCrosshair(p_282828_, minecraft.getWindow(), p_343490_, ci);
    }
}
