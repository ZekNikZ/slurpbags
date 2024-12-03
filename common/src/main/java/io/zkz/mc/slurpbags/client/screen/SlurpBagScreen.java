package io.zkz.mc.slurpbags.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.zkz.mc.slurpbags.inventory.LockableInventorySlot;
import io.zkz.mc.slurpbags.inventory.SlurpBagMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlurpBagScreen extends AbstractContainerScreen<SlurpBagMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");

    public SlurpBagScreen(SlurpBagMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        this.imageHeight = 114 + menu.getInventoryRowCount() * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private float[] getBackgroundColor(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return new float[]{1.0F, 1.0F, 1.0F};
        } else if (dyeColor == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            return dyeColor.getTextureDiffuseColors();
        }
    }

    private int getTextColor(@Nullable DyeColor dyeColor) {
        if (dyeColor == null) {
            return 0x404040;
        } else {
            return DyeColor.WHITE.getTextColor();
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        float[] backgroundColor = getBackgroundColor(DyeColor.RED);
        RenderSystem.setShaderColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0F);

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, 17);
        int containerRows = this.menu.getInventoryRowCount();
        for (int k = 0; k < (int) Math.ceil(containerRows / 6.0); k++) {
            guiGraphics.blit(CONTAINER_BACKGROUND, i, j + 17 + 18 * 6 * k, 0, 17, this.imageWidth, Math.min(containerRows - 6 * k, 6) * 18);
        }
        guiGraphics.blit(CONTAINER_BACKGROUND, i, j + containerRows * 18 + 17, 0, 126, this.imageWidth, 96);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        int textColor = getTextColor(DyeColor.RED);
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, textColor, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, textColor, false);
    }

    @Override
    protected boolean checkHotbarKeyPressed(int keyCode, int scanCode) {
        // prevent number keys from extracting items from a locked slot
        // vanilla only checks the hovered slot for being accessible, but the hotbar item is directly taken from the inventory, not from a slot,
        // therefore ignoring all restrictions put on the corresponding slot in the menu
        // also the hotbar slot has a varying index as the player inventory is always added last, so we store the first hotbar slot during menu construction
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
            for (int i = 0; i < 9; ++i) {
                if (this.minecraft != null && this.minecraft.options.keyHotbarSlots[i].matches(keyCode, scanCode)) {
                    if (this.menu.getSlot(this.menu.getHotbarStartIndex() + i) instanceof LockableInventorySlot slot && slot.locked()) {
                        return true;
                    }
                }
            }
        }

        return super.checkHotbarKeyPressed(keyCode, scanCode);
    }
}
