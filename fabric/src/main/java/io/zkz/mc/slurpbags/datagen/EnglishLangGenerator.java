package io.zkz.mc.slurpbags.datagen;

import io.zkz.mc.slurpbags.item.BagType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnglishLangGenerator extends FabricLanguageProvider {
    protected EnglishLangGenerator(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (BagType type : BagType.values()) {
            translationBuilder.add(type.getItem(), capitalizeFirstLetter(type.getName()) + " Bag");
        }

        translationBuilder.add("itemGroup.slurpbags.main", "Slurp Bags");

        translationBuilder.add("itemTooltip.slurpbags.slurp_mode", "Slurp Mode:");
        translationBuilder.add("itemTooltip.slurpbags.slurp_mode.SLURP_DISABLED", "Disabled");
        translationBuilder.add("itemTooltip.slurpbags.slurp_mode.SLURP_ON_PICKUP", "Pickup");
        translationBuilder.add("itemTooltip.slurpbags.slurp_mode.SLURP_ALWAYS", "Always");

        translationBuilder.add("notification.slurpbags.new_slurp_mode", "New Slurp Mode:");
    }

    private String capitalizeFirstLetter(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
