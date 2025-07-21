package com.tacz.guns.block.entity;

import com.tacz.guns.block.TargetBlock;
import com.tacz.guns.config.common.OtherConfig;
import com.tacz.guns.init.ModBlocks;
import com.tacz.guns.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.component.ResolvableProfile;

import javax.annotation.Nullable;

import static com.tacz.guns.block.TargetBlock.OUTPUT_POWER;
import static com.tacz.guns.block.TargetBlock.STAND;

public class TargetBlockEntity extends BlockEntity implements Nameable {
    // Removed static TYPE field - use ModBlocks.TARGET_BE.get() instead
    /**
     * ÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ©Ã‚ÂÃ‚Â¶ÃƒÂ¥Ã‚Â¤Ã‚ÂÃƒÂ¤Ã‚Â½Ã‚ÂÃƒÂ¦Ã¢â‚¬â€Ã‚Â¶ÃƒÂ©Ã¢â‚¬â€Ã‚Â´ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã…Â¡Ã¢â‚¬Å¡ÃƒÂ¥Ã‚Â®Ã…Â¡ÃƒÂ¤Ã‚Â¸Ã‚Âº 5 ÃƒÂ§Ã‚Â§Ã¢â‚¬â„¢
     */
    private static final int RESET_TIME = 5 * 20;
    private static final String OWNER_TAG = "Owner";
    private static final String CUSTOM_NAME_TAG = "CustomName";
    public float rot = 0;
    public float oRot = 0;
    private @Nullable ResolvableProfile owner;
    private @Nullable Component name;

    public TargetBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.TARGET_BE.get(), pos, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, TargetBlockEntity pBlockEntity) {
        pBlockEntity.oRot = pBlockEntity.rot;
        if (state.getValue(STAND)) {
            pBlockEntity.rot = Math.max(pBlockEntity.rot - 18, 0);
        } else {
            pBlockEntity.rot = Math.min(pBlockEntity.rot + 45, 90);
        }
    }

    @Nullable
    public ResolvableProfile getOwner() {
        return owner;
    }

    public void setOwner(@Nullable ResolvableProfile owner) {
        this.owner = owner;
        // SkullBlockEntity.updateGameprofile(this.owner, gameProfile -> {
        //     this.owner = gameProfile;
        //     this.refresh();
        // });
    }

    @Override
    protected void loadAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(OWNER_TAG, Tag.TAG_COMPOUND)) {
            // this.owner = NbtUtils.readGameProfile(tag.getCompound(OWNER_TAG));
        }
        if (tag.contains(CUSTOM_NAME_TAG, Tag.TAG_STRING)) {
            // this.name = Component.Serializer.fromJson(tag.getString(CUSTOM_NAME_TAG));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (owner != null) {
            // tag.put(OWNER_TAG, NbtUtils.writeGameProfile(new CompoundTag(), owner));
        }
        if (this.name != null) {
            // tag.putString(CUSTOM_NAME_TAG, Component.Serializer.toJson(this.name));
        }
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : Component.empty();
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    public void setCustomName(Component name) {
        this.name = name;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public void refresh() {
        this.setChanged();
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
        }
    }

    // @Override
    // public AABB getRenderBoundingBox() {
    //     return new AABB(worldPosition.offset(-2, 0, -2).getCenter(), worldPosition.offset(2, 2, 2).getCenter());
    // }

    public void hit(Level level, BlockState state, BlockHitResult hit, boolean isUpperBlock) {
        if (this.level != null && state.getValue(STAND)) {
            BlockPos blockPos = hit.getBlockPos();
            if (isUpperBlock) {
                blockPos = blockPos.below();
                state = level.getBlockState(blockPos);
            }
            int redstoneStrength = TargetBlock.getRedstoneStrength(hit, isUpperBlock);
            level.setBlock(blockPos, state.setValue(STAND, false).setValue(OUTPUT_POWER, redstoneStrength), Block.UPDATE_ALL);
            level.scheduleTick(blockPos, state.getBlock(), RESET_TIME);
            float volume = OtherConfig.TARGET_SOUND_DISTANCE.get() / 16.0f;
            volume = Math.max(volume, 0);
            level.playSound(null, blockPos, ModSounds.TARGET_HIT.get(), SoundSource.BLOCKS, volume, this.level.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}































































