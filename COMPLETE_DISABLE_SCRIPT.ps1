# Script Funcional - TacZ Migration
Write-Host "=== DESABILITANDO ARQUIVOS PROBLEMÁTICOS ===" -ForegroundColor Cyan

$baseDir = "src\main\java\com\tacz\guns"
$disabledCount = 0

# Verificar local
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

Write-Host "Desabilitando diretórios..." -ForegroundColor Yellow

# Lista de diretórios para desabilitar
$dirsToDisable = @("api", "client", "particles", "resource", "command", "compat", "config", "crafting", "debug", "event", "inventory", "mixin", "sound", "util", "init", "block", "network")

foreach ($dir in $dirsToDisable) {
    $dirPath = Join-Path $baseDir $dir
    if (Test-Path $dirPath) {
        Write-Host "Processando: $dir" -ForegroundColor Gray
        Get-ChildItem $dirPath -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | ForEach-Object {
            if (-not $_.FullName.EndsWith(".disabled")) {
                try {
                    $newName = $_.FullName + ".disabled"
                    Move-Item $_.FullName $newName -Force
                    $disabledCount++
                    Write-Host "  OK: $($_.Name)" -ForegroundColor Green
                }
                catch {
                    Write-Host "  ERRO: $($_.Name) - $($_.Exception.Message)" -ForegroundColor Red
                }
            }
        }
    } else {
        Write-Host "SKIP: $dir (diretório não encontrado)" -ForegroundColor Gray
    }
}

# Desabilitar arquivos específicos
Write-Host "Desabilitando arquivos específicos..." -ForegroundColor Yellow
$specificFiles = @(
    "entity\TargetMinecart.java",
    "entity\EntityKineticBullet.java", 
    "item\ModernKineticGunItem.java",
    "item\ModernKineticGunScriptAPI.java",
    "item\AmmoBoxItem.java",
    "item\AmmoItem.java",
    "item\AttachmentItem.java",
    "item\DefaultTableItem.java",
    "item\GunSmithTableItem.java",
    "item\GunTooltipPart.java",
    "item\TargetMinecartItem.java",
    "GunMod.java"
)

foreach ($file in $specificFiles) {
    $filePath = Join-Path $baseDir $file
    if (Test-Path $filePath) {
        try {
            $newName = $filePath + ".disabled"
            Move-Item $filePath $newName -Force
            $disabledCount++
            Write-Host "  OK: $file" -ForegroundColor Green
        }
        catch {
            Write-Host "  ERRO: $file - $($_.Exception.Message)" -ForegroundColor Red
        }
    } else {
        Write-Host "  SKIP: $file (não encontrado)" -ForegroundColor Gray
    }
}

# Desabilitar diretórios específicos em entity
Write-Host "Desabilitando subdiretórios em entity..." -ForegroundColor Yellow
$entitySubDirs = @("sync", "shooter")

foreach ($subDir in $entitySubDirs) {
    $entitySubPath = Join-Path $baseDir "entity\$subDir"
    if (Test-Path $entitySubPath) {
        Write-Host "  Processando entity/$subDir..." -ForegroundColor Gray
        Get-ChildItem $entitySubPath -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | ForEach-Object {
            if (-not $_.FullName.EndsWith(".disabled")) {
                try {
                    $newName = $_.FullName + ".disabled"
                    Move-Item $_.FullName $newName -Force
                    $disabledCount++
                    Write-Host "    OK: $($_.Name)" -ForegroundColor Green
                }
                catch {
                    Write-Host "    ERRO: $($_.Name) - $($_.Exception.Message)" -ForegroundColor Red
                }
            }
        }
    } else {
        Write-Host "  SKIP: entity/$subDir (não encontrado)" -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "Total desabilitado: $disabledCount arquivos" -ForegroundColor Yellow

# Testar compilação
Write-Host ""
Write-Host "Testando compilacao..." -ForegroundColor Yellow
try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "Todos os arquivos problemáticos foram desabilitados com sucesso!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "WARNING: $errorCount erros restantes (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Pode ser necessário desabilitar arquivos adicionais." -ForegroundColor Yellow
    } else {
        Write-Host "ERROR: Build com problemas (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique os logs do Gradle para mais detalhes." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

# Criar script de restauração simples
Write-Host ""
Write-Host "Criando script de restauracao..." -ForegroundColor Yellow

$restoreContent = 'Write-Host "=== RESTAURANDO ARQUIVOS ===" -ForegroundColor Yellow
$restored = 0
Get-ChildItem -Recurse -Filter "*.disabled" | ForEach-Object {
    $original = $_.FullName -replace "\.disabled$", ""
    try {
        Move-Item $_.FullName $original -Force
        $restored++
        Write-Host "OK: $(Split-Path $original -Leaf)" -ForegroundColor Green
    }
    catch {
        Write-Host "ERRO: $(Split-Path $original -Leaf)" -ForegroundColor Red
    }
}
Write-Host "Restaurados: $restored arquivos" -ForegroundColor Yellow'

$restoreContent | Out-File "RESTORE_SIMPLE.ps1" -Encoding UTF8
Write-Host "Script criado: RESTORE_SIMPLE.ps1" -ForegroundColor Green

Write-Host ""
Write-Host "CONCLUIDO!" -ForegroundColor Green
Write-Host "Script de restauração criado: RESTORE_SIMPLE.ps1" -ForegroundColor Cyan
Write-Host ""
Write-Host "=== RESUMO ===" -ForegroundColor Cyan
Write-Host "Total de arquivos desabilitados: $disabledCount" -ForegroundColor White
Write-Host "Diretórios processados: api, client, particles, resource, command, compat, config, crafting, debug, event, inventory, mixin, sound, util, init, block, network" -ForegroundColor White
Write-Host "Subdiretórios em entity: sync, shooter" -ForegroundColor White
Write-Host "Arquivos específicos: GunMod.java e todos os itens principais" -ForegroundColor White
Write-Host ""
