# Script para Análise de Progresso - TacZ NeoForge 1.21.1
# Analisa o PLANO_MIGRACAO.md e verifica o status real dos arquivos no projeto

param(
    [string]$PlanoPath = "PLANO_MIGRACAO.md",
    [string]$OutputPath = "progress.md",
    [string]$ProjectRoot = "src/main/java"
)

Write-Host "🔍 INICIANDO ANÁLISE DE PROGRESSO - TacZ NeoForge 1.21.1" -ForegroundColor Green
Write-Host "══════════════════════════════════════════════════════════" -ForegroundColor Green

# Função para verificar se arquivo está habilitado
function Test-FileEnabled {
    param(
        [string]$FileName,
        [string]$ProjectPath
    )
    
    # Procurar arquivo .java habilitado
    $enabledFile = Get-ChildItem -Path $ProjectPath -Recurse -Filter "$FileName" -ErrorAction SilentlyContinue
    
    # Procurar arquivo .disabled
    $disabledFile = Get-ChildItem -Path $ProjectPath -Recurse -Filter "$FileName.disabled" -ErrorAction SilentlyContinue
    
    if ($enabledFile) {
        return @{
            Status = "HABILITADO"
            Path = $enabledFile.FullName
            Enabled = $true
        }
    }
    elseif ($disabledFile) {
        return @{
            Status = "DESABILITADO"
            Path = $disabledFile.FullName
            Enabled = $false
        }
    }
    else {
        return @{
            Status = "NAO ENCONTRADO"
            Path = ""
            Enabled = $false
        }
    }
}
}

# Ler o arquivo PLANO_MIGRACAO.md
if (-not (Test-Path $PlanoPath)) {
    Write-Error "Arquivo $PlanoPath não encontrado!"
    exit 1
}

Write-Host "📋 Lendo checklist do $PlanoPath..." -ForegroundColor Yellow

$planoContent = Get-Content $PlanoPath -Encoding UTF8
$checklistItems = @()
$currentPhase = ""

# Regex para detectar itens de checklist
$checklistRegex = '^- \[([x ])\] (.+\.java).*$'

foreach ($line in $planoContent) {
    # Detectar mudança de fase
    if ($line -match '^## .*(Fase|FASE) (\d+|[A-Z]+)') {
        $currentPhase = $matches[0] -replace '^## ', ''
        Write-Host "📂 Processando: $currentPhase" -ForegroundColor Cyan
    }
    
    # Detectar itens de checklist
    if ($line -match $checklistRegex) {
        $isChecked = $matches[1] -eq 'x'
        $fileName = $matches[2]
        
        # Extrair apenas o nome do arquivo (sem caminho)
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

Write-Host "🔍 Encontrados $($checklistItems.Count) itens no checklist" -ForegroundColor Yellow

# Analisar status real de cada arquivo
$results = @()
$totalFiles = 0
$enabledFiles = 0
$correctlyMarked = 0
$incorrectlyMarked = 0

Write-Host "🔍 Analisando status real dos arquivos..." -ForegroundColor Yellow

foreach ($item in $checklistItems) {
    $totalFiles++
    $realStatus = Test-FileEnabled -FileName $item.FileName -ProjectPath $ProjectRoot
    
    # Determinar se a marcação está correta
    $isCorrect = ($item.MarkedAsComplete -eq $realStatus.Enabled)
    
    if ($isCorrect) {
        $correctlyMarked++
    } else {
        $incorrectlyMarked++
    }
    
    if ($realStatus.Enabled) {
        $enabledFiles++
    }
    
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
    
    # Log do progresso
    $statusIcon = if ($isCorrect) { "✅" } else { "⚠️" }
    Write-Host "  $statusIcon $($item.FileName) - Marcado: $($item.MarkedAsComplete), Real: $($realStatus.Enabled)" -ForegroundColor White
}

# Calcular estatísticas
$accuracyPercent = [math]::Round(($correctlyMarked / $totalFiles) * 100, 1)
$completionPercent = [math]::Round(($enabledFiles / $totalFiles) * 100, 1)

Write-Host ""
Write-Host "📊 ESTATÍSTICAS FINAIS:" -ForegroundColor Green
Write-Host "  • Total de arquivos analisados: $totalFiles" -ForegroundColor White
Write-Host "  • Arquivos habilitados: $enabledFiles ($completionPercent%)" -ForegroundColor Green
Write-Host "  • Marcações corretas: $correctlyMarked ($accuracyPercent%)" -ForegroundColor Green
Write-Host "  • Marcações incorretas: $incorrectlyMarked" -ForegroundColor Red

# Gerar relatório em markdown
$reportContent = @"
# 📊 RELATÓRIO DE PROGRESSO - TacZ NeoForge 1.21.1

**Data de Geração:** $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")  
**Script:** Generate-Progress-Report.ps1  
**Status:** Análise automática de checklist vs estado real do projeto

---

## 🎯 **RESUMO EXECUTIVO**

### **📊 Estatísticas Gerais:**
- **Total de arquivos analisados:** $totalFiles
- **Arquivos habilitados:** $enabledFiles (**$completionPercent%**)
- **Marcações corretas:** $correctlyMarked (**$accuracyPercent%**)
- **Marcações incorretas:** $incorrectlyMarked

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
    
    $reportContent += @"

### **$phaseName**
**Progresso:** $phaseEnabled/$phaseTotal (**$phasePercent%**)

"@
    
    foreach ($item in $phaseItems) {
        $checkbox = if ($item.IsEnabled) { "[x]" } else { "[ ]" }
        $statusIcon = if ($item.IsCorrect) { "" } else { " ⚠️" }
        $realStatusText = if ($item.IsEnabled) { "✅ HABILITADO" } else { "❌ DESABILITADO" }
        
        $reportContent += "- $checkbox **$($item.FileName)** - $realStatusText$statusIcon`n"
    }
}

# Adicionar seção de inconsistências
$inconsistencies = $results | Where-Object { -not $_.IsCorrect }

if ($inconsistencies.Count -gt 0) {
    $reportContent += @"

---

## ⚠️ **INCONSISTÊNCIAS DETECTADAS**

Os seguintes arquivos têm marcações incorretas no checklist:

"@
    
    foreach ($item in $inconsistencies) {
        $markedText = if ($item.MarkedAsComplete) { "MARCADO como completo" } else { "MARCADO como pendente" }
        $realText = if ($item.IsEnabled) { "REALMENTE habilitado" } else { "REALMENTE desabilitado" }
        
        $reportContent += "- **$($item.FileName)** - $markedText, mas $realText`n"
    }
}

# Adicionar arquivos recém-habilitados
$recentlyEnabled = $results | Where-Object { $_.IsEnabled -and -not $_.MarkedAsComplete }

if ($recentlyEnabled.Count -gt 0) {
    $reportContent += @"

---

## 🚀 **ARQUIVOS RECÉM-HABILITADOS**

Arquivos que foram habilitados mas ainda não atualizados no checklist:

"@
    
    foreach ($item in $recentlyEnabled) {
        $reportContent += "- **$($item.FileName)** - ✅ Habilitado (precisa atualizar checklist)`n"
    }
}

# Adicionar próximos candidatos
$disabledFiles = $results | Where-Object { -not $_.IsEnabled }

if ($disabledFiles.Count -gt 0) {
    $reportContent += @"

---

## 📋 **PRÓXIMOS CANDIDATOS PARA HABILITAÇÃO**

Arquivos que ainda precisam ser habilitados:

"@
    
    $topCandidates = $disabledFiles | Select-Object -First 10
    foreach ($item in $topCandidates) {
        $reportContent += "- **$($item.FileName)** - ❌ Aguardando habilitação`n"
    }
    
    if ($disabledFiles.Count -gt 10) {
        $reportContent += "`n*... e mais $($disabledFiles.Count - 10) arquivos*`n"
    }
}

$reportContent += @"

---

## 🔄 **COMO USAR ESTE RELATÓRIO**

1. **Atualize o checklist** marcando os arquivos recém-habilitados
2. **Foque nos próximos candidatos** para continuar o progresso
3. **Execute novamente** este script após mudanças: `.\Generate-Progress-Report.ps1`

---

**Gerado automaticamente por Generate-Progress-Report.ps1**
"@

# Salvar relatório
$reportContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host ""
Write-Host "✅ RELATÓRIO GERADO COM SUCESSO!" -ForegroundColor Green
Write-Host "📄 Arquivo salvo em: $OutputPath" -ForegroundColor Yellow
Write-Host "🔍 Total de inconsistências encontradas: $($inconsistencies.Count)" -ForegroundColor $(if ($inconsistencies.Count -eq 0) { "Green" } else { "Red" })

if ($inconsistencies.Count -gt 0) {
    Write-Host ""
    Write-Host "⚠️  ATENÇÃO: Foram encontradas inconsistências no checklist!" -ForegroundColor Red
    Write-Host "   Verifique a seção 'INCONSISTÊNCIAS DETECTADAS' no relatório." -ForegroundColor Red
}

Write-Host ""
Write-Host "🎯 PROGRESSO ATUAL: $enabledFiles/$totalFiles arquivos ($completionPercent%)" -ForegroundColor Green
Write-Host "══════════════════════════════════════════════════════════" -ForegroundColor Green
