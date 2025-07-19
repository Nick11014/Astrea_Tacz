# 📋 Plano de Refatoração Completa - Compatibilidade TacZ para NeoForge 1.21.1

Este documento detalha o plano completo para implementar todos os TODOs de migração inseridos nos arquivos de compatibilidade.

## 🎯 Objetivo

Implementar completamente todas as funcionalidades de compatibilidade que foram temporariamente substituídas por stubs durante a correção de compilação inicial.

---

## 📊 Status Atual

- ✅ **Compilação:** Funcional com stubs
- ⚠️ **Funcionalidade:** Limitada (stubs não fazem nada)
- 🎯 **Meta:** Funcionalidade completa com todas as dependências

---

## 🗂️ Estrutura do Plano

### **FASE 1: Player Animator** 🎭
**Prioridade:** ALTA  
**Complexidade:** ALTA  
**Tempo Estimado:** 2-3 semanas

### **FASE 2: Cloth Config** ⚙️
**Prioridade:** MÉDIA  
**Complexidade:** BAIXA  
**Tempo Estimado:** 3-5 dias

### **FASE 3: KubeJS Integration** 🧩
**Prioridade:** MÉDIA  
**Complexidade:** ALTA  
**Tempo Estimado:** 1-2 semanas

### **FASE 4: Arquivos Desabilitados** 📁
**Prioridade:** BAIXA  
**Complexidade:** MÉDIA  
**Tempo Estimado:** 1 semana

---

## 🎭 FASE 1: Player Animator

### 📁 Arquivo: `PlayerAnimatorCompat.java`

#### 🎯 Objetivos
1. Resolver dependência do Player Animator
2. Implementar todos os métodos stub
3. Criar sistema de animação funcional
4. Integrar com sistema de armas TacZ

#### 📋 TODOs Identificados

**TODO #1:** Inicialização de Registros
```java
// Linha 41
// TODO: Inicializar registros de animação quando a dependência estiver resolvida
```

**TODO #2-7:** Implementação de Métodos Core
```java
// Linhas 121, 126, 131, 137, 142, 149
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

#### 🛠️ Plano de Implementação

##### **1.1 Resolver Dependência**
```gradle
// build.gradle - Resolver dependência Player Animator
dependencies {
    // Testar versões disponíveis para NeoForge 1.21.1
    implementation 'dev.kosmx.player-anim:player-animation-lib-neoforge:X.X.X'
    // OU usar repositório alternativo se necessário
}
```

##### **1.2 Implementar Inicialização**
```java
public static void init() {
    if (isInstalled()) {
        // Registrar factory de animação
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            LOWER_ANIMATION, 93, player -> new ModifierLayer<>()
        );
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            LOOP_UPPER_ANIMATION, 94, player -> new ModifierLayer<>()
        );
        // ... outros registros
    }
}
```

##### **1.3 Implementar Sistema de Animação**
```java
private static class PlayerAnimatorImpl {
    public static void playAnimation(AbstractClientPlayer player, ResourceLocation animationId) {
        AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(player);
        ModifierLayer<KeyframeAnimationPlayer> modifierLayer = new ModifierLayer<>();
        
        // Carregar animação do ResourceLocation
        KeyframeAnimation animation = loadAnimationFromResource(animationId);
        if (animation != null) {
            KeyframeAnimationPlayer player = new KeyframeAnimationPlayer(animation);
            modifierLayer.setAnimation(player);
            animationStack.addAnimLayer(1000, modifierLayer);
        }
    }
    
    // Implementar loadAnimationFromResource()
    private static KeyframeAnimation loadAnimationFromResource(ResourceLocation animationId) {
        // Lógica de carregamento de animação
        return null; // Implementar
    }
}
```

##### **1.4 Integrar com Sistema de Armas**
```java
public static void playAnimationWithDisplay(AbstractClientPlayer player, GunDisplayInstance display, float limbSwingAmount) {
    // Obter animação específica da arma
    String animationName = display.getPlayerAnimatorAnimation();
    if (animationName != null) {
        ResourceLocation animationId = ResourceLocation.fromNamespaceAndPath("tacz", animationName);
        playAnimation(player, animationId);
    }
}
```

#### 📝 Tarefas Específicas

- [ ] **1.1** Pesquisar versão correta do Player Animator para NeoForge 1.21.1
- [ ] **1.2** Configurar dependência no build.gradle
- [ ] **1.3** Implementar `init()` com registros de AnimationFactory
- [ ] **1.4** Implementar `playAnimation()` básico
- [ ] **1.5** Implementar `playAnimationWithDisplay()`
- [ ] **1.6** Implementar `hasThirdPersonAnimation()`
- [ ] **1.7** Implementar `stopAllAnimations()`
- [ ] **1.8** Implementar `isAnimationPlaying()`
- [ ] **1.9** Implementar `getCurrentAnimation()`
- [ ] **1.10** Criar sistema de carregamento de animações
- [ ] **1.11** Integrar com GunDisplayInstance
- [ ] **1.12** Testar animações em jogo

#### 🔄 Dependências
- Player Animator mod disponível para NeoForge 1.21.1
- Sistema de carregamento de recursos TacZ funcional
- GunDisplayInstance implementado

---

## ⚙️ FASE 2: Cloth Config

### 📁 Arquivo: `MenuIntegration.java`

#### 🎯 Objetivos
1. Implementar integração completa com Cloth Config
2. Restaurar funcionalidade de configuração
3. Criar interface de configuração para TacZ

#### 📋 TODOs Identificados

**TODO #1:** API do Cloth Config
```java
// Linha 71, 90
// TODO: Verificar API correta do Cloth Config para 1.21.1
```

**TODO #2:** Expansão da Integração
```java
// Linha 12
// TODO: [MIGRAÇÃO] Expandir quando Cloth Config for completamente integrado
```

#### 🛠️ Plano de Implementação

##### **2.1 Pesquisar API Correta**
```java
// Verificar métodos disponíveis no Cloth Config para 1.21.1
// ConfigBuilder pode ter mudado de save() para buildAndSave()
// ou usar sistema de callback diferente
```

##### **2.2 Implementar Métodos de Configuração**
```java
public static void saveConfigs(ConfigBuilder builder) {
    // Pesquisar método correto - pode ser:
    // builder.build().save()
    // builder.saveAll()
    // ou sistema de callback
}

public static boolean hasUnsavedChanges(ConfigBuilder builder) {
    // Pesquisar método correto - pode ser:
    // builder.hasChanges()
    // builder.isDirty()
    // ou sistema de tracking diferente
}
```

##### **2.3 Criar Interface Completa de Configuração**
```java
public static Screen createConfigScreen(Screen parent) {
    ConfigBuilder builder = ConfigBuilder.create()
        .setParentScreen(parent)
        .setTitle(Component.translatable("config.tacz.title"));
    
    // Adicionar categorias de configuração TacZ
    ConfigCategory general = builder.getOrCreateCategory(Component.translatable("config.tacz.general"));
    ConfigCategory guns = builder.getOrCreateCategory(Component.translatable("config.tacz.guns"));
    ConfigCategory client = builder.getOrCreateCategory(Component.translatable("config.tacz.client"));
    
    // Implementar opções específicas
    addGeneralOptions(general);
    addGunOptions(guns);
    addClientOptions(client);
    
    return builder.build();
}
```

#### 📝 Tarefas Específicas

- [ ] **2.1** Pesquisar documentação Cloth Config para NeoForge 1.21.1
- [ ] **2.2** Identificar métodos corretos para save() e isEdited()
- [ ] **2.3** Implementar `saveConfigs()` corretamente
- [ ] **2.4** Implementar `hasUnsavedChanges()` corretamente
- [ ] **2.5** Criar `createConfigScreen()` completo
- [ ] **2.6** Adicionar opções de configuração TacZ
- [ ] **2.7** Integrar com sistema de configuração existente
- [ ] **2.8** Testar interface de configuração

---

## 🧩 FASE 3: KubeJS Integration

### 📁 Arquivos: `TimelessClientEvents.java`, `TimelessCommonEvents.java`, `TimelessServerEvents.java`

#### 🎯 Objetivos
1. Implementar sistema de eventos KubeJS personalizado
2. Permitir scripting de funcionalidades TacZ
3. Criar API para modpacks

#### 📋 TODOs Identificados

**TODO #1:** Reimplementação KubeJS
```java
// Linha 6 em todos os arquivos
// TODO: [MIGRAÇÃO] Reimplementar quando KubeJS for completamente migrado para 1.21.1
```

**TODO #2:** API Estável
```java
// Linha 12 em todos os arquivos
// TODO: Implementar quando a API do KubeJS estiver estável
```

#### 🛠️ Plano de Implementação

##### **3.1 Pesquisar Nova API KubeJS**
```javascript
// Verificar estrutura da nova API KubeJS para 1.21.1
// EventGroup pode ter mudado para EventRegistry
// KubeEvent pode ter sido substituído por EventHandler
```

##### **3.2 Implementar TimelessClientEvents**
```java
public class TimelessClientEvents {
    public static final EventGroup GROUP = EventGroup.of("TimelessClientEvents");
    
    // Usar nova API - exemplo hipotético
    public static final EventHandler<ClientEventJS> CLIENT_EVENT = 
        GROUP.client("client_event", ClientEventJS::new);
    
    public static void postKubeJSEvent(Event event) {
        CLIENT_EVENT.post(event, (handler, forgeEvent) -> {
            handler.accept(new ClientEventJS(forgeEvent));
        });
    }
}
```

##### **3.3 Criar Eventos Personalizados TacZ**
```java
// Eventos específicos para TacZ
public static final EventHandler<GunFireEventJS> GUN_FIRE_EVENT = 
    GROUP.client("gun_fire", GunFireEventJS::new);

public static final EventHandler<GunReloadEventJS> GUN_RELOAD_EVENT = 
    GROUP.client("gun_reload", GunReloadEventJS::new);

public static final EventHandler<GunAimEventJS> GUN_AIM_EVENT = 
    GROUP.client("gun_aim", GunAimEventJS::new);
```

##### **3.4 Implementar Classes de Evento JS**
```java
public class GunFireEventJS extends ClientEventJS {
    private final ItemStack gunStack;
    private final Player player;
    
    public GunFireEventJS(Event forgeEvent, ItemStack gunStack, Player player) {
        super(forgeEvent);
        this.gunStack = gunStack;
        this.player = player;
    }
    
    // Métodos para scripts KubeJS
    public ItemStack getGun() { return gunStack; }
    public Player getPlayer() { return player; }
    public String getGunId() { /* implementar */ }
}
```

#### 📝 Tarefas Específicas

- [ ] **3.1** Pesquisar documentação KubeJS para NeoForge 1.21.1
- [ ] **3.2** Identificar nova estrutura de EventGroup/EventHandler
- [ ] **3.3** Reimplementar TimelessClientEvents
- [ ] **3.4** Reimplementar TimelessCommonEvents
- [ ] **3.5** Reimplementar TimelessServerEvents
- [ ] **3.6** Criar eventos específicos TacZ (GunFire, GunReload, etc.)
- [ ] **3.7** Implementar classes de evento JS correspondentes
- [ ] **3.8** Integrar com eventos Forge do TacZ
- [ ] **3.9** Criar documentação para modpack makers
- [ ] **3.10** Testar com scripts KubeJS

---

## 📁 FASE 4: Arquivos Desabilitados

### 📁 Arquivos: `*.java.disabled`

#### 🎯 Objetivos
1. Reabilitar arquivos desabilitados após dependências resolvidas
2. Atualizar para APIs NeoForge 1.21.1
3. Integrar com sistema principal

#### 📋 Arquivos Identificados

1. **`AnimationDataRegisterFactory.java.disabled`**
2. **`PlayerAnimatorLoader.java.disabled`** 
3. **`PlayerAnimatorAssetManager.java.disabled`**
4. **`CustomGunItemBuilder.java.disabled`**

#### 🛠️ Plano de Implementação

##### **4.1 AnimationDataRegisterFactory**
```java
// Reabilitar após PlayerAnimatorCompat funcional
public class AnimationDataRegisterFactory {
    public static void register() {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            PlayerAnimatorCompat.LOWER_ANIMATION, 93, 
            player -> new ModifierLayer<>()
        );
        // ... outros registros
    }
}
```

##### **4.2 PlayerAnimatorLoader**
```java
// Atualizar para NeoForge 1.21.1
public class PlayerAnimatorLoader {
    public static void loadAnimations() {
        // Usar nova API de carregamento de recursos
        // ResourceProvider em vez de métodos deprecated
    }
}
```

##### **4.3 CustomGunItemBuilder** 
```java
// Migrar RegistryObject para DeferredHolder
public class CustomGunItemBuilder {
    private DeferredHolder<Item, ? extends Item> item; // em vez de RegistryObject
    
    // Atualizar todos os usos para nova API
}
```

#### 📝 Tarefas Específicas

- [ ] **4.1** Reabilitar AnimationDataRegisterFactory após Fase 1
- [ ] **4.2** Reabilitar PlayerAnimatorLoader após Fase 1  
- [ ] **4.3** Reabilitar PlayerAnimatorAssetManager após Fase 1
- [ ] **4.4** Atualizar CustomGunItemBuilder para DeferredHolder
- [ ] **4.5** Migrar outros RegistryObject para DeferredHolder
- [ ] **4.6** Testar funcionalidade de todos os arquivos reabilitados

---

## 📅 Cronograma de Implementação

### **Semana 1-3: FASE 1 - Player Animator**
- **Semana 1:** Pesquisa e configuração de dependências
- **Semana 2:** Implementação core do PlayerAnimatorCompat
- **Semana 3:** Integração e testes

### **Semana 4: FASE 2 - Cloth Config**
- **Dias 1-2:** Pesquisa de API e implementação básica
- **Dias 3-5:** Interface completa e integração

### **Semana 5-6: FASE 3 - KubeJS**
- **Semana 5:** Reimplementação dos eventos base
- **Semana 6:** Eventos personalizados TacZ e testes

### **Semana 7: FASE 4 - Arquivos Desabilitados**
- **Dias 1-3:** Reabilitação de arquivos
- **Dias 4-5:** Testes finais e integração

### **Semana 8: Finalização**
- **Dias 1-3:** Testes completos do sistema
- **Dias 4-5:** Documentação e cleanup

---

## 🧪 Estratégia de Testes

### **Por Fase:**

1. **Fase 1:** Testes de animação em jogo, verificação de performance
2. **Fase 2:** Testes de interface de configuração, persistência de dados  
3. **Fase 3:** Testes com scripts KubeJS reais, verificação de eventos
4. **Fase 4:** Testes de regressão completos

### **Testes Finais:**
- [ ] Compilação sem warnings
- [ ] Carregamento de mundo sem erros
- [ ] Funcionalidade completa de armas
- [ ] Animações funcionando
- [ ] Configurações persistindo
- [ ] Scripts KubeJS executando
- [ ] Performance adequada

---

## 🎯 Critérios de Sucesso

### **Funcionalidade Restaurada:**
- ✅ Animações de armas funcionando
- ✅ Interface de configuração acessível
- ✅ Eventos KubeJS disparando
- ✅ Sistema de construção de itens funcionando

### **Qualidade de Código:**
- ✅ Zero TODOs de migração restantes
- ✅ Cobertura de testes adequada
- ✅ Documentação atualizada
- ✅ Performance otimizada

### **Compatibilidade:**
- ✅ Totalmente compatível com NeoForge 1.21.1
- ✅ Dependências opcionais funcionando
- ✅ Compatibilidade com modpacks

---

## 📚 Recursos e Referências

### **Documentação:**
- [NeoForge 1.21.1 Documentation](https://docs.neoforged.net/)
- [Player Animator Wiki](https://github.com/KosmX/playeranimator/wiki)
- [Cloth Config API](https://github.com/shedaniel/ClothConfig)
- [KubeJS Documentation](https://kubejs.com/)

### **Repositórios de Referência:**
- [SuperbWarfare 1.21](https://github.com/Superb-Owls/superbwarfare) 
- [Player Animator Examples](https://github.com/KosmX/playeranimator/tree/main/examples)

### **Ferramentas:**
- IntelliJ IDEA com plugin Minecraft Development
- MCreator para testes de funcionalidade
- Gradle para build e dependências

---

## ⚠️ Riscos e Mitigações

### **Riscos Identificados:**

1. **Player Animator não disponível para 1.21.1**
   - **Mitigação:** Manter stubs funcionais, implementar apenas quando disponível

2. **APIs KubeJS instáveis**
   - **Mitigação:** Implementação modular, fácil de atualizar

3. **Breaking changes em dependências**
   - **Mitigação:** Versioning cuidadoso, testes extensivos

4. **Performance de animações**
   - **Mitigação:** Profiling regular, otimizações incrementais

---

## 📊 Métricas de Progresso

### **Tracking de TODOs:**
- **Total:** 15+ TODOs identificados
- **Fase 1:** 7 TODOs (Player Animator)
- **Fase 2:** 2 TODOs (Cloth Config)  
- **Fase 3:** 3 TODOs (KubeJS)
- **Fase 4:** 3+ TODOs (Arquivos desabilitados)

### **Milestone Tracking:**
- [ ] **M1:** Player Animator funcional (Semana 3)
- [ ] **M2:** Cloth Config funcional (Semana 4)
- [ ] **M3:** KubeJS funcional (Semana 6)
- [ ] **M4:** Todos arquivos reabilitados (Semana 7)
- [ ] **M5:** Sistema completo testado (Semana 8)

---

*Este plano será atualizado conforme o progresso e descoberta de novos requisitos durante a implementação.*
