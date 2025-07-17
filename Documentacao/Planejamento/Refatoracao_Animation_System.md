# Plano de Refatoração: Sistema de Animação

## Objetivo
Refatorar e reativar o sistema de animação, incluindo o suporte a animações GLTF e Bedrock, garantindo que as animações sejam carregadas, processadas e reproduzidas corretamente no jogo.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados ao sistema de animação:

*   `com\tacz\guns\api\client\animation\AnimationPlan.java`:
    *   `// TODO: Re-enable when ObjectAnimation is habilitado`
*   `com\tacz\guns\api\client\animation\gltf\AnimationModel.java`:
    *   `Object input, // TODO: Re-enable AccessorModel when AccessorModel is habilitado`
    *   `Object output) { // TODO: Re-enable AccessorModel when AccessorModel is habilitado`
*   `com\tacz\guns\api\client\animation\interpolator\Spline.java`:
    *   `// TODO` (múltiplas ocorrências)
*   `com\tacz\guns\api\client\animation\statemachine\AnimationConstant.java`:
    *   `// todo`
*   `com\tacz\guns\api\client\animation\statemachine\AnimationState.java`:
    *   `// TODO: Re-enable when AnimationStateContext and AnimationStateMachine are habilitado`
*   `com\tacz\guns\client\resource\ClientAssetsManager.java`:
    *   `gltfAnimation = register(new GltfManager());`
    *   `bedrockAnimation = register(new JsonDataManager<>(BedrockAnimationFile.class, GSON, new FileToIdConverter("animations", ".animation.json"), "BedrockAnimationLoader"));`
    *   `public Object getGltfAnimation(ResourceLocation id)` (comentado)
*   `com\tacz\guns\client\resource\manager\GltfManager.java`:
    *   `* TODO: Sistema de animação GLTF desabilitado temporariamente`
*   `com\tacz\guns\client\model\BedrockGunModel.java`:
    *   `// this.setFunctionalRenderer(LEFTHAND_POS_NODE, bedrockPart -> new LeftHandRender(this)); // TODO: Habilitar quando LeftHandRender estiver funcionando`
    *   `// this.setFunctionalRenderer(RIGHTHAND_POS_NODE, bedrockPart -> new RightHandRender(this)); // TODO: Habilitar quando RightHandRender estiver funcionando`
*   `com\tacz\guns\client\animation\screen\RefitTransform.java`:
    *   `// TODO: Re-enable when GunRefitScreen is available`
    *   `// TODO: Migrate to new tick event API in NeoForge 1.21.1`
    *   `// TODO: Re-enable event subscription when tick events are migrated`
    *   `// TODO: Re-enable when tick events are migrated and GunRefitScreen is available`

## Plano de Ação

### Fase 1: Reativação de Componentes Essenciais
1.  **Habilitar `ObjectAnimation` e `AccessorModel`:** Descomentar e corrigir o código relacionado a `ObjectAnimation` e `AccessorModel` em `AnimationPlan.java` e `AnimationModel.java`.
2.  **Resolver TODOs em `Spline.java`:** Analisar e implementar a lógica pendente na classe `Spline` para garantir o correto funcionamento da interpolação.
3.  **Habilitar `AnimationStateContext` e `AnimationStateMachine`:** Descomentar e corrigir o código relacionado a essas classes em `AnimationState.java`.

### Fase 2: Carregamento e Processamento de Animações
1.  **Implementar Carregamento GLTF:**
    *   Reativar e implementar a lógica de carregamento de animações GLTF em `GltfManager.java`.
    *   Garantir que `ClientAssetsManager.getGltfAnimation()` retorne os objetos de animação GLTF corretamente.
2.  **Garantir Animações Bedrock:** Verificar e, se necessário, refatorar o carregamento e processamento de animações Bedrock via `BedrockAnimationFile` em `ClientAssetsManager.java`.

### Fase 3: Integração e Renderização
1.  **Integrar Renderizadores Funcionais:** Habilitar e corrigir `LeftHandRender` e `RightHandRender` em `BedrockGunModel.java` para garantir que as animações sejam aplicadas corretamente aos modelos.
2.  **Migrar Eventos de Tick:** Em `RefitTransform.java`, migrar para a nova API de eventos de tick do NeoForge 1.21.1 e reativar a inscrição de eventos.
3.  **Habilitar `GunRefitScreen`:** Reativar e integrar `GunRefitScreen` em `RefitTransform.java`.

### Fase 4: Testes e Validação
1.  **Testes Unitários:** Criar ou atualizar testes unitários para as classes de animação.
2.  **Testes em Jogo:** Verificar a reprodução de todas as animações (armas, recargas, inspeções, etc.) no jogo.
3.  **Otimização:** Avaliar o desempenho do sistema de animação e realizar otimizações, se necessário.

## Considerações Adicionais
*   **Ferramentas de Animação:** Considerar a utilização de ferramentas externas para auxiliar na criação e depuração de animações GLTF e Bedrock.
*   **Documentação:** Atualizar a documentação sobre como adicionar e configurar novas animações no mod.
