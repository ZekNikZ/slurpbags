package io.zkz.mc.slurpbags.event;

import dev.architectury.event.events.common.PlayerEvent;
import io.zkz.mc.slurpbags.inventory.SlurpBagInventory;
import io.zkz.mc.slurpbags.inventory.SlurpBagMenu;
import io.zkz.mc.slurpbags.item.BagMode;
import io.zkz.mc.slurpbags.item.SlurpBagItem;
import io.zkz.mc.slurpbags.util.InventoryUtils;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ModEvents {
    public static void register() {
        PlayerEvent.PICKUP_ITEM_POST.register(ModEvents::onPickupItem);
        PlayerEvent.CLOSE_MENU.register(ModEvents::closeMenu);
    }

    private static void onPickupItem(Player player, ItemEntity itemEntity, ItemStack itemStack) {
        if (player.getInventory().items.stream().anyMatch(stackInInventory -> stackInInventory.getItem() instanceof SlurpBagItem slurpBagItem && slurpBagItem.getType().mayContain(itemStack) && slurpBagItem.getMode(stackInInventory) == BagMode.SLURP_ON_PICKUP)) {
            Inventory playerInventory = player.getInventory();

            // We remove the stack first just in case some other mod touched it first
            ItemStack stackToInsert = InventoryUtils.removeFromInventory(playerInventory, itemStack);

            // Find compatible bags
            for (int slotIndex = 0; slotIndex < playerInventory.getContainerSize(); slotIndex++) {
                ItemStack stackInSlot = playerInventory.getItem(slotIndex);
                if (stackInSlot.getItem() instanceof SlurpBagItem slurpBagItem && slurpBagItem.getMode(stackInSlot) == BagMode.SLURP_ON_PICKUP) {
                    if (slurpBagItem.getType().mayContain(stackToInsert)) {
                        SlurpBagInventory bagInventory = new SlurpBagInventory(stackInSlot, slurpBagItem.getType());
                        for (int bagSlotIndex = 0; bagSlotIndex < bagInventory.getContainerSize(); bagSlotIndex++) {
                            ItemStack stackInBagSlot = bagInventory.getItem(bagSlotIndex);
                            if (stackInBagSlot.isEmpty()) {
                                bagInventory.setItem(bagSlotIndex, stackToInsert.copy());
                                stackToInsert.setCount(0);
                            } else if (ItemStack.isSameItemSameTags(stackInBagSlot, stackToInsert)) {
                                int amountThatCanBeInserted = (stackInBagSlot.getMaxStackSize() - stackInBagSlot.getCount());
                                int amountToInsert = Math.min(amountThatCanBeInserted, stackToInsert.getCount());
                                stackInBagSlot.grow(amountToInsert);
                                stackToInsert.shrink(amountToInsert);
                            }
                        }
                        bagInventory.setChanged();
                    }
                }
            }

            if (!stackToInsert.isEmpty()) {
                playerInventory.add(stackToInsert);
            }
        }
    }

    private static void closeMenu(Player player, AbstractContainerMenu menu) {
        if (menu instanceof SlurpBagMenu) {
            SlurpBagItem.closeBags(player);
        }
    }
}
