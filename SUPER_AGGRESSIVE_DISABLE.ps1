# Script Super Agressivo - Manter apenas arquivos MUITO básicos
Write-Host "=== DESABILITAÇÃO SUPER AGRESSIVA ===" -ForegroundColor Red
Write-Host "Este script manterá apenas arquivos MUITO básicos sem dependências" -ForegroundColor Yellow
Write-Host ""

$baseDir = "src\main\java\com\tacz\guns"
$disabledCount = 0
$skippedCount = 0

# Verificar local
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

# Lista MÍNIMA de arquivos para manter (apenas POJOs e dados básicos)
$minimalEnabledFiles = @(
    "DataType.java",
    "GunTabType.java", 
    "PackInfo.java",
    "PackMeta.java",
    "Vector3fSerializer.java",
    "FeedType.java", 
    "BedrockVersion.java",
    "Align.java",
    "DefaultAnimationType.java",
    "MeleeData.java",
    "BulletData.java", 
    "BurstData.java",
    "ExplosionData.java",
    "Bolt.java",
    "Ignite.java",
    "FireSound.java",
    "GunRecoil.java",
    "GunReloadData.java",
    "BlockData.java",
    "GunFireModeAdjustData.java",
    "Md5Utils.java",
    "TacHitResult.java",
    "RenderHelper.java",
    "CycleTaskHelper.java",
    "PathHandler.java",
    "GetJarResources.java",
    "Serializers.java",
    "IDataSerializer.java",
    "JsonProperty.java"
)

Write-Host "Mantendo apenas $($minimalEnabledFiles.Count) arquivos básicos..." -ForegroundColor Yellow

# Função para verificar se um arquivo deve permanecer habilitado
function Should-Stay-Enabled($fileName) {
    return $minimalEnabledFiles -contains $fileName
}

# Processar todos os arquivos Java recursivamente
if (Test-Path $baseDir) {
    Get-ChildItem $baseDir -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | ForEach-Object {
        $fileName = $_.Name
        $relativePath = $_.FullName.Replace((Get-Location).Path + "\", "")
        
        # Se o arquivo não está na lista mínima, desabilite-o
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
Write-Host "=== RESUMO FINAL ===" -ForegroundColor Cyan
Write-Host "Arquivos desabilitados: $disabledCount" -ForegroundColor Red
Write-Host "Arquivos mantidos: $skippedCount" -ForegroundColor Green
Write-Host "Total processado: $($disabledCount + $skippedCount)" -ForegroundColor White

Write-Host ""
Write-Host "=== TESTE FINAL DE COMPILAÇÃO ===" -ForegroundColor Yellow

try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "🎉 SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "Compilação está funcionando com arquivos mínimos!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "⚠️  WARNING: $errorCount erros restantes (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Pode ser necessário desabilitar mais arquivos ou verificar dependências básicas." -ForegroundColor Yellow
    } else {
        Write-Host "❌ ERROR: Build ainda com problemas (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique se há problemas com o ambiente ou configuração." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "CONCLUÍDO! 🏁" -ForegroundColor Green
Write-Host "Se a compilação passou, você pode começar a re-habilitar arquivos gradualmente." -ForegroundColor Cyan
Write-Host "Use Migration-Scripts\PowerShell\RESTORE_SIMPLE.ps1 se precisar reverter tudo." -ForegroundColor Cyan
