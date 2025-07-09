# Fase 3: Lógica de Gameplay e Rede

Esta fase garante que o mod funcione como deveria, especialmente em um ambiente multiplayer.

## Checklist de Implementação:

* **[ ] Atualizar Sistema de Rede:**
    * O NeoForge tem seu próprio sistema de rede. O pacote `com.tacz.guns.network.message` precisará de uma grande refatoração.
    * Adapte a classe `NetworkHandler` para registrar os pacotes da maneira do NeoForge.
    * Adapte cada classe de mensagem (`ClientMessagePlayerShoot`, `ServerMessageSound`, etc.) para a nova estrutura de pacotes.

* **[ ] Lógica do Jogador (`LivingEntity` e `Player`):**
    * Revise os "mixins" (`LivingEntityMixin`, `LocalPlayerMixin`, `ServerPlayerMixin`). Os nomes dos métodos que você está injetando ("shadow" e "target") podem ter mudado.
    * Verifique a lógica de mira, recarga, disparo e movimento nos pacotes `com.tacz.guns.client.gameplay` e `com.tacz.guns.entity.shooter`.

* **[ ] Sistema de Atributos:**
    * O mod usa `ModAttributes`. Verifique se a forma de registrar e aplicar `AttributeModifier`s permanece a mesma.

* **[ ] Configuração:**
    * Verifique se a API de configuração do Forge/NeoForge (`ForgeConfigSpec`) não teve mudanças significativas que quebrem as classes em `com.tacz.guns.config`.

## Estratégia de Testes (Fase 3):

* **Teste 1: Single-player Funcional:**
    * Atirar consome munição e causa dano?
    * Recarregar funciona e restaura a munição?
    * Mirar afeta a precisão?
    * Acessórios aplicam corretamente seus modificadores de status?
    * O recuo (recoil) e a dispersão (spread) estão funcionando?

* **Teste 2: Servidor Dedicado (Multiplayer):**
    * **Sincronização de Animação:** Um jogador consegue ver outro jogador mirando, atirando e recarregando corretamente?
    * **Sincronização de Dano:** Atirar em outro jogador causa dano e exibe a animação de hit?
    * **Sincronização de Som:** Os sons de tiro e recarga são ouvidos por todos os jogadores próximos?
    * **Consistência de Estado:** Não deve haver dessincronização de munição (ex: o cliente acha que tem bala, mas o servidor não deixa atirar).
    * Teste todas as interações de rede, como a modificação de armas.

## Status:
- [ ] Não iniciado
- [ ] Em progresso  
- [ ] Concluído
- [ ] Testado
