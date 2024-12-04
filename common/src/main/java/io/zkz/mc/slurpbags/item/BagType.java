package io.zkz.mc.slurpbags.item;

import io.zkz.mc.slurpbags.inventory.ModMenus;
import io.zkz.mc.slurpbags.inventory.SlurpBagMenu;
import io.zkz.mc.slurpbags.tag.ModTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public enum BagType implements StringRepresentable {
    SEED("seed"),
    FLOWER("flower"),
    DYE("dye"),
    POTION("potion"),
    SAPLING("sapling"),
    LEAF("leaf");

    private final String name;

    private final TagKey<Item> whitelistTag;
    private final TagKey<Item> blacklistTag;

    BagType(final String name) {
        this.name = name;
        this.whitelistTag = ModTags.tag(Registries.ITEM, name + "_bag_whitelist");
        this.blacklistTag = ModTags.tag(Registries.ITEM, name + "_bag_blacklist");
    }

    public int getInventoryRowCount() {
        // TODO: config value
        return switch (this) {
            case SEED, FLOWER, SAPLING, LEAF -> 3;
            case DYE, POTION -> 2;
        };
    }

    public SlurpBagItem getItem() {
        return ModItems.BAG_ITEMS.get(this).get();
    }

    public MenuType<SlurpBagMenu> getMenuType() {
        return ModMenus.BAG_MENUS.get(this).get();
    }

    public boolean mayContain(ItemStack itemStack) {
        if (BuiltInRegistries.ITEM.getTagOrEmpty(this.whitelistTag).iterator().hasNext()) {
            return itemStack.is(this.whitelistTag);
        } else if (!itemStack.getItem().canFitInsideContainerItems()) {
            return false;
        } else {
            return !itemStack.is(this.blacklistTag);
        }
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public TagKey<Item> getWhitelistTag() {
        return this.whitelistTag;
    }

    public TagKey<Item> getBlacklistTag() {
        return this.blacklistTag;
    }
}
