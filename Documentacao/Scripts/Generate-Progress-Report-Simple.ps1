# Script para Análise de Progresso - TacZ NeoForge 1.21.1
# Versão Simplificada

param(
    [string]$PlanoPath = "PLANO_MIGRACAO.md",
    [string]$OutputPath = "progress.md",
    [string]$ProjectRoot = "src/main/java"
)

Write-Host "Iniciando análise de progresso - TacZ NeoForge 1.21.1" -ForegroundColor Green

# Função para verificar se arquivo está habilitado
function Test-FileEnabled {
    param([string]$FileName, [string]$ProjectPath)
    
    $enabledFile = Get-ChildItem -Path $ProjectPath -Recurse -Filter "$FileName" -ErrorAction SilentlyContinue
    $disabledFile = Get-ChildItem -Path $ProjectPath -Recurse -Filter "$FileName.disabled" -ErrorAction SilentlyContinue
    
    if ($enabledFile) {
        return @{ Status = "HABILITADO"; Enabled = $true; Path = $enabledFile.FullName }
    }
    elseif ($disabledFile) {
        return @{ Status = "DESABILITADO"; Enabled = $false; Path = $disabledFile.FullName }
    }
    else {
        return @{ Status = "NAO ENCONTRADO"; Enabled = $false; Path = "" }
    }
}

# Ler o arquivo PLANO_MIGRACAO.md
if (-not (Test-Path $PlanoPath)) {
    Write-Error "Arquivo $PlanoPath não encontrado!"
    exit 1
}

Write-Host "Lendo checklist do $PlanoPath..." -ForegroundColor Yellow

$planoContent = Get-Content $PlanoPath -Encoding UTF8
$checklistItems = @()
$currentPhase = ""
$checklistRegex = '^- \[([x ])\] (.+\.java).*$'

foreach ($line in $planoContent) {
    if ($line -match '^## .*(Fase|FASE) (\d+|[A-Z]+)') {
        $currentPhase = $matches[0] -replace '^## ', ''
        Write-Host "Processando: $currentPhase" -ForegroundColor Cyan
    }
    
    if ($line -match $checklistRegex) {
        $isChecked = $matches[1] -eq 'x'
        $fileName = $matches[2]
        
        if ($fileName -match '([^/\\]+\.java)') {
            $fileName = $matches[1]
        }
        
        $checklistItems += @{
            Phase = $currentPhase
            FileName = $fileName
            MarkedAsComplete = $isChecked
            OriginalLine = $line.Trim()
        }
    }
}

Write-Host "Encontrados $($checklistItems.Count) itens no checklist" -ForegroundColor Yellow

# Analisar status real de cada arquivo
$results = @()
$totalFiles = 0
$enabledFiles = 0
$correctlyMarked = 0

Write-Host "Analisando status real dos arquivos..." -ForegroundColor Yellow

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
        RealStatus = $realStatus.Status
        IsEnabled = $realStatus.Enabled
        IsCorrect = $isCorrect
        FilePath = $realStatus.Path
        OriginalLine = $item.OriginalLine
    }
    
    $statusIcon = if ($isCorrect) { "OK" } else { "ERRO" }
    Write-Host "  $statusIcon $($item.FileName) - Marcado: $($item.MarkedAsComplete), Real: $($realStatus.Enabled)" -ForegroundColor White
}

# Calcular estatísticas
$accuracyPercent = [math]::Round(($correctlyMarked / $totalFiles) * 100, 1)
$completionPercent = [math]::Round(($enabledFiles / $totalFiles) * 100, 1)

Write-Host ""
Write-Host "ESTATÍSTICAS FINAIS:" -ForegroundColor Green
Write-Host "  Total de arquivos analisados: $totalFiles" -ForegroundColor White
Write-Host "  Arquivos habilitados: $enabledFiles ($completionPercent%)" -ForegroundColor Green
Write-Host "  Marcações corretas: $correctlyMarked ($accuracyPercent%)" -ForegroundColor Green

# Gerar relatório
$date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

$reportContent = @"
# 📊 RELATÓRIO DE PROGRESSO - TacZ NeoForge 1.21.1

**Data de Geração:** $date
**Script:** Generate-Progress-Report.ps1
**Status:** Análise automática de checklist vs estado real do projeto

---

## 🎯 **RESUMO EXECUTIVO**

### **📊 Estatísticas Gerais:**
- **Total de arquivos analisados:** $totalFiles
- **Arquivos habilitados:** $enabledFiles (**$completionPercent%**)
- **Marcações corretas:** $correctlyMarked (**$accuracyPercent%**)

### **🎯 Status de Conclusão:**
$(if ($completionPercent -ge 90) { "🟢 **PROJETO QUASE COMPLETO**" } 
elseif ($completionPercent -ge 70) { "🟡 **BOA EVOLUÇÃO**" }
elseif ($completionPercent -ge 50) { "🟠 **PROGRESSO INTERMEDIÁRIO**" }
else { "🔴 **INÍCIO DO DESENVOLVIMENTO**" })

---

## 📋 **CHECKLIST ATUALIZADO**

"@

# Agrupar por fase
$resultsByPhase = $results | Group-Object Phase

foreach ($phaseGroup in $resultsByPhase) {
    $phaseName = $phaseGroup.Name
    $phaseItems = $phaseGroup.Group
    $phaseEnabled = ($phaseItems | Where-Object { $_.IsEnabled }).Count
    $phaseTotal = $phaseItems.Count
    $phasePercent = [math]::Round(($phaseEnabled / $phaseTotal) * 100, 1)
    
    $reportContent += "`n### **$phaseName**`n"
    $reportContent += "**Progresso:** $phaseEnabled/$phaseTotal (**$phasePercent%**)`n`n"
    
    foreach ($item in $phaseItems) {
        $checkbox = if ($item.IsEnabled) { "[x]" } else { "[ ]" }
        $statusIcon = if ($item.IsCorrect) { "" } else { " ⚠️" }
        $realStatusText = if ($item.IsEnabled) { "✅ HABILITADO" } else { "❌ DESABILITADO" }
        
        $reportContent += "- $checkbox **$($item.FileName)** - $realStatusText$statusIcon`n"
    }
}

# Inconsistências
$inconsistencies = $results | Where-Object { -not $_.IsCorrect }

if ($inconsistencies.Count -gt 0) {
    $reportContent += "`n---`n`n## ⚠️ **INCONSISTÊNCIAS DETECTADAS**`n`n"
    $reportContent += "Os seguintes arquivos têm marcações incorretas no checklist:`n`n"
    
    foreach ($item in $inconsistencies) {
        $markedText = if ($item.MarkedAsComplete) { "MARCADO como completo" } else { "MARCADO como pendente" }
        $realText = if ($item.IsEnabled) { "REALMENTE habilitado" } else { "REALMENTE desabilitado" }
        
        $reportContent += "- **$($item.FileName)** - $markedText, mas $realText`n"
    }
}

# Arquivos recém-habilitados
$recentlyEnabled = $results | Where-Object { $_.IsEnabled -and -not $_.MarkedAsComplete }

if ($recentlyEnabled.Count -gt 0) {
    $reportContent += "`n---`n`n## 🚀 **ARQUIVOS RECÉM-HABILITADOS**`n`n"
    $reportContent += "Arquivos que foram habilitados mas ainda não atualizados no checklist:`n`n"
    
    foreach ($item in $recentlyEnabled) {
        $reportContent += "- **$($item.FileName)** - ✅ Habilitado (precisa atualizar checklist)`n"
    }
}

$reportContent += "`n---`n`n**Gerado automaticamente por Generate-Progress-Report.ps1**`n"

# Salvar relatório
$reportContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host ""
Write-Host "RELATÓRIO GERADO COM SUCESSO!" -ForegroundColor Green
Write-Host "Arquivo salvo em: $OutputPath" -ForegroundColor Yellow
Write-Host "Inconsistências encontradas: $($inconsistencies.Count)" -ForegroundColor $(if ($inconsistencies.Count -eq 0) { "Green" } else { "Red" })
Write-Host "PROGRESSO ATUAL: $enabledFiles/$totalFiles arquivos ($completionPercent%)" -ForegroundColor Green
