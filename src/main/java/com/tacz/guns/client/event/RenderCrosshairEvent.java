package com.tacz.guns.client.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateContext;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateMachine;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.client.renderer.crosshair.CrosshairType;
import com.tacz.guns.compat.shouldersurfing.ShoulderSurfingCompat;
import com.tacz.guns.config.client.RenderConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.event.RenderFrameEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = GunMod.MOD_ID, value = Dist.CLIENT)
public class RenderCrosshairEvent {
    private static final ResourceLocation HIT_ICON = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/crosshair/hit/hit_marker.png");
    private static final long KEEP_TIME = 300;
    private static boolean isRefitScreen = false;
    private static long hitTimestamp = -1L;
    private static long killTimestamp = -1L;
    private static long headShotTimestamp = -1L;

    /**
     * ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â¿ÃƒÂ§Ã‚ÂÃ¢â€šÂ¬ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â¹ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ§Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ©Ã…Â¡Ã‚ÂÃƒÂ¨Ã¢â‚¬â€Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¿Ã†â€™
     */
    @SubscribeEvent(receiveCanceled = true)
    public static void onRenderOverlay(RenderGuiLayerEvent.Pre event) {
        if (event.getName().equals(VanillaGuiLayers.CROSSHAIR)) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            if (!IGun.mainHandHoldGun(player)) {
                return;
            }
            event.setCanceled(true);
            renderHitMarker(event.getGuiGraphics(), Minecraft.getInstance().getWindow());
            ReloadState reloadState = IGunOperator.fromLivingEntity(player).getSynReloadState();
            if (reloadState.getStateType().isReloading()) {
                return;
            }
            if (isRefitScreen) {
                return;
            }
            ItemStack stack = player.getMainHandItem();
            if (!(stack.getItem() instanceof IGun)) {
                return;
            }

            IClientPlayerGunOperator playerGunOperator = IClientPlayerGunOperator.fromLocalPlayer(player);
            TimelessAPI.getGunDisplay(stack).ifPresent(gunIndex -> {
                if (playerGunOperator.getClientAimingProgress(event.getPartialTick().getGameTimeDeltaPartialTick(false)) > 0.9) {
                    boolean forceShow = gunIndex.getDefaultDisplay().isShowCrosshair();
                    boolean shoulderSurfingForceShow = ShoulderSurfingCompat.showCrosshair();
                    if (!forceShow && !shoulderSurfingForceShow) {
                        return;
                    }
                }

                AnimationStateMachine<?> animationStateMachine = gunIndex.getAnimationStateMachine();
                AnimationStateContext context = animationStateMachine.getContext();
                if (context == null || !context.shouldHideCrossHair()) {
                    renderCrosshair(event.getGuiGraphics(), Minecraft.getInstance().getWindow());
                }
            });
        }
    }

    @SubscribeEvent
    public static void onRenderTick(RenderFrameEvent.Pre event) {
        isRefitScreen = Minecraft.getInstance().screen instanceof GunRefitScreen;
    }

    private static void renderCrosshair(GuiGraphics graphics, Window window) {
        Options options = Minecraft.getInstance().options;
        boolean shoulderSurfingForceShow = ShoulderSurfingCompat.showCrosshair();
        if (!options.getCameraType().isFirstPerson() && !shoulderSurfingForceShow) {
            return;
        }
        if (options.hideGui) {
            return;
        }
        MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
        if (gameMode == null) {
            return;
        }
        if (gameMode.getPlayerMode() == GameType.SPECTATOR) {
            return;
        }
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();

        ResourceLocation location = CrosshairType.getTextureLocation(RenderConfig.CROSSHAIR_TYPE.get());

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor(1F, 1F, 1F, 0.9f);
        float x = width / 2f - 8;
        float y = height / 2f - 8;
        graphics.blit(location, (int) x, (int) y, 0, 0, 16, 16, 16, 16);
    }

    private static void renderHitMarker(GuiGraphics graphics, Window window) {
        long remainHitTime = System.currentTimeMillis() - hitTimestamp;
        long remainKillTime = System.currentTimeMillis() - killTimestamp;
        long remainHeadShotTime = System.currentTimeMillis() - headShotTimestamp;
        float offset = RenderConfig.HIT_MARKET_START_POSITION.get().floatValue();
        float fadeTime;

        if (remainKillTime > KEEP_TIME) {
            if (remainHitTime > KEEP_TIME) {
                return;
            } else {
                fadeTime = remainHitTime;
            }
        } else {
            offset += (remainKillTime * 4f) / KEEP_TIME;
            fadeTime = remainKillTime;
        }

        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();
        float x = width / 2f - 8;
        float y = height / 2f - 8;

        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        if (remainHeadShotTime > KEEP_TIME) {
            RenderSystem.setShaderColor(1F, 1F, 1F, 1 - fadeTime / KEEP_TIME);
        } else {
            RenderSystem.setShaderColor(1F, 0, 0, 1 - fadeTime / KEEP_TIME);
        }

        graphics.blit(HIT_ICON, (int) (x - offset), (int) (y - offset), 0, 0, 8, 8, 16, 16);
        graphics.blit(HIT_ICON, (int) (x + 8 + offset), (int) (y - offset), 8, 0, 8, 8, 16, 16);
        graphics.blit(HIT_ICON, (int) (x - offset), (int) (y + 8 + offset), 0, 8, 8, 8, 16, 16);
        graphics.blit(HIT_ICON, (int) (x + 8 + offset), (int) (y + 8 + offset), 8, 8, 8, 8, 16, 16);
    }

    public static void markHitTimestamp() {
        RenderCrosshairEvent.hitTimestamp = System.currentTimeMillis();
    }

    public static void markKillTimestamp() {
        RenderCrosshairEvent.killTimestamp = System.currentTimeMillis();
    }

    public static void markHeadShotTimestamp() {
        RenderCrosshairEvent.headShotTimestamp = System.currentTimeMillis();
    }
}






























































