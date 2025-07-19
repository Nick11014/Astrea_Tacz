package com.tacz.guns.client.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tacz.guns.api.client.animation.gltf.AnimationStructure;
import com.tacz.guns.client.resource.manager.DisplayManager;
import com.tacz.guns.client.resource.manager.GltfManager;
import com.tacz.guns.client.resource.manager.PackInfoManager;

import com.tacz.guns.client.resource.pojo.CommonTransformObject;
import com.tacz.guns.client.resource.pojo.PackInfo;
import com.tacz.guns.client.resource.pojo.animation.bedrock.AnimationKeyframes;
import com.tacz.guns.client.resource.pojo.animation.bedrock.BedrockAnimationFile;
import com.tacz.guns.client.resource.pojo.animation.bedrock.SoundEffectKeyframes;
import com.tacz.guns.client.resource.pojo.display.ammo.AmmoDisplay;
import com.tacz.guns.client.resource.pojo.display.attachment.AttachmentDisplay;
import com.tacz.guns.client.resource.pojo.display.block.BlockDisplay;
import com.tacz.guns.client.resource.pojo.display.gun.GunDisplay;
import com.tacz.guns.client.resource.pojo.model.BedrockModelPOJO;
import com.tacz.guns.client.resource.pojo.model.CubesItem;
import com.tacz.guns.client.resource.serialize.AnimationKeyframesSerializer;
import com.tacz.guns.client.resource.serialize.ItemStackSerializer;
import com.tacz.guns.client.resource.serialize.SoundEffectKeyframesSerializer;
import com.tacz.guns.client.resource.serialize.Vector3fSerializer;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.manager.JsonDataManager;
import com.tacz.guns.resource.manager.ScriptManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.luaj.vm2.LuaTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import com.tacz.guns.api.vmlib.LuaLibrary;
import com.tacz.guns.api.vmlib.LuaAnimationConstant;
import com.tacz.guns.api.vmlib.LuaGunAnimationConstant;

/**
 * ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨<br/>
 * ÃƒÂ¦Ã¢â‚¬Â°Ã¢â€šÂ¬ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â­Ã‚Â¤
 */
@OnlyIn(Dist.CLIENT)
public enum ClientAssetsManager {
    INSTANCE;
    public static final Gson GSON = new GsonBuilder().registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(CubesItem.class, new CubesItem.Deserializer())
            .registerTypeAdapter(Vector3f.class, new Vector3fSerializer())
            .registerTypeAdapter(CommonTransformObject.class, new CommonTransformObject.Serializer())
            .registerTypeAdapter(ItemStack.class, new ItemStackSerializer())
            .registerTypeAdapter(AnimationKeyframes.class, new AnimationKeyframesSerializer())
            .registerTypeAdapter(SoundEffectKeyframes.class, new SoundEffectKeyframesSerializer())
            .registerTypeAdapter(ItemTransforms.class, new ItemTransforms.Deserializer())
            .registerTypeAdapter(ItemTransform.class, new ItemTransform.Deserializer())
            .create();

    private JsonDataManager<GunDisplay> gunDisplay;
    private JsonDataManager<AmmoDisplay> ammoDisplay;
    private JsonDataManager<AttachmentDisplay> attachmentDisplay;
    private JsonDataManager<BlockDisplay> blockDisplay;
    private JsonDataManager<BedrockModelPOJO> bedrockModel;
    private JsonDataManager<BedrockAnimationFile> bedrockAnimation;
    private GltfManager gltfAnimation;
    private JsonDataManager<com.tacz.guns.resource.pojo.AmmoIndexPOJO> ammoIndex;
    private JsonDataManager<com.tacz.guns.resource.pojo.BlockIndexPOJO> blockIndex;
    private final List<LuaLibrary> libList = List.of(new LuaAnimationConstant(), new LuaGunAnimationConstant());
    private ScriptManager scriptManager;
    
    private PackInfoManager packInfo;

    private List<PreparableReloadListener> listeners;

    public void reloadAndRegister(Consumer<PreparableReloadListener> register) {
        if (listeners == null) {
            listeners = new ArrayList<>();
            gunDisplay = register(new DisplayManager<>(GunDisplay.class, GSON, "display/guns", "GunDisplayLoader"));
            ammoDisplay = register(new DisplayManager<>(AmmoDisplay.class, GSON, "display/ammo", "AmmoDisplayLoader"));
            attachmentDisplay = register(new DisplayManager<>(AttachmentDisplay.class, GSON, "display/attachments", "AttachmentDisplayLoader"));
            blockDisplay = register(new DisplayManager<>(BlockDisplay.class, GSON, "display/blocks", "BlockDisplayLoader"));
            bedrockModel = register(new JsonDataManager<>(BedrockModelPOJO.class, GSON, "geo_models", "BedrockModelLoader"));
            bedrockAnimation = register(new JsonDataManager<>(BedrockAnimationFile.class, GSON, new FileToIdConverter("animations", ".animation.json"), "BedrockAnimationLoader"));
            gltfAnimation = register(new GltfManager());
            scriptManager = register(new ScriptManager(new FileToIdConverter("scripts", ".lua"), libList));
            packInfo = register(new PackInfoManager());
            ammoIndex = register(new JsonDataManager<>(com.tacz.guns.resource.pojo.AmmoIndexPOJO.class, GSON, "index/ammo", "AmmoIndexLoader"));
            blockIndex = register(new JsonDataManager<>(com.tacz.guns.resource.pojo.BlockIndexPOJO.class, GSON, "index/blocks", "BlockIndexLoader"));
        }
        listeners.forEach(register);
    }

    private <T extends PreparableReloadListener> T register(T listener) {
        listeners.add(listener);
        return listener;
    }

    @Nullable
    public GunDisplay getGunDisplay(ResourceLocation id) {
        return gunDisplay.getData(id);
    }

    public Set<Map.Entry<ResourceLocation, GunDisplay>> getGunDisplays() {
        return gunDisplay.getAllData().entrySet();
    }

    @Nullable
    public AttachmentDisplay getAttachmentDisplay(ResourceLocation id) {
        return attachmentDisplay.getData(id);
    }

    @Nullable
    public AmmoDisplay getAmmoDisplay(ResourceLocation id) {
        return ammoDisplay.getData(id);
    }

    @Nullable
    public BlockDisplay getBlockDisplay(ResourceLocation id) {
        return blockDisplay.getData(id);
    }

    @Nullable
    public BedrockModelPOJO getBedrockModelPOJO(ResourceLocation id) {
        return bedrockModel.getData(id);
    }

    @Nullable
    public BedrockAnimationFile getBedrockAnimations(ResourceLocation id) {
        return bedrockAnimation.getData(id);
    }

    @Nullable
    public LuaTable getScript(ResourceLocation id) {
        return scriptManager.getScript(id);
    }

    @Nullable
    public AnimationStructure getGltfAnimation(ResourceLocation id) {
        return gltfAnimation.getGltfAnimation(id);
    }

    @Nullable
    public PackInfo getPackInfo(String namespace) {
        return packInfo.getData(namespace);
    }

    @Nullable
    public PackInfo getPackInfo(@Nullable ResourceLocation namespace) {
        if (namespace == null) {
            return null;
        }
        return packInfo.getData(namespace.getNamespace());
    }
    
    @Nullable
    public com.tacz.guns.resource.pojo.AmmoIndexPOJO getAmmoIndexPOJO(ResourceLocation id) {
        return ammoIndex.getData(id);
    }

    @Nullable
    public com.tacz.guns.resource.pojo.BlockIndexPOJO getBlockIndexPOJO(ResourceLocation id) {
        return blockIndex.getData(id);
    }

    public java.util.Optional<com.tacz.guns.client.resource.index.ClientAmmoIndex> getAmmoIndex(ResourceLocation ammoId) {
        com.tacz.guns.resource.pojo.AmmoIndexPOJO pojo = getAmmoIndexPOJO(ammoId);
        if (pojo == null) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.of(com.tacz.guns.client.resource.index.ClientAmmoIndex.getInstance(pojo));
    }
    
    public java.util.Optional<com.tacz.guns.client.resource.index.ClientBlockIndex> getBlockIndex(ResourceLocation blockId) {
        com.tacz.guns.resource.pojo.BlockIndexPOJO pojo = getBlockIndexPOJO(blockId);
        if (pojo == null) {
            return java.util.Optional.empty();
        }
        return java.util.Optional.of(com.tacz.guns.client.resource.index.ClientBlockIndex.getInstance(pojo));
    }

    @OnlyIn(Dist.CLIENT)
    public static void reloadAllPack() {
        /*
        try {
            Minecraft.getInstance().reloadResourcePacks().get();
            if (ServerLifecycleHooks.getCurrentServer() == null) {
                ClientIndexManager.reload();
            } else {
                CommonAssetsManager.reloadAllPack();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */
    }
}

