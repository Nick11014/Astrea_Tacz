Com base na análise detalhada de toda a documentação fornecida, elaborei um plano de migração sistemático para que uma inteligência artificial agente possa dar continuidade ao excelente trabalho já realizado no projeto TacZ NeoForge 1.21.1.

O plano leva em consideração o estado atual do projeto (especialmente as conquistas da Fase D, Onda 4), a arquitetura de dependências (diagram.md) e as estratégias bem-sucedidas já empregadas (Implementação Mínima Estratégica e Object Strategy, documentadas no DEBITO_TECNICO.md).

---

## 🎯 **Plano de Ação para Agente de Migração de IA**

### **1. Objetivo Principal da Próxima Fase (Fase D, Onda 5)**

O objetivo é alcançar **100% de funcionalidade do sistema de renderização**, resolvendo a última dependência crítica que impede a habilitação completa do `AttachmentRender`. A conquista histórica da Onda 4, com a habilitação do `BedrockAttachmentModel`, preparou o terreno para esta finalização.

### **2. Análise do Estado Atual e Diretrizes Estratégicas**

* **Progresso Atual:** O sistema de renderização funcional está **92% completo**. Foram habilitados 9 renderizadores funcionais e o `BedrockAttachmentModel`, uma dependência crítica, agora está operacional.
* **Dependência Crítica Restante:** O principal bloqueador é o `AttachmentItemRenderer`, que representa os 20% restantes das dependências do `AttachmentRender`.
* **Estratégia Comprovada:** A abordagem de **"Implementação Mínima Estratégica"** e **"Object Strategy"** foi fundamental para superar bloqueios complexos, como a migração da API `VertexConsumer` e a habilitação do `BedrockAttachmentModel`. Esta deve ser a estratégia primária para lidar com o `AttachmentItemRenderer`.

### **3. Plano de Execução Detalhado: Foco em `AttachmentItemRenderer`**

O agente de IA deve seguir estes passos de forma sequencial e rigorosa.

#### **Passo 1: Habilitação Mínima do `AttachmentItemRenderer` (Prioridade Alta)**

1.  **Ação:** Renomear o arquivo `client/renderer/item/AttachmentItemRenderer.java.disabled` para `AttachmentItemRenderer.java`.
2.  **Análise de Dependências (via `diagram.md`):** O `AttachmentItemRenderer` possui dependências complexas, incluindo `BedrockAttachmentModel`, `ClientAttachmentIndex` e APIs de renderização do NeoForge.
3.  **Implementação Mínima Estratégica:**
    * O objetivo inicial **não é a funcionalidade completa**, mas sim criar uma estrutura que **compile sem erros**.
    * A classe deve implementar os métodos necessários para satisfazer a interface com `IClientItemExtensions` ou similar.
    * No método principal de renderização (`renderByItem`), o agente deve inserir um log informativo (ex: `LOGGER.info("AttachmentItemRenderer placeholder enabled.");`) e retornar, sem executar lógica de renderização.
    * Para tipos de retorno complexos (modelos, texturas), utilizar `null` ou placeholders, aplicando a "Object Strategy" se necessário para evitar erros de tipo imediatos.
4.  **Compilação:** Executar `./gradlew compileJava`. O build deve passar. Se falhar, analisar os erros e ajustar a implementação mínima até que a compilação seja bem-sucedida.

#### **Passo 2: Expansão e Integração do `AttachmentRender` (Prioridade Média)**

Com o `AttachmentItemRenderer` minimamente habilitado, o `AttachmentRender` pode ser totalmente desbloqueado.

1.  **Ação:** Renomear o arquivo `client/model/functional/AttachmentRender.java.disabled` para `AttachmentRender.java` (se ainda não estiver habilitado em uma forma mínima).
2.  **Restauração de Funcionalidades:**
    * No arquivo `AttachmentRender.java`, remover os comentários de seções de código que dependiam do `AttachmentItemRenderer`.
    * No `BedrockGunModel.java`, restaurar as chamadas para o `AttachmentRender` que foram comentadas.
    * No `FunctionalRendererManager.java`, garantir que a factory `createAttachmentRenderForType()` está corretamente implementada para instanciar `AttachmentRender`.
3.  **Análise e Adaptação:** O agente deve analisar as dependências que acabaram de ser descomentadas. Graças à habilitação do `BedrockAttachmentModel` e do `ClientAttachmentIndex` expandido, a maioria das dependências já deve estar resolvida.
4.  **Compilação:** Executar `./gradlew compileJava` para garantir que a integração não introduziu novos erros.

#### **Passo 3: Restauração de Funcionalidades Comentadas e Polimento (Prioridade Baixa)**

Esta etapa foca em limpar o débito técnico criado intencionalmente durante as fases anteriores.

1.  **Ação:** O agente deve escanear o projeto em busca de comentários `// TODO: MIGRATE` ou seções comentadas, conforme documentado no `DEBITO_TECNICO.md`.
2.  **Prioridades de Restauração:**
    * **Lógica de `JsonProperty` e `AttachmentPropertyManager`:** Restaurar a lógica de modificadores em `GunData.java` e `AttachmentDataManager.java`.
    * **Sistema de Assets do Cliente:** Habilitar a funcionalidade completa do `ClientAssetsManager`, `GltfManager` e `SoundAssetsManager`, buscando alternativas para APIs removidas como `OggAudioStream`.
    * **Índices do Cliente:** Expandir as implementações mínimas de `ClientGunIndex.java` e restaurar a funcionalidade completa do `ClientIndexManager.java`.
3.  **Compilação Incremental:** A cada funcionalidade restaurada, o agente deve executar o teste de compilação para garantir a estabilidade.

#### **Passo 4: Validação e Finalização**

1.  **Testes de Integração:** Executar testes para validar a renderização de acessórios no jogo, a aplicação de modificadores e a estabilidade geral do sistema.
2.  **Documentação Final:** O agente deve atualizar o `DEBITO_TECNICO.md`, removendo as tarefas concluídas e gerando um relatório final, similar aos relatórios de fase já existentes, declarando o sistema **100% COMPLETO**.
3.  **Build Final:** Executar uma compilação limpa e final do projeto.

### **4. Tabela Resumo para o Agente de IA**

| Fase | Ação Principal | Detalhes e Estratégias | Verificação de Sucesso |
| :--- | :--- | :--- | :--- |
| **D.5 - Passo 1** | Habilitar `AttachmentItemRenderer.java` | Usar "Implementação Mínima Estratégica". Foco em compilar, não em funcionalidade. | `./gradlew compileJava` bem-sucedido. |
| **D.5 - Passo 2** | Integrar e expandir `AttachmentRender.java` | Descomentar código dependente no `AttachmentRender` e `BedrockGunModel`. | `./gradlew compileJava` bem-sucedido. |
| **D.5 - Passo 3** | Restaurar Débito Técnico | Seguir o `DEBITO_TECNICO.md` para restaurar lógica de modificadores, assets e índices. | Compilações incrementais bem-sucedidas. |
| **D.5 - Passo 4** | Finalizar e Validar | Executar testes de integração e atualizar a documentação para refletir 100% de conclusão. | Build final bem-sucedido e relatório de conclusão gerado. |

Seguindo este plano, o agente de IA pode metodicamente concluir a migração, aproveitando o progresso e as estratégias que já se provaram eficazes.