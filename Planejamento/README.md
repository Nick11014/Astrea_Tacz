# Índice do Planejamento - Portabilidade TacZ para NeoForge 1.21.1

Este diretório contém o planejamento detalhado dividido em fases para a portabilidade do mod TacZ do Forge 1.20.1 para NeoForge 1.21.1.

## � Estrutura por Pastas:

### 🔧 [Fase 0: Configuração do Ambiente](Fase_0_Configuracao_Ambiente/)
- `planejamento.md` - Checklist detalhado
- `timeline_fase_0.md` - Timeline específica
- `impedimentos.md` - Registro de problemas

### 🏗️ [Fase 1: Migração do Core](Fase_1_Core_Migration/)
- `planejamento.md` - Migração para DataComponents
- `timeline_fase_1.md` - Progresso da fase
- `impedimentos.md` - Problemas encontrados

### 🎨 [Fase 2: Cliente e Renderização](Fase_2_Cliente_Renderizacao/)
- `planejamento.md` - Renderização e GUI
- `timeline_fase_2.md` - Animações e HUD
- `impedimentos.md` - Problemas de renderização

### 🌐 [Fase 3: Gameplay e Rede](Fase_3_Gameplay_Rede/)
- `planejamento.md` - Networking e gameplay
- `timeline_fase_3.md` - Multiplayer
- `impedimentos.md` - Problemas de rede

### 🔌 [Fase 4: Compatibilidade](Fase_4_Compatibilidade/)
- `planejamento.md` - Integrações JEI/KubeJS
- `timeline_fase_4.md` - Mods de compatibilidade
- `impedimentos.md` - Problemas de integração

### ✨ [Fase 5: Finalização](Fase_5_Finalizacao/)
- `planejamento.md` - Polimento final
- `timeline_fase_5.md` - Testes e build
- `impedimentos.md` - Problemas finais

## 📊 Acompanhamento:

### 📝 [Timeline do Claude](claude_timeline.md)
Registro detalhado de todas as ações realizadas durante o processo de portabilidade.

### 🚨 [Instruções para Claude](INSTRUCOES_CLAUDE.md)
Instruções permanentes sobre fluxo de trabalho, organização e sistema de impedimentos.

---

## 🛠️ Sistema de Impedimentos

Cada fase possui um arquivo `impedimentos.md` onde são registrados:
- **Problemas encontrados** durante o desenvolvimento
- **Soluções tentadas** antes de solicitar ajuda
- **Informações específicas** necessárias para resolução
- **Status** do impedimento (registrado → solicitado → resolvido)

### Como funciona:
1. ⏸️ **Pare** quando encontrar um problema que não consegue resolver
2. 📝 **Registre** o impedimento no arquivo da fase atual
3. 🔍 **Solicite** informações específicas necessárias
4. ⏳ **Aguarde** resolução antes de continuar

---

## 🎯 Objetivo Geral:
Portar com sucesso o mod TacZ do Forge 1.20.1 para NeoForge 1.21.1, mantendo todas as funcionalidades e garantindo estabilidade tanto em single-player quanto em multiplayer.

## 📈 Progresso Geral:
- [ ] Fase 0 - Configuração do Ambiente
- [ ] Fase 1 - Migração do Core  
- [ ] Fase 2 - Cliente e Renderização
- [ ] Fase 3 - Gameplay e Rede
- [ ] Fase 4 - Compatibilidade
- [ ] Fase 5 - Finalização
