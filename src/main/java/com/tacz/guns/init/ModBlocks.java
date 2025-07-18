package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import com.tacz.guns.block.GunSmithTableBlockA;
import com.tacz.guns.block.GunSmithTableBlockB;
import com.tacz.guns.block.GunSmithTableBlockC;
import com.tacz.guns.block.StatueBlock;
import com.tacz.guns.block.TargetBlock;
import com.tacz.guns.block.entity.GunSmithTableBlockEntity;
import com.tacz.guns.block.entity.StatueBlockEntity;
import com.tacz.guns.block.entity.TargetBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registro de blocos baseado no padrÃ£o do SuperbWarfare 1.21.1
 * Usando implementaÃ§Ã£o mÃ­nima atÃ© as classes de bloco estarem disponÃ­veis
 */
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, GunMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GunMod.MOD_ID);

    public static final DeferredHolder<Block, GunSmithTableBlockB> GUN_SMITH_TABLE = BLOCKS.register("gun_smith_table", GunSmithTableBlockB::new);
        
    public static final DeferredHolder<Block, GunSmithTableBlockA> WORKBENCH_111 = BLOCKS.register("workbench_a", GunSmithTableBlockA::new);
        
    public static final DeferredHolder<Block, GunSmithTableBlockB> WORKBENCH_211 = BLOCKS.register("workbench_b", GunSmithTableBlockB::new);
        
    public static final DeferredHolder<Block, GunSmithTableBlockC> WORKBENCH_121 = BLOCKS.register("workbench_c", GunSmithTableBlockC::new);

    public static final DeferredHolder<Block, TargetBlock> TARGET = BLOCKS.register("target", TargetBlock::new);
        
    public static final DeferredHolder<Block, StatueBlock> STATUE = BLOCKS.register("statue", StatueBlock::new);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GunSmithTableBlockEntity>> GUN_SMITH_TABLE_BE = TILE_ENTITIES.register("gun_smith_table", 
        () -> BlockEntityType.Builder.of(GunSmithTableBlockEntity::new, GUN_SMITH_TABLE.get()).build(null));
        
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TargetBlockEntity>> TARGET_BE = TILE_ENTITIES.register("target", 
        () -> BlockEntityType.Builder.of(TargetBlockEntity::new, TARGET.get()).build(null));
        
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StatueBlockEntity>> STATUE_BE = TILE_ENTITIES.register("statue", 
        () -> BlockEntityType.Builder.of(StatueBlockEntity::new, STATUE.get()).build(null));

    // Tags using the modernized ResourceLocation API
    public static final TagKey<Block> BULLET_IGNORE_BLOCKS = BlockTags.create(GunMod.loc("bullet_ignore"));
}































































