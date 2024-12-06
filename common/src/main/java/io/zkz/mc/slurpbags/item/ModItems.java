package io.zkz.mc.slurpbags.item;

import com.google.common.collect.ImmutableMap;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.zkz.mc.slurpbags.SlurpBags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(SlurpBags.MOD_ID, Registries.ITEM);

    public static Map<BagType, RegistrySupplier<SlurpBagItem>> BAG_ITEMS = new EnumMap<>(BagType.class);
    public static Map<BagType, Map<DyeColor, RegistrySupplier<SlurpBagItem>>> DYED_BAG_ITEMS = new EnumMap<>(BagType.class);

    public static void register() {
        for (BagType type : BagType.values()) {
            BAG_ITEMS.put(type, ITEMS.register(type.getName() + "_bag", () -> makeBagItem(type, null)));
            DYED_BAG_ITEMS.put(type, Arrays.stream(DyeColor.values()).collect(ImmutableMap.toImmutableMap(Function.identity(), (color) -> ITEMS.register(color.getName() + "_" + type.getName() + "_bag", () -> makeBagItem(type, color)))));
        }

        ITEMS.register();
    }

    @ExpectPlatform
    private static SlurpBagItem makeBagItem(BagType type, @Nullable DyeColor color) {
        throw new AssertionError("@ExpectPlatform for makeBagItem failed");
    }
}
