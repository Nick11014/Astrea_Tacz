# Script para re-habilitar arquivos da Fase 1
Write-Host "=== RE-HABILITANDO ARQUIVOS DA FASE 1 ===" -ForegroundColor Green

$baseDir = "src\main\java\com\tacz\guns"
$enabledCount = 0

# Lista dos arquivos da Fase 1 mais importantes
$phase1Files = @(
    "TimelessAPI.java",
    "GunTooltipPart.java",
    "CommonLoadPack.java",
    "KeepingItemRenderer.java",
    "ResourceManager.java",
    "Md5Utils.java",
    "MeleeData.java",
    "GunItemManager.java",
    "ItemDataAccessor.java",
    "GunItemDataAccessor.java",
    "AttachmentItemDataAccessor.java",
    "AmmoItemDataAccessor.java",
    "BlockItemDataAccessor.java",
    "AmmoBoxItemDataAccessor.java",
    "JsonProperty.java",
    "PackInfo.java",
    "PackMeta.java",
    "Vector3fSerializer.java",
    "BedrockVersion.java",
    "BedrockPolygon.java",
    "ModelRendererWrapper.java",
    "BedrockAnimation.java",
    "BedrockAnimationFile.java",
    "Animation.java",
    "AnimationChannel.java",
    "AnimationChannelTarget.java",
    "AnimationSampler.java",
    "Buffer.java",
    "Node.java",
    "IDisplay.java",
    "GunDisplay.java",
    "GunTransform.java",
    "MuzzleFlash.java",
    "AmmoDisplay.java",
    "AmmoEntityDisplay.java",
    "AmmoTransform.java",
    "ShellDisplay.java",
    "AttachmentDisplay.java",
    "AttachmentLod.java",
    "BlockDisplay.java",
    "CubesItem.java",
    "FaceUVsItem.java",
    "GeometryModelNew.java",
    "AttachmentSkin.java",
    "IDataSerializer.java",
    "Serializers.java",
    "BulletData.java",
    "BurstData.java",
    "ExplosionData.java",
    "Bolt.java",
    "Ignite.java",
    "FireSound.java",
    "GunFireModeAdjustData.java",
    "GunRecoil.java",
    "GunReloadData.java",
    "BlockData.java",
    "CycleTaskHelper.java",
    "GetJarResources.java",
    "PathHandler.java",
    "RenderHelper.java",
    "TacHitResult.java",
    "TacPathVisitor.java",
    "BlockRayTrace.java"
)

Write-Host "Processando arquivos da Fase 1..."

foreach ($fileName in $phase1Files) {
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
        # Verificar se já está habilitado
        $enabled = Get-ChildItem $baseDir -Recurse -Filter $fileName -ErrorAction SilentlyContinue
        if ($enabled) {
            Write-Host "  JA HABILITADO: $fileName" -ForegroundColor Cyan
            $enabledCount++
        } else {
            Write-Host "  NAO ENCONTRADO: $fileName" -ForegroundColor Yellow
        }
    }
}

Write-Host ""
Write-Host "RESUMO: $enabledCount arquivos processados" -ForegroundColor White
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
