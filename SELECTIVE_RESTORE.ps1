# ============================================================================
# SCRIPT DE RESTAURAÇÃO SELETIVA - TacZ Migration 
# ============================================================================
# Este script permite restaurar sistemas específicos de forma controlada
# para facilitar a migração gradual

param(
    [string[]]$Systems = @(),
    [switch]$ListSystems,
    [switch]$DryRun,
    [switch]$Force
)

# Mapeamento de sistemas para diretórios/arquivos
$systemMap = @{
    "main" = @("GunMod.java")
    "api" = @("api\*")
    "client" = @("client\*")
    "particles" = @("particles\*")
    "resource" = @("resource\*")
    "events" = @("event\*", "api\event\*")
    "items" = @("item\*")
    "entities" = @("entity\*")
    "blocks" = @("block\*")
    "init" = @("init\*")
    "commands" = @("command\*")
    "config" = @("config\*")
    "compat" = @("compat\*")
    "crafting" = @("crafting\*")
    "debug" = @("debug\*")
    "inventory" = @("inventory\*")
    "mixins" = @("mixin\*")
    "sound" = @("sound\*")
    "utils" = @("util\*")
}

$baseDir = "src\main\java\com\tacz\guns"

function Show-AvailableSystems {
    Write-Host ""
    Write-Host "=========================================" -ForegroundColor Cyan
    Write-Host "  SISTEMAS DISPONÍVEIS PARA RESTAURAÇÃO" -ForegroundColor Cyan
    Write-Host "=========================================" -ForegroundColor Cyan
    Write-Host ""
    
    foreach ($system in $systemMap.Keys | Sort-Object) {
        $paths = $systemMap[$system] -join ", "
        $disabledCount = 0
        
        foreach ($pattern in $systemMap[$system]) {
            $searchPath = Join-Path $baseDir $pattern.Replace("*", "").TrimEnd("\")
            if (Test-Path $searchPath) {
                $disabledCount += (Get-ChildItem $searchPath -Recurse -Filter "*.disabled" -ErrorAction SilentlyContinue | Measure-Object).Count
            }
        }
        
        Write-Host "• $system" -ForegroundColor Yellow -NoNewline
        Write-Host " ($disabledCount arquivos desabilitados)" -ForegroundColor Gray
        Write-Host "  Localização: $paths" -ForegroundColor DarkGray
        Write-Host ""
    }
    
    Write-Host "EXEMPLOS DE USO:" -ForegroundColor Green
    Write-Host ".\SELECTIVE_RESTORE.ps1 -Systems main,init" -ForegroundColor White
    Write-Host ".\SELECTIVE_RESTORE.ps1 -Systems api -DryRun" -ForegroundColor White
    Write-Host ".\SELECTIVE_RESTORE.ps1 -ListSystems" -ForegroundColor White
    Write-Host ""
}

function Restore-SystemFiles {
    param($SystemName, $Patterns)
    
    Write-Host "Restaurando sistema: $SystemName" -ForegroundColor Yellow
    $restoredCount = 0
    $errorCount = 0
    
    foreach ($pattern in $Patterns) {
        $searchPath = Join-Path $baseDir $pattern.Replace("*", "")
        
        if ($pattern.EndsWith("*")) {
            # Padrão de diretório
            $dirPath = $searchPath.TrimEnd("*").TrimEnd("\")
            if (Test-Path $dirPath) {
                Get-ChildItem $dirPath -Recurse -Filter "*.disabled" | ForEach-Object {
                    $originalPath = $_.FullName -replace "\.disabled$", ""
                    
                    if ($DryRun) {
                        Write-Host "  [DRY-RUN] Restauraria: $(Split-Path $originalPath -Leaf)" -ForegroundColor Cyan
                        $script:restoredCount++
                    } else {
                        try {
                            Move-Item $_.FullName $originalPath -Force
                            $script:restoredCount++
                            Write-Host "  ✓ $(Split-Path $originalPath -Leaf)" -ForegroundColor Green
                        }
                        catch {
                            $script:errorCount++
                            Write-Host "  ✗ Erro: $(Split-Path $originalPath -Leaf) - $_" -ForegroundColor Red
                        }
                    }
                }
            }
        } else {
            # Arquivo específico
            $disabledPath = "$searchPath.disabled"
            if (Test-Path $disabledPath) {
                if ($DryRun) {
                    Write-Host "  [DRY-RUN] Restauraria: $(Split-Path $searchPath -Leaf)" -ForegroundColor Cyan
                    $script:restoredCount++
                } else {
                    try {
                        Move-Item $disabledPath $searchPath -Force
                        $script:restoredCount++
                        Write-Host "  ✓ $(Split-Path $searchPath -Leaf)" -ForegroundColor Green
                    }
                    catch {
                        $script:errorCount++
                        Write-Host "  ✗ Erro: $(Split-Path $searchPath -Leaf) - $_" -ForegroundColor Red
                    }
                }
            }
        }
    }
}

# ============================================================================
# MAIN SCRIPT
# ============================================================================

Write-Host ""
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host "  RESTAURAÇÃO SELETIVA DE SISTEMAS" -ForegroundColor Cyan
Write-Host "=========================================" -ForegroundColor Cyan
Write-Host ""

# Verificar se estamos no diretório correto
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute este script na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

# Mostrar sistemas disponíveis se solicitado
if ($ListSystems) {
    Show-AvailableSystems
    exit 0
}

# Validar sistemas solicitados
if ($Systems.Count -eq 0) {
    Write-Host "ERRO: Especifique pelo menos um sistema para restaurar" -ForegroundColor Red
    Write-Host "Use -ListSystems para ver os sistemas disponíveis" -ForegroundColor Yellow
    exit 1
}

$invalidSystems = $Systems | Where-Object { $_ -notin $systemMap.Keys }
if ($invalidSystems.Count -gt 0) {
    Write-Host "ERRO: Sistemas inválidos: $($invalidSystems -join ', ')" -ForegroundColor Red
    Write-Host "Use -ListSystems para ver os sistemas válidos" -ForegroundColor Yellow
    exit 1
}

# Mostrar aviso se não for dry-run
if (-not $DryRun -and -not $Force) {
    Write-Host "⚠️ ATENÇÃO: Esta operação irá restaurar arquivos e pode quebrar o build!" -ForegroundColor Yellow
    Write-Host "Sistemas a restaurar: $($Systems -join ', ')" -ForegroundColor White
    Write-Host ""
    $response = Read-Host "Continuar? (s/N)"
    if ($response -ne 's' -and $response -ne 'S') {
        Write-Host "Operação cancelada pelo usuário" -ForegroundColor Yellow
        exit 0
    }
}

if ($DryRun) {
    Write-Host "=== MODO DRY-RUN (nenhum arquivo será movido) ===" -ForegroundColor Magenta
    Write-Host ""
}

# Contadores globais
$script:restoredCount = 0
$script:errorCount = 0

# Restaurar cada sistema solicitado
foreach ($system in $Systems) {
    $patterns = $systemMap[$system]
    Restore-SystemFiles $system $patterns
    Write-Host ""
}

# Relatório final
Write-Host "=========================================" -ForegroundColor Green
Write-Host "  RESTAURAÇÃO CONCLUÍDA" -ForegroundColor Green
Write-Host "=========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Arquivos processados: $script:restoredCount" -ForegroundColor $(if ($script:restoredCount -gt 0) { "Green" } else { "Yellow" })
Write-Host "Erros: $script:errorCount" -ForegroundColor $(if ($script:errorCount -eq 0) { "Green" } else { "Red" })

if ($script:restoredCount -gt 0 -and -not $DryRun) {
    Write-Host ""
    Write-Host "⚠️ RECOMENDAÇÃO: Teste a compilação agora!" -ForegroundColor Yellow
    Write-Host "Execute: .\gradlew compileJava" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Se houver erros, você pode:" -ForegroundColor White
    Write-Host "• Desabilitar novamente: .\COMPLETE_DISABLE_SCRIPT.ps1" -ForegroundColor White
    Write-Host "• Restaurar mais sistemas conforme necessário" -ForegroundColor White
}

Write-Host ""
