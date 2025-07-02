# =============================================================================
# DEPENDENCY-BASED PLAN GENERATOR - TacZ NeoForge Migration (Versao 2.1)
# Autor: Gemini AI
# Descricao:
# Script sem caracteres especiais para garantir compatibilidade com o terminal.
# Analisa as dependencias, realiza ordenacao topologica e gera o plano de migracao.
# =============================================================================

param(
    [string]$OutputFileName = "PLANO_ORDENADO_V2.md"
)

# --- 1. CONFIGURACAO E INICIALIZACAO ---
$ProjectRoot = $PSScriptRoot
if (-not (Test-Path (Join-Path $ProjectRoot "src"))) {
    $ProjectRoot = (Get-Item $ProjectRoot).Directory.Parent.FullName
}
$SrcPath = Join-Path $ProjectRoot "src\main\java"

Write-Host "Iniciando analise topologica de dependencias (v2.1)..." -ForegroundColor Green
Write-Host "Pasta fonte: $SrcPath" -ForegroundColor Cyan

# Encontrar todos os arquivos .java.disabled para analisar
$AllDisabledFiles = Get-ChildItem -Path $SrcPath -Recurse -Filter "*.java.disabled"
Write-Host "Encontrados $($AllDisabledFiles.Count) arquivos .disabled para analisar." -ForegroundColor Yellow

# --- 2. FASE 1: MAPEAMENTO DE CLASSES E DEPENDENCIAS ---
Write-Host "`n[FASE 1/3] Mapeando classes e construindo grafo de dependencias..."

$fileInfo = @{}
$dependencies = @{}
$inDegree = @{}

$packageRegex = [regex]::new("package\s+([a-zA-Z0-9_.]+);")
$classRegex = [regex]::new("(?:public|private|protected)?\s*(?:final|abstract)?\s*(class|interface|enum)\s+([a-zA-Z0-9_]+)")

foreach ($file in $AllDisabledFiles) {
    try {
        $content = Get-Content $file.FullName -Raw -ErrorAction Stop
        if (-not $content) {
            Write-Host "AVISO: Arquivo vazio ou nao legivel: $($file.Name)" -ForegroundColor Yellow
            continue
        }
        
        $packageName = ($packageRegex.Match($content).Groups[1].Value).Trim()
        $className = ($classRegex.Match($content).Groups[2].Value).Trim()

        if ($packageName -and $className) {
            $qualifiedName = "$packageName.$className"
            $fileInfo[$file.FullName] = [PSCustomObject]@{
                Path = $file.FullName
                FileName = $file.Name -replace '\.disabled$', ''
                ClassName = $className
                PackageName = $packageName
                QualifiedName = $qualifiedName
                DependencyCount = 0
            }
        } else {
            Write-Host "AVISO: Nao foi possivel extrair pacote/classe de $($file.Name)" -ForegroundColor Magenta
        }
    } catch {
        Write-Host "ERRO ao ler arquivo $($file.Name): $($_.Exception.Message)" -ForegroundColor Red
    }
}

$classToPathMap = @{}
$allClasses = $fileInfo.Values
foreach ($info in $allClasses) {
    $classToPathMap[$info.QualifiedName] = $info.Path
}

Write-Host "Analisando conteudo dos arquivos para detectar todas as dependencias..."
$progressCount = 0
foreach ($file in $AllDisabledFiles) {
    $progressCount++
    Write-Progress -Activity "Analisando dependencias" -Status "Processando $($file.Name)" -PercentComplete (($progressCount / $AllDisabledFiles.Count) * 100)

    $filePath = $file.FullName
    if (-not $fileInfo.ContainsKey($filePath)) { continue }

    $dependencies[$filePath] = @()
    $inDegree[$filePath] = 0
    
    $content = Get-Content $filePath -Raw -ErrorAction SilentlyContinue
    if (-not $content) { continue }

    $currentFileInfo = $fileInfo[$filePath]

    foreach ($potentialDepInfo in $allClasses) {
        if ($potentialDepInfo.QualifiedName -eq $currentFileInfo.QualifiedName) {
            continue
        }
        
        $dependencyRegex = "\b" + [regex]::Escape($potentialDepInfo.ClassName) + "\b"

        if ($content -match $dependencyRegex) {
            $isSamePackage = $potentialDepInfo.PackageName -eq $currentFileInfo.PackageName
            $isImported = $content -match "import\s+$(($potentialDepInfo.QualifiedName -replace '\.', '\\.'))(?:;|\.\*)"

            if ($isSamePackage -or $isImported) {
                 $dependencyPath = $classToPathMap[$potentialDepInfo.QualifiedName]
                 if ($dependencies[$filePath] -notcontains $dependencyPath) {
                    $dependencies[$filePath] += $dependencyPath
                }
            }
        }
    }
    
    $inDegree[$filePath] = $dependencies[$filePath].Count
    $fileInfo[$filePath].DependencyCount = $inDegree[$filePath]
}

# --- 3. FASE 2: ORDENACAO TOPOLOGICA (ALGORITMO DE KAHN) ---
Write-Host "`n[FASE 2/3] Executando ordenacao topologica..."

$validFiles = $fileInfo.Keys | Where-Object { $fileInfo[$_] -ne $null }
Write-Host "Arquivos validos para ordenacao: $($validFiles.Count)" -ForegroundColor Cyan

$queue = [System.Collections.Queue]::new()
Get-ChildItem -Path $SrcPath -Recurse -Filter "*.java" | ForEach-Object {
    if ($fileInfo.ContainsKey($_.FullName + ".disabled")) {
        $queue.Enqueue($_.FullName + ".disabled")
    }
}
foreach ($filePath in $validFiles) {
    if ($inDegree.ContainsKey($filePath) -and $inDegree[$filePath] -eq 0) {
        if(-not $queue.Contains($filePath)){
            $queue.Enqueue($filePath)
        }
    }
}

Write-Host "Arquivos sem dependencias (ponto de partida): $($queue.Count)" -ForegroundColor Cyan

$sortedFiles = @()
$reverseDependencies = @{}
foreach ($filePath in $validFiles) {
    if($dependencies[$filePath]){
        foreach ($depPath in $dependencies[$filePath]) {
            if (-not $reverseDependencies.ContainsKey($depPath)) {
                $reverseDependencies[$depPath] = @()
            }
            $reverseDependencies[$depPath] += $filePath
        }
    }
}

$processedInSort = @{}
while ($queue.Count -gt 0) {
    $currentPath = $queue.Dequeue()
    if($processedInSort.ContainsKey($currentPath)){ continue }

    $sortedFiles += $fileInfo[$currentPath]
    $processedInSort[$currentPath] = $true
    
    if ($reverseDependencies.ContainsKey($currentPath)) {
        foreach ($dependentPath in $reverseDependencies[$currentPath]) {
            if($inDegree.ContainsKey($dependentPath)){
                $inDegree[$dependentPath]--
                if ($inDegree[$dependentPath] -eq 0) {
                    $queue.Enqueue($dependentPath)
                }
            }
        }
    }
}

if ($sortedFiles.Count -ne $validFiles.Count) {
    Write-Host "`nAVISO: Possivel ciclo de dependencia detectado!" -ForegroundColor Yellow
    Write-Host "Arquivos validos: $($validFiles.Count), Arquivos ordenados: $($sortedFiles.Count)" -ForegroundColor Yellow
    $unorderedCount = $validFiles.Count - $sortedFiles.Count
    Write-Host "$unorderedCount arquivos nao puderam ser ordenados e estarao em uma secao separada." -ForegroundColor Yellow
}

Write-Host "Ordenacao concluida. $($sortedFiles.Count) arquivos ordenados com sucesso." -ForegroundColor Green

# --- 4. FASE 3: GERACAO DO ARQUIVO MARKDOWN ---
Write-Host "`n[FASE 3/3] Gerando arquivo de plano '$OutputFileName'..."

$phase1 = $sortedFiles | Where-Object { $_.DependencyCount -eq 0 }
$phase2 = $sortedFiles | Where-Object { $_.DependencyCount -ge 1 -and $_.DependencyCount -le 3 }
$phase3 = $sortedFiles | Where-Object { $_.DependencyCount -ge 4 -and $_.DependencyCount -le 10 }
$phase4 = $sortedFiles | Where-Object { $_.DependencyCount -ge 11 }

$mdLines = @(
    "# PLANO DE DESENVOLVIMENTO SISTEMATICO - TacZ NeoForge 1.21.1 (Gerado Automaticamente v2.1)",
    "",
    "**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1",
    "**Estrategia:** Habilitacao incremental baseada em ordenacao topologica de dependencias.",
    "**Data de Geracao:** $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')",
    "", "---", "",
    "## RESUMO ESTATISTICO", "",
    "| Fase | Descricao | Arquivos | Status |",
    "|------|-----------|----------|--------|",
    "| **Fase 1** | Sem dependencias internas | $($phase1.Count) | [OK] $($phase1.Count)/$($phase1.Count) |",
    "| **Fase 2** | Dependencias baixas (1-3) | $($phase2.Count) | [ ] 0/$($phase2.Count) |",
    "| **Fase 3** | Dependencias medias (4-10) | $($phase3.Count) | [ ] 0/$($phase3.Count) |",
    "| **Fase 4** | Dependencias altas (11+) | $($phase4.Count) | [ ] 0/$($phase4.Count) |",
    "| **TOTAL** | **Todos os arquivos** | **$($validFiles.Count)** | **$($phase1.Count)/$($validFiles.Count)** |",
    "", "---", "",
    "## CHECKLIST DE HABILITACAO ORDENADO", "",
    "Esta lista foi gerada para garantir que, ao habilitar um arquivo, todas as suas dependencias internas do mod ja tenham sido habilitadas. Siga a ordem rigorosamente.", ""
)

if ($phase2.Count -gt 0) {
    $mdLines += "### **FASE 2: DEPENDENCIAS BAIXAS (1-3)** ($($phase2.Count) arquivos)", "*Dependem de 1 a 3 arquivos, que ja devem estar habilitados na Fase 1.*", ""
    foreach ($file in $phase2) { $mdLines += "* [ ] $($file.FileName) (Deps: $($file.DependencyCount))" }
    $mdLines += "", "---"
}
if ($phase3.Count -gt 0) {
    $mdLines += "### **FASE 3: DEPENDENCIAS MEDIAS (4-10)** ($($phase3.Count) arquivos)", "*Dependem de 4 a 10 arquivos. A complexidade de integracao aumenta aqui.*", ""
    foreach ($file in $phase3) { $mdLines += "* [ ] $($file.FileName) (Deps: $($file.DependencyCount))" }
    $mdLines += "", "---"
}
if ($phase4.Count -gt 0) {
    $mdLines += "### **FASE 4: DEPENDENCIAS ALTAS (NUCLEO DO MOD)** ($($phase4.Count) arquivos)", "*Arquivos mais complexos, com 11 ou mais dependencias. Representam a logica central do mod.*", ""
    foreach ($file in $phase4) { $mdLines += "* [ ] $($file.FileName) (Deps: $($file.DependencyCount))" }
    $mdLines += "", "---"
}

$mdLines += @(
    "", "## WORKFLOW", "",
    "Siga o workflow definido no plano original para cada arquivo:",
    "1. **Habilitacao:** Renomeie .java.disabled para .java",
    "2. **Correcao:** Adapte o codigo para NeoForge 1.21.1",
    "3. **Build:** ./gradlew build",
    "4. **Validacao ou Rollback:** Se sucesso, commit. Se falha, reverta o arquivo.",
    "", "---", ""
)

if ($sortedFiles.Count -ne $validFiles.Count) {
    $mdLines += "### ATENCAO: POSSIVEIS DEPENDENCIAS CIRCULARES", ""
    $mdLines += "Os seguintes arquivos nao puderam ser ordenados, possivelmente devido a dependencias circulares. Eles precisam ser analisados e habilitados em conjunto.", ""
    $unorderedFiles = $validFiles | Where-Object { $_ -notin ($sortedFiles | ForEach-Object { $_.Path }) }
    foreach ($path in $unorderedFiles) {
        $mdLines += "- $($fileInfo[$path].FileName)"
    }
    $mdLines += "", "---"
}

$mdContent = $mdLines -join "`r`n"

try {
    Set-Content -Path (Join-Path $ProjectRoot $OutputFileName) -Value $mdContent -Encoding UTF8
    Write-Host "`nPlano de desenvolvimento salvo em: '$OutputFileName'" -ForegroundColor Green
} catch {
    Write-Host "`nERRO ao salvar arquivo: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "Script concluido!" -ForegroundColor Cyan