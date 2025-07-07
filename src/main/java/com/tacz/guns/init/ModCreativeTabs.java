package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// TODO: Re-enable when all dependencies are available
// import com.tacz.guns.api.DefaultAssets;
// import com.tacz.guns.api.item.GunTabType;
// import com.tacz.guns.api.item.attachment.AttachmentType;
// import com.tacz.guns.api.item.builder.AmmoItemBuilder;
// import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
// import com.tacz.guns.api.item.builder.GunItemBuilder;
// import com.tacz.guns.api.item.gun.AbstractGunItem;
// import com.tacz.guns.item.AmmoBoxItem;
// import com.tacz.guns.item.AmmoItem;
// import com.tacz.guns.item.AttachmentItem;
// import com.tacz.guns.item.GunSmithTableItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Creative Tabs baseado no padrão do SuperbWarfare 1.21.1
 * Usando implementação mínima até as dependências estarem disponíveis
 */
@SuppressWarnings("all")
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GunMod.MOD_ID);

    // TODO: Re-enable when ModItems and all builders are available
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OTHER_TAB = TABS.register("other", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.other"))
            .icon(() -> Items.IRON_INGOT.getDefaultInstance()) // Placeholder - ModItems.GUN_SMITH_TABLE.get().getDefaultInstance()
            .displayItems((parameters, output) -> {
                // TODO: Re-enable when items are available
                // output.acceptAll(GunSmithTableItem.fillItemCategory());
                // output.accept(ModItems.TARGET.get());
                // output.accept(ModItems.STATUE.get());
                // output.accept(ModItems.TARGET_MINECART.get());
                // AmmoBoxItem.fillItemCategory(output);
                output.accept(Items.IRON_INGOT); // Placeholder
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AMMO_TAB = TABS.register("ammo", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.ammo")).withTabsBefore(GunMod.loc("other"))
            .icon(() -> Items.ARROW.getDefaultInstance()) // Placeholder - AmmoItemBuilder.create().setId(DefaultAssets.DEFAULT_AMMO_ID).build()
            .displayItems((parameters, output) -> {
                // TODO: Re-enable when AmmoItem is available
                // output.acceptAll(AmmoItem.fillItemCategory())
                output.accept(Items.ARROW); // Placeholder
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_SCOPE_TAB = TABS.register("scope", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.scope.name")).withTabsBefore(GunMod.loc("ammo"))
            .icon(() -> Items.SPYGLASS.getDefaultInstance()) // Placeholder - AttachmentItemBuilder.create().setId(GunMod.loc("scope_acog_ta31")).build()
            .displayItems((parameters, output) -> {
                // TODO: Re-enable when AttachmentItem is available
                // output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.SCOPE))
                output.accept(Items.SPYGLASS); // Placeholder
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_MUZZLE_TAB = TABS.register("muzzle", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.muzzle.name")).withTabsBefore(GunMod.loc("scope"))
            .icon(() -> Items.IRON_NUGGET.getDefaultInstance()) // Placeholder - AttachmentItemBuilder.create().setId(GunMod.loc("muzzle_compensator_trident")).build()
            .displayItems((parameters, output) -> {
                // TODO: Re-enable when AttachmentItem is available
                // output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.MUZZLE))
                output.accept(Items.IRON_NUGGET); // Placeholder
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_STOCK_TAB = TABS.register("stock", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.stock.name")).withTabsBefore(GunMod.loc("muzzle"))
            .icon(() -> Items.STICK.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.STICK)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_GRIP_TAB = TABS.register("grip", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.grip.name")).withTabsBefore(GunMod.loc("stock"))
            .icon(() -> Items.LEATHER.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.LEATHER)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_EXTENDED_MAG_TAB = TABS.register("extended_mag", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.extended_mag.name")).withTabsBefore(GunMod.loc("grip"))
            .icon(() -> Items.IRON_BLOCK.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.IRON_BLOCK)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_LASER_TAB = TABS.register("laser", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.laser.name")).withTabsBefore(GunMod.loc("extended_mag"))
            .icon(() -> Items.REDSTONE_TORCH.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.REDSTONE_TORCH)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_PISTOL_TAB = TABS.register("pistol", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.pistol.name")).withTabsBefore(GunMod.loc("laser"))
            .icon(() -> Items.CROSSBOW.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.CROSSBOW)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_SNIPER_TAB = TABS.register("sniper", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.sniper.name")).withTabsBefore(GunMod.loc("pistol"))
            .icon(() -> Items.BOW.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.BOW)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_RIFLE_TAB = TABS.register("rifle", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.rifle.name")).withTabsBefore(GunMod.loc("sniper"))
            .icon(() -> Items.IRON_SWORD.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.IRON_SWORD)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_SHOTGUN_TAB = TABS.register("shotgun", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.shotgun.name")).withTabsBefore(GunMod.loc("rifle"))
            .icon(() -> Items.TRIDENT.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.TRIDENT)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_SMG_TAB = TABS.register("smg", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.smg.name")).withTabsBefore(GunMod.loc("shotgun"))
            .icon(() -> Items.GOLDEN_SWORD.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.GOLDEN_SWORD)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_RPG_TAB = TABS.register("rpg", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.rpg.name")).withTabsBefore(GunMod.loc("smg"))
            .icon(() -> Items.TNT.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.TNT)).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_MG_TAB = TABS.register("mg", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.mg.name")).withTabsBefore(GunMod.loc("rpg"))
            .icon(() -> Items.IRON_AXE.getDefaultInstance()) // Placeholder
            .displayItems((parameters, output) -> output.accept(Items.IRON_AXE)).build());
}
