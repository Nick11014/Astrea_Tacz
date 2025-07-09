# 📋 BACKLOG DE DÉBITO TÉCNICO - TacZ NeoForge 1.21.1

**Data de Criação:** 2025-07-09  
**Status:** Mapeamento completo de implementações mínimas  
**Objetivo:** Rastear e priorizar restauração de funcionalidades completas

---

## 🎯 **OVERVIEW DO DÉBITO TÉCNICO**

Durante a migração do núcleo comum, aplicamos **implementação mínima estratégica** para quebrar dependências circulares e manter a compilação funcionando. Este documento mapeia sistematicamente cada implementação temporária que precisa ser restaurada.

### **📊 Categorias de Débito**

| Categoria | Arquivos Afetados | Prioridade | Complexidade |
|-----------|-------------------|------------|--------------|
| **Sistema GSON** | 5 arquivos | 🔴 **Alta** | 🟡 Média |
| **Sistema de Modificadores** | 4 arquivos | 🟠 **Média-Alta** | 🔴 Alta |
| **Camada de Cliente** | 3 arquivos | 🟡 **Média** | 🔴 Alta |
| **Sistema de Crafting** | 2 arquivos | 🟢 **Baixa** | 🟠 Média |
| **Sistema de Rede** | 1 arquivo | 🟠 **Média-Alta** | 🟠 Média |

---

## 🔴 **CATEGORIA 1: SISTEMA GSON (Prioridade Alta)**

### **Problema:** Uso de `new Gson()` em vez de `CommonAssetsManager.GSON`
**Impacto:** Perda de serializers customizados para tipos complexos  
**Quando Restaurar:** Após completar migração de todos os serializers

#### **📁 Arquivos Afetados:**

**1. `AttachmentDataManager.java`**
- **Linha 16:** `super(..., new Gson(), ...)` → `super(..., CommonAssetsManager.GSON, ...)`
- **Dependência:** CommonAssetsManager totalmente funcional

**2. `RecipeFilterManager.java`**  
- **Linha 37:** `this.gson = new Gson()` → `this.gson = CommonAssetsManager.GSON`
- **Dependência:** CommonAssetsManager totalmente funcional

**3. `AttachmentsTagManager.java`**
- **Linha 38:** `this.gson = new Gson()` → `this.gson = CommonAssetsManager.GSON`  
- **Dependência:** CommonAssetsManager totalmente funcional

**4. `CommonNetworkCache.java`**
- **Linhas 158, 163, 191:** `new Gson()` → `CommonAssetsManager.GSON`
- **Dependência:** CommonAssetsManager totalmente funcional

---

## 🟠 **CATEGORIA 2: SISTEMA DE MODIFICADORES (Prioridade Média-Alta)**

### **Problema:** Lógica de `JsonProperty` e `AttachmentPropertyManager` comentada
**Impacto:** Modificações dinâmicas de armas e acessórios não funcionam  
**Quando Restaurar:** Após habilitar sistema de modificadores

#### **📁 Arquivos Afetados:**

**1. `GunData.java`**
- **Linhas 7-11:** Imports do sistema de modificadores comentados
- **Linha 296:** Lógica de `getRoundsPerMinute` e `getInaccuracy` simplificada
- **Dependência:** `IGun`, `JsonProperty`, sistema de modificadores

**2. `AttachmentDataManager.java`**
- **Linhas 6-9:** Imports de `JsonProperty` e `AttachmentPropertyManager` comentados
- **Linhas 24-41:** Lógica completa de aplicação de modificadores comentada
- **Dependência:** `AttachmentPropertyManager.getModifiers()`

**3. `CommonNetworkCache.java`**
- **Linhas 9-10, 18-19:** Imports do sistema de modificadores comentados
- **Linhas 167-183:** Lógica de aplicação de modificadores em `parseAttachmentData` comentada
- **Dependência:** `JsonProperty`, `AttachmentPropertyManager`

---

## 🟡 **CATEGORIA 3: CAMADA DE CLIENTE (Prioridade Média)**

### **Problema:** Dependências de renderização e display comentadas
**Impacto:** Interface de cliente não funcional  
**Quando Restaurar:** Durante migração da camada de cliente

#### **📁 Arquivos Afetados:**

**1. `ClientGunIndex.java`**
- **Linhas 4-6:** Imports de `ClientAssetsManager` e `GunDisplayInstance` comentados
- **Linha 25:** `GunDisplayInstance display` → `Object display`
- **Linhas 35-39:** Lógica de criação de display comentada
- **Linhas 70-76:** Método `checkDisplay` comentado
- **Linhas 95-99:** Retorno de `GunDisplayInstance` → `Object`
- **Dependência:** `ClientAssetsManager`, `GunDisplayInstance`, `GunDisplay`

**2. `ClientAttachmentIndex.java`**
- **Todo o arquivo:** Versão completamente simplificada
- **Dependência:** Sistema completo de renderização de cliente

**3. `GunDisplayInstance.java.disabled`**
- **Linha 15:** Import de `GunModelTypeManager` comentado
- **Status:** Arquivo ainda desabilitado por complexidade extrema
- **Dependência:** Sistema completo de modelos e animações

---

## 🟢 **CATEGORIA 4: SISTEMA DE CRAFTING (Prioridade Baixa)**

### **Problema:** Sistema de crafting customizado comentado
**Impacto:** Receitas customizadas de armas não funcionam  
**Quando Restaurar:** Após migrar sistema de receitas

#### **📁 Arquivos Afetados:**

**1. `CommonAssetsManager.java`**
- **Linhas 8-12:** Imports de crafting comentados (`GunSmithTableIngredient`, `GunSmithTableRecipe`, etc.)
- **Linhas 59-62:** Registros GSON de serializers de crafting comentados
- **Linhas 241-245:** Lógica de inicialização de receitas comentada
- **Dependência:** `GunSmithTableRecipe`, `ModRecipe`, serializers de crafting

---

## 🟠 **CATEGORIA 5: SISTEMA DE REDE (Prioridade Média-Alta)**

### **Problema:** Sincronização servidor-cliente comentada
**Impacto:** Dados não sincronizam entre servidor e cliente  
**Quando Restaurar:** Após migrar sistema de networking

#### **📁 Arquivos Afetados:**

**1. `CommonAssetsManager.java`**
- **Linhas 13-14:** Imports de rede comentados (`NetworkHandler`, `ServerMessageSyncGunPack`)
- **Linhas 261-266:** Lógica de sincronização de dados comentada
- **Dependência:** `NetworkHandler`, `ServerMessageSyncGunPack`, sistema de eventos

---

## 🛠️ **PLANO DE RESTAURAÇÃO POR FASES**

### **Fase A: Consolidação do GSON (Próxima Sessão)**
1. Verificar se todos os serializers customizados estão funcionando
2. Testar `CommonAssetsManager.GSON` completamente
3. Restaurar uso de `CommonAssetsManager.GSON` em todos os gerenciadores
4. **Resultado:** Sistema de serialização completo

### **Fase B: Sistema de Modificadores (Sessão Futura)**
1. Habilitar `AttachmentPropertyManager` e `JsonProperty`
2. Restaurar lógica de modificadores em `GunData`
3. Restaurar aplicação de modificadores em `AttachmentDataManager`
4. **Resultado:** Sistema de modificações dinâmicas funcional

### **Fase C: Camada de Cliente (Múltiplas Sessões)**
1. Migrar `ClientAssetsManager`
2. Migrar `GunDisplayInstance` (muito complexo)
3. Restaurar funcionalidade completa dos índices de cliente
4. **Resultado:** Interface e renderização funcionais

### **Fase D: Sistemas Avançados (Sessões Finais)**
1. Migrar sistema de networking
2. Migrar sistema de crafting customizado
3. Restaurar sincronização servidor-cliente
4. **Resultado:** Funcionalidade completa restaurada

---

## 📈 **MÉTRICAS DE PROGRESSO**

### **Status Atual:**
- **✅ Funcionalidade Básica:** 95% (compilação e estrutura)
- **🔄 Funcionalidade Intermediária:** 60% (dados básicos funcionam)
- **❌ Funcionalidade Avançada:** 20% (modificadores, cliente, networking)
- **❌ Funcionalidade Completa:** 5% (tudo funcionando perfeitamente)

### **Meta por Fase:**
- **Fase A:** 70% funcionalidade intermediária
- **Fase B:** 80% funcionalidade intermediária  
- **Fase C:** 90% funcionalidade avançada
- **Fase D:** 95% funcionalidade completa

---

## 🎯 **NOTAS ESTRATÉGICAS**

1. **Não Rush:** Cada categoria deve ser completada antes de avançar
2. **Testes Incrementais:** Testar cada restauração individualmente
3. **Backup dos TODOs:** Manter comentários até ter certeza que funciona
4. **Documentação:** Atualizar este arquivo conforme progresso

**Este débito técnico é ESTRATÉGICO, não acidental. Foi criado intencionalmente para permitir progresso rápido e será resolvido sistematicamente.**
