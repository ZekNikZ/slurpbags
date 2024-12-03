package io.zkz.mc.slurpbags.fabric.client;

import io.zkz.mc.slurpbags.client.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;

public final class SlurpBagsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.register();
    }
}
