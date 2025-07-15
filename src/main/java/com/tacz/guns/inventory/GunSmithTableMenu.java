package com.tacz.guns.inventory;

import org.jetbrains.annotations.Nullable;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.config.sync.SyncConfig;
import com.tacz.guns.crafting.GunSmithTableRecipeInput;
import com.tacz.guns.crafting.GunSmithTableIngredient;
import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.network.NetworkHandler;
import com.tacz.guns.network.message.ServerMessageCraft;
import com.tacz.guns.resource.filter.RecipeFilter;
import com.tacz.guns.resource.index.CommonBlockIndex;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import java.util.List;

public class GunSmithTableMenu extends AbstractContainerMenu {
    public static final MenuType<GunSmithTableMenu> TYPE = IMenuTypeExtension.create((windowId, inv, data) -> {
        ResourceLocation blockId = data.readResourceLocation();
        return new GunSmithTableMenu(windowId, inv, blockId);
    });

    private final ResourceLocation blockId;
    private final RecipeFilter filter;

    public GunSmithTableMenu(int id, Inventory inventory, @Nullable ResourceLocation resourceLocation) {
        super(TYPE, id);
        this.blockId = resourceLocation;
        this.filter = TimelessAPI.getCommonBlockIndex(getBlockId()).map(CommonBlockIndex::getFilter).orElse(null);
    }

    @Nullable
    public ResourceLocation getBlockId() {
        return blockId;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    @Nullable
    private GunSmithTableRecipe getRecipe(ResourceLocation recipeId, RecipeManager recipeManager) {
        if (!DefaultAssets.DEFAULT_BLOCK_ID.equals(getBlockId()) || SyncConfig.ENABLE_TABLE_FILTER.get()) {
            if (filter != null && !filter.contains(recipeId)) {
                return null;
            }
        }

        Recipe<?> recipe = recipeManager.byKey(recipeId).map(net.minecraft.world.item.crafting.RecipeHolder::value).orElse(null);
        if (recipe instanceof GunSmithTableRecipe gunSmithTableRecipe) {
            boolean flag = TimelessAPI.getCommonBlockIndex(getBlockId()).map(blockIndex -> {
                    return blockIndex.getData().getTabs().stream().map(com.tacz.guns.resource.pojo.data.block.TabConfig.class::cast).noneMatch(tab -> tab.id().equals(gunSmithTableRecipe.getResult().getGroup()));
            }).orElse(true);
            if (DefaultAssets.DEFAULT_BLOCK_ID.equals(getBlockId()) && !SyncConfig.ENABLE_TABLE_FILTER.get()) {
                flag = false;
            }
            if (flag) {
                return null;
            }
            return gunSmithTableRecipe;
        }
        return null;
    }
        
        /**
         * Retorna um contêiner simples para ser usado na verificação de receitas.
         * Este método é chamado pelo GunSmithTableScreen para obter ingredientes das receitas.
         * 
         * @return Um contêiner simples para processamento de receitas
         */
        public GunSmithTableRecipeInput getRecipeInput() {
            return new GunSmithTableRecipeInput(0);
        }

    public void doCraft(ResourceLocation recipeId, Player player) {
        GunSmithTableRecipe recipe = getRecipe(recipeId, player.level().getRecipeManager());
        if (recipe == null) {
            return;
        }
        IItemHandler handler = player.getCapability(Capabilities.ItemHandler.ENTITY);
        if (handler != null) {
            if (!player.isCreative()) {
                Int2IntArrayMap recordCount = new Int2IntArrayMap();
                List<GunSmithTableIngredient> ingredients = recipe.getInputs();

                for (GunSmithTableIngredient ingredient : ingredients) {
                    int count = 0;
                    for (int slotIndex = 0; slotIndex < handler.getSlots(); slotIndex++) {
                        ItemStack stack = handler.getStackInSlot(slotIndex);
                        int stackCount = stack.getCount();
                        if (!stack.isEmpty() && ingredient.getIngredient().test(stack)) {
                            count = count + stackCount;
                            if (count <= ingredient.getCount()) {
                                recordCount.put(slotIndex, stackCount);
                            } else {
                                int remaining = count - ingredient.getCount();
                                recordCount.put(slotIndex, stackCount - remaining);
                                break;
                            }
                        }
                    }
                    if (count < ingredient.getCount()) {
                        return;
                    }
                }

                for (int slotIndex : recordCount.keySet()) {
                    handler.extractItem(slotIndex, recordCount.get(slotIndex), false);
                }
            }

            if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY() + 0.5, player.getZ(), recipe.getResultItem(player.level().registryAccess()).copy());
                itemEntity.setPickUpDelay(0);
                player.level().addFreshEntity(itemEntity);
                serverPlayer.inventoryMenu.broadcastFullState();
                NetworkHandler.sendToClientPlayer(new ServerMessageCraft(this.containerId), serverPlayer);
            }
        }
    }
}






























































