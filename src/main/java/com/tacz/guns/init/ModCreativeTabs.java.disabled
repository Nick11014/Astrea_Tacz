package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// All other imports commented out temporarily until their classes are available
/*
import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.item.GunTabType;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.builder.GunItemBuilder;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.AmmoBoxItem;
import com.tacz.guns.item.AmmoItem;
import com.tacz.guns.item.AttachmentItem;
import com.tacz.guns.item.GunSmithTableItem;
*/
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

@SuppressWarnings("all")
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GunMod.MOD_ID);

    // TODO: Re-enable when item classes are available - using basic tabs for now
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> OTHER_TAB = TABS.register("other", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.other"))
            .icon(() -> Items.CRAFTING_TABLE.getDefaultInstance()) // Temporary icon
            .displayItems((parameters, output) -> {
                // TODO: Re-enable when items are available
                /*
                output.acceptAll(GunSmithTableItem.fillItemCategory());
                output.accept(ModItems.TARGET.get());
                output.accept(ModItems.STATUE.get());
                output.accept(ModItems.TARGET_MINECART.get());
                AmmoBoxItem.fillItemCategory(output);
                */
            }).build());

    // TODO: Re-enable other tabs when dependencies are available
    /*
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> AMMO_TAB = TABS.register("ammo", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.ammo")).withTabsBefore(OTHER_TAB.getId())
            .icon(() -> AmmoItemBuilder.create().setId(DefaultAssets.DEFAULT_AMMO_ID).build())
            .displayItems((parameters, output) -> output.acceptAll(AmmoItem.fillItemCategory())).build());
    
    // All other tabs will be re-enabled in future phases when dependencies are available
    */
}
