# Script para Adicionar Indicadores Visuais ao Progress.md
param(
    [string]$ProgressPath = "progress.md",
    [string]$OutputPath = "progress-enhanced.md"
)

Write-Host "Adicionando indicadores visuais ao progress.md..." -ForegroundColor Green

# Lista de arquivos com implementacao minima (extraida da analise anterior)
$minimalFiles = @{
    "IFunctionalRenderer.java" = "🔧 TODO"
    "ReloadResourceEvent.java" = "📝 Placeholder"
    "ServerMessageGunDraw.java" = "📝 Placeholder"
    "ServerMessageGunFire.java" = "📝 Placeholder"
    "RenderDistance.java" = "🔧 TODO"
    "AmmoCountPapi.java" = "📝 Placeholder"
    "AnimationPlan.java" = "📝 Placeholder"
    "AttachmentDataManager.java" = "🔧 TODO"
    "AttachmentItemRenderer.java" = "⚡ Object+TODO"
    "AttachmentPropertyEvent.java" = "📝 Placeholder"
    "CommonAttachmentIndex.java" = "🔧 TODO"
    "CommonBlockIndex.java" = "🔧 TODO"
    "CommonGunIndex.java" = "🔧 TODO"
    "ExplodeUtil.java" = "🔧 TODO"
    "KubeJSGunEventPoster.java" = "📝 Placeholder"
    "ModBlocks.java" = "📝 Placeholder"
    "ModEntities.java" = "📝 Placeholder"
    "ModItems.java" = "📝 Placeholder"
    "PapiManager.java" = "📝 Placeholder"
    "ShooterDataHolder.java" = "📝 Placeholder"
    "SoundManager.java" = "📝 Placeholder"
    "TextShowRender.java" = "🔧 TODO"
    "AttachmentRender.java" = "⚡ Object+Placeholder"
    "BedrockAttachmentModel.java" = "⚡ Object+TODO"
    "BedrockGunModel.java" = "🔧 TODO"
    "ClientAttachmentIndex.java" = "⚡ Object Strategy"
    "CommonAssetsManager.java" = "🔧 TODO"
    "CommonNetworkCache.java" = "🔧 TODO"
    "GunData.java" = "🔧 TODO"
    "ModCreativeTabs.java" = "📝 Placeholder"
}

# Ler arquivo atual
$content = Get-Content $ProgressPath -Encoding UTF8
$enhancedContent = @()

foreach ($line in $content) {
    # Procurar linhas de arquivos habilitados
    if ($line -match '- \[x\] \*\*(.+\.java)\*\* - HABILITADO(.*)') {
        $fileName = $matches[1]
        $rest = $matches[2]
        
        if ($minimalFiles.ContainsKey($fileName)) {
            $indicator = $minimalFiles[$fileName]
            $enhancedContent += "- [x] **$fileName** - HABILITADO (MÍNIMA: $indicator)$rest"
        } else {
            $enhancedContent += $line
        }
    } else {
        $enhancedContent += $line
    }
}

# Salvar versao aprimorada
$enhancedContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host "Arquivo aprimorado salvo em: $OutputPath" -ForegroundColor Green

# Mostrar estatisticas de indicadores adicionados
$totalIndicators = 0
foreach ($line in $enhancedContent) {
    if ($line -match 'MÍNIMA:') {
        $totalIndicators++
    }
}

Write-Host "Indicadores adicionados: $totalIndicators arquivos" -ForegroundColor Yellow
Write-Host ""
Write-Host "LEGENDA DOS INDICADORES:" -ForegroundColor Green
Write-Host "  ⚡ Object Strategy (Alta prioridade)"
Write-Host "  🔧 TODO/Restaurar (Aguardando dependencias)"
Write-Host "  📝 Placeholder (Dados temporarios)"
