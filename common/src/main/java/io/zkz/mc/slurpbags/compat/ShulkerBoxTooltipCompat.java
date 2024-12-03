package io.zkz.mc.slurpbags.compat;

import com.misterpemodder.shulkerboxtooltip.api.ShulkerBoxTooltipApi;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import io.zkz.mc.slurpbags.SlurpBags;
import io.zkz.mc.slurpbags.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ShulkerBoxTooltipCompat implements ShulkerBoxTooltipApi {
    @Override
    public void registerProviders(@NotNull PreviewProviderRegistry registry) {
        registry.register(new ResourceLocation(SlurpBags.MOD_ID, "slurp_bags"), new SlurpBagPreviewProvider(),
            ModItems.BAG_ITEMS.values().stream().map(Supplier::get).toList());
    }

    @ExpectPlatform
    public static void initCompat() {
        throw new AssertionError("@ExpectPlatform for initCompat failed");
    }
}
