# ===================================================================
# Enable-Absolute-Minimum.ps1 - Habilita a base mais segura possivel (Lista Manual)
# ===================================================================
Write-Host "=== HABILITANDO BASE MINIMA ABSOLUTA (Enums Puros) ===" -ForegroundColor Cyan

$baseDir = "src\main\java\com\tacz\guns"
$buildFile = "build.gradle"
$enabledCount = 0
$errorCount = 0
$notFoundCount = 0

# Verificacao inicial do ambiente
if (-not (Test-Path $baseDir)) {
    Write-Host "ERRO: Diretorio base nao encontrado: $baseDir" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $buildFile)) {
    Write-Host "ERRO: Arquivo build.gradle nao encontrado. Execute na raiz do projeto." -ForegroundColor Red
    exit 1
}

# Esta lista contem apenas enums puros que quase certamente nao tem dependencias.
$absoluteMinimalFiles = @(
    "DataType.java",
    "GunTabType.java", 
    "FeedType.java",
    "Align.java",
    "DefaultAnimationType.java",
    "FireMode.java",
    "ReloadState.java"
)

Write-Host "Procurando por $($absoluteMinimalFiles.Count) arquivos especificos de base..." -ForegroundColor Yellow
Write-Host ""

# Buscar arquivos .disabled recursivamente uma unica vez
$disabledFiles = Get-ChildItem -Path $baseDir -Recurse -Filter "*.java.disabled"
$disabledFileMap = @{}

# Criar mapa para busca rapida
foreach ($file in $disabledFiles) {
    $fileName = $file.Name.Replace(".disabled", "")
    $disabledFileMap[$fileName] = $file
}

# Processar cada arquivo da lista
foreach ($targetFile in $absoluteMinimalFiles) {
    Write-Host "Procurando: $targetFile" -ForegroundColor White
    
    if ($disabledFileMap.ContainsKey($targetFile)) {
        $file = $disabledFileMap[$targetFile]
        try {
            $newName = $file.FullName -replace '\.disabled$', ''
            Move-Item $file.FullName $newName -Force
            $enabledCount++
            Write-Host "  HABILITADO: $targetFile" -ForegroundColor Green
        }
        catch {
            Write-Host "  ERRO ao habilitar $targetFile : $($_.Exception.Message)" -ForegroundColor Red
            $errorCount++
        }
    }
    else {
        Write-Host "  NAO ENCONTRADO: $targetFile" -ForegroundColor Yellow
        $notFoundCount++
    }
}

Write-Host ""
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "RELATORIO FINAL DA HABILITACAO MANUAL" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "Arquivos alvo: $($absoluteMinimalFiles.Count)" -ForegroundColor White
Write-Host "Arquivos habilitados: $enabledCount" -ForegroundColor Green
Write-Host "Arquivos nao encontrados: $notFoundCount" -ForegroundColor Yellow
Write-Host "Erros de habilitacao: $errorCount" -ForegroundColor Red
Write-Host ""

if ($enabledCount -gt 0) {
    Write-Host "Tentando compilar a base minima..." -ForegroundColor Yellow
    
    # Teste de Compilacao
    try {
        $result = & .\gradlew compileJava 2>&1 | Out-String
        if ($LASTEXITCODE -eq 0 -and $result -match "BUILD SUCCESSFUL") {
            Write-Host "BUILD SUCCESSFUL! Base minima estavel estabelecida." -ForegroundColor Green
            Write-Host "Agora podemos prosseguir com seguranca." -ForegroundColor Green
        } else {
            Write-Host "BUILD FALHOU! Verifique os erros de compilacao." -ForegroundColor Red
            Write-Host "Pode ser necessario revisar a configuracao do ambiente/build.gradle." -ForegroundColor Red
        }
    }
    catch {
        Write-Host "ERRO CRITICO ao executar o gradle: $($_.Exception.Message)" -ForegroundColor Red
    }
}
else {
    Write-Host "Nenhum arquivo foi habilitado. Verifique se:" -ForegroundColor Yellow
    Write-Host "1. Os arquivos estao no formato .java.disabled" -ForegroundColor Yellow
    Write-Host "2. Os nomes dos arquivos coincidem exatamente com a lista" -ForegroundColor Yellow
    Write-Host "3. O diretorio base esta correto" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Habilitacao minima concluida." -ForegroundColor Cyan