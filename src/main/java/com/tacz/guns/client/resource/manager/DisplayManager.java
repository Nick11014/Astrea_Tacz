package com.tacz.guns.client.resource.manager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.tacz.guns.GunMod;
import com.tacz.guns.client.resource.pojo.display.IDisplay;
import com.tacz.guns.resource.manager.JsonDataManager;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

/**
 * ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚ÂÃ¢â‚¬Â ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨<br>
 * ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¨Ã‚ÂµÃ¢â‚¬Å¾ÃƒÂ¦Ã‚ÂºÃ‚ÂÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦/ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œjsonÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¥Ã‚Â¹Ã‚Â¶ÃƒÂ¨Ã‚Â§Ã‚Â£ÃƒÂ¦Ã…Â¾Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
 * @param <T> ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ§Ã‚Â±Ã‚Â»ÃƒÂ¥Ã…Â¾Ã¢â‚¬Â¹
 */
public class DisplayManager<T extends IDisplay> extends JsonDataManager<T> {

    public DisplayManager(Class<T> dataClass, Gson pGson, String directory, String marker) {
        super(dataClass, pGson, FileToIdConverter.json(directory), marker);
    }

    public DisplayManager(Class<T> dataClass, Gson pGson, FileToIdConverter fileToIdConverter, String marker) {
        super(dataClass, pGson, fileToIdConverter, marker);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        dataMap.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : pObject.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();
            try {
                T data = getGson().fromJson(element, getDataClass());
                if (data != null) {
                    data.init();
                    dataMap.put(id, data);
                }
            } catch (JsonParseException | IllegalArgumentException e) {
                GunMod.LOGGER.error(getMarker(), "Failed to load data file {}", id, e);
            }
        }
    }

}































































