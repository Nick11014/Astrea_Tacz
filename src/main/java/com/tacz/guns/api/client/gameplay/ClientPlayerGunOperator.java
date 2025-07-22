package com.tacz.guns.api.client.gameplay;

import com.tacz.guns.api.entity.ShootResult;
import com.tacz.guns.capability.PlayerGunCapability;
import com.tacz.guns.client.gameplay.LocalPlayerDataHolder;
import com.tacz.guns.client.gameplay.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Sistema de operações de armas para o jogador cliente
 * Agora usa um sistema de capability em vez de mixins
 */
@OnlyIn(Dist.CLIENT)
public class ClientPlayerGunOperator {
    
    private final LocalPlayer player;
    private final LocalPlayerDataHolder dataHolder;
    private final LocalPlayerAim aim;
    private final LocalPlayerCrawl crawl;
    private final LocalPlayerBolt bolt;
    private final LocalPlayerDraw draw;
    private final LocalPlayerFireSelect fireSelect;
    private final LocalPlayerMelee melee;
    private final LocalPlayerInspect inspect;
    private final LocalPlayerReload reload;
    private final LocalPlayerShoot shoot;
    private final LocalPlayerSprint sprint;
    
    private ClientPlayerGunOperator(LocalPlayer player) {
        this.player = player;
        this.dataHolder = PlayerGunCapability.getOrCreateClientData(player);
        this.aim = new LocalPlayerAim(dataHolder, player);
        this.crawl = new LocalPlayerCrawl(player);
        this.bolt = new LocalPlayerBolt(dataHolder, player);
        this.draw = new LocalPlayerDraw(dataHolder, player);
        this.fireSelect = new LocalPlayerFireSelect(dataHolder, player);
        this.melee = new LocalPlayerMelee(dataHolder, player);
        this.inspect = new LocalPlayerInspect(dataHolder, player);
        this.reload = new LocalPlayerReload(dataHolder, player);
        this.shoot = new LocalPlayerShoot(dataHolder, player);
        this.sprint = new LocalPlayerSprint(dataHolder, player);
    }
    
    /**
     * Get gun operator for LocalPlayer
     */
    public static ClientPlayerGunOperator fromLocalPlayer(LocalPlayer player) {
        return new ClientPlayerGunOperator(player);
    }
    
    public ShootResult shoot() {
        reload.cancelReload();
        return shoot.shoot();
    }
    
    public void draw(ItemStack lastItem) {
        draw.draw(lastItem);
    }
    
    public void bolt() {
        bolt.bolt();
    }
    
    public void reload() {
        reload.reload();
    }
    
    public void inspect() {
        inspect.inspect();
    }
    
    public void fireSelect() {
        fireSelect.fireSelect();
    }
    
    public void melee() {
        melee.melee();
    }
    
    public void aim(boolean isAim) {
        aim.aim(isAim);
    }
    
    public boolean isCrawl() {
        return crawl.isCrawling();
    }
    
    public LocalPlayerDataHolder getDataHolder() {
        return dataHolder;
    }
    
    public void crawl(boolean isCrawl) {
        crawl.crawl(isCrawl);
    }
    
    public float getClientAimingProgress(float partialTicks) {
        return aim.getClientAimingProgress(partialTicks);
    }
    
    public long getClientShootCoolDown() {
        return shoot.getClientShootCoolDown();
    }
    
    public boolean isAim() {
        return aim.isAim();
    }
    
    public void resetDraw() {
        draw.readyToDraw = false;
    }
    
    public boolean isReadyToDraw() {
        return draw.readyToDraw;
    }
    
    public void tick() {
        if (player.level().isClientSide()) {
            aim.tickAimingProgress();
            crawl.tickCrawl();
            dataHolder.tickStateLock();
            bolt.tickAutoBolt();
            player.setSprinting(sprint.getProcessedSprintStatus(player.isSprinting()));
        }
    }
    
    public void onRespawn() {
        dataHolder.reset();
        draw(ItemStack.EMPTY);
    }
}
