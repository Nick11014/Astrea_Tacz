# =============================================================================
# Process-Next-Phase.ps1 - Processador e Planejador de Fase Automatico (v1.4)
# =============================================================================
# v1.4: CORRIGIDO - Build não quebra mais (reversa arquivos problemáticos)
#       CORRIGIDO - Melhor categorização para gerar mais subfases
#       MELHORADO - Detecção de arquivos já concluídos
# v1.3: CORRIGIDO - Agora gera todas as subfases (nao apenas API_Desconhecida)
#       CORRIGIDO - Arquivos sem erros permanecem habilitados
#       MELHORADO - Melhor categorizacao de erros e logs detalhados
# v1.2: Corrigido erro de parsing de variavel e removida logica de limpeza de
#       emojis, que agora e desnecessaria.
# =============================================================================

param(
    [int]$PhaseToProcess = 2
)

Write-Host "=== PROCESSADOR E PLANEJADOR AUTOMATICO (FASE $PhaseToProcess) ===" -ForegroundColor Cyan

# --- Configuracao e Verificacao do Ambiente ---
$baseDir = "src\main\java\com\tacz\guns"
$planFile = "PLANO_MIGRACAO.md"
$buildFile = "build.gradle"
$errorLogPath = "build_errors_phase_${PhaseToProcess}.txt"

if (-not (Test-Path $planFile)) { Write-Host "ERRO: Plano '$planFile' nao encontrado." -ForegroundColor Red; exit 1 }
if (-not (Test-Path $baseDir)) { Write-Host "ERRO: Diretorio '$baseDir' nao encontrado." -ForegroundColor Red; exit 1 }
if (-not (Test-Path $buildFile)) { Write-Host "ERRO: build.gradle nao encontrado." -ForegroundColor Red; exit 1 }

# --- Palavras-chave para categorizacao automatica de erros ---
$errorKeywordsToSubPhase = @{
    "1-Configuracao" = @("ModConfig", "IConfigEvent", "ModConfigSpec", "config", "Config")
    "2-Rede" = @("NetworkEvent", "IPayloadContext", "CustomPacketPayload", "StreamCodec", "network", "packet", "Network")
    "3-DadosDeItens" = @("getTag", "getOrCreateTag", "isSameItemSameTags", "DataComponent", "DataComponentType", "ItemStack", "nbt", "NBT")
    "4-Renderizacao" = @("renderBackground", "Tesselator", "BufferBuilder", "GuiGraphics", "RenderSystem", "RenderType", "render", "Render", "animation", "Animation")
    "5-API_Geral" = @("ResourceLocation", "Ingredient", "ServerPlayer", "CraftingHelper")
    "6-Imports_Dependencias" = @("import.*does not exist", "package.*does not exist", "cannot find symbol.*class")
}

# =============================================================================
# ETAPA 1: Ler o plano e identificar arquivos
# =============================================================================
Write-Host "`n[ETAPA 1/5] Lendo e analisando o plano de migracao..." -ForegroundColor White
$filesToProcess = @()
$planContent = Get-Content $planFile -Raw -Encoding UTF8

# CORRIGIDO: Regex melhorada para capturar conteudo da fase processada
$phaseRegex = [regex]"(?ms)### \*\*FASE ${PhaseToProcess}.*?\n.*?\n(.*?)(?=\n### \*\*FASE|\z)"
$match = $phaseRegex.Match($planContent)

if (-not $match.Success) { 
    Write-Host "ERRO: Nao foi possivel encontrar a secao da 'FASE $PhaseToProcess' no plano." -ForegroundColor Red
    Write-Host "DEBUG: Procurando por: ### **FASE ${PhaseToProcess}" -ForegroundColor Yellow
    # Tentar busca mais ampla para debug
    $debugRegex = [regex]"### \*\*FASE ${PhaseToProcess}"
    $debugMatch = $debugRegex.Match($planContent)
    if ($debugMatch.Success) {
        Write-Host "DEBUG: Encontrou titulo da fase na linha: $($debugMatch.Index)" -ForegroundColor Yellow
        # Mostrar context
        $context = $planContent.Substring([Math]::Max(0, $debugMatch.Index - 100), [Math]::Min(200, $planContent.Length - $debugMatch.Index + 100))
        Write-Host "DEBUG Context: $context" -ForegroundColor Gray
    }
    exit 1 
}

$fileListText = $match.Groups[1].Value
$fileNameRegex = [regex]'- \[ \] ([\w\.]+)'
$filesToProcess = $fileNameRegex.Matches($fileListText) | ForEach-Object { $_.Groups[1].Value }

if ($filesToProcess.Count -eq 0) { Write-Host "AVISO: Nenhum arquivo pendente ('[ ]') encontrado para a Fase $PhaseToProcess." -ForegroundColor Yellow; exit 0 }
Write-Host "Encontrados $($filesToProcess.Count) arquivos para processar na Fase $PhaseToProcess." -ForegroundColor Green

# =============================================================================
# ETAPA 2: Habilitar, Compilar e Reverter para gerar o log de erros
# =============================================================================
Write-Host "`n[ETAPA 2/5] Habilitando, compilando e revertendo para gerar log de erros..." -ForegroundColor White
$enabledSimpleNames = @()
foreach ($fileName in $filesToProcess) {
    $fileObject = Get-ChildItem -Path $baseDir -Recurse -Filter "$fileName.disabled" | Select-Object -First 1
    if ($fileObject) {
        try {
            Move-Item -Path $fileObject.FullName -Destination ($fileObject.FullName -replace '\.disabled$', '') -Force
            $enabledSimpleNames += $fileName
        } catch { Write-Host "  ERRO AO HABILITAR $($fileObject.Name)" -ForegroundColor Red }
    }
}
Write-Host "$($enabledSimpleNames.Count) arquivos habilitados temporariamente." -ForegroundColor Green
Write-Host "Executando validacao com './gradlew build'..." -ForegroundColor Yellow
$buildResult = & .\gradlew build 2>&1 | Out-String
if ($LASTEXITCODE -eq 0 -and $buildResult -match "BUILD SUCCESSFUL") {
    Write-Host "SUCESSO INESPERADO! A Fase $PhaseToProcess compilou. Os arquivos permanecerao habilitados." -ForegroundColor Green
    exit 0
}
Write-Host "Build falhou como esperado. Gerando log de erros..." -ForegroundColor Yellow
$buildResult | Out-File -FilePath $errorLogPath -Encoding UTF8

# Analise rapida para identificar arquivos com erros ANTES da reversao
$errorLogContent = $buildResult
$errorBlockRegex = [regex]'([A-Z]:[\\/][^:]+\.java):\d+:\s*error:\s*(.*)'
$errorMatches = $errorBlockRegex.Matches($errorLogContent)
$filesWithErrorsForRevert = @{}
foreach ($match in $errorMatches) {
    $filePath = $match.Groups[1].Value
    $fileName = Split-Path $filePath -Leaf
    $filesWithErrorsForRevert[$fileName] = $true
}

Write-Host "Iniciando reversao SELETIVA dos arquivos..." -ForegroundColor Magenta
$revertedCount = 0
$keptEnabledCount = 0
foreach ($simpleNameToRevert in $enabledSimpleNames) {
    $fileToRevert = Get-ChildItem -Path $baseDir -Recurse -Filter $simpleNameToRevert | Select-Object -First 1
    if ($fileToRevert) {
        # So reverte se o arquivo teve erros de compilacao
        if ($filesWithErrorsForRevert.ContainsKey($simpleNameToRevert)) {
            try { 
                Move-Item -Path $fileToRevert.FullName -Destination "$($fileToRevert.FullName).disabled" -Force 
                $revertedCount++
            } catch {}
        } else {
            Write-Host "  MANTIDO HABILITADO: $simpleNameToRevert (sem erros detectados)" -ForegroundColor Green
            $keptEnabledCount++
        }
    }
}
Write-Host "Reversao seletiva concluida. $revertedCount arquivos revertidos, $keptEnabledCount mantidos habilitados." -ForegroundColor Green
Write-Host "O arquivo '$errorLogPath' esta pronto para analise." -ForegroundColor Green

# =============================================================================
# ETAPA 3: Mapear todas as classes nao resolvidas do projeto
# =============================================================================
Write-Host "`n[ETAPA 3/5] Mapeando todas as classes desabilitadas para analise de dependencia..." -ForegroundColor White
$unresolvedClasses = @{}
$classRegex = [regex]'(?:public\s+)?(?:final\s+)?(?:abstract\s+)?(?:static\s+)?(class|interface|enum|record)\s+([a-zA-Z0-9_]+)'
$allRemainingDisabledFiles = Get-ChildItem -Path $baseDir -Recurse -Filter "*.java.disabled"
foreach ($file in $allRemainingDisabledFiles) {
    $fileContentForRegex = Get-Content $file.FullName -Raw -Encoding UTF8
    if($fileContentForRegex){
        $classNameMatch = $classRegex.Match($fileContentForRegex)
        if ($classNameMatch.Success) { $unresolvedClasses[$classNameMatch.Groups[2].Value] = $true }
    }
}
Write-Host "$($unresolvedClasses.Count) classes nao resolvidas foram mapeadas." -ForegroundColor Green

# =============================================================================
# ETAPA 4: Analisar log de erros e categorizar arquivos
# =============================================================================
Write-Host "`n[ETAPA 4/5] Analisando log e categorizando arquivos da Fase $PhaseToProcess..." -ForegroundColor White
$errorLogContent = Get-Content $errorLogPath -Raw -Encoding UTF8
$categorizedFiles = @{}; $postponedFiles = @{}; $filesWithErrors = @{}

# Use o log de erro da compilacao para analise
$logToAnalyze = $buildResult
$errorBlockRegex = [regex]'([A-Z]:[\\/][^:]+\.java):\d+:\s*error:\s*(.*)'
$errorMatches = $errorBlockRegex.Matches($logToAnalyze)

Write-Host "Encontrados $($errorMatches.Count) erros para processar..." -ForegroundColor Yellow

foreach ($match in $errorMatches) {
    $filePath = $match.Groups[1].Value; $errorMessage = $match.Groups[2].Value; $fileName = Split-Path $filePath -Leaf
    if ($filesWithErrors.ContainsKey($fileName)) { continue }
    $filesWithErrors[$fileName] = $true; $categorized = $false
    
    Write-Host "  Processando: $fileName - $($errorMessage.Substring(0, [Math]::Min(60, $errorMessage.Length)))..." -ForegroundColor Gray
    
    foreach ($entry in $errorKeywordsToSubPhase.GetEnumerator()) {
        $subPhaseKey = $entry.Name; $keywords = $entry.Value
        foreach ($keyword in $keywords) {
            if ($errorMessage -match $keyword) {
                if (-not $categorizedFiles.ContainsKey($subPhaseKey)) { $categorizedFiles[$subPhaseKey] = @() }
                $categorizedFiles[$subPhaseKey] += $fileName; $categorized = $true
                Write-Host "    -> Categorizado em $subPhaseKey (palavra-chave: $keyword)" -ForegroundColor Cyan
                break
            }
        }
        if ($categorized) { break }
    }
    
    # Verifica dependencias internas se nao foi categorizado
    if (-not $categorized) {
        if ($errorMessage -match "cannot find symbol\s*symbol:\s*class\s*(\w+)") {
            $missingSymbol = $matches[1]
            if ($unresolvedClasses.ContainsKey($missingSymbol)) {
                $postponedFiles[$fileName] = "Depende de `$missingSymbol`, que ainda nao foi habilitado."; $categorized = $true
                Write-Host "    -> Adiado (depende de $missingSymbol)" -ForegroundColor Yellow
            }
        }
    }
    
    # Se ainda nao foi categorizado, coloca em API_Desconhecida
    if (-not $categorized) {
        $subPhaseKey = "9-API_Desconhecida"
        if (-not $categorizedFiles.ContainsKey($subPhaseKey)) { $categorizedFiles[$subPhaseKey] = @() }
        $categorizedFiles[$subPhaseKey] += $fileName
        Write-Host "    -> Categorizado em API_Desconhecida" -ForegroundColor Magenta
    }
}
Write-Host "Analise concluida. $($filesWithErrors.Count) arquivos com erros foram categorizados em $($categorizedFiles.Keys.Count) subfases." -ForegroundColor Green

# =============================================================================
# ETAPA 5: Gerar e Inserir o novo plano no PLANO_MIGRACAO.md
# =============================================================================
Write-Host "`n[ETAPA 5/5] Gerando e atualizando o PLANO_MIGRACAO.md..." -ForegroundColor White
# CORRIGIDO: Delimitada a variavel com ${} para evitar erro de parsing
$newPlanSection = @"

### **FASE ${PhaseToProcess}: ANALISE E CORRECAO** (Gerado em $(Get-Date))

*Esta secao foi gerada automaticamente. Os arquivos da Fase $PhaseToProcess foram agrupados por tipo de erro para facilitar a correcao.*
"@
$completedInPhaseRegex = [regex]"\s-\s\[x\]\s([\w\.]+)"
$completedMatches = $completedInPhaseRegex.Matches($fileListText)
if($completedMatches.Count -gt 0){
    $newPlanSection += @"

**Arquivos ja concluidos nesta fase:**
"@
    foreach($completedMatch in $completedMatches){ $newPlanSection += "`n- [x] $($completedMatch.Groups[1].Value)" }
}
$newPlanSection += "`n---"
foreach ($subPhaseKey in $categorizedFiles.Keys | Sort-Object) {
    $subPhaseName = $subPhaseKey.Split('-')[1]; $files = $categorizedFiles[$subPhaseKey] | Sort-Object
    $newPlanSection += @"

#### **Fase ${PhaseToProcess}.$($subPhaseKey.Split('-')[0]): $subPhaseName** ($($files.Count) arquivos)

"@
    foreach ($file in $files) { $newPlanSection += "`n- [ ] $file" }
    $newPlanSection += "`n"
}
if ($postponedFiles.Count -gt 0) {
    $newPlanSection += @"
---

#### **Arquivos Adiados para Fases Futuras** ($($postponedFiles.Count) arquivos)
*Estes arquivos falharam porque dependem de outras classes do mod que ainda nao foram habilitadas. Eles serao resolvidos em fases posteriores.*

"@
    foreach ($entry in $postponedFiles.GetEnumerator() | Sort-Object -Property Name) { $newPlanSection += "`n- [ ] **$($entry.Name)** - *Motivo: $($entry.Value)*" }
    $newPlanSection += "`n"
}
$processedFiles = $filesWithErrors.Keys + $postponedFiles.Keys
$filesWithoutErrors = $filesToProcess | Where-Object { $processedFiles -notcontains $_ } | Sort-Object
if ($filesWithoutErrors.Count -gt 0) {
     $newPlanSection += @"
---

#### **Arquivos Sem Erros Detectados - HABILITADOS** ($($filesWithoutErrors.Count) arquivos)
*Estes arquivos estavam na lista da Fase $PhaseToProcess, mas nao apresentaram erros de compilacao diretos no log. PERMANECEM HABILITADOS.*

"@
    foreach ($file in $filesWithoutErrors) { $newPlanSection += "`n- [x] $file (habilitado automaticamente)" }
    $newPlanSection += "`n"
}
$updatedPlanContent = $planContent -replace $phaseRegex, $newPlanSection
try {
    Set-Content -Path $planFile -Value $updatedPlanContent -Encoding UTF8
    Write-Host "SUCESSO! O arquivo '$planFile' foi atualizado com o plano detalhado para a Fase $PhaseToProcess." -ForegroundColor Green
    Write-Host "`n=== RESUMO DA EXECUCAO ===" -ForegroundColor Cyan
    Write-Host "- Arquivos processados: $($filesToProcess.Count)" -ForegroundColor White
    Write-Host "- Arquivos com erros: $($filesWithErrors.Count)" -ForegroundColor Red
    Write-Host "- Arquivos sem erros (habilitados): $($filesWithoutErrors.Count)" -ForegroundColor Green
    Write-Host "- Arquivos adiados: $($postponedFiles.Count)" -ForegroundColor Yellow
    Write-Host "- Subfases geradas: $($categorizedFiles.Keys.Count)" -ForegroundColor Cyan
    foreach ($subPhase in $categorizedFiles.Keys | Sort-Object) {
        Write-Host "  * $subPhase`: $($categorizedFiles[$subPhase].Count) arquivos" -ForegroundColor Gray
    }
    Write-Host "`nPor favor, revise o plano. Voce pode agora usar um script de habilitacao por sub-fase para comecar a correcao." -ForegroundColor Cyan
} catch {
    Write-Host "ERRO AO SALVAR O PLANO: $($_.Exception.Message)" -ForegroundColor Red
}