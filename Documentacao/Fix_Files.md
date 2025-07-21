# Guia de Correção de Erros - Migração TacZ NeoForge 1.21.1

Este documento fornece um plano de ação passo a passo para corrigir os 355 erros de compilação restantes nos arquivos do projeto. Siga a ordem das fases para maximizar a eficiência, já que a correção de erros fundamentais resolverá dezenas de outros problemas em cascata.

-----

## Fase 1: Correções em Massa (Programáticas e Sistemáticas)

Estes são erros com um padrão claro que afetam múltiplos arquivos.

### 1.1 - Erro: Mensagens de Rede (Payloads) não implementam o método `type()`

**Problema:** Todas as classes que implementam `CustomPacketPayload` precisam de um método `type()` que retorne o `ResourceLocation` do payload. A ausência deste método causa erros de "is not abstract and does not override abstract method".

**Solução:** Executar um script para adicionar o método `type()` em todas as classes de mensagem de rede.

**Instruções do Script (Conceitual):**
Para cada arquivo na lista abaixo, adicione o seguinte método dentro do corpo da classe (geralmente um `record`):

```java
@Override
public ResourceLocation type() {
    return TYPE;
}
```

*(Certifique-se de que a constante `TYPE` já existe na classe. Se não, ela deve ser adicionada: `public static final ResourceLocation TYPE = new ResourceLocation(GunMod.MOD_ID, "nome_do_payload");`)*

**Arquivos para Corrigir:**

  - [ ] `network/message/ClientMessageCraft.java`
  - [ ] `network/message/ClientMessageLaserColor.java`
  - [ ] `network/message/ClientMessagePlayerAim.java`
  - [ ] `network/message/ClientMessagePlayerCrawl.java`
  - [ ] `network/message/ClientMessagePlayerShoot.java`
  - [ ] `network/message/ClientMessageRefitGun.java`
  - [ ] `network/message/ClientMessageSyncBaseTimestamp.java`
  - [ ] `network/message/ClientMessageUnloadAttachment.java`
  - [ ] `network/message/event/ServerMessageGunFireSelect.java`
  - [ ] `network/message/event/ServerMessageGunHurt.java`
  - [ ] `network/message/event/ServerMessageGunKill.java`
  - [ ] `network/message/event/ServerMessageGunMelee.java`
  - [ ] `network/message/handshake/ServerMessageSyncedEntityDataMapping.java`
  - [ ] `network/message/ServerMessageSound.java`
  - [ ] `network/message/ServerMessageSyncGunPack.java`
  - [ ] `network/message/ServerMessageUpdateEntityData.java`

-----

### 1.2 - Erro: Assinaturas de Métodos `@Override` Incorretas nos Modificadores

**Problema:** As interfaces que os modificadores de atributos implementam mudaram. Métodos que antes eram válidos agora não correspondem mais à assinatura da interface-mãe, causando erros de "method does not override".

**Solução:** Aplicar uma ação de "Localizar e Substituir com Regex" no seu editor de código para atualizar as assinaturas dos métodos afetados.

**Instruções de Substituição:**
A maioria dos modificadores tem problemas com a assinatura de métodos que recebem `IAttachment.Slot`. A correção geralmente envolve adicionar o tipo genérico correto.

1.  **Localizar (Regex):** `public float getModifierValue(.*IAttachment\.Slot<.*> stack)`
2.  **Substituir por:** `public float getModifierValue$1` *(Isso pode variar, o objetivo é remover a assinatura incorreta para reimplementar com a correta fornecida pela IDE).*

**Arquivos para Corrigir:**

  - [ ] `resource/modifier/custom/AimInaccuracyModifier.java`
  - [ ] `resource/modifier/custom/AmmoSpeedModifier.java`
  - [ ] `resource/modifier/custom/ArmorIgnoreModifier.java`
  - [ ] `resource/modifier/custom/EffectiveRangeModifier.java`
  - [ ] `resource/modifier/custom/ExplosionModifier.java`
  - [ ] `resource/modifier/custom/ExtraMovementModifier.java`
  - [ ] `resource/modifier/custom/HeadShotModifier.java`
  - [ ] `resource/modifier/custom/IgniteModifier.java`
  - [ ] `resource/modifier/custom/InaccuracyModifier.java`
  - [ ] `resource/modifier/custom/KnockbackModifier.java`
  - [ ] `resource/modifier/custom/PierceModifier.java`
  - [ ] `resource/modifier/custom/RecoilModifier.java`
  - [ ] `resource/modifier/custom/RpmModifier.java`
  - [ ] `resource/modifier/custom/SilenceModifier.java`
  - [ ] `resource/modifier/custom/WeightModifier.java`

-----

## Fase 2: Correções Manuais e Específicas (Por Arquivo)

Estes erros são a causa raiz de centenas de outros problemas e precisam de atenção manual e cuidadosa. A ordem aqui é crucial.

### 2.1 - Erro Crítico: "Object Strategy" na API Principal

**Problema:** A `TimelessAPI` e `AttachmentCacheProperty` usam `Object` como um tipo de retorno genérico, o que quebra toda a verificação de tipos do Java e é a causa da maioria dos erros `cannot find symbol`.

**Arquivos e Ações:**

  - [ ] **`api/TimelessAPI.java`**

    1.  **Ação:** Mude a assinatura dos métodos `getClientGunIndex`, `getCommonGunIndex`, etc., para que retornem o tipo concreto em vez de `Optional<Object>`.
    2.  **Exemplo de Mudança:**
        ```java
        // Mudar de:
        public static Optional<Object> getClientGunIndex(ResourceLocation gunId) { ... }
        // Para:
        public static Optional<ClientGunIndex> getClientGunIndex(ResourceLocation gunId) { ... }
        ```
    3.  Repita para todos os métodos que retornam `Object` ou `Optional<Object>`.

  - [ ] **`resource/modifier/AttachmentCacheProperty.java`**

    1.  **Ação:** Altere os tipos dos mapas para usarem interfaces ou tipos base em vez de `Object`.
    2.  **Exemplo de Mudança:**
        ```java
        // Mudar de:
        private final Map<String, Object> cacheValues = Maps.newHashMap();
        // Para:
        private final Map<String, Pair<Integer, ?>> cacheValues = Maps.newHashMap(); // Ou um tipo mais específico se possível
        ```
    3.  **Ação:** Corrija o método `getCache` para que ele não precise mais de um `cast` inseguro.

-----

### 2.2 - Erro: Serializadores de Receita sem `streamCodec()`

**Problema:** `RecipeSerializer` agora exige a implementação do método `streamCodec()`.

**Arquivos e Ações:**

  - [ ] **`crafting/GunSmithTableSerializer.java`**
    1.  **Ação:** Adicione a implementação do método `streamCodec()`.
    2.  **Código para Adicionar:**
        ```java
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GunSmithTableRecipe> streamCodec() {
            // Implemente a lógica de serialização/desserialização aqui.
            // Exemplo:
            return StreamCodec.of(this::toNetwork, this::fromNetwork);
        }

        private GunSmithTableRecipe fromNetwork(RegistryFriendlyByteBuf buf) { ... }
        private void toNetwork(RegistryFriendlyByteBuf buf, GunSmithTableRecipe recipe) { ... }
        ```
  - [ ] **`resource/serialize/GunSmithTableResultSerializer.java`**
    1.  **Ação:** Realize a mesma correção, implementando o `streamCodec()` para a classe `RawGunTableResult`.

-----

### 2.3 - Erro: Construtor de `ImageButton` e Métodos de Renderização

**Problema:** APIs de GUI e renderização foram alteradas. O construtor de `ImageButton` e o método `renderToBuffer` são os principais culpados.

**Arquivos e Ações:**

  - [ ] **`client/gui/GunSmithTableScreen.java`** e **`client/gui/GunRefitScreen.java`**

    1.  **Ação:** Encontre todas as instâncias de `new ImageButton(...)`.
    2.  **Correção:** Substitua o construtor antigo pelo novo, que utiliza `WidgetSprites`.
    3.  **Exemplo:** `new ImageButton(x, y, w, h, new WidgetSprites(loc1, loc2), button -> { ... })`

  - [ ] **`client/renderer/item/GunItemRendererWrapper.java`** (e outros renderizadores)

    1.  **Ação:** Encontre todas as chamadas para `renderToBuffer`.
    2.  **Correção:** A nova assinatura do método geralmente tem menos parâmetros. Remova os argumentos extras (como `overlay`, `light`, etc.) e ajuste a chamada para corresponder à nova API. A IDE geralmente sugere a correção correta.

-----

### 2.4 - Erro: Entidade não implementa `defineSynchedData`

**Problema:** A classe `Entity` agora exige a implementação do método `defineSynchedData`.

  - [ ] **`entity/EntityKineticBullet.java`**
    1.  **Ação:** Adicione o método `defineSynchedData` e registre todas as variáveis de dados sincronizadas (como `shooterId`, `damage`, etc.) usando o `SynchedEntityData.Builder`.
    2.  **Código para Adicionar:**
        ```java
        @Override
        protected void defineSynchedData(SynchedEntityData.Builder builder) {
            // Exemplo: builder.define(DATA_OWNER_ID, Optional.empty());
        }
        ```

-----

Após concluir as correções da **Fase 1** e do **Erro Crítico 2.1**, compile o projeto novamente. A maioria dos 355 erros deve desaparecer, revelando os poucos problemas restantes que serão muito mais fáceis de corrigir individualmente.