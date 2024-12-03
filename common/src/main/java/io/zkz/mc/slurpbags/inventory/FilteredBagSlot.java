package io.zkz.mc.slurpbags.inventory;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FilteredBagSlot extends Slot {
    private final BagType type;

    public FilteredBagSlot(BagType type, Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        this.type = type;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return this.type.mayContain(stack);
    }
}
