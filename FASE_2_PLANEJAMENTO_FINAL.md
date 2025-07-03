# FASE 2 - PLANEJAMENTO FINAL
*Gerado automaticamente em 2025-07-02 23:01:57*

## ðŸ“Š RESUMO DA ANÃLISE

- **Total de arquivos analisados:** 448
- **Arquivos prontos para Fase 2:** 87
- **Arquivos que dependem de fases posteriores:** 361

## ðŸŽ¯ ESTRATÃ‰GIA DA FASE 2

A Fase 2 deve focar APENAS em arquivos que dependem exclusivamente das Fases 0 e 1 (base sÃ³lida jÃ¡ estabelecida).

### CritÃ©rios de InclusÃ£o na Fase 2:
1. âœ… **Sem dependÃªncias desconhecidas:** Arquivos que sÃ³ dependem de classes jÃ¡ habilitadas
2. âš ï¸ **DependÃªncias mÃ­nimas:** Arquivos com 1-3 dependÃªncias que podem ser resolvidas rapidamente
3. âŒ **Muitas dependÃªncias:** Arquivos que dependem de sistemas complexos (mover para fases posteriores)

---

## ðŸ“‹ FASE 2.1: ARQUIVOS PRONTOS (DependÃªncias Zero)
*Arquivos que podem ser habilitados imediatamente*

- [ ] **BonesItem.java** (Deps: 0)
- [ ] **GunAmmo.java** (Deps: 1) - Depende de: AmmoParticle
- [ ] **GeometryModelNew.java** (Deps: 0)
- [ ] **GeometryModelLegacy.java** (Deps: 0)
- [ ] **BedrockPolygon.java** (Deps: 0)
- [ ] **ThrowableAnimationStateContext.java** (Deps: 0)
- [ ] **BlockDisplay.java** (Deps: 1) - Depende de: IDisplay
- [ ] **RawAnimationStructure.java** (Deps: 0)
---

## ðŸ“‹ FASE 2.2: ARQUIVOS COM DEPENDÃŠNCIAS MÃNIMAS
*Arquivos que podem ser migrados com pequenas correÃ§Ãµes*

- [ ] **KubeJSCustomGunItem.java** (Unknown Deps: 1) - Depende de: ModernKineticGunItem
- [ ] **GunSmithTableResultComponents.java** (Unknown Deps: 3) - Depende de: GunSmithTableResultInfo, Recipe, RecipeComponent
- [ ] **ControllableCompat.java** (Unknown Deps: 2) - Depende de: ControllableInner, ModList
- [ ] **CustomGunItemBuilder.java** (Unknown Deps: 3) - Depende de: ForgeRegistries, KubeJSCustomGunItem, TimelessKubeJSPlugin
- [ ] **AttachmentNbtFactory.java** (Unknown Deps: 3) - Depende de: Attachment, AttachmentItem, TimelessItemType
- [ ] **LivingEntityHeat.java** (Unknown Deps: 2) - Depende de: AbstractGunItem, Stack
- [ ] **LivingEntitySprint.java** (Unknown Deps: 3) - Depende de: IGunOperator, Operator, StateType
- [ ] **TimelessItemType.java** (Unknown Deps: 3) - Depende de: AMMO, ATTACHMENT, GUN
- [ ] **GunSmithTableResult.java** (Unknown Deps: 2) - Depende de: RawGunTableResult, TabConfig
- [ ] **BlackList.java** (Unknown Deps: 3) - Depende de: GunMod, InterModComms, NeoForgeRegistries
- [ ] **AnimationChannel.java** (Unknown Deps: 1) - Depende de: NullPointerException
- [ ] **AmmoDisplay.java** (Unknown Deps: 1) - Depende de: Display
- [ ] **AccessorSparse.java** (Unknown Deps: 2) - Depende de: IllegalArgumentException, NullPointerException
- [ ] **Animation.java** (Unknown Deps: 2) - Depende de: IllegalArgumentException, NullPointerException
- [ ] **AmmoTransform.java** (Unknown Deps: 2) - Depende de: AmmoTransform, Transform
- [ ] **BedrockModelPOJO.java** (Unknown Deps: 1) - Depende de: ModelNew
- [ ] **FaceUVsItem.java** (Unknown Deps: 1) - Depende de: FaceUVsItem
- [ ] **AttachmentDisplay.java** (Unknown Deps: 2) - Depende de: LaserConfig, Lod
- [ ] **GunTransform.java** (Unknown Deps: 2) - Depende de: GunTransform, Transform
- [ ] **ShooterDataHolder.java** (Unknown Deps: 3) - Depende de: AttachmentCacheProperty, StateType, System
- [ ] **BlockIndexPOJO.java** (Unknown Deps: 1) - Depende de: GunMod
- [ ] **BlockData.java** (Unknown Deps: 2) - Depende de: GunMod, TabConfig
- [ ] **ServerGamePacketListenerImplMixin.java** (Unknown Deps: 3) - Depende de: IGunOperator, Operator, ServerGamePacketListenerImpl
- [ ] **ServerPlayerMixin.java** (Unknown Deps: 2) - Depende de: IGunOperator, ServerPlayer
- [ ] **GunRecoil.java** (Unknown Deps: 3) - Depende de: Frames, Math, SplineInterpolator
- [ ] **CommonAttachmentIndexSerializer.java** (Unknown Deps: 3) - Depende de: CommonAttachmentIndex, JsonDeserializer, JsonParseException
- [ ] **TacHitResult.java** (Unknown Deps: 1) - Depende de: EntityKineticBullet
- [ ] **TableRecipe.java** (Unknown Deps: 2) - Depende de: GunSmithTableIngredient, GunSmithTableResult
- [ ] **CommonAmmoIndexSerializer.java** (Unknown Deps: 3) - Depende de: AmmoIndexPOJO, JsonDeserializer, JsonParseException
- [ ] **HumanoidModelMixin.java** (Unknown Deps: 3) - Depende de: HumanoidModel, InnerThirdPersonManager, LivingEntity
- [ ] **TravelToDimensionEvent.java** (Unknown Deps: 2) - Depende de: IGun, IGunOperator
- [ ] **BellRing.java** (Unknown Deps: 2) - Depende de: AmmoHitBlockEvent, Result
- [ ] **DataEntry.java** (Unknown Deps: 3) - Depende de: DataEntry, SyncedEntityData, Validate
- [ ] **PreventGunClick.java** (Unknown Deps: 3) - Depende de: IGun, InHand, InteractionHand
- [ ] **CommandRegistry.java** (Unknown Deps: 1) - Depende de: RootCommand
- [ ] **DefaultTableItem.java** (Unknown Deps: 2) - Depende de: GunMod, GunSmithTableItem
- [ ] **AbstractButtonMixin.java** (Unknown Deps: 3) - Depende de: AbstractButton, LocalPlayerDataHolder, System
- [ ] **ModCapabilities.java** (Unknown Deps: 3) - Depende de: DataHolder, GunMod, Void
- [ ] **GunTooltip.java** (Unknown Deps: 3) - Depende de: CommonGunIndex, IGun, TooltipComponent
- [ ] **Accessor.java** (Unknown Deps: 3) - Depende de: IllegalArgumentException, NullPointerException, View
- [ ] **BeforeRenderHandEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **RenderItemInHandBobEvent.java** (Unknown Deps: 3) - Depende de: Event, KubeJSGunEventPoster, RenderItemInHandBobEvent
- [ ] **Step.java** (Unknown Deps: 2) - Depende de: AnimationChannelContent, RuntimeException
- [ ] **AnimationState.java** (Unknown Deps: 1) - Depende de: AnimationStateContext
- [ ] **RenderLevelBobEvent.java** (Unknown Deps: 3) - Depende de: Event, KubeJSGunEventPoster, RenderLevelBobEvent
- [ ] **IGunOperator.java** (Unknown Deps: 2) - Depende de: AttachmentCacheProperty, ShooterDataHolder
- [ ] **AttachmentPropertyEvent.java** (Unknown Deps: 3) - Depende de: AttachmentCacheProperty, Event, KubeJSGunEventPoster
- [ ] **SwapItemWithOffHand.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **IClientPlayerGunOperator.java** (Unknown Deps: 3) - Depende de: Dist, LocalPlayerDataHolder, ShootResult
- [ ] **Spline.java** (Unknown Deps: 1) - Depende de: AnimationChannelContent
- [ ] **AnimationListenerSupplier.java** (Unknown Deps: 1) - Depende de: ObjectAnimationChannel
- [ ] **AnimationPlan.java** (Unknown Deps: 1) - Depende de: ObjectAnimation
- [ ] **DefaultAssets.java** (Unknown Deps: 2) - Depende de: GunMod, ID
- [ ] **AnimationListener.java** (Unknown Deps: 1) - Depende de: ObjectAnimationChannel
- [ ] **AnimationModel.java** (Unknown Deps: 2) - Depende de: Collections, Objects
- [ ] **Accessors.java** (Unknown Deps: 2) - Depende de: GltfConstants, IllegalArgumentException
- [ ] **Linear.java** (Unknown Deps: 2) - Depende de: AnimationChannelContent, RuntimeException
- [ ] **BufferModel.java** (Unknown Deps: 1) - Depende de: Data
- [ ] **BufferViewModel.java** (Unknown Deps: 2) - Depende de: Model, SubstitutionCallback
- [ ] **GunDrawEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **LocalPlayerSprint.java** (Unknown Deps: 3) - Depende de: IGunOperator, Operator, StateType
- [ ] **BedrockAmmoModel.java** (Unknown Deps: 3) - Depende de: BedrockModel, BedrockModelPOJO, BedrockVersion
- [ ] **ItemAnimationStateContext.java** (Unknown Deps: 2) - Depende de: AnimationStateContext, Ticks
- [ ] **PlayGunSoundEvent.java** (Unknown Deps: 2) - Depende de: Dist, GunSoundInstance
- [ ] **FunctionalBedrockPart.java** (Unknown Deps: 3) - Depende de: LightTexture, Renderer, Stack
- [ ] **ModelAdditionalMagazineListener.java** (Unknown Deps: 3) - Depende de: AnimationListener, BedrockGunModel, ObjectAnimationChannel
- [ ] **CrosshairType.java** (Unknown Deps: 3) - Depende de: CACHE, GunMod, Locale
- [ ] **SlotModel.java** (Unknown Deps: 2) - Depende de: BedrockCubePerFace, FaceUVsItem
- [ ] **ModelRendererWrapper.java** (Unknown Deps: 1) - Depende de: Renderer
- [ ] **GunSmithTableBlockA.java** (Unknown Deps: 1) - Depende de: AbstractGunSmithTableBlock
- [ ] **GunFireSelectEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **GunMeleeEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **GunFinishReloadEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **GunFireEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **GunReloadEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **BlockItemBuilder.java** (Unknown Deps: 3) - Depende de: DefaultAssets, IBlock, Math
- [ ] **GunItemManager.java** (Unknown Deps: 1) - Depende de: AbstractGunItem
- [ ] **GunShootEvent.java** (Unknown Deps: 2) - Depende de: Event, KubeJSGunEventPoster
- [ ] **AmmoHitBlockEvent.java** (Unknown Deps: 3) - Depende de: EntityKineticBullet, Event, KubeJSGunEventPoster
---

## ðŸš« ARQUIVOS MOVIDOS PARA FASES POSTERIORES
*Arquivos que dependem de sistemas complexos ainda nÃ£o implementados*

- **PlayerRespawnEvent.java** (Unknown Deps: 6) - Principais: FeedType, GunConfig, IGun, ModernKineticGunScriptAPI, RESPAWN
- **SyncBaseTimestamp.java** (Unknown Deps: 4) - Principais: CHANNEL, NetworkHandler, PacketDistributor, ServerMessageSyncBaseTimestamp
- **LoadingConfigEvent.java** (Unknown Deps: 8) - Principais: Bus, Dist, DistExecutor, EventBusSubscriber, InteractKeyConfigRead
- **EntityDamageEvent.java** (Unknown Deps: 4) - Principais: EventPriority, ModAttributes, ModDamageTypes, RESISTANCE
- **HitboxHelperEvent.java** (Unknown Deps: 5) - Principais: FIX, LogicalSide, OtherConfig, Phase, TickEvent
- **SyncedEntityDataEvent.java** (Unknown Deps: 15) - Principais: *, CHANNEL, Collectors, Entities, Entries
- **CompatRegistry.java** (Unknown Deps: 10) - Principais: BlackList, Bus, ClothConfigScreen, Dist, EventBusSubscriber
- **ModAttributes.java** (Unknown Deps: 5) - Principais: ATTRIBUTES, DeferredRegister, ForgeRegistries, GunMod, RangedAttribute
- **CommonRegistry.java** (Unknown Deps: 7) - Principais: Bus, EventBusSubscriber, GunPackLoader, ModAttributes, ModSyncedEntityData
- **DestroyGlassBlock.java** (Unknown Deps: 5) - Principais: AmmoConfig, AmmoHitBlockEvent, EntityKineticBullet, GLASS, NoteBlockInstrument
- **CapabilityRegistry.java** (Unknown Deps: 6) - Principais: Bus, DataHolder, EventBusSubscriber, GunMod, ModCapabilities
- **ModSerializers.java** (Unknown Deps: 4) - Principais: CompoundTag, IDataSerializer, State, StateType
- **ModSyncedEntityData.java** (Unknown Deps: 6) - Principais: GunMod, ModSerializers, Serializers, SyncedDataKey, SyncedEntityData
- **LivingEntitySpeedModifier.java** (Unknown Deps: 14) - Principais: AbstractGunItem, AttachmentCacheProperty, AttributeModifier, Attributes, ExtraMovementModifier
- **LivingEntityReload.java** (Unknown Deps: 14) - Principais: AbstractGunItem, Bolt, BUS, GunItem, GunReloadEvent
- **LivingEntityShoot.java** (Unknown Deps: 27) - Principais: AbstractGunItem, Bolt, BUS, CHANNEL, CommonGunIndex
- **DataHolder.java** (Unknown Deps: 4) - Principais: Collectors, HashMap, SyncedDataKey, SyncMode
- **ChangeGunPropertyEvent.java** (Unknown Deps: 6) - Principais: ApiStatus, AttachmentPropertyEvent, Gun, IGun, Index
- **CommonLoadPack.java** (Unknown Deps: 6) - Principais: Bus, DedicatedServerReloadManager, Dist, EventBusSubscriber, GunMod
- **SyncedEntityData.java** (Unknown Deps: 35) - Principais: AtomicInteger, Class, ClassKeys, Collectors, CommonRegistry
---

## ðŸ“ˆ ESTATÃSTICAS DETALHADAS

### Por Categoria:
- **Fase 2.1 (Prontos):** 8 arquivos
- **Fase 2.2 (DependÃªncias MÃ­nimas):** 79 arquivos  
- **Fases Posteriores:** 361 arquivos

### DistribuiÃ§Ã£o de DependÃªncias:
- ** dependÃªncias desconhecidas:** 448 arquivos
---

## ðŸŽ¯ PRÃ“XIMOS PASSOS

1. **Revisar Fase 2.1:** Validar que os arquivos "prontos" realmente nÃ£o tÃªm dependÃªncias problemÃ¡ticas
2. **Analisar Fase 2.2:** Determinar quais dependÃªncias mÃ­nimas podem ser resolvidas rapidamente
3. **Implementar em ordem:** ComeÃ§ar pelos arquivos com menos dependÃªncias
4. **Testar progressivamente:** Compilar apÃ³s cada grupo de arquivos habilitados
5. **Atualizar planejamento oficial:** Quando estiver satisfeito com este planejamento

---

*AnÃ¡lise completa realizada em 2025-07-02 23:01:57*
