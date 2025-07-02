# ===================================================================
# Enable-Absolute-Minimum.ps1 - Habilita a base mais segura possível.
# ===================================================================
Write-Host "=== HABILITANDO BASE MÍNIMA ABSOLUTA (Enums Puros) ===" -ForegroundColor Cyan

$baseDir = "src\main\java\com\tacz\guns"
$enabledCount = 0

# Esta lista contém apenas enums puros que quase certamente não têm dependências.
$absoluteMinimalFiles = @(
    "DataType.java",
    "GunTabType.java",
    "FeedType.java",
    "Align.java",
    "DefaultAnimationType.java",
    "FireMode.java",
    "ReloadState.java"
)

Write-Host "Procurando por arquivos de base para habilitar..." -ForegroundColor Yellow

Get-ChildItem -Path $baseDir -Recurse -Filter "*.java.disabled" | ForEach-Object {
    if ($absoluteMinimalFiles -contains $_.Name.Replace(".disabled", "")) {
        try {
            $newName = $_.FullName -replace '\.disabled$', ''
            Move-Item $_.FullName $newName -Force
            $enabledCount++
            Write-Host "  HABILITADO: $($_.Name)" -ForegroundColor Green
        }
        catch {
            Write-Host "ERRO ao habilitar $($_.Name): $($_.Exception.Message)" -ForegroundColor Red
        }
    }
}

Write-Host ""
Write-Host "Habilitação mínima concluída: $enabledCount arquivos." -ForegroundColor Green
Write-Host "Tentando compilar a base mínima..." -ForegroundColor Yellow

# Teste de Compilação
try {
    $result = & .\gradlew compileJava 2>&1 | Out-String
    if ($LASTEXITCODE -eq 0 -and $result -match "BUILD SUCCESSFUL") {
        Write-Host "🎉 SUCESSO! BUILD SUCCESSFUL!" -ForegroundColor Green
        Write-Host "Base mínima estável estabelecida. Agora podemos prosseguir."
    } else {
        Write-Host "❌ ERRO: A compilação da base mínima falhou. Verifique a configuração do ambiente/build.gradle." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO CRÍTICO ao executar o gradle." -ForegroundColor Red
}