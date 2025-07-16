package com.tacz.guns.resource.serialize;

import com.google.gson.*;
import com.tacz.guns.GunMod;
import com.tacz.guns.crafting.result.GunSmithTableResult;
import com.tacz.guns.crafting.result.RawGunTableResult;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.block.TabConfig;
import com.tacz.guns.resource.pojo.data.recipe.GunResult;
import com.google.gson.*;
import com.tacz.guns.GunMod;
import com.tacz.guns.crafting.result.GunSmithTableResult;
import com.tacz.guns.crafting.result.RawGunTableResult;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.data.block.TabConfig;
import com.tacz.guns.resource.pojo.data.recipe.GunResult;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

import java.lang.reflect.Type;


public class GunSmithTableResultSerializer implements JsonDeserializer<GunSmithTableResult> {

    @Override
    public GunSmithTableResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            String typeName = GsonHelper.getAsString(jsonObject, "type");
            int count = 1;
            CompoundTag extraTag = null;
            ResourceLocation tabOverride = null;
            if (jsonObject.has("count")) {
                count = Math.max(GsonHelper.getAsInt(jsonObject, "count"), 1);
            }
            if (jsonObject.has("nbt")) {
                // TODO: [MIGRATION] CraftingHelper.getNBT() removed in NeoForge 1.21.1 - use DataComponents
                // extraTag = CraftingHelper.getNBT(jsonObject.get("nbt"));
                extraTag = null; // Temporary placeholder
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
                    if (extraTag != null) {
                        raw.setNbt(extraTag);
                    }
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
                    // In a real modding scenario, you would get your JsonObject from a file or network.
                    // And the HolderLookup.Provider would typically be available from a server or client context.
                    // For a simple demonstration, we'll use null, but this will likely cause issues
                    // if the JSON contains actual item IDs that need to be resolved.
                    // You would need a proper HolderLookup.Provider to resolve "minecraft:dirt" to an actual Item object.
                    // HolderLookup.Provider dummyRegistries = null; // Replace with actual registries in a mod environment
                    // ItemStack itemStack = ItemStack.CODEC.decode(dummyRegistries, JsonOps.INSTANCE, resultObject)
                    //         .getOrThrow(IllegalStateException::new)
                    //         .getFirst();
                    ItemStack itemStack = ItemStack.EMPTY; // Placeholder for now, needs HolderLookup.Provider
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































































