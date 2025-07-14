package com.tacz.guns.client.gameplay;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateMachine;
import com.tacz.guns.api.event.common.GunMeleeEvent;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.client.animation.statemachine.GunAnimationConstant;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ClientMessagePlayerMelee;
import com.tacz.guns.resource.pojo.data.attachment.MeleeData;
import com.tacz.guns.resource.pojo.data.gun.GunDefaultMeleeData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.LogicalSide;

import javax.annotation.Nullable;

public class LocalPlayerMelee {
    public static final String MELEE_STOCK_ANIMATION = "melee_stock";
    private final LocalPlayerDataHolder data;
    private final LocalPlayer player;

    public LocalPlayerMelee(LocalPlayerDataHolder data, LocalPlayer player) {
        this.data = data;
        this.player = player;
    }

    public void melee() {
        // ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã¢â‚¬ÂÃ‚Â
        if (data.clientStateLock) {
            return;
        }
        // ÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã‚Â»ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Â¹
        ItemStack mainHandItem = player.getMainHandItem();
        if (!(mainHandItem.getItem() instanceof IGun iGun)) {
            return;
        }
        ClientGunIndex display = TimelessAPI.getGunDisplay(mainHandItem).orElse(null);
        if (display == null) {
            return;
        }
        ResourceLocation gunId = iGun.getGunId(mainHandItem);
        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¦Ã‚Â£Ã¢â€šÂ¬ÃƒÂ¦Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¥Ã‚ÂÃ‚Â£ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¦Ã…â€œÃ¢â‚¬Â°ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¥Ã‚Â±Ã…Â¾ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â§
        ResourceLocation muzzleId = iGun.getAttachmentId(mainHandItem, AttachmentType.MUZZLE);
        MeleeData muzzleMeleeData = getMeleeData(muzzleId);
        if (muzzleMeleeData != null) {
            this.doMuzzleMelee(display);
            return;
        }

        ResourceLocation stockId = iGun.getAttachmentId(mainHandItem, AttachmentType.STOCK);
        MeleeData stockMeleeData = getMeleeData(stockId);
        if (stockMeleeData != null) {
            this.doStockMelee(display);
            return;
        }

        TimelessAPI.getClientGunIndex(gunId).ifPresent(index -> {
            GunDefaultMeleeData defaultMeleeData = index.getGunData().getMeleeData().getDefaultMeleeData();
            if (defaultMeleeData == null) {
                return;
            }
            String animationType = defaultMeleeData.getAnimationType();
            if (MELEE_STOCK_ANIMATION.equals(animationType)) {
                this.doStockMelee(display);
                return;
            }
            this.doPushMelee(display);
        });
    }

    private boolean prepareMelee() {
        // ÃƒÂ©Ã¢â‚¬ÂÃ‚ÂÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ©Ã¢â‚¬ÂÃ‚Â
        data.lockState(operator -> operator.getSynMeleeCoolDown() > 0);
        // ÃƒÂ¨Ã‚Â§Ã‚Â¦ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â¹ÃƒÂ¤Ã‚Â»Ã‚Â¶
        GunMeleeEvent gunMeleeEvent = new GunMeleeEvent(player, player.getMainHandItem(), LogicalSide.CLIENT);
        return !NeoForge.EVENT_BUS.post(gunMeleeEvent).isCanceled();
    }

    private void doMuzzleMelee(ClientGunIndex display) {
        if (prepareMelee()) {
            SoundPlayManager.playMeleeBayonetSound(player, display);
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
            NetworkHandler.CHANNEL.sendToServer(new ClientMessagePlayerMelee());
            // ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
            AnimationStateMachine<?> animationStateMachine = display.getAnimationStateMachine();
            if (animationStateMachine != null) {
                animationStateMachine.trigger(GunAnimationConstant.INPUT_BAYONET_MUZZLE);
            }
        }
    }

    private void doStockMelee(ClientGunIndex display) {
        if (prepareMelee()) {
            SoundPlayManager.playMeleeStockSound(player, display);
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
            NetworkHandler.CHANNEL.sendToServer(new ClientMessagePlayerMelee());
            // ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
            AnimationStateMachine<?> animationStateMachine = display.getAnimationStateMachine();
            if (animationStateMachine != null) {
                animationStateMachine.trigger(GunAnimationConstant.INPUT_BAYONET_STOCK);
            }
        }
    }

    private void doPushMelee(ClientGunIndex display) {
        if (prepareMelee()) {
            // ÃƒÂ¦Ã¢â‚¬â„¢Ã‚Â­ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ©Ã…Â¸Ã‚Â³ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ 
            SoundPlayManager.playMeleePushSound(player, display);
            // ÃƒÂ¥Ã‚ÂÃ¢â‚¬ËœÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ËœÃƒÂ¦Ã‹â€ Ã‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ¦Ã‚ÂÃ‚Â®ÃƒÂ¥Ã…â€™Ã¢â‚¬Â¦ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â€šÂ¬Ã…Â¡ÃƒÂ§Ã…Â¸Ã‚Â¥ÃƒÂ¦Ã…â€œÃ‚ÂÃƒÂ¥Ã…Â Ã‚Â¡ÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨
            NetworkHandler.CHANNEL.sendToServer(new ClientMessagePlayerMelee());
            // ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚ÂÃƒÂ¦Ã…â€œÃ‚ÂºÃƒÂ¨Ã‚Â½Ã‚Â¬ÃƒÂ§Ã‚Â§Ã‚Â»ÃƒÂ§Ã…Â Ã‚Â¶ÃƒÂ¦Ã¢â€šÂ¬Ã‚Â
            AnimationStateMachine<?> animationStateMachine = display.getAnimationStateMachine();
            if (animationStateMachine != null) {
                animationStateMachine.trigger(GunAnimationConstant.INPUT_BAYONET_PUSH);
            }
        }
    }

    @Nullable
    private MeleeData getMeleeData(ResourceLocation attachmentId) {
        if (DefaultAssets.isEmptyAttachmentId(attachmentId)) {
            return null;
        }
        return TimelessAPI.getClientAttachmentIndex(attachmentId).map(index -> index.getData().getMeleeData()).orElse(null);
    }
}































































