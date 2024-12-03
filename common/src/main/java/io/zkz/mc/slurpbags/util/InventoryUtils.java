package io.zkz.mc.slurpbags.util;

import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public class InventoryUtils {
    public static ItemStack removeFromInventory(Container container, ItemStack stackToRemove) {
        int availableCount = ContainerHelper.clearOrCountMatchingItems(container, (stack) -> ItemStack.isSameItemSameTags(stack, stackToRemove), stackToRemove.getCount(), false);
        container.setChanged();
        return stackToRemove.copyWithCount(availableCount);
    }
}
