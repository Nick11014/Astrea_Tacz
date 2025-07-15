package com.tacz.guns.inventory;

import org.jetbrains.annotations.Nullable;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.config.sync.SyncConfig;
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
                    return blockIndex.getData().getTabs().stream().map(com.tacz.guns.resource.pojo.data.block.TabConfig.class::cast).noneMatch(tab -> tab.id().equals(gunSmithTableRecipe.getTab()));
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
        public SimpleContainer getRecipeInput() {
            return new SimpleContainer(0);
        }

    public void doCraft(ResourceLocation recipeId, Player player) {
        GunSmithTableRecipe recipe = getRecipe(recipeId, player.level().getRecipeManager());
        if (recipe == null) {
            return;
        }
        player.getCapability(Capabilities.ItemHandler.ENTITY).ifPresent(handler -> {
            // ÃƒÂ¦Ã‹Å“Ã‚Â¯ÃƒÂ¥Ã‹â€ Ã¢â‚¬ÂºÃƒÂ©Ã¢â€šÂ¬Ã‚Â ÃƒÂ¦Ã‚Â¨Ã‚Â¡ÃƒÂ¥Ã‚Â¼Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â°Ã‚Â±ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ¢â€žÂ¢
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
                            // ÃƒÂ¨Ã‚Â®Ã‚Â°ÃƒÂ¥Ã‚Â½Ã¢â‚¬Â¢ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£ÃƒÂ©Ã¢â€žÂ¢Ã‚Â¤ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ slot ÃƒÂ¥Ã¢â‚¬â„¢Ã…â€™ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
                            if (count <= ingredient.getCount()) {
                                // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¨Ã‚Â¶Ã‚Â³ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â¨ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£
                                recordCount.put(slotIndex, stackCount);
                            } else {
                                //  ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¥Ã‚Â¤Ã…Â¸ÃƒÂ¤Ã‚ÂºÃ¢â‚¬Â ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‚ÂªÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£ÃƒÂ©Ã…â€œÃ¢â€šÂ¬ÃƒÂ¨Ã‚Â¦Ã‚ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚Â
                                int remaining = count - ingredient.getCount();
                                recordCount.put(slotIndex, stackCount - remaining);
                                break;
                            }
                        }
                    }
                    // ÃƒÂ¦Ã¢â‚¬Â¢Ã‚Â°ÃƒÂ©Ã¢â‚¬Â¡Ã‚ÂÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¥Ã‚Â¤Ã…Â¸ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¤Ã‚Â¸Ã‚ÂÃƒÂ¦Ã¢â‚¬Â°Ã‚Â§ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ§Ã‚Â»Ã‚Â­ÃƒÂ©Ã¢â€šÂ¬Ã‚Â»ÃƒÂ¨Ã‚Â¾Ã¢â‚¬ËœÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚ÂÃ‹â€ ÃƒÂ¦Ã‹â€ Ã‚ÂÃƒÂ¥Ã‚Â¤Ã‚Â±ÃƒÂ¨Ã‚Â´Ã‚Â¥
                    if (count < ingredient.getCount()) {
                        return;
                    }
                }

                // ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â§Ã¢â‚¬Â¹ÃƒÂ¦Ã¢â‚¬Â°Ã‚Â£ÃƒÂ¦Ã‚ÂÃ‚ÂÃƒÂ¦Ã¢â‚¬â€œÃ¢â€žÂ¢
                for (int slotIndex : recordCount.keySet()) {
                    handler.extractItem(slotIndex, recordCount.get(slotIndex), false);
                }
            }

            // ÃƒÂ§Ã‚Â»Ã¢â€žÂ¢ÃƒÂ§Ã…Â½Ã‚Â©ÃƒÂ¥Ã‚Â®Ã‚Â¶ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¥Ã‚ÂºÃ¢â‚¬ÂÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚Â
            if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                ItemEntity itemEntity = new ItemEntity(player.level(), player.getX(), player.getY() + 0.5, player.getZ(), recipe.getResultItem(player.level().registryAccess()).copy());
                itemEntity.setPickUpDelay(0);
                player.level().addFreshEntity(itemEntity);
                serverPlayer.inventoryMenu.broadcastFullState();
                NetworkHandler.sendToClientPlayer(new ServerMessageCraft(this.containerId), serverPlayer);
            }
        });
    }
}






























































