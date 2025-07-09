# Script Simples para Analisar Implementacoes Minimas
param(
    [string]$ProjectRoot = "src/main/java",
    [string]$ProgressPath = "progress.md"
)

Write-Host "Analisando implementacoes minimas..." -ForegroundColor Green

# Padroes que indicam implementacao minima
$patterns = @(
    "implementacao minima",
    "Object Strategy",
    "placeholder",
    "TODO.*implementacao",
    "TODO.*restaurar"
)

function Test-MinimalImplementation {
    param([string]$FilePath)
    
    if (-not (Test-Path $FilePath)) {
        return @{ IsMinimal = $false; Patterns = @() }
    }
    
    $content = Get-Content $FilePath -ErrorAction SilentlyContinue
    if (-not $content) {
        return @{ IsMinimal = $false; Patterns = @() }
    }
    
    $foundPatterns = @()
    
    foreach ($pattern in $patterns) {
        $matches = $content | Select-String -Pattern $pattern -CaseSensitive:$false
        if ($matches) {
            $foundPatterns += $pattern
        }
    }
    
    return @{
        IsMinimal = $foundPatterns.Count -gt 0
        Patterns = $foundPatterns
    }
}

# Ler progress.md e extrair arquivos habilitados
$progressContent = Get-Content $ProgressPath -Encoding UTF8
$habilitados = @()

foreach ($line in $progressContent) {
    if ($line -match '- \[x\] \*\*(.+\.java)\*\* - HABILITADO') {
        $fileName = $matches[1]
        $habilitados += $fileName
    }
}

Write-Host "Encontrados $($habilitados.Count) arquivos habilitados"

# Analisar cada arquivo
$results = @()
$minimalCount = 0
$objectStrategyCount = 0
$placeholderCount = 0
$todoCount = 0

foreach ($fileName in $habilitados) {
    $file = Get-ChildItem -Path $ProjectRoot -Recurse -Filter $fileName -ErrorAction SilentlyContinue | Select-Object -First 1
    
    if ($file) {
        $analysis = Test-MinimalImplementation -FilePath $file.FullName
        
        if ($analysis.IsMinimal) {
            $minimalCount++
            
            if ($analysis.Patterns -contains "Object Strategy") {
                $objectStrategyCount++
            }
            if ($analysis.Patterns -contains "placeholder") {
                $placeholderCount++
            }
            if ($analysis.Patterns | Where-Object { $_ -match "TODO" }) {
                $todoCount++
            }
        }
        
        $results += @{
            FileName = $fileName
            IsMinimal = $analysis.IsMinimal
            Patterns = $analysis.Patterns
        }
    }
}

Write-Host ""
Write-Host "ESTATISTICAS DE IMPLEMENTACAO:" -ForegroundColor Green
Write-Host "  Total analisados: $($results.Count)"
Write-Host "  Implementacao minima: $minimalCount"
Write-Host "  Implementacao completa: $($results.Count - $minimalCount)"
Write-Host "  Object Strategy: $objectStrategyCount"
Write-Host "  Placeholders: $placeholderCount"
Write-Host "  TODO/Restaurar: $todoCount"

Write-Host ""
Write-Host "ARQUIVOS COM IMPLEMENTACAO MINIMA:" -ForegroundColor Yellow

$minimalFiles = $results | Where-Object { $_.IsMinimal }
foreach ($file in $minimalFiles) {
    $typeInfo = ""
    if ($file.Patterns -contains "Object Strategy") {
        $typeInfo += "[OBJECT] "
    }
    if ($file.Patterns -contains "placeholder") {
        $typeInfo += "[PLACEHOLDER] "
    }
    if ($file.Patterns | Where-Object { $_ -match "TODO" }) {
        $typeInfo += "[TODO] "
    }
    if ($file.Patterns | Where-Object { $_ -match "implementacao minima" }) {
        $typeInfo += "[MINIMAL] "
    }
    
    Write-Host "  $typeInfo$($file.FileName)"
}

# Criar resumo em arquivo
$summaryContent = @"
# ANALISE DE IMPLEMENTACOES MINIMAS - TacZ NeoForge 1.21.1

**Data:** $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")

## ESTATISTICAS

- **Total de arquivos habilitados:** $($results.Count)
- **Implementacao completa:** $($results.Count - $minimalCount) 
- **Implementacao minima:** $minimalCount
- **Object Strategy:** $objectStrategyCount
- **Placeholders:** $placeholderCount  
- **TODO/Restaurar:** $todoCount

## ARQUIVOS COM IMPLEMENTACAO MINIMA

"@

foreach ($file in $minimalFiles) {
    $typeInfo = ""
    if ($file.Patterns -contains "Object Strategy") {
        $typeInfo += "Object Strategy, "
    }
    if ($file.Patterns -contains "placeholder") {
        $typeInfo += "Placeholder, "
    }
    if ($file.Patterns | Where-Object { $_ -match "TODO" }) {
        $typeInfo += "TODO, "
    }
    if ($file.Patterns | Where-Object { $_ -match "implementacao minima" }) {
        $typeInfo += "Minimal, "
    }
    
    $typeInfo = $typeInfo.TrimEnd(", ")
    $summaryContent += "- **$($file.FileName)** - [$typeInfo]`n"
}

$summaryContent += @"

## PRIORIDADES

### Alta Prioridade (Object Strategy -> Real):
"@

$objectFiles = $results | Where-Object { $_.Patterns -contains "Object Strategy" } | Select-Object -First 10
foreach ($file in $objectFiles) {
    $summaryContent += "- $($file.FileName)`n"
}

$summaryContent += @"

### Media Prioridade (Minimal -> Completa):
"@

$minimalOnly = $results | Where-Object { 
    $_.IsMinimal -and 
    $_.Patterns -notcontains "Object Strategy" 
} | Select-Object -First 10

foreach ($file in $minimalOnly) {
    $summaryContent += "- $($file.FileName)`n"
}

$summaryContent | Out-File -FilePath "implementacoes-minimas.md" -Encoding UTF8

Write-Host ""
Write-Host "RESUMO SALVO EM: implementacoes-minimas.md" -ForegroundColor Green
