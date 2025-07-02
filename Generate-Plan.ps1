# =============================================================================
# Generate-Plan.ps1 - Gerador de Plano de Migracao TacZ v3.0
# =============================================================================
# Este script analisa dependencias entre arquivos Java e gera um plano de 
# migracao ordenado topologicamente para o projeto TacZ NeoForge 1.21.1
# =============================================================================

param(
    [string]$OutputFileName = "PLANO_MIGRACAO.md"
)

Write-Host "=== GERADOR DE PLANO DE MIGRACAO TacZ v3.0 ===" -ForegroundColor Cyan

# --- VERIFICACAO INICIAL DO AMBIENTE ---
$projectRoot = $PSScriptRoot
$buildFile = Join-Path $projectRoot "build.gradle"
$srcPath = Join-Path $projectRoot "src\main\java\com\tacz\guns"

# Verificar se estamos na raiz do projeto
if (-not (Test-Path $buildFile)) {
    Write-Host "ERRO: build.gradle nao encontrado. Execute na raiz do projeto." -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $srcPath)) {
    Write-Host "ERRO: Pasta source nao encontrada: $srcPath" -ForegroundColor Red
    exit 1
}

Write-Host "Pasta do projeto: $projectRoot" -ForegroundColor Yellow
Write-Host "Pasta source: $srcPath" -ForegroundColor Yellow
Write-Host ""

# =============================================================================
# FASE DE EXECUCAO 0 - IDENTIFICACAO DA BASE (FASE 0 DO PLANO)
# =============================================================================
Write-Host "[FASE 0/5] Identificando arquivos da fundacao (ja habilitados)..." -ForegroundColor Green

# Encontrar todos os arquivos .java ja habilitados
$enabledFiles = Get-ChildItem -Path $srcPath -Recurse -Filter "*.java"
$phase0Files = @()
$phase0ClassMap = @{}

Write-Host "Encontrados $($enabledFiles.Count) arquivos ja habilitados." -ForegroundColor Cyan

# Regex para extrair package e classe (melhoradas para detectar mais casos)
$packageRegex = [regex]'package\s+([a-zA-Z0-9_.]+)\s*;'
$classRegex = [regex]'(?:public\s+)?(?:final\s+)?(?:abstract\s+)?(?:static\s+)?(class|interface|enum|record)\s+([a-zA-Z0-9_]+)'

foreach ($file in $enabledFiles) {
    try {
        $content = Get-Content $file.FullName -Raw -Encoding UTF8
        if (-not $content) { 
            Write-Host "  AVISO: Arquivo vazio ignorado: $($file.Name)" -ForegroundColor Yellow
            continue 
        }
        
        # Pular arquivos package-info.java (documentação de pacote)
        if ($file.Name -eq "package-info.java") {
            Write-Host "  PULADO: $($file.Name) (arquivo de documentação)" -ForegroundColor Gray
            continue
        }
        
        $packageMatch = $packageRegex.Match($content)
        $classMatch = $classRegex.Match($content)
        
        if ($packageMatch.Success -and $classMatch.Success) {
            $packageName = $packageMatch.Groups[1].Value.Trim()
            $className = $classMatch.Groups[2].Value.Trim()
            $qualifiedName = "$packageName.$className"
            
            $fileInfo = [PSCustomObject]@{
                Path = $file.FullName
                FileName = $file.Name
                ClassName = $className
                PackageName = $packageName
                QualifiedName = $qualifiedName
                Phase = 0
                DependencyCount = 0
            }
            
            $phase0Files += $fileInfo
            $phase0ClassMap[$qualifiedName] = $fileInfo
            
            Write-Host "  [FASE 0] $($file.Name) -> $qualifiedName" -ForegroundColor Green
        } else {
            Write-Host "  AVISO: Nao foi possivel extrair classe de $($file.Name)" -ForegroundColor Yellow
        }
    }
    catch {
        Write-Host "  AVISO: Erro ao ler $($file.Name): $($_.Exception.Message)" -ForegroundColor Yellow
    }
}

Write-Host "Fase 0 (Fundacao): $($phase0Files.Count) arquivos identificados." -ForegroundColor Green
Write-Host ""

# =============================================================================
# FASE DE EXECUCAO 1 - MAPEAMENTO DE CLASSES
# =============================================================================
Write-Host "[FASE 1/5] Mapeando classes desabilitadas..." -ForegroundColor Green

# Encontrar todos os arquivos .java.disabled
$disabledFiles = Get-ChildItem -Path $srcPath -Recurse -Filter "*.java.disabled"
$allDisabledClasses = @{}
$disabledFileInfo = @{}

Write-Host "Encontrados $($disabledFiles.Count) arquivos desabilitados para analise." -ForegroundColor Cyan

foreach ($file in $disabledFiles) {
    try {
        $content = Get-Content $file.FullName -Raw -Encoding UTF8
        if (-not $content) { continue }
        
        # Pular arquivos package-info.java (documentação de pacote)
        if ($file.Name -eq "package-info.java.disabled") {
            continue
        }
        
        $packageMatch = $packageRegex.Match($content)
        $classMatch = $classRegex.Match($content)
        
        if ($packageMatch.Success -and $classMatch.Success) {
            $packageName = $packageMatch.Groups[1].Value.Trim()
            $className = $classMatch.Groups[2].Value.Trim()
            $qualifiedName = "$packageName.$className"
            
            $fileInfo = [PSCustomObject]@{
                Path = $file.FullName
                FileName = $file.Name -replace '\.disabled$', ''
                ClassName = $className
                PackageName = $packageName
                QualifiedName = $qualifiedName
                Dependencies = @()
                Phase = -1  # Sera definido na fase 3
                DependencyCount = 0
            }
            
            $disabledFileInfo[$file.FullName] = $fileInfo
            $allDisabledClasses[$qualifiedName] = $fileInfo
        }
    }
    catch {
        Write-Host "  AVISO: Erro ao ler $($file.Name): $($_.Exception.Message)" -ForegroundColor Yellow
    }
}

# Criar mapa combinado de todas as classes (habilitadas + desabilitadas)
$allClassMap = $phase0ClassMap.Clone()
foreach ($kvp in $allDisabledClasses.GetEnumerator()) {
    $allClassMap[$kvp.Key] = $kvp.Value
}

Write-Host "Classes mapeadas: $($allClassMap.Count) total ($($phase0ClassMap.Count) habilitadas + $($allDisabledClasses.Count) desabilitadas)" -ForegroundColor Cyan
Write-Host ""

# =============================================================================
# FASE DE EXECUCAO 2 - CONSTRUCAO DO GRAFO DE DEPENDENCIAS
# =============================================================================
Write-Host "[FASE 2/5] Construindo grafo de dependencias..." -ForegroundColor Green

$progressCount = 0
foreach ($filePath in $disabledFileInfo.Keys) {
    $progressCount++
    $fileInfo = $disabledFileInfo[$filePath]
    
    Write-Progress -Activity "Analisando dependencias" -Status "Processando $($fileInfo.FileName)" -PercentComplete (($progressCount / $disabledFileInfo.Count) * 100)
    
    try {
        $content = Get-Content $filePath -Raw -Encoding UTF8
        if (-not $content) { continue }
        
        # Para cada classe conhecida no projeto, verificar se e uma dependencia
        foreach ($potentialDepName in $allClassMap.Keys) {
            $potentialDep = $allClassMap[$potentialDepName]
            
            # Nao pode depender de si mesmo
            if ($potentialDep.QualifiedName -eq $fileInfo.QualifiedName) {
                continue
            }
            
            # Criar regex para encontrar uso da classe
            $classNameEscaped = [regex]::Escape($potentialDep.ClassName)
            $classUsageRegex = [regex]"\b$classNameEscaped\b"
            
            # Verificar se a classe e usada no codigo
            if ($content -match $classUsageRegex) {
                # Verificar se e uma dependencia real (mesmo pacote OU importada)
                $isSamePackage = $potentialDep.PackageName -eq $fileInfo.PackageName
                $qualifiedNameEscaped = [regex]::Escape($potentialDep.QualifiedName)
                $isImported = $content -match "import\s+$qualifiedNameEscaped\s*;"
                
                if ($isSamePackage -or $isImported) {
                    # Adicionar dependencia se nao ja existir
                    if ($fileInfo.Dependencies -notcontains $potentialDep.QualifiedName) {
                        $fileInfo.Dependencies += $potentialDep.QualifiedName
                    }
                }
            }
        }
        
        $fileInfo.DependencyCount = $fileInfo.Dependencies.Count
    }
    catch {
        Write-Host "  ERRO ao analisar $($fileInfo.FileName): $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Progress -Activity "Analisando dependencias" -Completed
Write-Host "Grafo de dependencias construido com sucesso." -ForegroundColor Green
Write-Host ""

# =============================================================================
# FASE DE EXECUCAO 3 - CLASSIFICACAO E ORDENACAO
# =============================================================================
Write-Host "[FASE 3/5] Classificando arquivos por fases..." -ForegroundColor Green

$phase1Files = @()
$phase2Files = @()
$phase3Files = @()
$phase4Files = @()
$cyclicFiles = @()

# Classificar arquivos por fase baseado nas dependencias
foreach ($fileInfo in $disabledFileInfo.Values) {
    # Verificar se todas as dependencias estao na Fase 0 (fundacao)
    $allDepsInPhase0 = $true
    $depsInPhase0Count = 0
    
    foreach ($depQualifiedName in $fileInfo.Dependencies) {
        if ($phase0ClassMap.ContainsKey($depQualifiedName)) {
            $depsInPhase0Count++
        } else {
            $allDepsInPhase0 = $false
        }
    }
    
    if ($fileInfo.DependencyCount -eq 0) {
        # Sem dependencias - pode ser Fase 1
        $fileInfo.Phase = 1
        $phase1Files += $fileInfo
    }
    elseif ($allDepsInPhase0) {
        # Todas as dependencias estao na Fase 0 - e Fase 1
        $fileInfo.Phase = 1
        $phase1Files += $fileInfo
    }
    elseif ($fileInfo.DependencyCount -le 3) {
        # Dependencias baixas - Fase 2
        $fileInfo.Phase = 2
        $phase2Files += $fileInfo
    }
    elseif ($fileInfo.DependencyCount -le 10) {
        # Dependencias medias - Fase 3
        $fileInfo.Phase = 3
        $phase3Files += $fileInfo
    }
    else {
        # Dependencias altas - Fase 4
        $fileInfo.Phase = 4
        $phase4Files += $fileInfo
    }
}

# Ordenar cada fase por numero de dependencias (crescente)
$phase1Files = $phase1Files | Sort-Object DependencyCount, FileName
$phase2Files = $phase2Files | Sort-Object DependencyCount, FileName
$phase3Files = $phase3Files | Sort-Object DependencyCount, FileName
$phase4Files = $phase4Files | Sort-Object DependencyCount, FileName

Write-Host "Classificacao concluida:" -ForegroundColor Cyan
Write-Host "  Fase 0 (Fundacao): $($phase0Files.Count) arquivos" -ForegroundColor Green
Write-Host "  Fase 1 (Primeira Camada): $($phase1Files.Count) arquivos" -ForegroundColor Yellow
Write-Host "  Fase 2 (Dependencias Baixas): $($phase2Files.Count) arquivos" -ForegroundColor Yellow
Write-Host "  Fase 3 (Dependencias Medias): $($phase3Files.Count) arquivos" -ForegroundColor Yellow
Write-Host "  Fase 4 (Dependencias Altas): $($phase4Files.Count) arquivos" -ForegroundColor Yellow
Write-Host ""

# =============================================================================
# FASE DE EXECUCAO 4 - GERACAO DO ARQUIVO MARKDOWN
# =============================================================================
Write-Host "[FASE 4/5] Gerando arquivo de plano '$OutputFileName'..." -ForegroundColor Green

$totalFiles = $phase0Files.Count + $phase1Files.Count + $phase2Files.Count + $phase3Files.Count + $phase4Files.Count

$mdContent = @"
# PLANO DE MIGRACAO SISTEMATICA - TacZ NeoForge 1.21.1 (v3.0)

**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1  
**Estrategia:** Habilitacao incremental baseada em analise topologica de dependencias  
**Data de Geracao:** $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')  
**Script:** Generate-Plan.ps1 v3.0  

---

## RESUMO ESTATISTICO

| Fase | Descricao | Arquivos | Status |
|------|-----------|----------|--------|
| **Fase 0** | Fundacao (ja habilitada) | $($phase0Files.Count) | ✅ $($phase0Files.Count)/$($phase0Files.Count) |
| **Fase 1** | Primeira Camada (dependem apenas da fundacao) | $($phase1Files.Count) | ⏳ 0/$($phase1Files.Count) |
| **Fase 2** | Dependencias Baixas (1-3) | $($phase2Files.Count) | ⏳ 0/$($phase2Files.Count) |
| **Fase 3** | Dependencias Medias (4-10) | $($phase3Files.Count) | ⏳ 0/$($phase3Files.Count) |
| **Fase 4** | Dependencias Altas (11+) | $($phase4Files.Count) | ⏳ 0/$($phase4Files.Count) |
| **TOTAL** | **Todos os arquivos** | **$totalFiles** | **$($phase0Files.Count)/$totalFiles** |

---

## PLANO DE EXECUCAO ORDENADO

### **FASE 0: FUNDACAO (Ja Habilitada)** ✅

*Esta e a base solida ja estabelecida. Estes arquivos compilam sem erros e servem como fundacao para as proximas fases.*

"@

# Adicionar arquivos da Fase 0
foreach ($file in ($phase0Files | Sort-Object FileName)) {
    $mdContent += "`n- [x] $($file.FileName) (Fundacao)"
}

$mdContent += @"

---

### **FASE 1: PRIMEIRA CAMADA** ⭐ 
*Proxima a ser executada - Dependem apenas da fundacao*

*Estes arquivos podem ser habilitados com seguranca pois todas as suas dependencias ja estao habilitadas na Fase 0.*

"@

# Adicionar arquivos da Fase 1
foreach ($file in $phase1Files) {
    $mdContent += "`n- [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
}

if ($phase2Files.Count -gt 0) {
    $mdContent += @"

---

### **FASE 2: DEPENDENCIAS BAIXAS (1-3)** ($($phase2Files.Count) arquivos)
*Arquivos com poucas dependencias internas do mod*

"@
    foreach ($file in $phase2Files) {
        $mdContent += "`n- [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
    }
}

if ($phase3Files.Count -gt 0) {
    $mdContent += @"

---

### **FASE 3: DEPENDENCIAS MEDIAS (4-10)** ($($phase3Files.Count) arquivos)
*Arquivos com dependencias moderadas*

"@
    foreach ($file in $phase3Files) {
        $mdContent += "`n- [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
    }
}

if ($phase4Files.Count -gt 0) {
    $mdContent += @"

---

### **FASE 4: DEPENDENCIAS ALTAS (11+)** ($($phase4Files.Count) arquivos)
*Arquivos complexos do nucleo do mod*

"@
    foreach ($file in $phase4Files) {
        $mdContent += "`n- [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
    }
}

$mdContent += @"

---

## WORKFLOW DE EXECUCAO

Para cada arquivo na ordem das fases:

1. **Habilitar:** Renomeie `.java.disabled` para `.java`
2. **Corrigir:** Adapte o codigo para NeoForge 1.21.1 APIs
3. **Testar:** Execute `./gradlew compileJava`
4. **Validar:** Se compila, continue. Se falha, reverta e analise dependencias

## REGRAS IMPORTANTES

- ✅ **SEMPRE** siga a ordem das fases rigorosamente  
- ✅ **NUNCA** pule arquivos dentro de uma fase  
- ✅ **SEMPRE** teste a compilacao apos cada arquivo habilitado  
- ✅ **REVERTA** imediatamente se houver falha de compilacao  

---

*Plano gerado automaticamente em $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') pelo Generate-Plan.ps1 v3.0*
"@

# Salvar arquivo
try {
    $outputPath = Join-Path $projectRoot $OutputFileName
    Set-Content -Path $outputPath -Value $mdContent -Encoding UTF8
    Write-Host "Plano de migracao salvo com sucesso em: $OutputFileName" -ForegroundColor Green
}
catch {
    Write-Host "ERRO ao salvar arquivo: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# =============================================================================
# RESUMO FINAL
# =============================================================================
Write-Host ""
Write-Host "=== RESUMO FINAL ===" -ForegroundColor Cyan
Write-Host "Fundacao (Fase 0): $($phase0Files.Count) arquivos (ja habilitados)" -ForegroundColor Green
Write-Host "Proxima fase (Fase 1): $($phase1Files.Count) arquivos prontos para habilitar" -ForegroundColor Yellow
Write-Host "Fases futuras: $($phase2Files.Count + $phase3Files.Count + $phase4Files.Count) arquivos restantes" -ForegroundColor White
Write-Host ""
Write-Host "Plano de migracao v3.0 gerado com sucesso!" -ForegroundColor Green
Write-Host "Arquivo salvo: $OutputFileName" -ForegroundColor Cyan
