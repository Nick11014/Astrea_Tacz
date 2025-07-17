# Plano de Refatoração: Integração com KubeJS

## Objetivo
Reativar e integrar adequadamente a funcionalidade KubeJS no projeto, garantindo que os scripts KubeJS possam interagir com o mod conforme o esperado.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados à integração com KubeJS:

*   `com\tacz\guns\api\client\event\BeforeRenderHandEvent.java`:
    *   `// TODO: Re-add KubeJS integration when dependencies are available`
*   `com\tacz\guns\api\client\event\RenderItemInHandBobEvent.java`:
    *   `// TODO: Re-add KubeJS integration when dependencies are available`
*   `com\tacz\guns\api\client\event\RenderLevelBobEvent.java`:
    *   `// TODO: Re-add KubeJS integration when dependencies are available`
*   `com\tacz\guns\api\client\event\SwapItemWithOffHand.java`:
    *   `// TODO: Re-add KubeJS integration when dependencies are available`
*   `com\tacz\guns\api\event\common\EntityKillByGunEvent.java`:
    *   `* TODO: Implementação mínima até KubeJSGunEventPoster ser habilitado`
    *   `// TODO: Re-habilitar quando KubeJSGunEventPoster for habilitado`
*   `com\tacz\guns\api\event\common\KubeJSGunEventPoster.java`:
    *   `// TODO: Re-enable when KubeJS integration classes are available`
    *   `// TODO: Re-enable when KubeJS integration is available` (múltiplas ocorrências)
*   `com\tacz\guns\client\resource\ClientAssetsManager.java`:
    *   `// scriptManager = register(new ScriptManager(new FileToIdConverter("scripts", ".lua"), libList));` (comentado)
    *   `// return scriptManager.getScript(id);` (comentado)

## Plano de Ação

### Fase 1: Análise e Habilitação Básica
1.  **Verificar Dependências KubeJS:** Confirmar se as bibliotecas KubeJS estão corretamente configuradas no `build.gradle` e disponíveis no ambiente de desenvolvimento.
2.  **Descomentar Código KubeJS:** Descomentar as linhas relacionadas a `ScriptManager` em `ClientAssetsManager.java` e outras classes, se aplicável.
3.  **Identificar APIs KubeJS Ausentes:** Analisar os erros de compilação e referências ausentes após descomentar o código para identificar quais APIs ou classes KubeJS específicas precisam ser adaptadas ou substituídas.

### Fase 2: Adaptação e Implementação
1.  **Atualizar `KubeJSGunEventPoster`:** Implementar a lógica necessária para que os eventos do mod sejam corretamente postados para o KubeJS.
2.  **Refatorar Eventos do Cliente:** Ajustar `BeforeRenderHandEvent`, `RenderItemInHandBobEvent`, `RenderLevelBobEvent` e `SwapItemWithOffHand` para garantir que a integração com KubeJS funcione conforme o esperado.
3.  **Testar `ScriptManager`:** Verificar se o `ScriptManager` consegue carregar e executar scripts Lua/KubeJS corretamente.

### Fase 3: Testes e Validação
1.  **Testes de Funcionalidade:** Criar ou utilizar scripts KubeJS de teste para verificar se os eventos são disparados e as funcionalidades são executadas corretamente.
2.  **Testes de Integração:** Garantir que a integração com KubeJS não cause conflitos ou problemas com outras partes do mod.
3.  **Documentação:** Atualizar a documentação sobre como os desenvolvedores de scripts KubeJS podem interagir com o mod.

## Considerações Adicionais
*   **Versão do KubeJS:** Confirmar a versão do KubeJS com a qual o mod deve ser compatível e ajustar o código de acordo.
*   **APIs em Mudança:** Estar ciente de que as APIs do KubeJS podem mudar entre as versões do Minecraft, exigindo manutenção contínua.
