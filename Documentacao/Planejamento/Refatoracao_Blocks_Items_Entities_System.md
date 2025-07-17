# Plano de Refatoração: Sistemas de Blocos, Itens e Entidades

## Objetivo
Reativar e migrar as classes de blocos, itens e entidades para garantir que todos os elementos do jogo funcionem corretamente e estejam em conformidade com as APIs mais recentes do NeoForge 1.21.1.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados aos sistemas de blocos, itens e entidades:

*   **`com\tacz\guns\init\ModBlocks.java`**:
    *   `// import com.tacz.guns.block.entity.TargetBlockEntity; // TODO: Migrar APIs`
    *   `// TODO: Re-enable when all block classes are available`
    *   `// TODO: Re-enable when block classes are available`
*   **`com\tacz\guns\init\ModCreativeTabs.java`**:
    *   `// TODO: Re-enable when all dependencies are available`
    *   `// TODO: Re-enable when ModItems and all builders are available`
    *   `// TODO: Re-enable when items are available`
    *   `// TODO: Re-enable when AmmoItem is available`
    *   `// TODO: Re-enable when AttachmentItem is available`
*   **`com\tacz\guns\init\ModEntities.java`**:
    *   `// TODO: Re-enable when all entity classes are available`
    *   `// TODO: Re-enable when entity classes are available`
*   **`com\tacz\guns\init\ModItems.java`**:
    *   `// TODO: Re-enable when all item classes are available`
    *   `// TODO: Re-enable when item classes are available`
    *   `// TODO: Re-enable when all dependencies are available`
*   **`com\tacz\guns\init\ModRecipe.java`**:
    *   `// TODO: Re-enable when all crafting classes are available`
    *   `// TODO: Re-enable when crafting classes are available`

## Plano de Ação

### Fase 1: Reativação e Migração de Classes
1.  **Blocos (`ModBlocks.java`):**
    *   Descomentar e reativar todas as classes de blocos.
    *   Migrar as APIs de `TargetBlockEntity` para as versões mais recentes do NeoForge.
2.  **Itens (`ModItems.java`):**
    *   Descomentar e reativar todas as classes de itens.
    *   Garantir que as dependências de `AmmoItem` e `AttachmentItem` estejam disponíveis e corretas.
3.  **Entidades (`ModEntities.java`):**
    *   Descomentar e reativar todas as classes de entidades.

### Fase 2: Integração e Registro
1.  **Abas Criativas (`ModCreativeTabs.java`):**
    *   Reativar `ModCreativeTabs` e garantir que todas as dependências (ModItems, builders, AmmoItem, AttachmentItem) estejam disponíveis para o registro correto das abas.
2.  **Receitas (`ModRecipe.java`):**
    *   Reativar `ModRecipe` e garantir que todas as classes de crafting estejam disponíveis para o registro correto das receitas.

### Fase 3: Testes e Validação
1.  **Testes de Funcionalidade:** Testar a colocação e interação com todos os blocos, o uso de todos os itens e o comportamento de todas as entidades no jogo.
2.  **Testes de Registro:** Verificar se todos os blocos, itens e entidades estão sendo registrados corretamente no jogo e aparecem nas abas criativas.
3.  **Limpeza:** Remover quaisquer comentários TODOs e código obsoleto após a conclusão da refatoração.

## Considerações Adicionais
*   **Dependências:** Estar atento a quaisquer dependências cruzadas entre blocos, itens e entidades que possam surgir durante a reativação.
*   **Documentação:** Atualizar a documentação sobre como adicionar novos blocos, itens e entidades ao mod.
