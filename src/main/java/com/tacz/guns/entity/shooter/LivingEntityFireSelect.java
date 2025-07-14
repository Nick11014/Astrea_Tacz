package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.event.common.GunFireSelectEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunFireSelect;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;

public class LivingEntityFireSelect {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityFireSelect(LivingEntity shooter, ShooterDataHolder data) {
        this.shooter = shooter;
        this.data = data;
    }

    public void fireSelect() {
        if (data.currentGunItem == null) {
            return;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        if (!(currentGunItem.getItem() instanceof IGun iGun)) {
            return;
        }
        if (NeoForge.EVENT_BUS.post(new GunFireSelectEvent(shooter, currentGunItem, LogicalSide.SERVER).isCanceled())) {
            return;
        }
        NetworkHandler.sendToTrackingEntity(new ServerMessageGunFireSelect(shooter.getId(), currentGunItem), shooter);
        if (iGun instanceof AbstractGunItem logicGun) {
            logicGun.fireSelect(data, currentGunItem);
            // ÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ§Ã‚Â¼Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â­Ã‹Å“
            AttachmentPropertyManager.postChangeEvent(shooter, currentGunItem);
        }
    }
}































































