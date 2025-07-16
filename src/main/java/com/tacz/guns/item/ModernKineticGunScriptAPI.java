package com.tacz.guns.item;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.IGunOperator;
import com.tacz.guns.api.event.common.GunFireEvent;
import com.tacz.guns.api.item.IAmmo;
import com.tacz.guns.api.item.IAmmoBox;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.gun.FireMode;
import com.tacz.guns.api.util.LuaEntityAccessor;
import com.tacz.guns.api.util.LuaNbtAccessor;
import com.tacz.guns.client.animation.statemachine.GunAnimationStateContext;
import com.tacz.guns.config.common.AmmoConfig;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.event.ServerMessageGunFire;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.modifier.custom.AmmoSpeedModifier;
import com.tacz.guns.resource.modifier.custom.InaccuracyModifier;
import com.tacz.guns.resource.modifier.custom.SilenceModifier;
import com.tacz.guns.resource.pojo.data.gun.*;
import com.tacz.guns.sound.SoundManager;
import com.tacz.guns.util.AttachmentDataUtils;
import com.tacz.guns.util.CycleTaskHelper;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.fml.LogicalSide;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import net.minecraft.server.level.ServerPlayer;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModernKineticGunScriptAPI {
    public static String MARKER = "ScriptAPI";

    private LivingEntity shooter;

    private ShooterDataHolder dataHolder;

    private ItemStack itemStack;

    private AbstractGunItem abstractGunItem;

    private CommonGunIndex gunIndex;

    private ResourceLocation gunId;

    private ResourceLocation gunDisplayId;

    private Supplier<Float> pitchSupplier;

    private Supplier<Float> yawSupplier;

    private LuaNbtAccessor nbtUtil;

    private LuaEntityAccessor entityAccessor;

    /**
     * Dispara uma vez com as configurações atuais, afetado pelo estado do jogador (mira, movimento, etc.),
     * acessórios, tipo de munição, modo de disparo, som de disparo, etc.
     * @param consumeAmmo Se este disparo deve consumir munição.
     */
    public void shootOnce(boolean consumeAmmo){
        if (gunIndex == null) {
            return; // Object Strategy: retorna early se o index não estiver disponível
        }
        
        GunData gunData = gunIndex.getGunData();
        BulletData bulletData = gunIndex.getBulletData();
        IGunOperator gunOperator = IGunOperator.fromLivingEntity(shooter);

        AttachmentCacheProperty cacheProperty = gunOperator.getCacheProperty();
        if (cacheProperty == null) {
            return;
        }

        //Lida com dados de aquecimento
        float heatInaccuracy = 1f;
        if(hasHeatData()) {
            GunHeatData heatData = gunIndex.getGunData().getHeatData();
            float heatPercentage = (getHeatAmount() / heatData.getHeatMax());
            heatInaccuracy *= Mth.lerp(heatPercentage, heatData.getMinInaccuracy(), heatData.getMaxInaccuracy());
        }

        InaccuracyType inaccuracyType = InaccuracyType.getInaccuracyType(shooter);
        final float inaccuracy = Math.max(0, cacheProperty.<Map<InaccuracyType, Float>>getCache(InaccuracyModifier.ID).get(inaccuracyType) * heatInaccuracy);

        Pair<Integer, Boolean> silence = cacheProperty.getCache(SilenceModifier.ID);
        final int soundDistance = silence.getLeft().intValue();
        final boolean useSilenceSound = silence.getRight();

        float speed = cacheProperty.<Float>getCache(AmmoSpeedModifier.ID);
        speed *= AmmoConfig.GLOBAL_BULLET_SPEED_MODIFIER.get();
        float processedSpeed = Mth.clamp(speed / 20, 0, Float.MAX_VALUE);
        int bulletAmount = Math.max(bulletData.getBulletAmount(), 1);

        FireMode fireMode = abstractGunItem.getFireMode(itemStack);
        int cycles = fireMode == FireMode.BURST ? gunData.getBurstData().getCount() : 1;
        long period = fireMode == FireMode.BURST ? gunData.getBurstShootInterval() : 1;

        CycleTaskHelper.addCycleTask(() -> {
            if (shooter.isDeadOrDying()) {
                return false;
            }
            if (!shooter.getMainHandItem().equals(itemStack) || shooter.getMainHandItem().isEmpty()) {
                return false;
            }
            boolean fire = !NeoForge.EVENT_BUS.post(new GunFireEvent(shooter, itemStack, LogicalSide.SERVER)).isCancelled();
            if (fire) {
                if (shooter instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToClientPlayer(new ServerMessageGunFire(shooter.getId(), itemStack), serverPlayer);
                }
                if (consumeAmmo) {
                    if (!this.reduceAmmoOnce()) {
                        return false;
                    }
                }
                //Lida com dados de aquecimento
                if(gunIndex.getGunData().hasHeatData()) {
                    Optional.ofNullable(gunIndex.getScript())
                            .map(script -> checkFunction(script.get("handle_shoot_heat")))
                            .ifPresentOrElse(
                                    func -> func.call(CoerceJavaToLua.coerce(this)),
                                    this::handleShootHeat
                            );
                }
                float pitch = pitchSupplier != null ? pitchSupplier.get() : shooter.getXRot();
                float yaw = yawSupplier != null ? yawSupplier.get() : shooter.getYRot();
                Level world = shooter.level();
                ResourceLocation ammoId = gunData.getAmmoId();
                for (int i = 0; i < bulletAmount; i++) {
                    boolean isTracer = bulletData.hasTracerAmmo() && gunOperator.nextBulletIsTracer(bulletData.getTracerCountInterval());
                    EntityKineticBullet bullet = new EntityKineticBullet(world, shooter, itemStack, ammoId, gunId,
                            gunDisplayId, isTracer, gunData, bulletData);
                    abstractGunItem.doBulletSpread(dataHolder, itemStack, shooter, bullet, i, processedSpeed,
                            inaccuracy, pitch, yaw);
                    world.addFreshEntity(bullet);
                }
                if (soundDistance > 0) {
                    String soundId = useSilenceSound ? SoundManager.SILENCE_3P_SOUND : SoundManager.SHOOT_3P_SOUND;
                    SoundManager.sendSoundToNearby(shooter, soundDistance, gunId, gunDisplayId, soundId, 0.8f, 0.9f + shooter.getRandom().nextFloat() * 0.125f);
                }
            }
            return true;
        }, period, cycles);
    }

    /**
     * Lida com o processo de aquecimento de um disparo.
     */
    public void handleShootHeat() {
        GunHeatData heatData = gunIndex.getGunData().getHeatData();
        if (heatData == null) {
            return;
        }
        float newHeat = Math.min(abstractGunItem.getHeatAmount(itemStack) + heatData.getHeatPerShot(), heatData.getHeatMax());
        abstractGunItem.setHeatAmount(itemStack, newHeat);
        if (newHeat >= heatData.getHeatMax()) {
            abstractGunItem.setOverheatLocked(itemStack, true);
        }
    }

    /**
     * Reduz a munição da arma em uma unidade, seguindo as regras de ferrolho,
     * consumo de inventário ou do pente. Consome a munição do cano antes da do pente.
     * Se não houver munição para consumir, este método retornará false. Por exemplo, se a arma
     * tem uma bala no pente, mas você está em uma ação de ferrolho e não há bala no cano,
     * então retornará false.
     * @return Se uma bala foi consumida com sucesso.
     */
    public boolean reduceAmmoOnce() {
        Bolt boltType = TimelessAPI.getCommonGunIndex(abstractGunItem.getGunId(itemStack))
                .map(index -> index.getGunData().getBolt())
                .orElse(null);
        boolean hasAmmoInBarrel = abstractGunItem.hasBulletInBarrel(itemStack) && boltType != Bolt.OPEN_BOLT;
        boolean hasInventoryAmmo = abstractGunItem.hasInventoryAmmo(shooter, itemStack, isReloadingNeedConsumeAmmo());
        boolean noAmmo = useInventoryAmmo() && !hasInventoryAmmo ||
                !useInventoryAmmo() && abstractGunItem.getCurrentAmmoCount(itemStack) < 1;
        if (boltType == null) {
            return false;
        }
        if (boltType == Bolt.MANUAL_ACTION) {
            if (!hasAmmoInBarrel) {
                return false;
            }
            abstractGunItem.setBulletInBarrel(itemStack, false);
            return true;
        }
        if (boltType == Bolt.CLOSED_BOLT) {
            if (!noAmmo) {
                if (useInventoryAmmo()) {
                    return consumeAmmoFromPlayer(1) > 0;
                }
                abstractGunItem.reduceCurrentAmmoCount(itemStack);
                return true;
            }
            if (!hasAmmoInBarrel) {
                return false;
            }
            abstractGunItem.setBulletInBarrel(itemStack, false);
            return true;
        }
        if (boltType == Bolt.OPEN_BOLT) {
            if (noAmmo) {
                return false;
            }
            if (useInventoryAmmo()) {
                return consumeAmmoFromPlayer(1) == 1;
            }
            abstractGunItem.reduceCurrentAmmoCount(itemStack);
            return true;
        }
        return false;
    }

    /**
     * Obtém o tempo decorrido desde o início da recarga, em ms.
     *
     * @return O tempo decorrido desde o início da recarga, em ms.
     */
    public long getReloadTime() {
        if (dataHolder.reloadTimestamp == -1) {
            return 0;
        }
        return System.currentTimeMillis() - dataHolder.reloadTimestamp;
    }

    /**
     * Obtém o tempo decorrido desde o início da ação de ferrolho, em ms.
     *
     * @return O tempo decorrido desde o início da ação de ferrolho, em ms.
     */
    public long getBoltTime() {
        if (!dataHolder.isBolting) {
            return 0;
        }
        return System.currentTimeMillis() - dataHolder.boltTimestamp;
    }

    /**
     * Obtém o intervalo de disparo da arma, em milissegundos.
     *
     * @return O intervalo de disparo.
     */
    public long getShootInterval() {
        if (gunIndex == null) {
            return 1000L; // Object Strategy: retorna valor padrão se o index não estiver disponível
        }
        
        FireMode fireMode = abstractGunItem.getFireMode(itemStack);
        if (fireMode == FireMode.BURST) {
            long coolDown = (long) (gunIndex.getGunData().getBurstData().getMinInterval() * 1000f);
            coolDown = coolDown - 5;
            return Math.max(coolDown, 0L);
        }
        long coolDown = gunIndex.getGunData().getShootInterval(this.shooter, fireMode, itemStack);
        coolDown = coolDown - 5;
        return Math.max(coolDown, 0L);
    }

    /**
     * Retorna o timestamp (hora do sistema) do último disparo, em milissegundos.
     * Este valor é resetado para -1 ao trocar de arma.
     *
     * @return O timestamp do último disparo, resetado para -1 ao trocar de arma.
     */
    public long getLastShootTimestamp() {
        return dataHolder.lastShootTimestamp + dataHolder.baseTimestamp;
    }

    /**
     * Ajusta o intervalo de disparo.
     * O intervalo de disparo é calculado a cada tick, então você provavelmente
     * precisará executar esta operação repetidamente em uma função de estado.
     *
     * @param alpha O valor a ser adicionado ao intervalo de disparo, em milissegundos.
     * Positivo para aumentar o intervalo, negativo para diminuir.
     * @see GunAnimationStateContext#adjustClientShootInterval
     */
    public void adjustShootInterval(long alpha) {
        dataHolder.shootTimestamp += alpha;
    }

    /**
     * Ajusta o tempo de recarga.
     *
     * @param alpha O valor a ser adicionado ao tempo de recarga, em milissegundos.
     * Positivo para aumentar o tempo (diminuir a velocidade), negativo para diminuir (aumentar a velocidade).
     */
    public void adjustReloadTime(long alpha) {
        dataHolder.reloadTimestamp -= alpha;
    }

    /**
     * Ajusta o tempo da ação de ferrolho.
     *
     * @param alpha O valor a ser adicionado ao tempo de ferrolho, em milissegundos.
     * Positivo para aumentar o tempo (diminuir a velocidade), negativo para diminuir (aumentar a velocidade).
     */
    public void adjustBoltTime(long alpha) {
        dataHolder.boltTimestamp -= alpha;
    }

    /**
     * Obtém a velocidade de mira.
     *
     * @return Um valor de 0 a 1, onde 0 significa sem mira e 1 significa mira completa.
     */
    public float getAimingProgress() {
        return dataHolder.aimingProgress;
    }

    /**
     * Obtém o estado de recarga atual do jogador.
     *
     * @return O estado de recarga atual do jogador (ordinal).
     */
    public int getReloadStateType() {
        return dataHolder.reloadStateType.ordinal();
    }

    /**
     * Obtém o modo de disparo atual da arma (automático, semi-automático, rajada, etc.).
     *
     * @return O modo de disparo (ordinal).
     */
    public int getFireMode() {
        return abstractGunItem.getFireMode(itemStack).ordinal();
    }

    /**
     * Obtém se o disparo atual do jogador precisa consumir munição.
     * Através desta configuração, jogadores em modo criativo podem disparar sem consumir munição.
     *
     * @return Se o disparo precisa consumir munição.
     */
    public boolean isShootingNeedConsumeAmmo() {
        return IGunOperator.fromLivingEntity(shooter).consumesAmmoOrNot();
    }

    /**
     * Obtém se a recarga atual do jogador precisa consumir munição.
     * Geralmente, jogadores em modo criativo não precisam consumir munição.
     *
     * @return Se a recarga precisa consumir munição.
     */
    public boolean isReloadingNeedConsumeAmmo() {
        return IGunOperator.fromLivingEntity(shooter).needCheckAmmo();
    }

    /**
     * Obtém a quantidade de munição necessária para a arma atual.
     *
     * @return A quantidade de munição necessária para a arma atual.
     */
    public int getNeededAmmoAmount() {
        if (gunIndex == null) {
            return 0; // Object Strategy: retorna 0 se o index não estiver disponível
        }
        
        int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(itemStack, gunIndex.getGunData());
        int currentAmmoCount = abstractGunItem.getCurrentAmmoCount(itemStack);
        return maxAmmoCount - currentAmmoCount;
    }

    /**
     * Obtém a contagem de munição restante no pente.
     *
     * @return Retorna a contagem de munição restante no pente, sem contar a bala já no cano.
     */
    public int getAmmoAmount() {
        return abstractGunItem.getCurrentAmmoCount(itemStack);
    }

    /**
     * Obtém a contagem máxima de munição do pente da arma.
     *
     * @return Retorna a contagem máxima de munição do pente da arma, sem contar a bala já no cano.
     */
    public int getMaxAmmoCount() {
        if (gunIndex == null) {
            return 0; // Object Strategy: retorna 0 se o index não estiver disponível
        }
        
        return AttachmentDataUtils.getAmmoCountWithAttachment(itemStack, gunIndex.getGunData());
    }

    /**
     * Obtém o nível de extensão de capacidade da arma.
     *
     * @return Nível de extensão, retorna 0 ~ 3. 0 indica que não há pente de capacidade estendida instalado,
     * 1 ~ 3 indica que um pente de nível de capacidade estendida 1 ~ 3 está instalado.
     */
    public int getMagExtentLevel() {
        return AttachmentDataUtils.getMagExtendLevel(itemStack, gunIndex.getGunData());
    }

    /**
     * Tenta consumir a munição especificada do inventário do jogador (incluindo caixas de munição),
     * retornando a quantidade consumida.
     *
     * @param neededAmount A quantidade de munição necessária.
     * @return A quantidade de munição realmente consumida.
     */
    public int consumeAmmoFromPlayer(int neededAmount) {
        if (useInventoryAmmo() && !isReloadingNeedConsumeAmmo()) {
            return neededAmount;
        }
        if (abstractGunItem.useDummyAmmo(itemStack)) {
            // CORREÇÃO: O método deve retornar um int, não um boolean.
            // Removido "> 0" para retornar a quantidade de munição extraída.
            return abstractGunItem.findAndExtractDummyAmmo(itemStack, neededAmount);
        } else {
            IItemHandler itemHandler = shooter.getCapability(Capabilities.ItemHandler.ENTITY, null);
            if (itemHandler != null) {
                return abstractGunItem.findAndExtractInventoryAmmo(itemHandler, itemStack, neededAmount);
            } else {
                return 0;
            }
        }
    }

    /**
     * Verifica se o jogador tem munição para consumir em seu inventário (incluindo caixas de munição).
     * Comumente usado em condições de recarga cíclica.
     * Jogadores em modo criativo retornarão diretamente true.
     * @return Se o jogador tem munição para consumir em seu inventário (incluindo caixas de munição).
     */
    public boolean hasAmmoToConsume(){
        if (!isReloadingNeedConsumeAmmo()) {
            return true;
        }
        if (abstractGunItem.useDummyAmmo(itemStack)) {
            return abstractGunItem.getDummyAmmoAmount(itemStack) > 0;
        }
        IItemHandler itemHandler = shooter.getCapability(Capabilities.ItemHandler.ENTITY, null);
        if (itemHandler != null) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                ItemStack checkAmmoStack = itemHandler.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(itemStack, checkAmmoStack)) {
                    return true;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(itemStack, checkAmmoStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Coloca balas no pente.
     *
     * @param amount A quantidade de balas a serem colocadas.
     * @return As balas excedentes.
     */
    public int putAmmoInMagazine(int amount) {
        if (amount < 0) {
            return 0;
        }
        int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(itemStack, gunIndex.getGunData());
        int currentAmmoCount = abstractGunItem.getCurrentAmmoCount(itemStack);
        int newAmmoCount = currentAmmoCount + amount;
        if (maxAmmoCount < newAmmoCount) {
            abstractGunItem.setCurrentAmmoCount(itemStack, maxAmmoCount);
            return newAmmoCount - maxAmmoCount;
        } else {
            abstractGunItem.setCurrentAmmoCount(itemStack, newAmmoCount);
            return 0;
        }
    }

    /**
     * Remove balas do pente.
     *
     * @param amount A quantidade a ser removida.
     * @return A quantidade removida com sucesso.
     */
    public int removeAmmoFromMagazine(int amount) {
        if (amount < 0) {
            return 0;
        }
        int currentAmmoCount = abstractGunItem.getCurrentAmmoCount(itemStack);
        if (currentAmmoCount < amount) {
            abstractGunItem.setCurrentAmmoCount(itemStack, 0);
            return currentAmmoCount;
        } else {
            abstractGunItem.setCurrentAmmoCount(itemStack, currentAmmoCount - amount);
            return amount;
        }
    }

    /**
     * Obtém a quantidade de balas no pente.
     *
     * @return A quantidade de balas no pente.
     */
    public int getAmmoCountInMagazine() {
        return abstractGunItem.getCurrentAmmoCount(itemStack);
    }

    /**
     * Obtém se há uma bala no cano da arma.
     *
     * @return Se há uma bala no cano da arma. Se for uma arma de ferrolho aberto, este método retornará false.
     */
    public boolean hasAmmoInBarrel() {
        Bolt boltType = gunIndex.getGunData().getBolt();
        return boltType != Bolt.OPEN_BOLT && abstractGunItem.hasBulletInBarrel(itemStack);
    }

    /**
     * Define se há uma bala no cano da arma.
     */
    public void setAmmoInBarrel(boolean ammoInBarrel) {
        abstractGunItem.setBulletInBarrel(itemStack, ammoInBarrel);
    }

    /**
     * Armazena em cache qualquer objeto Lua nos dados do jogador, para passar dados
     * de forma síncrona entre funções de estado, ou seja, para passar dados entre chamadas de métodos.
     *
     * @param luaValue O objeto Lua a ser armazenado em cache.
     */
    public void cacheScriptData(LuaValue luaValue) {
        this.dataHolder.scriptData = luaValue;
    }

    /**
     * Obtém o objeto Lua armazenado em cache nos dados do jogador.
     *
     * @return O objeto Lua armazenado em cache.
     */
    public LuaValue getCachedScriptData() {
        return dataHolder.scriptData;
    }

    /**
     * Obtém os parâmetros de script declarados nos dados da arma.
     *
     * @return A tabela de parâmetros de script.
     */
    public LuaTable getScriptParams() {
        LuaTable param = gunIndex.getScriptParam();
        return param == null ? new LuaTable() : param;
    }

    /**
     * Adiciona uma tarefa de ciclo atrasada, executada no thread principal, é segura para threads,
     * o tempo não é estrito, a precisão depende do TPS.
     *
     * @param value    Deve ser uma LuaFunction que retorna um booleano. Se retornar false, o ciclo será interrompido.
     * @param delayMs  O tempo para executar com atraso.
     * @param periodMs O intervalo de execução do ciclo.
     * @param cycles   O número máximo de ciclos, -1 representa infinito.
     */
    public void safeAsyncTask(LuaValue value, long delayMs, long periodMs, int cycles) {
        LuaFunction func = value.checkfunction();
        CycleTaskHelper.addCycleTask(() -> func.call().checkboolean(), delayMs, periodMs, cycles);
    }

    /**
     * Obtém a hora atual do sistema, em milissegundos.
     *
     * @return A hora atual do sistema.
     */
    public long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * Obtém o ID do acessório da arma.
     *
     * @return O ID do acessório. Se o tipo de slot de acessório correspondente não existir,
     * retorna o ID do acessório vazio 'tacz:empty'.
     */
    public String getAttachment(String type) {
        try {
            AttachmentType t = AttachmentType.valueOf(type);
            return abstractGunItem.getAttachmentId(itemStack, t).toString();
        } catch (IllegalArgumentException e) {
            return DefaultAssets.EMPTY_ATTACHMENT_ID.toString();
        }
    }

    /**
     * Retorna um acessador NBT vinculado ao item da arma atual. A menos que seja necessário
     * salvar dados de estado complexos, você não deve usar este método com frequência.
     * @see LuaNbtAccessor
     * @return Acessador NBT.
     */
    public LuaNbtAccessor getNbt() {
        return nbtUtil;
    }

    /**
     * Retorna uma ferramenta para a entidade da arma atual. Este acessador fornece
     * alguns métodos comumente usados, como enviar mensagens de sistema, enviar ActionBars,
     * criar eventos de sistema, etc.
     * @see LuaEntityAccessor
     * @return Acessador de entidade.
     */
    public LuaEntityAccessor getEntityUtil() {
        if (entityAccessor == null) {
            entityAccessor = new LuaEntityAccessor(shooter);
        }
        return entityAccessor;
    }

    public void setShooter(LivingEntity shooter) {
        this.shooter = shooter;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        initGunItem();
    }

    public void setPitchSupplier(Supplier<Float> pitchSupplier) {
        this.pitchSupplier = pitchSupplier;
    }

    public void setYawSupplier(Supplier<Float> yawSupplier) {
        this.yawSupplier = yawSupplier;
    }

    public LivingEntity getShooter() {
        return shooter;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public AbstractGunItem getAbstractGunItem() {
        return abstractGunItem;
    }

    public CommonGunIndex getGunIndex() {
        return gunIndex;
    }

    public void setHeatAmount(float amount) {
        abstractGunItem.setHeatAmount(itemStack, amount);
    }

    public float getHeatAmount() {
        return abstractGunItem.getHeatAmount(itemStack);
    }

    public boolean hasHeatData() {
        if (gunIndex == null) {
            return false; // Object Strategy: retorna false se o index não estiver disponível
        }
        return gunIndex.getGunData().getHeatData() != null;
    }

    public float getHeatMinRpm() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMinRpmMod();
        return 0f;
    }

    public float getHeatMaxRpm() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMaxRpmMod();
        return 0f;
    }

    public float getHeatMinInaccuracy() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMinInaccuracy();
        return 0f;
    }

    public float getHeatMaxInaccuracy() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getMaxInaccuracy();
        return 0f;
    }

    public float getHeatMax() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getHeatMax();
        return 0f;
    }

    public float getHeatPerShot() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getHeatPerShot();
        return 0f;
    }

    public boolean isOverheatLocked() {
        return abstractGunItem.isOverheatLocked(itemStack);
    }

    public void setOverheatLocked(boolean locked) {
        abstractGunItem.setOverheatLocked(itemStack, locked);
    }

    public long getOverheatTime() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getOverHeatTime();
        return 0;
    }

    public long getCoolingDelay() {
        if(hasHeatData()) return gunIndex.getGunData().getHeatData().getCoolingDelay();
        return 0;
    }

    public float calcHeatReduction(long heatTimestamp) {
        GunHeatData heatData = gunIndex.getGunData().getHeatData();
        if (heatData != null) {
            return ((float)(System.currentTimeMillis() - heatTimestamp) / 10000f)
                    * heatData.getCoolingMultiplier();
        }
        return 0f;
    }

    public int getBoltByInt() {
        Bolt bolt = gunIndex.getGunData().getBolt();
        if (bolt == Bolt.MANUAL_ACTION) {
            return 1;
        }
        if (bolt == Bolt.CLOSED_BOLT) {
            return 2;
        }
        if (bolt == Bolt.OPEN_BOLT) {
            return 3;
        }
        return 0;
    }

    public Bolt getBolt() {
        return gunIndex.getGunData().getBolt();
    }

    public void setDataHolder(ShooterDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    public boolean useInventoryAmmo() {
        return abstractGunItem.useInventoryAmmo(itemStack);
    }

    ShooterDataHolder getDataHolder() {
        return this.dataHolder;
    }

    private void initGunItem() {
        if (itemStack == null || !(itemStack.getItem() instanceof AbstractGunItem gunItem)) {
            gunIndex = null;
            abstractGunItem = null;
            return;
        }
        gunId = gunItem.getGunId(itemStack);
        gunDisplayId = gunItem.getGunDisplayId(itemStack);
        Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(gunId);
        gunIndex = gunIndexOptional.orElse(null);
        abstractGunItem = gunItem;        // Migração para DataComponents (NeoForge 1.21.1)
        // Em vez de usar itemStack.getTag(), agora usamos LuaNbtAccessor.from(itemStack)
        nbtUtil = LuaNbtAccessor.from(itemStack);
    }


    private LuaFunction checkFunction(LuaValue luaValue) {
        if (luaValue.isfunction()) {
            return (LuaFunction) luaValue;
        } else if (luaValue.isnil()) {
            return null;
        } else {
            throw new LuaError("bad argument: function or nil expected, got " + luaValue.typename());
        }
    }
}
