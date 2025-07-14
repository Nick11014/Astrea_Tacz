package com.tacz.guns.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.api.client.event.RenderItemInHandBobEvent;
import com.tacz.guns.api.client.event.RenderLevelBobEvent;
import com.tacz.guns.client.renderer.other.GunHurtBobTweak;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Unique
    private boolean tacz$useFovSetting;

    @Shadow
    public abstract Minecraft getMinecraft();

    @Shadow
    public abstract void render(float pPartialTicks, long pNanoTime, boolean pRenderLevel);

    @Inject(method = "bobHurt", at = @At("HEAD"), cancellable = true)
    public void onBobHurt(PoseStack pMatrixStack, float pPartialTicks, CallbackInfo ci) {
        // ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ¤Ã‚Â¼Ã‚Â¤ÃƒÂ¥Ã‚Â¯Ã‚Â¼ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â´ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â§Ã¢â‚¬Â ÃƒÂ¨Ã‚Â§Ã¢â‚¬â„¢ÃƒÂ¦Ã¢â‚¬ËœÃ¢â‚¬Â¡ÃƒÂ¦Ã¢â€žÂ¢Ã†â€™
        if (this.getMinecraft().getCameraEntity() instanceof LocalPlayer player && !player.isDeadOrDying()) {
            if (GunHurtBobTweak.onHurtBobTweak(player, pMatrixStack, pPartialTicks)) {
                ci.cancel();
                return;
            }
        }
        // ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
        boolean cancel;
        if (!tacz$useFovSetting) {
            cancel = NeoForge.EVENT_BUS.post(new RenderItemInHandBobEvent.BobHurt());
        } else {
            cancel = NeoForge.EVENT_BUS.post(new RenderLevelBobEvent.BobHurt());
        }
        if (cancel) {
            ci.cancel();
        }
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    public void onBobView(PoseStack pMatrixStack, float pPartialTicks, CallbackInfo ci) {
        boolean cancel;
        if (!tacz$useFovSetting) {
            cancel = NeoForge.EVENT_BUS.post(new RenderItemInHandBobEvent.BobView());
        } else {
            cancel = NeoForge.EVENT_BUS.post(new RenderLevelBobEvent.BobView());
        }
        if (cancel) {
            ci.cancel();
        }
    }

    /**
     * ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¸Ã‚Âª hack ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¤Ã‚Â¸Ã‚Âº getFov ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â»Ã‚Âº ÃƒÂ¦Ã…Â Ã¢â‚¬Â¢ÃƒÂ¥Ã‚Â½Ã‚Â±ÃƒÂ§Ã…Â¸Ã‚Â©ÃƒÂ©Ã‹Å“Ã‚Âµ ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¦Ã‚Â­Ã‚Â¤ÃƒÂ¥Ã‚ÂÃ‚Â¯ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¦Ã‚Â Ã‚Â¹ÃƒÂ¦Ã‚ÂÃ‚Â® getFov ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ pUseFovSetting ÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¥Ã¢â‚¬Â¡Ã¢â‚¬Â ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ Level ÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ HandWithItem ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     * ÃƒÂ¨Ã¢â‚¬Â¡Ã‚Â³ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â»Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¥Ã‚Â¯Ã‚Â¹ renderItemInHand ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ mixin ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¥Ã‚Â®Ã¢â‚¬Â°ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â  Optifine ÃƒÂ¤Ã‚Â¹Ã¢â‚¬Â¹ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã‚Â³Ã¢â‚¬Â¢ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¥Ã‚Â¹Ã¢â‚¬Â¦ÃƒÂ¥Ã‚ÂºÃ‚Â¦ÃƒÂ¤Ã‚Â¿Ã‚Â®ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
     */
    @Inject(method = "getFov", at = @At("HEAD"))
    public void switchRenderType(Camera pActiveRenderInfo, float pPartialTicks, boolean pUseFOVSetting, CallbackInfoReturnable<Double> cir) {
        this.tacz$useFovSetting = pUseFOVSetting;
    }
}































































