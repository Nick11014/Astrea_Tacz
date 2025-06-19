# Fase 0: Configuração do Ambiente e Estrutura do Projeto

O objetivo desta fase é fazer o projeto compilar no novo ambiente, sem se preocupar com a lógica do jogo ainda. Isso isola problemas de build dos problemas de código.

## Checklist de Implementação:

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

## Estratégia de Testes (Fase 0):

* O único teste aqui é a saúde do ambiente de desenvolvimento.
* **Critério de Sucesso:** O projeto é importado corretamente na IDE, as dependências do NeoForge e do Minecraft 1.21.1 são baixadas e o processo de build (`gradlew build`) começa, mesmo que falhe devido a erros de código. Não deve haver erros relacionados à configuração do Gradle em si.

## Status:
- [ ] Não iniciado
- [ ] Em progresso  
- [ ] Concluído
- [ ] Testado
