# 🎯 PLANO ESTRATÉGICO: DIVIDIR E CONQUISTAR - TacZ NeoForge 1.21.1

## 🧩 Estratégia: Desabilitar Tudo, Habilitar Seção por Seção

**Filosofia:** Em vez de tentar corrigir erros específicos, vamos desabilitar TODAS as funcionalidades do mod e habilitar apenas UMA seção por vez. Só passamos para a próxima quando a atual estiver 100% funcional.

**Critério de Sucesso:** `runClient` executa sem erros para a seção atual antes de habilitar a próxima.

## 🔧 Estratégia de Implementação por Seções

### **SEÇÃO 1: REGISTROS BÁSICOS** 
*Objetivo: Fazer o mod carregar apenas com registros mínimos*

#### **O que manter habilitado:**
- [ ] **ModItems.java** - Apenas registros de itens sem lógica
- [ ] **ModSoundEvents.java** - Apenas registros de sons
- [ ] **ModDataComponents.java** - Apenas registros de DataComponents
- [ ] **GunMod.java** - Apenas inicialização básica dos registros

#### **O que desabilitar temporariamente:**
- [ ] **Todo o sistema de assets/recursos**
- [ ] **Todo o sistema de renderização**
- [ ] **Todo o sistema de rede**
- [ ] **Todo o sistema de scripts**
- [ ] **Todo o sistema de compatibilidade**
- [ ] **Toda lógica de gameplay**
- [ ] **Todas as configurações complexas**

#### **Arquivos a comentar/desabilitar:**
- [ ] **CommonAssetsManager.java** - Comentar toda a classe
- [ ] **ScriptManager.java** - Comentar toda a classe
- [ ] **NetworkHandler.java** - Comentar toda a classe
- [ ] **Toda pasta `client/`** - Comentar imports no GunMod
- [ ] **Toda pasta `compat/`** - Comentar imports no GunMod
- [ ] **GunConfig.java** - Comentar configurações complexas

**✅ Critério de Sucesso:** `runClient` carrega o mod e mostra itens no Creative Tab sem crashes.

---

### **SEÇÃO 2: CONFIGURAÇÕES BÁSICAS**
*Objetivo: Sistema de configurações funcionando*

#### **O que habilitar:**
- [ ] **GunConfig.java** - Configurações simples
- [ ] **ConfigIO.java** - Sistema de I/O de configs

#### **Mantém da seção anterior:**
- [ ] Todos os registros básicos

**✅ Critério de Sucesso:** Configurações carregam sem erros e podem ser modificadas.

---

### **SEÇÃO 3: SISTEMA DE REDE BÁSICO**
*Objetivo: Comunicação cliente-servidor básica*

#### **O que habilitar:**
- [ ] **NetworkHandler.java** - Apenas pacotes básicos
- [ ] **Pacotes essenciais do `network/`** - Um por vez

#### **Mantém das seções anteriores:**
- [ ] Registros básicos
- [ ] Configurações

**✅ Critério de Sucesso:** Servidor e cliente se comunicam sem crashes de rede.

---

### **SEÇÃO 4: SISTEMA DE RECURSOS (SEM SCRIPTS)**
*Objetivo: Carregamento de assets básicos*

#### **O que habilitar:**
- [ ] **CommonAssetsManager.java** - SEM LuaTable/Scripts
- [ ] **CommonNetworkCache.java** - Versão simplificada
- [ ] **CommonGunIndex.java** - SEM scripts

#### **O que manter desabilitado:**
- [ ] **ScriptManager.java** - Continua comentado
- [ ] **Qualquer referência a LuaTable**

**✅ Critério de Sucesso:** Assets carregam sem scripts, dados básicos disponíveis.

---

### **SEÇÃO 5: RENDERIZAÇÃO BÁSICA**
*Objetivo: Visualização de itens*

#### **O que habilitar:**
- [ ] **Renderização básica de itens**
- [ ] **Modelos 3D simples**
- [ ] **Texturas básicas**

#### **O que manter desabilitado:**
- [ ] **Animações complexas**
- [ ] **Sistema GLTF/Bedrock**
- [ ] **HUD avançado**

**✅ Critério de Sucesso:** Itens aparecem com modelos e texturas corretas.

---

### **SEÇÃO 6: GAMEPLAY BÁSICO**
*Objetivo: Mecânicas fundamentais das armas*

#### **O que habilitar:**
- [ ] **Sistema de armas básico**
- [ ] **Tiro simples (sem animações)**
- [ ] **Recarga básica**

**✅ Critério de Sucesso:** Armas funcionam basicamente (atirar e recarregar).

---

### **SEÇÃO 7: SISTEMA DE SCRIPTS**
*Objetivo: Funcionalidades avançadas com Lua*

#### **O que habilitar:**
- [ ] **ScriptManager.java**
- [ ] **Sistema LuaTable**
- [ ] **Scripts nas armas**

**✅ Critério de Sucesso:** Scripts funcionam e armas têm comportamentos avançados.

---

### **SEÇÃO 8: RENDERIZAÇÃO AVANÇADA**
*Objetivo: Animações e efeitos visuais*

#### **O que habilitar:**
- [ ] **Sistema GLTF/Bedrock**
- [ ] **Animações de recarga e tiro**
- [ ] **HUD complexo**

**✅ Critério de Sucesso:** Animações funcionam corretamente.

---

### **SEÇÃO 9: COMPATIBILIDADE**
*Objetivo: Integração com outros mods*

#### **O que habilitar:**
- [ ] **Sistema JEI**
- [ ] **Sistema KubeJS**
- [ ] **Outras integrações**

**✅ Critério de Sucesso:** Compatibilidade funciona sem conflitos.

---

## 🚀 IMPLEMENTAÇÃO IMEDIATA

### **Passo 1: Preparar Base Limpa**
1. **Identificar todos os pontos de inicialização no GunMod.java**
2. **Comentar TUDO exceto registros básicos**
3. **Criar flags booleanas para controlar seções:**

```java
public class GunMod {
    // Flags de controle de seções
    public static final boolean ENABLE_CONFIGS = false;
    public static final boolean ENABLE_NETWORK = false;
    public static final boolean ENABLE_ASSETS = false;
    public static final boolean ENABLE_CLIENT = false;
    public static final boolean ENABLE_SCRIPTS = false;
    public static final boolean ENABLE_COMPAT = false;
    
    // Apenas registros básicos habilitados inicialmente
}
```

### **Passo 2: Implementar Controle Condicional**
- Envolver cada inicialização com `if (ENABLE_SECTION)`
- Comentar imports desnecessários temporariamente
- Usar `@SuppressWarnings` onde necessário

### **Passo 3: Processo de Teste**
1. **Habilitar uma flag por vez**
2. **Executar `runClient`**
3. **Corrigir TODOS os erros da seção atual**
4. **Só após sucesso total, passar para próxima seção**

## 📋 STATUS ATUAL: PREPARAÇÃO PARA SEÇÃO 1
**Próximo Passo:** Comentar todas as funcionalidades e deixar apenas registros básicos ativos.
