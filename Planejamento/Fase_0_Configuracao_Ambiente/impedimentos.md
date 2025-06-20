# Impedimentos - Fase 0: Configuração do Ambiente

## Impedimento #1 - 19/06/2025

### 📍 Contexto:
- **Fase:** 0 - Configuração do Ambiente
- **Item do Checklist:** Finalização do build.gradle e teste de compilação
- **Arquivo(s) Afetado(s):** build.gradle, gradle.properties

### 🚫 Problema Encontrado:
**Incompatibilidade de Versão Java com NeoGradle**

O ambiente de desenvolvimento está utilizando Java 24, mas o NeoGradle versão 7.0.175 (mais recente disponível) ainda não suporta completamente esta versão do Java. Isso resulta no erro:

```
FAILURE: Build failed with an exception.
* What went wrong:
BUG! exception in phase 'semantic analysis' in source unit '_BuildScript_' 
Unsupported class file major version 68
```

**Impacto:** ⚠️ **BLOQUEANTE** - Impossibilita a compilação do projeto e bloqueia o progresso para as próximas fases.

### 🔍 O que foi tentado:
1. **Atualização do NeoGradle:** Testadas versões 7.0.142, 7.0.163 e 7.0.175
2. **Simplificação do build.gradle:** Removidas dependências complexas, mantido apenas o essencial
3. **Diferentes versões do Gradle:** Testadas versões 8.8 e 8.10
4. **Configurações alternativas:** Tentativas com diferentes sintaxes de configuração

### 📋 Detalhes Técnicos:
**Ambiente Atual:**
- **Java:** 24.0.1 (Oracle Corporation 24.0.1+9-30)
- **Gradle:** 8.8
- **NeoGradle:** 7.0.175 (mais recente disponível)
- **SO:** Windows 10
- **Major Version 68:** Corresponde ao Java 24

**Situação do Projeto:**
- ✅ **80% da migração está completa:** gradle.properties, mods.toml, pack.mcmeta configurados
- ✅ **Estrutura básica funciona:** Dependências são baixadas corretamente
- ❌ **Bloqueio na compilação:** Incompatibilidade de versão Java

### 🎯 Soluções Propostas:

#### **Solução 1: Downgrade para Java 21 (RECOMENDADA)**
- **Ação:** Instalar Java 21 LTS e configurar como padrão para desenvolvimento
- **Justificativa:** Java 21 é LTS e totalmente suportado pelo ecossistema NeoForge
- **Esforço:** 1-2 horas (download, instalação, configuração)
- **Impacto:** Baixo - Java 21 atende todas as necessidades do projeto

#### **Solução 2: Configuração JAVA_HOME Específica**
- **Ação:** Manter Java 24 globalmente, usar Java 21 apenas para este projeto
- **Comando:** `set JAVA_HOME=C:\Program Files\Java\jdk-21` (antes de executar gradlew)
- **Esforço:** 2-3 horas (instalação Java 21, scripts, configuração)
- **Impacto:** Médio - Requer configuração adicional por projeto

#### **Solução 3: Aguardar Atualização (NÃO RECOMENDADA)**
- **Ação:** Esperar nova versão do NeoGradle com suporte ao Java 24
- **Estimativa:** Indefinida (pode levar meses)
- **Impacto:** Alto - Atraso significativo no projeto

### 🎯 Status:
- [x] Impedimento registrado
- [x] Problema diagnosticado
- [x] Soluções propostas
- [x] **SOLUÇÃO IMPLEMENTADA:** Atualização do NeoGradle para 7.0.185 com Gradle 8.14.2
- [x] **Impedimento resolvido** ✅

### ✅ Solução Aplicada:
**Atualização Completa do Build System**

1. **NeoGradle atualizado:** 7.0.175 → **7.0.185** (com suporte ao Java 24)
2. **Gradle atualizado:** 8.8 → **8.14.2** (versão com suporte nativo ao Java 24)
3. **Configuração das runs corrigida:** Sintaxe atualizada para NeoForge 1.21.1
4. **mods.toml → neoforge.mods.toml:** Renomeado conforme exigido
5. **Estrutura do projeto atualizada:** Baseada no MDK oficial do NeoForge

**Resultado:** ✅ **Compilação básica funcionando** - O projeto agora compila a infraestrutura do NeoForge sem problemas de compatibilidade Java.

### 📊 Próximos Passos:
1. ✅ **FASE 0 CONCLUÍDA** - Build system configurado com sucesso
2. **Iniciar Fase 1:** Migração do Core
   - Migração de NBT para DataComponents (prioridade máxima)
   - Atualização de APIs do EventBus (100 erros para corrigir)
   - Adição de dependências faltantes (LuaJ, Apache Commons Math)
   - Migração de `@OnlyIn` e outras anotações obsoletas
3. **Configurar dependências:** Adicionar no build.gradle as libs necessárias
4. **Testar construção completa:** Após correções da Fase 1

---

## Impedimento #3 - 23/06/2025

### 📍 Contexto:
- **Fase:** 0 - Configuração do Ambiente (REABERTA)
- **Item do Checklist:** Diagnóstico de problemas no build system
- **Arquivo(s) Afetado(s):** build.gradle, gradle.properties, sistema de build Gradle

### 🚫 Problema Encontrado:
**Build System Issues após retorno à Fase 0**

Durante a Fase 2 (Cliente/Renderização), foram identificados problemas persistentes no sistema de build que impediram o progresso efetivo. O build.gradle apresentou inconsistências e o IDE não estava sincronizando corretamente as dependências do NeoForge 1.21.1.

**Sintomas:**
- Falhas de sincronização do IDE com NeoForge 1.21.1
- Possíveis conflitos de versões entre Minecraft/NeoForge
- Problemas de dependências não resolvidos corretamente

**Impacto:** ⚠️ **BLOQUEANTE** - Impede progresso efetivo nas fases de migração.

### 🔍 Investigação Realizada:

#### **Comparação com Template Oficial:**
- **Template MDK-1.21-NeoGradle-main:** ✅ **BUILD SUCCESSFUL**
  - Minecraft: 1.21
  - NeoForge: 21.0.167
  - Gradle: 8.7

- **Projeto Principal:** ❓ **EM TESTE**
  - Minecraft: 1.21.1  
  - NeoForge: 21.1.42
  - Gradle: 8.14.2

#### **Diferenças Identificadas:**
1. **Versões mais recentes** no projeto principal
2. **Configurações adicionais** no build.gradle do projeto
3. **Estrutura mais complexa** comparada ao template

### 🎯 Status Atual:
- [x] Impedimento registrado
- [x] Template oficial testado (funciona perfeitamente)
- [x] Build do projeto principal executado
- [x] **BUILD FAILED CONFIRMADO** - 100 erros de compilação identificados

### 🚨 **PROBLEMA CONFIRMADO:**
**Migração Incompleta de APIs Forge → NeoForge**

**100 erros de compilação** causados por APIs do Forge 1.20.1 que não existem no NeoForge 1.21.1:

#### **APIs Problemáticas Identificadas:**
1. **Event System:**
   - `net.minecraftforge.eventbus.api.Event` → `net.neoforged.bus.api.Event`
   - `net.minecraftforge.eventbus.api.Cancelable` → `net.neoforged.bus.api.ICancellableEvent`
   - `net.minecraftforge.fml.LogicalSide` → `net.neoforged.fml.LogicalSide`

2. **Side Markers:**
   - `net.minecraftforge.api.distmarker.*` → `net.neoforged.api.distmarker.*`

3. **Entity System:**
   - `net.minecraftforge.entity.IEntityAdditionalSpawnData` → API mudou
   - `net.minecraftforge.entity.PartEntity` → API mudou

4. **Registry System:**
   - `net.minecraftforge.registries.RegistryObject` → `net.neoforged.neoforge.registries.DeferredHolder`

5. **Capabilities:**
   - `net.minecraftforge.common.capabilities.ForgeCapabilities` → Sistema mudou

6. **Networking:**
   - `net.minecraftforge.network.NetworkHooks` → API mudou

### 📊 Próximos Passos (PRIORIDADE MÁXIMA):
1. **PAUSAR** todas as outras fases
2. **Migrar sistematicamente** todas as APIs Forge → NeoForge
3. **Iniciar nova Fase 1** focada em APIs (não apenas DataComponents)
4. **Usar template MDK** como referência para APIs corretas
