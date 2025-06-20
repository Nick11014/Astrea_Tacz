# Exemplo Prático: Migrando ClientHitMark.java

## 📁 Arquivo: `com/tacz/guns/client/event/ClientHitMark.java`

### ❌ Código Atual (Forge 1.20.1):
```java
package com.tacz.guns.client.event;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import com.tacz.guns.client.gui.overlay.KillAmountOverlay;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.config.client.RenderConfig;
import com.tacz.guns.entity.TargetMinecart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;           // ❌ PROBLEMA
import net.minecraftforge.eventbus.api.SubscribeEvent;   // ❌ PROBLEMA
import net.minecraftforge.fml.LogicalSide;               // ❌ PROBLEMA
import net.minecraftforge.fml.common.Mod;                // ❌ PROBLEMA

@Mod.EventBusSubscriber(value = Dist.CLIENT)             // ❌ PROBLEMA
public class ClientHitMark {
    // ... resto do código
}
```

### ✅ Código Migrado (NeoForge 1.21.1):
```java
package com.tacz.guns.client.event;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import com.tacz.guns.api.event.common.EntityKillByGunEvent;
import com.tacz.guns.client.gui.overlay.KillAmountOverlay;
import com.tacz.guns.client.sound.SoundPlayManager;
import com.tacz.guns.config.client.RenderConfig;
import com.tacz.guns.entity.TargetMinecart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;                // ✅ CORRETO
import net.neoforged.bus.api.SubscribeEvent;             // ✅ CORRETO
import net.neoforged.fml.LogicalSide;                    // ✅ CORRETO
import net.neoforged.neoforge.common.NeoForge;          // ✅ NOVO
import net.neoforged.bus.api.EventPriority;             // ✅ OPCIONAL (se precisar de prioridade)

@EventBusSubscriber(modid = "tacz", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.NEOFORGE) // ✅ CORRETO
public class ClientHitMark {
    // ... resto do código (sem mudanças)
}
```

## 🔧 Mudanças Específicas Necessárias:

### 1. **Imports a Alterar:**
| Forge 1.20.1 | NeoForge 1.21.1 |
|---------------|------------------|
| `net.minecraftforge.api.distmarker.Dist` | `net.neoforged.api.distmarker.Dist` |
| `net.minecraftforge.eventbus.api.SubscribeEvent` | `net.neoforged.bus.api.SubscribeEvent` |
| `net.minecraftforge.fml.LogicalSide` | `net.neoforged.fml.LogicalSide` |
| `net.minecraftforge.fml.common.Mod` | ❌ **REMOVER** (não necessário) |

### 2. **Anotação a Alterar:**
```java
// ANTES:
@Mod.EventBusSubscriber(value = Dist.CLIENT)

// DEPOIS:
@EventBusSubscriber(modid = "tacz", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.NEOFORGE)
```

### 3. **Import Adicional Necessário:**
```java
import net.neoforged.bus.api.EventBusSubscriber;
```

## 📋 Checklist de Migração:

- [ ] Alterar import `net.minecraftforge.api.distmarker.Dist` → `net.neoforged.api.distmarker.Dist`
- [ ] Alterar import `net.minecraftforge.eventbus.api.SubscribeEvent` → `net.neoforged.bus.api.SubscribeEvent`
- [ ] Alterar import `net.minecraftforge.fml.LogicalSide` → `net.neoforged.fml.LogicalSide`
- [ ] Remover import `net.minecraftforge.fml.common.Mod`
- [ ] Adicionar import `net.neoforged.bus.api.EventBusSubscriber`
- [ ] Alterar anotação `@Mod.EventBusSubscriber` → `@EventBusSubscriber`
- [ ] Adicionar parâmetros `modid` e `bus` na anotação
- [ ] Testar compilação

## ⚠️ Notas Importantes:

1. **ModID:** Substitua `"tacz"` pelo ID correto do mod (pode estar em uma constante)
2. **Bus Type:** Use `EventBusSubscriber.Bus.NEOFORGE` para eventos do jogo, `EventBusSubscriber.Bus.MOD` para eventos do mod
3. **Lógica do código:** O conteúdo dos métodos `onEntityHurt` e `onEntityKill` **não precisa mudar**
4. **Events customizados:** Os eventos `EntityHurtByGunEvent` e `EntityKillByGunEvent` precisarão ser migrados separadamente
