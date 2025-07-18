package com.tacz.guns.client.resource.index;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.tacz.guns.client.model.BedrockGunModel;
import com.tacz.guns.client.resource.pojo.TransformScale;
import com.tacz.guns.client.resource.pojo.display.gun.GunDisplay;
import com.tacz.guns.client.resource.pojo.display.gun.LayerGunShow;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.pojo.GunIndexPOJO;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import com.tacz.guns.client.resource.index.GunDisplayInstance;

@OnlyIn(Dist.CLIENT)
public class ClientGunIndex {
    private String name;
    private GunData gunData;
    private String type;
    private String itemType;

    // private GunDisplayInstance display;
    private GunDisplay display; // Placeholder temporÃƒÆ’Ã‚Â¡rio

    private ClientGunIndex() {
    }

    public static ClientGunIndex getInstance(GunIndexPOJO gunIndexPOJO) throws IllegalArgumentException {
        ClientGunIndex index = new ClientGunIndex();
        checkIndex(gunIndexPOJO, index);
        GunDisplay display = checkDisplay(gunIndexPOJO);
        checkData(gunIndexPOJO, index);
        checkName(gunIndexPOJO, index);
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
        index.gunData = data;
    }

    @NotNull
    private static GunDisplay checkDisplay(GunIndexPOJO gunIndexPOJO) {
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
    public com.tacz.guns.client.resource.index.GunDisplayInstance getDisplayInstance() {
        // This is a temporary workaround until the display system is fully migrated
        // For now, we'll return null and update the calling code to handle this case
        return null;
    }

    // Temporary method for AnimationStateMachine compatibility during migration
    public com.tacz.guns.api.client.animation.statemachine.AnimationStateMachine<?> getAnimationStateMachine() {
        return null;
    }

    // Temporary method for sound compatibility during migration
    public net.minecraft.resources.ResourceLocation getSounds(String name) {
        return null;
    }
    
    // Implementação dos métodos necessários
    public BedrockGunModel getGunModel() {
        // Implementação temporária retornando um modelo vazio
        return new BedrockGunModel(null, null);
    }
    
    public ResourceLocation getModelTexture() {
        // Implementação temporária retornando uma textura padrão
        return ResourceLocation.fromNamespaceAndPath("tacz", "textures/item/default_gun.png");
    }
    
    public ResourceLocation getSlotTexture() {
        // Implementação temporária retornando uma textura de slot padrão
        return ResourceLocation.fromNamespaceAndPath("tacz", "textures/item/default_slot.png");
    }
    
    public Pair<BedrockGunModel, ResourceLocation> getLodModel() {
        // Implementação temporária retornando um par com modelo e textura padrão
        return Pair.of(getGunModel(), getModelTexture());
    }
    
    public TransformScale getScale() {
        // Implementação temporária retornando uma escala padrão
        return TransformScale.getGunDefault();
    }
}

