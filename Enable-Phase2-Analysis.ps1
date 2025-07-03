# =============================================================================
# Enable-Phase2-Analysis.ps1 - Habilitador e Analisador da Fase 2 TacZ
# =============================================================================
# Este script habilita todos os arquivos candidatos da Fase 2, testa a compilação,
# captura erros específicos e reverte arquivos problemáticos para análise detalhada
# =============================================================================

Write-Host "=== HABILITADOR E ANALISADOR DA FASE 2 - TacZ ===" -ForegroundColor Cyan

# --- ARQUIVOS CANDIDATOS DA FASE 2 ---

# Fase 2.1: Arquivos com zero dependências desconhecidas
$fase2_1_Ready = @(
    "BonesItem.java",
    "GeometryModelLegacy.java", 
    "GeometryModelNew.java",
    "BedrockPolygon.java",
    "BlockDisplay.java",
    "GunAmmo.java",
    "ThrowableAnimationStateContext.java",
    "RawAnimationStructure.java"
)

# Fase 2.2: Arquivos com dependências mínimas
$fase2_2_Minimal = @(
    "GunDrawEvent.java", "GunFireEvent.java", "GunReloadEvent.java", 
    "GunMeleeEvent.java", "GunFinishReloadEvent.java", "GunFireSelectEvent.java",
    "Linear.java", "Spline.java", "Step.java", "AnimationState.java",
    "AnimationChannel.java", "Animation.java", "AnimationModel.java",
    "CommonAmmoIndexSerializer.java", "CommonAttachmentIndexSerializer.java",
    "TableRecipe.java", "BlockData.java", "GunRecoil.java", "AmmoDisplay.java",
    "ItemAnimationStateContext.java", "BlockIndexPOJO.java", "DataEntry.java",
    "DefaultTableItem.java", "BlackList.java", "CrosshairType.java"
)

# Fase 2.3: Arquivos complexos para teste
$fase2_3_Complex = @(
    "BufferModel.java", "BufferViewModel.java", "Accessors.java",
    "AnimationListener.java", "DefaultAssets.java", "BedrockAmmoModel.java",
    "SlotModel.java", "PlayGunSoundEvent.java"
)

# --- FUNCOES UTILITARIAS ---

function Get-ProjectPaths {
    $projectRoot = $PSScriptRoot
    $srcPath = Join-Path $projectRoot "src\main\java\com\tacz\guns"
    
    Write-Host "Pasta do projeto: $projectRoot" -ForegroundColor Yellow
    Write-Host "Pasta source: $srcPath" -ForegroundColor Yellow
    Write-Host ""
    
    return @{
        Root = $projectRoot
        Source = $srcPath
    }
}

function Enable-FileGroup {
    param(
        [string]$groupName,
        [array]$files,
        [string]$srcPath
    )
    
    Write-Host "Habilitando $groupName..." -ForegroundColor Green
    $enabledFiles = @()
    $skippedFiles = @()
    
    foreach ($file in $files) {
        $disabledPath = Get-ChildItem -Path $srcPath -Recurse -Filter "$file.disabled" -ErrorAction SilentlyContinue
        if ($disabledPath) {
            $enabledPath = $disabledPath.FullName -replace '\.disabled$', ''
            try {
                Rename-Item -Path $disabledPath.FullName -NewName $enabledPath -ErrorAction Stop
                Write-Host "  OK: $file" -ForegroundColor Green
                $enabledFiles += @{
                    Name = $file
                    OriginalPath = $disabledPath.FullName
                    EnabledPath = $enabledPath
                }
            }
            catch {
                Write-Host "  ERRO: $file - $($_.Exception.Message)" -ForegroundColor Red
                $skippedFiles += $file
            }
        }
        else {
            Write-Host "  SKIP: $file (nao encontrado ou ja habilitado)" -ForegroundColor Yellow
            $skippedFiles += $file
        }
    }
    
    Write-Host "  Resultado: $($enabledFiles.Count)/$($files.Count) arquivos habilitados" -ForegroundColor Cyan
    
    return @{
        Enabled = $enabledFiles
        Skipped = $skippedFiles
        GroupName = $groupName
    }
}

function Test-BuildAndCaptureErrors {
    param(
        [string]$errorFile = "build_errors_phase2.txt"
    )
    
    Write-Host ""
    Write-Host "Executando build completo..." -ForegroundColor Yellow
    
    try {
        # Executar build e capturar saída
        $buildOutput = & .\gradlew build --no-daemon --console=plain 2>&1 | Out-String
        
        # Salvar saída completa
        $buildOutput | Out-File -FilePath $errorFile -Encoding UTF8 -Force
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "BUILD SUCCESSFUL!" -ForegroundColor Green
            return @{
                Success = $true
                Output = $buildOutput
                ErrorFile = $errorFile
            }
        }
        else {
            Write-Host "BUILD FAILED!" -ForegroundColor Red
            Write-Host "Erros salvos em: $errorFile" -ForegroundColor Yellow
            
            # Extrair erros para visualização
            $errorLines = ($buildOutput -split "`n" | Where-Object { $_ -match "(error|Error|ERROR)" } | Select-Object -First 10)
            if ($errorLines) {
                Write-Host ""
                Write-Host "Primeiros erros encontrados:" -ForegroundColor Red
                foreach ($line in $errorLines) {
                    Write-Host "  $line" -ForegroundColor Red
                }
            }
            
            return @{
                Success = $false
                Output = $buildOutput
                ErrorFile = $errorFile
                ErrorLines = $errorLines
            }
        }
    }
    catch {
        Write-Host "Erro critico ao executar build: $($_.Exception.Message)" -ForegroundColor Red
        return @{
            Success = $false
            Output = "CRITICAL ERROR: $($_.Exception.Message)"
            ErrorFile = $errorFile
        }
    }
}

function Revert-ProblematicFiles {
    param(
        [array]$enabledFiles,
        [string]$buildOutput
    )
    
    Write-Host ""
    Write-Host "Analisando arquivos problematicos e revertendo..." -ForegroundColor Yellow
    
    $revertedFiles = @()
    $keptFiles = @()
    
    foreach ($fileInfo in $enabledFiles) {
        $fileName = $fileInfo.Name
        $fileNameWithoutExt = $fileName -replace '\.java$', ''
        
        # Verificar se o arquivo aparece nos erros
        $hasErrors = $buildOutput -match $fileNameWithoutExt
        
        if ($hasErrors) {
            # Reverter arquivo problemático
            try {
                Rename-Item -Path $fileInfo.EnabledPath -NewName $fileInfo.OriginalPath -ErrorAction Stop
                Write-Host "  REVERTIDO: $fileName (encontrados erros)" -ForegroundColor Yellow
                $revertedFiles += $fileInfo
            }
            catch {
                Write-Host "  ERRO ao reverter $fileName : $($_.Exception.Message)" -ForegroundColor Red
            }
        }
        else {
            Write-Host "  MANTIDO: $fileName (sem erros detectados)" -ForegroundColor Green
            $keptFiles += $fileInfo
        }
    }
    
    Write-Host ""
    Write-Host "Resumo da reversao:" -ForegroundColor Cyan
    Write-Host "  Arquivos revertidos: $($revertedFiles.Count)" -ForegroundColor Yellow
    Write-Host "  Arquivos mantidos: $($keptFiles.Count)" -ForegroundColor Green
    
    return @{
        Reverted = $revertedFiles
        Kept = $keptFiles
    }
}

function Analyze-ErrorTypes {
    param(
        [string]$buildOutput,
        [array]$revertedFiles
    )
    
    Write-Host ""
    Write-Host "Analisando tipos de erros..." -ForegroundColor Cyan
    
    $errorCategories = @{}
    
    foreach ($fileInfo in $revertedFiles) {
        $fileName = $fileInfo.Name
        $fileNameWithoutExt = $fileName -replace '\.java$', ''
        
        # Extrair linhas de erro relacionadas
        $fileErrors = ($buildOutput -split "`n" | Where-Object { $_ -match $fileNameWithoutExt -and $_ -match "(error|Error|ERROR)" })
        
        # Categorizar tipos de erro
        $categories = @()
        
        foreach ($error in $fileErrors) {
            if ($error -match 'cannot find symbol') { 
                $categories += "Symbol Not Found" 
            }
            elseif ($error -match 'package.*does not exist') { 
                $categories += "Package Missing" 
            }
            elseif ($error -match 'incompatible types') { 
                $categories += "Type Incompatibility" 
            }
            elseif ($error -match 'method.*not found') { 
                $categories += "Method Missing" 
            }
            elseif ($error -match 'constructor.*not found') { 
                $categories += "Constructor Missing" 
            }
            else { 
                $categories += "Other" 
            }
        }
        
        $primaryCategory = ($categories | Group-Object | Sort-Object Count -Descending | Select-Object -First 1).Name
        if (-not $primaryCategory) { $primaryCategory = "Unknown" }
        
        if (-not $errorCategories.ContainsKey($primaryCategory)) {
            $errorCategories[$primaryCategory] = @()
        }
        $errorCategories[$primaryCategory] += $fileInfo
    }
    
    Write-Host "Categorias de erro identificadas:" -ForegroundColor Green
    foreach ($category in $errorCategories.Keys) {
        Write-Host "  $category : $($errorCategories[$category].Count) arquivos" -ForegroundColor White
    }
    
    return $errorCategories
}

function Generate-Phase2Plan {
    param(
        [hashtable]$errorCategories,
        [array]$keptFiles,
        [string]$errorFile
    )
    
    $planContent = "# FASE 2 - PLANEJAMENTO DETALHADO POR SUBFASES`n"
    $planContent += "Baseado na analise automatica de build errors - $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")`n`n"
    
    $planContent += "## RESUMO DA ANALISE`n`n"
    $planContent += "### Arquivos Aprovados (Build Successful)`n"
    $planContent += "Total: $($keptFiles.Count) arquivos prontos para Fase 2`n`n"

    foreach ($file in $keptFiles) {
        $planContent += "- [x] $($file.Name) - Pronto`n"
    }

    $totalProblematic = 0
    if ($errorCategories.Values) {
        $totalProblematic = ($errorCategories.Values | Measure-Object -Property Count -Sum).Sum
    }
    
    $planContent += "`n### Arquivos com Problemas (Necessitam Migracao)`n"
    $planContent += "Total: $totalProblematic arquivos`n`n"

    # Gerar subfases baseadas nos tipos de erro
    $subfaseCounter = 1
    foreach ($category in ($errorCategories.Keys | Sort-Object)) {
        $files = $errorCategories[$category]
        
        $planContent += "---`n`n"
        $planContent += "## FASE 2.$subfaseCounter : $category`n"
        $planContent += "$($files.Count) arquivos com problemas similares`n`n"
        $planContent += "### Estrategia de Resolucao:`n`n"

        switch ($category) {
            "Symbol Not Found" {
                $planContent += "Problema: Classes ou simbolos nao encontrados`n"
                $planContent += "Solucao:`n" 
                $planContent += "1. Verificar se as dependencias estao habilitadas nas fases anteriores`n"
                $planContent += "2. Atualizar imports para novos namespaces do NeoForge 1.21.1`n"
                $planContent += "3. Implementar classes faltantes ou encontrar equivalentes`n`n"
            }
            "Package Missing" {
                $planContent += "Problema: Pacotes nao existem no NeoForge 1.21.1`n"
                $planContent += "Solucao:`n"
                $planContent += "1. Mapear pacotes antigos para novos equivalentes`n"
                $planContent += "2. Atualizar imports para nova estrutura do NeoForge`n"
                $planContent += "3. Remover dependencias de pacotes removidos`n`n"
            }
            "Type Incompatibility" {
                $planContent += "Problema: Tipos incompativeis entre versoes`n"
                $planContent += "Solucao:`n"
                $planContent += "1. Atualizar tipos para as novas APIs`n"
                $planContent += "2. Implementar conversoes de tipo necessarias`n"
                $planContent += "3. Ajustar genericos e assinaturas de metodo`n`n"
            }
            "Method Missing" {
                $planContent += "Problema: Metodos removidos ou renomeados`n"
                $planContent += "Solucao:`n"
                $planContent += "1. Encontrar metodos equivalentes na nova API`n"
                $planContent += "2. Implementar wrappers para metodos removidos`n"
                $planContent += "3. Atualizar chamadas para nova assinatura`n`n"
            }
            default {
                $planContent += "Problema: $category`n"
                $planContent += "Solucao: Analise especifica necessaria`n`n"
            }
        }

        $planContent += "### Arquivos desta subfase:`n"
        foreach ($file in $files) {
            $planContent += "- [ ] $($file.Name) - Requer migracao de $category`n"
        }
        $planContent += "`n"

        $subfaseCounter++
    }

    $planContent += "---`n`n"
    $planContent += "## PLANO DE IMPLEMENTACAO`n`n"
    $planContent += "### Ordem de Implementacao Recomendada:`n"
    $planContent += "1. Arquivos Aprovados - Habilitar imediatamente`n"
    $planContent += "2. Symbol Not Found - Resolver dependencias basicas`n"
    $planContent += "3. Package Missing - Atualizar imports e namespaces`n"
    $planContent += "4. Method Missing - Adaptar chamadas de metodo`n"
    $planContent += "5. Type Incompatibility - Ajustar tipos e genericos`n"
    $planContent += "6. Outras categorias - Analise caso a caso`n`n"

    $planContent += "---`n`n"
    $planContent += "## ESTATISTICAS`n`n"
    
    $totalFiles = $keptFiles.Count + $totalProblematic
    if ($totalFiles -gt 0) {
        $successRate = [math]::Round(($keptFiles.Count / $totalFiles) * 100, 1)
        $planContent += "Taxa de aprovacao: $successRate%`n"
    }
    
    $planContent += "Arquivo de erros: $errorFile`n`n"

    $planContent += "---`n`n"
    $planContent += "Planejamento automatico gerado - Pronto para implementacao manual!`n"

    # Salvar o plano
    $planFile = "FASE_2_PLANEJAMENTO_DETALHADO.md"
    $planContent | Out-File -FilePath $planFile -Encoding UTF8 -Force
    
    Write-Host ""
    Write-Host "Plano detalhado salvo em: $planFile" -ForegroundColor Green
    
    return $planFile
}

# --- EXECUCAO PRINCIPAL ---

Write-Host "Iniciando analise completa da Fase 2..." -ForegroundColor Cyan
Write-Host ""

$paths = Get-ProjectPaths
$allEnabledFiles = @()

# Habilitar todos os grupos de arquivos
Write-Host "Habilitando todos os arquivos candidatos da Fase 2..." -ForegroundColor Yellow

$phase2_1 = Enable-FileGroup "Fase 2.1 - Arquivos Prontos" $fase2_1_Ready $paths.Source
$allEnabledFiles += $phase2_1.Enabled

$phase2_2 = Enable-FileGroup "Fase 2.2 - Dependencias Minimas" $fase2_2_Minimal $paths.Source  
$allEnabledFiles += $phase2_2.Enabled

$phase2_3 = Enable-FileGroup "Fase 2.3 - Complexos (Teste)" $fase2_3_Complex $paths.Source
$allEnabledFiles += $phase2_3.Enabled

Write-Host ""
Write-Host "Total habilitado: $($allEnabledFiles.Count) arquivos" -ForegroundColor Cyan

# Executar build e capturar erros
$buildResult = Test-BuildAndCaptureErrors

# Analisar e reverter arquivos problemáticos
if (-not $buildResult.Success) {
    $revertResult = Revert-ProblematicFiles $allEnabledFiles $buildResult.Output
    $errorCategories = Analyze-ErrorTypes $buildResult.Output $revertResult.Reverted
    
    # Gerar plano detalhado
    $planFile = Generate-Phase2Plan $errorCategories $revertResult.Kept $buildResult.ErrorFile
    
    Write-Host ""
    Write-Host "ANALISE DA FASE 2 CONCLUIDA!" -ForegroundColor Green
    Write-Host "Plano detalhado: $planFile" -ForegroundColor Yellow
    Write-Host "Arquivo de erros: $($buildResult.ErrorFile)" -ForegroundColor Yellow
    Write-Host "Arquivos mantidos: $($revertResult.Kept.Count)" -ForegroundColor Green
    Write-Host "Arquivos revertidos: $($revertResult.Reverted.Count)" -ForegroundColor Yellow
}
else {
    Write-Host ""
    Write-Host "SUCESSO TOTAL! Todos os arquivos compilaram sem erros!" -ForegroundColor Green
    Write-Host "Arquivos aprovados: $($allEnabledFiles.Count)" -ForegroundColor Green
    
    # Gerar plano de sucesso
    $planFile = Generate-Phase2Plan @{} $allEnabledFiles $buildResult.ErrorFile
}

Write-Host ""
Write-Host "Proximos passos:" -ForegroundColor Cyan
Write-Host "   1. Revisar o plano detalhado gerado" -ForegroundColor White
Write-Host "   2. Implementar subfases na ordem recomendada" -ForegroundColor White
Write-Host "   3. Usar os padroes identificados para acelerar migracoes" -ForegroundColor White
Write-Host ""
