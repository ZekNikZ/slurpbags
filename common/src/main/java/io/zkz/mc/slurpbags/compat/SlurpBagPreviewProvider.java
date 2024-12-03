package io.zkz.mc.slurpbags.compat;

import com.misterpemodder.shulkerboxtooltip.api.PreviewContext;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import io.zkz.mc.slurpbags.item.SlurpBagItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlurpBagPreviewProvider implements PreviewProvider {
    @Override
    public boolean shouldDisplay(@NotNull PreviewContext context) {
        return !getInventory(context).stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public List<ItemStack> getInventory(@NotNull PreviewContext context) {
        NonNullList<ItemStack> items = NonNullList.withSize(((SlurpBagItem) context.stack().getItem()).getType().getInventoryRowCount() * 9, ItemStack.EMPTY);
        CompoundTag tag = context.stack().getTag();
        if (tag != null) {
            ContainerHelper.loadAllItems(tag, items);
        }
        return items;
    }

    @Override
    public int getInventoryMaxSize(@NotNull PreviewContext context) {
        return getInventory(context).size();
    }
}
