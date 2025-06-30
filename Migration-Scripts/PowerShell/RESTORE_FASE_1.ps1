# Script para re-habilitar arquivos da Fase 1
Write-Host "=== RE-HABILITANDO ARQUIVOS DA FASE 1 ===" -ForegroundColor Green
Write-Host ""

$baseDir = "src\main\java\com\tacz\guns"
$enabledCount = 0
$notFoundCount = 0

# Lista dos arquivos da Fase 1 que devem estar habilitados
$phase1Files = @(
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

Write-Host "Processando $($phase1Files.Count) arquivos da Fase 1..." -ForegroundColor Yellow

# Função para encontrar e habilitar arquivo
function Enable-File($fileName) {
    $found = Get-ChildItem $baseDir -Recurse -Filter "$fileName.disabled" -ErrorAction SilentlyContinue
    
    if ($found) {
        foreach ($file in $found) {
            $newName = $file.FullName.Replace(".disabled", "")
            try {
                Move-Item $file.FullName $newName -Force
                $relativePath = $newName.Replace((Get-Location).Path + "\", "")
                Write-Host "  ✅ HABILITADO: $relativePath" -ForegroundColor Green
                return $true
            }
            catch {
                Write-Host "  ❌ ERRO: $fileName - $($_.Exception.Message)" -ForegroundColor Red
                return $false
            }
        }
    }
    
    # Verificar se já está habilitado
    $enabled = Get-ChildItem $baseDir -Recurse -Filter $fileName -ErrorAction SilentlyContinue
    if ($enabled) {
        Write-Host "  ✅ JÁ HABILITADO: $fileName" -ForegroundColor Cyan
        return $true
    }
    
    Write-Host "  ❓ NÃO ENCONTRADO: $fileName" -ForegroundColor Yellow
    return $false
}

# Processar cada arquivo
foreach ($fileName in $phase1Files) {
    if (Enable-File $fileName) {
        $enabledCount++
    } else {
        $notFoundCount++
    }
}

Write-Host ""
Write-Host "=== RESUMO ===" -ForegroundColor Cyan
Write-Host "Arquivos habilitados: $enabledCount" -ForegroundColor Green
Write-Host "Arquivos não encontrados: $notFoundCount" -ForegroundColor Yellow
Write-Host "Total da Fase 1: $($phase1Files.Count)" -ForegroundColor White

Write-Host ""
Write-Host "=== TESTE DE COMPILAÇÃO ===" -ForegroundColor Yellow

try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "🎉 SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "Fase 1 restaurada com sucesso!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "⚠️  WARNING: $errorCount erros (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Alguns arquivos podem precisar de migração adicional." -ForegroundColor Yellow
    } else {
        Write-Host "❌ ERROR: Problemas na compilação (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique os logs de erro." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "FASE 1 PROCESSADA! 🚀" -ForegroundColor Green
