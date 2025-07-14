package com.tacz.guns.compat.jei.category;

import com.tacz.guns.GunMod;
import com.tacz.guns.compat.jei.entry.AttachmentQueryEntry;
import com.tacz.guns.init.ModCreativeTabs;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AttachmentQueryCategory implements IRecipeCategory<AttachmentQueryEntry> {
    public static final RecipeType<AttachmentQueryEntry> ATTACHMENT_QUERY = RecipeType.create(GunMod.MOD_ID, "attachment_query", AttachmentQueryEntry.class);
    public static final int MAX_GUN_SHOW_COUNT = 60;
    private static final Component TITLE = Component.translatable("jei.tacz.attachment_query.title");
    private final IDrawableStatic bgDraw;
    private final IDrawable slotDraw;
    private final IDrawable iconDraw;

    public AttachmentQueryCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createBlankDrawable(160, 145);
        this.slotDraw = guiHelper.getSlotDrawable();
        this.iconDraw = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, ModCreativeTabs.ATTACHMENT_SCOPE_TAB.get().getIconItem());
    }

    @Override
    public void draw(AttachmentQueryEntry entry, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        List<ItemStack> extraAllowGunStacks = entry.getExtraAllowGunStacks();
        if (!extraAllowGunStacks.isEmpty()) {
            Font font = Minecraft.getInstance().font;
            guiGraphics.drawString(font, Component.translatable("jei.tacz.attachment_query.more"), 128, 134, 0x555555, false);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AttachmentQueryEntry entry, IFocusGroup focuses) {
        ItemStack attachmentStack = entry.getAttachmentStack();
        List<ItemStack> allowGunStacks = entry.getAllowGunStacks();
        List<ItemStack> extraAllowGunStacks = entry.getExtraAllowGunStacks();

        // ÃƒÂ¥Ã¢â‚¬Â¦Ã‹â€ ÃƒÂ¦Ã…Â Ã…Â ÃƒÂ©Ã¢â‚¬Â¦Ã‚ÂÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬ÂÃ‚Â¾ÃƒÂ¥Ã…â€œÃ‚Â¨ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¤Ã‚Â¸Ã‚Â­ÃƒÂ¥Ã‚Â¤Ã‚Â®
        builder.addSlot(RecipeIngredientRole.OUTPUT, 72, 0).addItemStack(attachmentStack).setBackground(slotDraw, -1, -1);

        // ÃƒÂ©Ã¢â€šÂ¬Ã‚ÂÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ§Ã¢â‚¬ÂÃ‚Â»ÃƒÂ¦Ã…Â¾Ã‚ÂªÃƒÂ¦Ã‚Â¢Ã‚Â°ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¦Ã‚Â¯Ã‚ÂÃƒÂ¨Ã‚Â¡Ã…â€™ 9 ÃƒÂ¤Ã‚Â¸Ã‚Âª
        int xOffset = 0;
        int yOffset = 20;
        for (int i = 0; i < allowGunStacks.size(); i++) {
            int column = i % 9;
            int row = i / 9;
            xOffset = column * 18;
            yOffset = 20 + row * 18;
            ItemStack gun = allowGunStacks.get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, xOffset, yOffset).addItemStack(gun).setBackground(slotDraw, -1, -1);
        }

        // ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ¨Ã‚Â¶Ã¢â‚¬Â¦ÃƒÂ¥Ã¢â‚¬Â¡Ã‚ÂºÃƒÂ¤Ã‚Â¸Ã…Â ÃƒÂ©Ã¢â€žÂ¢Ã‚ÂÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ©Ã¢â‚¬Å¡Ã‚Â£ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¦Ã…â€œÃ¢â€šÂ¬ÃƒÂ¥Ã‚ÂÃ…Â½ÃƒÂ¤Ã‚Â¸Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â Ã‚Â¼ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚ÂÃ‚Â¥ÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ¨Ã‚Â·Ã‚Â³ÃƒÂ¥Ã‚ÂÃ‹Å“ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ§Ã¢â‚¬Â°Ã‚Â©ÃƒÂ¥Ã¢â‚¬Å“Ã‚Â
        if (!extraAllowGunStacks.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, xOffset + 18, yOffset).addItemStacks(extraAllowGunStacks).setBackground(slotDraw, -1, -1);
        }
    }

    @Override
    public RecipeType<AttachmentQueryEntry> getRecipeType() {
        return ATTACHMENT_QUERY;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    @SuppressWarnings("removal")
    public IDrawable getBackground() {
        return bgDraw;
    }

    @Override
    public IDrawable getIcon() {
        return iconDraw;
    }
}































































