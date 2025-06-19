# Fase 2: Sistemas do Lado do Cliente - Renderização, GUI e Animações

Esta é a parte mais complexa e de maior risco do seu mod. As APIs de renderização costumam mudar entre versões.

## Checklist de Implementação:

* **[ ] Atualizar Renderizadores de Itens (IClientItemExtensions/BER):**
    * A classe `GunItemRendererWrapper` e outras que usam `BlockEntityWithoutLevelRenderer` precisarão ser revisadas. As assinaturas de métodos e a forma como o `PoseStack` e `MultiBufferSource` são usados podem ter mudado.
    * Preste atenção especial em como as animações (do sistema de animação GLTF/Bedrock) são aplicadas ao `PoseStack`.

* **[ ] Atualizar Renderizadores de Entidades de Bloco (BER):**
    * Revise `GunSmithTableRenderer`, `StatueRenderer` e `TargetRenderer`.

* **[ ] Atualizar Renderizadores de Entidades:**
    * Revise `EntityBulletRenderer`.

* **[ ] Atualizar Telas de GUI (`Screen`):**
    * Classes como `GunRefitScreen` e `GunSmithTableScreen` são pontos críticos. Verifique construtores, métodos de renderização (`render`, `renderBackground`), e a manipulação de widgets.

* **[ ] Atualizar Overlays (HUD):**
    * O sistema de overlays do Forge foi significativamente alterado e o NeoForge seguiu um caminho parecido. A classe `GunHudOverlay` precisará ser portada para a nova API `IForgeGui`.

* **[ ] Revisar Sistema de Animação:**
    * O núcleo do seu sistema de animação em `com.tacz.guns.api.client.animation` é provavelmente agnóstico à versão do Minecraft.
    * O ponto de falha será a *ponte* entre seu sistema e a renderização do jogo. Verifique as classes `FirstPersonRenderGunEvent` e como os listeners (`ModelRotateListener`, etc.) interagem com o `PoseStack`.

* **[ ] Mapeamento de Teclas (`KeyMapping`):**
    * Verifique o registro de `KeyMapping` em `ClientSetupEvent` e as classes no pacote `com.tacz.guns.client.input`.

## Estratégia de Testes (Fase 2):

* **Teste 1: Renderização de Itens:**
    * Segure cada arma na mão, em primeira e terceira pessoa. Elas devem renderizar corretamente.
    * Verifique os itens no inventário, na hotbar e como itens dropados no chão.
    * Adicione e remova acessórios e veja se o modelo da arma é atualizado corretamente em tempo real.

* **Teste 2: Animações:**
    * Execute todas as animações: inspecionar, recarregar (vazio e tático), mirar, atirar, puxar o ferrolho. Verifique se as animações da arma (view model) e do jogador (third-person) estão funcionando.

* **Teste 3: GUI e HUD:**
    * Abra a mesa de armeiro e a tela de modificação. Todos os botões, slots e diagramas devem ser funcionais e renderizar corretamente.
    * O HUD de munição e outros indicadores devem aparecer e funcionar como esperado durante o jogo.

* **Teste 4: Efeitos Visuais:** Verifique partículas de fumaça do cano, ejeção de cápsulas e buracos de bala.

## Status:
- [ ] Não iniciado
- [ ] Em progresso  
- [ ] Concluído
- [ ] Testado
