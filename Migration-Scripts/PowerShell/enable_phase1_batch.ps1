# Script para habilitar todos os arquivos da Fase 1 em lote
# Lista dos próximos arquivos da Fase 1 (próximos 20)

$arquivos = @(
    "KeepingItemRenderer.java",
    "ResourceManager.java", 
    "Md5Utils.java",
    "MeleeData.java",
    "GunFireSelectEvent.java",
    "InteractKeyConfigRead.java",
    "GunAnimationConstant.java",
    "DefaultTableItem.java",
    "FaceUVsItem.java",
    "DefaultAnimationType.java",
    "GunItemManager.java",
    "ShellDisplay.java",
    "GunFinishReloadEvent.java",
    "Align.java",
    "DebugCommand.java",
    "Ignite.java",
    "PackInfo.java",
    "IFunctionalRenderer.java",
    "BlockItemTooltip.java",
    "JsonProperty.java"
)

Set-Location "c:\Users\mathe\OneDrive\Área de Trabalho\Modding\Astrea_Arsenal"

foreach ($arquivo in $arquivos) {
    Write-Host "Procurando $arquivo..."
    
    # Encontrar o arquivo .disabled
    $encontrados = Get-ChildItem -Recurse -Name "*$arquivo.disabled"
    
    if ($encontrados.Count -gt 0) {
        foreach ($caminho in $encontrados) {
            $origem = $caminho
            $destino = $caminho -replace '\.disabled$', ''
            
            Write-Host "Habilitando: $origem -> $destino"
            Move-Item $origem $destino
        }
    } else {
        Write-Host "Arquivo $arquivo não encontrado ou já habilitado"
    }
}

Write-Host "Processo concluído!"
