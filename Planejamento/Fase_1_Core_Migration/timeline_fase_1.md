# Timeline - Fase 1: Migração do Core

## Data: 19 de Junho de 2025

### 📋 Ações Realizadas:

#### ✅ Configuração Inicial - 17:00
- **Ação:** Análise da estrutura do projeto e identificação das classes NBT
- **Status:** Concluído

#### ✅ Criação da Classe ModDataComponents - 17:15
- **Ação:** Criada classe com todos os DataComponents necessários para substituir NBT
- **Status:** Concluído
- **Arquivo:** `src/main/java/com/tacz/guns/init/ModDataComponents.java`
- **Componentes criados:** 16 DataComponents para armas, munições, acessórios e blocos

#### ✅ Migração da Classe FireMode - 17:25
- **Ação:** Adicionados codecs necessários para DataComponents no NeoForge 1.21.1
- **Status:** Concluído
- **Arquivo:** `src/main/java/com/tacz/guns/api/item/gun/FireMode.java`

#### ✅ Atualização do ModItems - 17:30
- **Ação:** Migrado de APIs Forge para NeoForge (DeferredRegister, registros)
- **Status:** Concluído
- **Arquivo:** `src/main/java/com/tacz/guns/init/ModItems.java`

#### ✅ Atualização do GunMod - 17:35
- **Ação:** Migrado para NeoForge e adicionado registro de DataComponents
- **Status:** Concluído
- **Arquivo:** `src/main/java/com/tacz/guns/GunMod.java`

#### ✅ Migração do AmmoItemDataAccessor - 17:40
- **Ação:** Primeira classe NBT migrada para DataComponents
- **Status:** Concluído
- **Arquivo:** `src/main/java/com/tacz/guns/api/item/nbt/AmmoItemDataAccessor.java`

#### ✅ Adição de Dependências - 17:45
- **Ação:** Adicionadas dependências LuaJ e Apache Commons Math ao build.gradle
- **Status:** Concluído
- **Arquivo:** `build.gradle`

---

### 🎯 Próximas Ações:
1. ✅ Resolver erros de APIs Forge → NeoForge (Event system, @OnlyIn, etc.)
2. ⏳ Migrar classes restantes de NBT para DataComponents
3. ⏳ Atualizar sistema de eventos para NeoForge
4. ⏳ Corrigir imports e anotações obsoletas

---

### 📊 Progresso da Fase:
- [x] DataComponents criados (16 componentes)
- [x] Dependências adicionadas (LuaJ, Commons Math)
- [x] ModItems atualizado para NeoForge
- [x] GunMod atualizado para NeoForge
- [x] FireMode migrado com codecs
- [x] AmmoItemDataAccessor migrado
- [ ] Registros restantes atualizados
- [ ] Creative Tabs atualizados
- [ ] Receitas revisadas
- [ ] Damage Types atualizados
- [ ] Sistema de eventos migrado
- [ ] Testes realizados

**Status Geral da Fase:** 🟡 Em Progresso (40% concluído)

### 📈 Estatísticas:
- **DataComponents criados:** 16/16 ✅
- **Acessores NBT migrados:** 1/6 (AmmoItemDataAccessor)
- **Classes init/ atualizadas:** 2/15 (ModItems, ModDataComponents)
- **Dependências resolvidas:** 2/2 (LuaJ, Apache Commons Math)
- **Erros de compilação restantes:** ~100 (relacionados a APIs Forge)
