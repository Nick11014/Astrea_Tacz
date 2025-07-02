# PLANO DE MIG| **Fase 1** | Primeira Camada (dependem apenas da fundacao) | 78 | â³ 0/78 |
| **Fase 2** | Dependencias Baixas (1-3) | 260 | â³ 0/260 |
| **Fase 3** | Dependencias Medias (4-10) | 139 | â³ 0/139 |AO SISTEMATICA - TacZ NeoForge 1.21.1 (v3.0)

**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1  
**Estrategia:** Habilitacao incremental baseada em analise topologica de dependencias  
**Data de Geracao:** 2025-07-02 18:01:20  
**Script:** Generate-Plan.ps1 v3.0  

---

## RESUMO ESTATISTICO

| Fase | Descricao | Arquivos | Status |
|------|-----------|----------|--------|
| **Fase 0** | Fundacao (ja habilitada) | 95 | âœ… 95/95 |
| **Fase 1** | Primeira Camada (dependem apenas da fundacao) | 81 | â³ 0/81 |
| **Fase 2** | Dependencias Baixas (1-3) | 259 | â³ 0/259 |
| **Fase 3** | Dependencias Medias (4-10) | 137 | â³ 0/137 |
| **Fase 4** | Dependencias Altas (11+) | 51 | â³ 0/51 |
| **TOTAL** | **Todos os arquivos** | **623** | **95/623** |

---

## PLANO DE EXECUCAO ORDENADO

### **FASE 0: FUNDACAO (Ja Habilitada)** âœ…

*Esta e a base solida ja estabelecida. Estes arquivos compilam sem erros e servem como fundacao para as proximas fases.*

- [x] AccessorData.java (Fundacao)
- [x] Align.java (Fundacao)
- [x] AmmoConfig.java (Fundacao)
- [x] AmmoCountStyle.java (Fundacao)
- [x] AmmoEntityDisplay.java (Fundacao)
- [x] AmmoIndexPOJO.java (Fundacao)
- [x] AnimationBone.java (Fundacao)
- [x] AnimationChannelContent.java (Fundacao)
- [x] AnimationConstant.java (Fundacao)
- [x] AnimationKeyframes.java (Fundacao)
- [x] AnimationName.java (Fundacao)
- [x] AnimationSoundChannelContent.java (Fundacao)
- [x] AttachmentLod.java (Fundacao)
- [x] AttachmentSkin.java (Fundacao)
- [x] AttachmentType.java (Fundacao)
- [x] BedrockAnimation.java (Fundacao)
- [x] BedrockAnimationFile.java (Fundacao)
- [x] BedrockCube.java (Fundacao)
- [x] Bolt.java (Fundacao)
- [x] BurstData.java (Fundacao)
- [x] CacheValue.java (Fundacao)
- [x] ColorHex.java (Fundacao)
- [x] ControllableData.java (Fundacao)
- [x] CycleTaskHelper.java (Fundacao)
- [x] DamageStyle.java (Fundacao)
- [x] DataType.java (Fundacao)
- [x] DebugCommand.java (Fundacao)
- [x] DefaultAnimationType.java (Fundacao)
- [x] DelayedTask.java (Fundacao)
- [x] Description.java (Fundacao)
- [x] Easing.java (Fundacao)
- [x] EffectData.java (Fundacao)
- [x] ElementType.java (Fundacao)
- [x] ExplosionData.java (Fundacao)
- [x] ExtraDamage.java (Fundacao)
- [x] FeedType.java (Fundacao)
- [x] FireSound.java (Fundacao)
- [x] GltfConstants.java (Fundacao)
- [x] GunAnimationConstant.java (Fundacao)
- [x] GunConfig.java (Fundacao)
- [x] GunDamageSourcePart.java (Fundacao)
- [x] GunDefaultMeleeData.java (Fundacao)
- [x] GunFireModeAdjustData.java (Fundacao)
- [x] GunHeatData.java (Fundacao)
- [x] GunHurtBobTweak.java (Fundacao)
- [x] GunIndexPOJO.java (Fundacao)
- [x] GunLod.java (Fundacao)
- [x] GunMeleeData.java (Fundacao)
- [x] GunMeleeDebug.java (Fundacao)
- [x] GunModelConstant.java (Fundacao)
- [x] GunProperty.java (Fundacao)
- [x] GunReloadTime.java (Fundacao)
- [x] GunSmithTableIngredient.java (Fundacao)
- [x] GunTabType.java (Fundacao)
- [x] IAmmo.java (Fundacao)
- [x] IAmmoBox.java (Fundacao)
- [x] IBlock.java (Fundacao)
- [x] IDataSerializer.java (Fundacao)
- [x] IFilter.java (Fundacao)
- [x] IFunctionalRenderer.java (Fundacao)
- [x] Ignite.java (Fundacao)
- [x] InputExtraCheck.java (Fundacao)
- [x] IStackTooltip.java (Fundacao)
- [x] ITargetEntity.java (Fundacao)
- [x] IThirdPersonAnimation.java (Fundacao)
- [x] JsonProperty.java (Fundacao)
- [x] KeepingItemRenderer.java (Fundacao)
- [x] KeyConfig.java (Fundacao)
- [x] KnockBackModifier.java (Fundacao)
- [x] LanguageMixin.java (Fundacao)
- [x] LaserConfig.java (Fundacao)
- [x] ListPackCommand.java (Fundacao)
- [x] LuaEntityAccessor.java (Fundacao)
- [x] LuaLibrary.java (Fundacao)
- [x] MeleeData.java (Fundacao)
- [x] Modifier.java (Fundacao)
- [x] MuzzleFlash.java (Fundacao)
- [x] NumberArrays.java (Fundacao)
- [x] OculusCompatLegacy.java (Fundacao)
- [x] OculusCompatNewly.java (Fundacao)
- [x] OptifineCompat.java (Fundacao)
- [x] OtherConfig.java (Fundacao)
- [x] PackInfo.java (Fundacao)
- [x] PackMeta.java (Fundacao)
- [x] PathHandler.java (Fundacao)
- [x] SecondOrderDynamics.java (Fundacao)
- [x] ShellDisplay.java (Fundacao)
- [x] ShootResult.java (Fundacao)
- [x] ShoulderSurfingCompat.java (Fundacao)
- [x] ShoulderSurfingCompatInner.java (Fundacao)
- [x] SoundEffectKeyframes.java (Fundacao)
- [x] StairBlockAccessor.java (Fundacao)
- [x] SyncConfig.java (Fundacao)
- [x] TrackArrayMismatchException.java (Fundacao)
- [x] ZoomConfig.java (Fundacao)
---

### **FASE 1: PRIMEIRA CAMADA** â­ 
*Próxima a ser executada - Dependem apenas da fundação*

Esta fase consiste em corrigir as classes que dependem apenas da "Fundação" (Fase 0), mas que falham devido a mudanças na API do NeoForge 1.21.1.

#### **Fase 1.1: Sistema de Configuração**
*O sistema de configuração do Forge/NeoForge foi completamente reestruturado. Classes não devem mais herdar de `ModConfig`.*

**Ação Recomendada:**
- Remova a herança de `ModConfig`.
- Utilize `ModConfigSpec.Builder` para construir sua especificação de configuração.
- Registre sua configuração no construtor da sua classe de mod principal usando `ModLoadingContext.get().registerConfig(ModConfig.Type, spec)`.
- Para ler os valores ou reagir a mudanças, utilize os eventos `ModConfigEvent.Loading` e `ModConfigEvent.Reloading`.

- [ ] PreLoadModConfig.java

---

#### **Fase 1.2: Sistema de Eventos e Rede (Networking)**
*O sistema de eventos teve pacotes movidos e o sistema de rede foi modernizado, eliminando `NetworkEvent`.*

**Ação Recomendada:**
- **Eventos de Tick:** Altere o import do `TickEvent` para o pacote correto, como `net.neoforged.neoforge.event.tick.ServerTickEvent`.
- **Rede (Networking):** Migre o sistema de mensagens para o `PacketRegistrar` do NeoForge.
  - Defina suas mensagens (packets).
  - Crie um `PacketRegistrar` e registre suas mensagens nele.
  - A lógica de manipulação da mensagem (o `handle`) não recebe mais um `Supplier<NetworkEvent.Context>`, mas sim um `IPayloadContext`.

- [ ] ServerTickEvent.java
- [ ] IMessage.java
- [ ] ServerMessageLevelUp.java

---

#### **Fase 1.3: Interação com Itens e Dados (ItemStack & Data Components)**
*O acesso direto a NBT via `.getTag()` foi substituído pelo sistema de "Data Components" para melhorar o desempenho e a clareza.*

**Ação Recomendada:**
- **Comparação de Itens:** Substitua `ItemStack.isSameItemSameTags(stackA, stackB)` por `ItemStack.matches(stackA, stackB)`.
- **Acesso a Dados:** Defina seus próprios `DataComponentType` para os dados customizados que você precisa armazenar.
  - Substitua `stack.getTag()` e `stack.getOrCreateTag()` por `stack.get(MyComponents.MY_DATA)` para ler e `stack.set(MyComponents.MY_DATA, value)` para escrever.
- **Tooltips:** O método `getTooltipLines` mudou sua assinatura. Agora ele requer `(TooltipContext, Player, TooltipFlag)`.
- **Serialização:** O `CraftingHelper.getItemStack` foi alterado. Investigue as novas formas de carregar `ItemStack` de JSON, possivelmente através de `ItemStack.CODEC`.

- [ ] IAnimationItem.java
- [ ] LuaNbtAccessor.java
- [ ] IComponentTooltip.java
- [ ] GunTooltipPart.java
- [ ] ItemStackSerializer.java

---

#### **Fase 1.4: Renderização e GUI**
*A API de renderização passou por atualizações para se alinhar com as mudanças internas do Minecraft.*

**Ação Recomendada:**
- **Fundo de Telas (GUI):** A assinatura do método `renderBackground` foi atualizada para `renderBackground(GuiGraphics, int, int, float)`.
- **Renderização Manual (Tesselator):** O fluxo de renderização com `Tesselator` e `BufferBuilder` foi simplificado.
  - `Tesselator.getInstance().getBuilder()` foi substituído, o fluxo agora é mais direto.
  - As chamadas `begin()` e `end()` foram substituídas por um método final que constrói e desenha a geometria, como `BufferUploader.drawWithShader(bufferBuilder.buildOrThrow())`.

- [ ] GunPackProgressScreen.java
- [ ] RenderHelper.java

---

#### **Fase 1.5: API Geral do NeoForge/Minecraft**
*Esta seção agrupa diversas outras mudanças de API pontuais.*

**Ação Recomendada:**
- **ResourceLocation:** O construtor `new ResourceLocation("string")` foi alterado. Use `ResourceLocation.fromNamespaceAndPath("modid", "path")`.
- **Ingredient:** O método `Ingredient.fromJson()` foi alterado. O método recomendado agora é usar o `Codec` do `Ingredient`: `Ingredient.CODEC.parse(JsonOps.INSTANCE, jsonElement)`.
- **ServerPlayer:** A propriedade `latency` para obter o ping do jogador foi movida ou seu acesso foi alterado. Verifique os novos métodos da classe `ServerPlayer`.

- [ ] HeadShotAABBConfigRead.java
- [ ] SyncedClassKey.java
- [ ] TacPathVisitor.java
- [ ] GunSmithTableIngredientSerializer.java
- [ ] HitboxHelper.java
- [ ] ConfigCommand.java

---

#### **Arquivos Restantes da Fase 1**
*Arquivos sem dependências internas que podem ser habilitados com segurança:*

- [ ] AccessorSparseIndices.java (Deps: 0)
- [ ] AccessorSparseValues.java (Deps: 0)
- [ ] AmmoBoxTooltip.java (Deps: 0)
- [ ] AmmoParticle.java (Deps: 0)
- [ ] AnimationChannelTarget.java (Deps: 0)
- [ ] AnimationSampler.java (Deps: 0)
- [ ] BedrockVertex.java (Deps: 0)
- [ ] BlockItemTooltip.java (Deps: 0)
- [ ] Buffer.java (Deps: 0)
- [ ] Buffers.java (Deps: 0)
- [ ] BufferView.java (Deps: 0)
- [ ] CommonTransformObject.java (Deps: 0)
- [ ] DiscreteTrackArray.java (Deps: 0)
- [ ] FaceItem.java (Deps: 0)
- [ ] FireMode.java (Deps: 0)
- [ ] FlatColorButton.java (Deps: 0)
- [ ] GunLevelUpToast.java (Deps: 0)
- [ ] GunRecoilKeyFrame.java (Deps: 0)
- [ ] IDisplay.java (Deps: 0)
- [ ] LayerGunShow.java (Deps: 0)
- [ ] LoginIndexHolder.java (Deps: 0)
- [ ] MathUtil.java (Deps: 0)
- [ ] Md5Utils.java (Deps: 0)
- [ ] MoveSpeed.java (Deps: 0)
- [ ] Node.java (Deps: 0)
- [ ] NodeModel.java (Deps: 0)
- [ ] OpenGunPackDirEntry.java (Deps: 0)
- [ ] PairSerializer.java (Deps: 0)
- [ ] PerlinNoise.java (Deps: 0)
- [ ] PlayerNamePapi.java (Deps: 0)
- [ ] ReloadState.java (Deps: 0)
- [ ] ShellEjection.java (Deps: 0)
- [ ] TimelessItemNbtFactory.java (Deps: 0)
- [ ] TransformScale.java (Deps: 0)
- [ ] Vec3Serializer.java (Deps: 0)
- [ ] Vector3fSerializer.java (Deps: 0)
- [ ] AmmoClothConfig.java (Deps: 1)
- [ ] AttachmentIndexPOJO.java (Deps: 1)
- [ ] AttachmentItemTooltip.java (Deps: 1)
- [ ] BedrockPart.java (Deps: 1)
- [ ] CommonAmmoIndex.java (Deps: 1)
- [ ] DistanceDamagePairSerializer.java (Deps: 1)
- [ ] GunClothConfig.java (Deps: 1)
- [ ] GunResult.java (Deps: 1)
- [ ] IAttachment.java (Deps: 1)
- [ ] IgniteSerializer.java (Deps: 1)
- [ ] INetworkCacheReloadListener.java (Deps: 1)
- [ ] Interpolator.java (Deps: 1)
- [ ] KnockbackChange.java (Deps: 1)
- [ ] LiteralFilter.java (Deps: 1)
- [ ] LivingEntityAmmoCheck.java (Deps: 1)
- [ ] RegexFilter.java (Deps: 1)
- [ ] ServerConfig.java (Deps: 1)
- [ ] SoundEffectKeyframesSerializer.java (Deps: 1)
- [ ] TextShow.java (Deps: 1)
- [ ] ThirdPersonManager.java (Deps: 1)
- [ ] ZoomClothConfig.java (Deps: 1)
- [ ] AttachmentData.java (Deps: 2)
- [ ] GunReloadData.java (Deps: 2)
- [ ] BulletData.java (Deps: 3)
- [ ] CommonConfig.java (Deps: 3)
---

### **FASE 2: DEPENDENCIAS BAIXAS (1-3)** (259 arquivos)
*Arquivos com poucas dependencias internas do mod*

**Arquivos movidos da Fase 1 (dependem de outras classes do mod):**
- [ ] TacHitResult.java (Deps: 1 - depende de `EntityKineticBullet` que está na Fase 4)

- [ ] AbstractButtonMixin.java (Deps: 1)
- [ ] Accessor.java (Deps: 1)
- [ ] AllowAttachmentTagMatcher.java (Deps: 1)
- [ ] AmmoHitBlockEvent.java (Deps: 1)
- [ ] AmmoTransform.java (Deps: 1)
- [ ] AnimationChannel.java (Deps: 1)
- [ ] AnimationListener.java (Deps: 1)
- [ ] AnimationPlan.java (Deps: 1)
- [ ] AttachmentIdFix.java (Deps: 1)
- [ ] AttachmentLockCommand.java (Deps: 1)
- [ ] BedrockPolygon.java (Deps: 1)
- [ ] BedrockVersion.java (Deps: 1)
- [ ] BeforeRenderHandEvent.java (Deps: 1)
- [ ] BellRing.java (Deps: 1)
- [ ] BlackList.java (Deps: 1)
- [ ] BlockDisplay.java (Deps: 1)
- [ ] BlockIndexPOJO.java (Deps: 1)
- [ ] BonesItem.java (Deps: 1)
- [ ] BufferModel.java (Deps: 1)
- [ ] ClientMessageCraft.java (Deps: 1)
- [ ] ClientMessagePlayerAim.java (Deps: 1)
- [ ] ClientMessagePlayerBoltGun.java (Deps: 1)
- [ ] ClientMessagePlayerCancelReload.java (Deps: 1)
- [ ] ClientMessagePlayerDrawGun.java (Deps: 1)
- [ ] ClientMessagePlayerFireSelect.java (Deps: 1)
- [ ] ClientMessagePlayerMelee.java (Deps: 1)
- [ ] ClientMessagePlayerReloadGun.java (Deps: 1)
- [ ] ClientMessagePlayerShoot.java (Deps: 1)
- [ ] ClientMessagePlayerZoom.java (Deps: 1)
- [ ] ClothConfigScreen.java (Deps: 1)
- [ ] CommandRegistry.java (Deps: 1)
- [ ] CommonLoadPack.java (Deps: 1)
- [ ] ControllableCompat.java (Deps: 1)
- [ ] ConvertCommand.java (Deps: 1)
- [ ] CrosshairDropdown.java (Deps: 1)
- [ ] CrosshairType.java (Deps: 1)
- [ ] CubesItem.java (Deps: 1)
- [ ] DefaultAssets.java (Deps: 1)
- [ ] DummyAmmoCommand.java (Deps: 1)
- [ ] FaceUVsItem.java (Deps: 1)
- [ ] GeometryModelLegacy.java (Deps: 1)
- [ ] GunAmmo.java (Deps: 1)
- [ ] GunDrawEvent.java (Deps: 1)
- [ ] GunFinishReloadEvent.java (Deps: 1)
- [ ] GunFireSelectEvent.java (Deps: 1)
- [ ] GunItemManager.java (Deps: 1)
- [ ] GunMeleeEvent.java (Deps: 1)
- [ ] GunRecoil.java (Deps: 1)
- [ ] GunReloadEvent.java (Deps: 1)
- [ ] GunSmithTableBlockA.java (Deps: 1)
- [ ] GunSmithTableBlockB.java (Deps: 1)
- [ ] GunSmithTableBlockC.java (Deps: 1)
- [ ] GunTransform.java (Deps: 1)
- [ ] HumanoidModelMixin.java (Deps: 1)
- [ ] InspectKey.java (Deps: 1)
- [ ] ItemAnimationStateContext.java (Deps: 1)
- [ ] KubeJSCustomGunItem.java (Deps: 1)
- [ ] MeleeKey.java (Deps: 1)
- [ ] ModAttributes.java (Deps: 1)
- [ ] ModDamageTypes.java (Deps: 1)
- [ ] ModelRendererWrapper.java (Deps: 1)
- [ ] ModPainting.java (Deps: 1)
- [ ] ModSounds.java (Deps: 1)
- [ ] OverwriteCommand.java (Deps: 1)
- [ ] PlayerAnimatorAssetManager.java (Deps: 1)
- [ ] PlayerEnterWorld.java (Deps: 1)
- [ ] PlayGunSoundEvent.java (Deps: 1)
- [ ] PreLoadConfig.java (Deps: 1)
- [ ] PreventGunClick.java (Deps: 1)
- [ ] ReloadResourceEvent.java (Deps: 1)
- [ ] RenderConfig.java (Deps: 1)
- [ ] RenderDistance.java (Deps: 1)
- [ ] RenderItemInHandBobEvent.java (Deps: 1)
- [ ] RenderLevelBobEvent.java (Deps: 1)
- [ ] ResourceManager.java (Deps: 1)
- [ ] ResourceScanner.java (Deps: 1)
- [ ] ResultButton.java (Deps: 1)
- [ ] ServerGamePacketListenerImplMixin.java (Deps: 1)
- [ ] ServerMessageCraft.java (Deps: 1)
- [ ] ServerMessageGunDraw.java (Deps: 1)
- [ ] ServerMessageGunFire.java (Deps: 1)
- [ ] ServerMessageGunFireSelect.java (Deps: 1)
- [ ] ServerMessageGunHurt.java (Deps: 1)
- [ ] ServerMessageGunKill.java (Deps: 1)
- [ ] ServerMessageGunMelee.java (Deps: 1)
- [ ] ServerMessageGunReload.java (Deps: 1)
- [ ] ServerMessageGunShoot.java (Deps: 1)
- [ ] ServerMessageSwapItem.java (Deps: 1)
- [ ] ServerPlayerMixin.java (Deps: 1)
- [ ] SoundAssetsManager.java (Deps: 1)
- [ ] StatueBlockEntity.java (Deps: 1)
- [ ] SwapItemWithOffHand.java (Deps: 1)
- [ ] TargetMinecartItem.java (Deps: 1)
- [ ] ThrowableAnimationStateContext.java (Deps: 1)
- [ ] TimelessItemType.java (Deps: 1)
- [ ] TimelessKubeJSEventRegister.java (Deps: 1)
- [ ] TypeButton.java (Deps: 1)
- [ ] AbstractAccessorData.java (Deps: 2)
- [ ] Accessors.java (Deps: 2)
- [ ] AccessorSparse.java (Deps: 2)
- [ ] AccessorSparseUtils.java (Deps: 2)
- [ ] AdjustmentYRotModifier.java (Deps: 2)
- [ ] AmmoParticleSpawner.java (Deps: 2)
- [ ] Animation.java (Deps: 2)
- [ ] AnimationDataRegisterFactory.java (Deps: 2)
- [ ] AnimationKeyframesSerializer.java (Deps: 2)
- [ ] AnimationListenerSupplier.java (Deps: 2)
- [ ] AnimationModel.java (Deps: 2)
- [ ] AnimationState.java (Deps: 2)
- [ ] AttachmentPropertyEvent.java (Deps: 2)
- [ ] BedrockModelPOJO.java (Deps: 2)
- [ ] BlockData.java (Deps: 2)
- [ ] BlockItemBuilder.java (Deps: 2)
- [ ] BlockRayTrace.java (Deps: 2)
- [ ] BufferViewModel.java (Deps: 2)
- [ ] BulletHoleOption.java (Deps: 2)
- [ ] ClientAmmoBoxTooltip.java (Deps: 2)
- [ ] ClientMessagePlayerCrawl.java (Deps: 2)
- [ ] CommonAmmoIndexSerializer.java (Deps: 2)
- [ ] CommonAttachmentIndexSerializer.java (Deps: 2)
- [ ] CommonBlockIndexSerializer.java (Deps: 2)
- [ ] CommonGunIndexSerializer.java (Deps: 2)
- [ ] CustomGunItemBuilder.java (Deps: 2)
- [ ] DataEntry.java (Deps: 2)
- [ ] DataHolder.java (Deps: 2)
- [ ] DefaultTableItem.java (Deps: 2)
- [ ] DestroyGlassBlock.java (Deps: 2)
- [ ] EntityDamageEvent.java (Deps: 2)
- [ ] EntityKillByGunEvent.java (Deps: 2)
- [ ] EntityUtil.java (Deps: 2)
- [ ] ExplodeUtil.java (Deps: 2)
- [ ] FireSelectKey.java (Deps: 2)
- [ ] FunctionalBedrockPart.java (Deps: 2)
- [ ] GeometryModelNew.java (Deps: 2)
- [ ] GetJarResources.java (Deps: 2)
- [ ] GunFireEvent.java (Deps: 2)
- [ ] GunShootEvent.java (Deps: 2)
- [ ] GunSmithTableCategory.java (Deps: 2)
- [ ] GunSmithTableResult.java (Deps: 2)
- [ ] GunSmithTableResultComponents.java (Deps: 2)
- [ ] GunSmithTableResultInfo.java (Deps: 2)
- [ ] GunSoundInstance.java (Deps: 2)
- [ ] GunTooltip.java (Deps: 2)
- [ ] HideTooltipPartCommand.java (Deps: 2)
- [ ] HitboxHelperEvent.java (Deps: 2)
- [ ] IClientPlayerGunOperator.java (Deps: 2)
- [ ] InaccuracyType.java (Deps: 2)
- [ ] InteractKey.java (Deps: 2)
- [ ] InteractKeyConfigRead.java (Deps: 2)
- [ ] InventoryAttachmentSlot.java (Deps: 2)
- [ ] JsonDataManager.java (Deps: 2)
- [ ] JsonResourceLoader.java (Deps: 2)
- [ ] KeyClothConfig.java (Deps: 2)
- [ ] Linear.java (Deps: 2)
- [ ] LivingEntityHeat.java (Deps: 2)
- [ ] LoadingConfigEvent.java (Deps: 2)
- [ ] LocalPlayerDataHolder.java (Deps: 2)
- [ ] LocalPlayerMixin.java (Deps: 2)
- [ ] ModCapabilities.java (Deps: 2)
- [ ] ModContainer.java (Deps: 2)
- [ ] ModContainerScreen.java (Deps: 2)
- [ ] ModDataComponents.java (Deps: 2)
- [ ] ModEntities.java (Deps: 2)
- [ ] ModParticles.java (Deps: 2)
- [ ] ModSerializers.java (Deps: 2)
- [ ] ObjectAnimationSoundChannel.java (Deps: 2)
- [ ] OtherClothConfig.java (Deps: 2)
- [ ] PapiManager.java (Deps: 2)
- [ ] ParameterizedCache.java (Deps: 2)
- [ ] ParameterizedCachePair.java (Deps: 2)
- [ ] PlayerModelMixin.java (Deps: 2)
- [ ] PreventsHotbarEvent.java (Deps: 2)
- [ ] RefitKey.java (Deps: 2)
- [ ] RefitTurnPageButton.java (Deps: 2)
- [ ] RefitUnloadButton.java (Deps: 2)
- [ ] ReloadCommand.java (Deps: 2)
- [ ] RenderHeadShotAABB.java (Deps: 2)
- [ ] ScriptManager.java (Deps: 2)
- [ ] Serializers.java (Deps: 2)
- [ ] ServerMessageRefreshRefitScreen.java (Deps: 2)
- [ ] ServerMessageSound.java (Deps: 2)
- [ ] ServerMessageUpdateEntityData.java (Deps: 2)
- [ ] ServerPlayNetHandlerMixin.java (Deps: 2)
- [ ] ShooterDataHolder.java (Deps: 2)
- [ ] SLerp.java (Deps: 2)
- [ ] SoundManager.java (Deps: 2)
- [ ] Spline.java (Deps: 2)
- [ ] Step.java (Deps: 2)
- [ ] SyncBaseTimestamp.java (Deps: 2)
- [ ] TableRecipe.java (Deps: 2)
- [ ] TargetBlock.java (Deps: 2)
- [ ] TimelessCommonEvents.java (Deps: 2)
- [ ] TravelToDimensionEvent.java (Deps: 2)
- [ ] VersionChecker.java (Deps: 2)
- [ ] AccessorByteData.java (Deps: 3)
- [ ] AccessorFloatData.java (Deps: 3)
- [ ] AccessorIntData.java (Deps: 3)
- [ ] AccessorShortData.java (Deps: 3)
- [ ] Acknowledge.java (Deps: 3)
- [ ] AimKey.java (Deps: 3)
- [ ] AmmoItemBuilder.java (Deps: 3)
- [ ] AnimationStateMachine.java (Deps: 3)
- [ ] AttachmentItemBuilder.java (Deps: 3)
- [ ] AttachmentItemDataAccessor.java (Deps: 3)
- [ ] AttachmentQueryCategory.java (Deps: 3)
- [ ] BedrockCubeBox.java (Deps: 3)
- [ ] BlockItemDataAccessor.java (Deps: 3)
- [ ] CapabilityRegistry.java (Deps: 3)
- [ ] ChangeGunPropertyEvent.java (Deps: 3)
- [ ] ClientAttachmentSkinIndex.java (Deps: 3)
- [ ] ClientConfig.java (Deps: 3)
- [ ] ClientMessageLaserColor.java (Deps: 3)
- [ ] ClientMessageSyncBaseTimestamp.java (Deps: 3)
- [ ] ClientPreventGunClick.java (Deps: 3)
- [ ] CommonDataManager.java (Deps: 3)
- [ ] ConfigKey.java (Deps: 3)
- [ ] ConstraintTranslateListener.java (Deps: 3)
- [ ] CustomInterpolator.java (Deps: 3)
- [ ] DisplayManager.java (Deps: 3)
- [ ] EntityHurtByGunEvent.java (Deps: 3)
- [ ] GameRendererMixin.java (Deps: 3)
- [ ] GunModelTypeManager.java (Deps: 3)
- [ ] GunPackList.java (Deps: 3)
- [ ] GunProperties.java (Deps: 3)
- [ ] GunSmithTableBlockEntity.java (Deps: 3)
- [ ] GunSmithTableItemRenderer.java (Deps: 3)
- [ ] ItemInHandRendererMixin.java (Deps: 3)
- [ ] KillAmountOverlay.java (Deps: 3)
- [ ] KubeJSGunEventPoster.java (Deps: 3)
- [ ] LeftHandRender.java (Deps: 3)
- [ ] LivingEntityCrawl.java (Deps: 3)
- [ ] LivingEntitySprint.java (Deps: 3)
- [ ] LocalPlayerSprint.java (Deps: 3)
- [ ] LuaAnimationConstant.java (Deps: 3)
- [ ] LuaAnimationState.java (Deps: 3)
- [ ] LuaGunLogicConstant.java (Deps: 3)
- [ ] ModelAdditionalMagazineListener.java (Deps: 3)
- [ ] ModelScaleListener.java (Deps: 3)
- [ ] ModItems.java (Deps: 3)
- [ ] ModRecipe.java (Deps: 3)
- [ ] ObjectAnimationChannel.java (Deps: 3)
- [ ] OculusCompat.java (Deps: 3)
- [ ] PackConvertor.java (Deps: 3)
- [ ] PackInfoManager.java (Deps: 3)
- [ ] ParticleFactoryRegistry.java (Deps: 3)
- [ ] PlayerAnimatorLoader.java (Deps: 3)
- [ ] PlayerHurtByGunEvent.java (Deps: 3)
- [ ] ProjectileExplosion.java (Deps: 3)
- [ ] RefitTransform.java (Deps: 3)
- [ ] RefreshClonePlayerDataEvent.java (Deps: 3)
- [ ] RenderClothConfig.java (Deps: 3)
- [ ] RightHandRender.java (Deps: 3)
- [ ] ServerMessageSyncGunPack.java (Deps: 3)
- [ ] SlotModel.java (Deps: 3)
- [ ] StatueBlock.java (Deps: 3)
- [ ] SyncedDataKey.java (Deps: 3)
- [ ] TimelessForgeEventWrappers.java (Deps: 3)
- [ ] TimelessGunSmithTableRecipeSchema.java (Deps: 3)
- [ ] ZoomKey.java (Deps: 3)
---

### **FASE 3: DEPENDENCIAS MEDIAS (4-10)** (137 arquivos)
*Arquivos com dependencias moderadas*

**Arquivos movidos da Fase 1 (dependem de outras classes do mod):**
- [ ] RootCommand.java (Deps: 0 - mas depende de sub-comandos como `AttachmentLockCommand`, `DummyAmmoCommand`, etc.)
- [ ] GunModSubtype.java (Deps: 0 - mas depende da interface `IGun`)

- [ ] AbstractGunSmithTableBlock.java (Deps: 4)
- [ ] AmmoCountPapi.java (Deps: 4)
- [ ] AmmoNbtFactory.java (Deps: 4)
- [ ] AnimationController.java (Deps: 4)
- [ ] AttachmentDisplay.java (Deps: 4)
- [ ] AttachmentNbtFactory.java (Deps: 4)
- [ ] BedrockAmmoModel.java (Deps: 4)
- [ ] BulletHoleParticle.java (Deps: 4)
- [ ] CameraRotateListener.java (Deps: 4)
- [ ] ClientBlockItemTooltip.java (Deps: 4)
- [ ] CommonAttachmentIndex.java (Deps: 4)
- [ ] CommonBlockIndex.java (Deps: 4)
- [ ] CommonGunIndex.java (Deps: 4)
- [ ] CommonRegistry.java (Deps: 4)
- [ ] CompatRegistry.java (Deps: 4)
- [ ] ConstraintRotateListener.java (Deps: 4)
- [ ] ControllableInner.java (Deps: 4)
- [ ] CrawlKey.java (Deps: 4)
- [ ] GltfManager.java (Deps: 4)
- [ ] GunAttachmentSlot.java (Deps: 4)
- [ ] GunSmithTableRecipe.java (Deps: 4)
- [ ] HSVSliderGroup.java (Deps: 4)
- [ ] HumanoidOffhandRender.java (Deps: 4)
- [ ] IAttachmentModifier.java (Deps: 4)
- [ ] IGun.java (Deps: 4)
- [ ] IGunOperator.java (Deps: 4)
- [ ] InteractKeyTextOverlay.java (Deps: 4)
- [ ] ItemInHandLayerMixin.java (Deps: 4)
- [ ] LocalPlayerCrawl.java (Deps: 4)
- [ ] LuaAnimationStateMachine.java (Deps: 4)
- [ ] ModBlocks.java (Deps: 4)
- [ ] ModelRotateListener.java (Deps: 4)
- [ ] PlayerRespawnEvent.java (Deps: 4)
- [ ] RecipeFilter.java (Deps: 4)
- [ ] SyncedEntityDataEvent.java (Deps: 4)
- [ ] TargetBlockEntity.java (Deps: 4)
- [ ] TargetMinecartRenderer.java (Deps: 4)
- [ ] TextShowRender.java (Deps: 4)
- [ ] TickAnimationEvent.java (Deps: 4)
- [ ] TimelessServerEvents.java (Deps: 4)
- [ ] AccessorModel.java (Deps: 5)
- [ ] AmmoBoxItemDataAccessor.java (Deps: 5)
- [ ] AmmoDisplay.java (Deps: 5)
- [ ] AmmoItemDataAccessor.java (Deps: 5)
- [ ] AttachmentCacheProperty.java (Deps: 5)
- [ ] AttachmentsTagManager.java (Deps: 5)
- [ ] BedrockCubePerFace.java (Deps: 5)
- [ ] CameraAnimationObject.java (Deps: 5)
- [ ] ClientMessageRefitGun.java (Deps: 5)
- [ ] ClientMessageUnloadAttachment.java (Deps: 5)
- [ ] FirstPersonRenderEvent.java (Deps: 5)
- [ ] GunSmithTableSerializer.java (Deps: 5)
- [ ] InterpolatorUtil.java (Deps: 5)
- [ ] InventoryEvent.java (Deps: 5)
- [ ] LuaGunAnimationConstant.java (Deps: 5)
- [ ] LuaStateMachineFactory.java (Deps: 5)
- [ ] ModelTranslateListener.java (Deps: 5)
- [ ] NetworkHandler.java (Deps: 5)
- [ ] ObjectAnimation.java (Deps: 5)
- [ ] ObjectAnimationRunner.java (Deps: 5)
- [ ] RawAnimationStructure.java (Deps: 5)
- [ ] ReloadKey.java (Deps: 5)
- [ ] ServerMessageSyncBaseTimestamp.java (Deps: 5)
- [ ] StatueRenderer.java (Deps: 5)
- [ ] TooltipEvent.java (Deps: 5)
- [ ] AccessorDatas.java (Deps: 6)
- [ ] AmmoItemRenderer.java (Deps: 6)
- [ ] AttachmentDataManager.java (Deps: 6)
- [ ] AttachmentItemRenderer.java (Deps: 6)
- [ ] AttachmentQueryEntry.java (Deps: 6)
- [ ] BedrockModel.java (Deps: 6)
- [ ] ClientAmmoIndex.java (Deps: 6)
- [ ] ClientBlockIndex.java (Deps: 6)
- [ ] ClientGunIndex.java (Deps: 6)
- [ ] GunPackLoader.java (Deps: 6)
- [ ] GunSmithTableItem.java (Deps: 6)
- [ ] GunSmithTableResultSerializer.java (Deps: 6)
- [ ] InnerThirdPersonManager.java (Deps: 6)
- [ ] LaserColorUtil.java (Deps: 6)
- [ ] MenuIntegration.java (Deps: 6)
- [ ] PlayerAnimatorCompat.java (Deps: 6)
- [ ] RecipeFilterManager.java (Deps: 6)
- [ ] SyncedEntityData.java (Deps: 6)
- [ ] TabConfig.java (Deps: 6)
- [ ] TargetRenderer.java (Deps: 6)
- [ ] TimelessClientEvents.java (Deps: 6)
- [ ] AnimationStateContext.java (Deps: 7)
- [ ] ConstraintObject.java (Deps: 7)
- [ ] EntityBulletRenderer.java (Deps: 7)
- [ ] ExtraMovementModifier.java (Deps: 7)
- [ ] GunKubeJSEvents.java (Deps: 7)
- [ ] GunMod.java (Deps: 7)
- [ ] GunSmithTableRenderer.java (Deps: 7)
- [ ] LivingEntityFireSelect.java (Deps: 7)
- [ ] LivingEntityMixin.java (Deps: 7)
- [ ] ModSyncedEntityData.java (Deps: 7)
- [ ] ServerMessageSyncedEntityDataMapping.java (Deps: 7)
- [ ] ShootKey.java (Deps: 7)
- [ ] AnimateGeoItemRenderer.java (Deps: 8)
- [ ] AttachmentRender.java (Deps: 8)
- [ ] ClientHitMark.java (Deps: 8)
- [ ] GunItemBuilder.java (Deps: 8)
- [ ] GunNbtFactory.java (Deps: 8)
- [ ] ICommonResourceProvider.java (Deps: 8)
- [ ] IgniteModifier.java (Deps: 8)
- [ ] InternalAssetLoader.java (Deps: 8)
- [ ] LivingEntityBolt.java (Deps: 8)
- [ ] LivingEntityDrawGun.java (Deps: 8)
- [ ] LivingEntitySpeedModifier.java (Deps: 8)
- [ ] LocalPlayerAim.java (Deps: 8)
- [ ] AdsModifier.java (Deps: 9)
- [ ] AimInaccuracyModifier.java (Deps: 9)
- [ ] AmmoBoxItem.java (Deps: 9)
- [ ] AmmoItem.java (Deps: 9)
- [ ] AttachmentItem.java (Deps: 9)
- [ ] AttachmentPropertyManager.java (Deps: 9)
- [ ] EffectiveRangeModifier.java (Deps: 9)
- [ ] ExplosionModifier.java (Deps: 9)
- [ ] GunPropertyDiagrams.java (Deps: 9)
- [ ] GunSmithTableMenu.java (Deps: 9)
- [ ] HeatBarOverlay.java (Deps: 9)
- [ ] LocalPlayerDraw.java (Deps: 9)
- [ ] LocalPlayerInspect.java (Deps: 9)
- [ ] ModEntitiesRender.java (Deps: 9)
- [ ] PierceModifier.java (Deps: 9)
- [ ] RawGunTableResult.java (Deps: 9)
- [ ] SilenceModifier.java (Deps: 9)
- [ ] SoundPlayManager.java (Deps: 9)
- [ ] WeightModifier.java (Deps: 9)
- [ ] AnimationStructure.java (Deps: 10)
- [ ] BeamRenderer.java (Deps: 10)
- [ ] ClientAttachmentItemTooltip.java (Deps: 10)
- [ ] MouseHandlerMixin.java (Deps: 10)
- [ ] ShellRender.java (Deps: 10)
- [ ] TargetMinecart.java (Deps: 10)
- [ ] TimelessItemWrapper.java (Deps: 10)
- [ ] TimelessRecipeJS.java (Deps: 10)
---

### **FASE 4: DEPENDENCIAS ALTAS (11+)** (51 arquivos)
*Arquivos complexos do nucleo do mod*

- [ ] AnimationManager.java (Deps: 11)
- [ ] BedrockAttachmentModel.java (Deps: 11)
- [ ] GunModPlugin.java (Deps: 11)
- [ ] LivingEntityAim.java (Deps: 11)
- [ ] LivingEntityReload.java (Deps: 11)
- [ ] LocalPlayerFireSelect.java (Deps: 11)
- [ ] MuzzleFlashRender.java (Deps: 11)
- [ ] RpmModifier.java (Deps: 11)
- [ ] AmmoSpeedModifier.java (Deps: 12)
- [ ] GunItemDataAccessor.java (Deps: 12)
- [ ] LocalPlayerBolt.java (Deps: 12)
- [ ] RecoilModifier.java (Deps: 12)
- [ ] RenderCrosshairEvent.java (Deps: 12)
- [ ] ClientAttachmentIndex.java (Deps: 13)
- [ ] InaccuracyModifier.java (Deps: 13)
- [ ] KnockbackModifier.java (Deps: 13)
- [ ] ModCreativeTabs.java (Deps: 13)
- [ ] TimelessKubeJSPlugin.java (Deps: 13)
- [ ] CommonNetworkCache.java (Deps: 14)
- [ ] GunDisplay.java (Deps: 14)
- [ ] LivingEntityMelee.java (Deps: 14)
- [ ] LocalPlayerMelee.java (Deps: 14)
- [ ] ModernKineticGunItem.java (Deps: 14)
- [ ] ArmorIgnoreModifier.java (Deps: 15)
- [ ] ClientIndexManager.java (Deps: 15)
- [ ] DamageModifier.java (Deps: 15)
- [ ] GunHudOverlay.java (Deps: 15)
- [ ] HeadShotModifier.java (Deps: 15)
- [ ] LivingEntityShoot.java (Deps: 15)
- [ ] AbstractGunItem.java (Deps: 16)
- [ ] Animations.java (Deps: 16)
- [ ] AttachmentDataUtils.java (Deps: 16)
- [ ] GunRefitScreen.java (Deps: 16)
- [ ] LocalPlayerReload.java (Deps: 16)
- [ ] TimelessAPI.java (Deps: 16)
- [ ] BedrockAnimatedModel.java (Deps: 17)
- [ ] BedrockGunModel.java (Deps: 17)
- [ ] ClientGunTooltip.java (Deps: 18)
- [ ] GunData.java (Deps: 18)
- [ ] GunAnimationStateContext.java (Deps: 19)
- [ ] ClientSetupEvent.java (Deps: 20)
- [ ] GunDisplayInstance.java (Deps: 20)
- [ ] FirstPersonRenderGunEvent.java (Deps: 21)
- [ ] GunItemRendererWrapper.java (Deps: 21)
- [ ] CameraSetupEvent.java (Deps: 22)
- [ ] CommonAssetsManager.java (Deps: 22)
- [ ] GunSmithTableScreen.java (Deps: 22)
- [ ] LocalPlayerShoot.java (Deps: 22)
- [ ] ModernKineticGunScriptAPI.java (Deps: 24)
- [ ] EntityKineticBullet.java (Deps: 25)
- [ ] ClientAssetsManager.java (Deps: 27)
---

## WORKFLOW DE EXECUCAO

Para cada arquivo na ordem das fases:

1. **Habilitar:** Renomeie .java.disabled para .java
2. **Corrigir:** Adapte o codigo para NeoForge 1.21.1 APIs
3. **Testar:** Execute ./gradlew compileJava
4. **Validar:** Se compila, continue. Se falha, reverta e analise dependencias

## REGRAS IMPORTANTES

- âœ… **SEMPRE** siga a ordem das fases rigorosamente  
- âœ… **NUNCA** pule arquivos dentro de uma fase  
- âœ… **SEMPRE** teste a compilacao apos cada arquivo habilitado  
- âœ… **REVERTA** imediatamente se houver falha de compilacao  

---

*Plano gerado automaticamente em 2025-07-02 18:01:20 pelo Generate-Plan.ps1 v3.0*
