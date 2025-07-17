# Plano de Refatoração: Sistema de Renderização e Modelos

## Objetivo
Refatorar o sistema de renderização e modelos para garantir o carregamento, processamento e exibição corretos de modelos 3D (incluindo modelos Bedrock e GLTF), texturas e efeitos visuais, como feixes de laser e buracos de bala.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados ao sistema de renderização e modelos:

*   **`com\tacz\guns\client\model\BedrockAttachmentModel.java`**:
    *   `// import com.tacz.guns.client.model.functional.TextShowRender; // TODO: Restaurar quando TextShowRender aceitar Object`
    *   `* TODO: Expandir funcionalidades quando dependências estiverem completamente estáveis:`
    *   `private @Nullable Object currentGunItem; // TODO: ItemStack quando import estiver funcionando`
    *   `private @Nullable Object attachmentItem; // TODO: ItemStack quando import estiver funcionando`
    *   `* TODO: Restaurar quando TextShowRender aceitar Object em vez de ItemStack`
    *   `* Método principal de renderização com implementação mínima estratégica`
    *   `* TODO: Restaurar tipos específicos quando imports estiverem funcionando:`
*   **`com\tacz\guns\client\model\BedrockGunModel.java`**:
    *   `// this.setFunctionalRenderer(LEFTHAND_POS_NODE, bedrockPart -> new LeftHandRender(this)); // TODO: Habilitar quando LeftHandRender estiver funcionando`
    *   `// this.setFunctionalRenderer(RIGHTHAND_POS_NODE, bedrockPart -> new RightHandRender(this)); // TODO: Habilitar quando RightHandRender estiver funcionando`
    *   `// TODO: Implementar quando ClientAttachmentIndex estiver completo` (múltiplas ocorrências)
    *   `// BeamRenderer.renderLaserBeam(gunItem, matrixStack, transformType, laserBeamPaths); // TODO: Implementar quando BeamRenderer estiver completo`
*   **`com\tacz\guns\client\model\functional\AttachmentRender.java`**:
    *   `* TODO: Expandir quando PoseStack, VertexConsumer, BedrockAttachmentModel e outras dependências estiverem disponíveis`
    *   `// TODO: usar modelo LOD`
    *   `// TODO: renderizar modelo`
*   **`com\tacz\guns\client\model\functional\BeamRenderer.java`**:
    *   `* TODO: Expandir quando ItemStack, PoseStack, ItemDisplayContext, LaserConfig e outras dependências estiverem disponíveis`
    *   `* Método utilitário para validar parâmetros de feixe laser`
    *   `* Método utilitário para obter intensidade do laser baseado na distância`
    *   `* Método utilitário para calcular cor com fade baseado na distância`
*   **`com\tacz\guns\client\model\functional\TextShowRender.java`**:
    *   `// TODO: Restaurar quando imports estiverem funcionando`
*   **`com\tacz\guns\client\resource\index\ClientAttachmentIndex.java`**:
    *   `* TODO: Expandir quando BedrockAttachmentModel estiver disponível`
    *   `private Object model; // TODO: BedrockAttachmentModel quando disponível`
    *   `private Object texture; // TODO: ResourceLocation quando import estiver funcionando`
    *   `private Object slotTextureLocation; // TODO: ResourceLocation quando import estiver funcionando`
    *   `public Object getModel() { // TODO: retornar BedrockAttachmentModel quando disponível`
    *   `public Object getTexture() { // TODO: retornar ResourceLocation quando import estiver funcionando`
    *   `public Object getSlotTextureLocation() { // TODO: retornar ResourceLocation quando import estiver funcionando`
    *   `* TODO: Retornar BedrockAttachmentModel tipado quando imports estiverem estáveis`
    *   `* TODO: Retornar ResourceLocation quando disponível` (múltiplas ocorrências)
    *   `* TODO: Retornar Pair<BedrockAttachmentModel, ResourceLocation> quando disponível`
    *   `* TODO: Expandir quando BedrockModelPOJO e dependências estiverem disponíveis`
    *   `* Método estático placeholder para carregar modelo de acessório`
    *   `* TODO: Implementar corretamente quando BedrockAttachmentModel estiver disponível`
*   **`com\tacz\guns\client\resource\index\ClientAttachmentSkinIndex.java`**:
    *   `private Object model; // TODO: BedrockAttachmentModel quando disponível`
*   **`com\tacz\guns\util\LaserColorUtil.java`**:
    *   `* TODO: Expandir quando GunDisplayInstance e ClientAttachmentIndex estiverem completos`
    *   `* Método utilitário para obter cor padrão de laser`
    *   `* Método utilitário para validar cor de laser`

## Plano de Ação

### Fase 1: Modelos e Texturas
1.  **Habilitar `TextShowRender`:** Modificar `TextShowRender.java` para aceitar `Object` e `ItemStack` conforme os TODOs, garantindo que os imports estejam funcionando.
2.  **Tipagem de Modelos e Texturas:** Em `ClientAttachmentIndex.java` e `ClientAttachmentSkinIndex.java`, substituir `Object` por `BedrockAttachmentModel` e `ResourceLocation` onde apropriado, resolvendo os TODOs de tipagem e imports.
3.  **Carregamento de Modelos de Acessórios:** Implementar a lógica correta para carregar `BedrockAttachmentModel` em `ClientAttachmentIndex.java`, substituindo o método placeholder.

### Fase 2: Renderizadores Funcionais e Efeitos Visuais
1.  **Habilitar Renderizadores de Mão:** Em `BedrockGunModel.java`, habilitar e corrigir `LeftHandRender` e `RightHandRender`.
2.  **Completar `ClientAttachmentIndex`:** Finalizar a implementação de `ClientAttachmentIndex` para que ele forneça todos os dados necessários para a renderização de anexos.
3.  **Implementar `BeamRenderer`:** Desenvolver a funcionalidade completa de `BeamRenderer.java` para renderizar feixes de laser, incluindo a validação de parâmetros, obtenção de intensidade e cálculo de cor com fade.
4.  **Refatorar `AttachmentRender`:** Em `AttachmentRender.java`, implementar o uso de modelos LOD e a lógica de renderização de modelos.
5.  **Expandir `LaserColorUtil`:** Completar `LaserColorUtil.java` para usar `GunDisplayInstance` e `ClientAttachmentIndex` para obter e validar cores de laser.

### Fase 3: Testes e Otimização
1.  **Testes Visuais:** Realizar testes em jogo para verificar a renderização correta de todas as armas, anexos, texturas e efeitos visuais.
2.  **Testes de Performance:** Monitorar o desempenho da renderização e otimizar o código, se necessário.
3.  **Limpeza:** Remover quaisquer comentários TODOs e código obsoleto após a conclusão da refatoração.

## Considerações Adicionais
*   **Ferramentas de Modelagem:** Utilizar ferramentas de modelagem 3D para verificar a integridade dos modelos Bedrock e GLTF.
*   **Documentação:** Atualizar a documentação sobre a estrutura de modelos, texturas e como adicionar novos ativos visuais.
