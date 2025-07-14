package com.tacz.guns.entity.shooter;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.GunDrawEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunDraw;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.modifier.AttachmentPropertyManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;

import java.util.Optional;
import java.util.function.Supplier;

public class LivingEntityDrawGun {
    private final LivingEntity shooter;
    private final ShooterDataHolder data;

    public LivingEntityDrawGun(LivingEntity shooter, ShooterDataHolder data) {
        this.shooter = shooter;
        this.data = data;
    }

    public void draw(Supplier<ItemStack> gunItemSupplier) {
        // ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¥Ã‚ÂÃ¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
        data.initialData();

        // ÃƒÂ¦Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¦Ã‹â€ Ã‚Â³
        if (data.drawTimestamp == -1) {
            data.drawTimestamp = System.currentTimeMillis();
        }
        if (data.heatTimestamp == -1) {
            data.heatTimestamp = System.currentTimeMillis();
        }
        long drawTime = System.currentTimeMillis() - data.drawTimestamp;
        if (drawTime >= 0) {
            // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¤Ã¢â‚¬Å¾ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬Â¢Ã‚Â¿
            if (drawTime < data.currentPutAwayTimeS * 1000) {
                // ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â Ã‚Â¬ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¥Ã‚Â°Ã‚ÂÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ¦Ã…Â Ã‚Â¬ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
                data.drawTimestamp = System.currentTimeMillis() + drawTime;
            } else {
                // ÃƒÂ¤Ã‚Â»Ã…Â½ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â¡ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ§Ã…Â½Ã‚Â°ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â Ã‚Â¬ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¥Ã‚Â¤Ã‚Â§ÃƒÂ¤Ã‚ÂºÃ…Â½ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¶ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¨Ã‚Â®Ã‚Â¡ÃƒÂ§Ã‚Â®Ã¢â‚¬â€ÃƒÂ£Ã¢â€šÂ¬Ã¢â‚¬Å¡
                data.drawTimestamp = System.currentTimeMillis() + (long) (data.currentPutAwayTimeS * 1000);
            }
        }
        ItemStack lastItem = data.currentGunItem == null ? ItemStack.EMPTY : data.currentGunItem.get();
        NeoForge.EVENT_BUS.post(new GunDrawEvent(shooter, lastItem, gunItemSupplier.get(), LogicalSide.SERVER));
        NetworkHandler.sendToTrackingEntity(new ServerMessageGunDraw(shooter.getId(), lastItem, gunItemSupplier.get()), shooter);
        data.currentGunItem = gunItemSupplier;
        // ÃƒÂ¥Ã‹â€ Ã‚Â·ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â°ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®
        AttachmentPropertyManager.postChangeEvent(shooter, gunItemSupplier.get());
        updatePutAwayTime();
    }

    public long getDrawCoolDown() {
        if (data.currentGunItem == null) {
            return 0;
        }
        ItemStack currentGunItem = data.currentGunItem.get();
        if (!(currentGunItem.getItem() instanceof IGun iGun)) {
            return 0;
        }
        ResourceLocation gunId = iGun.getGunId(currentGunItem);
        Optional<CommonGunIndex> gunIndex = TimelessAPI.getCommonGunIndex(gunId);
        return gunIndex.map(index -> {
            long coolDown = (long) (index.getGunData().getDrawTime() * 1000) - (System.currentTimeMillis() - data.drawTimestamp);
            // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ 5 ms ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€ÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â»Ã‚Â¥ÃƒÂ¥Ã‚Â¹Ã‚Â³ÃƒÂ¨Ã‚Â¡Ã‚Â¡ÃƒÂ¥Ã‚Â»Ã‚Â¶ÃƒÂ¨Ã‚Â¿Ã…Â¸
            coolDown = coolDown - 5;
            if (coolDown < 0) {
                return 0L;
            }
            return coolDown;
        }).orElse(-1L);
    }

    private void updatePutAwayTime() {
        ItemStack gunItem = data.currentGunItem == null ? ItemStack.EMPTY : data.currentGunItem.get();
        IGun iGun = IGun.getIGunOrNull(gunItem);
        if (iGun != null) {
            Optional<CommonGunIndex> gunIndex = TimelessAPI.getCommonGunIndex(iGun.getGunId(gunItem));
            data.currentPutAwayTimeS = gunIndex.map(index -> index.getGunData().getPutAwayTime()).orElse(0F);
        } else {
            data.currentPutAwayTimeS = 0;
        }
    }
}































































