# Instruções para Claude - Portabilidade TacZ para NeoForge 1.21.1

## 📋 Estrutura do Projeto

### 📁 Organização das Pastas por Fase:
```
Planejamento/
├── INSTRUCOES_CLAUDE.md (este arquivo)
├── claude_timeline.md (timeline geral)
├── README.md (índice principal)
├── Fase_0_Configuracao_Ambiente/
│   ├── planejamento.md
│   ├── timeline_fase_0.md
│   └── impedimentos.md
├── Fase_1_Core_Migration/
│   ├── planejamento.md
│   ├── timeline_fase_1.md
│   └── impedimentos.md
├── Fase_2_Cliente_Renderizacao/
│   ├── planejamento.md
│   ├── timeline_fase_2.md
│   └── impedimentos.md
├── Fase_3_Gameplay_Rede/
│   ├── planejamento.md
│   ├── timeline_fase_3.md
│   └── impedimentos.md
├── Fase_4_Compatibilidade/
│   ├── planejamento.md
│   ├── timeline_fase_4.md
│   └── impedimentos.md
└── Fase_5_Finalizacao/
    ├── planejamento.md
    ├── timeline_fase_5.md
    └── impedimentos.md
```

## 🛠️ Fluxo de Trabalho

### Durante o desenvolvimento:
1. **SEMPRE** atualizar o `timeline_fase_X.md` da fase atual com as ações realizadas
2. **SEMPRE** atualizar o `claude_timeline.md` geral com progresso importante
3. **SEMPRE** marcar itens concluídos no `planejamento.md` da fase

### Quando encontrar impedimentos:
1. **PARE** o trabalho atual
2. **REGISTRE** o impedimento no arquivo `impedimentos.md` da fase
3. **SOLICITE** ajuda com informações específicas necessárias
4. **AGUARDE** resolução antes de continuar

## 📝 Template de Impedimento

Quando encontrar um impedimento, registre no formato:

```markdown
## Impedimento #X - [Data]

### 📍 Contexto:
- **Fase:** X - Nome da Fase
- **Item do Checklist:** [Item específico que estava sendo trabalhado]
- **Arquivo(s) Afetado(s):** [Lista de arquivos]

### 🚫 Problema Encontrado:
[Descrição detalhada do problema]

### 🔍 O que foi tentado:
1. [Ação 1]
2. [Ação 2]
3. [Ação 3]

### 📋 Informações Necessárias:
[Liste especificamente que informações você precisa]
- Código da classe X
- Documentação da API Y
- Versões corretas das dependências
- etc.

### 🎯 Status:
- [ ] Impedimento registrado
- [ ] Informações solicitadas
- [ ] Informações recebidas
- [ ] Impedimento resolvido
```

## 🎯 Prioridades de Trabalho

1. **Fase 0:** Configuração do ambiente - CRÍTICO
2. **Fase 1:** Core e DataComponents - CRÍTICO
3. **Fase 2:** Renderização - ALTO RISCO
4. **Fase 3:** Gameplay - ESSENCIAL
5. **Fase 4:** Compatibilidade - OPCIONAL
6. **Fase 5:** Finalização - POLIMENTO

## 🚨 Regras Importantes

### ❌ NÃO FAÇA:
- Não assuma APIs ou métodos sem verificar
- Não continue se encontrar um erro crítico
- Não deixe de documentar impedimentos
- Não pule fases ou itens críticos

### ✅ SEMPRE FAÇA:
- Documente todas as mudanças nos timelines
- Registre impedimentos imediatamente
- Solicite informações específicas quando necessário
- Mantenha os checklists atualizados
- Teste cada mudança quando possível

## 📞 Solicitação de Informações

Quando precisar de informações, use este formato:

```
🔍 SOLICITAÇÃO DE INFORMAÇÕES

**Fase:** X - Nome da Fase
**Impedimento:** #X
**Arquivos Necessários:** 
- caminho/para/arquivo1.java
- caminho/para/arquivo2.java

**Contexto:** [Explique o que você está tentando fazer]
**Informação Específica:** [O que exatamente você precisa ver]
```

---

## 📋 Status Atual do Projeto:
- ✅ Estrutura de planejamento criada
- ⏳ Aguardando início da Fase 0

**Última atualização:** 19 de Junho de 2025
