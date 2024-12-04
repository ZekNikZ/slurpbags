package io.zkz.mc.slurpbags.datagen;

import io.zkz.mc.slurpbags.datagen.model.SlurpBagModel;
import io.zkz.mc.slurpbags.item.BagType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        SlurpBagModel.createTemplate(generators.output);

        for (BagType type : BagType.values()) {
            SlurpBagModel.create(type.getItem(), generators.output);
        }
    }
}
