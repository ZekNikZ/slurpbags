package io.zkz.mc.slurpbags.item.fabric;

import io.zkz.mc.slurpbags.item.BagType;
import net.minecraft.world.item.Item;

public class ModItemsImpl {
    public static Item makeBagItem(BagType type) {
        return new SlurpBagItem(type);
    }
}