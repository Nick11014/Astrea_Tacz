# =============================================================================
# Enable-Phase-2.ps1 - Habilita e valida os arquivos da Fase 2 do plano
# =============================================================================
# Este script le o arquivo PLANO_MIGRACAO.md, identifica os arquivos da
# "Fase 2", os renomeia de .java.disabled para .java e executa um build.
#
# Se o build falhar, o script revertera automaticamente os arquivos
# habilitados para seu estado .java.disabled original.
# =============================================================================

Write-Host "=== HABILITANDO ARQUIVOS DA FASE 2 (com reversao automatica) ===" -ForegroundColor Cyan

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
    # CORRIGIDO: Expressao regular ajustada para encontrar a FASE 2. Parenteses foram escapados com '\'.
    $phase2Regex = [regex]'(?ms)### \*\*FASE 2: DEPENDENCIAS BAIXAS \(1-3\)\*\*.*?\n(.*?)\n---'
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

# --- Habilitacao dos Arquivos ---
$enabledCount = 0
$notFoundCount = 0
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
            $enabledCount++
            $enabledList += $fileName
        }
        catch {
            Write-Host "  ERRO ao renomear $($fileToEnable.Name): $($_.Exception.Message)" -ForegroundColor Red
        }
    }
    else {
        Write-Host "  NAO ENCONTRADO: O arquivo $disabledFileName nao foi localizado." -ForegroundColor Yellow
        $notFoundCount++
        $notFoundList += $disabledFileName
    }
}

Write-Host ""
Write-Host "--- Resumo da Habilitacao ---" -ForegroundColor Cyan
Write-Host "Arquivos habilitados com sucesso: $enabledCount" -ForegroundColor Green
Write-Host "Arquivos nao encontrados: $notFoundCount" -ForegroundColor Yellow

if ($notFoundCount -gt 0) {
    Write-Host "Arquivos nao encontrados:" -ForegroundColor Yellow
    foreach ($file in $notFoundList) {
        Write-Host "  - $file"
    }
}
Write-Host "-----------------------------"
Write-Host ""

# --- Validacao com Build ---
if ($enabledCount -gt 0) {
    Write-Host "Executando validacao com './gradlew build'. Isso pode levar alguns minutos..." -ForegroundColor Yellow
    
    try {
        $buildResult = & .\gradlew build 2>&1 | Out-String
        
        if ($LASTEXITCODE -eq 0 -and $buildResult -match "BUILD SUCCESSFUL") {
            Write-Host "===============================================" -ForegroundColor Green
            Write-Host "BUILD SUCCESSFUL!" -ForegroundColor Green
            Write-Host "A Fase 2 foi habilitada e compilada com sucesso." -ForegroundColor Green
            Write-Host "Total de $enabledCount arquivos habilitados." -ForegroundColor Green
            Write-Host "===============================================" -ForegroundColor Green
            
            Write-Host "Para atualizar o plano com o progresso, execute o script 'Generate-Plan.ps1' novamente." -ForegroundColor Cyan

        }
        else {
            Write-Host "===============================================" -ForegroundColor Red
            Write-Host "BUILD FALHOU!" -ForegroundColor Red
            Write-Host "A habilitacao da Fase 2 resultou em erros de compilacao." -ForegroundColor Red
            
            # CORRIGIDO: Nome do arquivo de log alterado para fase 2
            $errorLogPath = "build_errors_phase2.txt"
            $buildResult | Out-File -FilePath $errorLogPath -Encoding UTF8
            Write-Host "O log de erro completo foi salvo em: $errorLogPath" -ForegroundColor Yellow
            
            #------------------------------------------------
            # Bloco de Reversao Automatica
            #------------------------------------------------
            Write-Host ""
            Write-Host "INICIANDO REVERSAO AUTOMATICA DAS ALTERACOES..." -ForegroundColor Magenta
            
            $revertedCount = 0
            foreach ($fileToRevertName in $enabledList) {
                $fileToRevert = Get-ChildItem -Path $baseDir -Recurse -Filter $fileToRevertName | Select-Object -First 1
                if ($null -ne $fileToRevert) {
                    try {
                        $revertedName = "$($fileToRevert.FullName).disabled"
                        Move-Item -Path $fileToRevert.FullName -Destination $revertedName -Force
                        Write-Host "  - REVERTIDO: $fileToRevertName" -ForegroundColor Magenta
                        $revertedCount++
                    }
                    catch {
                        Write-Host "  - ERRO AO REVERTER ${fileToRevertName}: $($_.Exception.Message)" -ForegroundColor Red
                    }
                }
            }
            Write-Host "$revertedCount de $($enabledList.Count) arquivos foram revertidos para .java.disabled." -ForegroundColor Magenta
            Write-Host "O projeto foi restaurado a um estado compillavel." -ForegroundColor Magenta
            #------------------------------------------------

            $errorLines = $buildResult -split "`n" | Where-Object { $_ -match "(error|Error|ERROR|FAILURE:)" } | Select-Object -First 10
            if ($errorLines.Count -gt 0) {
                Write-Host ""
                Write-Host "PRIMEIROS ERROS ENCONTRADOS NO LOG:" -ForegroundColor Red
                foreach ($errorLine in $errorLines) {
                    Write-Host "  $errorLine" -ForegroundColor Red
                }
            }
            Write-Host ""
            # CORRIGIDO: Mensagem de acao recomendada atualizada para Fase 2
            Write-Host "ACAO RECOMENDADA: Analise o log '$errorLogPath' para corrigir as APIs nos arquivos da Fase 2." -ForegroundColor Yellow
            Write-Host "===============================================" -ForegroundColor Red
        }
    }
    catch {
        Write-Host "ERRO CRITICO ao executar o processo do gradle: $($_.Exception.Message)" -ForegroundColor Red
    }
}
else {
    Write-Host "Nenhum arquivo foi habilitado. O processo de build nao foi executado." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Processo da Fase 2 concluido." -ForegroundColor Cyan