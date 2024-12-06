package io.zkz.mc.slurpbags.datagen;

import io.zkz.mc.slurpbags.recipe.ModRecipeSerializers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.SpecialRecipeBuilder;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        SpecialRecipeBuilder.special(ModRecipeSerializers.SLURP_BAG_DYING.get()).save(exporter, "slurp_bag_dying");
    }
}
