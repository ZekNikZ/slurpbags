package io.zkz.mc.slurpbags.item;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.zkz.mc.slurpbags.SlurpBags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(SlurpBags.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> BAGS = CREATIVE_TABS.register("bags",
        () -> CreativeTabRegistry.create(builder -> {
            builder.title(Component.translatable("itemGroup.slurpbags.bags"));
            builder.icon(() -> new ItemStack(ModItems.BAG_ITEMS.get(BagType.SEED)::get));
            builder.displayItems((display, output) -> {
                ModItems.BAG_ITEMS.forEach((type, item) -> output.accept(item::get));
            });
        }));

    public static final RegistrySupplier<CreativeModeTab> BAGS_DYED = CREATIVE_TABS.register("bags_dyed",
        () -> CreativeTabRegistry.create(builder -> {
            builder.title(Component.translatable("itemGroup.slurpbags.bags_dyed"));
            builder.icon(() -> new ItemStack(ModItems.DYED_BAG_ITEMS.get(BagType.SEED).get(DyeColor.YELLOW)::get));
            builder.displayItems((display, output) -> {
                ModItems.DYED_BAG_ITEMS.forEach((type, colorMap) -> {
                    colorMap.forEach((color, item) -> output.accept(item::get));
                });
            });
        }));

    public static void register() {
        CREATIVE_TABS.register();
    }
}
