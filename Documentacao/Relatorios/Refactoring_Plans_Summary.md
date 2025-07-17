# Relatório de Planos de Refatoração

Este relatório sumariza os planos de refatoração detalhados gerados para os principais sistemas do projeto, com base na análise de TODOs e placeholders existentes. Cada plano visa modernizar o código, melhorar a compatibilidade com o NeoForge 1.21.1 e reativar funcionalidades desabilitadas.

## Planos de Refatoração Gerados:

1.  **Migração para Data Components**
    *   **Objetivo:** Migrar todas as funcionalidades que atualmente dependem de NBT para o novo sistema de Data Components do NeoForge 1.21.1.
    *   **Foco:** `AmmoBoxItemDataAccessor`, `GunItemDataAccessor`, `Serializers`, `ClientAssetsManager`, `RawGunTableResult`, `GunSmithTableResultSerializer`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Data_Components.md`

2.  **Integração com KubeJS**
    *   **Objetivo:** Reativar e integrar adequadamente a funcionalidade KubeJS no projeto.
    *   **Foco:** `BeforeRenderHandEvent`, `RenderItemInHandBobEvent`, `RenderLevelBobEvent`, `SwapItemWithOffHand`, `EntityKillByGunEvent`, `KubeJSGunEventPoster`, `ClientAssetsManager`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_KubeJS_Integration.md`

3.  **Sistema de Animação**
    *   **Objetivo:** Refatorar e reativar o sistema de animação, incluindo o suporte a animações GLTF e Bedrock.
    *   **Foco:** `AnimationPlan`, `AnimationModel`, `Spline`, `AnimationConstant`, `AnimationState`, `ClientAssetsManager`, `GltfManager`, `BedrockGunModel`, `RefitTransform`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Animation_System.md`

4.  **Sistemas de Compatibilidade**
    *   **Objetivo:** Refatorar e garantir a integração completa e funcional com mods externos como Cloth Config, Controllable e PlayerAnimator.
    *   **Foco:** `ClothConfigScreen`, `MenuIntegration`, `ControllableCompat`, `GunModSubtype`, `PlayerAnimatorCompat`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Compatibility_Systems.md`

5.  **Sistema de Renderização e Modelos**
    *   **Objetivo:** Refatorar o sistema de renderização e modelos para garantir o carregamento, processamento e exibição corretos de modelos 3D, texturas e efeitos visuais.
    *   **Foco:** `BedrockAttachmentModel`, `BedrockGunModel`, `AttachmentRender`, `BeamRenderer`, `TextShowRender`, `ClientAttachmentIndex`, `ClientAttachmentSkinIndex`, `LaserColorUtil`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Rendering_Model_System.md`

6.  **Sistemas de Blocos, Itens e Entidades**
    *   **Objetivo:** Reativar e migrar as classes de blocos, itens e entidades para garantir que todos os elementos do jogo funcionem corretamente.
    *   **Foco:** `ModBlocks`, `ModCreativeTabs`, `ModEntities`, `ModItems`, `ModRecipe`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Blocks_Items_Entities_System.md`

7.  **Sistema de Propriedades e Modificadores de Anexos**
    *   **Objetivo:** Refatorar e habilitar completamente o sistema de propriedades e modificadores de anexos.
    *   **Foco:** `AttachmentPropertyEvent`, `IAttachmentModifier`, `ParameterizedCache`, `ShooterDataHolder`, `AttachmentPropertyManager`, `DamageModifier`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Attachment_Properties_Modifiers_System.md`

8.  **Sistemas de Rede e Eventos Gerais**
    *   **Objetivo:** Refatorar e habilitar completamente os sistemas de rede e eventos gerais.
    *   **Foco:** `ClientResourceReloader`, `AmmoParticleSpawner`, `RootCommand`, `VersionChecker`, `AttachmentPropertyManager`, `SoundManager`, `ExplodeUtil`, `ProjectileExplosion`.
    *   **Arquivo:** `Documentacao/Planejamento/Refatoracao_Network_General_Events_System.md`

Cada um desses planos detalha os TODOs específicos, as localizações afetadas e um plano de ação em fases para guiar a implementação. A execução desses planos garantirá a modernização e a funcionalidade completa do mod no NeoForge 1.21.1.
