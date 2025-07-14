package com.tacz.guns.client.resource;

import com.google.common.collect.Maps;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.client.animation.Animations;
import com.tacz.guns.api.client.animation.ObjectAnimation;
import com.tacz.guns.client.model.bedrock.BedrockModel;
import com.tacz.guns.client.resource.pojo.animation.bedrock.BedrockAnimationFile;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.BedrockVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
// ÃƒÂ¨Ã‚Â¿Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¨Ã‚Â®Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â 
public class InternalAssetLoader {
    // ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â³ÃƒÂ¥Ã¢â‚¬Â¦Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â¼Ã‚Â¹ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
    public static final ResourceLocation DEFAULT_BULLET_TEXTURE = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/entity/basic_bullet.png");
    public static final ResourceLocation DEFAULT_BULLET_MODEL = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "models/bedrock/basic_bullet.json");
    // ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ©Ã‚ÂÃ‚Â¶ÃƒÂ¨Ã‚Â½Ã‚Â¦
    public static final ResourceLocation TARGET_MINECART_MODEL_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "models/bedrock/target_minecart.json");
    public static final ResourceLocation TARGET_MINECART_TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/entity/target_minecart.png");
    public static final ResourceLocation ENTITY_EMPTY_TEXTURE = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/entity/empty.png");
    // ÃƒÂ¥Ã‚Â°Ã¢â‚¬Å¾ÃƒÂ¥Ã¢â‚¬Â¡Ã‚Â»ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ©Ã‚ÂÃ‚Â¶
    public static final ResourceLocation TARGET_MODEL_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "models/bedrock/target.json");
    public static final ResourceLocation TARGET_TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/block/target.png");
    // ÃƒÂ©Ã¢â‚¬ÂºÃ¢â‚¬Â¢ÃƒÂ¥Ã†â€™Ã‚Â
    public static final ResourceLocation STATUE_MODEL_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "models/bedrock/statue.json");
    public static final ResourceLocation STATUE_TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/block/statue.png");
    // ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¨Ã‚Â£Ã¢â‚¬Â¦ÃƒÂ¥Ã‚ÂÃ‚Â°
    public static final ResourceLocation SMITH_TABLE_MODEL_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "models/bedrock/gun_smith_table.json");
    public static final ResourceLocation SMITH_TABLE_TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "textures/block/gun_smith_table.png");
    // ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»
    private static final ResourceLocation DEFAULT_PISTOL_ANIMATIONS_LOC = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "animations/pistol_default.animation.json");
    private static final ResourceLocation DEFAULT_RIFLE_ANIMATIONS_LOC = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "animations/rifle_default.animation.json");
    // ÃƒÂ¥Ã¢â‚¬Â Ã¢â‚¬Â¦ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
    private static final Map<ResourceLocation, BedrockModel> BEDROCK_MODELS = Maps.newHashMap();
    private static List<ObjectAnimation> defaultPistolAnimations;
    private static List<ObjectAnimation> defaultRifleAnimations;

    public static void onResourceReload() {
        // ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
        BedrockAnimationFile pistolAnimationFile = loadAnimations(DEFAULT_PISTOL_ANIMATIONS_LOC);
        BedrockAnimationFile rifleAnimationFile = loadAnimations(DEFAULT_RIFLE_ANIMATIONS_LOC);
        defaultPistolAnimations = Animations.createAnimationFromBedrock(pistolAnimationFile);
        defaultRifleAnimations = Animations.createAnimationFromBedrock(rifleAnimationFile);

        // ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¤Ã‚Â»Ã‚Â£ÃƒÂ§Ã‚Â Ã‚ÂÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã…Â½Ã‚Â¥ÃƒÂ¨Ã‚Â°Ã†â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¥Ã…Â¸Ã‚ÂºÃƒÂ¥Ã‚Â²Ã‚Â©ÃƒÂ§Ã¢â‚¬Â°Ã‹â€ ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
        BEDROCK_MODELS.clear();
        loadBedrockModels(InternalAssetLoader.SMITH_TABLE_MODEL_LOCATION);
        loadBedrockModels(InternalAssetLoader.TARGET_MODEL_LOCATION);
        loadBedrockModels(InternalAssetLoader.TARGET_MINECART_MODEL_LOCATION);
        loadBedrockModels(InternalAssetLoader.DEFAULT_BULLET_MODEL);
        loadBedrockModels(InternalAssetLoader.STATUE_MODEL_LOCATION);
    }

    private static BedrockAnimationFile loadAnimations(ResourceLocation resourceLocation) {
        try (InputStream inputStream = Minecraft.getInstance().getResourceManager().open(resourceLocation)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            JsonObject json = JsonParser.parseReader(bufferedReader).getAsJsonObject();
            return ClientAssetsManager.GSON.fromJson(json, BedrockAnimationFile.class);
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadBedrockModels(ResourceLocation location) {
        try (InputStream stream = Minecraft.getInstance().getResourceManager().open(location)) {
            BedrockModelPOJO pojo = ClientAssetsManager.GSON.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), BedrockModelPOJO.class);
            BEDROCK_MODELS.put(location, new BedrockModel(pojo, BedrockVersion.NEW));
        } catch (IOException | JsonSyntaxException | JsonIOException e) {
            e.fillInStackTrace();
        }
    }

    public static List<ObjectAnimation> getDefaultPistolAnimations() {
        return defaultPistolAnimations;
    }

    public static List<ObjectAnimation> getDefaultRifleAnimations() {
        return defaultRifleAnimations;
    }

    public static Optional<BedrockModel> getBedrockModel(ResourceLocation location) {
        return Optional.ofNullable(BEDROCK_MODELS.get(location));
    }
}































































