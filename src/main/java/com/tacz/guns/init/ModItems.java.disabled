package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// TODO: Re-enable when dependencies are available
/*
import com.tacz.guns.api.item.gun.GunItemManager;
import com.tacz.guns.item.*;
*/
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, GunMod.MOD_ID);

    // TODO: Re-enable when item classes are available
    /*
    public static Supplier<ModernKineticGunItem> MODERN_KINETIC_GUN = ITEMS.register("modern_kinetic_gun", ModernKineticGunItem::new);

//    public static Supplier<ThrowableItem> M67 = ITEMS.register("m67", ThrowableItem::new);

    public static Supplier<Item> AMMO = ITEMS.register("ammo", AmmoItem::new);
    public static Supplier<AttachmentItem> ATTACHMENT = ITEMS.register("attachment", AttachmentItem::new);

    public static Supplier<GunSmithTableItem> GUN_SMITH_TABLE = ITEMS.register("gun_smith_table", () -> new DefaultTableItem(ModBlocks.GUN_SMITH_TABLE.get()));
    public static Supplier<GunSmithTableItem> WORKBENCH_111 = ITEMS.register("workbench_a", () -> new GunSmithTableItem(ModBlocks.WORKBENCH_111.get()));
    public static Supplier<GunSmithTableItem> WORKBENCH_211 = ITEMS.register("workbench_b", () -> new GunSmithTableItem(ModBlocks.WORKBENCH_211.get()));
    public static Supplier<GunSmithTableItem> WORKBENCH_121 = ITEMS.register("workbench_c", () -> new GunSmithTableItem(ModBlocks.WORKBENCH_121.get()));


    public static Supplier<Item> TARGET = ITEMS.register("target", () -> new BlockItem(ModBlocks.TARGET.get(), new Item.Properties()));
    public static Supplier<Item> STATUE = ITEMS.register("statue", () -> new BlockItem(ModBlocks.STATUE.get(), new Item.Properties()));
    public static Supplier<Item> AMMO_BOX = ITEMS.register("ammo_box", AmmoBoxItem::new);
    public static Supplier<Item> TARGET_MINECART = ITEMS.register("target_minecart", TargetMinecartItem::new);
    */

    @SubscribeEvent
    public static void onItemRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            // TODO: Re-enable when GunItemManager is available
            // GunItemManager.registerGunItem(ModernKineticGunItem.TYPE_NAME, MODERN_KINETIC_GUN);
        }
    }
}
