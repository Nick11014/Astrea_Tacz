# Plano de Refatoração: Migração para Data Components (NeoForge 1.21.1)

## Objetivo
Migrar todas as funcionalidades que atualmente dependem de NBT (Network Binary Tag) para o novo sistema de Data Components introduzido no NeoForge 1.21.1. Isso garantirá compatibilidade, melhor desempenho e aderência às melhores práticas da plataforma.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados à migração de Data Components:

*   `com\tacz\guns\api\item\nbt\AmmoBoxItemDataAccessor.java`:
    *   `* Todos os métodos agora usam os DataComponents definidos em ModDataComponents`
*   `com\tacz\guns\api\item\nbt\GunItemDataAccessor.java`:
    *   `* Todos os métodos agora usam os DataComponents definidos em ModDataComponents`
*   `com\tacz\guns\entity\sync\core\Serializers.java`:
    *   `// TODO: Reconstruir ItemStack com DataComponents`
    *   `// TODO: Migrar para DataComponents conforme pesquisa`
    *   `// TODO: Implementar leitura completa com DataComponents`
*   `com\tacz\guns\client\resource\ClientAssetsManager.java`:
    *   `// Precisa converter AmmoDisplay para AmmoIndexPOJO ou ajustar o método getInstance`
    *   `// TODO: Implementar conversão adequada ou ajustar ClientAmmoIndex.getInstance`
    *   `// Precisa converter BlockDisplay para BlockIndexPOJO ou ajustar o método getInstance`
    *   `// TODO: Implementar conversão adequada ou ajustar ClientBlockIndex.getInstance`
*   `com\tacz\guns\crafting\result\RawGunTableResult.java`:
    *   `// TODO: Implementar migração específica de NBT para DataComponents quando necessário`
*   `com\tacz\guns\resource\serialize\GunSmithTableResultSerializer.java`:
    *   `// TODO: [MIGRATION] CraftingHelper.getNBT() removed in NeoForge 1.21.1 - use DataComponents`

## Plano de Ação

### Fase 1: Compreensão e Mapeamento
1.  **Estudar a API de Data Components do NeoForge:** Aprofundar o conhecimento sobre como os Data Components funcionam, como são registrados, lidos e escritos.
2.  **Mapear Dados Atuais para Data Components:** Para cada tipo de dado que atualmente usa NBT (e.g., dados de armas, munições, anexos), definir a estrutura correspondente usando Data Components.

### Fase 2: Implementação da Migração
1.  **Atualizar `ModDataComponents`:** Registrar todos os Data Components necessários para o mod.
2.  **Refatorar Acessadores de Dados:**
    *   Modificar `AmmoBoxItemDataAccessor` e `GunItemDataAccessor` para ler e escrever dados usando os novos Data Components.
    *   Remover qualquer lógica de NBT obsoleta.
3.  **Atualizar Serialização e Desserialização:**
    *   Em `Serializers.java`, refatorar a lógica de serialização e desserialização de `ItemStack` para usar Data Components.
    *   Em `GunSmithTableResultSerializer.java`, substituir o uso de `CraftingHelper.getNBT()` pela abordagem de Data Components.
4.  **Ajustar Gerenciadores de Assets do Cliente:**
    *   Em `ClientAssetsManager.java`, implementar a conversão de `AmmoDisplay` para `ClientAmmoIndex` e `BlockDisplay` para `ClientBlockIndex` usando Data Components, ou ajustar os métodos `getInstance` para trabalhar diretamente com os novos tipos de dados.
5.  **Refatorar Resultados de Crafting:**
    *   Em `RawGunTableResult.java`, implementar a migração específica de NBT para Data Components para garantir que os itens criados tenham os dados corretos.

### Fase 3: Verificação e Testes
1.  **Testes Unitários:** Criar ou atualizar testes unitários para garantir que a leitura e escrita de dados via Data Components funcionem corretamente.
2.  **Testes de Integração:** Realizar testes em jogo para verificar se todas as funcionalidades que dependem desses dados (e.g., comportamento de armas, munições, anexos) continuam funcionando como esperado.
3.  **Verificação de Performance:** Monitorar o impacto da migração no desempenho do jogo.

## Considerações Adicionais
*   **Compatibilidade:** Garantir que a migração seja retrocompatível, se necessário, ou que haja um plano claro para lidar com versões antigas de dados.
*   **Documentação:** Atualizar a documentação interna e externa sobre como os dados dos itens são armazenados e acessados.
