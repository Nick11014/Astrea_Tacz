Aqui está um plano de desenvolvimento detalhado, dividido em fases, com checklists e estratégias de teste para cada etapa.

***

## Plano de Desenvolvimento: Portabilidade do TacZ para NeoForge 1.21.1

### Fase 0: Configuração do Ambiente e Estrutura do Projeto

O objetivo desta fase é fazer o projeto compilar no novo ambiente, sem se preocupar com a lógica do jogo ainda. Isso isola problemas de build dos problemas de código.

**Checklist de Implementação:**

* **[ ] Atualizar o `build.gradle`:**
    * Alterar o `minecraft_version` para `1.21.1`.
    * Alterar a dependência do modloader de `forge` para `neoforge`. Você precisará encontrar a versão recomendada do NeoForge para a 1.21.1.
    * Atualizar o `mappings_channel` e `mappings_version` para os correspondentes oficiais do NeoForge 1.21.1 (ex: `official`).
    * Revisar e atualizar as dependências de outras bibliotecas (como JEI, KubeJS, Cloth Config) para suas versões compatíveis com NeoForge 1.21.1.
* **[ ] Atualizar o `mods.toml`:**
    * Localizado em `src/main/resources/META-INF/mods.toml`.
    * Alterar a versão do loader para `neoforge`.
    * Especificar a versão do Minecraft como `1.21.1`.
    * Atualizar as declarações de dependência para refletir as novas versões e IDs de mods, se necessário.
* **[ ] Atualizar `pack.mcmeta`:**
    * Ajustar o `pack_format` para a versão do Minecraft 1.21.1.
* **[ ] Regenerar o Projeto:**
    * Execute os comandos do Gradle para limpar e reconfigurar o ambiente: `gradlew clean` e `gradlew genSources`.
    * Importe o projeto atualizado na sua IDE. Haverá muitos erros de compilação, o que é esperado.

**Estratégia de Testes (Fase 0):**

* O único teste aqui é a saúde do ambiente de desenvolvimento.
* **Critério de Sucesso:** O projeto é importado corretamente na IDE, as dependências do NeoForge e do Minecraft 1.21.1 são baixadas e o processo de build (`gradlew build`) começa, mesmo que falhe devido a erros de código. Não deve haver erros relacionados à configuração do Gradle em si.

---

### Fase 1: Migração do Core - Itens, Blocos e Registros

Esta fase foca em atualizar os sistemas fundamentais do mod, especialmente a forma como os dados são armazenados e os objetos são registrados. A maior mudança da 1.20.1 para a 1.21.1 é a introdução dos **DataComponents** em substituição ao NBT em `ItemStacks`.

**Checklist de Implementação:**

* **[ ] Migração para DataComponents (Prioridade Alta):**
    * Identifique todas as classes que manipulam NBT de `ItemStack`, principalmente no pacote `com.tacz.guns.api.item.nbt` (`GunItemDataAccessor`, `AmmoItemDataAccessor`, `AttachmentItemDataAccessor`).
    * Para cada dado customizado (ex: `currentAmmo`, `fireMode`, `attachments`), crie um `DataComponent` correspondente.
    * Registre seus novos `DataComponent`s.
    * Refatore todo o código que usa `itemStack.getOrCreateTag()`, `itemStack.getTag().put(...)`, etc., para usar os novos `DataComponent`s (`itemStack.set(MyComponents.MY_DATA, value)` e `itemStack.get(MyComponents.MY_DATA)`). Isso afetará grande parte da lógica de tiro, recarga e modificação de armas.
* **[ ] Atualizar Registros (Registries):**
    * Revise todas as classes no pacote `com.tacz.guns.init` (`ModItems`, `ModBlocks`, `ModEntities`, `ModSounds`, `ModRecipe`, etc.).
    * O NeoForge utiliza `DeferredRegister` de forma similar ao Forge, mas verifique se a API do evento de registro (`RegisterEvent`) mudou ou se há novas práticas recomendadas.
* **[ ] Atualizar Creative Tabs:**
    * A classe `ModCreativeTabs` precisará ser adaptada. A API `CreativeModeTabs` e a forma de registrar e popular abas customizadas foram alteradas no NeoForge 1.21.
* **[ ] Revisar Receitas:**
    * Verifique as classes `GunSmithTableRecipe` e seu `Serializer`. As APIs de receitas e seus JSONs podem ter sofrido alterações. Valide se os tipos de receita customizados ainda funcionam como esperado.
* **[ ] Atualizar Tipos de Dano (`DamageTypes`):**
    * Revise o registro e o uso em `ModDamageTypes`. Verifique se os JSONs em `data/tacz/damage_type` estão corretos para a nova versão.

**Estratégia de Testes (Fase 1):**

* **Teste 1: Lançamento do Jogo:** O jogo deve iniciar sem crashes.
* **Teste 2: Verificação de Itens e Blocos:**
    * Entre em um mundo no modo criativo.
    * A aba de criativo do TacZ deve aparecer com todos os itens e blocos.
    * Use o comando `/give` para obter cada arma, munição e acessório. Verifique se eles têm os `DataComponents` padrões corretos usando comandos de debug ou logs.
    * Coloque e quebre todos os blocos do mod (mesa de armeiro, alvo, etc.).
* **Teste 3: Receitas:** Verifique no JEI (quando a compatibilidade for portada) ou através da mesa de armeiro se as receitas são carregadas corretamente.

---

### Fase 2: Sistemas do Lado do Cliente - Renderização, GUI e Animações

Esta é a parte mais complexa e de maior risco do seu mod. As APIs de renderização costumam mudar entre versões.

**Checklist de Implementação:**

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

**Estratégia de Testes (Fase 2):**

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

---

### Fase 3: Lógica de Gameplay e Rede

Esta fase garante que o mod funcione como deveria, especialmente em um ambiente multiplayer.

**Checklist de Implementação:**

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

**Estratégia de Testes (Fase 3):**

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

---

### Fase 4: Módulos de Compatibilidade

Depois que o mod principal estiver estável, porte as integrações.

**Checklist de Implementação:**

* **[ ] Portar JEI:**
    * Atualize a dependência do JEI.
    * Revise a classe `GunModPlugin` e as categorias customizadas. A API do JEI provavelmente mudou.
* **[ ] Portar KubeJS:**
    * Atualize a dependência.
    * A classe `TimelessKubeJSPlugin` e os `events` customizados precisarão de validação e possíveis ajustes para a nova API do KubeJS para NeoForge 1.21.
* **[ ] Portar PlayerAnimator:**
    * Atualize a dependência.
    * Verifique as classes em `com.tacz.guns.compat.playeranimator.animation`. A forma como as animações são registradas e aplicadas ao modelo do jogador pode ter mudado.
* **[ ] Outras Compatibilidades (Cloth Config, Oculus, etc.):**
    * Siga o mesmo processo: atualizar dependência e revisar o código de integração.

**Estratégia de Testes (Fase 4):**

* Ative um mod de compatibilidade por vez.
* **JEI:** Verifique se as receitas da mesa de armeiro aparecem corretamente.
* **KubeJS:** Crie scripts de teste para verificar se os eventos e builders customizados do TacZ estão funcionando.
* **PlayerAnimator:** Em terceira pessoa, verifique se as animações customizadas de segurar a arma, mirar e recarregar estão sendo aplicadas ao modelo do jogador.

---

### Fase 5: Finalização e Polimento

A última etapa é garantir a qualidade e estabilidade do mod.

**Checklist de Implementação:**

* **[ ] Limpeza de Código:** Remova todas as referências a APIs antigas e métodos obsoletos (`@Deprecated`).
* **[ ] Revisão de Logs:** Inicie o jogo e jogue por um tempo. Verifique o console em busca de quaisquer mensagens de `ERROR` ou `WARN` geradas pelo seu mod.
* **[ ] Teste de Regressão Completo:** Execute todos os testes das fases anteriores novamente para garantir que as últimas mudanças não quebraram nada.
* **[ ] Empacotamento:** Gere o arquivo JAR final para distribuição.

**Estratégia de Testes (Fase 5):**

* Jogue o mod como um usuário final faria, em single-player e multiplayer.
* Tente "quebrar" o mod: troque de armas rapidamente, recarregue enquanto faz outra ação, etc.
* Considere liberar uma versão "alfa" ou "beta" para que a comunidade possa ajudar a encontrar bugs que você não encontrou.

Boa sorte com a portabilidade! É um trabalho grande, mas com um plano claro e metódico, você conseguirá fazer a transição com sucesso.