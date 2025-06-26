Write-Host "=== RESTAURANDO ARQUIVOS ===" -ForegroundColor Yellow
$restored = 0
Get-ChildItem -Recurse -Filter "*.disabled" | ForEach-Object {
    $original = $_.FullName -replace "\.disabled$", ""
    try {
        Move-Item $_.FullName $original -Force
        $restored++
        Write-Host "OK: $(Split-Path $original -Leaf)" -ForegroundColor Green
    }
    catch {
        Write-Host "ERRO: $(Split-Path $original -Leaf)" -ForegroundColor Red
    }
}
Write-Host "Restaurados: $restored arquivos" -ForegroundColor Yellow
