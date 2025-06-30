# Script ABSOLUTAMENTE MINIMALISTA - Apenas enums puros
Write-Host "=== DESABILITAÇÃO ABSOLUTAMENTE MINIMALISTA ===" -ForegroundColor Red
Write-Host "Mantendo apenas enums puros e classes sem dependências" -ForegroundColor Yellow
Write-Host ""

$baseDir = "src\main\java\com\tacz\guns"
$disabledCount = 0
$skippedCount = 0

# Verificar local
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

# Lista ABSOLUTAMENTE MÍNIMA - apenas enums puros sem imports internos
$absoluteMinimalFiles = @(
    "DataType.java",
    "GunTabType.java",
    "FeedType.java",
    "Align.java",
    "DefaultAnimationType.java"
)

Write-Host "Mantendo apenas $($absoluteMinimalFiles.Count) arquivos de enums puros..." -ForegroundColor Yellow

# Função para verificar se um arquivo deve permanecer habilitado
function Should-Stay-Enabled($fileName) {
    return $absoluteMinimalFiles -contains $fileName
}

# Processar todos os arquivos Java recursivamente
if (Test-Path $baseDir) {
    Get-ChildItem $baseDir -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | ForEach-Object {
        $fileName = $_.Name
        $relativePath = $_.FullName.Replace((Get-Location).Path + "\", "")
        
        # Se o arquivo não está na lista absoluta mínima, desabilite-o
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
Write-Host "=== RESUMO ABSOLUTO ===" -ForegroundColor Cyan
Write-Host "Arquivos desabilitados: $disabledCount" -ForegroundColor Red
Write-Host "Arquivos mantidos: $skippedCount" -ForegroundColor Green
Write-Host "Total processado: $($disabledCount + $skippedCount)" -ForegroundColor White

Write-Host ""
Write-Host "=== TESTE ABSOLUTO FINAL ===" -ForegroundColor Yellow

try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "🎉 ABSOLUTO SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "PERFEITO! Base mínima absoluta funcionando!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "⚠️  $errorCount erros restantes (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Problemas podem estar na configuração do ambiente ou imports do sistema." -ForegroundColor Yellow
    } else {
        Write-Host "❌ ERROR: Problemas fundamentais (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique a configuração do NeoForge e build.gradle." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "ABSOLUTO CONCLUÍDO! 🎯" -ForegroundColor Green
Write-Host "Base mínima absoluta criada. Agora re-habilite arquivos um por vez!" -ForegroundColor Cyan
