# 🚀 PLANO DE DESENVOLVIMENTO SISTEMÁTICO - TacZ NeoForge 1.21.1

**Projeto:** Migração TacZ de Forge 1.20.1 para NeoForge 1.21.1  
**Status Atual:** Fase 0 concluída - Todos os arquivos desabilitados, build limpo  
**Estratégia:** Habilitação incremental com validação a cada etapa

---

## ✅ CHECKLIST DE PROGRESSO GERAL

- [ ] GunMod.java
- [ ] init/
- [ ] util/
- [ ] config/
- [ ] api/
- [ ] block/
- [ ] item/ (básicos)
- [ ] entity/TargetMinecart.java
- [ ] entity/EntityKineticBullet.java
- [ ] network/
- [ ] event/
- [ ] command/
- [ ] inventory/
- [ ] crafting/
- [ ] debug/
- [ ] client/
- [ ] particles/
- [ ] sound/
- [ ] resource/
- [ ] compat/
- [ ] mixin/
- [ ] Outros arquivos restantes

---

## 📋 ESTRATÉGIA GERAL

### Princípios de Desenvolvimento:
1. **Incremental:** Habilitar poucos arquivos por vez
2. **Validação Contínua:** Build e teste após cada grupo
3. **Dependências Primeiro:** Corrigir fundações antes de features
4. **Isolamento de Problemas:** Um problema por vez
5. **Rollback Fácil:** Manter arquivos .disabled até confirmação

### Critérios de Sucesso por Etapa:
- ✅ Build compila sem erros
- ✅ Mod carrega sem crash
- ✅ Funcionalidade básica testada

---

## 🎯 FASES DE DESENVOLVIMENTO

## **FASE 1: CORE FOUNDATIONS** (Prioridade Máxima)
*Objetivo: Estabelecer a base mínima funcional do mod*

### 1.1 Configurações e Estrutura Base
**Ordem de Habilitação:**
```
1. config/CommonConfig.java.disabled         → config/CommonConfig.java
2. config/ClientConfig.java.disabled         → config/ClientConfig.java  
3. config/ServerConfig.java.disabled         → config/ServerConfig.java
4. config/common/*.disabled                   → config/common/*
```

**Ações Necessárias:**
- Validar imports do NeoForge para `ModConfigSpec`
- Verificar compatibilidade das configurações
- **Build Test:** Configs devem carregar sem erro

### 1.2 DataComponents (CRÍTICO - Nova Feature 1.21.1)
**Ordem de Habilitação:**
```
1. init/ModDataComponents.java.disabled       → init/ModDataComponents.java
```

**Ações Necessárias:**
- Implementar todos os `DataComponentType` necessários
- Registrar no `DeferredRegister<DataComponentType<?>>`
- Substituir sistema NBT legado (prioridade máxima)
- **Build Test:** DataComponents registram corretamente

### 1.3 Registros Fundamentais
**Ordem de Habilitação:**
```
1. init/ModBlocks.java.disabled               → init/ModBlocks.java
2. init/ModItems.java.disabled                → init/ModItems.java  
3. init/ModCreativeTabs.java.disabled         → init/ModCreativeTabs.java
4. init/ModEntities.java.disabled             → init/ModEntities.java
5. init/ModSounds.java.disabled               → init/ModSounds.java
```

**Ações Necessárias:**
- Corrigir imports para NeoForge 1.21.1
- Atualizar `DeferredRegister` syntax
- Migrar `RegistryObject` para `DeferredHolder`
- **Build Test:** Todos os registros funcionam

### 1.4 Classe Principal
**Ordem de Habilitação:**
```
1. GunMod.java.disabled                       → GunMod.java
```

**Ações Necessárias:**
- Integrar todos os registros habilitados
- Configurar event bus corretamente
- **Build Test:** Mod carrega completamente

---

## **FASE 2: ITEMS E DATA SYSTEM** 
*Objetivo: Sistema de itens e dados funcionando*

### 2.1 Sistema de Itens Base
**Ordem de Habilitação:**
```
1. item/gun/GunItem.java.disabled             → item/gun/GunItem.java
2. item/ammo/AmmoItem.java.disabled           → item/ammo/AmmoItem.java
3. item/attachment/AttachmentItem.java.disabled → item/attachment/AttachmentItem.java
```

**Ações Necessárias:**
- Migrar NBT para DataComponents em todos os itens
- Atualizar tooltips e rendering
- **Build Test:** Itens aparecem no creative tab

### 2.2 Sistema de Dados NBT → DataComponents
**Ordem de Habilitação:**
```
1. api/item/nbt/GunItemDataAccessor.java.disabled → api/item/nbt/GunItemDataAccessor.java
2. api/item/nbt/AmmoItemDataAccessor.java.disabled → api/item/nbt/AmmoItemDataAccessor.java
3. api/item/nbt/AttachmentItemDataAccessor.java.disabled → api/item/nbt/AttachmentItemDataAccessor.java
```

**Ações Necessárias:**
- **REFATORAÇÃO COMPLETA:** Substituir todos os métodos NBT por DataComponents
- Criar novos métodos usando `stack.set()` e `stack.get()`
- Manter API compatibility se possível
- **Build Test:** Dados persistem corretamente

---

## **FASE 3: RECIPES E CONTAINERS**
*Objetivo: Sistema de crafting e interfaces funcionando*

### 3.1 Sistema de Receitas
**Ordem de Habilitação:**
```
1. init/ModRecipe.java.disabled               → init/ModRecipe.java
2. recipes/*.java.disabled                    → recipes/*.java
```

### 3.2 Containers e GUIs
**Ordem de Habilitação:**
```
1. init/ModContainer.java.disabled            → init/ModContainer.java
2. inventory/*.java.disabled                  → inventory/*.java
```

---

## **FASE 4: BLOCKS E BLOCK ENTITIES**
*Objetivo: Blocos customizados funcionando*

### 4.1 Block Entities
**Ordem de Habilitação:**
```
1. block/entity/*.java.disabled               → block/entity/*.java
2. block/GunSmithTableBlock*.java.disabled    → block/GunSmithTableBlock*.java
```

---

## **FASE 5: ENTITIES E PHYSICS**
*Objetivo: Sistema de entidades e física funcionando*

### 5.1 Entidades Base
**Ordem de Habilitação:**
```
1. entity/EntityKinetic.java.disabled         → entity/EntityKinetic.java
2. entity/sync/*.java.disabled                → entity/sync/*.java
```

---

## **FASE 6: RENDERING E CLIENT** (ALTA COMPLEXIDADE)
*Objetivo: Sistema de renderização funcionando*

**⚠️ ATENÇÃO:** Esta é a fase mais complexa devido às mudanças nas APIs de renderização

### 6.1 Renderização Base
**Ordem de Habilitação:**
```
1. client/renderer/item/*.java.disabled       → client/renderer/item/*.java
2. client/renderer/block/*.java.disabled      → client/renderer/block/*.java
```

**Ações Necessárias:**
- Migrar `BlockEntityWithoutLevelRenderer` para `IClientItemExtensions`
- Atualizar `PoseStack` e `MultiBufferSource` usage
- Corrigir rendering pipelines

---

## **FASE 7: NETWORKING**
*Objetivo: Sistema de rede funcionando*

### 7.1 Network Packets
**Ordem de Habilitação:**
```
1. network/NetworkHandler.java.disabled       → network/NetworkHandler.java
2. network/message/*.java.disabled            → network/message/*.java
```

**Ações Necessárias:**
- Migrar para nova API de networking do NeoForge
- Atualizar packet registration e handling

---

## **FASE 8: GAMEPLAY SYSTEMS**
*Objetivo: Mecânicas de jogo funcionando*

### 8.1 Sistema de Tiro
**Ordem de Habilitação:**
```
1. api/gun/*.java.disabled                    → api/gun/*.java
2. util/math/*.java.disabled                  → util/math/*.java
```

---

## **FASE 9: COMPATIBILITY**
*Objetivo: Compatibilidade com outros mods*

### 9.1 Mod Compatibility
**Ordem de Habilitação:**
```
1. compat/kubejs/*.java.disabled              → compat/kubejs/*.java
2. compat/jei/*.java.disabled                 → compat/jei/*.java
```

---

## 🛠️ WORKFLOW DETALHADO

### Para Cada Arquivo/Grupo:

1. **Pré-habilitação:**
   ```powershell
   # Backup do estado atual
   git add .; git commit -m "Backup antes de habilitar [ARQUIVO]"
   ```

2. **Habilitação:**
   ```powershell
   # Renomear arquivo
   Move-Item "path\to\file.java.disabled" "path\to\file.java"
   ```

3. **Correção:**
   - Atualizar imports para NeoForge 1.21.1
   - Migrar NBT para DataComponents quando aplicável
   - Corrigir APIs obsoletas

4. **Validação:**
   ```powershell
   # Build test
   .\gradlew build
   ```

5. **Teste:**
   ```powershell
   # Run test
   .\gradlew runClient
   ```

6. **Confirmação ou Rollback:**
   ```powershell
   # Se sucesso
   git add .; git commit -m "✅ [ARQUIVO] habilitado com sucesso"
   
   # Se falha
   Move-Item "path\to\file.java" "path\to\file.java.disabled"
   git reset --hard HEAD
   ```

---

## 📊 MÉTRICAS DE PROGRESSO

### Tracking Sheets:
- [ ] **Fase 1:** 0/15 arquivos habilitados
- [ ] **Fase 2:** 0/25 arquivos habilitados  
- [ ] **Fase 3:** 0/20 arquivos habilitados
- [ ] **Fase 4:** 0/15 arquivos habilitados
- [ ] **Fase 5:** 0/30 arquivos habilitados
- [ ] **Fase 6:** 0/150 arquivos habilitados
- [ ] **Fase 7:** 0/40 arquivos habilitados
- [ ] **Fase 8:** 0/100 arquivos habilitados
- [ ] **Fase 9:** 0/30 arquivos habilitados

### Build Status:
- [ ] Fase 1 Build Success
- [ ] Fase 2 Build Success  
- [ ] Fase 3 Build Success
- [ ] Fase 4 Build Success
- [ ] Fase 5 Build Success
- [ ] Fase 6 Build Success
- [ ] Fase 7 Build Success
- [ ] Fase 8 Build Success
- [ ] Fase 9 Build Success

---

## 🚨 PROBLEMAS CONHECIDOS

### DataComponents Migration (Crítico):
- **Problema:** Sistema NBT completamente substituído
- **Solução:** Refatorar `api/item/nbt/*` para usar `DataComponentType`
- **Prioridade:** Máxima

### Rendering API Changes:
- **Problema:** `BlockEntityWithoutLevelRenderer` movido
- **Solução:** Usar `IClientItemExtensions`
- **Prioridade:** Alta

### Networking Changes:
- **Problema:** API de rede refatorada no NeoForge
- **Solução:** Reescrever packet handling
- **Prioridade:** Média

---

## 📚 RECURSOS DE REFERÊNCIA

### Documentação:
- [NeoForge 1.21.1 Documentation](https://docs.neoforged.net/)
- [Minecraft 1.21.1 DataComponents](https://minecraft.wiki/w/Data_component_format)
- [Migration Guide Forge → NeoForge](https://docs.neoforged.net/docs/migration/)

### Exemplos de Código:
- Exemplos de DataComponents no código atual
- Referencias de migração em `Planejamento/`

---

**🎯 COMEÇAR POR:** Fase 1.1 - Configurações Base  
**⚡ PRÓXIMO PASSO:** Habilitar `config/CommonConfig.java.disabled`
