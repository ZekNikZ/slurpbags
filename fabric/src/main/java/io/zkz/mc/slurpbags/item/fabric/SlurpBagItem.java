package io.zkz.mc.slurpbags.item.fabric;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SlurpBagItem extends io.zkz.mc.slurpbags.item.SlurpBagItem {
    public SlurpBagItem(BagType type) {
        super(type);
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
