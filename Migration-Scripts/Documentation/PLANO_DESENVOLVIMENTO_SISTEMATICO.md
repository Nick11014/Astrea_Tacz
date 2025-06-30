# 🚀 PLANO DE DESENVOLVIMENTO SISTEMÁTICO - TacZ NeoForge 1.21.1

**Projeto:** Migração TacZ de Forge 1.20.1 para NeoForge 1.21.1  
**Status Atual:** Fase 0 concluída - Todos os arquivos desabilitados, build limpo  
**Estratégia:** Habilitação incremental baseada em análise de dependências

---

## 📊 RESUMO ESTATÍSTICO

| Fase | Descrição | Arquivos | Status |
|------|-----------|----------|--------|
| **Fase 1** | Sem dependências | 207 | [ ] 0/207 |
| **Fase 2** | Dependências baixas (1-3) | 221 | [ ] 0/221 |
| **Fase 3** | Dependências médias (4-10) | 132 | [ ] 0/132 |
| **Fase 4** | Dependências altas (11+) | 52 | [ ] 0/52 |
| **TOTAL** | **Todos os arquivos** | **612** | **0/612** |

---

## 🎯 ESTRATÉGIA DE DEPENDÊNCIAS

### Princípios Fundamentais:
1. **Ordem rigorosa:** Arquivo só é habilitado se TODAS suas dependências já estão ativas
2. **Validação contínua:** Build test após cada grupo de 5-10 arquivos
3. **Rollback fácil:** Arquivo problemático volta para `.disabled` imediatamente
4. **Isolamento:** Um problema por vez, sem acúmulo de erros

### Critérios de Progressão:
- ✅ **Fase 1:** Sem dependências - pode habilitar qualquer ordem
- ✅ **Fase 2:** Dependências baixas - ordem específica necessária
- ✅ **Fase 3+:** Dependências complexas - análise cuidadosa

---

## 📋 CHECKLIST DE DESENVOLVIMENTO POR FASES

### **FASE 1: SEM DEPENDÊNCIAS** ✅ (207 arquivos)
*Estes arquivos podem ser habilitados em qualquer ordem pois não dependem de nenhum outro arquivo do mod*

**Status: [ ] 0/207 arquivos habilitados**

#### 1.1 POJOs Fundamentais (4 arquivos)
- [ ] `AmmoIndexPOJO.java` - Estrutura de dados para índice de munições
- [ ] `GunIndexPOJO.java` - Estrutura de dados para índice de armas  
- [ ] `BlockIndexPOJO.java` - Estrutura de dados para índice de blocos
- [ ] `AttachmentIndexPOJO.java` - Estrutura de dados para índice de acessórios

#### 1.2 Utilitários Básicos (12 arquivos)
- [ ] `ColorHex.java` - Conversão de cores hexadecimais
- [ ] `CycleTaskHelper.java` - Helper para tarefas cíclicas
- [ ] `DelayedTask.java` - Sistema de tarefas com delay
- [ ] `Easing.java` - Funções de easing para animações
- [ ] `MathUtil.java` - Utilitários matemáticos
- [ ] `PerlinNoise.java` - Gerador de ruído Perlin
- [ ] `SecondOrderDynamics.java` - Dinâmica de segunda ordem
- [ ] `EntityUtil.java` - Utilitários para entidades
- [ ] `ExplodeUtil.java` - Utilitários de explosão
- [ ] `GetJarResources.java` - Extração de recursos do JAR
- [ ] `Md5Utils.java` - Utilitários MD5
- [ ] `PathHandler.java` - Manipulação de caminhos

#### 1.3 Enums e Constantes (8 arquivos)
- [ ] `FireMode.java` - Modos de disparo das armas
- [ ] `AttachmentType.java` - Tipos de acessórios
- [ ] `ElementType.java` - Tipos de elementos GLTF
- [ ] `FeedType.java` - Tipos de alimentação de munição
- [ ] `DataType.java` - Tipos de dados de rede
- [ ] `InaccuracyType.java` - Tipos de imprecisão
- [ ] `GltfConstants.java` - Constantes GLTF
- [ ] `AnimationConstant.java` - Constantes de animação

#### 1.4 POJOs de Display e Renderização (25 arquivos)
- [ ] `AmmoCountStyle.java` - Estilo de contagem de munição
- [ ] `DamageStyle.java` - Estilo de exibição de dano
- [ ] `DefaultAnimationType.java` - Tipos de animação padrão
- [ ] `Align.java` - Configurações de alinhamento
- [ ] `AmmoDisplay.java` - Display de munição
- [ ] `AmmoEntityDisplay.java` - Display de entidade de munição
- [ ] `AmmoParticle.java` - Partículas de munição
- [ ] `AttachmentDisplay.java` - Display de acessórios
- [ ] `AttachmentLod.java` - Level of detail de acessórios
- [ ] `BlockDisplay.java` - Display de blocos
- [ ] `ControllableData.java` - Dados de controle
- [ ] `GunDisplay.java` - Display de armas
- [ ] `GunLod.java` - Level of detail de armas
- [ ] `GunTransform.java` - Transformações de arma
- [ ] `LayerGunShow.java` - Camada de exibição de arma
- [ ] `MuzzleFlash.java` - Flash do cano
- [ ] `ShellEjection.java` - Ejeção de cápsula
- [ ] `TextShow.java` - Exibição de texto
- [ ] `AmmoTransform.java` - Transformações de munição
- [ ] `ShellDisplay.java` - Display de cápsula
- [ ] `CommonTransformObject.java` - Objeto de transformação comum
- [ ] `PackInfo.java` - Informações do pack
- [ ] `TransformScale.java` - Escala de transformação
- [ ] `LaserConfig.java` - Configuração de laser
- [ ] `IDisplay.java` - Interface de display

#### 1.5 Estruturas de Dados de Armas (15 arquivos)
- [ ] `BulletData.java` - Dados de projétil
- [ ] `BurstData.java` - Dados de rajada
- [ ] `ExplosionData.java` - Dados de explosão
- [ ] `GunHeatData.java` - Dados de aquecimento da arma
- [ ] `FireSound.java` - Sons de disparo
- [ ] `Bolt.java` - Dados do ferrolho
- [ ] `ExtraDamage.java` - Dano extra
- [ ] `GunDefaultMeleeData.java` - Dados de corpo-a-corpo padrão
- [ ] `GunFireModeAdjustData.java` - Ajuste de modo de disparo
- [ ] `GunRecoil.java` - Recuo da arma
- [ ] `GunRecoilKeyFrame.java` - Keyframe de recuo
- [ ] `GunReloadData.java` - Dados de recarga
- [ ] `GunReloadTime.java` - Tempo de recarga
- [ ] `Ignite.java` - Ignição
- [ ] `MoveSpeed.java` - Velocidade de movimento

#### 1.6 Estruturas GLTF e Animação (45 arquivos)
- [ ] `AbstractAccessorData.java` - Dados abstratos de accessor
- [ ] `AccessorByteData.java` - Dados de accessor em bytes
- [ ] `AccessorFloatData.java` - Dados de accessor em float
- [ ] `AccessorIntData.java` - Dados de accessor em int
- [ ] `AccessorShortData.java` - Dados de accessor em short
- [ ] `AccessorData.java` - Dados de accessor
- [ ] `AccessorSparseUtils.java` - Utilitários sparse de accessor
- [ ] `NumberArrays.java` - Arrays numéricos
- [ ] `Accessor.java` - POJO Accessor GLTF
- [ ] `AccessorSparse.java` - Sparse de accessor
- [ ] `AccessorSparseIndices.java` - Índices sparse
- [ ] `AccessorSparseValues.java` - Valores sparse
- [ ] `Animation.java` - Animação GLTF
- [ ] `AnimationChannel.java` - Canal de animação
- [ ] `AnimationChannelTarget.java` - Alvo do canal
- [ ] `AnimationSampler.java` - Sampler de animação
- [ ] `Buffer.java` - Buffer GLTF
- [ ] `BufferView.java` - View do buffer
- [ ] `Node.java` - Nó GLTF
- [ ] `RawAnimationStructure.java` - Estrutura bruta de animação
- [ ] `AnimationModel.java` - Modelo de animação
- [ ] `BufferModel.java` - Modelo de buffer
- [ ] `Buffers.java` - Buffers
- [ ] `BufferViewModel.java` - Modelo de view do buffer
- [ ] `AnimationStructure.java` - Estrutura de animação
- [ ] `AnimationBone.java` - Osso de animação
- [ ] `AnimationKeyframes.java` - Keyframes de animação
- [ ] `BedrockAnimation.java` - Animação Bedrock
- [ ] `BedrockAnimationFile.java` - Arquivo de animação Bedrock
- [ ] `SoundEffectKeyframes.java` - Keyframes de efeito sonoro
- [ ] `AnimationChannelContent.java` - Conteúdo do canal
- [ ] `AnimationController.java` - Controlador de animação
- [ ] `AnimationListener.java` - Listener de animação
- [ ] `AnimationListenerSupplier.java` - Supplier de listener
- [ ] `AnimationPlan.java` - Plano de animação
- [ ] `AnimationSoundChannelContent.java` - Conteúdo sonoro
- [ ] `DiscreteTrackArray.java` - Array de track discreto
- [ ] `ObjectAnimation.java` - Animação de objeto
- [ ] `ObjectAnimationChannel.java` - Canal de animação de objeto
- [ ] `ObjectAnimationRunner.java` - Executor de animação
- [ ] `ObjectAnimationSoundChannel.java` - Canal sonoro de objeto
- [ ] `AnimationState.java` - Estado de animação
- [ ] `AnimationStateContext.java` - Contexto de estado
- [ ] `LuaAnimationState.java` - Estado Lua
- [ ] `TrackArrayMismatchException.java` - Exceção de array

#### 1.7 Event System (8 arquivos)
- [ ] `EntityHurtByGunEvent.java` - Evento de dano por arma
- [ ] `EntityKillByGunEvent.java` - Evento de morte por arma
- [ ] `GunDamageSourcePart.java` - Fonte de dano de arma
- [ ] `GunDrawEvent.java` - Evento de sacar arma
- [ ] `GunFinishReloadEvent.java` - Evento de finalizar recarga
- [ ] `GunFireEvent.java` - Evento de disparo
- [ ] `GunFireSelectEvent.java` - Evento de seleção de modo
- [ ] `GunMeleeEvent.java` - Evento de corpo-a-corpo

#### 1.8 Models e Rendering (20 arquivos)
- [ ] `BedrockModelPOJO.java` - POJO modelo Bedrock
- [ ] `BedrockVersion.java` - Versão Bedrock
- [ ] `BonesItem.java` - Item de ossos
- [ ] `CubesItem.java` - Item de cubos
- [ ] `Description.java` - Descrição
- [ ] `FaceItem.java` - Item de face
- [ ] `FaceUVsItem.java` - UVs de face
- [ ] `GeometryModelLegacy.java` - Modelo geométrico legado
- [ ] `GeometryModelNew.java` - Modelo geométrico novo
- [ ] `BedrockCube.java` - Cubo Bedrock
- [ ] `BedrockCubeBox.java` - Caixa de cubo Bedrock
- [ ] `BedrockCubePerFace.java` - Cubo por face
- [ ] `BedrockModel.java` - Modelo Bedrock
- [ ] `BedrockPart.java` - Parte Bedrock
- [ ] `BedrockPolygon.java` - Polígono Bedrock
- [ ] `BedrockVertex.java` - Vértice Bedrock
- [ ] `ModelRendererWrapper.java` - Wrapper de renderizador
- [ ] `GunModelConstant.java` - Constantes de modelo
- [ ] `SlotModel.java` - Modelo de slot
- [ ] `CrosshairType.java` - Tipo de mira

#### 1.9 Tooltips e Interface (8 arquivos)
- [ ] `AmmoBoxTooltip.java` - Tooltip de caixa de munição
- [ ] `BlockItemTooltip.java` - Tooltip de itens de bloco
- [ ] `FlatColorButton.java` - Botão de cor sólida
- [ ] `GunTooltip.java` - Tooltip de arma
- [ ] `AttachmentItemTooltip.java` - Tooltip de acessório
- [ ] `IComponentTooltip.java` - Interface de tooltip
- [ ] `IStackTooltip.java` - Interface de tooltip de stack
- [ ] `GunLevelUpToast.java` - Toast de level up

#### 1.10 Compatibilidade e Diversos (65+ arquivos restantes)
*Inclui arquivos de compatibilidade, serializers, interpoladores, etc.*
- [ ] `AnimationName.java` - Nomes de animação
- [ ] `AttachmentSkin.java` - Skin de acessório
- [ ] `CacheValue.java` - Valor de cache
- [ ] `DebugCommand.java` - Comando de debug
- [ ] E mais 60+ arquivos...

**Observação:** Fase 1 deve ser habilitada completamente antes de prosseguir para Fase 2.

---

### **FASE 2: DEPENDÊNCIAS BAIXAS** 🔸 (221 arquivos)
*Estes arquivos dependem de 1-3 outros arquivos do mod*

**Status: [ ] 0/221 arquivos habilitados**
**Pré-requisito: ✅ Fase 1 deve estar 100% concluída**

#### 2.1 Sistema de Comandos Básicos (8 arquivos)
- [ ] `AttachmentLockCommand.java` - Comando de trava de acessórios (Deps: 1)
- [ ] `ConfigCommand.java` - Comandos de configuração (Deps: 1)
- [ ] `ConvertCommand.java` - Comando de conversão (Deps: 1)
- [ ] `DummyAmmoCommand.java` - Comando de munição dummy (Deps: 1)
- [ ] `HideTooltipPartCommand.java` - Ocultar partes do tooltip (Deps: 1)
- [ ] `ListPackCommand.java` - Listar packs (Deps: 1)
- [ ] `OverwriteCommand.java` - Comando de sobrescrita (Deps: 1)
- [ ] `ReloadCommand.java` - Comando de reload (Deps: 1)

#### 2.2 Network Messages Básicas (15 arquivos)
- [ ] `ClientMessageCraft.java` - Mensagem de crafting (Deps: 1)
- [ ] `ClientMessagePlayerAim.java` - Mensagem de mira do jogador (Deps: 1)
- [ ] `ClientMessagePlayerBoltGun.java` - Mensagem de ferrolho (Deps: 1)
- [ ] `ClientMessagePlayerCancelReload.java` - Cancelar recarga (Deps: 1)
- [ ] `ClientMessagePlayerDrawGun.java` - Sacar arma (Deps: 1)
- [ ] `ClientMessagePlayerFireSelect.java` - Seleção de modo (Deps: 1)
- [ ] `ClientMessagePlayerMelee.java` - Corpo-a-corpo (Deps: 1)
- [ ] `ClientMessagePlayerReloadGun.java` - Recarga (Deps: 1)
- [ ] `ClientMessagePlayerShoot.java` - Disparo (Deps: 1)
- [ ] `ClientMessagePlayerZoom.java` - Zoom (Deps: 1)
- [ ] `ClientMessageLaserColor.java` - Cor do laser (Deps: 1)
- [ ] `ServerMessageCraft.java` - Crafting do servidor (Deps: 1)
- [ ] `ServerMessageLevelUp.java` - Level up (Deps: 1)
- [ ] `ServerMessageSound.java` - Som do servidor (Deps: 1)
- [ ] `ServerMessageSwapItem.java` - Trocar item (Deps: 1)

#### 2.3 Configurações Cloth Config (8 arquivos)
- [ ] `AmmoClothConfig.java` - Config de munição (Deps: 1)
- [ ] `GunClothConfig.java` - Config de armas (Deps: 1)
- [ ] `OtherClothConfig.java` - Outras configs (Deps: 1)
- [ ] `KeyClothConfig.java` - Config de teclas (Deps: 1)
- [ ] `RenderClothConfig.java` - Config de renderização (Deps: 1)
- [ ] `ZoomClothConfig.java` - Config de zoom (Deps: 1)
- [ ] `ClothConfigScreen.java` - Tela de configuração (Deps: 1)
- [ ] `CrosshairDropdown.java` - Dropdown de mira (Deps: 1)

#### 2.4 Estruturas de Dados e POJOs Avançados (20 arquivos)
- [ ] `AttachmentData.java` - Dados de acessório (Deps: 1)
- [ ] `AmmoDisplay.java` - Display de munição (Deps: 1)
- [ ] `AmmoTransform.java` - Transformação de munição (Deps: 1)
- [ ] `BlockDisplay.java` - Display de bloco (Deps: 1)
- [ ] `GunAmmo.java` - Sistema de munição da arma (Deps: 1)
- [ ] `GunTransform.java` - Transformação de arma (Deps: 1)
- [ ] `EffectData.java` - Dados de efeito (Deps: 1)
- [ ] `MeleeData.java` - Dados de corpo-a-corpo (Deps: 1)
- [ ] `Modifier.java` - Modificador (Deps: 1)
- [ ] `BlockData.java` - Dados de bloco (Deps: 1)
- [ ] `TabConfig.java` - Config da aba (Deps: 1)
- [ ] `GunData.java` - Dados da arma (Deps: 1)
- [ ] `GunMeleeData.java` - Dados de corpo-a-corpo da arma (Deps: 1)
- [ ] `TableRecipe.java` - Receita da mesa (Deps: 1)
- [ ] `PackMeta.java` - Metadados do pack (Deps: 1)
- [ ] `VersionChecker.java` - Verificador de versão (Deps: 1)
- [ ] `IFilter.java` - Interface de filtro (Deps: 1)
- [ ] `LiteralFilter.java` - Filtro literal (Deps: 1)
- [ ] `RecipeFilter.java` - Filtro de receita (Deps: 1)
- [ ] `RegexFilter.java` - Filtro regex (Deps: 1)

#### 2.5 Sistema de Receitas e Serializers (25 arquivos)
- [ ] `GunResult.java` - Resultado de receita (Deps: 1)
- [ ] `GunSmithTableResult.java` - Resultado da mesa de ferreiro (Deps: 1)
- [ ] `GunSmithTableIngredientSerializer.java` - Serializador de ingredientes (Deps: 1)
- [ ] `GunSmithTableResultSerializer.java` - Serializador de resultado (Deps: 1)
- [ ] `GunSmithTableResultComponents.java` - Componentes de resultado (Deps: 1)
- [ ] `GunSmithTableResultInfo.java` - Info de resultado (Deps: 1)
- [ ] `DistanceDamagePairSerializer.java` - Serializador de distância/dano (Deps: 1)
- [ ] `IgniteSerializer.java` - Serializador de ignição (Deps: 1)
- [ ] `PairSerializer.java` - Serializador de par (Deps: 1)
- [ ] `Vec3Serializer.java` - Serializador Vec3 (Deps: 1)
- [ ] `CommonAmmoIndexSerializer.java` - Serializador de índice de munição (Deps: 1)
- [ ] `CommonAttachmentIndexSerializer.java` - Serializador de acessórios (Deps: 1)
- [ ] `CommonBlockIndexSerializer.java` - Serializador de blocos (Deps: 1)
- [ ] `CommonGunIndexSerializer.java` - Serializador de armas (Deps: 1)
- [ ] `ItemStackSerializer.java` - Serializador ItemStack (Deps: 1)
- [ ] `SoundEffectKeyframesSerializer.java` - Serializador de keyframes (Deps: 1)
- [ ] `Vector3fSerializer.java` - Serializador Vector3f (Deps: 1)
- [ ] `TimelessGunSmithTableRecipeSchema.java` - Schema de receita (Deps: 1)
- [ ] `TimelessRecipeJS.java` - Receita JS (Deps: 1)
- [ ] `AmmoNbtFactory.java` - Factory NBT de munição (Deps: 1)
- [ ] `AttachmentNbtFactory.java` - Factory NBT de acessório (Deps: 1)
- [ ] `TimelessItemNbtFactory.java` - Factory NBT genérico (Deps: 1)
- [ ] `GunSmithTableResultInfo.java` - Info de resultado da mesa (Deps: 1)
- [ ] `TimelessItemWrapper.java` - Wrapper de item (Deps: 1)
- [ ] E mais 5+ serializadores...

#### 2.6 Índices e Managers Simples (12 arquivos)
- [ ] `CommonAmmoIndex.java` - Índice comum de munição (Deps: 1)
- [ ] `CommonDataManager.java` - Gerenciador de dados comum (Deps: 1)
- [ ] `CommandRegistry.java` - Registro de comandos (Deps: 1)
- [ ] `SoundManager.java` - Gerenciador de som (Deps: 1)
- [ ] `ICommonResourceProvider.java` - Provedor de recursos (Deps: 1)
- [ ] `INetworkCacheReloadListener.java` - Listener de reload (Deps: 1)
- [ ] `JsonDataManager.java` - Gerenciador JSON (Deps: 1)
- [ ] `RecipeFilterManager.java` - Gerenciador de filtros (Deps: 1)
- [ ] `ScriptManager.java` - Gerenciador de scripts (Deps: 1)
- [ ] `AllowAttachmentTagMatcher.java` - Matcher de tags (Deps: 1)
- [ ] `AttachmentIdFix.java` - Fix de ID de acessório (Deps: 1)
- [ ] E mais 1+ managers...

#### 2.7 Compatibilidade Básica (25 arquivos)
- [ ] `CustomGunItemBuilder.java` - Builder customizado (Deps: 1)
- [ ] `KubeJSCustomGunItem.java` - Item customizado KubeJS (Deps: 1)
- [ ] `TimelessClientEvents.java` - Eventos do cliente (Deps: 1)
- [ ] `TimelessCommonEvents.java` - Eventos comuns (Deps: 1)
- [ ] `TimelessServerEvents.java` - Eventos do servidor (Deps: 1)
- [ ] `TimelessKubeJSEventRegister.java` - Registro de eventos (Deps: 1)
- [ ] `OculusCompat.java` - Compatibilidade Oculus (Deps: 1)
- [ ] `OculusCompatLegacy.java` - Oculus legado (Deps: 1)
- [ ] `OculusCompatNewly.java` - Oculus novo (Deps: 1)
- [ ] `OptifineCompat.java` - Compatibilidade Optifine (Deps: 1)
- [ ] `PlayerAnimatorCompat.java` - PlayerAnimator (Deps: 1)
- [ ] `ShoulderSurfingCompat.java` - ShoulderSurfing (Deps: 1)
- [ ] `ShoulderSurfingCompatInner.java` - Inner ShoulderSurfing (Deps: 1)
- [ ] `AnimationDataRegisterFactory.java` - Factory de registro (Deps: 1)
- [ ] `AnimationManager.java` - Gerenciador de animação (Deps: 1)
- [ ] `PlayerAnimatorAssetManager.java` - Assets PlayerAnimator (Deps: 1)
- [ ] `PlayerAnimatorLoader.java` - Loader PlayerAnimator (Deps: 1)
- [ ] `AdjustmentYRotModifier.java` - Modificador Y Rot (Deps: 1)
- [ ] `BlackList.java` - Lista negra (Deps: 1)
- [ ] `MenuIntegration.java` - Integração de menu (Deps: 1)
- [ ] `OpenGunPackDirEntry.java` - Entrada de diretório (Deps: 1)
- [ ] `GunModSubtype.java` - Subtipo do mod (Deps: 1)
- [ ] `AttachmentQueryEntry.java` - Entrada de query (Deps: 1)
- [ ] `TimelessItemType.java` - Tipo de item (Deps: 1)
- [ ] E mais 1+ arquivos de compat...

#### 2.8 Utilitários e Helpers Intermediários (45 arquivos)
- [ ] `Accessors.java` - Acessors GLTF (Deps: 1)
- [ ] `AnimationStateMachine.java` - Máquina de estados (Deps: 1)
- [ ] `AttachmentItemTooltip.java` - Tooltip de acessório (Deps: 1)
- [ ] `AttachmentPropertyEvent.java` - Evento de propriedade (Deps: 1)
- [ ] `BeforeRenderHandEvent.java` - Evento de render de mão (Deps: 1)
- [ ] `BellRing.java` - Toque de sino (Deps: 1)
- [ ] `FunctionalBedrockPart.java` - Parte funcional Bedrock (Deps: 1)
- [ ] `HeadShotAABBConfigRead.java` - Config de headshot (Deps: 1)
- [ ] `InteractKeyConfigRead.java` - Config de tecla interact (Deps: 1)
- [ ] `PreLoadConfig.java` - Config de pré-carregamento (Deps: 1)
- [ ] `PreLoadModConfig.java` - Config do mod (Deps: 1)
- [ ] `BulletHoleOption.java` - Opção de buraco de bala (Deps: 1)
- [ ] `GunMeleeDebug.java` - Debug de corpo-a-corpo (Deps: 1)
- [ ] `InputExtraCheck.java` - Check extra de input (Deps: 1)
- [ ] `LaserColorUtil.java` - Util de cor do laser (Deps: 1)
- [ ] `RenderDistance.java` - Distância de render (Deps: 1)
- [ ] `RenderHelper.java` - Helper de render (Deps: 1)
- [ ] `ResourceScanner.java` - Scanner de recursos (Deps: 1)
- [ ] `TacHitResult.java` - Resultado de hit (Deps: 1)
- [ ] `TacPathVisitor.java` - Visitor de caminho (Deps: 1)
- [ ] `BlockRayTrace.java` - Ray trace de bloco (Deps: 1)
- [ ] `ProjectileExplosion.java` - Explosão de projétil (Deps: 1)
- [ ] `HitboxHelper.java` - Helper de hitbox (Deps: 1)
- [ ] `ExplodeUtil.java` - Util de explosão (Deps: 1)
- [ ] `EntityUtil.java` - Util de entidade (Deps: 1)
- [ ] E mais 20+ utilitários com 1-3 dependências...

#### 2.9 Mixins e Eventos Simples (35 arquivos)
- [ ] `AbstractButtonMixin.java` - Mixin de botão (Deps: 1)
- [ ] `HumanoidModelMixin.java` - Mixin de modelo humanoide (Deps: 1)
- [ ] `LanguageMixin.java` - Mixin de linguagem (Deps: 1)
- [ ] `StairBlockAccessor.java` - Accessor de escada (Deps: 1)
- [ ] `ChangeGunPropertyEvent.java` - Evento de mudança (Deps: 1)
- [ ] `CommonLoadPack.java` - Carregamento comum (Deps: 1)
- [ ] `EntityDamageEvent.java` - Evento de dano (Deps: 1)
- [ ] `HitboxHelperEvent.java` - Evento de hitbox (Deps: 1)
- [ ] `KnockbackChange.java` - Mudança de knockback (Deps: 1)
- [ ] `LoadingConfigEvent.java` - Evento de config (Deps: 1)
- [ ] `PreventGunClick.java` - Prevenir click (Deps: 1)
- [ ] `ServerTickEvent.java` - Evento de tick (Deps: 1)
- [ ] `SyncBaseTimestamp.java` - Sync de timestamp (Deps: 1)
- [ ] `SyncedEntityDataEvent.java` - Evento de sync (Deps: 1)
- [ ] `TravelToDimensionEvent.java` - Evento de viagem (Deps: 1)
- [ ] `DestroyGlassBlock.java` - Destruir vidro (Deps: 1)
- [ ] E mais 20+ eventos e mixins simples...

#### 2.10 Outros Arquivos da Fase 2 (75+ arquivos restantes)
*Inclui modificadores, interfaces, GUIs básicas, keys, etc. com 1-3 dependências*

**Ordem de Habilitação na Fase 2:** 
1. Comandos básicos primeiro
2. Network messages simples
3. Configurações
4. Estruturas de dados
5. Serializers e receitas
6. Índices simples
7. Compatibilidade básica
8. Utilitários intermediários
9. Mixins e eventos
10. Outros arquivos restantes

---

### **FASE 3: DEPENDÊNCIAS MÉDIAS** 🔶 (132 arquivos)
*Estes arquivos dependem de 4-10 outros arquivos do mod*

**Status: [ ] 0/132 arquivos habilitados**
**Pré-requisito: ✅ Fases 1 e 2 devem estar 100% concluídas**

#### 3.1 Sistema de Blocos e Block Entities (8 arquivos)
- [ ] `AbstractGunSmithTableBlock.java` - Bloco base da mesa (Deps: 4)
- [ ] `GunSmithTableBlockA.java` - Mesa tipo A (Deps: 4)
- [ ] `GunSmithTableBlockB.java` - Mesa tipo B (Deps: 4)
- [ ] `GunSmithTableBlockC.java` - Mesa tipo C (Deps: 4)
- [ ] `StatueBlock.java` - Bloco de estátua (Deps: 4)
- [ ] `TargetBlock.java` - Bloco de alvo (Deps: 4)
- [ ] `GunSmithTableBlockEntity.java` - Entity da mesa (Deps: 6)
- [ ] `StatueBlockEntity.java` - Entity de estátua (Deps: 6)

#### 3.2 Sistema de Items Complexos (12 arquivos)
- [ ] `AmmoItemBuilder.java` - Builder de munição (Deps: 4)
- [ ] `AttachmentItemBuilder.java` - Builder de acessório (Deps: 4)
- [ ] `BlockItemBuilder.java` - Builder de bloco (Deps: 5)
- [ ] `GunItemBuilder.java` - Builder de arma (Deps: 5)
- [ ] `AmmoItem.java` - Item de munição (Deps: 6)
- [ ] `AttachmentItem.java` - Item de acessório (Deps: 8)
- [ ] `DefaultTableItem.java` - Item de mesa padrão (Deps: 6)
- [ ] `GunSmithTableItem.java` - Item da mesa de ferreiro (Deps: 5)
- [ ] `TargetMinecartItem.java` - Item de carrinho alvo (Deps: 7)
- [ ] `GunTooltipPart.java` - Parte do tooltip de arma (Deps: 6)
- [ ] `IAmmo.java` - Interface de munição (Deps: 4)
- [ ] `IAmmoBox.java` - Interface de caixa de munição (Deps: 4)

#### 3.3 Sistema de Rendering Intermediário (20 arquivos)
- [ ] `BedrockAmmoModel.java` - Modelo Bedrock de munição (Deps: 4)
- [ ] `BedrockAttachmentModel.java` - Modelo de acessório (Deps: 6)
- [ ] `AmmoItemRenderer.java` - Renderizador de munição (Deps: 7)
- [ ] `AnimateGeoItemRenderer.java` - Renderizador geo animado (Deps: 8)
- [ ] `AttachmentItemRenderer.java` - Renderizador de acessórios (Deps: 6)
- [ ] `GunSmithTableItemRenderer.java` - Renderizador da mesa (Deps: 8)
- [ ] `GunSmithTableRenderer.java` - Renderizador de bloco (Deps: 6)
- [ ] `StatueRenderer.java` - Renderizador de estátua (Deps: 5)
- [ ] `TargetRenderer.java` - Renderizador de alvo (Deps: 5)
- [ ] `TargetMinecartRenderer.java` - Renderizador de carrinho (Deps: 4)
- [ ] `EntityBulletRenderer.java` - Renderizador de bala (Deps: 6)
- [ ] `AttachmentRender.java` - Render de acessório (Deps: 7)
- [ ] `LeftHandRender.java` - Render mão esquerda (Deps: 8)
- [ ] `RightHandRender.java` - Render mão direita (Deps: 8)
- [ ] `ShellRender.java` - Render de cápsula (Deps: 7)
- [ ] `IFunctionalRenderer.java` - Interface funcional (Deps: 4)
- [ ] `TextShowRender.java` - Render de texto (Deps: 4)
- [ ] `CameraAnimationObject.java` - Objeto de câmera (Deps: 4)
- [ ] `ModelRendererWrapper.java` - Wrapper de renderizador (Deps: 6)
- [ ] `HumanoidOffhandRender.java` - Render offhand (Deps: 4)

#### 3.4 Sistema de Networking Avançado (15 arquivos)
- [ ] `NetworkHandler.java` - Handler principal de rede (Deps: 8)
- [ ] `IMessage.java` - Interface de mensagem (Deps: 4)
- [ ] `LoginIndexHolder.java` - Holder de índice (Deps: 5)
- [ ] `Acknowledge.java` - Confirmação de handshake (Deps: 4)
- [ ] `ClientMessageRefitGun.java` - Refit de arma (Deps: 4)
- [ ] `ClientMessageSyncBaseTimestamp.java` - Sync timestamp (Deps: 4)
- [ ] `ClientMessageUnloadAttachment.java` - Descarregar acessório (Deps: 4)
- [ ] `ServerMessageRefreshRefitScreen.java` - Refresh da tela (Deps: 6)
- [ ] `ServerMessageSyncBaseTimestamp.java` - Sync server (Deps: 5)
- [ ] `ServerMessageSyncGunPack.java` - Sincronização de gun pack (Deps: 6)
- [ ] `ServerMessageUpdateEntityData.java` - Update de dados (Deps: 7)
- [ ] `ServerMessageSyncedEntityDataMapping.java` - Mapping de dados (Deps: 8)
- [ ] `ServerMessageGunDraw.java` - Sacar arma servidor (Deps: 5)
- [ ] `ServerMessageGunFire.java` - Disparo servidor (Deps: 6)
- [ ] `ServerMessageGunFireSelect.java` - Seleção servidor (Deps: 5)

#### 3.5 Sistema de Entidades e Physics (12 arquivos)
- [ ] `EntityKineticBullet.java` - Entidade de bala (Deps: 9)
- [ ] `LivingEntityAim.java` - Mira de entidade (Deps: 6)
- [ ] `LivingEntityAmmoCheck.java` - Check de munição (Deps: 7)
- [ ] `LivingEntityBolt.java` - Ferrolho de entidade (Deps: 5)
- [ ] `LivingEntityCrawl.java` - Rastejar (Deps: 8)
- [ ] `LivingEntityDrawGun.java` - Sacar arma (Deps: 7)
- [ ] `LivingEntityFireSelect.java` - Seleção de modo (Deps: 6)
- [ ] `LivingEntityHeat.java` - Aquecimento (Deps: 8)
- [ ] `LivingEntitySpeedModifier.java` - Modificador de velocidade (Deps: 9)
- [ ] `LivingEntitySprint.java` - Sprint (Deps: 7)
- [ ] `ShooterDataHolder.java` - Holder de dados (Deps: 8)
- [ ] `ModSerializers.java` - Serializadores (Deps: 6)

#### 3.6 Sistema de GUI e Interface (18 arquivos)
- [ ] `GunPackProgressScreen.java` - Tela de progresso (Deps: 5)
- [ ] `ModContainerScreen.java` - Tela de container (Deps: 6)
- [ ] `GunPackList.java` - Lista de gun packs (Deps: 7)
- [ ] `GunAttachmentSlot.java` - Slot de acessório (Deps: 8)
- [ ] `GunPropertyDiagrams.java` - Diagramas de propriedade (Deps: 9)
- [ ] `HSVSliderGroup.java` - Grupo de sliders HSV (Deps: 4)
- [ ] `InventoryAttachmentSlot.java` - Slot no inventário (Deps: 7)
- [ ] `RefitTurnPageButton.java` - Botão de página (Deps: 6)
- [ ] `RefitUnloadButton.java` - Botão de descarregar (Deps: 5)
- [ ] `ResultButton.java` - Botão de resultado (Deps: 7)
- [ ] `TypeButton.java` - Botão de tipo (Deps: 6)
- [ ] `HeatBarOverlay.java` - Overlay de calor (Deps: 8)
- [ ] `InteractKeyTextOverlay.java` - Overlay de tecla (Deps: 4)
- [ ] `KillAmountOverlay.java` - Overlay de kills (Deps: 9)
- [ ] `RefitTransform.java` - Transformação de refit (Deps: 4)
- [ ] `ModEntitiesRender.java` - Render de entidades (Deps: 7)
- [ ] `ParticleFactoryRegistry.java` - Registro de partículas (Deps: 4)
- [ ] `BulletHoleParticle.java` - Partícula de buraco (Deps: 5)

#### 3.7 Sistema de Input e Keys (11 arquivos)
- [ ] `AimKey.java` - Tecla de mira (Deps: 6)
- [ ] `ConfigKey.java` - Tecla de config (Deps: 5)
- [ ] `CrawlKey.java` - Tecla de rastejar (Deps: 4)
- [ ] `FireSelectKey.java` - Tecla de seleção (Deps: 7)
- [ ] `InspectKey.java` - Tecla de inspeção (Deps: 8)
- [ ] `InteractKey.java` - Tecla de interação (Deps: 6)
- [ ] `MeleeKey.java` - Tecla de corpo-a-corpo (Deps: 7)
- [ ] `RefitKey.java` - Tecla de refit (Deps: 5)
- [ ] `ReloadKey.java` - Tecla de recarga (Deps: 8)
- [ ] `ShootKey.java` - Tecla de disparo (Deps: 9)
- [ ] `ZoomKey.java` - Tecla de zoom (Deps: 6)

#### 3.8 Sistema de Índices e Managers Avançados (15 arquivos)
- [ ] `CommonAttachmentIndex.java` - Índice de acessórios (Deps: 4)
- [ ] `CommonBlockIndex.java` - Índice de blocos (Deps: 4)
- [ ] `CommonGunIndex.java` - Índice de armas (Deps: 5)
- [ ] `AttachmentDataManager.java` - Manager de acessórios (Deps: 5)
- [ ] `AttachmentsTagManager.java` - Manager de tags (Deps: 5)
- [ ] `AttachmentCacheProperty.java` - Cache de propriedades (Deps: 4)
- [ ] `AttachmentPropertyManager.java` - Manager de propriedades (Deps: 8)
- [ ] `DisplayManager.java` - Manager de display (Deps: 4)
- [ ] `GltfManager.java` - Manager GLTF (Deps: 5)
- [ ] `PackInfoManager.java` - Manager de pack info (Deps: 4)
- [ ] `SoundAssetsManager.java` - Manager de áudio (Deps: 7)
- [ ] `CompatRegistry.java` - Registro de compatibilidade (Deps: 4)
- [ ] `CapabilityRegistry.java` - Registro de capabilities (Deps: 6)
- [ ] `CommonRegistry.java` - Registro comum (Deps: 8)
- [ ] `GunPackLoader.java` - Carregador de packs (Deps: 9)

#### 3.9 Sistema de Gameplay Local (12 arquivos)
- [ ] `LocalPlayerAim.java` - Mira local (Deps: 8)
- [ ] `LocalPlayerCrawl.java` - Rastejar local (Deps: 4)
- [ ] `LocalPlayerDataHolder.java` - Holder de dados (Deps: 7)
- [ ] `LocalPlayerDraw.java` - Sacar arma local (Deps: 9)
- [ ] `LocalPlayerFireSelect.java` - Seleção local (Deps: 6)
- [ ] `LocalPlayerInspect.java` - Inspeção local (Deps: 8)
- [ ] `LocalPlayerSprint.java` - Sprint local (Deps: 5)
- [ ] `ClientHitMark.java` - Marca de hit (Deps: 7)
- [ ] `ClientPreventGunClick.java` - Prevenir click (Deps: 4)
- [ ] `PlayerEnterWorld.java` - Entrar no mundo (Deps: 6)
- [ ] `PreventsHotbarEvent.java` - Prevenir hotbar (Deps: 8)
- [ ] `ReloadResourceEvent.java` - Reload de recursos (Deps: 9)

#### 3.10 Outros Sistemas Intermediários (25+ arquivos)
*Inclui modificadores, listeners, mixins avançados, eventos complexos, etc.*
- [ ] `ControllableInner.java` - Inner controllable (Deps: 4)
- [ ] `AttachmentQueryCategory.java` - Categoria JEI (Deps: 4)
- [ ] `ConstraintObject.java` - Objeto de constraint (Deps: 5)
- [ ] `ModelRotateListener.java` - Listener de rotação (Deps: 4)
- [ ] `ModelTranslateListener.java` - Listener de translação (Deps: 5)
- [ ] E mais 20+ arquivos intermediários...

**Observação:** Análise cuidadosa de dependências necessária nesta fase.

---

### **FASE 4: DEPENDÊNCIAS ALTAS** 🔴 (52 arquivos)
*Estes arquivos dependem de 11+ outros arquivos do mod*

**Status: [ ] 0/52 arquivos habilitados**
**Pré-requisito: ✅ Fases 1, 2 e 3 devem estar 100% concluídas**

#### 4.1 Sistema Principal de Armas (8 arquivos)
- [ ] `ModernKineticGunItem.java` - Item principal de arma (Deps: 15)
- [ ] `AbstractGunItem.java` - Item base de arma (Deps: 15)
- [ ] `GunItemManager.java` - Gerenciador de itens (Deps: 18)
- [ ] `IGun.java` - Interface principal de arma (Deps: 12)
- [ ] `GunItemDataAccessor.java` - Accessor de dados da arma (Deps: 20+)
- [ ] `AmmoItemDataAccessor.java` - Accessor de munição (Deps: 16)
- [ ] `AttachmentItemDataAccessor.java` - Accessor de acessórios (Deps: 18)
- [ ] `ItemDataAccessor.java` - Accessor base (Deps: 14)

#### 4.2 Sistema de Renderização Complexo (12 arquivos)
- [ ] `GunItemRendererWrapper.java` - Wrapper de renderização (Deps: 20)
- [ ] `BedrockAnimatedModel.java` - Modelo animado complexo (Deps: 14)
- [ ] `BedrockGunModel.java` - Modelo Bedrock de arma (Deps: 14)
- [ ] `ThirdPersonManager.java` - Gerenciador terceira pessoa (Deps: 16+)
- [ ] `InnerThirdPersonManager.java` - Inner third person (Deps: 18)
- [ ] `KeepingItemRenderer.java` - Renderer de item em mão (Deps: 15)
- [ ] `BeamRenderer.java` - Renderizador de feixe (Deps: 11)
- [ ] `MuzzleFlashRender.java` - Render de flash (Deps: 11)
- [ ] `GunModelTypeManager.java` - Manager de tipos (Deps: 17)
- [ ] `IThirdPersonAnimation.java` - Interface terceira pessoa (Deps: 13)
- [ ] `IClientPlayerGunOperator.java` - Operador cliente (Deps: 19)
- [ ] `GunDisplayInstance.java` - Instância de display (Deps: 27)

#### 4.3 Sistema de Gameplay Avançado (15 arquivos)
- [ ] `LocalPlayerShoot.java` - Sistema de tiro local (Deps: 21)
- [ ] `LocalPlayerReload.java` - Sistema de recarga local (Deps: 15)
- [ ] `LocalPlayerMelee.java` - Corpo-a-corpo local (Deps: 13)
- [ ] `LocalPlayerBolt.java` - Ferrolho local (Deps: 11)
- [ ] `LivingEntityShoot.java` - Sistema principal de tiro (Deps: 13)
- [ ] `LivingEntityReload.java` - Sistema de recarga (Deps: 11+)
- [ ] `LivingEntityMelee.java` - Sistema de corpo-a-corpo (Deps: 12)
- [ ] `TargetMinecart.java` - Carrinho alvo (Deps: 14)
- [ ] `IGunOperator.java` - Operador de arma (Deps: 16)
- [ ] `ITargetEntity.java` - Interface de alvo (Deps: 14)
- [ ] `ReloadState.java` - Estado de recarga (Deps: 12)
- [ ] `ShootResult.java` - Resultado de tiro (Deps: 15)
- [ ] `KnockBackModifier.java` - Modificador de knockback (Deps: 18)
- [ ] `IAnimationItem.java` - Interface de animação (Deps: 13)
- [ ] `IAttachment.java` - Interface de acessório (Deps: 16)

#### 4.4 Sistema de Modificadores Avançados (8 arquivos)
- [ ] `AmmoSpeedModifier.java` - Modificador de velocidade (Deps: 12)
- [ ] `ArmorIgnoreModifier.java` - Ignorar armadura (Deps: 15)
- [ ] `DamageModifier.java` - Modificador de dano (Deps: 16)
- [ ] `HeadShotModifier.java` - Modificador headshot (Deps: 15)
- [ ] `InaccuracyModifier.java` - Modificador imprecisão (Deps: 13)
- [ ] `KnockbackModifier.java` - Modificador knockback (Deps: 13)
- [ ] `RecoilModifier.java` - Modificador recuo (Deps: 12)
- [ ] `RpmModifier.java` - Modificador RPM (Deps: 11)

#### 4.5 Sistema de Índices e Resources Complexos (5 arquivos)
- [ ] `ClientIndexManager.java` - Gerenciador de índices (Deps: 18)
- [ ] `ClientAssetsManager.java` - Gerenciador de assets (Deps: 26)
- [ ] `ClientAttachmentIndex.java` - Índice de acessórios (Deps: 12)
- [ ] `InternalAssetLoader.java` - Carregador interno (Deps: 20)
- [ ] `CommonAssetsManager.java` - Assets comum (Deps: 21)

#### 4.6 Sistema de Interface Avançada (4 arquivos)
- [ ] `GunHudOverlay.java` - Overlay do HUD de arma (Deps: 21)
- [ ] `GunRefitScreen.java` - Tela de refit (Deps: 21)
- [ ] `GunSmithTableScreen.java` - Tela da mesa de ferreiro (Deps: 24)
- [ ] `ClientSetupEvent.java` - Setup do cliente (Deps: 27)

**Observação:** Esta é a fase mais crítica. Cada arquivo deve ser habilitado individualmente com testes extensivos.

---

## 🛠️ WORKFLOW POR ARQUIVO

### Para Cada Arquivo da Lista:

1. **Verificação de Pré-requisitos:**
   ```bash
   # Verificar se todas as dependências já estão habilitadas
   # Usar script: .\DEPENDENCY_ANALYZER_SIMPLE.ps1
   ```

2. **Backup de Segurança:**
   ```bash
   git add .; git commit -m "Backup antes de [FASE X.Y] - [ARQUIVO]"
   ```

3. **Habilitação:**
   ```bash
   Move-Item "path\to\file.java.disabled" "path\to\file.java"
   ```

4. **Correção de Imports NeoForge 1.21.1:**
   - Migrar `RegistryObject` → `DeferredHolder`
   - Migrar NBT → `DataComponent` quando aplicável
   - Atualizar imports de APIs do NeoForge

5. **Build Test:**
   ```bash
   .\gradlew build
   ```

6. **Validação ou Rollback:**
   ```bash
   # Se SUCESSO:
   git add .; git commit -m "✅ [FASE X.Y] [ARQUIVO] - Habilitado com sucesso"
   
   # Se FALHA:
   Move-Item "path\to\file.java" "path\to\file.java.disabled"
   git reset --hard HEAD
   ```

---

## 📊 PROGRESSO DETALHADO

### Status por Fase:
- **Fase 1:** [ ] 0/207 (0%) - Fundação ⚡ **PRÓXIMO PASSO**
- **Fase 2:** [ ] 0/221 (0%) - Dependências Baixas 🔸  
- **Fase 3:** [ ] 0/132 (0%) - Dependências Médias 🔶
- **Fase 4:** [ ] 0/52 (0%) - Dependências Altas 🔴

### Build Status:
- [ ] Fase 1 Build Success
- [ ] Fase 2 Build Success  
- [ ] Fase 3 Build Success
- [ ] Fase 4 Build Success

### Última Atualização:
**Data:** 2025-06-27  
**Próximo passo:** Iniciar Fase 1 - arquivos sem dependências
