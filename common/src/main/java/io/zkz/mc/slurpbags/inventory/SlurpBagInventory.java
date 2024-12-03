package io.zkz.mc.slurpbags.inventory;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlurpBagInventory implements WorldlyContainer {
    private final ItemStack stack;
    private final NonNullList<ItemStack> items;

    public SlurpBagInventory(ItemStack stack, BagType type) {
        this.stack = stack;
        this.items = NonNullList.withSize(type.getInventoryRowCount() * 9, ItemStack.EMPTY);
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            ContainerHelper.loadAllItems(tag, items);
        }
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }
        return result;
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack stack, @Nullable Direction dir) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction dir) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return getItems().size();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(getItems(), slot, amount);
        if (!result.isEmpty()) {
            setChanged();
        }
        return result;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(getItems(), slot);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    public void setChanged() {
        CompoundTag tag = stack.getOrCreateTag();
        ContainerHelper.saveAllItems(tag, items);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    @Override
    public void clearContent() {
        getItems().clear();
    }
}
