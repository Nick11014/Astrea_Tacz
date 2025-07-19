# 🎯 PLANO ESTRATÉGICO: DIVIDIR E CONQUISTAR - TacZ NeoForge 1.21.1

## 📊 Análise do Crash Atual

**Problema Principal:** `NoClassDefFoundError: org/luaj/vm2/LuaTable`

O erro indica que a dependência LuaJ não está sendo carregada corretamente ou há um conflito de versões. O mod está tentando carregar classes relacionadas ao sistema de scripts Lua durante a inicialização.

## 🔧 Estratégia de Divisão em Fases

### **FASE 0: ESTABILIZAÇÃO BASE (PRIORIDADE MÁXIMA)**
*Objetivo: Fazer o mod carregar sem crashes*

#### **Etapa 0.1: Correção de Dependências**
- [ ] **Verificar e corrigir dependência LuaJ no build.gradle**
- [ ] **Testar versões alternativas do LuaJ compatíveis com NeoForge 1.21.1**
- [ ] **Verificar conflitos com outras dependências**

#### **Etapa 0.2: Desabilitar Sistema de Scripts Temporariamente**
- [ ] **Comentar/desabilitar ScriptManager no GunMod.java**
- [ ] **Comentar imports e referências a LuaTable em:**
  - `CommonAssetsManager.java`
  - `CommonNetworkCache.java` 
  - `CommonGunIndex.java`
  - `ScriptManager.java`

#### **Etapa 0.3: Teste de Carregamento Básico**
- [ ] **Executar `runClient` para verificar se o mod carrega**
- [ ] **Verificar se registros básicos funcionam (itens, sons, etc.)**

---

### **FASE 1: NÚCLEO BÁSICO**
*Objetivo: Sistemas fundamentais funcionando*

#### **Etapa 1.1: Sistema de Registros**
- [ ] **Verificar ModDataComponents**
- [ ] **Verificar ModSoundEvents**
- [ ] **Testar criação de itens básicos**

#### **Etapa 1.2: Configurações**
- [ ] **Verificar GunConfig**
- [ ] **Testar carregamento de configurações**

#### **Etapa 1.3: Rede Básica**
- [ ] **Verificar NetworkHandler**
- [ ] **Testar comunicação cliente-servidor básica**

---

### **FASE 2: RECURSOS E ASSETS**
*Objetivo: Sistema de carregamento de recursos*

#### **Etapa 2.1: Gerenciadores de Recursos (SEM Scripts)**
- [ ] **Reabilitar CommonAssetsManager (sem LuaTable)**
- [ ] **Verificar carregamento de texturas e modelos**
- [ ] **Testar sistema de índices básico**

#### **Etapa 2.2: Sistema de Cache**
- [ ] **Reabilitar CommonNetworkCache (sem scripts)**
- [ ] **Verificar sincronização de dados**

---

### **FASE 3: RENDERIZAÇÃO BÁSICA**
*Objetivo: Visualização de itens e blocos*

#### **Etapa 3.1: Renderização de Itens**
- [ ] **Reabilitar renderização básica de armas**
- [ ] **Verificar texturas e modelos simples**

#### **Etapa 3.2: Interface Básica**
- [ ] **Reabilitar HUD simples (sem animações)**
- [ ] **Verificar menus básicos**

---

### **FASE 4: GAMEPLAY BÁSICO**
*Objetivo: Mecânicas fundamentais das armas*

#### **Etapa 4.1: Sistema de Armas Básico**
- [ ] **Reabilitar criação de armas**
- [ ] **Verificar carregamento de stats básicos**

#### **Etapa 4.2: Mecânicas Básicas**
- [ ] **Implementar tiro básico (sem animações)**
- [ ] **Implementar recarga básica**

---

### **FASE 5: SISTEMA DE SCRIPTS**
*Objetivo: Reintegrar funcionalidades avançadas*

#### **Etapa 5.1: Correção de LuaJ**
- [ ] **Investigar versão correta do LuaJ para NeoForge 1.21.1**
- [ ] **Implementar wrapper alternativo se necessário**

#### **Etapa 5.2: Reintegração Gradual**
- [ ] **Reabilitar ScriptManager**
- [ ] **Reintegrar sistema de scripts nas armas**

---

### **FASE 6: FUNCIONALIDADES AVANÇADAS**
*Objetivo: Recursos completos do mod*

#### **Etapa 6.1: Animações Complexas**
- [ ] **Sistema GLTF/Bedrock**
- [ ] **Animações de recarga e tiro**

#### **Etapa 6.2: Compatibilidade**
- [ ] **Integração com JEI, KubeJS, etc.**
- [ ] **Sistemas de compatibilidade**

---

## 🚀 IMPLEMENTAÇÃO IMEDIATA

### **Arquivos a Modificar na Fase 0:**

1. **build.gradle** - Verificar dependência LuaJ
2. **GunMod.java** - Desabilitar carregamento de sistemas com LuaTable
3. **CommonAssetsManager.java** - Comentar métodos com LuaTable
4. **CommonNetworkCache.java** - Comentar métodos com LuaTable
5. **CommonGunIndex.java** - Comentar fields e métodos com LuaTable
6. **ScriptManager.java** - Classe inteira comentada temporariamente

### **Critérios de Sucesso por Fase:**

- **Fase 0:** Mod carrega sem crashes
- **Fase 1:** Registros funcionam, configurações carregam
- **Fase 2:** Assets básicos carregam
- **Fase 3:** Itens aparecem no jogo com texturas
- **Fase 4:** Armas podem ser usadas basicamente
- **Fase 5:** Scripts funcionam novamente
- **Fase 6:** Todas as funcionalidades restauradas

### **Monitoramento:**
- Após cada fase, executar `runClient` e documentar problemas
- Manter log de mudanças para poder reverter se necessário
- Testar funcionalidades básicas antes de prosseguir

## 📋 STATUS ATUAL: FASE 0 - ESTABILIZAÇÃO BASE
**Próximo Passo:** Implementar correções emergenciais para fazer o mod carregar.
