# =============================================================================
# Enable-Phase-1.ps1 - Habilita e valida os arquivos da Fase 1 do plano
# =============================================================================
# Este script lê o arquivo PLANO_MIGRACAO.md, identifica os arquivos da
# "Fase 1", os renomeia de .java.disabled para .java e executa um build
# para garantir que a base continua estável.
# =============================================================================

Write-Host "=== HABILITANDO ARQUIVOS DA FASE 1 ===" -ForegroundColor Cyan

# --- Verificacao do Ambiente ---
$baseDir = "src\main\java\com\tacz\guns"
$planFile = "PLANO_MIGRACAO.md"
$buildFile = "build.gradle"

# Validar se o plano de migração existe
if (-not (Test-Path $planFile)) {
    Write-Host "ERRO: Arquivo de plano nao encontrado: $planFile" -ForegroundColor Red
    Write-Host "Execute este script na mesma pasta onde PLANO_MIGRACAO.md se encontra." -ForegroundColor Yellow
    exit 1
}

# Validar se o diretório base existe
if (-not (Test-Path $baseDir)) {
    Write-Host "ERRO: Diretorio base nao encontrado: $baseDir" -ForegroundColor Red
    exit 1
}

# Validar se o build.gradle existe
if (-not (Test-Path $buildFile)) {
    Write-Host "ERRO: Arquivo build.gradle nao encontrado. Execute na raiz do projeto." -ForegroundColor Red
    exit 1
}

# --- Leitura e Análise do Plano ---
Write-Host "Lendo o plano de migracao: $planFile" -ForegroundColor White

try {
    $planContent = Get-Content $planFile -Raw -Encoding UTF8
    # Regex CORRIGIDA: Ignora caracteres extras (como emojis) no final do título
    # e busca pelo conteúdo entre o título e a próxima linha '---'
    $phase1Regex = [regex]'(?ms)### \*\*FASE 1: PRIMEIRA CAMADA\*\*.*?\n(.*?)\n---'
    $match = $phase1Regex.Match($planContent)

    if (-not $match.Success) {
        Write-Host "ERRO: Nao foi possivel encontrar a secao da 'FASE 1' no plano." -ForegroundColor Red
        exit 1
    }

    $fileListText = $match.Groups[1].Value
    # Regex para extrair nomes de arquivos da lista
    $fileNameRegex = [regex]'- \[ \] ([\w\.]+)'
    $filesToEnable = $fileNameRegex.Matches($fileListText) | ForEach-Object { $_.Groups[1].Value }

    if ($filesToEnable.Count -eq 0) {
        Write-Host "AVISO: Nenhum arquivo encontrado para habilitar na Fase 1. Verifique se eles ja nao estao marcados como '[x]'." -ForegroundColor Yellow
        exit 0
    }
}
catch {
    Write-Host "ERRO ao ler ou analisar o arquivo de plano: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}


Write-Host "Encontrados $($filesToEnable.Count) arquivos para habilitar na Fase 1." -ForegroundColor Green
Write-Host ""

# --- Habilitação dos Arquivos ---
$enabledCount = 0
$notFoundCount = 0
$enabledList = @()
$notFoundList = @()

foreach ($fileName in $filesToEnable) {
    Write-Host "Processando: $fileName" -ForegroundColor White
    $disabledFileName = "$fileName.disabled"
    
    # Busca o arquivo recursivamente no diretório base
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

# --- Validação com Build ---
if ($enabledCount -gt 0) {
    Write-Host "Executando validacao com './gradlew build'. Isso pode levar alguns minutos..." -ForegroundColor Yellow
    
    try {
        # Executa o gradlew build e captura toda a saida (stdout e stderr)
        $buildResult = & .\gradlew build 2>&1 | Out-String
        
        if ($LASTEXITCODE -eq 0 -and $buildResult -match "BUILD SUCCESSFUL") {
            Write-Host "===============================================" -ForegroundColor Green
            Write-Host "BUILD SUCCESSFUL!" -ForegroundColor Green
            Write-Host "A Fase 1 foi habilitada e compilada com sucesso." -ForegroundColor Green
            Write-Host "Total de $enabledCount arquivos habilitados." -ForegroundColor Green
            Write-Host "===============================================" -ForegroundColor Green
            
            # (Opcional) Atualizar o plano para marcar os itens como concluídos
            Write-Host "Para atualizar o plano, execute o script 'Generate-Plan.ps1' novamente." -ForegroundColor Cyan

        }
        else {
            Write-Host "===============================================" -ForegroundColor Red
            Write-Host "BUILD FALHOU!" -ForegroundColor Red
            Write-Host "A habilitacao da Fase 1 resultou em erros de compilacao." -ForegroundColor Red
            
            # Salva o log de erro para análise
            $errorLogPath = "build_errors_phase1.txt"
            $buildResult | Out-File -FilePath $errorLogPath -Encoding UTF8
            Write-Host "O log de erro completo foi salvo em: $errorLogPath" -ForegroundColor Yellow
            
            # Exibe os primeiros erros encontrados
            $errorLines = $buildResult -split "`n" | Where-Object { $_ -match "(error|Error|ERROR|FAILURE:)" } | Select-Object -First 10
            if ($errorLines.Count -gt 0) {
                Write-Host ""
                Write-Host "PRIMEIROS ERROS ENCONTRADOS NO LOG:" -ForegroundColor Red
                foreach ($errorLine in $errorLines) {
                    Write-Host "  $errorLine" -ForegroundColor Red
                }
            }
            Write-Host ""
            Write-Host "ACAO RECOMENDADA: REVERTA as alteracoes (ex: 'git restore .') e analise o log de erro." -ForegroundColor Yellow
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
Write-Host "Processo da Fase 1 concluido." -ForegroundColor Cyan