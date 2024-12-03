package io.zkz.mc.slurpbags.inventory;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlurpBagMenu extends AbstractContainerMenu {
    private final Container container;
    private final BagType bagType;
    private int hotbarStartIndex;

    public static MenuType.MenuSupplier<SlurpBagMenu> create(BagType type) {
        return (containerId, inventory) -> new SlurpBagMenu(type, containerId, inventory);
    }

    private SlurpBagMenu(BagType type, int containerId, Inventory inventory) {
        this(type, containerId, inventory, new SimpleContainer(type.getInventoryRowCount() * 9));
    }

    public SlurpBagMenu(BagType type, int containerId, Inventory inventory, Container container) {
        super(type.getMenuType(), containerId);

        int rowCount = type.getInventoryRowCount();
        checkContainerSize(container, rowCount * 9);
        this.container = container;
        this.bagType = type;
        container.startOpen(inventory.player);
        int i = (rowCount - 4) * 18;

        // Bag inventory
        for (int j = 0; j < rowCount; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new FilteredBagSlot(bagType, container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        // Inventory
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new LockableInventorySlot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        // Hotbar
        for (int i1 = 0; i1 < 9; ++i1) {
            Slot hotbarSlot = this.addSlot(new LockableInventorySlot(inventory, i1, 8 + i1 * 18, 161 + i));
            if (i1 == 0) {
                this.hotbarStartIndex = hotbarSlot.index;
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public int getInventoryRowCount() {
        return this.bagType.getInventoryRowCount();
    }

    public int getHotbarStartIndex() {
        return this.hotbarStartIndex;
    }
}
