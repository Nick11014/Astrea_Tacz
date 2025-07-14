# **Plano de Migração \- TacZ para NeoForge 1.21.1**

Data: 14/07/2025  
Versão do Documento: 1.0  
Objetivo: Estruturar e priorizar as tarefas restantes para a migração completa do mod TacZ, resolvendo os 502 erros de compilação atuais, eliminando débitos técnicos e garantindo a funcionalidade na nova versão do NeoForge.

## **1\. Análise da Situação Atual**

Com base nos documentos fornecidos (PROGRESS.md, DEBITO\_TECNICO.md, problems-report.html e diagram.md), a situação é a seguinte:

* **Progresso:** 64.9% dos arquivos (395 de 609\) estão habilitados, mas muitos com implementação mínima.  
* **Erros Críticos:** Existem **502 erros** de compilação que impedem o build do projeto. A maioria está concentrada em APIs que foram removidas ou drasticamente alteradas no NeoForge 1.21.1.  
* **Débito Técnico:** Várias classes foram habilitadas usando a "Object Strategy" ou contêm placeholders e TODOs que precisam ser resolvidos.  
* **Dependências:** O diagram.md mostra uma alta interconectividade, onde a falha em classes centrais (como NetworkHandler, TimelessAPI, e classes de renderização) causa um efeito cascata de erros.

## **2\. Fases da Migração**

O plano está dividido em quatro fases sequenciais para garantir uma abordagem estruturada.

### **Fase 1: Resolução de Erros Críticos de Compilação (O Desafio dos 500 Erros)**

O foco principal desta fase é tornar o projeto compilável novamente. A análise do problems-report.html revela padrões de erros que podem ser resolvidos em blocos.

**Prioridade 1: Sistema de Networking (NetworkHandler)**

* **Problema:** O erro mais recorrente é cannot find symbol: class NetworkHandler. O sistema de rede do Forge foi completamente substituído pelo sistema de Payloads e StreamCodec do NeoForge.  
* **Solução:**  
  1. Remover todas as chamadas ao antigo NetworkHandler.CHANNEL.send....  
  2. Para cada mensagem (ex: ClientMessagePlayerAim), criar um CustomPacketPayload e registrá-lo no PayloadRegistrar.  
  3. Utilizar StreamCodec para serializar e desserializar os dados dos pacotes, eliminando o uso de FriendlyByteBuf diretamente quando possível.  
  4. Substituir PacketDistributor pelas novas implementações, como PacketDistributor.sendToPlayer(player, payload).  
* **Arquivos-chave para corrigir:** GunMod.java, LocalPlayer classes, LivingEntity shooter classes, e todas as classes no pacote network.message.

**Prioridade 2: Construtores e Métodos de API Removidos/Alterados**

* **Problema:** Erros como no suitable constructor found for ImageButton, method does not override or implement a method from a supertype, e chamadas a métodos que não existem mais (getDeltaTracker, getFrameTime, setHoverName).  
* **Solução:**  
  1. **ImageButton:** Atualizar os construtores para usar WidgetSprites em vez de ResourceLocation e coordenadas de textura separadas.  
  2. **@Override:** Revisar todas as classes que implementam interfaces ou estendem classes do Minecraft/NeoForge. O problems-report.html lista exatamente quais métodos precisam ser implementados ou removidos. Por exemplo, RecipeSerializer agora requer streamCodec().  
  3. **APIs de Renderização:** Substituir chamadas diretas a RenderSystem.getModelViewStack() por GuiGraphics. O método render em widgets agora é final e não pode ser sobrescrito; a lógica deve ser movida para outros métodos apropriados.  
  4. **ResourceLocation:** O construtor new ResourceLocation("id") foi substituído por ResourceLocation.fromNamespaceAndPath("namespace", "path"). É crucial corrigir isso em todos os locais.

**Prioridade 3: Sistema de Eventos**

* **Problema:** Os eventos e a forma como são postados e cancelados mudaram. Erros como bad operand type GunFireEvent for unary operator '\!' e isCanceled() não encontrado.  
* **Solução:**  
  1. A chamada NeoForge.EVENT\_BUS.post(event) agora retorna o próprio evento.  
  2. A verificação de cancelamento deve ser feita em uma linha separada: if (event.isCancelable() && event.isCanceled()).  
  3. Verificar se os eventos ainda existem ou se foram substituídos por equivalentes no NeoForge.

### **Fase 2: Quitação do Débito Técnico e Finalização do Núcleo**

Com o projeto compilando, o foco muda para a correção das implementações mínimas e placeholders.

**Checklist de Débitos (baseado em DEBITO\_TECNICO.md):**

* \[ \] **Object Strategy:** Substituir todos os usos de Object por tipos concretos. O diagram.md ajudará a identificar as dependências corretas.  
  * **Arquivos Críticos:** ClientGunIndex, CommonGunIndex, ClientAttachmentIndex. A funcionalidade completa desses índices é necessária para desbloquear a migração de renderizadores e modificadores.  
* \[ \] **IAttachmentModifier e Modificadores:** Implementar a lógica completa para os modificadores (DamageModifier, AdsModifier, RecoilModifier, etc.), substituindo os placeholders.  
* \[ \] **Renderizadores:**  
  * **AttachmentItemRenderer:** Finalizar a implementação, conectando-a ao BedrockAttachmentModel.  
  * **AnimateGeoItemRenderer:** Corrigir a lógica de animação e o gerenciamento de estado (AnimationStateMachine).  
  * **GunItemRendererWrapper:** Garantir que todos os modos de renderização (primeira pessoa, terceira pessoa, inventário) funcionem corretamente.  
* \[ \] **Placeholders e TODOs:** Realizar uma busca global por // TODO e // FIXME e resolver cada um deles, especialmente os relacionados a dados e lógica de jogo.

### **Fase 3: Migração de Funcionalidades Restantes**

Com o núcleo estável, a migração dos arquivos desabilitados pode começar.

**Ordem Sugerida (baseada em PROGRESS.md e dependências):**

1. **Sistema de Entidades:**  
   * \[ \] EntityKineticBullet.java: Corrigir a lógica de colisão, dano e sincronização de dados.  
   * \[ \] TargetMinecart.java e StatueBlock.java: Atualizar a lógica de interação e renderização.  
2. **Sistema de Crafting:**  
   * \[ \] GunSmithTableRecipe.java e GunSmithTableSerializer.java: Migrar para o novo sistema de receitas e serializadores do NeoForge.  
3. **GUI (Interface Gráfica):**  
   * \[ \] GunSmithTableScreen.java e GunRefitScreen.java: Finalizar a migração de todos os widgets, botões e lógica de interação, garantindo que a comunicação com o servidor (via pacotes) esteja funcional.  
4. **API de Scripting (Lua):**  
   * \[ \] ModernKineticGunScriptAPI.java: Revisar e adaptar todos os bindings da API para garantir que funcionem com as novas classes e lógicas do mod.  
5. **Compatibilidade com Outros Mods:**  
   * \[ \] JEI e Controllable: Revisar e corrigir a integração com as versões mais recentes dessas dependências.

### **Fase 4: Refinamento e Testes**

* \[ \] **Testes Funcionais:** Executar testes completos em single-player e multiplayer para validar todas as funcionalidades: disparo, recarga, mira, modificadores, crafting, etc.  
* \[ \] **Revisão de Código:** Realizar uma revisão final para garantir a consistência do código, remover soluções temporárias e otimizar o desempenho.  
* \[ \] **Documentação:** Atualizar a documentação interna para refletir as mudanças da nova arquitetura.

## **3\. Roadmap Kanban Priorizado**

| Tarefa (To Do) | Em Progresso | Concluído |
| :---- | :---- | :---- |
| 1\. **\[Bloco 1\]** Refatorar todo o sistema de rede para usar Payloads e StreamCodec. |  |  |
| 2\. **\[Bloco 2\]** Corrigir todos os construtores de ImageButton e ResourceLocation. |  |  |
| 3\. **\[Bloco 3\]** Implementar os métodos de interface ausentes (@Override), como streamCodec() em serializadores. |  |  |
| 4\. **\[Bloco 4\]** Atualizar todas as chamadas de eventos para o novo padrão do NeoForge. |  |  |
| 5\. Refatorar ClientGunIndex e CommonGunIndex para remover a "Object Strategy". |  |  |
| 6\. Implementar a lógica completa dos principais modificadores de armas (Damage, Ads, Recoil). |  |  |
| 7\. Finalizar a migração dos renderizadores (GunItemRendererWrapper, AttachmentItemRenderer). |  |  |
| 8\. Corrigir e habilitar EntityKineticBullet. |  |  |
| 9\. Migrar o sistema de crafting da GunSmithTable. |  |  |
| 10\. Finalizar as telas de GUI (GunSmithTableScreen, GunRefitScreen). |  |  |
| 11\. Revisar e testar a API de Scripting (Lua). |  |  |
| 12\. Teste de integração completo (Single-player e Multiplayer). |  |  |

