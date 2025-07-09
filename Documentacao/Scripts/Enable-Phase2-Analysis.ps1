# =============================================================================
# Enable-Phase-2-Iterative.ps1 - Habilita e valida os arquivos da Fase 2
# de forma iterativa ate obter um build estavel.
# =============================================================================
# Este script le o plano, habilita os arquivos da "Fase 2" e entra em um
# ciclo de build. A cada falha, ele identifica os arquivos com erro,
# os reverte para .java.disabled, e tenta o build novamente com os
# arquivos restantes. O processo continua ate que o build seja bem-sucedido.
# =============================================================================

Write-Host "=== HABILITANDO ARQUIVOS DA FASE 2 (com reversao iterativa) ===" -ForegroundColor Cyan

# --- Verificacao do Ambiente ---
$baseDir = "src\main\java\com\tacz\guns"
$planFile = "PLANO_MIGRACAO.md"
$buildFile = "build.gradle"

# Validar se o plano de migracao existe
if (-not (Test-Path $planFile)) {
    Write-Host "ERRO: Arquivo de plano nao encontrado: $planFile" -ForegroundColor Red
    Write-Host "Execute este script na mesma pasta onde PLANO_MIGRACAO.md se encontra." -ForegroundColor Yellow
    exit 1
}

# Validar se o diretorio base existe
if (-not (Test-Path $baseDir)) {
    Write-Host "ERRO: Diretorio base nao encontrado: $baseDir" -ForegroundColor Red
    exit 1
}

# Validar se o build.gradle existe
if (-not (Test-Path $buildFile)) {
    Write-Host "ERRO: Arquivo build.gradle nao encontrado. Execute na raiz do projeto." -ForegroundColor Red
    exit 1
}

# --- Leitura e Analise do Plano ---
Write-Host "Lendo o plano de migracao: $planFile" -ForegroundColor White

try {
    $planContent = Get-Content $planFile -Raw -Encoding UTF8
    $phase2Regex = [regex]'(?ms)### \*\*FASE 2: DEPENDENCIAS BAIXAS \(1-3\)\*\*.*?\n(.*?)(?=\n### \*\*FASE 3)'
    $match = $phase2Regex.Match($planContent)

    if (-not $match.Success) {
        Write-Host "ERRO: Nao foi possivel encontrar a secao da 'FASE 2' no plano." -ForegroundColor Red
        exit 1
    }

    $fileListText = $match.Groups[1].Value
    $fileNameRegex = [regex]'- \[ \] ([\w\.]+)'
    $filesToEnable = $fileNameRegex.Matches($fileListText) | ForEach-Object { $_.Groups[1].Value }

    if ($filesToEnable.Count -eq 0) {
        Write-Host "AVISO: Nenhum arquivo encontrado para habilitar na Fase 2. Verifique se eles ja nao estao marcados como '[x]'." -ForegroundColor Yellow
        exit 0
    }
}
catch {
    Write-Host "ERRO ao ler ou analisar o arquivo de plano: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

Write-Host "Encontrados $($filesToEnable.Count) arquivos para habilitar na Fase 2." -ForegroundColor Green
Write-Host ""

# --- Habilitacao Inicial dos Arquivos ---
$enabledList = @()
$notFoundList = @()

foreach ($fileName in $filesToEnable) {
    Write-Host "Processando: $fileName" -ForegroundColor White
    $disabledFileName = "$fileName.disabled"
    $fileToEnable = Get-ChildItem -Path $baseDir -Recurse -Filter $disabledFileName | Select-Object -First 1

    if ($null -ne $fileToEnable) {
        try {
            $newName = $fileToEnable.FullName -replace '\.disabled$', ''
            Move-Item -Path $fileToEnable.FullName -Destination $newName -Force
            Write-Host "  HABILITADO: $fileName" -ForegroundColor Green
            $enabledList += $fileName
        } catch {
            Write-Host "  ERRO ao renomear $($fileToEnable.Name): $($_.Exception.Message)" -ForegroundColor Red
        }
    } else {
        Write-Host "  NAO ENCONTRADO: O arquivo $disabledFileName nao foi localizado." -ForegroundColor Yellow
        $notFoundList += $disabledFileName
    }
}

Write-Host ""
Write-Host "--- Resumo da Habilitacao Inicial ---" -ForegroundColor Cyan
Write-Host "Arquivos habilitados para a primeira tentativa: $($enabledList.Count)" -ForegroundColor Green
if ($notFoundList.Count -gt 0) {
    Write-Host "Arquivos nao encontrados: $($notFoundList.Count)" -ForegroundColor Yellow
    $notFoundList | ForEach-Object { Write-Host "  - $_" }
}
Write-Host "------------------------------------"
Write-Host ""

# --- CICLO DE VALIDACAO E REVERSAO ITERATIVA ---
if ($enabledList.Count -gt 0) {
    $buildAttempt = 1
    $currentlyEnabledFiles = @($enabledList)
    $totalRevertedFiles = @()

    while ($true) {
        Write-Host "=======================================================================" -ForegroundColor Cyan
        Write-Host "INICIANDO TENTATIVA DE BUILD Nº $buildAttempt com $($currentlyEnabledFiles.Count) arquivos..." -ForegroundColor Yellow
        Write-Host "=======================================================================" -ForegroundColor Cyan

        try {
            $buildResult = & .\gradlew build 2>&1 | Out-String
            
            if ($LASTEXITCODE -eq 0 -and $buildResult -match "BUILD SUCCESSFUL") {
                Write-Host "===============================================" -ForegroundColor Green
                Write-Host "BUILD SUCCESSFUL!" -ForegroundColor Green
                Write-Host "O projeto foi estabilizado com sucesso." -ForegroundColor Green
                Write-Host "$($currentlyEnabledFiles.Count) arquivos da Fase 2 foram mantidos habilitados." -ForegroundColor Green
                Write-Host "$($totalRevertedFiles.Count) arquivos foram revertidos no total." -ForegroundColor Magenta
                Write-Host "===============================================" -ForegroundColor Green
                
                $keptFilesPath = "habilitados_sem_erros_fase2.txt"
                $currentlyEnabledFiles | Sort-Object | Out-File -FilePath $keptFilesPath -Encoding UTF8
                Write-Host "Lista final de arquivos mantidos com sucesso salva em: $keptFilesPath" -ForegroundColor Cyan
                Write-Host "Para atualizar o plano com o progresso, execute o script 'Generate-Plan.ps1'." -ForegroundColor Cyan
                break # Encerra o ciclo while
            }
            else {
                # BUILD FALHOU, INICIAR ANALISE E REVERSAO
                Write-Host "-----------------------------------------------" -ForegroundColor Red
                Write-Host "TENTATIVA DE BUILD Nº $buildAttempt FALHOU." -ForegroundColor Red
                
                $errorLogPath = "build_errors_attempt_$buildAttempt.txt"
                $buildResult | Out-File -FilePath $errorLogPath -Encoding UTF8
                Write-Host "Log de erro salvo em: $errorLogPath" -ForegroundColor Yellow

                # Extrai os nomes dos arquivos com erro desta tentativa
                $errorLines = $buildResult -split "`n" | Where-Object { $_ -match '\.java:\d+:\s+error:' }
                $filesWithErrorsList = foreach ($line in $errorLines) {
                    $match = [regex]::Match($line, '(?<path>.*\.java):\d+:\s+error:')
                    if ($match.Success) {
                        Split-Path -Path $match.Groups['path'].Value.Trim() -Leaf
                    }
                }
                $newlyFoundErrors = $filesWithErrorsList | Sort-Object -Unique

                if ($newlyFoundErrors.Count -eq 0) {
                    Write-Host ""
                    Write-Host "ERRO CRITICO: O build falhou, mas nenhum arquivo de erro foi identificado." -ForegroundColor Red
                    Write-Host "Isso pode indicar um problema de configuracao do projeto ou um erro nao relacionado a um arquivo especifico." -ForegroundColor Red
                    Write-Host "O processo foi interrompido. Verifique o log '$errorLogPath' manualmente." -ForegroundColor Red
                    break # Encerra o ciclo while
                }

                Write-Host "$($newlyFoundErrors.Count) arquivo(s) com erro encontrados nesta iteracao. Revertendo-os..." -ForegroundColor Magenta
                
                $filesToRevertThisIteration = @()
                
                # Reverte apenas os arquivos com erro encontrados nesta iteracao
                foreach ($fileToRevertName in $newlyFoundErrors) {
                    if ($currentlyEnabledFiles -contains $fileToRevertName) {
                        $fileToRevert = Get-ChildItem -Path $baseDir -Recurse -Filter $fileToRevertName | Select-Object -First 1
                        if ($null -ne $fileToRevert) {
                            try {
                                $revertedName = "$($fileToRevert.FullName).disabled"
                                Move-Item -Path $fileToRevert.FullName -Destination $revertedName -Force
                                Write-Host "  - REVERTIDO: $fileToRevertName" -ForegroundColor Magenta
                                $filesToRevertThisIteration += $fileToRevertName
                            } catch {
                                Write-Host "  - ERRO AO REVERTER ${fileToRevert.Name}: $($_.Exception.Message)" -ForegroundColor Red
                            }
                        }
                    }
                }

                # Atualiza as listas para a proxima iteracao
                $totalRevertedFiles += $filesToRevertThisIteration
                $currentlyEnabledFiles = $currentlyEnabledFiles | Where-Object { $f = $_; $filesToRevertThisIteration -notcontains $f }
                
                Write-Host "Restam $($currentlyEnabledFiles.Count) arquivos candidatos para a proxima tentativa." -ForegroundColor Yellow
                Write-Host "-----------------------------------------------"
                Write-Host ""
                $buildAttempt++
            }
        } catch {
            Write-Host "ERRO CRITICO no script durante o ciclo de build: $($_.Exception.Message)" -ForegroundColor Red
            break
        }
    }
}
else {
    Write-Host "Nenhum arquivo foi habilitado. O processo de build nao foi executado." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Processo da Fase 2 concluido." -ForegroundColor Cyan