package com.tacz.guns.client.event;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.util.DelayedTask;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
// TODO: Migrate to new tick event API in NeoForge 1.21.1
// import net.neoforged.neoforge.event.tick.TickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.function.BooleanSupplier;

/**
 * 当玩家跨越维度时，客户端需要刷新一次玩家的配件属性缓存
 */
@EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class RefreshClonePlayerDataEvent {
    @SubscribeEvent
    public static void onClientPlayerClone(ClientPlayerNetworkEvent.Clone event) {
        LocalPlayer newPlayer = event.getNewPlayer();
        // 但是这个事件触发时，玩家的背包并未同步，导致无法读取枪械数据进行配件属性缓存的刷新
        // 延迟 10 tick 执行缓存刷新就好了
        DelayedTask.add(() -> IGunOperator.fromLivingEntity(newPlayer).initialData(), 10);
    }

    /**
     * 延迟执行是通过这个方法执行的
     * TODO: Re-enable when tick events are migrated to NeoForge 1.21.1
     */
    /*
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            try {
                DelayedTask.SUPPLIERS.removeIf(BooleanSupplier::getAsBoolean);
            } catch (Exception e) {
                DelayedTask.SUPPLIERS.clear();
                GunMod.LOGGER.catching(e);
            }
        }
    }
    */
}
