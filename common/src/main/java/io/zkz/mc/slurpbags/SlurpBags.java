package io.zkz.mc.slurpbags;

import io.zkz.mc.slurpbags.event.ModEvents;
import io.zkz.mc.slurpbags.inventory.ModMenus;
import io.zkz.mc.slurpbags.item.ModCreativeTabs;
import io.zkz.mc.slurpbags.item.ModItems;
import io.zkz.mc.slurpbags.networking.ModNetworking;
import io.zkz.mc.slurpbags.recipe.ModRecipeSerializers;
import net.minecraft.resources.ResourceLocation;

public final class SlurpBags {
    public static final String MOD_ID = "slurpbags";

    public static void init() {
        ModItems.register();
        ModCreativeTabs.register();
        ModMenus.register();
        ModNetworking.register();
        ModEvents.register();
        ModRecipeSerializers.register();
    }

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }
}
