# PLANO DE DESENVOLVIMENTO SISTEMATICO - TacZ NeoForge 1.21.1 (Gerado Automaticamente)

**Projeto:** Migracao TacZ de Forge 1.20.1 para NeoForge 1.21.1
**Estrategia:** Habilitacao incremental baseada em ordenacao topologica de dependencias.
**Data de Geracao:** 2025-06-27 13:47:17

---

## RESUMO ESTATISTICO

| Fase | Descricao | Arquivos | Status |
|------|-----------|----------|--------|
| **Fase 1** | Sem dependencias internas | 229 | [ ] 0/229 |
| **Fase 2** | Dependencias baixas (1-3) | 93 | [ ] 0/93 |
| **Fase 3** | Dependencias medias (4-10) | 5 | [ ] 0/5 |
| **Fase 4** | Dependencias altas (11+) | 0 | [ ] 0/0 |
| **TOTAL** | **Todos os arquivos** | **327** | **0/327** |

---

## CHECKLIST DE HABILITACAO ORDENADO

Esta lista foi gerada para garantir que, ao habilitar um arquivo, todas as suas dependencias internas do mod ja tenham sido habilitadas. Siga a ordem rigorosamente.

### **FASE 1: FUNDACAO (Sem Dependencias Internas)** (229 arquivos)
*Estes arquivos nao possuem imports de outras classes do mod. Eles sao a base e podem ser habilitados primeiro.*

* [ ] DataType.java
* [ ] GunTabType.java
* [ ] GunTooltipPart.java
* [ ] CommonLoadPack.java
* [ ] OpenGunPackDirEntry.java
* [ ] CycleTaskHelper.java
* [ ] AttachmentLod.java
* [ ] GunHurtBobTweak.java
* [ ] ZoomClothConfig.java
* [ ] GunFireModeAdjustData.java
* [ ] AmmoBoxTooltip.java
* [ ] Vector3fSerializer.java
* [ ] FeedType.java
* [ ] BufferViewModel.java
* [ ] OculusCompatNewly.java
* [ ] TimelessCommonEvents.java
* [ ] KeepingItemRenderer.java
* [ ] ResourceManager.java
* [ ] Md5Utils.java
* [ ] MeleeData.java
* [ ] GunFireSelectEvent.java
* [ ] InteractKeyConfigRead.java
* [ ] GunAnimationConstant.java
* [ ] DefaultTableItem.java
* [ ] FaceUVsItem.java
* [ ] DefaultAnimationType.java
* [ ] GunItemManager.java
* [ ] ShellDisplay.java
* [ ] GunFinishReloadEvent.java
* [ ] Align.java
* [ ] DebugCommand.java
* [ ] Ignite.java
* [ ] PackInfo.java
* [ ] IFunctionalRenderer.java
* [ ] BlockItemTooltip.java
* [ ] JsonProperty.java
* [ ] TimelessKubeJSEventRegister.java
* [ ] BedrockAnimationFile.java
* [ ] AnimationPlan.java
* [ ] BedrockPolygon.java
* [ ] PlayerAnimatorAssetManager.java
* [ ] TacPathVisitor.java
* [ ] GltfConstants.java
* [ ] FireSound.java
* [ ] AnimationSoundChannelContent.java
* [ ] ITargetEntity.java
* [ ] TacHitResult.java
* [ ] ExplosionData.java
* [ ] GunShootEvent.java
* [ ] GunDrawEvent.java
* [ ] BurstData.java
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

