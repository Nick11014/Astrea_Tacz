package com.tacz.guns.client.resource.manager;

import com.google.common.collect.Maps;
import com.google.gson.JsonParseException;
import com.tacz.guns.GunMod;
// TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - sistema de animaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o GLTF desabilitado temporariamente
// import com.tacz.guns.api.client.animation.gltf.AnimationStructure;
import com.tacz.guns.client.resource.ClientAssetsManager;
import com.tacz.guns.client.resource.pojo.animation.gltf.RawAnimationStructure;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;


/**
 * Gerenciador de animaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Âµes GLTF - ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
 * TODO: Sistema de animaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o GLTF desabilitado temporariamente
 */
public class GltfManager extends SimplePreparableReloadListener<Map<ResourceLocation, Object>> {
    private static final Marker MARKER = MarkerManager.getMarker("GltfAnimationLoader");

    private final Map<ResourceLocation, Object> dataMap = Maps.newHashMap();
    private final FileToIdConverter filetoidconverter = new FileToIdConverter("animations", ".gltf");

    @Override
    @NotNull
    protected Map<ResourceLocation, Object> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<ResourceLocation, Object> output = Maps.newHashMap();
        
        // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - sistema de animaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o GLTF desabilitado temporariamente
        /*
        for(Map.Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(pResourceManager).entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            ResourceLocation resourcelocation1 = filetoidconverter.fileToId(resourcelocation);

            try (Reader reader = entry.getValue().openAsReader()) {
                RawAnimationStructure rawStructure = ClientAssetsManager.GSON.fromJson(reader, RawAnimationStructure.class);
                AnimationStructure animationStructure = new AnimationStructure(rawStructure);
                output.put(resourcelocation1, animationStructure);
            } catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
                GunMod.LOGGER.warn(MARKER, "Failed to read gltf animation file: {}", resourcelocation);
            }
        }
        */
        
        return output;
    }

    @Override
    protected void apply(Map<ResourceLocation, Object> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        dataMap.clear();
        dataMap.putAll(pObject);
    }

    public Object getGltfAnimation(ResourceLocation id) {
        return dataMap.get(id);
    }
}































































