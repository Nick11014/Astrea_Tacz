# =============================================================================
# DEPENDENCY-BASED PLAN GENERATOR - TacZ NeoForge Migration
# Autor: Gemini AI
# Descrição:
# Este script analisa as dependências entre os arquivos .java.disabled,
# realiza uma ordenação topológica para determinar a ordem de habilitação correta,
# e gera um arquivo de plano de desenvolvimento em Markdown (.md).
# =============================================================================

param(
    [string]$OutputFileName = "PLANO_ORDENADO.md"
)

# --- 1. CONFIGURAÇÃO E INICIALIZAÇÃO ---
$ProjectRoot = $PSScriptRoot
$SrcPath = Join-Path $ProjectRoot "src\main\java"

Write-Host "Iniciando análise topológica de dependências..." -ForegroundColor Green
Write-Host "Pasta fonte: $SrcPath" -ForegroundColor Cyan

# Encontrar todos os arquivos .java.disabled
$AllDisabledFiles = Get-ChildItem -Path $SrcPath -Recurse -Filter "*.java.disabled"
Write-Host "Encontrados $($AllDisabledFiles.Count) arquivos .disabled para analisar." -ForegroundColor Yellow

# --- 2. FASE 1: MAPEAMENTO DE CLASSES E DEPENDÊNCIAS ---
Write-Host "`n[FASE 1/3] Mapeando classes e construindo grafo de dependências..."

# $fileInfo: Armazena informações sobre cada arquivo (nó do grafo)
#   Key: Caminho do arquivo
#   Value: PSCustomObject com ClassName, PackageName, etc.
$fileInfo = @{}

# $dependencies: Grafo de adjacência. Armazena as dependências de cada arquivo.
#   Key: Caminho do arquivo
#   Value: Lista de caminhos de arquivos dos quais ele depende
$dependencies = @{}

# $inDegree: Conta quantas dependências cada arquivo tem (essencial para a ordenação)
#   Key: Caminho do arquivo
#   Value: Inteiro (contagem de dependências)
$inDegree = @{}

# Regex para extrair pacote e nome da classe principal
$packageRegex = [regex]::new("package\s+([a-zA-Z0-9_.]+);")
$classRegex = [regex]::new("(?:public|private|protected)?\s*(?:final|abstract)?\s*(class|interface|enum)\s+([a-zA-Z0-9_]+)")

# Primeiro passo: popular o $fileInfo com o nome qualificado de cada classe
foreach ($file in $AllDisabledFiles) {
    try {
        $content = Get-Content $file.FullName -Raw -ErrorAction Stop
        if (-not $content) {
            Write-Host "AVISO: Arquivo vazio ou não legível: $($file.Name)" -ForegroundColor Yellow
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
            Write-Host "AVISO: Não foi possível extrair pacote/classe de $($file.Name)" -ForegroundColor Magenta
        }
    } catch {
        Write-Host "ERRO ao ler arquivo $($file.Name): $($_.Exception.Message)" -ForegroundColor Red
    }
}

# Criar um mapa reverso de NomeQualificado -> Caminho para busca rápida
$classToPathMap = @{}
foreach ($key in $fileInfo.Keys) {
    $classToPathMap[$fileInfo[$key].QualifiedName] = $fileInfo[$key].Path
}

# Segundo passo: Analisar imports e construir o grafo
foreach ($file in $AllDisabledFiles) {
    $filePath = $file.FullName
    
    # Só processar se o arquivo foi mapeado corretamente
    if (-not $fileInfo.ContainsKey($filePath)) {
        continue
    }
    
    $dependencies[$filePath] = @()
    $inDegree[$filePath] = 0
    
    $content = Get-Content $filePath -Raw -ErrorAction SilentlyContinue
    if (-not $content) {
        Write-Host "AVISO: Não foi possível ler o arquivo $($file.Name)" -ForegroundColor Yellow
        continue
    }
    
    # Regex para encontrar apenas os imports do próprio mod
    $importMatches = $content | Select-String -Pattern "import\s+com\.tacz\.guns\.([a-zA-Z0-9_.]+);" -AllMatches
    
    foreach ($match in $importMatches.Matches) {
        $importedClass = "com.tacz.guns." + $match.Groups[1].Value
        
        # Se a classe importada é uma das classes do nosso mod
        if ($classToPathMap.ContainsKey($importedClass)) {
            $dependencyPath = $classToPathMap[$importedClass]
            
            # Adiciona a dependência se ainda não existir e não for uma auto-dependência
            if ($dependencyPath -ne $filePath -and $dependencies[$filePath] -notcontains $dependencyPath) {
                $dependencies[$filePath] += $dependencyPath
            }
        }
    }
    
    # Atualizar a contagem de dependências
    $inDegree[$filePath] = $dependencies[$filePath].Count
    $fileInfo[$filePath].DependencyCount = $inDegree[$filePath]
}

# --- 3. FASE 2: ORDENAÇÃO TOPOLÓGICA (ALGORITMO DE KAHN) ---
Write-Host "`n[FASE 2/3] Executando ordenação topológica..."

# Filtrar apenas arquivos com informações válidas
$validFiles = $fileInfo.Keys | Where-Object { $fileInfo[$_] -ne $null }
Write-Host "Arquivos válidos para ordenação: $($validFiles.Count)" -ForegroundColor Cyan

# Fila para nós com in-degree zero (sem dependências)
$queue = [System.Collections.Queue]::new()
foreach ($filePath in $validFiles) {
    if ($inDegree[$filePath] -eq 0) {
        $queue.Enqueue($filePath)
    }
}

Write-Host "Arquivos sem dependências (ponto de partida): $($queue.Count)" -ForegroundColor Cyan

# $sortedFiles: A lista final com a ordem de habilitação correta
$sortedFiles = @()

# $reverseDependencies: Mapa para encontrar rapidamente quem depende de um arquivo
$reverseDependencies = @{}
foreach ($filePath in $validFiles) {
    foreach ($depPath in $dependencies[$filePath]) {
        if (-not $reverseDependencies.ContainsKey($depPath)) {
            $reverseDependencies[$depPath] = @()
        }
        $reverseDependencies[$depPath] += $filePath
    }
}

while ($queue.Count -gt 0) {
    $currentPath = $queue.Dequeue()
    $sortedFiles += $fileInfo[$currentPath]
    
    # Para cada arquivo que DEPENDE do arquivo atual...
    if ($reverseDependencies.ContainsKey($currentPath)) {
        foreach ($dependentPath in $reverseDependencies[$currentPath]) {
            # ...diminuir sua contagem de dependências
            $inDegree[$dependentPath]--
            # Se a contagem chegar a zero, ele é o próximo da fila
            if ($inDegree[$dependentPath] -eq 0) {
                $queue.Enqueue($dependentPath)
            }
        }
    }
}

# Verificação de ciclo (se a ordenação não incluiu todos, há uma dependência circular)
if ($sortedFiles.Count -ne $validFiles.Count) {
    Write-Host "`nAVISO: Possível ciclo de dependência detectado!" -ForegroundColor Yellow
    Write-Host "Arquivos válidos: $($validFiles.Count), Arquivos ordenados: $($sortedFiles.Count)" -ForegroundColor Yellow
    Write-Host "Continuando com os arquivos que puderam ser ordenados..." -ForegroundColor Yellow
}

Write-Host "Ordenação concluída. $($sortedFiles.Count) arquivos ordenados com sucesso." -ForegroundColor Green

# --- 4. FASE 3: GERAÇÃO DO ARQUIVO MARKDOWN ---
Write-Host "`n[FASE 3/3] Gerando arquivo de plano '$OutputFileName'..."

# Classificar arquivos em fases baseadas na contagem de dependências original para organização
$phase1 = $sortedFiles | Where-Object { $_.DependencyCount -eq 0 }
$phase2 = $sortedFiles | Where-Object { $_.DependencyCount -ge 1 -and $_.DependencyCount -le 3 }
$phase3 = $sortedFiles | Where-Object { $_.DependencyCount -ge 4 -and $_.DependencyCount -le 10 }
$phase4 = $sortedFiles | Where-Object { $_.DependencyCount -ge 11 }

# Construir o conteúdo do arquivo Markdown
$mdLines = @()
$mdLines += "# PLANO DE DESENVOLVIMENTO SISTEMATICO - TacZ NeoForge 1.21.1 (Gerado Automaticamente)"
$mdLines += ""
$mdLines += "**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1"
$mdLines += "**Estrategia:** Habilitacao incremental baseada em ordenacao topologica de dependencias."
$mdLines += "**Data de Geracao:** $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
$mdLines += ""
$mdLines += "---"
$mdLines += ""
$mdLines += "## RESUMO ESTATISTICO"
$mdLines += ""
$mdLines += "| Fase | Descricao | Arquivos | Status |"
$mdLines += "|------|-----------|----------|--------|"
$mdLines += "| **Fase 1** | Sem dependencias internas | $($phase1.Count) | [ ] 0/$($phase1.Count) |"
$mdLines += "| **Fase 2** | Dependencias baixas (1-3) | $($phase2.Count) | [ ] 0/$($phase2.Count) |"
$mdLines += "| **Fase 3** | Dependencias medias (4-10) | $($phase3.Count) | [ ] 0/$($phase3.Count) |"
$mdLines += "| **Fase 4** | Dependencias altas (11+) | $($phase4.Count) | [ ] 0/$($phase4.Count) |"
$mdLines += "| **TOTAL** | **Todos os arquivos** | **$($sortedFiles.Count)** | **0/$($sortedFiles.Count)** |"
$mdLines += ""
$mdLines += "---"
$mdLines += ""
$mdLines += "## CHECKLIST DE HABILITACAO ORDENADO"
$mdLines += ""
$mdLines += "Esta lista foi gerada para garantir que, ao habilitar um arquivo, todas as suas dependencias internas do mod ja tenham sido habilitadas. Siga a ordem rigorosamente."
$mdLines += ""
$mdLines += "### **FASE 1: FUNDACAO (Sem Dependencias Internas)** ($($phase1.Count) arquivos)"
$mdLines += "*Estes arquivos nao possuem imports de outras classes do mod. Eles sao a base e podem ser habilitados primeiro.*"
$mdLines += ""

foreach ($file in $phase1) {
    $mdLines += "* [ ] $($file.FileName)"
}

$mdLines += ""
$mdLines += "---"
$mdLines += "### **FASE 2: DEPENDENCIAS BAIXAS** ($($phase2.Count) arquivos)"
$mdLines += "*Dependem de 1 a 3 arquivos, que ja devem estar habilitados na Fase 1.*"
$mdLines += ""

foreach ($file in $phase2) {
    $mdLines += "* [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
}

$mdLines += ""
$mdLines += "---"
$mdLines += "### **FASE 3: DEPENDENCIAS MEDIAS** ($($phase3.Count) arquivos)"
$mdLines += "*Dependem de 4 a 10 arquivos. A complexidade de integracao aumenta aqui.*"
$mdLines += ""

foreach ($file in $phase3) {
    $mdLines += "* [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
}

$mdLines += ""
$mdLines += "---"
$mdLines += "### **FASE 4: DEPENDENCIAS ALTAS (NUCLEO DO MOD)** ($($phase4.Count) arquivos)"
$mdLines += "*Arquivos mais complexos, com 11 ou mais dependencias. Representam a logica central do mod.*"
$mdLines += ""

foreach ($file in $phase4) {
    $mdLines += "* [ ] $($file.FileName) (Deps: $($file.DependencyCount))"
}

$mdLines += ""
$mdLines += "---"
$mdLines += ""
$mdLines += "## WORKFLOW"
$mdLines += ""
$mdLines += "Siga o workflow definido no plano original para cada arquivo:"
$mdLines += "1. **Backup:** git commit -m 'Backup antes de habilitar [arquivo]'"
$mdLines += "2. **Habilitacao:** Renomeie .java.disabled para .java"
$mdLines += "3. **Correcao:** Adapte o codigo para NeoForge 1.21.1"
$mdLines += "4. **Build:** ./gradlew build"
$mdLines += "5. **Validacao ou Rollback:** Se sucesso, commit. Se falha, reverta o arquivo."
$mdLines += ""
$mdLines += "---"
$mdLines += ""
$mdLines += "## DETALHES DA ORDENACAO TOPOLOGICA"
$mdLines += ""
$mdLines += "### Estatisticas Gerais:"
$mdLines += "- **Total de arquivos analisados:** $($AllDisabledFiles.Count)"
$mdLines += "- **Arquivos com informacoes validas:** $($fileInfo.Count)"
$mdLines += "- **Arquivos ordenados com sucesso:** $($sortedFiles.Count)"
$mdLines += ""

# Adicionar informações sobre possíveis ciclos se houver
if ($sortedFiles.Count -lt $AllDisabledFiles.Count) {
    $mdLines += "### ATENCAO: Possiveis Dependencias Circulares"
    $mdLines += ""
    $mdLines += "Alguns arquivos nao puderam ser ordenados, possivelmente devido a dependencias circulares:"
    $unorderedFiles = $AllDisabledFiles | Where-Object { $_.FullName -notin ($sortedFiles | ForEach-Object { $_.Path }) }
    foreach ($file in $unorderedFiles) {
        $mdLines += "- $($file.Name)"
    }
    $mdLines += ""
}

# Adicionar uma seção com a ordem topológica recomendada
if ($sortedFiles.Count -gt 0) {
    $mdLines += ""
    $mdLines += "## ORDEM TOPOLOGICA RECOMENDADA"
    $mdLines += ""
    $mdLines += "Os arquivos abaixo estao ordenados de acordo com suas dependencias internas."
    $mdLines += "Habilite na ordem apresentada para minimizar erros de compilacao:"
    $mdLines += ""
    
    for ($i = 0; $i -lt $sortedFiles.Count; $i++) {
        $file = $sortedFiles[$i]
        $orderNum = $i + 1
        $mdLines += "$orderNum. $($file.FileName) (Deps: $($file.DependencyCount))"
    }
    $mdLines += ""
}

# Juntar todas as linhas em uma string
$mdContent = $mdLines -join "`n"

# Salvar o arquivo
try {
    Set-Content -Path (Join-Path $ProjectRoot $OutputFileName) -Value $mdContent -Encoding UTF8
    Write-Host "`nPlano de desenvolvimento salvo em: '$OutputFileName'" -ForegroundColor Green
} catch {
    Write-Host "`nERRO ao salvar arquivo: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "Script concluído!" -ForegroundColor Cyan