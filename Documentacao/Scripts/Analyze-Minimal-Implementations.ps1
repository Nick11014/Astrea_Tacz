# Script para Analisar Implementações Mínimas
param(
    [string]$ProjectRoot = "src/main/java",
    [string]$ProgressPath = "progress.md",
    [string]$OutputPath = "progress-enhanced.md"
)

Write-Host "Analisando implementações mínimas..." -ForegroundColor Green

# Padrões que indicam implementação mínima
$minimalPatterns = @(
    "implementação mínima",
    "minimal implementation", 
    "Object Strategy",
    "placeholder",
    "TODO.*implementação",
    "TODO.*restaurar",
    "TODO.*habilitar",
    "comentado temporariamente",
    "temporário",
    "estratégica.*mínima"
)

# Função para verificar se arquivo tem implementação mínima
function Test-MinimalImplementation {
    param([string]$FilePath)
    
    if (-not (Test-Path $FilePath)) {
        return @{ IsMinimal = $false; Patterns = @(); LineCount = 0 }
    }
    
    $content = Get-Content $FilePath -ErrorAction SilentlyContinue
    if (-not $content) {
        return @{ IsMinimal = $false; Patterns = @(); LineCount = 0 }
    }
    
    $foundPatterns = @()
    $lineCount = $content.Count
    
    foreach ($pattern in $minimalPatterns) {
        $matches = $content | Select-String -Pattern $pattern -CaseSensitive:$false
        if ($matches) {
            $foundPatterns += @{
                Pattern = $pattern
                Count = $matches.Count
                Lines = $matches | ForEach-Object { $_.LineNumber }
            }
        }
    }
    
    return @{
        IsMinimal = $foundPatterns.Count -gt 0
        Patterns = $foundPatterns
        LineCount = $lineCount
    }
}

# Ler progress.md
$progressContent = Get-Content $ProgressPath -Encoding UTF8

# Extrair arquivos habilitados
$habilitadosPattern = '- \[x\] \*\*(.+\.java)\*\* - HABILITADO'
$habilitados = @()

foreach ($line in $progressContent) {
    if ($line -match $habilitadosPattern) {
        $fileName = $matches[1]
        $habilitados += $fileName
    }
}

Write-Host "Encontrados $($habilitados.Count) arquivos habilitados"

# Analisar cada arquivo habilitado
$results = @()
$minimalCount = 0

foreach ($fileName in $habilitados) {
    Write-Host "Analisando $fileName..." -NoNewline
    
    # Procurar arquivo no projeto
    $file = Get-ChildItem -Path $ProjectRoot -Recurse -Filter $fileName -ErrorAction SilentlyContinue | Select-Object -First 1
    
    if ($file) {
        $analysis = Test-MinimalImplementation -FilePath $file.FullName
        
        if ($analysis.IsMinimal) {
            $minimalCount++
            Write-Host " [MÍNIMA]" -ForegroundColor Yellow
        } else {
            Write-Host " [COMPLETA]" -ForegroundColor Green
        }
        
        $results += @{
            FileName = $fileName
            IsMinimal = $analysis.IsMinimal
            Patterns = $analysis.Patterns
            LineCount = $analysis.LineCount
            FullPath = $file.FullName
        }
    } else {
        Write-Host " [NÃO ENCONTRADO]" -ForegroundColor Red
        $results += @{
            FileName = $fileName
            IsMinimal = $false
            Patterns = @()
            LineCount = 0
            FullPath = ""
        }
    }
}

Write-Host ""
Write-Host "ESTATÍSTICAS DE IMPLEMENTAÇÃO:" -ForegroundColor Green
Write-Host "  Total analisados: $($results.Count)"
Write-Host "  Implementação mínima: $minimalCount"
Write-Host "  Implementação completa: $($results.Count - $minimalCount)"

# Categorizar por tipo de implementação mínima
$objectStrategy = $results | Where-Object { 
    $_.Patterns | Where-Object { $_.Pattern -match "Object Strategy" }
}

$placeholders = $results | Where-Object { 
    $_.Patterns | Where-Object { $_.Pattern -match "placeholder" }
}

$todoRestore = $results | Where-Object { 
    $_.Patterns | Where-Object { $_.Pattern -match "TODO.*restaurar" }
}

$minimal = $results | Where-Object { 
    $_.Patterns | Where-Object { $_.Pattern -match "implementação mínima" }
}

Write-Host ""
Write-Host "CATEGORIAS DE IMPLEMENTAÇÃO MÍNIMA:" -ForegroundColor Yellow
Write-Host "  Object Strategy: $($objectStrategy.Count)"
Write-Host "  Placeholders: $($placeholders.Count)"
Write-Host "  TODO Restaurar: $($todoRestore.Count)"
Write-Host "  Explicitamente Mínima: $($minimal.Count)"

# Gerar relatório aprimorado
$enhancedContent = @()

# Copiar conteúdo original até estatísticas
$copying = $true
foreach ($line in $progressContent) {
    if ($line -match "^---$" -and $copying) {
        $enhancedContent += $line
        break
    }
    $enhancedContent += $line
}

# Adicionar nova seção de análise de implementação
$enhancedContent += ""
$enhancedContent += "## 🔍 **ANÁLISE DE IMPLEMENTAÇÕES**"
$enhancedContent += ""
$enhancedContent += "### **📊 Tipos de Implementação:**"
$enhancedContent += "- **Implementação Completa:** $($results.Count - $minimalCount) arquivos"
$enhancedContent += "- **Implementação Mínima:** $minimalCount arquivos"
$enhancedContent += ""
$enhancedContent += "### **🛠️ Categorias de Implementação Mínima:**"
$enhancedContent += "- **Object Strategy:** $($objectStrategy.Count) arquivos (resolvem dependências quebradas)"
$enhancedContent += "- **Placeholders:** $($placeholders.Count) arquivos (dados temporários)"
$enhancedContent += "- **TODO Restaurar:** $($todoRestore.Count) arquivos (aguardando dependências)"
$enhancedContent += "- **Explicitamente Mínima:** $($minimal.Count) arquivos (funcionalidade reduzida)"
$enhancedContent += ""

if ($objectStrategy.Count -gt 0) {
    $enhancedContent += "### **🎯 Arquivos com Object Strategy:**"
    foreach ($file in $objectStrategy) {
        $enhancedContent += "- **$($file.FileName)** - Resolve imports problemáticos"
    }
    $enhancedContent += ""
}

if ($minimal.Count -gt 0) {
    $enhancedContent += "### **⚡ Arquivos com Implementação Explicitamente Mínima:**"
    foreach ($file in $minimal) {
        $patternCount = ($file.Patterns | Measure-Object).Count
        $enhancedContent += "- **$($file.FileName)** - $patternCount padrões de implementação mínima"
    }
    $enhancedContent += ""
}

# Adicionar seção de prioridades
$enhancedContent += "### **🎯 PRIORIDADES PARA EXPANSÃO:**"
$enhancedContent += ""
$enhancedContent += "#### **Alta Prioridade** (Object Strategy → Implementação Real):"
foreach ($file in $objectStrategy | Select-Object -First 5) {
    $enhancedContent += "- **$($file.FileName)** - Expandir de Object para tipos reais"
}
$enhancedContent += ""

$enhancedContent += "#### **Média Prioridade** (Implementação Mínima → Completa):"
foreach ($file in $minimal | Select-Object -First 5) {
    $enhancedContent += "- **$($file.FileName)** - Adicionar funcionalidades completas"
}
$enhancedContent += ""

# Continuar com o resto do conteúdo original
$skipUntilChecklist = $true
foreach ($line in $progressContent) {
    if ($line -match "^## CHECKLIST ATUALIZADO") {
        $skipUntilChecklist = $false
    }
    
    if (-not $skipUntilChecklist) {
        # Modificar linhas de arquivos habilitados para incluir tipo de implementação
        if ($line -match '- \[x\] \*\*(.+\.java)\*\* - HABILITADO(.*)') {
            $fileName = $matches[1]
            $rest = $matches[2]
            
            $fileResult = $results | Where-Object { $_.FileName -eq $fileName }
            if ($fileResult -and $fileResult.IsMinimal) {
                $implType = ""
                if ($fileResult.Patterns | Where-Object { $_.Pattern -match "Object Strategy" }) {
                    $implType = " 🎯"
                } elseif ($fileResult.Patterns | Where-Object { $_.Pattern -match "implementação mínima" }) {
                    $implType = " ⚡"
                } elseif ($fileResult.Patterns | Where-Object { $_.Pattern -match "placeholder" }) {
                    $implType = " 📝"
                } else {
                    $implType = " 🔧"
                }
                $enhancedContent += "- [x] **$fileName** - HABILITADO (MÍNIMA$implType)$rest"
            } else {
                $enhancedContent += $line
            }
        } else {
            $enhancedContent += $line
        }
    }
}

# Salvar versão aprimorada
$enhancedContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host ""
Write-Host "RELATÓRIO APRIMORADO GERADO!" -ForegroundColor Green
Write-Host "Arquivo salvo em: $OutputPath" -ForegroundColor Yellow
Write-Host ""
Write-Host "RESUMO:" -ForegroundColor Green
Write-Host "  Arquivos com Object Strategy: $($objectStrategy.Count)" -ForegroundColor Yellow
Write-Host "  Arquivos com implementação mínima: $minimalCount" -ForegroundColor Yellow
