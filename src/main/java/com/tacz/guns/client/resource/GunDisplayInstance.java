package com.tacz.guns.client.resource;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.client.animation.AnimationController;
import com.tacz.guns.api.client.animation.Animations;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.api.client.animation.gltf.AnimationStructure;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateContext;
import com.tacz.guns.api.client.animation.statemachine.LuaAnimationStateMachine;
import com.tacz.guns.api.client.animation.statemachine.LuaStateMachineFactory;
import com.tacz.guns.api.client.other.GunModelTypeManager;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.animation.statemachine.GunAnimationStateContext;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.resource.pojo.animation.bedrock.BedrockAnimationFile;
import com.tacz.guns.client.resource.pojo.display.LaserConfig;
import com.tacz.guns.client.resource.pojo.display.ammo.AmmoParticle;
import com.tacz.guns.client.resource.pojo.display.gun.*;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.BedrockVersion;
import com.tacz.guns.sound.SoundManager;
import com.tacz.guns.util.ColorHex;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.commands.arguments.ParticleArgument;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã‚Â Ã‚Â¡ÃƒÂ©Ã‚ÂªÃ…â€™ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
 */
@OnlyIn(Dist.CLIENT)
public class GunDisplayInstance {
    private String thirdPersonAnimation = "empty";
    private BedrockGunModel gunModel;
    private @Nullable Pair<BedrockGunModel, ResourceLocation> lodModel;
    private LuaAnimationStateMachine<? extends AnimationStateContext> animationStateMachine;
    private @Nullable LuaTable stateMachineParam;
    private @Nullable ResourceLocation playerAnimator3rd = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "rifle_default.player_animation");
    private boolean is3rdFixedHand = false;
    private Map<String, ResourceLocation> sounds;
    private GunTransform transform;
    private ResourceLocation modelTexture;
    private ResourceLocation slotTexture;
    private ResourceLocation hudTexture;
    private @Nullable ResourceLocation hudEmptyTexture;
    private @Nullable ShellEjection shellEjection;
    private @Nullable MuzzleFlash muzzleFlash;
    private LayerGunShow offhandShow;
    private @Nullable Int2ObjectArrayMap<LayerGunShow> hotbarShow;
    private float ironZoom;
    private float zoomModelFov;
    private boolean showCrosshair = false;
    private @Nullable AmmoParticle particle;
    private float @Nullable [] tracerColor = null;
    private EnumMap<FireMode, ControllableData> controllableData;
    private AmmoCountStyle ammoCountStyle = AmmoCountStyle.NORMAL;
    private DamageStyle damageStyle = DamageStyle.PER_PROJECTILE;
    private @Nullable LaserConfig laserConfig;

    GunDisplayInstance(GunDisplay display) {
        checkTextureAndModel(display);
        checkLod(display);
        checkSlotTexture(display);
        checkHUDTexture(display);
        checkAnimation(display);
        checkSounds(display);
        checkTransform(display);
        checkShellEjection(display);
        checkGunAmmo(display);
        checkMuzzleFlash(display);
        checkLayerGunShow(display);
        checkIronZoom(display);
        checkTextShow(display);
        checkZoomModelFov(display);
        showCrosshair = display.isShowCrosshair();
        controllableData = display.getControllableData();
        ammoCountStyle = display.getAmmoCountStyle();
        damageStyle = display.getDamageStyle();
        laserConfig = display.getLaserConfig();
    }

    public static GunDisplayInstance create(GunDisplay display)  throws IllegalArgumentException {
        return new GunDisplayInstance(display);
    }

    private void checkIronZoom(GunDisplay display) {
        ironZoom = display.getIronZoom();
        if (ironZoom < 1) {
            ironZoom = 1;
        }
    }

    private void checkZoomModelFov(GunDisplay display) {
        zoomModelFov = display.getZoomModelFov();
        if (zoomModelFov > 70) {
            zoomModelFov = 70;
        }
    }

    private void checkTextShow(GunDisplay display) {
        Map<String, TextShow> textShowMap = Maps.newHashMap();
        display.getTextShows().forEach((key, value) -> {
            if (StringUtils.isNoneBlank(key)) {
                int color = ColorHex.colorTextToRbgInt(value.getColorText());
                value.setColorInt(color);
                textShowMap.put(key, value);
            }
        });
        gunModel.setTextShowList(textShowMap);
    }

    private void checkTextureAndModel(GunDisplay display) {
        //ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
        String modelType = display.getModelType();
        BiFunction<BedrockModelPOJO, BedrockVersion, ? extends BedrockGunModel> constructor = GunModelTypeManager.getModelInstanceConstructor(modelType);
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
        ResourceLocation modelLocation = display.getModelLocation();
        Preconditions.checkArgument(modelLocation != null, "display object missing model field");
        BedrockModelPOJO modelPOJO = ClientAssetsManager.INSTANCE.getBedrockModelPOJO(modelLocation);
        Preconditions.checkArgument(modelPOJO != null, "there is no corresponding model file");
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¨Ã‚Â´Ã‚Â¨ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ‚Â¦ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã…â€œÃ‚Â¨
        ResourceLocation textureLocation = display.getModelTexture();
        Preconditions.checkArgument(textureLocation != null, "missing default texture");
        modelTexture = textureLocation;
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ 1.10.0 ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
        if (BedrockVersion.isLegacyVersion(modelPOJO) && modelPOJO.getGeometryModelLegacy() != null) {
            gunModel = constructor.apply(modelPOJO, BedrockVersion.LEGACY);
        }
        // ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ 1.12.0 ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
        if (BedrockVersion.isNewVersion(modelPOJO) && modelPOJO.getGeometryModelNew() != null) {
            gunModel = constructor.apply(modelPOJO, BedrockVersion.NEW);
        }
        Preconditions.checkArgument(gunModel != null, "there is no model data in the model file");
    }

    private void checkLod(GunDisplay display) {
        GunLod gunLod = display.getGunLod();
        if (gunLod != null) {
            ResourceLocation texture = gunLod.getModelTexture();
            if (gunLod.getModelLocation() == null) {
                return;
            }
            if (texture == null) {
                return;
            }
            BedrockModelPOJO modelPOJO = ClientAssetsManager.INSTANCE.getBedrockModelPOJO(gunLod.getModelLocation());
            if (modelPOJO == null) {
                return;
            }
            // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ 1.10.0 ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
            if (BedrockVersion.isLegacyVersion(modelPOJO) && modelPOJO.getGeometryModelLegacy() != null) {
                BedrockGunModel model = new BedrockGunModel(modelPOJO, BedrockVersion.LEGACY);
                lodModel = Pair.of(model, texture);
            }
            // ÃƒÂ¥Ã‹â€ Ã‚Â¤ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã‹Å“Ã‚Â¯ 1.12.0 ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã…â€œÃ‚Â¬ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
            if (BedrockVersion.isNewVersion(modelPOJO) && modelPOJO.getGeometryModelNew() != null) {
                BedrockGunModel model = new BedrockGunModel(modelPOJO, BedrockVersion.NEW);
                lodModel = Pair.of(model, texture);
            }
        }
    }

    private void checkAnimation(GunDisplay display) {
        ResourceLocation location = display.getAnimationLocation();
        AnimationController controller;
        if (location == null) {
            controller = new AnimationController(Lists.newArrayList(), gunModel);
        } else {
            // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Cast temporÃƒÆ’Ã‚Â¡rio enquanto getGltfAnimation retorna Object
            AnimationStructure gltfAnimations = (AnimationStructure) ClientAssetsManager.INSTANCE.getGltfAnimation(location);
            BedrockAnimationFile bedrockAnimationFile = ClientAssetsManager.INSTANCE.getBedrockAnimations(location);
            if (bedrockAnimationFile != null) {
                // ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ bedrock ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
                controller = Animations.createControllerFromBedrock(bedrockAnimationFile, gunModel);
            } else if (gltfAnimations != null) {
                // ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ gltf ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ¥Ã‚Â»Ã‚ÂºÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
                controller = Animations.createControllerFromGltf(gltfAnimations, gunModel);
            } else {
                throw new IllegalArgumentException("animation not found: " + location);
            }
            // ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¥Ã‚Â¡Ã‚Â«ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¥ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
            ResourceLocation defaultAnimation = display.getDefaultAnimation();
            if (defaultAnimation != null) {
                BedrockAnimationFile animationFile = ClientAssetsManager.INSTANCE.getBedrockAnimations(defaultAnimation);
                if (animationFile == null) {
                    throw new IllegalArgumentException("animation not found: " + defaultAnimation);
                }
                List<ObjectAnimation> animations = Animations.createAnimationFromBedrock(animationFile);
                for (ObjectAnimation animation : animations) {
                    controller.providePrototypeIfAbsent(animation.name, () -> new ObjectAnimation(animation));
                }
            } else {
                DefaultAnimationType defaultAnimationType = display.getDefaultAnimationType();
                if (defaultAnimationType != null) {
                    switch (defaultAnimationType) {
                        case RIFLE -> {
                            for (ObjectAnimation animation : InternalAssetLoader.getDefaultRifleAnimations()) {
                                controller.providePrototypeIfAbsent(animation.name, () -> new ObjectAnimation(animation));
                            }
                        }
                        case PISTOL -> {
                            for (ObjectAnimation animation : InternalAssetLoader.getDefaultPistolAnimations()) {
                                controller.providePrototypeIfAbsent(animation.name, () -> new ObjectAnimation(animation));
                            }
                        }
                    }
                }
            }
        }
        // ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã…Â½Ã‚Â§ÃƒÂ¥Ã‹â€ Ã‚Â¶ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¥Ã…Â½Ã‚Â»ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
        ResourceLocation stateMachineLocation = display.getStateMachineLocation();
        if (stateMachineLocation == null) {
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€™Ã¢â‚¬Â¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚Âº
            stateMachineLocation = ResourceLocation.fromNamespaceAndPath("tacz", "default_state_machine");
        }
        LuaTable script = ClientAssetsManager.INSTANCE.getScript(stateMachineLocation);
        if (script != null) {
            animationStateMachine = new LuaStateMachineFactory<>()
                    .setController(controller)
                    .setLuaScripts(script)
                    .build();
        } else {
            throw new IllegalArgumentException("statemachine not found: " + stateMachineLocation);
        }
        // ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¡ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°
        Map<String, Object> params = display.getStateMachineParam();
        if (params != null) {
            stateMachineParam = new LuaTable();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                stateMachineParam.set(entry.getKey(), CoerceJavaToLua.coerce(entry.getValue()));
            }
        }
        // ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œÃƒÂ§Ã‚Â¬Ã‚Â¬ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â°ÃƒÂ¤Ã‚ÂºÃ‚ÂºÃƒÂ§Ã‚Â§Ã‚Â°ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
        if (StringUtils.isNoneBlank(display.getThirdPersonAnimation())) {
            thirdPersonAnimation = display.getThirdPersonAnimation();
        }
        // player animator ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¼ÃƒÂ¥Ã‚Â®Ã‚Â¹ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
        if (display.getPlayerAnimator3rd() != null) {
            playerAnimator3rd = display.getPlayerAnimator3rd();
            is3rdFixedHand = display.is3rdFixedHand();
        }
    }

    private void checkSounds(GunDisplay display) {
        sounds = Maps.newHashMap();
        Map<String, ResourceLocation> soundMaps = display.getSounds();
        if (soundMaps == null || soundMaps.isEmpty()) {
            return;
        }
        // Parte do som ÃƒÆ’Ã‚Â© o som padrÃƒÆ’Ã‚Â£o, se nÃƒÆ’Ã‚Â£o existir, vocÃƒÆ’Ã‚Âª precisa adicionar o som padrÃƒÆ’Ã‚Â£o
        soundMaps.putIfAbsent(SoundManager.DRY_FIRE_SOUND, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, SoundManager.DRY_FIRE_SOUND));
        soundMaps.putIfAbsent(SoundManager.FIRE_SELECT, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, SoundManager.FIRE_SELECT));
        soundMaps.putIfAbsent(SoundManager.HEAD_HIT_SOUND, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, SoundManager.HEAD_HIT_SOUND));
        soundMaps.putIfAbsent(SoundManager.FLESH_HIT_SOUND, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, SoundManager.FLESH_HIT_SOUND));
        soundMaps.putIfAbsent(SoundManager.KILL_SOUND, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, SoundManager.KILL_SOUND));
        soundMaps.putIfAbsent(SoundManager.MELEE_BAYONET, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "melee_bayonet/melee_bayonet_01"));
        soundMaps.putIfAbsent(SoundManager.MELEE_STOCK, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "melee_stock/melee_stock_01"));
        soundMaps.putIfAbsent(SoundManager.MELEE_PUSH, ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "melee_stock/melee_stock_02"));
        sounds.putAll(soundMaps);
    }

    private void checkTransform(GunDisplay display) {
        GunTransform readTransform = display.getTransform();
        if (readTransform == null || readTransform.getScale() == null) {
            transform = GunTransform.getDefault();
        } else {
            transform = display.getTransform();
        }
    }

    private void checkSlotTexture(GunDisplay display) {
        // ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ GUI ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¥Ã¢â‚¬ÂºÃ‚Â¾ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡
        slotTexture = Objects.requireNonNullElseGet(display.getSlotTextureLocation(), MissingTextureAtlasSprite::getLocation);
    }

    private void checkHUDTexture(GunDisplay display) {
        hudTexture = Objects.requireNonNullElseGet(display.getHudTextureLocation(), MissingTextureAtlasSprite::getLocation);
        hudEmptyTexture = display.getHudEmptyTextureLocation();
    }

    private void checkShellEjection(GunDisplay display) {
        shellEjection = display.getShellEjection();
    }

    private void checkGunAmmo(GunDisplay display) {
        GunAmmo displayGunAmmo = display.getGunAmmo();
        if (displayGunAmmo == null) {
            return;
        }
        String tracerColorText = displayGunAmmo.getTracerColor();
        if (StringUtils.isNoneBlank(tracerColorText)) {
            tracerColor = ColorHex.colorTextToRbgFloatArray(tracerColorText);
        }
        AmmoParticle particle = displayGunAmmo.getParticle();
        if (particle != null) {
            try {
                String name = particle.getName();
                if (StringUtils.isNoneBlank(name)) {
                    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] ParticleArgument.readParticle agora requer HolderLookup.Provider
                    // particle.setParticleOptions(ParticleArgument.readParticle(new StringReader(name), BuiltInRegistries.PARTICLE_TYPE.asLookup()));
                    Preconditions.checkArgument(particle.getCount() > 0, "particle count must be greater than 0");
                    Preconditions.checkArgument(particle.getLifeTime() > 0, "particle life time must be greater than 0");
                    // this.particle = particle; // Temporariamente desabilitado
                }
            } catch (Exception e) {  // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] CommandSyntaxException pode nÃƒÆ’Ã‚Â£o ser mais necessÃƒÆ’Ã‚Â¡rio
                throw new RuntimeException(e);
            }
        }
    }

    private void checkMuzzleFlash(GunDisplay display) {
        muzzleFlash = display.getMuzzleFlash();
        if (muzzleFlash != null && muzzleFlash.getTexture() == null) {
            muzzleFlash = null;
        }
    }

    private void checkLayerGunShow(GunDisplay display) {
        offhandShow = display.getOffhandShow();
        if (offhandShow == null) {
            offhandShow = new LayerGunShow();
        }
        Map<String, LayerGunShow> show = display.getHotbarShow();
        if (show == null || show.isEmpty()) {
            return;
        }
        hotbarShow = new Int2ObjectArrayMap<>();
        for (String key : show.keySet()) {
            try {
                hotbarShow.put(Integer.parseInt(key), show.get(key));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("index number is error: " + key);
            }
        }
    }

    public BedrockGunModel getGunModel() {
        return gunModel;
    }

    @Nullable
    public Pair<BedrockGunModel, ResourceLocation> getLodModel() {
        return lodModel;
    }

    public LuaAnimationStateMachine<? extends AnimationStateContext> getAnimationStateMachine() {
        return animationStateMachine;
    }

    public @Nullable LuaTable getStateMachineParam() {
        return stateMachineParam;
    }

    @Nullable
    public ResourceLocation getSounds(String name) {
        return sounds.get(name);
    }

    public GunTransform getTransform() {
        return transform;
    }

    public ResourceLocation getSlotTexture() {
        return slotTexture;
    }

    public ResourceLocation getHUDTexture() {
        return hudTexture;
    }

    @Nullable
    public ResourceLocation getHudEmptyTexture() {
        return hudEmptyTexture;
    }

    public ResourceLocation getModelTexture() {
        return modelTexture;
    }

    public String getThirdPersonAnimation() {
        return thirdPersonAnimation;
    }

    @Nullable
    public ShellEjection getShellEjection() {
        return shellEjection;
    }

    public float @Nullable [] getTracerColor() {
        return tracerColor;
    }

    @Nullable
    public AmmoParticle getParticle() {
        return particle;
    }

    @Nullable
    public MuzzleFlash getMuzzleFlash() {
        return muzzleFlash;
    }

    public LayerGunShow getOffhandShow() {
        return offhandShow;
    }

    @Nullable
    public Int2ObjectArrayMap<LayerGunShow> getHotbarShow() {
        return hotbarShow;
    }

    public float getIronZoom() {
        return ironZoom;
    }

    public float getZoomModelFov() {
        return zoomModelFov;
    }

    public boolean isShowCrosshair() {
        return showCrosshair;
    }

    public @Nullable ResourceLocation getPlayerAnimator3rd() {
        return playerAnimator3rd;
    }

    public boolean is3rdFixedHand() {
        return is3rdFixedHand;
    }

    public EnumMap<FireMode, ControllableData> getControllableData() {
        return controllableData;
    }

    public AmmoCountStyle getAmmoCountStyle() {
        return ammoCountStyle;
    }

    public DamageStyle getDamageStyle() {
        return damageStyle;
    }

    public @Nullable LaserConfig getLaserConfig() {
        return laserConfig;
    }
}































































