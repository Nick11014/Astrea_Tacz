# Script para habilitar arquivos restantes da Fase 1
Write-Host "=== HABILITANDO ARQUIVOS RESTANTES DA FASE 1 ===" -ForegroundColor Green

$baseDir = "src\main\java\com\tacz\guns"
$enabledCount = 0

# Arquivos restantes da Fase 1
$remainingFiles = @(
    "AnimationPlan.java",
    "AnimationSoundChannelContent.java",
    "BufferViewModel.java",
    "GltfConstants.java",
    "LuaAnimationState.java",
    "ITargetEntity.java",
    "ShootResult.java",
    "GunDrawEvent.java",
    "GunFinishReloadEvent.java",
    "GunFireSelectEvent.java",
    "GunShootEvent.java",
    "GunAnimationConstant.java",
    "GunLevelUpToast.java",
    "IFunctionalRenderer.java",
    "GunHurtBobTweak.java",
    "DebugCommand.java",
    "ZoomClothConfig.java",
    "OpenGunPackDirEntry.java",
    "TimelessCommonEvents.java",
    "TimelessKubeJSEventRegister.java",
    "OculusCompatNewly.java",
    "PlayerAnimatorAssetManager.java",
    "InteractKeyConfigRead.java",
    "AmmoBoxTooltip.java",
    "BlockItemTooltip.java",
    "DefaultTableItem.java",
    "ObjectAnimation.java",
    "ObjectAnimationChannel.java",
    "ObjectAnimationSoundChannel.java",
    "IThirdPersonAnimation.java",
    "RefitTransform.java",
    "PlayGunSoundEvent.java",
    "ServerMessageSound.java"
)

Write-Host "Processando $($remainingFiles.Count) arquivos restantes..."

foreach ($fileName in $remainingFiles) {
    $found = Get-ChildItem $baseDir -Recurse -Filter "$fileName.disabled" -ErrorAction SilentlyContinue
    
    if ($found) {
        foreach ($file in $found) {
            $newName = $file.FullName.Replace(".disabled", "")
            try {
                Move-Item $file.FullName $newName -Force
                Write-Host "  HABILITADO: $fileName" -ForegroundColor Green
                $enabledCount++
            }
            catch {
                Write-Host "  ERRO: $fileName" -ForegroundColor Red
            }
        }
    } else {
        Write-Host "  JA HABILITADO OU NAO ENCONTRADO: $fileName" -ForegroundColor Cyan
    }
}

Write-Host ""
Write-Host "RESUMO: $enabledCount novos arquivos habilitados" -ForegroundColor White
Write-Host ""
Write-Host "Testando compilacao..."

try {
    $result = & .\gradlew compileJava 2>&1 | Out-String
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "SUCCESS: BUILD SUCCESSFUL!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "WARNING: $errorCount erros" -ForegroundColor Yellow
    } else {
        Write-Host "ERROR: Problemas na compilacao" -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle" -ForegroundColor Red
}

Write-Host "CONCLUIDO!" -ForegroundColor Green
