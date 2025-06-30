# Script para manter apenas os arquivos da lista especificada
Write-Host "=== HABILITANDO APENAS ARQUIVOS DA LISTA ESPECIFICADA ===" -ForegroundColor Green
Write-Host ""

$baseDir = "src\main\java\com\tacz\guns"
$disabledCount = 0
$keptCount = 0

# Lista EXATA dos arquivos que devem permanecer habilitados
$allowedFiles = @(
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
    "Accessor.java",
    "StairBlockAccessor.java",
    "LuaEntityAccessor.java",
    "LuaNbtAccessor.java"
)

Write-Host "Processando todos os arquivos Java..."
Write-Host "PERMITIDOS: $($allowedFiles.Count) arquivos" -ForegroundColor Cyan
Write-Host ""

# Obter todos os arquivos Java ativos
$allJavaFiles = Get-ChildItem $baseDir -Recurse -Filter "*.java"

foreach ($file in $allJavaFiles) {
    $fileName = $file.Name
    
    if ($allowedFiles -contains $fileName) {
        Write-Host "  ✅ MANTIDO: $fileName" -ForegroundColor Green
        $keptCount++
    } else {
        try {
            $disabledPath = $file.FullName + ".disabled"
            Move-Item $file.FullName $disabledPath -Force
            Write-Host "  ❌ DESABILITADO: $fileName" -ForegroundColor Red
            $disabledCount++
        }
        catch {
            Write-Host "  ⚠️  ERRO: $fileName - $($_.Exception.Message)" -ForegroundColor Yellow
        }
    }
}

Write-Host ""
Write-Host "=== RESUMO ===" -ForegroundColor Cyan
Write-Host "Arquivos mantidos: $keptCount" -ForegroundColor Green
Write-Host "Arquivos desabilitados: $disabledCount" -ForegroundColor Red
Write-Host "Total da lista permitida: $($allowedFiles.Count)" -ForegroundColor White

# Verificar se algum arquivo da lista não foi encontrado
$foundFiles = Get-ChildItem $baseDir -Recurse -Filter "*.java" | ForEach-Object { $_.Name }
$missingFiles = $allowedFiles | Where-Object { $_ -notin $foundFiles }

if ($missingFiles) {
    Write-Host ""
    Write-Host "⚠️ ARQUIVOS NÃO ENCONTRADOS:" -ForegroundColor Yellow
    foreach ($missing in $missingFiles) {
        Write-Host "  - $missing" -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "=== TESTE DE COMPILAÇÃO ===" -ForegroundColor Yellow

try {
    $buildStart = Get-Date
    $result = & .\gradlew compileJava 2>&1 | Out-String
    $buildEnd = Get-Date
    $buildTime = ($buildEnd - $buildStart).TotalSeconds
    
    if ($result -match "BUILD SUCCESSFUL") {
        Write-Host "SUCCESS: BUILD SUCCESSFUL! (${buildTime}s)" -ForegroundColor Green
        Write-Host "Lista de arquivos aplicada com sucesso!" -ForegroundColor Green
    } elseif ($result -match "(\d+) errors") {
        $errorCount = $matches[1]
        Write-Host "WARNING: $errorCount erros (${buildTime}s)" -ForegroundColor Yellow
        Write-Host "Alguns arquivos podem precisar de migração adicional." -ForegroundColor Yellow
    } else {
        Write-Host "ERROR: Problemas na compilação (${buildTime}s)" -ForegroundColor Red
        Write-Host "Verifique os logs de erro." -ForegroundColor Red
    }
}
catch {
    Write-Host "ERRO: Falha ao executar gradle - $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "LISTA APLICADA! 🎯" -ForegroundColor Green
