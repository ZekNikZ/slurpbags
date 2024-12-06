package io.zkz.mc.slurpbags.client.screen;

import dev.architectury.registry.menu.MenuRegistry;
import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.item.DyeColor;

public class ModScreens {
    public static void register() {
        for (BagType type : BagType.values()) {
            MenuRegistry.registerScreenFactory(type.getMenuType(null), SlurpBagScreen::new);
            for (DyeColor color : DyeColor.values()) {
                MenuRegistry.registerScreenFactory(type.getMenuType(color), SlurpBagScreen::new);
            }
        }
    }
}
