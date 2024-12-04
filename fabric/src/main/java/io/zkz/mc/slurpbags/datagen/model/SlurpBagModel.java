package io.zkz.mc.slurpbags.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.zkz.mc.slurpbags.SlurpBags;
import io.zkz.mc.slurpbags.item.SlurpBagItem;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class SlurpBagModel {
    public static void createTemplate(BiConsumer<ResourceLocation, Supplier<JsonElement>> output) {
        output.accept(SlurpBags.id("item/slurp_bag"), () -> {
            JsonObject modelObj = new JsonObject();
            modelObj.addProperty("parent", "minecraft:item/generated");
            {
                JsonObject texturesObj = new JsonObject();
                texturesObj.addProperty("layer0", "#bag");
                texturesObj.addProperty("layer1", "#band");
                modelObj.add("textures", texturesObj);
            }
            return modelObj;
        });
    }

    public static void create(SlurpBagItem item, BiConsumer<ResourceLocation, Supplier<JsonElement>> output) {
        output.accept(SlurpBags.id("item/" + item.getType().getName() + "_bag"), () -> {
            JsonObject modelObj = buildBase(item, false);
            {
                JsonArray overridesObj = new JsonArray();
                {
                    JsonObject overrideObj = new JsonObject();
                    {
                        JsonObject predicateObj = new JsonObject();
                        predicateObj.addProperty("custom_model_data", 1);
                        overrideObj.add("predicate", predicateObj);
                    }
                    overrideObj.addProperty("model", "slurpbags:item/" + item.getType().getName() + "_bag_open");
                    overridesObj.add(overrideObj);
                }
                modelObj.add("overrides", overridesObj);
            }
            return modelObj;
        });

        output.accept(SlurpBags.id("item/" + item.getType().getName() + "_bag_open"), () ->
            buildBase(item, true));
    }

    private static JsonObject buildBase(SlurpBagItem item, boolean open) {
        JsonObject modelObj = new JsonObject();
        modelObj.addProperty("parent", "slurpbags:item/slurp_bag");
        {
            JsonObject texturesObj = new JsonObject();
            texturesObj.addProperty("bag", "slurpbags:item/bag" + (open ? "_open" : ""));
            texturesObj.addProperty("band", "slurpbags:item/" + item.getType().getName() + "_band" + (open ? "_open" : ""));
            modelObj.add("textures", texturesObj);
        }
        return modelObj;
    }
}
