package com.tacz.guns.api.item.nbt;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.item.IBlock;
import com.tacz.guns.init.ModDataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public interface BlockItemDataAccessor extends IBlock {
    String BLOCK_ID = "BlockId";    @Override
    @Nonnull
    default ResourceLocation getBlockId(ItemStack block) {
        ResourceLocation blockId = block.get(ModDataComponents.BLOCK_ID.get());
        return Objects.requireNonNullElse(blockId, DefaultAssets.EMPTY_BLOCK_ID);
    }

    @Override
    default void setBlockId(ItemStack block, @Nullable ResourceLocation blockId) {
        if (blockId != null) {
            block.set(ModDataComponents.BLOCK_ID.get(), blockId);
        } else {
            block.set(ModDataComponents.BLOCK_ID.get(), DefaultAssets.EMPTY_BLOCK_ID);
        }
    }

}
