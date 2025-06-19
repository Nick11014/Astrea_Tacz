# Fase 1: Migração do Core - Itens, Blocos e Registros

Esta fase foca em atualizar os sistemas fundamentais do mod, especialmente a forma como os dados são armazenados e os objetos são registrados. A maior mudança da 1.20.1 para a 1.21.1 é a introdução dos **DataComponents** em substituição ao NBT em `ItemStacks`.

## Checklist de Implementação:

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

## Estratégia de Testes (Fase 1):

* **Teste 1: Lançamento do Jogo:** O jogo deve iniciar sem crashes.
* **Teste 2: Verificação de Itens e Blocos:**
    * Entre em um mundo no modo criativo.
    * A aba de criativo do TacZ deve aparecer com todos os itens e blocos.
    * Use o comando `/give` para obter cada arma, munição e acessório. Verifique se eles têm os `DataComponents` padrões corretos usando comandos de debug ou logs.
    * Coloque e quebre todos os blocos do mod (mesa de armeiro, alvo, etc.).
* **Teste 3: Receitas:** Verifique no JEI (quando a compatibilidade for portada) ou através da mesa de armeiro se as receitas são carregadas corretamente.

## Status:
- [ ] Não iniciado
- [x] Em progresso (40% concluído)
- [ ] Concluído
- [ ] Testado

### 📊 Progresso Detalhado:
- ✅ **DataComponents:** 16/16 componentes criados
- ✅ **Dependências:** LuaJ e Apache Commons Math adicionadas
- 🟡 **Migração NBT:** 1/6 classes migradas (AmmoItemDataAccessor)
- ❌ **Sistema de Eventos:** Pendente (~100 erros para resolver)
- ❌ **Testes:** Aguardando resolução de erros de compilação

**Próximo foco:** Resolver erros de APIs Forge → NeoForge para permitir compilação
