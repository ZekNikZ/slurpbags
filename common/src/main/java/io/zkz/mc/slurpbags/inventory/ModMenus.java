package io.zkz.mc.slurpbags.inventory;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.zkz.mc.slurpbags.SlurpBags;
import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import java.util.EnumMap;
import java.util.Map;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(SlurpBags.MOD_ID, Registries.MENU);

    public static Map<BagType, RegistrySupplier<MenuType<SlurpBagMenu>>> BAG_MENUS = new EnumMap<>(BagType.class);

    public static void register() {
        for (BagType type : BagType.values()) {
            BAG_MENUS.put(type, MENUS.register(type.getName() + "_bag", () -> new MenuType<>(SlurpBagMenu.create(type), FeatureFlags.DEFAULT_FLAGS)));
        }

        MENUS.register();
    }
}
