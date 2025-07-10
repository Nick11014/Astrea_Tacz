package com.tacz.guns.client.resource.index;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.tacz.guns.client.resource.pojo.display.gun.GunDisplay;
import com.tacz.guns.client.resource.pojo.display.gun.LayerGunShow;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.GunIndexPOJO;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientGunIndex {
    private String name;
    private GunData gunData;
    private String type;
    private String itemType;

    // TODO: [MIGRAÇÃO] Restaurar quando GunDisplayInstance for habilitado
    // private GunDisplayInstance display;
    private GunDisplay display; // Placeholder temporário

    private ClientGunIndex() {
    }

    public static ClientGunIndex getInstance(GunIndexPOJO gunIndexPOJO) throws IllegalArgumentException {
        ClientGunIndex index = new ClientGunIndex();
        checkIndex(gunIndexPOJO, index);
        // TODO: [MIGRAÇÃO] Restaurar quando GunDisplay e ClientAssetsManager forem habilitados
        GunDisplay display = checkDisplay(gunIndexPOJO);
        checkData(gunIndexPOJO, index);
        checkName(gunIndexPOJO, index);
        // TODO: [MIGRAÇÃO] Restaurar quando GunDisplayInstance for habilitado
        // index.display = GunDisplayInstance.create(display);
        index.display = display; // Placeholder temporário
        return index;
    }

    private static void checkIndex(GunIndexPOJO gunIndexPOJO, ClientGunIndex index) {
        Preconditions.checkArgument(gunIndexPOJO != null, "index object file is empty");
        Preconditions.checkArgument(StringUtils.isNoneBlank(gunIndexPOJO.getType()), "index object missing type field");
        index.type = gunIndexPOJO.getType();
        index.itemType = gunIndexPOJO.getItemType();
    }

    private static void checkName(GunIndexPOJO gunIndexPOJO, ClientGunIndex index) {
        index.name = gunIndexPOJO.getName();
        if (StringUtils.isBlank(index.name)) {
            index.name = "custom.tacz.error.no_name";
        }
    }

    private static void checkData(GunIndexPOJO gunIndexPOJO, ClientGunIndex index) {
        ResourceLocation pojoData = gunIndexPOJO.getData();
        Preconditions.checkArgument(pojoData != null, "index object missing pojoData field");
        GunData data = CommonAssetsManager.get().getGunData(pojoData);
        Preconditions.checkArgument(data != null, "there is no corresponding data file");
        // 剩下的不需要校验了，Common的读取逻辑中已经校验过了
        index.gunData = data;
    }

    @NotNull
    private static GunDisplay checkDisplay(GunIndexPOJO gunIndexPOJO) {
        // TODO: [MIGRAÇÃO] Restaurar quando ClientAssetsManager for habilitado
        // ResourceLocation pojoDisplay = gunIndexPOJO.getDisplay();
        // Preconditions.checkArgument(pojoDisplay != null, "index object missing display field");
        // GunDisplay display = ClientAssetsManager.INSTANCE.getGunDisplay(pojoDisplay);
        // Preconditions.checkArgument(display != null, "there is no corresponding display file");
        // return display;
        return new GunDisplay(); // Placeholder temporário
    }

    public String getType() {
        return type;
    }

    public String getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public GunData getGunData() {
        return gunData;
    }

    // TODO: [MIGRAÇÃO] Restaurar quando GunDisplayInstance for habilitado
    // public GunDisplayInstance getDefaultDisplay() {
    //     return display;
    // }
    public GunDisplay getDefaultDisplay() {
        return display; // Placeholder temporário
    }

    public LayerGunShow getOffhandShow() {
        return display.getOffhandShow();
    }

    public Map<Integer, LayerGunShow> getHotbarShow() {
        Map<Integer, LayerGunShow> result = Maps.newHashMap();
        if (display.getHotbarShow() != null) {
            for (Map.Entry<String, LayerGunShow> entry : display.getHotbarShow().entrySet()) {
                try {
                    result.put(Integer.parseInt(entry.getKey()), entry.getValue());
                } catch (NumberFormatException e) {
                    // Ignore invalid keys
                }
            }
        }
        return result;
    }
}
