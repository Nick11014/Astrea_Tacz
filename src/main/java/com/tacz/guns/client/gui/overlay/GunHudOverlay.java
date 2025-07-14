package com.tacz.guns.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.resource.GunDisplayInstance;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.client.resource.pojo.display.gun.AmmoCountStyle;
import com.tacz.guns.config.client.RenderConfig;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class GunHudOverlay implements LayeredDraw.Layer {
    private static final ResourceLocation SEMI = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/hud/fire_mode_semi.png");
    private static final ResourceLocation AUTO = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/hud/fire_mode_auto.png");
    private static final ResourceLocation BURST = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/hud/fire_mode_burst.png");
    private static final ResourceLocation HEATBAR = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/hud/heat_bar.png");
    private static final ResourceLocation HEATBASE = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/hud/heat_base.png");

    private static final DecimalFormat CURRENT_AMMO_FORMAT = new DecimalFormat("000");
    private static final DecimalFormat CURRENT_AMMO_FORMAT_PERCENT = new DecimalFormat("000%");
    private static final DecimalFormat INVENTORY_AMMO_FORMAT = new DecimalFormat("0000");
    private static long checkAmmoTimestamp = -1L;
    private static int cacheMaxAmmoCount = 0;
    private static int cacheInventoryAmmoCount = 0;

    private static final int MAX_AMMO_COUNT = 9999;

    @Override
    public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        if (!RenderConfig.GUN_HUD_ENABLE.get()) {
            return;
        }
        
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        float partialTick = deltaTracker.getGameTimeDeltaPartialTick(false);
        
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (!(player instanceof IClientPlayerGunOperator)) {
            return;
        }
        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof IGun iGun)) {
            return;
        }
        ResourceLocation gunId = iGun.getGunId(stack);

        GunData gunData = TimelessAPI.getClientGunIndex(gunId).map(ClientGunIndex::getGunData).orElse(null);
        GunDisplayInstance display = TimelessAPI.getGunDisplay(stack).orElse(null);
        if (gunData == null || display == null) {
            return;
        }

        // ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»
        boolean useInventoryAmmo = iGun.useInventoryAmmo(stack);
        // ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹
        boolean useDummyAmmo = iGun.useDummyAmmo(stack);
        // ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ§Ã†â€™Ã‚Â­
        boolean overheatLocked = gunData.hasHeatData() && iGun.isOverheatLocked(stack);
        // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        int ammoCount = useInventoryAmmo ? cacheInventoryAmmoCount + (iGun.hasBulletInBarrel(stack) && gunData.getBolt() != Bolt.OPEN_BOLT ? 1 : 0) :
                iGun.getCurrentAmmoCount(stack) + (iGun.hasBulletInBarrel(stack) && gunData.getBolt() != Bolt.OPEN_BOLT ? 1 : 0);
        ammoCount = Math.min(ammoCount, MAX_AMMO_COUNT);
        // ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²
        int ammoCountColor;
        if (ammoCount < (cacheMaxAmmoCount * 0.25) && ammoCount < 10 || overheatLocked) {
            // ÃƒÂ§Ã‚ÂºÃ‚Â¢ÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²
            ammoCountColor = 0xFF5555;
        } else {
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¤Ã‚Â¸Ã¢â‚¬ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ©Ã‚ÂÃ¢â‚¬â„¢ÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ©Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¶ÃƒÂ¤Ã‚Â»Ã¢â‚¬â€œÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ§Ã¢â€žÂ¢Ã‚Â½ÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²
            ammoCountColor = useInventoryAmmo && useDummyAmmo ? 0x55FFFF : useInventoryAmmo ? 0xFFFF55 : 0xFFFFFF;
        }
        // ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²
        int inventoryAmmoCountColor;
        if (!useInventoryAmmo && useDummyAmmo) {
            inventoryAmmoCountColor = 0x55FFFF;
        } else {
            inventoryAmmoCountColor = 0xAAAAAA;
        }

        // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº
        String currentAmmoCountText;
        if (display.getAmmoCountStyle() == AmmoCountStyle.PERCENT) {
            // ÃƒÂ§Ã¢â€žÂ¢Ã‚Â¾ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ¦Ã‚Â¯Ã¢â‚¬ÂÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â
            currentAmmoCountText = CURRENT_AMMO_FORMAT_PERCENT.format((float)ammoCount/(cacheMaxAmmoCount==0 ? 1f : cacheMaxAmmoCount));
        } else {
            // ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â®ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â
            currentAmmoCountText = CURRENT_AMMO_FORMAT.format(ammoCount);
        }

        // ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚Âº (ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¥Ã‚Â¤Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¼Ã‚Â¹)
        String inventoryAmmoCountText = useInventoryAmmo ? "" : INVENTORY_AMMO_FORMAT.format(cacheInventoryAmmoCount);
        if (!useInventoryAmmo && gunData.getReloadData().isInfinite()) {
            inventoryAmmoCountText = "ÃƒÂ¢Ã‹â€ Ã…Â¾";
        }

        // ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        handleCacheCount(player, stack, gunData, iGun, useInventoryAmmo);

        // ÃƒÂ§Ã‚Â«Ã¢â‚¬â€œÃƒÂ§Ã‚ÂºÃ‚Â¿
        graphics.fill(width - 75, height - 43, width - 74, height - 25, 0xFFFFFFFF);

        PoseStack poseStack = graphics.pose();

        Font font = mc.font;

        // ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€
        poseStack.pushPose();
        poseStack.scale(1.5f, 1.5f, 1);
        graphics.drawString(font, currentAmmoCountText, (width - 70) / 1.5f, (height - 43) / 1.5f, ammoCountColor, false);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.scale(0.8f, 0.8f, 1);
        graphics.drawString(font, inventoryAmmoCountText, (width - 68 + mc.font.width(currentAmmoCountText) * 1.5f) / 0.8f, (height - 43) / 0.8f, inventoryAmmoCountColor, false);
        poseStack.popPose();

        // ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ§Ã‚Â»Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¤Ã‚Â¿Ã‚Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¯
        String minecraftVersion = SharedConstants.getCurrentVersion().getName();
        String modVersion = ModList.get().getModFileById(GunMod.MOD_ID).versionString();
        String debugInfo = String.format("%s-%s", minecraftVersion, modVersion);
        // ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¦Ã…â€œÃ‚Â¬
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 1);
        graphics.drawString(font, debugInfo, (int) ((width - 70) / 0.5f), (int) ((height - 29f) / 0.5f), 0xffaaaaaa);
        poseStack.popPose();

        // ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â¾ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â¾ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        ResourceLocation hudTexture = display.getHUDTexture();
        @Nullable ResourceLocation hudEmptyTexture = display.getHudEmptyTexture();

        if (ammoCount <= 0 || overheatLocked) {
            if (hudEmptyTexture == null) {
                RenderSystem.setShaderColor(1, 0.3f, 0.3f, 1);
            } else {
                hudTexture = hudEmptyTexture;
            }
        }
        // ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â¾ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        graphics.blit(hudTexture, width - 117, height - 44, 0, 0, 39, 13, 39, 13);

        // ÃƒÂ¦Ã‚Â¸Ã‚Â²ÃƒÂ¦Ã…Â¸Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ§Ã‚ÂÃ‚Â«ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â¾ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        FireMode fireMode = IGun.getMainHandFireMode(player);
        ResourceLocation fireModeTexture = switch (fireMode) {
            case AUTO -> AUTO;
            case BURST -> BURST;
            default -> SEMI;
        };
        RenderSystem.setShaderColor(1, 1, 1, 1);
        graphics.blit(fireModeTexture, (int) (width - 68.5 + mc.font.width(currentAmmoCountText) * 1.5), height - 38, 0, 0, 10, 10, 10, 10);
    }

    private static void handleCacheCount(LocalPlayer player, ItemStack stack, GunData gunData, IGun iGun, boolean useInventoryAmmo) {
        // 0.05 ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢ (1 tick) ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡
        if ((System.currentTimeMillis() - checkAmmoTimestamp) > 50) {
            checkAmmoTimestamp = System.currentTimeMillis();
            // ÃƒÂ¥Ã‚Â½Ã¢â‚¬Å“ÃƒÂ¥Ã¢â‚¬Â°Ã‚ÂÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
            cacheMaxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(stack, gunData);
            // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
            if (IGunOperator.fromLivingEntity(player).needCheckAmmo()) {
                if (iGun.useDummyAmmo(stack)) {
                    // ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã¢â€žÂ¢Ã…Â¡ÃƒÂ¦Ã¢â‚¬Â¹Ã…Â¸ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
                    cacheInventoryAmmoCount = iGun.getDummyAmmoAmount(stack);
                } else {
                    // ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
                    handleInventoryAmmo(stack, player.getInventory());
                }
            } else {
                cacheInventoryAmmoCount = MAX_AMMO_COUNT;
            }
            if (useInventoryAmmo) {
                iGun.setCurrentAmmoCount(stack, cacheInventoryAmmoCount);
            }
        }
    }

    private static void handleInventoryAmmo(ItemStack stack, Inventory inventory) {
        cacheInventoryAmmoCount = 0;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack inventoryItem = inventory.getItem(i);
            if (inventoryItem.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(stack, inventoryItem)) {
                cacheInventoryAmmoCount += inventoryItem.getCount();
            }
            if (inventoryItem.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(stack, inventoryItem)) {
                // ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¨Ã‚ÂÃ‚Â¯ÃƒÂ§Ã‚Â®Ã‚Â±ÃƒÂ¯Ã‚Â¼Ã…Â¸ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ 9999
                if (iAmmoBox.isAllTypeCreative(inventoryItem) || iAmmoBox.isCreative(inventoryItem)) {
                    cacheInventoryAmmoCount = 9999;
                    return;
                }
                cacheInventoryAmmoCount += iAmmoBox.getAmmoCount(inventoryItem);
            }
        }
    }
}































































