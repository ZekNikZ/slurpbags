package io.zkz.mc.slurpbags.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.zkz.mc.slurpbags.SlurpBags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

import java.util.EnumMap;
import java.util.Map;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SlurpBags.MOD_ID, Registries.ITEM);

    public static Map<BagType, RegistrySupplier<Item>> BAG_ITEMS = new EnumMap<>(BagType.class);

    public static void register() {
        for (BagType type : BagType.values()) {
            BAG_ITEMS.put(type, ITEMS.register(type.getName() + "_bag", () -> makeBagItem(type)));
        }

        ITEMS.register();
    }

    @ExpectPlatform
    private static Item makeBagItem(BagType type) {
        throw new AssertionError("@ExpectPlatform for makeBagItem failed");
    }
}
