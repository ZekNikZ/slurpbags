package io.zkz.mc.slurpbags.recipe;

import io.zkz.mc.slurpbags.item.SlurpBagItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SlurpBagDying extends CustomRecipe {
    public SlurpBagDying(ResourceLocation key, CraftingBookCategory category) {
        super(key, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int slurpBagCount = 0;
        int dyeCount = 0;

        for (int k = 0; k < container.getContainerSize(); ++k) {
            ItemStack itemStack = container.getItem(k);
            if (!itemStack.isEmpty()) {
                Item item = itemStack.getItem();
                if (item instanceof SlurpBagItem) {
                    ++slurpBagCount;
                } else if (item instanceof DyeItem) {
                    ++dyeCount;
                } else {
                    return false;
                }

                if (dyeCount > 1 || slurpBagCount > 1) {
                    return false;
                }
            }
        }

        return slurpBagCount == 1 && dyeCount == 1;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack bagStack = ItemStack.EMPTY;
        SlurpBagItem bagItem = null;
        DyeItem dyeItem = (DyeItem) Items.WHITE_DYE;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack stackInSlot = container.getItem(i);
            if (!stackInSlot.isEmpty()) {
                Item item = stackInSlot.getItem();
                if (item instanceof SlurpBagItem bagItem1) {
                    bagStack = stackInSlot;
                    bagItem = bagItem1;
                } else if (item instanceof DyeItem dyeItem1) {
                    dyeItem = dyeItem1;
                }
            }
        }

        if (bagItem == null) {
            throw new IllegalStateException("No bag item found");
        }

        ItemStack resultStack = bagItem.getColoredItemStack(dyeItem.getDyeColor());
        if (bagStack.hasTag()) {
            resultStack.setTag(Objects.requireNonNull(bagStack.getTag()).copy());
        }

        return resultStack;
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SLURP_BAG_DYING.get();
    }
}
