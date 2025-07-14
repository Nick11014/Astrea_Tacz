package com.tacz.guns.client.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.client.event.SwapItemWithOffHand;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.item.IAnimationItem;
import com.tacz.guns.api.item.IGun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
// TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Tick events nÃƒÆ’Ã‚Â£o estÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­veis ainda
// import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class InventoryEvent {
    // ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬Ëœ
    private static int oldHotbarSelected = -1;
    private static ItemStack oldHotbarSelectItem = ItemStack.EMPTY;

    // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Tick events nÃƒÆ’Ã‚Â£o estÃƒÆ’Ã‚Â£o disponÃƒÆ’Ã‚Â­veis ainda  
    // @SubscribeEvent
    // public static void onPlayerChangeSelect(TickEvent.ClientTickEvent event) {
    //     // TODO: [MIGRAÃƒÆ’Ã¢â‚¬Â¡ÃƒÆ’Ã†â€™O NeoForge 1.21.1] Usando API correta de eventos
    //     if (event.phase != TickEvent.Phase.START) {
    //         return;
    //     }
    //     LocalPlayer player = Minecraft.getInstance().player;
    //     if (player == null) {
    //         return;
    //     }
    //     Inventory inventory = player.getInventory();
    //     // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã‚ÂÃ‚Â¢ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚Âµ
    //     if (oldHotbarSelected != inventory.selected) {
    //         if (oldHotbarSelected == -1) {
    //             IClientPlayerGunOperator.fromLocalPlayer(player).draw(ItemStack.EMPTY);
    //         } else {
    //             IClientPlayerGunOperator.fromLocalPlayer(player).draw(inventory.getItem(oldHotbarSelected));
    //         }
    //         oldHotbarSelected = inventory.selected;
    //         oldHotbarSelectItem = inventory.getItem(inventory.selected).copy();
    //         return;
    //     }
    //     // ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¹ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã†â€™Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â Ã‚Âµ
    //     ItemStack currentItem = inventory.getItem(inventory.selected);
    //     if (currentItem.getItem() instanceof IAnimationItem item ) {
    //         if (!item.isSame(oldHotbarSelectItem, currentItem)) {
    //             IClientPlayerGunOperator.fromLocalPlayer(player).draw(oldHotbarSelectItem);
    //         }
    //     } else {
    //         if (!ItemStack.matches(oldHotbarSelectItem, currentItem)) {
    //             IClientPlayerGunOperator.fromLocalPlayer(player).draw(oldHotbarSelectItem);
    //         }
    //     }
    //     if (!ItemStack.matches(oldHotbarSelectItem, currentItem)) {
    //         oldHotbarSelectItem = currentItem.copy();
    //     }
    // }

    @SubscribeEvent
    public static void onPlayerSwapMainHand(SwapItemWithOffHand event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        IClientPlayerGunOperator.fromLocalPlayer(player).draw(player.getMainHandItem());
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        // ÃƒÂ§Ã‚Â¦Ã‚Â»ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â¸Ã‚Â¸ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚Â®Ã‚Â¢ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ§Ã‚Â«Ã‚Â¯ draw ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        oldHotbarSelected = -1;
        oldHotbarSelectItem = ItemStack.EMPTY;
    }

    private static boolean isSame(ItemStack i, ItemStack j) {
        IGun iGun1 = IGun.getIGunOrNull(i);
        IGun iGun2 = IGun.getIGunOrNull(j);
        if (iGun1 != null && iGun2 != null) {
            return iGun1.getGunId(i).equals(iGun2.getGunId(j));
        }
        if (i.isEmpty() || j.isEmpty()) {
            return i.isEmpty() && j.isEmpty();
        }
        return ItemStack.matches(i, j);
    }
}































































