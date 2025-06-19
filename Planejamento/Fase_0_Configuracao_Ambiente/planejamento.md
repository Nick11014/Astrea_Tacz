# Fase 0: Configuração do Ambiente e Estrutura do Projeto

O objetivo desta fase é fazer o projeto compilar no novo ambiente, sem se preocupar com a lógica do jogo ainda. Isso isola problemas de build dos problemas de código.

## Checklist de Implementação:

* **[x] Atualizar o `gradle.properties`:**
    * ✅ Configurado versões do NeoForge 1.21.1 (neoforge_version=21.1.42)
    * ✅ Configurado minecraft_version=1.21.1
    * ✅ Atualizado dependências principais (JEI, Cloth Config, etc.)

* **[x] Atualizar o `build.gradle`:**
    * ✅ Plugin NeoGradle configurado (net.neoforged.gradle.userdev)
    * ✅ Java toolchain configurado para Java 21
    * ✅ Dependência básica do NeoForge adicionada
    * ⚠️ **BLOQUEADO:** Configuração completa (runs, mappings) - aguardando resolução impedimento Java

* **[x] Atualizar o `mods.toml`:**
    * ✅ Migrado de "forge" para "neoforge"
    * ✅ loaderVersion atualizado para "[4,)"
    * ✅ Versão do Minecraft especificada como "[1.21.1,1.21.2)"
    * ✅ Dependências atualizadas

* **[x] Atualizar `pack.mcmeta`:**
    * ✅ pack_format atualizado para 34 (correspondente ao Minecraft 1.21.1)

* **[ ] Regenerar o Projeto:**
    * ⚠️ **BLOQUEADO:** `gradlew clean` executa mas `gradlew build` falha devido incompatibilidade Java 24 vs NeoGradle
    * ⚠️ **PENDENTE:** `gradlew genSources` - aguardando resolução do impedimento
    * ⚠️ **PENDENTE:** Importar na IDE - aguardando build funcional

## ⚠️ Impedimento Crítico Identificado:
**Incompatibilidade Java 24 vs NeoGradle** - Ver detalhes em `impedimentos.md`

## Estratégia de Testes (Fase 0):

* O único teste aqui é a saúde do ambiente de desenvolvimento.
* **Critério de Sucesso:** O projeto é importado corretamente na IDE, as dependências do NeoForge e do Minecraft 1.21.1 são baixadas e o processo de build (`gradlew build`) executa sem erros relacionados à configuração do Gradle.

## Status Atual:
- [x] **80% Concluído** - Configurações básicas migradas com sucesso
- [ ] **20% Pendente** - Bloqueado por impedimento Java (ver impedimentos.md)

### ✅ Realizações:
1. **Migração bem-sucedida** de Forge 1.20.1 → NeoForge 1.21.1
2. **Configurações atualizadas** para todas as versões corretas
3. **Estrutura do projeto** preparada para NeoForge
4. **Dependências básicas** funcionando (download bem-sucedido)

### ⚠️ Pendências:
1. **Resolver incompatibilidade Java 24 vs NeoGradle**
2. **Finalizar configuração build.gradle** (bloco minecraft{}, runs)
3. **Testar compilação completa**
4. **Configurar ambiente de desenvolvimento na IDE**

## Próximos Passos:
1. **URGENTE:** Resolver impedimento Java (instalar Java 21)
2. **Finalizar build.gradle** com configurações de runs
3. **Testar `gradlew build`** sem erros
4. **Marcar Fase 0 como concluída**
5. **Iniciar Fase 1** - Migração do Core
