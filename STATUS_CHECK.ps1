# ============================================================================
# SCRIPT DE STATUS - TacZ Migration
# ============================================================================
# Este script mostra o status atual de todos os sistemas do projeto

param(
    [switch]$Detailed,
    [switch]$CheckBuild,
    [string]$System = ""
)

$baseDir = "src\main\java\com\tacz\guns"

# Mapeamento de sistemas
$systemMap = @{
    "main" = @("GunMod.java")
    "api" = @("api")
    "client" = @("client")
    "particles" = @("particles")
    "resource" = @("resource")
    "events" = @("event")
    "items" = @("item")
    "entities" = @("entity")
    "blocks" = @("block")
    "init" = @("init")
    "commands" = @("command")
    "config" = @("config")
    "compat" = @("compat")
    "crafting" = @("crafting")
    "debug" = @("debug")
    "inventory" = @("inventory")
    "mixins" = @("mixin")
    "sound" = @("sound")
    "utils" = @("util")
}

function Get-SystemStatus {
    param($SystemName, $Paths)
    
    $activeFiles = 0
    $disabledFiles = 0
    $totalFiles = 0
    $details = @()
    
    foreach ($path in $Paths) {
        $fullPath = Join-Path $baseDir $path
        
        if (Test-Path $fullPath) {
            if ((Get-Item $fullPath).PSIsContainer) {
                # É um diretório
                Get-ChildItem $fullPath -Recurse -Filter "*.java*" | ForEach-Object {
                    $totalFiles++
                    if ($_.Name.EndsWith(".disabled")) {
                        $disabledFiles++
                        if ($Detailed) {
                            $details += "  🔴 $($_.Name -replace '\.disabled$', '')"
                        }
                    } else {
                        $activeFiles++
                        if ($Detailed) {
                            $details += "  🟢 $($_.Name)"
                        }
                    }
                }
            } else {
                # É um arquivo
                $totalFiles++
                $activeFiles++
                if ($Detailed) {
                    $details += "  🟢 $(Split-Path $fullPath -Leaf)"
                }
            }
        }
        
        # Verificar versão desabilitada do arquivo
        $disabledPath = "$fullPath.disabled"
        if (Test-Path $disabledPath) {
            if (-not (Test-Path $fullPath)) {
                $totalFiles++
                $disabledFiles++
                if ($Detailed) {
                    $details += "  🔴 $(Split-Path $fullPath -Leaf)"
                }
            }
        }
    }
    
    return @{
        Name = $SystemName
        Active = $activeFiles
        Disabled = $disabledFiles
        Total = $totalFiles
        Status = if ($activeFiles -eq 0) { "DISABLED" } elseif ($disabledFiles -eq 0) { "ACTIVE" } else { "PARTIAL" }
        Details = $details
    }
}

function Show-SystemStatus {
    param($Status)
    
    $statusColor = switch ($Status.Status) {
        "ACTIVE" { "Green" }
        "DISABLED" { "Red" }
        "PARTIAL" { "Yellow" }
        default { "Gray" }
    }
    
    $statusIcon = switch ($Status.Status) {
        "ACTIVE" { "🟢" }
        "DISABLED" { "🔴" }
        "PARTIAL" { "🟡" }
        default { "⚪" }
    }
    
    Write-Host "$statusIcon " -NoNewline
    Write-Host $Status.Name.PadRight(12) -ForegroundColor White -NoNewline
    Write-Host $Status.Status.PadRight(10) -ForegroundColor $statusColor -NoNewline
    Write-Host "Active: $($Status.Active.ToString().PadLeft(3))" -ForegroundColor Green -NoNewline
    Write-Host " | " -NoNewline
    Write-Host "Disabled: $($Status.Disabled.ToString().PadLeft(3))" -ForegroundColor Red -NoNewline
    Write-Host " | " -NoNewline
    Write-Host "Total: $($Status.Total)" -ForegroundColor Cyan
    
    if ($Detailed -and $Status.Details.Count -gt 0) {
        $Status.Details | ForEach-Object { Write-Host $_ -ForegroundColor Gray }
        Write-Host ""
    }
}

function Test-BuildStatus {
    Write-Host "Testando compilação..." -ForegroundColor Yellow
    $buildStart = Get-Date
    
    $buildOutput = & .\gradlew compileJava --quiet 2>&1 | Out-String
    
    $buildEnd = Get-Date
    $duration = ($buildEnd - $buildStart).TotalSeconds
    
    if ($buildOutput -match "BUILD SUCCESSFUL") {
        Write-Host "🎉 BUILD SUCCESSFUL" -ForegroundColor Green -NoNewline
        Write-Host " (${duration}s)" -ForegroundColor Gray
        return $true
    } elseif ($buildOutput -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "❌ $errorCount errors" -ForegroundColor Red -NoNewline
        Write-Host " (${duration}s)" -ForegroundColor Gray
        return $false
    } else {
        Write-Host "❓ Status indeterminado" -ForegroundColor Yellow -NoNewline
        Write-Host " (${duration}s)" -ForegroundColor Gray
        return $null
    }
}

# ============================================================================
# MAIN SCRIPT
# ============================================================================

Write-Host ""
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  STATUS DOS SISTEMAS - TacZ Migration" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se estamos no diretório correto
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute este script na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

# Se um sistema específico foi solicitado
if ($System) {
    if ($System -notin $systemMap.Keys) {
        Write-Host "ERRO: Sistema '$System' não encontrado" -ForegroundColor Red
        Write-Host "Sistemas disponíveis: $($systemMap.Keys -join ', ')" -ForegroundColor Yellow
        exit 1
    }
    
    $status = Get-SystemStatus $System $systemMap[$System]
    Show-SystemStatus $status
    
    if ($CheckBuild) {
        Write-Host ""
        Test-BuildStatus | Out-Null
    }
    
    exit 0
}

# Status geral de todos os sistemas
Write-Host "Sistema".PadRight(13) -NoNewline -ForegroundColor White
Write-Host "Status".PadRight(11) -NoNewline -ForegroundColor White
Write-Host "Arquivos" -ForegroundColor White
Write-Host ("-" * 50) -ForegroundColor DarkGray

$totalStats = @{
    ActiveFiles = 0
    DisabledFiles = 0
    TotalFiles = 0
    ActiveSystems = 0
    DisabledSystems = 0
    PartialSystems = 0
}

foreach ($systemName in ($systemMap.Keys | Sort-Object)) {
    $status = Get-SystemStatus $systemName $systemMap[$systemName]
    Show-SystemStatus $status
    
    $totalStats.ActiveFiles += $status.Active
    $totalStats.DisabledFiles += $status.Disabled
    $totalStats.TotalFiles += $status.Total
    
    switch ($status.Status) {
        "ACTIVE" { $totalStats.ActiveSystems++ }
        "DISABLED" { $totalStats.DisabledSystems++ }
        "PARTIAL" { $totalStats.PartialSystems++ }
    }
}

# Resumo geral
Write-Host ""
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  RESUMO GERAL" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "SISTEMAS:" -ForegroundColor White
Write-Host "• Ativos: " -NoNewline -ForegroundColor Green
Write-Host $totalStats.ActiveSystems -ForegroundColor Green
Write-Host "• Parciais: " -NoNewline -ForegroundColor Yellow
Write-Host $totalStats.PartialSystems -ForegroundColor Yellow
Write-Host "• Desabilitados: " -NoNewline -ForegroundColor Red
Write-Host $totalStats.DisabledSystems -ForegroundColor Red
Write-Host ""

Write-Host "ARQUIVOS:" -ForegroundColor White
Write-Host "• Ativos: " -NoNewline -ForegroundColor Green
Write-Host $totalStats.ActiveFiles -ForegroundColor Green
Write-Host "• Desabilitados: " -NoNewline -ForegroundColor Red
Write-Host $totalStats.DisabledFiles -ForegroundColor Red
Write-Host "• Total: " -NoNewline -ForegroundColor Cyan
Write-Host $totalStats.TotalFiles -ForegroundColor Cyan

# Calcular percentual de migração
$migrationPercent = if ($totalStats.TotalFiles -gt 0) { 
    [math]::Round(($totalStats.DisabledFiles / $totalStats.TotalFiles) * 100, 1) 
} else { 0 }

Write-Host ""
Write-Host "PROGRESSO DE DESABILITAÇÃO: " -NoNewline -ForegroundColor White
Write-Host "$migrationPercent%" -ForegroundColor $(if ($migrationPercent -gt 80) { "Green" } elseif ($migrationPercent -gt 50) { "Yellow" } else { "Red" })

# Teste de build se solicitado
if ($CheckBuild) {
    Write-Host ""
    Write-Host "BUILD STATUS:" -ForegroundColor White
    Test-BuildStatus | Out-Null
}

Write-Host ""
Write-Host "COMANDOS ÚTEIS:" -ForegroundColor Cyan
Write-Host "• Status detalhado: .\STATUS_CHECK.ps1 -Detailed" -ForegroundColor White
Write-Host "• Com teste de build: .\STATUS_CHECK.ps1 -CheckBuild" -ForegroundColor White
Write-Host "• Sistema específico: .\STATUS_CHECK.ps1 -System api" -ForegroundColor White
Write-Host "• Restaurar sistema: .\SELECTIVE_RESTORE.ps1 -Systems main,init" -ForegroundColor White
Write-Host "• Desabilitar tudo: .\COMPLETE_DISABLE_SCRIPT.ps1" -ForegroundColor White
Write-Host ""
