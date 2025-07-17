# Plano de Refatoração: Sistema de Propriedades e Modificadores de Anexos

## Objetivo
Refatorar e habilitar completamente o sistema de propriedades e modificadores de anexos, garantindo que os anexos possam modificar as estatísticas e o comportamento das armas de forma dinâmica e extensível.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados ao sistema de propriedades e modificadores de anexos:

*   **`com\tacz\guns\api\event\common\AttachmentPropertyEvent.java`**:
    *   `// TODO: Re-habilitar quando AttachmentCacheProperty for habilitado`
    *   `* TODO: Implementação mínima até AttachmentCacheProperty e KubeJSGunEventPoster serem habilitados`
    *   `// TODO: Re-habilitar quando AttachmentCacheProperty for habilitado`
    *   `// TODO: Atualizar construtor quando AttachmentCacheProperty for habilitado`
    *   `// TODO: Re-habilitar quando AttachmentCacheProperty for habilitado`
*   **`com\tacz\guns\api\modifier\IAttachmentModifier.java`**:
    *   `// TODO: Implementar quando JsonProperty estiver completo`
    *   `// TODO: Implementar`
*   **`com\tacz\guns\api\modifier\ParameterizedCache.java`**:
    *   `* Método temporário até AttachmentPropertyManager.functionEval estar disponível`
*   **`com\tacz\guns\entity\shooter\ShooterDataHolder.java`**:
    *   `// TODO: Re-habilitar quando AttachmentCacheProperty for habilitado`
    *   `* TODO: Re-habilitar quando AttachmentCacheProperty for habilitado`
*   **`com\tacz\guns\resource\modifier\AttachmentPropertyManager.java`**:
    *   `* TODO: Expandir funcionalidade quando modificadores customizados estiverem habilitados:`
    *   `// TODO: Implementar quando modificadores estiverem funcionais`
    *   `* TODO: Implementar quando engine LuaJ estiver disponível para scripts customizados`
    *   `// TODO: Implementar quando sistema de eventos estiver funcional`
    *   `* Limpa todos os modificadores (para testes)`
    *   `* TODO: Implementar lógica completa quando modificadores estiverem prontos` (múltiplas ocorrências)
*   **`com\tacz\guns\resource\modifier\custom\DamageModifier.java`**:
    *   `* TODO: Expandir funcionalidade quando dependências estiverem habilitadas:`

## Plano de Ação

### Fase 1: Habilitação de Propriedades e Estrutura Básica
1.  **Habilitar `AttachmentCacheProperty`:** Descomentar e corrigir o código relacionado a `AttachmentCacheProperty` em `AttachmentPropertyEvent.java` e `ShooterDataHolder.java`.
2.  **Implementar `JsonProperty`:** Desenvolver a implementação completa de `JsonProperty` conforme indicado em `IAttachmentModifier.java`.
3.  **Estrutura de Modificadores:** Garantir que a estrutura básica dos modificadores em `IAttachmentModifier.java` e `DamageModifier.java` esteja funcional.

### Fase 2: Funcionalidade e Integração
1.  **Tornar Modificadores Funcionais:** Implementar a lógica completa para que os modificadores em `AttachmentPropertyManager.java` e `DamageModifier.java` possam ser aplicados e funcionem conforme o esperado.
2.  **Integrar LuaJ:** Se aplicável, integrar o motor LuaJ em `AttachmentPropertyManager.java` para permitir scripts customizados para modificadores.
3.  **Sistema de Eventos para Modificadores:** Implementar o sistema de eventos para modificadores em `AttachmentPropertyManager.java` para permitir que os modificadores reajam a eventos do jogo.
4.  **Remover `ParameterizedCache` Temporário:** Remover o método temporário em `ParameterizedCache.java` quando `AttachmentPropertyManager.functionEval` estiver disponível.

### Fase 3: Testes e Validação
1.  **Testes Unitários:** Criar ou atualizar testes unitários para garantir que os modificadores apliquem as alterações corretamente.
2.  **Testes de Integração:** Testar a aplicação de diferentes anexos e modificadores em jogo para verificar se as estatísticas e o comportamento das armas são alterados conforme o esperado.
3.  **Limpeza:** Remover quaisquer comentários TODOs e código obsoleto após a conclusão da refatoração.

## Considerações Adicionais
*   **Extensibilidade:** Projetar o sistema de modificadores de forma que seja fácil adicionar novos tipos de modificadores no futuro.
*   **Balanceamento:** Considerar o balanceamento do jogo ao implementar novos modificadores e suas interações.
