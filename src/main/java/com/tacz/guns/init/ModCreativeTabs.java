package com.tacz.guns.init;

import com.tacz.guns.GunMod;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Creative Tabs baseado no padrÃ£o do SuperbWarfare 1.21.1
 * Usando implementaÃ§Ã£o mÃ­nima atÃ© as dependÃªncias estarem disponÃ­veis
 */
@SuppressWarnings("all")
public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GunMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> OTHER_TAB = TABS.register("other", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.other"))
            .icon(() -> ModItems.GUN_SMITH_TABLE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(GunSmithTableItem.fillItemCategory());
                output.accept(ModItems.TARGET.get());
                output.accept(ModItems.STATUE.get());
                output.accept(ModItems.TARGET_MINECART.get());
                AmmoBoxItem.fillItemCategory(output);
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> AMMO_TAB = TABS.register("ammo", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.tacz.ammo")).withTabsBefore(GunMod.loc("other"))
            .icon(() -> AmmoItemBuilder.create().setId(DefaultAssets.DEFAULT_AMMO_ID).build())
            .displayItems((parameters, output) -> {
                output.acceptAll(AmmoItem.fillItemCategory());
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_SCOPE_TAB = TABS.register("scope", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.scope.name")).withTabsBefore(GunMod.loc("ammo"))
            .icon(() -> AttachmentItemBuilder.create().setId(GunMod.loc("scope_acog_ta31")).build())
            .displayItems((parameters, output) -> {
                output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.SCOPE));
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_MUZZLE_TAB = TABS.register("muzzle", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.muzzle.name")).withTabsBefore(GunMod.loc("scope"))
            .icon(() -> AttachmentItemBuilder.create().setId(GunMod.loc("muzzle_compensator_trident")).build())
            .displayItems((parameters, output) -> {
                output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.MUZZLE));
            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_STOCK_TAB = TABS.register("stock", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.stock.name")).withTabsBefore(GunMod.loc("muzzle"))
            .icon(() -> AttachmentItemBuilder.create().setId(GunMod.loc("stock_ar15_m4")).build())
            .displayItems((parameters, output) -> output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.STOCK))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_GRIP_TAB = TABS.register("grip", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.grip.name")).withTabsBefore(GunMod.loc("stock"))
            .icon(() -> AttachmentItemBuilder.create().setId(GunMod.loc("grip_vertical_grip")).build())
            .displayItems((parameters, output) -> output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.GRIP))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_EXTENDED_MAG_TAB = TABS.register("extended_mag", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.extended_mag.name")).withTabsBefore(GunMod.loc("grip"))
            .icon(() -> AttachmentItemBuilder.create().setId(GunMod.loc("extended_mag_ar15_std")).build())
            .displayItems((parameters, output) -> output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.EXTENDED_MAG))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ATTACHMENT_LASER_TAB = TABS.register("laser", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.laser.name")).withTabsBefore(GunMod.loc("extended_mag"))
            .icon(() -> AttachmentItemBuilder.create().setId(GunMod.loc("laser_laser_light")).build())
            .displayItems((parameters, output) -> output.acceptAll(AttachmentItem.fillItemCategory(AttachmentType.LASER))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_PISTOL_TAB = TABS.register("pistol", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.pistol.name")).withTabsBefore(GunMod.loc("laser"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("pistol_glock17")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.PISTOL))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_SNIPER_TAB = TABS.register("sniper", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.sniper.name")).withTabsBefore(GunMod.loc("pistol"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("sniper_awp")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.SNIPER))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_RIFLE_TAB = TABS.register("rifle", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.rifle.name")).withTabsBefore(GunMod.loc("sniper"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("rifle_ak47")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.RIFLE))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_SHOTGUN_TAB = TABS.register("shotgun", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.shotgun.name")).withTabsBefore(GunMod.loc("rifle"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("shotgun_m870")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.SHOTGUN))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_SMG_TAB = TABS.register("smg", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.smg.name")).withTabsBefore(GunMod.loc("shotgun"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("smg_mp5")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.SMG))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_RPG_TAB = TABS.register("rpg", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.rpg.name")).withTabsBefore(GunMod.loc("smg"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("rpg_rpg7")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.RPG))).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> GUN_MG_TAB = TABS.register("mg", () -> CreativeModeTab.builder()
            .title(Component.translatable("tacz.type.mg.name")).withTabsBefore(GunMod.loc("rpg"))
            .icon(() -> GunItemBuilder.create().setId(GunMod.loc("mg_m249")).build())
            .displayItems((parameters, output) -> output.acceptAll(AbstractGunItem.fillItemCategory(GunTabType.MG))).build());
}
































































