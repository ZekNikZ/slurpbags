package io.zkz.mc.slurpbags.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import io.zkz.mc.slurpbags.SlurpBags;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class ModRecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(SlurpBags.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static RegistrySupplier<RecipeSerializer<SlurpBagDying>> SLURP_BAG_DYING = RECIPE_SERIALIZERS.register(SlurpBags.id("slurp_bag_dying"), () -> new SimpleCraftingRecipeSerializer<>(SlurpBagDying::new));

    public static void register() {
        RECIPE_SERIALIZERS.register();
    }
}
