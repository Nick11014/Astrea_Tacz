package com.tacz.guns.init;

import com.tacz.guns.GunMod;
// TODO: Re-enable when all block classes are available
// import com.tacz.guns.block.*;
// import com.tacz.guns.block.entity.GunSmithTableBlockEntity;
// import com.tacz.guns.block.entity.StatueBlockEntity;
// import com.tacz.guns.block.entity.TargetBlockEntity;
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
 * Registro de blocos baseado no padrão do SuperbWarfare 1.21.1
 * Usando implementação mínima até as classes de bloco estarem disponíveis
 */
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, GunMod.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GunMod.MOD_ID);

    // TODO: Re-enable when block classes are available
    // Implementação mínima com placeholders simples
    public static final DeferredHolder<Block, Block> GUN_SMITH_TABLE = BLOCKS.register("gun_smith_table", 
        () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0F, 3.0F))); // Placeholder - GunSmithTableBlockB::new
        
    public static final DeferredHolder<Block, Block> WORKBENCH_111 = BLOCKS.register("workbench_a", 
        () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0F, 3.0F))); // Placeholder - GunSmithTableBlockA::new
        
    public static final DeferredHolder<Block, Block> WORKBENCH_211 = BLOCKS.register("workbench_b", 
        () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0F, 3.0F))); // Placeholder - GunSmithTableBlockB::new
        
    public static final DeferredHolder<Block, Block> WORKBENCH_121 = BLOCKS.register("workbench_c", 
        () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0F, 3.0F))); // Placeholder - GunSmithTableBlockC::new

    public static final DeferredHolder<Block, Block> TARGET = BLOCKS.register("target", 
        () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.0F, 2.0F))); // Placeholder - TargetBlock::new
        
    public static final DeferredHolder<Block, Block> STATUE = BLOCKS.register("statue", 
        () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(2.0F, 3.0F))); // Placeholder - StatueBlock::new

    // TODO: Re-enable when block entity classes are available
    // Block entities também precisam de placeholders temporários
    /*
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GunSmithTableBlockEntity>> GUN_SMITH_TABLE_BE = TILE_ENTITIES.register("gun_smith_table", 
        () -> BlockEntityType.Builder.of(GunSmithTableBlockEntity::new, GUN_SMITH_TABLE.get()).build(null));
        
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TargetBlockEntity>> TARGET_BE = TILE_ENTITIES.register("target", 
        () -> BlockEntityType.Builder.of(TargetBlockEntity::new, TARGET.get()).build(null));
        
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StatueBlockEntity>> STATUE_BE = TILE_ENTITIES.register("statue", 
        () -> BlockEntityType.Builder.of(StatueBlockEntity::new, STATUE.get()).build(null));
    */

    // Tags using the modernized ResourceLocation API
    public static final TagKey<Block> BULLET_IGNORE_BLOCKS = BlockTags.create(GunMod.loc("bullet_ignore"));
}
