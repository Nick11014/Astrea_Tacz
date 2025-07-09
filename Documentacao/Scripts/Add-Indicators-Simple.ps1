# Script Simples para Indicadores Visuais
param(
    [string]$ProgressPath = "progress.md",
    [string]$OutputPath = "progress-enhanced.md"
)

Write-Host "Adicionando indicadores visuais..." -ForegroundColor Green

# Lista de arquivos com implementacao minima
$minimalFiles = @{
    "IFunctionalRenderer.java" = "TODO"
    "ReloadResourceEvent.java" = "PLACEHOLDER"
    "ServerMessageGunDraw.java" = "PLACEHOLDER"
    "ServerMessageGunFire.java" = "PLACEHOLDER"
    "RenderDistance.java" = "TODO"
    "AmmoCountPapi.java" = "PLACEHOLDER"
    "AnimationPlan.java" = "PLACEHOLDER"
    "AttachmentDataManager.java" = "TODO"
    "AttachmentItemRenderer.java" = "OBJECT+TODO"
    "AttachmentPropertyEvent.java" = "PLACEHOLDER"
    "CommonAttachmentIndex.java" = "TODO"
    "CommonBlockIndex.java" = "TODO"
    "CommonGunIndex.java" = "TODO"
    "ExplodeUtil.java" = "TODO"
    "KubeJSGunEventPoster.java" = "PLACEHOLDER"
    "ModBlocks.java" = "PLACEHOLDER"
    "ModEntities.java" = "PLACEHOLDER"
    "ModItems.java" = "PLACEHOLDER"
    "PapiManager.java" = "PLACEHOLDER"
    "ShooterDataHolder.java" = "PLACEHOLDER"
    "SoundManager.java" = "PLACEHOLDER"
    "TextShowRender.java" = "TODO"
    "AttachmentRender.java" = "OBJECT+PLACEHOLDER"
    "BedrockAttachmentModel.java" = "OBJECT+TODO"
    "BedrockGunModel.java" = "TODO"
    "ClientAttachmentIndex.java" = "OBJECT"
    "CommonAssetsManager.java" = "TODO"
    "CommonNetworkCache.java" = "TODO"
    "GunData.java" = "TODO"
    "ModCreativeTabs.java" = "PLACEHOLDER"
}

# Ler arquivo atual
$content = Get-Content $ProgressPath -Encoding UTF8
$enhancedContent = @()

foreach ($line in $content) {
    if ($line -match '- \[x\] \*\*(.+\.java)\*\* - HABILITADO(.*)') {
        $fileName = $matches[1]
        $rest = $matches[2]
        
        if ($minimalFiles.ContainsKey($fileName)) {
            $indicator = $minimalFiles[$fileName]
            $enhancedContent += "- [x] **$fileName** - HABILITADO (MINIMA: $indicator)$rest"
        } else {
            $enhancedContent += $line
        }
    } else {
        $enhancedContent += $line
    }
}

# Salvar
$enhancedContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host "Arquivo aprimorado salvo em: $OutputPath" -ForegroundColor Green

# Contar indicadores
$totalIndicators = 0
foreach ($line in $enhancedContent) {
    if ($line -match 'MINIMA:') {
        $totalIndicators++
    }
}

Write-Host "Indicadores adicionados: $totalIndicators arquivos" -ForegroundColor Yellow
Write-Host ""
Write-Host "LEGENDA:" -ForegroundColor Green
Write-Host "  OBJECT = Object Strategy (alta prioridade)"
Write-Host "  TODO = Aguardando dependencias"
Write-Host "  PLACEHOLDER = Dados temporarios"
