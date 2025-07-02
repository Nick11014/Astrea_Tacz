# ===================================================================
# Discover-And-Enable-True-Baseline.ps1 - Descobre e habilita base minima segura
# ===================================================================
# Este script descobre dinamicamente arquivos Java que podem ser considerados
# "base absolutamente minima" - ou seja, livres de dependencias internas.
# ===================================================================

Write-Host "=== DESCOBRINDO E HABILITANDO BASE MINIMA DINAMICA ===" -ForegroundColor Cyan

# Verificacao inicial do ambiente
$baseDir = "src\main\java\com\tacz\guns"
$buildFile = "build.gradle"

# Verificar se o diretorio base existe
if (-not (Test-Path $baseDir)) {
    Write-Host "ERRO: Diretorio base nao encontrado: $baseDir" -ForegroundColor Red
    exit 1
}

# Verificar se o build.gradle existe (necessario para validacao)
if (-not (Test-Path $buildFile)) {
    Write-Host "ERRO: Arquivo build.gradle nao encontrado. Execute na raiz do projeto." -ForegroundColor Red
    exit 1
}

Write-Host "Analisando arquivos em: $baseDir" -ForegroundColor Yellow
Write-Host ""

# Contadores para relatorio final
$totalFiles = 0
$enabledFiles = 0
$ignoredFiles = 0
$enabledList = @()
$ignoredList = @()

# Buscar todos os arquivos .java.disabled recursivamente
$disabledFiles = Get-ChildItem -Path $baseDir -Recurse -Filter "*.java.disabled"

if ($disabledFiles.Count -eq 0) {
    Write-Host "AVISO: Nenhum arquivo .java.disabled encontrado em $baseDir" -ForegroundColor Yellow
    exit 0
}

Write-Host "Encontrados $($disabledFiles.Count) arquivos para analise..." -ForegroundColor Yellow
Write-Host ""

foreach ($file in $disabledFiles) {
    $totalFiles++
    $fileName = $file.Name.Replace(".disabled", "")
    $relativePath = $file.FullName.Replace((Get-Location).Path, "").TrimStart('\')
    
    Write-Host "Analisando: $fileName" -ForegroundColor White
    
    try {
        # Ler o conteudo completo do arquivo
        $content = Get-Content $file.FullName -Raw -Encoding UTF8
        
        # REGRA A: Verificar ausencia de imports internos
        # Usar aspas simples para a regex como solicitado
        $hasInternalImports = $content -match 'import com\.tacz\.guns\.'
        
        # REGRA B: Verificar ausencia de heranca (extends)
        $hasExtends = $content -match '\bextends\b'
        
        # REGRA C: Verificar ausencia de implementacao de interfaces (implements)
        $hasImplements = $content -match '\bimplements\b'
        
        # Determinar se o arquivo e seguro (deve passar em TODAS as regras)
        $isSafe = -not ($hasInternalImports -or $hasExtends -or $hasImplements)
        
        if ($isSafe) {
            # Arquivo seguro - habilitar
            try {
                $newName = $file.FullName -replace '\.disabled$', ''
                Move-Item $file.FullName $newName -Force
                $enabledFiles++
                $enabledList += $fileName
                Write-Host "  HABILITADO (Base Pura): $fileName" -ForegroundColor Green
            }
            catch {
                Write-Host "  ERRO ao habilitar $fileName : $($_.Exception.Message)" -ForegroundColor Red
                $ignoredFiles++
                $ignoredList += "$fileName (erro de arquivo)"
            }
        }
        else {
            # Arquivo nao seguro - determinar motivo especifico
            $reasons = @()
            if ($hasInternalImports) { $reasons += "imports internos" }
            if ($hasExtends) { $reasons += "heranca (extends)" }
            if ($hasImplements) { $reasons += "interfaces (implements)" }
            
            $reasonText = $reasons -join ", "
            $ignoredFiles++
            $ignoredList += "$fileName ($reasonText)"
            Write-Host "  IGNORADO ($reasonText): $fileName" -ForegroundColor Red
        }
    }
    catch {
        Write-Host "  ERRO ao ler arquivo $fileName : $($_.Exception.Message)" -ForegroundColor Red
        $ignoredFiles++
        $ignoredList += "$fileName (erro de leitura)"
    }
    
    Write-Host ""
}

# Relatorio final detalhado
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "RELATORIO FINAL DA DESCOBERTA DE BASE MINIMA" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "Total de arquivos analisados: $totalFiles" -ForegroundColor White
Write-Host "Arquivos habilitados: $enabledFiles" -ForegroundColor Green
Write-Host "Arquivos ignorados: $ignoredFiles" -ForegroundColor Red
Write-Host ""

if ($enabledFiles -gt 0) {
    Write-Host "ARQUIVOS HABILITADOS:" -ForegroundColor Green
    foreach ($file in $enabledList) {
        Write-Host "  + $file" -ForegroundColor Green
    }
    Write-Host ""
}

if ($ignoredFiles -gt 0) {
    Write-Host "ARQUIVOS IGNORADOS (com motivos):" -ForegroundColor Red
    foreach ($file in $ignoredList) {
        Write-Host "  - $file" -ForegroundColor Red
    }
    Write-Host ""
}

# Validacao com build se algum arquivo foi habilitado
if ($enabledFiles -gt 0) {
    Write-Host "Executando validacao com build..." -ForegroundColor Yellow
    Write-Host ""
    
    try {
        # Executar gradlew build e capturar saida
        $buildResult = & .\gradlew build 2>&1 | Out-String
        
        if ($LASTEXITCODE -eq 0 -and $buildResult -match "BUILD SUCCESSFUL") {
            Write-Host "BUILD SUCCESSFUL! Base minima estavel estabelecida." -ForegroundColor Green
            Write-Host "Total de $enabledFiles arquivos seguros habilitados com sucesso." -ForegroundColor Green
        }
        else {
            Write-Host "BUILD FALHOU! Verifique os erros de compilacao." -ForegroundColor Red
            Write-Host "Pode ser necessario revisar as regras de descoberta ou o ambiente." -ForegroundColor Red
        }
    }
    catch {
        Write-Host "ERRO CRITICO ao executar o gradle: $($_.Exception.Message)" -ForegroundColor Red
    }
}
else {
    Write-Host "Nenhum arquivo foi habilitado. Verifique se:" -ForegroundColor Yellow
    Write-Host "1. Os arquivos estao realmente no formato .java.disabled" -ForegroundColor Yellow
    Write-Host "2. Existem arquivos que atendem aos criterios de base minima" -ForegroundColor Yellow
    Write-Host "3. As regras de verificacao nao estao muito restritivas" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Descoberta e habilitacao de base minima concluida." -ForegroundColor Cyan
