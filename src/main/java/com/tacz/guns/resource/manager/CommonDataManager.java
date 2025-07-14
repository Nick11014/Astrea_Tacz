package com.tacz.guns.resource.manager;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tacz.guns.resource.network.DataType;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

/**
 * ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¤Ã‚Â¾Ã‚Â§ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨<br>
 * ÃƒÂ¨Ã‚Â¯Ã‚Â¥ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ÃƒÂ¥Ã‚ÂÃ…â€™ÃƒÂ¦Ã‚Â­Ã‚Â¥
 * @param <T> ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
 */
public class CommonDataManager<T> extends JsonDataManager<T> implements INetworkCacheReloadListener {
    private final DataType type;
    protected Map<ResourceLocation, String> networkCache;

    public CommonDataManager(DataType type, Class<T> dataClass, Gson pGson, String directory, String marker) {
        super(dataClass, pGson, directory, marker);
        this.type = type;
    }

    public CommonDataManager(DataType type, Class<T> dataClass, Gson pGson, FileToIdConverter fileToIdConverter, String marker) {
        super(dataClass, pGson, fileToIdConverter, marker);
        this.type = type;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        super.apply(pObject, pResourceManager, pProfiler);

        ImmutableMap.Builder<ResourceLocation, String> builder = ImmutableMap.builder();
        pObject.forEach((id, element) -> builder.put(id, element.toString()));
        this.networkCache = builder.build();
    }

    public void clear() {
        this.dataMap.clear();
    }

    public Map<ResourceLocation, String> getNetworkCache() {
        return this.networkCache;
    }

    public DataType getType() {
        return this.type;
    }
}































































