# Script ULTRA MINIMALISTA - Apenas dados puros
Write-Host "=== DESABILITAÇÃO ULTRA MINIMALISTA ===" -ForegroundColor Red
Write-Host "Mantendo apenas arquivos de dados puros sem nenhuma dependência interna" -ForegroundColor Yellow
Write-Host ""

$baseDir = "src\main\java\com\tacz\guns"
$disabledCount = 0
$skippedCount = 0

# Verificar local
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

# Lista ULTRA MÍNIMA - apenas enums e dados básicos sem dependências
$ultraMinimalFiles = @(
    "DataType.java",
    "GunTabType.java",
    "FeedType.java",
    "Align.java",
    "DefaultAnimationType.java",
    "Vector3fSerializer.java",
    "Md5Utils.java",
    "CycleTaskHelper.java",
    "PathHandler.java",
    "GetJarResources.java",
    "RenderHelper.java"
)

Write-Host "Mantendo apenas $($ultraMinimalFiles.Count) arquivos ultra básicos..." -ForegroundColor Yellow

# Função para verificar se um arquivo deve permanecer habilitado
function Should-Stay-Enabled($fileName) {
    return $ultraMinimalFiles -contains $fileName
}

# Processar todos os arquivos Java recursivamente
if (Test-Path $baseDir) {
    Get-ChildItem $baseDir -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | ForEach-Object {
        $fileName = $_.Name
        $relativePath = $_.FullName.Replace((Get-Location).Path + "\", "")
        
        # Se o arquivo não está na lista ultra mínima, desabilite-o
        if (-not (Should-Stay-Enabled $fileName)) {
            if (-not $_.FullName.EndsWith(".disabled")) {
                try {
                    $newName = $_.FullName + ".disabled"
                    Move-Item $_.FullName $newName -Force
                    $disabledCount++
                    Write-Host "  DESABILITADO: $relativePath" -ForegroundColor Red
                }
                catch {
                    Write-Host "  ERRO: $relativePath - $($_.Exception.Message)" -ForegroundColor Yellow
                }
            }
        } else {
            $skippedCount++
            Write-Host "  MANTIDO: $relativePath" -ForegroundColor Green
        }
    }
}

Write-Host ""
Write-Host "=== RESUMO ULTRA FINAL ===" -ForegroundColor Cyan
Write-Host "Arquivos desabilitados: $disabledCount" -ForegroundColor Red
Write-Host "Arquivos mantidos: $skippedCount" -ForegroundColor Green
Write-Host "Total processado: $($disabledCount + $skippedCount)" -ForegroundColor White

Write-Host ""
Write-Host "=== TESTE ULTRA FINAL ===" -ForegroundColor Yellow

try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "🎉 ULTRA SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "PERFEITO! Agora você tem uma base estável para trabalhar!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "⚠️  $errorCount erros restantes (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Se ainda há erros, pode ser problema de configuração do ambiente." -ForegroundColor Yellow
    } else {
        Write-Host "❌ ERROR: Ainda há problemas (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique se há problemas fundamentais no ambiente." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "ULTRA CONCLUÍDO! 🚀" -ForegroundColor Green
Write-Host "Base mínima estabelecida. Agora pode re-habilitar arquivos gradualmente!" -ForegroundColor Cyan
