package com.tacz.guns.init;

// import com.tacz.guns.GunMod; // TODO: Re-enable when GunMod is available
// import com.tacz.guns.block.*; // TODO: Re-enable when block classes are available
// import com.tacz.guns.block.entity.GunSmithTableBlockEntity; // TODO: Re-enable when block entities are available
// import com.tacz.guns.block.entity.StatueBlockEntity; // TODO: Re-enable when block entities are available
// import com.tacz.guns.block.entity.TargetBlockEntity; // TODO: Re-enable when block entities are available
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocks {
    // TODO: Replace with GunMod.MOD_ID when GunMod is available
    private static final String MOD_ID = "tacz";
    
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);

    // TODO: Re-enable when block classes are available
    /*
    // 旧方块就让他独占一个了
    public static DeferredHolder<Block, Block> GUN_SMITH_TABLE = BLOCKS.register("gun_smith_table", GunSmithTableBlockB::new);
    public static DeferredHolder<Block, Block> WORKBENCH_111 = BLOCKS.register("workbench_a", GunSmithTableBlockA::new);
    public static DeferredHolder<Block, Block> WORKBENCH_211 = BLOCKS.register("workbench_b", GunSmithTableBlockB::new);
    public static DeferredHolder<Block, Block> WORKBENCH_121 = BLOCKS.register("workbench_c", GunSmithTableBlockC::new);

    public static DeferredHolder<Block, Block> TARGET = BLOCKS.register("target", TargetBlock::new);
    public static DeferredHolder<Block, Block> STATUE = BLOCKS.register("statue", StatueBlock::new);

    // TODO: Re-enable when Block Entities are available
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<GunSmithTableBlockEntity>> GUN_SMITH_TABLE_BE = TILE_ENTITIES.register("gun_smith_table", () -> GunSmithTableBlockEntity.TYPE);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TargetBlockEntity>> TARGET_BE = TILE_ENTITIES.register("target", () -> TargetBlockEntity.TYPE);
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<StatueBlockEntity>> STATUE_BE = TILE_ENTITIES.register("statue", () -> StatueBlockEntity.TYPE);
    */
    
    public static final TagKey<Block> BULLET_IGNORE_BLOCKS = BlockTags.create(ResourceLocation.fromNamespaceAndPath(MOD_ID, "bullet_ignore"));
}
