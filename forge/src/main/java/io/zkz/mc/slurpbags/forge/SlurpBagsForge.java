package io.zkz.mc.slurpbags.forge;

import io.zkz.mc.slurpbags.SlurpBags;
import dev.architectury.platform.forge.EventBuses;
import io.zkz.mc.slurpbags.client.screen.ModScreens;
import io.zkz.mc.slurpbags.compat.ShulkerBoxTooltipCompat;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SlurpBags.MOD_ID)
public final class SlurpBagsForge {
    public SlurpBagsForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(SlurpBags.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        SlurpBags.init();

        // Initialize client setup
        FMLJavaModLoadingContext.get().getModEventBus().register(this);

        // Mod integrations
        if (ModList.get().isLoaded("shulkerboxtooltip")) {
            ShulkerBoxTooltipCompat.initCompat();
        }
    }

    @SubscribeEvent
    void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModScreens::register);
    }
}
