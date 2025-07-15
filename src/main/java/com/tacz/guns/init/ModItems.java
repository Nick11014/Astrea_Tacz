package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// TODO: Re-enable when all item classes are available
// import com.tacz.guns.api.item.gun.GunItemManager;
// import com.tacz.guns.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registro de itens baseado no padrÃƒÆ’Ã‚Â£o do SuperbWarfare 1.21.1
 * Usando implementaÃƒÆ’Ã‚Â§ÃƒÆ’Ã‚Â£o mÃƒÆ’Ã‚Â­nima atÃƒÆ’Ã‚Â© as classes de item estarem disponÃƒÆ’Ã‚Â­veis
 */
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, GunMod.MOD_ID);

    // TODO: Re-enable when item classes are available
    public static final DeferredHolder<Item, Item> MODERN_KINETIC_GUN = ITEMS.register("modern_kinetic_gun", 
        () -> new Item(new Item.Properties())); // Placeholder - ModernKineticGunItem::new

    public static final DeferredHolder<Item, Item> AMMO = ITEMS.register("ammo", 
        () -> new Item(new Item.Properties())); // Placeholder - AmmoItem::new
        
    public static final DeferredHolder<Item, Item> ATTACHMENT = ITEMS.register("attachment", 
        () -> new Item(new Item.Properties())); // Placeholder - AttachmentItem::new

    public static final DeferredHolder<Item, Item> GUN_SMITH_TABLE = ITEMS.register("gun_smith_table", 
        () -> new Item(new Item.Properties())); // Placeholder - GunSmithTableItem
        
    // Workbenches que foram removidas por engano - adicionando placeholders
    public static final DeferredHolder<Item, Item> WORKBENCH_111 = ITEMS.register("workbench_a", 
        () -> new Item(new Item.Properties())); // Placeholder - GunSmithTableItem(ModBlocks.WORKBENCH_111.get())
        
    public static final DeferredHolder<Item, Item> WORKBENCH_211 = ITEMS.register("workbench_b", 
        () -> new Item(new Item.Properties())); // Placeholder - GunSmithTableItem(ModBlocks.WORKBENCH_211.get())
        
    public static final DeferredHolder<Item, Item> WORKBENCH_121 = ITEMS.register("workbench_c", 
        () -> new Item(new Item.Properties())); // Placeholder - GunSmithTableItem(ModBlocks.WORKBENCH_121.get())
        
    public static final DeferredHolder<Item, Item> TARGET = ITEMS.register("target", 
        () -> new Item(new Item.Properties())); // Placeholder
        
    public static final DeferredHolder<Item, Item> STATUE = ITEMS.register("statue", 
        () -> new Item(new Item.Properties())); // Placeholder
        
    public static final DeferredHolder<Item, Item> AMMO_BOX = ITEMS.register("ammo_box", 
        () -> new Item(new Item.Properties())); // Placeholder
        
    public static final DeferredHolder<Item, Item> TARGET_MINECART = ITEMS.register("target_minecart", 
        () -> new Item(new Item.Properties())); // Placeholder

    // TODO: Re-enable when all dependencies are available
    /*
    @SubscribeEvent
    public static void onRegisterItems(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.ITEM)) {
            GunItemManager.addGunItems();
        }
    }
    */
}































































