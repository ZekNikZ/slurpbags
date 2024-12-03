package io.zkz.mc.slurpbags.item.forge;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.item.ItemStack;

public class SlurpBagItem extends io.zkz.mc.slurpbags.item.SlurpBagItem {
    public SlurpBagItem(BagType type) {
        super(type);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
