package io.zkz.mc.slurpbags.compat.forge;

import com.misterpemodder.shulkerboxtooltip.api.forge.ShulkerBoxTooltipPlugin;
import io.zkz.mc.slurpbags.compat.ShulkerBoxTooltipCompat;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ShulkerBoxTooltipCompatImpl {
    public static void initCompat() {
        FMLJavaModLoadingContext.get().registerExtensionPoint(ShulkerBoxTooltipPlugin.class,
            () -> new ShulkerBoxTooltipPlugin(ShulkerBoxTooltipCompat::new));

    }
}
