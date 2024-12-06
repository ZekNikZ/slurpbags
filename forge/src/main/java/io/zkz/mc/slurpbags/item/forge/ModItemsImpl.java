package io.zkz.mc.slurpbags.item.forge;

import io.zkz.mc.slurpbags.item.BagType;
import io.zkz.mc.slurpbags.item.SlurpBagItem;
import net.minecraft.world.item.DyeColor;

public class ModItemsImpl {
    public static SlurpBagItem makeBagItem(BagType type, DyeColor color) {
        return new SlurpBagItemForge(type, color);
    }
}
