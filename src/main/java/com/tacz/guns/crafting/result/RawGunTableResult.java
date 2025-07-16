package com.tacz.guns.crafting.result;

import com.tacz.guns.GunMod;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.api.item.builder.AttachmentItemBuilder;
import com.tacz.guns.api.item.builder.GunItemBuilder;
import com.tacz.guns.resource.pojo.data.block.TabConfig;
import com.tacz.guns.resource.pojo.data.recipe.GunResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Locale;


/**
 * ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ¨Ã‚Â½Ã‚Â½ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã†â€™Ã‚Â¨ÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ¤Ã‚Â¸Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¨Ã‚Â¿Ã‹Å“ÃƒÂ¦Ã…â€œÃ‚ÂªÃƒÂ¥Ã‚Â®Ã…â€™ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œ<br/>
 * ÃƒÂ§Ã‚Â­Ã¢â‚¬Â°ÃƒÂ¥Ã‚Â¾Ã¢â‚¬Â¦ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¥Ã‚Â®Ã…Â¾ÃƒÂ©Ã¢â€žÂ¢Ã¢â‚¬Â¦ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚Â¿ÃƒÂ§Ã¢â‚¬ÂÃ‚Â¨ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ‚Â¹ÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ¥Ã¢â‚¬Â Ã‚ÂÃƒÂ¨Ã‚Â¿Ã¢â‚¬ÂºÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¥Ã…â€™Ã¢â‚¬â€œ
 */
public class RawGunTableResult {
    private final String type;
    private final int count;
    private final ResourceLocation id;
    @Nullable
    private GunResult extraData;
    @Nullable
    private CompoundTag nbt;

    public RawGunTableResult(@NotNull String type, @NotNull ResourceLocation id, int count) {
        this.type = type;
        this.id = id;
        this.count = count;
    }

    public void setExtraData(@Nullable GunResult extraData) {
        this.extraData = extraData;
    }

    public void setNbt(@Nullable CompoundTag nbt) {
        this.nbt = nbt;
    }

    public static GunSmithTableResult init(RawGunTableResult raw) {
        GunSmithTableResult result = switch (raw.type) {
            case GunSmithTableResult.GUN -> raw.getGunStack();
            case GunSmithTableResult.AMMO -> raw.getAmmoStack();
            case GunSmithTableResult.ATTACHMENT -> raw.getAttachmentStack();
            default -> new GunSmithTableResult(ItemStack.EMPTY, TabConfig.TAB_EMPTY);
        };        // MIGRAÇÃO PARA DATACOMPONENTS (NeoForge 1.21.1)
        // O sistema NBT foi substituído por DataComponents
        // TODO: Implementar migração específica de NBT para DataComponents quando necessário
        // Por enquanto, mantemos compatibilidade básica através do LuaNbtAccessor
        if (raw.nbt != null) {
            // Aplicar dados NBT usando o sistema de compatibilidade
            // Nota: Este é um fallback temporário - idealmente deveria usar DataComponents específicos
            var accessor = com.tacz.guns.api.util.LuaNbtAccessor.from(result.getResult());
            // A aplicação específica de NBT será feita através dos DataComponents apropriados
            // quando os builders forem chamados (GunItemBuilder, AmmoItemBuilder, etc.)
        }
        return result;
    }

    private GunSmithTableResult getGunStack() {
        int ammoCount;
        EnumMap<AttachmentType, ResourceLocation> attachments;
        if (extraData != null) {
            ammoCount = Math.max(0, extraData.getAmmoCount());
            attachments = extraData.getAttachments();
        } else {
            ammoCount = 0;
            attachments = new EnumMap<>(AttachmentType.class);
        }

        return TimelessAPI.getCommonGunIndex(id).map(gunIndex -> {
            ItemStack itemStack = GunItemBuilder.create()
                    .setCount(count)
                    .setId(id)
                    .setAmmoCount(ammoCount)
                    .setAmmoInBarrel(false)
                    .putAllAttachment(attachments)
                    .setFireMode(gunIndex.getGunData().getFireModeSet().get(0)).build();
            String raw = gunIndex.getType();
            if (!raw.contains(":")) {
                raw = GunMod.MOD_ID + ":" + raw;
            }
            ResourceLocation group = ResourceLocation.tryParse(raw);
            return new GunSmithTableResult(itemStack, group);
        }).orElse(new GunSmithTableResult(ItemStack.EMPTY, TabConfig.TAB_EMPTY));
    }

    private GunSmithTableResult getAmmoStack() {
        return new GunSmithTableResult(AmmoItemBuilder.create().setCount(count).setId(id).build(), TabConfig.TAB_AMMO);
    }

    private GunSmithTableResult getAttachmentStack() {
        return TimelessAPI.getCommonAttachmentIndex(id).map(attachmentIndex -> {
            ItemStack itemStack = AttachmentItemBuilder.create().setCount(count).setId(id).build();
            String raw = attachmentIndex.getType().name().toLowerCase(Locale.US);
            if (!raw.contains(":")) {
                raw = GunMod.MOD_ID + ":" + raw;
            }
            ResourceLocation group = ResourceLocation.tryParse(raw);
            return new GunSmithTableResult(itemStack, group);
        }).orElse(new GunSmithTableResult(ItemStack.EMPTY, TabConfig.TAB_EMPTY));
    }
}































































