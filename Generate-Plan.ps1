# =============================================================================
# Generate-Plan.ps1 - Gerador de Plano de Migracao TacZ v4.3
# =============================================================================
# v4.3: Corrigida a numeracao das fases no markdown gerado.
# v4.2: Substituidos emojis por marcadores de texto simples.
# =============================================================================

param(
    [string]$OutputFileName = "PLANO_MIGRACAO.md"
)

Write-Host "=== GERADOR DE PLANO DE MIGRACAO TacZ v4.3 ===" -ForegroundColor Cyan

# --- VERIFICACAO INICIAL DO AMBIENTE ---
$projectRoot = $PSScriptRoot
$buildFile = Join-Path $projectRoot "build.gradle"
$srcPath = Join-Path $projectRoot "src\main\java\com\tacz\guns"

if (-not (Test-Path $buildFile)) { Write-Host "ERRO: build.gradle nao encontrado." -ForegroundColor Red; exit 1 }
if (-not (Test-Path $srcPath)) { Write-Host "ERRO: Pasta source nao encontrada: $srcPath" -ForegroundColor Red; exit 1 }

# --- O código das Fases 0, 1, 2 e 3 (identificacao e analise) nao muda e foi omitido por brevidade ---
# FASE 0
$enabledFiles = Get-ChildItem -Path $srcPath -Recurse -Filter "*.java"; $phase0Files = @(); $phase0ClassMap = @{}
$packageRegex = [regex]'package\s+([a-zA-Z0-9_.]+)\s*;'
$classRegex = [regex]'(?:public\s+)?(?:final\s+)?(?:abstract\s+)?(?:static\s+)?(class|interface|enum|record)\s+([a-zA-Z0-9_]+)'
foreach ($file in $enabledFiles) { try { $content = Get-Content $file.FullName -Raw -Encoding UTF8; if (-not $content -or $file.Name -eq "package-info.java") { continue }; $packageMatch = $packageRegex.Match($content); $classMatch = $classRegex.Match($content); if ($packageMatch.Success -and $classMatch.Success) { $packageName = $packageMatch.Groups[1].Value.Trim(); $className = $classMatch.Groups[2].Value.Trim(); $qualifiedName = "$packageName.$className"; $fileInfo = [PSCustomObject]@{ Path = $file.FullName; FileName = $file.Name; ClassName = $className; PackageName = $packageName; QualifiedName = $qualifiedName; Phase = 0; DependencyCount = 0 }; $phase0Files += $fileInfo; $phase0ClassMap[$qualifiedName] = $fileInfo; } } catch {} }
# FASE 1
$disabledFiles = Get-ChildItem -Path $srcPath -Recurse -Filter "*.java.disabled"; $allDisabledClasses = @{}; $disabledFileInfo = @{}
foreach ($file in $disabledFiles) { try { $content = Get-Content $file.FullName -Raw -Encoding UTF8; if (-not $content -or $file.Name -eq "package-info.java.disabled") { continue }; $packageMatch = $packageRegex.Match($content); $classMatch = $classRegex.Match($content); if ($packageMatch.Success -and $classMatch.Success) { $packageName = $packageMatch.Groups[1].Value.Trim(); $className = $classMatch.Groups[2].Value.Trim(); $qualifiedName = "$packageName.$className"; $fileInfo = [PSCustomObject]@{ Path = $file.FullName; FileName = $file.Name -replace '\.disabled$', ''; ClassName = $className; PackageName = $packageName; QualifiedName = $qualifiedName; Dependencies = @(); Phase = -1; DependencyCount = 0 }; $disabledFileInfo[$file.FullName] = $fileInfo; $allDisabledClasses[$qualifiedName] = $fileInfo; } } catch {} }
$allClassMap = $phase0ClassMap.Clone(); foreach ($kvp in $allDisabledClasses.GetEnumerator()) { $allClassMap[$kvp.Key] = $kvp.Value }
# FASE 2
foreach ($fileInfo in $disabledFileInfo.Values) { try { $content = Get-Content $fileInfo.Path -Raw -Encoding UTF8; if (-not $content) { continue }; foreach ($potentialDep in $allClassMap.Values) { if ($potentialDep.QualifiedName -eq $fileInfo.QualifiedName) { continue }; $classUsageRegex = [regex]"\b$([regex]::Escape($potentialDep.ClassName))\b"; if ($content -match $classUsageRegex) { $isSamePackage = $potentialDep.PackageName -eq $fileInfo.PackageName; $isImported = $content -match "import\s+$([regex]::Escape($potentialDep.QualifiedName))\s*;"; if (($isSamePackage -or $isImported) -and ($fileInfo.Dependencies -notcontains $potentialDep.QualifiedName)) { $fileInfo.Dependencies += $potentialDep.QualifiedName; } } }; $fileInfo.DependencyCount = $fileInfo.Dependencies.Count; } catch {} }
# FASE 3
$resolvedClasses = [System.Collections.Generic.HashSet[string]]::new([string[]]$phase0ClassMap.Keys, [System.StringComparer]::OrdinalIgnoreCase); $remainingFiles = [System.Collections.Generic.List[object]]::new([object[]]$disabledFileInfo.Values); $unresolvedFiles = @(); $allPhases = @{}; $currentPhase = 1
while ($remainingFiles.Count -gt 0) { $filesForThisPhase = @(); for ($i = $remainingFiles.Count - 1; $i -ge 0; $i--) { $fileInfo = $remainingFiles[$i]; $allDependenciesMet = $true; foreach ($depName in $fileInfo.Dependencies) { if (-not $resolvedClasses.Contains($depName)) { $allDependenciesMet = $false; break } }; if ($allDependenciesMet) { $fileInfo.Phase = $currentPhase; $filesForThisPhase += $fileInfo; $remainingFiles.RemoveAt($i) } }; if ($filesForThisPhase.Count -eq 0) { $unresolvedFiles = $remainingFiles.ToArray(); break }; $allPhases[$currentPhase] = $filesForThisPhase | Sort-Object DependencyCount, FileName; foreach ($fileInfo in $filesForThisPhase) { [void]$resolvedClasses.Add($fileInfo.QualifiedName) }; $currentPhase++ }

# =============================================================================
# FASE 4 - GERACAO DO ARQUIVO MARKDOWN
# =============================================================================
Write-Host "[FASE 4/4] Gerando arquivo de plano '$OutputFileName'..." -ForegroundColor Green
$totalDisabled = $disabledFiles.Count; $totalEnabled = $phase0Files.Count; $totalFiles = $totalDisabled + $totalEnabled
$mdContent = @"
# PLANO DE MIGRACAO SISTEMATICA - TacZ NeoForge 1.21.1 (v4.3)

**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1
**Estrategia:** Habilitacao incremental baseada em ordenacao topologica de dependencias
**Data de Geracao:** $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')
**Script:** Generate-Plan.ps1 v4.3

---

## RESUMO ESTATISTICO

| Fase | Descricao | Arquivos | Status |
|------|-----------|----------|--------|
| **Fases 0 e 1** | Fundacao (ja habilitada) | $totalEnabled | [OK] $totalEnabled/$totalEnabled |
"@
$displayPhaseNum = 2
foreach($phaseNumKey in $allPhases.Keys | Sort-Object) {
    $phaseFileCount = $allPhases[$phaseNumKey].Count
    $mdContent += "`n| **Fase $displayPhaseNum** | Proxima camada de habilitacao | $phaseFileCount | [Pendente] 0/$phaseFileCount |"
    $displayPhaseNum++
}
if ($unresolvedFiles.Count -gt 0) {
    $mdContent += "`n| **N/A** | Nao Resolvidos (Circular/Ausente) | $($unresolvedFiles.Count) | [ERRO] 0/$($unresolvedFiles.Count) |"
}
$mdContent += "`n| **TOTAL** | **Todos os arquivos** | **$totalFiles** | **$totalEnabled/$totalFiles** |"
$mdContent += @"

---

## PLANO DE EXECUCAO ORDENADO

### **FASES 0 e 1: FUNDACAO (Ja Habilitada)** [OK]

*Esta e a base solida ja estabelecida. Estes arquivos compilam sem erros e servem como fundacao para as proximas fases.*

"@
foreach ($file in ($phase0Files | Sort-Object FileName)) { $mdContent += "`n- [x] $($file.FileName) (Fundacao)" }
$displayPhaseNum = 2
foreach($phaseNumKey in $allPhases.Keys | Sort-Object) {
    $phaseFiles = $allPhases[$phaseNumKey]
    $phaseLabel = "FASE $displayPhaseNum"
    $mdContent += @"

---

### **$($phaseLabel.ToUpper()): PROXIMA CAMADA DE HABILITACAO** ($($phaseFiles.Count) arquivos)
*Arquivos cujas dependencias foram resolvidas nas fases anteriores. Esta e a proxima fase a ser trabalhada.*

"@
    foreach ($file in $phaseFiles) { $mdContent += "`n- [ ] $($file.FileName) (Deps: $($file.DependencyCount))" }
    $displayPhaseNum++
}
if ($unresolvedFiles.Count -gt 0) {
    $mdContent += @"

---

### **ARQUIVOS NAO RESOLVIDOS** [ERRO]
*Estes arquivos nao puderam ser classificados. Eles provavelmente possuem dependencias circulares entre si ou dependem de uma classe ausente/nao mapeada.*

"@
    foreach ($file in ($unresolvedFiles | Sort-Object FileName)) {
        $mdContent += "`n- [ ] **$($file.FileName)** (Deps: $($file.DependencyCount))"
        foreach($depName in $file.Dependencies) { if (-not $resolvedClasses.Contains($depName)) { $mdContent += "`n  - *Depende de: $depName (NAO RESOLVIDO)*" } }
    }
}
$mdContent += "`n`n---`n*Plano gerado automaticamente em $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') pelo Generate-Plan.ps1 v4.3*"
try {
    Set-Content -Path (Join-Path $projectRoot $OutputFileName) -Value $mdContent -Encoding UTF8
    Write-Host "Plano de migracao salvo com sucesso em: $OutputFileName" -ForegroundColor Green
} catch {
    Write-Host "ERRO ao salvar arquivo: $($_.Exception.Message)" -ForegroundColor Red
}