package io.zkz.mc.slurpbags.item;

import io.zkz.mc.slurpbags.inventory.LockableInventorySlot;
import io.zkz.mc.slurpbags.inventory.SlurpBagInventory;
import io.zkz.mc.slurpbags.inventory.SlurpBagMenu;
import io.zkz.mc.slurpbags.networking.LockSlotMessage;
import io.zkz.mc.slurpbags.networking.ModNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SlurpBagItem extends Item {
    private final BagType type;

    public SlurpBagItem(final BagType type) {
        super(new Item.Properties().stacksTo(1));
        this.type = type;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (player.isSecondaryUseActive()) {
            // Shift-click = rotate mode
            rotateMode(itemInHand);
            player.displayClientMessage(Component.translatable("notification.slurpbags.new_mode").append(" ").append(Component.literal(getMode(itemInHand).getSerializedName()).withStyle(ChatFormatting.GREEN)), true);
            player.playSound(SoundEvents.NOTE_BLOCK_HAT.value(), 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        } else {
            // Non-shift click = open menu
            if (!level.isClientSide) {
                player.openMenu(this.getMenuProvider(itemInHand));
                player.awardStat(Stats.ITEM_USED.get(this));
                this.lockMySlot(player, itemInHand);
            }
            player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide);
    }

    private MenuProvider getMenuProvider(ItemStack stack) {
        return new SimpleMenuProvider((containerId, inventory, player) -> {
            Container container = new SlurpBagInventory(stack, type);
            return new SlurpBagMenu(this.type, containerId, inventory, container);
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
        SlurpBagInventory container = new SlurpBagInventory(itemEntity.getItem(), type);
        Stream<ItemStack> stream = container.getItems().stream().filter(Predicate.not(ItemStack::isEmpty));
        ItemUtils.onContainerDestroyed(itemEntity, stream);
    }

    public BagType getType() {
        return this.type;
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
        tooltip.add(Component.translatable("itemTooltip.slurpbags.slurpMode").withStyle(ChatFormatting.GRAY).append(" ").append(Component.literal(getMode(itemStack).getSerializedName()).withStyle(ChatFormatting.GREEN)));
    }
}
