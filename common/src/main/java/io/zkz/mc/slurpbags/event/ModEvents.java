package io.zkz.mc.slurpbags.event;

import dev.architectury.event.events.common.PlayerEvent;
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
            boolean didInsertSome = false;
            for (int slotIndex = 0; slotIndex < playerInventory.getContainerSize(); slotIndex++) {
                ItemStack bagStack = playerInventory.getItem(slotIndex);
                if (bagStack.getItem() instanceof SlurpBagItem slurpBagItem && slurpBagItem.getMode(bagStack) == BagMode.SLURP_ON_PICKUP) {
                    if (slurpBagItem.insertIntoBag(bagStack, stackToInsert)) {
                        didInsertSome = true;
                    }
                }
            }

            if (!stackToInsert.isEmpty()) {
                playerInventory.add(stackToInsert);
            }

            if (didInsertSome) {
                SlurpBagMenu.updateIfMenuOpen(player);
            }
        }
    }

    private static void closeMenu(Player player, AbstractContainerMenu menu) {
        if (menu instanceof SlurpBagMenu) {
            SlurpBagItem.closeBags(player);
        }
    }
}
