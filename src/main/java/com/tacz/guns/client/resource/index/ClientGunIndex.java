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

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Restaurar quando GunDisplayInstance for habilitado
    // private GunDisplayInstance display;
    private GunDisplay display; // Placeholder temporÃƒÆ’Ã‚Â¡rio

    private ClientGunIndex() {
    }

    public static ClientGunIndex getInstance(GunIndexPOJO gunIndexPOJO) throws IllegalArgumentException {
        ClientGunIndex index = new ClientGunIndex();
        checkIndex(gunIndexPOJO, index);
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Restaurar quando GunDisplay e ClientAssetsManager forem habilitados
        GunDisplay display = checkDisplay(gunIndexPOJO);
        checkData(gunIndexPOJO, index);
        checkName(gunIndexPOJO, index);
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Restaurar quando GunDisplayInstance for habilitado
        // index.display = GunDisplayInstance.create(display);
        index.display = display; // Placeholder temporÃƒÆ’Ã‚Â¡rio
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
        // ÃƒÂ¥Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¦Ã‚Â Ã‚Â¡ÃƒÂ©Ã‚ÂªÃ…â€™ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™CommonÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã‚Â¯Ã‚Â»ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€œÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â·Ã‚Â²ÃƒÂ§Ã‚Â»Ã‚ÂÃƒÂ¦Ã‚Â Ã‚Â¡ÃƒÂ©Ã‚ÂªÃ…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬Â¡ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â 
        index.gunData = data;
    }

    @NotNull
    private static GunDisplay checkDisplay(GunIndexPOJO gunIndexPOJO) {
        // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Restaurar quando ClientAssetsManager for habilitado
        // ResourceLocation pojoDisplay = gunIndexPOJO.getDisplay();
        // Preconditions.checkArgument(pojoDisplay != null, "index object missing display field");
        // GunDisplay display = ClientAssetsManager.INSTANCE.getGunDisplay(pojoDisplay);
        // Preconditions.checkArgument(display != null, "there is no corresponding display file");
        // return display;
        return new GunDisplay(); // Placeholder temporÃƒÆ’Ã‚Â¡rio
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

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O] Restaurar quando GunDisplayInstance for habilitado
    // public GunDisplayInstance getDefaultDisplay() {
    //     return display;
    // }
    public GunDisplay getDefaultDisplay() {
        return display; // Placeholder temporÃƒÆ’Ã‚Â¡rio
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

    // Temporary method to get GunDisplayInstance for compatibility with existing sound system
    public com.tacz.guns.client.resource.GunDisplayInstance getDisplayInstance() {
        // This is a temporary workaround until the display system is fully migrated
        // For now, we'll return null and update the calling code to handle this case
        return null;
    }
}































































