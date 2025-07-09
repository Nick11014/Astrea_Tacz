# =============================================================================
# Enable-Phase1-Detailed.ps1 - Habilitador da Fase 1 Detalhada TacZ v2.0
# =============================================================================
# Este script habilita os arquivos da Fase 1 seguindo a estrutura detalhada
# com subfases específicas para diferentes tipos de erros da API NeoForge 1.21.1
# =============================================================================

Write-Host "=== HABILITADOR DA FASE 1 DETALHADA - TacZ v2.0 ===" -ForegroundColor Cyan

# --- ARQUIVOS POR SUBFASE (baseado na análise de build_errors_phase1.txt) ---

$fase1_1_Config = @(
    "PreLoadModConfig.java"
)

$fase1_2_EventsNetwork = @(
    "ServerTickEvent.java",
    "IMessage.java", 
    "ServerMessageLevelUp.java"
)

$fase1_3_ItemStackData = @(
    "IAnimationItem.java",
    "LuaNbtAccessor.java",
    "IComponentTooltip.java",
    "GunTooltipPart.java",
    "ItemStackSerializer.java"
)

$fase1_4_Rendering = @(
    "GunPackProgressScreen.java",
    "RenderHelper.java",
    "FlatColorButton.java",
    "OpenGunPackDirEntry.java"
)

$fase1_5_GeneralAPI = @(
    "HeadShotAABBConfigRead.java",
    "SyncedClassKey.java",
    "TacPathVisitor.java",
    "GunSmithTableIngredientSerializer.java",
    "HitboxHelper.java",
    "ConfigCommand.java"
)

# Arquivos restantes da Fase 1 (sem problemas conhecidos de API)
$fase1_Restantes = @(
    "AccessorSparseIndices.java", "AccessorSparseValues.java", "AmmoBoxTooltip.java",
    "AmmoParticle.java", "AnimationChannelTarget.java", "AnimationSampler.java",
    "BedrockVertex.java", "BlockItemTooltip.java", "Buffer.java", "Buffers.java",
    "BufferView.java", "CommonTransformObject.java", "DiscreteTrackArray.java",
    "FaceItem.java", "FireMode.java", "GunLevelUpToast.java",
    "GunRecoilKeyFrame.java", "IDisplay.java", "LayerGunShow.java", "LoginIndexHolder.java",
    "MathUtil.java", "Md5Utils.java", "MoveSpeed.java", "Node.java", "NodeModel.java",
    "PairSerializer.java", "PerlinNoise.java", "PlayerNamePapi.java",
    "ReloadState.java", "ShellEjection.java", "TimelessItemNbtFactory.java", "TransformScale.java",
    "Vec3Serializer.java", "Vector3fSerializer.java", "AmmoClothConfig.java", "AttachmentIndexPOJO.java",
    "AttachmentItemTooltip.java", "BedrockPart.java", "CommonAmmoIndex.java", "DistanceDamagePairSerializer.java",
    "GunClothConfig.java", "GunResult.java", "IAttachment.java", "IgniteSerializer.java",
    "INetworkCacheReloadListener.java", "Interpolator.java", "KnockbackChange.java", "LiteralFilter.java",
    "LivingEntityAmmoCheck.java", "RegexFilter.java", "ServerConfig.java", "SoundEffectKeyframesSerializer.java",
    "TextShow.java", "ThirdPersonManager.java", "ZoomClothConfig.java", "AttachmentData.java",
    "GunReloadData.java", "BulletData.java", "CommonConfig.java"
)

# --- EXECUÇÃO ---
$projectRoot = $PSScriptRoot
$srcPath = Join-Path $projectRoot "src\main\java\com\tacz\guns"

Write-Host ""
Write-Host "📁 Pasta do projeto: $projectRoot" -ForegroundColor Yellow
Write-Host "📁 Pasta source: $srcPath" -ForegroundColor Yellow
Write-Host ""

function Enable-Files {
    param(
        [string]$subfase,
        [array]$files
    )
    
    Write-Host "🔄 Habilitando $subfase..." -ForegroundColor Green
    $enabled = 0
    
    foreach ($file in $files) {
        $disabledPath = Get-ChildItem -Path $srcPath -Recurse -Filter "$file.disabled" -ErrorAction SilentlyContinue
        if ($disabledPath) {
            $enabledPath = $disabledPath.FullName -replace '\.disabled$', ''
            try {
                Rename-Item -Path $disabledPath.FullName -NewName $enabledPath -ErrorAction Stop
                Write-Host "  ✅ $file" -ForegroundColor Green
                $enabled++
            }
            catch {
                Write-Host "  ❌ Erro ao habilitar $file`: $($_.Exception.Message)" -ForegroundColor Red
            }
        }
        else {
            Write-Host "  ⚠️  $file não encontrado ou já habilitado" -ForegroundColor Yellow
        }
    }
    
    Write-Host "  📊 $enabled/$($files.Count) arquivos habilitados" -ForegroundColor Cyan
    return $enabled
}

function Test-Compilation {
    Write-Host ""
    Write-Host "🔨 Testando compilação..." -ForegroundColor Yellow
    
    try {
        $buildResult = & .\gradlew compileJava 2>&1 | Out-String
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ COMPILAÇÃO SUCEDIDA!" -ForegroundColor Green
            return $true
        }
        else {
            Write-Host "❌ COMPILAÇÃO FALHOU!" -ForegroundColor Red
            Write-Host "Saída do build:" -ForegroundColor Yellow
            Write-Host $buildResult -ForegroundColor White
            return $false
        }
    }
    catch {
        Write-Host "❌ Erro ao executar build: $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

# --- EXECUÇÃO DAS SUBFASES ---
$totalEnabled = 0

Write-Host "🚀 Iniciando habilitação da Fase 1 por subfases..." -ForegroundColor Cyan
Write-Host ""

# Fase 1.1: Sistema de Configuração
$enabled = Enable-Files "Fase 1.1 - Sistema de Configuração" $fase1_1_Config
$totalEnabled += $enabled

if (-not (Test-Compilation)) {
    Write-Host "⚠️  Revertendo Fase 1.1..." -ForegroundColor Yellow
    # Reverter arquivos da Fase 1.1
    exit 1
}

# Fase 1.2: Sistema de Eventos e Rede
$enabled = Enable-Files "Fase 1.2 - Sistema de Eventos e Rede" $fase1_2_EventsNetwork
$totalEnabled += $enabled

if (-not (Test-Compilation)) {
    Write-Host "⚠️  Revertendo Fase 1.2..." -ForegroundColor Yellow
    # Reverter arquivos da Fase 1.2
    exit 1
}

# Fase 1.3: Interação com Itens e Dados
$enabled = Enable-Files "Fase 1.3 - Interação com Itens e Dados" $fase1_3_ItemStackData
$totalEnabled += $enabled

if (-not (Test-Compilation)) {
    Write-Host "⚠️  Revertendo Fase 1.3..." -ForegroundColor Yellow
    # Reverter arquivos da Fase 1.3
    exit 1
}

# Fase 1.4: Renderização e GUI
$enabled = Enable-Files "Fase 1.4 - Renderização e GUI" $fase1_4_Rendering
$totalEnabled += $enabled

if (-not (Test-Compilation)) {
    Write-Host "⚠️  Revertendo Fase 1.4..." -ForegroundColor Yellow
    # Reverter arquivos da Fase 1.4
    exit 1
}

# Fase 1.5: API Geral
$enabled = Enable-Files "Fase 1.5 - API Geral do NeoForge/Minecraft" $fase1_5_GeneralAPI
$totalEnabled += $enabled

if (-not (Test-Compilation)) {
    Write-Host "⚠️  Revertendo Fase 1.5..." -ForegroundColor Yellow
    # Reverter arquivos da Fase 1.5
    exit 1
}

Write-Host ""
Write-Host "🎯 SUBFASES CRÍTICAS CONCLUÍDAS!" -ForegroundColor Green
Write-Host "📊 Arquivos problemáticos habilitados: $totalEnabled" -ForegroundColor Cyan
Write-Host ""

# Perguntar se quer habilitar os arquivos restantes
$continuar = Read-Host "Deseja habilitar os arquivos restantes da Fase 1? (s/N)"

if ($continuar -eq 's' -or $continuar -eq 'S') {
    Write-Host ""
    Write-Host "🔄 Habilitando arquivos restantes da Fase 1..." -ForegroundColor Yellow
    
    $enabled = Enable-Files "Arquivos Restantes da Fase 1" $fase1_Restantes
    $totalEnabled += $enabled
    
    if (-not (Test-Compilation)) {
        Write-Host "⚠️  Alguns arquivos restantes causaram erros. Reverta manualmente se necessário." -ForegroundColor Yellow
    }
    else {
        Write-Host "✅ Todos os arquivos restantes habilitados com sucesso!" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "🎉 FASE 1 DETALHADA CONCLUÍDA!" -ForegroundColor Green
Write-Host "📊 Total de arquivos habilitados: $totalEnabled" -ForegroundColor Cyan
Write-Host ""

if ($totalEnabled -gt 17) {
    Write-Host "✅ Todas as subfases críticas e arquivos restantes foram habilitados!" -ForegroundColor Green
    Write-Host "🔄 Você pode prosseguir para a Fase 2." -ForegroundColor Yellow
}
else {
    Write-Host "✅ Subfases críticas da Fase 1 concluídas!" -ForegroundColor Green
    Write-Host "🔄 Execute novamente com 's' para habilitar os arquivos restantes." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "📝 Próximos passos:" -ForegroundColor Cyan
Write-Host "   1. Corrija os erros de API identificados nas subfases" -ForegroundColor White
Write-Host "   2. Teste a compilação novamente" -ForegroundColor White
Write-Host "   3. Prossiga para os arquivos restantes ou Fase 2" -ForegroundColor White
