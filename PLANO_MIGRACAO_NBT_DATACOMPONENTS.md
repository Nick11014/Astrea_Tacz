# 🔧 PLANO TÉCNICO DETALHADO - MIGRAÇÃO NBT → DATACOMPONENTS

## 🎯 **OBJETIVO**
Migrar completamente o sistema de armazenamento de dados em ItemStacks do antigo sistema NBT para o novo sistema DataComponents introduzido no Minecraft 1.21.1.

---

## 📊 **ANÁLISE DOS ARQUIVOS AFETADOS**

### 🔍 **1. TimelessItemNbtFactory.java.disabled**
**Função:** Classe base para todas as factories NBT  
**Complexidade:** 🔴 Alta - É a base de todo o sistema  

**Código Atual (QUEBRADO):**
```java
public abstract class TimelessItemNbtFactory<T extends Item, E extends TimelessItemNbtFactory<T, E>> {
    protected ItemStack itemStack;
    protected T item;
    
    public void setTag(String key, String value) {
        itemStack.getOrCreateTag().putString(key, value); // ❌ QUEBRADO
    }
}
```

**Nova Implementação Necessária:**
```java
public abstract class TimelessItemDataComponentFactory<T extends Item, E extends TimelessItemDataComponentFactory<T, E>> {
    protected ItemStack itemStack;
    protected T item;
    
    public void setComponent(DataComponentType<String> component, String value) {
        itemStack.set(component, value); // ✅ NOVO SISTEMA
    }
}
```

### 🔍 **2. AttachmentNbtFactory.java.disabled**
**Função:** Gerencia dados de attachments (miras, silenciadores, etc.)  
**Dados Armazenados:**
- Skin ID do attachment
- Propriedades de modificação
- Estado de durabilidade

**Migração Necessária:**
```java
// ANTIGO (NBT)
public void setSkinId(ResourceLocation skinId) {
    itemStack.getOrCreateTag().putString("SkinId", skinId.toString());
}

// NOVO (DataComponents)
public void setSkinId(ResourceLocation skinId) {
    itemStack.set(ModDataComponents.ATTACHMENT_SKIN_ID, skinId);
}
```

### 🔍 **3. GunNbtFactory.java.disabled**
**Função:** Gerencia dados de armas (munição, modo de fogo, etc.)  
**Dados Críticos:**
- Munição atual (`CurrentAmmo`)
- Munição no carregador (`AmmoInBarrel`)
- Modo de fogo (`FireMode`)
- Skin da arma (`SkinId`)
- Attachments equipados

**Exemplo de Migração:**
```java
// ANTIGO
public void setCurrentAmmo(int ammo) {
    itemStack.getOrCreateTag().putInt("CurrentAmmo", ammo);
}

public void setAmmoInBarrel(boolean hasAmmo) {
    itemStack.getOrCreateTag().putBoolean("AmmoInBarrel", hasAmmo);
}

// NOVO
public void setCurrentAmmo(int ammo) {
    itemStack.set(ModDataComponents.CURRENT_AMMO, ammo);
}

public void setAmmoInBarrel(boolean hasAmmo) {
    itemStack.set(ModDataComponents.AMMO_IN_BARREL, hasAmmo);
}
```

### 🔍 **4. AmmoNbtFactory.java.disabled**
**Função:** Gerencia dados de munição  
**Dados:**
- Tipo de munição
- Propriedades especiais
- Skin/aparência

---

## 🏗️ **IMPLEMENTAÇÃO STEP-BY-STEP**

### 📋 **STEP 1: Criar ModDataComponents Registry**

```java
// Arquivo: ModDataComponents.java
public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = 
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, GunMod.MOD_ID);

    // Gun Components
    public static final Supplier<DataComponentType<Integer>> CURRENT_AMMO = COMPONENTS.register("current_ammo",
        () -> DataComponentType.<Integer>builder().persistent(Codec.INT).build());
        
    public static final Supplier<DataComponentType<Boolean>> AMMO_IN_BARREL = COMPONENTS.register("ammo_in_barrel",
        () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).build());
        
    public static final Supplier<DataComponentType<ResourceLocation>> GUN_SKIN_ID = COMPONENTS.register("gun_skin_id",
        () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).build());
        
    public static final Supplier<DataComponentType<String>> FIRE_MODE = COMPONENTS.register("fire_mode",
        () -> DataComponentType.<String>builder().persistent(Codec.STRING).build());

    // Attachment Components  
    public static final Supplier<DataComponentType<ResourceLocation>> ATTACHMENT_SKIN_ID = COMPONENTS.register("attachment_skin_id",
        () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).build());

    // Ammo Components
    public static final Supplier<DataComponentType<ResourceLocation>> AMMO_TYPE = COMPONENTS.register("ammo_type",
        () -> DataComponentType.<ResourceLocation>builder().persistent(ResourceLocation.CODEC).build());
        
    // Complex Components (for attachments data)
    public static final Supplier<DataComponentType<CompoundTag>> ATTACHMENT_DATA = COMPONENTS.register("attachment_data",
        () -> DataComponentType.<CompoundTag>builder().persistent(NbtOps.INSTANCE, CompoundTag.CODEC).build());
}
```

### 📋 **STEP 2: Migrar TimelessItemNbtFactory → TimelessItemDataComponentFactory**

```java
public abstract class TimelessItemDataComponentFactory<T extends Item, E extends TimelessItemDataComponentFactory<T, E>> {
    protected ItemStack itemStack;
    protected T item;

    public TimelessItemDataComponentFactory(@Nonnull T item) {
        this.item = item;
        this.itemStack = new ItemStack(item);
    }

    public TimelessItemDataComponentFactory() {
        this.itemStack = ItemStack.EMPTY;
    }

    // Métodos genéricos para DataComponents
    public <V> void setComponent(DataComponentType<V> component, V value) {
        itemStack.set(component, value);
    }
    
    public <V> V getComponent(DataComponentType<V> component, V defaultValue) {
        return itemStack.getOrDefault(component, defaultValue);
    }
    
    public <V> boolean hasComponent(DataComponentType<V> component) {
        return itemStack.has(component);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    @SuppressWarnings("unchecked")
    public E copy() {
        try {
            E copy = (E) this.getClass().getDeclaredConstructor().newInstance();
            copy.itemStack = this.itemStack.copy();
            copy.item = this.item;
            return copy;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create copy", e);
        }
    }
}
```

### 📋 **STEP 3: Implementar GunDataComponentFactory**

```java
public class GunDataComponentFactory extends TimelessItemDataComponentFactory<AbstractGunItem, GunDataComponentFactory> {

    public GunDataComponentFactory(@Nonnull AbstractGunItem item) {
        super(item);
    }

    public GunDataComponentFactory() {
        super((AbstractGunItem) TimelessItemType.GUN.getItem());
    }

    // Gun-specific methods
    public void setCurrentAmmo(int ammo) {
        setComponent(ModDataComponents.CURRENT_AMMO.get(), ammo);
    }

    public int getCurrentAmmo() {
        return getComponent(ModDataComponents.CURRENT_AMMO.get(), 0);
    }

    public void setAmmoInBarrel(boolean hasAmmo) {
        setComponent(ModDataComponents.AMMO_IN_BARREL.get(), hasAmmo);
    }

    public boolean hasAmmoInBarrel() {
        return getComponent(ModDataComponents.AMMO_IN_BARREL.get(), false);
    }

    public void setGunSkinId(ResourceLocation skinId) {
        setComponent(ModDataComponents.GUN_SKIN_ID.get(), skinId);
    }

    public ResourceLocation getGunSkinId() {
        return getComponent(ModDataComponents.GUN_SKIN_ID.get(), DefaultAssets.DEFAULT_GUN_SKIN);
    }

    public void setFireMode(String fireMode) {
        setComponent(ModDataComponents.FIRE_MODE.get(), fireMode);
    }

    public String getFireMode() {
        return getComponent(ModDataComponents.FIRE_MODE.get(), "SEMI");
    }

    // Complex attachment data
    public void setAttachmentData(CompoundTag attachmentData) {
        setComponent(ModDataComponents.ATTACHMENT_DATA.get(), attachmentData);
    }

    public CompoundTag getAttachmentData() {
        return getComponent(ModDataComponents.ATTACHMENT_DATA.get(), new CompoundTag());
    }
}
```

### 📋 **STEP 4: Implementar AttachmentDataComponentFactory**

```java
public class AttachmentDataComponentFactory extends TimelessItemDataComponentFactory<AttachmentItem, AttachmentDataComponentFactory> {

    public AttachmentDataComponentFactory(@Nonnull AttachmentItem item) {
        super(item);
    }

    public AttachmentDataComponentFactory() {
        super((AttachmentItem) TimelessItemType.ATTACHMENT.getItem());
    }

    public void setAttachmentSkinId(ResourceLocation skinId) {
        setComponent(ModDataComponents.ATTACHMENT_SKIN_ID.get(), skinId);
    }

    public ResourceLocation getAttachmentSkinId() {
        return getComponent(ModDataComponents.ATTACHMENT_SKIN_ID.get(), DefaultAssets.DEFAULT_ATTACHMENT_SKIN);
    }
}
```

### 📋 **STEP 5: Implementar AmmoDataComponentFactory**

```java
public class AmmoDataComponentFactory extends TimelessItemDataComponentFactory<AmmoItem, AmmoDataComponentFactory> {

    public AmmoDataComponentFactory(@Nonnull AmmoItem item) {
        super(item);
    }

    public AmmoDataComponentFactory() {
        super((AmmoItem) TimelessItemType.AMMO.getItem());
    }

    public void setAmmoType(ResourceLocation ammoType) {
        setComponent(ModDataComponents.AMMO_TYPE.get(), ammoType);
    }

    public ResourceLocation getAmmoType() {
        return getComponent(ModDataComponents.AMMO_TYPE.get(), DefaultAssets.DEFAULT_AMMO_TYPE);
    }
}
```

### 📋 **STEP 6: Atualizar TimelessItemWrapper**

```java
public class TimelessItemWrapper {
    
    public static ItemStack gunItem(Consumer<GunDataComponentFactory> callback) {
        GunDataComponentFactory factory = new GunDataComponentFactory();
        callback.accept(factory);
        return factory.getItemStack();
    }

    public static ItemStack attachmentItem(Consumer<AttachmentDataComponentFactory> callback) {
        AttachmentDataComponentFactory factory = new AttachmentDataComponentFactory();
        callback.accept(factory);
        return factory.getItemStack();
    }

    public static ItemStack ammoItem(Consumer<AmmoDataComponentFactory> callback) {
        AmmoDataComponentFactory factory = new AmmoDataComponentFactory();
        callback.accept(factory);
        return factory.getItemStack();
    }
}
```

---

## 🧪 **SISTEMA DE TESTES**

### 📋 **Teste 1: Persistência de Dados**
```java
@Test
public void testDataComponentPersistence() {
    // Criar arma com dados
    ItemStack gun = TimelessItemWrapper.gunItem(factory -> {
        factory.setCurrentAmmo(30);
        factory.setAmmoInBarrel(true);
        factory.setGunSkinId(new ResourceLocation("tacz", "ak47_woodland"));
    });
    
    // Serializar e deserializar
    CompoundTag tag = new CompoundTag();
    gun.save(tag);
    ItemStack loadedGun = ItemStack.of(tag);
    
    // Verificar dados
    GunDataComponentFactory factory = new GunDataComponentFactory();
    factory.itemStack = loadedGun;
    
    assertEquals(30, factory.getCurrentAmmo());
    assertTrue(factory.hasAmmoInBarrel());
    assertEquals(new ResourceLocation("tacz", "ak47_woodland"), factory.getGunSkinId());
}
```

### 📋 **Teste 2: Compatibilidade com KubeJS**
```java
@Test
public void testKubeJSIntegration() {
    // Simular script KubeJS
    ItemStack gun = TimelessItemWrapper.gunItem(factory -> {
        factory.setCurrentAmmo(15);
        factory.setFireMode("AUTO");
    });
    
    // Verificar se dados são acessíveis via KubeJS wrapper
    assertNotNull(gun);
    // Mais testes quando KubeJS estiver implementado
}
```

---

## ⚠️ **CONSIDERAÇÕES IMPORTANTES**

### 🔄 **Migração de Dados Existentes**
**Problema:** Itens existentes ainda usam NBT  
**Solução:** Implementar sistema de migração automática

```java
public class DataMigrationUtil {
    public static void migrateGunNbtToDataComponents(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            // Migrar dados antigos
            if (nbt.contains("CurrentAmmo")) {
                int ammo = nbt.getInt("CurrentAmmo");
                stack.set(ModDataComponents.CURRENT_AMMO.get(), ammo);
                nbt.remove("CurrentAmmo");
            }
            
            if (nbt.contains("AmmoInBarrel")) {
                boolean hasAmmo = nbt.getBoolean("AmmoInBarrel");
                stack.set(ModDataComponents.AMMO_IN_BARREL.get(), hasAmmo);
                nbt.remove("AmmoInBarrel");
            }
            
            // Limpar NBT se vazio
            if (nbt.isEmpty()) {
                stack.setTag(null);
            }
        }
    }
}
```

### 🔒 **Serialização Personalizada**
Para dados complexos, pode ser necessário criar codecs personalizados:

```java
public class AttachmentDataCodec {
    public static final Codec<AttachmentData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            ResourceLocation.CODEC.fieldOf("skinId").forGetter(AttachmentData::getSkinId),
            Codec.FLOAT.fieldOf("durability").forGetter(AttachmentData::getDurability)
        ).apply(instance, AttachmentData::new)
    );
}
```

---

## 📊 **CRONOGRAMA DE IMPLEMENTAÇÃO**

| Dia | Tarefa | Tempo Est. |
|-----|--------|------------|
| 1 | Criar ModDataComponents registry | 2h |
| 2 | Migrar TimelessItemNbtFactory base | 2h |
| 3 | Implementar GunDataComponentFactory | 3h |
| 4 | Implementar Attachment e Ammo factories | 2h |
| 5 | Atualizar TimelessItemWrapper | 1h |
| 6 | Testes e debug | 2h |

**TOTAL:** 12 horas

---

**📅 Atualizado em:** 19 de Julho de 2025  
**🎯 Status:** Planejamento completo - Pronto para implementação
