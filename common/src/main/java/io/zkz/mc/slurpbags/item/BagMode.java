package io.zkz.mc.slurpbags.item;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BagMode implements StringRepresentable {
    SLURP_DISABLED,
    SLURP_ON_PICKUP,
    SLURP_ALWAYS;

    @Override
    public @NotNull String getSerializedName() {
        return name();
    }

    public BagMode next() {
        return BagMode.values()[(ordinal() + 1) % BagMode.values().length];
    }
}
