# Script Completo de Desabilitação - TacZ Migration para NeoForge 1.21.1
Write-Host "=== DESABILITANDO TODOS OS ARQUIVOS PROBLEMÁTICOS ===" -ForegroundColor Cyan
Write-Host "Este script irá desabilitar TODOS os arquivos Java exceto os já habilitados na Fase 1" -ForegroundColor Yellow
Write-Host ""

$baseDir = "src\main\java\com\tacz\guns"
$disabledCount = 0
$skippedCount = 0

# Verificar local
if (-not (Test-Path "build.gradle")) {
    Write-Host "ERRO: Execute na pasta raiz do projeto" -ForegroundColor Red
    exit 1
}

# Lista de arquivos que devem permanecer habilitados (Fase 1 concluída)
$enabledFiles = @(
    "DataType.java",
    "GunTabType.java", 
    "GunTooltipPart.java",
    "CommonLoadPack.java",
    "OpenGunPackDirEntry.java",
    "CycleTaskHelper.java",
    "AttachmentLod.java",
    "GunHurtBobTweak.java",
    "ZoomClothConfig.java",
    "GunFireModeAdjustData.java",
    "AmmoBoxTooltip.java",
    "Vector3fSerializer.java",
    "FeedType.java",
    "BufferViewModel.java",
    "OculusCompatNewly.java",
    "TimelessCommonEvents.java",
    "KeepingItemRenderer.java",
    "ResourceManager.java",
    "Md5Utils.java",
    "MeleeData.java",
    "GunFireSelectEvent.java",
    "InteractKeyConfigRead.java",
    "GunAnimationConstant.java",
    "DefaultTableItem.java",
    "FaceUVsItem.java",
    "DefaultAnimationType.java",
    "GunItemManager.java",
    "ShellDisplay.java",
    "GunFinishReloadEvent.java",
    "Align.java",
    "DebugCommand.java",
    "Ignite.java",
    "PackInfo.java",
    "IFunctionalRenderer.java",
    "BlockItemTooltip.java",
    "JsonProperty.java",
    "TimelessKubeJSEventRegister.java",
    "BedrockAnimationFile.java",
    "AnimationPlan.java",
    "BedrockPolygon.java",
    "PlayerAnimatorAssetManager.java",
    "TacPathVisitor.java",
    "GltfConstants.java",
    "FireSound.java",
    "AnimationSoundChannelContent.java",
    "ITargetEntity.java",
    "TacHitResult.java",
    "ExplosionData.java",
    "GunShootEvent.java",
    "GunDrawEvent.java",
    "BurstData.java",
    "GeometryModelNew.java",
    "CubesItem.java",
    "GunLevelUpToast.java",
    "ModelRendererWrapper.java",
    "ShootResult.java",
    "LuaAnimationState.java",
    "IDataSerializer.java",
    "BlockRayTrace.java",
    "PackMeta.java",
    "Serializers.java",
    "BedrockVersion.java",
    "Bolt.java",
    "MuzzleFlash.java",
    "RenderHelper.java",
    "TimelessAPI.java",
    "ItemDataAccessor.java",
    "GunItemDataAccessor.java",
    "AttachmentItemDataAccessor.java",
    "AmmoItemDataAccessor.java",
    "BlockItemDataAccessor.java",
    "AmmoBoxItemDataAccessor.java",
    "AnimationSampler.java",
    "PathHandler.java",
    "GunRecoil.java",
    "BlockData.java",
    "IThirdPersonAnimation.java",
    "GetJarResources.java",
    "ObjectAnimation.java",
    "BulletData.java",
    "GunReloadData.java",
    "AttachmentSkin.java",
    "Animation.java",
    "BedrockAnimation.java",
    "AnimationChannel.java",
    "ObjectAnimationSoundChannel.java",
    "ObjectAnimationChannel.java",
    "Buffer.java",
    "AnimationChannelTarget.java",
    "PlayGunSoundEvent.java",
    "Node.java",
    "GunTransform.java",
    "AmmoTransform.java",
    "RefitTransform.java",
    "AttachmentDisplay.java",
    "GunDisplay.java",
    "AmmoDisplay.java",
    "AmmoEntityDisplay.java",
    "IDisplay.java",
    "BlockDisplay.java",
    "ServerMessageSound.java"
)

Write-Host "Processando todos os arquivos Java..." -ForegroundColor Yellow

# Função para verificar se um arquivo deve permanecer habilitado
function Should-Stay-Enabled($fileName) {
    return $enabledFiles -contains $fileName
}

# Processar todos os arquivos Java recursivamente
if (Test-Path $baseDir) {
    Get-ChildItem $baseDir -Recurse -Filter "*.java" -ErrorAction SilentlyContinue | ForEach-Object {
        $fileName = $_.Name
        $relativePath = $_.FullName.Replace((Get-Location).Path + "\", "")
        
        # Se o arquivo não está na lista de habilitados, desabilite-o
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
Write-Host "=== DESABILITANDO ARQUIVO PRINCIPAL ===" -ForegroundColor Yellow

# Desabilitar o arquivo principal GunMod.java se existir
$gunModPath = Join-Path $baseDir "GunMod.java"
if (Test-Path $gunModPath) {
    try {
        $newName = $gunModPath + ".disabled"
        Move-Item $gunModPath $newName -Force
        $disabledCount++
        Write-Host "  DESABILITADO: GunMod.java" -ForegroundColor Red
    }
    catch {
        Write-Host "  ERRO: GunMod.java - $($_.Exception.Message)" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "=== RESUMO ===" -ForegroundColor Cyan
Write-Host "Arquivos desabilitados: $disabledCount" -ForegroundColor Red
Write-Host "Arquivos mantidos (Fase 1): $skippedCount" -ForegroundColor Green
Write-Host "Total processado: $($disabledCount + $skippedCount)" -ForegroundColor White

Write-Host ""
Write-Host "=== TESTANDO COMPILAÇÃO ===" -ForegroundColor Yellow

try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "Todos os arquivos problemáticos foram desabilitados!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "WARNING: $errorCount erros restantes (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Pode ser necessária análise adicional." -ForegroundColor Yellow
    } else {
        Write-Host "ERROR: Build com problemas (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique os logs detalhados." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "CONCLUÍDO!" -ForegroundColor Green
Write-Host "Use o script Migration-Scripts\PowerShell\RESTORE_SIMPLE.ps1 para reverter se necessário." -ForegroundColor Cyan
