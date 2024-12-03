package io.zkz.mc.slurpbags.networking;

import dev.architectury.networking.NetworkChannel;
import io.zkz.mc.slurpbags.SlurpBags;

public class ModNetworking {
    public static final NetworkChannel CHANNEL = NetworkChannel.create(SlurpBags.id("networking_channel"));

    public static void register() {
        CHANNEL.register(LockSlotMessage.class, LockSlotMessage::encode, LockSlotMessage::new, LockSlotMessage::apply);
    }
}
