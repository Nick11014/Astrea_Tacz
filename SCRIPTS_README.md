# 🛠️ Scripts de Migração TacZ - NeoForge 1.21.1

Este conjunto de scripts facilita a migração do mod TacZ de Forge 1.20.1 para NeoForge 1.21.1, permitindo desabilitar, restaurar e gerenciar arquivos problemáticos de forma controlada.

## 📁 Scripts Disponíveis

### 🔧 `COMPLETE_DISABLE_SCRIPT.ps1` - Script Principal de Desabilitação

**Função**: Desabilita TODOS os arquivos problemáticos para garantir que o build compile sem erros.

**Uso básico**:
```powershell
.\COMPLETE_DISABLE_SCRIPT.ps1
```

**Opções avançadas**:
```powershell
# Pular backup (mais rápido)
.\COMPLETE_DISABLE_SCRIPT.ps1 -SkipBackup

# Modo verboso (mais detalhes)
.\COMPLETE_DISABLE_SCRIPT.ps1 -Verbose

# Ambos
.\COMPLETE_DISABLE_SCRIPT.ps1 -SkipBackup -Verbose
```

**O que faz**:
- ✅ Cria backup automático (opcional)
- ✅ Desabilita ~350+ arquivos problemáticos
- ✅ Testa compilação automaticamente
- ✅ Gera log detalhado
- ✅ Cria script de restauração completa

**Resultado esperado**: `BUILD SUCCESSFUL`

---

### 🔄 `RESTORE_ALL_FILES.ps1` - Restauração Completa

**Função**: Restaura TODOS os arquivos desabilitados (criado automaticamente pelo script principal).

**Uso**:
```powershell
.\RESTORE_ALL_FILES.ps1
```

**⚠️ Atenção**: Após executar, o build voltará a ter erros!

---

### 🎯 `SELECTIVE_RESTORE.ps1` - Restauração Seletiva

**Função**: Restaura sistemas específicos de forma controlada para migração gradual.

**Ver sistemas disponíveis**:
```powershell
.\SELECTIVE_RESTORE.ps1 -ListSystems
```

**Restaurar sistemas específicos**:
```powershell
# Restaurar apenas arquivo principal e sistema de inicialização
.\SELECTIVE_RESTORE.ps1 -Systems main,init

# Restaurar APIs (cuidado: pode quebrar o build!)
.\SELECTIVE_RESTORE.ps1 -Systems api

# Teste seguro (não move arquivos)
.\SELECTIVE_RESTORE.ps1 -Systems api,client -DryRun

# Forçar sem confirmação
.\SELECTIVE_RESTORE.ps1 -Systems main -Force
```

**Sistemas disponíveis**:
- `main` - GunMod.java (arquivo principal)
- `api` - APIs legadas completas  
- `client` - Sistema de cliente/renderização
- `particles` - Sistema de partículas
- `resource` - Sistema de recursos
- `events` - Sistema de eventos
- `items` - Itens do mod
- `entities` - Entidades
- `blocks` - Blocos
- `init` - Sistema de inicialização
- `commands` - Comandos
- `config` - Configurações
- `compat` - Compatibilidade com outros mods
- `crafting` - Sistema de crafting
- `debug` - Ferramentas de debug
- `inventory` - Inventários customizados
- `mixins` - Mixins
- `sound` - Sistema de som
- `utils` - Utilitários

---

### 📊 `STATUS_CHECK.ps1` - Verificação de Status

**Função**: Mostra o status atual de todos os sistemas (ativo/desabilitado/parcial).

**Uso básico**:
```powershell
.\STATUS_CHECK.ps1
```

**Opções avançadas**:
```powershell
# Status detalhado (lista todos os arquivos)
.\STATUS_CHECK.ps1 -Detailed

# Incluir teste de compilação
.\STATUS_CHECK.ps1 -CheckBuild

# Status de um sistema específico
.\STATUS_CHECK.ps1 -System api

# Combinado
.\STATUS_CHECK.ps1 -System client -Detailed -CheckBuild
```

---

## 🚀 Fluxo de Trabalho Recomendado

### 1. **Preparação Inicial** (Executar UMA vez)
```powershell
# Desabilitar tudo para garantir build limpo
.\COMPLETE_DISABLE_SCRIPT.ps1
```

### 2. **Migração Gradual** (Fase por fase)
```powershell
# Verificar status atual
.\STATUS_CHECK.ps1

# Restaurar sistema principal para começar migração
.\SELECTIVE_RESTORE.ps1 -Systems main

# Testar compilação
.\gradlew compileJava

# Se der erro, investigar e corrigir antes de prosseguir
# Se OK, continuar com próximo sistema...

# Restaurar sistema de inicialização
.\SELECTIVE_RESTORE.ps1 -Systems init

# Testar novamente
.\gradlew compileJava
```

### 3. **Verificação Contínua**
```powershell
# Status geral
.\STATUS_CHECK.ps1 -CheckBuild

# Status detalhado de sistema específico
.\STATUS_CHECK.ps1 -System api -Detailed
```

### 4. **Reversão se Necessário**
```powershell
# Desabilitar tudo novamente se algo der errado
.\COMPLETE_DISABLE_SCRIPT.ps1 -SkipBackup

# Ou restaurar tudo (para debug)
.\RESTORE_ALL_FILES.ps1
```

---

## 📋 Ordem de Migração Recomendada

1. **Core básico**: `main` (GunMod.java)
2. **Registros**: `init` (DataComponents, registros básicos)
3. **APIs base**: `api` (sem dependências de cliente)
4. **Itens e entidades**: `items`, `entities`
5. **Sistema de recursos**: `resource`
6. **Cliente** (último): `client`, `particles`

---

## 📁 Arquivos Gerados

- **`disable_script_log.txt`** - Log detalhado do processo
- **`disabled_files_backup_YYYYMMDD_HHMMSS/`** - Backup dos arquivos originais
- **`RESTORE_ALL_FILES.ps1`** - Script de restauração completa (auto-gerado)

---

## ⚠️ Avisos Importantes

1. **SEMPRE teste a compilação** após restaurar qualquer sistema:
   ```powershell
   .\gradlew compileJava
   ```

2. **Mantenha backups**: Os scripts criam backups automáticos, mas mantenha seu próprio controle de versão.

3. **Migração gradual**: NÃO restaure tudo de uma vez. Migre sistema por sistema.

4. **Dependências**: Alguns sistemas dependem de outros. Se um sistema falhar, pode ser necessário migrar suas dependências primeiro.

5. **APIs do NeoForge**: Lembre-se de usar as novas APIs do NeoForge 1.21.1, especialmente:
   - DataComponents (substitui NBT)
   - Novo sistema de networking
   - Novas APIs de renderização

---

## 🆘 Solução de Problemas

### "Build ainda tem erros após executar COMPLETE_DISABLE_SCRIPT"
- Execute novamente: `.\COMPLETE_DISABLE_SCRIPT.ps1 -Verbose`
- Verifique o log: `disable_script_log.txt`
- Pode haver arquivos não cobertos pelos padrões

### "Script não funciona / erro de permissão"
```powershell
# Verificar política de execução
Get-ExecutionPolicy

# Se necessário, alterar (como admin)
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### "Build quebra após restaurar sistema"
- Execute o status: `.\STATUS_CHECK.ps1 -CheckBuild`
- Desabilite novamente: `.\COMPLETE_DISABLE_SCRIPT.ps1 -SkipBackup`
- Migre as dependências necessárias primeiro

---

## 📞 Referência Rápida

| Comando | Função |
|---------|--------|
| `.\COMPLETE_DISABLE_SCRIPT.ps1` | Desabilita tudo |
| `.\STATUS_CHECK.ps1` | Mostra status |
| `.\SELECTIVE_RESTORE.ps1 -ListSystems` | Lista sistemas |
| `.\SELECTIVE_RESTORE.ps1 -Systems main` | Restaura sistema |
| `.\RESTORE_ALL_FILES.ps1` | Restaura tudo |
| `.\gradlew compileJava` | Testa compilação |

---

*Scripts criados para facilitar a migração TacZ Forge 1.20.1 → NeoForge 1.21.1*
