# ✅ Checklist de Implementação - Refatoração Compatibilidade TacZ

Este arquivo contém checklists práticos para implementar cada TODO identificado nos arquivos de compatibilidade.

---

## 🎭 CHECKLIST FASE 1: Player Animator

### 📋 Preparação
- [ ] **Research:** Verificar se Player Animator está disponível para NeoForge 1.21.1
- [ ] **Dependency:** Encontrar versão correta no repositório KosmX
- [ ] **Documentation:** Ler changelog do Player Animator para mudanças na API
- [ ] **Testing:** Configurar ambiente de teste com Player Animator

### 📋 build.gradle
- [ ] **Uncomment dependency:** Descomentar linha do Player Animator no build.gradle
- [ ] **Test compilation:** Verificar se compila com dependência ativa
- [ ] **Version verification:** Confirmar que é a versão correta para 1.21.1

### 📋 PlayerAnimatorCompat.java - Linha 41 (init method)
```java
// TODO: Inicializar registros de animação quando a dependência estiver resolvida
```

**Implementação:**
- [ ] **Import necessários:** Adicionar imports do Player Animator
- [ ] **AnimationFactory registration:** Implementar registros no método init()
- [ ] **Resource locations:** Verificar se ResourceLocations estão corretos
- [ ] **Priority levels:** Configurar prioridades das animações (93, 94, 95, 96)

**Código de referência:**
```java
public static void init() {
    if (isInstalled()) {
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            LOWER_ANIMATION, 93, player -> new ModifierLayer<>()
        );
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            LOOP_UPPER_ANIMATION, 94, player -> new ModifierLayer<>()
        );
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            ONCE_UPPER_ANIMATION, 95, player -> new ModifierLayer<>()
        );
        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
            ROTATION_ANIMATION, 96, player -> new ModifierLayer<>()
        );
    }
}
```

### 📋 PlayerAnimatorCompat.java - Linha 121 (playAnimation)
```java
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

**Implementação:**
- [ ] **AnimationStack access:** Implementar acesso ao stack de animação do jogador
- [ ] **ModifierLayer creation:** Criar e configurar ModifierLayer
- [ ] **Animation loading:** Implementar sistema de carregamento de animações
- [ ] **Error handling:** Adicionar tratamento de erros

**Código de referência:**
```java
public static void playAnimation(AbstractClientPlayer player, ResourceLocation animationId) {
    try {
        AnimationStack animationStack = PlayerAnimationAccess.getPlayerAnimLayer(player);
        ModifierLayer<KeyframeAnimationPlayer> modifierLayer = new ModifierLayer<>();
        
        // Carregar animação (implementar loadAnimationFromResource)
        KeyframeAnimation animation = loadAnimationFromResource(animationId);
        if (animation != null) {
            KeyframeAnimationPlayer animationPlayer = new KeyframeAnimationPlayer(animation);
            modifierLayer.setAnimation(animationPlayer);
            animationStack.addAnimLayer(1000, modifierLayer);
        }
    } catch (Exception e) {
        // Log error
    }
}
```

### 📋 PlayerAnimatorCompat.java - Linha 126 (playAnimationWithDisplay)
```java
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

**Implementação:**
- [ ] **GunDisplayInstance integration:** Conectar com sistema de display de armas
- [ ] **Animation name extraction:** Extrair nome da animação do display
- [ ] **ResourceLocation conversion:** Converter nome para ResourceLocation
- [ ] **Limb swing integration:** Considerar limbSwingAmount na animação

### 📋 PlayerAnimatorCompat.java - Linha 131 (hasThirdPersonAnimation)
```java
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

**Implementação:**
- [ ] **Animation existence check:** Verificar se animação existe no display
- [ ] **Player validation:** Validar que é AbstractClientPlayer
- [ ] **Resource validation:** Validar que recurso de animação existe
- [ ] **Boolean return:** Retornar true/false corretamente

### 📋 PlayerAnimatorCompat.java - Linha 137 (stopAllAnimations)
```java
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

**Implementação:**
- [ ] **Animation stack access:** Acessar stack de animações
- [ ] **Layer removal:** Remover todas as layers de animação TacZ
- [ ] **Layer tracking:** Implementar sistema para rastrear layers adicionadas
- [ ] **Cleanup:** Limpar recursos desnecessários

### 📋 PlayerAnimatorCompat.java - Linha 142 (isAnimationPlaying)
```java
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

**Implementação:**
- [ ] **Animation stack query:** Consultar stack para animações ativas
- [ ] **ID matching:** Comparar ResourceLocation com animações ativas
- [ ] **State verification:** Verificar se animação está realmente rodando
- [ ] **Boolean return:** Retornar estado correto

### 📋 PlayerAnimatorCompat.java - Linha 149 (getCurrentAnimation)
```java
// TODO: Implementar quando as dependências do Player Animator estiverem disponíveis
```

**Implementação:**
- [ ] **Animation stack query:** Consultar animação atual
- [ ] **Object return:** Retornar KeyframeAnimation ou null
- [ ] **Layer identification:** Identificar qual layer está ativa
- [ ] **Null safety:** Tratar casos onde não há animação

### 📋 Animation Loading System (novo)
**Necessário para suportar playAnimation:**
- [ ] **Resource loading:** Implementar loadAnimationFromResource()
- [ ] **Cache system:** Sistema de cache para animações carregadas
- [ ] **Error handling:** Tratamento para animações não encontradas
- [ ] **Format support:** Suporte para formatos de animação do Player Animator

### 📋 Testing
- [ ] **Compilation test:** Projeto compila com todas as implementações
- [ ] **Runtime test:** Animações carregam sem erro
- [ ] **Integration test:** Animações funcionam com armas TacZ
- [ ] **Performance test:** Sem impacto significativo na performance

---

## ⚙️ CHECKLIST FASE 2: Cloth Config

### 📋 MenuIntegration.java - Linha 71 (saveConfigs)
```java
// TODO: Verificar API correta do Cloth Config para 1.21.1
```

**Research necessário:**
- [ ] **API documentation:** Verificar documentação Cloth Config para 1.21.1
- [ ] **Method names:** Identificar método correto para salvar (save(), build(), etc.)
- [ ] **Return types:** Verificar tipo de retorno dos métodos
- [ ] **Examples:** Encontrar exemplos de uso para 1.21.1

**Implementação:**
- [ ] **Correct method call:** Implementar chamada correta
- [ ] **Error handling:** Adicionar tratamento de erros
- [ ] **Callback support:** Implementar callbacks se necessário
- [ ] **Testing:** Testar salvamento de configurações

### 📋 MenuIntegration.java - Linha 90 (hasUnsavedChanges)
```java
// TODO: Verificar API correta do Cloth Config para 1.21.1
```

**Research necessário:**
- [ ] **State tracking:** Como Cloth Config rastreia mudanças
- [ ] **Method names:** isEdited(), isDirty(), hasChanges(), etc.
- [ ] **Alternative approaches:** Sistemas de tracking alternativos

**Implementação:**
- [ ] **State check:** Implementar verificação de estado
- [ ] **Boolean return:** Retornar true/false corretamente
- [ ] **Edge cases:** Tratar casos especiais
- [ ] **Testing:** Testar detecção de mudanças

### 📋 MenuIntegration.java - Linha 12 (Expansão completa)
```java
// TODO: [MIGRAÇÃO] Expandir quando Cloth Config for completamente integrado
```

**Implementação completa:**
- [ ] **Config screen creation:** Implementar createConfigScreen()
- [ ] **Categories:** Criar categorias (General, Guns, Client, etc.)
- [ ] **Options:** Adicionar opções de configuração específicas TacZ
- [ ] **Validation:** Implementar validação de valores
- [ ] **Persistence:** Garantir que configurações persistem
- [ ] **Reset functionality:** Implementar reset para defaults
- [ ] **Import/Export:** Sistema de importar/exportar configurações

---

## 🧩 CHECKLIST FASE 3: KubeJS Integration

### 📋 TimelessClientEvents.java - Linha 6
```java
// TODO: [MIGRAÇÃO] Reimplementar quando KubeJS for completamente migrado para 1.21.1
```

**Research necessário:**
- [ ] **KubeJS 1.21.1 API:** Verificar mudanças na API
- [ ] **EventGroup structure:** Como criar grupos de eventos
- [ ] **Event registration:** Novo sistema de registro
- [ ] **Client/Server separation:** Como separar eventos client/server

**Implementação:**
- [ ] **New imports:** Atualizar imports para nova API
- [ ] **EventGroup creation:** Implementar criação de grupo
- [ ] **Event registration:** Registrar eventos personalizados
- [ ] **Event posting:** Implementar disparo de eventos

### 📋 TimelessClientEvents.java - Linha 12 (postKubeJSEvent)
```java
// TODO: Implementar quando a API do KubeJS estiver estável
```

**Implementação:**
- [ ] **Event wrapper:** Criar wrapper para Event do Forge
- [ ] **ClientEventJS creation:** Instanciar objeto JS corretamente
- [ ] **Event posting:** Usar API correta para postar evento
- [ ] **Error handling:** Tratar erros de scripting

### 📋 TimelessCommonEvents.java (Similar ao Client)
- [ ] **Research:** Mesma pesquisa que client events
- [ ] **Implementation:** Implementação similar mas para common events
- [ ] **Testing:** Testar em contexto server-side

### 📋 TimelessServerEvents.java (Similar ao Client)
- [ ] **Research:** Mesma pesquisa que client events  
- [ ] **Implementation:** Implementação similar mas para server events
- [ ] **Testing:** Testar em contexto server-only

### 📋 Custom TacZ Events (novo)
**Criar eventos específicos para TacZ:**
- [ ] **GunFireEvent:** Evento quando arma dispara
- [ ] **GunReloadEvent:** Evento quando arma recarrega
- [ ] **GunAimEvent:** Evento quando jogador mira
- [ ] **GunCraftEvent:** Evento quando arma é craftada
- [ ] **AttachmentEvent:** Evento quando attachment é modificado

**Para cada evento:**
- [ ] **JS Class:** Criar classe JavaScript correspondente
- [ ] **Forge Integration:** Integrar com eventos Forge do TacZ
- [ ] **Documentation:** Documentar para modpack makers
- [ ] **Examples:** Criar exemplos de uso

---

## 📁 CHECKLIST FASE 4: Arquivos Desabilitados

### 📋 AnimationDataRegisterFactory.java.disabled
```java
// TODO: Re-enable when PlayerAnimatorCompat and AdjustmentYRotModifier are habilitado
```

**Pré-requisitos:**
- [ ] **PlayerAnimatorCompat functional:** Fase 1 completa
- [ ] **Dependencies resolved:** Todas dependências disponíveis

**Implementação:**
- [ ] **Remove .disabled:** Renomear arquivo removendo .disabled
- [ ] **Update imports:** Atualizar imports se necessário
- [ ] **Test compilation:** Verificar se compila
- [ ] **Integration test:** Testar com PlayerAnimatorCompat

### 📋 PlayerAnimatorLoader.java.disabled
**Similar ao anterior:**
- [ ] **Re-enable:** Remover .disabled
- [ ] **API updates:** Atualizar para APIs 1.21.1
- [ ] **Resource loading:** Verificar sistema de carregamento
- [ ] **Testing:** Testar carregamento de animações

### 📋 PlayerAnimatorAssetManager.java.disabled
**Similar ao anterior:**
- [ ] **Re-enable:** Remover .disabled
- [ ] **Asset system:** Verificar compatibilidade com sistema de assets
- [ ] **Resource paths:** Verificar paths de recursos
- [ ] **Testing:** Testar gerenciamento de assets

### 📋 CustomGunItemBuilder.java.disabled - Linha 24
```java
// TODO: Migrar para NeoForge 1.21.1 - DeferredHolder em vez de RegistryObject
```

**Migração específica:**
- [ ] **RegistryObject → DeferredHolder:** Substituir todas as ocorrências
- [ ] **Import updates:** Atualizar imports
- [ ] **Method calls:** Atualizar chamadas de métodos
- [ ] **Type parameters:** Verificar parâmetros de tipo
- [ ] **Testing:** Testar criação de itens customizados

**Exemplo de migração:**
```java
// Antigo
private RegistryObject<Item> item;

// Novo  
private DeferredHolder<Item, ? extends Item> item;
```

---

## 🧪 CHECKLIST DE TESTES

### 📋 Testes por Arquivo

**PlayerAnimatorCompat:**
- [ ] **Init test:** Método init() executa sem erro
- [ ] **Animation test:** Animações carregam e executam
- [ ] **Stop test:** stopAllAnimations() funciona
- [ ] **Query test:** isAnimationPlaying() retorna correto
- [ ] **Integration test:** Funciona com armas TacZ

**MenuIntegration:**
- [ ] **Screen test:** Tela de configuração abre
- [ ] **Save test:** Configurações salvam corretamente  
- [ ] **Load test:** Configurações carregam na inicialização
- [ ] **Change detection:** Detecção de mudanças funciona

**KubeJS Events:**
- [ ] **Event registration:** Eventos se registram sem erro
- [ ] **Event posting:** Eventos são disparados corretamente
- [ ] **Script integration:** Scripts KubeJS podem usar eventos
- [ ] **Custom events:** Eventos TacZ funcionam

**Arquivos Reabilitados:**
- [ ] **Compilation:** Todos compilam sem erro
- [ ] **Functionality:** Funcionalidade original restaurada
- [ ] **Integration:** Integram com sistema principal
- [ ] **No regression:** Não quebram funcionalidade existente

### 📋 Testes de Integração

- [ ] **Full compilation:** Projeto completo compila
- [ ] **Game loading:** Jogo carrega sem crashes
- [ ] **Mod functionality:** Todas as funcionalidades TacZ funcionam
- [ ] **Performance:** Sem degradação de performance significativa
- [ ] **Multiplayer:** Funciona em servidor
- [ ] **Modpack compatibility:** Compatível com outros mods

---

## 📊 TRACKING DE PROGRESSO

### 📋 Por Fase
**Fase 1 - Player Animator: __ / 12 tarefas**
**Fase 2 - Cloth Config: __ / 6 tarefas**  
**Fase 3 - KubeJS: __ / 9 tarefas**
**Fase 4 - Arquivos: __ / 8 tarefas**

### 📋 Por Arquivo
- [ ] **PlayerAnimatorCompat.java:** __ / 7 TODOs
- [ ] **MenuIntegration.java:** __ / 3 TODOs
- [ ] **TimelessClientEvents.java:** __ / 2 TODOs
- [ ] **TimelessCommonEvents.java:** __ / 2 TODOs
- [ ] **TimelessServerEvents.java:** __ / 2 TODOs
- [ ] **AnimationDataRegisterFactory.java:** __ / 1 TODO
- [ ] **CustomGunItemBuilder.java:** __ / 1 TODO

### 📋 Milestones
- [ ] **M1:** Todas dependências resolvidas
- [ ] **M2:** Player Animator completamente funcional
- [ ] **M3:** Cloth Config completamente funcional
- [ ] **M4:** KubeJS completamente funcional
- [ ] **M5:** Todos arquivos reabilitados
- [ ] **M6:** Testes completos passando
- [ ] **M7:** Documentação atualizada

---

**Total de TODOs identificados: ~18**  
**Total de tarefas de implementação: ~35**  
**Progresso atual: 0% (apenas stubs funcionais)**

*Esta checklist deve ser atualizada conforme o progresso da implementação.*
