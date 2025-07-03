# ===================================================================
# Analyze-Phase2-Dependencies.ps1 - Analisa dependências da Fase 2
# ===================================================================
# Este script analisa arquivos .java.disabled para determinar quais
# pertencem verdadeiramente à Fase 2 (dependem apenas das Fases 0 e 1)
# e gera um arquivo .md separado para planejamento
# ===================================================================

Write-Host "=== ANALISANDO DEPENDENCIAS DA FASE 2 ===" -ForegroundColor Cyan

# Verificacao inicial do ambiente
$baseDir = "src\main\java\com\tacz\guns"
$outputFile = "FASE_2_PLANEJAMENTO_FINAL.md"

# Verificar se o diretorio base existe
if (-not (Test-Path $baseDir)) {
    Write-Host "ERRO: Diretorio base nao encontrado: $baseDir" -ForegroundColor Red
    exit 1
}

Write-Host "Analisando arquivos em: $baseDir" -ForegroundColor Yellow
Write-Host "Gerando planejamento em: $outputFile" -ForegroundColor Yellow
Write-Host ""

# Listas conhecidas das Fases 0 e 1 (classes que estão habilitadas e funcionais)
$phase0And1Classes = @(
    # Fase 0 - Fundação
    "ModSounds", "ModItems", "ModBlocks", "ModEntities", "ModRecipeTypes", "ModCreativeTabs",
    "ModDataComponents", "CommonConfig", "ClientConfig", "ServerConfig", "SyncConfig", "PreLoadModConfig",
    "TacPathVisitor", "ResourceScanner", "HeadShotAABBConfigRead", "SyncedClassKey",
    "GunPackProgressScreen", "RenderHelper", "FlatColorButton", "OpenGunPackDirEntry",
    "GunSmithTableIngredientSerializer", "HitboxHelper", "ConfigCommand",
    
    # Fase 1 - Arquivos Restantes (todos os que estão marcados como ✅)
    "AccessorSparseIndices", "AccessorSparseValues", "AmmoBoxTooltip", "AmmoParticle", 
    "AnimationChannelTarget", "AnimationSampler", "BedrockVertex", "BlockItemTooltip",
    "Buffer", "Buffers", "BufferView", "CommonTransformObject", "DiscreteTrackArray",
    "FaceItem", "FireMode", "GunLevelUpToast", "GunRecoilKeyFrame", "IDisplay",
    "LayerGunShow", "LoginIndexHolder", "MathUtil", "Md5Utils", "MoveSpeed",
    "Node", "NodeModel", "PairSerializer", "PerlinNoise", "PlayerNamePapi",
    "ReloadState", "ShellEjection", "TimelessItemNbtFactory", "TransformScale",
    "Vec3Serializer", "Vector3fSerializer", "AmmoClothConfig", "AttachmentIndexPOJO",
    "AttachmentItemTooltip", "BedrockPart", "CommonAmmoIndex", "DistanceDamagePairSerializer",
    "GunClothConfig", "GunResult", "IAttachment", "IgniteSerializer", "INetworkCacheReloadListener",
    "Interpolator", "KnockbackChange", "LiteralFilter", "LivingEntityAmmoCheck",
    "RegexFilter", "SoundEffectKeyframesSerializer", "TextShow", "ThirdPersonManager",
    "ZoomClothConfig", "AttachmentData", "GunReloadData", "BulletData",
    
    # Classes padrão do Minecraft/Java (sempre permitidas)
    "Component", "ResourceLocation", "ItemStack", "Block", "Item", "Entity", "Player", "Level", "BlockPos"
)

# Contadores para relatorio
$totalFiles = 0
$phase2ValidFiles = @()
$dependsOnLaterPhases = @()
$analysisResults = @()

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
    $className = $fileName.Replace(".java", "")
    $relativePath = $file.FullName.Replace((Get-Location).Path, "").TrimStart('\')
    
    Write-Host "Analisando: $fileName" -ForegroundColor White
    
    try {
        # Ler o conteudo completo do arquivo
        $content = Get-Content $file.FullName -Raw -Encoding UTF8
        
        # Extrair todos os imports internos (com.tacz.guns)
        $internalImports = @()
        $importMatches = [regex]::Matches($content, 'import com\.tacz\.guns\.([^;]+);')
        foreach ($match in $importMatches) {
            $importPath = $match.Groups[1].Value
            $importedClass = ($importPath -split '\.')[-1]
            $internalImports += $importedClass
        }
        
        # Extrair referencias a classes internas no código (new ClassName, ClassName.method, etc.)
        $classReferences = @()
        # Buscar patterns como: new ClassName, ClassName.method, extends ClassName, implements ClassName
        $referencePatterns = @(
            'new\s+([A-Z][a-zA-Z0-9]+)',
            '([A-Z][a-zA-Z0-9]+)\.',
            'extends\s+([A-Z][a-zA-Z0-9]+)',
            'implements\s+([A-Z][a-zA-Z0-9]+)'
        )
        
        foreach ($pattern in $referencePatterns) {
            $matches = [regex]::Matches($content, $pattern)
            foreach ($match in $matches) {
                $classRef = $match.Groups[1].Value
                # Filtrar classes obviamente do Java/Minecraft
                if ($classRef -notmatch '^(String|List|Map|Set|Array|Optional|UUID|JsonElement|ResourceLocation|ItemStack|Block|Item|Entity|Player|Level|BlockPos|Component|Codec|Registry)') {
                    $classReferences += $classRef
                }
            }
        }
        
        # Combinar imports e referencias
        $allDependencies = ($internalImports + $classReferences) | Sort-Object -Unique
        
        # Filtrar dependencias que NAO estao na Fase 0 ou 1
        $unknownDependencies = @()
        foreach ($dep in $allDependencies) {
            if ($dep -notin $phase0And1Classes) {
                $unknownDependencies += $dep
            }
        }
        
        # Determinar categoria do arquivo
        $category = ""
        $priority = 0
        
        if ($unknownDependencies.Count -eq 0) {
            $category = "FASE_2_READY"
            $priority = 1
            $phase2ValidFiles += @{
                Name = $className
                File = $fileName
                Path = $relativePath
                Dependencies = $allDependencies
                Category = "Pronto para Fase 2"
            }
            Write-Host "  FASE 2 READY: $fileName (sem dependencias desconhecidas)" -ForegroundColor Green
        }
        elseif ($unknownDependencies.Count -le 3) {
            $category = "FASE_2_MAYBE"
            $priority = 2
            $phase2ValidFiles += @{
                Name = $className
                File = $fileName
                Path = $relativePath
                Dependencies = $allDependencies
                UnknownDeps = $unknownDependencies
                Category = "Possivelmente Fase 2"
            }
            Write-Host "  FASE 2 MAYBE: $fileName (depende de: $($unknownDependencies -join ', '))" -ForegroundColor Yellow
        }
        else {
            $category = "LATER_PHASE"
            $priority = 3
            $dependsOnLaterPhases += @{
                Name = $className
                File = $fileName
                Path = $relativePath
                Dependencies = $allDependencies
                UnknownDeps = $unknownDependencies
                Category = "Fase Posterior"
            }
            Write-Host "  LATER PHASE: $fileName (muitas dependencias: $($unknownDependencies.Count))" -ForegroundColor Red
        }
        
        $analysisResults += @{
            Name = $className
            File = $fileName
            Path = $relativePath
            Category = $category
            Priority = $priority
            TotalDeps = $allDependencies.Count
            UnknownDeps = $unknownDependencies.Count
            Dependencies = $allDependencies
            UnknownDependencies = $unknownDependencies
        }
    }
    catch {
        Write-Host "  ERRO ao analisar $fileName : $($_.Exception.Message)" -ForegroundColor Red
    }
}

# Gerar arquivo de planejamento
Write-Host ""
Write-Host "Gerando arquivo de planejamento: $outputFile" -ForegroundColor Cyan

$mdContent = @"
# FASE 2 - PLANEJAMENTO FINAL
*Gerado automaticamente em $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")*

## 📊 RESUMO DA ANÁLISE

- **Total de arquivos analisados:** $totalFiles
- **Arquivos prontos para Fase 2:** $($phase2ValidFiles.Count)
- **Arquivos que dependem de fases posteriores:** $($dependsOnLaterPhases.Count)

## 🎯 ESTRATÉGIA DA FASE 2

A Fase 2 deve focar APENAS em arquivos que dependem exclusivamente das Fases 0 e 1 (base sólida já estabelecida).

### Critérios de Inclusão na Fase 2:
1. ✅ **Sem dependências desconhecidas:** Arquivos que só dependem de classes já habilitadas
2. ⚠️ **Dependências mínimas:** Arquivos com 1-3 dependências que podem ser resolvidas rapidamente
3. ❌ **Muitas dependências:** Arquivos que dependem de sistemas complexos (mover para fases posteriores)

---

## 📋 FASE 2.1: ARQUIVOS PRONTOS (Dependências Zero)
*Arquivos que podem ser habilitados imediatamente*

"@

# Adicionar arquivos prontos para Fase 2
$readyFiles = $analysisResults | Where-Object { $_.Category -eq "FASE_2_READY" } | Sort-Object Name
foreach ($file in $readyFiles) {
    $mdContent += "`n- [ ] **$($file.Name).java** (Deps: $($file.TotalDeps))"
    if ($file.Dependencies.Count -gt 0) {
        $mdContent += " - Depende de: $($file.Dependencies -join ', ')"
    }
}

$mdContent += @"

---

## 📋 FASE 2.2: ARQUIVOS COM DEPENDÊNCIAS MÍNIMAS
*Arquivos que podem ser migrados com pequenas correções*

"@

# Adicionar arquivos com dependências mínimas
$maybeFiles = $analysisResults | Where-Object { $_.Category -eq "FASE_2_MAYBE" } | Sort-Object UnknownDeps, Name
foreach ($file in $maybeFiles) {
    $mdContent += "`n- [ ] **$($file.Name).java** (Unknown Deps: $($file.UnknownDeps))"
    if ($file.UnknownDependencies.Count -gt 0) {
        $mdContent += " - Depende de: $($file.UnknownDependencies -join ', ')"
    }
}

$mdContent += @"

---

## 🚫 ARQUIVOS MOVIDOS PARA FASES POSTERIORES
*Arquivos que dependem de sistemas complexos ainda não implementados*

"@

# Adicionar arquivos para fases posteriores
$laterFiles = $analysisResults | Where-Object { $_.Category -eq "LATER_PHASE" } | Sort-Object UnknownDeps -Descending | Select-Object -First 20
foreach ($file in $laterFiles) {
    $mdContent += "`n- **$($file.Name).java** (Unknown Deps: $($file.UnknownDeps)) - Principais: $($file.UnknownDependencies[0..4] -join ', ')"
}

if ($laterFiles.Count -gt 20) {
    $mdContent += "`n- *... e mais $(($analysisResults | Where-Object { $_.Category -eq "LATER_PHASE" }).Count - 20) arquivos*"
}

$mdContent += @"

---

## 📈 ESTATÍSTICAS DETALHADAS

### Por Categoria:
- **Fase 2.1 (Prontos):** $($readyFiles.Count) arquivos
- **Fase 2.2 (Dependências Mínimas):** $($maybeFiles.Count) arquivos  
- **Fases Posteriores:** $(($analysisResults | Where-Object { $_.Category -eq "LATER_PHASE" }).Count) arquivos

### Distribuição de Dependências:
"@

# Estatísticas de dependências
$depStats = $analysisResults | Group-Object UnknownDeps | Sort-Object Name
foreach ($stat in $depStats) {
    $mdContent += "`n- **$($stat.Name) dependências desconhecidas:** $($stat.Count) arquivos"
}

$mdContent += @"

---

## 🎯 PRÓXIMOS PASSOS

1. **Revisar Fase 2.1:** Validar que os arquivos "prontos" realmente não têm dependências problemáticas
2. **Analisar Fase 2.2:** Determinar quais dependências mínimas podem ser resolvidas rapidamente
3. **Implementar em ordem:** Começar pelos arquivos com menos dependências
4. **Testar progressivamente:** Compilar após cada grupo de arquivos habilitados
5. **Atualizar planejamento oficial:** Quando estiver satisfeito com este planejamento

---

*Análise completa realizada em $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")*
"@

# Salvar arquivo
$mdContent | Out-File -FilePath $outputFile -Encoding UTF8 -Force

# Relatorio final
Write-Host ""
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "RELATORIO FINAL DA ANALISE DA FASE 2" -ForegroundColor Cyan
Write-Host "===============================================" -ForegroundColor Cyan
Write-Host "Total de arquivos analisados: $totalFiles" -ForegroundColor White
Write-Host "Arquivos prontos para Fase 2: $($readyFiles.Count)" -ForegroundColor Green
Write-Host "Arquivos com dependencias minimas: $($maybeFiles.Count)" -ForegroundColor Yellow
Write-Host "Arquivos para fases posteriores: $(($analysisResults | Where-Object { $_.Category -eq "LATER_PHASE" }).Count)" -ForegroundColor Red
Write-Host ""
Write-Host "Planejamento salvo em: $outputFile" -ForegroundColor Green
Write-Host ""
Write-Host "Revise o arquivo gerado e, quando estiver satisfeito," -ForegroundColor Cyan
Write-Host "poderemos atualizar o planejamento oficial!" -ForegroundColor Cyan
