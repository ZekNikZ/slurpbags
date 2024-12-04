package io.zkz.mc.slurpbags.item.fabric;

import io.zkz.mc.slurpbags.item.BagType;
import io.zkz.mc.slurpbags.item.SlurpBagItem;

public class ModItemsImpl {
    public static SlurpBagItem makeBagItem(BagType type) {
        return new SlurpBagItemFabric(type);
    }
}
