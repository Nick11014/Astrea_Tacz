# Lista Completa de Imports Problemáticos - Migração Forge 1.20.1 → NeoForge 1.21.1

**Todos os imports do Forge e Minecraft que precisam ser migrados para funcionar no NeoForge 1.21.1**

---

## 🔥 IMPORTS DO MINECRAFTFORGE (PRECISAM SER MIGRADOS)

```java
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ConfigTracker;
import net.minecraftforge.fml.config.IConfigEvent;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.resource.DelegatingPackResources;
import net.minecraftforge.resource.PathPackResources;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.command.EnumArgument;
```

---

## ⚠️ IMPORTS DO MINECRAFT QUE MUDARAM OU SÃO PROBLEMÁTICOS

```java
// NBT - MIGRAR PARA DATACOMPONENTS
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;

// TAGS E RECURSOS
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceKey;

// NETWORKING
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;

// SERVIDOR E CLIENTE
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;

// CLIENTE
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;

// ENTIDADES
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.projectile.Projectile;

// ITEMS E CRAFTING
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;

// WORLD E BLOCOS
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;

// DANO E COMBATE
import net.minecraft.world.damagesource.DamageSource;

// MATEMÁTICA E FÍSICA
import net.minecraft.util.Mth;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

// PARTÍCULAS
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

// CORE E DIREÇÕES
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

// COMANDOS
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;

// FORMATAÇÃO E CHAT
import net.minecraft.ChatFormatting;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.SharedConstants;
```

---

## 🔄 TABELA DE CONVERSÃO: FORGE 1.20.1 → NEOFORGE 1.21.1

| Import Problemático (Forge 1.20.1) | Import Correto ou Padrão Equivalente (NeoForge 1.21.1) |
| :--- | :--- |
| `net.minecraftforge.api.distmarker.Dist` | `net.neoforged.api.distmarker.Dist` |
| `net.minecraftforge.api.distmarker.OnlyIn` | `net.neoforged.api.distmarker.OnlyIn` |
| `net.minecraftforge.client.extensions.common.IClientItemExtensions` | `net.neoforged.neoforge.client.extensions.common.IClientItemExtensions` |
| `net.minecraftforge.common.ForgeConfigSpec` | `net.neoforged.neoforge.common.ModConfigSpec` *(Classe renomeada)* |
| `net.minecraftforge.common.MinecraftForge` | `net.neoforged.neoforge.common.NeoForge` *(Classe renomeada)* |
| `net.minecraftforge.common.capabilities.ForgeCapabilities` | `net.neoforged.neoforge.capabilities.Capabilities` *(Classe renomeada)* |
| `net.minecraftforge.common.crafting.CraftingHelper` | `net.neoforged.neoforge.common.crafting.CraftingHelper` |
| `net.minecraftforge.common.extensions.IForgeMenuType` | `net.neoforged.neoforge.common.extensions.IMenuTypeExtension` *(Classe renomeada)* |
| `net.minecraftforge.entity.IEntityAdditionalSpawnData` | `net.neoforged.neoforge.entity.IEntityWithComplexSpawn` *(Interface substituída)* |
| `net.minecraftforge.entity.PartEntity` | `net.neoforged.neoforge.entity.PartEntity` |
| `net.minecraftforge.event.AddReloadListenerEvent` | `net.neoforged.neoforge.event.AddReloadListenerEvent` |
| `net.minecraftforge.event.ForgeEventFactory` | `net.neoforged.neoforge.event.EventHooks` *(Classe renomeada)* |
| `net.minecraftforge.event.OnDatapackSyncEvent` | `net.neoforged.neoforge.event.OnDatapackSyncEvent` |
| `net.minecraftforge.event.RegisterCommandsEvent` | `net.neoforged.neoforge.event.RegisterCommandsEvent` |
| `net.minecraftforge.event.TagsUpdatedEvent` | `net.neoforged.neoforge.event.TagsUpdatedEvent` |
| `net.minecraftforge.event.server.ServerStoppedEvent` | `net.neoforged.neoforge.event.server.ServerStoppedEvent` |
| `net.minecraftforge.eventbus.api.Cancelable` | `net.neoforged.bus.api.Cancelable` |
| `net.minecraftforge.eventbus.api.Event` | `net.neoforged.bus.api.Event` |
| `net.minecraftforge.eventbus.api.SubscribeEvent` | `net.neoforged.bus.api.SubscribeEvent` |
| `net.minecraftforge.fml.DistExecutor` | 💀 **Removido.** Use `net.neoforged.fml.loading.FMLEnvironment.dist` |
| `net.minecraftforge.fml.LogicalSide` | `net.neoforged.fml.LogicalSide` |
| `net.minecraftforge.fml.ModContainer` | `net.neoforged.fml.ModContainer` |
| `net.minecraftforge.fml.ModList` | `net.neoforged.fml.ModList` |
| `net.minecraftforge.fml.ModLoadingContext` | `net.neoforged.fml.ModLoadingContext` |
| `net.minecraftforge.fml.common.Mod` | `net.neoforged.fml.common.Mod` |
| `net.minecraftforge.fml.config.ConfigTracker` | `net.neoforged.fml.config.ConfigTracker` |
| `net.minecraftforge.fml.config.IConfigEvent` | `net.neoforged.fml.config.IConfigEvent` |
| `net.minecraftforge.fml.config.IConfigSpec` | `net.neoforged.fml.config.IConfigSpec` |
| `net.minecraftforge.fml.config.ModConfig` | `net.neoforged.fml.config.ModConfig` |
| `net.minecraftforge.fml.loading.FMLPaths` | `net.neoforged.fml.loading.FMLPaths` |
| `net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent` | `net.neoforged.fml.event.lifecycle.FMLClientSetupEvent` |
| `net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent` | `net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent` |
| `net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent` | 💀 **Removido.** Use eventos customizados ou `DeferredWorkQueue`. |
| `net.minecraftforge.fml.event.lifecycle.InterModProcessEvent` | 💀 **Removido.** A comunicação entre mods é feita por outros meios. |
| `net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext` | `net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext` |
| `net.minecraftforge.forgespi.language.IModInfo` | `net.neoforged.fml.loading.moddiscovery.IModInfo` |
| `net.minecraftforge.forgespi.locating.IModFile` | `net.neoforged.fml.loading.moddiscovery.IModFile` |
| `net.minecraftforge.network.HandshakeHandler` | 💀 **Removido.** Substituído pelo sistema de `Payloads`. |
| `net.minecraftforge.network.NetworkDirection` | 💀 **Removido.** Substituído pelo sistema de `Payloads`. |
| `net.minecraftforge.network.NetworkEvent` | `net.neoforged.neoforge.network.handling.IPayloadContext` *(Arquitetura diferente)* |
| `net.minecraftforge.network.NetworkHooks` | 💀 **Removido.** Funcionalidade integrada ao NeoForge. |
| `net.minecraftforge.network.NetworkRegistry` | 💀 **Removido.** Substituído por `IPayloadRegistrar`. |
| `net.minecraftforge.network.PacketDistributor` | `net.neoforged.neoforge.network.PacketDistributor` |
| `net.minecraftforge.network.simple.SimpleChannel` | 💀 **Removido.** Substituído pelo sistema de `Payloads`. |
| `net.minecraftforge.registries.DeferredRegister` | `net.neoforged.neoforge.registries.DeferredRegister` |
| `net.minecraftforge.registries.ForgeRegistries` | 💀 **Removido.** Use `net.minecraft.core.registries.BuiltInRegistries` ou `net.neoforged.neoforge.registries.NeoForgeRegistries`. |
| `net.minecraftforge.registries.IForgeRegistry` | 💀 **Removido.** Use `net.minecraft.core.Registry` do vanilla. |
| `net.minecraftforge.registries.ObjectHolder` | 💀 **Removido.** Use `RegistryObject`s fornecidos pelo `DeferredRegister`.|
| `net.minecraftforge.registries.RegistryObject` | `net.neoforged.neoforge.registries.DeferredHolder` |
| `net.minecraftforge.resource.DelegatingPackResources` | `net.neoforged.neoforge.resource.DelegatingPackResources` |
| `net.minecraftforge.resource.PathPackResources` | `net.neoforged.neoforge.resource.PathPackResources` |
| `net.minecraftforge.server.ServerLifecycleHooks` | `net.neoforged.neoforge.server.ServerLifecycleHooks` |
| `net.minecraftforge.server.command.EnumArgument` | `net.minecraft.commands.arguments.EnumArgument` *(Movido para o vanilla)* |

---

## ⚠️ MUDANÇAS DE ARQUITETURA CRÍTICAS

### 🚨 **Sistema de Rede - REFATORAÇÃO COMPLETA NECESSÁRIA**
- **`SimpleChannel`, `NetworkRegistry`, `NetworkEvent`** foram **REMOVIDOS**
- **Novo sistema:** Baseado em `IPayload` e `IPayloadHandler`
- **Migração:** Cada pacote precisa implementar `net.neoforged.neoforge.network.payload.IPayload`
- **Registro:** Use `IPayloadRegistrar` no evento `RegisterPayloadHandlersEvent`

### 🚨 **Registries - MUDANÇAS SIGNIFICATIVAS**
- **`@ObjectHolder`** foi **REMOVIDO** → Use `DeferredHolder` (antigo `RegistryObject`)
- **`ForgeRegistries`** foi **REMOVIDO** → Use `BuiltInRegistries` ou `NeoForgeRegistries`
- **`IForgeRegistry`** foi **REMOVIDO** → Use `Registry` do vanilla

### 🚨 **Configuração - CLASSE RENOMEADA**
- **`ForgeConfigSpec`** → **`ModConfigSpec`**
- **Usage permanece similar:** `ModConfigSpec.Builder`, `ConfigValue`, etc.

### 🚨 **Inter-Mod Communication - REMOVIDO**
- **`InterModEnqueueEvent`** e **`InterModProcessEvent`** foram **REMOVIDOS**
- **Nova abordagem:** APIs bem definidas ou `DeferredWorkQueue`

### 🚨 **DistExecutor - REMOVIDO**
- **`DistExecutor`** foi **REMOVIDO**
- **Substituição:** Use `FMLEnvironment.dist` para verificar side

---

## 🎯 PRÓXIMAS AÇÕES PRIORITÁRIAS

### **Fase 1: Imports Críticos para Correção Imediata** *(~70% Concluída)*
1. ✅ **Config**: `ForgeConfigSpec` → `ModConfigSpec` *(Concluído)*
2. ✅ **DeferredRegister**: Parcialmente migrado *(ModSounds, ModBlocks, ModCreativeTabs)*
3. ✅ **RegistryObject**: Parcialmente migrado → `DeferredHolder`
4. 🔄 **Restante**: Completar `ModParticles.java`, `ModEntities.java`, `ModRecipe.java`

### **Fase 2: Refatoração Arquitetural** *(Próxima Prioridade)*
1. 🚨 **Sistema de Rede**: `NetworkHandler.java` - **REFATORAÇÃO COMPLETA para IPayload**
2. 🔄 **ForgeRegistries**: Finalizar migração para `BuiltInRegistries`
3. 🔄 **Client Extensions**: Migrar `IClientItemExtensions`
4. 🔄 **Event System**: Alterar `net.minecraftforge.eventbus.api.*` → `net.neoforged.bus.api.*`

### **Arquivos de Alta Prioridade para Correção**
- `src/main/java/com/tacz/guns/network/` (Sistema de rede - refatoração completa)
- `src/main/java/com/tacz/guns/init/` (Registries - `DeferredRegister`)
- `src/main/java/com/tacz/guns/config/` (Config - `ModConfigSpec`)
- `src/main/java/com/tacz/guns/GunMod.java` (Ponto de entrada do mod)
- `src/main/java/com/tacz/guns/client/` (Client extensions e eventos)

---

## 📋 CHECKLIST DE VERIFICAÇÃO

### ✅ **Imports Básicos** 
- [x] **Config**: `ForgeConfigSpec` → `ModConfigSpec` *(PreLoadConfig.java, CommonConfig.java, ClientConfig.java)*
- [x] **FML**: `net.minecraftforge.fml.*` → `net.neoforged.fml.*` *(PreLoadModConfig.java)*
- [ ] Todos os demais `net.minecraftforge.*` → `net.neoforged.*`
- [ ] `MinecraftForge` → `NeoForge`

### ✅ **Sistema de Rede**
- [ ] 🚨 **CRÍTICO**: `NetworkHandler.java` usa `SimpleChannel` e `NetworkRegistry` - **REFATORAÇÃO COMPLETA NECESSÁRIA**
- [ ] Implementar `IPayload` para todos os pacotes de rede
- [ ] Registrar handlers no `RegisterPayloadHandlersEvent`
- [ ] Migrar todos os pacotes em `src/main/java/com/tacz/guns/network/message/`

### ✅ **Registries**
- [x] `DeferredRegister` atualizado *(GunMod.java já usando NeoForge)*
- [x] `RegistryObject` → `DeferredHolder` *(ModSounds.java, ModCreativeTabs.java parcial, ModBlocks.java)*
- [x] `ForgeRegistries` → `BuiltInRegistries` *(ModSounds.java, ModBlocks.java)*
- [ ] Completar migração em `ModParticles.java`, `ModEntities.java`, `ModRecipe.java`, `ModAttributes.java`, `ModContainer.java`

### ✅ **NBT → DataComponents (Prioridade Máxima)**
- [x] `ModDataComponents.java` - Sistema base implementado
- [x] `AttachmentItemDataAccessor.java` - Migração parcial concluída
- [ ] `GunItemDataAccessor.java` - Completar migração restante
- [ ] `AmmoItemDataAccessor.java` - Migrar para DataComponents
- [ ] `AmmoBoxItemDataAccessor.java` - Migrar para DataComponents

### ✅ **Client Extensions**
- [ ] `IClientItemExtensions` migrado
- [ ] Eventos de cliente atualizados

---

*Este arquivo serve como referência central para toda a migração. Mantenha-o atualizado conforme o progresso da portabilidade.*


