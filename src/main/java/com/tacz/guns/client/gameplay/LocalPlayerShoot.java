package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateMachine;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.api.event.common.GunShootEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerShoot;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.SilenceModifier;
import com.tacz.guns.resource.pojo.data.gun.Bolt;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.sound.SoundManager;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class LocalPlayerShoot {
    private static final Predicate<IGunOperator> SHOOT_LOCKED_CONDITION = operator -> operator.getSynShootCoolDown() > 0;
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;

    public LocalPlayerShoot(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    /**
     * Tenta disparar a arma que o jogador está segurando.
     * Realiza uma série de verificações (cooldown, munição, estado do jogador) antes de efetuar o disparo.
     *
     * @return Um enum ShootResult indicando o resultado da tentativa de disparo.
     */
    public ShootResult shoot() {
        // Impede disparos acidentais muito rápidos (debounce de 50ms)
        if (System.currentTimeMillis() - LocalPlayerDataHolder.clientClickButtonTimestamp < 50) {
            return ShootResult.COOL_DOWN;
        }
        // Garante que o evento de disparo foi registrado antes de prosseguir
        if (!data.isShootRecorded) {
            return ShootResult.COOL_DOWN;
        }
        // Bloqueia o disparo se o jogador estiver em outra ação (como sacar a arma)
        if (data.clientStateLock && data.lockedCondition != SHOOT_LOCKED_CONDITION && data.lockedCondition != null) {
            data.isShootRecorded = true;
            return ShootResult.IS_DRAWING;
        }

        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            return ShootResult.NOT_GUN;
        }

        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        Optional<ClientGunIndex> gunIndexOptional = TimelessAPI.getClientGunIndex(gunId);
        ClientGunIndex display = TimelessAPI.getGunDisplay(mainHandItem).orElse(null);
        if (gunIndexOptional.isEmpty() || display == null) {
            return ShootResult.ID_NOT_EXIST;
        }

        ClientGunIndex gunIndex = gunIndexOptional.get();
        GunData gunData = gunIndex.getGunData();
        long coolDown = this.getCoolDown(iGun, mainHandItem, gunData);

        // Verifica o cooldown de disparo
        if (coolDown >= 50) {
            return ShootResult.COOL_DOWN;
        }

        IGunOperator gunOperator = IGunOperator.fromLivingEntity(player);
        // Verifica se está recarregando, sacando a arma, ferrolhando ou em ataque corpo-a-corpo
        if (gunOperator.getSynReloadState().getStateType().isReloading()) {
            return ShootResult.IS_RELOADING;
        }
        if (gunOperator.getSynDrawCoolDown() != 0) {
            return ShootResult.IS_DRAWING;
        }
        if (gunOperator.getSynIsBolting()) {
            return ShootResult.IS_BOLTING;
        }
        if (gunOperator.getSynMeleeCoolDown() != 0) {
            return ShootResult.IS_MELEE;
        }

        // Verificação de munição
        Bolt boltType = gunData.getBolt();
        boolean useInventoryAmmo = iGun.useInventoryAmmo(mainHandItem);
        boolean hasAmmoInBarrel = iGun.hasBulletInBarrel(mainHandItem) && boltType != Bolt.OPEN_BOLT;
        boolean hasInventoryAmmo = iGun.hasInventoryAmmo(player, mainHandItem, gunOperator.needCheckAmmo()) || hasAmmoInBarrel;
        int ammoCount = iGun.getCurrentAmmoCount(mainHandItem) + (hasAmmoInBarrel ? 1 : 0);
        boolean noAmmo = (useInventoryAmmo && !hasInventoryAmmo) || (!useInventoryAmmo && ammoCount < 1);

        if (noAmmo) {
            SoundPlayManager.playDryFireSound(player, display);
            return ShootResult.NO_AMMO;
        }

        // Verificação de superaquecimento
        if (gunData.hasHeatData() && iGun.isOverheatLocked(mainHandItem)) {
            SoundPlayManager.playDryFireSound(player, display);
            return ShootResult.OVERHEATED;
        }

        // Verifica se precisa ferrolhar (para armas de ação manual)
        if (boltType == Bolt.MANUAL_ACTION && !hasAmmoInBarrel) {
            IClientPlayerGunOperator.fromLocalPlayer(player).bolt();
            return ShootResult.NEED_BOLT;
        }

        // Verifica se está correndo
        if (gunOperator.getSynSprintTime() > 0) {
            return ShootResult.IS_SPRINTING;
        }

        // Dispara o evento de disparo (GunShootEvent)
        GunShootEvent gunShootEvent = new GunShootEvent(player, mainHandItem, LogicalSide.CLIENT);
        NeoForge.EVENT_BUS.post(gunShootEvent);
        if (gunShootEvent.isCancelable() && gunShootEvent.isCanceled()) {
            return ShootResult.FORGE_EVENT_CANCEL;
        }

        // Bloqueia o estado do jogador para a ação de atirar
        data.lockState(SHOOT_LOCKED_CONDITION);
        data.isShootRecorded = false;

        // Executa a lógica de disparo
        this.doShoot(display, iGun, mainHandItem, gunData, coolDown);
        return ShootResult.SUCCESS;
    }

    /**
     * Executa a lógica de disparo, incluindo rajadas, sons e animações.
     */
    private void doShoot(ClientGunIndex display, IGun iGun, ItemStack mainHandItem, GunData gunData, long delay) {
        FireMode fireMode = iGun.getFireMode(mainHandItem);
        Bolt boltType = gunData.getBolt();
        boolean consumeAmmo = IGunOperator.fromLivingEntity(player).consumesAmmoOrNot();
        boolean hasAmmoInBarrel = iGun.hasBulletInBarrel(mainHandItem) && boltType != Bolt.OPEN_BOLT;
        int ammoCount = consumeAmmo ? iGun.getCurrentAmmoCount(mainHandItem) + (hasAmmoInBarrel ? 1 : 0) : Integer.MAX_VALUE;

        long period = (fireMode == FireMode.BURST) ? gunData.getBurstShootInterval() : 1;
        final int maxCount = Math.min(ammoCount, (fireMode == FireMode.BURST) ? gunData.getBurstData().getCount() : 1);
        AtomicInteger count = new AtomicInteger(0);

        LocalPlayerDataHolder.SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(() -> {
            if (count.get() == 0) {
                data.isShootRecorded = true; // Permite o próximo disparo no tick seguinte
            }

            // Cancela a tarefa se a arma superaqueceu, o jogador morreu ou a rajada terminou
            if ((gunData.hasHeatData() && iGun.isOverheatLocked(mainHandItem)) || count.get() >= maxCount || player.isDeadOrDying()) {
                if (Thread.currentThread() instanceof ScheduledFuture<?> future) {
                    future.cancel(false);
                }
                return;
            }

            if (count.get() == 0) {
                if (data.clientStateLock && data.lockedCondition != SHOOT_LOCKED_CONDITION && data.lockedCondition != null) {
                    return;
                }
                // Registra os timestamps do disparo e notifica o servidor
                data.clientLastShootTimestamp = data.clientShootTimestamp;
                data.clientShootTimestamp = System.currentTimeMillis();
                NetworkHandler.sendToServer(new ClientMessagePlayerShoot(data.clientShootTimestamp - data.clientBaseTimestamp));
            }

            // Submete a lógica de som e animação para a thread principal do jogo
            Minecraft.getInstance().submitAsync(() -> {
                // Dispara o evento de "tiro" (GunFireEvent)
                GunFireEvent gunFireEvent = new GunFireEvent(player, mainHandItem, LogicalSide.CLIENT);
                NeoForge.EVENT_BUS.post(gunFireEvent);
                boolean fire = !(gunFireEvent.isCancelable() && gunFireEvent.isCanceled());

                if (fire) {
                    // Aciona a animação de disparo
                    AnimationStateMachine<?> animationStateMachine = display.getAnimationStateMachine();
                    if (animationStateMachine != null) {
                        animationStateMachine.trigger(GunAnimationConstant.INPUT_SHOOT);
                    }
                    // Toca o som de disparo (com ou sem silenciador)
                    final boolean useSilenceSound = this.useSilenceSound();
                    SoundPlayManager.stopPlayGunSound(display, SoundManager.INSPECT_SOUND);
                    if (useSilenceSound) {
                        SoundPlayManager.playSilenceSound(player, display, gunData);
                    } else {
                        SoundPlayManager.playShootSound(player, display, gunData);
                    }
                }
            });

            count.getAndIncrement();
        }, delay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * Verifica se um silenciador está equipado e deve ser usado.
     */
    private boolean useSilenceSound() {
        AttachmentCacheProperty cacheProperty = IGunOperator.fromLivingEntity(player).getCacheProperty();
        if (cacheProperty != null) {
            Pair<Integer, Boolean> silence = cacheProperty.getCache(SilenceModifier.ID);
            return silence.right();
        }
        return false;
    }

    /**
     * Calcula o cooldown de disparo atual.
     */
    private long getCoolDown(IGun iGun, ItemStack mainHandItem, GunData gunData) {
        FireMode fireMode = iGun.getFireMode(mainHandItem);
        long coolDown;
        if (fireMode == FireMode.BURST) {
            coolDown = (long) (gunData.getBurstData().getMinInterval() * 1000f) - (System.currentTimeMillis() - data.clientShootTimestamp);
        } else {
            coolDown = gunData.getShootInterval(this.player, fireMode, mainHandItem) - (System.currentTimeMillis() - data.clientShootTimestamp);
        }
        return Math.max(coolDown, 0);
    }

    /**
     * Obtém o cooldown de disparo do cliente.
     */
    public long getClientShootCoolDown() {
        ItemStack mainHandItem = player.getMainHandItem();
        IGun iGun = IGun.getIGunOrNull(mainHandItem);
        if (iGun == null) {
            return -1;
        }
        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(gunId);
        return gunIndexOptional.map(commonGunIndex -> getCoolDown(iGun, mainHandItem, commonGunIndex.getGunData())).orElse(-1L);
    }
}
