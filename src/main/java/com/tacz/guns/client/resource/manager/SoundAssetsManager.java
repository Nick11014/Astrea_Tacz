package com.tacz.guns.client.resource.manager;

import com.google.common.collect.Maps;
// TODO: OggAudioStream foi removido no NeoForge 1.21.1, encontrar alternativa
// import net.minecraft.client.sounds.OggAudioStream;
import com.tacz.guns.GunMod;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Gerenciador de assets de som baseado no padrÃƒÆ’Ã‚Â£o SuperbWarfare 1.21.1
 * ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - OggAudioStream foi removido no NeoForge 1.21.1
 */
public class SoundAssetsManager extends SimplePreparableReloadListener<Map<ResourceLocation, SoundAssetsManager.SoundData>> {
    public record SoundData(ByteBuffer byteBuffer, AudioFormat audioFormat) {
    }
    
    private static final Marker MARKER = MarkerManager.getMarker("SoundsLoader");
    private final Map<ResourceLocation, SoundData> dataMap = Maps.newHashMap();
    private final FileToIdConverter filetoidconverter = new FileToIdConverter("tacz_sounds", ".ogg");

    @Override
    @NotNull
    protected Map<ResourceLocation, SoundData> prepare(ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<ResourceLocation, SoundData> output = Maps.newHashMap();
        
        // TODO: OggAudioStream foi removido no NeoForge 1.21.1
        /*
        for(Map.Entry<ResourceLocation, Resource> entry : filetoidconverter.listMatchingResources(pResourceManager).entrySet()) {
            ResourceLocation resourcelocation = entry.getKey();
            ResourceLocation resourcelocation1 = filetoidconverter.fileToId(resourcelocation);

            try (InputStream stream = entry.getValue().open(); OggAudioStream audioStream = new OggAudioStream(stream)) {
                ByteBuffer bytebuffer = audioStream.readAll();
                output.put(resourcelocation1, new SoundData(bytebuffer, audioStream.getFormat()));
            } catch (IOException exception) {
                GunMod.LOGGER.warn(MARKER, "Failed to read sound file: {}", resourcelocation);
                exception.printStackTrace();
            }
        }
        */
        
        GunMod.LOGGER.info(MARKER, "SoundAssetsManager: Audio loading temporarily disabled - OggAudioStream removed in NeoForge 1.21.1");
        return output;
    }

    @Override
    protected void apply(Map<ResourceLocation, SoundData> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        dataMap.clear();
        dataMap.putAll(pObject);
    }

    public SoundData getData(ResourceLocation id) {
        return dataMap.get(id);
    }
}































































