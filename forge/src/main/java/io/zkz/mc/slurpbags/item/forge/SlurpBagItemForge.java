package io.zkz.mc.slurpbags.item.forge;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class SlurpBagItemForge extends io.zkz.mc.slurpbags.item.SlurpBagItem {
    public SlurpBagItemForge(BagType type, DyeColor color) {
        super(type, color);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
