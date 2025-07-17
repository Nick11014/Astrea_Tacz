# Refatoração do Sistema de Som para NeoForge SoundEvents

Este documento detalha o plano passo a passo para refatorar o sistema de som atual, que utiliza `SoundAssetsManager.SoundData` e `SoundAssetsManager.getData()`, para o sistema de `SoundEvent`s e `sounds.json` do NeoForge 1.21.1.

## Objetivo

Migrar a funcionalidade de carregamento e reprodução de sons para o sistema nativo do NeoForge, garantindo compatibilidade, melhor desempenho e aderência às melhores práticas da plataforma.

## Plano de Ação

### Fase 1: Preparação e Geração de Metadados de Som

1.  **Analisar Uso Atual de `SoundAssetsManager`:**
    *   Identificar todos os locais no código onde `SoundAssetsManager.SoundData` e `SoundAssetsManager.getData()` são utilizados.
    *   Compreender como os dados de som são atualmente acessados e utilizados para reprodução.
    *   Mapear os `ResourceLocation`s usados para carregar sons para seus nomes de evento de som correspondentes.

2.  **Gerar `sounds.json`:**
    *   Utilizar o script Python `generate_sounds.py` (ou similar) para escanear todos os arquivos `.ogg` na pasta `src/main/resources/assets/tacz/` (e subpastas).
    *   Gerar um arquivo `sounds.json` no formato `src/main/resources/assets/tacz/sounds.json` que mapeie cada arquivo `.ogg` para um `SoundEvent` correspondente.
    *   Exemplo de entrada no `sounds.json`:
        ```json
        {
          "nome_do_som": {
            "sounds": [
              {
                "name": "tacz:caminho/para/o/arquivo_ogg",
                "stream": false
              }
            ]
          }
        }
        ```
    *   Garantir que o `name` no `sounds.json` reflita o `ResourceLocation` completo do arquivo `.ogg` dentro do `assets/<modid>/`.

3.  **Atualizar `ModSoundEvents.java`:**
    *   Utilizar o script Python `generate_sounds.py` (ou similar) para gerar as entradas `public static final Supplier<SoundEvent>` para cada som no `ModSoundEvents.java`.
    *   Cada entrada deve corresponder a um `SoundEvent` definido no `sounds.json`.
    *   Exemplo de entrada no `ModSoundEvents.java`:
        ```java
        public static final Supplier<SoundEvent> NOME_DO_SOM = registerSoundEvent("nome_do_som");
        ```
    *   Verificar se `ModSoundEvents.SOUND_EVENTS.register(bus);` está sendo chamado no construtor principal do mod (`GunMod.java`) para registrar os `SoundEvent`s.

### Fase 2: Refatoração do Código Java

1.  **Modificar `GunSoundInstance`:**
    *   Remover a dependência direta de `SoundAssetsManager.SoundData`.
    *   Atualizar o construtor e os métodos para aceitar e utilizar um `SoundEvent` em vez de `SoundAssetsManager.SoundData`.
    *   A reprodução do som deve ser feita através do `SoundEvent` e da API de som do Minecraft/NeoForge (e.g., `Level.playSound()`, `Player.playSound()`).

2.  **Atualizar `ClientAssetsManager`:**
    *   Remover o método `getSoundBuffers(ResourceLocation id)`.
    *   Se houver outros métodos que dependam de `SoundAssetsManager.SoundData`, eles precisarão ser atualizados ou removidos.
    *   A lógica de obtenção de sons deve agora referenciar diretamente os `SoundEvent`s registrados em `ModSoundEvents`.

3.  **Refatorar `SoundAssetsManager`:**
    *   Simplificar a classe `SoundAssetsManager`. Ela não será mais responsável por carregar os dados brutos dos arquivos `.ogg`.
    *   Remover toda a lógica de leitura de `OggAudioStream` e manipulação de `ByteBuffer`.
    *   A classe pode ser mantida como um `SimplePreparableReloadListener` se ainda houver a necessidade de alguma lógica de recarga de recursos relacionada a sons que não seja o carregamento direto de `.ogg` (e.g., processamento de metadados de som customizados). Caso contrário, pode ser removida.
    *   Remover os TODOs relacionados a `OggAudioStream`.

4.  **Atualizar Chamadas de Reprodução de Som:**
    *   Localizar todas as chamadas para `SoundAssetsManager.getData()` e substituí-las por referências aos `SoundEvent`s apropriados de `ModSoundEvents`.
    *   Ajustar os parâmetros das chamadas de reprodução de som para corresponderem à API de som do Minecraft/NeoForge.

### Fase 3: Verificação e Testes

1.  **Compilação:**
    *   Executar `gradlew build` para garantir que todas as alterações de código compilem sem erros.

2.  **Testes em Jogo:**
    *   Iniciar o jogo e verificar se todos os sons customizados (tiros, recargas, etc.) estão sendo reproduzidos corretamente.
    *   Testar diferentes cenários de som (com e sem silenciadores, sons de impacto, etc.).
    *   Verificar se não há erros no log do jogo relacionados ao sistema de som.

3.  **Limpeza:**
    *   Remover quaisquer arquivos temporários ou classes obsoletas que não sejam mais necessárias após a refatoração.
    *   Remover o script `generate_sounds.py` após a conclusão bem-sucedida da refatoração.

## Considerações Adicionais

*   **Sons de Terceiros:** Se o mod utiliza sons de outros mods ou do próprio Minecraft, verificar se a forma como esses sons são referenciados é compatível com o novo sistema.
*   **Performance:** Monitorar o desempenho do jogo após a refatoração para garantir que a nova abordagem não introduza latência ou consumo excessivo de recursos.
*   **Localização:** Se houver nomes de sons que precisam ser localizados, garantir que o `sounds.json` e o sistema de `SoundEvent`s suportem isso.
