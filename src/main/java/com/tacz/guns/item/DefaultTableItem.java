package com.tacz.guns.item;

import com.tacz.guns.GunMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultTableItem extends GunSmithTableItem{
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, "gun_smith_table");
    public DefaultTableItem(Block block) {
        super(block);
    }

    @Override
    @NotNull
    public ResourceLocation getBlockId(ItemStack block) {
        return ID;
    }

    @Override
    public void setBlockId(ItemStack block, @Nullable ResourceLocation blockId) {
        // ÃƒÂ©Ã‚Â»Ã‹Å“ÃƒÂ¨Ã‚Â®Ã‚Â¤ÃƒÂ¥Ã‚ÂÃ¢â‚¬â€ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾idÃƒÂ¦Ã¢â‚¬â€Ã‚Â ÃƒÂ¦Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬ÂÃ‚ÂÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚Âº"tacz:gun_smith_table"
    }
}































































