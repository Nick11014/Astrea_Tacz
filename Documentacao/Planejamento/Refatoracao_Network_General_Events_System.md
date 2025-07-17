# Plano de Refatoração: Sistemas de Rede e Eventos Gerais

## Objetivo
Refatorar e habilitar completamente os sistemas de rede e eventos gerais, garantindo a comunicação eficiente entre cliente e servidor, o correto disparo de eventos e a reativação de funcionalidades essenciais que foram temporariamente desabilitadas.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados aos sistemas de rede e eventos gerais:

*   `com\tacz\guns\client\event\ClientResourceReloader.java`:
    *   `// TODO: Re-enable ClientReloadManager.reloadAllPack() when ClientReloadManager is available`
*   `com\tacz\guns\client\particle\AmmoParticleSpawner.java`:
    *   `// TODO: Re-enable when TimelessAPI and EntityKineticBullet are habilitado`
    *   `// TODO: Re-enable when EntityKineticBullet is habilitado` (múltiplas ocorrências)
    *   `// TODO: Re-enable when TimelessAPI is habilitado`
*   `com\tacz\guns\command\RootCommand.java`:
    *   `// TODO: Re-enable sub-commands when their dependencies are habilitadas`
*   `com\tacz\guns\resource\VersionChecker.java`:
    *   `// TODO: Re-enable when CommonAssetsManager is habilitado`
*   `com\tacz\guns\resource\modifier\AttachmentPropertyManager.java`:
    *   `// TODO: Implementar quando sistema de eventos estiver funcional`
*   `com\tacz\guns\sound\SoundManager.java`:
    *   `* mas implementa métodos de rede com placeholders seguros.`
    *   `* TODO: Implementar quando NetworkHandler estiver disponível`
    *   `* TODO: Implementar quando NetworkHandler e ServerMessageSound estiverem disponíveis`
*   `com\tacz\guns\util\ExplodeUtil.java`:
    *   `* TODO: Implementar ProjectileExplosion quando APIs de Explosion forem migradas`
    *   `// TODO: Restaurar ProjectileExplosion quando APIs forem migradas`
*   `com\tacz\guns\util\block\ProjectileExplosion.java`:
    *   `// TODO: [MIGRATION] Explosion constructor signature changed in NeoForge 1.21.1`

## Plano de Ação

### Fase 1: Rede e Comunicação
1.  **Implementar `NetworkHandler`:** Desenvolver a classe `NetworkHandler` para gerenciar o registro e o envio de pacotes de rede.
2.  **Implementar `ServerMessageSound`:** Garantir que `ServerMessageSound` seja corretamente implementado e possa ser enviado e recebido via `NetworkHandler`.
3.  **Atualizar `SoundManager`:** Remover os placeholders e implementar a lógica real de rede em `SoundManager.java` para o envio de sons.

### Fase 2: Reativação de Funcionalidades
1.  **Reativar `ClientReloadManager.reloadAllPack()`:** Em `ClientResourceReloader.java`, habilitar a chamada para `ClientReloadManager.reloadAllPack()` quando `ClientReloadManager` estiver disponível e funcional.
2.  **Habilitar `CommonAssetsManager`:** Em `VersionChecker.java`, reativar a funcionalidade que depende de `CommonAssetsManager`.
3.  **Reativar Subcomandos:** Em `RootCommand.java`, habilitar os subcomandos quando suas dependências estiverem resolvidas.

### Fase 3: Eventos e Efeitos
1.  **Implementar `ProjectileExplosion`:** Desenvolver a classe `ProjectileExplosion` em `ExplodeUtil.java` e `ProjectileExplosion.java`, migrando as APIs de `Explosion` para o NeoForge 1.21.1.
2.  **Reativar `AmmoParticleSpawner`:** Em `AmmoParticleSpawner.java`, reativar a funcionalidade que depende de `TimelessAPI` e `EntityKineticBullet`.
3.  **Sistema de Eventos para Modificadores:** Em `AttachmentPropertyManager.java`, implementar o sistema de eventos para modificadores.

### Fase 4: Testes e Validação
1.  **Testes de Rede:** Realizar testes de comunicação entre cliente e servidor para garantir que os pacotes sejam enviados e recebidos corretamente.
2.  **Testes de Eventos:** Verificar se os eventos são disparados e processados corretamente.
3.  **Testes de Funcionalidade:** Testar todas as funcionalidades reativadas para garantir que funcionem como esperado.
4.  **Limpeza:** Remover quaisquer comentários TODOs e código obsoleto após a conclusão da refatoração.

## Considerações Adicionais
*   **Segurança da Rede:** Garantir que a comunicação de rede seja segura e resistente a exploits.
*   **Performance da Rede:** Otimizar o tráfego de rede para minimizar a latência e o uso de largura de banda.
