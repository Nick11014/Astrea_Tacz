package com.tacz.guns.resource.serialize;

import com.google.gson.*;
import com.tacz.guns.GunMod;
import com.tacz.guns.crafting.result.GunSmithTableResult;
import com.tacz.guns.crafting.result.RawGunTableResult;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.block.TabConfig;
import com.tacz.guns.resource.pojo.data.recipe.GunResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Type;


import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.CraftingContext;

public class GunSmithTableResultSerializer implements JsonDeserializer<GunSmithTableResult> {
    public static final ThreadLocal<HolderLookup.Provider> REGISTRY_ACCESS_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public GunSmithTableResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            String typeName = GsonHelper.getAsString(jsonObject, "type");
            int count = 1;
            ResourceLocation tabOverride = null;
            if (jsonObject.has("count")) {
                count = Math.max(GsonHelper.getAsInt(jsonObject, "count"), 1);
            }
            if (jsonObject.has("group")) {
                String raw = GsonHelper.getAsString(jsonObject, "group");
                if (!raw.contains(":")) {
                    raw = GunMod.MOD_ID + ":" + raw;
                }
                tabOverride = ResourceLocation.tryParse(raw);
            }

            GunSmithTableResult result;
            switch (typeName) {
                case GunSmithTableResult.GUN, GunSmithTableResult.AMMO, GunSmithTableResult.ATTACHMENT -> {
                    RawGunTableResult raw = new RawGunTableResult(typeName, getId(jsonObject), count);
                    if (typeName.equals(GunSmithTableResult.GUN)) {
                        GunResult gunResult = CommonAssetsManager.GSON.fromJson(jsonObject, GunResult.class);
                        if (gunResult != null) {
                            raw.setExtraData(gunResult);
                        }
                    }

                    result = new GunSmithTableResult(raw, tabOverride);
                }
                case GunSmithTableResult.CUSTOM -> {
                    JsonObject resultObject = GsonHelper.getAsJsonObject(jsonObject, "item");
                    HolderLookup.Provider provider = REGISTRY_ACCESS_THREAD_LOCAL.get();
                    if (provider == null) {
                        throw new JsonParseException("HolderLookup.Provider is not available.");
                    }
                    ItemStack itemStack = ItemStack.parse(provider, resultObject).orElseThrow(() -> new JsonParseException("Failed to parse ItemStack"));
                    result = new GunSmithTableResult(itemStack, tabOverride);
                }
                default -> {
                    return new GunSmithTableResult(ItemStack.EMPTY, TabConfig.TAB_EMPTY);
                }
            }
            return result;
        }
        return new GunSmithTableResult(ItemStack.EMPTY, TabConfig.TAB_EMPTY);
    }

    private ResourceLocation getId(JsonObject jsonObject) {
        return ResourceLocation.parse(GsonHelper.getAsString(jsonObject, "id"));
    }
}































































