param(
    [string]$PlanoPath = "PLANO_MIGRACAO.md",
    [string]$OutputPath = "progress.md",
    [string]$ProjectRoot = "src/main/java"
)

Write-Host "Iniciando analise de progresso..." -ForegroundColor Green

function Test-FileEnabled {
    param([string]$FileName, [string]$ProjectPath)
    
    $enabledFile = Get-ChildItem -Path $ProjectPath -Recurse -Filter "$FileName" -ErrorAction SilentlyContinue
    $disabledFile = Get-ChildItem -Path $ProjectPath -Recurse -Filter "$FileName.disabled" -ErrorAction SilentlyContinue
    
    if ($enabledFile) {
        return @{ Status = "HABILITADO"; Enabled = $true }
    }
    elseif ($disabledFile) {
        return @{ Status = "DESABILITADO"; Enabled = $false }
    }
    else {
        return @{ Status = "NAO ENCONTRADO"; Enabled = $false }
    }
}

$planoContent = Get-Content $PlanoPath -Encoding UTF8
$checklistItems = @()
$currentPhase = ""

foreach ($line in $planoContent) {
    if ($line -match '^## .*(Fase|FASE)') {
        $currentPhase = $line -replace '^## ', ''
    }
    
    if ($line -match '^- \[([x ])\] (.+\.java)') {
        $isChecked = $matches[1] -eq 'x'
        $fileName = $matches[2]
        
        if ($fileName -match '([^/\\]+\.java)') {
            $fileName = $matches[1]
        }
        
        $checklistItems += @{
            Phase = $currentPhase
            FileName = $fileName
            MarkedAsComplete = $isChecked
        }
    }
}

Write-Host "Encontrados $($checklistItems.Count) itens no checklist"

$results = @()
$totalFiles = 0
$enabledFiles = 0
$correctlyMarked = 0

foreach ($item in $checklistItems) {
    $totalFiles++
    $realStatus = Test-FileEnabled -FileName $item.FileName -ProjectPath $ProjectRoot
    
    $isCorrect = ($item.MarkedAsComplete -eq $realStatus.Enabled)
    
    if ($isCorrect) { $correctlyMarked++ }
    if ($realStatus.Enabled) { $enabledFiles++ }
    
    $results += @{
        Phase = $item.Phase
        FileName = $item.FileName
        MarkedAsComplete = $item.MarkedAsComplete
        IsEnabled = $realStatus.Enabled
        IsCorrect = $isCorrect
    }
}

$completionPercent = [math]::Round(($enabledFiles / $totalFiles) * 100, 1)

Write-Host ""
Write-Host "ESTATISTICAS:" -ForegroundColor Green
Write-Host "  Total: $totalFiles arquivos"
Write-Host "  Habilitados: $enabledFiles ($completionPercent%)"
Write-Host "  Corretos: $correctlyMarked"

$date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

$reportContent = @"
# RELATORIO DE PROGRESSO - TacZ NeoForge 1.21.1

**Data:** $date
**Total:** $totalFiles arquivos
**Habilitados:** $enabledFiles ($completionPercent%)

---

## CHECKLIST ATUALIZADO

"@

$resultsByPhase = $results | Group-Object Phase

foreach ($phaseGroup in $resultsByPhase) {
    $phaseName = $phaseGroup.Name
    $phaseItems = $phaseGroup.Group
    $phaseEnabled = ($phaseItems | Where-Object { $_.IsEnabled }).Count
    $phaseTotal = $phaseItems.Count
    
    $reportContent += "`n### $phaseName`n"
    $reportContent += "**Progresso:** $phaseEnabled/$phaseTotal`n`n"
    
    foreach ($item in $phaseItems) {
        $checkbox = if ($item.IsEnabled) { "[x]" } else { "[ ]" }
        $status = if ($item.IsEnabled) { "HABILITADO" } else { "DESABILITADO" }
        $warning = if ($item.IsCorrect) { "" } else { " (PRECISA ATUALIZAR)" }
        
        $reportContent += "- $checkbox **$($item.FileName)** - $status$warning`n"
    }
}

$inconsistencies = $results | Where-Object { -not $_.IsCorrect }

if ($inconsistencies.Count -gt 0) {
    $reportContent += "`n---`n`n## INCONSISTENCIAS`n`n"
    
    foreach ($item in $inconsistencies) {
        $markedText = if ($item.MarkedAsComplete) { "MARCADO como completo" } else { "MARCADO como pendente" }
        $realText = if ($item.IsEnabled) { "REALMENTE habilitado" } else { "REALMENTE desabilitado" }
        
        $reportContent += "- **$($item.FileName)** - $markedText, mas $realText`n"
    }
}

$reportContent += "`n---`n`nGerado automaticamente`n"

$reportContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host ""
Write-Host "RELATORIO SALVO EM: $OutputPath" -ForegroundColor Green
Write-Host "Inconsistencias: $($inconsistencies.Count)"
