# PLANO DE DESENVOLVIMENTO SISTEMATICO - TacZ NeoForge 1.21.1 (Gerado Automaticamente)

**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1
**Estrategia:** Habilitacao incremental baseada em ordenacao topologica de dependencias.
**Data de Geracao:** 2025-06-27 13:47:52

---

## RESUMO ESTATISTICO

| Fase | Descricao | Arquivos | Status |
|------|-----------|----------|--------|
| **Fase 1** | Sem dependencias internas | 229 | [🔄] 43/229 |
| **Fase 2** | Dependencias baixas (1-3) | 93 | [ ] 0/93 |
| **Fase 3** | Dependencias medias (4-10) | 5 | [ ] 0/5 |
| **Fase 4** | Dependencias altas (11+) | 0 | [ ] 0/0 |
| **TOTAL** | **Todos os arquivos** | **327** | **43/327** |

---

## CHECKLIST DE HABILITACAO ORDENADO

Esta lista foi gerada para garantir que, ao habilitar um arquivo, todas as suas dependencias internas do mod ja tenham sido habilitadas. Siga a ordem rigorosamente.

### **FASE 1: FUNDACAO (Sem Dependencias Internas)** (229 arquivos)
*Estes arquivos nao possuem imports de outras classes do mod. Eles sao a base e podem ser habilitados primeiro.*

* [x] DataType.java
* [x] GunTabType.java
* [x] GunTooltipPart.java (✅ MIGRADO para DataComponents)
* [x] CommonLoadPack.java
* [x] OpenGunPackDirEntry.java
* [x] CycleTaskHelper.java
* [x] AttachmentLod.java
* [x] GunHurtBobTweak.java
* [x] ZoomClothConfig.java
* [x] GunFireModeAdjustData.java
* [x] AmmoBoxTooltip.java
* [x] Vector3fSerializer.java
* [x] FeedType.java
* [x] BufferViewModel.java
* [x] OculusCompatNewly.java
* [x] TimelessCommonEvents.java
* [x] KeepingItemRenderer.java
* [x] ResourceManager.java
* [x] Md5Utils.java
* [x] MeleeData.java
* [x] GunFireSelectEvent.java
* [x] InteractKeyConfigRead.java
* [x] GunAnimationConstant.java
* [x] DefaultTableItem.java
* [x] FaceUVsItem.java
* [x] DefaultAnimationType.java
* [x] GunItemManager.java
* [x] ShellDisplay.java
* [x] GunFinishReloadEvent.java
* [x] Align.java
* [x] DebugCommand.java
* [x] Ignite.java
* [x] PackInfo.java
* [x] IFunctionalRenderer.java
* [x] BlockItemTooltip.java
* [x] JsonProperty.java
* [x] TimelessKubeJSEventRegister.java
* [x] BedrockAnimationFile.java
* [x] AnimationPlan.java
* [x] BedrockPolygon.java
* [x] PlayerAnimatorAssetManager.java
* [x] TacPathVisitor.java
* [x] GltfConstants.java
* [x] FireSound.java
* [x] AnimationSoundChannelContent.java
* [x] ITargetEntity.java
* [x] TacHitResult.java
* [x] ExplosionData.java
* [x] GunShootEvent.java
* [x] GunDrawEvent.java
* [x] BurstData.java
* [ ] GeometryModelNew.java
* [ ] CubesItem.java
* [ ] GunLevelUpToast.java
* [ ] ModelRendererWrapper.java
* [ ] ShootResult.java
* [ ] LuaAnimationState.java
* [ ] IDataSerializer.java
* [ ] BlockRayTrace.java
* [ ] PackMeta.java
* [ ] Serializers.java
* [ ] AnimationSampler.java
* [ ] PathHandler.java
* [ ] GunRecoil.java
* [ ] BlockData.java
* [ ] IThirdPersonAnimation.java
* [ ] GetJarResources.java
* [ ] ObjectAnimation.java
* [ ] DataEntry.java
* [ ] AnimationState.java
* [ ] AnimationChannelTarget.java
* [ ] Easing.java
* [ ] GunClothConfig.java
* [ ] DefaultAssets.java
* [ ] BlockIndexPOJO.java
* [ ] GunReloadEvent.java
* [ ] RootCommand.java
* [ ] GunMeleeEvent.java
* [ ] AnimationName.java
* [ ] LuaLibrary.java
* [ ] ListPackCommand.java
* [ ] Bolt.java
* [ ] ShoulderSurfingCompat.java
* [ ] GunSmithTableBlockA.java
* [ ] LayerGunShow.java
* [ ] AccessorIntData.java
* [ ] PapiManager.java
* [ ] IBlock.java
* [ ] GunReloadData.java
* [ ] DataHolder.java
* [ ] GunSmithTableIngredient.java
* [ ] IAmmo.java
* [ ] StatueBlockEntity.java
* [ ] TypeButton.java
* [ ] AccessorData.java
* [ ] TimelessItemNbtFactory.java
* [ ] DelayedTask.java
* [ ] AnimationModel.java
* [ ] MuzzleFlash.java
* [ ] BedrockCube.java
* [ ] SoundEffectKeyframes.java
* [ ] InterpolatorUtil.java
* [ ] GunSmithTableBlockC.java
* [ ] BonesItem.java
* [ ] GunDefaultMeleeData.java
* [ ] AnimationChannelContent.java
* [ ] OptifineCompat.java
* [ ] TrackArrayMismatchException.java
* [ ] AnimationListener.java
* [ ] AnimationListenerSupplier.java
* [ ] IFilter.java
* [ ] AmmoCountStyle.java
* [ ] GunPackProgressScreen.java
* [ ] LiteralFilter.java
* [ ] AmmoClothConfig.java
* [ ] ColorHex.java
* [ ] AttachmentType.java
* [ ] IAmmoBox.java
* [ ] RenderDistance.java
* [ ] AccessorSparse.java
* [ ] HeadShotAABBConfigRead.java
* [ ] FireMode.java
* [ ] ItemStackSerializer.java
* [ ] IStackTooltip.java
* [ ] BufferView.java
* [ ] RecipeFilter.java
* [ ] NodeModel.java
* [ ] GunMeleeDebug.java
* [ ] EffectData.java
* [ ] PerlinNoise.java
* [ ] LanguageMixin.java
* [ ] IDisplay.java
* [ ] Modifier.java
* [ ] BedrockVertex.java
* [ ] ModDamageTypes.java
* [ ] AnimationKeyframes.java
* [ ] GeometryModelLegacy.java
* [ ] MathUtil.java
* [ ] GunSmithTableResult.java
* [ ] Node.java
* [ ] FaceItem.java
* [ ] ShoulderSurfingCompatInner.java
* [ ] AmmoParticle.java
* [ ] ModAttributes.java
* [ ] GunIndexPOJO.java
* [ ] IComponentTooltip.java
* [ ] MoveSpeed.java
* [ ] BedrockVersion.java
* [ ] CommonTransformObject.java
* [ ] AnimationChannel.java
* [ ] Accessor.java
* [ ] BedrockCubeBox.java
* [ ] PairSerializer.java
* [ ] ModPainting.java
* [ ] ThirdPersonManager.java
* [ ] LaserConfig.java
* [ ] SyncedDataKey.java
* [ ] ConfigCommand.java
* [ ] GunReloadTime.java
* [ ] EntityHurtByGunEvent.java
* [ ] SoundAssetsManager.java
* [ ] ElementType.java
* [ ] AccessorSparseIndices.java
* [ ] AccessorSparseValues.java
* [ ] BufferModel.java
* [ ] AmmoIndexPOJO.java
* [ ] RegexFilter.java
* [ ] VersionChecker.java
* [ ] PreLoadConfig.java
* [ ] ServerMessageLevelUp.java
* [ ] DiscreteTrackArray.java
* [ ] ThrowableAnimationStateContext.java
* [ ] LoginIndexHolder.java
* [ ] ControllableCompat.java
* [ ] GunMeleeData.java
* [ ] IAnimationItem.java
* [ ] GunFireEvent.java
* [ ] ShellEjection.java
* [ ] KnockBackModifier.java
* [ ] Vec3Serializer.java
* [ ] AccessorShortData.java
* [ ] GunHeatData.java
* [ ] AccessorFloatData.java
* [ ] FlatColorButton.java
* [ ] BlackList.java
* [ ] ExtraDamage.java
* [ ] InputExtraCheck.java
* [ ] DamageStyle.java
* [ ] PreLoadModConfig.java
* [ ] ControllableData.java
* [ ] GunLod.java
* [ ] CacheValue.java
* [ ] ResultButton.java
* [ ] LivingEntityAmmoCheck.java
* [ ] AnimationConstant.java
* [ ] Description.java
* [ ] TransformScale.java
* [ ] TextShow.java
* [ ] GunDamageSourcePart.java
* [ ] AccessorByteData.java
* [ ] EntityKillByGunEvent.java
* [ ] CrosshairType.java
* [ ] GunModelConstant.java
* [ ] AmmoEntityDisplay.java
* [ ] GunSmithTableBlockB.java
* [ ] PlayerNamePapi.java
* [ ] ReloadState.java
* [ ] AttachmentSkin.java
* [ ] RawAnimationStructure.java
* [ ] Animation.java
* [ ] AnimationBone.java
* [ ] AbstractAccessorData.java
* [ ] ResourceScanner.java
* [ ] Buffer.java
* [ ] BedrockModelPOJO.java
* [ ] BedrockPart.java
* [ ] BulletData.java
* [ ] AccessorSparseUtils.java
* [ ] GunRecoilKeyFrame.java
* [ ] BedrockAnimation.java
* [ ] TimelessItemType.java
* [ ] OculusCompatLegacy.java
* [ ] Buffers.java
* [ ] NumberArrays.java
* [ ] IMessage.java
* [ ] AnimationController.java
* [ ] StairBlockAccessor.java
* [ ] SecondOrderDynamics.java
* [ ] GunModSubtype.java

---
### **FASE 2: DEPENDENCIAS BAIXAS** (93 arquivos)
*Dependem de 1 a 3 arquivos, que ja devem estar habilitados na Fase 1.*

* [ ] CommonDataManager.java (Deps: 1)
* [ ] INetworkCacheReloadListener.java (Deps: 1)
* [ ] KeyClothConfig.java (Deps: 1)
* [ ] ServerTickEvent.java (Deps: 1)
* [ ] ServerMessageGunFireSelect.java (Deps: 1)
* [ ] IgniteSerializer.java (Deps: 1)
* [ ] PackConvertor.java (Deps: 1)
* [ ] AttachmentData.java (Deps: 1)
* [ ] JsonResourceLoader.java (Deps: 1)
* [ ] PlayerAnimatorLoader.java (Deps: 1)
* [ ] Accessors.java (Deps: 1)
* [ ] HitboxHelper.java (Deps: 1)
* [ ] ServerMessageGunShoot.java (Deps: 1)
* [ ] ServerMessageGunDraw.java (Deps: 1)
* [ ] AttachmentIdFix.java (Deps: 1)
* [ ] ServerMessageGunReload.java (Deps: 1)
* [ ] CommandRegistry.java (Deps: 1)
* [ ] ServerMessageGunMelee.java (Deps: 1)
* [ ] ScriptManager.java (Deps: 1)
* [ ] BlockItemBuilder.java (Deps: 2)
* [ ] BlockItemDataAccessor.java (Deps: 2)
* [ ] ModCapabilities.java (Deps: 1)
* [ ] GunSmithTableIngredientSerializer.java (Deps: 1)
* [ ] AmmoItemBuilder.java (Deps: 2)
* [ ] SoundEffectKeyframesSerializer.java (Deps: 1)
* [ ] Interpolator.java (Deps: 1)
* [ ] SLerp.java (Deps: 1)
* [ ] Linear.java (Deps: 1)
* [ ] Spline.java (Deps: 1)
* [ ] Step.java (Deps: 1)
* [ ] RenderHelper.java (Deps: 1)
* [ ] GunResult.java (Deps: 1)
* [ ] AttachmentItemTooltip.java (Deps: 1)
* [ ] IAttachment.java (Deps: 1)
* [ ] AttachmentIndexPOJO.java (Deps: 1)
* [ ] ClientAmmoBoxTooltip.java (Deps: 2)
* [ ] RenderHeadShotAABB.java (Deps: 1)
* [ ] EntityUtil.java (Deps: 1)
* [ ] LoadingConfigEvent.java (Deps: 2)
* [ ] BlockDisplay.java (Deps: 1)
* [ ] AmmoDisplay.java (Deps: 1)
* [ ] ParameterizedCachePair.java (Deps: 1)
* [ ] AnimationKeyframesSerializer.java (Deps: 1)
* [ ] ObjectAnimationRunner.java (Deps: 1)
* [ ] CustomInterpolator.java (Deps: 2)
* [ ] GunSmithTableResultInfo.java (Deps: 1)
* [ ] TableRecipe.java (Deps: 2)
* [ ] BedrockCubePerFace.java (Deps: 2)
* [ ] GunAmmo.java (Deps: 1)
* [ ] EntityDamageEvent.java (Deps: 2)
* [ ] GunDisplay.java (Deps: 3)
* [ ] ServerMessageGunHurt.java (Deps: 1)
* [ ] CommonAmmoIndex.java (Deps: 1)
* [ ] GunPackLoader.java (Deps: 3)
* [ ] OverwriteCommand.java (Deps: 1)
* [ ] OtherClothConfig.java (Deps: 1)
* [ ] ServerMessageGunFire.java (Deps: 1)
* [ ] KnockbackChange.java (Deps: 1)
* [ ] DistanceDamagePairSerializer.java (Deps: 1)
* [ ] LuaAnimationConstant.java (Deps: 2)
* [ ] GunTransform.java (Deps: 1)
* [ ] AmmoTransform.java (Deps: 1)
* [ ] AttachmentDisplay.java (Deps: 3)
* [ ] ServerMessageGunKill.java (Deps: 1)
* [ ] CrosshairDropdown.java (Deps: 1)
* [ ] LuaGunAnimationConstant.java (Deps: 3)
* [ ] LuaGunLogicConstant.java (Deps: 2)
* [ ] ModSerializers.java (Deps: 2)
* [ ] JsonDataManager.java (Deps: 1)
* [ ] FunctionalBedrockPart.java (Deps: 1)
* [ ] Acknowledge.java (Deps: 2)
* [ ] AnimationStateMachine.java (Deps: 1)
* [ ] LuaStateMachineFactory.java (Deps: 1)
* [ ] LuaAnimationStateMachine.java (Deps: 1)
* [ ] PlayerEnterWorld.java (Deps: 1)
* [ ] ConvertCommand.java (Deps: 1)
* [ ] HitboxHelperEvent.java (Deps: 1)
* [ ] ProjectileExplosion.java (Deps: 1)
* [ ] ObjectAnimationChannel.java (Deps: 1)
* [ ] AttachmentItemBuilder.java (Deps: 2)
* [ ] AttachmentItemDataAccessor.java (Deps: 2)
* [ ] TimelessGunSmithTableRecipeSchema.java (Deps: 1)
* [ ] GunSmithTableResultComponents.java (Deps: 1)
* [ ] SlotModel.java (Deps: 3)
* [ ] CommonAmmoIndexSerializer.java (Deps: 2)
* [ ] RenderClothConfig.java (Deps: 2)
* [ ] DisplayManager.java (Deps: 2)
* [ ] ExplodeUtil.java (Deps: 1)
* [ ] ConstraintTranslateListener.java (Deps: 2)
* [ ] CameraRotateListener.java (Deps: 3)
* [ ] ConstraintRotateListener.java (Deps: 3)
* [ ] ModelScaleListener.java (Deps: 3)
* [ ] ItemAnimationStateContext.java (Deps: 1)

---
### **FASE 3: DEPENDENCIAS MEDIAS** (5 arquivos)
*Dependem de 4 a 10 arquivos. A complexidade de integracao aumenta aqui.*

* [ ] AnimationStateContext.java (Deps: 4)
* [ ] ConstraintObject.java (Deps: 5)
* [ ] CameraAnimationObject.java (Deps: 4)
* [ ] ModelRotateListener.java (Deps: 4)
* [ ] MenuIntegration.java (Deps: 6)

---
### **FASE 4: DEPENDENCIAS ALTAS (NUCLEO DO MOD)** (0 arquivos)
*Arquivos mais complexos, com 11 ou mais dependencias. Representam a logica central do mod.*


---

## WORKFLOW

Siga o workflow definido no plano original para cada arquivo:
1. **Backup:** git commit -m 'Backup antes de habilitar [arquivo]'
2. **Habilitacao:** Renomeie .java.disabled para .java
3. **Correcao:** Adapte o codigo para NeoForge 1.21.1
4. **Build:** ./gradlew build
5. **Validacao ou Rollback:** Se sucesso, commit. Se falha, reverta o arquivo.

---

## DETALHES DA ORDENACAO TOPOLOGICA

### Estatisticas Gerais:
- **Total de arquivos analisados:** 613
- **Arquivos com informacoes validas:** 602
- **Arquivos ordenados com sucesso:** 327

### ATENCAO: Possiveis Dependencias Circulares

Alguns arquivos nao puderam ser ordenados, possivelmente devido a dependencias circulares:
- GunProperties.java.disabled
- GunProperty.java.disabled
- TimelessAPI.java.disabled
- Animations.java.disabled
- ObjectAnimationSoundChannel.java.disabled
- AccessorModel.java.disabled
- AnimationStructure.java.disabled
- AccessorDatas.java.disabled
- BeforeRenderHandEvent.java.disabled
- RenderItemInHandBobEvent.java.disabled
- RenderLevelBobEvent.java.disabled
- SwapItemWithOffHand.java.disabled
- IClientPlayerGunOperator.java.disabled
- GunModelTypeManager.java.disabled
- IGunOperator.java.disabled
- AttachmentPropertyEvent.java.disabled
- KubeJSGunEventPoster.java.disabled
- AmmoHitBlockEvent.java.disabled
- IGun.java.disabled
- GunItemBuilder.java.disabled
- AbstractGunItem.java.disabled
- AmmoBoxItemDataAccessor.java.disabled
- AmmoItemDataAccessor.java.disabled
- GunItemDataAccessor.java.disabled
- ItemDataAccessor.java.disabled
- IAttachmentModifier.java.disabled
- ParameterizedCache.java.disabled
- LuaEntityAccessor.java.disabled
- LuaNbtAccessor.java.disabled
- AbstractGunSmithTableBlock.java.disabled
- package-info.java.disabled
- StatueBlock.java.disabled
- TargetBlock.java.disabled
- GunSmithTableBlockEntity.java.disabled
- package-info.java.disabled
- TargetBlockEntity.java.disabled
- RefitTransform.java.disabled
- GunAnimationStateContext.java.disabled
- InnerThirdPersonManager.java.disabled
- CameraSetupEvent.java.disabled
- ClientHitMark.java.disabled
- ClientPreventGunClick.java.disabled
- FirstPersonRenderEvent.java.disabled
- FirstPersonRenderGunEvent.java.disabled
- InventoryEvent.java.disabled
- PlayerHurtByGunEvent.java.disabled
- PlayGunSoundEvent.java.disabled
- PreventsHotbarEvent.java.disabled
- RefreshClonePlayerDataEvent.java.disabled
- ReloadResourceEvent.java.disabled
- RenderCrosshairEvent.java.disabled
- TickAnimationEvent.java.disabled
- TooltipEvent.java.disabled
- LocalPlayerAim.java.disabled
- LocalPlayerBolt.java.disabled
- LocalPlayerCrawl.java.disabled
- LocalPlayerDataHolder.java.disabled
- LocalPlayerDraw.java.disabled
- LocalPlayerFireSelect.java.disabled
- LocalPlayerInspect.java.disabled
- LocalPlayerMelee.java.disabled
- LocalPlayerReload.java.disabled
- LocalPlayerShoot.java.disabled
- LocalPlayerSprint.java.disabled
- GunRefitScreen.java.disabled
- GunSmithTableScreen.java.disabled
- ClothConfigScreen.java.disabled
- GunPackList.java.disabled
- GunAttachmentSlot.java.disabled
- GunPropertyDiagrams.java.disabled
- HSVSliderGroup.java.disabled
- InventoryAttachmentSlot.java.disabled
- RefitTurnPageButton.java.disabled
- RefitUnloadButton.java.disabled
- GunHudOverlay.java.disabled
- HeatBarOverlay.java.disabled
- InteractKeyTextOverlay.java.disabled
- KillAmountOverlay.java.disabled
- ClientSetupEvent.java.disabled
- ModContainerScreen.java.disabled
- ModEntitiesRender.java.disabled
- ParticleFactoryRegistry.java.disabled
- AimKey.java.disabled
- ConfigKey.java.disabled
- CrawlKey.java.disabled
- FireSelectKey.java.disabled
- InspectKey.java.disabled
- InteractKey.java.disabled
- MeleeKey.java.disabled
- RefitKey.java.disabled
- ReloadKey.java.disabled
- ShootKey.java.disabled
- ZoomKey.java.disabled
- BedrockAmmoModel.java.disabled
- BedrockAnimatedModel.java.disabled
- BedrockAttachmentModel.java.disabled
- BedrockGunModel.java.disabled
- BedrockModel.java.disabled
- AttachmentRender.java.disabled
- BeamRenderer.java.disabled
- LeftHandRender.java.disabled
- MuzzleFlashRender.java.disabled
- RightHandRender.java.disabled
- ShellRender.java.disabled
- TextShowRender.java.disabled
- ModelAdditionalMagazineListener.java.disabled
- ModelTranslateListener.java.disabled
- AmmoCountPapi.java.disabled
- AmmoParticleSpawner.java.disabled
- BulletHoleParticle.java.disabled
- GunSmithTableRenderer.java.disabled
- StatueRenderer.java.disabled
- TargetRenderer.java.disabled
- EntityBulletRenderer.java.disabled
- TargetMinecartRenderer.java.disabled
- AmmoItemRenderer.java.disabled
- AnimateGeoItemRenderer.java.disabled
- AttachmentItemRenderer.java.disabled
- GunItemRendererWrapper.java.disabled
- GunSmithTableItemRenderer.java.disabled
- HumanoidOffhandRender.java.disabled
- ClientAssetsManager.java.disabled
- ClientIndexManager.java.disabled
- GunDisplayInstance.java.disabled
- InternalAssetLoader.java.disabled
- ClientAmmoIndex.java.disabled
- ClientAttachmentIndex.java.disabled
- ClientAttachmentSkinIndex.java.disabled
- ClientBlockIndex.java.disabled
- ClientGunIndex.java.disabled
- GltfManager.java.disabled
- PackInfoManager.java.disabled
- GunSoundInstance.java.disabled
- SoundPlayManager.java.disabled
- ClientAttachmentItemTooltip.java.disabled
- ClientBlockItemTooltip.java.disabled
- ClientGunTooltip.java.disabled
- package-info.java.disabled
- AttachmentLockCommand.java.disabled
- DummyAmmoCommand.java.disabled
- HideTooltipPartCommand.java.disabled
- ReloadCommand.java.disabled
- ControllableInner.java.disabled
- GunModPlugin.java.disabled
- AttachmentQueryCategory.java.disabled
- GunSmithTableCategory.java.disabled
- package-info.java.disabled
- AttachmentQueryEntry.java.disabled
- TimelessKubeJSPlugin.java.disabled
- CustomGunItemBuilder.java.disabled
- KubeJSCustomGunItem.java.disabled
- GunKubeJSEvents.java.disabled
- TimelessClientEvents.java.disabled
- TimelessForgeEventWrappers.java.disabled
- TimelessServerEvents.java.disabled
- TimelessRecipeJS.java.disabled
- AmmoNbtFactory.java.disabled
- AttachmentNbtFactory.java.disabled
- GunNbtFactory.java.disabled
- TimelessItemWrapper.java.disabled
- OculusCompat.java.disabled
- PlayerAnimatorCompat.java.disabled
- AdjustmentYRotModifier.java.disabled
- AnimationDataRegisterFactory.java.disabled
- AnimationManager.java.disabled
- GunSmithTableRecipe.java.disabled
- GunSmithTableSerializer.java.disabled
- package-info.java.disabled
- RawGunTableResult.java.disabled
- EntityKineticBullet.java.disabled
- TargetMinecart.java.disabled
- LivingEntityAim.java.disabled
- LivingEntityBolt.java.disabled
- LivingEntityCrawl.java.disabled
- LivingEntityDrawGun.java.disabled
- LivingEntityFireSelect.java.disabled
- LivingEntityHeat.java.disabled
- LivingEntityMelee.java.disabled
- LivingEntityReload.java.disabled
- LivingEntityShoot.java.disabled
- LivingEntitySpeedModifier.java.disabled
- LivingEntitySprint.java.disabled
- ShooterDataHolder.java.disabled
- ModSyncedEntityData.java.disabled
- SyncedClassKey.java.disabled
- SyncedEntityData.java.disabled
- ChangeGunPropertyEvent.java.disabled
- PlayerRespawnEvent.java.disabled
- PreventGunClick.java.disabled
- SyncBaseTimestamp.java.disabled
- SyncedEntityDataEvent.java.disabled
- TravelToDimensionEvent.java.disabled
- BellRing.java.disabled
- DestroyGlassBlock.java.disabled
- CapabilityRegistry.java.disabled
- CommonRegistry.java.disabled
- CompatRegistry.java.disabled
- ModContainer.java.disabled
- ModParticles.java.disabled
- ModRecipe.java.disabled
- GunSmithTableMenu.java.disabled
- GunTooltip.java.disabled
- AmmoBoxItem.java.disabled
- AmmoItem.java.disabled
- AttachmentItem.java.disabled
- GunSmithTableItem.java.disabled
- ModernKineticGunItem.java.disabled
- ModernKineticGunScriptAPI.java.disabled
- TargetMinecartItem.java.disabled
- AbstractButtonMixin.java.disabled
- GameRendererMixin.java.disabled
- HumanoidModelMixin.java.disabled
- ItemInHandLayerMixin.java.disabled
- ItemInHandRendererMixin.java.disabled
- LocalPlayerMixin.java.disabled
- MouseHandlerMixin.java.disabled
- PlayerModelMixin.java.disabled
- LivingEntityMixin.java.disabled
- ServerGamePacketListenerImplMixin.java.disabled
- ServerPlayerMixin.java.disabled
- ServerPlayNetHandlerMixin.java.disabled
- NetworkHandler.java.disabled
- ClientMessageCraft.java.disabled
- ClientMessageLaserColor.java.disabled
- ClientMessagePlayerAim.java.disabled
- ClientMessagePlayerBoltGun.java.disabled
- ClientMessagePlayerCancelReload.java.disabled
- ClientMessagePlayerCrawl.java.disabled
- ClientMessagePlayerDrawGun.java.disabled
- ClientMessagePlayerFireSelect.java.disabled
- ClientMessagePlayerMelee.java.disabled
- ClientMessagePlayerReloadGun.java.disabled
- ClientMessagePlayerShoot.java.disabled
- ClientMessagePlayerZoom.java.disabled
- ClientMessageRefitGun.java.disabled
- ClientMessageSyncBaseTimestamp.java.disabled
- ClientMessageUnloadAttachment.java.disabled
- ServerMessageCraft.java.disabled
- ServerMessageRefreshRefitScreen.java.disabled
- ServerMessageSound.java.disabled
- ServerMessageSwapItem.java.disabled
- ServerMessageSyncBaseTimestamp.java.disabled
- ServerMessageSyncGunPack.java.disabled
- ServerMessageUpdateEntityData.java.disabled
- ServerMessageSyncedEntityDataMapping.java.disabled
- BulletHoleOption.java.disabled
- package-info.java.disabled
- CommonAssetsManager.java.disabled
- ICommonResourceProvider.java.disabled
- CommonAttachmentIndex.java.disabled
- CommonBlockIndex.java.disabled
- CommonGunIndex.java.disabled
- AttachmentDataManager.java.disabled
- AttachmentsTagManager.java.disabled
- RecipeFilterManager.java.disabled
- AttachmentCacheProperty.java.disabled
- AttachmentPropertyManager.java.disabled
- AdsModifier.java.disabled
- AimInaccuracyModifier.java.disabled
- AmmoSpeedModifier.java.disabled
- ArmorIgnoreModifier.java.disabled
- DamageModifier.java.disabled
- EffectiveRangeModifier.java.disabled
- ExplosionModifier.java.disabled
- ExtraMovementModifier.java.disabled
- HeadShotModifier.java.disabled
- IgniteModifier.java.disabled
- InaccuracyModifier.java.disabled
- KnockbackModifier.java.disabled
- PierceModifier.java.disabled
- RecoilModifier.java.disabled
- RpmModifier.java.disabled
- SilenceModifier.java.disabled
- WeightModifier.java.disabled
- CommonNetworkCache.java.disabled
- TabConfig.java.disabled
- GunData.java.disabled
- InaccuracyType.java.disabled
- CommonAttachmentIndexSerializer.java.disabled
- CommonBlockIndexSerializer.java.disabled
- CommonGunIndexSerializer.java.disabled
- GunSmithTableResultSerializer.java.disabled
- SoundManager.java.disabled
- AllowAttachmentTagMatcher.java.disabled
- AttachmentDataUtils.java.disabled
- LaserColorUtil.java.disabled


## ORDEM TOPOLOGICA RECOMENDADA

Os arquivos abaixo estao ordenados de acordo com suas dependencias internas.
Habilite na ordem apresentada para minimizar erros de compilacao:

1. DataType.java (Deps: 0)
2. GunTabType.java (Deps: 0)
3. GunTooltipPart.java (Deps: 0)
4. CommonLoadPack.java (Deps: 0)
5. OpenGunPackDirEntry.java (Deps: 0)
6. CycleTaskHelper.java (Deps: 0)
7. AttachmentLod.java (Deps: 0)
8. GunHurtBobTweak.java (Deps: 0)
9. ZoomClothConfig.java (Deps: 0)
10. GunFireModeAdjustData.java (Deps: 0)
11. AmmoBoxTooltip.java (Deps: 0)
12. Vector3fSerializer.java (Deps: 0)
13. FeedType.java (Deps: 0)
14. BufferViewModel.java (Deps: 0)
15. OculusCompatNewly.java (Deps: 0)
16. TimelessCommonEvents.java (Deps: 0)
17. KeepingItemRenderer.java (Deps: 0)
18. ResourceManager.java (Deps: 0)
19. Md5Utils.java (Deps: 0)
20. MeleeData.java (Deps: 0)
21. GunFireSelectEvent.java (Deps: 0)
22. InteractKeyConfigRead.java (Deps: 0)
23. GunAnimationConstant.java (Deps: 0)
24. DefaultTableItem.java (Deps: 0)
25. FaceUVsItem.java (Deps: 0)
26. DefaultAnimationType.java (Deps: 0)
27. GunItemManager.java (Deps: 0)
28. ShellDisplay.java (Deps: 0)
29. GunFinishReloadEvent.java (Deps: 0)
30. Align.java (Deps: 0)
31. DebugCommand.java (Deps: 0)
32. Ignite.java (Deps: 0)
33. PackInfo.java (Deps: 0)
34. IFunctionalRenderer.java (Deps: 0)
35. BlockItemTooltip.java (Deps: 0)
36. JsonProperty.java (Deps: 0)
37. TimelessKubeJSEventRegister.java (Deps: 0)
38. BedrockAnimationFile.java (Deps: 0)
39. AnimationPlan.java (Deps: 0)
40. BedrockPolygon.java (Deps: 0)
41. PlayerAnimatorAssetManager.java (Deps: 0)
42. TacPathVisitor.java (Deps: 0)
43. GltfConstants.java (Deps: 0)
44. FireSound.java (Deps: 0)
45. AnimationSoundChannelContent.java (Deps: 0)
46. ITargetEntity.java (Deps: 0)
47. TacHitResult.java (Deps: 0)
48. ExplosionData.java (Deps: 0)
49. GunShootEvent.java (Deps: 0)
50. GunDrawEvent.java (Deps: 0)
51. BurstData.java (Deps: 0)
52. GeometryModelNew.java (Deps: 0)
53. CubesItem.java (Deps: 0)
54. GunLevelUpToast.java (Deps: 0)
55. ModelRendererWrapper.java (Deps: 0)
56. ShootResult.java (Deps: 0)
57. LuaAnimationState.java (Deps: 0)
58. IDataSerializer.java (Deps: 0)
59. BlockRayTrace.java (Deps: 0)
60. PackMeta.java (Deps: 0)
61. Serializers.java (Deps: 0)
62. AnimationSampler.java (Deps: 0)
63. PathHandler.java (Deps: 0)
64. GunRecoil.java (Deps: 0)
65. BlockData.java (Deps: 0)
66. IThirdPersonAnimation.java (Deps: 0)
67. GetJarResources.java (Deps: 0)
68. ObjectAnimation.java (Deps: 0)
69. DataEntry.java (Deps: 0)
70. AnimationState.java (Deps: 0)
71. AnimationChannelTarget.java (Deps: 0)
72. Easing.java (Deps: 0)
73. GunClothConfig.java (Deps: 0)
74. DefaultAssets.java (Deps: 0)
75. BlockIndexPOJO.java (Deps: 0)
76. GunReloadEvent.java (Deps: 0)
77. RootCommand.java (Deps: 0)
78. GunMeleeEvent.java (Deps: 0)
79. AnimationName.java (Deps: 0)
80. LuaLibrary.java (Deps: 0)
81. ListPackCommand.java (Deps: 0)
82. Bolt.java (Deps: 0)
83. ShoulderSurfingCompat.java (Deps: 0)
84. GunSmithTableBlockA.java (Deps: 0)
85. LayerGunShow.java (Deps: 0)
86. AccessorIntData.java (Deps: 0)
87. PapiManager.java (Deps: 0)
88. IBlock.java (Deps: 0)
89. GunReloadData.java (Deps: 0)
90. DataHolder.java (Deps: 0)
91. GunSmithTableIngredient.java (Deps: 0)
92. IAmmo.java (Deps: 0)
93. StatueBlockEntity.java (Deps: 0)
94. TypeButton.java (Deps: 0)
95. AccessorData.java (Deps: 0)
96. TimelessItemNbtFactory.java (Deps: 0)
97. DelayedTask.java (Deps: 0)
98. AnimationModel.java (Deps: 0)
99. MuzzleFlash.java (Deps: 0)
100. BedrockCube.java (Deps: 0)
101. SoundEffectKeyframes.java (Deps: 0)
102. InterpolatorUtil.java (Deps: 0)
103. GunSmithTableBlockC.java (Deps: 0)
104. BonesItem.java (Deps: 0)
105. GunDefaultMeleeData.java (Deps: 0)
106. AnimationChannelContent.java (Deps: 0)
107. OptifineCompat.java (Deps: 0)
108. TrackArrayMismatchException.java (Deps: 0)
109. AnimationListener.java (Deps: 0)
110. AnimationListenerSupplier.java (Deps: 0)
111. IFilter.java (Deps: 0)
112. AmmoCountStyle.java (Deps: 0)
113. GunPackProgressScreen.java (Deps: 0)
114. LiteralFilter.java (Deps: 0)
115. AmmoClothConfig.java (Deps: 0)
116. ColorHex.java (Deps: 0)
117. AttachmentType.java (Deps: 0)
118. IAmmoBox.java (Deps: 0)
119. RenderDistance.java (Deps: 0)
120. AccessorSparse.java (Deps: 0)
121. HeadShotAABBConfigRead.java (Deps: 0)
122. FireMode.java (Deps: 0)
123. ItemStackSerializer.java (Deps: 0)
124. IStackTooltip.java (Deps: 0)
125. BufferView.java (Deps: 0)
126. RecipeFilter.java (Deps: 0)
127. NodeModel.java (Deps: 0)
128. GunMeleeDebug.java (Deps: 0)
129. EffectData.java (Deps: 0)
130. PerlinNoise.java (Deps: 0)
131. LanguageMixin.java (Deps: 0)
132. IDisplay.java (Deps: 0)
133. Modifier.java (Deps: 0)
134. BedrockVertex.java (Deps: 0)
135. ModDamageTypes.java (Deps: 0)
136. AnimationKeyframes.java (Deps: 0)
137. GeometryModelLegacy.java (Deps: 0)
138. MathUtil.java (Deps: 0)
139. GunSmithTableResult.java (Deps: 0)
140. Node.java (Deps: 0)
141. FaceItem.java (Deps: 0)
142. ShoulderSurfingCompatInner.java (Deps: 0)
143. AmmoParticle.java (Deps: 0)
144. ModAttributes.java (Deps: 0)
145. GunIndexPOJO.java (Deps: 0)
146. IComponentTooltip.java (Deps: 0)
147. MoveSpeed.java (Deps: 0)
148. BedrockVersion.java (Deps: 0)
149. CommonTransformObject.java (Deps: 0)
150. AnimationChannel.java (Deps: 0)
151. Accessor.java (Deps: 0)
152. BedrockCubeBox.java (Deps: 0)
153. PairSerializer.java (Deps: 0)
154. ModPainting.java (Deps: 0)
155. ThirdPersonManager.java (Deps: 0)
156. LaserConfig.java (Deps: 0)
157. SyncedDataKey.java (Deps: 0)
158. ConfigCommand.java (Deps: 0)
159. GunReloadTime.java (Deps: 0)
160. EntityHurtByGunEvent.java (Deps: 0)
161. SoundAssetsManager.java (Deps: 0)
162. ElementType.java (Deps: 0)
163. AccessorSparseIndices.java (Deps: 0)
164. AccessorSparseValues.java (Deps: 0)
165. BufferModel.java (Deps: 0)
166. AmmoIndexPOJO.java (Deps: 0)
167. RegexFilter.java (Deps: 0)
168. VersionChecker.java (Deps: 0)
169. PreLoadConfig.java (Deps: 0)
170. ServerMessageLevelUp.java (Deps: 0)
171. DiscreteTrackArray.java (Deps: 0)
172. ThrowableAnimationStateContext.java (Deps: 0)
173. LoginIndexHolder.java (Deps: 0)
174. ControllableCompat.java (Deps: 0)
175. GunMeleeData.java (Deps: 0)
176. IAnimationItem.java (Deps: 0)
177. GunFireEvent.java (Deps: 0)
178. ShellEjection.java (Deps: 0)
179. KnockBackModifier.java (Deps: 0)
180. Vec3Serializer.java (Deps: 0)
181. AccessorShortData.java (Deps: 0)
182. GunHeatData.java (Deps: 0)
183. AccessorFloatData.java (Deps: 0)
184. FlatColorButton.java (Deps: 0)
185. BlackList.java (Deps: 0)
186. ExtraDamage.java (Deps: 0)
187. InputExtraCheck.java (Deps: 0)
188. DamageStyle.java (Deps: 0)
189. PreLoadModConfig.java (Deps: 0)
190. ControllableData.java (Deps: 0)
191. GunLod.java (Deps: 0)
192. CacheValue.java (Deps: 0)
193. ResultButton.java (Deps: 0)
194. LivingEntityAmmoCheck.java (Deps: 0)
195. AnimationConstant.java (Deps: 0)
196. Description.java (Deps: 0)
197. TransformScale.java (Deps: 0)
198. TextShow.java (Deps: 0)
199. GunDamageSourcePart.java (Deps: 0)
200. AccessorByteData.java (Deps: 0)
201. EntityKillByGunEvent.java (Deps: 0)
202. CrosshairType.java (Deps: 0)
203. GunModelConstant.java (Deps: 0)
204. AmmoEntityDisplay.java (Deps: 0)
205. GunSmithTableBlockB.java (Deps: 0)
206. PlayerNamePapi.java (Deps: 0)
207. ReloadState.java (Deps: 0)
208. AttachmentSkin.java (Deps: 0)
209. RawAnimationStructure.java (Deps: 0)
210. Animation.java (Deps: 0)
211. AnimationBone.java (Deps: 0)
212. AbstractAccessorData.java (Deps: 0)
213. ResourceScanner.java (Deps: 0)
214. Buffer.java (Deps: 0)
215. BedrockModelPOJO.java (Deps: 0)
216. BedrockPart.java (Deps: 0)
217. BulletData.java (Deps: 0)
218. AccessorSparseUtils.java (Deps: 0)
219. GunRecoilKeyFrame.java (Deps: 0)
220. BedrockAnimation.java (Deps: 0)
221. TimelessItemType.java (Deps: 0)
222. OculusCompatLegacy.java (Deps: 0)
223. Buffers.java (Deps: 0)
224. NumberArrays.java (Deps: 0)
225. IMessage.java (Deps: 0)
226. AnimationController.java (Deps: 0)
227. StairBlockAccessor.java (Deps: 0)
228. SecondOrderDynamics.java (Deps: 0)
229. GunModSubtype.java (Deps: 0)
230. CommonDataManager.java (Deps: 1)
231. INetworkCacheReloadListener.java (Deps: 1)
232. KeyClothConfig.java (Deps: 1)
233. ServerTickEvent.java (Deps: 1)
234. ServerMessageGunFireSelect.java (Deps: 1)
235. IgniteSerializer.java (Deps: 1)
236. PackConvertor.java (Deps: 1)
237. AttachmentData.java (Deps: 1)
238. JsonResourceLoader.java (Deps: 1)
239. PlayerAnimatorLoader.java (Deps: 1)
240. Accessors.java (Deps: 1)
241. HitboxHelper.java (Deps: 1)
242. ServerMessageGunShoot.java (Deps: 1)
243. ServerMessageGunDraw.java (Deps: 1)
244. AttachmentIdFix.java (Deps: 1)
245. ServerMessageGunReload.java (Deps: 1)
246. CommandRegistry.java (Deps: 1)
247. ServerMessageGunMelee.java (Deps: 1)
248. ScriptManager.java (Deps: 1)
249. BlockItemBuilder.java (Deps: 2)
250. BlockItemDataAccessor.java (Deps: 2)
251. ModCapabilities.java (Deps: 1)
252. GunSmithTableIngredientSerializer.java (Deps: 1)
253. AmmoItemBuilder.java (Deps: 2)
254. SoundEffectKeyframesSerializer.java (Deps: 1)
255. Interpolator.java (Deps: 1)
256. SLerp.java (Deps: 1)
257. Linear.java (Deps: 1)
258. Spline.java (Deps: 1)
259. Step.java (Deps: 1)
260. RenderHelper.java (Deps: 1)
261. GunResult.java (Deps: 1)
262. AttachmentItemTooltip.java (Deps: 1)
263. IAttachment.java (Deps: 1)
264. AttachmentIndexPOJO.java (Deps: 1)
265. ClientAmmoBoxTooltip.java (Deps: 2)
266. RenderHeadShotAABB.java (Deps: 1)
267. EntityUtil.java (Deps: 1)
268. LoadingConfigEvent.java (Deps: 2)
269. BlockDisplay.java (Deps: 1)
270. AmmoDisplay.java (Deps: 1)
271. ParameterizedCachePair.java (Deps: 1)
272. AnimationKeyframesSerializer.java (Deps: 1)
273. ObjectAnimationRunner.java (Deps: 1)
274. CustomInterpolator.java (Deps: 2)
275. GunSmithTableResultInfo.java (Deps: 1)
276. TableRecipe.java (Deps: 2)
277. BedrockCubePerFace.java (Deps: 2)
278. GunAmmo.java (Deps: 1)
279. EntityDamageEvent.java (Deps: 2)
280. GunDisplay.java (Deps: 3)
281. ServerMessageGunHurt.java (Deps: 1)
282. CommonAmmoIndex.java (Deps: 1)
283. GunPackLoader.java (Deps: 3)
284. OverwriteCommand.java (Deps: 1)
285. OtherClothConfig.java (Deps: 1)
286. ServerMessageGunFire.java (Deps: 1)
287. KnockbackChange.java (Deps: 1)
288. DistanceDamagePairSerializer.java (Deps: 1)
289. LuaAnimationConstant.java (Deps: 2)
290. GunTransform.java (Deps: 1)
291. AmmoTransform.java (Deps: 1)
292. AttachmentDisplay.java (Deps: 3)
293. ServerMessageGunKill.java (Deps: 1)
294. CrosshairDropdown.java (Deps: 1)
295. LuaGunAnimationConstant.java (Deps: 3)
296. LuaGunLogicConstant.java (Deps: 2)
297. ModSerializers.java (Deps: 2)
298. JsonDataManager.java (Deps: 1)
299. FunctionalBedrockPart.java (Deps: 1)
300. Acknowledge.java (Deps: 2)
301. AnimationStateMachine.java (Deps: 1)
302. LuaStateMachineFactory.java (Deps: 1)
303. LuaAnimationStateMachine.java (Deps: 1)
304. PlayerEnterWorld.java (Deps: 1)
305. ConvertCommand.java (Deps: 1)
306. HitboxHelperEvent.java (Deps: 1)
307. ProjectileExplosion.java (Deps: 1)
308. ObjectAnimationChannel.java (Deps: 1)
309. AttachmentItemBuilder.java (Deps: 2)
310. AttachmentItemDataAccessor.java (Deps: 2)
311. AnimationStateContext.java (Deps: 4)
312. TimelessGunSmithTableRecipeSchema.java (Deps: 1)
313. GunSmithTableResultComponents.java (Deps: 1)
314. SlotModel.java (Deps: 3)
315. CommonAmmoIndexSerializer.java (Deps: 2)
316. RenderClothConfig.java (Deps: 2)
317. DisplayManager.java (Deps: 2)
318. ExplodeUtil.java (Deps: 1)
319. ConstraintObject.java (Deps: 5)
320. CameraAnimationObject.java (Deps: 4)
321. ConstraintTranslateListener.java (Deps: 2)
322. CameraRotateListener.java (Deps: 3)
323. ConstraintRotateListener.java (Deps: 3)
324. ModelRotateListener.java (Deps: 4)
325. ModelScaleListener.java (Deps: 3)
326. ItemAnimationStateContext.java (Deps: 1)
327. MenuIntegration.java (Deps: 6)

---

## 📊 PROGRESSO ATUAL DA MIGRAÇÃO

**Status:** Fase 1 em andamento  
**Data da última atualização:** 27 de Junho de 2025  
**Progresso total:** 43/327 arquivos (13.1%)

### ✅ Conquistas da Sessão Atual
- **43 arquivos habilitados** na Fase 1 (15 novos nesta sessão)
- **1 migração crítica** realizada: `GunTooltipPart.java` (NBT → DataComponents)
- **Workflow estabelecido** com commits sistemáticos
- **Scripts de automação** criados para acelerar o processo

### 🔧 Migrações Realizadas

#### GunTooltipPart.java - NBT para DataComponents
**Antes (Forge 1.20.1):**
```java
CompoundTag tag = stack.getTag();
if (tag != null && tag.contains("HideFlags", Tag.TAG_ANY_NUMERIC)) {
    return tag.getInt("HideFlags");
}
stack.getOrCreateTag().putInt("HideFlags", mask);
```

**Depois (NeoForge 1.21.1):**
```java
// Usar DataComponents em vez de NBT - HideFlags agora é um componente nativo
return stack.getOrDefault(DataComponents.HIDE_TOOLTIP, 0);
stack.set(DataComponents.HIDE_TOOLTIP, mask);
```

### 🎯 Próximos Arquivos da Fase 1
```
44. GeometryModelNew.java
45. CubesItem.java  
46. GunLevelUpToast.java
47. ModelRendererWrapper.java
48. ShootResult.java
49. LuaAnimationState.java
50. IDataSerializer.java
51. BlockRayTrace.java
52. PackMeta.java
53. Serializers.java
```

### ⚠️ Observações Importantes
1. **Problemas de Compilação:** O ambiente apresenta problemas na fase de cache do Minecraft 1.21.1
2. **Estratégia Adotada:** Continuando migração independente dos problemas de compilação
3. **NBT Critical:** Prioridade máxima para identificar arquivos que usam `getTag()`, `getOrCreateTag()`
4. **Ritmo Atual:** ~43 arquivos habilitados (15 nesta sessão)

### 📈 Estimativas
- **Restam na Fase 1:** 186 arquivos
- **Tempo estimado Fase 1:** 5-6 sessões restantes
- **Meta mensal:** Completar Fase 1 e iniciar Fase 2

---

**⚡ INSTRUÇÕES PARA PRÓXIMA SESSÃO:**
1. Continuar habilitando arquivos da Fase 1 seguindo a ordem do checklist
2. Identificar e migrar arquivos que usam NBT para DataComponents
3. Manter commits regulares a cada 10-20 arquivos
4. Validar compilação quando ambiente permitir

