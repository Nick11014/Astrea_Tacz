classDiagram
direction BT
class AbstractAccessorData {
  ~ AbstractAccessorData(Class~?~, ByteBuffer, int, int, int, int, Integer) 
  - int numBytesPerComponent
  - int byteStridePerElement
  - ByteBuffer bufferViewByteBuffer
  - Class~?~ componentType
  - int numComponentsPerElement
  - int numElements
  # getByteIndex(int, int) int
   int numComponentsPerElement
   int numBytesPerComponent
   int totalNumComponents
   ByteBuffer bufferViewByteBuffer
   Class~?~ componentType
   int byteStridePerElement
   int numElements
}
class AbstractButtonMixin {
  + AbstractButtonMixin() 
  + onClickHead(double, double, CallbackInfo) void
}
class AbstractGunItem {
  # AbstractGunItem(Properties) 
  + doBulletSpread(ShooterDataHolder, ItemStack, LivingEntity, Projectile, int, float, float, float, float) void
  + hasInventoryAmmo(LivingEntity, ItemStack, boolean) boolean
  + melee(ShooterDataHolder, LivingEntity, ItemStack) void
  + startReload(ShooterDataHolder, ItemStack, LivingEntity) boolean
  + tickHeat(ShooterDataHolder, ItemStack, LivingEntity) void
  + isSame(ItemStack, ItemStack) boolean
  + findAndExtractDummyAmmo(ItemStack, int) int
  + initializeClient(Consumer~IClientItemExtensions~) void
  + allowAttachment(ItemStack, ItemStack) boolean
  + fireSelect(ShooterDataHolder, ItemStack) void
  + getTooltipImage(ItemStack) Optional~TooltipComponent~
  + startBolt(ShooterDataHolder, ItemStack, LivingEntity) boolean
  + tickReload(ShooterDataHolder, ItemStack, LivingEntity) ReloadState
  + fillItemCategory(GunTabType) NonNullList~ItemStack~
  + useInventoryAmmo(ItemStack) boolean
  + findAndExtractInventoryAmmos(IItemHandler, ItemStack, int) int
  + interruptReload(ShooterDataHolder, ItemStack, LivingEntity) void
  + tickBolt(ShooterDataHolder, ItemStack, LivingEntity) boolean
  + shoot(ShooterDataHolder, ItemStack, Supplier~Float~, Supplier~Float~, LivingEntity) void
  - idNameSort() Comparator~Entry~ResourceLocation, CommonGunIndex~~
  + onEntitySwing(ItemStack, LivingEntity) boolean
  + allowAttachmentType(ItemStack, AttachmentType) boolean
  + findAndExtractInventoryAmmo(IItemHandler, ItemStack, int) int
  + getRPM(ItemStack) int
  + isCanCrawl(ItemStack) boolean
  + canReload(LivingEntity, ItemStack) boolean
  + getName(ItemStack) Component
  + dropAllAmmo(Player, ItemStack) void
}
class AbstractGunSmithTableBlock {
  + AbstractGunSmithTableBlock() 
  + getRenderShape(BlockState) RenderShape
  + getPistonPushReaction(BlockState) PushReaction
  + setPlacedBy(Level, BlockPos, BlockState, LivingEntity?, ItemStack) void
  + getRootPos(BlockPos, BlockState) BlockPos
  + parseRotation(Direction) float
  + newBlockEntity(BlockPos, BlockState) BlockEntity?
  + isRoot(BlockState) boolean
  + getCloneItemStack(BlockState, HitResult, BlockGetter, BlockPos, Player) ItemStack
  + use(BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult) InteractionResult
  # createBlockStateDefinition(Builder~Block, BlockState~) void
}
class Accessor {
  + Accessor() 
  - Number[] min
  - Integer byteOffset
  - Boolean normalized
  - Integer count
  - Number[] max
  - String type
  - Integer bufferView
  - AccessorSparse sparse
  - Integer componentType
  + defaultNormalized() Boolean
  + isNormalized() Boolean
  + defaultByteOffset() Integer
   String type
   Boolean normalized
   Integer byteOffset
   Integer componentType
   Number[] min
   AccessorSparse sparse
   Integer bufferView
   Number[] max
   Integer count
}
class AccessorByteData {
  + AccessorByteData(int, ByteBuffer, int, int, int, Integer) 
  - boolean unsigned
  + computeMin() byte[]
  + getInt(int, int) int
  + createString(Locale, String, int) String
  + get(int) byte
  + get(int, int) byte
  + set(int, int, byte) void
  + computeMax() byte[]
  + getInt(int) int
  + computeMinInt() int[]
  + createByteBuffer() ByteBuffer
  + set(int, byte) void
  + computeMaxInt() int[]
   boolean unsigned
}
class AccessorData {
<<Interface>>
  + createByteBuffer() ByteBuffer
   int numComponentsPerElement
   int totalNumComponents
   Class~?~ componentType
   int numElements
}
class AccessorDatas {
  - AccessorDatas() 
  + createString(AccessorData, int) String
  + computeMin(AccessorData) Number[]
  + isIntType(int) boolean
  ~ validateByteType(int) void
  + create(int, ByteBuffer, int, int, int, Integer) AccessorData
  + computeMax(AccessorData) Number[]
  + create(AccessorModel, ByteBuffer) AccessorData?
  ~ createShort(AccessorModel) AccessorShortData
  - createFloat(AccessorModel, ByteBuffer) AccessorFloatData
  + isByteType(int) boolean
  + create(AccessorModel) AccessorData
  ~ validateFloatType(int) void
  ~ validateShortType(int) void
  - createShort(AccessorModel, ByteBuffer) AccessorShortData
  - createByte(AccessorModel, ByteBuffer) AccessorByteData
  + createFloat(AccessorModel) AccessorFloatData
  ~ isUnsignedType(int) boolean
  - createInt(AccessorModel, ByteBuffer) AccessorIntData
  ~ validateCapacity(int, int, int, int) void
  + isShortType(int) boolean
  ~ createInt(AccessorModel) AccessorIntData
  + isFloatType(int) boolean
  ~ validateIntType(int) void
  ~ createByte(AccessorModel) AccessorByteData
}
class AccessorFloatData {
  + AccessorFloatData(int, ByteBuffer, int, int, int, Integer) 
  + get(int, int) float
  + createByteBuffer() ByteBuffer
  + computeMax() float[]
  + computeMin() float[]
  + createString(Locale, String, int) String
  + set(int, float) void
  + get(int) float
  + set(int, int, float) void
}
class AccessorIntData {
  + AccessorIntData(int, ByteBuffer, int, int, int, Integer) 
  - boolean unsigned
  + createByteBuffer() ByteBuffer
  + get(int, int) int
  + computeMaxLong() long[]
  + createString(Locale, String, int) String
  + getLong(int) long
  + computeMax() int[]
  + computeMin() int[]
  + set(int, int, int) void
  + computeMinLong() long[]
  + set(int, int) void
  + getLong(int, int) long
  + get(int) int
   boolean unsigned
}
class AccessorModel {
  + AccessorModel(int, int, ElementType) 
  - ElementType elementType
  - AccessorData accessorData
  - int byteStride
  - int count
  - Number[] max
  - int componentType
  - Number[] min
  - int byteOffset
  - BufferViewModel bufferViewModel
   ElementType elementType
   BufferViewModel bufferViewModel
   Number[] min
   int componentSizeInBytes
   int count
   AccessorData accessorData
   int elementSizeInBytes
   Number[] max
   int componentType
   int byteStride
   int byteOffset
   Class~?~ componentDataType
}
class AccessorShortData {
  + AccessorShortData(int, ByteBuffer, int, int, int, Integer) 
  - boolean unsigned
  + computeMin() short[]
  + get(int) short
  + createString(Locale, String, int) String
  + computeMinInt() int[]
  + computeMax() short[]
  + get(int, int) short
  + set(int, short) void
  + computeMaxInt() int[]
  + getInt(int) int
  + set(int, int, short) void
  + getInt(int, int) int
  + createByteBuffer() ByteBuffer
   boolean unsigned
}
class AccessorSparse {
  + AccessorSparse() 
  - Integer count
  - AccessorSparseIndices indices
  - AccessorSparseValues values
   AccessorSparseIndices indices
   AccessorSparseValues values
   Integer count
}
class AccessorSparseIndices {
  + AccessorSparseIndices() 
  - Integer bufferView
  - Integer componentType
  - Integer byteOffset
  + defaultByteOffset() Integer
   Integer byteOffset
   Integer componentType
   Integer bufferView
}
class AccessorSparseUtils {
  - AccessorSparseUtils() 
  - extractIndices(AccessorData) int[]
  - substituteShortAccessorData(AccessorShortData, AccessorShortData, AccessorData, AccessorShortData) void
  - substituteIntAccessorData(AccessorIntData, AccessorIntData, AccessorData, AccessorIntData) void
  - substituteFloatAccessorData(AccessorFloatData, AccessorFloatData, AccessorData, AccessorFloatData) void
  + substituteAccessorData(AccessorData, AccessorData, AccessorData, AccessorData) void
  - substituteByteAccessorData(AccessorByteData, AccessorByteData, AccessorData, AccessorByteData) void
}
class AccessorSparseValues {
  + AccessorSparseValues() 
  - Integer bufferView
  - Integer byteOffset
  + defaultByteOffset() Integer
   Integer byteOffset
   Integer bufferView
}
class Accessors {
  - Accessors() 
  + getDataTypeForAccessorComponentType(int) Class~?~
  + getNumComponentsForAccessorType(String) int
  + getNumBytesForAccessorComponentType(int) int
}
class Acknowledge {
  + Acknowledge() 
  + encode(Acknowledge, FriendlyByteBuf) void
  + handle(Acknowledge, Supplier~Context~) void
  + decode(FriendlyByteBuf) Acknowledge
}
class AdjustmentYRotModifier {
  - AdjustmentYRotModifier(Player) 
  + apply(String) Optional~PartModifier~
  + getModifier(Player) AdjustmentModifier
}
class AdsJsonProperty {
  + AdsJsonProperty(Modifier) 
  + initComponents() void
}
class AdsModifier {
  + AdsModifier() 
  + String ID
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~Float~
  + readJson(String) JsonProperty~Modifier~
  + eval(List~Modifier~, CacheValue~Float~) void
   String ID
   String optionalFields
   int diagramsDataSize
}
class AimInaccuracyJsonProperty {
  + AimInaccuracyJsonProperty(Map~InaccuracyType, Modifier~) 
  + initComponents() void
}
class AimInaccuracyModifier {
  + AimInaccuracyModifier() 
  + String ID
  + readJson(String) JsonProperty~Map~InaccuracyType, Modifier~~
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + eval(List~Map~InaccuracyType, Modifier~~, CacheValue~Map~InaccuracyType, Float~~) void
  + initCache(ItemStack, GunData) CacheValue~Map~InaccuracyType, Float~~
   String ID
   int diagramsDataSize
}
class AimKey {
  + AimKey() 
  + cancelAim(ClientTickEvent) void
  + onAimPress(Post) void
  + onAimControllerPress(boolean) boolean
}
class Align {
<<enumeration>>
  + Align() 
  + values() Align[]
  + valueOf(String) Align
}
class AllowAttachmentTagMatcher {
  + AllowAttachmentTagMatcher() 
  + match(ResourceLocation, ResourceLocation) boolean
  - treeSearch(Set~String~, ResourceLocation, AtomicBoolean) void
}
class AmmoBoxItem {
  + AmmoBoxItem() 
  - playInsertSound(Entity) void
  - getTagColor(ItemStack) int
  + getBarWidth(ItemStack) int
  + getColor(ItemStack, int) int
  + isFoil(ItemStack) boolean
  + getStatue(ItemStack, ClientLevel?, LivingEntity?, int) float
  + isBarVisible(ItemStack) boolean
  + getName(ItemStack) Component
  + overrideOtherStackedOnMe(ItemStack, ItemStack, Slot, ClickAction, Player, SlotAccess) boolean
  - getOpenStatue(ItemStack, IAmmoBox) int
  + overrideStackedOnOther(ItemStack, Slot, ClickAction, Player) boolean
  + getBarColor(ItemStack) int
  - playRemoveOneSound(Entity) void
  + fillItemCategory(Output) void
  - getLevelStatue(ItemStack, IAmmoBox) int
  + getTooltipImage(ItemStack) Optional~TooltipComponent~
  + appendHoverText(ItemStack, Level?, List~Component~, TooltipFlag) void
}
class AmmoBoxItemDataAccessor {
<<Interface>>
  + isCreative(ItemStack) boolean
  + getAmmoId(ItemStack) ResourceLocation
  + setAmmoLevel(ItemStack, int) ItemStack
  + setCreative(ItemStack, boolean) ItemStack
  + getAmmoCount(ItemStack) int
  + isAllTypeCreative(ItemStack) boolean
  + setAmmoCount(ItemStack, int) void
  + setAmmoId(ItemStack, ResourceLocation) void
  + getAmmoLevel(ItemStack) int
  + isAmmoBoxOfGun(ItemStack, ItemStack) boolean
}
class AmmoBoxTooltip {
  + AmmoBoxTooltip(ItemStack, ItemStack, int) 
  - ItemStack ammoBox
  - ItemStack ammo
  - int count
   ItemStack ammo
   int count
   ItemStack ammoBox
}
class AmmoClothConfig {
  + AmmoClothConfig() 
  + init(ConfigBuilder, ConfigEntryBuilder) void
}
class AmmoConfig {
  + AmmoConfig() 
  + init(Builder) void
}
class AmmoCountPapi {
  + AmmoCountPapi() 
  + apply(ItemStack) String
}
class AmmoCountStyle {
<<enumeration>>
  + AmmoCountStyle() 
  + values() AmmoCountStyle[]
  + valueOf(String) AmmoCountStyle
}
class AmmoDisplay {
  + AmmoDisplay() 
  - ResourceLocation? slotTextureLocation
  - AmmoEntityDisplay? ammoEntity
  - ShellDisplay? shellDisplay
  - ResourceLocation modelLocation
  - AmmoParticle? particle
  - String tracerColor
  - ResourceLocation modelTexture
  - AmmoTransform? transform
  + init() void
   ResourceLocation modelLocation
   AmmoEntityDisplay? ammoEntity
   String tracerColor
   ShellDisplay? shellDisplay
   AmmoParticle? particle
   ResourceLocation modelTexture
   ResourceLocation? slotTextureLocation
   AmmoTransform? transform
}
class AmmoEntityDisplay {
  + AmmoEntityDisplay() 
  # ResourceLocation modelTexture
  - ResourceLocation modelLocation
   ResourceLocation modelLocation
   ResourceLocation modelTexture
}
class AmmoHitBlockEvent {
  + AmmoHitBlockEvent(Level, BlockHitResult, BlockState) 
  - BlockState state
  - Level level
  - BlockHitResult hitResult
   Level level
   BlockHitResult hitResult
   boolean cancelable
   BlockState state
}
class AmmoHitBlockEventJS {
  + AmmoHitBlockEventJS(AmmoHitBlockEvent) 
   ResourceLocation? eventSubId
}
class AmmoHitBlockWrapper {
<<Interface>>
   EntityKineticBullet ammo
   Level level
   BlockHitResult hitResult
   BlockState state
}
class AmmoIndexPOJO {
  + AmmoIndexPOJO() 
  - String? tooltip
  - String name
  - ResourceLocation display
  - int stackSize
   String name
   ResourceLocation display
   int stackSize
   String? tooltip
}
class AmmoItem {
  + AmmoItem() 
  + initializeClient(Consumer~IClientItemExtensions~) void
  + getName(ItemStack) Component
  + appendHoverText(ItemStack, Level?, List~Component~, TooltipFlag) void
  + getMaxStackSize(ItemStack) int
  + fillItemCategory() NonNullList~ItemStack~
}
class AmmoItemBuilder {
  - AmmoItemBuilder() 
  - int count
  + create() AmmoItemBuilder
  + build() ItemStack
   ResourceLocation id
   int count
}
class AmmoItemDataAccessor {
<<Interface>>
  + getAmmoId(ItemStack) ResourceLocation
  + setAmmoId(ItemStack, ResourceLocation?) void
  + isAmmoOfGun(ItemStack, ItemStack) boolean
}
class AmmoItemRenderer {
  + AmmoItemRenderer(BlockEntityRenderDispatcher, EntityModelSet) 
  + renderByItem(ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, int) void
  - applyScaleTransform(ItemDisplayContext, TransformScale, PoseStack) void
  - applyPositioningNodeTransform(List~BedrockPart~, PoseStack, Vector3f) void
  - applyPositioningTransform(ItemDisplayContext, TransformScale, BedrockAmmoModel, PoseStack) void
}
class AmmoNbtFactory {
  + AmmoNbtFactory() 
  + AmmoNbtFactory(AmmoItem) 
  + build() ItemStack
}
class AmmoParticle {
  + AmmoParticle() 
  - int count
  - float speed
  - int lifeTime
  - String name
  - Vector3f delta
  - ParticleOptions particleOptions
   ParticleOptions? particleOptions
   String name
   Vector3f delta
   int lifeTime
   float speed
   int count
}
class AmmoParticleSpawner {
  + AmmoParticleSpawner() 
  - spawnParticle(Object, AmmoParticle) void
  - createParticle(Object, AmmoParticle, RandomSource, Vector3f, float, Entity, ParticleEngine, ParticleOptions) void
  + addParticle(Object) void
}
class AmmoSpeedModifier {
  + AmmoSpeedModifier() 
  + String ID
  + readJson(String) JsonProperty~Modifier~
  + eval(List~Modifier~, CacheValue~Float~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~Float~
   String ID
   int diagramsDataSize
}
class AmmoTransform {
  + AmmoTransform() 
  - TransformScale scale
   TransformScale scale
   AmmoTransform default
}
class AnimateGeoItemRenderer~M, CTX~ {
  + AnimateGeoItemRenderer() 
  # M model
  + getRenderType(ItemStack) RenderType
  + initContext(ItemStack, Player, float) CTX
  + applyLevelCameraAnimation(ComputeCameraAngles, ItemStack, LocalPlayer) void
  + getTextureLocation(ItemStack) ResourceLocation
  + getStateMachine(ItemStack) LuaAnimationStateMachine~CTX~?
  + applyLevelCameraAnimation(ComputeCameraAngles, ItemStack, float) void
  + visualUpdate(ItemStack) void
  + triggerAnimation(ItemStack, String) void
  + applyItemInHandCameraAnimation(BeforeRenderHandEvent, ItemStack, float) void
  + applyFirstPersonPositioningTransform(PoseStack, BedrockAnimatedModel, ItemStack) void
  + applyItemInHandCameraAnimation(BeforeRenderHandEvent, ItemStack, LocalPlayer) void
  + needReInit(ItemStack) boolean
  + getPutAwayTime(ItemStack) long
  + renderByItem(ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, int) void
  + tryInit(ItemStack, Player, float) void
  + getPositioningNodeInverse(List~BedrockPart~) Matrix4f
  + tryExit(ItemStack, long) void
  + renderFirstPerson(LocalPlayer, ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, float) void
  + getModel(ItemStack) M
  + doExtraTransforms(PoseStack, M, ItemStack) void
  + updateContext(CTX, ItemStack, Player, float) void
   M model
}
class Animation {
  + Animation() 
  - List~AnimationSampler~ samplers
  - String name
  - List~AnimationChannel~ channels
  + removeChannels(AnimationChannel) void
  + addChannels(AnimationChannel) void
  + removeSamplers(AnimationSampler) void
  + addSamplers(AnimationSampler) void
   String name
   List~AnimationSampler~ samplers
   List~AnimationChannel~ channels
}
class AnimationBone {
  + AnimationBone() 
  - AnimationKeyframes position
  - AnimationKeyframes rotation
  - AnimationKeyframes scale
   AnimationKeyframes scale
   AnimationKeyframes position
   AnimationKeyframes rotation
}
class AnimationChannel {
  + AnimationChannel() 
  - Integer sampler
  - AnimationChannelTarget target
   Integer sampler
   AnimationChannelTarget target
}
class AnimationChannelContent {
  + AnimationChannelContent() 
  + AnimationChannelContent(AnimationChannelContent) 
}
class AnimationChannelTarget {
  + AnimationChannelTarget() 
  - Integer node
  - String path
   Integer node
   String path
}
class AnimationConstant {
  + AnimationConstant() 
}
class AnimationController {
  + AnimationController(List~ObjectAnimation~, AnimationListenerSupplier) 
  # Iterable~Integer~? updatingTrackArray
  + setBlending(int, boolean) void
  + update() void
  + containPrototype(String) boolean
  + removeAnimation(int) void
  + updateSoundOnly() void
  + providePrototypeIfAbsent(String, Supplier~ObjectAnimation~) void
  + queueAnimation(int, Queue~AnimationPlan~) void
  - updateByTrack(int, boolean) void
  + runAnimation(int, String, PlayType, float) void
  - run(int, String, PlayType, float) void
  + getAnimation(int) ObjectAnimationRunner?
   Iterable~Integer~? updatingTrackArray
}
class AnimationDataRegisterFactory {
  + AnimationDataRegisterFactory() 
  + registerData() void
}
class AnimationKeyframes {
  + AnimationKeyframes(Double2ObjectRBTreeMap~Keyframe~) 
  - Double2ObjectRBTreeMap~Keyframe~ keyframes
   Double2ObjectRBTreeMap~Keyframe~ keyframes
}
class AnimationKeyframesSerializer {
  + AnimationKeyframesSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) AnimationKeyframes
  - readVector3fElement(JsonElement, String) float
  - readKeyFrames(JsonElement) Keyframe
  - readVector3f(JsonArray) Vector3f
}
class AnimationListener {
<<Interface>>
  + initialValue() float[]
  + update(float[], boolean) void
   ChannelType type
}
class AnimationListenerSupplier {
<<Interface>>
  + supplyListeners(String, ChannelType) AnimationListener?
}
class AnimationManager {
  + AnimationManager() 
  + playLoopAnimation(AbstractClientPlayer, GunDisplayInstance, ResourceLocation, String) void
  + onMelee(GunMeleeEvent) void
  - isPlayerLie(AbstractClientPlayer) boolean
  + playLowerAnimation(AbstractClientPlayer, GunDisplayInstance, float) void
  + onReload(GunReloadEvent) void
  + onFire(GunShootEvent) void
  + playOnceAnimation(AbstractClientPlayer, GunDisplayInstance, ResourceLocation, String) void
  + onDraw(GunDrawEvent) void
  + hasPlayerAnimator3rd(GunDisplayInstance) boolean
  + playLoopUpperAnimation(AbstractClientPlayer, GunDisplayInstance, float) void
  + isFlying(AbstractClientPlayer) boolean
  - stopAnimation(AbstractClientPlayer, ResourceLocation, int) void
  + stopAllAnimation(AbstractClientPlayer, int) void
  + stopAllAnimation(AbstractClientPlayer) void
  + playRotationAnimation(AbstractClientPlayer, GunDisplayInstance) void
}
class AnimationModel {
  + AnimationModel() 
  - List~Channel~ channels
  - String name
  + addChannel(Channel) void
   String name
   List~Channel~ channels
}
class AnimationName {
  + AnimationName() 
}
class AnimationPlan {
  + AnimationPlan(String, Object, float) 
}
class AnimationSampler {
  + AnimationSampler() 
  - Integer output
  - Integer input
  - String interpolation
  + defaultInterpolation() String
   Integer output
   Integer input
   String interpolation
}
class AnimationSoundChannelContent {
  + AnimationSoundChannelContent() 
  + AnimationSoundChannelContent(AnimationSoundChannelContent) 
}
class AnimationState~T~ {
<<Interface>>
  + entryAction(T) void
  + transition(T, String) AnimationState~T~
  + update(T) void
  + exitAction(T) void
}
class AnimationStateContext {
  + AnimationStateContext() 
  - DiscreteTrackArray trackArray
  - boolean shouldHideCrossHair
  - AnimationStateMachine~?~? stateMachine
  + setAnimationProgress(int, float, boolean) void
  + findIdleTrack(int, boolean) int
  + adjustAnimationProgress(int, float, boolean) void
  + getAsSingletonTrack(int) int
  + ensureTracksAmount(int, int) void
  + assignNewTrack(int) int
  + holdAnimation(int) void
  + stopAnimation(int) void
  + isHolding(int) boolean
  + resumeAnimation(int) void
  + shouldHideCrossHair() boolean
  + isStopped(int) boolean
  + pauseAnimation(int) void
  - checkStateMachine() AnimationStateMachine~?~
  - checkTrackArray() void
  + addTrackLine() int
  + runAnimation(String, int, boolean, int, float) void
  + getTrack(int, int) int
  + ensureTrackLineSize(int) void
  + isPause(int) boolean
  + hasAnimationPrototype(String) boolean
  + trigger(String) void
   boolean shouldHideCrossHair
   AnimationStateMachine~?~? stateMachine
   int trackLineSize
   DiscreteTrackArray trackArray
}
class AnimationStateMachine~T~ {
  + AnimationStateMachine(AnimationController) 
  - AnimationController animationController
  - Supplier~Iterable~AnimationState~T~~~ statesSupplier
  # long exitingTime
  # T context
  - checkNullPointer() void
  + initialize() void
  + update() void
  + exit() void
  + visualUpdate() void
  + trigger(String) void
  + processContextIfExist(Consumer~T~) void
   long exitingTime
   T? context
   boolean initialized
   AnimationController animationController
   Supplier~Iterable~AnimationState~T~~~ statesSupplier
}
class AnimationStructure {
  + AnimationStructure(RawAnimationStructure) 
  - List~AccessorModel~ accessorModels
  - List~BufferModel~ bufferModels
  - List~AnimationModel~ animationModels
  - List~NodeModel~ nodeModels
  - List~BufferViewModel~ bufferViewModels
  + readDataUri(String) byte[]
  - createBufferViewModel(String, ByteBuffer) BufferViewModel
  - initAccessorModels() void
  - initAnimationModels() void
  - createBufferModels() void
  - createAnimationModels() void
  - createBufferViewModel(BufferView) BufferViewModel
  - initDenseAccessorModel(int, Accessor, AccessorModel) void
  - initBufferViewModels() void
  - createAccessorModels() void
  - substituteSparseAccessorData(Accessor, AccessorModel, AccessorData, AccessorData) void
  - createBufferViewModels() void
  - initSparseAccessorModel(int, Accessor, AccessorModel) void
  - clone(float[]) float[]
  - initBufferModels() void
  - createNodeModels() void
  - createSparseValuesAccessorData(AccessorSparseValues, int, int, int) AccessorData
  - initNodeModels() void
  - createSparseIndicesAccessorData(AccessorSparseIndices, int) AccessorData
  - createChannel(Animation, AnimationChannel) Channel
  - isDataUriString(String) boolean
  - isDataUri(URI) boolean
   List~AccessorModel~ accessorModels
   List~BufferViewModel~ bufferViewModels
   List~AnimationModel~ animationModels
   List~BufferModel~ bufferModels
   List~NodeModel~ nodeModels
}
class Animations {
  + Animations() 
  - writeBedrockScale(ObjectAnimationChannel, AnimationKeyframes) void
  - readVector3fToArray(float[], Vector3f, int) void
  - writeBedrockTranslation(ObjectAnimationChannel, AnimationKeyframes) void
  + createControllerFromBedrock(BedrockAnimationFile, AnimationListenerSupplier) AnimationController
  + createAnimationFromBedrock(BedrockAnimationFile) List~ObjectAnimation~
  - writeBedrockRotation(ObjectAnimationChannel, AnimationKeyframes) void
  + createControllerFromGltf(AnimationStructure, AnimationListenerSupplier) AnimationController
  - toAngle(Vector3f) void
}
class ArmorIgnoreJsonProperty {
  + ArmorIgnoreJsonProperty(Modifier) 
  + initComponents() void
}
class ArmorIgnoreModifier {
  + ArmorIgnoreModifier() 
  + String ID
  + eval(List~Modifier~, CacheValue~Float~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + readJson(String) JsonProperty~Modifier~
  + initCache(ItemStack, GunData) CacheValue~Float~
   String ID
   int diagramsDataSize
}
class AttachmentCacheProperty {
  + AttachmentCacheProperty() 
  + getCache(String) T
  + setCache(GunProperty~T~, T) void
  + eval(ItemStack, GunData) void
  + getCache(GunProperty~T~) T
}
class AttachmentData {
  + AttachmentData() 
  - MeleeData? meleeData
  - float weight
  - Map~String, JsonProperty~?~~ modifier
  - int extendedMagLevel
  + addModifier(String, JsonProperty~?~) void
   int extendedMagLevel
   Map~String, JsonProperty~?~~ modifier
   float weight
   MeleeData? meleeData
}
class AttachmentDataManager {
  + AttachmentDataManager() 
  # parseJson(JsonElement) AttachmentData
}
class AttachmentDataUtils {
  + AttachmentDataUtils() 
  - resolve(JsonProperty~?~, BooleanResolver~T~, Class~T~) boolean
  + getAmmoCountWithAttachment(ItemStack, GunData) int
  + getWightWithAttachment(ItemStack, GunData) double
  + getArmorIgnoreWithAttachment(ItemStack, GunData) double
  + getAllAttachmentData(ItemStack, GunData, Consumer~AttachmentData~) void
  + isExplodeEnabled(ItemStack, GunData) boolean
  + getMagExtendLevel(ItemStack, GunData) int
  + getHeadshotMultiplier(ItemStack, GunData) double
  - calcBooleanValue(ItemStack, GunData, String, Class~T~, BooleanResolver~T~) boolean
  + getDamageWithAttachment(ItemStack, GunData) double
  - getModifiers(ItemStack, GunData, String) List~Modifier~
}
class AttachmentDisplay {
  + AttachmentDisplay() 
  - float fov
  - Map~String, TextShow~ textShows
  - float[]? zoom
  - ResourceLocation slotTextureLocation
  - String? adapterNodeName
  - LaserConfig laserConfig
  - int[]? views
  - boolean isSight
  - float[]? viewsFov
  - boolean showMuzzle
  - boolean isScope
  - ResourceLocation texture
  - AttachmentLod? attachmentLod
  - Map~String, ResourceLocation~ sounds
  - ResourceLocation model
  + init() void
   AttachmentLod? attachmentLod
   Map~String, ResourceLocation~ sounds
   ResourceLocation model
   float[]? viewsFov
   boolean isScope
   String? adapterNodeName
   boolean showMuzzle
   float[]? zoom
   Map~String, TextShow~ textShows
   ResourceLocation slotTextureLocation
   ResourceLocation texture
   int[]? views
   LaserConfig? laserConfig
   float fov
   boolean isSight
}
class AttachmentIdFix {
  - AttachmentIdFix() 
  + updateAttachmentIdInTag(CompoundTag) boolean
  + updateAttachmentId(ResourceLocation) ResourceLocation
}
class AttachmentIndexPOJO {
  + AttachmentIndexPOJO() 
  - String name
  - ResourceLocation data
  - AttachmentType type
  - ResourceLocation display
  - boolean hidden
  - String? tooltip
  - int sort
   String name
   int sort
   ResourceLocation display
   String? tooltip
   AttachmentType type
   ResourceLocation data
   boolean hidden
}
class AttachmentItem {
  + AttachmentItem() 
  + initializeClient(Consumer~IClientItemExtensions~) void
  + getTooltipImage(ItemStack) Optional~TooltipComponent~
  + fillItemCategory(AttachmentType) NonNullList~ItemStack~
  - idNameSort() Comparator~Entry~ResourceLocation, CommonAttachmentIndex~~
  + verifyTagAfterLoad(CompoundTag) void
  + getName(ItemStack) Component
  + getType(ItemStack) AttachmentType
}
class AttachmentItemBuilder {
  - AttachmentItemBuilder() 
  - int count
  + create() AttachmentItemBuilder
  + build() ItemStack
   ResourceLocation skinId
   ResourceLocation id
   int count
}
class AttachmentItemDataAccessor {
<<Interface>>
  + getAttachmentId(ItemStack) ResourceLocation
  + setZoomNumber(ItemStack, int) void
  + getZoomNumberFromTag(CompoundTag?) int
  + isAttachmentLike(CompoundTag) boolean
  + setLaserColor(ItemStack, int) void
  + setSkinId(ItemStack, ResourceLocation?) void
  + setAttachmentId(ItemStack, ResourceLocation?) void
  + getSkinId(ItemStack) ResourceLocation?
  + getZoomNumber(ItemStack) int
  + setZoomNumberToTag(CompoundTag, int) void
  + hasCustomLaserColor(ItemStack) boolean
  + getAttachmentIdFromTag(CompoundTag?) ResourceLocation
  + getLaserColor(ItemStack) int
}
class AttachmentItemRenderer {
  + AttachmentItemRenderer(BlockEntityRenderDispatcher, EntityModelSet) 
  + renderByItem(ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, int) void
  - renderDefaultAttachment(ItemDisplayContext, PoseStack, MultiBufferSource, int, int, ClientAttachmentIndex) void
}
class AttachmentItemTooltip {
  + AttachmentItemTooltip(ResourceLocation, AttachmentType, ItemStack) 
  - AttachmentType type
  - ResourceLocation attachmentId
  - ItemStack attachmentItem
   ItemStack attachmentItem
   AttachmentType type
   ResourceLocation attachmentId
}
class AttachmentLockCommand {
  + AttachmentLockCommand() 
  + get() LiteralArgumentBuilder~CommandSourceStack~
  - setAttachmentLock(CommandContext~CommandSourceStack~) int
}
class AttachmentLod {
  + AttachmentLod() 
  - ResourceLocation modelLocation
  # ResourceLocation modelTexture
   ResourceLocation modelLocation
   ResourceLocation modelTexture
}
class AttachmentNbtFactory {
  + AttachmentNbtFactory(AttachmentItem) 
  + AttachmentNbtFactory() 
  + build() ItemStack
   ResourceLocation skinId
}
class AttachmentPropertyEvent {
  + AttachmentPropertyEvent(ItemStack, Object) 
  - Object cacheProperty
  - ItemStack gunItem
   ItemStack gunItem
   Object cacheProperty
}
class AttachmentPropertyEventJS {
  + AttachmentPropertyEventJS(AttachmentPropertyEvent) 
   ItemStack eventItemStack
}
class AttachmentPropertyManager {
  + AttachmentPropertyManager() 
  - Map~String, IAttachmentModifier~?, ?~~ MODIFIERS
  + registerModifier() void
  + functionEval(double, double, String) double
  + eval(List~Modifier~, double) double
  + postChangeEvent(LivingEntity, ItemStack) void
  + eval(List~Boolean~, boolean) boolean
  + eval(Modifier, double) double
   Map~String, IAttachmentModifier~?, ?~~ MODIFIERS
}
class AttachmentPropertyWrapper {
<<Interface>>
   AttachmentCacheProperty cacheProperty
   ItemStack gunItem
}
class AttachmentQueryCategory {
  + AttachmentQueryCategory(IGuiHelper) 
  - Component TITLE
  + setRecipe(IRecipeLayoutBuilder, AttachmentQueryEntry, IFocusGroup) void
  + draw(AttachmentQueryEntry, IRecipeSlotsView, GuiGraphics, double, double) void
   Component TITLE
   IDrawable background
   RecipeType~AttachmentQueryEntry~ recipeType
   IDrawable icon
}
class AttachmentQueryEntry {
  + AttachmentQueryEntry(ResourceLocation, GunTabType) 
  - ItemStack attachmentStack
  - List~ItemStack~ extraAllowGunStacks
  - List~ItemStack~ allowGunStacks
  - addAllAllowGuns(GunTabType) void
  - dividedGuns() void
   List~AttachmentQueryEntry~ allAttachmentQueryEntries
   List~ItemStack~ extraAllowGunStacks
   List~ItemStack~ allowGunStacks
   ItemStack attachmentStack
}
class AttachmentRender {
  + AttachmentRender(BedrockGunModel, AttachmentType) 
  + renderAttachment(ItemStack, ItemStack, PoseStack, ItemDisplayContext, int, int) void
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class AttachmentSkin {
  + AttachmentSkin() 
  - ResourceLocation texture
  - ResourceLocation parent
  - String name
  - ResourceLocation model
   ResourceLocation texture
   String name
   ResourceLocation parent
   ResourceLocation model
}
class AttachmentType {
<<enumeration>>
  + AttachmentType() 
  + values() AttachmentType[]
  + valueOf(String) AttachmentType
}
class AttachmentsTagManager {
  + AttachmentsTagManager() 
  # Map~ResourceLocation, String~ networkCache
  - parseJson(JsonElement) List~String~
  + getAllowAttachmentTags(ResourceLocation) Set~String~
  # prepare(ResourceManager, ProfilerFiller) Map~ResourceLocation, List~JsonElement~~
  + getAttachmentTags(ResourceLocation) Set~String~
  # apply(Map~ResourceLocation, List~JsonElement~~, ResourceManager, ProfilerFiller) void
   DataType type
   Map~ResourceLocation, String~ networkCache
}
class BeamRenderer {
  + BeamRenderer() 
  - getLaserConfig(ItemStack) LaserConfig
  + renderLaserBeam(ItemStack, PoseStack, ItemDisplayContext, List~BedrockPart~) void
  - stringVertex(float, float, VertexConsumer, Pose, int, int, int, boolean) void
}
class BedrockAmmoModel {
  + BedrockAmmoModel(BedrockModelPOJO, BedrockVersion) 
  # List~BedrockPart~? fixedOriginPath
  # List~BedrockPart~? groundOriginPath
  # List~BedrockPart~? thirdPersonHandOriginPath
   List~BedrockPart~? groundOriginPath
   List~BedrockPart~? thirdPersonHandOriginPath
   List~BedrockPart~? fixedOriginPath
}
class BedrockAnimatedModel {
  + BedrockAnimatedModel(BedrockModelPOJO, BedrockVersion) 
  # List~BedrockPart~? idleSightPath
  # List~BedrockPart~? constraintPath
  - CameraAnimationObject cameraAnimationObject
  - ConstraintObject? constraintObject
  + setFunctionalRenderer(String, Function~BedrockPart, IFunctionalRenderer~) void
  # loadLegacyModel(BedrockModelPOJO) void
  # loadNewModel(BedrockModelPOJO) void
  + supplyListeners(String, ChannelType) AnimationListener?
  + cleanCameraAnimationTransform() void
  + cleanAnimationTransform() void
   List~BedrockPart~ idleSightPath
   List~BedrockPart~? constraintPath
   BedrockPart rootNode
   boolean renderHand
   ConstraintObject? constraintObject
   CameraAnimationObject cameraAnimationObject
}
class BedrockAnimation {
  + BedrockAnimation() 
  - boolean loop
  - double animationLength
  - Map~String, AnimationBone~? bones
  - SoundEffectKeyframes soundEffects
   boolean loop
   double animationLength
   Map~String, AnimationBone~? bones
   SoundEffectKeyframes soundEffects
}
class BedrockAnimationFile {
  + BedrockAnimationFile() 
  - String version
  - Map~String, BedrockAnimation~ animations
   String version
   Map~String, BedrockAnimation~ animations
}
class BedrockAttachmentModel {
  + BedrockAttachmentModel(BedrockModelPOJO, BedrockVersion) 
  - boolean isSight
  - float scopeViewRadiusModifier
  - renderTempPart(PoseStack, ItemDisplayContext, RenderType, int, int, List~BedrockPart~) void
  + isScope() boolean
  + setIsSight(boolean) void
  - getBedrockPartCenter(PoseStack, List~BedrockPart~) Vector3f
  - renderDivisionOnly(PoseStack, ItemDisplayContext, RenderType, int, int) void
  + getScopeViewPath(int) List~BedrockPart~?
  - renderOcularStencil(PoseStack, ItemDisplayContext, RenderType, int, int, boolean) void
  - renderBoth(PoseStack, ItemDisplayContext, RenderType, int, int) void
  + render(ItemStack?, ItemStack, PoseStack, ItemDisplayContext, RenderType, int, int) void
  - renderOcularAndDivision(PoseStack, ItemDisplayContext, RenderType, int, int, boolean) void
  - renderScope(PoseStack, ItemDisplayContext, RenderType, int, int) void
  - renderSight(PoseStack, ItemDisplayContext, RenderType, int, int) void
   float scopeViewRadiusModifier
   Map~String, TextShow~ textShowList
   boolean isScope
   boolean isSight
}
class BedrockCube {
<<Interface>>
  + compile(Pose, VertexConsumer, int, int, float, float, float, float) void
}
class BedrockCubeBox {
  + BedrockCubeBox(float, float, float, float, float, float, float, float, float, boolean, float, float) 
  + compile(Pose, VertexConsumer, int, int, float, float, float, float) void
}
class BedrockCubePerFace {
  + BedrockCubePerFace(float, float, float, float, float, float, float, float, float, FaceUVsItem) 
  - getTexturedQuad(BedrockVertex[], float, float, Direction, FaceUVsItem) BedrockPolygon
  + compile(Pose, VertexConsumer, int, int, float, float, float, float) void
}
class BedrockGunModel {
  + BedrockGunModel(BedrockModelPOJO, BedrockVersion) 
  - EnumMap~AttachmentType, ItemStack~ currentAttachmentItem
  # List~BedrockPart~? muzzleFlashPosPath
  # List~BedrockPart~? fixedOriginPath
  # List~BedrockPart~? ironSightPath
  # List~BedrockPart~? groundOriginPath
  - ItemStack currentGunItem
  - boolean renderHand
  # List~BedrockPart~? thirdPersonHandOriginPath
  # List~BedrockPart~? scopePosPath
  # BedrockPart? additionalMagazineNode
  # List~BedrockPart~? idleSightPath
  + getShellRender(int) ShellRender?
  - extendedMagHiddenRender(BedrockPart, int) IFunctionalRenderer?
  - renderAdditionalMagazine(BedrockPart) IFunctionalRenderer
  - handguardTacticalRender(BedrockPart) IFunctionalRenderer?
  + render(PoseStack, ItemStack, ItemDisplayContext, RenderType, int, int) void
  + supplyListeners(String, ChannelType) AnimationListener?
  - scopeHiddenRender(BedrockPart, Predicate~ItemStack~) IFunctionalRenderer?
  - cacheShellOriginNodes() void
  - checkShowMuzzle(BedrockPart, ItemStack) boolean
  - cacheOtherPath() void
  - ammoHiddenRender(BedrockPart, Predicate~IGun~) IFunctionalRenderer?
  + getRefitAttachmentViewPath(AttachmentType) List~BedrockPart~?
  - cacheRefitAttachmentViewPath() void
  - attachmentAdapterNodeRender(BedrockPart) IFunctionalRenderer?
  - handguardDefaultRender(BedrockPart) IFunctionalRenderer?
  - allAttachmentRender() void
  + cleanAnimationTransform() void
   List~BedrockPart~? groundOriginPath
   ItemStack currentGunItem
   List~BedrockPart~? fixedOriginPath
   BedrockPart? rootNode
   List~BedrockPart~? ironSightPath
   List~BedrockPart~? idleSightPath
   Map~String, TextShow~ textShowList
   BedrockPart? additionalMagazineNode
   List~BedrockPart~? muzzleFlashPosPath
   EnumMap~AttachmentType, ItemStack~ currentAttachmentItem
   List~BedrockPart~? thirdPersonHandOriginPath
   List~BedrockPart~? scopePosPath
   boolean renderHand
}
class BedrockModel {
  + BedrockModel(BedrockModelPOJO, BedrockVersion) 
  # BedrockModel() 
  # List~BedrockPart~ shouldRender
  # Vec2? size
  # Vec3? offset
  # HashMap~String, BonesItem~ indexBones
  # loadLegacyModel(BedrockModelPOJO) void
  - setRotationAngle(BedrockPart, float, float, float) void
  + getNode(String) BedrockPart
  + getBone(String) BonesItem
  # getPath(ModelRendererWrapper?) List~BedrockPart~
  # convertOrigin(BonesItem, CubesItem, int) float
  + render(PoseStack, ItemDisplayContext, RenderType, int, int) void
  # convertOrigin(CubesItem, int) float
  + render(PoseStack, ItemDisplayContext, RenderType, int, int, float, float, float, float) void
  + delegateRender(IFunctionalRenderer) void
  # loadNewModel(BedrockModelPOJO) void
  # convertPivot(BonesItem, int) float
  # convertPivot(BonesItem, CubesItem, int) float
  # convertRotation(float) float
   Vec3? offset
   List~BedrockPart~ shouldRender
   Vec2? size
   HashMap~String, BonesItem~ indexBones
}
class BedrockModelPOJO {
  + BedrockModelPOJO() 
  - String formatVersion
  - GeometryModelLegacy? geometryModelLegacy
  - List~GeometryModelNew~? geometryModelNew
   GeometryModelNew? geometryModelNew
   GeometryModelLegacy? geometryModelLegacy
   String formatVersion
}
class BedrockPart {
  + BedrockPart(String?) 
  - float initRotX
  - float initRotZ
  # BedrockPart parent
  - float initRotY
  + translateAndRotateAndScale(PoseStack) void
  + compile(Pose, VertexConsumer, int, int, float, float, float, float) void
  + setInitRotationAngle(float, float, float) void
  + render(PoseStack, ItemDisplayContext, VertexConsumer, int, int) void
  + setPos(float, float, float) void
  + render(PoseStack, ItemDisplayContext, VertexConsumer, int, int, float, float, float, float) void
  + addChild(BedrockPart) void
  + getRandomCube(Random) BedrockCube
   float initRotX
   boolean empty
   float initRotY
   BedrockPart parent
   float initRotZ
}
class BedrockPolygon {
  + BedrockPolygon(BedrockVertex[], float, float, float, float, float, float, boolean, Direction) 
}
class BedrockVersion {
<<enumeration>>
  - BedrockVersion(String) 
  - String version
  + valueOf(String) BedrockVersion
  + isNewVersion(BedrockModelPOJO) boolean
  + isLegacyVersion(BedrockModelPOJO) boolean
  + values() BedrockVersion[]
   String version
}
class BedrockVertex {
  + BedrockVertex(float, float, float, float, float) 
  + BedrockVertex(Vector3f, float, float) 
  + remap(float, float) BedrockVertex
}
class BeforeRenderHandEvent {
  + BeforeRenderHandEvent(PoseStack) 
  - PoseStack poseStack
   PoseStack poseStack
}
class BeforeRenderHandEventJS {
  + BeforeRenderHandEventJS(BeforeRenderHandEvent) 
}
class BeforeRenderHandWrapper {
<<Interface>>
   PoseStack poseStack
}
class BellRing {
  + BellRing() 
  + onAmmoHitBlock(AmmoHitBlockEvent) void
}
class BlackList {
  + BlackList() 
  + addBlackList() void
}
class BlockData {
  + BlockData() 
  - List~Object~ tabs
  - ResourceLocation filter
   List~Object~ tabs
   ResourceLocation filter
}
class BlockDisplay {
  + BlockDisplay() 
  - ResourceLocation modelLocation
  - ResourceLocation modelTexture
  - ItemTransforms transforms
  + init() void
   ResourceLocation modelLocation
   ItemTransforms transforms
   ResourceLocation modelTexture
}
class BlockIndexPOJO {
  + BlockIndexPOJO() 
  - ResourceLocation display
  - ResourceLocation data
  - ResourceLocation id
  - String? tooltip
  - String name
  - int stackSize
   String name
   ResourceLocation data
   ResourceLocation display
   ResourceLocation id
   int stackSize
   String? tooltip
}
class BlockItemBuilder {
  - BlockItemBuilder(ItemLike) 
  - int count
  + build() ItemStack
  + create(ItemLike) BlockItemBuilder
   ResourceLocation id
   int count
}
class BlockItemDataAccessor {
<<Interface>>
  + getBlockId(ItemStack) ResourceLocation
  + setBlockId(ItemStack, ResourceLocation?) void
}
class BlockItemTooltip {
  + BlockItemTooltip(ResourceLocation) 
  - ResourceLocation blockId
   ResourceLocation blockId
}
class BlockRayTrace {
  + BlockRayTrace() 
  - getBlockHitResult(Level, ClipContext, BlockPos, BlockState) BlockHitResult?
  + rayTraceBlocks(Level, ClipContext) BlockHitResult
  - performRayTrace(ClipContext, BiFunction~ClipContext, BlockPos, T~, Function~ClipContext, T~) T
}
class BobHurt {
  + BobHurt() 
}
class BobHurt {
  + BobHurt() 
}
class BobView {
  + BobView() 
}
class BobView {
  + BobView() 
}
class Bolt {
<<enumeration>>
  + Bolt() 
  + values() Bolt[]
  + valueOf(String) Bolt
}
class BonesItem {
  + BonesItem() 
  - List~Float~ rotation
  - String parent
  - String name
  - List~Float~ pivot
  - boolean mirror
  - List~CubesItem~ cubes
  + toString() String
   String name
   List~CubesItem~? cubes
   boolean mirror
   List~Float~ rotation
   List~Float~ pivot
   String parent
}
class BooleanResolver~T~ {
<<Interface>>
  + apply(T) boolean
}
class Buffer {
  + Buffer() 
  - String uri
  - Integer byteLength
   String uri
   Integer byteLength
}
class BufferModel {
  + BufferModel() 
  - String uri
  - ByteBuffer bufferData
   int byteLength
   String uri
   ByteBuffer bufferData
}
class BufferView {
  + BufferView() 
  - Integer byteStride
  - Integer target
  - Integer buffer
  - Integer byteLength
  - Integer byteOffset
  + defaultByteOffset() Integer
   Integer target
   Integer byteLength
   Integer buffer
   Integer byteOffset
   Integer byteStride
}
class BufferViewModel {
  + BufferViewModel(Integer) 
  - int byteLength
  - Integer byteStride
  - BufferModel bufferModel
  - Consumer~ByteBuffer~ sparseSubstitutionCallback
  - int byteOffset
  - Integer target
   Integer target
   Integer byteStride
   Consumer~ByteBuffer~ sparseSubstitutionCallback
   int byteLength
   BufferModel bufferModel
   int byteOffset
   ByteBuffer bufferViewData
}
class Buffers {
  - Buffers() 
  + createSlice(ByteBuffer, int, int) ByteBuffer
  + createSlice(ByteBuffer) ByteBuffer
  + create(byte[], int, int) ByteBuffer
  + create(byte[]) ByteBuffer
  + create(int) ByteBuffer
}
class Builder~E, T~ {
  - Builder(SyncedClassKey~E~, IDataSerializer~T~) 
  + id(ResourceLocation) Builder~E, T~
  + key(String) Builder~E, T~
  + syncMode(SyncMode) Builder~E, T~
  + build() SyncedDataKey~E, T~
  + id(String) Builder~E, T~
  + saveToFile() Builder~E, T~
  + resetOnDeath() Builder~E, T~
  + defaultValueSupplier(Supplier~T~) Builder~E, T~
}
class Builder~T~ {
  + Builder() 
  + build() LiteralFilter~T~
  + add(T) Builder~T~
  + create() Builder~T~
}
class BulletData {
  + BulletData() 
  - int bulletAmount
  - float speed
  - float damageAmount
  - int pierce
  - float friction
  - ExtraDamage? extraDamage
  - int tracerCountInterval
  - ExplosionData? explosionData
  - int igniteEntityTime
  - Ignite ignite
  - float gravity
  - float lifeSecond
  - float knockback
  + hasTracerAmmo() boolean
   ExtraDamage? extraDamage
   Ignite ignite
   float gravity
   float speed
   float damageAmount
   float knockback
   int pierce
   int bulletAmount
   int igniteEntityTime
   float lifeSecond
   int tracerCountInterval
   float friction
   ExplosionData? explosionData
}
class BulletHoleOption {
  + BulletHoleOption(Direction, BlockPos, String, String, String) 
  - BlockPos pos
  - String gunDisplayId
  - String gunId
  - String ammoId
  - Direction direction
   BlockPos pos
   String ammoId
   String gunDisplayId
   ParticleType~?~ type
   Direction direction
   String gunId
}
class BulletHoleParticle {
  + BulletHoleParticle(ClientLevel, double, double, double, Direction, BlockPos, String, String, String) 
  + render(VertexConsumer, Camera, float) void
  - getSprite(BlockPos) TextureAtlasSprite
  + tick() void
  - shouldRemove() boolean
  - getLifetimeFromConfig(ClientLevel) int
   ParticleRenderType renderType
   float u0
   float v1
   float v0
   float u1
   TextureAtlasSprite sprite
}
class BulletSpeedJsonProperty {
  + BulletSpeedJsonProperty(Modifier) 
  + initComponents() void
}
class BurstData {
  + BurstData() 
  - boolean continuousShoot
  - int count
  - double minInterval
  - int bpm
   double minInterval
   boolean continuousShoot
   int bpm
   int count
}
class CacheValue~T~ {
  + CacheValue(T) 
  - T value
   T value
}
class CameraAnimationObject {
  + CameraAnimationObject() 
  + supplyListeners(String, ChannelType) AnimationListener?
}
class CameraRotateListener {
  + CameraRotateListener(CameraAnimationObject) 
  + update(float[], boolean) void
  + initialValue() float[]
   ChannelType type
}
class CameraSetupEvent {
  + CameraSetupEvent() 
  + applyItemInHandCameraAnimation(BeforeRenderHandEvent) void
  + onComputeMovementFov(ComputeFovModifierEvent) void
  + initialCameraRecoil(GunFireEvent) void
  + applyCameraRecoil(ComputeCameraAngles) void
  + applyLevelCameraAnimation(ComputeCameraAngles) void
  + applyGunModelFovModifying(ComputeFov) void
  + applyScopeMagnification(ComputeFov) void
}
class CapabilityRegistry {
  + CapabilityRegistry() 
  + onRegisterCapabilities(RegisterCapabilitiesEvent) void
}
class ChangeGunPropertyEvent {
  + ChangeGunPropertyEvent() 
  + internalOnAttachmentPropertyEvent(AttachmentPropertyEvent) void
}
class Channel {
  + Channel(Sampler, NodeModel, String) 
  + nodeModel() NodeModel
  + sampler() Sampler
  + path() String
}
class ChannelType {
<<enumeration>>
  + ChannelType() 
  + values() ChannelType[]
  + valueOf(String) ChannelType
}
class Checkbox {
  + Checkbox(int, int, int, int, Component, boolean) 
  + Checkbox(int, int, int, int, Component, String, boolean) 
  + Checkbox(int, int, int, int, Component, boolean, boolean) 
  - String id
  + onPress() void
  + updateWidgetNarration(NarrationElementOutput) void
  + selected() boolean
  + renderWidget(GuiGraphics, int, int, float) void
   String id
}
class ClientAmmoBoxTooltip {
  + ClientAmmoBoxTooltip(AmmoBoxTooltip) 
  + getWidth(Font) int
  + renderText(Font, int, int, Matrix4f, BufferSource) void
  + renderImage(Font, int, int, GuiGraphics) void
   int height
}
class ClientAmmoIndex {
  - ClientAmmoIndex() 
  - ResourceLocation slotTextureLocation
  - float[] tracerColor
  - ResourceLocation? ammoEntityTextureLocation
  - BedrockAmmoModel? ammoEntityModel
  - String name
  - ResourceLocation? shellTextureLocation
  - BedrockAmmoModel? ammoModel
  - String? tooltipKey
  - AmmoParticle? particle
  - int stackSize
  - AmmoTransform transform
  - ResourceLocation? modelTextureLocation
  - BedrockAmmoModel? shellModel
  - checkSlotTexture(AmmoDisplay, ClientAmmoIndex) void
  - checkTransform(AmmoDisplay, ClientAmmoIndex) void
  - checkIndex(AmmoIndexPOJO, ClientAmmoIndex) void
  - checkTracerColor(AmmoDisplay, ClientAmmoIndex) void
  + getInstance(AmmoIndexPOJO) ClientAmmoIndex
  - checkName(AmmoIndexPOJO, ClientAmmoIndex) void
  - checkDisplay(AmmoIndexPOJO) AmmoDisplay
  - checkTextureAndModel(AmmoDisplay, ClientAmmoIndex) void
  - checkAmmoEntity(AmmoDisplay, ClientAmmoIndex) void
  - checkStackSize(AmmoIndexPOJO, ClientAmmoIndex) void
  - checkParticle(AmmoDisplay, ClientAmmoIndex) void
  - checkShell(AmmoDisplay, ClientAmmoIndex) void
   ResourceLocation? ammoEntityTextureLocation
   BedrockAmmoModel? shellModel
   ResourceLocation slotTextureLocation
   AmmoTransform transform
   float[] tracerColor
   String name
   ResourceLocation? shellTextureLocation
   BedrockAmmoModel? ammoEntityModel
   BedrockAmmoModel? ammoModel
   String? tooltipKey
   AmmoParticle? particle
   ResourceLocation? modelTextureLocation
   int stackSize
}
class ClientAssetsManager {
<<enumeration>>
  + ClientAssetsManager() 
  + getAmmoDisplay(ResourceLocation) AmmoDisplay?
  + getAttachmentDisplay(ResourceLocation) AttachmentDisplay?
  + getGltfAnimation(ResourceLocation) AnimationStructure?
  + reloadAndRegister(Consumer~PreparableReloadListener~) void
  + getGunDisplay(ResourceLocation) GunDisplay?
  - register(T) T
  + reloadAllPack() void
  + getPackInfo(String) PackInfo?
  + getPackInfo(ResourceLocation?) PackInfo?
  + valueOf(String) ClientAssetsManager
  + getBlockDisplay(ResourceLocation) BlockDisplay?
  + getBedrockModelPOJO(ResourceLocation) BedrockModelPOJO?
  + getSoundBuffers(ResourceLocation) SoundData?
  + values() ClientAssetsManager[]
  + getBedrockAnimations(ResourceLocation) BedrockAnimationFile?
  + getScript(ResourceLocation) LuaTable?
   Set~Entry~ResourceLocation, GunDisplay~~ gunDisplays
}
class ClientAttachmentIndex {
  - ClientAttachmentIndex() 
  - float[]? zoom
  - boolean isScope
  - ResourceLocation slotTexture
  - int[] views
  - String? tooltipKey
  - AttachmentData data
  - BedrockAttachmentModel? attachmentModel
  - boolean showMuzzle
  - String name
  - String? adapterNodeName
  - boolean isSight
  - ResourceLocation? modelTexture
  - Map~String, ResourceLocation~ sounds
  - Pair~BedrockAttachmentModel, ResourceLocation~? lodModel
  - LaserConfig? laserConfig
  - float[] viewsFov
  - checkTextShow(AttachmentDisplay, BedrockAttachmentModel) void
  + getSkinIndex(ResourceLocation?) ClientAttachmentSkinIndex?
  - checkData(AttachmentIndexPOJO, ClientAttachmentIndex) void
  - checkDisplay(AttachmentIndexPOJO, ClientAttachmentIndex) AttachmentDisplay
  - checkName(AttachmentIndexPOJO, ClientAttachmentIndex) void
  - checkSlotTexture(AttachmentDisplay, ClientAttachmentIndex) void
  - checkTextureAndModel(AttachmentDisplay, ClientAttachmentIndex) void
  + getOrLoadAttachmentModel(ResourceLocation?) BedrockAttachmentModel?
  + getAttachmentModel(BedrockModelPOJO) BedrockAttachmentModel?
  - checkLod(AttachmentDisplay, ClientAttachmentIndex) void
  + getInstance(ResourceLocation, AttachmentIndexPOJO) ClientAttachmentIndex
  - checkIndex(AttachmentIndexPOJO, ClientAttachmentIndex) void
  - checkSounds(AttachmentDisplay, ClientAttachmentIndex) void
   Map~String, ResourceLocation~ sounds
   AttachmentData data
   float[] viewsFov
   boolean isScope
   ResourceLocation slotTexture
   String? adapterNodeName
   boolean showMuzzle
   float[]? zoom
   String name
   Pair~BedrockAttachmentModel, ResourceLocation~? lodModel
   String? tooltipKey
   ResourceLocation? modelTexture
   int[] views
   LaserConfig? laserConfig
   BedrockAttachmentModel? attachmentModel
   boolean isSight
}
class ClientAttachmentItemTooltip {
  + ClientAttachmentItemTooltip(AttachmentItemTooltip) 
  + renderText(Font, int, int, Matrix4f, BufferSource) void
  - getShowGuns() void
  + rgbToHex(int) String
  + getWidth(Font) int
  + renderImage(Font, int, int, GuiGraphics) void
  - getAllAllowGuns(List~ItemStack~, ResourceLocation) List~ItemStack~
  - addPackInfo() void
  - addText(AttachmentType) void
   int height
}
class ClientAttachmentSkinIndex {
  - ClientAttachmentSkinIndex() 
  - ResourceLocation texture
  - BedrockAttachmentModel model
  - String name
  - checkIndex(AttachmentSkin, ClientAttachmentSkinIndex) void
  - checkName(AttachmentSkin, ClientAttachmentSkinIndex) void
  + getInstance(AttachmentSkin) ClientAttachmentSkinIndex
  - checkTextureAndModel(AttachmentSkin, ClientAttachmentSkinIndex) void
   ResourceLocation texture
   String name
   BedrockAttachmentModel model
}
class ClientBlockIndex {
  + ClientBlockIndex() 
  - ResourceLocation texture
  - String tooltipKey
  - BedrockModel model
  - ItemTransforms transforms
  - String name
  - checkTransforms(BlockDisplay, ClientBlockIndex) void
  - checkDisplay(BlockIndexPOJO, ClientBlockIndex) BlockDisplay
  - checkName(BlockIndexPOJO, ClientBlockIndex) void
  - checkIndex(BlockIndexPOJO, ClientBlockIndex) void
  + getInstance(BlockIndexPOJO) ClientBlockIndex
  - checkModel(BlockDisplay, ClientBlockIndex) void
   ResourceLocation texture
   String name
   String tooltipKey
   BedrockModel model
   ItemTransforms transforms
}
class ClientBlockItemTooltip {
  + ClientBlockItemTooltip(BlockItemTooltip) 
  - addPackInfo() void
  - addText() void
  + renderText(Font, int, int, Matrix4f, BufferSource) void
  + renderImage(Font, int, int, GuiGraphics) void
  + getWidth(Font) int
   int height
}
class ClientConfig {
  + ClientConfig() 
  + init() ModConfigSpec
}
class ClientGunIndex {
  - ClientGunIndex() 
  - String type
  - String itemType
  - String name
  - GunData gunData
  - checkIndex(GunIndexPOJO, ClientGunIndex) void
  - checkDisplay(GunIndexPOJO) GunDisplay
  + getInstance(GunIndexPOJO) ClientGunIndex
  - checkData(GunIndexPOJO, ClientGunIndex) void
  - checkName(GunIndexPOJO, ClientGunIndex) void
   String name
   String type
   GunData gunData
   String itemType
   GunDisplayInstance defaultDisplay
}
class ClientGunTooltip {
  + ClientGunTooltip(GunTooltip) 
  + getWidth(Font) int
  + renderImage(Font, int, int, GuiGraphics) void
  + renderText(Font, int, int, Matrix4f, BufferSource) void
  - getText() void
  - shouldShow(GunTooltipPart) boolean
   int height
}
class ClientHitMark {
  + ClientHitMark() 
  + onEntityKill(EntityKillByGunEvent) void
  + onEntityHurt(Post) void
}
class ClientIndexManager {
  + ClientIndexManager() 
  + loadGunIndex() void
  + loadAttachmentIndex() void
  + reload() void
  + loadGunDisplay() void
  + loadAmmoIndex() void
  + loadBlockIndex() void
   Set~Entry~ResourceLocation, ClientGunIndex~~ allGuns
   Set~Entry~ResourceLocation, ClientAmmoIndex~~ allAmmo
   Set~Entry~ResourceLocation, ClientAttachmentIndex~~ allAttachments
   Set~Entry~ResourceLocation, ClientBlockIndex~~ allBlocks
}
class ClientMessageCraft {
  + ClientMessageCraft(ResourceLocation, int) 
  + handle(ClientMessageCraft, Supplier~Context~) void
  + decode(FriendlyByteBuf) ClientMessageCraft
  + encode(ClientMessageCraft, FriendlyByteBuf) void
}
class ClientMessageLaserColor {
  + ClientMessageLaserColor(ItemStack, int) 
  - ClientMessageLaserColor() 
  + decode(FriendlyByteBuf) ClientMessageLaserColor
  + encode(ClientMessageLaserColor, FriendlyByteBuf) void
  + handle(ClientMessageLaserColor, Supplier~Context~) void
}
class ClientMessagePlayerAim {
  + ClientMessagePlayerAim(boolean) 
  + decode(FriendlyByteBuf) ClientMessagePlayerAim
  + encode(ClientMessagePlayerAim, FriendlyByteBuf) void
  + handle(ClientMessagePlayerAim, IPayloadContext) void
}
class ClientMessagePlayerBoltGun {
  + ClientMessagePlayerBoltGun() 
  + decode(FriendlyByteBuf) ClientMessagePlayerBoltGun
  + handle(ClientMessagePlayerBoltGun, IPayloadContext) void
  + encode(ClientMessagePlayerBoltGun, FriendlyByteBuf) void
}
class ClientMessagePlayerCancelReload {
  + ClientMessagePlayerCancelReload() 
  + handle(ClientMessagePlayerCancelReload, IPayloadContext) void
  + decode(FriendlyByteBuf) ClientMessagePlayerCancelReload
  + encode(ClientMessagePlayerCancelReload, FriendlyByteBuf) void
}
class ClientMessagePlayerCrawl {
  + ClientMessagePlayerCrawl(boolean) 
  + decode(FriendlyByteBuf) ClientMessagePlayerCrawl
  + encode(ClientMessagePlayerCrawl, FriendlyByteBuf) void
  + handle(ClientMessagePlayerCrawl, IPayloadContext) void
}
class ClientMessagePlayerDrawGun {
  + ClientMessagePlayerDrawGun() 
  + decode(FriendlyByteBuf) ClientMessagePlayerDrawGun
  + handle(ClientMessagePlayerDrawGun, IPayloadContext) void
  + encode(ClientMessagePlayerDrawGun, FriendlyByteBuf) void
}
class ClientMessagePlayerFireSelect {
  + ClientMessagePlayerFireSelect() 
  + encode(ClientMessagePlayerFireSelect, FriendlyByteBuf) void
  + handle(ClientMessagePlayerFireSelect, IPayloadContext) void
  + decode(FriendlyByteBuf) ClientMessagePlayerFireSelect
}
class ClientMessagePlayerMelee {
  + ClientMessagePlayerMelee() 
  + handle(ClientMessagePlayerMelee, IPayloadContext) void
  + encode(ClientMessagePlayerMelee, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ClientMessagePlayerMelee
}
class ClientMessagePlayerReloadGun {
  + ClientMessagePlayerReloadGun() 
  + encode(ClientMessagePlayerReloadGun, FriendlyByteBuf) void
  + handle(ClientMessagePlayerReloadGun, IPayloadContext) void
  + decode(FriendlyByteBuf) ClientMessagePlayerReloadGun
}
class ClientMessagePlayerShoot {
  + ClientMessagePlayerShoot(long) 
  + ClientMessagePlayerShoot() 
  + decode(FriendlyByteBuf) ClientMessagePlayerShoot
  + handle(ClientMessagePlayerShoot, IPayloadContext) void
  + encode(ClientMessagePlayerShoot, FriendlyByteBuf) void
}
class ClientMessagePlayerZoom {
  + ClientMessagePlayerZoom() 
  + decode(FriendlyByteBuf) ClientMessagePlayerZoom
  + encode(ClientMessagePlayerZoom, FriendlyByteBuf) void
  + handle(ClientMessagePlayerZoom, IPayloadContext) void
}
class ClientMessageRefitGun {
  + ClientMessageRefitGun(int, int, AttachmentType) 
  + encode(ClientMessageRefitGun, FriendlyByteBuf) void
  + handle(ClientMessageRefitGun, Supplier~Context~) void
  + decode(FriendlyByteBuf) ClientMessageRefitGun
}
class ClientMessageSyncBaseTimestamp {
  + ClientMessageSyncBaseTimestamp() 
  + decode(FriendlyByteBuf) ClientMessageSyncBaseTimestamp
  + handle(ClientMessageSyncBaseTimestamp, Supplier~Context~) void
  + encode(ClientMessageSyncBaseTimestamp, FriendlyByteBuf) void
}
class ClientMessageUnloadAttachment {
  + ClientMessageUnloadAttachment(int, AttachmentType) 
  + handle(ClientMessageUnloadAttachment, Supplier~Context~) void
  + encode(ClientMessageUnloadAttachment, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ClientMessageUnloadAttachment
}
class ClientPreventGunClick {
  + ClientPreventGunClick() 
  + onClickInput(InteractionKeyMappingTriggered) void
}
class ClientSetupEvent {
  + ClientSetupEvent() 
  + onRegisterGuiOverlays(RegisterGuiLayersEvent) void
  + onClientSetup(RegisterKeyMappingsEvent) void
  + onClientSetup(RegisterClientTooltipComponentFactoriesEvent) void
  + onClientResourceReload(RegisterClientReloadListenersEvent) void
  + onClientSetup(FMLClientSetupEvent) void
}
class ClothConfigScreen {
  # ClothConfigScreen(Screen) 
  + registerNoClothConfigPage() void
  # init() void
  - openUrl(String) void
  + render(GuiGraphics, int, int, float) void
}
class ColorHex {
  + ColorHex() 
  + colorTextToRbgFloatArray(String) float[]
  + colorTextToRbgInt(String) int
}
class CommandRegistry {
  + CommandRegistry() 
  + onServerStaring(RegisterCommandsEvent) void
}
class CommonAmmoIndex {
  - CommonAmmoIndex() 
  - AmmoIndexPOJO pojo
  - int stackSize
  + getInstance(AmmoIndexPOJO) CommonAmmoIndex
  - checkIndex(AmmoIndexPOJO, CommonAmmoIndex) void
   AmmoIndexPOJO pojo
   int stackSize
}
class CommonAmmoIndexSerializer {
  + CommonAmmoIndexSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) CommonAmmoIndex
}
class CommonAssetsManager {
  + CommonAssetsManager() 
  + getScript(ResourceLocation) LuaTable?
  + getAttachmentData(ResourceLocation) AttachmentData?
  + reloadAndRegister(Consumer~PreparableReloadListener~) void
  - register(T) T
  + getAllowAttachmentTags(ResourceLocation) Set~String~
  + OnDatapackSync(OnDatapackSyncEvent) void
  + getBlockIndex(ResourceLocation) CommonBlockIndex?
  + onReload(TagsUpdatedEvent) void
  + getGunIndex(ResourceLocation) CommonGunIndex?
  + getBlockData(ResourceLocation) BlockData?
  + getAmmoIndex(ResourceLocation) CommonAmmoIndex?
  + getGunData(ResourceLocation) GunData?
  + onReload(AddReloadListenerEvent) void
  + get() ICommonResourceProvider
  + reloadAllPack() void
  + getRecipeFilter(ResourceLocation) RecipeFilter?
  + getAttachmentTags(ResourceLocation) Set~String~
  + onServerStopped(ServerStoppedEvent) void
  + getAttachmentIndex(ResourceLocation) CommonAttachmentIndex?
   Set~Entry~ResourceLocation, CommonGunIndex~~ allGuns
   Set~Entry~ResourceLocation, CommonAmmoIndex~~ allAmmos
   CommonAssetsManager? instance
   Set~Entry~ResourceLocation, CommonAttachmentIndex~~ allAttachments
   Set~Entry~ResourceLocation, CommonBlockIndex~~ allBlocks
   Map~DataType, Map~ResourceLocation, String~~ networkCache
}
class CommonAttachmentIndex {
  - CommonAttachmentIndex() 
  - AttachmentIndexPOJO pojo
  - int sort
  - AttachmentType type
  - AttachmentData data
  - checkIndex(AttachmentIndexPOJO, CommonAttachmentIndex) void
  - checkData(AttachmentIndexPOJO, CommonAttachmentIndex) void
  + getInstance(AttachmentIndexPOJO) CommonAttachmentIndex
   AttachmentIndexPOJO pojo
   int sort
   AttachmentType type
   AttachmentData data
}
class CommonAttachmentIndexSerializer {
  + CommonAttachmentIndexSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) Object
}
class CommonBlockIndex {
  + CommonBlockIndex() 
  - BlockData data
  - BlockItem block
  - RecipeFilter filter
  - BlockIndexPOJO pojo
  - checkData(BlockIndexPOJO, CommonBlockIndex) void
  + getInstance(BlockIndexPOJO) CommonBlockIndex
  - checkIndex(BlockIndexPOJO, CommonBlockIndex) void
   BlockItem block
   BlockIndexPOJO pojo
   RecipeFilter filter
   BlockData data
}
class CommonBlockIndexSerializer {
  + CommonBlockIndexSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) CommonBlockIndex
}
class CommonConfig {
  + CommonConfig() 
  + init() ModConfigSpec
}
class CommonDataManager~T~ {
  + CommonDataManager(DataType, Class~T~, Gson, String, String) 
  + CommonDataManager(DataType, Class~T~, Gson, FileToIdConverter, String) 
  - DataType type
  # Map~ResourceLocation, String~ networkCache
  # apply(Map~ResourceLocation, JsonElement~, ResourceManager, ProfilerFiller) void
  + clear() void
   DataType type
   Map~ResourceLocation, String~ networkCache
}
class CommonGunIndex {
  - CommonGunIndex() 
  - int sort
  - LuaTable scriptParam
  - GunData gunData
  - String type
  - GunIndexPOJO pojo
  - LuaTable script
  - checkInaccuracy(GunData) void
  + getInstance(GunIndexPOJO) CommonGunIndex
  - checkRecoil(GunData) void
  - checkData(GunIndexPOJO, CommonGunIndex) void
  - checkScript(GunData, CommonGunIndex) void
  - checkIndex(GunIndexPOJO, CommonGunIndex) void
   BulletData bulletData
   int sort
   LuaTable script
   String type
   GunData gunData
   LuaTable scriptParam
   GunIndexPOJO pojo
}
class CommonGunIndexSerializer {
  + CommonGunIndexSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) CommonGunIndex
}
class CommonLoadPack {
  + CommonLoadPack() 
  + loadGunPack(FMLCommonSetupEvent) void
}
class CommonNetworkCache {
<<enumeration>>
  + CommonNetworkCache() 
  - parse(String, Class~T~) T
  + getAmmoIndex(ResourceLocation) CommonAmmoIndex?
  + getAttachmentTags(ResourceLocation) Set~String~
  - resolveAttachmentTags(Map~ResourceLocation, String~) void
  + getBlockData(ResourceLocation) BlockData?
  - parseAttachmentData(String) AttachmentData
  - fromNetwork(DataType, Map~ResourceLocation, String~) void
  + getAttachmentIndex(ResourceLocation) CommonAttachmentIndex?
  + getAttachmentData(ResourceLocation) AttachmentData?
  + getScript(ResourceLocation) LuaTable?
  + getBlockIndex(ResourceLocation) CommonBlockIndex?
  + values() CommonNetworkCache[]
  + fromNetwork(Map~DataType, Map~ResourceLocation, String~~) void
  + getGunIndex(ResourceLocation) CommonGunIndex?
  + valueOf(String) CommonNetworkCache
  + getRecipeFilter(ResourceLocation) RecipeFilter?
  + getGunData(ResourceLocation) GunData?
  + getAllowAttachmentTags(ResourceLocation) Set~String~
   Set~Entry~ResourceLocation, CommonGunIndex~~ allGuns
   Set~Entry~ResourceLocation, CommonAmmoIndex~~ allAmmos
   Set~Entry~ResourceLocation, CommonAttachmentIndex~~ allAttachments
   Set~Entry~ResourceLocation, CommonBlockIndex~~ allBlocks
}
class CommonRegistry {
  + CommonRegistry() 
  - boolean LOAD_COMPLETE
  + registerAttributes(EntityAttributeModificationEvent) void
  + onAddPackFinders(AddPackFindersEvent) void
  + onSetupEvent(FMLCommonSetupEvent) void
  + onLoadComplete(FMLLoadCompleteEvent) void
   boolean LOAD_COMPLETE
}
class CommonTransformObject {
  + CommonTransformObject(Vector3f?, Vector3f?, Vector3f?) 
  + CommonTransformObject() 
  - Vector3f? translation
  - Vector3f? rotation
  - Vector3f? scale
  + lerp(CommonTransformObject, float) CommonTransformObject
  + equals(Object) boolean
   Vector3f translation
   Vector3f rotation
   Vector3f scale
}
class CompatRegistry {
  + CompatRegistry() 
  + onEnqueue(InterModEnqueueEvent) void
  + checkModLoad(String, Runnable) void
}
class ConfigCommand {
  + ConfigCommand() 
  - suggestConfigKeys(CommandContext~CommandSourceStack~, SuggestionsBuilder) CompletableFuture~Suggestions~
  - setConfig(CommandContext~CommandSourceStack~) int
  + get() LiteralArgumentBuilder~CommandSourceStack~
}
class ConfigKey {
  + ConfigKey() 
  + onOpenConfig(Key) void
}
class ConfigKey {
<<enumeration>>
  - ConfigKey(String) 
  + fromString(String) ConfigKey?
  + values() ConfigKey[]
  + valueOf(String) ConfigKey
   String serializedName
}
class ConstraintObject {
  + ConstraintObject() 
  + supplyListeners(String, ChannelType) AnimationListener?
}
class ConstraintRotateListener {
  + ConstraintRotateListener(ConstraintObject) 
  + initialValue() float[]
  + update(float[], boolean) void
   ChannelType type
}
class ConstraintTranslateListener {
  + ConstraintTranslateListener(ConstraintObject) 
  + update(float[], boolean) void
  + initialValue() float[]
   ChannelType type
}
class ControllableCompat {
  + ControllableCompat() 
  + init() void
}
class ControllableData {
  + ControllableData() 
  - float highFrequency
  - float lowFrequency
  - int timeInMs
   float highFrequency
   int timeInMs
   float lowFrequency
}
class ControllableInner {
  + ControllableInner() 
  - doRumble(Controller) void
  + onButtonInput(Controller, Value~Integer~, int, boolean) boolean
  + init() void
  + onClientTickEnd() void
}
class ConvertCommand {
  + ConvertCommand() 
  - convert(CommandContext~CommandSourceStack~) int
  + get() LiteralArgumentBuilder~CommandSourceStack~
}
class CrawlKey {
  + CrawlKey() 
  + onCrawlPress(Key) void
  + onCrawlControllerPress(boolean) boolean
}
class CrosshairDropdown {
  + CrosshairDropdown() 
  + of() SelectionCellCreator~CrosshairType~
  + of(CrosshairType) SelectionTopCellElement~CrosshairType~
}
class CrosshairType {
<<enumeration>>
  + CrosshairType() 
  + valueOf(String) CrosshairType
  + getTextureLocation(CrosshairType) ResourceLocation
  + values() CrosshairType[]
}
class CubesItem {
  + CubesItem() 
  - List~Float~ uv
  - FaceUVsItem faceUv
  - boolean mirror
  - List~Float~ origin
  - List~Float~ size
  - boolean hasMirror
  - List~Float~ pivot
  - float inflate
  - List~Float~ rotation
   List~Float~ origin
   FaceUVsItem? faceUv
   boolean hasMirror
   List~Float~ size
   List~Float~ uv
   float inflate
   boolean mirror
   List~Float~? rotation
   List~Float~? pivot
}
class CustomGunItemBuilder {
  + CustomGunItemBuilder(ResourceLocation) 
  + String typeName
  + createObject() Item
   String typeName
}
class CustomInterpolator {
  + CustomInterpolator() 
  - getAsThreeAxis(int, boolean) float[]
  - exp(Quaternionf) Quaternionf
  - reverse(Quaternionf) Quaternionf
  - intermediate(Quaternionf, Quaternionf, Quaternionf) float[]
  - doOtherLerp(int, int, float) float[]
  + clone() CustomInterpolator
  - doSphericalLinear(int, int, float) float[]
  + compile(AnimationChannelContent) void
  - doCatmullromLerp(int, int, float) float[]
  + interpolate(int, int, float) float[]
  - getValue(int, boolean) float[]
  - getAsQuaternion(int, boolean) float[]
  - doSphericalSquad(int, int, float) float[]
  - log(Quaternionf) Quaternionf
  + squad(float[], float[], float[], float[], float) float[]
}
class CycleTaskHelper {
  + CycleTaskHelper() 
  + addCycleTask(BooleanSupplier, long, long, int) void
  + tick() void
  + addCycleTask(BooleanSupplier, long, int) void
}
class CycleTaskTicker {
  - CycleTaskTicker(BooleanSupplier, long, long, int) 
  - CycleTaskTicker(BooleanSupplier, long, int) 
  - tick() boolean
}
class DamageJsonProperty {
  + DamageJsonProperty(Modifier) 
  + initComponents() void
}
class DamageModifier {
  + DamageModifier() 
  + String ID
  + readJson(String) JsonProperty~Modifier~
  + initCache(ItemStack, GunData) CacheValue~LinkedList~DistanceDamagePair~~
  + eval(List~Modifier~, CacheValue~LinkedList~DistanceDamagePair~~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
   String ID
   int diagramsDataSize
}
class DamageStyle {
<<enumeration>>
  + DamageStyle() 
  + values() DamageStyle[]
  + valueOf(String) DamageStyle
}
class Data {
  + Data() 
  - Modifier? inaccuracy
  - Modifier? aimInaccuracy
  - Modifier? sneakInaccuracy
  - Modifier? lieInaccuracy
   Modifier? lieInaccuracy
   float inaccuracyAddendTime
   Modifier? aimInaccuracy
   Modifier? inaccuracy
   Modifier? sneakInaccuracy
}
class Data {
  + Data() 
  - Modifier? pierce
   Modifier? pierce
}
class Data {
  + Data(long, Vector3f) 
}
class Data {
  + Data() 
  - Modifier? effectiveRange
   Modifier? effectiveRange
}
class Data {
  + Data() 
  - MoveSpeed? moveSpeed
   MoveSpeed? moveSpeed
}
class Data {
  + Data() 
  - Modifier? knockback
   Modifier? knockback
}
class Data {
  + Data() 
  - ExplosionModifierValue? explosion
   ExplosionModifierValue? explosion
}
class Data {
  + Data() 
  - Modifier? aimInaccuracy
   Modifier? aimInaccuracy
}
class Data {
  + Data() 
  - Silence? silence
   Silence? silence
}
class Data {
  + Data() 
  - Modifier? rpm
   Modifier? rpm
}
class Data {
  + Data() 
  - Modifier? headShot
   Modifier? headShot
}
class Data {
  + Data() 
  - Modifier? damage
   Modifier? damage
}
class Data {
  + Data() 
  - float adsAddendTime
  - Modifier? ads
   float adsAddendTime
   Modifier? ads
}
class Data {
  + Data() 
  - Modifier? armorIgnore
   Modifier? armorIgnore
}
class Data {
  + Data() 
}
class Data {
  + Data() 
  - float weightAddend
  - Modifier? weightModifier
   float weightAddend
   Modifier? weightModifier
}
class Data {
  + Data() 
  - Modifier? ammoSpeed
   Modifier? ammoSpeed
}
class Data {
  + Data() 
  - Ignite ignite
   Ignite? ignite
}
class DataEntry~E, T~ {
  + DataEntry(SyncedDataKey~E, T~) 
  - SyncedDataKey~E, T~ key
  - T value
  - boolean dirty
  + read(FriendlyByteBuf) DataEntry~?, ?~
  + readValue(FriendlyByteBuf) void
  + write(FriendlyByteBuf) void
  + readValue(Tag) void
  + clean() void
  + writeValue() Tag
  + setValue(T, boolean) void
   boolean dirty
   T value
   SyncedDataKey~E, T~ key
}
class DataHolder {
  + DataHolder() 
  - boolean dirty
  + get(SyncedDataKey~E, T~) T?
  + gatherAll() List~DataEntry~?, ?~~
  + clean() void
  + set(E, SyncedDataKey~?, ?~, T) boolean
  + gatherDirty() List~DataEntry~?, ?~~
   boolean dirty
}
class DataType {
<<enumeration>>
  + DataType() 
  + values() DataType[]
  + valueOf(String) DataType
}
class DebugCommand {
  + DebugCommand() 
  + get() LiteralArgumentBuilder~CommandSourceStack~
  - setValue(CommandContext~CommandSourceStack~) int
}
class DefaultAnimationType {
<<enumeration>>
  + DefaultAnimationType() 
  + valueOf(String) DefaultAnimationType
  + values() DefaultAnimationType[]
}
class DefaultAssets {
  + DefaultAssets() 
  + isEmptyAttachmentId(ResourceLocation) boolean
}
class DefaultTableItem {
  + DefaultTableItem(Block) 
  + getBlockId(ItemStack) ResourceLocation
  + setBlockId(ItemStack, ResourceLocation?) void
}
class DelayedTask {
  - DelayedTask() 
  + add(Consumer~Integer~, int, int) void
  + add(Runnable, int) void
}
class Description {
  + Description() 
  - int textureHeight
  - float visibleBoundsHeight
  - int textureWidth
  - float visibleBoundsWidth
  - List~Float~ visibleBoundsOffset
   List~Float~ visibleBoundsOffset
   float visibleBoundsHeight
   int textureHeight
   float visibleBoundsWidth
   int textureWidth
}
class Deserializer {
  + Deserializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) CubesItem
}
class Deserializer {
  + Deserializer() 
  - loadFilters(JsonArray, List~IFilter~ResourceLocation~~) void
  + serialize(RecipeFilter, Type, JsonSerializationContext) JsonElement
  - toList(List~IFilter~ResourceLocation~~) List~String~
  + deserialize(JsonElement, Type, JsonDeserializationContext) RecipeFilter
}
class Deserializer {
  + Deserializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) TabConfig
}
class DestroyGlassBlock {
  + DestroyGlassBlock() 
  + onAmmoHitBlock(AmmoHitBlockEvent) void
}
class DiagramsData {
  + DiagramsData(double, double, Number, String, String, String, String, boolean) 
  + negativeString() String
  + titleKey() String
  + modifierPercent() double
  + positivelyString() String
  + modifier() Number
  + defaultString() String
  + positivelyBetter() boolean
  + defaultPercent() double
}
class DiscreteTrackArray {
  + DiscreteTrackArray() 
  + ensureTrackAmount(int, int) void
  + addTrackLine() int
  + iterator() Iterator~Integer~
  + getByIndex(int) List~Integer~
  + ensureCapacity(int) void
  + assignNewTrack(int) int
   int trackLineSize
}
class DisplayManager~T~ {
  + DisplayManager(Class~T~, Gson, String, String) 
  + DisplayManager(Class~T~, Gson, FileToIdConverter, String) 
  # apply(Map~ResourceLocation, JsonElement~, ResourceManager, ProfilerFiller) void
}
class DistanceDamagePair {
  + DistanceDamagePair(float, float) 
  - float distance
  - float damage
   float distance
   float damage
}
class DistanceDamagePairSerializer {
  + DistanceDamagePairSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) DistanceDamagePair
}
class DummyAmmoCommand {
  + DummyAmmoCommand() 
  - setDummy(CommandContext~CommandSourceStack~) int
  + get() LiteralArgumentBuilder~CommandSourceStack~
}
class Easing {
  + Easing() 
  + easeOutCubic(double) double
}
class EffectData {
  + EffectData() 
  - int time
  - boolean hideParticles
  - int amplifier
  - ResourceLocation? effectId
   int amplifier
   int time
   boolean hideParticles
   ResourceLocation? effectId
}
class EffectiveRangeJsonProperty {
  + EffectiveRangeJsonProperty(Modifier) 
  + initComponents() void
}
class EffectiveRangeModifier {
  + EffectiveRangeModifier() 
  + String ID
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~Float~
  + readJson(String) JsonProperty~Modifier~
  + eval(List~Modifier~, CacheValue~Float~) void
   String ID
   int diagramsDataSize
}
class ElementType {
<<enumeration>>
  - ElementType(int) 
  - int numComponents
  + values() ElementType[]
  + contains(String) boolean
  + forString(String) ElementType
  + valueOf(String) ElementType
   int numComponents
}
class EntityBulletRenderer {
  + EntityBulletRenderer(Context) 
  + render(EntityKineticBullet, float, float, PoseStack, MultiBufferSource, int) void
  + shouldRender(EntityKineticBullet, Frustum, double, double, double) boolean
  + renderTracerAmmo(EntityKineticBullet, float[], float, PoseStack, int) void
  # getBlockLightLevel(EntityKineticBullet, BlockPos) int
  + getTextureLocation(EntityKineticBullet) ResourceLocation
   Optional~BedrockModel~ model
}
class EntityDamageEvent {
  + EntityDamageEvent() 
  + onLivingHurt(LivingDamageEvent) void
}
class EntityHurtByGunEvent {
  # EntityHurtByGunEvent(Entity, Entity?, LivingEntity?, ResourceLocation, ResourceLocation, float, Pair~DamageSource, DamageSource~?, boolean, float, LogicalSide) 
  # Entity bullet
  # boolean isHeadShot
  # LogicalSide logicalSide
  # Entity? hurtEntity
  # LivingEntity? attacker
  # ResourceLocation gunId
  # ResourceLocation gunDisplayId
  # float headshotMultiplier
  # float baseAmount
  + getDamageSource(GunDamageSourcePart) DamageSource
   Entity bullet
   boolean isHeadShot
   float headshotMultiplier
   LogicalSide logicalSide
   ResourceLocation gunId
   Entity? hurtEntity
   ResourceLocation gunDisplayId
   LivingEntity? attacker
   float amount
   float baseAmount
}
class EntityHurtByGunPostEventJS {
  + EntityHurtByGunPostEventJS(Post) 
   ResourceLocation? eventSubId
   ScriptTypeHolder? typeHolder
}
class EntityHurtByGunPreEventJS {
  + EntityHurtByGunPreEventJS(Pre) 
   ResourceLocation? eventSubId
   ScriptTypeHolder? typeHolder
}
class EntityHurtByGunPreWrapper {
<<Interface>>
  + setDamageSource(GunDamageSourcePart, DamageSource) void
   float headshotMultiplier
   ResourceLocation gunId
   Entity hurtEntity
   boolean headshot
   LivingEntity attacker
   float baseAmount
}
class EntityHurtByGunWrapper~E~ {
<<Interface>>
  + getDamageSource(GunDamageSourcePart) DamageSource
   Entity bullet
   float headshotMultiplier
   LogicalSide logicalSide
   ResourceLocation gunId
   Entity? hurtEntity
   LivingEntity? attacker
   boolean headShot
   float baseAmount
}
class EntityKillByGunEvent {
  + EntityKillByGunEvent(Entity, LivingEntity?, LivingEntity?, ResourceLocation, ResourceLocation, float, Pair~DamageSource, DamageSource~?, boolean, float, LogicalSide) 
  - LivingEntity? killedEntity
  - LogicalSide logicalSide
  - ResourceLocation gunDisplayId
  - LivingEntity? attacker
  - ResourceLocation gunId
  - Entity bullet
  - float baseDamage
  - float headshotMultiplier
  - boolean isHeadShot
  + getDamageSource(GunDamageSourcePart) DamageSource
   Entity bullet
   boolean isHeadShot
   float headshotMultiplier
   LogicalSide logicalSide
   ResourceLocation gunId
   float baseDamage
   ResourceLocation gunDisplayId
   LivingEntity? attacker
   LivingEntity? killedEntity
}
class EntityKillByGunEventJS {
  + EntityKillByGunEventJS(EntityKillByGunEvent) 
   ResourceLocation? eventSubId
   ScriptTypeHolder? typeHolder
}
class EntityKillByGunWrapper {
<<Interface>>
  + getDamageSource(GunDamageSourcePart) DamageSource
   Entity bullet
   float headshotMultiplier
   LogicalSide logicalSide
   ResourceLocation gunId
   float baseDamage
   ResourceLocation gunDisplayId
   LivingEntity? attacker
   boolean headShot
   LivingEntity? killedEntity
}
class EntityKineticBullet {
  + EntityKineticBullet(Level, LivingEntity, ItemStack, ResourceLocation, ResourceLocation, boolean, GunData, BulletData) 
  + EntityKineticBullet(EntityType~Projectile~, double, double, double, Level) 
  + EntityKineticBullet(Level, LivingEntity, ItemStack, ResourceLocation, ResourceLocation, ResourceLocation, boolean, GunData, BulletData) 
  # EntityKineticBullet(EntityType~Projectile~, Level, LivingEntity, ItemStack, ResourceLocation, ResourceLocation, ResourceLocation, boolean, GunData, BulletData) 
  + EntityKineticBullet(EntityType~Projectile~, Level) 
  - ResourceLocation gunId
  - Vector3f firstPersonRenderOffset
  - float cameraYRot
  - ResourceLocation gunDisplayId
  - boolean isTracerAmmo
  - float cameraXRot
  - ResourceLocation ammoId
  + ownedBy(Entity?) boolean
  + tick() void
  - createDamageSources(MaybeMultipartEntity) Pair~DamageSource, DamageSource~
  # defineSynchedData() void
  + shootFromRotation(Entity, float, float, float, float, Vector2d) void
  - tacAttackEntity(MaybeMultipartEntity, float, Pair~DamageSource, DamageSource~) void
  + writeSpawnData(FriendlyByteBuf) void
  + readSpawnData(FriendlyByteBuf) void
  # onHitEntity(TacHitResult, Vec3, Vec3) void
  # onHitBlock(BlockHitResult, Vec3, Vec3) void
  + getDamage(Vec3) float
  # onBulletTick() void
  + shoot(double, double, float, Vector2d) void
   Optional~float[]~ tracerColorOverride
   ResourceLocation gunId
   ResourceLocation gunDisplayId
   Vector3f firstPersonRenderOffset
   Packet~ClientGamePacketListener~ addEntityPacket
   float cameraYRot
   RandomSource random
   float tracerSizeOverride
   float cameraXRot
   boolean isTracerAmmo
   ResourceLocation ammoId
}
class EntityResult {
  + EntityResult(Entity, Vec3, boolean) 
  - boolean headshot
  - Entity entity
   Vec3 hitPos
   boolean headshot
   Entity entity
}
class EntityUtil {
  + EntityUtil() 
  # getHitResult(Projectile, Entity, Vec3, Vec3) EntityResult?
  + findEntityOnPath(Projectile, Vec3, Vec3) EntityResult?
  + findEntitiesOnPath(Projectile, Vec3, Vec3) List~EntityResult~
}
class Entry {
  + Entry(AbstractWidget) 
  + narratables() List~NarratableEntry~
  + children() List~GuiEventListener~
  + render(GuiGraphics, int, int, int, int, int, int, int, boolean, float) void
}
class ExplodeUtil {
  + ExplodeUtil() 
  + createExplosion(Entity, Entity, float, float, boolean, boolean, Vec3) void
}
class ExplosionData {
  + ExplosionData(boolean, float, float, boolean, float, boolean) 
  - float damage
  - boolean explode
  - float radius
  - boolean destroyBlock
  - boolean knockback
  - float delay
   boolean knockback
   boolean destroyBlock
   boolean explode
   float radius
   float damage
   float delay
}
class ExplosionJsonProperty {
  + ExplosionJsonProperty(ExplosionModifierValue) 
  + initComponents() void
}
class ExplosionModifier {
  + ExplosionModifier() 
  + String ID
  + eval(List~ExplosionModifierValue~, CacheValue~ExplosionData~) void
  + initCache(ItemStack, GunData) CacheValue~ExplosionData~
  + readJson(String) JsonProperty~ExplosionModifierValue~
   String ID
}
class ExplosionModifierValue {
  + ExplosionModifierValue() 
  - boolean explode
   boolean explode
}
class ExtraDamage {
  + ExtraDamage() 
  - LinkedList~DistanceDamagePair~ damageAdjust
  - float armorIgnore
  - float headShotMultiplier
   LinkedList~DistanceDamagePair~? damageAdjust
   float headShotMultiplier
   float armorIgnore
}
class ExtraEntry {
  + ExtraEntry(Class~?~, String, String) 
  + extraDirName() String
  + srcPath() String
  + modMainClass() Class~?~
}
class ExtraMovementModifier {
  + ExtraMovementModifier() 
  + String ID
  + initCache(ItemStack, GunData) CacheValue~MoveSpeed~
  + eval(List~MoveSpeed~, CacheValue~MoveSpeed~) void
  + readJson(String) JsonProperty~MoveSpeed~
   String ID
}
class ExtraSpeedJsonProperty {
  + ExtraSpeedJsonProperty(MoveSpeed) 
  + initComponents() void
  - resolveComponent(float, String) void
}
class FaceItem {
  + FaceItem() 
  - float[] uvSize
  - float[] uv
  + single16X() FaceItem
  - empty() FaceItem
   float[] uvSize
   float[] uv
}
class FaceUVsItem {
  + FaceUVsItem() 
  + singleSouthFace() FaceUVsItem
  + getFace(Direction) FaceItem
}
class FeedType {
<<enumeration>>
  + FeedType() 
  + valueOf(String) FeedType
  + values() FeedType[]
}
class FireMode {
<<enumeration>>
  + FireMode() 
  + values() FireMode[]
  + valueOf(String) FireMode
   String serializedName
}
class FireSelectKey {
  + FireSelectKey() 
  + onFireSelectControllerPress(boolean) boolean
  + onFireSelectKeyPress(Key) void
  - doFireSelectLogic() void
  + onFireSelectMousePress(Post) void
}
class FireSound {
  + FireSound() 
  - float fireMultiplier
  - float silenceMultiplier
   float fireMultiplier
   float silenceMultiplier
}
class FirstPersonRenderEvent {
  + FirstPersonRenderEvent() 
  + onRenderHand(RenderHandEvent) void
}
class FirstPersonRenderGunEvent {
  + FirstPersonRenderGunEvent() 
  - applyGunMovements(BedrockGunModel, float, float) void
  + cancelItemInHandViewBobbing(BobView) void
  - getPositioningNodeInverse(List~BedrockPart~) Matrix4f
  + applyAnimationConstraintTransform(PoseStack, BedrockGunModel, float) void
  - applyJumpingSway(BedrockGunModel, float) void
  - getAnimationConstraintTransform(List~BedrockPart~, Vector3f, Vector3f, Vector3f) void
  - bulletFromPlayer(Entity) boolean
  + applyFirstPersonGunTransform(LocalPlayer, ItemStack, PoseStack, BedrockGunModel, float) void
  - applyFirstPersonPositioningTransform(PoseStack, BedrockGunModel, ItemStack, float, float) void
  + onGunFire(GunFireEvent) void
  - applyShootSwayAndRotation(BedrockGunModel, float) void
}
class FlatColorButton {
  + FlatColorButton(int, int, int, int, Component, OnPress) 
  - boolean isSelect
  - List~Component~ tooltips
  + setTooltips(String) FlatColorButton
  + renderWidget(GuiGraphics, int, int, float) void
  + setTooltips(List~Component~) FlatColorButton
  + renderToolTip(GuiGraphics, Screen, int, int) void
   Component[] tooltips
   boolean isSelect
}
class ForgeEventWrapper~E~ {
<<Interface>>
   E forgeEvent
}
class FunctionalBedrockPart {
  + FunctionalBedrockPart(Function~BedrockPart, IFunctionalRenderer~?, String) 
  + FunctionalBedrockPart(Function~BedrockPart, IFunctionalRenderer~?, BedrockPart) 
  + render(PoseStack, ItemDisplayContext, VertexConsumer, int, int, float, float, float, float) void
}
class GameRendererMixin {
  + GameRendererMixin() 
  + render(float, long, boolean) void
  + onBobHurt(PoseStack, float, CallbackInfo) void
  + switchRenderType(Camera, float, boolean, CallbackInfoReturnable~Double~) void
  + onBobView(PoseStack, float, CallbackInfo) void
   Minecraft minecraft
}
class GeometryModelLegacy {
  + GeometryModelLegacy() 
  - int textureWidth
  - int textureHeight
  - List~Float~ visibleBoundsOffset
  - float visibleBoundsWidth
  - List~BonesItem~? bones
  - float visibleBoundsHeight
  + deco() GeometryModelLegacy
   List~BonesItem~? bones
   List~Float~ visibleBoundsOffset
   float visibleBoundsHeight
   int textureHeight
   float visibleBoundsWidth
   int textureWidth
}
class GeometryModelNew {
  + GeometryModelNew() 
  - List~BonesItem~? bones
  - Description description
  + deco() GeometryModelNew
   List~BonesItem~? bones
   Description description
}
class GetJarResources {
  - GetJarResources() 
  + copyModDirectory(Class~?~, String, Path, String) void
  + copyModDirectory(String, Path, String) void
  - backupFiles(Path) void
  + copyModFile(String, Path, String) void
  - copyFolder(URI, Path) void
  - checkOldBackups(Path) Set~String~
  - deleteFiles(Path) void
  + readModFile(String) InputStream?
}
class GltfConstants {
  - GltfConstants() 
  + stringFor(int) String
}
class GltfManager {
  + GltfManager() 
  # prepare(ResourceManager, ProfilerFiller) Map~ResourceLocation, AnimationStructure~
  # apply(Map~ResourceLocation, AnimationStructure~, ResourceManager, ProfilerFiller) void
  + getGltfAnimation(ResourceLocation) AnimationStructure
}
class GunAmmo {
  + GunAmmo() 
  - String? tracerColor
  - AmmoParticle? particle
   AmmoParticle? particle
   String? tracerColor
}
class GunAnimationConstant {
  + GunAnimationConstant() 
}
class GunAnimationStateContext {
  + GunAnimationStateContext() 
  - ItemStack currentGunItem
  - processGunOperator(Function~IClientPlayerGunOperator, T~) Optional~T~
  + shouldSlide() boolean
  + getAttachment(String) String
  - processCameraEntity(Function~Entity, T~) Optional~T~
  - processGunData(BiFunction~IGun, GunDisplayInstance, T~) Optional~T~
  + adjustClientShootInterval(long) void
  + anchorWalkDist() void
  + hasBulletInBarrel() boolean
  - processRemoteGunOperator(Function~IGunOperator, T~) Optional~T~
  + popShellFrom(int) void
  + hasAmmoToConsume() boolean
   int reloadStateType
   int fireMode
   boolean inputDown
   boolean aiming
   float aimingProgress
   int ammoCount
   long currentTimestamp
   boolean inputUp
   int magExtentLevel
   long lastShootTimestamp
   boolean crouching
   long shootCoolDown
   LuaNbtAccessor nbtAccessor
   ItemStack currentGunItem
   int maxAmmoCount
   boolean overHeat
   LuaTable stateMachineParams
   boolean inputRight
   float heatProgress
   boolean inputJumping
   boolean onGround
   boolean crawl
   boolean inputLeft
   long shootInterval
   float walkDist
}
class GunAttachmentSlot {
  + GunAttachmentSlot(int, int, AttachmentType, int, Inventory, OnPress) 
  - boolean selected
  - AttachmentType type
  - ItemStack attachmentItem
  + renderTooltip(Consumer~ItemStack~) void
  + renderWidget(GuiGraphics, int, int, float) void
   ItemStack attachmentItem
   AttachmentType type
   boolean allow
   boolean selected
}
class GunClothConfig {
  + GunClothConfig() 
  + init(ConfigBuilder, ConfigEntryBuilder) void
}
class GunConfig {
  + GunConfig() 
  + init(Builder) void
}
class GunDamageSourcePart {
<<enumeration>>
  + GunDamageSourcePart() 
  + values() GunDamageSourcePart[]
  + valueOf(String) GunDamageSourcePart
}
class GunData {
  + GunData() 
  - int[]? extendedMagAmmoAmount
  - float boltActionTime
  - BulletData bulletData
  - float sprintTime
  - ResourceLocation ammoId
  - int ammoAmount
  - GunReloadData reloadData
  - float boltFeedTime
  - GunRecoil recoil
  - Map~ResourceLocation, AttachmentData~ exclusiveAttachments
  - float aimTime
  - List~AttachmentType~ allowAttachments
  - Bolt bolt
  - boolean canCrawl
  - Map~AttachmentType, ResourceLocation~ builtInAttachments
  - Map~InaccuracyType, Float~ inaccuracy
  - float putAwayTime
  - int roundsPerMinute
  - float weight
  - ResourceLocation script
  - float drawTime
  - BurstData burstData
  - FireSound fireSound
  - MoveSpeed moveSpeed
  - float crawlRecoilMultiplier
  - List~FireMode~ fireModeSet
  - float hurtBobTweakMultiplier
  - Map~String, Object~ scriptParam
  + getInaccuracy(InaccuracyType) float
  + getRoundsPerMinute(FireMode) int
  + getInaccuracy(InaccuracyType, float) float
  + hasHeatData() boolean
  + getShootInterval(LivingEntity, FireMode, ItemStack) long
  + canSlide() boolean
  + getFireModeAdjustData(FireMode) GunFireModeAdjustData?
   float putAwayTime
   Map~ResourceLocation, AttachmentData~ exclusiveAttachments
   BulletData bulletData
   GunRecoil recoil
   int ammoAmount
   List~AttachmentType~? allowAttachments
   float crawlRecoilMultiplier
   Map~AttachmentType, ResourceLocation~ builtInAttachments
   GunReloadData reloadData
   GunMeleeData meleeData
   Map~String, Object~? scriptParam
   float drawTime
   GunHeatData? heatData
   float boltFeedTime
   float boltActionTime
   FireSound fireSound
   Map~InaccuracyType, Float~ inaccuracy
   ResourceLocation ammoId
   List~FireMode~ fireModeSet
   float aimTime
   MoveSpeed moveSpeed
   float hurtBobTweakMultiplier
   boolean canCrawl
   float sprintTime
   BurstData burstData
   long burstShootInterval
   ResourceLocation? script
   int roundsPerMinute
   float weight
   Bolt bolt
   int[]? extendedMagAmmoAmount
}
class GunDefaultMeleeData {
  + GunDefaultMeleeData() 
  - float distance
  - float damage
  - float knockback
  - float rangeAngle
  - float cooldown
  - float prepTime
  - String animationType
   String animationType
   float distance
   float prepTime
   float rangeAngle
   float cooldown
   float damage
   float knockback
}
class GunDisplay {
  + GunDisplay() 
  - GunAmmo? gunAmmo
  - Map~String, Object~? stateMachineParam
  - DamageStyle damageStyle
  - DefaultAnimationType? defaultAnimationType
  - AmmoCountStyle ammoCountStyle
  - ResourceLocation? hudEmptyTextureLocation
  - ResourceLocation modelTexture
  - LaserConfig laserConfig
  - float zoomModelFov
  - String modelType
  - ResourceLocation modelLocation
  - EnumMap~FireMode, ControllableData~ controllableData
  - GunLod? gunLod
  - Map~String, ResourceLocation~? sounds
  - ShellEjection? shellEjection
  - float ironZoom
  - String? thirdPersonAnimation
  - ResourceLocation? playerAnimator3rd
  - MuzzleFlash? muzzleFlash
  - LayerGunShow offhandShow
  - ResourceLocation? slotTextureLocation
  - ResourceLocation? stateMachineLocation
  - Map~String, TextShow~ textShows
  - GunTransform? transform
  - boolean showCrosshair
  - ResourceLocation? animationLocation
  - ResourceLocation? hudTextureLocation
  - Map~String, LayerGunShow~? hotbarShow
  - ResourceLocation? defaultAnimation
  + init() void
   ResourceLocation modelLocation
   float ironZoom
   ResourceLocation? hudEmptyTextureLocation
   Map~String, ResourceLocation~? sounds
   ResourceLocation? hudTextureLocation
   GunLod? gunLod
   Map~String, TextShow~ textShows
   ResourceLocation? slotTextureLocation
   Map~String, Object~? stateMachineParam
   boolean 3rdFixedHand
   ResourceLocation? defaultAnimation
   ResourceLocation? playerAnimator3rd
   String modelType
   DamageStyle damageStyle
   GunTransform? transform
   ResourceLocation? stateMachineLocation
   GunAmmo? gunAmmo
   float zoomModelFov
   boolean showCrosshair
   LayerGunShow offhandShow
   DefaultAnimationType? defaultAnimationType
   ResourceLocation? animationLocation
   Map~String, LayerGunShow~? hotbarShow
   AmmoCountStyle ammoCountStyle
   EnumMap~FireMode, ControllableData~ controllableData
   ShellEjection? shellEjection
   ResourceLocation modelTexture
   MuzzleFlash? muzzleFlash
   String? thirdPersonAnimation
   LaserConfig? laserConfig
}
class GunDisplayInstance {
  ~ GunDisplayInstance(GunDisplay) 
  - boolean showCrosshair
  - ShellEjection? shellEjection
  - float zoomModelFov
  - LayerGunShow offhandShow
  - AmmoCountStyle ammoCountStyle
  - LaserConfig? laserConfig
  - Int2ObjectArrayMap~LayerGunShow~? hotbarShow
  - ResourceLocation? hudEmptyTexture
  - AmmoParticle? particle
  - MuzzleFlash? muzzleFlash
  - LuaAnimationStateMachine~GunAnimationStateContext~ animationStateMachine
  - float ironZoom
  - GunTransform transform
  - String thirdPersonAnimation
  - EnumMap~FireMode, ControllableData~ controllableData
  - LuaTable? stateMachineParam
  - Pair~BedrockGunModel, ResourceLocation~? lodModel
  - ResourceLocation slotTexture
  - ResourceLocation modelTexture
  - float[]? tracerColor
  - BedrockGunModel gunModel
  - ResourceLocation? playerAnimator3rd
  - DamageStyle damageStyle
  + create(GunDisplay) GunDisplayInstance
  - checkSlotTexture(GunDisplay) void
  - checkTextureAndModel(GunDisplay) void
  - checkTextShow(GunDisplay) void
  - checkShellEjection(GunDisplay) void
  - checkLayerGunShow(GunDisplay) void
  - checkLod(GunDisplay) void
  - checkGunAmmo(GunDisplay) void
  + getSounds(String) ResourceLocation?
  - checkIronZoom(GunDisplay) void
  - checkAnimation(GunDisplay) void
  - checkHUDTexture(GunDisplay) void
  - checkZoomModelFov(GunDisplay) void
  - checkSounds(GunDisplay) void
  - checkMuzzleFlash(GunDisplay) void
  - checkTransform(GunDisplay) void
   float ironZoom
   ResourceLocation? hudEmptyTexture
   boolean 3rdFixedHand
   float[]? tracerColor
   ResourceLocation? playerAnimator3rd
   Pair~BedrockGunModel, ResourceLocation~? lodModel
   DamageStyle damageStyle
   GunTransform transform
   AmmoParticle? particle
   float zoomModelFov
   boolean showCrosshair
   LayerGunShow offhandShow
   ResourceLocation slotTexture
   AmmoCountStyle ammoCountStyle
   LuaTable? stateMachineParam
   BedrockGunModel gunModel
   ResourceLocation HUDTexture
   EnumMap~FireMode, ControllableData~ controllableData
   ShellEjection? shellEjection
   ResourceLocation modelTexture
   MuzzleFlash? muzzleFlash
   String thirdPersonAnimation
   LaserConfig? laserConfig
   LuaAnimationStateMachine~GunAnimationStateContext~ animationStateMachine
   Int2ObjectArrayMap~LayerGunShow~? hotbarShow
}
class GunDrawEvent {
  + GunDrawEvent(LivingEntity, ItemStack, ItemStack, LogicalSide) 
  - ItemStack previousGunItem
  - LogicalSide logicalSide
  - LivingEntity entity
  - ItemStack currentGunItem
   LogicalSide logicalSide
   LivingEntity entity
   ItemStack currentGunItem
   ItemStack previousGunItem
}
class GunDrawEventJS {
  + GunDrawEventJS(GunDrawEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunDrawWrapper {
<<Interface>>
   LogicalSide logicalSide
   LivingEntity entity
   ItemStack currentGunItem
   ItemStack previousGunItem
}
class GunEventJS~E~ {
  + GunEventJS(E) 
  + cancel() Object
   E forgeEvent
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
   ResourceLocation? eventSubId
}
class GunFinishReloadEvent {
  + GunFinishReloadEvent(ItemStack, LogicalSide) 
  - ItemStack gunItemStack
  - LogicalSide logicalSide
   LogicalSide logicalSide
   ItemStack gunItemStack
   boolean cancelable
}
class GunFinishReloadEventJS {
  + GunFinishReloadEventJS(GunFinishReloadEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunFinishReloadWrapper {
<<Interface>>
   LogicalSide logicalSide
   ItemStack gunItemStack
}
class GunFireEvent {
  + GunFireEvent(LivingEntity, ItemStack, LogicalSide) 
  - LivingEntity shooter
  - LogicalSide logicalSide
  - ItemStack gunItemStack
   LogicalSide logicalSide
   ItemStack gunItemStack
   boolean cancelable
   LivingEntity shooter
}
class GunFireEventJS {
  + GunFireEventJS(GunFireEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunFireModeAdjustData {
  + GunFireModeAdjustData() 
  - float knockback
  - float aimInaccuracy
  - float speed
  - float otherInaccuracy
  - float armorIgnore
  - float headShotMultiplier
  - int roundsPerMinute
  - float damageAmount
   int roundsPerMinute
   float armorIgnore
   float otherInaccuracy
   float speed
   float headShotMultiplier
   float damageAmount
   float knockback
   float aimInaccuracy
}
class GunFireSelectEvent {
  + GunFireSelectEvent(LivingEntity, ItemStack, LogicalSide) 
  - LogicalSide logicalSide
  - LivingEntity shooter
  - ItemStack gunItemStack
   LogicalSide logicalSide
   ItemStack gunItemStack
   boolean cancelable
   LivingEntity shooter
}
class GunFireSelectEventJS {
  + GunFireSelectEventJS(GunFireSelectEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunFireSelectWrapper {
<<Interface>>
   LogicalSide logicalSide
   ItemStack gunItemStack
   LivingEntity shooter
}
class GunFireWrapper {
<<Interface>>
   LogicalSide logicalSide
   ItemStack gunItemStack
   LivingEntity shooter
}
class GunHeatData {
  + GunHeatData() 
  - float heatMax
  - float maxRpmMod
  - float maxInaccuracy
  - float minInaccuracy
  - float coolingMultiplier
  - long coolingDelay
  - float heatPerShot
  - float minRpmMod
  - long overHeatTime
   float heatMax
   float minInaccuracy
   long coolingDelay
   float maxRpmMod
   float coolingMultiplier
   float minRpmMod
   float heatPerShot
   float maxInaccuracy
   long overHeatTime
}
class GunHudOverlay {
  + GunHudOverlay() 
  + render(GuiGraphics, DeltaTracker) void
  - handleInventoryAmmo(ItemStack, Inventory) void
  - handleCacheCount(LocalPlayer, ItemStack, GunData, IGun, boolean) void
}
class GunHurtBobTweak {
  + GunHurtBobTweak() 
  + onHurtBobTweak(LocalPlayer, PoseStack, float) boolean
  + markTimestamp(float) void
}
class GunIndexPOJO {
  + GunIndexPOJO() 
  - String type
  - String itemType
  - ResourceLocation display
  - String name
  - ResourceLocation data
  - String? tooltip
  - int sort
   String name
   int sort
   String type
   ResourceLocation display
   String itemType
   String? tooltip
   ResourceLocation data
}
class GunItemBuilder {
  - GunItemBuilder() 
  - int count
  - int ammoCount
  - FireMode fireMode
  - boolean heatData
  + putAttachment(AttachmentType, ResourceLocation) GunItemBuilder
  + putAllAttachment(EnumMap~AttachmentType, ResourceLocation~) GunItemBuilder
  + forceBuild() ItemStack
  + build() ItemStack
  + create() GunItemBuilder
   boolean heatData
   boolean ammoInBarrel
   int ammoCount
   ResourceLocation id
   FireMode fireMode
   int count
}
class GunItemDataAccessor {
<<Interface>>
  + setAttachmentLock(ItemStack, boolean) void
  + setDummyAmmoAmount(ItemStack, int) void
  + getLevel(ItemStack) int
  + hasBulletInBarrel(ItemStack) boolean
  + getBuiltInAttachmentId(ItemStack, AttachmentType) ResourceLocation
  + lerpRPM(ItemStack) float
  + getDummyAmmoAmount(ItemStack) int
  + getMaxDummyAmmoAmount(ItemStack) int
  + getExpCurrentLevel(ItemStack) int
  + setFireMode(ItemStack, FireMode?) void
  + hasAttachmentLock(ItemStack) boolean
  + setOverheatLocked(ItemStack, boolean) void
  + getAttachment(ItemStack, AttachmentType) ItemStack
  + setHeatAmount(ItemStack, float) void
  + getExpToNextLevel(ItemStack) int
  + getGunDisplayId(ItemStack) ResourceLocation
  + getAimingZoom(ItemStack) float
  + setGunId(ItemStack, ResourceLocation?) void
  + unloadAttachment(ItemStack, AttachmentType) void
  + getLaserColor(ItemStack) int
  + setCurrentAmmoCount(ItemStack, int) void
  + getGunId(ItemStack) ResourceLocation
  + getExp(ItemStack) int
  + getAttachmentId(ItemStack, AttachmentType) ResourceLocation
  + installAttachment(ItemStack, ItemStack) void
  + setMaxDummyAmmoAmount(ItemStack, int) void
  + useDummyAmmo(ItemStack) boolean
  + hasMaxDummyAmmo(ItemStack) boolean
  + getHeatAmount(ItemStack) float
  + getBuiltinAttachment(ItemStack, AttachmentType) ItemStack
  + hasHeatData(ItemStack) boolean
  + reduceCurrentAmmoCount(ItemStack) void
  + getAttachmentTag(ItemStack, AttachmentType) CompoundTag?
  + isOverheatLocked(ItemStack) boolean
  + addDummyAmmoAmount(ItemStack, int) void
  + lerpInaccuracy(ItemStack) float
  + setGunDisplayId(ItemStack, ResourceLocation) void
  + getFireMode(ItemStack) FireMode
  + setBulletInBarrel(ItemStack, boolean) void
  + getCurrentAmmoCount(ItemStack) int
  + setLaserColor(ItemStack, int) void
  + hasCustomLaserColor(ItemStack) boolean
}
class GunItemManager {
  + GunItemManager() 
  + registerGunItem(String, DeferredHolder~AbstractGunItem~) void
  + getGunItemRegistryObject(String) DeferredHolder~AbstractGunItem~
   Collection~DeferredHolder~AbstractGunItem~~ allGunItems
}
class GunItemRendererWrapper {
  + GunItemRendererWrapper() 
  + initContext(ItemStack, Player, float) GunAnimationStateContext
  + getTextureLocation(ItemStack) ResourceLocation
  - applyPositioningNodeTransform(List~BedrockPart~, PoseStack, Vector3f) void
  - applyPositioningTransform(ItemDisplayContext, TransformScale, BedrockGunModel, PoseStack) void
  + renderByItem(ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, int) void
  + getPutAwayTime(ItemStack) long
  + applyItemInHandCameraAnimation(BeforeRenderHandEvent, ItemStack, LocalPlayer) void
  + tryInit(ItemStack, Player, float) void
  - cacheMuzzlePosition(PoseStack, BedrockGunModel) void
  + updateContext(GunAnimationStateContext, ItemStack, Player, float) void
  + tryExit(ItemStack, long) void
  + applyLevelCameraAnimation(ComputeCameraAngles, ItemStack, LocalPlayer) void
  + getStateMachine(ItemStack) LuaAnimationStateMachine~GunAnimationStateContext~?
  - applyScaleTransform(ItemDisplayContext, TransformScale, PoseStack) void
  + getModel(ItemStack) BedrockGunModel
  + renderFirstPerson(LocalPlayer, ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, float) void
}
class GunKeyConflict {
  + GunKeyConflict() 
  + conflicts(IBindingContext) boolean
   boolean active
}
class GunKubeJSEvents {
  + GunKubeJSEvents() 
}
class GunLevelUpToast {
  + GunLevelUpToast(ItemStack, Component, Component?) 
  + render(GuiGraphics, ToastComponent, long) Visibility
}
class GunLod {
  + GunLod() 
  # ResourceLocation modelTexture
  - ResourceLocation modelLocation
   ResourceLocation modelLocation
   ResourceLocation modelTexture
}
class GunMeleeData {
  + GunMeleeData() 
  - float distance
  - GunDefaultMeleeData? defaultMeleeData
  - float cooldown
   float distance
   GunDefaultMeleeData? defaultMeleeData
   float cooldown
}
class GunMeleeDebug {
  + GunMeleeDebug() 
  + showRange(LivingEntity, int, Vec3, Vec3, float) void
}
class GunMeleeEvent {
  + GunMeleeEvent(LivingEntity, ItemStack, LogicalSide) 
  - LivingEntity shooter
  - ItemStack gunItemStack
  - LogicalSide logicalSide
   LogicalSide logicalSide
   ItemStack gunItemStack
   boolean cancelable
   LivingEntity shooter
}
class GunMeleeEventJS {
  + GunMeleeEventJS(GunMeleeEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunMeleeWrapper {
<<Interface>>
   LogicalSide logicalSide
   ItemStack gunItemStack
   LivingEntity shooter
}
class GunMod {
  + GunMod(IEventBus, ModContainer) 
  + loc(String) ResourceLocation
}
class GunModPlugin {
  + GunModPlugin() 
  + registerCategories(IRecipeCategoryRegistration) void
  + registerRecipeCatalysts(IRecipeCatalystRegistration) void
  + registerRecipes(IRecipeRegistration) void
  + registerItemSubtypes(ISubtypeRegistration) void
   ResourceLocation pluginUid
}
class GunModSubtype {
  + GunModSubtype() 
   IIngredientSubtypeInterpreter~ItemStack~ tableSubType
   IIngredientSubtypeInterpreter~ItemStack~ ammoSubtype
   IIngredientSubtypeInterpreter~ItemStack~ ammoBoxSubtype
   IIngredientSubtypeInterpreter~ItemStack~ attachmentSubtype
   IIngredientSubtypeInterpreter~ItemStack~ gunSubtype
}
class GunModelConstant {
  + GunModelConstant() 
}
class GunModelTypeManager {
  + GunModelTypeManager() 
  + registerModelType(String, BiFunction~BedrockModelPOJO, BedrockVersion, BedrockGunModel~) void
  + getModelInstanceConstructor(String) BiFunction~BedrockModelPOJO, BedrockVersion, BedrockGunModel~
}
class GunNbtFactory {
  + GunNbtFactory() 
  + GunNbtFactory(AbstractGunItem) 
  - int ammoCount
  - boolean bulletInBarrel
  - FireMode fireMode
  + putAttachment(AttachmentType, ResourceLocation) GunNbtFactory
  + putAllAttachment(EnumMap~AttachmentType, ResourceLocation~) GunNbtFactory
  + build() ItemStack
   int ammoCount
   FireMode fireMode
   boolean bulletInBarrel
}
class GunPack {
  + GunPack(Path, String) 
  + name() String
  + path() Path
}
class GunPackList {
  + GunPackList(Minecraft, int, int, int, int, int, Map~ResourceLocation, List~ResourceLocation~~, GunSmithTableScreen) 
  + namespaceList() Set~String~
  + updateSelectedNamespaces() void
  + render(GuiGraphics, int, int, float) void
   String searchText
   int scrollbarPosition
   int rowLeft
   int rowWidth
   boolean byHandSelected
}
class GunPackLoader {
<<enumeration>>
  + GunPackLoader() 
  + loadPacks(Consumer~Pack~) void
  - fromZipPath(Path) GunPack?
  + valueOf(String) GunPackLoader
  - modVersionAllMatch(PackMeta) boolean
  - fromDirPath(Path) GunPack?
  + values() GunPackLoader[]
  - scanExtensions(Path) List~GunPack~
  + getModIcon(String) Path?
  + discoverExtensions() Pack?
  - modVersionMatch(String, String) boolean
}
class GunPackProgressScreen {
  + GunPackProgressScreen() 
  + render(GuiGraphics, int, int, float) void
  + progressStart(Component) void
  + progressStagePercentage(int) void
  + stop() void
  # init() void
  + progressStartNoAbort(Component) void
  + progressStage(Component) void
}
class GunProperties {
  - GunProperties() 
}
class GunProperty~T~ {
  + GunProperty(String, Class~T~) 
  + of(String, TypeToken~T~) GunProperty~T~
  + of(String, Class~T~) GunProperty~T~
  + type() Class~T~
  + name() String
}
class GunPropertyDiagrams {
  + GunPropertyDiagrams() 
  + draw(GuiGraphics, Font, int, int) void
   int hidePropertyButtonYOffset
}
class GunRecoil {
  + GunRecoil() 
  - GunRecoilKeyFrame[]? yaw
  - GunRecoilKeyFrame[]? pitch
  + genYawSplineFunction(float) PolynomialSplineFunction?
  - getSplineFunction(GunRecoilKeyFrame[], float) PolynomialSplineFunction
  + genPitchSplineFunction(float) PolynomialSplineFunction?
   GunRecoilKeyFrame[] yaw
   GunRecoilKeyFrame[] pitch
}
class GunRecoilKeyFrame {
  + GunRecoilKeyFrame() 
  - float time
  - float[] value
  + compareTo(GunRecoilKeyFrame) int
   float time
   float[] value
}
class GunRefitScreen {
  + GunRefitScreen() 
  + onClose() void
  - addInventoryAttachmentButtons() void
  - switchHideButton() void
  + render(GuiGraphics, int, int, float) void
  + getSlotTextureXOffset(ItemStack, AttachmentType) int
  + init() void
  - addAttachmentTypeButtons() void
   boolean pauseScreen
   int slotsTextureWidth
}
class GunReloadData {
  + GunReloadData() 
  - boolean infinite
  - GunReloadTime feed
  - FeedType type
  - GunReloadTime cooldown
   GunReloadTime feed
   GunReloadTime cooldown
   FeedType type
   boolean infinite
}
class GunReloadEvent {
  + GunReloadEvent(LivingEntity, ItemStack, LogicalSide) 
  - LivingEntity entity
  - LogicalSide logicalSide
  - ItemStack gunItemStack
   LogicalSide logicalSide
   ItemStack gunItemStack
   boolean cancelable
   LivingEntity entity
}
class GunReloadEventJS {
  + GunReloadEventJS(GunReloadEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunReloadTime {
  + GunReloadTime() 
  - float emptyTime
  - float tacticalTime
   float tacticalTime
   float emptyTime
}
class GunReloadWrapper {
<<Interface>>
   LogicalSide logicalSide
   ItemStack gunItemStack
   LivingEntity entity
}
class GunResult {
  + GunResult() 
  - int ammoCount
  - EnumMap~AttachmentType, ResourceLocation~ attachments
   int ammoCount
   EnumMap~AttachmentType, ResourceLocation~ attachments
}
class GunShootEvent {
  + GunShootEvent(LivingEntity, ItemStack, LogicalSide) 
  - LivingEntity shooter
  - ItemStack gunItemStack
  - LogicalSide logicalSide
   LogicalSide logicalSide
   ItemStack gunItemStack
   boolean cancelable
   LivingEntity shooter
}
class GunShootEventJS {
  + GunShootEventJS(GunShootEvent) 
   ItemStack eventItemStack
   ScriptTypeHolder? typeHolder
}
class GunShootWrapper {
<<Interface>>
   LogicalSide logicalSide
   ItemStack gunItemStack
   LivingEntity shooter
}
class GunSmithTableBlockA {
  + GunSmithTableBlockA() 
  + isRoot(BlockState) boolean
  + getRootPos(BlockPos, BlockState) BlockPos
  + getStateForPlacement(BlockPlaceContext) BlockState
}
class GunSmithTableBlockB {
  + GunSmithTableBlockB() 
  + getRootPos(BlockPos, BlockState) BlockPos
  - getNeighbourDirection(BedPart, Direction) Direction
  # createBlockStateDefinition(Builder~Block, BlockState~) void
  + getStateForPlacement(BlockPlaceContext) BlockState
  + playerWillDestroy(Level, BlockPos, BlockState, Player) void
  + isRoot(BlockState) boolean
  + setPlacedBy(Level, BlockPos, BlockState, LivingEntity?, ItemStack) void
  + updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos) BlockState
  + parseRotation(Direction) float
}
class GunSmithTableBlockC {
  + GunSmithTableBlockC() 
  + updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos) BlockState
  + isRoot(BlockState) boolean
  + playerWillDestroy(Level, BlockPos, BlockState, Player) void
  # createBlockStateDefinition(Builder~Block, BlockState~) void
  + getStateForPlacement(BlockPlaceContext) BlockState
  + setPlacedBy(Level, BlockPos, BlockState, LivingEntity?, ItemStack) void
  + getRootPos(BlockPos, BlockState) BlockPos
}
class GunSmithTableBlockEntity {
  + GunSmithTableBlockEntity(BlockPos, BlockState) 
  - ResourceLocation? id
  # saveAdditional(CompoundTag) void
  + createMenu(int, Inventory, Player) AbstractContainerMenu?
  + load(CompoundTag) void
   Component displayName
   CompoundTag updateTag
   Packet~ClientGamePacketListener~? updatePacket
   ResourceLocation? id
   AABB renderBoundingBox
}
class GunSmithTableCategory {
  + GunSmithTableCategory(IGuiHelper, ItemStack, RecipeType~GunSmithTableRecipe~, Component) 
  - Component title
  - getInput(List~GunSmithTableIngredient~, int) List~ItemStack~
  + setRecipe(IRecipeLayoutBuilder, GunSmithTableRecipe, IFocusGroup) void
   Component title
   IDrawable background
   RecipeType~GunSmithTableRecipe~ recipeType
   IDrawable icon
}
class GunSmithTableIngredient {
  + GunSmithTableIngredient(Ingredient, int) 
  - int count
  - Ingredient ingredient
   Ingredient ingredient
   int count
}
class GunSmithTableIngredientSerializer {
  + GunSmithTableIngredientSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) GunSmithTableIngredient
}
class GunSmithTableItem {
  + GunSmithTableItem(Block) 
  + getTooltipImage(ItemStack) Optional~TooltipComponent~
  + fillItemCategory() NonNullList~ItemStack~
  + initializeClient(Consumer~IClientItemExtensions~) void
  + getName(ItemStack) Component
}
class GunSmithTableItemRenderer {
  + GunSmithTableItemRenderer(BlockEntityRenderDispatcher, EntityModelSet) 
  + renderByItem(ItemStack, ItemDisplayContext, PoseStack, MultiBufferSource, int, int) void
}
class GunSmithTableMenu {
  + GunSmithTableMenu(int, Inventory, ResourceLocation?) 
  - ResourceLocation blockId
  + stillValid(Player) boolean
  + doCraft(ResourceLocation, Player) void
  + quickMoveStack(Player, int) ItemStack
  - getRecipe(ResourceLocation, RecipeManager) GunSmithTableRecipe?
   ResourceLocation? blockId
}
class GunSmithTableRecipe {
  + GunSmithTableRecipe(ResourceLocation, TableRecipe) 
  + GunSmithTableRecipe(ResourceLocation, GunSmithTableResult, List~GunSmithTableIngredient~) 
  - ResourceLocation id
  - List~GunSmithTableIngredient~ inputs
  - GunSmithTableResult result
  + assemble(Inventory, RegistryAccess) ItemStack
  + canCraftInDimensions(int, int) boolean
  + init() void
  + matches(Inventory, Level) boolean
  + getResultItem(RegistryAccess) ItemStack
   ResourceLocation tab
   RecipeType~?~ type
   GunSmithTableResult result
   List~GunSmithTableIngredient~ inputs
   ResourceLocation id
   RecipeSerializer~?~ serializer
   ItemStack output
}
class GunSmithTableRenderer {
  + GunSmithTableRenderer(Context) 
  + render(GunSmithTableBlockEntity, float, PoseStack, MultiBufferSource, int, int) void
  + getIndex(GunSmithTableBlockEntity) Optional~ClientBlockIndex~
  + shouldRenderOffScreen(GunSmithTableBlockEntity) boolean
  + getIndex(ItemStack) Optional~ClientBlockIndex~
}
class GunSmithTableResult {
  + GunSmithTableResult(ItemStack, ResourceLocation?) 
  + GunSmithTableResult(RawGunTableResult, ResourceLocation?) 
  + GunSmithTableResult(RawGunTableResult) 
  - ItemStack result
  - ResourceLocation group
  + init() void
   ItemStack result
   ResourceLocation group
}
class GunSmithTableResultComponents {
  + GunSmithTableResultComponents() 
}
class GunSmithTableResultInfo {
  - GunSmithTableResultInfo() 
  - GunSmithTableResultInfo(JsonObject) 
  + of(Object) GunSmithTableResultInfo
  + create() GunSmithTableResultInfo
  + createFromJson(JsonObject) GunSmithTableResultInfo
  + toJson() JsonObject
  + createFromItemStack(ItemStack) GunSmithTableResultInfo
   String type
   String groupName
   OutputGroupName group
   int count
   JsonObject customItem
   CompoundTag nbt
   ResourceLocation id
}
class GunSmithTableResultSerializer {
  + GunSmithTableResultSerializer() 
  - getId(JsonObject) ResourceLocation
  + deserialize(JsonElement, Type, JsonDeserializationContext) GunSmithTableResult
}
class GunSmithTableScreen {
  + GunSmithTableScreen(GunSmithTableMenu, Inventory, Component) 
  - int indexPage
  + drawModCenteredString(GuiGraphics, Font, Component, int, int, int) void
  - addTypePageButtons() void
  - classifyRecipes() void
  - isNameMatch(GunSmithTableRecipe) boolean
  - isSuitableForMainHand(GunSmithTableRecipe) boolean
  # renderBg(GuiGraphics, float, int, int) void
  + render(GuiGraphics, int, int, float) void
  - addIndexButtons() void
  - addCraftButton() void
  - addIndexPageButtons() void
  - addScaleButtons() void
  - addUrlButton() void
  + mouseScrolled(double, double, double) boolean
  + init() void
  - renderLeftModel(GunSmithTableRecipe) void
  # renderLabels(GuiGraphics, int, int) void
  - getPlayerIngredientCount(GunSmithTableRecipe) void
  - addTypeButtons() void
  - renderPackInfo(GuiGraphics, GunSmithTableRecipe) void
  - renderIngredient(GuiGraphics) void
  + updateIngredientCount() void
  - getSelectedRecipe(ResourceLocation) GunSmithTableRecipe?
   boolean pauseScreen
   int indexPage
}
class GunSmithTableSerializer {
  + GunSmithTableSerializer() 
  + fromJson(ResourceLocation, JsonObject) GunSmithTableRecipe?
  + fromNetwork(ResourceLocation, FriendlyByteBuf) GunSmithTableRecipe?
  + toNetwork(FriendlyByteBuf, GunSmithTableRecipe) void
}
class GunSoundInstance {
  + GunSoundInstance(SoundEvent, SoundSource, float, float, Entity, int, ResourceLocation, boolean) 
  - ResourceLocation registryName
  + setStop() void
   SoundBuffer? soundBuffer
   ResourceLocation registryName
}
class GunTabType {
<<enumeration>>
  + GunTabType() 
  + values() GunTabType[]
  + valueOf(String) GunTabType
}
class GunTooltip {
  + GunTooltip(ItemStack, IGun, ResourceLocation, CommonGunIndex) 
  - ResourceLocation ammoId
  - ItemStack gun
  - CommonGunIndex gunIndex
   IGun IGun
   CommonGunIndex gunIndex
   ResourceLocation ammoId
   ItemStack gun
}
class GunTooltipPart {
<<enumeration>>
  + GunTooltipPart() 
  - int mask
  + setHideFlags(ItemStack, int) void
  + valueOf(String) GunTooltipPart
  + getHideFlags(ItemStack) int
  + values() GunTooltipPart[]
   int mask
}
class GunTransform {
  + GunTransform() 
  - TransformScale scale
   TransformScale scale
   GunTransform default
}
class HSVSliderGroup {
  + HSVSliderGroup(int, int, int, int, Inventory, int, AttachmentType) 
  - LaserColorSlider hueSlider
  - LaserColorSlider saturationSlider
  - getColor(AttachmentType) int
  + apply() void
   LaserColorSlider saturationSlider
   LaserColorSlider hueSlider
}
class HeadShotAABBConfigRead {
  + HeadShotAABBConfigRead() 
  + clearAABB() void
  + getAABB(ResourceLocation) AABB
  + init() void
  + addCheck(String) void
}
class HeadShotJsonProperty {
  + HeadShotJsonProperty(Modifier) 
  + initComponents() void
}
class HeadShotModifier {
  + HeadShotModifier() 
  + String ID
  + eval(List~Modifier~, CacheValue~Float~) void
  + initCache(ItemStack, GunData) CacheValue~Float~
  + readJson(String) JsonProperty~Modifier~
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
   String ID
   int diagramsDataSize
}
class HeatBarOverlay {
  + HeatBarOverlay() 
  + render(GuiGraphics, DeltaTracker) void
  + renderOverheat(float, GuiGraphics, int, int, boolean, int) void
  + getHeatColor(float, boolean, int) int
}
class HideTooltipPartCommand {
  + HideTooltipPartCommand() 
  + get() LiteralArgumentBuilder~CommandSourceStack~
  - setHide(CommandContext~CommandSourceStack~) int
}
class HitboxHelper {
  + HitboxHelper() 
  + getPlayerVelocity(Player) Vec3
  + getFixedBoundingBox(Entity, Entity) AABB
  + onPlayerTick(Player) void
  + getBoundingBox(Player, int) AABB
  + getVelocity(Player, int) Vec3
  + onPlayerLoggedOut(Player) void
}
class HitboxHelperEvent {
  + HitboxHelperEvent() 
  + onPlayerTick(Post) void
  + onPlayerLoggedOut(PlayerLoggedOutEvent) void
}
class HumanoidModelMixin~T~ {
  + HumanoidModelMixin() 
  - setRotationAnglesHead(T, float, float, float, float, float, CallbackInfo) void
}
class HumanoidOffhandRender {
  + HumanoidOffhandRender() 
  - renderHotbarGun(LivingEntity, PoseStack, MultiBufferSource, int, ItemStack, int) void
  - renderOffhandGun(LivingEntity, PoseStack, MultiBufferSource, int) void
  + renderGun(LivingEntity, PoseStack, MultiBufferSource, int) void
  - renderHotbarGun(LivingEntity, PoseStack, MultiBufferSource, int) void
  - renderGunItem(LivingEntity, PoseStack, MultiBufferSource, int, ItemStack, LayerGunShow) void
}
class IAmmo {
<<Interface>>
  + setAmmoId(ItemStack, ResourceLocation?) void
  + getIAmmoOrNull(ItemStack?) IAmmo?
  + isAmmoOfGun(ItemStack, ItemStack) boolean
  + getAmmoId(ItemStack) ResourceLocation
}
class IAmmoBox {
<<Interface>>
  + getAmmoId(ItemStack) ResourceLocation
  + setAmmoLevel(ItemStack, int) ItemStack
  + setAmmoId(ItemStack, ResourceLocation) void
  + isCreative(ItemStack) boolean
  + setCreative(ItemStack, boolean) ItemStack
  + getAmmoCount(ItemStack) int
  + isAllTypeCreative(ItemStack) boolean
  + setAmmoCount(ItemStack, int) void
  + isAmmoBoxOfGun(ItemStack, ItemStack) boolean
  + getAmmoLevel(ItemStack) int
}
class IAnimationItem {
<<Interface>>
  + isSame(ItemStack, ItemStack) boolean
  + matchesIgnoreCount(ItemStack, ItemStack) boolean
}
class IAttachment {
<<Interface>>
  + getZoomNumber(ItemStack) int
  + getLaserColor(ItemStack) int
  + getAttachmentId(ItemStack) ResourceLocation
  + hasCustomLaserColor(ItemStack) boolean
  + setSkinId(ItemStack, ResourceLocation?) void
  + setAttachmentId(ItemStack, ResourceLocation?) void
  + getSkinId(ItemStack) ResourceLocation?
  + setZoomNumber(ItemStack, int) void
  + getType(ItemStack) AttachmentType
  + getIAttachmentOrNull(ItemStack?) IAttachment?
  + setLaserColor(ItemStack, int) void
}
class IAttachmentModifier~T, K~ {
<<Interface>>
  + readJson(String) JsonProperty~T~
  + eval(List~T~, CacheValue~K~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~K~
   String id
   String optionalFields
   int diagramsDataSize
}
class IBlock {
<<Interface>>
  + getBlockId(ItemStack) ResourceLocation
  + setBlockId(ItemStack, ResourceLocation?) void
}
class IClientPlayerGunOperator {
<<Interface>>
  + shoot() ShootResult
  + crawl(boolean) void
  + melee() void
  + fromLocalPlayer(LocalPlayer) IClientPlayerGunOperator
  + bolt() void
  + resetDraw() void
  + draw(ItemStack) void
  + getClientAimingProgress(float) float
  + reload() void
  + aim(boolean) void
  + inspect() void
  + fireSelect() void
   boolean aim
   long clientShootCoolDown
   boolean crawl
   LocalPlayerDataHolder dataHolder
   boolean readyToDraw
}
class ICommonResourceProvider {
<<Interface>>
  + getBlockData(ResourceLocation) BlockData?
  + getBlockIndex(ResourceLocation) CommonBlockIndex?
  + getScript(ResourceLocation) LuaTable?
  + getAttachmentTags(ResourceLocation) Set~String~
  + getGunIndex(ResourceLocation) CommonGunIndex?
  + getAttachmentIndex(ResourceLocation) CommonAttachmentIndex?
  + getAmmoIndex(ResourceLocation) CommonAmmoIndex?
  + getGunData(ResourceLocation) GunData?
  + getAttachmentData(ResourceLocation) AttachmentData?
  + getAllowAttachmentTags(ResourceLocation) Set~String~
  + getRecipeFilter(ResourceLocation) RecipeFilter?
   Set~Entry~ResourceLocation, CommonGunIndex~~ allGuns
   Set~Entry~ResourceLocation, CommonAmmoIndex~~ allAmmos
   Set~Entry~ResourceLocation, CommonAttachmentIndex~~ allAttachments
   Set~Entry~ResourceLocation, CommonBlockIndex~~ allBlocks
}
class IComponentTooltip {
<<Interface>>
  + getTooltipFromItem(ItemStack) List~Component~
  + renderTooltip(Consumer~List~Component~~) void
}
class IDataSerializer~T~ {
<<Interface>>
  + write(T) Tag
  + read(Tag) T
  + write(FriendlyByteBuf, T) void
  + read(FriendlyByteBuf) T
}
class IDisplay {
<<Interface>>
  + init() void
}
class IFilter~T~ {
<<Interface>>
  + filter(List~T~, boolean) List~T~
  + test(T) boolean
}
class IFunctionalRenderer {
<<Interface>>
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class IGun {
<<Interface>>
  + getDummyAmmoAmount(ItemStack) int
  + getHeatAmount(ItemStack) float
  + getMainHandFireMode(LivingEntity) FireMode
  + setLaserColor(ItemStack, int) void
  + getAttachmentTag(ItemStack, AttachmentType) CompoundTag?
  + addDummyAmmoAmount(ItemStack, int) void
  + getLevel(ItemStack) int
  + setMaxDummyAmmoAmount(ItemStack, int) void
  + isCanCrawl(ItemStack) boolean
  + setOverheatLocked(ItemStack, boolean) void
  + setGunDisplayId(ItemStack, ResourceLocation?) void
  + setCurrentAmmoCount(ItemStack, int) void
  + getExp(int) int
  + installAttachment(ItemStack, ItemStack) void
  + setDummyAmmoAmount(ItemStack, int) void
  + getIGunOrNull(ItemStack?) IGun?
  + lerpInaccuracy(ItemStack) float
  + hasMaxDummyAmmo(ItemStack) boolean
  + getCurrentAmmoCount(ItemStack) int
  + hasAttachmentLock(ItemStack) boolean
  + allowAttachmentType(ItemStack, AttachmentType) boolean
  + getExpCurrentLevel(ItemStack) int
  + useDummyAmmo(ItemStack) boolean
  + getGunId(ItemStack) ResourceLocation
  + isOverheatLocked(ItemStack) boolean
  + unloadAttachment(ItemStack, AttachmentType) void
  + setBulletInBarrel(ItemStack, boolean) void
  + hasCustomLaserColor(ItemStack) boolean
  + getLevel(int) int
  + hasBulletInBarrel(ItemStack) boolean
  + getBuiltInAttachmentId(ItemStack, AttachmentType) ResourceLocation
  + dropAllAmmo(Player, ItemStack) void
  + allowAttachment(ItemStack, ItemStack) boolean
  + getGunDisplayId(ItemStack) ResourceLocation
  + setFireMode(ItemStack, FireMode?) void
  + setHeatAmount(ItemStack, float) void
  + getAttachmentId(ItemStack, AttachmentType) ResourceLocation
  + getFireMode(ItemStack) FireMode
  + getMainhandFireMode(LivingEntity) FireMode
  + getRPM(ItemStack) int
  + mainhandHoldGun(LivingEntity) boolean
  + getBuiltinAttachment(ItemStack, AttachmentType) ItemStack
  + hasInventoryAmmo(LivingEntity, ItemStack, boolean) boolean
  + lerpRPM(ItemStack) float
  + getExp(ItemStack) int
  + setAttachmentLock(ItemStack, boolean) void
  + getLaserColor(ItemStack) int
  + reduceCurrentAmmoCount(ItemStack) void
  + hasHeatData(ItemStack) boolean
  + getMaxDummyAmmoAmount(ItemStack) int
  + useInventoryAmmo(ItemStack) boolean
  + getExpToNextLevel(ItemStack) int
  + getAttachment(ItemStack, AttachmentType) ItemStack
  + mainHandHoldGun(LivingEntity) boolean
  + setGunId(ItemStack, ResourceLocation?) void
  + getAimingZoom(ItemStack) float
   int maxLevel
}
class IGunOperator {
<<Interface>>
  + fireSelect() void
  + getProcessedSprintStatus(boolean) boolean
  + consumesAmmoOrNot() boolean
  + zoom() void
  + bolt() void
  + melee() void
  + nextBulletIsTracer(int) boolean
  + shoot(Supplier~Float~, Supplier~Float~) ShootResult
  + draw(Supplier~ItemStack~) void
  + reload() void
  + needCheckAmmo() boolean
  + updateCacheProperty(AttachmentCacheProperty) void
  + initialData() void
  + aim(boolean) void
  + crawl(boolean) void
  + fromLivingEntity(LivingEntity) IGunOperator
  + shoot(Supplier~Float~, Supplier~Float~, long) ShootResult
  + cancelReload() void
   long synMeleeCoolDown
   AttachmentCacheProperty? cacheProperty
   ShooterDataHolder dataHolder
   long synShootCoolDown
   boolean synIsAiming
   boolean synIsBolting
   long synDrawCoolDown
   float synAimingProgress
   ReloadState synReloadState
   float synSprintTime
}
class IMessage {
<<Interface>>
  + handle(IPayloadContext) void
   StreamCodec~ByteBuf, IMessage~ streamCodec
}
class INetworkCacheReloadListener {
<<Interface>>
   DataType type
   Map~ResourceLocation, String~ networkCache
}
class IStackTooltip {
<<Interface>>
  + renderTooltip(Consumer~ItemStack~) void
}
class ITargetEntity {
<<Interface>>
  + onProjectileHit(Entity, EntityHitResult, DamageSource, float) void
}
class IThirdPersonAnimation {
<<Interface>>
  + animateGunHold(LivingEntity, ModelPart, ModelPart, ModelPart, ModelPart) void
  + animateGunAim(LivingEntity, ModelPart, ModelPart, ModelPart, ModelPart, float) void
}
class Ignite {
  + Ignite(boolean, boolean) 
  + Ignite(boolean) 
  - boolean igniteEntity
  - boolean igniteBlock
   boolean igniteBlock
   boolean igniteEntity
}
class IgniteJsonProperty {
  + IgniteJsonProperty(Ignite) 
  + initComponents() void
}
class IgniteModifier {
  + IgniteModifier() 
  + String ID
  + eval(List~Ignite~, CacheValue~Ignite~) void
  + readJson(String) JsonProperty~Ignite~
  + initCache(ItemStack, GunData) CacheValue~Ignite~
   String ID
}
class IgniteSerializer {
  + IgniteSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) Ignite
}
class InaccuracyJsonProperty {
  + InaccuracyJsonProperty(Map~InaccuracyType, Modifier~) 
  + initComponents() void
  - createEntry(InaccuracyType, String, String) void
}
class InaccuracyModifier {
  + InaccuracyModifier() 
  + String ID
  - buildAim(GunData, AttachmentCacheProperty, GunFireModeAdjustData) DiagramsData
  + eval(List~Map~InaccuracyType, Modifier~~, CacheValue~Map~InaccuracyType, Float~~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~Map~InaccuracyType, Float~~
  + readJson(String) JsonProperty~Map~InaccuracyType, Modifier~~
  - buildNormal(GunData, AttachmentCacheProperty, GunFireModeAdjustData, InaccuracyType, String, double) DiagramsData
   String ID
   String optionalFields
   int diagramsDataSize
}
class InaccuracyType {
<<enumeration>>
  + InaccuracyType() 
  +  AIM
  + values() InaccuracyType[]
  - isMove(LivingEntity) boolean
  + getInaccuracyType(LivingEntity) InaccuracyType
  + valueOf(String) InaccuracyType
   boolean AIM
   Map~InaccuracyType, Float~ defaultInaccuracy
}
class Info {
  + Info() 
  - HashMap~String, String~ dependencies
   HashMap~String, String~ dependencies
}
class InnerThirdPersonManager {
  + InnerThirdPersonManager() 
  + setRotationAnglesHead(LivingEntity, ModelPart, ModelPart, ModelPart, ModelPart, float) void
  - playVanillaAnimation(LivingEntity, ModelPart, ModelPart, ModelPart, ModelPart, IGunOperator, GunDisplayInstance) void
}
class InputExtraCheck {
  + InputExtraCheck() 
   boolean inGame
}
class InspectKey {
  + InspectKey() 
  + onInspectControllerPress(boolean) boolean
  + onInspectPress(Key) void
}
class InteractKey {
  + InteractKey() 
  + onInteractKeyPress(Key) void
  + onInteractControllerPress(boolean) boolean
  - interactBlock(BlockHitResult, LocalPlayer, Minecraft) void
  + onInteractMousePress(Post) void
  - interactEntity(EntityHitResult, Minecraft) void
  - doInteractLogic() void
}
class InteractKeyConfigRead {
  + InteractKeyConfigRead() 
  + init() void
  + canInteractEntity(Entity) boolean
  - handleConfigData(List~String~, EnumMap~Type, List~ResourceLocation~~, Type) void
  + canInteractBlock(BlockState) boolean
}
class InteractKeyTextOverlay {
  + InteractKeyTextOverlay() 
  + render(GuiGraphics, DeltaTracker) void
  - renderText(GuiGraphics, int, int, Font) void
  - renderEntityText(GuiGraphics, int, int, EntityHitResult, Minecraft) void
  - renderBlockText(GuiGraphics, int, int, BlockHitResult, LocalPlayer, Minecraft) void
}
class InternalAssetLoader {
  + InternalAssetLoader() 
  - List~ObjectAnimation~ defaultPistolAnimations
  - List~ObjectAnimation~ defaultRifleAnimations
  + onResourceReload() void
  - loadBedrockModels(ResourceLocation) void
  - loadAnimations(ResourceLocation) BedrockAnimationFile
  + getBedrockModel(ResourceLocation) Optional~BedrockModel~
   List~ObjectAnimation~ defaultRifleAnimations
   List~ObjectAnimation~ defaultPistolAnimations
}
class Interpolation {
<<enumeration>>
  + Interpolation() 
  + valueOf(String) Interpolation
  + values() Interpolation[]
}
class Interpolator {
<<Interface>>
  + clone() Interpolator
  + interpolate(int, int, float) float[]
  + compile(AnimationChannelContent) void
}
class InterpolatorType {
<<enumeration>>
  + InterpolatorType() 
  + values() InterpolatorType[]
  + valueOf(String) InterpolatorType
}
class InterpolatorUtil {
  + InterpolatorUtil() 
  + fromInterpolation(InterpolatorType) Interpolator
}
class InventoryAttachmentSlot {
  + InventoryAttachmentSlot(int, int, int, Inventory, OnPress) 
  - int slotIndex
  + renderTooltip(Consumer~ItemStack~) void
  + renderWidget(GuiGraphics, int, int, float) void
   int slotIndex
}
class InventoryEvent {
  + InventoryEvent() 
  + onPlayerChangeSelect(ClientTickEvent) void
  + onPlayerSwapMainHand(SwapItemWithOffHand) void
  + onPlayerLoggedOut(LoggingOut) void
  - isSame(ItemStack, ItemStack) boolean
}
class ItemAnimationStateContext {
  + ItemAnimationStateContext() 
  - float putAwayTime
  # float partialTicks
   float putAwayTime
   float partialTicks
}
class ItemInHandLayerMixin {
  + ItemInHandLayerMixin() 
  - renderArmWithItemHead(LivingEntity, ItemStack, ItemDisplayContext, HumanoidArm, PoseStack, MultiBufferSource, int, CallbackInfo) void
  - render(PoseStack, MultiBufferSource, int, LivingEntity, float, float, float, float, float, float, CallbackInfo) void
  - renderArmWithItemTail(LivingEntity, ItemStack, ItemDisplayContext, HumanoidArm, PoseStack, MultiBufferSource, int, CallbackInfo) void
}
class ItemInHandRendererMixin {
  + ItemInHandRendererMixin() 
  + cancelEquippedProgress(CallbackInfo) void
  + keep(ItemStack, long) void
  + beforeHandRender(float, PoseStack, BufferSource, LocalPlayer, int, CallbackInfo) void
   ItemStack currentItem
}
class ItemIndexInfo {
  - ItemIndexInfo(String, ResourceLocation) 
  - ItemIndexInfo(String, String, String) 
  - ResourceLocation indexId
  - String parent
  + create(String) ItemIndexInfo
  + getItemStack(Item) ItemStack
  - isTypeValidForRecipe(String) boolean
  - isTypeValid(String) boolean
  + createFromResourceLocation(ResourceLocation) ItemIndexInfo
   boolean valid
   ItemStack itemStack
   ResourceLocation indexId
   String parent
   boolean validForRecipe
}
class ItemStackSerializer {
  + ItemStackSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) ItemStack
}
class JsonDataManager~T~ {
  + JsonDataManager(Class~T~, Gson, String, String) 
  + JsonDataManager(Class~T~, Gson, FileToIdConverter, String) 
  - Marker marker
  - Class~T~ dataClass
  - Gson gson
  + getData(ResourceLocation) T
  # prepare(ResourceManager, ProfilerFiller) Map~ResourceLocation, JsonElement~
  # parseJson(JsonElement) T
  # apply(Map~ResourceLocation, JsonElement~, ResourceManager, ProfilerFiller) void
   Class~T~ dataClass
   Map~ResourceLocation, T~ allData
   Gson gson
   Marker marker
}
class JsonProperty~T~ {
  + JsonProperty(T?) 
  - T? value
  # List~Component~ components
  + initComponents() void
   List~Component~ components
   T? value
}
class JsonResourceLoader~T~ {
  + JsonResourceLoader(Class~T~, String, String) 
  - Class~T~ dataClass
  + resolveJson(ResourceLocation, String) void
  + load(ZipFile, String) boolean
  + load(File) void
   Class~T~ dataClass
}
class KeepingItemRenderer {
<<Interface>>
  + keep(ItemStack, long) void
   ItemStack currentItem
   KeepingItemRenderer renderer
}
class KeyClothConfig {
  + KeyClothConfig() 
  + init(ConfigBuilder, ConfigEntryBuilder) void
}
class KeyConfig {
  + KeyConfig() 
  + init(Builder) void
}
class Keyframe {
  + Keyframe(Vector3f?, Vector3f?, Vector3f?, String?) 
  + pre() Vector3f?
  + data() Vector3f?
  + lerpMode() String?
  + post() Vector3f?
}
class KillAmountOverlay {
  + KillAmountOverlay() 
  + render(GuiGraphics, DeltaTracker) void
  + markTimestamp() void
}
class KnockBackModifier {
<<Interface>>
  + fromLivingEntity(LivingEntity) KnockBackModifier
  + resetKnockBackStrength() void
   double knockBackStrength
}
class KnockbackChange {
  + KnockbackChange() 
  + onKnockback(LivingKnockBackEvent) void
}
class KnockbackJsonProperty {
  + KnockbackJsonProperty(Modifier) 
  + initComponents() void
}
class KnockbackModifier {
  + KnockbackModifier() 
  + String ID
  + eval(List~Modifier~, CacheValue~Float~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~Float~
  + readJson(String) JsonProperty~Modifier~
   String ID
   int diagramsDataSize
}
class KubeJSCustomGunItem {
  # KubeJSCustomGunItem() 
}
class KubeJSGunEventPoster~E~ {
<<Interface>>
  + postClientEventToKubeJS(E) void
  + postEventToKubeJS(E) void
  + postServerEventToKubeJS(E) void
}
class LanguageMixin {
  + LanguageMixin() 
}
class LaserBeamRenderState {
  + LaserBeamRenderState(String, Runnable, Runnable) 
  # RenderType LASER_BEAM
   RenderType LASER_BEAM
}
class LaserColorSlider {
  + LaserColorSlider(int, int, int, int, HSVSliderGroup, double) 
  # applyValue() void
}
class LaserColorUtil {
  + LaserColorUtil() 
  + getLaserColor(ItemStack, LaserConfig) int
  + getLaserColor(ItemStack) int
}
class LaserConfig {
  + LaserConfig() 
  - Integer defaultColor
  - int length
  - float width
  + canEdit() boolean
   int length
   int defaultColor
   float lengthThird
   float width
   float widthThird
}
class LayerGunShow {
  + LayerGunShow() 
  - Vector3f rotate
  - Vector3f pos
  - Vector3f scale
   Vector3f rotate
   Vector3f pos
   Vector3f scale
}
class LeftHandRender {
  + LeftHandRender(BedrockAnimatedModel) 
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class LegacyPack {
  + LegacyPack(File, String, PackInfo) 
  - parsePackInfo(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseRecipe(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseTexture(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseAnimation(ZipOutputStream, ZipEntry, ZipFile) boolean
  + info() PackInfo
  + file() File
  - addMeta(ZipOutputStream) void
  - parseData(ZipOutputStream, ZipEntry, ZipFile) boolean
  - writeEntry(ZipOutputStream, ZipEntry, ZipFile, String) void
  + namespace() String
  - parseTags(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseLang(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parsePlayerAnimator(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseSound(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseDisplay(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseIndex(ZipOutputStream, ZipEntry, ZipFile) boolean
  - parseModels(ZipOutputStream, ZipEntry, ZipFile) boolean
  + convert() void
}
class LerpMode {
<<enumeration>>
  + LerpMode() 
  + values() LerpMode[]
  + valueOf(String) LerpMode
}
class Linear {
  + Linear() 
  + interpolate(int, int, float) float[]
  + compile(AnimationChannelContent) void
  + clone() Linear
}
class ListPackCommand {
  + ListPackCommand() 
}
class LiteralFilter~T~ {
  + LiteralFilter(Set~T~) 
  - Set~T~ set
  + test(T) boolean
   Set~T~ set
}
class LivingEntityAim {
  + LivingEntityAim(LivingEntity, ShooterDataHolder) 
  + aim(boolean) void
  + zoom() void
  + tickSprint() void
  + tickAimingProgress() void
}
class LivingEntityAmmoCheck {
  + LivingEntityAmmoCheck(LivingEntity) 
  + needCheckAmmo() boolean
  + consumesAmmoOrNot() boolean
}
class LivingEntityBolt {
  + LivingEntityBolt(ShooterDataHolder, LivingEntity, LivingEntityDrawGun, LivingEntityShoot) 
  + bolt() void
  + tickBolt() void
}
class LivingEntityCrawl {
  + LivingEntityCrawl(LivingEntity, ShooterDataHolder) 
  - setCrawlPose() void
  + tickCrawling() void
  + crawl(boolean) void
}
class LivingEntityDrawGun {
  + LivingEntityDrawGun(LivingEntity, ShooterDataHolder) 
  + draw(Supplier~ItemStack~) void
  - updatePutAwayTime() void
   long drawCoolDown
}
class LivingEntityFireSelect {
  + LivingEntityFireSelect(LivingEntity, ShooterDataHolder) 
  + fireSelect() void
}
class LivingEntityHeat {
  + LivingEntityHeat(LivingEntity, ShooterDataHolder) 
  + tickHeat() void
}
class LivingEntityMelee {
  + LivingEntityMelee(LivingEntity, ShooterDataHolder, LivingEntityDrawGun) 
  - getMeleeData(ResourceLocation) MeleeData?
  + scheduleTickMelee() void
  + melee() void
  - getTotalCooldownTime(GunMeleeData, float) long
   long meleeCoolDown
}
class LivingEntityMixin {
  + LivingEntityMixin(EntityType~?~, Level) 
  + getProcessedSprintStatus(boolean) boolean
  + needCheckAmmo() boolean
  - onTickServerSide(CallbackInfo) void
  + shoot(Supplier~Float~, Supplier~Float~) ShootResult
  + melee() void
  + aim(boolean) void
  + crawl(boolean) void
  + cancelReload() void
  + draw(Supplier~ItemStack~) void
  + reload() void
  + initialData() void
  + updateCacheProperty(AttachmentCacheProperty) void
  + bolt() void
  + consumesAmmoOrNot() boolean
  + resetKnockBackStrength() void
  + zoom() void
  + shoot(Supplier~Float~, Supplier~Float~, long) ShootResult
  + nextBulletIsTracer(int) boolean
  + fireSelect() void
   long synMeleeCoolDown
   AttachmentCacheProperty? cacheProperty
   ShooterDataHolder dataHolder
   double knockBackStrength
   long synShootCoolDown
   boolean synIsAiming
   boolean synIsBolting
   long synDrawCoolDown
   float synAimingProgress
   ReloadState synReloadState
   float synSprintTime
}
class LivingEntityReload {
  + LivingEntityReload(LivingEntity, ShooterDataHolder, LivingEntityDrawGun, LivingEntityShoot) 
  + cancelReload() void
  + reload() void
  + tickReloadState() ReloadState
}
class LivingEntityShoot {
  + LivingEntityShoot(LivingEntity, ShooterDataHolder, LivingEntityDrawGun) 
  + consumeAmmoFromPlayer(int, ItemStack, boolean) void
  + shoot(Supplier~Float~, Supplier~Float~, long) ShootResult
  + getShootCoolDown(long) long
   long shootCoolDown
}
class LivingEntitySpeedModifier {
  + LivingEntitySpeedModifier(LivingEntity, ShooterDataHolder) 
  + updateSpeedModifier() void
  - getTargetSpeed(MoveSpeed) double
}
class LivingEntitySprint {
  + LivingEntitySprint(LivingEntity, ShooterDataHolder) 
  + getProcessedSprintStatus(boolean) boolean
}
class LoadingConfigEvent {
  + LoadingConfigEvent() 
  + onLoadingConfig(Loading) void
  + onReloadingConfig(Reloading) void
}
class LocalPlayerAim {
  + LocalPlayerAim(LocalPlayerDataHolder, LocalPlayer) 
  + getClientAimingProgress(float) float
  + tickAimingProgress() void
  - aimProgressCalculate(float) void
  + aim(boolean) void
  - getAlphaProgress(GunData) float
   boolean aim
}
class LocalPlayerBolt {
  + LocalPlayerBolt(LocalPlayerDataHolder, LocalPlayer) 
  + bolt() void
  + tickAutoBolt() void
}
class LocalPlayerCrawl {
  + LocalPlayerCrawl(LocalPlayer) 
  - boolean isCrawling
  + crawl(boolean) void
  + tickCrawl() void
  - setCrawlPose() void
   boolean isCrawling
}
class LocalPlayerDataHolder {
  + LocalPlayerDataHolder(LocalPlayer) 
  + tickStateLock() void
  + lockState(Predicate~IGunOperator~?) void
  + reset() void
}
class LocalPlayerDraw {
  + LocalPlayerDraw(LocalPlayerDataHolder, LocalPlayer) 
  - doDraw(ItemStack, long) void
  - doPutAway(ItemStack, long) void
  + draw(ItemStack) void
  - getDrawTime(ItemStack, IGun, long) long
  - resetData() void
}
class LocalPlayerFireSelect {
  + LocalPlayerFireSelect(LocalPlayerDataHolder, LocalPlayer) 
  + fireSelect() void
}
class LocalPlayerInspect {
  + LocalPlayerInspect(LocalPlayerDataHolder, LocalPlayer) 
  + inspect() void
}
class LocalPlayerMelee {
  + LocalPlayerMelee(LocalPlayerDataHolder, LocalPlayer) 
  + melee() void
  - doPushMelee(GunDisplayInstance) void
  - getMeleeData(ResourceLocation) MeleeData?
  - doMuzzleMelee(GunDisplayInstance) void
  - doStockMelee(GunDisplayInstance) void
  - prepareMelee() boolean
}
class LocalPlayerMixin {
  + LocalPlayerMixin() 
  + onRespawn(CallbackInfo) void
  + reload() void
  + swapSprintStatus(LocalPlayer, boolean, Operation~Void~) void
  + inspect() void
  + fireSelect() void
  + getClientAimingProgress(float) float
  + crawl(boolean) void
  + shoot() ShootResult
  + bolt() void
  + draw(ItemStack) void
  + aim(boolean) void
  + melee() void
  + resetDraw() void
  + onTickClientSide(CallbackInfo) void
   boolean aim
   long clientShootCoolDown
   boolean crawl
   LocalPlayerDataHolder dataHolder
   boolean readyToDraw
}
class LocalPlayerReload {
  + LocalPlayerReload(LocalPlayerDataHolder, LocalPlayer) 
  - cancelReload(GunDisplayInstance) void
  - doReload(IGun, GunDisplayInstance, GunData, ItemStack) void
  + cancelReload() void
  + reload() void
}
class LocalPlayerShoot {
  + LocalPlayerShoot(LocalPlayerDataHolder, LocalPlayer) 
  + shoot() ShootResult
  - doShoot(GunDisplayInstance, IGun, ItemStack, GunData, long) void
  - useSilenceSound() boolean
  - getCoolDown(IGun, ItemStack, GunData) long
   long clientShootCoolDown
}
class LocalPlayerSprint {
  + LocalPlayerSprint(LocalPlayerDataHolder, LocalPlayer) 
  + getProcessedSprintStatus(boolean) boolean
}
class LoginIndexHolder {
  + LoginIndexHolder() 
  - int loginIndex
   int loginIndex
   int asInt
}
class LuaAnimationConstant {
  + LuaAnimationConstant() 
  + install(LuaValue) void
}
class LuaAnimationState~T~ {
  ~ LuaAnimationState(LuaTable, LuaTable) 
  - checkLuaFunction(String) LuaFunction?
  + update(T) void
  + transition(T, String) AnimationState~T~
  + entryAction(T) void
  + exitAction(T) void
}
class LuaAnimationStateMachine~T~ {
  ~ LuaAnimationStateMachine(AnimationController) 
  + initialize() void
  + exit() void
}
class LuaEntityAccessor {
  + LuaEntityAccessor(LivingEntity) 
  + entity() LivingEntity
  + translatable(String) Component
  + literal(String) Component
  + sendSystemMessage(Component) void
  + sendActionBar(Component) void
  + hurt(float) boolean
  + translatable(String, Component[]) Component
   float health
}
class LuaGunAnimationConstant {
  + LuaGunAnimationConstant() 
  + install(LuaValue) void
}
class LuaGunLogicConstant {
  + LuaGunLogicConstant() 
  + install(LuaValue) void
}
class LuaLibrary {
<<Interface>>
  + install(LuaValue) void
}
class LuaNbtAccessor {
  + LuaNbtAccessor(ItemStack, CompoundTag) 
  + getString(String) String
  + putCompound(String, LuaNbtAccessor) void
  + getFloat(String) float
  + getCompound(String) LuaNbtAccessor?
  + contains(String, int) boolean
  + putString(String, String) void
  + getLong(String) long
  + putFloat(String, float) void
  + putBoolean(String, boolean) void
  + contains(String) boolean
  - getDataComponentValue(String) Object?
  + stack() ItemStack
  + getBoolean(String) boolean
  + putLong(String, long) void
  - setDataComponentValue(String, Object) void
  + from(ItemStack) LuaNbtAccessor
  + putInt(String, int) void
  + getInt(String) int
  + getDouble(String) double
  + from(CompoundTag) LuaNbtAccessor
  + putDouble(String, double) void
  + fallbackNbt() CompoundTag
  + newCompoundTag() LuaNbtAccessor
}
class LuaStateMachineFactory~T~ {
  + LuaStateMachineFactory() 
  - AnimationController controller
  + build() LuaAnimationStateMachine~T~
  - checkNullPointer() void
  - checkFunction(String, LuaTable) LuaFunction?
   LuaTable luaScripts
   Supplier~Iterable~AnimationState~T~~~? statesSupplier
   AnimationController controller
}
class MathUtil {
  + MathUtil() 
  + blendQuaternion(Quaternionf, Quaternionf) void
  + toDegreePositive(double) double
  + slerp(float[], float[], float) float[]
  + copySign(double, double) double
  + zoomSensitivityRatio(double, double, double) double
  + applyMatrixLerp(Matrix4f, Matrix4f, Matrix4f, float) void
  + toEulerAngles(float[]) float[]
  + getRelativeQuaternion(float[], float[]) float[]
  + magnificationToFov(double, double) double
  + toQuaternion(float[]) Quaternionf
  + getRelativeQuaternion(Quaternionf, Quaternionf) Quaternionf
  + fovToMagnification(double, double) double
  + toEulerAngles(Quaternionf) float[]
  + splineCurve(float[], float, float) float
  + toQuaternion(float, float, float) float[]
  + slerp(Quaternionf, Quaternionf, float) Quaternionf
  + normalizeQuaternion(Quaternionf) void
  + mulQuaternion(float[], float[]) float[]
  + solveEquations(float[][], float[]) float[]
  + multiplyQuaternion(Quaternionf, float) Quaternionf
  + getAngleAndAxis(Quaternionf) Pair~Float, Vector3f~
  + quaternionSplineCurve(float[][], float, float) float[]
  + toQuaternion(float, float, float, Quaternionf) void
  + expQuaternion(Quaternionf) void
  + getAngleAndAxis(float[]) Pair~Float, Vector3f~
  + multiplyQuaternion(float[], float) float[]
  + inverseQuaternion(float[]) float[]
  + magnificationToFovMultiplier(double, double) double
  + logQuaternion(Quaternionf) void
  + getEulerAngles(Matrix4f) Vector3f
  + getTwoVecAngle(Vec3, Vec3) double
}
class MaybeMultipartEntity {
  + MaybeMultipartEntity(Entity, Entity) 
  + hitPart() Entity
  + core() Entity
  + of(Entity) MaybeMultipartEntity
}
class Md5Utils {
  + Md5Utils() 
  + md5Hex(InputStream) String
  + md5(InputStream) byte[]
  + md5(byte[]) byte[]
  + md5Hex(byte[]) String
  + toHexString(byte[]) String
}
class MeleeData {
  + MeleeData() 
  - float rangeAngle
  - float cooldown
  - List~EffectData~ effects
  - float distance
  - float prepTime
  - float damage
  - float knockback
   float distance
   float prepTime
   float rangeAngle
   List~EffectData~ effects
   float cooldown
   float damage
   float knockback
}
class MeleeKey {
  + MeleeKey() 
  + onMeleeMousePress(Post) void
  + onMeleeKeyPress(Key) void
  + onMeleeControllerPress(boolean) boolean
  - doMeleeLogic() void
}
class MenuIntegration {
  + MenuIntegration() 
  + registerModsPage() void
  + getConfigScreen(Screen?) Screen
   ConfigBuilder configBuilder
}
class ModAttributes {
  + ModAttributes() 
  + addAttributes(EntityAttributeModificationEvent) void
}
class ModBlocks {
  + ModBlocks() 
}
class ModCapabilities {
  + ModCapabilities() 
}
class ModContainer {
  + ModContainer() 
}
class ModContainerScreen {
  + ModContainerScreen() 
  + clientSetup(FMLClientSetupEvent) void
}
class ModCreativeTabs {
  + ModCreativeTabs() 
}
class ModDamageTypes {
  + ModDamageTypes() 
}
class ModDataComponents {
  + ModDataComponents() 
}
class ModEntities {
  + ModEntities() 
}
class ModEntitiesRender {
  + ModEntitiesRender() 
  + onEntityRenderers(RegisterRenderers) void
}
class ModItems {
  + ModItems() 
}
class ModPainting {
  + ModPainting() 
}
class ModParticles {
  + ModParticles() 
  + createOptions(MapCodec~T~, StreamCodec~RegistryFriendlyByteBuf, T~) ParticleType~T~
}
class ModRecipe {
  + ModRecipe() 
}
class ModSerializers {
  + ModSerializers() 
}
class ModSounds {
  + ModSounds() 
}
class ModSyncedEntityData {
  + ModSyncedEntityData() 
  + init() void
  - registerEntityData(SyncedDataKey~Entity, ?~) void
}
class ModelAdditionalMagazineListener {
  + ModelAdditionalMagazineListener(AnimationListener, BedrockGunModel) 
  + update(float[], boolean) void
  + initialValue() float[]
   ChannelType type
}
class ModelRendererWrapper {
  + ModelRendererWrapper(BedrockPart) 
  - BedrockPart modelRenderer
  + addOffsetX(float) void
  + render(PoseStack, ItemDisplayContext, VertexConsumer, int, int) void
  + addOffsetY(float) void
  + addOffsetZ(float) void
   float initRotateAngleZ
   Quaternionf additionalQuaternion
   float initRotateAngleY
   float initRotateAngleX
   float rotationPointZ
   float rotationPointY
   float rotationPointX
   boolean hidden
   float rotateAngleY
   float rotateAngleX
   float scaleZ
   float rotateAngleZ
   float scaleY
   float scaleX
   float offsetY
   float offsetZ
   float offsetX
   BedrockPart modelRenderer
}
class ModelRotateListener {
  + ModelRotateListener(ModelRendererWrapper) 
  + initialValue() float[]
  + update(float[], boolean) void
   ChannelType type
}
class ModelScaleListener {
  + ModelScaleListener(ModelRendererWrapper) 
  + initialValue() float[]
  + update(float[], boolean) void
   ChannelType type
}
class ModelTranslateListener {
  + ModelTranslateListener(BedrockAnimatedModel, ModelRendererWrapper, String) 
  + initialValue() float[]
  + update(float[], boolean) void
   ChannelType type
}
class ModernKineticGunItem {
  + ModernKineticGunItem() 
  - doMelee(LivingEntity, float, float, float, float, float, List~EffectData~) void
  + tickReload(ShooterDataHolder, ItemStack, LivingEntity) ReloadState
  + getLevel(int) int
  - checkFunction(LuaValue) LuaFunction?
  + startBolt(ShooterDataHolder, ItemStack, LivingEntity) boolean
  - defaultTickHeat(long, ItemStack) void
  + doBulletSpread(ShooterDataHolder, ItemStack, LivingEntity, Projectile, int, float, float, float, float) void
  + tickHeat(ShooterDataHolder, ItemStack, LivingEntity) void
  + startReload(ShooterDataHolder, ItemStack, LivingEntity) boolean
  + tickNormal(IGun, ItemStack, GunHeatData, long) void
  - getMeleeData(ResourceLocation) MeleeData?
  + tickLocked(IGun, ItemStack, GunHeatData, long) void
  + shoot(ShooterDataHolder, ItemStack, Supplier~Float~, Supplier~Float~, LivingEntity) void
  - doPerLivingHurt(LivingEntity, LivingEntity, float, float, List~EffectData~) void
  - defaultTickBolt(ModernKineticGunScriptAPI) boolean
  + getExp(int) int
  + melee(ShooterDataHolder, LivingEntity, ItemStack) void
  - defaultReloadFinishing(ModernKineticGunScriptAPI, boolean) void
  - defaultTickReload(ModernKineticGunScriptAPI) ReloadState
  + interruptReload(ShooterDataHolder, ItemStack, LivingEntity) void
  + tickBolt(ShooterDataHolder, ItemStack, LivingEntity) boolean
  + fireSelect(ShooterDataHolder, ItemStack) void
   int maxLevel
}
class ModernKineticGunScriptAPI {
  + ModernKineticGunScriptAPI() 
  - LivingEntity shooter
  - CommonGunIndex gunIndex
  - AbstractGunItem abstractGunItem
  - ItemStack itemStack
  - ShooterDataHolder dataHolder
  - Supplier~Float~ pitchSupplier
  - Supplier~Float~ yawSupplier
  + getAttachment(String) String
  + adjustReloadTime(long) void
  + handleShootHeat() void
  + hasAmmoToConsume() boolean
  + shootOnce(boolean) void
  + reduceAmmoOnce() boolean
  + calcHeatReduction(long) float
  + safeAsyncTask(LuaValue, long, long, int) void
  + hasAmmoInBarrel() boolean
  - initGunItem() void
  + adjustShootInterval(long) void
  + cacheScriptData(LuaValue) void
  + consumeAmmoFromPlayer(int) int
  + hasHeatData() boolean
  + putAmmoInMagazine(int) int
  + useInventoryAmmo() boolean
  + adjustBoltTime(long) void
  - checkFunction(LuaValue) LuaFunction?
  + removeAmmoFromMagazine(int) int
   int reloadStateType
   int fireMode
   float heatMinRpm
   long coolingDelay
   int ammoAmount
   LivingEntity shooter
   CommonGunIndex gunIndex
   long overheatTime
   float heatMaxInaccuracy
   float aimingProgress
   float heatMax
   LuaTable scriptParams
   ShooterDataHolder dataHolder
   Supplier~Float~ pitchSupplier
   long currentTimestamp
   float heatMaxRpm
   LuaNbtAccessor nbt
   ItemStack itemStack
   boolean reloadingNeedConsumeAmmo
   int magExtentLevel
   boolean shootingNeedConsumeAmmo
   long lastShootTimestamp
   long boltTime
   boolean ammoInBarrel
   AbstractGunItem abstractGunItem
   LuaEntityAccessor entityUtil
   int boltByInt
   int maxAmmoCount
   float heatAmount
   int neededAmmoAmount
   int ammoCountInMagazine
   float heatPerShot
   boolean overheatLocked
   long reloadTime
   long shootInterval
   float heatMinInaccuracy
   Bolt bolt
   LuaValue cachedScriptData
   Supplier~Float~ yawSupplier
}
class Modifier {
  + Modifier() 
  - double addend
  - String? function
  - double percent
  - double multiplier
   String? function
   double multiplier
   double percent
   double addend
}
class MouseHandlerMixin {
  + MouseHandlerMixin() 
  - getCrawlPitch(LocalPlayer, double, double) double
  + reduceSensitivity(LocalPlayer, double, double, Operation~Void~) void
}
class MoveSpeed {
  + MoveSpeed() 
  + MoveSpeed(float, float, float) 
  - float reloadMultiplier
  - float baseMultiplier
  - float aimMultiplier
  + of(MoveSpeed, List~MoveSpeed~) MoveSpeed
   float reloadMultiplier
   float aimMultiplier
   float baseMultiplier
}
class MuzzleFlash {
  + MuzzleFlash() 
  # ResourceLocation texture
  - float scale
   ResourceLocation texture
   float scale
}
class MuzzleFlashRender {
  + MuzzleFlashRender(BedrockGunModel) 
  - renderMuzzleFlash(GunDisplayInstance, PoseStack, BedrockModel, long) void
  - doRender(int, int, MuzzleFlash, long) void
  + onShoot() void
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class MyIterator {
  + MyIterator(int) 
  + next() Integer
  - findNextNotEmptyList(int) int
  - checkForModifications() void
  + hasNext() boolean
}
class NetworkHandler {
  + NetworkHandler() 
  + sendToDimension(Object, Entity) void
  + sendToTrackingEntity(Object, Entity) void
  + registerHandshakeMessage(Class~T~, Function~Boolean, List~Pair~String, T~~~?) void
  + registerAcknowledge() void
  + sendToClientPlayer(Object, Player) void
  + sendToTrackingEntityAndSelf(Entity, Object) void
  + sendToAllPlayers(Object) void
  + init() void
}
class NewRecoilData {
  + NewRecoilData() 
  - Modifier yaw
  - Modifier pitch
   Modifier pitch
   Modifier yaw
}
class Node {
  + Node() 
  - String name
  - float[] matrix
  - List~Integer~ children
  - float[] scale
  - float[] rotation
  - float[] translation
  + addChildren(Integer) void
  + removeChildren(Integer) void
   String name
   float[] scale
   float[] rotation
   List~Integer~ children
   float[] translation
   float[] matrix
}
class NodeModel {
  + NodeModel() 
  - List~NodeModel~ children
  - String name
  - float[] matrix
  - float[] scale
  - float[] rotation
  - NodeModel parent
  - float[] translation
  - check(float[], int) float[]
  + addChild(NodeModel) void
   String name
   float[] scale
   float[] rotation
   NodeModel parent
   float[] translation
   float[] matrix
   List~NodeModel~ children
}
class NumberArrays {
  - NumberArrays() 
  ~ asNumbers(int[]) Number[]
  ~ asNumbers(float[]) Number[]
  ~ asNumbers(long[]) Number[]
}
class ObjectAnimation {
  # ObjectAnimation(String) 
  + ObjectAnimation(ObjectAnimation) 
  - ObjectAnimationSoundChannel? soundChannel
  - Map~String, List~ObjectAnimationChannel~~ channels
  - float maxEndTimeS
  # addChannel(ObjectAnimationChannel) void
  + applyAnimationListeners(AnimationListenerSupplier) void
  + update(boolean, float) void
   float maxEndTimeS
   Map~String, List~ObjectAnimationChannel~~ channels
   ObjectAnimationSoundChannel? soundChannel
}
class ObjectAnimationChannel {
  + ObjectAnimationChannel(ChannelType, AnimationChannelContent) 
  + ObjectAnimationChannel(ChannelType) 
  - List~AnimationListener~ listeners
  + addListener(AnimationListener) void
  + getResult(float) float[]
  + clearListeners() void
  + update(float, boolean) void
  - computeIndex(float) int
  - computeAlpha(float, int) float
  + removeListener(AnimationListener) void
   List~AnimationListener~ listeners
   float endTimeS
}
class ObjectAnimationRunner {
  + ObjectAnimationRunner(ObjectAnimation) 
  - boolean isTransitioning
  - boolean running
  # long transitionTimeNs
  - long progressNs
  - long transitionProgressNs
  - ObjectAnimationRunner? transitionTo
  - boolean pausing
  - ObjectAnimation animation
  + stopTransition() void
  + updateSoundOnly() void
  + run() void
  + reset() void
  + hold() void
  + pause() void
  - slerp(float[], float[], float, float[]) void
  - easeOutCubic(double) float
  + stop() void
  + update(boolean) void
  - lerp(float[], float[], float, float[]) void
  - updateProgress(long) void
  + transition(ObjectAnimationRunner, long) void
  - updateTransition(float, boolean) void
   ObjectAnimation animation
   boolean isTransitioning
   boolean holding
   long transitionProgressNs
   boolean stopped
   long transitionTimeNs
   boolean running
   long progressNs
   boolean pausing
   ObjectAnimationRunner? transitionTo
}
class ObjectAnimationSoundChannel {
  + ObjectAnimationSoundChannel() 
  + ObjectAnimationSoundChannel(AnimationSoundChannelContent) 
  - computeIndex(double, boolean) int
  + playSound(double, double, Entity, int, float, float) void
   double endTimeS
}
class OcularWrapper {
  + OcularWrapper(ModelRendererWrapper, boolean) 
}
class OculusCompat {
  + OculusCompat() 
  + initCompat() void
  + endBatch(BufferSource) boolean
   boolean usingRenderPack
   boolean renderShadow
}
class OculusCompatLegacy {
  + OculusCompatLegacy() 
  + endBatch(BufferSource) boolean
   boolean renderShadow
}
class OculusCompatNewly {
  + OculusCompatNewly() 
  + endBatch(BufferSource) boolean
   boolean renderShadow
}
class OldRecoilData {
  + OldRecoilData() 
  - float pitch
  - float yaw
   float yaw
   float pitch
}
class OpenGunPackDirEntry {
  + OpenGunPackDirEntry(Component) 
  + render(GuiGraphics, int, int, int, int, int, int, int, boolean, float) void
  + children() List~GuiEventListener~
  + narratables() List~NarratableEntry~
   Boolean value
   Optional~Boolean~ defaultValue
}
class OptifineCompat {
  + OptifineCompat() 
  + isClassFound(String) boolean
   boolean optifineInstalled
}
class OtherClothConfig {
  + OtherClothConfig() 
  + init(ConfigBuilder, ConfigEntryBuilder) void
}
class OtherConfig {
  + OtherConfig() 
  + init(Builder) void
  - serverConfig(Builder) void
}
class OutputGroupName {
<<enumeration>>
  - OutputGroupName(String) 
  - String name
  + valueOf(String) OutputGroupName
  + values() OutputGroupName[]
   String name
}
class OverwriteCommand {
  + OverwriteCommand() 
  + get() LiteralArgumentBuilder~CommandSourceStack~
  - setOverwrite(CommandContext~CommandSourceStack~) int
}
class PackConvertor {
  + PackConvertor() 
  - msg(CommandSourceStack, Component) void
  - pre(Component) Component
  + convert(CommandSourceStack) void
  + fromZipFile(File) LegacyPack?
}
class PackInfo {
  + PackInfo() 
  - String version
  - String url
  - List~String~ authors
  - String license
  - String name
  - String date
  - String description
   String name
   String description
   String version
   String url
   List~String~ authors
   String date
   String license
}
class PackInfoManager {
  + PackInfoManager() 
  # prepare(ResourceManager, ProfilerFiller) Map~String, PackInfo~
  # apply(Map~String, PackInfo~, ResourceManager, ProfilerFiller) void
  + getData(String) PackInfo
}
class PackMeta {
  + PackMeta(String, HashMap~String, String~) 
  - String name
  - HashMap~String, String~ dependencies
   String name
   HashMap~String, String~ dependencies
}
class PairSerializer {
  + PairSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) Pair~Float, Float~
  + serialize(Pair~Float, Float~, Type, JsonSerializationContext) JsonElement
}
class PapiManager {
  + PapiManager() 
  + getTextShow(String, ItemStack) String
  + addPapi(String, Function~ItemStack, String~) void
}
class ParameterizedCache~T~ {
  + ParameterizedCache(List~Modifier~, T) 
  - T defaultValue
  + eval(double) double
  + of(List~Modifier~, T) ParameterizedCache~T~
  + eval(double, double, double, double) double
  + of(T) ParameterizedCache~T~
   T defaultValue
}
class ParameterizedCachePair~L, R~ {
  - ParameterizedCachePair(Pair~ParameterizedCache~L~, ParameterizedCache~R~~) 
  + of(L, R) ParameterizedCachePair~L, R~
  + left() ParameterizedCache~L~
  + right() ParameterizedCache~R~
  + of(List~Modifier~, List~Modifier~, L, R) ParameterizedCachePair~L, R~
}
class ParticleFactoryRegistry {
  + ParticleFactoryRegistry() 
  + onRegisterParticleFactory(RegisterParticleProvidersEvent) void
}
class PathHandler {
  + PathHandler() 
  + getPath(Path, Path, String) String
}
class PerlinNoise {
  + PerlinNoise(float, float, long) 
  - boolean reverse
  - easeInterpolate(double) double
   boolean reverse
   float value
}
class PierceJsonProperty {
  + PierceJsonProperty(Modifier) 
  + initComponents() void
}
class PierceModifier {
  + PierceModifier() 
  + String ID
  + initCache(ItemStack, GunData) CacheValue~Integer~
  + eval(List~Modifier~, CacheValue~Integer~) void
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + readJson(String) JsonProperty~Modifier~
   String ID
   int diagramsDataSize
}
class PlayGunSoundEvent {
  + PlayGunSoundEvent() 
  + onPlaySoundSource(PlaySoundSourceEvent) void
}
class PlayType {
<<enumeration>>
  + PlayType() 
  + values() PlayType[]
  + valueOf(String) PlayType
}
class PlayerAnimatorAssetManager {
  + PlayerAnimatorAssetManager() 
  + get() PlayerAnimatorAssetManager
  ~ getAnimations(ResourceLocation, String) Optional~KeyframeAnimation~
  + clearAll() void
  ~ putAnimation(ResourceLocation, InputStream) void
  + containsKey(ResourceLocation) boolean
  # apply(Map~ResourceLocation, HashMap~String, KeyframeAnimation~~, ResourceManager, ProfilerFiller) void
  # prepare(ResourceManager, ProfilerFiller) Map~ResourceLocation, HashMap~String, KeyframeAnimation~~
}
class PlayerAnimatorCompat {
  + PlayerAnimatorCompat() 
  + loadAnimationFromZip(ZipFile, String) boolean
  + registerReloadListener(Consumer~PreparableReloadListener~) void
  + init() void
  + stopAllAnimation(LivingEntity, int) void
  + hasPlayerAnimator3rd(LivingEntity, GunDisplayInstance) boolean
  + loadAnimationFromFile(File) void
  + stopAllAnimation(LivingEntity) void
  + playAnimation(LivingEntity, GunDisplayInstance, float) void
  + clearAllAnimationCache() void
   boolean installed
}
class PlayerAnimatorLoader {
  + PlayerAnimatorLoader() 
  + load(ZipFile, String) boolean
  + load(File) void
}
class PlayerEnterWorld {
  + PlayerEnterWorld() 
  - pre(Component) Component
  + onPlayerEnterWorld(PlayerLoggedInEvent) void
}
class PlayerHurtByGunEvent {
  + PlayerHurtByGunEvent() 
  + onPlayerHurtByGun(Post) void
}
class PlayerModelMixin~T~ {
  + PlayerModelMixin(ModelPart) 
  - tacz$resetAll(ModelPart) void
  - setRotationAnglesTail(T, float, float, float, float, float, CallbackInfo) void
}
class PlayerNamePapi {
  + PlayerNamePapi() 
  + apply(ItemStack) String
}
class PlayerRespawnEvent {
  + PlayerRespawnEvent() 
  + onPlayerRespawn(PlayerRespawnEvent) void
}
class Post {
  + Post(Entity, Entity?, LivingEntity?, ResourceLocation, ResourceLocation, float, Pair~DamageSource, DamageSource~?, boolean, float, LogicalSide) 
}
class Pre {
  + Pre(Entity, Entity?, LivingEntity?, ResourceLocation, ResourceLocation, float, Pair~DamageSource, DamageSource~?, boolean, float, LogicalSide) 
  + setDamageSource(GunDamageSourcePart, DamageSource) void
   float headshotMultiplier
   ResourceLocation gunId
   Entity hurtEntity
   boolean headshot
   LivingEntity attacker
   float baseAmount
}
class PreLoadConfig {
  + PreLoadConfig() 
  - ModConfigSpec spec
  + load(Path) void
   PreLoadModConfig modConfig
   ModConfigSpec spec
}
class PreLoadModConfig {
  + PreLoadModConfig(Type, ModConfigSpec, ModContainer, String) 
  - Type type
  - String fileName
  - CommentedConfig configData
  - ModConfigSpec spec
  + save() void
   Type type
   ModConfigSpec spec
   Path fullPath
   CommentedConfig configData
   ModContainer modContainer
   String fileName
}
class PreventGunClick {
  + PreventGunClick() 
  + onLeftClickBlock(LeftClickBlock) void
}
class PreventsHotbarEvent {
  + PreventsHotbarEvent() 
  + onRenderHotbarEvent(Pre) void
}
class ProjectileExplosion {
  + ProjectileExplosion(Level, Entity, Entity, DamageSource?, ExplosionDamageCalculator?, double, double, double, float, float, boolean, BlockInteraction) 
  + explode() void
}
class Provider {
  + Provider() 
  + createParticle(BulletHoleOption, ClientLevel, double, double, double, double, double, double) BulletHoleParticle?
}
class RawAnimationStructure {
  + RawAnimationStructure() 
  - List~Accessor~ accessors
  - List~Animation~ animations
  - List~BufferView~ bufferViews
  - List~Buffer~ buffers
  - List~Node~ nodes
   List~Buffer~ buffers
   List~Node~ nodes
   List~Animation~ animations
   List~Accessor~ accessors
   List~BufferView~ bufferViews
}
class RawGunTableResult {
  + RawGunTableResult(String, ResourceLocation, int) 
  - CompoundTag? nbt
  - GunResult? extraData
  + init(RawGunTableResult) GunSmithTableResult
   GunResult? extraData
   GunSmithTableResult attachmentStack
   CompoundTag? nbt
   GunSmithTableResult gunStack
   GunSmithTableResult ammoStack
}
class RecipeFilter {
  + RecipeFilter() 
  + merge(RecipeFilter) void
  + contains(ResourceLocation) boolean
  + filter(List~T~, Function~T, ResourceLocation~) List~T~
  + filter(List~ResourceLocation~) List~ResourceLocation~
}
class RecipeFilterManager {
  + RecipeFilterManager() 
  # Map~ResourceLocation, String~ networkCache
  - Map~ResourceLocation, RecipeFilter~ filters
  # apply(Map~ResourceLocation, List~JsonElement~~, ResourceManager, ProfilerFiller) void
  + getFilter(ResourceLocation) RecipeFilter
  - parseJson(JsonElement) RecipeFilter
  # prepare(ResourceManager, ProfilerFiller) Map~ResourceLocation, List~JsonElement~~
   Map~ResourceLocation, RecipeFilter~ filters
   DataType type
   Map~ResourceLocation, String~ networkCache
}
class RecoilJsonProperty {
  + RecoilJsonProperty(Pair~Modifier, Modifier~) 
  + initComponents() void
}
class RecoilModifier {
  + RecoilModifier() 
  + String ID
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + eval(List~Pair~Modifier, Modifier~~, CacheValue~ParameterizedCachePair~Float, Float~~) void
  - getMaxInGunRecoilKeyFrame(GunRecoilKeyFrame[]) float
  + initCache(ItemStack, GunData) CacheValue~ParameterizedCachePair~Float, Float~~
  + readJson(String) JsonProperty~Pair~Modifier, Modifier~~
   String ID
   String optionalFields
   int diagramsDataSize
}
class RefitKey {
  + RefitKey() 
  + onRefitPress(Key) void
}
class RefitTransform {
  + RefitTransform() 
  - AttachmentType oldTransformType
  - AttachmentType currentTransformType
  + changeRefitScreenView(AttachmentType) boolean
  + init() void
   AttachmentType currentTransformType
   float openingProgress
   float transformProgress
   AttachmentType oldTransformType
}
class RefitTurnPageButton {
  + RefitTurnPageButton(int, int, boolean, OnPress) 
  + renderWidget(GuiGraphics, int, int, float) void
  + renderTooltip(Consumer~List~Component~~) void
}
class RefitUnloadButton {
  + RefitUnloadButton(int, int, OnPress) 
  + renderTooltip(Consumer~List~Component~~) void
  + renderWidget(GuiGraphics, int, int, float) void
}
class RefreshClonePlayerDataEvent {
  + RefreshClonePlayerDataEvent() 
  + onClientPlayerClone(Clone) void
}
class RegexFilter~T~ {
  + RegexFilter(String) 
  - Pattern pattern
  + test(T) boolean
   Pattern pattern
}
class ReloadCommand {
  + ReloadCommand() 
  - reloadAllPack(CommandContext~CommandSourceStack~) int
  + reloadClient() void
  + get() LiteralArgumentBuilder~CommandSourceStack~
}
class ReloadKey {
  + ReloadKey() 
  + onReloadControllerPress(boolean) boolean
  + onReloadPress(Key) void
  + autoReload(PlayerTickEvent) void
}
class ReloadResourceEvent {
  + ReloadResourceEvent() 
}
class ReloadState {
  + ReloadState(ReloadState) 
  + ReloadState() 
  # StateType stateType
  # long countDown
  + equals(Object) boolean
   StateType stateType
   long countDown
}
class RenderClothConfig {
  + RenderClothConfig() 
  + init(ConfigBuilder, ConfigEntryBuilder) void
}
class RenderConfig {
  + RenderConfig() 
  + init(Builder) void
}
class RenderCrosshairEvent {
  + RenderCrosshairEvent() 
  + markKillTimestamp() void
  - renderHitMarker(GuiGraphics, Window) void
  + markHitTimestamp() void
  + markHeadShotTimestamp() void
  + onRenderTick(Pre) void
  + onRenderOverlay(Pre) void
  - renderCrosshair(GuiGraphics, Window) void
}
class RenderDistance {
  + RenderDistance() 
  + inRenderHighPolyModelDistance(PoseStack) boolean
  + markGuiRenderTimestamp() void
   boolean guiRender
}
class RenderHeadShotAABB {
  + RenderHeadShotAABB() 
  + onRenderEntity(Post~?, ?~) void
}
class RenderHelper {
  + RenderHelper() 
  + enableItemEntityStencilTest() void
  - innerBlit(Matrix4f, float, float, float, float, float, float, float, float, float) void
  - innerBlit(PoseStack, float, float, float, float, float, float, float, float, float, float, float) void
  - blit(PoseStack, float, float, float, float, float, float, float, float, float, float) void
  + disableItemEntityStencilTest() void
  + renderFirstPersonArm(LocalPlayer, HumanoidArm, PoseStack, int) void
  + blit(PoseStack, float, float, float, float, float, float, float, float) void
}
class RenderItemInHandBobEvent {
  + RenderItemInHandBobEvent() 
}
class RenderItemInHandBobHurtEventJS {
  + RenderItemInHandBobHurtEventJS(BobHurt) 
}
class RenderItemInHandBobViewEventJS {
  + RenderItemInHandBobViewEventJS(BobView) 
}
class RenderLevelBobEvent {
  + RenderLevelBobEvent() 
}
class RenderLevelBobHurtEventJS {
  + RenderLevelBobHurtEventJS(BobHurt) 
}
class RenderLevelBobViewEventJS {
  + RenderLevelBobViewEventJS(BobView) 
}
class ResourceManager {
  + ResourceManager() 
  + registerExtraGunPack(Class~?~, String) void
  + registerExportResource(Class~?~, String) void
}
class ResourceScanner {
  + ResourceScanner() 
  + scanDirectoryAll(ResourceManager, FileToIdConverter, Gson) Map~ResourceLocation, List~JsonElement~~
  + scanDirectory(ResourceManager, String, Gson) Map~ResourceLocation, JsonElement~
  + scanDirectory(ResourceManager, FileToIdConverter, Gson) Map~ResourceLocation, JsonElement~
}
class ResultButton {
  + ResultButton(int, int, ItemStack, OnPress) 
  - boolean isSelected
  # renderWidget(GuiGraphics, int, int, float) void
  + onPress() void
  + renderTooltips(Consumer~ItemStack~) void
   boolean isSelected
}
class RightHandRender {
  + RightHandRender(BedrockAnimatedModel) 
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class RootCommand {
  + RootCommand() 
  + register(CommandDispatcher~CommandSourceStack~) void
}
class RpmJsonProperty {
  + RpmJsonProperty(Modifier) 
  + initComponents() void
}
class RpmModifier {
  + RpmModifier() 
  + String ID
  + readJson(String) JsonProperty~Modifier~
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
  + initCache(ItemStack, GunData) CacheValue~Integer~
  + eval(List~Modifier~, CacheValue~Integer~) void
   String ID
   int diagramsDataSize
}
class SLerp {
  + SLerp() 
  + compile(AnimationChannelContent) void
  + clone() SLerp
  + interpolate(int, int, float) float[]
}
class Sampler {
  + Sampler(Object, Interpolation, Object) 
  + input() Object
  + interpolation() Interpolation
  + output() Object
}
class ScriptManager {
  + ScriptManager(FileToIdConverter, List~LuaLibrary~) 
  - getModuleName(ResourceLocation) String
  # prepare(ResourceManager, ProfilerFiller) List~Entry~String, Supplier~LuaTable~~~
  + getScript(ResourceLocation) LuaTable
  - initGlobals() void
  # apply(List~Entry~String, Supplier~LuaTable~~~, ResourceManager, ProfilerFiller) void
  - wrapLoadingFunction(ResourceLocation, Resource) Entry~String, Supplier~LuaTable~~
}
class SecondOrderDynamics {
  + SecondOrderDynamics(float, float, float, float) 
  - update() void
  + stop() void
  + update(float) float
  + get() float
}
class Serializer {
  + Serializer() 
  + serialize(CommonTransformObject, Type, JsonSerializationContext) JsonElement
  + deserialize(JsonElement, Type, JsonDeserializationContext) CommonTransformObject
}
class Serializers {
  + Serializers() 
}
class ServerConfig {
  + ServerConfig() 
  + init() ModConfigSpec
}
class ServerGamePacketListenerImplMixin {
  + ServerGamePacketListenerImplMixin() 
  + cancelSprintCommand(ServerPlayer, boolean, Operation~Void~) void
}
class ServerMessageCraft {
  + ServerMessageCraft(int) 
  + decode(FriendlyByteBuf) ServerMessageCraft
  + handle(ServerMessageCraft, Supplier~Context~) void
  - updateScreen(int) void
  + encode(ServerMessageCraft, FriendlyByteBuf) void
}
class ServerMessageGunDraw {
  + ServerMessageGunDraw(int, ItemStack, ItemStack) 
  + encode(ServerMessageGunDraw, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunDraw
  - doClientEvent(ServerMessageGunDraw) void
  + handle(ServerMessageGunDraw, IPayloadContext) void
}
class ServerMessageGunFire {
  + ServerMessageGunFire(int, ItemStack) 
  + handle(ServerMessageGunFire, IPayloadContext) void
  - doClientEvent(ServerMessageGunFire) void
  + encode(ServerMessageGunFire, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunFire
}
class ServerMessageGunFireSelect {
  + ServerMessageGunFireSelect(int, ItemStack) 
  - doClientEvent(ServerMessageGunFireSelect) void
  + handle(ServerMessageGunFireSelect, Supplier~Context~) void
  + decode(FriendlyByteBuf) ServerMessageGunFireSelect
  + encode(ServerMessageGunFireSelect, FriendlyByteBuf) void
}
class ServerMessageGunHurt {
  + ServerMessageGunHurt(int, int, int, ResourceLocation, ResourceLocation, float, boolean, float) 
  - onHurt(ServerMessageGunHurt) void
  + handle(ServerMessageGunHurt, Supplier~Context~) void
  + encode(ServerMessageGunHurt, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunHurt
}
class ServerMessageGunKill {
  + ServerMessageGunKill(int, int, int, ResourceLocation, ResourceLocation, float, boolean, float) 
  - onKill(ServerMessageGunKill) void
  + encode(ServerMessageGunKill, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunKill
  + handle(ServerMessageGunKill, Supplier~Context~) void
}
class ServerMessageGunMelee {
  + ServerMessageGunMelee(int, ItemStack) 
  + encode(ServerMessageGunMelee, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunMelee
  + handle(ServerMessageGunMelee, Supplier~Context~) void
  - doClientEvent(ServerMessageGunMelee) void
}
class ServerMessageGunReload {
  + ServerMessageGunReload(int, ItemStack) 
  + handle(ServerMessageGunReload, Supplier~Context~) void
  + encode(ServerMessageGunReload, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunReload
  - doClientEvent(ServerMessageGunReload) void
}
class ServerMessageGunShoot {
  + ServerMessageGunShoot(int, ItemStack) 
  + encode(ServerMessageGunShoot, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageGunShoot
  - doClientEvent(ServerMessageGunShoot) void
  + handle(ServerMessageGunShoot, Supplier~Context~) void
}
class ServerMessageLevelUp {
  + ServerMessageLevelUp(ItemStack, int) 
  + handle(ServerMessageLevelUp, IPayloadContext) void
  + gun() ItemStack
  + type() Type~CustomPacketPayload~
  - onLevelUp(ServerMessageLevelUp) void
  + level() int
}
class ServerMessageRefreshRefitScreen {
  + ServerMessageRefreshRefitScreen() 
  + handle(ServerMessageRefreshRefitScreen, Supplier~Context~) void
  + encode(ServerMessageRefreshRefitScreen, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageRefreshRefitScreen
  - updateScreen() void
}
class ServerMessageSound {
  + ServerMessageSound(int, ResourceLocation, ResourceLocation, String, float, float, int) 
  + ServerMessageSound(int, ResourceLocation, String, float, float, int) 
  - ResourceLocation gunId
  - ResourceLocation gunDisplayId
  - float volume
  - int entityId
  - String soundName
  - float pitch
  - int distance
  + decode(FriendlyByteBuf) ServerMessageSound
  + encode(ServerMessageSound, FriendlyByteBuf) void
  + handle(ServerMessageSound, Supplier~Context~) void
   ResourceLocation gunId
   float pitch
   ResourceLocation gunDisplayId
   float volume
   int distance
   int entityId
   String soundName
}
class ServerMessageSwapItem {
  + ServerMessageSwapItem() 
  + handle(ServerMessageSwapItem, IPayloadContext) void
  + decode(FriendlyByteBuf) ServerMessageSwapItem
  + encode(ServerMessageSwapItem, FriendlyByteBuf) void
}
class ServerMessageSyncBaseTimestamp {
  + ServerMessageSyncBaseTimestamp() 
  - updateBaseTimestamp(long) void
  + handle(ServerMessageSyncBaseTimestamp, Supplier~Context~) void
  + encode(ServerMessageSyncBaseTimestamp, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageSyncBaseTimestamp
}
class ServerMessageSyncGunPack {
  + ServerMessageSyncGunPack(Map~DataType, Map~ResourceLocation, String~~) 
  - Map~DataType, Map~ResourceLocation, String~~ cache
  + decode(FriendlyByteBuf) ServerMessageSyncGunPack
  - doSync(ServerMessageSyncGunPack) void
  + encode(ServerMessageSyncGunPack, FriendlyByteBuf) void
  + handle(ServerMessageSyncGunPack, Supplier~Context~) void
   Map~DataType, Map~ResourceLocation, String~~ cache
}
class ServerMessageSyncedEntityDataMapping {
  + ServerMessageSyncedEntityDataMapping() 
  - ServerMessageSyncedEntityDataMapping(Map~ResourceLocation, List~Pair~ResourceLocation, Integer~~~) 
  - Map~ResourceLocation, List~Pair~ResourceLocation, Integer~~~ keyMap
  + encode(ServerMessageSyncedEntityDataMapping, FriendlyByteBuf) void
  + handle(ServerMessageSyncedEntityDataMapping, Supplier~Context~) void
  + decode(FriendlyByteBuf) ServerMessageSyncedEntityDataMapping
   Map~ResourceLocation, List~Pair~ResourceLocation, Integer~~~ keyMap
}
class ServerMessageUpdateEntityData {
  + ServerMessageUpdateEntityData(int, List~DataEntry~?, ?~~) 
  + encode(ServerMessageUpdateEntityData, FriendlyByteBuf) void
  + decode(FriendlyByteBuf) ServerMessageUpdateEntityData
  + handle(ServerMessageUpdateEntityData, Supplier~Context~) void
  - onHandle(ServerMessageUpdateEntityData) void
}
class ServerPlayNetHandlerMixin {
  + ServerPlayNetHandlerMixin() 
  + applySwapOffhandDraw(ServerboundPlayerActionPacket, CallbackInfo) void
}
class ServerPlayerMixin {
  + ServerPlayerMixin() 
  + initialGunOperateData(ServerPlayer, boolean, CallbackInfo) void
}
class ServerTickHandler {
  + ServerTickHandler() 
  + onServerTick(Post) void
}
class ShellDisplay {
  + ShellDisplay() 
  # ResourceLocation modelTexture
  - ResourceLocation modelLocation
   ResourceLocation modelLocation
   ResourceLocation modelTexture
}
class ShellEjection {
  + ShellEjection() 
  - Vector3f randomVelocity
  - Vector3f acceleration
  - Vector3f initialVelocity
  - float livingTime
  - Vector3f angularVelocity
   Vector3f initialVelocity
   Vector3f angularVelocity
   Vector3f acceleration
   Vector3f randomVelocity
   float livingTime
}
class ShellRender {
  + ShellRender(BedrockGunModel) 
  - renderSingleShell(ItemDisplayContext, int, int, Data, Vector3f, Vector3f, Vector3f, BedrockAmmoModel, ResourceLocation) void
  + addShell(Vector3f) void
  - renderShell(GunDisplayInstance, GunData, PoseStack, BedrockGunModel) void
  - checkShellQueue(long) void
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class ShootKey {
  + ShootKey() 
  + autoShoot(ClientTickEvent) void
  + semiShoot(Post) void
  + autoShootController() boolean
  + semiShootController(boolean) boolean
}
class ShootResult {
<<enumeration>>
  + ShootResult() 
  + values() ShootResult[]
  + valueOf(String) ShootResult
}
class ShooterDataHolder {
  + ShooterDataHolder() 
  + initialData() void
}
class ShoulderSurfingCompat {
  + ShoulderSurfingCompat() 
  + showCrosshair() boolean
  + init() void
}
class ShoulderSurfingCompatInner {
  + ShoulderSurfingCompatInner() 
  + showCrosshair() boolean
}
class Silence {
  + Silence() 
  - int distanceAddend
  - Modifier? distance
  - boolean useSilenceSound
   Modifier? distance
   int distanceAddend
   boolean useSilenceSound
}
class SilenceJsonProperty {
  + SilenceJsonProperty(Pair~Modifier, Boolean~) 
  + initComponents() void
}
class SilenceModifier {
  + SilenceModifier() 
  + String ID
  + readJson(String) SilenceJsonProperty
  + initCache(ItemStack, GunData) CacheValue~Pair~Integer, Boolean~~
  + eval(List~Pair~Modifier, Boolean~~, CacheValue~Pair~Integer, Boolean~~) void
   String ID
}
class SlotModel {
  + SlotModel(boolean) 
  + SlotModel() 
  + renderToBuffer(PoseStack, VertexConsumer, int, int, float, float, float, float) void
  + setupAnim(Entity, float, float, float, float, float) void
}
class SoundAssetsManager {
  + SoundAssetsManager() 
  # prepare(ResourceManager, ProfilerFiller) Map~ResourceLocation, SoundData~
  # apply(Map~ResourceLocation, SoundData~, ResourceManager, ProfilerFiller) void
  + getData(ResourceLocation) SoundData
}
class SoundData {
  + SoundData(ByteBuffer, AudioFormat) 
  + byteBuffer() ByteBuffer
  + audioFormat() AudioFormat
}
class SoundEffectKeyframes {
  + SoundEffectKeyframes(Double2ObjectRBTreeMap~ResourceLocation~) 
  - Double2ObjectRBTreeMap~ResourceLocation~ keyframes
   Double2ObjectRBTreeMap~ResourceLocation~ keyframes
}
class SoundEffectKeyframesSerializer {
  + SoundEffectKeyframesSerializer() 
  + deserialize(JsonElement, Type, JsonDeserializationContext) SoundEffectKeyframes
}
class SoundManager {
  + SoundManager() 
  + sendSoundToNearby(LivingEntity, int, ResourceLocation, String, float, float) void
  + sendSoundToNearby(LivingEntity, int, ResourceLocation, ResourceLocation, String, float, float) void
}
class SoundPlayManager {
  + SoundPlayManager() 
  + stopPlayGunSound(GunDisplayInstance, String) void
  + playerRefitSound(ItemStack, LocalPlayer, String) void
  + playFireSelectSound(LivingEntity, GunDisplayInstance) void
  + playMessageSound(ServerMessageSound) void
  + resetDryFireSound() void
  + playPutAwaySound(LivingEntity, GunDisplayInstance) void
  + playShootSound(LivingEntity, GunDisplayInstance, GunData) void
  + playDrawSound(LivingEntity, GunDisplayInstance) void
  + stopPlayGunSound() void
  + playInspectSound(LivingEntity, GunDisplayInstance, boolean) void
  + playBoltSound(LivingEntity, GunDisplayInstance) void
  + playClientSound(Entity, ResourceLocation?, float, float, int, boolean) GunSoundInstance
  + playMeleeStockSound(LivingEntity, GunDisplayInstance) void
  + playFleshHitSound(LivingEntity, GunDisplayInstance) void
  + playSilenceSound(LivingEntity, GunDisplayInstance, GunData) void
  + playMeleeBayonetSound(LivingEntity, GunDisplayInstance) void
  + playClientSound(Entity, ResourceLocation?, float, float, int) GunSoundInstance
  + playMeleePushSound(LivingEntity, GunDisplayInstance) void
  + playReloadSound(LivingEntity, GunDisplayInstance, boolean) void
  + playKillSound(LivingEntity, GunDisplayInstance) void
  + playDryFireSound(LivingEntity, GunDisplayInstance) void
  + playHeadHitSound(LivingEntity, GunDisplayInstance) void
}
class Sources {
  + Sources() 
  - getHolder(RegistryAccess, ResourceKey~DamageType~) Reference~DamageType~
  + bulletVoid(RegistryAccess, Entity, Entity, boolean) DamageSource
  + bullet(RegistryAccess, Entity, Entity, boolean) DamageSource
}
class Spline {
  + Spline() 
  + interpolate(int, int, float) float[]
  + compile(AnimationChannelContent) void
  + clone() Interpolator
}
class StairBlockAccessor {
<<Interface>>
  + invokeGetModelBlock() Block
}
class StateType {
<<enumeration>>
  + StateType() 
  + valueOf(String) StateType
  + values() StateType[]
   boolean reloadingTactical
   boolean reloadFinishing
   boolean reloading
   boolean reloadingEmpty
}
class StatueBlock {
  + StatueBlock() 
  + getTicker(Level, BlockState, BlockEntityType~T~) BlockEntityTicker~T~?
  + getPistonPushReaction(BlockState) PushReaction
  + use(BlockState, Level, BlockPos, Player, InteractionHand, BlockHitResult) InteractionResult
  + updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos) BlockState
  + newBlockEntity(BlockPos, BlockState) BlockEntity?
  + setPlacedBy(Level, BlockPos, BlockState, LivingEntity?, ItemStack) void
  + onBlockExploded(BlockState, Level, BlockPos, Explosion) void
  + getRenderShape(BlockState) RenderShape
  # createBlockStateDefinition(Builder~Block, BlockState~) void
  + getStateForPlacement(BlockPlaceContext) BlockState
  + onRemove(BlockState, Level, BlockPos, BlockState, boolean) void
}
class StatueBlockEntity {
  + StatueBlockEntity(BlockPos, BlockState) 
  - ItemStack gunItem
  + load(CompoundTag) void
  + clientTick(Level, BlockPos, BlockState, StatueBlockEntity) void
  + dropItem() void
  # saveAdditional(CompoundTag) void
   ItemStack gunItem
   CompoundTag updateTag
   Packet~ClientGamePacketListener~ updatePacket
   ItemStack gun
   AABB renderBoundingBox
}
class StatueRenderer {
  + StatueRenderer(Context) 
  + shouldRender(StatueBlockEntity, Vec3) boolean
  + render(StatueBlockEntity, float, PoseStack, MultiBufferSource, int, int) void
  + shouldRenderOffScreen(StatueBlockEntity) boolean
   ResourceLocation textureLocation
   int viewDistance
   Optional~BedrockModel~ model
}
class Step {
  + Step() 
  + clone() Step
  + compile(AnimationChannelContent) void
  + interpolate(int, int, float) float[]
}
class SwapItemWithOffHand {
  + SwapItemWithOffHand() 
}
class SwapItemWithOffHandEventJS {
  + SwapItemWithOffHandEventJS(SwapItemWithOffHand) 
}
class SyncBaseTimestamp {
  + SyncBaseTimestamp() 
  + onPlayerJoinWorld(EntityJoinLevelEvent) void
}
class SyncConfig {
  + SyncConfig() 
  + interactKey(Builder) void
  + init(Builder) void
  - baseMultiplier(Builder) void
  - misc(Builder) void
}
class SyncMode {
<<enumeration>>
  - SyncMode(boolean, boolean) 
  ~ boolean self
  ~ boolean tracking
  + values() SyncMode[]
  + valueOf(String) SyncMode
   boolean self
   boolean tracking
}
class SyncedClassKey~E~ {
  + SyncedClassKey(Class~E~, ResourceLocation) 
  + hashCode() int
  + id() ResourceLocation
  + entityClass() Class~E~
  + equals(Object) boolean
}
class SyncedDataKey~E, T~ {
  + SyncedDataKey(Pair~ResourceLocation, ResourceLocation~, ResourceLocation, SyncedClassKey~E~, IDataSerializer~T~, Supplier~T~, boolean, boolean, SyncMode) 
  + hashCode() int
  + defaultValueSupplier() Supplier~T~
  + syncMode() SyncMode
  + equals(Object) boolean
  + serializer() IDataSerializer~T~
  + getValue(E) T
  + builder(SyncedClassKey~E~, IDataSerializer~T~) Builder~E, T~
  + classKey() SyncedClassKey~E~
  + setValue(E, T) void
  + id() ResourceLocation
  + persistent() boolean
  + save() boolean
  + pairKey() Pair~ResourceLocation, ResourceLocation~
}
class SyncedEntityData {
  - SyncedEntityData() 
  - boolean dirty
  - List~Entity~ dirtyEntities
  - getClassNameCapabilityCache(boolean) Map~String, Boolean~
  + updateMappings(ServerMessageSyncedEntityDataMapping) boolean
  + set(E, SyncedDataKey~?, ?~, T) void
  + getClassKey(ResourceLocation) SyncedClassKey~?~?
  + instance() SyncedEntityData
  + getKey(int) SyncedDataKey~?, ?~?
  + getKey(SyncedClassKey~?~, ResourceLocation) SyncedDataKey~?, ?~?
  - registerClassKey(SyncedClassKey~E~) void
  + registerDataKey(SyncedDataKey~E, T~) void
  + getDataHolder(Entity) DataHolder?
  + hasSyncedDataKey(Entity) boolean
  + get(E, SyncedDataKey~E, T~) T
  + getInternalId(SyncedDataKey~?, ?~) int
   List~Entity~ dirtyEntities
   boolean dirty
   Set~SyncedDataKey~?, ?~~ keys
}
class SyncedEntityDataEvent {
  + SyncedEntityDataEvent() 
  + onPlayerJoinWorld(EntityJoinLevelEvent) void
  + onServerTick(ServerTickEvent) void
  + onStartTracking(StartTracking) void
  + onPlayerClone(Clone) void
}
class TabConfig {
  + TabConfig(ResourceLocation, String, ItemStack) 
  - String name
  + id() ResourceLocation
  + icon() ItemStack
  + name() String
   Component name
}
class TableRecipe {
  + TableRecipe() 
  - GunSmithTableResult result
  - List~GunSmithTableIngredient~ materials
   GunSmithTableResult result
   List~GunSmithTableIngredient~ materials
}
class TacHitResult {
  + TacHitResult(EntityResult) 
  - boolean headshot
   boolean headshot
}
class TacPathVisitor {
  + TacPathVisitor(File, String, String, BiConsumer~ResourceLocation, Path~) 
  + visitFile(Path, BasicFileAttributes) FileVisitResult
}
class TargetBlock {
  + TargetBlock() 
  + getTicker(Level, BlockState, BlockEntityType~T~) BlockEntityTicker~T~?
  + tick(BlockState, ServerLevel, BlockPos, RandomSource) void
  + setPlacedBy(Level, BlockPos, BlockState, LivingEntity?, ItemStack) void
  + getRenderShape(BlockState) RenderShape
  + newBlockEntity(BlockPos, BlockState) BlockEntity?
  + getRedstoneStrength(BlockHitResult, boolean) int
  + isSignalSource(BlockState) boolean
  + getPistonPushReaction(BlockState) PushReaction
  # createBlockStateDefinition(Builder~Block, BlockState~) void
  + getShape(BlockState, BlockGetter, BlockPos, CollisionContext) VoxelShape
  + getStateForPlacement(BlockPlaceContext) BlockState
  + getCloneItemStack(BlockState, HitResult, BlockGetter, BlockPos, Player) ItemStack
  + canSurvive(BlockState, LevelReader, BlockPos) boolean
  + getSignal(BlockState, BlockGetter, BlockPos, Direction) int
  + onPlace(BlockState, Level, BlockPos, BlockState, boolean) void
  + onProjectileHit(Level, BlockState, BlockHitResult, Projectile) void
  + updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos) BlockState
}
class TargetBlockEntity {
  + TargetBlockEntity(BlockPos, BlockState) 
  - GameProfile? owner
  - Component? name
  + load(CompoundTag) void
  + clientTick(Level, BlockPos, BlockState, TargetBlockEntity) void
  # saveAdditional(CompoundTag) void
  + hit(Level, BlockState, BlockHitResult, boolean) void
  + refresh() void
   GameProfile? owner
   CompoundTag updateTag
   Packet~ClientGamePacketListener~ updatePacket
   Component name
   AABB renderBoundingBox
   Component? customName
}
class TargetMinecart {
  + TargetMinecart(EntityType~TargetMinecart~, Level) 
  + TargetMinecart(Level, double, double, double) 
  - GameProfile? gameProfile
  + shouldRenderAtSqrDistance(double) boolean
  + onProjectileHit(Entity, EntityHitResult, DamageSource, float) void
  + destroy(DamageSource) void
  + isInvulnerableTo(DamageSource) boolean
  + canBeRidden() boolean
   Item dropItem
   ItemStack pickResult
   float maxCartSpeedOnRail
   Type minecartType
   BlockState defaultDisplayBlockState
   GameProfile? gameProfile
}
class TargetMinecartItem {
  + TargetMinecartItem() 
  + useOn(UseOnContext) InteractionResult
}
class TargetMinecartRenderer {
  + TargetMinecartRenderer(Context) 
  + getTextureLocation(TargetMinecart) ResourceLocation
  # renderMinecartContents(TargetMinecart, float, BlockState, PoseStack, MultiBufferSource, int) void
   Optional~BedrockModel~ model
}
class TargetRenderer {
  + TargetRenderer(Context) 
  + render(TargetBlockEntity, float, PoseStack, MultiBufferSource, int, int) void
  + shouldRenderOffScreen(TargetBlockEntity) boolean
   int viewDistance
   Optional~BedrockModel~ model
}
class TextShow {
  + TextShow() 
  - boolean shadow
  - int textLight
  - Align align
  - String textKey
  - float scale
  - int colorInt
  - String colorText
   int textLight
   int colorInt
   boolean shadow
   String colorText
   String textKey
   float scale
   Align align
}
class TextShowRender {
  + TextShowRender(BedrockModel, TextShow, ItemStack) 
  + render(PoseStack, VertexConsumer, ItemDisplayContext, int, int) void
}
class ThirdPersonManager {
  + ThirdPersonManager() 
  + getAnimation(String) IThirdPersonAnimation
  + registerDefault() void
  + register(String, IThirdPersonAnimation) void
}
class ThrowableAnimationStateContext {
  + ThrowableAnimationStateContext() 
  - ItemStack currentItem
  - int usingTick
  - boolean using
   int stackCount
   ItemStack currentItem
   boolean using
   int usingTick
}
class TickAnimationEvent {
  + TickAnimationEvent() 
  + tickAnimation(ClientTickEvent) void
  + tickAnimation(RenderTickEvent) void
}
class TimelessAPI {
  + TimelessAPI() 
  + getGunId(ItemStack) Optional~ResourceLocation~
  + getGunDisplay(ResourceLocation, ResourceLocation) Optional~Object~
  + getClientBlockIndex(ResourceLocation) Optional~Object~
  + getClientAmmoIndex(ResourceLocation) Optional~Object~
  + registerThirdPersonAnimation(ResourceLocation, Object) void
  + getClientGunIndex(ResourceLocation) Optional~Object~
  + getClientAttachmentIndex(ResourceLocation) Optional~Object~
  + getCommonBlockIndex(ResourceLocation) Optional~Object~
  + isGun(ItemStack) boolean
  + getCommonAttachmentIndex(ResourceLocation) Optional~Object~
  + getCommonAmmoIndex(ResourceLocation) Optional~Object~
  + getCommonGunIndex(ResourceLocation) Optional~Object~
  + getGunDisplayId(ItemStack) Optional~ResourceLocation~
  + getGunDisplay(ItemStack) Optional~Object~
   Set~Entry~ResourceLocation, Object~~ allGuns
   RecipeType~?~? gunSmithTableRecipeType
   Set~Entry~ResourceLocation, Object~~ allAmmos
   Set~Entry~ResourceLocation, Object~~ allAttachments
   Set~Entry~ResourceLocation, Object~~ allBlocks
}
class TimelessClientEvents {
  - TimelessClientEvents() 
  + Map~Class~Event~, Consumer~Event~~ EVENT_HANDLERS
  + registerEventHandler(Class~E~, Consumer~Event~) void
   ScriptTypePredicate scriptType
   Map~Class~Event~, Consumer~Event~~ EVENT_HANDLERS
}
class TimelessCommonEvents {
  - TimelessCommonEvents() 
  + Map~Class~Event~, Consumer~Event~~ EVENT_HANDLERS
  + registerEventHandler(Class~E~, Consumer~Event~) void
   ScriptTypePredicate scriptType
   Map~Class~Event~, Consumer~Event~~ EVENT_HANDLERS
}
class TimelessForgeEventWrappers {
<<Interface>>

}
class TimelessGunSmithTableRecipeSchema {
<<Interface>>

}
class TimelessItemNbtFactory~T, S~ {
  + TimelessItemNbtFactory(T) 
  # ResourceLocation id
  # int count
  + build() ItemStack
   ResourceLocation id
   int count
}
class TimelessItemType {
<<enumeration>>
  - TimelessItemType(Item) 
  - Item item
  + values() TimelessItemType[]
  + valueOf(String) TimelessItemType
   Item item
}
class TimelessItemWrapper {
  + TimelessItemWrapper() 
  + blockItem(ItemLike, Consumer~BlockItemBuilder~) ItemStack
  + of(Item, ResourceLocation) ItemStack
  + ammoItem(Consumer~AmmoItemBuilder~) ItemStack
  + gunItem(Item, Consumer~GunNbtFactory~) ItemStack
  + attachmentItem(Item, Consumer~AttachmentNbtFactory~) ItemStack
  + ammoItem(Item, Consumer~AmmoNbtFactory~) ItemStack
  + of(ResourceLocation) ItemStack
  + attachmentItem(Consumer~AttachmentItemBuilder~) ItemStack
  + gunItem(Consumer~GunNbtFactory~) ItemStack
}
class TimelessKubeJSEventRegister {
<<Interface>>
  + init() void
  + registerEventHandler(Class~E~, Consumer~Event~) void
  + registerTimelessCommonEvent(String, Class~GunEventJS~E~~, Class~E~, Function~E, GunEventJS~E~~, boolean) EventHandler
  + registerTimelessCommonEvent(String, Class~GunEventJS~E~~, Class~E~, Function~E, GunEventJS~E~~) EventHandler
  + postKubeJSEvent(Event) boolean
  + registerTimelessEvent(String, Class~GunEventJS~E~~, Class~E~, Function~E, GunEventJS~E~~) EventHandler
  + registerTimelessEvent(String, Class~GunEventJS~E~~, Class~E~, Function~E, GunEventJS~E~~, boolean) EventHandler
  + registerEventJS(String, Class~GunEventJS~E~~, boolean) EventHandler
   ScriptTypePredicate scriptType
   Map~Class~Event~, Consumer~Event~~ eventHandlers
}
class TimelessKubeJSPlugin {
  + TimelessKubeJSPlugin() 
  + registerEvents() void
  + onItemRegister(RegisterEvent) void
  + registerGunType(String, RegistryObject~AbstractGunItem~) void
  + registerTypeWrappers(ScriptType, TypeWrappers) void
  + registerRecipeComponents(RecipeComponentFactoryRegistryEvent) void
  + registerRecipeSchemas(RegisterRecipeSchemasEvent) void
  + init() void
  + registerBindings(BindingsEvent) void
}
class TimelessRecipeJS {
  + TimelessRecipeJS() 
  - getGunTypeFromJson(JsonObject) String
  - getAmmoItemFromJson(JsonObject) ItemStack
  - getGunItemFromJson(JsonObject) ItemStack
  + writeInputItem(InputItem) JsonElement
  + writeOutputItem(OutputItem) JsonElement
  + readOutputItem(Object) OutputItem
  - getIdFromJson(JsonObject) ResourceLocation
  - getAttachmentTypeFromJson(JsonObject) String
  - getAttachmentItemFromJson(JsonObject) ItemStack
  + readInputItem(Object) InputItem
  + outputGroup(OutputGroupName) TimelessRecipeJS
  + outputGroupName(String) TimelessRecipeJS
   GunSmithTableResultInfo resultInfo
}
class TimelessServerEvents {
  - TimelessServerEvents() 
  + Map~Class~Event~, Consumer~Event~~ EVENT_HANDLERS
  + registerEventHandler(Class~E~, Consumer~Event~) void
   ScriptTypePredicate scriptType
   Map~Class~Event~, Consumer~Event~~ EVENT_HANDLERS
}
class TooltipEvent {
  + TooltipEvent() 
  + onTooltip(ItemTooltipEvent) void
  + formatTooltip(String, ResourceLocation) Component
}
class TrackArrayMismatchException {
  + TrackArrayMismatchException(String) 
  + TrackArrayMismatchException() 
}
class TransformScale {
  + TransformScale() 
  - Vector3f? thirdPerson
  - Vector3f? fixed
  - Vector3f? ground
   TransformScale gunDefault
   Vector3f? ground
   TransformScale ammoDefault
   Vector3f? thirdPerson
   Vector3f? fixed
}
class TravelToDimensionEvent {
  + TravelToDimensionEvent() 
  + onTravelToDimension(EntityTravelToDimensionEvent) void
}
class Type {
<<enumeration>>
  + Type() 
  + valueOf(String) Type
  + values() Type[]
}
class TypeButton {
  + TypeButton(int, int, ItemStack, OnPress) 
  - boolean isSelected
  # renderWidget(GuiGraphics, int, int, float) void
  + onPress() void
   boolean isSelected
}
class Vec3Serializer {
  + Vec3Serializer() 
  + serialize(Vec3, Type, JsonSerializationContext) JsonElement
  + deserialize(JsonElement, Type, JsonDeserializationContext) Vec3
}
class Vector3fSerializer {
  + Vector3fSerializer() 
  + serialize(Vector3f, Type, JsonSerializationContext) JsonElement
  + deserialize(JsonElement, Type, JsonDeserializationContext) Vector3f
}
class VersionChecker {
  + VersionChecker() 
  - checkDirVersion(File) boolean
  - modVersionAllMatch(Info) boolean
  + match(File) boolean
  - modVersionMatch(String, String) boolean
  - checkZipVersion(ZipFile) boolean
  + clearCache() void
  + noneMatch(ZipFile, Path) boolean
}
class WeightJsonProperty {
  + WeightJsonProperty(Modifier) 
  + initComponents() void
}
class WeightModifier {
  + WeightModifier() 
  + String ID
  + eval(List~Modifier~, CacheValue~Float~) void
  + initCache(ItemStack, GunData) CacheValue~Float~
  + readJson(String) JsonProperty~Modifier~
  + getPropertyDiagramsData(ItemStack, GunData, AttachmentCacheProperty) List~DiagramsData~
   String ID
   String optionalFields
   int diagramsDataSize
}
class ZoomClothConfig {
  + ZoomClothConfig() 
  + init(ConfigBuilder, ConfigEntryBuilder) void
}
class ZoomConfig {
  + ZoomConfig() 
  + init(Builder) void
}
class ZoomKey {
  + ZoomKey() 
  + onZoomControllerPress(boolean) boolean
  + onZoomKeyPress(Key) void
  - doZoomLogic() void
  + onZoomMousePress(Post) void
}

AbstractAccessorData  ..>  AccessorData 
AbstractButtonMixin  ..>  LocalPlayerDataHolder 
AbstractGunItem  ..>  AbstractGunItem 
AbstractGunItem  ..>  AllowAttachmentTagMatcher 
AbstractGunItem  ..>  AmmoItemBuilder 
AbstractGunItem  ..>  AttachmentDataUtils 
AbstractGunItem  ..>  AttachmentType 
AbstractGunItem  ..>  ClientGunIndex 
AbstractGunItem  ..>  CommonGunIndex 
AbstractGunItem  ..>  DefaultAssets 
AbstractGunItem  ..>  FeedType 
AbstractGunItem  ..>  FireMode 
AbstractGunItem  ..>  GunData 
AbstractGunItem  ..>  GunItemBuilder 
AbstractGunItem  ..>  GunItemRendererWrapper : «create»
AbstractGunItem  ..>  GunReloadData 
AbstractGunItem  ..>  GunTabType 
AbstractGunItem  ..>  GunTooltip : «create»
AbstractGunItem  ..>  IAmmo 
AbstractGunItem  ..>  IAmmoBox 
AbstractGunItem  ..>  IAnimationItem 
AbstractGunItem  ..>  IAttachment 
AbstractGunItem  ..>  IGun 
AbstractGunItem  ..>  ReloadState 
AbstractGunItem  ..>  ShooterDataHolder 
AbstractGunItem  ..>  TimelessAPI 
AbstractGunSmithTableBlock  ..>  AbstractGunSmithTableBlock 
AbstractGunSmithTableBlock  ..>  BlockItemBuilder 
AbstractGunSmithTableBlock  ..>  BlockItemDataAccessor 
AbstractGunSmithTableBlock  ..>  DefaultAssets 
AbstractGunSmithTableBlock  ..>  GunSmithTableBlockEntity : «create»
Accessor "1" *--> "sparse 1" AccessorSparse 
AccessorByteData  -->  AbstractAccessorData 
AccessorByteData  ..>  AccessorByteData 
AccessorByteData  ..>  AccessorData 
AccessorByteData  ..>  AccessorDatas 
AccessorData  ..>  AccessorByteData 
AccessorData  ..>  AccessorFloatData 
AccessorData  ..>  AccessorIntData 
AccessorData  ..>  AccessorShortData 
AccessorDatas  ..>  AccessorByteData : «create»
AccessorDatas  ..>  AccessorData 
AccessorDatas  ..>  AccessorDatas 
AccessorDatas  ..>  AccessorFloatData : «create»
AccessorDatas  ..>  AccessorIntData : «create»
AccessorDatas  ..>  AccessorModel 
AccessorDatas  ..>  AccessorShortData : «create»
AccessorDatas  ..>  BufferViewModel 
AccessorDatas  ..>  ElementType 
AccessorDatas  ..>  GltfConstants 
AccessorDatas  ..>  NumberArrays 
AccessorFloatData  -->  AbstractAccessorData 
AccessorFloatData  ..>  AccessorData 
AccessorFloatData  ..>  AccessorDatas 
AccessorFloatData  ..>  AccessorFloatData 
AccessorIntData  -->  AbstractAccessorData 
AccessorIntData  ..>  AccessorData 
AccessorIntData  ..>  AccessorDatas 
AccessorIntData  ..>  AccessorIntData 
AccessorModel "1" *--> "accessorData 1" AccessorData 
AccessorModel  ..>  AccessorDatas 
AccessorModel  ..>  AccessorModel 
AccessorModel  ..>  Accessors 
AccessorModel "1" *--> "bufferViewModel 1" BufferViewModel 
AccessorModel "1" *--> "elementType 1" ElementType 
AccessorShortData  -->  AbstractAccessorData 
AccessorShortData  ..>  AccessorData 
AccessorShortData  ..>  AccessorDatas 
AccessorShortData  ..>  AccessorShortData 
AccessorSparse "1" *--> "indices 1" AccessorSparseIndices 
AccessorSparse "1" *--> "values 1" AccessorSparseValues 
AccessorSparseUtils  ..>  AbstractAccessorData 
AccessorSparseUtils  ..>  AccessorByteData 
AccessorSparseUtils  ..>  AccessorData 
AccessorSparseUtils  ..>  AccessorFloatData 
AccessorSparseUtils  ..>  AccessorIntData 
AccessorSparseUtils  ..>  AccessorShortData 
AccessorSparseUtils  ..>  AccessorSparseUtils 
Accessors  ..>  GltfConstants 
Acknowledge  ..>  Acknowledge : «create»
Acknowledge  ..>  GunMod 
Acknowledge  ..>  IMessage 
Acknowledge  -->  LoginIndexHolder 
AdjustmentYRotModifier  ..>  AdjustmentYRotModifier : «create»
AdjustmentYRotModifier  ..>  GunDisplayInstance 
AdjustmentYRotModifier  ..>  TimelessAPI 
AdsModifier  -->  AdsJsonProperty 
AdsJsonProperty  ..>  AttachmentPropertyManager 
AdsJsonProperty  -->  JsonProperty~T~ 
AdsJsonProperty  ..>  Modifier 
AdsModifier  ..>  AdsJsonProperty : «create»
AdsModifier  ..>  AdsModifier 
AdsModifier  ..>  AttachmentCacheProperty 
AdsModifier  ..>  AttachmentPropertyManager 
AdsModifier  ..>  CacheValue~T~ : «create»
AdsModifier  ..>  CommonAssetsManager 
AdsModifier  ..>  Data 
AdsModifier  ..>  DiagramsData : «create»
AdsModifier  ..>  GunData 
AdsModifier  ..>  GunProperties 
AdsModifier  ..>  GunProperty~T~ 
AdsModifier  ..>  IAttachmentModifier~T, K~ 
AdsModifier  ..>  JsonProperty~T~ 
AdsModifier  ..>  Modifier : «create»
AimInaccuracyModifier  -->  AimInaccuracyJsonProperty 
AimInaccuracyJsonProperty  ..>  InaccuracyType 
AimInaccuracyJsonProperty  -->  JsonProperty~T~ 
AimInaccuracyJsonProperty  ..>  Modifier 
AimInaccuracyModifier  ..>  AimInaccuracyJsonProperty : «create»
AimInaccuracyModifier  ..>  AttachmentCacheProperty 
AimInaccuracyModifier  ..>  CacheValue~T~ : «create»
AimInaccuracyModifier  ..>  DiagramsData 
AimInaccuracyModifier  ..>  GunData 
AimInaccuracyModifier  ..>  GunProperties 
AimInaccuracyModifier  ..>  GunProperty~T~ 
AimInaccuracyModifier  ..>  IAttachmentModifier~T, K~ 
AimInaccuracyModifier  ..>  InaccuracyModifier 
AimInaccuracyModifier  ..>  InaccuracyType 
AimInaccuracyModifier  ..>  JsonProperty~T~ 
AimInaccuracyModifier  ..>  Modifier 
AimKey  ..>  IClientPlayerGunOperator 
AimKey  ..>  IGun 
AimKey  ..>  InputExtraCheck 
AimKey  ..>  KeyConfig 
AllowAttachmentTagMatcher  ..>  AllowAttachmentTagMatcher 
AllowAttachmentTagMatcher  ..>  CommonAssetsManager 
AllowAttachmentTagMatcher  ..>  ICommonResourceProvider 
AmmoBoxItem  ..>  AmmoBoxItem 
AmmoBoxItem  ..>  AmmoBoxItemDataAccessor 
AmmoBoxItem  ..>  AmmoBoxTooltip : «create»
AmmoBoxItem  ..>  AmmoItemBuilder 
AmmoBoxItem  ..>  DefaultAssets 
AmmoBoxItem  ..>  GunMod 
AmmoBoxItem  ..>  IAmmo 
AmmoBoxItem  ..>  IAmmoBox 
AmmoBoxItem  ..>  ModItems 
AmmoBoxItem  ..>  SyncConfig 
AmmoBoxItem  ..>  TimelessAPI 
AmmoBoxItemDataAccessor  ..>  AmmoBoxItemDataAccessor 
AmmoBoxItemDataAccessor  ..>  DefaultAssets 
AmmoBoxItemDataAccessor  -->  IAmmoBox 
AmmoBoxItemDataAccessor  ..>  IGun 
AmmoBoxItemDataAccessor  ..>  ModDataComponents 
AmmoClothConfig  ..>  AmmoConfig 
AmmoCountPapi  ..>  Bolt 
AmmoCountPapi  ..>  ClientGunIndex 
AmmoCountPapi  ..>  GunData 
AmmoCountPapi  ..>  IGun 
AmmoCountPapi  ..>  TimelessAPI 
AmmoDisplay "1" *--> "ammoEntity 1" AmmoEntityDisplay 
AmmoDisplay "1" *--> "particle 1" AmmoParticle 
AmmoDisplay "1" *--> "transform 1" AmmoTransform 
AmmoDisplay  ..>  IDisplay 
AmmoDisplay "1" *--> "shellDisplay 1" ShellDisplay 
AmmoHitBlockEventJS  ..>  AmmoHitBlockEvent 
AmmoHitBlockEventJS  ..>  AmmoHitBlockWrapper 
AmmoHitBlockEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  AmmoHitBlockEventJS 
AmmoHitBlockEventJS  ..>  TimelessForgeEventWrappers 
AmmoHitBlockWrapper  ..>  AmmoHitBlockEvent 
AmmoHitBlockWrapper  ..>  EntityKineticBullet 
AmmoHitBlockWrapper  -->  ForgeEventWrapper~E~ 
TimelessForgeEventWrappers  -->  AmmoHitBlockWrapper 
AmmoItem  ..>  AmmoItemBuilder 
AmmoItem  ..>  AmmoItemDataAccessor 
AmmoItem  ..>  AmmoItemRenderer : «create»
AmmoItem  ..>  ClientAmmoIndex 
AmmoItem  ..>  ClientAssetsManager 
AmmoItem  ..>  CommonAmmoIndex 
AmmoItem  ..>  IAmmo 
AmmoItem  ..>  PackInfo 
AmmoItem  ..>  TimelessAPI 
AmmoItemBuilder  ..>  AmmoItemBuilder : «create»
AmmoItemBuilder  ..>  DefaultAssets 
AmmoItemBuilder  ..>  IAmmo 
AmmoItemBuilder  ..>  ModItems 
AmmoItemDataAccessor  ..>  DefaultAssets 
AmmoItemDataAccessor  -->  IAmmo 
AmmoItemDataAccessor  ..>  IGun 
AmmoItemDataAccessor  ..>  ModDataComponents 
AmmoItemRenderer  ..>  AmmoItemRenderer 
AmmoItemRenderer  ..>  BedrockAmmoModel 
AmmoItemRenderer  ..>  BedrockModel 
AmmoItemRenderer  ..>  BedrockPart 
AmmoItemRenderer  ..>  IAmmo 
AmmoItemRenderer  ..>  SlotModel : «create»
AmmoItemRenderer "1" *--> "SLOT_AMMO_MODEL 1" SlotModel 
AmmoItemRenderer  ..>  TimelessAPI 
AmmoItemRenderer  ..>  TransformScale 
AmmoNbtFactory  ..>  AmmoItem 
AmmoNbtFactory  ..>  AmmoNbtFactory 
AmmoNbtFactory  ..>  IAmmo 
AmmoNbtFactory  -->  TimelessItemNbtFactory~T, S~ 
AmmoNbtFactory  ..>  TimelessItemType 
AmmoParticleSpawner  ..>  AmmoParticle 
AmmoSpeedModifier  ..>  AmmoSpeedModifier 
AmmoSpeedModifier  ..>  AttachmentCacheProperty 
AmmoSpeedModifier  ..>  AttachmentPropertyManager 
AmmoSpeedModifier  ..>  BulletData 
AmmoSpeedModifier  ..>  BulletSpeedJsonProperty : «create»
AmmoSpeedModifier  ..>  CacheValue~T~ : «create»
AmmoSpeedModifier  ..>  CommonAssetsManager 
AmmoSpeedModifier  ..>  Data 
AmmoSpeedModifier  ..>  DiagramsData : «create»
AmmoSpeedModifier  ..>  FireMode 
AmmoSpeedModifier  ..>  GunData 
AmmoSpeedModifier  ..>  GunFireModeAdjustData 
AmmoSpeedModifier  ..>  GunProperties 
AmmoSpeedModifier  ..>  GunProperty~T~ 
AmmoSpeedModifier  ..>  IAttachmentModifier~T, K~ 
AmmoSpeedModifier  ..>  IGun 
AmmoSpeedModifier  ..>  JsonProperty~T~ 
AmmoSpeedModifier  ..>  Modifier 
AmmoTransform  ..>  AmmoTransform : «create»
AmmoTransform "1" *--> "scale 1" TransformScale 
AnimateGeoItemRenderer~M, CTX~  ..>  AnimateGeoItemRenderer~M, CTX~ 
AnimateGeoItemRenderer~M, CTX~  ..>  AnimationStateMachine~T~ 
AnimateGeoItemRenderer~M, CTX~  ..>  BedrockAnimatedModel 
AnimateGeoItemRenderer~M, CTX~  ..>  BedrockModel 
AnimateGeoItemRenderer~M, CTX~  ..>  BedrockPart 
AnimateGeoItemRenderer~M, CTX~  ..>  BeforeRenderHandEvent 
AnimateGeoItemRenderer~M, CTX~  ..>  GunAnimationConstant 
AnimateGeoItemRenderer~M, CTX~  ..>  ItemAnimationStateContext 
AnimateGeoItemRenderer~M, CTX~  ..>  KeepingItemRenderer 
AnimateGeoItemRenderer~M, CTX~ "1" *--> "stateMachine 1" LuaAnimationStateMachine~T~ 
AnimateGeoItemRenderer~M, CTX~  ..>  MathUtil 
Animation "1" *--> "channels *" AnimationChannel 
Animation "1" *--> "samplers *" AnimationSampler 
AnimationBone "1" *--> "position 1" AnimationKeyframes 
AnimationChannel "1" *--> "target 1" AnimationChannelTarget 
AnimationChannelContent  ..>  AnimationChannelContent 
AnimationChannelContent "1" *--> "lerpModes *" LerpMode 
AnimationController  ..>  AnimationController 
AnimationController "1" *--> "listenerSupplier 1" AnimationListenerSupplier 
AnimationController  ..>  AnimationPlan 
AnimationController "1" *--> "prototypes *" ObjectAnimation 
AnimationController  ..>  ObjectAnimation : «create»
AnimationController  ..>  ObjectAnimationRunner : «create»
AnimationController "1" *--> "currentRunners *" ObjectAnimationRunner 
AnimationController  ..>  PlayType 
AnimationKeyframes  ..>  Keyframe 
AnimationKeyframesSerializer  ..>  AnimationKeyframes : «create»
AnimationKeyframesSerializer  ..>  AnimationKeyframesSerializer 
AnimationKeyframesSerializer  ..>  GunMod 
AnimationKeyframesSerializer  ..>  Keyframe : «create»
AnimationListener  ..>  ChannelType 
AnimationListener  ..>  ObjectAnimationChannel 
AnimationListenerSupplier  ..>  AnimationListener 
AnimationListenerSupplier  ..>  ChannelType 
AnimationListenerSupplier  ..>  ObjectAnimationChannel 
AnimationManager  ..>  AnimationManager 
AnimationManager  ..>  AnimationName 
AnimationManager  ..>  GunDisplayInstance 
AnimationManager  ..>  GunDrawEvent 
AnimationManager  ..>  GunMeleeEvent 
AnimationManager  ..>  GunReloadEvent 
AnimationManager  ..>  GunShootEvent 
AnimationManager  ..>  IGun 
AnimationManager  ..>  IGunOperator 
AnimationManager  ..>  PlayerAnimatorAssetManager 
AnimationManager  ..>  PlayerAnimatorCompat 
AnimationManager  ..>  TimelessAPI 
AnimationModel "1" *--> "channels *" Channel 
AnimationSoundChannelContent  ..>  AnimationSoundChannelContent 
AnimationState~T~  ..>  AnimationState~T~ 
AnimationState~T~  ..>  AnimationStateMachine~T~ 
AnimationStateContext  ..>  AnimationConstant 
AnimationStateContext  ..>  AnimationController 
AnimationStateContext  ..>  AnimationStateContext 
AnimationStateContext "1" *--> "stateMachine 1" AnimationStateMachine~T~ 
AnimationStateContext "1" *--> "trackArray 1" DiscreteTrackArray 
AnimationStateContext  ..>  DiscreteTrackArray : «create»
AnimationStateContext  ..>  ObjectAnimation 
AnimationStateContext  ..>  ObjectAnimationRunner 
AnimationStateContext  ..>  PlayType 
AnimationStateContext  ..>  TrackArrayMismatchException : «create»
AnimationStateMachine~T~ "1" *--> "animationController 1" AnimationController 
AnimationStateMachine~T~ "1" *--> "currentStates *" AnimationState~T~ 
AnimationStateMachine~T~  ..>  AnimationStateContext 
AnimationStateMachine~T~  ..>  AnimationStateMachine~T~ 
AnimationStructure  ..>  Accessor 
AnimationStructure  ..>  AccessorData 
AnimationStructure  ..>  AccessorDatas 
AnimationStructure "1" *--> "accessorModels *" AccessorModel 
AnimationStructure  ..>  AccessorModel : «create»
AnimationStructure  ..>  AccessorSparse 
AnimationStructure  ..>  AccessorSparseIndices 
AnimationStructure  ..>  AccessorSparseUtils 
AnimationStructure  ..>  AccessorSparseValues 
AnimationStructure  ..>  Animation 
AnimationStructure  ..>  AnimationChannel 
AnimationStructure  ..>  AnimationChannelTarget 
AnimationStructure "1" *--> "animationModels *" AnimationModel 
AnimationStructure  ..>  AnimationModel : «create»
AnimationStructure  ..>  AnimationSampler 
AnimationStructure  ..>  AnimationStructure 
AnimationStructure  ..>  Buffer 
AnimationStructure  ..>  BufferModel : «create»
AnimationStructure "1" *--> "bufferModels *" BufferModel 
AnimationStructure  ..>  BufferView 
AnimationStructure  ..>  BufferViewModel : «create»
AnimationStructure "1" *--> "bufferViewModels *" BufferViewModel 
AnimationStructure  ..>  Buffers 
AnimationStructure  ..>  Channel : «create»
AnimationStructure  ..>  ElementType 
AnimationStructure  ..>  Interpolation 
AnimationStructure  ..>  Node 
AnimationStructure "1" *--> "nodeModels *" NodeModel 
AnimationStructure  ..>  NodeModel : «create»
AnimationStructure "1" *--> "gltf 1" RawAnimationStructure 
AnimationStructure  ..>  Sampler : «create»
Animations  ..>  AbstractAccessorData 
Animations  ..>  AccessorData 
Animations  ..>  AccessorFloatData 
Animations  ..>  AccessorModel 
Animations  ..>  AnimationBone 
Animations  ..>  AnimationChannelContent 
Animations  ..>  AnimationController : «create»
Animations  ..>  AnimationKeyframes 
Animations  ..>  AnimationListener 
Animations  ..>  AnimationListenerSupplier 
Animations  ..>  AnimationModel 
Animations  ..>  AnimationSoundChannelContent : «create»
Animations  ..>  AnimationStructure 
Animations  ..>  Animations 
Animations  ..>  BedrockAnimation 
Animations  ..>  BedrockAnimationFile 
Animations  ..>  Channel 
Animations  ..>  ChannelType 
Animations  ..>  CustomInterpolator : «create»
Animations  ..>  Interpolation 
Animations  ..>  Interpolator 
Animations  ..>  InterpolatorType 
Animations  ..>  InterpolatorUtil 
Animations  ..>  Keyframe 
Animations  ..>  LerpMode : «create»
Animations  ..>  MathUtil 
Animations  ..>  NodeModel 
Animations  ..>  ObjectAnimation : «create»
Animations  ..>  ObjectAnimationChannel : «create»
Animations  ..>  ObjectAnimationSoundChannel : «create»
Animations  ..>  Sampler 
Animations  ..>  SoundEffectKeyframes 
ArmorIgnoreModifier  -->  ArmorIgnoreJsonProperty 
ArmorIgnoreJsonProperty  ..>  AttachmentPropertyManager 
ArmorIgnoreJsonProperty  -->  JsonProperty~T~ 
ArmorIgnoreJsonProperty  ..>  Modifier 
ArmorIgnoreModifier  ..>  ArmorIgnoreJsonProperty : «create»
ArmorIgnoreModifier  ..>  ArmorIgnoreModifier 
ArmorIgnoreModifier  ..>  AttachmentCacheProperty 
ArmorIgnoreModifier  ..>  AttachmentPropertyManager 
ArmorIgnoreModifier  ..>  BulletData 
ArmorIgnoreModifier  ..>  CacheValue~T~ : «create»
ArmorIgnoreModifier  ..>  CommonAssetsManager 
ArmorIgnoreModifier  ..>  Data 
ArmorIgnoreModifier  ..>  DiagramsData : «create»
ArmorIgnoreModifier  ..>  ExtraDamage 
ArmorIgnoreModifier  ..>  FireMode 
ArmorIgnoreModifier  ..>  GunData 
ArmorIgnoreModifier  ..>  GunFireModeAdjustData 
ArmorIgnoreModifier  ..>  GunProperties 
ArmorIgnoreModifier  ..>  GunProperty~T~ 
ArmorIgnoreModifier  ..>  IAttachmentModifier~T, K~ 
ArmorIgnoreModifier  ..>  IGun 
ArmorIgnoreModifier  ..>  JsonProperty~T~ 
ArmorIgnoreModifier  ..>  Modifier 
ArmorIgnoreModifier  ..>  SyncConfig 
AttachmentCacheProperty  ..>  AttachmentData 
AttachmentCacheProperty  ..>  AttachmentDataUtils 
AttachmentCacheProperty  ..>  AttachmentPropertyManager 
AttachmentCacheProperty "1" *--> "cacheValues *" CacheValue~T~ 
AttachmentCacheProperty  ..>  GunData 
AttachmentCacheProperty  ..>  GunProperty~T~ 
AttachmentCacheProperty  ..>  IAttachmentModifier~T, K~ 
AttachmentCacheProperty  ..>  JsonProperty~T~ 
AttachmentData "1" *--> "modifier *" JsonProperty~T~ 
AttachmentData "1" *--> "meleeData 1" MeleeData 
AttachmentDataManager  ..>  AttachmentData 
AttachmentDataManager  ..>  AttachmentPropertyManager 
AttachmentDataManager  ..>  CommonAssetsManager 
AttachmentDataManager  -->  CommonDataManager~T~ 
AttachmentDataManager  ..>  DataType 
AttachmentDataManager  ..>  IAttachmentModifier~T, K~ 
AttachmentDataManager  ..>  JsonDataManager~T~ 
AttachmentDataManager  ..>  JsonProperty~T~ 
AttachmentDataUtils  ..>  ArmorIgnoreModifier 
AttachmentDataUtils  ..>  AttachmentCacheProperty 
AttachmentDataUtils  ..>  AttachmentData 
AttachmentDataUtils  ..>  AttachmentDataUtils 
AttachmentDataUtils  ..>  AttachmentPropertyManager 
AttachmentDataUtils  ..>  AttachmentType 
AttachmentDataUtils  ..>  BooleanResolver~T~ 
AttachmentDataUtils  ..>  BulletData 
AttachmentDataUtils  ..>  CommonAttachmentIndex 
AttachmentDataUtils  ..>  DamageModifier 
AttachmentDataUtils  ..>  DefaultAssets 
AttachmentDataUtils  ..>  DistanceDamagePair 
AttachmentDataUtils  ..>  ExplosionData 
AttachmentDataUtils  ..>  ExplosionModifier 
AttachmentDataUtils  ..>  ExplosionModifierValue 
AttachmentDataUtils  ..>  ExtraDamage 
AttachmentDataUtils  ..>  FireMode 
AttachmentDataUtils  ..>  GunData 
AttachmentDataUtils  ..>  GunFireModeAdjustData 
AttachmentDataUtils  ..>  HeadShotModifier 
AttachmentDataUtils  ..>  IGun 
AttachmentDataUtils  ..>  JsonProperty~T~ 
AttachmentDataUtils  ..>  Modifier : «create»
AttachmentDataUtils  ..>  SyncConfig 
AttachmentDataUtils  ..>  TimelessAPI 
AttachmentDataUtils  ..>  WeightModifier 
AttachmentDisplay "1" *--> "attachmentLod 1" AttachmentLod 
AttachmentDisplay  ..>  IDisplay 
AttachmentDisplay "1" *--> "laserConfig 1" LaserConfig 
AttachmentDisplay "1" *--> "textShows *" TextShow 
AttachmentIdFix  ..>  AttachmentIdFix 
AttachmentIdFix  ..>  AttachmentItemDataAccessor 
AttachmentIdFix  ..>  DefaultAssets 
AttachmentIndexPOJO "1" *--> "type 1" AttachmentType 
AttachmentItem  ..>  AttachmentIdFix 
AttachmentItem  ..>  AttachmentItem 
AttachmentItem  ..>  AttachmentItemBuilder 
AttachmentItem  ..>  AttachmentItemDataAccessor 
AttachmentItem  ..>  AttachmentItemRenderer : «create»
AttachmentItem  ..>  AttachmentItemTooltip : «create»
AttachmentItem  ..>  AttachmentType 
AttachmentItem  ..>  ClientAttachmentIndex 
AttachmentItem  ..>  CommonAttachmentIndex 
AttachmentItem  ..>  IAttachment 
AttachmentItem  ..>  TimelessAPI 
AttachmentItemBuilder  ..>  AttachmentItemBuilder : «create»
AttachmentItemBuilder  ..>  DefaultAssets 
AttachmentItemBuilder  ..>  IAttachment 
AttachmentItemBuilder  ..>  ModItems 
AttachmentItemDataAccessor  ..>  AttachmentItemDataAccessor 
AttachmentItemDataAccessor  ..>  DefaultAssets 
AttachmentItemDataAccessor  -->  IAttachment 
AttachmentItemDataAccessor  ..>  ModDataComponents 
AttachmentItemRenderer  ..>  AttachmentItemRenderer 
AttachmentItemRenderer  ..>  BedrockAttachmentModel 
AttachmentItemRenderer  ..>  ClientAttachmentIndex 
AttachmentItemRenderer  ..>  IAttachment 
AttachmentItemRenderer  ..>  RenderDistance 
AttachmentItemRenderer  ..>  SlotModel : «create»
AttachmentItemRenderer "1" *--> "SLOT_ATTACHMENT_MODEL 1" SlotModel 
AttachmentItemRenderer  ..>  TimelessAPI 
AttachmentItemTooltip "1" *--> "type 1" AttachmentType 
AttachmentLockCommand  ..>  AttachmentLockCommand 
AttachmentLockCommand  ..>  IGun 
AttachmentNbtFactory  ..>  AttachmentItem 
AttachmentNbtFactory  ..>  AttachmentNbtFactory 
AttachmentNbtFactory  ..>  IAttachment 
AttachmentNbtFactory  -->  TimelessItemNbtFactory~T, S~ 
AttachmentNbtFactory  ..>  TimelessItemType 
AttachmentPropertyEventJS  ..>  AttachmentPropertyEvent 
AttachmentPropertyEventJS  ..>  AttachmentPropertyWrapper 
AttachmentPropertyEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  AttachmentPropertyEventJS 
AttachmentPropertyEventJS  ..>  TimelessForgeEventWrappers 
AttachmentPropertyManager  ..>  AdsModifier : «create»
AttachmentPropertyManager  ..>  AmmoSpeedModifier : «create»
AttachmentPropertyManager  ..>  ArmorIgnoreModifier : «create»
AttachmentPropertyManager  ..>  AttachmentCacheProperty : «create»
AttachmentPropertyManager  ..>  AttachmentPropertyEvent : «create»
AttachmentPropertyManager  ..>  AttachmentPropertyManager 
AttachmentPropertyManager  ..>  ChangeGunPropertyEvent 
AttachmentPropertyManager  ..>  DamageModifier : «create»
AttachmentPropertyManager  ..>  EffectiveRangeModifier : «create»
AttachmentPropertyManager  ..>  ExplosionModifier : «create»
AttachmentPropertyManager  ..>  ExtraMovementModifier : «create»
AttachmentPropertyManager  ..>  GunMod 
AttachmentPropertyManager  ..>  HeadShotModifier : «create»
AttachmentPropertyManager "1" *--> "MODIFIERS *" IAttachmentModifier~T, K~ 
AttachmentPropertyManager  ..>  IGun 
AttachmentPropertyManager  ..>  IGunOperator 
AttachmentPropertyManager  ..>  IgniteModifier : «create»
AttachmentPropertyManager  ..>  InaccuracyModifier : «create»
AttachmentPropertyManager  ..>  KnockbackModifier : «create»
AttachmentPropertyManager  ..>  Modifier 
AttachmentPropertyManager  ..>  PierceModifier : «create»
AttachmentPropertyManager  ..>  RecoilModifier : «create»
AttachmentPropertyManager  ..>  RpmModifier : «create»
AttachmentPropertyManager  ..>  SilenceModifier : «create»
AttachmentPropertyManager  ..>  TimelessAPI 
AttachmentPropertyManager  ..>  WeightModifier : «create»
AttachmentPropertyWrapper  ..>  AttachmentCacheProperty 
AttachmentPropertyWrapper  ..>  AttachmentPropertyEvent 
AttachmentPropertyWrapper  -->  ForgeEventWrapper~E~ 
TimelessForgeEventWrappers  -->  AttachmentPropertyWrapper 
AttachmentQueryCategory  ..>  AttachmentQueryEntry 
AttachmentQueryCategory  ..>  GunMod 
AttachmentQueryCategory  ..>  ModCreativeTabs 
AttachmentQueryEntry  ..>  AttachmentItemBuilder 
AttachmentQueryEntry  ..>  AttachmentQueryCategory 
AttachmentQueryEntry  ..>  AttachmentQueryEntry : «create»
AttachmentQueryEntry  ..>  GunItemBuilder 
AttachmentQueryEntry  ..>  GunTabType 
AttachmentQueryEntry  ..>  IGun 
AttachmentQueryEntry  ..>  TimelessAPI 
AttachmentRender  ..>  AttachmentItemRenderer 
AttachmentRender  ..>  AttachmentRender 
AttachmentRender "1" *--> "type 1" AttachmentType 
AttachmentRender  ..>  BedrockAttachmentModel 
AttachmentRender "1" *--> "bedrockGunModel 1" BedrockGunModel 
AttachmentRender  ..>  BedrockModel 
AttachmentRender  ..>  IAttachment 
AttachmentRender  ..>  IFunctionalRenderer 
AttachmentRender  ..>  RenderDistance 
AttachmentRender  ..>  SlotModel 
AttachmentRender  ..>  TimelessAPI 
AttachmentsTagManager  ..>  AttachmentsTagManager 
AttachmentsTagManager  ..>  CommonAssetsManager 
AttachmentsTagManager  ..>  DataType 
AttachmentsTagManager  ..>  GunMod 
AttachmentsTagManager  ..>  INetworkCacheReloadListener 
AttachmentsTagManager  ..>  ResourceScanner 
BeamRenderer  ..>  BeamRenderer 
BeamRenderer  ..>  BedrockPart 
BeamRenderer  ..>  ClientAttachmentIndex 
BeamRenderer  ..>  GunDisplayInstance 
BeamRenderer  ..>  GunMod 
BeamRenderer  ..>  IAttachment 
BeamRenderer  ..>  IGun 
BeamRenderer  ..>  LaserBeamRenderState 
BeamRenderer  ..>  LaserColorUtil 
BeamRenderer  ..>  LaserConfig : «create»
BeamRenderer "1" *--> "DEFAULT_LASER_CONFIG 1" LaserConfig 
BeamRenderer  ..>  RenderConfig 
BeamRenderer  ..>  TimelessAPI 
BedrockAmmoModel  -->  BedrockModel 
BedrockAmmoModel  ..>  BedrockModelPOJO 
BedrockAmmoModel "1" *--> "fixedOriginPath *" BedrockPart 
BedrockAmmoModel  ..>  BedrockVersion 
BedrockAnimatedModel  ..>  AnimationListener 
BedrockAnimatedModel  ..>  AnimationListenerSupplier 
BedrockAnimatedModel  -->  BedrockModel 
BedrockAnimatedModel  ..>  BedrockModelPOJO 
BedrockAnimatedModel "1" *--> "constraintPath *" BedrockPart 
BedrockAnimatedModel  ..>  BedrockVersion 
BedrockAnimatedModel  ..>  BonesItem 
BedrockAnimatedModel  ..>  CameraAnimationObject : «create»
BedrockAnimatedModel "1" *--> "cameraAnimationObject 1" CameraAnimationObject 
BedrockAnimatedModel  ..>  ChannelType 
BedrockAnimatedModel "1" *--> "constraintObject 1" ConstraintObject 
BedrockAnimatedModel  ..>  ConstraintObject : «create»
BedrockAnimatedModel  ..>  FunctionalBedrockPart : «create»
BedrockAnimatedModel  ..>  GeometryModelLegacy 
BedrockAnimatedModel  ..>  GeometryModelNew 
BedrockAnimatedModel  ..>  IFunctionalRenderer 
BedrockAnimatedModel  ..>  ModelRendererWrapper : «create»
BedrockAnimatedModel  ..>  ModelRotateListener : «create»
BedrockAnimatedModel  ..>  ModelScaleListener : «create»
BedrockAnimatedModel  ..>  ModelTranslateListener : «create»
BedrockAnimatedModel  ..>  ObjectAnimationChannel 
BedrockAnimation "1" *--> "bones *" AnimationBone 
BedrockAnimation "1" *--> "soundEffects 1" SoundEffectKeyframes 
BedrockAnimationFile "1" *--> "animations *" BedrockAnimation 
BedrockAttachmentModel  ..>  BeamRenderer 
BedrockAttachmentModel  -->  BedrockAnimatedModel 
BedrockAttachmentModel  ..>  BedrockAttachmentModel 
BedrockAttachmentModel  ..>  BedrockModel 
BedrockAttachmentModel  ..>  BedrockModelPOJO 
BedrockAttachmentModel "1" *--> "scopeBodyPath *" BedrockPart 
BedrockAttachmentModel  ..>  BedrockVersion 
BedrockAttachmentModel  ..>  IClientPlayerGunOperator 
BedrockAttachmentModel  ..>  ModelRendererWrapper 
BedrockAttachmentModel  ..>  OcularWrapper : «create»
BedrockAttachmentModel  ..>  OculusCompat 
BedrockAttachmentModel  ..>  RenderHelper 
BedrockAttachmentModel  ..>  TextShow 
BedrockAttachmentModel  ..>  TextShowRender : «create»
BedrockCubeBox  ..>  BedrockCube 
BedrockCubeBox "1" *--> "polygons *" BedrockPolygon 
BedrockCubeBox  ..>  BedrockPolygon : «create»
BedrockCubeBox  ..>  BedrockVertex : «create»
BedrockCubePerFace  ..>  BedrockCube 
BedrockCubePerFace  ..>  BedrockCubePerFace 
BedrockCubePerFace  ..>  BedrockPolygon : «create»
BedrockCubePerFace "1" *--> "polygons *" BedrockPolygon 
BedrockCubePerFace "1" *--> "EMPTY_VERTEX *" BedrockVertex 
BedrockCubePerFace  ..>  BedrockVertex : «create»
BedrockCubePerFace  ..>  FaceItem 
BedrockCubePerFace  ..>  FaceUVsItem 
BedrockGunModel  ..>  AnimationListener 
BedrockGunModel  ..>  AttachmentRender : «create»
BedrockGunModel  ..>  AttachmentType 
BedrockGunModel  ..>  BeamRenderer 
BedrockGunModel  -->  BedrockAnimatedModel 
BedrockGunModel  ..>  BedrockGunModel 
BedrockGunModel  ..>  BedrockModel 
BedrockGunModel  ..>  BedrockModelPOJO 
BedrockGunModel "1" *--> "ironSightPath *" BedrockPart 
BedrockGunModel  ..>  BedrockVersion 
BedrockGunModel  ..>  ChannelType 
BedrockGunModel  ..>  ClientAttachmentIndex 
BedrockGunModel  ..>  IAttachment 
BedrockGunModel  ..>  IFunctionalRenderer 
BedrockGunModel  ..>  IGun 
BedrockGunModel  ..>  LeftHandRender : «create»
BedrockGunModel  ..>  ModelAdditionalMagazineListener : «create»
BedrockGunModel  ..>  ModelRendererWrapper 
BedrockGunModel  ..>  MuzzleFlashRender : «create»
BedrockGunModel  ..>  ObjectAnimationChannel 
BedrockGunModel  ..>  RenderHelper 
BedrockGunModel  ..>  RightHandRender : «create»
BedrockGunModel  ..>  ShellRender : «create»
BedrockGunModel "1" *--> "shellRenderList *" ShellRender 
BedrockGunModel  ..>  TextShow 
BedrockGunModel  ..>  TextShowRender : «create»
BedrockGunModel  ..>  TimelessAPI 
BedrockModel  ..>  BedrockCubeBox : «create»
BedrockModel  ..>  BedrockCubePerFace : «create»
BedrockModel "1" *--> "dummyModel 1" BedrockModel 
BedrockModel  ..>  BedrockModel : «create»
BedrockModel  ..>  BedrockModelPOJO 
BedrockModel  ..>  BedrockPart : «create»
BedrockModel "1" *--> "shouldRender *" BedrockPart 
BedrockModel  ..>  BedrockVersion 
BedrockModel "1" *--> "indexBones *" BonesItem 
BedrockModel  ..>  CubesItem 
BedrockModel  ..>  Description 
BedrockModel  ..>  FaceUVsItem 
BedrockModel  ..>  GeometryModelLegacy 
BedrockModel  ..>  GeometryModelNew 
BedrockModel "1" *--> "delegateRenderers *" IFunctionalRenderer 
BedrockModel  ..>  ModelRendererWrapper : «create»
BedrockModel "1" *--> "modelMap *" ModelRendererWrapper 
BedrockModel  ..>  OculusCompat 
BedrockModelPOJO "1" *--> "geometryModelLegacy 1" GeometryModelLegacy 
BedrockModelPOJO "1" *--> "geometryModelNew *" GeometryModelNew 
BedrockPart "1" *--> "cubes *" BedrockCube 
BedrockPart "1" *--> "children *" BedrockPart 
BedrockPolygon "1" *--> "vertices *" BedrockVertex 
BedrockVersion  ..>  BedrockModelPOJO 
BedrockVersion  ..>  BedrockVersion 
BedrockVertex  ..>  BedrockVertex : «create»
BeforeRenderHandEventJS  ..>  BeforeRenderHandEvent 
BeforeRenderHandEventJS  ..>  BeforeRenderHandWrapper 
BeforeRenderHandEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  BeforeRenderHandEventJS 
BeforeRenderHandEventJS  ..>  TimelessForgeEventWrappers 
BeforeRenderHandWrapper  ..>  BeforeRenderHandEvent 
BeforeRenderHandWrapper  -->  ForgeEventWrapper~E~ 
TimelessForgeEventWrappers  -->  BeforeRenderHandWrapper 
BellRing  ..>  AmmoHitBlockEvent 
BlackList  ..>  GunMod 
BlockData  ..>  GunMod 
BlockDisplay  ..>  IDisplay 
BlockIndexPOJO  ..>  GunMod 
BlockItemBuilder  ..>  BlockItemBuilder : «create»
BlockItemBuilder  ..>  DefaultAssets 
BlockItemBuilder  ..>  IBlock 
BlockItemDataAccessor  ..>  DefaultAssets 
BlockItemDataAccessor  -->  IBlock 
BlockItemDataAccessor  ..>  ModDataComponents 
BlockRayTrace  ..>  AmmoConfig 
BlockRayTrace  ..>  BlockRayTrace 
BlockRayTrace  ..>  ModBlocks 
RenderItemInHandBobEvent  -->  BobHurt 
BobHurt  -->  RenderItemInHandBobEvent 
RenderLevelBobEvent  -->  BobHurt 
BobHurt  -->  RenderLevelBobEvent 
BobView  -->  RenderItemInHandBobEvent 
RenderItemInHandBobEvent  -->  BobView 
BobView  -->  RenderLevelBobEvent 
RenderLevelBobEvent  -->  BobView 
BonesItem "1" *--> "cubes *" CubesItem 
AttachmentDataUtils  -->  BooleanResolver~T~ 
BufferModel  ..>  Buffers 
BufferViewModel "1" *--> "bufferModel 1" BufferModel 
BufferViewModel  ..>  BufferViewModel 
BufferViewModel  ..>  Buffers 
Buffers  ..>  Buffers 
Builder~E, T~  ..>  Builder~E, T~ 
Builder~E, T~ "1" *--> "serializer 1" IDataSerializer~T~ 
Builder~E, T~ "1" *--> "syncMode 1" SyncMode 
Builder~E, T~ "1" *--> "classKey 1" SyncedClassKey~E~ 
SyncedDataKey~E, T~  -->  Builder~E, T~ 
Builder~E, T~  ..>  SyncedDataKey~E, T~ : «create»
Builder~T~  ..>  Builder~T~ : «create»
LiteralFilter~T~  -->  Builder~T~ 
Builder~T~  ..>  LiteralFilter~T~ : «create»
BulletData "1" *--> "explosionData 1" ExplosionData 
BulletData "1" *--> "extraDamage 1" ExtraDamage 
BulletData  ..>  Ignite : «create»
BulletData "1" *--> "ignite 1" Ignite 
BulletHoleOption  ..>  BulletHoleOption 
BulletHoleOption  ..>  ModParticles 
BulletHoleParticle  ..>  BulletHoleParticle 
BulletHoleParticle  ..>  ModBlocks 
BulletHoleParticle  ..>  RenderConfig 
BulletHoleParticle  ..>  TimelessAPI 
AmmoSpeedModifier  -->  BulletSpeedJsonProperty 
BulletSpeedJsonProperty  ..>  AttachmentPropertyManager 
BulletSpeedJsonProperty  -->  JsonProperty~T~ 
BulletSpeedJsonProperty  ..>  Modifier 
CameraAnimationObject  ..>  AnimationListener 
CameraAnimationObject  ..>  AnimationListenerSupplier 
CameraAnimationObject  ..>  CameraRotateListener : «create»
CameraAnimationObject  ..>  ChannelType 
CameraAnimationObject "1" *--> "cameraRenderer 1" ModelRendererWrapper 
CameraAnimationObject  ..>  ObjectAnimationChannel 
CameraRotateListener  ..>  AnimationListener 
CameraRotateListener "1" *--> "camera 1" CameraAnimationObject 
CameraRotateListener  ..>  ChannelType 
CameraRotateListener  ..>  MathUtil 
CameraRotateListener  ..>  ModelRendererWrapper 
CameraRotateListener  ..>  ObjectAnimationChannel 
CameraSetupEvent  ..>  AbstractGunItem 
CameraSetupEvent  ..>  AnimateGeoItemRenderer~M, CTX~ 
CameraSetupEvent  ..>  AttachmentCacheProperty 
CameraSetupEvent  ..>  AttachmentItemDataAccessor 
CameraSetupEvent  ..>  AttachmentType 
CameraSetupEvent  ..>  BeforeRenderHandEvent 
CameraSetupEvent  ..>  ClientGunIndex 
CameraSetupEvent  ..>  DefaultAssets 
CameraSetupEvent  ..>  GunData 
CameraSetupEvent  ..>  GunDisplayInstance 
CameraSetupEvent  ..>  GunFireEvent 
CameraSetupEvent  ..>  GunMod 
CameraSetupEvent  ..>  GunRecoil 
CameraSetupEvent  ..>  IClientPlayerGunOperator 
CameraSetupEvent  ..>  IGun 
CameraSetupEvent  ..>  IGunOperator 
CameraSetupEvent  ..>  KeepingItemRenderer 
CameraSetupEvent  ..>  MathUtil 
CameraSetupEvent  ..>  ParameterizedCache~T~ 
CameraSetupEvent  ..>  ParameterizedCachePair~L, R~ 
CameraSetupEvent  ..>  RecoilModifier 
CameraSetupEvent  ..>  RenderConfig 
CameraSetupEvent  ..>  SecondOrderDynamics : «create»
CameraSetupEvent "1" *--> "WORLD_FOV_DYNAMICS 1" SecondOrderDynamics 
CameraSetupEvent  ..>  TimelessAPI 
CapabilityRegistry  ..>  DataHolder : «create»
CapabilityRegistry  ..>  ModCapabilities 
CapabilityRegistry  ..>  SyncedEntityData 
ChangeGunPropertyEvent  ..>  AttachmentPropertyEvent 
ChangeGunPropertyEvent  ..>  IGun 
AnimationModel  -->  Channel 
Channel  ..>  NodeModel 
Channel "1" *--> "sampler 1" Sampler 
ObjectAnimationChannel  -->  ChannelType 
Checkbox  ..>  Checkbox 
GunPackList  -->  Checkbox 
ClientAmmoBoxTooltip  ..>  AmmoBoxTooltip 
ClientAmmoBoxTooltip  ..>  IAmmoBox 
ClientAmmoIndex  ..>  AmmoDisplay 
ClientAmmoIndex  ..>  AmmoEntityDisplay 
ClientAmmoIndex  ..>  AmmoIndexPOJO 
ClientAmmoIndex "1" *--> "particle 1" AmmoParticle 
ClientAmmoIndex "1" *--> "transform 1" AmmoTransform 
ClientAmmoIndex  ..>  BedrockAmmoModel : «create»
ClientAmmoIndex "1" *--> "ammoModel 1" BedrockAmmoModel 
ClientAmmoIndex  ..>  BedrockModelPOJO 
ClientAmmoIndex  ..>  BedrockVersion 
ClientAmmoIndex  ..>  ClientAmmoIndex : «create»
ClientAmmoIndex  ..>  ClientAssetsManager 
ClientAmmoIndex  ..>  ColorHex 
ClientAmmoIndex  ..>  ShellDisplay 
ClientAssetsManager  ..>  AmmoDisplay 
ClientAssetsManager  ..>  AnimationKeyframes 
ClientAssetsManager  ..>  AnimationKeyframesSerializer : «create»
ClientAssetsManager  ..>  AnimationStructure 
ClientAssetsManager  ..>  AttachmentDisplay 
ClientAssetsManager  ..>  BedrockAnimationFile 
ClientAssetsManager  ..>  BedrockModelPOJO 
ClientAssetsManager  ..>  BlockDisplay 
ClientAssetsManager  ..>  ClientAssetsManager 
ClientAssetsManager  ..>  ClientIndexManager 
ClientAssetsManager  ..>  CommonAssetsManager 
ClientAssetsManager  ..>  CommonTransformObject 
ClientAssetsManager  ..>  CubesItem 
ClientAssetsManager  ..>  Deserializer : «create»
ClientAssetsManager  ..>  DisplayManager~T~ : «create»
ClientAssetsManager  ..>  GltfManager : «create»
ClientAssetsManager "1" *--> "gltfAnimation 1" GltfManager 
ClientAssetsManager  ..>  GunDisplay 
ClientAssetsManager  ..>  ItemStackSerializer : «create»
ClientAssetsManager  ..>  JsonDataManager~T~ : «create»
ClientAssetsManager "1" *--> "gunDisplay 1" JsonDataManager~T~ 
ClientAssetsManager  ..>  LuaAnimationConstant : «create»
ClientAssetsManager  ..>  LuaGunAnimationConstant : «create»
ClientAssetsManager "1" *--> "libList *" LuaLibrary 
ClientAssetsManager  ..>  PackInfo 
ClientAssetsManager "1" *--> "packInfo 1" PackInfoManager 
ClientAssetsManager  ..>  PackInfoManager : «create»
ClientAssetsManager  ..>  ScriptManager : «create»
ClientAssetsManager "1" *--> "scriptManager 1" ScriptManager 
ClientAssetsManager  ..>  Serializer : «create»
ClientAssetsManager "1" *--> "soundAssetsManager 1" SoundAssetsManager 
ClientAssetsManager  ..>  SoundAssetsManager : «create»
ClientAssetsManager  ..>  SoundData 
ClientAssetsManager  ..>  SoundEffectKeyframes 
ClientAssetsManager  ..>  SoundEffectKeyframesSerializer : «create»
ClientAssetsManager  ..>  Vector3fSerializer : «create»
ClientAttachmentIndex "1" *--> "data 1" AttachmentData 
ClientAttachmentIndex  ..>  AttachmentDisplay 
ClientAttachmentIndex  ..>  AttachmentIndexPOJO 
ClientAttachmentIndex  ..>  AttachmentLod 
ClientAttachmentIndex  ..>  BedrockAttachmentModel : «create»
ClientAttachmentIndex "1" *--> "attachmentModel 1" BedrockAttachmentModel 
ClientAttachmentIndex  ..>  BedrockModelPOJO 
ClientAttachmentIndex  ..>  BedrockVersion 
ClientAttachmentIndex  ..>  ClientAssetsManager 
ClientAttachmentIndex  ..>  ClientAttachmentIndex : «create»
ClientAttachmentIndex "1" *--> "skinIndexMap *" ClientAttachmentSkinIndex 
ClientAttachmentIndex  ..>  ColorHex 
ClientAttachmentIndex  ..>  CommonAssetsManager 
ClientAttachmentIndex  ..>  ICommonResourceProvider 
ClientAttachmentIndex "1" *--> "laserConfig 1" LaserConfig 
ClientAttachmentIndex  ..>  TextShow 
ClientAttachmentItemTooltip  ..>  AttachmentData 
ClientAttachmentItemTooltip  ..>  AttachmentItemBuilder 
ClientAttachmentItemTooltip  ..>  AttachmentItemTooltip 
ClientAttachmentItemTooltip  ..>  AttachmentType 
ClientAttachmentItemTooltip  ..>  ClientAssetsManager 
ClientAttachmentItemTooltip  ..>  ClientAttachmentItemTooltip 
ClientAttachmentItemTooltip  ..>  GunItemBuilder 
ClientAttachmentItemTooltip  ..>  IAttachment 
ClientAttachmentItemTooltip  ..>  IGun 
ClientAttachmentItemTooltip  ..>  JsonProperty~T~ 
ClientAttachmentItemTooltip  ..>  PackInfo 
ClientAttachmentItemTooltip  ..>  TimelessAPI 
ClientAttachmentSkinIndex  ..>  AttachmentSkin 
ClientAttachmentSkinIndex "1" *--> "model 1" BedrockAttachmentModel 
ClientAttachmentSkinIndex  ..>  ClientAttachmentIndex 
ClientAttachmentSkinIndex  ..>  ClientAttachmentSkinIndex : «create»
ClientBlockIndex  ..>  BedrockModel : «create»
ClientBlockIndex "1" *--> "model 1" BedrockModel 
ClientBlockIndex  ..>  BedrockModelPOJO 
ClientBlockIndex  ..>  BedrockVersion 
ClientBlockIndex  ..>  BlockDisplay 
ClientBlockIndex  ..>  BlockIndexPOJO 
ClientBlockIndex  ..>  ClientAssetsManager 
ClientBlockIndex  ..>  ClientBlockIndex : «create»
ClientBlockItemTooltip  ..>  BlockItemTooltip 
ClientBlockItemTooltip  ..>  ClientAssetsManager 
ClientBlockItemTooltip  ..>  ClientBlockItemTooltip 
ClientBlockItemTooltip  ..>  PackInfo 
ClientBlockItemTooltip  ..>  TimelessAPI 
ClientConfig  ..>  KeyConfig 
ClientConfig  ..>  RenderConfig 
ClientConfig  ..>  ZoomConfig 
ClientGunIndex  ..>  ClientAssetsManager 
ClientGunIndex  ..>  ClientGunIndex : «create»
ClientGunIndex  ..>  CommonAssetsManager 
ClientGunIndex "1" *--> "gunData 1" GunData 
ClientGunIndex  ..>  GunDisplay 
ClientGunIndex "1" *--> "display 1" GunDisplayInstance 
ClientGunIndex  ..>  GunIndexPOJO 
ClientGunIndex  ..>  ICommonResourceProvider 
ClientGunTooltip  ..>  AmmoCountStyle 
ClientGunTooltip  ..>  AmmoItemBuilder 
ClientGunTooltip  ..>  AttachmentDataUtils 
ClientGunTooltip  ..>  Bolt 
ClientGunTooltip  ..>  BulletData 
ClientGunTooltip  ..>  ClientAssetsManager 
ClientGunTooltip  ..>  ClientGunTooltip 
ClientGunTooltip "1" *--> "gunIndex 1" CommonGunIndex 
ClientGunTooltip  ..>  DamageStyle 
ClientGunTooltip  ..>  ExplosionData 
ClientGunTooltip  ..>  ExtraDamage 
ClientGunTooltip  ..>  GunData 
ClientGunTooltip "1" *--> "display 1" GunDisplayInstance 
ClientGunTooltip  ..>  GunIndexPOJO 
ClientGunTooltip  ..>  GunTooltip 
ClientGunTooltip  ..>  GunTooltipPart 
ClientGunTooltip "1" *--> "iGun 1" IGun 
ClientGunTooltip  ..>  PackInfo 
ClientGunTooltip  ..>  RefitKey 
ClientGunTooltip  ..>  SyncConfig 
ClientGunTooltip  ..>  TimelessAPI 
ClientHitMark  ..>  EntityHurtByGunEvent 
ClientHitMark  ..>  EntityKillByGunEvent 
ClientHitMark  ..>  KillAmountOverlay 
ClientHitMark  ..>  Post 
ClientHitMark  ..>  RenderConfig 
ClientHitMark  ..>  RenderCrosshairEvent 
ClientHitMark  ..>  SoundPlayManager 
ClientHitMark  ..>  TargetMinecart 
ClientHitMark  ..>  TimelessAPI 
ClientIndexManager  ..>  AmmoIndexPOJO 
ClientIndexManager  ..>  AttachmentIndexPOJO 
ClientIndexManager  ..>  AttachmentPropertyManager 
ClientIndexManager  ..>  BlockIndexPOJO 
ClientIndexManager "1" *--> "AMMO_INDEX *" ClientAmmoIndex 
ClientIndexManager  ..>  ClientAssetsManager 
ClientIndexManager "1" *--> "ATTACHMENT_INDEX *" ClientAttachmentIndex 
ClientIndexManager "1" *--> "BLOCK_INDEX *" ClientBlockIndex 
ClientIndexManager "1" *--> "GUN_INDEX *" ClientGunIndex 
ClientIndexManager  ..>  ClientIndexManager 
ClientIndexManager "1" *--> "GUN_DISPLAY *" GunDisplayInstance 
ClientIndexManager  ..>  GunIndexPOJO 
ClientIndexManager  ..>  GunMod 
ClientIndexManager  ..>  IClientPlayerGunOperator 
ClientIndexManager  ..>  IGun 
ClientIndexManager  ..>  TimelessAPI 
ClientMessageCraft  ..>  ClientMessageCraft : «create»
ClientMessageCraft  ..>  GunSmithTableMenu 
ClientMessageLaserColor "1" *--> "colorMap *" AttachmentType 
ClientMessageLaserColor  ..>  ClientMessageLaserColor : «create»
ClientMessageLaserColor  ..>  IAttachment 
ClientMessageLaserColor  ..>  IGun 
ClientMessagePlayerAim  ..>  ClientMessagePlayerAim : «create»
ClientMessagePlayerAim  ..>  IGunOperator 
ClientMessagePlayerBoltGun  ..>  ClientMessagePlayerBoltGun : «create»
ClientMessagePlayerBoltGun  ..>  IGunOperator 
ClientMessagePlayerCancelReload  ..>  ClientMessagePlayerCancelReload : «create»
ClientMessagePlayerCancelReload  ..>  IGunOperator 
ClientMessagePlayerCrawl  ..>  ClientMessagePlayerCrawl : «create»
ClientMessagePlayerCrawl  ..>  IGunOperator 
ClientMessagePlayerCrawl  ..>  SyncConfig 
ClientMessagePlayerDrawGun  ..>  ClientMessagePlayerDrawGun : «create»
ClientMessagePlayerDrawGun  ..>  IGunOperator 
ClientMessagePlayerFireSelect  ..>  ClientMessagePlayerFireSelect : «create»
ClientMessagePlayerFireSelect  ..>  IGunOperator 
ClientMessagePlayerMelee  ..>  ClientMessagePlayerMelee : «create»
ClientMessagePlayerMelee  ..>  IGunOperator 
ClientMessagePlayerReloadGun  ..>  ClientMessagePlayerReloadGun : «create»
ClientMessagePlayerReloadGun  ..>  IGunOperator 
ClientMessagePlayerShoot  ..>  ClientMessagePlayerShoot : «create»
ClientMessagePlayerShoot  ..>  IGunOperator 
ClientMessagePlayerZoom  ..>  ClientMessagePlayerZoom : «create»
ClientMessagePlayerZoom  ..>  IGunOperator 
ClientMessageRefitGun  ..>  AttachmentPropertyManager 
ClientMessageRefitGun "1" *--> "attachmentType 1" AttachmentType 
ClientMessageRefitGun  ..>  ClientMessageRefitGun : «create»
ClientMessageRefitGun  ..>  IGun 
ClientMessageRefitGun  ..>  NetworkHandler 
ClientMessageRefitGun  ..>  ServerMessageRefreshRefitScreen : «create»
ClientMessageSyncBaseTimestamp  ..>  ClientMessageSyncBaseTimestamp : «create»
ClientMessageSyncBaseTimestamp  ..>  GunMod 
ClientMessageSyncBaseTimestamp  ..>  IGunOperator 
ClientMessageSyncBaseTimestamp  ..>  ShooterDataHolder 
ClientMessageUnloadAttachment  ..>  AttachmentPropertyManager 
ClientMessageUnloadAttachment "1" *--> "attachmentType 1" AttachmentType 
ClientMessageUnloadAttachment  ..>  ClientMessageUnloadAttachment : «create»
ClientMessageUnloadAttachment  ..>  IGun 
ClientMessageUnloadAttachment  ..>  NetworkHandler 
ClientMessageUnloadAttachment  ..>  ServerMessageRefreshRefitScreen : «create»
ClientPreventGunClick  ..>  GunMod 
ClientPreventGunClick  ..>  IGun 
ClientPreventGunClick  ..>  InteractKey 
ClientSetupEvent  ..>  AimKey 
ClientSetupEvent  ..>  AmmoBoxItem 
ClientSetupEvent  ..>  AmmoBoxTooltip 
ClientSetupEvent  ..>  AttachmentItemTooltip 
ClientSetupEvent  ..>  BlockItemTooltip 
ClientSetupEvent  ..>  ClientAmmoBoxTooltip 
ClientSetupEvent  ..>  ClientAssetsManager 
ClientSetupEvent  ..>  ClientAttachmentItemTooltip 
ClientSetupEvent  ..>  ClientBlockItemTooltip 
ClientSetupEvent  ..>  ClientGunTooltip 
ClientSetupEvent  ..>  ConfigKey 
ClientSetupEvent  ..>  ControllableCompat 
ClientSetupEvent  ..>  CrawlKey 
ClientSetupEvent  ..>  FireSelectKey 
ClientSetupEvent  ..>  GunHudOverlay : «create»
ClientSetupEvent  ..>  GunMod 
ClientSetupEvent  ..>  GunTooltip 
ClientSetupEvent  ..>  HeatBarOverlay : «create»
ClientSetupEvent  ..>  InspectKey 
ClientSetupEvent  ..>  InteractKey 
ClientSetupEvent  ..>  InteractKeyTextOverlay : «create»
ClientSetupEvent  ..>  KillAmountOverlay : «create»
ClientSetupEvent  ..>  MeleeKey 
ClientSetupEvent  ..>  ModItems 
ClientSetupEvent  ..>  PlayerAnimatorCompat 
ClientSetupEvent  ..>  RefitKey 
ClientSetupEvent  ..>  ReloadKey 
ClientSetupEvent  ..>  ShootKey 
ClientSetupEvent  ..>  ShoulderSurfingCompat 
ClientSetupEvent  ..>  ThirdPersonManager 
ClientSetupEvent  ..>  ZoomKey 
ClothConfigScreen  ..>  ClothConfigScreen 
ColorHex  ..>  ColorHex 
CommandRegistry  ..>  RootCommand 
CommonAmmoIndex "1" *--> "pojo 1" AmmoIndexPOJO 
CommonAmmoIndex  ..>  CommonAmmoIndex : «create»
CommonAmmoIndexSerializer  ..>  AmmoIndexPOJO 
CommonAmmoIndexSerializer  ..>  CommonAmmoIndex 
CommonAssetsManager  ..>  AttachmentData 
CommonAssetsManager  ..>  AttachmentDataManager : «create»
CommonAssetsManager  ..>  AttachmentsTagManager : «create»
CommonAssetsManager "1" *--> "attachmentsTagManager 1" AttachmentsTagManager 
CommonAssetsManager  ..>  BlockData 
CommonAssetsManager  ..>  CommonAmmoIndex 
CommonAssetsManager  ..>  CommonAmmoIndexSerializer : «create»
CommonAssetsManager  ..>  CommonAssetsManager : «create»
CommonAssetsManager "1" *--> "INSTANCE 1" CommonAssetsManager 
CommonAssetsManager  ..>  CommonAttachmentIndex 
CommonAssetsManager  ..>  CommonAttachmentIndexSerializer : «create»
CommonAssetsManager  ..>  CommonBlockIndex 
CommonAssetsManager  ..>  CommonBlockIndexSerializer : «create»
CommonAssetsManager  ..>  CommonDataManager~T~ : «create»
CommonAssetsManager "1" *--> "gunData 1" CommonDataManager~T~ 
CommonAssetsManager  ..>  CommonGunIndex 
CommonAssetsManager  ..>  CommonGunIndexSerializer : «create»
CommonAssetsManager  ..>  CommonNetworkCache 
CommonAssetsManager  ..>  DataType 
CommonAssetsManager  ..>  Deserializer : «create»
CommonAssetsManager  ..>  Deserializer : «create»
CommonAssetsManager  ..>  DistanceDamagePair 
CommonAssetsManager  ..>  DistanceDamagePairSerializer : «create»
CommonAssetsManager  ..>  ExtraDamage 
CommonAssetsManager  ..>  GunData 
CommonAssetsManager  ..>  GunSmithTableIngredient 
CommonAssetsManager  ..>  GunSmithTableIngredientSerializer : «create»
CommonAssetsManager  ..>  GunSmithTableRecipe 
CommonAssetsManager  ..>  GunSmithTableResult 
CommonAssetsManager  ..>  GunSmithTableResultSerializer : «create»
CommonAssetsManager  ..>  ICommonResourceProvider 
CommonAssetsManager "1" *--> "listeners *" INetworkCacheReloadListener 
CommonAssetsManager  ..>  Ignite 
CommonAssetsManager  ..>  IgniteSerializer : «create»
CommonAssetsManager  ..>  JsonDataManager~T~ 
CommonAssetsManager  ..>  LuaGunLogicConstant : «create»
CommonAssetsManager "1" *--> "libList *" LuaLibrary 
CommonAssetsManager  ..>  ModRecipe 
CommonAssetsManager  ..>  NetworkHandler 
CommonAssetsManager  ..>  PairSerializer : «create»
CommonAssetsManager  ..>  RecipeFilter 
CommonAssetsManager  ..>  RecipeFilterManager : «create»
CommonAssetsManager "1" *--> "recipeFilterManager 1" RecipeFilterManager 
CommonAssetsManager "1" *--> "scriptManager 1" ScriptManager 
CommonAssetsManager  ..>  ScriptManager : «create»
CommonAssetsManager  ..>  ServerMessageSyncGunPack : «create»
CommonAssetsManager  ..>  TabConfig 
CommonAssetsManager  ..>  Vec3Serializer : «create»
CommonAttachmentIndex "1" *--> "data 1" AttachmentData 
CommonAttachmentIndex "1" *--> "pojo 1" AttachmentIndexPOJO 
CommonAttachmentIndex "1" *--> "type 1" AttachmentType 
CommonAttachmentIndex  ..>  CommonAssetsManager 
CommonAttachmentIndex  ..>  CommonAttachmentIndex : «create»
CommonAttachmentIndex  ..>  ICommonResourceProvider 
CommonAttachmentIndexSerializer  ..>  AttachmentIndexPOJO 
CommonBlockIndex "1" *--> "data 1" BlockData 
CommonBlockIndex "1" *--> "pojo 1" BlockIndexPOJO 
CommonBlockIndex  ..>  CommonAssetsManager 
CommonBlockIndex  ..>  CommonBlockIndex : «create»
CommonBlockIndex  ..>  ICommonResourceProvider 
CommonBlockIndex "1" *--> "filter 1" RecipeFilter 
CommonBlockIndexSerializer  ..>  BlockIndexPOJO 
CommonBlockIndexSerializer  ..>  CommonBlockIndex 
CommonConfig  ..>  AmmoConfig 
CommonConfig  ..>  GunConfig 
CommonConfig  ..>  OtherConfig 
CommonDataManager~T~ "1" *--> "type 1" DataType 
CommonDataManager~T~  ..>  INetworkCacheReloadListener 
CommonDataManager~T~  -->  JsonDataManager~T~ 
CommonGunIndex  ..>  BulletData 
CommonGunIndex  ..>  CommonAssetsManager 
CommonGunIndex  ..>  CommonGunIndex : «create»
CommonGunIndex  ..>  FireMode 
CommonGunIndex "1" *--> "gunData 1" GunData 
CommonGunIndex "1" *--> "pojo 1" GunIndexPOJO 
CommonGunIndex  ..>  GunMod 
CommonGunIndex  ..>  GunRecoil 
CommonGunIndex  ..>  GunRecoilKeyFrame 
CommonGunIndex  ..>  GunReloadData 
CommonGunIndex  ..>  ICommonResourceProvider 
CommonGunIndex  ..>  InaccuracyType 
CommonGunIndexSerializer  ..>  CommonGunIndex 
CommonGunIndexSerializer  ..>  GunIndexPOJO 
CommonLoadPack  ..>  GunMod 
CommonNetworkCache "1" *--> "attachmentData *" AttachmentData 
CommonNetworkCache  ..>  AttachmentPropertyManager 
CommonNetworkCache "1" *--> "blockData *" BlockData 
CommonNetworkCache "1" *--> "ammoIndex *" CommonAmmoIndex 
CommonNetworkCache  ..>  CommonAssetsManager 
CommonNetworkCache "1" *--> "attachmentIndex *" CommonAttachmentIndex 
CommonNetworkCache "1" *--> "blockIndex *" CommonBlockIndex 
CommonNetworkCache "1" *--> "gunIndex *" CommonGunIndex 
CommonNetworkCache  ..>  CommonNetworkCache 
CommonNetworkCache  ..>  DataType 
CommonNetworkCache "1" *--> "gunData *" GunData 
CommonNetworkCache  ..>  GunMod 
CommonNetworkCache  ..>  IAttachmentModifier~T, K~ 
CommonNetworkCache  ..>  ICommonResourceProvider 
CommonNetworkCache  ..>  JsonProperty~T~ 
CommonNetworkCache "1" *--> "recipeFilter *" RecipeFilter 
CommonRegistry  ..>  GunPackLoader 
CommonRegistry  ..>  ModAttributes 
CommonRegistry  ..>  ModSyncedEntityData 
CommonRegistry  ..>  NetworkHandler 
CommonTransformObject  ..>  CommonTransformObject : «create»
ConfigCommand  ..>  ConfigCommand 
ConfigCommand  ..>  ConfigKey 
ConfigCommand  ..>  SyncConfig 
ConfigKey  ..>  ClothConfigScreen 
ConfigKey  ..>  CompatRegistry 
ConfigCommand  -->  ConfigKey 
ConfigKey  ..>  ConfigKey 
ConfigKey  ..>  InputExtraCheck 
ConfigKey  ..>  MenuIntegration 
ConstraintObject  ..>  AnimationListener 
ConstraintObject  ..>  AnimationListenerSupplier 
ConstraintObject "1" *--> "node 1" BedrockPart 
ConstraintObject "1" *--> "bonesItem 1" BonesItem 
ConstraintObject  ..>  ChannelType 
ConstraintObject  ..>  ConstraintRotateListener : «create»
ConstraintObject  ..>  ConstraintTranslateListener : «create»
ConstraintObject  ..>  ObjectAnimationChannel 
ConstraintRotateListener  ..>  AnimationListener 
ConstraintRotateListener  ..>  ChannelType 
ConstraintRotateListener "1" *--> "constraint 1" ConstraintObject 
ConstraintRotateListener  ..>  MathUtil 
ConstraintRotateListener  ..>  ObjectAnimationChannel 
ConstraintTranslateListener  ..>  AnimationListener 
ConstraintTranslateListener  ..>  BonesItem 
ConstraintTranslateListener  ..>  ChannelType 
ConstraintTranslateListener "1" *--> "constraint 1" ConstraintObject 
ConstraintTranslateListener  ..>  ObjectAnimationChannel 
ControllableCompat  ..>  ControllableInner 
ControllableInner  ..>  AimKey 
ControllableInner  ..>  ControllableData 
ControllableInner  ..>  ControllableInner 
ControllableInner  ..>  CrawlKey 
ControllableInner  ..>  FireMode 
ControllableInner  ..>  FireSelectKey 
ControllableInner  ..>  GunKeyConflict : «create»
ControllableInner  ..>  IGun 
ControllableInner  ..>  InspectKey 
ControllableInner  ..>  InteractKey 
ControllableInner  ..>  MeleeKey 
ControllableInner  ..>  ReloadKey 
ControllableInner  ..>  ShootKey 
ControllableInner  ..>  TimelessAPI 
ControllableInner  ..>  ZoomKey 
ConvertCommand  ..>  ConvertCommand 
ConvertCommand  ..>  PackConvertor 
CrawlKey  ..>  IClientPlayerGunOperator 
CrawlKey  ..>  IGun 
CrawlKey  ..>  InputExtraCheck 
CrawlKey  ..>  KeyConfig 
CrawlKey  ..>  SyncConfig 
CrosshairDropdown  ..>  CrosshairType 
CrosshairType "1" *--> "CACHE *" CrosshairType 
CrosshairType  ..>  GunMod 
CubesItem "1" *--> "faceUv 1" FaceUVsItem 
CustomGunItemBuilder  ..>  KubeJSCustomGunItem : «create»
CustomInterpolator "1" *--> "content 1" AnimationChannelContent 
CustomInterpolator  ..>  CustomInterpolator 
CustomInterpolator  ..>  Interpolator 
CustomInterpolator  ..>  LerpMode 
CustomInterpolator  ..>  MathUtil 
CycleTaskHelper  ..>  CycleTaskHelper 
CycleTaskHelper "1" *--> "CYCLE_TASKS *" CycleTaskTicker 
CycleTaskHelper  ..>  CycleTaskTicker : «create»
CycleTaskHelper  -->  CycleTaskTicker 
DamageJsonProperty  ..>  AttachmentPropertyManager 
DamageModifier  -->  DamageJsonProperty 
DamageJsonProperty  -->  JsonProperty~T~ 
DamageJsonProperty  ..>  Modifier 
DamageModifier  ..>  AttachmentCacheProperty 
DamageModifier  ..>  AttachmentPropertyManager 
DamageModifier  ..>  BulletData 
DamageModifier  ..>  CacheValue~T~ : «create»
DamageModifier  ..>  CommonAssetsManager 
DamageModifier  ..>  DamageJsonProperty : «create»
DamageModifier  ..>  DamageModifier 
DamageModifier  ..>  Data 
DamageModifier  ..>  DiagramsData : «create»
DamageModifier  ..>  DistanceDamagePair : «create»
DamageModifier  ..>  ExtraDamage 
DamageModifier  ..>  FireMode 
DamageModifier  ..>  GunData 
DamageModifier  ..>  GunFireModeAdjustData 
DamageModifier  ..>  GunProperties 
DamageModifier  ..>  GunProperty~T~ 
DamageModifier  ..>  IAttachmentModifier~T, K~ 
DamageModifier  ..>  IGun 
DamageModifier  ..>  JsonProperty~T~ 
DamageModifier  ..>  Modifier 
DamageModifier  ..>  SyncConfig 
AdsModifier  -->  Data 
AimInaccuracyModifier  -->  Data 
AmmoSpeedModifier  -->  Data 
ArmorIgnoreModifier  -->  Data 
DamageModifier  -->  Data 
EffectiveRangeModifier  -->  Data 
ExplosionModifier  -->  Data 
Data "1" *--> "explosion 1" ExplosionModifierValue 
ExtraMovementModifier  -->  Data 
HeadShotModifier  -->  Data 
Data  ..>  Ignite : «create»
Data "1" *--> "ignite 1" Ignite 
IgniteModifier  -->  Data 
InaccuracyModifier  -->  Data 
KnockbackModifier  -->  Data 
Data "1" *--> "aimInaccuracy 1" Modifier 
Data "1" *--> "ammoSpeed 1" Modifier 
Data "1" *--> "pierce 1" Modifier 
Data "1" *--> "inaccuracy 1" Modifier 
Data "1" *--> "effectiveRange 1" Modifier 
Data "1" *--> "rpm 1" Modifier 
Data "1" *--> "weightModifier 1" Modifier 
Data "1" *--> "ads 1" Modifier 
Data "1" *--> "knockback 1" Modifier 
Data "1" *--> "headShot 1" Modifier 
Data "1" *--> "damage 1" Modifier 
Data "1" *--> "armorIgnore 1" Modifier 
Data "1" *--> "moveSpeed 1" MoveSpeed 
Data "1" *--> "newRecoilData 1" NewRecoilData 
Data "1" *--> "oldRecoilData 1" OldRecoilData 
PierceModifier  -->  Data 
RecoilModifier  -->  Data 
RpmModifier  -->  Data 
ShellRender  -->  Data 
Data "1" *--> "silence 1" Silence 
SilenceModifier  -->  Data 
WeightModifier  -->  Data 
DataEntry~E, T~  ..>  DataEntry~E, T~ : «create»
DataEntry~E, T~  ..>  IDataSerializer~T~ 
DataEntry~E, T~ "1" *--> "key 1" SyncedDataKey~E, T~ 
DataEntry~E, T~  ..>  SyncedEntityData 
DataHolder "1" *--> "dataMap *" DataEntry~E, T~ 
DataHolder "1" *--> "dataMap *" SyncedDataKey~E, T~ 
DebugCommand  ..>  DebugCommand 
DefaultAssets  ..>  GunMod 
DefaultTableItem  ..>  GunMod 
DefaultTableItem  -->  GunSmithTableItem 
Deserializer  ..>  Builder~T~ : «create»
CubesItem  -->  Deserializer 
Deserializer  ..>  Deserializer 
Deserializer  ..>  FaceUVsItem 
Deserializer  ..>  GunMod 
Deserializer  ..>  IFilter~T~ 
Deserializer  ..>  LiteralFilter~T~ 
RecipeFilter  -->  Deserializer 
Deserializer  ..>  RecipeFilter : «create»
Deserializer  ..>  RegexFilter~T~ : «create»
TabConfig  -->  Deserializer 
Deserializer  ..>  TabConfig : «create»
DestroyGlassBlock  ..>  AmmoConfig 
DestroyGlassBlock  ..>  AmmoHitBlockEvent 
DestroyGlassBlock  ..>  EntityKineticBullet 
IAttachmentModifier~T, K~  -->  DiagramsData 
DiscreteTrackArray  ..>  DiscreteTrackArray 
DiscreteTrackArray  ..>  MyIterator : «create»
DisplayManager~T~  ..>  GunMod 
DisplayManager~T~  ..>  IDisplay 
DisplayManager~T~  -->  JsonDataManager~T~ 
ExtraDamage  -->  DistanceDamagePair 
DistanceDamagePairSerializer  ..>  DistanceDamagePair : «create»
DistanceDamagePairSerializer  ..>  ExtraDamage 
DummyAmmoCommand  ..>  DummyAmmoCommand 
DummyAmmoCommand  ..>  IGun 
EffectiveRangeJsonProperty  ..>  AttachmentPropertyManager 
EffectiveRangeModifier  -->  EffectiveRangeJsonProperty 
EffectiveRangeJsonProperty  -->  JsonProperty~T~ 
EffectiveRangeJsonProperty  ..>  Modifier 
EffectiveRangeModifier  ..>  AttachmentCacheProperty 
EffectiveRangeModifier  ..>  AttachmentPropertyManager 
EffectiveRangeModifier  ..>  BulletData 
EffectiveRangeModifier  ..>  CacheValue~T~ : «create»
EffectiveRangeModifier  ..>  CommonAssetsManager 
EffectiveRangeModifier  ..>  Data 
EffectiveRangeModifier  ..>  DiagramsData : «create»
EffectiveRangeModifier  ..>  DistanceDamagePair 
EffectiveRangeModifier  ..>  EffectiveRangeJsonProperty : «create»
EffectiveRangeModifier  ..>  EffectiveRangeModifier 
EffectiveRangeModifier  ..>  ExtraDamage 
EffectiveRangeModifier  ..>  GunData 
EffectiveRangeModifier  ..>  GunProperties 
EffectiveRangeModifier  ..>  GunProperty~T~ 
EffectiveRangeModifier  ..>  IAttachmentModifier~T, K~ 
EffectiveRangeModifier  ..>  JsonProperty~T~ 
EffectiveRangeModifier  ..>  Modifier 
ElementType  ..>  ElementType 
EntityBulletRenderer  ..>  BedrockAmmoModel 
EntityBulletRenderer  ..>  BedrockModel 
EntityBulletRenderer  ..>  EntityBulletRenderer 
EntityBulletRenderer  ..>  EntityKineticBullet 
EntityBulletRenderer  ..>  GunDisplayInstance 
EntityBulletRenderer  ..>  GunItemRendererWrapper 
EntityBulletRenderer  ..>  InternalAssetLoader 
EntityBulletRenderer  ..>  RenderConfig 
EntityBulletRenderer  ..>  TimelessAPI 
EntityHurtByGunEvent  ..>  EntityHurtByGunEvent 
EntityHurtByGunEvent  ..>  GunDamageSourcePart 
EntityHurtByGunEvent  ..>  KubeJSGunEventPoster~E~ 
EntityHurtByGunPostEventJS  ..>  EntityHurtByGunEvent 
EntityHurtByGunPostEventJS  ..>  EntityHurtByGunWrapper~E~ 
EntityHurtByGunPostEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  EntityHurtByGunPostEventJS 
EntityHurtByGunPostEventJS  ..>  Post 
EntityHurtByGunPostEventJS  ..>  TimelessForgeEventWrappers 
EntityHurtByGunPreEventJS  ..>  EntityHurtByGunEvent 
EntityHurtByGunPreEventJS  ..>  EntityHurtByGunPreWrapper 
EntityHurtByGunPreEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  EntityHurtByGunPreEventJS 
EntityHurtByGunPreEventJS  ..>  Pre 
EntityHurtByGunPreEventJS  ..>  TimelessForgeEventWrappers 
EntityHurtByGunPreWrapper  ..>  EntityHurtByGunEvent 
EntityHurtByGunPreWrapper  -->  EntityHurtByGunWrapper~E~ 
EntityHurtByGunPreWrapper  ..>  ForgeEventWrapper~E~ 
EntityHurtByGunPreWrapper  ..>  GunDamageSourcePart 
EntityHurtByGunPreWrapper  ..>  Pre 
TimelessForgeEventWrappers  -->  EntityHurtByGunPreWrapper 
EntityHurtByGunWrapper~E~  ..>  EntityHurtByGunEvent 
EntityHurtByGunWrapper~E~  -->  ForgeEventWrapper~E~ 
EntityHurtByGunWrapper~E~  ..>  GunDamageSourcePart 
TimelessForgeEventWrappers  -->  EntityHurtByGunWrapper~E~ 
EntityKillByGunEvent  ..>  GunDamageSourcePart 
EntityKillByGunEventJS  ..>  EntityKillByGunEvent 
EntityKillByGunEventJS  ..>  EntityKillByGunWrapper 
EntityKillByGunEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  EntityKillByGunEventJS 
EntityKillByGunEventJS  ..>  TimelessForgeEventWrappers 
EntityKillByGunWrapper  ..>  EntityKillByGunEvent 
EntityKillByGunWrapper  -->  ForgeEventWrapper~E~ 
EntityKillByGunWrapper  ..>  GunDamageSourcePart 
TimelessForgeEventWrappers  -->  EntityKillByGunWrapper 
EntityKineticBullet  ..>  AmmoConfig 
EntityKineticBullet  ..>  AmmoHitBlockEvent : «create»
EntityKineticBullet  ..>  AmmoParticleSpawner 
EntityKineticBullet  ..>  AmmoSpeedModifier 
EntityKineticBullet  ..>  ArmorIgnoreModifier 
EntityKineticBullet  ..>  AttachmentCacheProperty 
EntityKineticBullet  ..>  BlockRayTrace 
EntityKineticBullet  ..>  BulletData 
EntityKineticBullet  ..>  BulletHoleOption : «create»
EntityKineticBullet  ..>  DamageModifier 
EntityKineticBullet  ..>  DefaultAssets 
EntityKineticBullet "1" *--> "damageAmount *" DistanceDamagePair 
EntityKineticBullet  ..>  EffectiveRangeModifier 
EntityKineticBullet  ..>  EntityHurtByGunEvent 
EntityKineticBullet  ..>  EntityKillByGunEvent : «create»
EntityKineticBullet  ..>  EntityKineticBullet 
EntityKineticBullet  ..>  EntityResult : «create»
EntityKineticBullet  ..>  EntityUtil 
EntityKineticBullet  ..>  ExplodeUtil 
EntityKineticBullet  ..>  ExplosionData 
EntityKineticBullet  ..>  ExplosionModifier 
EntityKineticBullet  ..>  GunData 
EntityKineticBullet  ..>  GunMod 
EntityKineticBullet  ..>  HeadShotModifier 
EntityKineticBullet  ..>  IGunOperator 
EntityKineticBullet  ..>  ITargetEntity 
EntityKineticBullet  ..>  Ignite 
EntityKineticBullet  ..>  IgniteModifier 
EntityKineticBullet  ..>  KnockBackModifier 
EntityKineticBullet  ..>  KnockbackModifier 
EntityKineticBullet  ..>  MaybeMultipartEntity 
EntityKineticBullet  ..>  ModDamageTypes 
EntityKineticBullet  ..>  NetworkHandler 
EntityKineticBullet  ..>  PierceModifier 
EntityKineticBullet  ..>  Post : «create»
EntityKineticBullet  ..>  Pre : «create»
EntityKineticBullet  ..>  ServerMessageGunHurt : «create»
EntityKineticBullet  ..>  ServerMessageGunKill : «create»
EntityKineticBullet  ..>  Sources 
EntityKineticBullet  ..>  SyncConfig 
EntityKineticBullet  ..>  TacHitResult : «create»
EntityKineticBullet  -->  EntityResult 
EntityUtil  ..>  EntityKineticBullet 
EntityUtil  ..>  EntityResult : «create»
EntityUtil  ..>  EntityUtil 
EntityUtil  ..>  HeadShotAABBConfigRead 
EntityUtil  ..>  HitboxHelper 
Entry  ..>  Entry 
GunPackList  -->  Entry 
ExplosionModifier  -->  ExplosionJsonProperty 
ExplosionJsonProperty  ..>  ExplosionModifierValue 
ExplosionJsonProperty  -->  JsonProperty~T~ 
ExplosionModifier  ..>  AttachmentPropertyManager 
ExplosionModifier  ..>  BulletData 
ExplosionModifier  ..>  CacheValue~T~ : «create»
ExplosionModifier  ..>  CommonAssetsManager 
ExplosionModifier  ..>  Data 
ExplosionModifier  ..>  ExplosionData : «create»
ExplosionModifier  ..>  ExplosionJsonProperty : «create»
ExplosionModifier  ..>  ExplosionModifier 
ExplosionModifier  ..>  ExplosionModifierValue 
ExplosionModifier  ..>  GunData 
ExplosionModifier  ..>  GunProperties 
ExplosionModifier  ..>  GunProperty~T~ 
ExplosionModifier  ..>  IAttachmentModifier~T, K~ 
ExplosionModifier  ..>  JsonProperty~T~ 
ExplosionModifier  ..>  Modifier 
ExplosionModifier  -->  ExplosionModifierValue 
ExplosionModifierValue "1" *--> "radius 1" Modifier 
ExplosionModifierValue  ..>  Modifier : «create»
ExtraDamage "1" *--> "damageAdjust *" DistanceDamagePair 
ResourceManager  -->  ExtraEntry 
ExtraMovementModifier  ..>  CacheValue~T~ : «create»
ExtraMovementModifier  ..>  CommonAssetsManager 
ExtraMovementModifier  ..>  Data 
ExtraMovementModifier  ..>  ExtraMovementModifier 
ExtraMovementModifier  ..>  ExtraSpeedJsonProperty : «create»
ExtraMovementModifier  ..>  GunData 
ExtraMovementModifier  ..>  GunProperties 
ExtraMovementModifier  ..>  GunProperty~T~ 
ExtraMovementModifier  ..>  IAttachmentModifier~T, K~ 
ExtraMovementModifier  ..>  JsonProperty~T~ 
ExtraMovementModifier  ..>  MoveSpeed 
ExtraMovementModifier  -->  ExtraSpeedJsonProperty 
ExtraSpeedJsonProperty  ..>  ExtraSpeedJsonProperty 
ExtraSpeedJsonProperty  -->  JsonProperty~T~ 
ExtraSpeedJsonProperty  ..>  MoveSpeed 
FaceItem "1" *--> "EMPTY 1" FaceItem 
FaceItem  ..>  FaceItem : «create»
FaceUVsItem "1" *--> "down 1" FaceItem 
FaceUVsItem  ..>  FaceUVsItem : «create»
FireMode  ..>  FireMode 
FireSelectKey  ..>  FireSelectKey 
FireSelectKey  ..>  IClientPlayerGunOperator 
FireSelectKey  ..>  IGun 
FireSelectKey  ..>  InputExtraCheck 
FirstPersonRenderEvent  ..>  AnimateGeoItemRenderer~M, CTX~ 
FirstPersonRenderEvent "1" *--> "lastStateMachine 1" AnimationStateMachine~T~ 
FirstPersonRenderEvent  ..>  GunMod 
FirstPersonRenderEvent  ..>  IGun 
FirstPersonRenderEvent  ..>  KeepingItemRenderer 
FirstPersonRenderGunEvent  ..>  AttachmentItemDataAccessor 
FirstPersonRenderGunEvent  ..>  AttachmentType 
FirstPersonRenderGunEvent  ..>  BedrockAnimatedModel 
FirstPersonRenderGunEvent  ..>  BedrockAttachmentModel 
FirstPersonRenderGunEvent  ..>  BedrockGunModel 
FirstPersonRenderGunEvent  ..>  BedrockPart 
FirstPersonRenderGunEvent  ..>  BobView 
FirstPersonRenderGunEvent  ..>  ClientAttachmentIndex 
FirstPersonRenderGunEvent  ..>  DefaultAssets 
FirstPersonRenderGunEvent  ..>  Easing 
FirstPersonRenderGunEvent  ..>  EntityKineticBullet 
FirstPersonRenderGunEvent  ..>  FirstPersonRenderGunEvent 
FirstPersonRenderGunEvent  ..>  GunFireEvent 
FirstPersonRenderGunEvent  ..>  GunItemRendererWrapper 
FirstPersonRenderGunEvent  ..>  GunMod 
FirstPersonRenderGunEvent  ..>  IClientPlayerGunOperator 
FirstPersonRenderGunEvent  ..>  IGun 
FirstPersonRenderGunEvent  ..>  KeepingItemRenderer 
FirstPersonRenderGunEvent  ..>  MathUtil 
FirstPersonRenderGunEvent  ..>  MuzzleFlashRender 
FirstPersonRenderGunEvent "1" *--> "SHOOT_X_SWAY_NOISE 1" PerlinNoise 
FirstPersonRenderGunEvent  ..>  PerlinNoise : «create»
FirstPersonRenderGunEvent  ..>  RefitTransform 
FirstPersonRenderGunEvent  ..>  RenderItemInHandBobEvent 
FirstPersonRenderGunEvent "1" *--> "AIMING_DYNAMICS 1" SecondOrderDynamics 
FirstPersonRenderGunEvent  ..>  SecondOrderDynamics : «create»
FirstPersonRenderGunEvent  ..>  TimelessAPI 
FlatColorButton  ..>  FlatColorButton 
TimelessForgeEventWrappers  -->  ForgeEventWrapper~E~ 
FunctionalBedrockPart  -->  BedrockPart 
FunctionalBedrockPart  ..>  IFunctionalRenderer 
GameRendererMixin  ..>  BobHurt : «create»
GameRendererMixin  ..>  BobHurt : «create»
GameRendererMixin  ..>  BobView : «create»
GameRendererMixin  ..>  BobView : «create»
GameRendererMixin  ..>  GameRendererMixin 
GameRendererMixin  ..>  GunHurtBobTweak 
GameRendererMixin  ..>  RenderItemInHandBobEvent 
GameRendererMixin  ..>  RenderLevelBobEvent 
GeometryModelLegacy "1" *--> "bones *" BonesItem 
GeometryModelLegacy  ..>  CubesItem 
GeometryModelLegacy  ..>  GeometryModelLegacy 
GeometryModelNew "1" *--> "bones *" BonesItem 
GeometryModelNew  ..>  CubesItem 
GeometryModelNew "1" *--> "description 1" Description 
GeometryModelNew  ..>  GeometryModelNew 
GetJarResources  ..>  GetJarResources 
GetJarResources  ..>  GunMod 
GetJarResources  ..>  Md5Utils 
GltfManager  ..>  AnimationStructure : «create»
GltfManager "1" *--> "dataMap *" AnimationStructure 
GltfManager  ..>  ClientAssetsManager 
GltfManager  ..>  GunMod 
GltfManager  ..>  RawAnimationStructure 
GunAmmo "1" *--> "particle 1" AmmoParticle 
GunAnimationStateContext  ..>  AttachmentDataUtils 
GunAnimationStateContext  ..>  AttachmentType 
GunAnimationStateContext  ..>  BedrockGunModel 
GunAnimationStateContext  ..>  Bolt 
GunAnimationStateContext  ..>  BurstData 
GunAnimationStateContext  ..>  ClientGunIndex 
GunAnimationStateContext  ..>  DefaultAssets 
GunAnimationStateContext  ..>  FireMode 
GunAnimationStateContext  ..>  GunAnimationStateContext 
GunAnimationStateContext "1" *--> "gunData 1" GunData 
GunAnimationStateContext "1" *--> "display 1" GunDisplayInstance 
GunAnimationStateContext  ..>  GunHeatData 
GunAnimationStateContext  ..>  IAmmo 
GunAnimationStateContext  ..>  IAmmoBox 
GunAnimationStateContext  ..>  IClientPlayerGunOperator 
GunAnimationStateContext "1" *--> "iGun 1" IGun 
GunAnimationStateContext  ..>  IGunOperator 
GunAnimationStateContext  -->  ItemAnimationStateContext 
GunAnimationStateContext "1" *--> "nbtUtil 1" LuaNbtAccessor 
GunAnimationStateContext  ..>  LuaNbtAccessor : «create»
GunAnimationStateContext  ..>  ReloadState 
GunAnimationStateContext  ..>  ShellEjection 
GunAnimationStateContext  ..>  ShellRender 
GunAnimationStateContext  ..>  TimelessAPI 
GunAttachmentSlot "1" *--> "type 1" AttachmentType 
GunAttachmentSlot  ..>  GunRefitScreen 
GunAttachmentSlot  ..>  IGun 
GunAttachmentSlot  ..>  IStackTooltip 
GunClothConfig  ..>  GunConfig 
GunData  ..>  AttachmentCacheProperty 
GunData "1" *--> "exclusiveAttachments *" AttachmentData 
GunData "1" *--> "allowAttachments *" AttachmentType 
GunData "1" *--> "bolt 1" Bolt 
GunData "1" *--> "bulletData 1" BulletData 
GunData  ..>  BulletData : «create»
GunData  ..>  BurstData : «create»
GunData "1" *--> "burstData 1" BurstData 
GunData "1" *--> "fireModeSet *" FireMode 
GunData  ..>  FireSound : «create»
GunData "1" *--> "fireSound 1" FireSound 
GunData  ..>  GunData 
GunData  ..>  GunFireModeAdjustData 
GunData "1" *--> "gunHeatData 1" GunHeatData 
GunData "1" *--> "gunMeleeData 1" GunMeleeData 
GunData  ..>  GunMeleeData : «create»
GunData  ..>  GunRecoil : «create»
GunData "1" *--> "recoil 1" GunRecoil 
GunData "1" *--> "reloadData 1" GunReloadData 
GunData  ..>  GunReloadData : «create»
GunData  ..>  IGun 
GunData  ..>  IGunOperator 
GunData "1" *--> "inaccuracy *" InaccuracyType 
GunData "1" *--> "moveSpeed 1" MoveSpeed 
GunData  ..>  MoveSpeed : «create»
GunData  ..>  RpmModifier 
GunDisplay "1" *--> "ammoCountStyle 1" AmmoCountStyle 
GunDisplay  ..>  ControllableData 
GunDisplay "1" *--> "damageStyle 1" DamageStyle 
GunDisplay "1" *--> "defaultAnimationType 1" DefaultAnimationType 
GunDisplay  ..>  FireMode 
GunDisplay "1" *--> "gunAmmo 1" GunAmmo 
GunDisplay "1" *--> "gunLod 1" GunLod 
GunDisplay "1" *--> "transform 1" GunTransform 
GunDisplay  ..>  IDisplay 
GunDisplay "1" *--> "laserConfig 1" LaserConfig 
GunDisplay  ..>  LayerGunShow : «create»
GunDisplay "1" *--> "hotbarShow *" LayerGunShow 
GunDisplay "1" *--> "muzzleFlash 1" MuzzleFlash 
GunDisplay "1" *--> "shellEjection 1" ShellEjection 
GunDisplay "1" *--> "textShows *" TextShow 
GunDisplayInstance "1" *--> "ammoCountStyle 1" AmmoCountStyle 
GunDisplayInstance "1" *--> "particle 1" AmmoParticle 
GunDisplayInstance  ..>  AnimationController : «create»
GunDisplayInstance  ..>  AnimationStructure 
GunDisplayInstance  ..>  Animations 
GunDisplayInstance  ..>  BedrockAnimationFile 
GunDisplayInstance "1" *--> "gunModel 1" BedrockGunModel 
GunDisplayInstance  ..>  BedrockGunModel : «create»
GunDisplayInstance  ..>  BedrockModelPOJO 
GunDisplayInstance  ..>  BedrockVersion 
GunDisplayInstance  ..>  ClientAssetsManager 
GunDisplayInstance  ..>  ColorHex 
GunDisplayInstance  ..>  ControllableData 
GunDisplayInstance "1" *--> "damageStyle 1" DamageStyle 
GunDisplayInstance  ..>  DefaultAnimationType 
GunDisplayInstance  ..>  FireMode 
GunDisplayInstance  ..>  GunAmmo 
GunDisplayInstance  ..>  GunAnimationStateContext 
GunDisplayInstance  ..>  GunDisplay 
GunDisplayInstance  ..>  GunDisplayInstance : «create»
GunDisplayInstance  ..>  GunLod 
GunDisplayInstance  ..>  GunMod 
GunDisplayInstance  ..>  GunModelTypeManager 
GunDisplayInstance "1" *--> "transform 1" GunTransform 
GunDisplayInstance  ..>  InternalAssetLoader 
GunDisplayInstance "1" *--> "laserConfig 1" LaserConfig 
GunDisplayInstance "1" *--> "offhandShow 1" LayerGunShow 
GunDisplayInstance  ..>  LayerGunShow : «create»
GunDisplayInstance "1" *--> "animationStateMachine 1" LuaAnimationStateMachine~T~ 
GunDisplayInstance  ..>  LuaStateMachineFactory~T~ : «create»
GunDisplayInstance "1" *--> "muzzleFlash 1" MuzzleFlash 
GunDisplayInstance  ..>  ObjectAnimation : «create»
GunDisplayInstance "1" *--> "shellEjection 1" ShellEjection 
GunDisplayInstance  ..>  SoundManager 
GunDisplayInstance  ..>  TextShow 
GunDrawEvent  ..>  GunDrawEvent 
GunDrawEvent  ..>  KubeJSGunEventPoster~E~ 
GunDrawEventJS  ..>  GunDrawEvent 
GunDrawEventJS  ..>  GunDrawWrapper 
GunDrawEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  GunDrawEventJS 
GunDrawEventJS  ..>  TimelessForgeEventWrappers 
GunDrawWrapper  -->  ForgeEventWrapper~E~ 
GunDrawWrapper  ..>  GunDrawEvent 
TimelessForgeEventWrappers  -->  GunDrawWrapper 
GunEventJS~E~  ..>  ForgeEventWrapper~E~ 
GunEventJS~E~  ..>  GunEventJS~E~ 
GunKubeJSEvents  -->  GunEventJS~E~ 
GunEventJS~E~  ..>  IGun 
GunEventJS~E~  ..>  TimelessForgeEventWrappers 
GunFinishReloadEvent  ..>  GunFinishReloadEvent 
GunFinishReloadEvent  ..>  KubeJSGunEventPoster~E~ 
GunFinishReloadEventJS  -->  GunEventJS~E~ 
GunFinishReloadEventJS  ..>  GunFinishReloadEvent 
GunFinishReloadEventJS  ..>  GunFinishReloadWrapper 
GunKubeJSEvents  -->  GunFinishReloadEventJS 
GunFinishReloadEventJS  ..>  TimelessForgeEventWrappers 
GunFinishReloadWrapper  -->  ForgeEventWrapper~E~ 
GunFinishReloadWrapper  ..>  GunFinishReloadEvent 
TimelessForgeEventWrappers  -->  GunFinishReloadWrapper 
GunFireEvent  ..>  GunFireEvent 
GunFireEvent  ..>  GunShootEvent 
GunFireEvent  ..>  KubeJSGunEventPoster~E~ 
GunFireEventJS  -->  GunEventJS~E~ 
GunFireEventJS  ..>  GunFireEvent 
GunFireEventJS  ..>  GunFireWrapper 
GunKubeJSEvents  -->  GunFireEventJS 
GunFireEventJS  ..>  TimelessForgeEventWrappers 
GunFireSelectEvent  ..>  GunFireSelectEvent 
GunFireSelectEvent  ..>  KubeJSGunEventPoster~E~ 
GunFireSelectEventJS  -->  GunEventJS~E~ 
GunFireSelectEventJS  ..>  GunFireSelectEvent 
GunFireSelectEventJS  ..>  GunFireSelectWrapper 
GunKubeJSEvents  -->  GunFireSelectEventJS 
GunFireSelectEventJS  ..>  TimelessForgeEventWrappers 
GunFireSelectWrapper  -->  ForgeEventWrapper~E~ 
GunFireSelectWrapper  ..>  GunFireSelectEvent 
TimelessForgeEventWrappers  -->  GunFireSelectWrapper 
GunFireWrapper  -->  ForgeEventWrapper~E~ 
GunFireWrapper  ..>  GunFireEvent 
TimelessForgeEventWrappers  -->  GunFireWrapper 
GunHudOverlay  ..>  AmmoCountStyle 
GunHudOverlay  ..>  AttachmentDataUtils 
GunHudOverlay  ..>  Bolt 
GunHudOverlay  ..>  ClientGunIndex 
GunHudOverlay  ..>  FireMode 
GunHudOverlay  ..>  GunData 
GunHudOverlay  ..>  GunDisplayInstance 
GunHudOverlay  ..>  GunHudOverlay 
GunHudOverlay  ..>  GunMod 
GunHudOverlay  ..>  GunReloadData 
GunHudOverlay  ..>  IAmmo 
GunHudOverlay  ..>  IAmmoBox 
GunHudOverlay  ..>  IClientPlayerGunOperator 
GunHudOverlay  ..>  IGun 
GunHudOverlay  ..>  IGunOperator 
GunHudOverlay  ..>  RenderConfig 
GunHudOverlay  ..>  TimelessAPI 
GunItemBuilder  ..>  AbstractGunItem 
GunItemBuilder  ..>  AttachmentItemBuilder 
GunItemBuilder  ..>  AttachmentType 
GunItemBuilder "1" *--> "fireMode 1" FireMode 
GunItemBuilder  ..>  GunItemBuilder : «create»
GunItemBuilder  ..>  GunItemManager 
GunItemBuilder  ..>  IGun 
GunItemBuilder  ..>  ModItems 
GunItemBuilder  ..>  TimelessAPI 
GunItemDataAccessor  ..>  AttachmentItemBuilder 
GunItemDataAccessor  ..>  AttachmentItemDataAccessor 
GunItemDataAccessor  ..>  AttachmentType 
GunItemDataAccessor  ..>  ClientAttachmentIndex 
GunItemDataAccessor  ..>  CommonGunIndex 
GunItemDataAccessor  ..>  DefaultAssets 
GunItemDataAccessor  ..>  FireMode 
GunItemDataAccessor  ..>  GunData 
GunItemDataAccessor  ..>  GunDisplayInstance 
GunItemDataAccessor  ..>  GunItemDataAccessor 
GunItemDataAccessor  ..>  IAttachment 
GunItemDataAccessor  -->  IGun 
GunItemDataAccessor  ..>  ModDataComponents 
GunItemDataAccessor  ..>  TimelessAPI 
GunItemManager  ..>  AbstractGunItem 
GunItemRendererWrapper  -->  AnimateGeoItemRenderer~M, CTX~ 
GunItemRendererWrapper  ..>  AnimationStateMachine~T~ 
GunItemRendererWrapper  ..>  BedrockAnimatedModel 
GunItemRendererWrapper "1" *--> "lastModel 1" BedrockGunModel 
GunItemRendererWrapper  ..>  BedrockPart 
GunItemRendererWrapper  ..>  BeforeRenderHandEvent 
GunItemRendererWrapper  ..>  CameraSetupEvent 
GunItemRendererWrapper  ..>  FirstPersonRenderGunEvent 
GunItemRendererWrapper  ..>  GunAnimationConstant 
GunItemRendererWrapper  ..>  GunAnimationStateContext : «create»
GunItemRendererWrapper  ..>  GunDisplayInstance 
GunItemRendererWrapper  ..>  GunItemRendererWrapper 
GunItemRendererWrapper  ..>  IClientPlayerGunOperator 
GunItemRendererWrapper  ..>  IGun 
GunItemRendererWrapper  ..>  ItemAnimationStateContext 
GunItemRendererWrapper  ..>  KeepingItemRenderer 
GunItemRendererWrapper  ..>  LuaAnimationStateMachine~T~ 
GunItemRendererWrapper  ..>  MathUtil 
GunItemRendererWrapper  ..>  MuzzleFlashRender 
GunItemRendererWrapper  ..>  RefitTransform 
GunItemRendererWrapper  ..>  RenderDistance 
GunItemRendererWrapper  ..>  SecondOrderDynamics 
GunItemRendererWrapper  ..>  ShellRender 
GunItemRendererWrapper "1" *--> "SLOT_GUN_MODEL 1" SlotModel 
GunItemRendererWrapper  ..>  SlotModel : «create»
GunItemRendererWrapper  ..>  TimelessAPI 
GunItemRendererWrapper  ..>  TransformScale 
ControllableInner  -->  GunKeyConflict 
GunKeyConflict  ..>  IGun 
GunMeleeData "1" *--> "defaultMeleeData 1" GunDefaultMeleeData 
GunMeleeEvent  ..>  GunMeleeEvent 
GunMeleeEvent  ..>  KubeJSGunEventPoster~E~ 
GunMeleeEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  GunMeleeEventJS 
GunMeleeEventJS  ..>  GunMeleeEvent 
GunMeleeEventJS  ..>  GunMeleeWrapper 
GunMeleeEventJS  ..>  TimelessForgeEventWrappers 
GunMeleeWrapper  -->  ForgeEventWrapper~E~ 
GunMeleeWrapper  ..>  GunMeleeEvent 
TimelessForgeEventWrappers  -->  GunMeleeWrapper 
GunMod  ..>  CommonConfig 
GunMod  ..>  GunMod 
GunMod  ..>  ModBlocks 
GunMod  ..>  ModCreativeTabs 
GunMod  ..>  ModDataComponents 
GunMod  ..>  ModEntities 
GunMod  ..>  ModItems 
GunMod  ..>  ModParticles 
GunMod  ..>  ModRecipe 
GunMod  ..>  ServerConfig 
GunModPlugin  ..>  AttachmentQueryCategory : «create»
GunModPlugin  ..>  AttachmentQueryEntry 
GunModPlugin  ..>  BlockItemBuilder 
GunModPlugin  ..>  GunItemManager 
GunModPlugin  ..>  GunMod 
GunModPlugin  ..>  GunModSubtype 
GunModPlugin  ..>  GunSmithTableCategory : «create»
GunModPlugin  ..>  GunSmithTableRecipe 
GunModPlugin  ..>  GunSmithTableResult 
GunModPlugin  ..>  ModItems 
GunModPlugin  ..>  ModRecipe 
GunModPlugin  ..>  TimelessAPI 
GunModSubtype  ..>  IAmmo 
GunModSubtype  ..>  IAmmoBox 
GunModSubtype  ..>  IAttachment 
GunModSubtype  ..>  IBlock 
GunModelTypeManager  ..>  BedrockGunModel 
GunModelTypeManager  ..>  BedrockModelPOJO 
GunModelTypeManager  ..>  BedrockVersion 
GunNbtFactory  ..>  AbstractGunItem 
GunNbtFactory  ..>  AttachmentItem 
GunNbtFactory  ..>  AttachmentNbtFactory : «create»
GunNbtFactory  ..>  AttachmentType 
GunNbtFactory "1" *--> "fireMode 1" FireMode 
GunNbtFactory  ..>  GunNbtFactory 
GunNbtFactory  ..>  IGun 
GunNbtFactory  -->  TimelessItemNbtFactory~T, S~ 
GunNbtFactory  ..>  TimelessItemType 
GunPackLoader  -->  GunPack 
GunPackList "1" *--> "gunPackList *" Checkbox 
GunPackList  ..>  ClientAssetsManager 
GunPackList  ..>  Entry : «create»
GunPackList  ..>  GunPackList 
GunPackList "1" *--> "parent 1" GunSmithTableScreen 
GunPackList  ..>  PackInfo 
GunPackLoader  ..>  CommonAssetsManager 
GunPackLoader  ..>  ExtraEntry 
GunPackLoader  ..>  GetJarResources 
GunPackLoader  ..>  GunMod 
GunPackLoader  ..>  GunPack : «create»
GunPackLoader  ..>  GunPackLoader 
GunPackLoader  ..>  PackMeta 
GunPackLoader  ..>  PreLoadConfig 
GunPackLoader  ..>  ResourceManager 
GunPackProgressScreen  ..>  GunPackProgressScreen 
GunProperties  ..>  DistanceDamagePair 
GunProperties  ..>  ExplosionData 
GunProperties  ..>  ExtraDamage 
GunProperties "1" *--> "ADS_TIME 1" GunProperty~T~ 
GunProperties  ..>  Ignite 
GunProperties  ..>  InaccuracyModifier 
GunProperties  ..>  InaccuracyType 
GunProperties  ..>  MoveSpeed 
GunProperties  ..>  ParameterizedCachePair~L, R~ 
GunProperty~T~  ..>  GunProperty~T~ : «create»
GunPropertyDiagrams  ..>  AttachmentCacheProperty 
GunPropertyDiagrams  ..>  AttachmentDataUtils 
GunPropertyDiagrams  ..>  AttachmentPropertyManager 
GunPropertyDiagrams  ..>  Bolt 
GunPropertyDiagrams  ..>  DiagramsData 
GunPropertyDiagrams  ..>  FireMode 
GunPropertyDiagrams  ..>  GunData 
GunPropertyDiagrams  ..>  GunPropertyDiagrams 
GunPropertyDiagrams  ..>  IAttachmentModifier~T, K~ 
GunPropertyDiagrams  ..>  IGun 
GunPropertyDiagrams  ..>  IGunOperator 
GunPropertyDiagrams  ..>  TimelessAPI 
GunRecoil  ..>  GunRecoil 
GunRecoil "1" *--> "pitch *" GunRecoilKeyFrame 
GunRecoilKeyFrame  ..>  GunRecoilKeyFrame 
GunRefitScreen  ..>  AttachmentType 
GunRefitScreen  ..>  ClientAttachmentIndex 
GunRefitScreen  ..>  ClientMessageLaserColor : «create»
GunRefitScreen  ..>  ClientMessageRefitGun : «create»
GunRefitScreen  ..>  ClientMessageUnloadAttachment : «create»
GunRefitScreen  ..>  FlatColorButton : «create»
GunRefitScreen  ..>  GunAttachmentSlot : «create»
GunRefitScreen  ..>  GunDisplayInstance 
GunRefitScreen  ..>  GunMod 
GunRefitScreen  ..>  GunPropertyDiagrams 
GunRefitScreen  ..>  GunRefitScreen 
GunRefitScreen  ..>  HSVSliderGroup : «create»
GunRefitScreen  ..>  IAttachment 
GunRefitScreen  ..>  IClientPlayerGunOperator 
GunRefitScreen  ..>  IComponentTooltip 
GunRefitScreen  ..>  IGun 
GunRefitScreen  ..>  IStackTooltip 
GunRefitScreen  ..>  InventoryAttachmentSlot : «create»
GunRefitScreen  ..>  NetworkHandler 
GunRefitScreen  ..>  RefitTransform 
GunRefitScreen  ..>  RefitTurnPageButton : «create»
GunRefitScreen  ..>  RefitUnloadButton : «create»
GunRefitScreen  ..>  SoundManager 
GunRefitScreen  ..>  SoundPlayManager 
GunRefitScreen  ..>  TimelessAPI 
GunReloadData "1" *--> "type 1" FeedType 
GunReloadData "1" *--> "feed 1" GunReloadTime 
GunReloadData  ..>  GunReloadTime : «create»
GunReloadEvent  ..>  GunReloadEvent 
GunReloadEvent  ..>  KubeJSGunEventPoster~E~ 
GunReloadEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  GunReloadEventJS 
GunReloadEventJS  ..>  GunReloadEvent 
GunReloadEventJS  ..>  GunReloadWrapper 
GunReloadEventJS  ..>  TimelessForgeEventWrappers 
GunReloadWrapper  -->  ForgeEventWrapper~E~ 
GunReloadWrapper  ..>  GunReloadEvent 
TimelessForgeEventWrappers  -->  GunReloadWrapper 
GunResult  ..>  AttachmentType 
GunShootEvent  ..>  GunFireEvent 
GunShootEvent  ..>  GunShootEvent 
GunShootEvent  ..>  KubeJSGunEventPoster~E~ 
GunShootEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  GunShootEventJS 
GunShootEventJS  ..>  GunShootEvent 
GunShootEventJS  ..>  GunShootWrapper 
GunShootEventJS  ..>  TimelessForgeEventWrappers 
GunShootWrapper  -->  ForgeEventWrapper~E~ 
GunShootWrapper  ..>  GunShootEvent 
TimelessForgeEventWrappers  -->  GunShootWrapper 
GunSmithTableBlockA  -->  AbstractGunSmithTableBlock 
GunSmithTableBlockB  -->  AbstractGunSmithTableBlock 
GunSmithTableBlockB  ..>  GunSmithTableBlockB 
GunSmithTableBlockC  -->  AbstractGunSmithTableBlock 
GunSmithTableBlockEntity  ..>  DefaultAssets 
GunSmithTableBlockEntity  ..>  GunSmithTableBlockEntity 
GunSmithTableBlockEntity  ..>  GunSmithTableMenu : «create»
GunSmithTableBlockEntity  ..>  ModBlocks 
GunSmithTableCategory  ..>  GunSmithTableCategory 
GunSmithTableCategory  ..>  GunSmithTableIngredient 
GunSmithTableCategory  ..>  GunSmithTableRecipe 
GunSmithTableIngredientSerializer  ..>  GunSmithTableIngredient : «create»
GunSmithTableItem  ..>  BlockItemBuilder 
GunSmithTableItem  ..>  BlockItemDataAccessor 
GunSmithTableItem  ..>  BlockItemTooltip : «create»
GunSmithTableItem  ..>  ClientBlockIndex 
GunSmithTableItem  ..>  GunSmithTableItemRenderer : «create»
GunSmithTableItem  ..>  TimelessAPI 
GunSmithTableItemRenderer  ..>  BedrockModel 
GunSmithTableItemRenderer  ..>  ClientBlockIndex 
GunSmithTableItemRenderer  ..>  GunSmithTableRenderer 
GunSmithTableItemRenderer "1" *--> "SLOT_BLOCK_MODEL 1" SlotModel 
GunSmithTableItemRenderer  ..>  SlotModel : «create»
GunSmithTableMenu  ..>  CommonBlockIndex 
GunSmithTableMenu  ..>  DefaultAssets 
GunSmithTableMenu  ..>  GunSmithTableIngredient 
GunSmithTableMenu  ..>  GunSmithTableMenu : «create»
GunSmithTableMenu  ..>  GunSmithTableRecipe 
GunSmithTableMenu  ..>  NetworkHandler 
GunSmithTableMenu "1" *--> "filter 1" RecipeFilter 
GunSmithTableMenu  ..>  ServerMessageCraft : «create»
GunSmithTableMenu  ..>  SyncConfig 
GunSmithTableMenu  ..>  TimelessAPI 
GunSmithTableRecipe "1" *--> "inputs *" GunSmithTableIngredient 
GunSmithTableRecipe  ..>  GunSmithTableRecipe 
GunSmithTableRecipe "1" *--> "result 1" GunSmithTableResult 
GunSmithTableRecipe  ..>  ModRecipe 
GunSmithTableRecipe  ..>  TableRecipe 
GunSmithTableRenderer  ..>  AbstractGunSmithTableBlock 
GunSmithTableRenderer  ..>  BedrockModel 
GunSmithTableRenderer  ..>  ClientBlockIndex 
GunSmithTableRenderer  ..>  DefaultAssets 
GunSmithTableRenderer  ..>  GunSmithTableBlockEntity 
GunSmithTableRenderer  ..>  GunSmithTableRenderer 
GunSmithTableRenderer  ..>  IBlock 
GunSmithTableRenderer  ..>  TimelessAPI 
GunSmithTableResult  ..>  GunSmithTableResult 
GunSmithTableResult "1" *--> "raw 1" RawGunTableResult 
GunSmithTableResult  ..>  TabConfig 
GunSmithTableResultComponents  ..>  GunSmithTableResultInfo 
GunSmithTableResultComponents  ..>  TimelessRecipeJS 
GunSmithTableResultInfo  ..>  GunSmithTableResult 
GunSmithTableResultInfo  ..>  GunSmithTableResultInfo : «create»
GunSmithTableResultInfo  ..>  ItemIndexInfo 
GunSmithTableResultInfo  ..>  OutputGroupName 
GunSmithTableResultInfo  ..>  TimelessItemWrapper 
GunSmithTableResultSerializer  ..>  CommonAssetsManager 
GunSmithTableResultSerializer  ..>  GunMod 
GunSmithTableResultSerializer  ..>  GunResult 
GunSmithTableResultSerializer  ..>  GunSmithTableResult : «create»
GunSmithTableResultSerializer  ..>  GunSmithTableResultSerializer 
GunSmithTableResultSerializer  ..>  RawGunTableResult : «create»
GunSmithTableResultSerializer  ..>  TabConfig 
GunSmithTableScreen  ..>  ClientAssetsManager 
GunSmithTableScreen  ..>  ClientMessageCraft : «create»
GunSmithTableScreen  ..>  DefaultAssets 
GunSmithTableScreen  ..>  FlatColorButton : «create»
GunSmithTableScreen  ..>  GunMod 
GunSmithTableScreen  ..>  GunPackList : «create»
GunSmithTableScreen "1" *--> "filterList 1" GunPackList 
GunSmithTableScreen  ..>  GunSmithTableIngredient 
GunSmithTableScreen  ..>  GunSmithTableMenu 
GunSmithTableScreen "1" *--> "selectedRecipe 1" GunSmithTableRecipe 
GunSmithTableScreen  ..>  GunSmithTableResult 
GunSmithTableScreen  ..>  GunSmithTableScreen 
GunSmithTableScreen  ..>  IAmmo 
GunSmithTableScreen  ..>  IAttachment 
GunSmithTableScreen  ..>  IGun 
GunSmithTableScreen  ..>  ModRecipe 
GunSmithTableScreen  ..>  NetworkHandler 
GunSmithTableScreen  ..>  PackInfo 
GunSmithTableScreen  ..>  RecipeFilter 
GunSmithTableScreen  ..>  RenderDistance 
GunSmithTableScreen  ..>  ResultButton : «create»
GunSmithTableScreen  ..>  SyncConfig 
GunSmithTableScreen "1" *--> "recipeKeys *" TabConfig 
GunSmithTableScreen  ..>  TabConfig : «create»
GunSmithTableScreen  ..>  TimelessAPI 
GunSmithTableScreen  ..>  TypeButton : «create»
GunSmithTableSerializer  ..>  CommonAssetsManager 
GunSmithTableSerializer  ..>  GunSmithTableIngredient : «create»
GunSmithTableSerializer  ..>  GunSmithTableRecipe : «create»
GunSmithTableSerializer  ..>  GunSmithTableResult : «create»
GunSmithTableSerializer  ..>  TableRecipe 
GunSoundInstance  ..>  ClientAssetsManager 
GunSoundInstance  ..>  SoundAssetsManager 
GunSoundInstance  ..>  SoundData 
GunTooltip "1" *--> "gunIndex 1" CommonGunIndex 
GunTooltip "1" *--> "iGun 1" IGun 
GunTooltipPart  ..>  ModDataComponents 
GunTransform  ..>  GunTransform : «create»
GunTransform "1" *--> "scale 1" TransformScale 
HSVSliderGroup "1" *--> "type 1" AttachmentType 
HSVSliderGroup  ..>  HSVSliderGroup 
HSVSliderGroup  ..>  IAttachment 
HSVSliderGroup  ..>  IGun 
HSVSliderGroup "1" *--> "hueSlider 1" LaserColorSlider 
HSVSliderGroup  ..>  LaserColorSlider : «create»
HSVSliderGroup  ..>  LaserColorUtil 
HeadShotAABBConfigRead  ..>  HeadShotAABBConfigRead 
HeadShotAABBConfigRead  ..>  SyncConfig 
HeadShotJsonProperty  ..>  AttachmentPropertyManager 
HeadShotModifier  -->  HeadShotJsonProperty 
HeadShotJsonProperty  -->  JsonProperty~T~ 
HeadShotJsonProperty  ..>  Modifier 
HeadShotModifier  ..>  AttachmentCacheProperty 
HeadShotModifier  ..>  AttachmentPropertyManager 
HeadShotModifier  ..>  BulletData 
HeadShotModifier  ..>  CacheValue~T~ : «create»
HeadShotModifier  ..>  CommonAssetsManager 
HeadShotModifier  ..>  Data 
HeadShotModifier  ..>  DiagramsData : «create»
HeadShotModifier  ..>  ExtraDamage 
HeadShotModifier  ..>  FireMode 
HeadShotModifier  ..>  GunData 
HeadShotModifier  ..>  GunFireModeAdjustData 
HeadShotModifier  ..>  GunProperties 
HeadShotModifier  ..>  GunProperty~T~ 
HeadShotModifier  ..>  HeadShotJsonProperty : «create»
HeadShotModifier  ..>  HeadShotModifier 
HeadShotModifier  ..>  IAttachmentModifier~T, K~ 
HeadShotModifier  ..>  IGun 
HeadShotModifier  ..>  JsonProperty~T~ 
HeadShotModifier  ..>  Modifier 
HeadShotModifier  ..>  SyncConfig 
HeatBarOverlay  ..>  ClientGunIndex 
HeatBarOverlay  ..>  GunData 
HeatBarOverlay  ..>  GunDisplayInstance 
HeatBarOverlay  ..>  GunHeatData 
HeatBarOverlay  ..>  GunMod 
HeatBarOverlay  ..>  HeatBarOverlay 
HeatBarOverlay  ..>  IClientPlayerGunOperator 
HeatBarOverlay  ..>  IGun 
HeatBarOverlay  ..>  RenderConfig 
HeatBarOverlay  ..>  TimelessAPI 
HideTooltipPartCommand  ..>  GunTooltipPart 
HideTooltipPartCommand  ..>  HideTooltipPartCommand 
HideTooltipPartCommand  ..>  IGun 
HitboxHelper  ..>  HitboxHelper 
HitboxHelper  ..>  ITargetEntity 
HitboxHelper  ..>  OtherConfig 
HitboxHelperEvent  ..>  HitboxHelper 
HitboxHelperEvent  ..>  OtherConfig 
HumanoidModelMixin~T~  ..>  InnerThirdPersonManager 
HumanoidOffhandRender  ..>  HumanoidOffhandRender 
HumanoidOffhandRender  ..>  IGun 
HumanoidOffhandRender  ..>  LayerGunShow 
HumanoidOffhandRender  ..>  MathUtil 
HumanoidOffhandRender  ..>  TimelessAPI 
IAmmo  ..>  IAmmo 
IAttachment  ..>  AttachmentType 
IAttachment  ..>  IAttachment 
IAttachmentModifier~T, K~  ..>  AttachmentCacheProperty 
IAttachmentModifier~T, K~  ..>  CacheValue~T~ 
IAttachmentModifier~T, K~  ..>  DiagramsData 
IAttachmentModifier~T, K~  ..>  GunData 
IAttachmentModifier~T, K~  ..>  JsonProperty~T~ 
IClientPlayerGunOperator  ..>  IClientPlayerGunOperator 
IClientPlayerGunOperator  ..>  LocalPlayerDataHolder 
IClientPlayerGunOperator  ..>  ShootResult 
ICommonResourceProvider  ..>  AttachmentData 
ICommonResourceProvider  ..>  BlockData 
ICommonResourceProvider  ..>  CommonAmmoIndex 
ICommonResourceProvider  ..>  CommonAttachmentIndex 
ICommonResourceProvider  ..>  CommonBlockIndex 
ICommonResourceProvider  ..>  CommonGunIndex 
ICommonResourceProvider  ..>  GunData 
ICommonResourceProvider  ..>  RecipeFilter 
IFilter~T~  ..>  IFilter~T~ 
IGun  ..>  AttachmentType 
IGun  ..>  DefaultAssets 
IGun  ..>  FireMode 
IGun  ..>  IGun 
IGunOperator  ..>  AttachmentCacheProperty 
IGunOperator  ..>  IGunOperator 
IGunOperator  ..>  ReloadState 
IGunOperator  ..>  ShootResult 
IGunOperator  ..>  ShooterDataHolder 
IMessage  ..>  IMessage 
INetworkCacheReloadListener  ..>  DataType 
Ignite  ..>  Ignite 
IgniteJsonProperty  ..>  Ignite 
IgniteModifier  -->  IgniteJsonProperty 
IgniteJsonProperty  -->  JsonProperty~T~ 
IgniteModifier  ..>  AttachmentPropertyManager 
IgniteModifier  ..>  BulletData 
IgniteModifier  ..>  CacheValue~T~ : «create»
IgniteModifier  ..>  CommonAssetsManager 
IgniteModifier  ..>  Data 
IgniteModifier  ..>  GunData 
IgniteModifier  ..>  GunProperties 
IgniteModifier  ..>  GunProperty~T~ 
IgniteModifier  ..>  IAttachmentModifier~T, K~ 
IgniteModifier  ..>  Ignite : «create»
IgniteModifier  ..>  IgniteJsonProperty : «create»
IgniteModifier  ..>  IgniteModifier 
IgniteModifier  ..>  JsonProperty~T~ 
IgniteSerializer  ..>  Ignite : «create»
InaccuracyJsonProperty  ..>  AttachmentPropertyManager 
InaccuracyJsonProperty  ..>  InaccuracyJsonProperty 
InaccuracyModifier  -->  InaccuracyJsonProperty 
InaccuracyJsonProperty  ..>  InaccuracyType 
InaccuracyJsonProperty  -->  JsonProperty~T~ 
InaccuracyJsonProperty  ..>  Modifier 
InaccuracyModifier  ..>  AttachmentCacheProperty 
InaccuracyModifier  ..>  AttachmentPropertyManager 
InaccuracyModifier  ..>  CacheValue~T~ : «create»
InaccuracyModifier  ..>  CommonAssetsManager 
InaccuracyModifier  ..>  Data 
InaccuracyModifier  ..>  DiagramsData : «create»
InaccuracyModifier  ..>  FireMode 
InaccuracyModifier  ..>  GunData 
InaccuracyModifier  ..>  GunFireModeAdjustData 
InaccuracyModifier  ..>  GunProperties 
InaccuracyModifier  ..>  GunProperty~T~ 
InaccuracyModifier  ..>  IAttachmentModifier~T, K~ 
InaccuracyModifier  ..>  IGun 
InaccuracyModifier  ..>  InaccuracyJsonProperty : «create»
InaccuracyModifier  ..>  InaccuracyModifier 
InaccuracyModifier  ..>  InaccuracyType 
InaccuracyModifier  ..>  JsonProperty~T~ 
InaccuracyModifier  ..>  Modifier : «create»
InaccuracyType  ..>  HitboxHelper 
InaccuracyType  ..>  IGunOperator 
InaccuracyType  ..>  InaccuracyType 
VersionChecker  -->  Info 
InnerThirdPersonManager  ..>  GunDisplayInstance 
InnerThirdPersonManager  ..>  IGun 
InnerThirdPersonManager  ..>  IGunOperator 
InnerThirdPersonManager  ..>  IThirdPersonAnimation 
InnerThirdPersonManager  ..>  InnerThirdPersonManager 
InnerThirdPersonManager  ..>  PlayerAnimatorCompat 
InnerThirdPersonManager  ..>  ThirdPersonManager 
InnerThirdPersonManager  ..>  TimelessAPI 
InspectKey  ..>  IClientPlayerGunOperator 
InspectKey  ..>  InputExtraCheck 
InteractKey  ..>  IGun 
InteractKey  ..>  InputExtraCheck 
InteractKey  ..>  InteractKey 
InteractKey  ..>  InteractKeyConfigRead 
InteractKeyConfigRead  ..>  GunMod 
InteractKeyConfigRead  ..>  InteractKeyConfigRead 
InteractKeyConfigRead  ..>  SyncConfig 
InteractKeyConfigRead  ..>  Type 
InteractKeyTextOverlay  ..>  IGun 
InteractKeyTextOverlay  ..>  InteractKey 
InteractKeyTextOverlay  ..>  InteractKeyConfigRead 
InteractKeyTextOverlay  ..>  InteractKeyTextOverlay 
InteractKeyTextOverlay  ..>  RenderConfig 
InternalAssetLoader  ..>  Animations 
InternalAssetLoader  ..>  BedrockAnimationFile 
InternalAssetLoader "1" *--> "BEDROCK_MODELS *" BedrockModel 
InternalAssetLoader  ..>  BedrockModel : «create»
InternalAssetLoader  ..>  BedrockModelPOJO 
InternalAssetLoader  ..>  BedrockVersion 
InternalAssetLoader  ..>  ClientAssetsManager 
InternalAssetLoader  ..>  GunMod 
InternalAssetLoader  ..>  InternalAssetLoader 
InternalAssetLoader "1" *--> "defaultPistolAnimations *" ObjectAnimation 
AnimationModel  -->  Interpolation 
Interpolator  ..>  AnimationChannelContent 
Interpolator  ..>  Interpolator 
InterpolatorUtil  -->  InterpolatorType 
InterpolatorUtil  ..>  Interpolator 
InterpolatorUtil  ..>  InterpolatorType 
InterpolatorUtil  ..>  Linear : «create»
InterpolatorUtil  ..>  SLerp : «create»
InterpolatorUtil  ..>  Spline : «create»
InterpolatorUtil  ..>  Step : «create»
InventoryAttachmentSlot  ..>  GunRefitScreen 
InventoryAttachmentSlot  ..>  IStackTooltip 
InventoryEvent  ..>  GunMod 
InventoryEvent  ..>  IAnimationItem 
InventoryEvent  ..>  IClientPlayerGunOperator 
InventoryEvent  ..>  IGun 
InventoryEvent  ..>  SwapItemWithOffHand 
ItemInHandLayerMixin  ..>  HumanoidOffhandRender 
ItemInHandLayerMixin  ..>  IGun 
ItemInHandLayerMixin  ..>  MuzzleFlashRender 
ItemInHandLayerMixin  ..>  ShellRender 
ItemInHandRendererMixin  ..>  BeforeRenderHandEvent : «create»
ItemInHandRendererMixin  ..>  IGun 
ItemInHandRendererMixin  ..>  KeepingItemRenderer 
ItemIndexInfo  ..>  AmmoItemBuilder 
ItemIndexInfo  ..>  AttachmentItemBuilder 
ItemIndexInfo  ..>  BlockItemBuilder 
ItemIndexInfo  ..>  DefaultAssets 
ItemIndexInfo "1" *--> "DEFAULT 1" ItemIndexInfo 
ItemIndexInfo  ..>  ItemIndexInfo : «create»
ItemIndexInfo  ..>  TimelessItemNbtFactory~T, S~ 
TimelessItemWrapper  -->  ItemIndexInfo 
JsonDataManager~T~  ..>  GunMod 
JsonDataManager~T~  ..>  JsonDataManager~T~ 
JsonDataManager~T~  ..>  ResourceScanner 
JsonResourceLoader~T~  ..>  GunMod 
JsonResourceLoader~T~  ..>  JsonResourceLoader~T~ 
JsonResourceLoader~T~  ..>  TacPathVisitor : «create»
KeepingItemRenderer  ..>  KeepingItemRenderer 
KeyClothConfig  ..>  KeyConfig 
KeyClothConfig  ..>  OpenGunPackDirEntry : «create»
AnimationKeyframes  -->  Keyframe 
KillAmountOverlay  ..>  IClientPlayerGunOperator 
KillAmountOverlay  ..>  IGun 
KillAmountOverlay  ..>  RenderConfig 
KnockBackModifier  ..>  KnockBackModifier 
KnockbackChange  ..>  KnockBackModifier 
KnockbackJsonProperty  ..>  AttachmentPropertyManager 
KnockbackJsonProperty  -->  JsonProperty~T~ 
KnockbackModifier  -->  KnockbackJsonProperty 
KnockbackJsonProperty  ..>  Modifier 
KnockbackModifier  ..>  AttachmentCacheProperty 
KnockbackModifier  ..>  AttachmentPropertyManager 
KnockbackModifier  ..>  BulletData 
KnockbackModifier  ..>  CacheValue~T~ : «create»
KnockbackModifier  ..>  CommonAssetsManager 
KnockbackModifier  ..>  Data 
KnockbackModifier  ..>  DiagramsData : «create»
KnockbackModifier  ..>  FireMode 
KnockbackModifier  ..>  GunData 
KnockbackModifier  ..>  GunFireModeAdjustData 
KnockbackModifier  ..>  GunProperties 
KnockbackModifier  ..>  GunProperty~T~ 
KnockbackModifier  ..>  IAttachmentModifier~T, K~ 
KnockbackModifier  ..>  IGun 
KnockbackModifier  ..>  JsonProperty~T~ 
KnockbackModifier  ..>  KnockbackJsonProperty : «create»
KnockbackModifier  ..>  KnockbackModifier 
KnockbackModifier  ..>  Modifier 
KubeJSCustomGunItem  -->  ModernKineticGunItem 
BeamRenderer  -->  LaserBeamRenderState 
HSVSliderGroup  -->  LaserColorSlider 
LaserColorSlider "1" *--> "parent 1" HSVSliderGroup 
LaserColorUtil  ..>  ClientAttachmentIndex 
LaserColorUtil  ..>  GunDisplayInstance 
LaserColorUtil  ..>  IAttachment 
LaserColorUtil  ..>  IGun 
LaserColorUtil  ..>  LaserConfig 
LaserColorUtil  ..>  TimelessAPI 
LeftHandRender "1" *--> "bedrockGunModel 1" BedrockAnimatedModel 
LeftHandRender  ..>  BedrockModel 
LeftHandRender  ..>  IFunctionalRenderer 
LeftHandRender  ..>  RenderHelper 
LegacyPack  ..>  GunMod 
LegacyPack  ..>  LegacyPack 
PackConvertor  -->  LegacyPack 
LegacyPack  ..>  PackInfo 
LegacyPack  ..>  PackMeta : «create»
AnimationChannelContent  -->  LerpMode 
Linear "1" *--> "content 1" AnimationChannelContent 
Linear  ..>  Interpolator 
Linear  ..>  Linear 
LiteralFilter~T~  ..>  IFilter~T~ 
LivingEntityAim  ..>  AdsModifier 
LivingEntityAim  ..>  AttachmentItemDataAccessor 
LivingEntityAim  ..>  AttachmentType 
LivingEntityAim  ..>  CommonGunIndex 
LivingEntityAim  ..>  DefaultAssets 
LivingEntityAim  ..>  GunData 
LivingEntityAim  ..>  IGun 
LivingEntityAim  ..>  IGunOperator 
LivingEntityAim  ..>  ReloadState 
LivingEntityAim "1" *--> "data 1" ShooterDataHolder 
LivingEntityAim  ..>  StateType 
LivingEntityAim  ..>  TimelessAPI 
LivingEntityAmmoCheck  ..>  GunConfig 
LivingEntityBolt  ..>  AbstractGunItem 
LivingEntityBolt  ..>  Bolt 
LivingEntityBolt  ..>  CommonGunIndex 
LivingEntityBolt  ..>  IGun 
LivingEntityBolt  ..>  IGunOperator 
LivingEntityBolt "1" *--> "draw 1" LivingEntityDrawGun 
LivingEntityBolt "1" *--> "shoot 1" LivingEntityShoot 
LivingEntityBolt "1" *--> "data 1" ShooterDataHolder 
LivingEntityBolt  ..>  StateType 
LivingEntityBolt  ..>  TimelessAPI 
LivingEntityCrawl  ..>  IGun 
LivingEntityCrawl  ..>  LivingEntityCrawl 
LivingEntityCrawl "1" *--> "data 1" ShooterDataHolder 
LivingEntityCrawl  ..>  TimelessAPI 
LivingEntityDrawGun  ..>  AttachmentPropertyManager 
LivingEntityDrawGun  ..>  CommonGunIndex 
LivingEntityDrawGun  ..>  GunData 
LivingEntityDrawGun  ..>  GunDrawEvent : «create»
LivingEntityDrawGun  ..>  IGun 
LivingEntityDrawGun  ..>  LivingEntityDrawGun 
LivingEntityDrawGun  ..>  NetworkHandler 
LivingEntityDrawGun  ..>  ServerMessageGunDraw : «create»
LivingEntityDrawGun "1" *--> "data 1" ShooterDataHolder 
LivingEntityDrawGun  ..>  TimelessAPI 
LivingEntityFireSelect  ..>  AbstractGunItem 
LivingEntityFireSelect  ..>  AttachmentPropertyManager 
LivingEntityFireSelect  ..>  GunFireSelectEvent : «create»
LivingEntityFireSelect  ..>  IGun 
LivingEntityFireSelect  ..>  NetworkHandler 
LivingEntityFireSelect  ..>  ServerMessageGunFireSelect : «create»
LivingEntityFireSelect "1" *--> "data 1" ShooterDataHolder 
LivingEntityHeat  ..>  AbstractGunItem 
LivingEntityHeat "1" *--> "data 1" ShooterDataHolder 
LivingEntityMelee  ..>  AbstractGunItem 
LivingEntityMelee  ..>  AttachmentType 
LivingEntityMelee  ..>  CommonGunIndex 
LivingEntityMelee  ..>  DefaultAssets 
LivingEntityMelee  ..>  GunData 
LivingEntityMelee  ..>  GunDefaultMeleeData 
LivingEntityMelee  ..>  GunMeleeData 
LivingEntityMelee  ..>  GunMeleeEvent : «create»
LivingEntityMelee  ..>  IGun 
LivingEntityMelee "1" *--> "draw 1" LivingEntityDrawGun 
LivingEntityMelee  ..>  LivingEntityMelee 
LivingEntityMelee  ..>  MeleeData 
LivingEntityMelee  ..>  NetworkHandler 
LivingEntityMelee  ..>  ServerMessageGunMelee : «create»
LivingEntityMelee "1" *--> "data 1" ShooterDataHolder 
LivingEntityMelee  ..>  TimelessAPI 
LivingEntityMixin  ..>  AttachmentCacheProperty 
LivingEntityMixin  ..>  AttachmentPropertyManager 
LivingEntityMixin  ..>  IGunOperator 
LivingEntityMixin  ..>  KnockBackModifier 
LivingEntityMixin "1" *--> "tacz$aim 1" LivingEntityAim 
LivingEntityMixin  ..>  LivingEntityAim : «create»
LivingEntityMixin  ..>  LivingEntityAmmoCheck : «create»
LivingEntityMixin "1" *--> "tacz$ammoCheck 1" LivingEntityAmmoCheck 
LivingEntityMixin "1" *--> "tacz$bolt 1" LivingEntityBolt 
LivingEntityMixin  ..>  LivingEntityBolt : «create»
LivingEntityMixin "1" *--> "tacz$crawl 1" LivingEntityCrawl 
LivingEntityMixin  ..>  LivingEntityCrawl : «create»
LivingEntityMixin "1" *--> "tacz$draw 1" LivingEntityDrawGun 
LivingEntityMixin  ..>  LivingEntityDrawGun : «create»
LivingEntityMixin "1" *--> "tacz$fireSelect 1" LivingEntityFireSelect 
LivingEntityMixin  ..>  LivingEntityFireSelect : «create»
LivingEntityMixin  ..>  LivingEntityHeat : «create»
LivingEntityMixin "1" *--> "tacz$heat 1" LivingEntityHeat 
LivingEntityMixin  ..>  LivingEntityMelee : «create»
LivingEntityMixin "1" *--> "tacz$melee 1" LivingEntityMelee 
LivingEntityMixin  ..>  LivingEntityMixin 
LivingEntityMixin  ..>  LivingEntityReload : «create»
LivingEntityMixin "1" *--> "tacz$reload 1" LivingEntityReload 
LivingEntityMixin "1" *--> "tacz$shoot 1" LivingEntityShoot 
LivingEntityMixin  ..>  LivingEntityShoot : «create»
LivingEntityMixin "1" *--> "tacz$speed 1" LivingEntitySpeedModifier 
LivingEntityMixin  ..>  LivingEntitySpeedModifier : «create»
LivingEntityMixin  ..>  LivingEntitySprint : «create»
LivingEntityMixin "1" *--> "tacz$sprint 1" LivingEntitySprint 
LivingEntityMixin  ..>  ModSyncedEntityData 
LivingEntityMixin  ..>  ReloadState 
LivingEntityMixin  ..>  ShootResult 
LivingEntityMixin "1" *--> "tacz$data 1" ShooterDataHolder 
LivingEntityMixin  ..>  ShooterDataHolder : «create»
LivingEntityMixin  ..>  SyncedDataKey~E, T~ 
LivingEntityReload  ..>  AbstractGunItem 
LivingEntityReload  ..>  Bolt 
LivingEntityReload  ..>  GunReloadEvent : «create»
LivingEntityReload  ..>  IGun 
LivingEntityReload  ..>  IGunOperator 
LivingEntityReload "1" *--> "draw 1" LivingEntityDrawGun 
LivingEntityReload "1" *--> "shoot 1" LivingEntityShoot 
LivingEntityReload  ..>  NetworkHandler 
LivingEntityReload  ..>  ReloadState : «create»
LivingEntityReload  ..>  ServerMessageGunReload : «create»
LivingEntityReload "1" *--> "data 1" ShooterDataHolder 
LivingEntityReload  ..>  StateType 
LivingEntityReload  ..>  TimelessAPI 
LivingEntityShoot  ..>  AbstractGunItem 
LivingEntityShoot  ..>  Bolt 
LivingEntityShoot  ..>  BurstData 
LivingEntityShoot  ..>  CommonGunIndex 
LivingEntityShoot  ..>  FireMode 
LivingEntityShoot  ..>  GunData 
LivingEntityShoot  ..>  GunShootEvent : «create»
LivingEntityShoot  ..>  IGun 
LivingEntityShoot  ..>  IGunOperator 
LivingEntityShoot "1" *--> "draw 1" LivingEntityDrawGun 
LivingEntityShoot  ..>  LivingEntityShoot 
LivingEntityShoot  ..>  NetworkHandler 
LivingEntityShoot  ..>  ServerMessageGunShoot : «create»
LivingEntityShoot  ..>  ServerMessageSyncBaseTimestamp : «create»
LivingEntityShoot  ..>  ShootResult 
LivingEntityShoot "1" *--> "data 1" ShooterDataHolder 
LivingEntityShoot  ..>  StateType 
LivingEntityShoot  ..>  SyncConfig 
LivingEntityShoot  ..>  TimelessAPI 
LivingEntitySpeedModifier  ..>  AbstractGunItem 
LivingEntitySpeedModifier  ..>  AttachmentCacheProperty 
LivingEntitySpeedModifier  ..>  ExtraMovementModifier 
LivingEntitySpeedModifier  ..>  IGunOperator 
LivingEntitySpeedModifier  ..>  LivingEntitySpeedModifier 
LivingEntitySpeedModifier  ..>  MoveSpeed 
LivingEntitySpeedModifier "1" *--> "dataHolder 1" ShooterDataHolder 
LivingEntitySpeedModifier  ..>  StateType 
LivingEntitySpeedModifier  ..>  SyncConfig 
LivingEntitySpeedModifier  ..>  WeightModifier 
LivingEntitySprint  ..>  IGunOperator 
LivingEntitySprint  ..>  ReloadState 
LivingEntitySprint "1" *--> "data 1" ShooterDataHolder 
LivingEntitySprint  ..>  StateType 
LoadingConfigEvent  ..>  HeadShotAABBConfigRead 
LoadingConfigEvent  ..>  InteractKeyConfigRead 
LocalPlayerAim  ..>  AdsModifier 
LocalPlayerAim  ..>  AttachmentCacheProperty 
LocalPlayerAim  ..>  ClientMessagePlayerAim : «create»
LocalPlayerAim  ..>  GunData 
LocalPlayerAim  ..>  IGun 
LocalPlayerAim  ..>  IGunOperator 
LocalPlayerAim  ..>  LocalPlayerAim 
LocalPlayerAim "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerAim  ..>  NetworkHandler 
LocalPlayerAim  ..>  TimelessAPI 
LocalPlayerBolt  ..>  AnimationStateMachine~T~ 
LocalPlayerBolt  ..>  Bolt 
LocalPlayerBolt  ..>  ClientGunIndex 
LocalPlayerBolt  ..>  ClientMessagePlayerBoltGun : «create»
LocalPlayerBolt  ..>  GunAnimationConstant 
LocalPlayerBolt  ..>  GunData 
LocalPlayerBolt  ..>  IGun 
LocalPlayerBolt  ..>  IGunOperator 
LocalPlayerBolt  ..>  LocalPlayerBolt 
LocalPlayerBolt "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerBolt  ..>  NetworkHandler 
LocalPlayerBolt  ..>  SoundPlayManager 
LocalPlayerBolt  ..>  TimelessAPI 
LocalPlayerCrawl  ..>  ClientMessagePlayerCrawl : «create»
LocalPlayerCrawl  ..>  IGun 
LocalPlayerCrawl  ..>  LocalPlayerCrawl 
LocalPlayerCrawl  ..>  NetworkHandler 
LocalPlayerCrawl  ..>  TimelessAPI 
LocalPlayerDataHolder  ..>  IGunOperator 
LocalPlayerDataHolder  ..>  ReloadState 
LocalPlayerDataHolder  ..>  StateType 
LocalPlayerDraw  ..>  AnimateGeoItemRenderer~M, CTX~ 
LocalPlayerDraw  ..>  AttachmentPropertyManager 
LocalPlayerDraw  ..>  ClientMessagePlayerDrawGun : «create»
LocalPlayerDraw  ..>  GunDrawEvent : «create»
LocalPlayerDraw  ..>  IGun 
LocalPlayerDraw  ..>  IGunOperator 
LocalPlayerDraw "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerDraw  ..>  LocalPlayerDraw 
LocalPlayerDraw  ..>  NetworkHandler 
LocalPlayerDraw  ..>  SoundPlayManager 
LocalPlayerDraw  ..>  TimelessAPI 
LocalPlayerFireSelect  ..>  AbstractGunItem 
LocalPlayerFireSelect  ..>  AnimationStateMachine~T~ 
LocalPlayerFireSelect  ..>  AttachmentPropertyManager 
LocalPlayerFireSelect  ..>  ClientMessagePlayerFireSelect : «create»
LocalPlayerFireSelect  ..>  GunAnimationConstant 
LocalPlayerFireSelect  ..>  GunFireSelectEvent : «create»
LocalPlayerFireSelect  ..>  IGun 
LocalPlayerFireSelect "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerFireSelect  ..>  NetworkHandler 
LocalPlayerFireSelect  ..>  SoundPlayManager 
LocalPlayerFireSelect  ..>  TimelessAPI 
LocalPlayerInspect  ..>  AnimateGeoItemRenderer~M, CTX~ 
LocalPlayerInspect  ..>  Bolt 
LocalPlayerInspect  ..>  ClientGunIndex 
LocalPlayerInspect  ..>  GunAnimationConstant 
LocalPlayerInspect  ..>  GunData 
LocalPlayerInspect  ..>  IGun 
LocalPlayerInspect "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerInspect  ..>  SoundPlayManager 
LocalPlayerInspect  ..>  TimelessAPI 
LocalPlayerMelee  ..>  AnimationStateMachine~T~ 
LocalPlayerMelee  ..>  AttachmentType 
LocalPlayerMelee  ..>  ClientMessagePlayerMelee : «create»
LocalPlayerMelee  ..>  DefaultAssets 
LocalPlayerMelee  ..>  GunAnimationConstant 
LocalPlayerMelee  ..>  GunDefaultMeleeData 
LocalPlayerMelee  ..>  GunDisplayInstance 
LocalPlayerMelee  ..>  GunMeleeEvent : «create»
LocalPlayerMelee  ..>  IGun 
LocalPlayerMelee  ..>  IGunOperator 
LocalPlayerMelee "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerMelee  ..>  LocalPlayerMelee 
LocalPlayerMelee  ..>  MeleeData 
LocalPlayerMelee  ..>  NetworkHandler 
LocalPlayerMelee  ..>  SoundPlayManager 
LocalPlayerMelee  ..>  TimelessAPI 
LocalPlayerMixin  ..>  IClientPlayerGunOperator 
LocalPlayerMixin "1" *--> "tac$aim 1" LocalPlayerAim 
LocalPlayerMixin  ..>  LocalPlayerAim : «create»
LocalPlayerMixin  ..>  LocalPlayerBolt : «create»
LocalPlayerMixin "1" *--> "tac$bolt 1" LocalPlayerBolt 
LocalPlayerMixin  ..>  LocalPlayerCrawl : «create»
LocalPlayerMixin "1" *--> "tac$crawl 1" LocalPlayerCrawl 
LocalPlayerMixin  ..>  LocalPlayerDataHolder : «create»
LocalPlayerMixin "1" *--> "tac$data 1" LocalPlayerDataHolder 
LocalPlayerMixin "1" *--> "tac$draw 1" LocalPlayerDraw 
LocalPlayerMixin  ..>  LocalPlayerDraw : «create»
LocalPlayerMixin  ..>  LocalPlayerFireSelect : «create»
LocalPlayerMixin "1" *--> "tac$fireSelect 1" LocalPlayerFireSelect 
LocalPlayerMixin  ..>  LocalPlayerInspect : «create»
LocalPlayerMixin "1" *--> "tac$inspect 1" LocalPlayerInspect 
LocalPlayerMixin  ..>  LocalPlayerMelee : «create»
LocalPlayerMixin "1" *--> "tac$melee 1" LocalPlayerMelee 
LocalPlayerMixin  ..>  LocalPlayerMixin 
LocalPlayerMixin  ..>  LocalPlayerReload : «create»
LocalPlayerMixin "1" *--> "tac$reload 1" LocalPlayerReload 
LocalPlayerMixin "1" *--> "tac$shoot 1" LocalPlayerShoot 
LocalPlayerMixin  ..>  LocalPlayerShoot : «create»
LocalPlayerMixin "1" *--> "tac$sprint 1" LocalPlayerSprint 
LocalPlayerMixin  ..>  LocalPlayerSprint : «create»
LocalPlayerMixin  ..>  ShootResult 
LocalPlayerReload  ..>  AbstractGunItem 
LocalPlayerReload  ..>  AnimationStateMachine~T~ 
LocalPlayerReload  ..>  Bolt 
LocalPlayerReload  ..>  ClientGunIndex 
LocalPlayerReload  ..>  ClientMessagePlayerCancelReload : «create»
LocalPlayerReload  ..>  ClientMessagePlayerReloadGun : «create»
LocalPlayerReload  ..>  GunAnimationConstant 
LocalPlayerReload  ..>  GunData 
LocalPlayerReload  ..>  GunDisplayInstance 
LocalPlayerReload  ..>  GunReloadEvent : «create»
LocalPlayerReload  ..>  IGun 
LocalPlayerReload  ..>  IGunOperator 
LocalPlayerReload "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerReload  ..>  LocalPlayerReload 
LocalPlayerReload  ..>  LuaAnimationStateMachine~T~ 
LocalPlayerReload  ..>  NetworkHandler 
LocalPlayerReload  ..>  ReloadState 
LocalPlayerReload  ..>  SoundPlayManager 
LocalPlayerReload  ..>  StateType 
LocalPlayerReload  ..>  TimelessAPI 
LocalPlayerShoot  ..>  AnimationStateMachine~T~ 
LocalPlayerShoot  ..>  AttachmentCacheProperty 
LocalPlayerShoot  ..>  Bolt 
LocalPlayerShoot  ..>  BurstData 
LocalPlayerShoot  ..>  ClientGunIndex 
LocalPlayerShoot  ..>  ClientMessagePlayerShoot : «create»
LocalPlayerShoot  ..>  CommonGunIndex 
LocalPlayerShoot  ..>  FireMode 
LocalPlayerShoot  ..>  GunAnimationConstant 
LocalPlayerShoot  ..>  GunData 
LocalPlayerShoot  ..>  GunDisplayInstance 
LocalPlayerShoot  ..>  GunFireEvent : «create»
LocalPlayerShoot  ..>  GunShootEvent : «create»
LocalPlayerShoot  ..>  IClientPlayerGunOperator 
LocalPlayerShoot  ..>  IGun 
LocalPlayerShoot  ..>  IGunOperator 
LocalPlayerShoot "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerShoot  ..>  LocalPlayerShoot 
LocalPlayerShoot  ..>  NetworkHandler 
LocalPlayerShoot  ..>  ReloadState 
LocalPlayerShoot  ..>  ShootResult 
LocalPlayerShoot  ..>  SilenceModifier 
LocalPlayerShoot  ..>  SoundManager 
LocalPlayerShoot  ..>  SoundPlayManager 
LocalPlayerShoot  ..>  StateType 
LocalPlayerShoot  ..>  TimelessAPI 
LocalPlayerSprint  ..>  IGunOperator 
LocalPlayerSprint "1" *--> "data 1" LocalPlayerDataHolder 
LocalPlayerSprint  ..>  ReloadState 
LocalPlayerSprint  ..>  StateType 
LoginIndexHolder  ..>  LoginIndexHolder 
LuaAnimationConstant  ..>  AnimationConstant 
LuaAnimationConstant  ..>  LuaLibrary 
LuaAnimationConstant  ..>  ObjectAnimation 
LuaAnimationConstant  ..>  PlayType 
LuaAnimationState~T~  ..>  AnimationState~T~ 
LuaAnimationState~T~  ..>  AnimationStateContext 
LuaAnimationState~T~  ..>  LuaAnimationState~T~ : «create»
LuaAnimationState~T~  ..>  LuaStateMachineFactory~T~ 
LuaAnimationStateMachine~T~  ..>  AnimationController 
LuaAnimationStateMachine~T~  ..>  AnimationStateContext 
LuaAnimationStateMachine~T~  -->  AnimationStateMachine~T~ 
LuaAnimationStateMachine~T~  ..>  LuaStateMachineFactory~T~ 
LuaGunAnimationConstant  ..>  FireMode 
LuaGunAnimationConstant  ..>  GunAnimationConstant 
LuaGunAnimationConstant  ..>  LuaAnimationConstant 
LuaGunAnimationConstant  ..>  LuaLibrary 
LuaGunAnimationConstant  ..>  ReloadState 
LuaGunAnimationConstant  ..>  StateType 
LuaGunLogicConstant  ..>  FireMode 
LuaGunLogicConstant  ..>  LuaLibrary 
LuaGunLogicConstant  ..>  ReloadState 
LuaGunLogicConstant  ..>  StateType 
LuaNbtAccessor  ..>  FireMode 
LuaNbtAccessor  ..>  LuaNbtAccessor : «create»
LuaNbtAccessor  ..>  ModDataComponents 
LuaStateMachineFactory~T~ "1" *--> "controller 1" AnimationController 
LuaStateMachineFactory~T~  ..>  AnimationState~T~ 
LuaStateMachineFactory~T~  ..>  AnimationStateContext 
LuaStateMachineFactory~T~  ..>  AnimationStateMachine~T~ 
LuaStateMachineFactory~T~  ..>  LuaAnimationState~T~ : «create»
LuaStateMachineFactory~T~  ..>  LuaAnimationStateMachine~T~ : «create»
LuaStateMachineFactory~T~  ..>  LuaStateMachineFactory~T~ 
MathUtil  ..>  MathUtil 
EntityKineticBullet  -->  MaybeMultipartEntity 
MaybeMultipartEntity  ..>  MaybeMultipartEntity : «create»
Md5Utils  ..>  Md5Utils 
MeleeData "1" *--> "effects *" EffectData 
MeleeKey  ..>  IClientPlayerGunOperator 
MeleeKey  ..>  InputExtraCheck 
MeleeKey  ..>  MeleeKey 
MenuIntegration  ..>  AmmoClothConfig 
MenuIntegration  ..>  GunClothConfig 
MenuIntegration  ..>  KeyClothConfig 
MenuIntegration  ..>  MenuIntegration 
MenuIntegration  ..>  OtherClothConfig 
MenuIntegration  ..>  RenderClothConfig 
MenuIntegration  ..>  ZoomClothConfig 
ModAttributes  ..>  GunMod 
ModBlocks  ..>  GunMod 
ModCapabilities  ..>  DataHolder 
ModCapabilities  ..>  GunMod 
ModContainer  ..>  GunMod 
ModContainer  ..>  GunSmithTableMenu 
ModContainerScreen  ..>  GunSmithTableMenu 
ModContainerScreen  ..>  GunSmithTableScreen 
ModCreativeTabs  ..>  GunMod 
ModDamageTypes  ..>  GunMod 
ModDataComponents  ..>  FireMode 
ModDataComponents  ..>  GunMod 
ModEntities  ..>  EntityKineticBullet 
ModEntities  ..>  GunMod 
ModEntities  ..>  TargetMinecart 
ModEntitiesRender  ..>  EntityBulletRenderer 
ModEntitiesRender  ..>  EntityKineticBullet 
ModEntitiesRender  ..>  GunSmithTableBlockEntity 
ModEntitiesRender  ..>  GunSmithTableRenderer 
ModEntitiesRender  ..>  StatueBlockEntity 
ModEntitiesRender  ..>  StatueRenderer 
ModEntitiesRender  ..>  TargetBlockEntity 
ModEntitiesRender  ..>  TargetMinecart 
ModEntitiesRender  ..>  TargetMinecartRenderer 
ModEntitiesRender  ..>  TargetRenderer 
ModItems  ..>  GunMod 
ModPainting  ..>  GunMod 
ModParticles  ..>  BulletHoleOption 
ModParticles  ..>  GunMod 
ModParticles  ..>  ModParticles 
ModRecipe  ..>  GunMod 
ModRecipe  ..>  GunSmithTableRecipe 
ModRecipe  ..>  GunSmithTableSerializer 
ModSerializers "1" *--> "RELOAD_STATE 1" IDataSerializer~T~ 
ModSerializers  ..>  ReloadState : «create»
ModSerializers  ..>  StateType 
ModSounds  ..>  GunMod 
ModSyncedEntityData  ..>  Builder~E, T~ 
ModSyncedEntityData  ..>  GunMod 
ModSyncedEntityData  ..>  ModSerializers 
ModSyncedEntityData  ..>  ModSyncedEntityData 
ModSyncedEntityData  ..>  ReloadState 
ModSyncedEntityData  ..>  Serializers 
ModSyncedEntityData  ..>  SyncedClassKey~E~ 
ModSyncedEntityData "1" *--> "SHOOT_COOL_DOWN_KEY 1" SyncedDataKey~E, T~ 
ModSyncedEntityData  ..>  SyncedEntityData 
ModelAdditionalMagazineListener  ..>  AnimationListener 
ModelAdditionalMagazineListener "1" *--> "listener 1" AnimationListener 
ModelAdditionalMagazineListener "1" *--> "model 1" BedrockGunModel 
ModelAdditionalMagazineListener  ..>  ChannelType 
ModelAdditionalMagazineListener  ..>  ObjectAnimationChannel 
ModelRendererWrapper "1" *--> "modelRenderer 1" BedrockPart 
ModelRendererWrapper  ..>  ModelRendererWrapper 
ModelRotateListener  ..>  AnimationListener 
ModelRotateListener  ..>  ChannelType 
ModelRotateListener  ..>  MathUtil 
ModelRotateListener "1" *--> "rendererWrapper 1" ModelRendererWrapper 
ModelRotateListener  ..>  ObjectAnimationChannel 
ModelScaleListener  ..>  AnimationListener 
ModelScaleListener  ..>  ChannelType 
ModelScaleListener "1" *--> "rendererWrapper 1" ModelRendererWrapper 
ModelScaleListener  ..>  ObjectAnimationChannel 
ModelTranslateListener  ..>  AnimationListener 
ModelTranslateListener  ..>  BedrockAnimatedModel 
ModelTranslateListener  ..>  BedrockModel 
ModelTranslateListener "1" *--> "bonesItem 1" BonesItem 
ModelTranslateListener  ..>  ChannelType 
ModelTranslateListener "1" *--> "rendererWrapper 1" ModelRendererWrapper 
ModelTranslateListener  ..>  ObjectAnimationChannel 
ModernKineticGunItem  -->  AbstractGunItem 
ModernKineticGunItem  ..>  AttachmentType 
ModernKineticGunItem  ..>  Bolt 
ModernKineticGunItem  ..>  CommonGunIndex 
ModernKineticGunItem  ..>  DebugCommand 
ModernKineticGunItem  ..>  DefaultAssets 
ModernKineticGunItem  ..>  EffectData 
ModernKineticGunItem  ..>  EntityKineticBullet 
ModernKineticGunItem  ..>  FireMode 
ModernKineticGunItem  ..>  GunData 
ModernKineticGunItem  ..>  GunDefaultMeleeData 
ModernKineticGunItem  ..>  GunHeatData 
ModernKineticGunItem  ..>  GunItemDataAccessor 
ModernKineticGunItem  ..>  GunMeleeData 
ModernKineticGunItem  ..>  GunMeleeDebug 
ModernKineticGunItem  ..>  GunReloadData 
ModernKineticGunItem  ..>  GunReloadTime 
ModernKineticGunItem  ..>  IGun 
ModernKineticGunItem  ..>  MeleeData 
ModernKineticGunItem  ..>  ModernKineticGunItem 
ModernKineticGunItem  ..>  ModernKineticGunScriptAPI : «create»
ModernKineticGunItem  ..>  ReloadState : «create»
ModernKineticGunItem  ..>  ShooterDataHolder 
ModernKineticGunItem  ..>  StateType 
ModernKineticGunItem  ..>  TimelessAPI 
ModernKineticGunScriptAPI "1" *--> "abstractGunItem 1" AbstractGunItem 
ModernKineticGunScriptAPI  ..>  AmmoConfig 
ModernKineticGunScriptAPI  ..>  AmmoSpeedModifier 
ModernKineticGunScriptAPI  ..>  AttachmentCacheProperty 
ModernKineticGunScriptAPI  ..>  AttachmentDataUtils 
ModernKineticGunScriptAPI  ..>  AttachmentType 
ModernKineticGunScriptAPI  ..>  Bolt 
ModernKineticGunScriptAPI  ..>  BulletData 
ModernKineticGunScriptAPI  ..>  BurstData 
ModernKineticGunScriptAPI "1" *--> "gunIndex 1" CommonGunIndex 
ModernKineticGunScriptAPI  ..>  CycleTaskHelper 
ModernKineticGunScriptAPI  ..>  DefaultAssets 
ModernKineticGunScriptAPI  ..>  EntityKineticBullet : «create»
ModernKineticGunScriptAPI  ..>  FireMode 
ModernKineticGunScriptAPI  ..>  GunAnimationStateContext 
ModernKineticGunScriptAPI  ..>  GunData 
ModernKineticGunScriptAPI  ..>  GunFireEvent : «create»
ModernKineticGunScriptAPI  ..>  GunHeatData 
ModernKineticGunScriptAPI  ..>  IAmmo 
ModernKineticGunScriptAPI  ..>  IAmmoBox 
ModernKineticGunScriptAPI  ..>  IGun 
ModernKineticGunScriptAPI  ..>  IGunOperator 
ModernKineticGunScriptAPI  ..>  InaccuracyModifier 
ModernKineticGunScriptAPI  ..>  InaccuracyType 
ModernKineticGunScriptAPI "1" *--> "entityAccessor 1" LuaEntityAccessor 
ModernKineticGunScriptAPI  ..>  LuaEntityAccessor : «create»
ModernKineticGunScriptAPI  ..>  LuaNbtAccessor : «create»
ModernKineticGunScriptAPI "1" *--> "nbtUtil 1" LuaNbtAccessor 
ModernKineticGunScriptAPI  ..>  ModernKineticGunScriptAPI 
ModernKineticGunScriptAPI  ..>  NetworkHandler 
ModernKineticGunScriptAPI  ..>  ServerMessageGunFire : «create»
ModernKineticGunScriptAPI "1" *--> "dataHolder 1" ShooterDataHolder 
ModernKineticGunScriptAPI  ..>  SilenceModifier 
ModernKineticGunScriptAPI  ..>  SoundManager 
ModernKineticGunScriptAPI  ..>  TimelessAPI 
MouseHandlerMixin  ..>  AttachmentItemDataAccessor 
MouseHandlerMixin  ..>  AttachmentType 
MouseHandlerMixin  ..>  ClientAttachmentIndex 
MouseHandlerMixin  ..>  DefaultAssets 
MouseHandlerMixin  ..>  GunDisplayInstance 
MouseHandlerMixin  ..>  IGun 
MouseHandlerMixin  ..>  IGunOperator 
MouseHandlerMixin  ..>  MathUtil 
MouseHandlerMixin  ..>  MouseHandlerMixin 
MouseHandlerMixin  ..>  TimelessAPI 
MouseHandlerMixin  ..>  ZoomConfig 
MoveSpeed  ..>  MoveSpeed : «create»
MuzzleFlashRender  ..>  AttachmentType 
MuzzleFlashRender "1" *--> "bedrockGunModel 1" BedrockGunModel 
MuzzleFlashRender  ..>  BedrockModel 
MuzzleFlashRender  ..>  GunDisplayInstance 
MuzzleFlashRender  ..>  IAttachment 
MuzzleFlashRender  ..>  IFunctionalRenderer 
MuzzleFlashRender  ..>  MuzzleFlash 
MuzzleFlashRender  ..>  MuzzleFlashRender 
MuzzleFlashRender  ..>  OculusCompat 
MuzzleFlashRender  ..>  SilenceModifier 
MuzzleFlashRender "1" *--> "MUZZLE_FLASH_MODEL 1" SlotModel 
MuzzleFlashRender  ..>  SlotModel : «create»
MuzzleFlashRender  ..>  TimelessAPI 
DiscreteTrackArray  -->  MyIterator 
MyIterator  ..>  MyIterator 
NetworkHandler  ..>  Acknowledge : «create»
NetworkHandler  ..>  ClientMessageCraft 
NetworkHandler  ..>  ClientMessageLaserColor 
NetworkHandler  ..>  ClientMessagePlayerAim 
NetworkHandler  ..>  ClientMessagePlayerBoltGun 
NetworkHandler  ..>  ClientMessagePlayerCancelReload 
NetworkHandler  ..>  ClientMessagePlayerCrawl 
NetworkHandler  ..>  ClientMessagePlayerDrawGun 
NetworkHandler  ..>  ClientMessagePlayerFireSelect 
NetworkHandler  ..>  ClientMessagePlayerMelee 
NetworkHandler  ..>  ClientMessagePlayerReloadGun 
NetworkHandler  ..>  ClientMessagePlayerShoot 
NetworkHandler  ..>  ClientMessagePlayerZoom 
NetworkHandler  ..>  ClientMessageRefitGun 
NetworkHandler  ..>  ClientMessageSyncBaseTimestamp 
NetworkHandler  ..>  ClientMessageUnloadAttachment 
NetworkHandler  ..>  GunMod 
NetworkHandler  ..>  LoginIndexHolder 
NetworkHandler  ..>  NetworkHandler 
NetworkHandler  ..>  ServerMessageCraft 
NetworkHandler  ..>  ServerMessageGunDraw 
NetworkHandler  ..>  ServerMessageGunFire 
NetworkHandler  ..>  ServerMessageGunFireSelect 
NetworkHandler  ..>  ServerMessageGunHurt 
NetworkHandler  ..>  ServerMessageGunKill 
NetworkHandler  ..>  ServerMessageGunMelee 
NetworkHandler  ..>  ServerMessageGunReload 
NetworkHandler  ..>  ServerMessageGunShoot 
NetworkHandler  ..>  ServerMessageLevelUp 
NetworkHandler  ..>  ServerMessageRefreshRefitScreen 
NetworkHandler  ..>  ServerMessageSound 
NetworkHandler  ..>  ServerMessageSwapItem 
NetworkHandler  ..>  ServerMessageSyncBaseTimestamp 
NetworkHandler  ..>  ServerMessageSyncGunPack 
NetworkHandler  ..>  ServerMessageSyncedEntityDataMapping 
NetworkHandler  ..>  ServerMessageUpdateEntityData 
NewRecoilData "1" *--> "pitch 1" Modifier 
NewRecoilData  ..>  Modifier : «create»
RecoilModifier  -->  NewRecoilData 
NodeModel "1" *--> "children *" NodeModel 
ObjectAnimation  ..>  AnimationListener 
ObjectAnimation  ..>  AnimationListenerSupplier 
ObjectAnimation  ..>  ObjectAnimation 
ObjectAnimation  ..>  ObjectAnimationChannel : «create»
ObjectAnimation  ..>  ObjectAnimationRunner 
ObjectAnimation "1" *--> "soundChannel 1" ObjectAnimationSoundChannel 
ObjectAnimation  ..>  ObjectAnimationSoundChannel : «create»
ObjectAnimation "1" *--> "playType 1" PlayType 
ObjectAnimationChannel "1" *--> "content 1" AnimationChannelContent 
ObjectAnimationChannel  ..>  AnimationChannelContent : «create»
ObjectAnimationChannel "1" *--> "listeners *" AnimationListener 
ObjectAnimationChannel "1" *--> "type 1" ChannelType 
ObjectAnimationChannel "1" *--> "interpolator 1" Interpolator 
ObjectAnimationChannel  ..>  ObjectAnimationChannel 
ObjectAnimationRunner  ..>  AnimationListener 
ObjectAnimationRunner  ..>  MathUtil 
ObjectAnimationRunner "1" *--> "animation 1" ObjectAnimation 
ObjectAnimationRunner "1" *--> "transitionFromChannels *" ObjectAnimationChannel 
ObjectAnimationRunner "1" *--> "transitionTo 1" ObjectAnimationRunner 
ObjectAnimationRunner  ..>  ObjectAnimationSoundChannel 
ObjectAnimationSoundChannel "1" *--> "content 1" AnimationSoundChannelContent 
ObjectAnimationSoundChannel  ..>  ObjectAnimationSoundChannel 
ObjectAnimationSoundChannel  ..>  SoundPlayManager 
BedrockAttachmentModel  -->  OcularWrapper 
OcularWrapper "1" *--> "renderer 1" ModelRendererWrapper 
OculusCompat  ..>  CompatRegistry 
OculusCompat  ..>  OculusCompatLegacy 
OculusCompat  ..>  OculusCompatNewly 
RecoilModifier  -->  OldRecoilData 
OptifineCompat  ..>  OptifineCompat 
OtherClothConfig  ..>  OtherConfig 
OtherClothConfig  ..>  PreLoadConfig 
OtherConfig  ..>  OtherConfig 
GunSmithTableResultInfo  -->  OutputGroupName 
OverwriteCommand  ..>  OverwriteCommand 
OverwriteCommand  ..>  PreLoadConfig 
PackConvertor  ..>  GunMod 
PackConvertor  ..>  LegacyPack : «create»
PackConvertor  ..>  PackConvertor 
PackConvertor  ..>  PackInfo 
PackInfoManager  ..>  CommonAssetsManager 
PackInfoManager  ..>  GunMod 
PackInfoManager "1" *--> "dataMap *" PackInfo 
PapiManager  ..>  PapiManager 
PapiManager  ..>  PlayerNamePapi : «create»
ParameterizedCache~T~  ..>  AttachmentPropertyManager 
ParameterizedCache~T~  ..>  Modifier 
ParameterizedCache~T~  ..>  ParameterizedCache~T~ : «create»
ParameterizedCachePair~L, R~  ..>  Modifier 
ParameterizedCachePair~L, R~  ..>  ParameterizedCache~T~ : «create»
ParameterizedCachePair~L, R~  ..>  ParameterizedCachePair~L, R~ : «create»
ParticleFactoryRegistry  ..>  GunMod 
PerlinNoise  ..>  PerlinNoise 
PierceJsonProperty  ..>  AttachmentPropertyManager 
PierceJsonProperty  -->  JsonProperty~T~ 
PierceJsonProperty  ..>  Modifier 
PierceModifier  -->  PierceJsonProperty 
PierceModifier  ..>  AttachmentCacheProperty 
PierceModifier  ..>  AttachmentPropertyManager 
PierceModifier  ..>  BulletData 
PierceModifier  ..>  CacheValue~T~ : «create»
PierceModifier  ..>  CommonAssetsManager 
PierceModifier  ..>  Data 
PierceModifier  ..>  DiagramsData : «create»
PierceModifier  ..>  GunData 
PierceModifier  ..>  GunProperties 
PierceModifier  ..>  GunProperty~T~ 
PierceModifier  ..>  IAttachmentModifier~T, K~ 
PierceModifier  ..>  JsonProperty~T~ 
PierceModifier  ..>  Modifier 
PierceModifier  ..>  PierceJsonProperty : «create»
PierceModifier  ..>  PierceModifier 
PlayGunSoundEvent  ..>  GunSoundInstance 
ObjectAnimation  -->  PlayType 
PlayerAnimatorAssetManager  ..>  GunMod 
PlayerAnimatorAssetManager "1" *--> "INSTANCE 1" PlayerAnimatorAssetManager 
PlayerAnimatorAssetManager  ..>  PlayerAnimatorAssetManager : «create»
PlayerAnimatorCompat  ..>  AnimationDataRegisterFactory 
PlayerAnimatorCompat  ..>  AnimationManager : «create»
PlayerAnimatorCompat  ..>  GunDisplayInstance 
PlayerAnimatorCompat  ..>  GunMod 
PlayerAnimatorCompat  ..>  PlayerAnimatorAssetManager 
PlayerAnimatorCompat  ..>  PlayerAnimatorCompat 
PlayerAnimatorCompat  ..>  PlayerAnimatorLoader 
PlayerAnimatorLoader  ..>  GunMod 
PlayerAnimatorLoader  ..>  PlayerAnimatorAssetManager 
PlayerAnimatorLoader  ..>  TacPathVisitor : «create»
PlayerEnterWorld  ..>  PackConvertor 
PlayerEnterWorld  ..>  PlayerEnterWorld 
PlayerHurtByGunEvent  ..>  EntityHurtByGunEvent 
PlayerHurtByGunEvent  ..>  GunHurtBobTweak 
PlayerHurtByGunEvent  ..>  Post 
PlayerHurtByGunEvent  ..>  TimelessAPI 
PlayerModelMixin~T~  ..>  IGun 
PlayerModelMixin~T~  ..>  KeepingItemRenderer 
PlayerModelMixin~T~  ..>  PlayerModelMixin~T~ 
PlayerRespawnEvent  ..>  CommonGunIndex 
PlayerRespawnEvent  ..>  FeedType 
PlayerRespawnEvent  ..>  GunConfig 
PlayerRespawnEvent  ..>  GunData 
PlayerRespawnEvent  ..>  GunReloadData 
PlayerRespawnEvent  ..>  IGun 
PlayerRespawnEvent  ..>  ModernKineticGunScriptAPI : «create»
EntityHurtByGunEvent  -->  Post 
Post  -->  EntityHurtByGunEvent 
Post  ..>  EntityKillByGunEvent 
Post  ..>  KubeJSGunEventPoster~E~ 
Pre  -->  EntityHurtByGunEvent 
EntityHurtByGunEvent  -->  Pre 
Pre  ..>  GunDamageSourcePart 
Pre  ..>  KubeJSGunEventPoster~E~ 
PreLoadConfig  ..>  PreLoadConfig 
PreLoadConfig  ..>  PreLoadModConfig : «create»
PreventGunClick  ..>  IGun 
PreventsHotbarEvent  ..>  GunRefitScreen 
PreventsHotbarEvent  ..>  GunSmithTableScreen 
ProjectileExplosion  ..>  AmmoConfig 
ProjectileExplosion  ..>  BlockRayTrace 
ProjectileExplosion  ..>  HitboxHelper 
Provider  ..>  BulletHoleOption 
BulletHoleParticle  -->  Provider 
Provider  ..>  BulletHoleParticle : «create»
RawAnimationStructure "1" *--> "accessors *" Accessor 
RawAnimationStructure "1" *--> "animations *" Animation 
RawAnimationStructure "1" *--> "buffers *" Buffer 
RawAnimationStructure "1" *--> "bufferViews *" BufferView 
RawAnimationStructure "1" *--> "nodes *" Node 
RawGunTableResult  ..>  AmmoItemBuilder 
RawGunTableResult  ..>  AttachmentItemBuilder 
RawGunTableResult  ..>  AttachmentType 
RawGunTableResult  ..>  GunItemBuilder 
RawGunTableResult  ..>  GunMod 
RawGunTableResult "1" *--> "extraData 1" GunResult 
RawGunTableResult  ..>  GunSmithTableResult : «create»
RawGunTableResult  ..>  RawGunTableResult 
RawGunTableResult  ..>  TabConfig 
RawGunTableResult  ..>  TimelessAPI 
RecipeFilter "1" *--> "whitelist *" IFilter~T~ 
RecipeFilter  ..>  RecipeFilter 
RecipeFilterManager  ..>  CommonAssetsManager 
RecipeFilterManager  ..>  DataType 
RecipeFilterManager  ..>  GunMod 
RecipeFilterManager  ..>  INetworkCacheReloadListener 
RecipeFilterManager "1" *--> "filters *" RecipeFilter 
RecipeFilterManager  ..>  RecipeFilterManager 
RecipeFilterManager  ..>  ResourceScanner 
RecoilJsonProperty  ..>  AttachmentPropertyManager 
RecoilJsonProperty  -->  JsonProperty~T~ 
RecoilJsonProperty  ..>  Modifier 
RecoilModifier  -->  RecoilJsonProperty 
RecoilModifier  ..>  AttachmentCacheProperty 
RecoilModifier  ..>  CacheValue~T~ : «create»
RecoilModifier  ..>  CommonAssetsManager 
RecoilModifier  ..>  Data 
RecoilModifier  ..>  DiagramsData : «create»
RecoilModifier  ..>  GunData 
RecoilModifier  ..>  GunProperties 
RecoilModifier  ..>  GunProperty~T~ 
RecoilModifier  ..>  GunRecoil 
RecoilModifier  ..>  GunRecoilKeyFrame 
RecoilModifier  ..>  IAttachmentModifier~T, K~ 
RecoilModifier  ..>  JsonProperty~T~ 
RecoilModifier  ..>  Modifier : «create»
RecoilModifier  ..>  NewRecoilData 
RecoilModifier  ..>  OldRecoilData 
RecoilModifier  ..>  ParameterizedCache~T~ 
RecoilModifier  ..>  ParameterizedCachePair~L, R~ 
RecoilModifier  ..>  RecoilJsonProperty : «create»
RecoilModifier  ..>  RecoilModifier 
RefitKey  ..>  GunRefitScreen : «create»
RefitKey  ..>  IGun 
RefitKey  ..>  InputExtraCheck 
RefitTransform "1" *--> "oldTransformType 1" AttachmentType 
RefitTurnPageButton  ..>  GunRefitScreen 
RefitTurnPageButton  ..>  IComponentTooltip 
RefitUnloadButton  ..>  GunRefitScreen 
RefitUnloadButton  ..>  IComponentTooltip 
RefreshClonePlayerDataEvent  ..>  DelayedTask 
RefreshClonePlayerDataEvent  ..>  GunMod 
RefreshClonePlayerDataEvent  ..>  IGunOperator 
RegexFilter~T~  ..>  IFilter~T~ 
ReloadCommand  ..>  ClientAssetsManager 
ReloadCommand  ..>  CommonAssetsManager 
ReloadCommand  ..>  ReloadCommand 
ReloadKey  ..>  Bolt 
ReloadKey  ..>  IClientPlayerGunOperator 
ReloadKey  ..>  IGun 
ReloadKey  ..>  InputExtraCheck 
ReloadKey  ..>  KeyConfig 
ReloadKey  ..>  TimelessAPI 
ReloadState  ..>  ReloadState 
ReloadState "1" *--> "stateType 1" StateType 
RenderClothConfig  ..>  CrosshairDropdown 
RenderClothConfig  ..>  CrosshairType 
RenderClothConfig  ..>  RenderConfig 
RenderConfig  ..>  CrosshairType 
RenderCrosshairEvent  ..>  AnimationStateContext 
RenderCrosshairEvent  ..>  AnimationStateMachine~T~ 
RenderCrosshairEvent  ..>  CrosshairType 
RenderCrosshairEvent  ..>  GunMod 
RenderCrosshairEvent  ..>  GunRefitScreen 
RenderCrosshairEvent  ..>  IClientPlayerGunOperator 
RenderCrosshairEvent  ..>  IGun 
RenderCrosshairEvent  ..>  IGunOperator 
RenderCrosshairEvent  ..>  ReloadState 
RenderCrosshairEvent  ..>  RenderConfig 
RenderCrosshairEvent  ..>  RenderCrosshairEvent 
RenderCrosshairEvent  ..>  ShoulderSurfingCompat 
RenderCrosshairEvent  ..>  StateType 
RenderCrosshairEvent  ..>  TimelessAPI 
RenderDistance  ..>  RenderConfig 
RenderDistance  ..>  RenderDistance 
RenderHeadShotAABB  ..>  HeadShotAABBConfigRead 
RenderHeadShotAABB  ..>  RenderConfig 
RenderHelper  ..>  OptifineCompat 
RenderHelper  ..>  RenderHelper 
RenderItemInHandBobHurtEventJS  ..>  BobHurt 
RenderItemInHandBobHurtEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  RenderItemInHandBobHurtEventJS 
RenderItemInHandBobHurtEventJS  ..>  RenderItemInHandBobEvent 
RenderItemInHandBobViewEventJS  ..>  BobView 
RenderItemInHandBobViewEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  RenderItemInHandBobViewEventJS 
RenderItemInHandBobViewEventJS  ..>  RenderItemInHandBobEvent 
RenderLevelBobHurtEventJS  ..>  BobHurt 
RenderLevelBobHurtEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  RenderLevelBobHurtEventJS 
RenderLevelBobHurtEventJS  ..>  RenderLevelBobEvent 
RenderLevelBobViewEventJS  ..>  BobView 
RenderLevelBobViewEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  RenderLevelBobViewEventJS 
RenderLevelBobViewEventJS  ..>  RenderLevelBobEvent 
ResourceManager "1" *--> "EXTRA_ENTRIES *" ExtraEntry 
ResourceManager  ..>  ExtraEntry : «create»
ResourceManager  ..>  GunMod 
ResourceScanner  ..>  ResourceScanner 
ResultButton  ..>  GunMod 
RightHandRender "1" *--> "bedrockGunModel 1" BedrockAnimatedModel 
RightHandRender  ..>  BedrockModel 
RightHandRender  ..>  IFunctionalRenderer 
RightHandRender  ..>  RenderHelper 
RootCommand  ..>  ConfigCommand 
RootCommand  ..>  DebugCommand 
RpmJsonProperty  ..>  AttachmentPropertyManager 
RpmJsonProperty  -->  JsonProperty~T~ 
RpmJsonProperty  ..>  Modifier 
RpmModifier  -->  RpmJsonProperty 
RpmModifier  ..>  AttachmentCacheProperty 
RpmModifier  ..>  AttachmentPropertyManager 
RpmModifier  ..>  CacheValue~T~ : «create»
RpmModifier  ..>  CommonAssetsManager 
RpmModifier  ..>  Data 
RpmModifier  ..>  DiagramsData : «create»
RpmModifier  ..>  FireMode 
RpmModifier  ..>  GunData 
RpmModifier  ..>  GunProperties 
RpmModifier  ..>  GunProperty~T~ 
RpmModifier  ..>  IAttachmentModifier~T, K~ 
RpmModifier  ..>  IGun 
RpmModifier  ..>  JsonProperty~T~ 
RpmModifier  ..>  Modifier 
RpmModifier  ..>  RpmJsonProperty : «create»
RpmModifier  ..>  RpmModifier 
SLerp "1" *--> "content 1" AnimationChannelContent 
SLerp  ..>  Interpolator 
SLerp  ..>  SLerp 
AnimationModel  -->  Sampler 
Sampler  ..>  Interpolation 
ScriptManager  ..>  GunMod 
ScriptManager "1" *--> "libraries *" LuaLibrary 
ScriptManager  ..>  ScriptManager 
SecondOrderDynamics  ..>  SecondOrderDynamics 
CommonTransformObject  -->  Serializer 
Serializer  ..>  CommonTransformObject : «create»
Serializers "1" *--> "BOOLEAN 1" IDataSerializer~T~ 
Serializers  ..>  SyncedDataKey~E, T~ 
ServerConfig  ..>  SyncConfig 
ServerGamePacketListenerImplMixin  ..>  IGunOperator 
ServerMessageCraft  ..>  GunSmithTableScreen 
ServerMessageCraft  ..>  ServerMessageCraft : «create»
ServerMessageGunDraw  ..>  GunDrawEvent : «create»
ServerMessageGunDraw  ..>  ServerMessageGunDraw : «create»
ServerMessageGunFire  ..>  GunFireEvent : «create»
ServerMessageGunFire  ..>  ServerMessageGunFire : «create»
ServerMessageGunFireSelect  ..>  GunFireSelectEvent : «create»
ServerMessageGunFireSelect  ..>  ServerMessageGunFireSelect : «create»
ServerMessageGunHurt  ..>  EntityHurtByGunEvent 
ServerMessageGunHurt  ..>  Post : «create»
ServerMessageGunHurt  ..>  ServerMessageGunHurt : «create»
ServerMessageGunKill  ..>  EntityKillByGunEvent : «create»
ServerMessageGunKill  ..>  ServerMessageGunKill : «create»
ServerMessageGunMelee  ..>  GunMeleeEvent : «create»
ServerMessageGunMelee  ..>  ServerMessageGunMelee : «create»
ServerMessageGunReload  ..>  GunReloadEvent : «create»
ServerMessageGunReload  ..>  ServerMessageGunReload : «create»
ServerMessageGunShoot  ..>  GunShootEvent : «create»
ServerMessageGunShoot  ..>  ServerMessageGunShoot : «create»
ServerMessageLevelUp  ..>  ServerMessageLevelUp 
ServerMessageRefreshRefitScreen  ..>  AttachmentPropertyManager 
ServerMessageRefreshRefitScreen  ..>  GunRefitScreen 
ServerMessageRefreshRefitScreen  ..>  ServerMessageRefreshRefitScreen : «create»
ServerMessageSound  ..>  DefaultAssets 
ServerMessageSound  ..>  ServerMessageSound : «create»
ServerMessageSound  ..>  SoundPlayManager 
ServerMessageSwapItem  ..>  ServerMessageSwapItem : «create»
ServerMessageSwapItem  ..>  SwapItemWithOffHand : «create»
ServerMessageSyncBaseTimestamp  ..>  ClientMessageSyncBaseTimestamp : «create»
ServerMessageSyncBaseTimestamp  ..>  GunMod 
ServerMessageSyncBaseTimestamp  ..>  IClientPlayerGunOperator 
ServerMessageSyncBaseTimestamp  ..>  LocalPlayerDataHolder 
ServerMessageSyncBaseTimestamp  ..>  NetworkHandler 
ServerMessageSyncBaseTimestamp  ..>  ServerMessageSyncBaseTimestamp : «create»
ServerMessageSyncGunPack  ..>  ClientIndexManager 
ServerMessageSyncGunPack  ..>  CommonNetworkCache 
ServerMessageSyncGunPack "1" *--> "cache *" DataType 
ServerMessageSyncGunPack  ..>  ServerMessageSyncGunPack : «create»
ServerMessageSyncedEntityDataMapping  ..>  Acknowledge : «create»
ServerMessageSyncedEntityDataMapping  ..>  GunMod 
ServerMessageSyncedEntityDataMapping  ..>  IMessage 
ServerMessageSyncedEntityDataMapping  -->  LoginIndexHolder 
ServerMessageSyncedEntityDataMapping  ..>  NetworkHandler 
ServerMessageSyncedEntityDataMapping  ..>  ServerMessageSyncedEntityDataMapping : «create»
ServerMessageSyncedEntityDataMapping  ..>  SyncedClassKey~E~ 
ServerMessageSyncedEntityDataMapping  ..>  SyncedDataKey~E, T~ 
ServerMessageSyncedEntityDataMapping  ..>  SyncedEntityData 
ServerMessageUpdateEntityData "1" *--> "entries *" DataEntry~E, T~ 
ServerMessageUpdateEntityData  ..>  ServerMessageUpdateEntityData : «create»
ServerMessageUpdateEntityData  ..>  SyncedEntityData 
ServerPlayNetHandlerMixin  ..>  NetworkHandler 
ServerPlayNetHandlerMixin  ..>  ServerMessageSwapItem : «create»
ServerPlayerMixin  ..>  IGunOperator 
ServerTickHandler  ..>  CycleTaskHelper 
ShellRender  ..>  BedrockAmmoModel 
ShellRender "1" *--> "bedrockGunModel 1" BedrockGunModel 
ShellRender  ..>  BedrockModel 
ShellRender  ..>  ClientGunIndex 
ShellRender "1" *--> "SHELL_QUEUE *" Data 
ShellRender  ..>  Data : «create»
ShellRender  ..>  GunData 
ShellRender  ..>  GunDisplayInstance 
ShellRender  ..>  IFunctionalRenderer 
ShellRender  ..>  IGun 
ShellRender  ..>  OculusCompat 
ShellRender  ..>  ShellEjection 
ShellRender  ..>  ShellRender 
ShellRender  ..>  TimelessAPI 
ShootKey  ..>  FireMode 
ShootKey  ..>  IClientPlayerGunOperator 
ShootKey  ..>  IGun 
ShootKey  ..>  InputExtraCheck 
ShootKey  ..>  LocalPlayerSprint 
ShootKey  ..>  ShootResult 
ShootKey  ..>  SoundPlayManager 
ShootKey  ..>  TimelessAPI 
ShooterDataHolder  ..>  ReloadState 
ShooterDataHolder "1" *--> "reloadStateType 1" StateType 
ShoulderSurfingCompat  ..>  ShoulderSurfingCompatInner 
Silence "1" *--> "distance 1" Modifier 
SilenceModifier  -->  Silence 
SilenceJsonProperty  ..>  AttachmentPropertyManager 
SilenceJsonProperty  ..>  GunConfig 
SilenceJsonProperty  -->  JsonProperty~T~ 
SilenceJsonProperty  ..>  Modifier 
SilenceModifier  -->  SilenceJsonProperty 
SilenceModifier  ..>  AttachmentPropertyManager 
SilenceModifier  ..>  CacheValue~T~ : «create»
SilenceModifier  ..>  CommonAssetsManager 
SilenceModifier  ..>  Data 
SilenceModifier  ..>  GunConfig 
SilenceModifier  ..>  GunData 
SilenceModifier  ..>  GunProperties 
SilenceModifier  ..>  GunProperty~T~ 
SilenceModifier  ..>  IAttachmentModifier~T, K~ 
SilenceModifier  ..>  Modifier : «create»
SilenceModifier  ..>  Silence 
SilenceModifier  ..>  SilenceJsonProperty : «create»
SilenceModifier  ..>  SilenceModifier 
SlotModel  ..>  BedrockCubePerFace : «create»
SlotModel "1" *--> "bone 1" BedrockPart 
SlotModel  ..>  BedrockPart : «create»
SlotModel  ..>  FaceUVsItem 
SlotModel  ..>  SlotModel 
SoundAssetsManager  ..>  GunMod 
SoundAssetsManager  ..>  SoundAssetsManager 
SoundAssetsManager "1" *--> "dataMap *" SoundData 
SoundAssetsManager  -->  SoundData 
SoundEffectKeyframesSerializer  ..>  SoundEffectKeyframes : «create»
SoundManager  ..>  SoundManager 
SoundPlayManager  ..>  FireSound 
SoundPlayManager  ..>  GunConfig 
SoundPlayManager  ..>  GunData 
SoundPlayManager  ..>  GunDisplayInstance 
SoundPlayManager "1" *--> "tmpSoundInstance 1" GunSoundInstance 
SoundPlayManager  ..>  GunSoundInstance : «create»
SoundPlayManager  ..>  IAttachment 
SoundPlayManager  ..>  ModSounds 
SoundPlayManager  ..>  ServerMessageSound 
SoundPlayManager  ..>  SoundManager 
SoundPlayManager  ..>  SoundPlayManager 
SoundPlayManager  ..>  TimelessAPI 
ModDamageTypes  -->  Sources 
Sources  ..>  Sources 
Spline  ..>  AnimationChannelContent 
Spline  ..>  Interpolator 
ReloadState  -->  StateType 
StateType  ..>  StateType 
StatueBlock  ..>  IGun 
StatueBlock  ..>  ModBlocks 
StatueBlock  ..>  StatueBlockEntity : «create»
StatueBlockEntity  ..>  ModBlocks 
StatueBlockEntity  ..>  StatueBlockEntity 
StatueRenderer  ..>  BedrockModel 
StatueRenderer  ..>  InternalAssetLoader 
StatueRenderer  ..>  RenderConfig 
StatueRenderer  ..>  StatueBlockEntity 
StatueRenderer  ..>  StatueRenderer 
StatueRenderer  ..>  TargetBlock 
Step "1" *--> "content 1" AnimationChannelContent 
Step  ..>  Interpolator 
Step  ..>  Step 
SwapItemWithOffHandEventJS  -->  GunEventJS~E~ 
GunKubeJSEvents  -->  SwapItemWithOffHandEventJS 
SwapItemWithOffHandEventJS  ..>  SwapItemWithOffHand 
SyncBaseTimestamp  ..>  NetworkHandler 
SyncBaseTimestamp  ..>  ServerMessageSyncBaseTimestamp : «create»
SyncConfig  ..>  SyncConfig 
SyncedDataKey~E, T~  -->  SyncMode 
SyncedClassKey~E~  ..>  SyncedClassKey~E~ : «create»
SyncedClassKey~E~ "1" *--> "LIVING_ENTITY 1" SyncedClassKey~E~ 
SyncedDataKey~E, T~  ..>  Builder~E, T~ : «create»
SyncedDataKey~E, T~  ..>  IDataSerializer~T~ 
SyncedDataKey~E, T~  ..>  SyncMode 
SyncedDataKey~E, T~  ..>  SyncedClassKey~E~ 
SyncedDataKey~E, T~  ..>  SyncedDataKey~E, T~ 
SyncedDataKey~E, T~  ..>  SyncedEntityData 
SyncedEntityData  ..>  CommonRegistry 
SyncedEntityData  ..>  DataHolder 
SyncedEntityData  ..>  GunMod 
SyncedEntityData  ..>  ServerMessageSyncedEntityDataMapping 
SyncedEntityData "1" *--> "registeredClassKeys *" SyncedClassKey~E~ 
SyncedEntityData "1" *--> "registeredDataKeys *" SyncedDataKey~E, T~ 
SyncedEntityData "1" *--> "INSTANCE 1" SyncedEntityData 
SyncedEntityData  ..>  SyncedEntityData : «create»
SyncedEntityDataEvent  ..>  DataEntry~E, T~ 
SyncedEntityDataEvent  ..>  DataHolder 
SyncedEntityDataEvent  ..>  NetworkHandler 
SyncedEntityDataEvent  ..>  ServerMessageUpdateEntityData : «create»
SyncedEntityDataEvent  ..>  SyncMode 
SyncedEntityDataEvent  ..>  SyncedDataKey~E, T~ 
SyncedEntityDataEvent  ..>  SyncedEntityData 
TabConfig  ..>  AmmoItemBuilder 
TabConfig  ..>  AttachmentItemBuilder 
TabConfig  ..>  DefaultAssets 
TabConfig  ..>  GunItemBuilder 
TabConfig  ..>  GunMod 
TabConfig  ..>  ModItems 
TabConfig "1" *--> "DEFAULT_TABS *" TabConfig 
TabConfig  ..>  TabConfig : «create»
TableRecipe "1" *--> "materials *" GunSmithTableIngredient 
TableRecipe "1" *--> "result 1" GunSmithTableResult 
TacHitResult  ..>  EntityKineticBullet 
TacHitResult  ..>  EntityResult 
TacPathVisitor  ..>  PathHandler 
TargetBlock  ..>  EntityKineticBullet 
TargetBlock  ..>  ModBlocks 
TargetBlock  ..>  TargetBlockEntity : «create»
TargetBlockEntity  ..>  ModBlocks 
TargetBlockEntity  ..>  ModSounds 
TargetBlockEntity  ..>  OtherConfig 
TargetBlockEntity  ..>  TargetBlock 
TargetBlockEntity  ..>  TargetBlockEntity 
TargetMinecart  ..>  EntityHurtByGunEvent 
TargetMinecart  ..>  EntityKineticBullet 
TargetMinecart  ..>  ITargetEntity 
TargetMinecart  ..>  ModBlocks 
TargetMinecart  ..>  ModItems 
TargetMinecart  ..>  ModSounds 
TargetMinecart  ..>  NetworkHandler 
TargetMinecart  ..>  OtherConfig 
TargetMinecart  ..>  Post : «create»
TargetMinecart  ..>  RenderConfig 
TargetMinecart  ..>  ServerMessageGunHurt : «create»
TargetMinecart  ..>  TargetMinecart 
TargetMinecartItem  ..>  TargetMinecart : «create»
TargetMinecartRenderer  ..>  BedrockModel 
TargetMinecartRenderer  ..>  BedrockPart 
TargetMinecartRenderer  ..>  InternalAssetLoader 
TargetMinecartRenderer  ..>  TargetMinecart 
TargetMinecartRenderer  ..>  TargetMinecartRenderer 
TargetRenderer  ..>  BedrockModel 
TargetRenderer  ..>  BedrockPart 
TargetRenderer  ..>  InternalAssetLoader 
TargetRenderer  ..>  RenderConfig 
TargetRenderer  ..>  TargetBlock 
TargetRenderer  ..>  TargetBlockEntity 
TargetRenderer  ..>  TargetRenderer 
TextShow "1" *--> "align 1" Align 
TextShowRender "1" *--> "bedrockModel 1" BedrockModel 
TextShowRender  ..>  IFunctionalRenderer 
TextShowRender  ..>  PapiManager 
TextShowRender "1" *--> "textShow 1" TextShow 
ThirdPersonManager "1" *--> "CACHE *" IThirdPersonAnimation 
ThrowableAnimationStateContext  -->  ItemAnimationStateContext 
TickAnimationEvent  ..>  AnimateGeoItemRenderer~M, CTX~ 
TickAnimationEvent  ..>  GunAnimationConstant 
TickAnimationEvent  ..>  GunMod 
TickAnimationEvent  ..>  TimelessAPI 
TimelessAPI  ..>  DefaultAssets 
TimelessAPI  ..>  IGun 
TimelessClientEvents  ..>  BeforeRenderHandEvent 
TimelessClientEvents  ..>  BeforeRenderHandEventJS 
TimelessClientEvents  ..>  BobHurt 
TimelessClientEvents  ..>  BobHurt 
TimelessClientEvents  ..>  BobView 
TimelessClientEvents  ..>  BobView 
TimelessClientEvents  ..>  GunKubeJSEvents 
TimelessClientEvents  ..>  RenderItemInHandBobEvent 
TimelessClientEvents  ..>  RenderItemInHandBobHurtEventJS 
TimelessClientEvents  ..>  RenderItemInHandBobViewEventJS 
TimelessClientEvents  ..>  RenderLevelBobEvent 
TimelessClientEvents  ..>  RenderLevelBobHurtEventJS 
TimelessClientEvents  ..>  RenderLevelBobViewEventJS 
TimelessClientEvents  ..>  SwapItemWithOffHand 
TimelessClientEvents  ..>  SwapItemWithOffHandEventJS 
TimelessClientEvents  ..>  TimelessClientEvents : «create»
TimelessClientEvents "1" *--> "INSTANCE 1" TimelessClientEvents 
TimelessClientEvents  ..>  TimelessKubeJSEventRegister 
TimelessCommonEvents  ..>  EntityHurtByGunEvent 
TimelessCommonEvents  ..>  EntityHurtByGunPostEventJS 
TimelessCommonEvents  ..>  EntityHurtByGunPreEventJS 
TimelessCommonEvents  ..>  EntityKillByGunEvent 
TimelessCommonEvents  ..>  EntityKillByGunEventJS 
TimelessCommonEvents  ..>  GunDrawEvent 
TimelessCommonEvents  ..>  GunDrawEventJS 
TimelessCommonEvents  ..>  GunFinishReloadEvent 
TimelessCommonEvents  ..>  GunFinishReloadEventJS 
TimelessCommonEvents  ..>  GunFireEvent 
TimelessCommonEvents  ..>  GunFireEventJS 
TimelessCommonEvents  ..>  GunFireSelectEvent 
TimelessCommonEvents  ..>  GunFireSelectEventJS 
TimelessCommonEvents  ..>  GunKubeJSEvents 
TimelessCommonEvents  ..>  GunMeleeEvent 
TimelessCommonEvents  ..>  GunMeleeEventJS 
TimelessCommonEvents  ..>  GunReloadEvent 
TimelessCommonEvents  ..>  GunReloadEventJS 
TimelessCommonEvents  ..>  GunShootEvent 
TimelessCommonEvents  ..>  GunShootEventJS 
TimelessCommonEvents  ..>  Post 
TimelessCommonEvents  ..>  Pre 
TimelessCommonEvents  ..>  TimelessCommonEvents : «create»
TimelessCommonEvents "1" *--> "INSTANCE 1" TimelessCommonEvents 
TimelessCommonEvents  ..>  TimelessKubeJSEventRegister 
TimelessGunSmithTableRecipeSchema  ..>  GunSmithTableResultComponents 
TimelessGunSmithTableRecipeSchema  ..>  GunSmithTableResultInfo 
TimelessGunSmithTableRecipeSchema  ..>  TimelessRecipeJS 
TimelessItemType  ..>  ModItems 
TimelessItemWrapper  ..>  AbstractGunItem 
TimelessItemWrapper  ..>  AmmoItem 
TimelessItemWrapper  ..>  AmmoItemBuilder 
TimelessItemWrapper  ..>  AmmoNbtFactory : «create»
TimelessItemWrapper  ..>  AttachmentItem 
TimelessItemWrapper  ..>  AttachmentItemBuilder 
TimelessItemWrapper  ..>  AttachmentNbtFactory : «create»
TimelessItemWrapper  ..>  BlockItemBuilder 
TimelessItemWrapper  ..>  GunNbtFactory : «create»
TimelessItemWrapper  ..>  ItemIndexInfo 
TimelessKubeJSEventRegister  ..>  GunEventJS~E~ 
TimelessKubeJSEventRegister  ..>  GunKubeJSEvents 
TimelessKubeJSEventRegister  ..>  TimelessKubeJSEventRegister 
TimelessKubeJSPlugin  ..>  AbstractGunItem 
TimelessKubeJSPlugin  ..>  CustomGunItemBuilder 
TimelessKubeJSPlugin  ..>  GunItemManager 
TimelessKubeJSPlugin  ..>  GunKubeJSEvents 
TimelessKubeJSPlugin  ..>  GunMod 
TimelessKubeJSPlugin  ..>  GunProperties 
TimelessKubeJSPlugin  ..>  GunSmithTableResultComponents 
TimelessKubeJSPlugin  ..>  GunSmithTableResultInfo 
TimelessKubeJSPlugin  ..>  TimelessClientEvents 
TimelessKubeJSPlugin  ..>  TimelessCommonEvents 
TimelessKubeJSPlugin  ..>  TimelessGunSmithTableRecipeSchema 
TimelessKubeJSPlugin  ..>  TimelessItemWrapper 
TimelessKubeJSPlugin  ..>  TimelessKubeJSEventRegister 
TimelessKubeJSPlugin  ..>  TimelessServerEvents 
TimelessRecipeJS  ..>  AmmoItemBuilder 
TimelessRecipeJS  ..>  AttachmentItemBuilder 
TimelessRecipeJS  ..>  AttachmentType 
TimelessRecipeJS  ..>  CommonAssetsManager 
TimelessRecipeJS  ..>  CommonGunIndex 
TimelessRecipeJS  ..>  GunItemBuilder 
TimelessRecipeJS  ..>  GunResult 
TimelessRecipeJS  ..>  GunSmithTableResult 
TimelessRecipeJS "1" *--> "info 1" GunSmithTableResultInfo 
TimelessRecipeJS  ..>  OutputGroupName 
TimelessRecipeJS  ..>  TimelessAPI 
TimelessRecipeJS  ..>  TimelessRecipeJS 
TimelessServerEvents  ..>  AmmoHitBlockEvent 
TimelessServerEvents  ..>  AmmoHitBlockEventJS 
TimelessServerEvents  ..>  AttachmentPropertyEvent 
TimelessServerEvents  ..>  AttachmentPropertyEventJS 
TimelessServerEvents  ..>  GunKubeJSEvents 
TimelessServerEvents  ..>  TimelessKubeJSEventRegister 
TimelessServerEvents "1" *--> "INSTANCE 1" TimelessServerEvents 
TimelessServerEvents  ..>  TimelessServerEvents : «create»
TooltipEvent  ..>  AmmoItemDataAccessor 
TooltipEvent  ..>  AttachmentItemDataAccessor 
TooltipEvent  ..>  BlockItemDataAccessor 
TooltipEvent  ..>  GunItemDataAccessor 
TooltipEvent  ..>  ModItems 
TooltipEvent  ..>  RenderConfig 
TooltipEvent  ..>  TooltipEvent 
TransformScale  ..>  TransformScale : «create»
TravelToDimensionEvent  ..>  IGun 
TravelToDimensionEvent  ..>  IGunOperator 
InteractKeyConfigRead  -->  Type 
TypeButton  ..>  GunMod 
VersionChecker  ..>  GunMod 
VersionChecker  ..>  Info 
VersionChecker  ..>  VersionChecker 
WeightJsonProperty  ..>  AttachmentPropertyManager 
WeightJsonProperty  -->  JsonProperty~T~ 
WeightJsonProperty  ..>  Modifier 
WeightModifier  -->  WeightJsonProperty 
WeightModifier  ..>  AttachmentCacheProperty 
WeightModifier  ..>  AttachmentPropertyManager 
WeightModifier  ..>  CacheValue~T~ : «create»
WeightModifier  ..>  CommonAssetsManager 
WeightModifier  ..>  Data 
WeightModifier  ..>  DiagramsData : «create»
WeightModifier  ..>  GunData 
WeightModifier  ..>  GunProperties 
WeightModifier  ..>  GunProperty~T~ 
WeightModifier  ..>  IAttachmentModifier~T, K~ 
WeightModifier  ..>  JsonProperty~T~ 
WeightModifier  ..>  Modifier : «create»
WeightModifier  ..>  WeightJsonProperty : «create»
WeightModifier  ..>  WeightModifier 
ZoomClothConfig  ..>  ZoomConfig 
ZoomKey  ..>  ClientMessagePlayerZoom : «create»
ZoomKey  ..>  IClientPlayerGunOperator 
ZoomKey  ..>  InputExtraCheck 
ZoomKey  ..>  NetworkHandler 
ZoomKey  ..>  ZoomKey 
