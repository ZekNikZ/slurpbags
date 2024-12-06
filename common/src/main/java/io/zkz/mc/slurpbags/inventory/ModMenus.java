package io.zkz.mc.slurpbags.inventory;

import com.google.common.collect.ImmutableMap;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.zkz.mc.slurpbags.SlurpBags;
import io.zkz.mc.slurpbags.item.BagType;
import io.zkz.mc.slurpbags.item.SlurpBagItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(SlurpBags.MOD_ID, Registries.MENU);

    public static Map<BagType, RegistrySupplier<MenuType<SlurpBagMenu>>> BAG_MENUS = new EnumMap<>(BagType.class);
    public static Map<BagType, Map<DyeColor, RegistrySupplier<MenuType<SlurpBagMenu>>>> DYED_BAG_MENUS = new EnumMap<>(BagType.class);

    public static void register() {
        for (BagType type : BagType.values()) {
            BAG_MENUS.put(type, MENUS.register(type.getName() + "_bag", () -> new MenuType<>(SlurpBagMenu.create(type, null), FeatureFlags.DEFAULT_FLAGS)));
            DYED_BAG_MENUS.put(type, Arrays.stream(DyeColor.values()).collect(ImmutableMap.toImmutableMap(Function.identity(), (color) -> MENUS.register(color.getName() + "_" + type.getName() + "_bag", () -> new MenuType<>(SlurpBagMenu.create(type, color), FeatureFlags.DEFAULT_FLAGS)))));
        }

        MENUS.register();
    }
}
