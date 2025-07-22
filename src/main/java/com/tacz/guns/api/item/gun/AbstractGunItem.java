package com.tacz.guns.api.item.gun;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.entity.ReloadState;
import com.tacz.guns.api.item.*;
import com.tacz.guns.api.item.attachment.AttachmentType;
import com.tacz.guns.api.item.builder.AmmoItemBuilder;
import com.tacz.guns.api.item.builder.GunItemBuilder;
import com.tacz.guns.client.renderer.item.GunItemRendererWrapper;
import com.tacz.guns.client.resource.index.ClientGunIndex;
import com.tacz.guns.entity.shooter.ShooterDataHolder;
import com.tacz.guns.inventory.tooltip.GunTooltip;
import com.tacz.guns.resource.CommonAssetsManager;
import com.tacz.guns.resource.index.CommonAmmoIndex;
import com.tacz.guns.resource.index.CommonGunIndex;
import com.tacz.guns.resource.pojo.data.gun.FeedType;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AllowAttachmentTagMatcher;
import com.tacz.guns.util.AttachmentDataUtils;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractGunItem extends Item implements IGun, IAnimationItem {
    protected AbstractGunItem(Properties pProperties) {
        super(pProperties);
    }

    /**
     * 开始拉栓时调用，返回 bolt 状态
     * @return bolt 状态。ture 代表开始 bolt，false 则代表不开始。
     */
    public abstract boolean startBolt(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter);

    /**
     * 拉栓 tick 时调用，返回是否仍在 bolt 状态
     * @return 是否仍在 bolt 状态
     */
    public abstract boolean tickBolt(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter);

    /**
     * 射击时触发
     */
    public abstract void shoot(ShooterDataHolder dataHolder, ItemStack gunItem, Supplier<Float> pitch, Supplier<Float> yaw, LivingEntity shooter);

    /**
     * 开始换弹时调用
     */
    public abstract boolean startReload(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter);

    /**
     * 换弹时每个 tick 调用
     * @return 如果返回的类型是 NOT_RELOADING 则下一个 tick 不再继续调用
     */
    public abstract ReloadState tickReload(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter);

    /**
     * 尝试打断换弹时调用
     */
    public abstract void interruptReload(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter);

    /**
     * 切换开火模式时调用
     */
    public abstract void fireSelect(ShooterDataHolder dataHolder, ItemStack gunItem);

    /**
     * 近战时调用
     */
    public abstract void melee(ShooterDataHolder dataHolder, LivingEntity user, ItemStack gunItem);

    /**
     * 过热 tick 处理<br/>
     * 默认不做任何事情
     */
    public void tickHeat(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter) {};

    /**
     * 初始化子弹角度和速度
     * @param dataHolder 状态数据
     * @param gunItem 枪械物品
     * @param shooter 射击者
     * @param projectile 子弹
     * @param bulletCnt 多弹丸的子弹序数
     * @param processedSpeed 修正后的子弹初速
     * @param inaccuracy 修正后的子弹不准确度
     * @param pitch 射击方向
     * @param yaw 射击方向
     */
    public void doBulletSpread(ShooterDataHolder dataHolder, ItemStack gunItem, LivingEntity shooter, Projectile projectile,
                                        int bulletCnt, float processedSpeed, float inaccuracy, float pitch, float yaw) {
        projectile.shootFromRotation(shooter, pitch, yaw, 0.0F, processedSpeed, inaccuracy);
    }

    /**
     * 换弹前的检查，完成如下检查：枪内弹药是否已经填满？玩家背包是否有可用弹药？是否为背包直读？
     * @param shooter 准备换弹的实体
     * @param gunItem 枪械物品
     * @return 是否满足换弹条件
     */
    public boolean canReload(LivingEntity shooter, ItemStack gunItem) {
        ResourceLocation gunId = this.getGunId(gunItem);
        Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(gunId);
        if (gunIndexOptional.isEmpty()) {
            return false;
        }
        CommonGunIndex gunIndex = gunIndexOptional.get();

        int currentAmmoCount = getCurrentAmmoCount(gunItem);
        int maxAmmoCount = AttachmentDataUtils.getAmmoCountWithAttachment(gunItem, gunIndex.getGunData());
        if (currentAmmoCount >= maxAmmoCount) {
            return false;
        }
        if (useInventoryAmmo(gunItem)) {
            return false;
        }
        if (gunIndex.getGunData().getReloadData().isInfinite()) {
            return true;
        }        // 虚拟备弹处理
        if (useDummyAmmo(gunItem)) {
            return getDummyAmmoAmount(gunItem) > 0;
        }
        IItemHandler cap = shooter.getCapability(Capabilities.ItemHandler.ENTITY, null);
        if (cap != null) {
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(gunItem, checkAmmoStack)) {
                    return true;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(gunItem, checkAmmoStack)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 将枪内的弹药全部退至背包（如果背包满了会丢到地上）。不会退枪膛内的弹药。
     * 目前，仅更换弹匣配件时调用。
     * @param player 玩家
     * @param gunItem 枪械物品
     */
    @Override
    public void dropAllAmmo(Player player, ItemStack gunItem) {
        if (useInventoryAmmo(gunItem)) {
            return;
        }
        int ammoCount = getCurrentAmmoCount(gunItem);
        if (ammoCount <= 0) {
            return;
        }
        ResourceLocation gunId = getGunId(gunItem);
        Object gunIndexObj = TimelessAPI.getCommonGunIndex(gunId).orElse(null);
        if (gunIndexObj != null && gunIndexObj instanceof CommonGunIndex) {
            CommonGunIndex index = (CommonGunIndex) gunIndexObj;
            if (useDummyAmmo(gunItem)) {
                setCurrentAmmoCount(gunItem, 0);
                if (index.getGunData().getReloadData().getType().equals(FeedType.FUEL)) {
                    return;
                }
                addDummyAmmoAmount(gunItem, ammoCount);
                return;
            }

            ResourceLocation ammoId = index.getGunData().getAmmoId();
            if (player.isCreative()) {
                int maxAmmCount = AttachmentDataUtils.getAmmoCountWithAttachment(gunItem, index.getGunData());
                setCurrentAmmoCount(gunItem, maxAmmCount);
                return;
            }
            if (index.getGunData().getReloadData().getType().equals(FeedType.FUEL)) {
                setCurrentAmmoCount(gunItem, 0);
                return;
            }
            Object ammoIndexObj = TimelessAPI.getCommonAmmoIndex(ammoId).orElse(null);
            if (ammoIndexObj != null && ammoIndexObj instanceof CommonAmmoIndex) {
                CommonAmmoIndex ammoIndex = (CommonAmmoIndex) ammoIndexObj;
                int stackSize = ammoIndex.getStackSize();
                int tmpAmmoCount = ammoCount;
                int roundCount = tmpAmmoCount / (stackSize + 1);
                for (int i = 0; i <= roundCount; i++) {
                    int count = Math.min(tmpAmmoCount, stackSize);
                    ItemStack ammoItem = AmmoItemBuilder.create().setId(ammoId).setCount(count).build();
                    ItemHandlerHelper.giveItemToPlayer(player, ammoItem);
                    tmpAmmoCount -= stackSize;
                }
                setCurrentAmmoCount(gunItem, 0);
            }
        }
    }

    /**
     * 枪械寻弹和扣除背包弹药逻辑
     * @param itemHandler 目标实体的背包
     * @param gunItem 枪械物品
     * @param needAmmoCount 需要的弹药 (物品) 数量
     * @return 寻找到的弹药 (物品) 数量
     */
    @Deprecated
    public int findAndExtractInventoryAmmos(IItemHandler itemHandler, ItemStack gunItem, int needAmmoCount) {
        return findAndExtractInventoryAmmo(itemHandler, gunItem, needAmmoCount);
    }

    /**
     * 枪械寻弹和扣除背包弹药逻辑
     * @param itemHandler 目标实体的背包
     * @param gunItem 枪械物品
     * @param needAmmoCount 需要的弹药 (物品) 数量
     * @return 寻找到的弹药 (物品) 数量
     */
    public int findAndExtractInventoryAmmo(IItemHandler itemHandler, ItemStack gunItem, int needAmmoCount) {
        int cnt = needAmmoCount;
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack checkAmmoStack = itemHandler.getStackInSlot(i);
            if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(gunItem, checkAmmoStack)) {
                ItemStack extractItem = itemHandler.extractItem(i, cnt, false);
                cnt = cnt - extractItem.getCount();
                if (cnt <= 0) {
                    break;
                }
            }
            if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(gunItem, checkAmmoStack)) {
                int boxAmmoCount = iAmmoBox.getAmmoCount(checkAmmoStack);
                int extractCount = Math.min(boxAmmoCount, cnt);
                int remainCount = boxAmmoCount - extractCount;
                iAmmoBox.setAmmoCount(checkAmmoStack, remainCount);
                if (remainCount <= 0) {
                    iAmmoBox.setAmmoId(checkAmmoStack, DefaultAssets.EMPTY_AMMO_ID);
                }
                cnt = cnt - extractCount;
                if (cnt <= 0) {
                    break;
                }
            }
        }
        return needAmmoCount - cnt;
    }

    /**
     * 扣除虚拟弹药逻辑，该方法具有通用的实现，放在此处
     * @param gunItem 枪械物品
     * @param needAmmoCount 需要的弹药(物品)数量
     * @return 找到的弹药(物品)数量
     */
    public int findAndExtractDummyAmmo(ItemStack gunItem, int needAmmoCount) {
        int dummyAmmoCount = getDummyAmmoAmount(gunItem);
        int extractCount = Math.min(dummyAmmoCount, needAmmoCount);
        addDummyAmmoAmount(gunItem, -extractCount);
        return extractCount;
    }

    /**
     * 检查枪械是否允许安装指定的物品作为配件
     */
    @Override
    public boolean allowAttachment(ItemStack gun, ItemStack attachmentItem) {
        IAttachment iAttachment = IAttachment.getIAttachmentOrNull(attachmentItem);
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun != null && iAttachment != null) {
            ResourceLocation gunId = iGun.getGunId(gun);
            ResourceLocation attachmentId = iAttachment.getAttachmentId(attachmentItem);
            return AllowAttachmentTagMatcher.match(gunId, attachmentId);
        }
        return false;
    }

    /**
     * 检查枪械是否允许安装某种类型的配件
     */
    @Override
    public boolean allowAttachmentType(ItemStack gun, AttachmentType type) {
        IGun iGun = IGun.getIGunOrNull(gun);
        if (iGun != null) {
            Object gunIndexObj = TimelessAPI.getCommonGunIndex(iGun.getGunId(gun)).orElse(null);
            if (gunIndexObj instanceof CommonGunIndex) {
                CommonGunIndex gunIndex = (CommonGunIndex) gunIndexObj;
                List<AttachmentType> allowAttachments = gunIndex.getGunData().getAllowAttachments();
                if (allowAttachments == null) {
                    return false;
                }
                return allowAttachments.contains(type);
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 获取枪械的显示名称
     */
    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public Component getName(@Nonnull ItemStack stack) {
        ResourceLocation gunId = this.getGunId(stack);
        Optional<ClientGunIndex> gunIndex = TimelessAPI.getClientGunIndex(gunId);
        if (gunIndex.isPresent()) {
            return Component.translatable(gunIndex.get().getName());
        }
        return super.getName(stack);
    }

    /**
     * 获取某一类 TabType 的所有枪械物品的实例。用于填充创造物品栏和枪械制造台。
     */
    public static NonNullList<ItemStack> fillItemCategory(GunTabType type) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        Comparator<Map.Entry<ResourceLocation, CommonGunIndex>> idNameSort = (o1, o2) -> {
            CommonGunIndex index1 = o1.getValue();
            CommonGunIndex index2 = o2.getValue();
            return Integer.compare(index1.getSort(), index2.getSort());
        };
        TimelessAPI.getAllCommonGunIndex().stream().sorted(idNameSort).forEach(entry -> {
            CommonGunIndex index = entry.getValue();
            GunData gunData = index.getGunData();
                String key = type.name().toLowerCase(Locale.US);
                String indexType = index.getType();
                if (key.equals(indexType)) {
                    ItemStack itemStack = GunItemBuilder.create()
                            .setId(entry.getKey())
                                .setFireMode(gunData.getFireModeSet().get(0))
                            .setAmmoCount(gunData.getAmmoAmount())
                            .setHeatData(gunData.hasHeatData())
                            .setAmmoInBarrel(true)
                            .build();
                    stacks.add(itemStack);
                }
        });
        return stacks;
    }

    /**
     * 获取某一类 TabType 的所有枪械物品的实例，使用 HolderLookup.Provider。用于填充创造物品栏。
     */
    public static NonNullList<ItemStack> fillItemCategory(GunTabType type, HolderLookup.Provider provider) {
        NonNullList<ItemStack> stacks = NonNullList.create();
        
        // Verificar se os dados estão disponíveis
        var assetsManager = CommonAssetsManager.getInstance();
        if (assetsManager == null) {
            // Se não estão carregados, retornar lista vazia
            // System.out.println("TACZ DEBUG: CommonAssetsManager is null for type: " + type);
            return stacks;
        }
        
        Comparator<Map.Entry<ResourceLocation, CommonGunIndex>> idNameSort = (o1, o2) -> {
            CommonGunIndex index1 = o1.getValue();
            CommonGunIndex index2 = o2.getValue();
            return Integer.compare(index1.getSort(), index2.getSort());
        };
        
        Set<Map.Entry<ResourceLocation, CommonGunIndex>> allGuns = TimelessAPI.getAllCommonGunIndex();
        if (allGuns.isEmpty()) {
            // System.out.println("TACZ DEBUG: No guns found in fillItemCategory for type: " + type);
            return stacks;
        }
        
        allGuns.stream().sorted(idNameSort).forEach(entry -> {
            CommonGunIndex index = entry.getValue();
            GunData gunData = index.getGunData();
            String key = type.name().toLowerCase(Locale.US);
            String indexType = index.getType();
            if (key.equals(indexType)) {
                ItemStack itemStack = GunItemBuilder.create()
                        .setId(entry.getKey())
                        .setFireMode(gunData.getFireModeSet().get(0))
                        .setAmmoCount(gunData.getAmmoAmount())
                        .setHeatData(gunData.hasHeatData())
                        .setAmmoInBarrel(true)
                        .build(provider);
                
                // Guarantee count is 1 for creative tabs to prevent crash
                if (itemStack.getCount() != 1) {
                    itemStack.setCount(1);
                }
                
                stacks.add(itemStack);
                // System.out.println("TACZ DEBUG: Added gun " + entry.getKey() + " to tab " + type);
            }
        });
        
        // System.out.println("TACZ DEBUG: Tab " + type + " populated with " + stacks.size() + " items");
        return stacks;
    }

    /**
     * 阻止玩家手臂挥动
     */
    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return true;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new GunItemRendererWrapper();
            }
        });
    }

    /**
     * 获取在 Tooltip 中渲染的图片
     */
    @Override
    @Nonnull
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        if (stack.getItem() instanceof IGun iGun) {
            Object gunIndexObj = TimelessAPI.getCommonGunIndex(this.getGunId(stack)).orElse(null);
            if (gunIndexObj instanceof CommonGunIndex) {
                CommonGunIndex gunIndex = (CommonGunIndex) gunIndexObj;
                ResourceLocation ammoId = gunIndex.getGunData().getAmmoId();
                return Optional.of(new GunTooltip(stack, iGun, ammoId, gunIndex));
            }
        }
        return Optional.empty();
    }

    /**
     * 获取是否使用弹药直读
     * @param gun 枪械
     * @return 是否使用弹药直读
     */
    @Override
    public boolean useInventoryAmmo(ItemStack gun) {
        if (gun.getItem() instanceof IGun) {
            Object gunIndexObj = TimelessAPI.getCommonGunIndex(this.getGunId(gun)).orElse(null);
            if (!(gunIndexObj instanceof CommonGunIndex)) {
                return false;
            }
            CommonGunIndex gunIndex = (CommonGunIndex) gunIndexObj;
            return gunIndex.getGunData().getReloadData().getType().equals(FeedType.INVENTORY);
        }
        return false;
    }

    /**
     * 获取是否有供给弹药直读的弹药
     * @param gun 枪械
     * @return 是否有供给弹药直读的弹药
     */
    @Override
    public boolean hasInventoryAmmo(LivingEntity shooter, ItemStack gun, boolean needCheckAmmo) {
        if (!useInventoryAmmo(gun)) {
            return false;
        }
        if (!needCheckAmmo) {
            return true;
        }
        if (useDummyAmmo(gun)) {
            return getDummyAmmoAmount(gun) > 0;
        }        // 检查背包内的弹药数量
        IItemHandler cap = shooter.getCapability(Capabilities.ItemHandler.ENTITY, null);
        if (cap != null) {
            for (int i = 0; i < cap.getSlots(); i++) {
                ItemStack checkAmmoStack = cap.getStackInSlot(i);
                if (checkAmmoStack.getItem() instanceof IAmmo iAmmo && iAmmo.isAmmoOfGun(gun, checkAmmoStack)) {
                    return true;
                }
                if (checkAmmoStack.getItem() instanceof IAmmoBox iAmmoBox && iAmmoBox.isAmmoBoxOfGun(gun, checkAmmoStack)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 获取 RPM
     * @param gun 枪械
     * @return RPM 数值
     */
    public int getRPM(ItemStack gun) {
        if (gun.getItem() instanceof IGun iGun) {
            Object gunIndexObj = TimelessAPI.getCommonGunIndex(this.getGunId(gun)).orElse(null);
            if (gunIndexObj instanceof CommonGunIndex) {
                CommonGunIndex gunIndex = (CommonGunIndex) gunIndexObj;
                GunData gunData = gunIndex.getGunData();
                FireMode fireMode = getFireMode(gun);
                int rpm = gunData.getRoundsPerMinute(fireMode);
                if (iGun.hasHeatData(gun)) {
                    
                }
                return rpm;
            }
            return 300;
        }
        return 300;
    }

    /**
     * 获取是否可以趴下射击
     * @param gun 枪械
     * @return 是否可以趴下射击
     */
    public boolean isCanCrawl(ItemStack gun) {
        if (gun.getItem() instanceof IGun) {
            Optional<CommonGunIndex> gunIndexOptional = TimelessAPI.getCommonGunIndex(this.getGunId(gun));
            if (gunIndexOptional.isPresent()) {
                CommonGunIndex gunIndex = gunIndexOptional.get();
                return gunIndex.getGunData().isCanCrawl();
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean isSame(ItemStack i, ItemStack j) {
        IGun iGun1 = IGun.getIGunOrNull(i);
        IGun iGun2 = IGun.getIGunOrNull(j);
        if (iGun1 != null && iGun2 != null) {
            return iGun1.getGunId(i).equals(iGun2.getGunId(j));
        }
        if (i.isEmpty() || j.isEmpty()) {
            return i.isEmpty() && j.isEmpty();
        }
        return ItemStack.matches(i, j);
    }
}
