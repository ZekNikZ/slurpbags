package io.zkz.mc.slurpbags.item;

import io.zkz.mc.slurpbags.inventory.LockableInventorySlot;
import io.zkz.mc.slurpbags.inventory.SlurpBagContainer;
import io.zkz.mc.slurpbags.inventory.SlurpBagMenu;
import io.zkz.mc.slurpbags.networking.LockSlotMessage;
import io.zkz.mc.slurpbags.networking.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SlurpBagItem extends Item {
    private final BagType type;
    private final @Nullable DyeColor color;

    public SlurpBagItem(final BagType type, final @Nullable DyeColor color) {
        super(new Item.Properties().stacksTo(1));
        this.type = type;
        this.color = color;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (player.isSecondaryUseActive()) {
            // Shift-click = rotate mode
            rotateMode(itemInHand);
            player.displayClientMessage(Component.translatable("notification.slurpbags.new_slurp_mode").withStyle(ChatFormatting.GRAY).append(" ").append(Component.translatable("itemTooltip.slurpbags.slurp_mode." + getMode(itemInHand).getSerializedName()).withStyle(getMode(itemInHand).getChatColor())), true);
            player.playSound(SoundEvents.NOTE_BLOCK_HAT.value(), 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        } else {
            // Non-shift click = open menu
            if (!level.isClientSide) {
                player.openMenu(this.getMenuProvider(itemInHand));
                player.awardStat(Stats.ITEM_USED.get(this));
                this.lockMySlot(player, itemInHand);
                open(itemInHand);
            }
            player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide);
    }

    private MenuProvider getMenuProvider(ItemStack stack) {
        return new SimpleMenuProvider((containerId, inventory, player) -> {
            SlurpBagContainer container = new SlurpBagContainer(stack, type);
            return new SlurpBagMenu(this.getType(), this.getColor(), containerId, inventory, container);
        }, stack.getHoverName());
    }

    private void lockMySlot(Player player, ItemStack stack) {
        if (!(player.containerMenu instanceof SlurpBagMenu menu)) return;
        NonNullList<ItemStack> items = menu.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == stack) {
                ((LockableInventorySlot) menu.getSlot(i)).lock();
                ModNetworking.CHANNEL.sendToPlayer((ServerPlayer) player, new LockSlotMessage(menu.containerId, i));
                return;
            }
        }
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        SlurpBagContainer container = new SlurpBagContainer(itemEntity.getItem(), type);
        Stream<ItemStack> stream = container.getItems().stream().filter(Predicate.not(ItemStack::isEmpty));
        ItemUtils.onContainerDestroyed(itemEntity, stream);
    }

    public BagType getType() {
        return this.type;
    }

    public @Nullable DyeColor getColor() {
        return this.color;
    }

    public BagMode getMode(ItemStack stack) {
        String mode = stack.getOrCreateTag().getString("mode");
        if (mode.isEmpty()) {
            mode = BagMode.SLURP_ON_PICKUP.getSerializedName();
            stack.getTag().putString("mode", mode);
        }
        return BagMode.valueOf(mode);
    }

    public void rotateMode(ItemStack stack) {
        BagMode mode = getMode(stack);

        stack.getOrCreateTag().putString("mode", mode.next().getSerializedName());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        tooltip.add(Component.translatable("itemTooltip.slurpbags.slurp_mode").withStyle(ChatFormatting.GRAY).append(" ").append(Component.translatable("itemTooltip.slurpbags.slurp_mode." + getMode(itemStack).getSerializedName()).withStyle(getMode(itemStack).getChatColor())));
    }

    public boolean isOpen(ItemStack stack) {
        return stack.getOrCreateTag().getInt("CustomModelData") == 1;
    }

    public void open(ItemStack stack) {
        stack.getOrCreateTag().putInt("CustomModelData", 1);
    }

    public void close(ItemStack stack) {
        stack.getOrCreateTag().putInt("CustomModelData", 0);
    }

    public static void closeBags(Player player) {
        player.getInventory().items.forEach(stack -> {
            if (stack.getItem() instanceof SlurpBagItem bagItem) {
                bagItem.close(stack);
            }
        });
    }

    public boolean mayContain(ItemStack otherStack) {
        return this.getType().mayContain(otherStack);
    }

    public boolean insertIntoBag(ItemStack bagStack, ItemStack stackToAdd) {
        SlurpBagContainer bagInventory = new SlurpBagContainer(bagStack, type);
        return bagInventory.insertItem(stackToAdd);
    }

    @Override
    public void inventoryTick(ItemStack bagStack, Level level, Entity entity, int itemSlot, boolean isSelected) {
        if (getMode(bagStack) != BagMode.SLURP_ALWAYS) {
            return;
        }

        if (!(entity instanceof Player player)) {
            return;
        }

        Inventory inventory = player.getInventory();
        boolean didInsertSome = false;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stackInSlot = inventory.getItem(i);
            if (i != itemSlot && this.mayContain(stackInSlot)) {
                if (this.insertIntoBag(bagStack, stackInSlot)) {
                    didInsertSome = true;
                }
            }
        }

        if (didInsertSome) {
            SlurpBagMenu.updateIfMenuOpen(player);
        }
    }

    public ItemStack getColoredItemStack(DyeColor dyeColor) {
        return new ItemStack(getType().getItem(dyeColor));
    }
}
