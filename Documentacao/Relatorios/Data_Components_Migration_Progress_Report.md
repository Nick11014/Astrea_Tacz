## Relatório de Progresso: Migração para Data Components

A refatoração do sistema para utilizar os Data Components do NeoForge 1.21.1 está em andamento. O objetivo é modernizar a forma como os dados dos itens são armazenados e acessados, eliminando a dependência do sistema NBT legado.

**Trabalho Concluído nesta Etapa:**

*   **`ModDataComponents.java`**: Confirmado que os tipos de Data Components estão corretamente definidos.
*   **`AmmoBoxItemDataAccessor.java`**: As constantes de tags NBT antigas foram removidas, e a interface foi atualizada para usar diretamente os `ModDataComponents` para acesso a dados de caixas de munição.
*   **`GunItemDataAccessor.java`**: 
    *   As constantes de tags NBT antigas foram removidas.
    *   Os métodos `getAttachmentTag`, `installAttachment`, `unloadAttachment`, `getAttachment`, `lerpRPM`, `lerpInaccuracy` e `getAimingZoom` foram refatorados para utilizar os `ModDataComponents` e o acesso correto a `HolderLookup.Provider` (via `net.minecraft.client.Minecraft.getInstance().level.registryAccess()`).
*   **`Serializers.java`**: 
    *   A serialização e desserialização de `ItemStack` foi atualizada para usar `buf.writeItemStack()` e `buf.readItemStack()`.
    *   As referências a `BuiltInRegistries.ITEM.asLookup()` foram substituídas por `net.minecraft.client.Minecraft.getInstance().level.registryAccess()`.
    *   As importações necessárias foram adicionadas.
*   **`RawGunTableResult.java`**: Campos e lógica relacionados a NBT foram removidos, simplificando a classe.
*   **`GunSmithTableResultSerializer.java`**: A lógica de desserialização foi ajustada para remover a dependência de `CompoundTag` e o tratamento de NBT legado.

**Status Atual e Próximos Passos:**

Apesar das refatorações significativas, o projeto ainda apresenta erros de compilação. Os principais problemas restantes são:

*   **`GunItemDataAccessor.java`**: Erros de incompatibilidade de tipo (`RegistryLookup<Item>` para `Provider`) e um `cannot find symbol` relacionado a `getIronZoom()` (que precisa ser acessado via `getDefaultDisplay()` de `ClientGunIndex`).
*   **`ClientAssetsManager.java`**: Erros de incompatibilidade de tipo (`AmmoDisplay` para `AmmoIndexPOJO` e `BlockDisplay` para `BlockIndexPOJO`). Essas linhas permanecem comentadas temporariamente, pois exigem uma refatoração mais aprofundada das classes de índice de cliente.
*   **`Serializers.java`**: Um `cannot find symbol` relacionado à variável `compound` (problema de escopo) e, possivelmente, resquícios de `writeItem`/`readItem` se as substituições não foram 100% eficazes.

Os próximos passos envolverão a depuração e correção desses erros de compilação restantes, com foco especial na resolução dos problemas de `HolderLookup.Provider` e na correção do acesso a `getIronZoom()`. A refatoração de `ClientAssetsManager.java` será abordada em uma etapa futura dedicada.