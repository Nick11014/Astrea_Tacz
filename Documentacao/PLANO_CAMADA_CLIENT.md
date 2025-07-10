# **PLANO DE MIGRAÇÃO TÁTICO - FASE 2: CAMADA DE CLIENTE**

**Projeto:** Migração TacZ NeoForge 1.21.1  
**Status Atual:** Núcleo Comum Migrado, Camada de Cliente em Andamento  
**Data da Versão:** 2025-07-09

## **VISÃO GERAL**

Este documento detalha o plano de ação para a **Fase 2** da migração: a **Camada de Cliente**. Ele se baseia nas estratégias validadas durante a migração bem-sucedida do Núcleo Comum e nas descobertas técnicas da Onda 3.

---

## **FASE 1: NÚCLEO COMUM (CONCLUÍDA)**

**Status:** ✅ **MISSÃO CUMPRIDA**

A espinha dorsal completa de dados e gerenciamento de recursos do mod foi migrada com sucesso. Todos os 6 blocos do núcleo, incluindo o `CommonAssetsManager`, estão habilitados e compilando. Esta base sólida é o alicerce para a fase atual.

---

## **FASE 2: CAMADA DE CLIENTE (EM ANDAMENTO)**

A migração da camada de cliente será executada em **"Ondas"**, do mais simples ao mais complexo, para mitigar riscos e garantir progresso constante.

### **Estratégias Fundamentais**
* **POJOs Primeiro:** Habilitar todas as estruturas de dados puras antes de qualquer lógica complexa ou de renderização.
* **Evitar Renderização Inicialmente:** Isolar as APIs de renderização do NeoForge 1.21.1, que mudaram significativamente, para uma fase dedicada.
* **Implementação Mínima Agressiva:** Usar placeholders e stubs de forma ainda mais intensa para quebrar dependências e manter a compilação.

### **Checklist de Execução por Ondas**

#### **ONDA 1: POJOs e Estruturas de Dados**
* **Objetivo:** Estabelecer o vocabulário de dados do cliente.
* **Sub-Bloco 7A: POJOs de Modelos**
    * [x] `BedrockVersion.java`
    * [x] `BedrockModelPOJO.java`
    * [ ] `Description.java`
    * [ ] `GeometryModelNew.java`
    * [ ] `GeometryModelLegacy.java`
    * [ ] `BonesItem.java`
    * [ ] `CubesItem.java`
    * [ ] `FaceItem.java`
    * [ ] `FaceUVsItem.java`
* **Sub-Bloco 7B: POJOs de Display**
    * [ ] `IDisplay.java`
    * [x] `LaserConfig.java`
    * [ ] `gun/GunDisplay.java`
    * [ ] `attachment/AttachmentDisplay.java`
    * [ ] `ammo/AmmoDisplay.java`

#### **ONDA 2: Gerenciadores de Cliente**
* **Objetivo:** Habilitar os gerenciadores de assets do lado do cliente.
* **Arquivos-Chave:**
    * [x] `ClientIndexManager.java` (Habilitado com implementação mínima)
    * [ ] `ClientAssetsManager.java`

#### **ONDA 3: Modelos Não-Renderização**
* **Objetivo:** Habilitar as classes de modelo que não invocam diretamente a API de renderização.
* **Arquivos-Chave:**
    * [ ] `BedrockModel.java` (Depende da correção do bloqueio de renderização)

#### **ONDA 4: Renderização (Fase Crítica)**
* **Objetivo:** Migrar todo o sistema de renderização, tratando como uma fase especializada.

---

## **TAREFA PARALELA: GERENCIAMENTO DO DÉBITO TÉCNICO**

A restauração das implementações mínimas seguirá o plano detalhado no `DEBITO_TECNICO.md`.

### **Plano de Restauração por Fases**

1.  **Fase A: Consolidação do GSON (Próxima Prioridade):** Restaurar o uso de `CommonAssetsManager.GSON` em todos os gerenciadores.
2.  **Fase B: Expansão da Camada de Cliente:** Habilitar e restaurar a lógica dos gerenciadores e índices do cliente.
3.  **Fase C: Migração de APIs de Renderização (Crítica):** Tarefa especializada para migrar o sistema de modelos Bedrock.
4.  **Fase D: Sistema de Modificadores:** Habilitar `AttachmentPropertyManager` e restaurar a lógica de modificadores em `GunData`.
5.  **Fase E: Sistemas Avançados:** Migrar e restaurar os sistemas de networking e crafting.

---

## **🚨 BLOQUEIO CRÍTICO MAPEADO: API de Renderização NeoForge 1.21.1**

* **Problema:** A assinatura do método `VertexConsumer.vertex()` foi alterada, quebrando todo o sistema de renderização de baixo nível.
* **Impacto:** Impede a habilitação de `BedrockCubeBox`, `BedrockCubePerFace` e, consequentemente, `BedrockModel`.
* **Ação:** Este problema **não pode ser resolvido com implementação mínima** e foi isolado como uma fase de trabalho separada que requer conhecimento específico da nova API.

---

## **PRINCÍPIOS GUIAS DA MIGRAÇÃO**

Continuaremos a aplicar as estratégias que provaram ser eficazes:

1.  **Implementação Mínima Cirúrgica:** Isolar complexidade, manter a compilação.
2.  **Quebra de Dependência Circular:** Habilitar arquivos em ordem topológica usando placeholders.
3.  **Simplificação Drástica:** Criar versões placeholder para arquivos extremamente complexos.
4.  **TODOs Estratégicos:** Usar `// TODO: [MIGRAÇÃO]` para rastrear o débito técnico.

Com este plano, temos um caminho claro e realista para atacar a camada de cliente e concluir a migração com sucesso.