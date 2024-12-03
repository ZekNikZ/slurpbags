package io.zkz.mc.slurpbags.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LockableInventorySlot extends Slot {
    private boolean locked;

    public LockableInventorySlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return !this.locked;
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        return !this.locked;
    }

    public void lock() {
        this.locked = true;
    }

    public boolean locked() {
        return this.locked;
    }
}
