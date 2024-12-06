package io.zkz.mc.slurpbags.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGeneratorEntrypoint implements net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(ItemTagGenerator::new);
        pack.addProvider(EnglishLangGenerator::new);
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(RecipeGenerator::new);
    }
}
