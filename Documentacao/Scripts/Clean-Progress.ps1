# Script para Limpar Progress-Enhanced.md
param(
    [string]$InputPath = "progress-enhanced.md",
    [string]$OutputPath = "progress-final.md"
)

Write-Host "Limpando progress-enhanced.md..." -ForegroundColor Green

# Ler conteudo
$content = Get-Content $InputPath -Encoding UTF8
$cleanContent = @()

foreach ($line in $content) {
    # Remover "(PRECISA ATUALIZAR)" - se refere ao PLANO_MIGRACAO.md
    $cleanLine = $line -replace ' \(PRECISA ATUALIZAR\)', ''
    $cleanContent += $cleanLine
}

# Salvar versao limpa
$cleanContent | Out-File -FilePath $OutputPath -Encoding UTF8

Write-Host "Arquivo limpo salvo em: $OutputPath" -ForegroundColor Green

# Contar quantas anotacoes foram removidas
$removedCount = 0
foreach ($line in $content) {
    if ($line -match 'PRECISA ATUALIZAR') {
        $removedCount++
    }
}

Write-Host "Anotacoes '(PRECISA ATUALIZAR)' removidas: $removedCount" -ForegroundColor Yellow

# Verificar se todos os arquivos com implementacao minima tem descricao
$minimalWithDesc = 0
$minimalWithoutDesc = 0

foreach ($line in $cleanContent) {
    if ($line -match 'MINIMA:') {
        $minimalWithDesc++
    } elseif ($line -match '- \[x\] \*\*(.+\.java)\*\* - HABILITADO$') {
        # Arquivo habilitado sem descricao - verificar se deveria ter
        $fileName = $matches[1]
        Write-Host "Arquivo sem descricao: $fileName" -ForegroundColor Yellow
        $minimalWithoutDesc++
    }
}

Write-Host ""
Write-Host "VERIFICACAO DE DESCRICOES:" -ForegroundColor Green
Write-Host "  Arquivos com 'MINIMA:': $minimalWithDesc"
Write-Host "  Arquivos habilitados sem descricao: $minimalWithoutDesc"
