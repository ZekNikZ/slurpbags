package io.zkz.mc.slurpbags.item;

import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BagMode implements StringRepresentable {
    SLURP_DISABLED(ChatFormatting.RED),
    SLURP_ON_PICKUP(ChatFormatting.AQUA),
    SLURP_ALWAYS(ChatFormatting.GREEN);

    private final ChatFormatting chatColor;

    BagMode(ChatFormatting chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name();
    }

    public BagMode next() {
        return BagMode.values()[(ordinal() + 1) % BagMode.values().length];
    }

    public ChatFormatting getChatColor() {
        return this.chatColor;
    }
}
