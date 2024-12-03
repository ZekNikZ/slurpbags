package io.zkz.mc.slurpbags.tag;

import io.zkz.mc.slurpbags.SlurpBags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> SLURP_BAGS = tag(Registries.ITEM, "slurp_bags");

    public static <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> resourceKey, String id) {
        return TagKey.create(resourceKey, SlurpBags.id(id));
    }
}
