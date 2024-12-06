package io.zkz.mc.slurpbags.datagen;

import io.zkz.mc.slurpbags.SlurpBags;
import io.zkz.mc.slurpbags.item.BagType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider<Item> {
    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    public static final TagKey<Item> SLURP_BAGS = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "slurp_bags"));

    public static TagKey<Item> bagWhitelist(BagType type) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, type.getName() + "_bag_whitelist"));
    }

    public static TagKey<Item> bagBlacklist(BagType type) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, type.getName() + "_bag_blacklist"));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Item[] bagItems = Arrays.stream(BagType.values()).map(BagType::getItem).toArray(Item[]::new);
        getOrCreateTagBuilder(SLURP_BAGS)
            .add(bagItems);

        // Disallowed tags
        for (BagType type : BagType.values()) {
            getOrCreateTagBuilder(bagBlacklist(type))
                .addOptionalTag(SLURP_BAGS);
        }

        // Allowed tags
        getOrCreateTagBuilder(bagWhitelist(BagType.SEED))
            .addOptionalTag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
            .add(Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD)
            .addOptionalTag(new ResourceLocation("minecraft:seeds"))
            .addOptionalTag(new ResourceLocation("c:seeds"))
            .addOptionalTag(new ResourceLocation("forge:seeds"));

        getOrCreateTagBuilder(bagWhitelist(BagType.FLOWER))
            .addOptionalTag(ItemTags.FLOWERS)
            .addOptionalTag(new ResourceLocation("c:flowers"))
            .addOptionalTag(new ResourceLocation("forge:flowers"));

        getOrCreateTagBuilder(bagWhitelist(BagType.DYE))
            .add(Items.WHITE_DYE, Items.ORANGE_DYE, Items.MAGENTA_DYE, Items.LIGHT_BLUE_DYE, Items.YELLOW_DYE, Items.LIME_DYE, Items.PINK_DYE, Items.GRAY_DYE, Items.LIGHT_GRAY_DYE, Items.CYAN_DYE, Items.PURPLE_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.GREEN_DYE, Items.RED_DYE, Items.BLACK_DYE)
            .addOptionalTag(new ResourceLocation("minecraft:dyes"))
            .addOptionalTag(new ResourceLocation("c:dyes"))
            .addOptionalTag(new ResourceLocation("forge:dyes"));
    }
}
