package com.tacz.guns.resource.pojo.data.block;

import com.google.gson.annotations.SerializedName;
import com.tacz.guns.GunMod;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlockData {
    @NotNull
    @SerializedName("filter")
    private ResourceLocation filter = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "default");

    @SerializedName("tabs")
    // TODO: Re-enable when TabConfig is habilitado
    private List<Object> tabs = new ArrayList<>(); // private List<TabConfig> tabs = new ArrayList<>();

    @NotNull
    public ResourceLocation getFilter() {
        return filter;
    }

    @NotNull
    public List<Object> getTabs() {
        // TODO: Re-enable when TabConfig is habilitado
        return tabs.isEmpty() ? new ArrayList<>() : tabs; // return tabs.isEmpty() ? TabConfig.DEFAULT_TABS : tabs;
    }
}
