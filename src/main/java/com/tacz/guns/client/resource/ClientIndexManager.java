package com.tacz.guns.client.resource;

import com.google.common.collect.Maps;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.TimelessAPI;
// TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - dependÃƒÆ’Ã‚Âªncias complexas desabilitadas temporariamente
// import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
// import com.tacz.guns.api.item.IGun;
// import com.tacz.guns.client.resource.index.ClientAmmoIndex;
// import com.tacz.guns.client.resource.index.ClientAttachmentIndex;
// import com.tacz.guns.client.resource.index.ClientBlockIndex;
import com.tacz.guns.client.resource.index.ClientGunIndex;
// import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
// import com.tacz.guns.resource.pojo.AmmoIndexPOJO;
// import com.tacz.guns.resource.pojo.AttachmentIndexPOJO;
// import com.tacz.guns.resource.pojo.BlockIndexPOJO;
import com.tacz.guns.resource.pojo.GunIndexPOJO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ClientIndexManager {
    // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - mapas simplificados temporariamente
    // public static final Map<ResourceLocation, GunDisplayInstance> GUN_DISPLAY = Maps.newHashMap();
    public static final Map<ResourceLocation, ClientGunIndex> GUN_INDEX = Maps.newHashMap();
    // public static final Map<ResourceLocation, ClientAmmoIndex> AMMO_INDEX = Maps.newHashMap();
    // public static final Map<ResourceLocation, ClientAttachmentIndex> ATTACHMENT_INDEX = Maps.newHashMap();
    // public static final Map<ResourceLocation, ClientBlockIndex> BLOCK_INDEX = Maps.newHashMap();

    public static void reload() {
        // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - apenas GUN_INDEX habilitado
        // GUN_DISPLAY.clear();
        GUN_INDEX.clear();
        // AMMO_INDEX.clear();
        // ATTACHMENT_INDEX.clear();
        // BLOCK_INDEX.clear();

        // loadGunDisplay();
        loadGunIndex();
        // loadAmmoIndex();
        // loadAttachmentIndex();
        // loadBlockIndex();

        // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - lÃƒÆ’Ã‚Â³gica de player desabilitada temporariamente
        /*
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null && IGun.mainHandHoldGun(player)) {
            AttachmentPropertyManager.postChangeEvent(player, player.getMainHandItem());

            // ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¬Ã‚Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¤Ã‚Â¾Ã‚Â¿ÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚Âº
            IClientPlayerGunOperator.fromLocalPlayer(player).draw(ItemStack.EMPTY);
        }
        */
    }

    // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - mÃƒÆ’Ã‚Â©todo de gun display desabilitado temporariamente
    /*
    public static void loadGunDisplay() {
        ClientAssetsManager.INSTANCE.getGunDisplays().forEach(entry -> {
            try {
                GUN_DISPLAY.put(entry.getKey(), GunDisplayInstance.create(entry.getValue()));
            } catch (IllegalArgumentException exception) {
                GunMod.LOGGER.warn("{} display init read fail!", entry.getKey(), exception);
            }
        });
    }
    */

    public static void loadGunIndex() {
        // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - carregamento simplificado temporariamente
        /*
        TimelessAPI.getAllCommonGunIndex().forEach(index -> {
            ResourceLocation id = index.getKey();
            GunIndexPOJO pojo = index.getValue().getPojo();
            try {
                GUN_INDEX.put(id, ClientGunIndex.getInstance(pojo));
            } catch (IllegalArgumentException exception) {
                GunMod.LOGGER.warn("{} index file read fail!", id, exception);
            }
        });
        */
        GunMod.LOGGER.info("ClientIndexManager: loadGunIndex() - implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima");
    }

    // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - mÃƒÆ’Ã‚Â©todos de ammo, attachment e block desabilitados temporariamente
    /*
    public static void loadAmmoIndex() {
        TimelessAPI.getAllCommonAmmoIndex().forEach(index -> {
            ResourceLocation id = index.getKey();
            AmmoIndexPOJO pojo = index.getValue().getPojo();
            try {
                AMMO_INDEX.put(id, ClientAmmoIndex.getInstance(pojo));
            } catch (IllegalArgumentException exception) {
                GunMod.LOGGER.warn("{} index file read fail!", id, exception);
            }
        });
    }

    public static void loadAttachmentIndex() {
        TimelessAPI.getAllCommonAttachmentIndex().forEach(index -> {
            ResourceLocation id = index.getKey();
            AttachmentIndexPOJO pojo = index.getValue().getPojo();
            try {
                ATTACHMENT_INDEX.put(id, ClientAttachmentIndex.getInstance(id, pojo));
            } catch (IllegalArgumentException exception) {
                GunMod.LOGGER.warn("{} index file read fail!", id, exception);
            }
        });
    }

    public static void loadBlockIndex() {
        TimelessAPI.getAllCommonBlockIndex().forEach(index -> {
            ResourceLocation id = index.getKey();
            BlockIndexPOJO pojo = index.getValue().getPojo();
            try {
                BLOCK_INDEX.put(id, ClientBlockIndex.getInstance(pojo));
            } catch (IllegalArgumentException exception) {
                GunMod.LOGGER.warn("{} index file read fail!", id, exception);
            }
        });
    }
    */

    public static Set<Map.Entry<ResourceLocation, ClientGunIndex>> getAllGuns() {
        return GUN_INDEX.entrySet();
    }

    // TODO: ImplementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima - getters de ammo, attachment e block desabilitados temporariamente
    /*
    public static Set<Map.Entry<ResourceLocation, ClientAmmoIndex>> getAllAmmo() {
        return AMMO_INDEX.entrySet();
    }

    public static Set<Map.Entry<ResourceLocation, ClientAttachmentIndex>> getAllAttachments() {
        return ATTACHMENT_INDEX.entrySet();
    }

    public static Set<Map.Entry<ResourceLocation, ClientBlockIndex>> getAllBlocks() {
        return BLOCK_INDEX.entrySet();
    }
    */
}































































