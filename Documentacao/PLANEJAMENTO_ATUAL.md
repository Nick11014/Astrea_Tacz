# **Plano de Migração \- TacZ para NeoForge 1.21.1 (v2)**

Data: 14/07/2025  
Versão do Documento: 2.0  
Objetivo: Priorizar e resolver os 355 erros de compilação restantes, com foco na refatoração da "Object Strategy" e na correção sistemática de APIs do NeoForge 1.21.1.

## **1\. Análise da Situação Atual (355 Erros)**

O relatório de erros atual, embora numericamente menor que os 500 iniciais, revela problemas mais profundos e interconectados. A correção bem-sucedida do NetworkHandler desmascarou os seguintes blocos críticos de erros:

* **cannot find symbol (Causa Raiz):** A causa da maioria dos erros. O uso de Object em classes centrais como AttachmentCacheProperty e nos retornos da TimelessAPI impede o compilador de resolver métodos específicos (.getData(), .getGunModel(), .getFilter(), etc.), gerando centenas de falhas em cascata. **Este é o nosso principal débito técnico a ser pago.**  
* **method does not override e is not abstract:** Um grande número de classes, especialmente nos pacotes network.message, crafting e resource.modifier, não estão mais em conformidade com as interfaces que implementam. Métodos como type(), streamCodec(), e outros precisam ser implementados ou ter suas assinaturas corrigidas.  
* **incompatible types:** Erros de tipagem são comuns, principalmente devido à "Object Strategy" e a mudanças na API, como o RecipeManager agora retornando RecipeHolder\<?\> em vez de Recipe\<?\>.  
* **no suitable constructor/method:** APIs de GUI (ImageButton) e de renderização (renderToBuffer) foram alteradas e precisam de uma correção sistemática.

## **2\. Fases da Migração (Revisadas)**

O plano foi reestruturado para focar primeiro nos problemas que desbloqueiam o maior número de outros erros.

### **Fase 1: Desmantelamento da "Object Strategy" e Correções de Contrato de API**

O objetivo desta fase é eliminar os erros em cascata, corrigindo a arquitetura central e garantindo que as classes cumpram seus contratos de interface.

**Prioridade 1: Refatoração da "Object Strategy" (O Maior Bloqueador)**

* **Problema:** TimelessAPI e outras classes de gerenciamento retornam Object ou Optional\<Object\>, o que quebra a checagem de tipos em tempo de compilação.  
* **Solução:**  
  1. Modificar os métodos em TimelessAPI para retornar os tipos específicos corretos (ex: Optional\<ClientGunIndex\>, Optional\<CommonAttachmentIndex\>).  
  2. Refatorar AttachmentCacheProperty para que o Map\<ResourceLocation, Object\> modifiers e Map\<String, Object\> cacheValues usem os tipos de interface corretos (ex: IAttachmentModifier).  
  3. Corrigir todas as chamadas a esses métodos, removendo a necessidade de casts e resolvendo os erros de cannot find symbol em cascata.  
* **Arquivos-chave:** AttachmentCacheProperty.java, TimelessAPI.java, e todas as classes que consomem dados de índices (GunSmithTableScreen, GunItemRendererWrapper, etc.).

**Prioridade 2: Implementação de Métodos Abstratos Obrigatórios**

* **Problema:** Dezenas de classes não implementam métodos abstratos exigidos pelas novas interfaces do NeoForge.  
* **Solução:**  
  1. **CustomPacketPayload:** Adicionar a implementação do método type() a **TODOS** os records de mensagens de rede. Ex: public ResourceLocation type() { return TYPE; }.  
  2. **RecipeSerializer:** Implementar o método streamCodec() em GunSmithTableSerializer e outros serializadores de receita.  
  3. **Entity:** Implementar defineSynchedData(Builder) em EntityKineticBullet.  
  4. **Recipe:** Implementar getResultItem(Provider) em GunSmithTableRecipe.

### **Fase 2: Correção Sistemática de APIs do NeoForge**

Com a arquitetura de dados corrigida, focaremos nos erros repetitivos de API.

**Prioridade 1: Correção de Construtores e Métodos de GUI/Renderização**

* **Problema:** Construtores e métodos de renderização foram alterados.  
* **Solução:**  
  1. **ImageButton:** Substituir todas as instanciações do construtor antigo pelo novo que utiliza WidgetSprites.  
  2. **renderToBuffer:** Atualizar todas as chamadas do método para a nova assinatura, que possui menos parâmetros.  
  3. **PoseStack:** Substituir RenderSystem.getModelViewStack() por uma alternativa válida ou gerenciar a PoseStack corretamente dentro dos métodos de renderização.

**Prioridade 2: Correção de Assinaturas de Métodos (@Override)**

* **Problema:** Erros de "method does not override" indicam que as assinaturas de métodos herdados mudaram.  
* **Solução:**  
  1. Revisar sistematicamente **todas** as classes com este erro.  
  2. Consultar a classe-mãe no código do NeoForge 1.21.1 para encontrar a assinatura correta do método e atualizá-la.

### **Fase 3: Quitação do Débito Técnico e Testes**

* \[ \] **Finalizar Débitos:** Revisar o arquivo DEBITO\_TECNICO.md e implementar as lógicas que foram deixadas como // TODO.  
* \[ \] **Testes Funcionais:** Compilar o mod com sucesso e iniciar testes completos em jogo para validar todas as funcionalidades.  
* \[ \] **Revisão Final:** Realizar uma última passagem pelo código para limpeza e otimização.

## **3\. Roadmap Kanban Priorizado (v2)**

| Tarefa (To Do) | Em Progresso | Concluído |
| :---- | :---- | :---- |
| 1\. **\[Bloco 1\]** Refatorar TimelessAPI e AttachmentCacheProperty para remover o uso de Object. |  |  |
| 2\. **\[Bloco 2\]** Implementar o método type() em todas as classes de CustomPacketPayload. |  |  |
| 3\. **\[Bloco 3\]** Implementar streamCodec() e outros métodos abstratos em Serializadores e Receitas. |  |  |
| 4\. **\[Bloco 4\]** Corrigir todos os construtores de ImageButton e chamadas a renderToBuffer. |  |  |
| 5\. Revisar e corrigir todos os erros de @Override em classes de modificadores e entidades. |  |  |
| 6\. Corrigir erros de RecipeHolder e outros tipos incompatíveis. |  |  |
| 7\. Resolver os cannot find symbol restantes em registros (ModRecipe, Capabilities, etc.). |  |  |
| 8\. Habilitar e corrigir os arquivos restantes da lista PROGRESS.md. |  |  |
| 9\. Implementar a lógica pendente do arquivo DEBITO\_TECNICO.md. |  |  |
| 10\. Compilar o projeto com zero erros. |  |  |
| 11\. Realizar testes funcionais completos em single-player e multiplayer. |  |  |

