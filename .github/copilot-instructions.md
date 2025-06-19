# Instruções Customizadas para o Projeto de Portabilidade do TacZ

## 🎯 Objetivo Principal do Projeto

O objetivo deste projeto é realizar a portabilidade completa do mod "TacZ" da versão **Forge 1.20.1** para a versão **NeoForge 1.21.1**. Este é um trabalho de migração significativo que envolve a atualização da versão do Minecraft e a adaptação para as APIs do NeoForge.

O Copilot deve auxiliar em todas as fases do desenvolvimento, desde a configuração do ambiente até a finalização, seguindo o plano de desenvolvimento estabelecido.

## CONTEXTO TÉCNICO CRÍTICO

### 1. Migração de NBT para DataComponents (Prioridade Máxima)

A mudança mais impactante entre o Minecraft 1.20.1 e 1.21.1 é a substituição do armazenamento de dados em NBT nos `ItemStack`s pelo novo sistema de `DataComponent`.

- **Regra:** **SEMPRE** prefira o uso de `DataComponent` em vez de `itemStack.getOrCreateTag()`, `itemStack.setTag()`, etc.
- **Foco:** O pacote `com.tacz.guns.api.item.nbt` e todas as suas utilizações no código devem ser refatorados para usar `DataComponent`.

**Exemplo de Migração:**

**Código Antigo (Forge 1.20.1):**
```java
// Em GunItemDataAccessor.java ou similar
public static voidsetCurrentAmmo(ItemStack stack, int ammo) {
    stack.getOrCreateTag().putInt("CurrentAmmo", ammo);
}

public static int getCurrentAmmo(ItemStack stack) {
    return stack.getOrCreateTag().getInt("CurrentAmmo");
}
```

**Novo Código (NeoForge 1.21.1):**
```java
// Em uma nova classe ModDataComponents.java
public static final DeferredRegister<DataComponentType<?>> COMPONENTS = ...;
public static final Supplier<DataComponentType<Integer>> CURRENT_AMMO = COMPONENTS.register("current_ammo",
    () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build()
);

// No código de uso
public static void setCurrentAmmo(ItemStack stack, int ammo) {
    stack.set(ModDataComponents.CURRENT_AMMO, ammo);
}

public static int getCurrentAmmo(ItemStack stack) {
    return stack.getOrDefault(ModDataComponents.CURRENT_AMMO, 0);
}
```

### 2. APIs e Frameworks
- **Modloader:** O alvo é **NeoForge 1.21.1**. As sugestões devem usar as APIs e convenções do NeoForge, não as do Forge antigo.
- **Registros:** Utilize `DeferredRegister` para registrar todos os itens, blocos, entidades, etc., seguindo o padrão do NeoForge.
- **Renderização:** As APIs de renderização do lado do cliente mudaram. Preste atenção especial ao portar classes que usam `BlockEntityWithoutLevelRenderer` (agora parte de `IClientItemExtensions`), `IForgeGui` (para overlays/HUD) e os métodos de renderização em geral (`PoseStack`, `MultiBufferSource`).
- **Rede (Networking):** O sistema de rede foi refatorado. As sugestões para pacotes de rede devem seguir a nova API do NeoForge para registro e manipulação de pacotes.

## 🗺️ Plano de Desenvolvimento por Fases

O projeto está dividido em fases. Entenda em qual fase o código está sendo trabalhado para fornecer contexto relevante:
- **Fase 0: Configuração do Ambiente:** Foco em `build.gradle`, `mods.toml` e dependências para fazer o projeto compilar.
- **Fase 1: Migração do Core:** Foco em `DataComponent`, registros (`init` package) e receitas.
- **Fase 2: Renderização e Cliente:** Foco nos pacotes `client/renderer`, `client/gui`, `client/animation`, e o sistema de HUD. Esta é uma área de alto risco.
- **Fase 3: Gameplay e Rede:** Foco na lógica de tiro, recarga, mira e no pacote `network`.
- **Fase 4: Compatibilidade:** Foco no pacote `compat` para mods como JEI, KubeJS, etc.
- **Fase 5: Finalização:** Polimento, correção de bugs e limpeza de código.

## ✍️ Como Ajudar (Princípios Gerais)

- **Identifique Código Obsoleto:** Ajude a identificar métodos e classes do Forge 1.20.1 que foram removidos ou alterados no NeoForge 1.21.1 e sugira as alternativas corretas.
- **Mantenha a Estrutura:** As sugestões devem respeitar a arquitetura e a estrutura de pacotes existentes do projeto.
- **Foco na Segurança de Tipos:** Ao refatorar, ajude a garantir que os tipos de dados (especialmente com `DataComponent`) sejam consistentes.
- **Sugira Comentários:** Para trechos de código complexos que foram refatorados (como a lógica de animação ou renderização), sugira adicionar comentários explicando o porquê da mudança.

## ❌ O Que Evitar

- **Não sugira APIs do Forge antigo.** A menos que sejam APIs que o NeoForge manteve por compatibilidade, sempre prefira a API nativa do NeoForge.
- **Não faça suposições sobre a lógica de animação.** O sistema de animação (`gltf`, `bedrock`, máquina de estados) é complexo e customizado. A ajuda deve se concentrar em conectar este sistema às novas APIs de renderização, não em alterar sua lógica interna.
- **Evite soluções "mágicas".** Prefira sugestões de código claras e explícitas que sigam as melhores práticas do modding em Java e NeoForge.
- **Não ignore o contexto de fase.** Sempre verifique em qual fase o código está sendo trabalhado e adapte suas sugestões para essa fase específica.