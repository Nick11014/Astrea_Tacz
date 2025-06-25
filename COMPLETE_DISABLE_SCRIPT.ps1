# ============================================================================
# SCRIPT COMPLETO DE DESABILITAÇÃO - TacZ Migration to NeoForge 1.21.1
# ============================================================================
# 
# Este script desabilita TODOS os arquivos problemáticos que impedem a compilação
# durante a migração de Forge 1.20.1 para NeoForge 1.21.1
#
# COMO USAR:
# 1. Abra PowerShell na pasta do projeto
# 2. Execute: .\COMPLETE_DISABLE_SCRIPT.ps1
# 3. Aguarde a conclusão (BUILD SUCCESSFUL)
#
# PARA RESTAURAR:
# Execute: .\RESTORE_ALL_FILES.ps1 (criado automaticamente)
#
# ============================================================================

param(
    [switch]$SkipBackup,
    [switch]$Verbose
)

# Configurações
$ErrorActionPreference = "Continue"
$baseDir = "src\main\java\com\tacz\guns"
$backupDir = "disabled_files_backup_$(Get-Date -Format 'yyyyMMdd_HHmmss')"
$logFile = "disable_script_log.txt"

# Contadores
$totalDisabled = 0
$totalErrors = 0

# Função para logging
function Write-Log {
    param($Message, $Level = "INFO")
    $timestamp = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $logEntry = "[$timestamp] [$Level] $Message"
    
    # Console output com cores
    switch ($Level) {
        "SUCCESS" { Write-Host $logEntry -ForegroundColor Green }
        "WARNING" { Write-Host $logEntry -ForegroundColor Yellow }
        "ERROR"   { Write-Host $logEntry -ForegroundColor Red }
        "INFO"    { Write-Host $logEntry -ForegroundColor Cyan }
        default   { Write-Host $logEntry }
    }
    
    # Log para arquivo
    $logEntry | Add-Content $logFile -Encoding UTF8
}

# Função para criar backup
function Create-Backup {
    if ($SkipBackup) {
        Write-Log "Backup pulado conforme solicitado" "WARNING"
        return
    }
    
    Write-Log "Criando backup em: $backupDir"
    
    if (-not (Test-Path $backupDir)) {
        New-Item -ItemType Directory -Path $backupDir -Force | Out-Null
    }
    
    # Backup de todos os arquivos .java
    Get-ChildItem $baseDir -Recurse -Filter "*.java" | ForEach-Object {
        $relativePath = $_.FullName.Replace((Get-Location).Path + "\$baseDir\", "")
        $backupPath = Join-Path $backupDir $relativePath
        $backupDirPath = Split-Path $backupPath -Parent
        
        if (-not (Test-Path $backupDirPath)) {
            New-Item -ItemType Directory -Path $backupDirPath -Force | Out-Null
        }
        
        Copy-Item $_.FullName $backupPath -Force
    }
    
    Write-Log "Backup concluído: $((Get-ChildItem $backupDir -Recurse -Filter '*.java' | Measure-Object).Count) arquivos" "SUCCESS"
}

# Função para desabilitar arquivo
function Disable-File {
    param($FilePath, $Context = "")
    
    if (-not (Test-Path $FilePath)) {
        if ($Verbose) { Write-Log "Arquivo não encontrado: $FilePath" "WARNING" }
        return $false
    }
    
    $disabledPath = "$FilePath.disabled"
    if (Test-Path $disabledPath) {
        if ($Verbose) { Write-Log "Já desabilitado: $(Split-Path $FilePath -Leaf)" }
        return $false
    }
    
    try {
        Move-Item $FilePath $disabledPath -Force
        $script:totalDisabled++
        $fileName = Split-Path $FilePath -Leaf
        $contextInfo = if ($Context) { "[$Context] " } else { "" }
        Write-Log "${contextInfo}✓ $fileName" "SUCCESS"
        return $true
    }
    catch {
        $script:totalErrors++
        Write-Log "Erro ao desabilitar $FilePath : $_" "ERROR"
        return $false
    }
}

# Função para desabilitar diretório completo
function Disable-Directory {
    param($DirectoryPath, $Context = "")
    
    $fullPath = Join-Path $baseDir $DirectoryPath
    if (-not (Test-Path $fullPath)) {
        Write-Log "Diretório não encontrado: $DirectoryPath" "WARNING"
        return
    }
    
    Write-Log "Desabilitando diretório: $DirectoryPath" "INFO"
    
    Get-ChildItem $fullPath -Recurse -Filter "*.java" | ForEach-Object {
        Disable-File $_.FullName $Context
    }
}

# ============================================================================
# INÍCIO DO SCRIPT PRINCIPAL
# ============================================================================

Write-Host ""
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  TacZ COMPLETE DISABLE SCRIPT v2.0" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se estamos no diretório correto
if (-not (Test-Path "build.gradle")) {
    Write-Log "ERRO: Execute este script na pasta raiz do projeto (onde está o build.gradle)" "ERROR"
    exit 1
}

# Inicializar log
"TACZ DISABLE SCRIPT LOG - $(Get-Date)" | Set-Content $logFile -Encoding UTF8

Write-Log "Iniciando processo de desabilitação massiva"
Write-Log "Diretório base: $baseDir"

# Criar backup se solicitado
Create-Backup

Write-Log "Iniciando desabilitação sistemática..."

# ============================================================================
# FASE 1: DIRETÓRIOS PRINCIPAIS (APIs e Sistemas Complexos)
# ============================================================================

Write-Log "=== FASE 1: APIs e Sistemas Principais ===" "INFO"

$mainDirectories = @(
    "api",           # APIs legadas completas  
    "client",        # Todo sistema de cliente
    "particles",     # Sistema de partículas
    "resource"       # Sistema de recursos
)

foreach ($dir in $mainDirectories) {
    Disable-Directory $dir "FASE1"
}

# ============================================================================
# FASE 2: SISTEMAS DE SUPORTE
# ============================================================================

Write-Log "=== FASE 2: Sistemas de Suporte ===" "INFO"

$supportDirectories = @(
    "command",       # Comandos
    "compat",        # Compatibilidade 
    "config",        # Configurações
    "crafting",      # Sistema de crafting
    "debug",         # Ferramentas de debug
    "event",         # Sistema de eventos
    "inventory",     # Inventários customizados
    "mixin",         # Mixins
    "sound",         # Sistema de som
    "util"           # Utilitários
)

foreach ($dir in $supportDirectories) {
    Disable-Directory $dir "FASE2"
}

# ============================================================================
# FASE 3: ENTIDADES E ITENS
# ============================================================================

Write-Log "=== FASE 3: Entidades e Itens ===" "INFO"

# Desabilitar entidades específicas
$entityFiles = @(
    "entity\TargetMinecart.java",
    "entity\EntityKineticBullet.java"
)

foreach ($file in $entityFiles) {
    $fullPath = Join-Path $baseDir $file
    Disable-File $fullPath "ENTIDADE"
}

# Desabilitar todo o diretório entity/sync
Disable-Directory "entity\sync" "ENTITY-SYNC"

# Desabilitar itens específicos problemáticos
$itemFiles = @(
    "item\ModernKineticGunItem.java",
    "item\ModernKineticGunScriptAPI.java",
    "item\AmmoBoxItem.java",
    "item\AmmoItem.java",
    "item\AttachmentItem.java",
    "item\DefaultTableItem.java", 
    "item\GunSmithTableItem.java",
    "item\GunTooltipPart.java",
    "item\TargetMinecartItem.java"
)

foreach ($file in $itemFiles) {
    $fullPath = Join-Path $baseDir $file
    Disable-File $fullPath "ITEM"
}

# ============================================================================
# FASE 4: SISTEMA DE INICIALIZAÇÃO
# ============================================================================

Write-Log "=== FASE 4: Sistema de Inicialização ===" "INFO"

# Desabilitar todo o diretório init
Disable-Directory "init" "INIT"

# ============================================================================
# FASE 5: ARQUIVOS PRINCIPAIS E BLOCOS
# ============================================================================

Write-Log "=== FASE 5: Arquivos Principais ===" "INFO"

# Arquivo principal do mod
$mainFiles = @(
    "GunMod.java"
)

foreach ($file in $mainFiles) {
    $fullPath = Join-Path $baseDir $file
    Disable-File $fullPath "MAIN"
}

# Diretório de blocos
Disable-Directory "block" "BLOCK"

# ============================================================================
# VERIFICAÇÃO FINAL
# ============================================================================

Write-Log "=== VERIFICAÇÃO FINAL ===" "INFO"

Write-Log "Testando compilação..."
$compileStart = Get-Date
$compileResult = & .\gradlew compileJava 2>&1 | Out-String
$compileEnd = Get-Date
$compileDuration = ($compileEnd - $compileStart).TotalSeconds

# Verificar resultado
if ($compileResult -match "BUILD SUCCESSFUL") {
    Write-Log "🎉 BUILD SUCCESSFUL! (${compileDuration}s)" "SUCCESS"
    $buildStatus = "SUCCESS"
} elseif ($compileResult -match "(\d+) errors") {
    $errorCount = $matches[1]
    Write-Log "⚠️ $errorCount erros restantes (${compileDuration}s)" "WARNING"
    $buildStatus = "PARTIAL - $errorCount errors"
} else {
    Write-Log "❌ Build falhou (${compileDuration}s)" "ERROR"
    $buildStatus = "FAILED"
}

# ============================================================================
# RELATÓRIO FINAL
# ============================================================================

Write-Log "=== RELATÓRIO FINAL ===" "INFO"

$finalReport = @"
RELATÓRIO DE DESABILITAÇÃO COMPLETA
===================================

Data/Hora: $(Get-Date)
Duração: $((Get-Date) - $compileStart | Select-Object -ExpandProperty TotalMinutes | ForEach-Object { [math]::Round($_, 2) }) minutos

ESTATÍSTICAS:
- Arquivos desabilitados: $totalDisabled
- Erros durante processo: $totalErrors  
- Status final do build: $buildStatus

SISTEMAS DESABILITADOS:
✓ APIs legadas (api/*)
✓ Sistema de cliente completo (client/*)
✓ Sistema de partículas (particles/*)
✓ Sistema de recursos (resource/*)
✓ Comandos, config, crafting
✓ Compatibilidade com mods
✓ Sistema de eventos
✓ Mixins
✓ Entidades e itens
✓ Sistema de inicialização
✓ Arquivo principal (GunMod.java)

PRÓXIMOS PASSOS:
1. Implementar DataComponents
2. Migrar networking para NeoForge 1.21.1  
3. Reabilitar sistemas gradualmente
4. Testar cada reativação

PARA RESTAURAR TUDO:
Execute: .\RESTORE_ALL_FILES.ps1

"@

Write-Host $finalReport -ForegroundColor Green
$finalReport | Add-Content $logFile -Encoding UTF8

# ============================================================================
# CRIAR SCRIPT DE RESTAURAÇÃO
# ============================================================================

$restoreScript = @'
# ============================================================================
# SCRIPT DE RESTAURAÇÃO - TacZ Migration
# ============================================================================
# Este script restaura TODOS os arquivos desabilitados pelo COMPLETE_DISABLE_SCRIPT.ps1

Write-Host "=========================================" -ForegroundColor Yellow
Write-Host "  RESTAURANDO ARQUIVOS DESABILITADOS" -ForegroundColor Yellow  
Write-Host "=========================================" -ForegroundColor Yellow

$restoredCount = 0
$errorCount = 0

Get-ChildItem -Recurse -Filter "*.disabled" | ForEach-Object {
    $originalPath = $_.FullName -replace "\.disabled$", ""
    
    try {
        Move-Item $_.FullName $originalPath -Force
        $restoredCount++
        Write-Host "✓ Restaurado: $(Split-Path $originalPath -Leaf)" -ForegroundColor Green
    }
    catch {
        $errorCount++
        Write-Host "✗ Erro ao restaurar: $(Split-Path $originalPath -Leaf) - $_" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "RESTAURAÇÃO CONCLUÍDA:" -ForegroundColor Yellow
Write-Host "- Arquivos restaurados: $restoredCount" -ForegroundColor Green
Write-Host "- Erros: $errorCount" -ForegroundColor $(if ($errorCount -eq 0) { "Green" } else { "Red" })

if ($restoredCount -gt 0) {
    Write-Host ""
    Write-Host "⚠️ ATENÇÃO: O build provavelmente terá erros novamente!" -ForegroundColor Yellow
    Write-Host "Execute .\COMPLETE_DISABLE_SCRIPT.ps1 para desabilitar novamente se necessário." -ForegroundColor Cyan
}
'@

$restoreScript | Set-Content "RESTORE_ALL_FILES.ps1" -Encoding UTF8

Write-Log "Script de restauração criado: RESTORE_ALL_FILES.ps1" "SUCCESS"

# ============================================================================
# FINALIZAÇÃO
# ============================================================================

Write-Host ""
Write-Host "=========================================" -ForegroundColor Green
Write-Host "  SCRIPT CONCLUÍDO COM SUCESSO!" -ForegroundColor Green
Write-Host "=========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Arquivos importantes criados:" -ForegroundColor Cyan
Write-Host "• $logFile - Log completo do processo" -ForegroundColor White
Write-Host "• RESTORE_ALL_FILES.ps1 - Para restaurar tudo" -ForegroundColor White
if (-not $SkipBackup) {
    Write-Host "• $backupDir/ - Backup dos arquivos originais" -ForegroundColor White
}
Write-Host ""

if ($buildStatus -eq "SUCCESS") {
    Write-Host "🚀 PROJETO PRONTO PARA FASE 1 DA MIGRAÇÃO!" -ForegroundColor Green
} else {
    Write-Host "⚠️ Verifique o log para detalhes dos problemas restantes." -ForegroundColor Yellow
}

Write-Host ""
