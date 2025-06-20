# Imports Problemáticos - Migração Forge → NeoForge

Este arquivo lista todos os imports que estão causando erros de compilação na migração do TacZ de Forge 1.20.1 para NeoForge 1.21.1.

## 📊 Resumo Geral
- **Total de erros identificados:** 100+
- **Categorias principais:** 6 grupos de APIs
- **Status:** Todos precisam ser migrados para as APIs do NeoForge

---

## 🚨 Categoria 1: Sistema de Eventos (Event System)

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.Cancelable;
```

### ✅ Substituições NeoForge:
```java
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent; // Note: Interface em vez de anotação
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/api/client/event/BeforeRenderHandEvent.java`
- `com/tacz/guns/api/client/event/RenderItemInHandBobEvent.java`
- `com/tacz/guns/api/client/event/RenderLevelBobEvent.java`
- `com/tacz/guns/api/client/event/SwapItemWithOffHand.java`
- `com/tacz/guns/api/event/common/AttachmentPropertyEvent.java`
- `com/tacz/guns/api/event/common/EntityHurtByGunEvent.java`
- `com/tacz/guns/api/event/common/EntityKillByGunEvent.java`
- `com/tacz/guns/api/event/common/GunDrawEvent.java`
- `com/tacz/guns/api/event/common/GunFinishReloadEvent.java`
- `com/tacz/guns/api/event/common/GunFireEvent.java`
- `com/tacz/guns/api/event/common/GunFireSelectEvent.java`
- `com/tacz/guns/api/event/common/GunMeleeEvent.java`
- `com/tacz/guns/api/event/common/GunReloadEvent.java`
- `com/tacz/guns/api/event/common/GunShootEvent.java`
- `com/tacz/guns/api/event/server/AmmoHitBlockEvent.java`
- `com/tacz/guns/api/event/common/KubeJSGunEventPoster.java`

---

## 🚨 Categoria 2: LogicalSide e FML

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.DistExecutor;
```

### ✅ Substituições NeoForge:
```java
import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment; // DistExecutor foi removido
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/api/event/common/EntityHurtByGunEvent.java`
- `com/tacz/guns/api/event/common/EntityKillByGunEvent.java`
- `com/tacz/guns/api/event/common/GunDrawEvent.java`
- `com/tacz/guns/api/event/common/GunFinishReloadEvent.java`
- `com/tacz/guns/api/event/common/GunFireEvent.java`
- `com/tacz/guns/api/event/common/GunFireSelectEvent.java`
- `com/tacz/guns/api/event/common/GunMeleeEvent.java`
- `com/tacz/guns/api/event/common/GunReloadEvent.java`
- `com/tacz/guns/api/event/common/GunShootEvent.java`
- `com/tacz/guns/entity/EntityKineticBullet.java`
- `com/tacz/guns/api/event/common/KubeJSGunEventPoster.java`

---

## 🚨 Categoria 3: Marcadores de Side (Side Markers)

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
```

### ✅ Substituições NeoForge:
```java
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/api/client/gameplay/IClientPlayerGunOperator.java`
- `com/tacz/guns/client/model/bedrock/BedrockPart.java`
- `com/tacz/guns/client/resource/GunDisplayInstance.java`
- `com/tacz/guns/api/item/gun/AbstractGunItem.java`

---

## 🚨 Categoria 4: Sistema de Registry

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.registries.RegistryObject;
```

### ✅ Substituições NeoForge:
```java
import net.neoforged.neoforge.registries.DeferredHolder;
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/api/item/builder/GunItemBuilder.java`

---

## 🚨 Categoria 5: Sistema de Entidades

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.entity.PartEntity;
```

### ✅ Substituições NeoForge:
```java
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn; // IEntityAdditionalSpawnData foi renomeado
// PartEntity foi removido ou mudou significativamente
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/entity/EntityKineticBullet.java`

---

## 🚨 Categoria 6: Networking

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.network.NetworkHooks;
```

### ✅ Substituições NeoForge:
```java
// NetworkHooks foi removido, usar novo sistema de networking do NeoForge
import net.neoforged.neoforge.network.PacketDistributor;
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/entity/EntityKineticBullet.java`

---

## 🚨 Categoria 7: Capabilities

### ❌ Imports Problemáticos:
```java
import net.minecraftforge.common.capabilities.ForgeCapabilities;
```

### ✅ Substituições NeoForge:
```java
import net.neoforged.neoforge.capabilities.Capabilities;
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/client/animation/statemachine/GunAnimationStateContext.java`

---

## 🚨 Categoria 8: Anotações de Mod

### ❌ Anotações Problemáticas:
```java
@Mod.EventBusSubscriber(value = Dist.CLIENT)
```

### ✅ Substituições NeoForge:
```java
@EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
```

### 📁 Arquivos Afetados:
- `com/tacz/guns/client/event/ClientHitMark.java` (arquivo atual do usuário)
- Múltiplos outros event subscribers

---

## 📋 Plano de Ação para Correção

### 🎯 Prioridade 1: Imports Básicos
1. **Sistema de Eventos** (16 arquivos)
2. **LogicalSide e FML** (11 arquivos)
3. **Marcadores de Side** (4 arquivos)

### 🎯 Prioridade 2: Registry e Entidades
4. **Sistema de Registry** (1 arquivo)
5. **Sistema de Entidades** (1 arquivo)

### 🎯 Prioridade 3: Sistemas Complexos
6. **Networking** (1 arquivo)
7. **Capabilities** (1 arquivo)

---

## 🔧 Exemplo de Migração Típica

**Antes (Forge 1.20.1):**
```java
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientHitMark {
    @SubscribeEvent
    public static void onEntityHurt(EntityHurtByGunEvent.Post event) {
        // ...
    }
}
```

**Depois (NeoForge 1.21.1):**
```java
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = ModConstants.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.NEOFORGE)
public class ClientHitMark {
    @SubscribeEvent
    public static void onEntityHurt(EntityHurtByGunEvent.Post event) {
        // ...
    }
}
```

---

## 📊 Status de Migração

- [ ] **Categoria 1:** Sistema de Eventos (0/16 arquivos migrados)
- [ ] **Categoria 2:** LogicalSide e FML (0/11 arquivos migrados)
- [ ] **Categoria 3:** Marcadores de Side (0/4 arquivos migrados)
- [ ] **Categoria 4:** Sistema de Registry (0/1 arquivos migrados)
- [ ] **Categoria 5:** Sistema de Entidades (0/1 arquivos migrados)
- [ ] **Categoria 6:** Networking (0/1 arquivos migrados)
- [ ] **Categoria 7:** Capabilities (0/1 arquivos migrados)

**Progresso total:** 0/35 arquivos migrados (0%)
