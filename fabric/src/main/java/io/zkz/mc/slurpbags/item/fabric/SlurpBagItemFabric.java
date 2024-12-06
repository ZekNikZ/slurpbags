package io.zkz.mc.slurpbags.item.fabric;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class SlurpBagItemFabric extends io.zkz.mc.slurpbags.item.SlurpBagItem {
    public SlurpBagItemFabric(BagType type, DyeColor color) {
        super(type, color);
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
