# Plano de Migração Final: TacZ para NeoForge 1.21.1

**Última Atualização:** 2025-07-13
**Objetivo:** Eliminar os 755 erros de compilação restantes e restaurar a funcionalidade completa do mod, migrando sistematicamente as APIs quebradas e reabilitando os sistemas desativados.

**Estratégia Principal:** A abordagem será dividida em fases, focando em resolver as dependências de base primeiro (bottom-up). Cada fase desbloqueia a próxima, reduzindo a complexidade e o número de erros de forma controlada. Continuaremos a usar suas excelentes "Object Strategy" e "Placeholder Strategy" como ferramentas temporárias.


## Fase 1: Estabilização do Núcleo e da Rede

**Objetivo:** Resolver os erros mais fundamentais que causam falhas em cascata, focando nos gerenciadores de assets e no sistema de rede completamente refeito do NeoForge.

### 1.1: Reabilitar os Gerenciadores de Assets (`CommonAssetManager` e `ClientAssetManager`)
- **Problema:** Muitos erros são `cannot find symbol` para `ClientAssetManager` e `CommonAssetManager`. A `TimelessAPI` depende diretamente deles.
- **Ação:**
    1.  Renomeie `ClientAssetsManager.java.disabled` para `ClientAssetsManager.java` (se aplicável).
    2.  Resolva os `import` quebrados dentro desses arquivos. As APIs de `scripting` e `gltf` podem permanecer comentadas por enquanto.
    3.  Garanta que os métodos `getInstance()` e os mapas de índices (`GUN_INDEX`, etc.) estejam disponíveis, mesmo que os métodos de carregamento (`loadGunIndex()`) ainda tenham implementações mínimas.
- **Resultado Esperado:** Os erros em `TimelessAPI` e em outras classes que dependem dos singletons dos gerenciadores serão resolvidos.

### 1.2: Migrar o Sistema de Rede (Network API)
- **Problema:** O sistema de rede do NeoForge 1.21.1 é totalmente novo e baseado em `CustomPacketPayload`. Todas as classes de mensagem (`ClientMessage...`, `ServerMessage...`) e o `NetworkHandler` estão quebrados.
- **Ação:**
    1.  **Desabilitar `NetworkHandler.java` temporariamente.** Crie um novo `NetworkManager.java` (ou similar) para a nova API.
    2.  **Migrar uma Mensagem Simples:** Escolha `ClientMessagePlayerAim` como piloto.
        -   Faça-a implementar `CustomPacketPayload`.
        -   Implemente o `StreamCodec` para serialização/desserialização.
        -   Crie um método `handle` para a lógica do pacote.
    3.  **Registrar o Pacote:** No novo `NetworkManager`, use `event.registrar(MOD_ID).versioned(VERSION)` e o método `.register()` para registrar sua mensagem e seu handler.
    4.  **Aplicar o Padrão:** Após migrar uma mensagem com sucesso, aplique o mesmo padrão para todas as outras classes de `record` de mensagem.

**Exemplo de Migração de Pacote:**

**Antes (Quebrado):**
```java
public record ClientMessagePlayerAim(boolean isAim) implements IMessage {
    // ... lógica antiga com encode/decode/handle
}
```

**Depois (NeoForge 1.21.1):**
```java
public record ClientMessagePlayerAim(boolean isAim) implements CustomPacketPayload {
    public static final StreamCodec<ByteBuf, ClientMessagePlayerAim> STREAM_CODEC = StreamCodec.of(
            ClientMessagePlayerAim::encode, ClientMessagePlayerAim::new);
    public static final ResourceLocation ID = new ResourceLocation(GunMod.MOD_ID, "client_player_aim");

    public ClientMessagePlayerAim(ByteBuf buf) {
        this(buf.readBoolean());
    }

    public void encode(ByteBuf buf) {
        buf.writeBoolean(this.isAim);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    // O handler será registrado separadamente no seu NetworkManager
    public static void handle(ClientMessagePlayerAim message, IPayloadContext context) {
        context.enqueueWork(() -> {
            // Lógica do pacote aqui
            Player player = context.player();
            // ...
        });
    }
}
```

  - **Resultado Esperado:** Eliminação massiva de erros relacionados a `NetworkHandler`, `IPayloadRegistrar`, e todas as classes de mensagem.

### 1.3: Migrar o Carregador de Packs (`GunPackLoader`)

  - **Problema:** As APIs de descoberta de mods e recursos (`IModInfo`, `IModFile`, `DelegatingPackResources`, `PathPackResources`) mudaram.
  - **Ação:**
    1.  Pesquise a nova API de `PackRepository` e `PackSource` do NeoForge.
    2.  Reimplemente a lógica de descoberta de packs em `GunPackLoader`. O foco será em como registrar diretórios e arquivos zip como fontes de recursos válidas.
    3.  Utilize `Pack.readMetaAndCreate` e fornecedores de recursos (`ResourcesSupplier`).
  - **Resultado Esperado:** O sistema de data packs e resource packs customizados voltará a funcionar.

-----

## Fase 2: Correção de APIs de Gameplay e Bloco

**Objetivo:** Corrigir as mudanças de API mais comuns que afetam blocos, entidades e itens em todo o código.

### 2.1: Migrar Blocos e BlockEntities

  - **Problema:** Assinaturas de métodos como `getCloneItemStack`, `saveAdditional`, e a necessidade de um `MapCodec` (`codec()`) estão causando erros.
  - **Ação:**
    1.  **Para cada Bloco:** Implemente o método `protected MapCodec<? extends BaseEntityBlock> codec()` obrigatório.
    2.  **`getCloneItemStack`:** Altere a assinatura de `(BlockGetter, ...)` para `(LevelReader, ...)`.
    3.  **`saveAdditional`:** Altere a assinatura para `protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries)`.
    4.  **`load`:** Altere a assinatura para `public void load(CompoundTag pTag, HolderLookup.Provider pRegistries)`.
    5.  **`ItemStack.parseOptional`:** Substitua pela nova forma de carregar `ItemStack` de NBT, que agora usa `ItemStack.parse(pRegistries, tag)`. Note que ele lança uma exceção em vez de retornar `Optional`.
  - **Resultado Esperado:** Correção de erros em `AbstractGunSmithTableBlock`, `TargetBlock`, `StatueBlock` e suas entidades.

### 2.2: Migrar Sistema de Crafting (`GunSmithTableRecipe`)

  - **Problema:** A interface `Recipe<T>` agora tem `T` limitado a `RecipeInput`. `Inventory` não é mais um `RecipeInput` válido. O `CraftingHelper` foi alterado.
  - **Ação:**
    1.  **Use `Container`:** A interface `Inventory` foi em grande parte substituída por `Container` em contextos de receita. Altere `Recipe<Inventory>` para `Recipe<Container>`.
    2.  **Migrar Serializer:** A interface `RecipeSerializer` agora requer um `StreamCodec`. Implemente-o para suas receitas.
    3.  **`CraftingHelper`:** Substitua o uso do `CraftingHelper` para obter `ItemStack` de JSON. Utilize `JsonOps.INSTANCE` e `ItemStack.CODEC` para serializar/desserializar.
  - **Resultado Esperado:** O sistema de receitas customizadas voltará a compilar, permitindo a reabilitação de `GunSmithTableSerializer` e `GunSmithTableRecipe`.

### 2.3: Resolver Incompatibilidades de Tipos Genéricos

  - **Problema:** Erros como `incompatible types: Comparator<Entry<ResourceLocation,Object>> cannot be converted to Comparator<? super Entry<ResourceLocation,CommonGunIndex>>`.
  - **Ação:**
    1.  **`AbstractGunItem.java`:** O problema do `Comparator` ocorre porque `TimelessAPI.getAllCommonGunIndex()` está retornando um `Set<Entry<ResourceLocation, Object>>` devido à "Object Strategy". Filtre e mapeie a stream antes de ordenar:
        
        **Depois (Corrigido):**
        ```java
        TimelessAPI.getAllCommonGunIndex().stream()
            .filter(entry -> entry.getValue() instanceof CommonGunIndex)
            .map(entry -> (Entry<ResourceLocation, CommonGunIndex>) (Object) entry) // Cast seguro após filtro
            .sorted(idNameSort())
            .forEach(entry -> { /* ... */ });
        ```
    2.  **AnimationController:** Os erros de `Object` para `PlayType` e `AccessorModel` indicam que a "Object Strategy" está vazando. A solução é a mesma: aplicar `instanceof` e `cast` ou, idealmente, corrigir a fonte do `Object` (Fase 3).
  - **Resultado Esperado:** Resolução de erros de tipo, tornando o código mais seguro e robusto.


## Fase 3: Restauração da Camada de Renderização e Lógica do Cliente

**Objetivo:** Habilitar os sistemas de renderização desativados, aproveitando o "breakthrough" da API `VertexConsumer` que você já descobriu.

### 3.1: Habilitar o Sistema de Modelos Bedrock

  - **Problema:** `BedrockModel`, `BedrockGunModel`, `BedrockAttachmentModel` e seus renderizadores estão desabilitados ou com implementações mínimas.
  - **Ação:**
    1.  **Habilite `BedrockModel.java` e seus dependentes.** Você já migrou `BedrockCubeBox` e `BedrockCubePerFace`, então isso deve ser possível agora.
    2.  **Habilite `BedrockGunModel.java` e `BedrockAttachmentModel.java`.** Comece com implementações mínimas, comentando a lógica que depende de outros sistemas ainda não habilitados (como `ShellRender`).
    3.  **Habilite os Renderizadores:** `AttachmentItemRenderer`, `GunItemRendererWrapper`, etc. Aplique a "Object Strategy" para as dependências que ainda não estão prontas (ex: `ClientAttachmentIndex` pode retornar `Object` para `getAttachmentModel()`).

### 3.2: Restaurar `GunDisplayInstance` e `GunAnimationStateContext`

  - **Problema:** Esses arquivos são complexos e agregam muitas funcionalidades do cliente que estavam quebradas.
  - **Ação:**
    1.  Habilite `GunDisplayInstance.java`. Resolva os erros de import um por um. A migração da API `ParticleArgument` será necessária (agora usa `HolderLookup.Provider`).
    2.  Habilite `GunAnimationStateContext.java`. Conecte-o ao `GunDisplayInstance` já funcional.
  - **Resultado Esperado:** A lógica principal do cliente para animação e exibição de armas estará de volta, permitindo testes visuais.

## Fase 4: Finalização e Limpeza

**Objetivo:** Habilitar os últimos sistemas, remover as estratégias de workaround e limpar o código.

### 4.1: Habilitar os Modificadores Específicos

  - **Problema:** As classes de modificadores (`AdsModifier`, `DamageModifier`, etc.) estão desabilitadas.
  - **Ação:**
    1.  Habilite uma classe de modificador por vez.
    2.  Resolva os erros de `eval`. Os métodos em `AttachmentPropertyManager` para avaliar modificadores precisarão ser implementados completamente, substituindo os placeholders.
  - **Resultado Esperado:** O sistema de acessórios e estatísticas dinâmicas estará 100% funcional.

### 4.2: Remover "Object Strategy" e Placeholders

  - **Problema:** O código ainda contém workarounds que reduzem a segurança de tipo.
  - **Ação:**
    1.  Busque todas as ocorrências de `instanceof` seguido de `cast` que foram adicionadas como parte da estratégia.
    2.  Corrija os métodos de origem (ex: em `TimelessAPI`) para que retornem o tipo correto em vez de `Object`.
    3.  Substitua todos os `// TODO: [MIGRAÇÃO ...]` e placeholders com a implementação final.
  - **Resultado Esperado:** Código limpo, seguro e totalmente migrado.

### 4.3: Revisão Final

  - **Ação:**
    1.  Compile o projeto uma última vez.
    2.  Execute o jogo e teste todas as funcionalidades: tiro, recarga, acessórios, crafting, mira, efeitos visuais e sonoros.
    3.  Remova o arquivo `DEBITO_TECNICO.md` ou arquive-o como um registro histórico da migração bem-sucedida.
