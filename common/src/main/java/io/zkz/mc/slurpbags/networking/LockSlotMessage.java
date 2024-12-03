package io.zkz.mc.slurpbags.networking;

import dev.architectury.networking.NetworkManager;
import io.zkz.mc.slurpbags.inventory.LockableInventorySlot;
import io.zkz.mc.slurpbags.inventory.SlurpBagMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class LockSlotMessage {
    public final int containerId;
    public final int slotId;

    public LockSlotMessage(FriendlyByteBuf buf) {
        this(buf.readByte(), buf.readShort());
    }

    public LockSlotMessage(int containerId, int slotId) {
        this.containerId = containerId;
        this.slotId = slotId;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        buf.writeShort(this.slotId);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        NetworkManager.PacketContext context = contextSupplier.get();
        Player player = context.getPlayer();
        if (player != null && player.containerMenu.containerId == this.containerId && player.containerMenu instanceof SlurpBagMenu menu) {
            ((LockableInventorySlot) menu.getSlot(slotId)).lock();
        }
    }
}
