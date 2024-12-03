package io.zkz.mc.slurpbags.client.screen;

import dev.architectury.registry.menu.MenuRegistry;
import io.zkz.mc.slurpbags.item.BagType;

public class ModScreens {
    public static void register() {
        for (BagType type : BagType.values()) {
            MenuRegistry.registerScreenFactory(type.getMenuType(), SlurpBagScreen::new);
        }
    }
}
