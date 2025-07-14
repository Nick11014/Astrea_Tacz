package com.tacz.guns.client.model.papi;

import com.google.common.collect.Maps;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public final class PapiManager {
    private static final Map<String, Function<ItemStack, String>> PAPI = Maps.newHashMap();

    // ÃƒÂ¦Ã‚Â³Ã‚Â¨ÃƒÂ¥Ã¢â‚¬Â Ã…â€™ÃƒÂ¯Ã‚Â¼Ã…â€™nÃƒÆ’Ã‚Â£o sei onde colocar, colocando aqui primeiro
    static {
        addPapi(PlayerNamePapi.NAME, new PlayerNamePapi());
        // TODO: ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ AmmoCountPapi habilitado com implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
        addPapi(AmmoCountPapi.NAME, new AmmoCountPapi());
        // TODO: ÃƒÂ¢Ã…â€œÃ¢â‚¬Â¦ FASE B - RestCountPapi e GunNamePapi habilitados com implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima
        addPapi(RestCountPapi.NAME, new RestCountPapi());
        addPapi(GunNamePapi.NAME, new GunNamePapi());
    }

    public static void addPapi(String textKey, Function<ItemStack, String> function) {
        textKey = "%" + textKey + "%";
        PAPI.put(textKey, function);
    }

    public static String getTextShow(String textKey, ItemStack stack) {
        String text = I18n.get(textKey);
        for (var entry : PAPI.entrySet()) {
            String placeholder = entry.getKey();
            String data = entry.getValue().apply(stack);
            text = text.replace(placeholder, data);
        }
        return text;
    }
}































































