# =============================================================================
# DEPENDENCY ANALYZER SIMPLE - TacZ NeoForge Migration
# Versão simplificada que apenas mostra estatísticas no terminal
# =============================================================================

param(
    # Sem parâmetros necessários - apenas estatísticas no terminal
)

$ProjectRoot = $PSScriptRoot
$SrcPath = Join-Path $ProjectRoot "src\main\java"

Write-Host "Iniciando análise de dependências..." -ForegroundColor Green
Write-Host "Pasta fonte: $SrcPath" -ForegroundColor Cyan

# Encontrar todos os arquivos .disabled
$DisabledFiles = Get-ChildItem -Path $SrcPath -Recurse -Filter "*.disabled"
Write-Host "Encontrados $($DisabledFiles.Count) arquivos .disabled" -ForegroundColor Yellow

# Lista para armazenar resultados
$Results = @()

foreach ($file in $DisabledFiles) {
    Write-Host "Analisando: $($file.Name)" -ForegroundColor Gray
    
    # Ler conteúdo do arquivo
    $content = Get-Content $file.FullName -Raw -ErrorAction SilentlyContinue
    
    if ($content) {
        # Contar imports que referenciam com.tacz.guns
        $tacImports = ($content | Select-String "import\s+com\.tacz\.guns\." -AllMatches).Matches.Count
        
        # Extrair nome do pacote
        $packageLine = $content | Select-String "package\s+(.*?);" 
        $packageName = if ($packageLine) { $packageLine.Matches[0].Groups[1].Value } else { "N/A" }
        
        # Caminho relativo
        $relativePath = $file.FullName.Replace($ProjectRoot, "").Replace("\", "/")
        
        # Criar objeto resultado
        $result = [PSCustomObject]@{
            FileName = $file.Name -replace '\.disabled$', ''
            FilePath = $relativePath
            PackageName = $packageName
            TotalDependencies = $tacImports
        }
        
        $Results += $result
    }
}

Write-Host "`nAnálise concluída!" -ForegroundColor Green

# Mostrar estatísticas finais
Write-Host "`nESTATÍSTICAS:" -ForegroundColor Green
Write-Host "Fase 1 (0 deps): $(($Results | Where-Object { $_.TotalDependencies -eq 0 }).Count)" -ForegroundColor Green
Write-Host "Fase 2 (1-3 deps): $(($Results | Where-Object { $_.TotalDependencies -ge 1 -and $_.TotalDependencies -le 3 }).Count)" -ForegroundColor Yellow
Write-Host "Fase 3 (4-10 deps): $(($Results | Where-Object { $_.TotalDependencies -ge 4 -and $_.TotalDependencies -le 10 }).Count)" -ForegroundColor Magenta
Write-Host "Fase 4 (11+ deps): $(($Results | Where-Object { $_.TotalDependencies -ge 11 }).Count)" -ForegroundColor Red

Write-Host "`nScript concluído! Nenhum arquivo foi gerado - apenas estatísticas mostradas acima." -ForegroundColor Cyan