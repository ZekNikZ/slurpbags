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

    public static final TagKey<Item> ALLOWED_IN_SEED_BAG = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "allowed_in_seed_bag"));
    public static final TagKey<Item> NOT_ALLOWED_IN_SEED_BAG = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "not_allowed_in_seed_bag"));

    public static final TagKey<Item> ALLOWED_IN_FLOWER_BAG = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "allowed_in_flower_bag"));
    public static final TagKey<Item> NOT_ALLOWED_IN_FLOWER_BAG = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "not_allowed_in_flower_bag"));

    public static final TagKey<Item> ALLOWED_IN_DYE_BAG = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "allowed_in_dye_bag"));
    public static final TagKey<Item> NOT_ALLOWED_IN_DYE_BAG = TagKey.create(Registries.ITEM, new ResourceLocation(SlurpBags.MOD_ID, "not_allowed_in_dye_bag"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Item[] bagItems = Arrays.stream(BagType.values()).map(BagType::getItem).toArray(Item[]::new);
        getOrCreateTagBuilder(SLURP_BAGS)
            .add(bagItems);

        getOrCreateTagBuilder(ALLOWED_IN_SEED_BAG)
            .addOptionalTag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
            .add(Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS, Items.BEETROOT_SEEDS, Items.TORCHFLOWER_SEEDS, Items.PITCHER_POD)
            .addOptionalTag(new ResourceLocation("minecraft:seeds"))
            .addOptionalTag(new ResourceLocation("c:seeds"))
            .addOptionalTag(new ResourceLocation("forge:seeds"));
        getOrCreateTagBuilder(NOT_ALLOWED_IN_SEED_BAG)
            .addOptionalTag(SLURP_BAGS);

        getOrCreateTagBuilder(ALLOWED_IN_FLOWER_BAG)
            .addOptionalTag(ItemTags.FLOWERS)
            .addOptionalTag(new ResourceLocation("c:flowers"))
            .addOptionalTag(new ResourceLocation("forge:flowers"));
        getOrCreateTagBuilder(NOT_ALLOWED_IN_FLOWER_BAG)
            .addOptionalTag(SLURP_BAGS);

        getOrCreateTagBuilder(ALLOWED_IN_DYE_BAG)
            .add(Items.WHITE_DYE, Items.ORANGE_DYE, Items.MAGENTA_DYE, Items.LIGHT_BLUE_DYE, Items.YELLOW_DYE, Items.LIME_DYE, Items.PINK_DYE, Items.GRAY_DYE, Items.LIGHT_GRAY_DYE, Items.CYAN_DYE, Items.PURPLE_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.GREEN_DYE, Items.RED_DYE, Items.BLACK_DYE)
            .addOptionalTag(new ResourceLocation("minecraft:dyes"))
            .addOptionalTag(new ResourceLocation("c:dyes"))
            .addOptionalTag(new ResourceLocation("forge:dyes"));
        getOrCreateTagBuilder(NOT_ALLOWED_IN_DYE_BAG)
            .addOptionalTag(SLURP_BAGS);
    }
}
